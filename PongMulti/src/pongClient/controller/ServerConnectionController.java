package pongClient.controller;


import java.io.File;
import java.io.IOException;
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
import pongClient.view.ServerConnectionView;
import utilityWindows.AlertBox;


public class ServerConnectionController {

	private TitleScreenController mainController;
	private ServerConnectionView connectionView;
	private GameAnimationTimer animationTimer;
	private Stage stage;
	private long timeElapsed;
	
	private String serverIp;
	private int serverPort;
	
	private Socket socket;
	
	public Socket getSocket() {
		return socket;
	}

	private UserInputQueue userInputQueue;
	public UserInputQueue getUserInputQueue() {
		return userInputQueue;
	}
	
	private Media soundcsgo;
	private MediaPlayer musicPlayer;
	
	
	public Stage getStage() {
		return stage;
	}

	
	public ServerConnectionController(TitleScreenController controller) {
		mainController = controller;
		animationTimer = new GameAnimationTimer();
		userInputQueue = new UserInputQueue();
	}

	public void initialize(){
		
		soundcsgo = new Media(new File("resources/sounds/csgoFindServer.mp3").toURI().toString());
		
		stage = mainController.getStage();
		stage.setTitle("PongClientApp");
		stage.setOnCloseRequest(e -> stage_CloseRequest(e));
		stage.show();
		
		connectionView = new ServerConnectionView(this);
		connectionView.initialize();
		animationTimer.start();
	
	}

	public void draw(){
		
		KeyCode keyCode = userInputQueue.getKeyCode();
		
		if (keyCode == KeyCode.ESCAPE) {
			stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		}
		
		if(timeElapsed == 1){
			musicPlayer = new MediaPlayer(soundcsgo);
			musicPlayer.play();
		}
		
		timeElapsed++;
		
	}

	public static boolean validIP(String ip) {

		if (ip.equals("localhost")) {
			return true;
		} else {

			try {
				if (ip == null || ip.isEmpty()) {
					return false;
				}

				String[] parts = ip.split("\\.");
				if (parts.length != 4) {
					return false;
				}

				for (String s : parts) {
					int i = Integer.parseInt(s);
					if ((i < 0) || (i > 255)) {
						return false;
					}
				}
				if (ip.endsWith(".")) {
					return false;
				}

				return true;
			} catch (NumberFormatException nfe) {
				return false;
			}
		}
	}
	
	public static boolean validPort(String _port){
		try{
			int serverPort = Integer.parseInt(_port);
			if(serverPort<1||serverPort>65535){
				return false;
			}
			else{
				return true;
			}
			
		}
		catch(NumberFormatException e){
			return false;
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
				}
			}
		});
	}


	public void backButtonPressed() {
		stop();
		mainController.initialize();
		
	}

	private void stop(){
		musicPlayer.stop();
		animationTimer.stop();
	}
	
	
	public void connectButtonPressed() {
		if(validIP(connectionView.getTextFieldIp().getText())&&validPort(connectionView.getTextFieldPort().getText())){
			
			serverIp = connectionView.getTextFieldIp().getText();
			serverPort = Integer.parseInt(connectionView.getTextFieldPort().getText());
			connectServer();
			
		}
		else{
			AlertBox.showAndWait(AlertType.ERROR, "Pong", "Bad values entered. Try again.");
			connectionView.initialize();
		}
		
	}
	
	public void connectServer(){
		/*try {
			socket = new Socket(serverIp, serverPort);
		} catch (IOException e) {
			AlertBox.showAndWait(AlertType.ERROR, "Pong", "Can't connect to the server.");
			//e.printStackTrace();
			initialize();
		}*/
		
		stop();
		ClientGameController gameViewControl = new ClientGameController(this);
		gameViewControl.initialize();
	}

	
}