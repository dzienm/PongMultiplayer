package pongServer.view;

import java.util.Random;

import gameUtilities.GameUtilitiesVariables;
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
import pongClient.model.PongBall;
import pongClient.model.PongRacket;
import pongServer.ServerGameController;

public class ServerGameView {

	private Image background;
	private ServerGameController gameController;
	
	private final int boardWidth;
	public int getBoardWidth() {
		return boardWidth;
	}

	private final int boardHeight;
	public int getBoardHeight() {
		return boardHeight;
	}

	private final int initialBallSpeed;
	public int getInitialBallSpeed() {
		return initialBallSpeed;
	}

	private final int racketSpeed;
	public int getRacketSpeed() {
		return racketSpeed;
	}

	private Stage stage;
	private Pane root;
	private Scene scene;
	private Canvas canvas;
	private GraphicsContext graphicsContext;
	
	private Text clientScoreText;
	public Text getClientScoreText() {
		return clientScoreText;
	}
	
	private Text serverScoreText;
	public Text getServerScoreText() {
		return serverScoreText;
	}

	private PongRacket clientRacket;
	public PongRacket getClientRacket() {
		return clientRacket;
	}

	private PongRacket serverRacket;
	public PongRacket getServerRacket() {
		return serverRacket;
	}

	private PongRacket upperBound;
	public PongRacket getUpperBound() {
		return upperBound;
	}

	private PongRacket lowerBound;
	public PongRacket getLowerBound() {
		return lowerBound;
	}

	private PongBall ball;
	public PongBall getPongBall(){
		return ball;
	}
	
	public ServerGameView(ServerGameController serverGameController){
		gameController = serverGameController;
		boardWidth = GameUtilitiesVariables.gameBoardWidth;
		boardHeight = GameUtilitiesVariables.gameBoardHeight;
		initialBallSpeed = GameUtilitiesVariables.initialBallSpeed;
		racketSpeed = GameUtilitiesVariables.racketSpeed;
	}
	
	public void initialize(){
		
		loadContent();
		
        MotionBlur mb = new MotionBlur();       
        mb.setRadius(5.0f);       
        mb.setAngle(90.0f);
        mb.setRadius(2.0f);
        
        clientScoreText = new Text();              
        clientScoreText.setText("" + 0);       
        clientScoreText.setFill(Color.SKYBLUE);       
        clientScoreText.setFont(Font.font("null", FontWeight.BOLD, 38));
        clientScoreText.setEffect(mb);
        clientScoreText.setTranslateX(boardWidth/2 - 75);       
        clientScoreText.setTranslateY(70);
       
        serverScoreText = new Text();              
        serverScoreText.setText("" + 0);       
        serverScoreText.setFill(Color.LIGHTCORAL);       
        serverScoreText.setFont(Font.font("null", FontWeight.BOLD, 38));
        serverScoreText.setEffect(mb);
        serverScoreText.setTranslateX(boardWidth/2 + 60);       
        serverScoreText.setTranslateY(70);
        
        clientRacket = new PongRacket();
        clientRacket.setRacketFill(Color.SKYBLUE);
        clientRacket.setHeight(GameUtilitiesVariables.racketHeight);
        clientRacket.setWidth(GameUtilitiesVariables.racketWidth);
        clientRacket.setPosition(GameUtilitiesVariables.initialRacketBoundaryOffset, boardHeight/2 - GameUtilitiesVariables.racketHeight);
        
        serverRacket = new PongRacket();
        serverRacket.setRacketFill(Color.LIGHTCORAL);
        serverRacket.setHeight(GameUtilitiesVariables.racketHeight);
        serverRacket.setWidth(GameUtilitiesVariables.racketWidth);
        serverRacket.setPosition(boardWidth - GameUtilitiesVariables.initialRacketBoundaryOffset, boardHeight/2 - GameUtilitiesVariables.racketHeight/2);
        
        lowerBound = new PongRacket();
        lowerBound.setRacketFill(Color.LAWNGREEN);
        lowerBound.setHeight(GameUtilitiesVariables.boundaryHeight);
        lowerBound.setWidth(boardWidth);
        lowerBound.setPosition(0, boardHeight - GameUtilitiesVariables.lowerBoundOffset);
        lowerBound.setHorizontalBoundary(true);
        
        upperBound = new PongRacket();
        upperBound.setRacketFill(Color.LAWNGREEN);
        upperBound.setHeight(GameUtilitiesVariables.boundaryHeight);
        upperBound.setWidth(boardWidth);
        upperBound.setPosition(0, GameUtilitiesVariables.upperBoundOffset);
        upperBound.setHorizontalBoundary(true);
        
		ball = new PongBall();
		ball.setBallFill(Color.LAWNGREEN);
		ball.setRadius(GameUtilitiesVariables.ballRadius);
		ball.setPosition(boardWidth/2 + GameUtilitiesVariables.ballRadius/2, boardHeight/2 + GameUtilitiesVariables.ballRadius/2);
		
		Random randomizer = new Random();
		
		int alpha;
		while (true) {
			alpha = randomizer.nextInt(360);
			if ((alpha > 60 && alpha < 120) || (alpha > 240 && alpha < 300)) {
				continue;
			}
			else{
				break;
			}
		}
		
		double alphaRad = 2 * Math.PI * alpha/360.0;
		ball.setVelocity(new Integer((int) (initialBallSpeed*Math.cos(alphaRad))),new Integer((int) (initialBallSpeed*Math.sin(alphaRad))));
		
		
		/*Random randomizer = new Random();

		int alpha = randomizer.nextInt(11);
		alpha = alpha - 20;
		if (alpha < 0) {
			ball.setVelocity(-1 * initialBallSpeed, 0);
		} else {
			ball.setVelocity(initialBallSpeed, 0);
		}*/
		
		canvas = new Canvas(boardWidth + 15,boardHeight + 15);	
		
		root = new Pane();

		graphicsContext = canvas.getGraphicsContext2D();
		graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		graphicsContext.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());
		
		root.getChildren().add(canvas);
		root.getChildren().add(clientRacket.getRacket());
		root.getChildren().add(serverRacket.getRacket());
		root.getChildren().add(lowerBound.getRacket());
		root.getChildren().add(upperBound.getRacket());
		root.getChildren().add(clientScoreText);
		root.getChildren().add(serverScoreText);
		root.getChildren().add(ball.getBall());
		
		scene = new Scene(root,boardWidth,boardHeight);
		scene.setOnKeyPressed(keyEvent -> gameController.getUserInputQueue().addKey(keyEvent));
		
		stage = gameController.getStage();
		stage.setScene(scene);
		stage.setResizable(false);
		
		
	}
	
	private void loadContent(){
		this.background = new Image("textures/background.png");
	}
	
	
	
}
