package pongServer;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

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
import pongClient.model.PongBall;
import pongServer.view.ServerGameView;
import utilityWindows.AlertBox;


public class ServerGameController {

	private ServerController serverController;
	private ServerGameView gameView;
	private GameAnimationTimer animationTimer;
	private Stage stage;
	private long timeElapsed;
	private double secondsElapsed;
	
	private static ServerSocket serverPong;
	private Socket clientSocket;

	private ObjectOutputStream objectWriter;
	private ObjectInputStream objectReader;
	private DataOutputStream dataWriter;
	private DataInputStream dataReader;
	
	private Thread clientThread;
	
	private int racketSpeed;
	
	private UserInputQueue userInputQueue;
	public UserInputQueue getUserInputQueue() {
		return userInputQueue;
	}
	
	private Socket socket;
	public Socket getSocket() {
		return socket;
	}

	private Media soundPop;
	private MediaPlayer soundPlayer;
	
	private int clientScore;
	private int serverScore;
	
	public Stage getStage() {
		return stage;
	}

	
	public ServerGameController(ServerController controller) {
		serverController = controller;
		animationTimer = new GameAnimationTimer();
		//socket = controller.getSocket();
		userInputQueue = new UserInputQueue();
		clientScore = 0;
		serverScore = 0;
		
	}

	public void initialize(){
		
		soundPop = new Media(new File("resources/sounds/Blop.mp3").toURI().toString());
		
		stage = new Stage();
		stage.setTitle("PongClientApp");
		stage.setOnCloseRequest(e -> stage_CloseRequest(e));
		stage.show();
		
		gameView = new ServerGameView(this);
		gameView.initialize();
		
		racketSpeed = gameView.getRacketSpeed();
		animationTimer.start();
	
		clientServerCommChannel();
		
	}

	public void draw(){
		
		KeyCode keyCode = userInputQueue.getKeyCode();
		
		if (keyCode == KeyCode.ESCAPE) {
			stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		}
		
		if (keyCode == KeyCode.DOWN) {
			if (gameView.getServerRacket().getRacket().getTranslateY() < gameView.getBoardHeight()
					- gameView.getServerRacket().getRacket().getHeight()) {
				double racketY = gameView.getServerRacket().getRacket().getTranslateY();
				racketY += racketSpeed;
				gameView.getServerRacket().getRacket().setTranslateY(racketY);
			}
		}
		
		if (keyCode == KeyCode.UP) {
			if (gameView.getServerRacket().getRacket().getTranslateY() > 0) {
				double racketY = gameView.getServerRacket().getRacket().getTranslateY();
				racketY -= racketSpeed;
				gameView.getServerRacket().getRacket().setTranslateY(racketY);
			}
		}
		
		if (keyCode == KeyCode.LEFT) {
			if (gameView.getServerRacket().getRacket().getTranslateX() > gameView.getBoardWidth()/2 + 30) {
				double racketX = gameView.getServerRacket().getRacket().getTranslateX();
				racketX -= racketSpeed;
				gameView.getServerRacket().getRacket().setTranslateX(racketX);
			}
		}
		
		if (keyCode == KeyCode.RIGHT) {
			if (gameView.getServerRacket().getRacket().getTranslateX() < gameView.getBoardWidth()) {
				double racketX = gameView.getServerRacket().getRacket().getTranslateX();
				racketX += racketSpeed;
				gameView.getServerRacket().getRacket().setTranslateX(racketX);
			}
		}
		
		if(gameView.getClientRacket().intersectBall(gameView.getPongBall())){
			soundPlayer = new MediaPlayer(soundPop);
			soundPlayer.play();
		}
		
		if(gameView.getServerRacket().intersectBall(gameView.getPongBall())){
			soundPlayer = new MediaPlayer(soundPop);
			soundPlayer.play();
		}
		
		if(gameView.getUpperBound().intersectBall(gameView.getPongBall())){
			soundPlayer = new MediaPlayer(soundPop);
			soundPlayer.play();
		}
		
		if(gameView.getLowerBound().intersectBall(gameView.getPongBall())){
			soundPlayer = new MediaPlayer(soundPop);
			soundPlayer.play();
		}
		
		if(gameView.getPongBall().getBall().getCenterX()<0){
			serverScore++;
			gameView.initialize();
			gameView.getClientScoreText().setText("" + clientScore);
			gameView.getServerScoreText().setText("" + serverScore);
			//gameView.getPongBall().setVelocity(-1 * gameView.getInitialBallSpeed(), 0);
		}
		
		if(gameView.getPongBall().getBall().getCenterX()>gameView.getBoardWidth()){
			clientScore++;
			gameView.initialize();
			gameView.getClientScoreText().setText("" + clientScore);
			gameView.getServerScoreText().setText("" + serverScore);
			//gameView.getPongBall().setVelocity(gameView.getInitialBallSpeed(), 0);
		}
		
		gameView.getPongBall().updatePosition();
		
		timeElapsed++;
		secondsElapsed = timeElapsed/60.0;
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
				}
			}
		});
	}


	private void clientServerCommChannel() {

		Runnable clientListener = new Runnable(){

			@Override
			public void run() {
				try {
					System.out.println("Czekam na polaczenie");
					clientSocket = serverPong.accept();
					//serverState = ServerStateEnum.ConnectionEstablished;
					objectWriter = new ObjectOutputStream(clientSocket.getOutputStream());
					objectReader = new ObjectInputStream(clientSocket.getInputStream());
					//serverState = ServerStateEnum.ConnectionEstablished;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					AlertBox.showAndWait(AlertType.ERROR, "Pong", "Connection lost.");
					e.printStackTrace();
					//reset();
					try {
						serverPong.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					serverController.reset();
					//ServerController newServer = new ServerController(new TitleScreenController(new Stage()));
					//newServer.initialize();
					stage.close();
					
				} 

				while(true){

					try{

						switch(serverController.getServerState()){
						
						case Initialized:
							break;
						
						case ConnectionEstablished:
							objectWriter.writeObject(serverController.getServerState());
							break;
							
						case GameStarted:
							objectWriter.writeObject(serverController.getServerState());
							double clientRacketPosX = dataReader.readDouble();
							double clientRacketPosY = dataReader.readDouble();
							gameView.getClientRacket().setPosition(clientRacketPosX, clientRacketPosY);
							
							dataWriter.writeDouble(gameView.getServerRacket().getPositionX());
							dataWriter.writeDouble(gameView.getServerRacket().getPositionY());
							dataWriter.writeDouble(gameView.getPongBall().getPositionX());
							dataWriter.writeDouble(gameView.getPongBall().getPositionY());
							dataWriter.writeDouble(clientRacketPosX);
							dataWriter.writeDouble(clientRacketPosY);
							dataWriter.writeInt(serverScore);
							dataWriter.writeInt(clientScore);
							
							break;
							
						default:
							stage.close();
							serverController.reset();
							break;
						}
						
						
					}
					catch(IOException ex){
						System.out.println("Connection lost.");
						serverController.reset();
						clientThread.interrupt();
						stage.close();
						break;
					}


				}
				//try{
					//serverPong.close();
					
				//}
				//catch(IOException ex1){}
			/*	try {
					serverPong.close();
				} catch (IOException ex) {
					AlertBox.showAndWait(AlertType.ERROR, "Pong", "Error when closing the server.");
					ex.printStackTrace();
				}*/
				
				
				//ObjectInputStream pileczkaReader = new ObjectInputStream(clientSocket.getInputStream());

				//PongBall pileczka = (PongBall) pileczkaReader.readObject();
				//System.out.println("Odebralem pileczke predkosc: Vx = " + pileczka.getVelocityX() + "Vy = " + pileczka.getVelocityY());

				//dataFlow();
				

			}

		};

		clientThread = new Thread(clientListener);
		clientThread.start();

	}

	public static ServerStateEnum initializeConnection(int _port) {
		try {
			System.setProperty("sun.net.useExclusiveBind", "false"); //konieczne dla ustawienia setReusePort
			serverPong = new ServerSocket();
			serverPong.setReuseAddress(true);
			serverPong.bind(new InetSocketAddress(_port));
			return ServerStateEnum.Initialized;
			//clientCommChannels();
		} catch (IOException e) {
			//AlertBox.showAndWait(AlertType.ERROR, "Pong", "Can't start the server.");
			//e.printStackTrace();
			return ServerStateEnum.NotConnected;
		}
		
	}


	
}