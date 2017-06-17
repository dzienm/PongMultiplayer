package pongClient.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import gameUtilities.GameUtilitiesVariables;
import gameUtilities.UserInputQueue;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pongClient.view.ClientGameView;
import pongServer.ServerStateEnum;
import utilityWindows.AlertBox;

/**
 * 
 * @author cprzyborowski
 *
 */
public class ClientGameController {

	private ServerConnectionController mainController;
	private ClientGameView gameView;
	private GameAnimationTimer animationTimer;
	private Stage stage;
	private long timeElapsed;
	private double secondsElapsed;

	private int racketSpeed;

	private UserInputQueue userInputQueue;

	public UserInputQueue getUserInputQueue() {
		return userInputQueue;
	}

	private Socket socket;
	private ServerStateEnum serverState;

	private boolean scored;
	
	@SuppressWarnings("unused")
	private ObjectOutputStream objectWriter;
	private ObjectInputStream objectReader;
	private DataOutputStream dataWriter;
	private DataInputStream dataReader;

	private Media soundPop;
	private MediaPlayer soundPlayer;

	public Stage getStage() {
		return stage;
	}

	public ClientGameController(ServerConnectionController controller) {
		mainController = controller;
		animationTimer = new GameAnimationTimer();
		socket = controller.getSocket();
		userInputQueue = new UserInputQueue();
		scored = false;
	}

	public void initialize() {

		soundPop = new Media(new File("resources/sounds/Blop.mp3").toURI().toString());

		try {
			objectWriter = new ObjectOutputStream(socket.getOutputStream());
			objectReader = new ObjectInputStream(socket.getInputStream());
			dataWriter = new DataOutputStream(socket.getOutputStream());
			dataReader = new DataInputStream(socket.getInputStream());
		} catch (IOException e1) {
			
			System.err.println("Communication error. Close the program.");
			System.exit(0);
		}

		stage = mainController.getStage();
		stage.setTitle("PongClientApp");
		stage.setOnCloseRequest(e -> stage_CloseRequest(e));
		stage.show();

		gameView = new ClientGameView(this);
		gameView.initialize();
		racketSpeed = gameView.getRacketSpeed();
		animationTimer.start();

	}

	public void draw() {

		try {
			serverState = (ServerStateEnum) objectReader.readObject();
			
		} catch (ClassNotFoundException e) {
			System.err.println("Communication error. Close the program.");
			System.exit(0);
		} catch (IOException e) {
			System.err.println("Communication error. Close the program.");
			System.exit(0);
		}

		switch (serverState) {

		case ConnectionEstablished:
			gameView.getGameStatusText().setText("Waiting for the game to start.");
			gameView.getGameStatusText().setTranslateX(GameUtilitiesVariables.gameBoardWidth/2 - 230);
			gameView.getGameStatusText().setVisible(true);
			gameView.getGameInfoText().setVisible(true);
			gameView.getGameInfoText().setOpacity(Math.abs(Math.sin(secondsElapsed/4 * 2 * Math.PI)));
			exchangeData();
			break;

		case GameStarted:
			gameView.getGameStatusText().setVisible(false);
			gameView.getGameInfoText().setVisible(false);
			gameView.getGameOverText().setVisible(false);
			keyboardController();
			exchangeData();
			if(scored){
				gameView.getClientRacket().getRacket().setTranslateX(GameUtilitiesVariables.initialRacketBoundaryOffset);
				gameView.getClientRacket().getRacket().setTranslateY(GameUtilitiesVariables.gameBoardHeight/2 - GameUtilitiesVariables.racketHeight/2);
				
			}
			soundHandle();
			break;

		case GamePaused:
			gameView.getGameStatusText().setText("Game paused.");
			gameView.getGameStatusText().setTranslateX(GameUtilitiesVariables.gameBoardWidth/2 - 130);
			gameView.getGameStatusText().setVisible(true);
			gameView.getGameInfoText().setVisible(true);
			gameView.getGameInfoText().setOpacity(Math.abs(Math.sin(secondsElapsed/4 * 2 * Math.PI)));
			exchangeData();
			break;

		case GameOver:
			exchangeData();
			gameView.getGameStatusText().setText("Game over.");
			gameView.getGameStatusText().setTranslateX(GameUtilitiesVariables.gameBoardWidth/2 - 100);
			gameView.getGameStatusText().setVisible(true);
			gameView.getGameOverText().setVisible(true);
			gameView.getGameOverText().setOpacity(Math.abs(Math.sin(secondsElapsed/4 * 2 * Math.PI)));
			break;

		default:
			break;
		}

		timeElapsed++;
		secondsElapsed = timeElapsed / 60.0;
	}

	private void exchangeData() {
		try {
			dataWriter.writeDouble(gameView.getClientRacket().getPositionX());
			dataWriter.writeDouble(gameView.getClientRacket().getPositionY());
			double serverRacketPosX = (double) dataReader.readDouble();
			double serverRacketPosY = (double) dataReader.readDouble();
			gameView.getServerRacket().setPosition(serverRacketPosX, serverRacketPosY);
			double ballPosX = (double) dataReader.readDouble();
			double ballPosY = (double) dataReader.readDouble();
			gameView.getPongBall().setPosition(ballPosX, ballPosY);
			double clientRacketPosX = (double) dataReader.readDouble();
			double clientRacketPosY = (double) dataReader.readDouble();
			gameView.getClientRacket().setPosition(clientRacketPosX, clientRacketPosY);
			int serverScore = (int) dataReader.readInt();
			int clientScore = (int) dataReader.readInt();
			gameView.getServerScoreText().setText("" + serverScore);
			gameView.getClientScoreText().setText("" + clientScore);
			scored = (boolean) dataReader.readBoolean();

		} catch (IOException e) {
			
			System.err.println("Communication error. Close the program.");
			System.exit(0);
		}
	}

	private void soundHandle() {
		if (gameView.getClientRacket().intersectBall(gameView.getPongBall())) {
			soundPlayer = new MediaPlayer(soundPop);
			soundPlayer.play();
		}

		if (gameView.getServerRacket().intersectBall(gameView.getPongBall())) {
			soundPlayer = new MediaPlayer(soundPop);
			soundPlayer.play();
		}

		if (gameView.getUpperBound().intersectBall(gameView.getPongBall())) {
			soundPlayer = new MediaPlayer(soundPop);
			soundPlayer.play();
		}

		if (gameView.getLowerBound().intersectBall(gameView.getPongBall())) {
			soundPlayer = new MediaPlayer(soundPop);
			soundPlayer.play();
		}
	}

	private void keyboardController() {
		KeyCode keyCode = userInputQueue.getKeyCode();

		if (keyCode == KeyCode.ESCAPE) {
			stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		}

		if (keyCode == KeyCode.S) {
			if (gameView.getClientRacket().getRacket().getTranslateY() < gameView.getBoardHeight()
					- gameView.getClientRacket().getRacket().getHeight()) {
				double racketY = gameView.getClientRacket().getRacket().getTranslateY();
				racketY += racketSpeed;
				gameView.getClientRacket().getRacket().setTranslateY(racketY);
			}
		}

		if (keyCode == KeyCode.W) {
			if (gameView.getClientRacket().getRacket().getTranslateY() > 0) {
				double racketY = gameView.getClientRacket().getRacket().getTranslateY();
				racketY -= racketSpeed;
				gameView.getClientRacket().getRacket().setTranslateY(racketY);
			}
		}

		if (keyCode == KeyCode.A) {
			if (gameView.getClientRacket().getRacket().getTranslateX() > 0) {
				double racketX = gameView.getClientRacket().getRacket().getTranslateX();
				racketX -= racketSpeed;
				gameView.getClientRacket().getRacket().setTranslateX(racketX);
			}
		}

		if (keyCode == KeyCode.D) {
			if (gameView.getClientRacket().getRacket().getTranslateX() < gameView.getBoardWidth() / 2 - 30) {
				double racketX = gameView.getClientRacket().getRacket().getTranslateX();
				racketX += racketSpeed;
				gameView.getClientRacket().getRacket().setTranslateX(racketX);
			}
		}

	}

	private class GameAnimationTimer extends AnimationTimer {

		@Override
		public void handle(long currentNanoTime) {
			draw();
		}

	}

	public void stage_CloseRequest(WindowEvent windowEvent) {
		windowEvent.consume();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (AlertBox.showAndWait(AlertType.CONFIRMATION, "Pong", "Do you want to close the program?")
						.orElse(ButtonType.CANCEL) == ButtonType.OK) {
					stage.close();
					System.exit(0);
				}
			}
		});
	}

	public void backButtonPressed() {
		animationTimer.stop();
		mainController.initialize();

	}

}
