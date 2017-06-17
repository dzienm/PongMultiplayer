package pongServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import gameUtilities.GameUtilitiesVariables;
import gameUtilities.UserInputQueue;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pongServer.view.ServerGameView;
import utilityWindows.AlertBox;

/**
 * Klasa kontrolera serwera komunkacji i logiki gry. Dzialanie serwera opiera
 * sie na wykorzystanie dwoch niezaleznych watkow. Jeden jest odpowiedzialny za
 * komunikacje z klientem i wymiane informacji, drugi za wyswietlanie na ekranie
 * gry zawartosci oraz aktualizacje stanu gry. Odswiezanie watku komunikacji
 * jest sterowane petla gry aplikacji klienta, logika gry jest sterowana petla
 * gry serwera.
 * 
 * @author mdziendzikowski
 *
 */
public class ServerGameController {

	private ServerController serverController;
	private ServerGameView gameView;

	public ServerGameView getGameView() {
		return gameView;
	}

	private GameAnimationTimer animationTimer;
	private Stage stage;
	private long timeElapsed;
	private double secondsElapsed;

	private ServerSocket serverPong;
	private Socket clientSocket;

	private double clientRacketPosX;

	/**
	 * Klasa zwracajaca polozenie X paletki klienta w sposob synchroniczny
	 * 
	 * @author mdziendzikowski
	 * @return polozenie X rakietki klienta
	 */
	public synchronized double getClientRacketPosX() {
		return clientRacketPosX;
	}

	private double clientRacketPosY;

	/**
	 * Klasa zwracajaca polozenie Y paletki klienta w sposob synchroniczny
	 * 
	 * @author mdziendzikowski
	 * @return polozenie Y rakietki klienta
	 */
	public synchronized double getClientRacketPosY() {
		return clientRacketPosY;
	}

	/**
	 * Klasa umozliwiajaca ustawienie polozenia paletki klienta w sposob
	 * synchroniczny. Zastosowanie metody synchronizacji wspolbieznej zapobiega
	 * zlemu wyswietlaniu paletki klienta.
	 * 
	 * @author mdziendzikowski
	 * @return polozenie X rakietki klienta
	 */
	public synchronized void setClientRacketPos(double clientRacketPosX, double clientRacketPosY) {
		this.clientRacketPosX = clientRacketPosX;
		this.clientRacketPosY = clientRacketPosY;
	}

	private ObjectOutputStream objectWriter;
	@SuppressWarnings("unused")
	private ObjectInputStream objectReader;
	private DataOutputStream dataWriter;
	private DataInputStream dataReader;

	private boolean scored;

	/**
	 * Synchroniczna metoda pobrania informacji o zdobyciu punktu przez ktoregos
	 * z graczy. Zastosowanie synchronizacji pozwala na zachowanie spojnosci
	 * danych pomiedzy aplikacja klienta (sterowana przez watek komunikacji)
	 * oraz watkiem logiki gry serwera.
	 * 
	 * @author mdziendzikowski
	 * @return zdobycie punktu w danym momencie gry.
	 */
	private synchronized boolean getScored() {
		return scored;
	}

	/**
	 * Synchroniczna metoda pozwalajaca na ustawienie informajci o zdobyciu
	 * punktu przez ktoregos z graczy.
	 * 
	 * @author mdziendzikowski
	 * @param _scored
	 */
	private synchronized void setScored(boolean _scored) {
		this.scored = _scored;
	}

	private Thread clientThread;

	private int racketSpeed;

	private UserInputQueue userInputQueue;

	public UserInputQueue getUserInputQueue() {
		return userInputQueue;
	}

	private Media soundPop;
	private MediaPlayer soundPlayer;

	private int clientScore;
	private int serverScore;

	public Stage getStage() {
		return stage;
	}

	/**
	 * Konstruktor kontrolera aplikacji serwera.
	 * 
	 * @author mdziendzikowski
	 * @param controller
	 */
	public ServerGameController(ServerController controller) {
		serverController = controller;
		animationTimer = new GameAnimationTimer();
		userInputQueue = new UserInputQueue();

	}

	/**
	 * Metoda inicjalizujaca kontroler serwera.
	 * 
	 * @author mdziendzikowski
	 */
	public void initialize() {

		soundPop = new Media(new File("resources/sounds/Blop.mp3").toURI().toString());

		clientScore = 0;
		serverScore = 0;
		scored = false;

		stage = new Stage();
		stage.setTitle("PongServerGameApp");
		stage.setOnCloseRequest(e -> stage_CloseRequest(e));
		stage.show();

		gameView = new ServerGameView(this);
		gameView.initialize();

		racketSpeed = gameView.getRacketSpeed();
		animationTimer.start();

	}

	/**
	 * Metoda resetujaca wynik gry.
	 * 
	 * @author mdziendzikowski
	 */
	public void resetScore() {
		clientScore = 0;
		serverScore = 0;

	}

	/**
	 * Metoda obslugujaca logike gry i widok aplikacji serwera.
	 * 
	 * @author mdziendzikowski
	 */
	public void draw() {

		switch (serverController.getServerState()) {

		case ConnectionEstablished:
			gameView.getGameStatusText().setText("Waiting for the game to start.");
			gameView.getGameStatusText().setTranslateX(GameUtilitiesVariables.gameBoardWidth / 2 - 230);
			gameView.getGameStatusText().setVisible(true);
			gameView.getGameInfoText().setVisible(true);
			gameView.getGameInfoText().setOpacity(Math.abs(Math.sin(secondsElapsed / 4 * 2 * Math.PI)));
			break;

		case GameStarted:
			gameView.getGameStatusText().setVisible(false);
			gameView.getGameInfoText().setVisible(false);
			keyboardController();
			gameView.getClientRacket().setPosition(getClientRacketPosX(), getClientRacketPosY());
			gameView.getPongBall().updatePosition();
			soundHandle();
			scoreUpdate();
			if (clientScore >= GameUtilitiesVariables.maxscore || serverScore >= GameUtilitiesVariables.maxscore) {
				serverController.setServerState(ServerStateEnum.GameOver);
			}
			break;

		case GamePaused:
			gameView.getGameStatusText().setText("Game paused.");
			gameView.getGameStatusText().setTranslateX(GameUtilitiesVariables.gameBoardWidth / 2 - 130);
			gameView.getGameStatusText().setVisible(true);
			gameView.getGameInfoText().setVisible(true);
			gameView.getGameInfoText().setOpacity(Math.abs(Math.sin(secondsElapsed / 4 * 2 * Math.PI)));
			break;

		case GameOver:
			gameView.getGameStatusText().setText("Game over.");
			gameView.getGameStatusText().setTranslateX(GameUtilitiesVariables.gameBoardWidth / 2 - 100);
			gameView.getGameStatusText().setVisible(true);
			gameView.getGameOverText().setVisible(true);
			gameView.getGameOverText().setOpacity(Math.abs(Math.sin(secondsElapsed / 4 * 2 * Math.PI)));
			break;

		default:
			break;
		}

		timeElapsed++;
		secondsElapsed = timeElapsed / 60.0;
	}

	/**
	 * Metoda weryfikujaca zdobycie punktu przez ktoregos z graczy
	 * 
	 * @author mdziendzikowski
	 */
	private void scoreUpdate() {
		if (gameView.getPongBall().getBall().getCenterX() < 0) {
			serverScore++;
			gameView.initialize();

			setClientRacketPos(GameUtilitiesVariables.initialRacketBoundaryOffset,
					GameUtilitiesVariables.gameBoardHeight / 2 - GameUtilitiesVariables.racketHeight / 2);
			setScored(true);
		}

		else if (gameView.getPongBall().getBall().getCenterX() > gameView.getBoardWidth()) {
			clientScore++;
			gameView.initialize();
			setClientRacketPos(GameUtilitiesVariables.initialRacketBoundaryOffset,
					GameUtilitiesVariables.gameBoardHeight / 2 - GameUtilitiesVariables.racketHeight / 2);
			setScored(true);
		}

		gameView.getClientScoreText().setText("" + clientScore);
		gameView.getServerScoreText().setText("" + serverScore);

	}

	/**
	 * Metoda obslugujaca dzwieki gry.
	 * 
	 * @author mdziendzikowski
	 */
	private void soundHandle() {

		if (gameView.getClientRacket().intersectBall(gameView.getPongBall())) {
			soundPlayer = new MediaPlayer(soundPop);
			soundPlayer.play();
		}

		if (gameView.getServerRacket().intersectBall(gameView.getPongBall())) {
			soundPlayer = new MediaPlayer(soundPop);
			soundPlayer.play();
		}

		if (gameView.getUpperBound().intersectBall(gameView.getPongBall())) {
			soundPlayer = new MediaPlayer(soundPop);
			soundPlayer.play();
		}

		if (gameView.getLowerBound().intersectBall(gameView.getPongBall())) {
			soundPlayer = new MediaPlayer(soundPop);
			soundPlayer.play();
		}

	}

	/**
	 * Metoda obslugujaca zdarzenia klawiatury.
	 * 
	 * @author mdziendzikowski
	 */
	private void keyboardController() {

		KeyCode keyCode = userInputQueue.getKeyCode();

		if (keyCode == KeyCode.ESCAPE) {
			stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		}

		if (keyCode == KeyCode.DOWN) {
			if (gameView.getServerRacket().getRacket().getTranslateY() < gameView.getBoardHeight()
					- gameView.getServerRacket().getRacket().getHeight()) {
				double racketY = gameView.getServerRacket().getRacket().getTranslateY();
				racketY += racketSpeed;
				gameView.getServerRacket().getRacket().setTranslateY(racketY);
			}
		}

		if (keyCode == KeyCode.UP) {
			if (gameView.getServerRacket().getRacket().getTranslateY() > 0) {
				double racketY = gameView.getServerRacket().getRacket().getTranslateY();
				racketY -= racketSpeed;
				gameView.getServerRacket().getRacket().setTranslateY(racketY);
			}
		}

		if (keyCode == KeyCode.LEFT) {
			if (gameView.getServerRacket().getRacket().getTranslateX() > gameView.getBoardWidth() / 2 + 30) {
				double racketX = gameView.getServerRacket().getRacket().getTranslateX();
				racketX -= racketSpeed;
				gameView.getServerRacket().getRacket().setTranslateX(racketX);
			}
		}

		if (keyCode == KeyCode.RIGHT) {
			if (gameView.getServerRacket().getRacket().getTranslateX() < gameView.getBoardWidth()) {
				double racketX = gameView.getServerRacket().getRacket().getTranslateX();
				racketX += racketSpeed;
				gameView.getServerRacket().getRacket().setTranslateX(racketX);
			}
		}

	}

	/**
	 * Klasa watku obslugujacego petle gry.
	 * 
	 * @author mdziendzikowski
	 *
	 */
	private class GameAnimationTimer extends AnimationTimer {

		@Override
		public void handle(long currentNanoTime) {
			draw();
		}

	}

	/**
	 * Metoda obslugujaca zamkniecie okna gry.
	 * 
	 * @author mdziendzikowski
	 * @param windowEvent
	 *            wywolanie zamknniecia okna gry.
	 */
	public void stage_CloseRequest(WindowEvent windowEvent) {
		windowEvent.consume();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (AlertBox.showAndWait(AlertType.CONFIRMATION, "Pong", "Do you want to close the program?")
						.orElse(ButtonType.CANCEL) == ButtonType.OK) {
					stage.close();
					System.exit(0);
				}
			}
		});
	}

	/**
	 * Metoda obslugujaca watek komunikacji pomiedzy klientem i serwerem.
	 * Czestotliwosc odswiezania watku zalezy od aplikacji klienta i jej petli
	 * gry.
	 * 
	 * @author mdziendzikowski
	 */
	private void clientServerCommChannel() {
		Runnable clientListener = new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("Czekam na polaczenie");
					clientSocket = serverPong.accept();
					System.out.println("Polaczono.");
					objectWriter = new ObjectOutputStream(clientSocket.getOutputStream());
					objectReader = new ObjectInputStream(clientSocket.getInputStream());
					dataWriter = new DataOutputStream(clientSocket.getOutputStream());
					dataReader = new DataInputStream(clientSocket.getInputStream());
					serverController.setServerState(ServerStateEnum.ConnectionEstablished);

					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							initialize();
						}
					});

				} catch (IOException e) {
					try {
						serverPong.close();
					} catch (IOException e1) {

					}
					
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							AlertBox.showAndWait(AlertType.ERROR, "Pong", "Connection lost.");
							System.exit(0);
						}
					});

				}

				while (true) {

					try {

						switch (serverController.getServerState()) {

						case ConnectionEstablished:
							exchangeData();
							break;

						case GameStarted:
							exchangeData();
							break;

						case GamePaused:
							exchangeData();
							break;

						case GameOver:
							exchangeData();
							break;

						default:
							stage.close();
							serverController.reset();
							break;
						}

					} catch (IOException ex) {
						System.out.println("Connection lost.");
						try {
							serverPong.close();
						} catch (IOException e) {
						}
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								AlertBox.showAndWait(AlertType.ERROR, "Pong", "Connection lost. Press OK to close the application.");
								System.exit(0);
							}
						});
						animationTimer.stop();
						break;
						
					}

				}

			}

			/**
			 * Metoda komunikacji pomiedzy serwerem i klientem za pomoca
			 * strumieni
			 * 
			 * @author mdziendzikowski
			 * @throws IOException
			 */
			private void exchangeData() throws IOException {
				objectWriter.writeObject(serverController.getServerState());
				clientRacketPosX = dataReader.readDouble();
				clientRacketPosY = dataReader.readDouble();
				setClientRacketPos(clientRacketPosX, clientRacketPosY);
				double serverRacketX = gameView.getServerRacket().getPositionX();
				double serverRacketY = gameView.getServerRacket().getPositionY();
				double ballX = gameView.getPongBall().getPositionX();
				double ballY = gameView.getPongBall().getPositionY();
				dataWriter.writeDouble(serverRacketX);
				dataWriter.writeDouble(serverRacketY);
				dataWriter.writeDouble(ballX);
				dataWriter.writeDouble(ballY);
				dataWriter.writeDouble(clientRacketPosX);
				dataWriter.writeDouble(clientRacketPosY);
				dataWriter.writeInt(serverScore);
				dataWriter.writeInt(clientScore);
				dataWriter.writeBoolean(getScored());
				if (getScored()) {
					setScored(false);
				}
			}

		};

		clientThread = new Thread(clientListener);
		clientThread.start();

	}

	/**
	 * Metoda inicjalizujaca gniazdo serwera gry.
	 * 
	 * @author mdziendzikowski
	 * @param _port
	 *            port na ktorym nasluchuje serwer
	 * @return stan serwera
	 */
	public ServerStateEnum initializeConnection(int _port) {
		try {
			// konieczne dla ustawienia setReuseAddress
			System.setProperty("sun.net.useExclusiveBind", "false");

			serverPong = new ServerSocket();
			serverPong.setReuseAddress(true);
			serverPong.bind(new InetSocketAddress(_port));
			clientServerCommChannel();
			return ServerStateEnum.Initialized;

		} catch (IOException e) {
			// AlertBox.showAndWait(AlertType.ERROR, "Pong", "Can't start the
			// server.");
			// e.printStackTrace();
			return ServerStateEnum.NotConnected;
		}

	}

}
