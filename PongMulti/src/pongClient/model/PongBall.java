package pongClient.model;

import java.io.Serializable;

import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PongBall implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2224822418047337630L;
	
	private transient Circle ball; 
	
	private double velocityX;
	private double velocityY;
	
	public double getPositionX() {
		return ball.getCenterX();
	}

	public double getPositionY() {
		return ball.getCenterY();
	}

	public Circle getBall() {
		return ball;
	}
	
	public PongBall(){
		initialize();
	}
	
	public void setPosition(double x, double y){
		ball.setCenterX(x);
		ball.setCenterY(y);
	}

	public void setBallFill(Color colour){
		ball.setFill(colour);
	}	
	
	public void setRadius(int r){
		ball.setRadius(r);
	}
	
	private void initialize(){
		ball = new Circle();
		
		BoxBlur bb = new BoxBlur();        
		bb.setWidth(5);        
		bb.setHeight(5);        
		bb.setIterations(1);
		ball.setEffect(bb);      		 
	}
	
	public void setVelocity(double vX,double vY){
		velocityX = vX;
		velocityY = vY;
	}
	
	public double getVelocityX() {
		return velocityX;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public void updatePosition(){
		ball.setCenterX(ball.getCenterX() + velocityX);
		ball.setCenterY(ball.getCenterY() + velocityY);
		
		//this.setPosition(ball.getCenterX() + this.velocityX, ball.getCenterY() + this.velocityY);
	}
}
