package pongClient.view;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pongClient.controller.MainController;
import pongClient.controller.TitleScreenController;
import pongClient.model.PongBall;
import pongClient.model.PongRacket;

public class TitleScreenView {

	private Image background;
	private MainController mainController;
	private TitleScreenController titleController;
	
	private Stage stage;
	private Pane root;
	private Scene scene;
	private Canvas canvas;
	private GraphicsContext graphicsContext;
	
	private Text multiplayerText;
	private Text findServerText;
	private Text startServerText;
	private Text creditsText;
	
	public Text getMultiplayerText() {
		return multiplayerText;
	}

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
	
	public TitleScreenView(TitleScreenController _titleController, MainController _primaryController){
		
		this.mainController = _primaryController;
		this.titleController = _titleController;
		
	}
	
	public void initialize(){
		
		loadContent();
		multiplayerText = new Text();        
        multiplayerText.setX(0.0f);        
        multiplayerText.setY(0.0f);        
        multiplayerText.setText("multiplayer");       
        multiplayerText.setFill(Color.LAWNGREEN);       
        multiplayerText.setFont(Font.font("null", FontWeight.BOLD, 40));
        
        MotionBlur mb = new MotionBlur();       
        mb.setRadius(5.0f);       
        mb.setAngle(90.0f);
 
        multiplayerText.setEffect(mb);
        multiplayerText.setTranslateX(315);       
        multiplayerText.setTranslateY(155);
        multiplayerText.setVisible(false);
        
        mb.setRadius(2.0f);
        
        startServerText = new Text();              
        startServerText.setText("Start server");       
        startServerText.setFill(Color.LAWNGREEN);       
        startServerText.setFont(Font.font("null", FontWeight.BOLD, 30));
        startServerText.setEffect(mb);
        startServerText.setTranslateX(370);       
        startServerText.setTranslateY(375);
        startServerText.setVisible(false);
        
        findServerText = new Text();              
        findServerText.setText("Find servers");       
        findServerText.setFill(Color.LAWNGREEN);       
        findServerText.setFont(Font.font("null", FontWeight.BOLD, 30));
        findServerText.setEffect(mb);
        findServerText.setTranslateX(370);       
        findServerText.setTranslateY(405);
        findServerText.setVisible(false);
        
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
		
		canvas = new Canvas(915,515);	//nie wiem czemu ale jest jakis dziwny ofset w scenie
		
		root = new Pane();

		graphicsContext = canvas.getGraphicsContext2D();
		graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		graphicsContext.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());
		
		root.getChildren().add(canvas);
		root.getChildren().add(multiplayerText);
		root.getChildren().add(startServerText);
		root.getChildren().add(findServerText);
		root.getChildren().add(creditsText);
		
		root.getChildren().add(racket_1.getRacket());
		root.getChildren().add(racket_2.getRacket());
		root.getChildren().add(ball_1.getBall());
		
		scene = new Scene(root,900,500);
		scene.setOnKeyPressed(keyEvent -> mainController.getUserInputQueue().addKey(keyEvent));
		
		stage = mainController.getPrimaryStage();
		stage.setScene(scene);
		stage.setResizable(false);
		
		
	}
	
	private void loadContent(){
		this.background = new Image("textures/titleScreen/main.png");
	}
	
	public void setView(){

		
		
		Rectangle rect = new Rectangle();
		rect.setHeight(100);
		rect.setWidth(100);
		rect.setFill(Color.WHITESMOKE);	
		
		rect.setVisible(true);
		
        rect.setTranslateX(40);
	
		//on mouseclicked event nie zadziala bo to chodzi w petli gamneanimation timer czyli trzeba byloby szybciej niz 60Hz klikac zeby eventqueue nie zostal wyczysczony
		rect.setOnMouseClicked(e -> {
			//rect.setTranslateX(60);
			titleController.playPressed();});
		
		rect.setOnMousePressed(e -> {
			//rect.setTranslateX(60);
			titleController.playPressed();});
		
		//root.getChildren().add(rect);
		
		//Group root = new Group();
		
	}
	
	
	
}
