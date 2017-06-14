package pongClient.controller;


import java.io.File;

import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pongClient.view.ServerConnectionView;
import utilityWindows.AlertBox;


public class ServerConnectionController {

	private MainController mainController;
	private ServerConnectionView connectionView;
	//private GameAnimationTimer animationTimer;
	private Stage stage;
	
	private Media soundcsgo;
	private MediaPlayer musicPlayer;
	
	
	public Stage getStage() {
		return stage;
	}

	
	public ServerConnectionController(MainController controller) {
		mainController = controller;
		
	}

	public void initialize(){
		
		soundcsgo = new Media(new File("resources/sounds/csgoTitleScreen.mp3").toURI().toString());
		
		stage = mainController.getPrimaryStage();
		stage.setTitle("PongClientApp");
		stage.setOnCloseRequest(e -> stage_CloseRequest(e));
		stage.show();
		
		connectionView = new ServerConnectionView(this);
		connectionView.initialize();
		//animationTimer.start();
	
	}

	public void draw(long currentTime){
		
		musicPlayer = new MediaPlayer(soundcsgo);
		musicPlayer.play();
		
	}

	public static boolean validIP(String ip) {

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
	
	/*private class GameAnimationTimer extends AnimationTimer {


		@Override
		public void handle(long currentNanoTime) {
			draw(currentNanoTime);
		}
		
	}*/
	
	public void stage_CloseRequest(WindowEvent windowEvent) {
		windowEvent.consume();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (AlertBox.showAndWait(AlertType.CONFIRMATION, "Pong", "Do you want to close the server?")
						.orElse(ButtonType.CANCEL) == ButtonType.OK) {
					stage.close();	
				}
			}
		});
	}

	
}
