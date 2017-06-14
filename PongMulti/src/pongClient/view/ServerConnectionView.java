package pongClient.view;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
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
	
	public ServerConnectionView(ServerConnectionController _controller){
		viewController = _controller;
		viewStage = viewController.getStage();
		
	}
	
	public void initialize(){
	
		loadContent();
	
		
		canvas = new Canvas(1115,615);	
		
		root = new Pane();

		graphicsContext = canvas.getGraphicsContext2D();
		graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		graphicsContext.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());
		
		root.getChildren().add(canvas);
		
		
				
		scene = new Scene(root,1100,600);
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
