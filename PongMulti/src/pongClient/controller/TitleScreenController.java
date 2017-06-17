package pongClient.controller;

import java.io.File;

import gameUtilities.UserInputQueue;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pongClient.view.TitleScreenView;
import pongServer.ServerController;
import utilityWindows.AlertBox;

/**
 * Kontroler ekranu powitalnego aplikacji.
 * @author mdziendzikowski
 *
 */

public class TitleScreenController {

	private GameAnimationTimer animationTimer;
	private long timeElapsed;
	private Stage stage;
	private Image gameIcon;
	
	private UserInputQueue userInputQueue;
	
	public UserInputQueue getUserInputQueue() {
		return userInputQueue;
	}

	public Stage getStage() {
		return stage;
	}


	Media soundPop;
	Media soundcsgo;
	MediaPlayer mediaPlayer;
	MediaPlayer musicPlayer;
	TitleScreenView currentView;
	
	public TitleScreenController(Stage primaryStage) {
		animationTimer = new GameAnimationTimer();
		stage = primaryStage;
		userInputQueue = new UserInputQueue();
	}

	/**
	 * Metoda inicjujaca kontroler.
	 * @author mdziendzikowski
	 */
	public void initialize(){
		loadContent();
		
		musicPlayer = new MediaPlayer(soundcsgo);
		
		stage.setTitle("Pong Multiplayer");
		stage.setOnCloseRequest(e -> stage_CloseRequest(e));
		stage.getIcons().add(gameIcon);
		stage.show();
		
		currentView = new TitleScreenView(this);
		currentView.initialize();
		
		timeElapsed = 0;
		animationTimer.start();
		
		//do wyrzucenia
		//openServer();
		//openClient();
	}
	
	/**
	 * Metoda wczytujaca zasoby kontrolera.
	 * @author mdziendzikowski
	 */
	private void loadContent(){
		gameIcon = new Image("icons/Game.png");
		soundPop = new Media(new File("resources/sounds/Blop.mp3").toURI().toString());
		soundcsgo = new Media(new File("resources/sounds/csgoTitleScreen.mp3").toURI().toString());
	}
	
	/**
	 * Metoda aktualizujaca widok ekranu powitalnego wywolywana przez watek petli gry.
	 * @author mdziendzikowski
	 */
	public void draw() {
		
		KeyCode keyCode = userInputQueue.getKeyCode();
		
		if (keyCode == KeyCode.ESCAPE) {
			stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		}
		
		if(timeElapsed == 15){
			mediaPlayer = new MediaPlayer(soundPop);
			mediaPlayer.play();
		}
		
		if(timeElapsed > 30){
			currentView.getRacket_1().getRacket().setVisible(true);
		}
		
		if(timeElapsed == 35){
			mediaPlayer = new MediaPlayer(soundPop);
			mediaPlayer.play();
		}
		
		if(timeElapsed > 40){
			currentView.getRacket_2().getRacket().setVisible(true);
		}
		
		if(timeElapsed == 65){
			mediaPlayer = new MediaPlayer(soundPop);
			mediaPlayer.play();
		}
		
		if(timeElapsed > 70){
			currentView.getBall_1().getBall().setVisible(true);
		}
		

		if(timeElapsed > 135){
			currentView.getStartServerText().setVisible(true);
			
		}
		
		if(timeElapsed > 138){
			currentView.getFindServerText().setVisible(true);
			
		}
		
		if(timeElapsed > 141){
			currentView.getCreditsText().setVisible(true);
		}
		
		if(timeElapsed == 140){
			musicPlayer.play();
		}
		
		if(timeElapsed > 150){
			currentView.getBall_1().updatePosition();
		}
		
		if(currentView.getRacket_1().intersectBall(currentView.getBall_1())){
			mediaPlayer = new MediaPlayer(soundPop);
			mediaPlayer.play();
		}
		
		if(currentView.getRacket_2().intersectBall(currentView.getBall_1())){
			mediaPlayer = new MediaPlayer(soundPop);
			mediaPlayer.play();
		}
		
		timeElapsed++;
		
	}

	
	public void playPressed() {
		System.out.println("wcisnieto");
	}
	
	/**
	 * Metoda wywolujaca ekran kontrolera serwera aplikacji.
	 * @author mdziendzikowski
	 */
	public void openServer(){
		close();
		ServerController serverControl = new ServerController(this);
		serverControl.initialize();
	}

	/**
	 * Metoda wywolujaca ekran kontrolera klienta aplikacji.
	 * @author mdziendzikowski
	 */
	public void openClient(){
		close();
		ServerConnectionController connectionControl = new ServerConnectionController(this);
		connectionControl.initialize();
		
	}
	
	/**
	 * Metoda zatrzymujaca petle gry ekranu powitalnego.
	 */
	public void close(){
		musicPlayer.stop();
		animationTimer.stop();
	}
	
	/**
	 * Metoda obslugujaca zdarzenie wywolania zamkniecia okna ekranu powitalnego
	 * @author mdziendzikowski
	 * @param windowEvent zdarzenie zamkniecie okna
	 */
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
	
	/**
	 * Klasa watku petli gry kontrolera.
	 * @author mdziendzikowski
	 *
	 */
	private class GameAnimationTimer extends AnimationTimer {


		@Override
		public void handle(long currentNanoTime) {
			
			draw();
		}
	
	}
	
}
