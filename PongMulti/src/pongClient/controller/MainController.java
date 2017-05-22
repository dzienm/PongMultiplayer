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
import pongClient.model.PongGameState;
import utilityWindows.AlertBox;

public class MainController {

	private Stage primaryStage;
	private GameAnimationTimer animationTimer;
	private PongGameState gameState;
	
	private UserInputQueue userInputQueue;
	
	private TitleScreenController titleScreenControl; //kazdy kontroler z animacja trzeba deklarowac albo miec oddzielne klasy gameAnimationTimer
	
	private Image gameIcon;
	
	public MainController(Stage _stage)
	{
		this.primaryStage = _stage;
		
	}

	public void initialize(){
		
		gameState = new PongGameState();
		userInputQueue = new UserInputQueue();
		loadContent();
		
		this.primaryStage.setTitle("PongMulti");
		this.primaryStage.getIcons().add(gameIcon);
		primaryStage.setOnCloseRequest(e -> stage_CloseRequest(e));
		primaryStage.show();
		
		this.titleScreenControl = new TitleScreenController(this);
		
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
					primaryStage.close();
				}
			}
		});
	}
	
	public PongGameState getCurrentGameState(){
		return gameState;
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
			switch (gameState.getGameState()) {
			case TitleScreen:
				
				titleScreenControl.draw();
				
//				if (keyCode == KeyCode.SPACE) {
//					state = State.Playing;
//					gameBoard.generateNewPieces();
//				} else if (keyCode == KeyCode.ESCAPE) {
//					stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
//				}
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
	

}
