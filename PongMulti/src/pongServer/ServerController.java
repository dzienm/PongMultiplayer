package pongServer;

import java.io.IOException;
import java.net.ServerSocket;

import gameUtilities.UserInputQueue;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pongServer.model.PongServerPoll;
import pongServer.view.ServerScreen;
import utilityWindows.AlertBox;

public class ServerController {

	private Stage serverStage;
	
	private UserInputQueue userInputQueue;
	private GameAnimationTimer animationTimer;
	
	private int serverPort;
	public int getServerPort() {
		return serverPort;
	}


	private ServerSocket serverPong;
	private ServerStateEnum serverState;
	
	private int totalSecondsLapsed;
	private long startNanoTime;
	
	ServerScreen serverView;
	
	public ServerController() {
		serverStage = new Stage();
		animationTimer = new GameAnimationTimer();
		
	}

	public void initialize(){
		
		serverStage.setTitle("PongServerApp");
		serverStage.setOnCloseRequest(e -> stage_CloseRequest(e));
		serverStage.show();
		
		serverPort = -1;
		serverState = ServerStateEnum.NotConnected;
		totalSecondsLapsed = 0;
		
		startNanoTime = System.nanoTime();
		serverPong = new PongServerPoll();
		
		serverView = new ServerScreen(this);
		serverView.initialize();
		animationTimer.start();
		
	}
	
	private void reset(){
		serverView = new ServerScreen(this);
		serverView.initialize();
		serverPort = -1;
		serverPong = new PongServerPoll();
		setServerStarted(false);
	}
	
	public void draw(long timeCurrent){
		if(serverState==ServerStateEnum.NotConnected){
			if(totalSecondsLapsed%2 == 0){
				serverView.getServerStateText().setVisible(false);}
			else{
				serverView.getServerStateText().setVisible(true);
			}
		}
		
		if(serverState==ServerStateEnum.Initialized){
			
			serverView.getServerStartedText().setText("Server started at " + serverPort + ".");
			serverView.getServerStartedText().setVisible(true);
			serverView.getServerStateText().setVisible(false);       
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
			try{
				serverPort = Integer.parseInt(serverView.getTextField().getText());
				if(serverPort<1||serverPort>65535){
					throw new Exception();
				}
				startServer();
			}
			catch(Exception e){
				AlertBox.showAndWait(AlertType.ERROR, "Pong", "Bad value entered. Try again."); 
				reset();
			}
			
			
	}

	public void updateTotalTime(long currentNanoTime) {
		
		if((currentNanoTime - startNanoTime)> 1000000000){
			startNanoTime = System.nanoTime();
			++totalSecondsLapsed;
		}
		
	}

	private void startServer() {
		
		serverView.getStartServerButton().setVisible(false);
		serverView.getStopServerButton().setVisible(true);
		
		
		try {
			serverPong = new ServerSocket(serverPort);
			setServerStarted(true);
		} catch (IOException e) {
			AlertBox.showAndWait(AlertType.ERROR, "Pong", "Can't start server.");
			e.printStackTrace();
			reset();
		}
		
		
	}

	public void stopServerButtonPressed() {
		serverPong.stopServer(this);
		reset();
	}

	public void serverError() {
		AlertBox.showAndWait(AlertType.ERROR, "Pong", "Server error.");
	}
	
	
	private class GameAnimationTimer extends AnimationTimer {


		@Override
		public void handle(long currentNanoTime) {
			updateTotalTime(currentNanoTime);
			updateGameState(currentNanoTime);
			draw(currentNanoTime);
		}
		
		private void updateGameState(long currentNanoTime) {
			
		}
		

	}


	public void setServerStarted(boolean started) {
		if(started){
			serverState = ServerStateEnum.Initialized;
		}
		else{
			serverState = ServerStateEnum.NotConnected;
		}
	}


}
