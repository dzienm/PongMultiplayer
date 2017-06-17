package pongClient.view;


import java.util.Arrays;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Klasa okienka informacji o grze
 * @author cprzyborowski
 *
 */

public class Credits extends Application {
	private static final double CHECK_PANE_WIDTH = 0;
	public String opis = " Autorzy , , cpreze cprzyborowski,  dzienm  mdziendzikowski, ,-----------------------------------,, Opis gry, , Pong  -  dwuwymiarowa, gra sportowa symuluje,  tenis  sto³owy. Gracz, porusza  sie  paletk¹, umieszczon¹ na skraju, ekranu. Mo¿na  graæ z,   przeciwnikiem poprzez, zasoby sieci lokalnej., Celem  jest  zdobycie,   dziesieciu   punktów.,-----------------------------------,";
	private Image gameIcon= new Image("icons/Game.png");
	
	 private String	background = "textures/titleScreen/main.png";
 public Credits() {
		//start();
	}
	public void start() {
		Stage primaryStage =new Stage();
		List<String> listTmp = Arrays.asList(opis.split(","));
		VBox vbox = new VBox();

		for (int i = 0; i < listTmp.size() * 12; i++) {
			Text text = new Text();
			text.setFont(Font.font("Courier", FontWeight.BOLD, 15));
			text.setWrappingWidth(CHECK_PANE_WIDTH / 2.8);
			text.setFill(Color.GREENYELLOW);
			text.setText(center(listTmp.get(i % listTmp.size()), 40));
			vbox.getChildren().add(text);

		}
	
		
		vbox.setStyle("-fx-background-image: url("+background+");-fx-background-repeat:no-repeat;-fx-background-size:cover ;-fx-background-position:left,top");

		ScrollPane sp = new ScrollPane(vbox);
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setVbarPolicy(ScrollBarPolicy.NEVER);

		Scene scene = new Scene(sp, 230, 10 * 12);
	
		primaryStage.setScene(scene);
		primaryStage.setTitle("PongMulti - Credits");
		primaryStage.getIcons().add(gameIcon);
		primaryStage.setAlwaysOnTop(true);
		primaryStage.show();
		double textHeight = vbox.getHeight() / vbox.getChildren().size();
		primaryStage.setHeight(textHeight * 12 + primaryStage.getHeight() - scene.getHeight());
		 primaryStage.setResizable(false);
		Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyValue kv = new KeyValue(sp.vvalueProperty(), sp.getVmax());
		KeyFrame kf = new KeyFrame(Duration.minutes(3), kv);
		timeline.getKeyFrames().addAll(kf);
		timeline.play();
	}

	public static String center(String text, int len) {
		String out = String.format("%" + len + "s%s%" + len + "s", "", text, "");
		float mid = (out.length() / 2);
		float start = mid - (len / 2);
		float end = start + len;
		return out.substring((int) start, (int) end);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		
	}
}