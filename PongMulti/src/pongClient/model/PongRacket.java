package pongClient.model;

import java.io.Serializable;

import javafx.scene.effect.Bloom;
import javafx.scene.effect.BoxBlur;
//import javafx.scene.image.Image;
import javafx.scene.paint.Color;
//import javafx.scene.shape.Circle;
//import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class PongRacket implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8035467035245885622L;

	private transient Rectangle racket;
	public double getPositionX(){
		return racket.getTranslateX();
	}
	public double getPositionY(){
		return racket.getTranslateY();
	}
	
	//private Image racketBackground;
	
	private int bounceNumber;	//zmienna stosowana do statystyk aktywnosci poszczegolnych graczy
	private transient Bloom racketBloom;

	private boolean isHorizontalBoundary;
	
	public boolean isHorizontalBoundary() {
		return isHorizontalBoundary;
	}

	public void setHorizontalBoundary(boolean isHorizontalBoundary) {
		this.isHorizontalBoundary = isHorizontalBoundary;
	}

	public PongRacket(){
		loadContent();
		initialize();
		isHorizontalBoundary = false;
	}
	
	public void setHeight(int h){
		racket.setHeight(h);
	}
	
	public void setWidth(int w){
		racket.setWidth(w);
	}
	
	public void setPosition(double x, double y){
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
	
	public boolean intersectBall(PongBall ball) {

		boolean collisionDetected = false;

		Shape intersect = Shape.intersect(this.racket, ball.getBall());
		if (intersect.getBoundsInLocal().getWidth() != -1) {
			collisionDetected = true;

			double vX = ball.getVelocityX();
			double vY = ball.getVelocityY();

			if (isHorizontalBoundary) {
				vY = -vY;
			} 
			else {

				double ballY = ball.getBall().getCenterY();
				double racketCenterY = racket.getTranslateY() + racket.getHeight() / 2;
				double angle = (ballY - racketCenterY) / (racket.getHeight() / 2) * 90.0;

				if(Math.abs(angle)>60 ){
					if(angle>0){
						angle = angle - 30.0;
					}
					else{
						angle = angle + 30.0;
					}
				}
				
				angle = angle / 360.0 * 2 * Math.PI;
				
				double modulusV = Math.sqrt(vX * vX + vY * vY);
				double sign_vX = Math.signum(vX);

				/*vX = (int) (Math.cos(angle) * modulusV);
				vX = -1 * (int) (vX * sign_vX);
				vY = (int) (Math.sin(angle) * modulusV);*/
				
				vX = Math.cos(angle) * modulusV;
				vX = -1.0 * vX * sign_vX;
				vY = Math.sin(angle) * modulusV;
						
			}
			
			ball.setVelocity(vX, vY);
		}

		return collisionDetected;
	}
}
