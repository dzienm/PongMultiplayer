package gameUtilities;

/**
 * Klasa statyczna zawierajaca zmienne konfiguracyjne gry oraz przydatne metody
 * statyczne.
 * 
 * @author mdziendzikowski
 *
 */
public final class GameUtilitiesVariables {

	// Zmienne konfiguracyjne obiektów gry. Zmienne s¹ finalne, zatem dostêp do
	// nich mo¿na upubliczniæ i nie jest to b³êdem
	// (podobnie jest np. z wartoœci¹ Math.PI w klasie Math

	public static final int racketSpeed = 7;
	public static final int initialRacketBoundaryOffset = 100;
	public static final int racketHeight = 100;
	public static final int racketWidth = 22;

	public static final int initialBallSpeed = 5;
	public static final int ballRadius = 10;

	public static final int gameBoardWidth = 1100;
	public static final int gameBoardHeight = 700;
	public static final int boundaryHeight = 5;
	public static final int lowerBoundOffset = 15;
	public static final int upperBoundOffset = 10;

	public static final int maxscore = 10;

	// prywatny konstruktor zapobiegajacy inicjalizacji klasy
	private GameUtilitiesVariables() {

	}

	/**
	 * Metoda weryfikujaca poprawnosc adresu IP serwera.
	 * 
	 * @author mdziendzikowski
	 * @param ip
	 *            adres ip serwera, dopuszczalny format xxx.xxx.xxx.xxx lub
	 *            {@link String} o warosci "localhost"
	 * @return zwraca informacje o poprawnosci wprowadzonego adresu IP
	 */
	public static boolean validIP(String ip) {

		if (ip.equals("localhost")) {
			return true;
		} else {

			try {
				if (ip == null || ip.isEmpty()) {
					return false;
				}

				String[] parts = ip.split("\\.");
				if (parts.length != 4) {
					return false;
				}

				for (String s : parts) {
					int i = Integer.parseInt(s);
					if ((i < 0) || (i > 255)) {
						return false;
					}
				}
				if (ip.endsWith(".")) {
					return false;
				}

				return true;
			} catch (NumberFormatException nfe) {
				return false;
			}
		}
	}

	/**
	 * Metoda weryfikujaca poprawnosc portu serwera
	 * 
	 * @author mdziendzikowski
	 * @param _port port pod ktorym ma nasluchiwac serwer
	 * @return zwraca informacje o poprawnosci wprowadzonej wartosci portu
	 */
	public static boolean validPort(String _port) {
		try {
			int serverPort = Integer.parseInt(_port);
			if (serverPort < 1 || serverPort > 65535) {
				return false;
			} else {
				return true;
			}

		} catch (NumberFormatException e) {
			return false;
		}
	}

}
