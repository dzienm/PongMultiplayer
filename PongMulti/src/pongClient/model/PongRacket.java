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

/**
 * Klasa generuj�ca oraz zarz�dzaj�ca rakietk� gracza w postaci prostok�ta o
 * zdefiniowanej szeroko��. W klasie s� zawarte metody definiuj�ce zachowanie
 * oraz polozenie rakiety gracza. Przechowywane s� tu r�wnie� statystyki ka�dego
 * obiektu rakiety, parametry okre�laj�ce wy�wietlanie oraz okre�laj�cy
 * interakcje z obiektem {@link PongBall} zmieniaj�c jego wektory ruchu oraz
 * pomorzenie w przestrzeni na podstawie interakcji.
 * 
 * @author mdziendzikowski
 */

public class PongRacket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8035467035245885622L;

	private transient Rectangle racket; // Obiekt klasy JAVAFX reprezentujacy
										// rakietke

	public double getPositionX() {
		return racket.getTranslateX();
	}

	public double getPositionY() {
		return racket.getTranslateY();
	}

	private int bounceNumber; // Zmienna stosowana do statystyk aktywno�ci
								// poszczeg�lnych graczy.

	private transient Bloom racketBloom; // Zmienna stosowana do definiowania
											// efektu
											// wy�wietlania rakietki.

	private boolean isHorizontalBoundary; // Zmienna okreslajaca czy rakietka
											// reprezentuje poziome ograniczenie
											// pola gry.

	/**
	 * Metoda okreslajaca czy rakietka reprezentuje ograniczenie poziome pola
	 * gry
	 * 
	 * @return zwraca informacje czy rakietka stanowi pozioma bariere pola gry
	 */
	public boolean isHorizontalBoundary() {
		return isHorizontalBoundary;
	}

	/**
	 * Metoda umozliwiajaca ustanowienie rakietki poziomym ograniczeniem pola
	 * gry
	 * 
	 * @param isHorizontalBoundary
	 *            zmienna okreslajaca czy rakietka ma byc ograniczeniem pola gry
	 */
	public void setHorizontalBoundary(boolean isHorizontalBoundary) {
		this.isHorizontalBoundary = isHorizontalBoundary;
	}

	/**
	 * Bezparametryczny konstruktor klasy, Uruchamia metody �aduj�ce kontent
	 * oraz wype�niaj�ce wi�kszo�� p�l obiektu. Wi�kszo�� warto�� parametr�w
	 * zostaje za�adowana zdefiniowanymi na sta�e warto�ciami okre�laj�cymi
	 * wielko�� i szeroko�� prostok�ta {@link PongRacket} oraz parametry
	 * definiuj�ce rodzaj wy�wietlania elementu
	 */
	public PongRacket() {
		initialize();
		isHorizontalBoundary = false;
	}

	/**
	 * Metoda inicjalizujaca stan rakietki po wywolaniu konstruktora obiektu.
	 */
	private void initialize() {
		racket = new Rectangle();
		racket.setArcHeight(20);
		racket.setArcWidth(20);

		racketBloom = new Bloom();
		racketBloom.setThreshold(1.0);
		racket.setEffect(racketBloom);

		BoxBlur bb = new BoxBlur();
		bb.setWidth(5);
		bb.setHeight(5);
		bb.setIterations(1);
		racket.setEffect(bb);

	}

	/**
	 * Metoda okre�la wielko�� wysoko�ci prostok�ta rakietki.
	 * 
	 * @param h
	 *            parametr przyjmuje warto�� liczb� ca�kowit� okre�laj�c�
	 *            wielko�� w pikselach
	 */
	public void setHeight(int h) {
		racket.setHeight(h);
	}

	/**
	 * Metoda okre�la wielko�� szeroko�� prostok�ta rakietki.
	 * 
	 * @param w
	 *            parametr przyjmuje warto�� liczb� ca�kowit� okre�laj�c�
	 *            szeroko�� w pikselach
	 */
	public void setWidth(int w) {
		racket.setWidth(w);
	}

	/**
	 * Metoda definiuje pomorzenie prostok�ta w przestrzeni dwu wymiarowej na
	 * macierzy sceny gry {@link TitleScreenView#setView}
	 * 
	 * @param x
	 *            parametr przyjmuje warto�� liczb� ca�kowit� okre�laj�c�
	 *            przesuniecie szeroko�ci obiektu w odst�pie od lewego g�rnego
	 *            roku pikselach
	 * @param y
	 *            parametr przyjmuje warto�� liczb� ca�kowit� okre�laj�c�
	 *            przesuniecie wysoko�ci obiektu w odst�pie od lewego g�rnego
	 *            roku pikselach
	 */
	public void setPosition(double x, double y) {
		racket.setTranslateX(x);
		racket.setTranslateY(y);
	}

	/**
	 * Metoda maj�ca na celu przekazanie informacji o pomorzeniu w przestrzenie
	 * dwu wymiarowej obiektu {@link PongRacket}.
	 * 
	 * @return zwracany jest obiekt Rectangle z zawartymi w wewn�trz
	 *         interesuj�cymi nas wsp�rz�dnymi
	 */
	public Rectangle getRacket() {
		return racket;
	}

	/**
	 * Metod s�u�y do zwracania pola kt�ra przechowuje liczb� dobi� rakietki.
	 * 
	 * @return zwracana jest liczba reprezentuj�ca liczbe odbic pi�eczek od
	 *         rakietki
	 */
	public int getBounceNumber() {
		return bounceNumber;
	}

	/**
	 * Wywo�anie metody powoduje inkrementacj� liczby odbi� dla zmiennej obiektu
	 * {@link PongRacket}.
	 */
	public void incrementBounceNumber() {
		bounceNumber++;
	}

	/**
	 * Metoda definiuje kolor z obiektu {@link Color} zgodnie z przyj�tym
	 * s�ownikiem.
	 * 
	 * @param colour
	 *            przyjmuje warto�ci z obiektu {@link Color}
	 */
	public void setRacketFill(Color colour) {
		racket.setFill(colour);
	}

	/**
	 * Metoda pozwala na wykrycie kolizji obiektu {@link PongRacket} oraz
	 * obiektu {@link PongBall}. Gdy parametr {@link Shape.intersect} przyjmie
	 * inna jak -1 oznacza, to i� obiekty wesz�y w kolizje. Zostaje obliczona w
	 * kt�rym miejscu obiektu {@link PongRacket} proporcjonalnie do �rodka oraz
	 * obliczany jest wektor ruchu {@link PongBall}. Zostaj� nast�pnie zmienione
	 * nast�puj�ce parametry w obiekcie {@link PongBall} przy u�yciu funkcji
	 * {@link Math#pi}. Po przeprowadzonych obliczaniach na obiekt
	 * {@link PongBall} zostaje zaburzony wektor ruchu i przekazany do obiektu
	 * {@link PongBall}. Wartosc kata odbicia zostala ograniczona do 60 stopni.
	 * W przypadku gdy rakietka reprezentuje bariere pozioma pola gry, odbicie
	 * pileczki jest doskonale sprezyste.
	 * 
	 * @param ball
	 *            przekazywany jest obiekt reprezentuj�cy pi�eczk�
	 * @return zawracana warto�ci boolowska gdy dla 2 obiekt�w nast�pi�a kolizja
	 * @author mdziendzikowski
	 */
	public boolean intersectBall(PongBall ball) {

		boolean collisionDetected = false;

		Shape intersect = Shape.intersect(this.racket, ball.getBall());
		if (intersect.getBoundsInLocal().getWidth() != -1) {
			collisionDetected = true;

			// zwiekszenie licznika odbic pileczki
			incrementBounceNumber();

			double vX = ball.getVelocityX();
			double vY = ball.getVelocityY();

			if (isHorizontalBoundary) {
				vY = -vY; // odbicie sprezyste dla barier poziomych ekranu
			} else {

				// zdefiniowanie kata odbicia w zaleznosci od wzglednego
				// polozenia pileczki i rakietki
				double ballY = ball.getBall().getCenterY();
				double racketCenterY = racket.getTranslateY() + racket.getHeight() / 2;
				double angle = (ballY - racketCenterY) / (racket.getHeight() / 2) * 90.0;

				// ograniczenie kata odbicia
				if (Math.abs(angle) > 60) {
					if (angle > 0) {
						angle = angle - 30.0;
					} else {
						angle = angle + 30.0;
					}
				}

				angle = angle / 360.0 * 2 * Math.PI;

				double modulusV = Math.sqrt(vX * vX + vY * vY);
				double sign_vX = Math.signum(vX);

				vX = Math.cos(angle) * modulusV;
				vX = -1.0 * vX * sign_vX;
				vY = Math.sin(angle) * modulusV;

			}

			// modyfikacja wektora predkosci pileczki po odbiciu
			ball.setVelocity(vX, vY);
		}

		return collisionDetected;
	}
}
