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
	
	private transient Circle ball; //transient potrzebne do serializacji
	private int velocityX;
	private int velocityY;
	
	public Circle getBall() {
		return ball;
	}
	
	public PongBall(){
		initialize();
	}
	
	public void setPosition(int x, int y){
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
	
	public void setVelocity(int vX,int vY){
		velocityX = vX;
		velocityY = vY;
	}
	
	public int getVelocityX() {
		return velocityX;
	}

	public int getVelocityY() {
		return velocityY;
	}

	public void updatePosition(){
		ball.setCenterX(ball.getCenterX() + velocityX);
		ball.setCenterY(ball.getCenterY() + velocityY);
		
		//this.setPosition(ball.getCenterX() + this.velocityX, ball.getCenterY() + this.velocityY);
	}
}
