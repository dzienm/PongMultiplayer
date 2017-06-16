package pongServer;

import gameUtilities.GameUtilitiesVariables;
import gameUtilities.UserInputQueue;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pongClient.controller.TitleScreenController;
import pongServer.view.ServerScreen;
import utilityWindows.AlertBox;

public class ServerController {

	private Stage serverStage;
	private TitleScreenController mainController; //byc moze niepotrzebny jesli otwierany jako oddzielne okno
	
	
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
		serverStage = _controller.getStage();
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
		
		serverView = new ServerScreen(this);
		serverView.initialize();
		animationTimer.start();
		
	}
	
	public void reset(){
		serverState = ServerStateEnum.NotConnected;
		serverView = new ServerScreen(this);
		serverView.initialize();
		serverPort = -1;
		
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
			serverView.getWaitingConnectionText().setVisible(true);
			
			if(totalSecondsLapsed%2 == 0){
				serverView.getWaitingConnectionText().setVisible(false);}
			else{
				serverView.getWaitingConnectionText().setVisible(true);
			}
			
		}
		
		if(serverState==ServerStateEnum.ConnectionEstablished){
			serverView.getWaitingConnectionText().setVisible(false);
			serverView.getClientConnectedText().setVisible(true);
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
		if(GameUtilitiesVariables.validPort(serverView.getTextField().getText())){
			serverPort = Integer.parseInt(serverView.getTextField().getText());
			startServer();
		}
		else{
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
		
		serverState = ServerGameController.initializeConnection(serverPort);
		
		if(serverState == ServerStateEnum.NotConnected){
			AlertBox.showAndWait(AlertType.ERROR, "Pong", "Can't start the server.");
			reset();
		}
		else{
			serverState = ServerStateEnum.Initialized;
			serverView.getStartServerButton().setVisible(false);
			serverView.getStopServerButton().setVisible(true);
			ServerGameController serverGame = new ServerGameController(this);
			serverGame.initialize();
		}
		
		
	}

	

	public void stopServerButtonPressed() {

		//clientThread.interrupt();
		reset();
		//mainController.setServerController(new ServerController(mainController));
		//mainController.getServerController().initialize();
		//serverStage.close();
		
		/*try {
			
			serverPong.close();
		} catch (IOException e) {
			
			AlertBox.showAndWait(AlertType.ERROR, "Pong", "Error when stopping the server. ");
			e.printStackTrace();
		}*/
		
		
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


}
