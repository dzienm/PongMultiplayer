package pongClient.model;

import java.io.Serializable;

import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PongBall implements Serializable {

	/**
	 * Klasa reprezentuje obiekt okreslaj¹cy pilke. Nadaje podstawowe wartosci
	 * poczatkowe oraz zmienia parametry jej ruchu na podstawie danych
	 * dostarczonych na jako wskaŸniki predkosci oraz po³o¿enia.
	 * 
	 * @author cprzyborowski
	 */
	private static final long serialVersionUID = -2224822418047337630L;

	private transient Circle ball; // Pole obiektu reprezentuj¹ce wymiary i
									// rozmiar pi³eczki.

	private double velocityX;	 // Pole reprezentowane przez liczbê zmiennoprzecinkowa
								 // definiuj¹ce predkosc w osi poziomej
								 // pi³eczki
	private double velocityY;	 // Pole reprezentowane przez liczbê ca³kowit¹
								 // definiuj¹ce predkosc w osi pionowej
								 // pi³eczki


	/**
	 * Metoda po wywolaniu ktorej zwracana jest wspolrzedna horyzontalna polozenia srodka pileczki.
	 * @return wspolrzedna X srodka.
	 */
	public double getPositionX() {
		return ball.getCenterX();
	}

	/**
	 * Metoda po wywolaniu ktorej zwracana jest wspolrzedna wertykalna polozenia srodka pileczki.
	 * @return wspolrzedna Y srodka.
	 */
	public double getPositionY() {
		return ball.getCenterY();
	}

	/**
	 * Metoda po której wywo³aniu zostaje zwrocony obiekt typu
	 * {@link Scene#shape#Circle} o œrodkowym po³o¿eniu mierzonym w pikselach.
	 * 
	 * @return zwracana jest wartoœæ obiektu {@link Scene#shape#Circle}
	 */
	public Circle getBall() {
		return ball;
	}

	/**
	 * Konstruktor obiektu metody {@link PongBall}. Wywo³uje metodê
	 * inicjalizuj¹c¹ wszystkie wymagane wartoœci obiektu.
	 */
	public PongBall() {
		initialize();
	}

	/**
	 * Metoda ustala parametry centrum obiektu {@link Scene#shape#Circle} dla
	 * {@link PongBall} w postaci 2 liczb zmiennoprzecinkowych maj¹cych odzwierciedlane
	 * w uk³adzie wspó³rzêdnych sceny.
	 * 
	 * @param x
	 *            parametr definiuj¹cy kolejny numer piksela poziomego licz¹c od
	 *            lewej strony sceny
	 * @param y
	 *            parametr definiuj¹cy kolejny numer piksela pionowego licz¹c od
	 *            góry sceny
	 */
	public void setPosition(double x, double y) {
		ball.setCenterX(x);
		ball.setCenterY(y);
	}

	/**
	 * Metoda definiuje kolor z obiektu {@link Color} zgodnie z przyjêtym
	 * s³ownikiem.
	 * 
	 * @param colour
	 *            przyjmuje wartoœci z obiektu {@link Color}
	 */
	public void setBallFill(Color colour) {
		ball.setFill(colour);
	}

	/**
	 * Metoda okreœla parametr promienia dla obiektu {@link Scene#shape#Circle}
	 * prezentuj¹cego pi³eczkê.
	 * 
	 * @param r
	 *            wartoœæ liczbowa ca³kowita okreœlaj¹c promieñ obiektu
	 *            {@link Scene#shape#Circle}
	 */
	public void setRadius(int r) {
		ball.setRadius(r);
	}

	/**
	 * Metoda inicjalizuj¹c¹ podstawowe wartoœci obiektu {@link PongBall}.
	 * Okreœla pocz¹tkowe przyspieszenie i prêdkoœci¹ pi³eczki w zwyk³ych
	 * wspó³rzêdnych kartezjañskich. Definiowane przede wszystkim s¹ takie
	 * wartoœci wysokoœæ generacji pi³eczki szerokoœæ oraz rozmycie krawêdzi
	 * kszta³tu podstawowego obiektu {@link Scene#shape#Circle}.
	 * 
	 */
	private void initialize() {
		ball = new Circle();

		BoxBlur bb = new BoxBlur();
		bb.setWidth(5);
		bb.setHeight(5);
		bb.setIterations(1);
		ball.setEffect(bb);
	}

	/**
	 * Metoda definiuje predkosc w kierunku okreœlonym rzez wektor w
	 * uk³adzie wspó³rzêdnych. W uk³adzie wspó³rzêdnych po³o¿enie obiektu
	 * opisujemy przez podanie odleg³oœci obiektu od pocz¹tku uk³adu
	 * wspó³rzêdnych, czyli d³ugoœci wektora wodz¹cego, oraz k¹ta, jaki tworzy
	 * wektor wodz¹cy z poziom¹ osi¹ kartezjañskiego uk³adu wspó³rzêdnych (osi¹
	 * X). Nale¿y pamiêtaæ i¿ obiektach typu {@link JavaFX} uk³ad wspó³rzêdnych
	 * jest odwrócony i rysuje elementy od lewego górnego rogu do prawego
	 * dolnego rogu macierzy pikseli.
	 * 
	 * @param vX
	 *            wartosc okreœlaj¹ca predkosc pilki w osi
	 *            poziomej
	 * @param vY
	 *            wartosc okreœlaj¹ca predkosc pilki w osi
	 *            pionowej
	 */
	public void setVelocity(double vX, double vY) {
		velocityX = vX;
		velocityY = vY;
	}

	/**
	 * Metoda zwracaj¹ca wartoœæ predkosci pilki w osi poziomej ekranu
	 * 
	 * @return zwracana jest liczba zmiennoprzecinkowa okreslajaca perdkosc pilki w osi
	 *         poziomej ekranu
	 */
	public double getVelocityX() {
		return velocityX;
	}

	/**
	 * Metoda zwracaj¹ca wartoœæ predkosci pilki w w osi pionowej ekranu
	 * 
	 * @return zwracana jest liczba zmiennoprzecinkowa okreslajaca perdkosc pilki w osi
	 *         poziomej ekranu
	 */
	public double getVelocityY() {
		return velocityY;
	}

	/**
	 * Metoda aktualizuje pozycje centrum pola {@link PongBall#ball} o wartoœci
	 * zadane z obiektach definiuj¹cych predkosc pilki
	 * {@link PongBall#getVelocityX()} oraz {@link PongBall#getVelocityY()}.
	 */
	public void updatePosition() {
		ball.setCenterX(ball.getCenterX() + velocityX);
		ball.setCenterY(ball.getCenterY() + velocityY);

	}
}
