package pongClient;
	
import javafx.application.Application;
import javafx.stage.Stage;
import pongClient.controller.TitleScreenController;


public class PongClientRun extends Application {
	@Override
	public void start(Stage stage) {
		TitleScreenController pongController = new TitleScreenController(stage);
		pongController.initialize(); //w ten sposob mozna sie pozbyc warningu o niewykorzystaniu pongController
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
