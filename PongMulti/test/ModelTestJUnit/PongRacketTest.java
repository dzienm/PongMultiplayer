package ModelTestJUnit;

import static org.junit.Assert.*;

import org.junit.Test;

import pongClient.model.PongBall;
import pongClient.model.PongRacket;

/**
 * Testy klasy {@link PongRacket}
 * @author mdziendzikowski
 *
 */
public class PongRacketTest {

	
	/**
	 * Test konstruktora klasy.
	 */
	@Test
	public final void testPongRacket() {

		PongRacket tester = new PongRacket();
		
		assertEquals(tester.getClass().toString(),"class pongClient.model.PongRacket");
	}

	/**
	 * Test metody okreslajacej wysokosc rakietki.
	 */
	@Test
	public final void testSetHeight() {
		PongRacket tester = new PongRacket();
		int tmp = 20;
		Exception ex = null; 
		
        try {
        	tester.setHeight(tmp);
        } catch (Exception e) {
            ex = e;
        }
        assertEquals(null,ex);
    }
		
	/**
	 * Test metody okreslajacej szerokosc rakietki 
	 */
	@Test
	public final void testSetWidth() {
		PongRacket tester = new PongRacket();
		int tmp = 20;
		Exception ex = null; 
		
        try {
        	tester.setWidth(tmp);
        } catch (Exception e) {
            ex = e;
        }
        assertEquals(null,ex);
	}

	/**
	 * Test metody okreslajacej pozycje rakietki.
	 */
	@Test
	public final void testSetPosition() {
		PongRacket tester = new PongRacket();
		int x = 20;
		int y=50;
		Exception ex = null; 
		
        try {
        	tester.setPosition(x, y);
        } catch (Exception e) {
            ex = e;
        }
        assertTrue(null==ex);
       int testX = (int) tester.getRacket().getTranslateX();
       int testY = (int) tester.getRacket().getTranslateY();
        assertEquals(x, testX);
        assertEquals(y, testY);
        
		
	}

	
	/**
	 * Test metody zwracajacej liczbe odbic pileczki od rakietki.
	 */
	@Test
	public final void testGetBounceNumber() {
		PongRacket tester = new PongRacket();
		int tmp = 20;
		tester.setBounceNumber(tmp);
		assertEquals(tmp, tester.getBounceNumber());
	}

	/**
	 * Test metody ustalajacej liczbe odbic pileczek od rakietki
	 */
	@Test
	public final void testSetBounceNumber() {
		PongRacket tester = new PongRacket();
		int tmp = 20;
		tester.setBounceNumber(tmp);
		assertEquals(tmp, tester.getBounceNumber());
	}

	/**
	 * Test metody inkrementujacej liczbe odbic pileczek od rakietki.
	 */
	@Test
	public final void testIncrementBounceNumber() {
		PongRacket tester = new PongRacket();
		int tmp = 20;
		tester.setBounceNumber(tmp);
		tester.incrementBounceNumber();
		assertEquals(tmp+1, tester.getBounceNumber());
	}

	/**
	 * Test metody okreslajacej czy rakietka i pileczka stykaja sie.
	 */
	@Test
	public final void testIntersectBall() {
		
		PongRacket tester = new PongRacket();
		tester.setHeight(100);
		tester.setWidth(25);
		tester.setPosition(0, 0);
		
		PongBall pileczka = new PongBall();
		pileczka.setRadius(10);
		pileczka.setPosition(0, 0);
		
		boolean przecina = tester.intersectBall(pileczka);
		assertTrue(przecina);
	}

}
