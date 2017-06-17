package pongServer.view;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import pongServer.ServerController;

public class ServerScreen {

	private Image background;
	private ServerController serverController;
	
	private Stage stage;
	private Pane root;
	private Scene scene;
	private Canvas canvas;
	private GraphicsContext graphicsContext;
	private TextField textField;
	private Button startServerButton;
	private Button mainMenuButton;

	//private Button stopServerButton;
	private Button startGameButton;
	private Button restartGameButton;


	private Button stopGameButton;
	private Text serverStateText;
	private Text serverStartedText;
	private Text waitingConnectionText;
	private Text clientConnectedText;
	


	public ServerScreen(ServerController _controller){
		
		serverController = _controller;
		stage = serverController.getServerStage();
		
	}
	
	public void initialize(){
		
		loadContent();
		
		MotionBlur mb = new MotionBlur();       
		mb.setRadius(2.0f);    
        mb.setAngle(90.0f);
		
		Text serverPortText = new Text();
		serverPortText.setX(0.0f);        
        serverPortText.setY(0.0f);        
        serverPortText.setText("Specify the server port (default 8989):");       
        serverPortText.setFill(Color.LAWNGREEN);       
        serverPortText.setFont(Font.font("null", FontWeight.BOLD, 30));
		serverPortText.setEffect(mb);
        serverPortText.setTranslateX(220);       
        serverPortText.setTranslateY(300);
		
        serverStateText = new Text();
        serverStateText.setX(0.0f);        
        serverStateText.setY(0.0f);        
        serverStateText.setText("Server not active.");       
        serverStateText.setFill(Color.RED);       
        serverStateText.setFont(Font.font("null", FontWeight.BOLD, 24));
        serverStateText.setEffect(mb);
        serverStateText.setTranslateX(220);       
        serverStateText.setTranslateY(420);
        
        textField = new TextField();
        textField.setTranslateX(220);
        textField.setTranslateY(320);
        textField.setOpacity(0.5);
        textField.setFont(Font.font("null", FontWeight.BOLD, 24));
        textField.setPrefWidth(250);
        textField.setStyle("-fx-text-inner-color: lawngreen;");
        
        serverStartedText = new Text();
        serverStartedText.setX(0.0f);        
        serverStartedText.setY(0.0f);        
        serverStartedText.setFill(Color.LAWNGREEN);       
        serverStartedText.setFont(Font.font("null", FontWeight.BOLD, 30));
        serverStartedText.setEffect(mb);
        serverStartedText.setTranslateX(220);       
        serverStartedText.setTranslateY(420);
        serverStartedText.setVisible(false);
        
        waitingConnectionText = new Text();
        waitingConnectionText.setText("Waiting for the client to connect."); 
        waitingConnectionText.setX(0.0f);        
        waitingConnectionText.setY(0.0f);        
        waitingConnectionText.setFill(Color.RED);       
        waitingConnectionText.setFont(Font.font("null", FontWeight.BOLD, 24));
        waitingConnectionText.setEffect(mb);
        waitingConnectionText.setTranslateX(220);       
        waitingConnectionText.setTranslateY(460);
        waitingConnectionText.setVisible(false);
        
        clientConnectedText = new Text();
        clientConnectedText.setText("Client connected."); 
        clientConnectedText.setX(0.0f);        
        clientConnectedText.setY(0.0f);        
        clientConnectedText.setFill(Color.LAWNGREEN);       
        clientConnectedText.setFont(Font.font("null", FontWeight.BOLD, 24));
        clientConnectedText.setEffect(mb);
        clientConnectedText.setTranslateX(220);       
        clientConnectedText.setTranslateY(460);
        clientConnectedText.setVisible(false);
        
        
        startServerButton = new Button();
        startServerButton.setTranslateX(500);
        startServerButton.setTranslateY(316);
        startServerButton.setPrefWidth(250);
        startServerButton.setFont(Font.font("null", FontWeight.BOLD, 24));
        startServerButton.setText("Start server");
        startServerButton.setId("startServerButton");
        startServerButton.setOnMousePressed(e -> {
			serverController.startServerButtonPressed();});
        
        mainMenuButton = new Button();
        mainMenuButton.setTranslateX(770);
        mainMenuButton.setTranslateY(316);
        mainMenuButton.setPrefWidth(250);
        mainMenuButton.setFont(Font.font("null", FontWeight.BOLD, 24));
        mainMenuButton.setText("Main menu");
        mainMenuButton.setOnMousePressed(e -> {
			serverController.mainMenuButtonPressed();});
        
       /* stopServerButton = new Button();
        stopServerButton.setTranslateX(500);
        stopServerButton.setTranslateY(316);
        stopServerButton.setPrefWidth(250);
        stopServerButton.setFont(Font.font("null", FontWeight.BOLD, 24));
        stopServerButton.setText("Stop server");
        stopServerButton.setOnMousePressed(e -> {
			serverController.stopServerButtonPressed();});
        stopServerButton.setId("stopServerButton");
        stopServerButton.setVisible(false);*/
        
        startGameButton = new Button();
        startGameButton.setTranslateX(220);
        startGameButton.setTranslateY(480);
        startGameButton.setPrefWidth(250);
        startGameButton.setFont(Font.font("null", FontWeight.BOLD, 24));
        startGameButton.setText("Start game");
        startServerButton.setId("startServerButton");
        startGameButton.setOnMousePressed(e -> {
			serverController.startGameButtonPressed();});
        startGameButton.setVisible(false);
        
        stopGameButton = new Button();
        stopGameButton.setTranslateX(220);
        stopGameButton.setTranslateY(480);
        stopGameButton.setPrefWidth(250);
        stopGameButton.setFont(Font.font("null", FontWeight.BOLD, 24));
        stopGameButton.setText("Pause game");
        stopGameButton.setId("stopServerButton");
        stopGameButton.setOnMousePressed(e -> {
			serverController.stopGameButtonPressed();});
        stopGameButton.setVisible(false);
        
        restartGameButton = new Button();
        restartGameButton.setTranslateX(220);
        restartGameButton.setTranslateY(480);
        restartGameButton.setPrefWidth(250);
        restartGameButton.setFont(Font.font("null", FontWeight.BOLD, 24));
        restartGameButton.setText("Restart game");
        restartGameButton.setId("startServerButton");
        restartGameButton.setOnMousePressed(e -> {
			serverController.restartGameButtonPressed();});
        restartGameButton.setVisible(false);
        
		canvas = new Canvas(1115,615);	
		
		root = new Pane();

		graphicsContext = canvas.getGraphicsContext2D();
		graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		graphicsContext.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());
		
		root.getChildren().add(canvas);
		root.getChildren().add(textField);
		root.getChildren().add(serverPortText);
		root.getChildren().add(serverStateText);
		root.getChildren().add(serverStartedText);
		root.getChildren().add(clientConnectedText);
		root.getChildren().add(waitingConnectionText);
		root.getChildren().add(startServerButton);
		root.getChildren().add(mainMenuButton);
		root.getChildren().add(restartGameButton);
		//root.getChildren().add(stopServerButton);
		root.getChildren().add(startGameButton);
		root.getChildren().add(stopGameButton);
				
		scene = new Scene(root,1100,600);
		scene.setOnKeyPressed(keyEvent -> {
			if(keyEvent.getCode() == KeyCode.ESCAPE){
				stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
			}		
		});
		scene.getStylesheets().add("styles/serverStyle.css");
		
		stage.setScene(scene);
		stage.setResizable(false);
		
		
	}
	
	public Text getServerStateText() {
		return serverStateText;
	}

	public Text getClientConnectedText() {
		return clientConnectedText;
	}

	public Text getWaitingConnectionText() {
		return waitingConnectionText;
	}

	public Text getServerStartedText() {
		return serverStartedText;
	}

	public TextField getTextField() {
		return textField;
	}
	
	/*public Button getStopServerButton() {
		return stopServerButton;
	}*/

	public Button getStartServerButton() {
		return startServerButton;
	}

	public Button getStartGameButton() {
		return startGameButton;
	}
	
	public Button getRestartGameButton() {
		return restartGameButton;
	}
	
	public Button getStopGameButton() {
		return stopGameButton;
	}
	
	public Button getMainMenuButton() {
		return mainMenuButton;
	}
	
	private void loadContent(){
		this.background = new Image("textures/titleScreen/main.png");
	}


	
}
