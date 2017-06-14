package pongClient.view;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pongClient.controller.TitleScreenController;
import pongClient.model.PongBall;
import pongClient.model.PongRacket;

public class TitleScreenView {

	private Image background;
	private TitleScreenController titleController;
	
	private Stage stage;
	private Pane root;
	private Scene scene;
	private Canvas canvas;
	private GraphicsContext graphicsContext;
	
	private Text findServerText;
	private Text startServerText;
	private Text creditsText;
	
	
	public Text getFindServerText() {
		return findServerText;
	}

	public Text getStartServerText() {
		return startServerText;
	}

	public Text getCreditsText() {
		return creditsText;
	}
	

	private PongRacket racket_1;
	private PongRacket racket_2;
	private PongBall ball_1;
	
	public PongRacket getRacket_1() {
		return racket_1;
	}

	public PongRacket getRacket_2() {
		return racket_2;
	}

	public PongBall getBall_1(){
		return ball_1;
	}
	
	public TitleScreenView(TitleScreenController _titleController){
		
		this.titleController = _titleController;
		
	}
	
	public void initialize(){
		
		loadContent();
		
        
        MotionBlur mb = new MotionBlur();       
        mb.setRadius(5.0f);       
        mb.setAngle(90.0f);
 
        mb.setRadius(2.0f);
        
        startServerText = new Text();              
        startServerText.setText("Start server");       
        startServerText.setFill(Color.LAWNGREEN);       
        startServerText.setFont(Font.font("null", FontWeight.BOLD, 30));
        startServerText.setEffect(mb);
        startServerText.setTranslateX(370);       
        startServerText.setTranslateY(375);
        startServerText.setVisible(false);
        startServerText.setOnMousePressed(e -> {
			titleController.openServer();});
        
        findServerText = new Text();              
        findServerText.setText("Start client");       
        findServerText.setFill(Color.LAWNGREEN);       
        findServerText.setFont(Font.font("null", FontWeight.BOLD, 30));
        findServerText.setEffect(mb);
        findServerText.setTranslateX(370);       
        findServerText.setTranslateY(405);
        findServerText.setVisible(false);
        findServerText.setOnMousePressed(e -> {
			titleController.openClient();});
        
        creditsText = new Text();              
        creditsText.setText("Credits");       
        creditsText.setFill(Color.LAWNGREEN);       
        creditsText.setFont(Font.font("null", FontWeight.BOLD, 30));
        creditsText.setEffect(mb);
        creditsText.setTranslateX(370);       
        creditsText.setTranslateY(435);
        creditsText.setVisible(false);
        creditsText.setOnMousePressed(e -> {
			titleController.playPressed();});
        
        racket_1 = new PongRacket();
		racket_1.setRacketFill(Color.SKYBLUE);
		racket_1.setHeight(100);
		racket_1.setWidth(22);
		racket_1.setPosition(180, 200);
		racket_1.getRacket().setVisible(false);
		
        racket_2 = new PongRacket();
		racket_2.setRacketFill(Color.LIGHTCORAL);
		racket_2.setHeight(100);
		racket_2.setWidth(22);
		racket_2.setPosition(900 - 180, 200);
		racket_2.getRacket().setVisible(false);
		
		ball_1 = new PongBall();
		ball_1.setBallFill(Color.LAWNGREEN);
		ball_1.setRadius(10);
		ball_1.setPosition(180 + (900 - 360)/2, 250);
		ball_1.setVelocity(-2, 0);
		ball_1.getBall().setVisible(false);
		
		canvas = new Canvas(915,515);	
		
		root = new Pane();

		graphicsContext = canvas.getGraphicsContext2D();
		graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		graphicsContext.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());
		
		root.getChildren().add(canvas);
		root.getChildren().add(startServerText);
		root.getChildren().add(findServerText);
		root.getChildren().add(creditsText);
		
		root.getChildren().add(racket_1.getRacket());
		root.getChildren().add(racket_2.getRacket());
		root.getChildren().add(ball_1.getBall());
		
		scene = new Scene(root,900,500);
		scene.setOnKeyPressed(keyEvent -> titleController.getUserInputQueue().addKey(keyEvent));
		
		stage = titleController.getStage();
		stage.setScene(scene);
		stage.setResizable(false);
		
		
	}
	
	private void loadContent(){
		this.background = new Image("textures/titleScreen/main.png");
	}
	
	
	
}
