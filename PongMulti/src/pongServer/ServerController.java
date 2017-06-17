package pongServer;

import java.io.File;

import gameUtilities.GameUtilitiesVariables;
import gameUtilities.UserInputQueue;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pongClient.controller.TitleScreenController;
import pongServer.view.ServerScreen;
import utilityWindows.AlertBox;

public class ServerController {

	private Stage serverStage;
	private TitleScreenController mainController; // byc moze niepotrzebny jesli
													// otwierany jako oddzielne
													// okno

	private Media soundcsgo;
	private MediaPlayer musicPlayer;
		
	private ServerGameController serverGame;

	private UserInputQueue userInputQueue;
	private GameAnimationTimer animationTimer;

	public GameAnimationTimer getAnimationTimer() {
		return animationTimer;
	}

	private int serverPort;

	public int getServerPort() {
		return serverPort;
	}

	private ServerStateEnum serverState;

	public ServerStateEnum getServerState() {
		return serverState;
	}

	public void setServerState(ServerStateEnum serverState) {
		this.serverState = serverState;
	}

	private int totalSecondsLapsed;
	private long startNanoTime;

	ServerScreen serverView;

	public ServerController(TitleScreenController _controller) {
		mainController = _controller;
		
		animationTimer = new GameAnimationTimer();
	}

	/**
	 * Funkcja inicjalizujaca stan poczatkowy kontrolera.
	 * @author mdziendzikowski
	 */
	public void initialize() {
		serverStage = mainController.getStage();
		serverStage.setTitle("PongServerApp");
		serverStage.setOnCloseRequest(e -> stage_CloseRequest(e));
		serverStage.show();

		soundcsgo = new Media(new File("resources/sounds/csgoFindServer.mp3").toURI().toString());
		musicPlayer = new MediaPlayer(soundcsgo);
		musicPlayer.play();
		
		serverPort = -1;
		serverState = ServerStateEnum.NotConnected;
		totalSecondsLapsed = 0;

		startNanoTime = System.nanoTime();

		serverView = new ServerScreen(this);
		serverView.initialize();
		animationTimer.start();

	}

	public void reset() {
		serverState = ServerStateEnum.NotConnected;
		serverView = new ServerScreen(this);
		serverView.initialize();
		serverPort = -1;

	}

	public void draw(long timeCurrent) {

		switch (serverState) {

		case NotConnected:
			if (totalSecondsLapsed % 2 == 0) {
				serverView.getServerStateText().setVisible(false);
			} else {
				serverView.getServerStateText().setVisible(true);
			}
			break;

		case Initialized:
			serverView.getServerStartedText().setText("Server started at " + serverPort + ".");
			serverView.getServerStartedText().setVisible(true);
			serverView.getServerStateText().setVisible(false);
			serverView.getWaitingConnectionText().setVisible(true);
			if (totalSecondsLapsed % 2 == 0) {
				serverView.getWaitingConnectionText().setVisible(false);
			} else {
				serverView.getWaitingConnectionText().setVisible(true);
			}
			break;

		case ConnectionEstablished:
			serverView.getWaitingConnectionText().setVisible(false);
			serverView.getClientConnectedText().setVisible(true);
			serverView.getStartGameButton().setVisible(true);
			break;

		case GameOver:
			serverView.getStopGameButton().setVisible(false);
			serverView.getRestartGameButton().setVisible(true);
			musicPlayer = new MediaPlayer(soundcsgo);
			musicPlayer.play();
			animationTimer.stop();
			break;
			
		default:
			break;
		}

	}

	public void stage_CloseRequest(WindowEvent windowEvent) {
		windowEvent.consume();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (AlertBox.showAndWait(AlertType.CONFIRMATION, "Pong", "Do you want to close the server?")
						.orElse(ButtonType.CANCEL) == ButtonType.OK) {
					serverStage.close();
				}
			}
		});
	}

	public Stage getServerStage() {
		return serverStage;
	}

	public UserInputQueue getUserInputQueue() {
		return userInputQueue;
	}

	public void startServerButtonPressed() {
		if (GameUtilitiesVariables.validPort(serverView.getTextField().getText())) {
			serverPort = Integer.parseInt(serverView.getTextField().getText());
			startServer();
		} else {
			AlertBox.showAndWait(AlertType.ERROR, "Pong", "Bad value entered. Try again.");
			reset();
		}
	}

	public void updateTotalTime(long currentNanoTime) {

		if ((currentNanoTime - startNanoTime) > 1000000000) {
			startNanoTime = System.nanoTime();
			++totalSecondsLapsed;
		}

	}

	private void startServer() {

		serverGame = new ServerGameController(this);
		serverState = serverGame.initializeConnection(serverPort);

		if (serverState == ServerStateEnum.NotConnected) {
			AlertBox.showAndWait(AlertType.ERROR, "Pong", "Can't start the server.");
			reset();
		} 
		else {
			serverView.getStartServerButton().setVisible(false);
			serverView.getMainMenuButton().setVisible(false);
		//	serverView.getStopServerButton().setVisible(true);
		}

	}

	public void stopServerButtonPressed() {

		// clientThread.interrupt();
		reset();
		// mainController.setServerController(new
		// ServerController(mainController));
		// mainController.getServerController().initialize();
		// serverStage.close();

		/*
		 * try {
		 * 
		 * serverPong.close(); } catch (IOException e) {
		 * 
		 * AlertBox.showAndWait(AlertType.ERROR, "Pong",
		 * "Error when stopping the server. "); e.printStackTrace(); }
		 */

	}


	public void startGameButtonPressed() {
		musicPlayer.stop();
		serverState = ServerStateEnum.GameStarted;
		serverView.getStartGameButton().setVisible(false);
		serverView.getStopGameButton().setVisible(true);
	}

	public void stopGameButtonPressed() {
		serverState = ServerStateEnum.GamePaused;
		serverView.getStartGameButton().setVisible(true);
		serverView.getStopGameButton().setVisible(false);
		musicPlayer = new MediaPlayer(soundcsgo);
		musicPlayer.play();
	}
	
	public void restartGameButtonPressed() {
		serverView.getStopGameButton().setVisible(true);
		serverView.getRestartGameButton().setVisible(false);
		musicPlayer.stop();
		serverGame.resetScore();
		serverGame.getGameView().initialize();
		serverState = ServerStateEnum.GameStarted;
		animationTimer.start();
	}
	
	private class GameAnimationTimer extends AnimationTimer {

		@Override
		public void handle(long currentNanoTime) {
			updateTotalTime(currentNanoTime);
			draw(currentNanoTime);
		}

	}

	public void mainMenuButtonPressed() {
		musicPlayer.stop();
		animationTimer.stop();
		mainController.initialize();
	}


}
