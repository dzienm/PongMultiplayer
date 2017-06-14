package pongClient.controller;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import pongClient.view.TitleScreenView;

//warto przyjrzec sie patternowi Dependency Injection/ Inversion of Control

public class TitleScreenController {

	private MainController mainController;
	private long timeElapsed;
	Media soundPop;
	Media soundcsgo;
	MediaPlayer mediaPlayer;
	MediaPlayer musicPlayer;
	TitleScreenView currentView;
	
	public TitleScreenController(MainController controller) {
		mainController = controller;
		initialize();
		
	}

	public void initialize(){
		currentView = new TitleScreenView(this,mainController);
		currentView.initialize();
		soundPop = new Media(new File("resources/sounds/Blop.mp3").toURI().toString());
		soundcsgo = new Media(new File("resources/sounds/csgoTitleScreen.mp3").toURI().toString());
		
	}
	
	public void draw() {
		
		
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
			currentView.getMultiplayerText().setVisible(true);
			currentView.getStartServerText().setVisible(true);
			
		}
		
		if(timeElapsed > 138){
			currentView.getFindServerText().setVisible(true);
			
		}
		
		if(timeElapsed > 141){
			currentView.getCreditsText().setVisible(true);
		}
		
		if(timeElapsed == 140){
			musicPlayer = new MediaPlayer(soundcsgo);
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
		//currentView.setView();
		//currentView.getMultiplayerText().setText("dziala?");
	}

	public void playPressed() {
		System.out.println("wcisnieto");
	}
	
	public void openServer(){
		mainController.getServerController().initialize();
	}

	public void close(){
		musicPlayer.stop();
	}
	
}
