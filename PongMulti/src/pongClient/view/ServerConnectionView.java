package pongClient.view;

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
import pongClient.controller.ServerConnectionController;


public class ServerConnectionView {

	private ServerConnectionController viewController;
	private Stage viewStage; 
	
	private Image background;
	private Pane root;
	private Scene scene;
	private Canvas canvas;
	private GraphicsContext graphicsContext;
	
	private Button backButton;
	private TextField textFieldIp;
	private TextField textFieldPort;
	private Button connectButton;
	
	public ServerConnectionView(ServerConnectionController _controller){
		viewController = _controller;
		viewStage = viewController.getStage();
		
	}
	
	public void initialize(){
	
		loadContent();
	
		MotionBlur mb = new MotionBlur();       
		mb.setRadius(2.0f);    
        mb.setAngle(90.0f);
		
		Text serverIdText = new Text();
		serverIdText.setX(0.0f);        
        serverIdText.setY(0.0f);        
        serverIdText.setText("Specify the server:");       
        serverIdText.setFill(Color.LAWNGREEN);       
        serverIdText.setFont(Font.font("null", FontWeight.BOLD, 30));
		serverIdText.setEffect(mb);
        serverIdText.setTranslateX(220);       
        serverIdText.setTranslateY(240);
        
        Text specifyIpText = new Text();
		specifyIpText.setX(0.0f);        
        specifyIpText.setY(0.0f);        
        specifyIpText.setText("Enter the server ip:");       
        specifyIpText.setFill(Color.LAWNGREEN);       
        specifyIpText.setFont(Font.font("null", FontWeight.BOLD, 24));
		specifyIpText.setEffect(mb);
        specifyIpText.setTranslateX(220);       
        specifyIpText.setTranslateY(300);
		
		textFieldIp = new TextField();
        textFieldIp.setTranslateX(500);
        textFieldIp.setTranslateY(265);
        textFieldIp.setOpacity(0.5);
        textFieldIp.setFont(Font.font("null", FontWeight.BOLD, 24));
        textFieldIp.setPrefWidth(250);
        textFieldIp.setStyle("-fx-text-inner-color: lawngreen;");
		
        Text specifyPortText = new Text();
		specifyPortText.setX(0.0f);        
        specifyPortText.setY(0.0f);        
        specifyPortText.setText("Enter the server port:");       
        specifyPortText.setFill(Color.LAWNGREEN);       
        specifyPortText.setFont(Font.font("null", FontWeight.BOLD, 24));
		specifyPortText.setEffect(mb);
        specifyPortText.setTranslateX(220);       
        specifyPortText.setTranslateY(360);
        
        textFieldPort = new TextField();
        textFieldPort.setTranslateX(500);
        textFieldPort.setTranslateY(325);
        textFieldPort.setOpacity(0.5);
        textFieldPort.setFont(Font.font("null", FontWeight.BOLD, 24));
        textFieldPort.setPrefWidth(250);
        textFieldPort.setStyle("-fx-text-inner-color: lawngreen;");
        
		backButton = new Button();
        backButton.setTranslateX(220);
        backButton.setTranslateY(420);
        backButton.setPrefWidth(250);
        backButton.setFont(Font.font("null", FontWeight.BOLD, 24));
        backButton.setText("Main menu");
        backButton.setOnMousePressed(e -> {
			viewController.backButtonPressed();});
        
        connectButton = new Button();
        connectButton.setTranslateX(500);
        connectButton.setTranslateY(420);
        connectButton.setPrefWidth(250);
        connectButton.setFont(Font.font("null", FontWeight.BOLD, 24));
        connectButton.setText("Connect");
        connectButton.setOnMousePressed(e -> {
			viewController.connectButtonPressed();});
		
        canvas = new Canvas(915,615);
		root = new Pane();

		graphicsContext = canvas.getGraphicsContext2D();
		graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		graphicsContext.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());
		
		root.getChildren().add(canvas);
		root.getChildren().add(serverIdText);
		root.getChildren().add(specifyIpText);
		root.getChildren().add(specifyPortText);
		root.getChildren().add(textFieldIp);
		root.getChildren().add(textFieldPort);
		root.getChildren().add(backButton);
		root.getChildren().add(connectButton);
				
		scene = new Scene(root,900,600);
		scene.setOnKeyPressed(keyEvent -> {
			if(keyEvent.getCode() == KeyCode.ESCAPE){
				viewStage.fireEvent(new WindowEvent(viewStage, WindowEvent.WINDOW_CLOSE_REQUEST));
			}		
		});
		scene.getStylesheets().add("styles/serverStyle.css");
		
		viewStage.setScene(scene);
		viewStage.setResizable(false);
	}
	
	private void loadContent(){
		this.background = new Image("textures/titleScreen/main.png");
	}
	
}
