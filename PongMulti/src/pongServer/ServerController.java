package pongServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

import gameUtilities.UserInputQueue;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pongClient.controller.MainController;
import pongClient.model.PongBall;
import pongServer.view.ServerScreen;
import utilityWindows.AlertBox;

public class ServerController {

	private Stage serverStage;
	private MainController mainController;
	
	private HashMap<Long,Thread> connectionThreads;
	
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

	private Socket clientSocket;

	private ObjectOutputStream dataWriter;
	private ObjectInputStream dataReader;
	
	private Thread clientThread;
	
	public ServerController(MainController _controller) {
		serverStage = new Stage();
		animationTimer = new GameAnimationTimer();
		mainController = _controller;
	}

	public void initialize(){
		
		System.setProperty("sun.net.useExclusiveBind", "false"); //konieczne dla ustawienia setReusePort
		
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
	
	private void reset(){
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
			
			serverPong = new ServerSocket();
			serverPong.setReuseAddress(true);
			serverPong.bind(new InetSocketAddress(serverPort));
			serverState = ServerStateEnum.Initialized;
			clientCommChannels();
		} catch (IOException e) {
			AlertBox.showAndWait(AlertType.ERROR, "Pong", "Can't start the server.");
			e.printStackTrace();
			reset();
		}
		
		
	}

	private void clientCommChannels() {


		Runnable clientListener = new Runnable(){

			@Override
			public void run() {
				try {
					System.out.println("Czekam na polaczenie");
					clientSocket = serverPong.accept();
					serverState = ServerStateEnum.ConnectionEstablished;
					//dataWriter = new ObjectOutputStream(clientSocket.getOutputStream());
					//dataReader = new ObjectInputStream(clientSocket.getInputStream());
					//serverState = ServerStateEnum.ConnectionEstablished;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//AlertBox.showAndWait(AlertType.ERROR, "Pong", "Connection lost.");
					e.printStackTrace();
					reset();
					
				} 
				while(true){

					try{

						ObjectOutputStream pileczkaWriter = new ObjectOutputStream(clientSocket.getOutputStream());
						ObjectInputStream pileczkaReader = new ObjectInputStream(clientSocket.getInputStream());

						PongBall pileczka = (PongBall) pileczkaReader.readObject();
						System.out.println("Odebralem pileczke predkosc: Vx = " + pileczka.getVelocityX() + "Vy = " + pileczka.getVelocityY());
						pileczka.setVelocity(2*pileczka.getVelocityX(), 2*pileczka.getVelocityY());
						//System.out.println("Wysylam pilke predkosc x 2");
						//pileczkaWriter.writeObject(pileczka);
					}
					catch(IOException ex){
						System.out.println("Connection lost.");
						serverState = ServerStateEnum.NotConnected;
						//reset();
						break;
					}

					catch (ClassNotFoundException e) {
						System.out.println("Program error. Illegal request sent to the server.");
						e.printStackTrace();
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
		connectionThreads.put(clientThread.getId(), clientThread);
		clientThread.start();



	}

	private void dataFlow() {
		while(true){
			try {
				dataWriter.writeObject(serverState);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
	}

	public void stopServerButtonPressed() {

		clientThread.interrupt();
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
