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
 * Klasa generuj¹ca oraz zarz¹dzaj¹ca rakietk¹ gracza w postaci prostok¹ta o
 * zdefiniowanej szerokoœæ. W klasie s¹ zawarte metody definiuj¹ce zachowanie
 * oraz polozenie rakiety gracza. Przechowywane s¹ tu równie¿ statystyki ka¿dego
 * obiektu rakiety, parametry okreœlaj¹ce wyœwietlanie oraz okreœlaj¹cy
 * interakcje z obiektem {@link PongBall} zmieniaj¹c jego wektory ruchu oraz
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

	private int bounceNumber; // Zmienna stosowana do statystyk aktywnoœci
								// poszczególnych graczy.

	private transient Bloom racketBloom; // Zmienna stosowana do definiowania
											// efektu
											// wyœwietlania rakietki.

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
	 * Bezparametryczny konstruktor klasy, Uruchamia metody ³aduj¹ce kontent
	 * oraz wype³niaj¹ce wiêkszoœæ pól obiektu. Wiêkszoœæ wartoœæ parametrów
	 * zostaje za³adowana zdefiniowanymi na sta³e wartoœciami okreœlaj¹cymi
	 * wielkoœæ i szerokoœæ prostok¹ta {@link PongRacket} oraz parametry
	 * definiuj¹ce rodzaj wyœwietlania elementu
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
	 * Metoda okreœla wielkoœæ wysokoœci prostok¹ta rakietki.
	 * 
	 * @param h
	 *            parametr przyjmuje wartoœæ liczb¹ ca³kowit¹ okreœlaj¹c¹
	 *            wielkoœæ w pikselach
	 */
	public void setHeight(int h) {
		racket.setHeight(h);
	}

	/**
	 * Metoda okreœla wielkoœæ szerokoœæ prostok¹ta rakietki.
	 * 
	 * @param w
	 *            parametr przyjmuje wartoœæ liczb¹ ca³kowit¹ okreœlaj¹c¹
	 *            szerokoœæ w pikselach
	 */
	public void setWidth(int w) {
		racket.setWidth(w);
	}

	/**
	 * Metoda definiuje pomorzenie prostok¹ta w przestrzeni dwu wymiarowej na
	 * macierzy sceny gry {@link TitleScreenView#setView}
	 * 
	 * @param x
	 *            parametr przyjmuje wartoœæ liczb¹ ca³kowit¹ okreœlaj¹c¹
	 *            przesuniecie szerokoœci obiektu w odstêpie od lewego górnego
	 *            roku pikselach
	 * @param y
	 *            parametr przyjmuje wartoœæ liczb¹ ca³kowit¹ okreœlaj¹c¹
	 *            przesuniecie wysokoœci obiektu w odstêpie od lewego górnego
	 *            roku pikselach
	 */
	public void setPosition(double x, double y) {
		racket.setTranslateX(x);
		racket.setTranslateY(y);
	}

	/**
	 * Metoda maj¹ca na celu przekazanie informacji o pomorzeniu w przestrzenie
	 * dwu wymiarowej obiektu {@link PongRacket}.
	 * 
	 * @return zwracany jest obiekt Rectangle z zawartymi w wewn¹trz
	 *         interesuj¹cymi nas wspó³rzêdnymi
	 */
	public Rectangle getRacket() {
		return racket;
	}

	/**
	 * Metod s³u¿y do zwracania pola która przechowuje liczbê dobiæ rakietki.
	 * 
	 * @return zwracana jest liczba reprezentuj¹ca liczbe odbic pi³eczek od
	 *         rakietki
	 */
	public int getBounceNumber() {
		return bounceNumber;
	}

	/**
	 * Wywo³anie metody powoduje inkrementacjê liczby odbiæ dla zmiennej obiektu
	 * {@link PongRacket}.
	 */
	public void incrementBounceNumber() {
		bounceNumber++;
	}

	/**
	 * Metoda definiuje kolor z obiektu {@link Color} zgodnie z przyjêtym
	 * s³ownikiem.
	 * 
	 * @param colour
	 *            przyjmuje wartoœci z obiektu {@link Color}
	 */
	public void setRacketFill(Color colour) {
		racket.setFill(colour);
	}

	/**
	 * Metoda pozwala na wykrycie kolizji obiektu {@link PongRacket} oraz
	 * obiektu {@link PongBall}. Gdy parametr {@link Shape.intersect} przyjmie
	 * inna jak -1 oznacza, to i¿ obiekty wesz³y w kolizje. Zostaje obliczona w
	 * którym miejscu obiektu {@link PongRacket} proporcjonalnie do œrodka oraz
	 * obliczany jest wektor ruchu {@link PongBall}. Zostaj¹ nastêpnie zmienione
	 * nastêpuj¹ce parametry w obiekcie {@link PongBall} przy u¿yciu funkcji
	 * {@link Math#pi}. Po przeprowadzonych obliczaniach na obiekt
	 * {@link PongBall} zostaje zaburzony wektor ruchu i przekazany do obiektu
	 * {@link PongBall}. Wartosc kata odbicia zostala ograniczona do 60 stopni.
	 * W przypadku gdy rakietka reprezentuje bariere pozioma pola gry, odbicie
	 * pileczki jest doskonale sprezyste.
	 * 
	 * @param ball
	 *            przekazywany jest obiekt reprezentuj¹cy pi³eczkê
	 * @return zawracana wartoœci boolowska gdy dla 2 obiektów nast¹pi³a kolizja
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
