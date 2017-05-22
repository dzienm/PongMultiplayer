package TestWindowActions;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pongClient.model.PongBall;



public class TestWindowRefresh extends Application {
	
	private PongBall ball;
	private Pane root;
	private Scene scene;
	private Stage primaryStage;
	private GameAnimationTimer gameAnimator;
	private Canvas canvas;
	private GraphicsContext graphicsContext;
	private Image background;
	
	@Override
	public void start(Stage stage) {
		//primaryStage = stage;
		initialize();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void initialize(){
		primaryStage = new Stage();
		
		ball = new PongBall();
		ball.setPosition(100, 100);
		ball.setRadius(15);
		ball.getBall().setFill(Color.GREENYELLOW);
		ball.setVelocity(1, 1);
		
		background = new Image("textures/titleScreen/main.png");
		
		root = new Pane();
		
		
		canvas = new Canvas(900, 500);
		graphicsContext = canvas.getGraphicsContext2D();
		graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		graphicsContext.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());
		
		//istotna jest kolejnosc
		root.getChildren().add(canvas);		
		root.getChildren().add(ball.getBall());
		
		//tak nie zadziala
		//root.getChildren().add(ball.getBall());
		//root.getChildren().add(canvas);		
		
		
		scene = new Scene(root,900,500);
		
		gameAnimator = new GameAnimationTimer();
		gameAnimator.start();
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Stage stage = new Stage();
		stage.show();
	}
	
	
	private class GameAnimationTimer extends AnimationTimer {
		
		@Override
		public void handle(long currentNanoTime) {
			
			//ball.setPosition(100+5*licznik, 100);
			ball.updatePosition();
		}
		
	}
}
