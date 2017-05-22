package pongClient.model;

import javafx.scene.effect.Bloom;
import javafx.scene.effect.BoxBlur;
//import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
//import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class PongRacket {

	private Rectangle racket;
	//private Image racketBackground;
	
	private int bounceNumber;	//zmienna stosowana do statystyk aktywnosci poszczegolnych graczy
	private Bloom racketBloom;

	public PongRacket(){
		loadContent();
		initialize();
	}
	
	public void setHeight(int h){
		racket.setHeight(h);
	}
	
	public void setWidth(int w){
		racket.setWidth(w);
	}
	
	public void setPosition(int x, int y){
		racket.setTranslateX(x);
		racket.setTranslateY(y);
	}
	
	
	public Rectangle getRacket(){
		return racket;
	}
	
	private void loadContent(){
		//racketBackground = new Image("textures/objects/Ball.png");
	}
	
	private void initialize(){
		racket = new Rectangle();
		racket.setArcHeight(20);
		racket.setArcWidth(20);
		
		racketBloom = new Bloom();
		racketBloom.setThreshold(1.0);
		racket.setEffect(racketBloom); 
		
		//racket.setFill(new ImagePattern(racketBackground)); //ewentualny background
		
		BoxBlur bb = new BoxBlur();        
		bb.setWidth(5);        
		bb.setHeight(5);        
		bb.setIterations(1);
		racket.setEffect(bb);      
		 
	}
	
	public int getBounceNumber() {
		return bounceNumber;
	}

	public void setRacketFill(Color colour){
		racket.setFill(colour);
	}
	
	public void setBounceNumber(int bounceNumber) {
		this.bounceNumber = bounceNumber;
	}
	
	public void incrementBounceNumber(){
		bounceNumber++;
	}
	
	public boolean intersectBall(PongBall ball){
		
		boolean collisionDetected = false;
		
		Shape intersect = Shape.intersect(this.racket, ball.getBall());
        if (intersect.getBoundsInLocal().getWidth() != -1) {
          collisionDetected = true;
          
          int vX = ball.getVelocityX();
          int vY = ball.getVelocityY();
          
          
          double ballY = ball.getBall().getCenterY();
          double racketCenterY = racket.getTranslateY() + racket.getHeight()/2;
          double angle = (racketCenterY - ballY)/(racket.getHeight()/2) * Math.PI/2;
          
          double modulusV = Math.sqrt(vX*vX + vY*vY);
          double sign_vX = Math.signum(vX);
                    
          vX = (int) (Math.cos(angle)*modulusV);
          vX = - (int) (vX * sign_vX);
          vY = (int) (Math.sin(angle)*modulusV);
          ball.setVelocity(vX, vY);
        }
        
        return collisionDetected;
	}
}
