package ModelTest;

import static org.junit.Assert.*;

import org.junit.Test;

import javafx.scene.paint.Color;
import pongClient.model.PongBall;

public class PongBallTest {

	@Test
	public final void testGetBall() {
		PongBall ball= new PongBall();
	assertEquals(ball.getBall().getClass().toString(),"class javafx.scene.shape.Circle");

	}

	@Test
	public final void testPongBall() {
	

		Exception ex = null; 
		
        try {
        	PongBall ball= new PongBall();
        	ball.toString();
        } catch (Exception e) {
            ex = e;
        }
        assertEquals(null,ex);
	}

	@Test
	public final void testSetPosition() {
		PongBall ball= new PongBall();
		int x = 20;
		int y=50;
		Exception ex = null; 
		
        try {
        	ball.setPosition(x, y);
        } catch (Exception e) {
            ex = e;
        }
        assertTrue(null==ex);

        
	}

	@Test
	public final void testSetBallFill() {
		PongBall ball= new PongBall();
		Exception ex = null; 
		
        try {
        	ball.setBallFill(Color.BLUE);
        } catch (Exception e) {
            ex = e;
        }
        assertTrue(null==ex);
	}

	@Test
	public final void testSetRadius() {
		PongBall ball= new PongBall();
	int	tmp= 30;
		Exception ex = null; 
		
        try {
        	ball.setRadius(tmp);
        } catch (Exception e) {
            ex = e;
        }
        assertTrue(null==ex);
	}

	@Test
	public final void testSetVelocity() {
		PongBall ball= new PongBall();
		int x = 20;
		int y=50;
		ball.setVelocity(x, y);
	       int testX = (int) ball.getVelocityX();
	       int testY = (int) ball.getVelocityY();
	        assertEquals(x, testX);
	        assertEquals(y, testY);
	}

	@Test
	public final void testGetVelocityX() {
		PongBall ball= new PongBall();
		int x = 20;
		int y=50;
		ball.setVelocity(x, y);
	       int testX = (int) ball.getVelocityX();

	        assertEquals(x, testX);

	}

	@Test
	public final void testGetVelocityY() {
		PongBall ball= new PongBall();
		int x = 20;
		int y=50;
		ball.setVelocity(x, y);
	       int testY = (int) ball.getVelocityY();

	        assertEquals(y, testY);
	}

	@Test
	public final void testUpdatePosition() {

		PongBall ball= new PongBall();
		int x = 20;
		int y=50;
		ball.setVelocity(x, y);
	       int testX = (int) ball.getVelocityX();
	       int testY = (int) ball.getVelocityY();
	        assertEquals(x, testX);
	        assertEquals(y, testY);
	        ball.setPosition(x, y);
	        ball.updatePosition();
	     int posX=   (int) ball.getBall().getCenterX();
	     int posY=  (int) ball.getBall().getCenterY();
	        assertEquals(y+y,posY);
	        assertEquals(x+x,posX);
	}

}
