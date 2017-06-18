package pongClient.view;

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
import pongClient.controller.ClientGameController;
import pongClient.model.PongBall;
import pongClient.model.PongRacket;

/**
 * Klasa generuj�ca widok oraz obs�uguj�ca okienko g��wne gracza. Wy�wietla i
 * obs�uguje event'y klawiatury wy�wietla paletki oraz po�redniczy w
 * przekazywaniu informacji do innych klas programu.
 * 
 * 
 * @author cprzyborowski
 *
 */
public class ClientGameView {

	private Image background;
	private ClientGameController clientController;

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

	private Text gameStatusText;

	public Text getGameStatusText() {
		return gameStatusText;
	}

	private Text gameInfoText;

	public Text getGameInfoText() {
		return gameInfoText;
	}

	private Text gameOverText;

	public Text getGameOverText() {
		return gameOverText;
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

	public PongBall getPongBall() {
		return ball;
	}

	public ClientGameView(ClientGameController clientGameController) {
		this.clientController = clientGameController;
		boardWidth = GameUtilitiesVariables.gameBoardWidth;
		boardHeight = GameUtilitiesVariables.gameBoardHeight;
		initialBallSpeed = GameUtilitiesVariables.initialBallSpeed;
		racketSpeed = GameUtilitiesVariables.racketSpeed;
	}

	public void initialize() {

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
		clientScoreText.setTranslateX(boardWidth / 2 - 75);
		clientScoreText.setTranslateY(70);

		serverScoreText = new Text();
		serverScoreText.setText("" + 0);
		serverScoreText.setFill(Color.LIGHTCORAL);
		serverScoreText.setFont(Font.font("null", FontWeight.BOLD, 38));
		serverScoreText.setEffect(mb);
		serverScoreText.setTranslateX(boardWidth / 2 + 60);
		serverScoreText.setTranslateY(70);

		gameStatusText = new Text();
		gameStatusText.setText("");
		gameStatusText.setFill(Color.LAWNGREEN);
		gameStatusText.setFont(Font.font("null", FontWeight.BOLD, 38));
		gameStatusText.setEffect(mb);
		gameStatusText.setTranslateX(boardWidth / 2 - 230);
		gameStatusText.setTranslateY(500);
		gameStatusText.setVisible(false);

		gameInfoText = new Text();
		gameInfoText.setText("Press: W, S, A or D, to go: up, down, left or right respectively.");
		gameInfoText.setFill(Color.LIGHTCORAL);
		gameInfoText.setFont(Font.font("null", FontWeight.BOLD, 24));
		gameInfoText.setEffect(mb);
		gameInfoText.setTranslateX(boardWidth / 2 - 350);
		gameInfoText.setTranslateY(600);
		gameInfoText.setVisible(false);

		gameOverText = new Text();
		gameOverText.setText("Restart or close the game.");
		gameOverText.setFill(Color.LIGHTCORAL);
		gameOverText.setFont(Font.font("null", FontWeight.BOLD, 24));
		gameOverText.setEffect(mb);
		gameOverText.setTranslateX(boardWidth / 2 - 130);
		gameOverText.setTranslateY(600);
		gameOverText.setVisible(false);

		clientRacket = new PongRacket();
		clientRacket.setRacketFill(Color.SKYBLUE);
		clientRacket.setHeight(GameUtilitiesVariables.racketHeight);
		clientRacket.setWidth(GameUtilitiesVariables.racketWidth);
		clientRacket.setPosition(GameUtilitiesVariables.initialRacketBoundaryOffset,
				boardHeight / 2 - GameUtilitiesVariables.racketHeight / 2);

		serverRacket = new PongRacket();
		serverRacket.setRacketFill(Color.LIGHTCORAL);
		serverRacket.setHeight(GameUtilitiesVariables.racketHeight);
		serverRacket.setWidth(GameUtilitiesVariables.racketWidth);
		serverRacket.setPosition(boardWidth - GameUtilitiesVariables.initialRacketBoundaryOffset,
				boardHeight / 2 - GameUtilitiesVariables.racketHeight / 2);

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
		ball.setPosition(boardWidth / 2 + GameUtilitiesVariables.ballRadius / 2,
				boardHeight / 2 + GameUtilitiesVariables.ballRadius / 2);

		Random randomizer = new Random();

		int alpha;
		while (true) {
			alpha = randomizer.nextInt(360);
			if ((alpha > 60 && alpha < 120) || (alpha > 240 && alpha < 300)) {
				continue;
			} else {
				break;
			}
		}

		double alphaRad = 2 * Math.PI * alpha / 360.0;
		ball.setVelocity(new Integer((int) (initialBallSpeed * Math.cos(alphaRad))),
				new Integer((int) (initialBallSpeed * Math.sin(alphaRad))));

		canvas = new Canvas(boardWidth + 15, boardHeight + 15);

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
		root.getChildren().add(gameStatusText);
		root.getChildren().add(gameInfoText);
		root.getChildren().add(gameOverText);
		root.getChildren().add(ball.getBall());

		scene = new Scene(root, boardWidth, boardHeight);
		scene.setOnKeyPressed(keyEvent -> clientController.getUserInputQueue().addKey(keyEvent));

		stage = clientController.getStage();
		stage.setScene(scene);
		stage.setResizable(false);

	}

	private void loadContent() {
		this.background = new Image("textures/background.png");
	}

}
