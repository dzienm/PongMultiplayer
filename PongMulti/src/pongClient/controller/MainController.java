package pongClient.controller;

import gameUtilities.UserInputQueue;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pongClient.model.GameStateEnum;
import pongServer.ServerController;
import utilityWindows.AlertBox;

public class MainController {

	private Stage primaryStage;
	private GameAnimationTimer animationTimer;
	private GameStateEnum gameState;
	
	public GameStateEnum getGameState() {
		return gameState;
	}

	public void setGameState(GameStateEnum gameState) {
		this.gameState = gameState;
	}

	private UserInputQueue userInputQueue;
	
	private TitleScreenController titleScreenControl; //kazdy kontroler z animacja trzeba deklarowac albo miec oddzielne klasy gameAnimationTimer
	private ServerController serverController;
	private ServerConnectionController clientConnectionController;
	
	
	public ServerConnectionController getClientConnectionController() {
		return clientConnectionController;
	}

	public void setServerController(ServerController serverController) {
		this.serverController = serverController;
	}

	private Image gameIcon;
	
	public MainController(Stage _stage)
	{
		this.primaryStage = _stage;
		
	}

	public void initialize(){
		
		gameState = GameStateEnum.TitleScreen;
		userInputQueue = new UserInputQueue();
		loadContent();
		
		this.primaryStage.setTitle("PongMulti");
		this.primaryStage.getIcons().add(gameIcon);
		primaryStage.setOnCloseRequest(e -> stage_CloseRequest(e));
		primaryStage.show();
		
		titleScreenControl = new TitleScreenController(this);
		serverController = new ServerController(this);
		clientConnectionController = new ServerConnectionController(this);
		
		//do wykomentowania to co ponizej
		serverController.initialize();
		
		this.animationTimer = new GameAnimationTimer();
		animationTimer.start();
		
	}
	
	private void loadContent(){
		this.gameIcon = new Image("icons/Game.png");
	}
	
	private void stage_CloseRequest(WindowEvent windowEvent) {
		windowEvent.consume();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (AlertBox.showAndWait(AlertType.CONFIRMATION, "Pong", "Do you want to stop the game?")
						.orElse(ButtonType.CANCEL) == ButtonType.OK) {
					animationTimer.stop();
					//unloadContent();
					titleScreenControl.close();
					primaryStage.close();
				}
			}
		});
	}
	

	
	
	private class GameAnimationTimer extends AnimationTimer {


		@Override
		public void handle(long currentNanoTime) {
			
			update(currentNanoTime);
			//			draw(currentNanoTime);
			//			
			//			if((currentNanoTime-startNanoTime)/1000000>1000){
			//            	startNanoTime = System.nanoTime();
			//            	++gameTime;
			//            	
			//            }
			//            
			//            if(state == State.Playing){
			//            	int gameTimeMs = (int) (currentNanoTime/1000000 - startNanoTime/1000000 + gameTime * 1000);
			//            	currentWaterLevel = (maxWaterLevel * gameTimeMs)/(gameMaxTime*1000); 
			//            }
			//            
			//            if(state == State.TitleScreen){
			//            	gameTime = 0;
			//            }
			//            
			//            if(state == State.Playing&&currentWaterLevel>maxWaterLevel){
			//            	state = State.GameOver;
			//            	gameTime = 0;
			//            }
			//            
			//            if(state == State.GameOver && gameTime>5){
			//            	state = State.TitleScreen;
			//            	gameLevel = 1;
			//            	playerScore = 0;
			//            	currentWaterLevel = 0;
			//            }

		}
		
		private void update(long currentNanoTime) {
			
			KeyCode keyCode = userInputQueue.getKeyCode();
			
			if (keyCode == KeyCode.ESCAPE) {
				primaryStage.fireEvent(new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST));
			}
			switch (gameState) {
			case TitleScreen:
				titleScreenControl.draw();
				break;
				
			case ClientConnectionSetup:
				clientConnectionController.draw(currentNanoTime);
				break;
//			case Playing:
//				gameBoard.resetWater();
//				gameBoard.generateNewPieces();
//				
//				for (int y = 0; y < GameBoard.GAME_BOARD_HEIGHT; y++) {
//					checkScoringChain(gameBoard.getWaterChain(y));
//				}
//				gameBoard.updateFadingPieces();
//				handleMouseInput();
//				break;
			default:
				break;
			}
		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public UserInputQueue getUserInputQueue() {
		return userInputQueue;
	}

	public ServerController getServerController() {
		return serverController;
	}

	

}
