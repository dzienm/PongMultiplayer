package gameUtilities;

public final class GameUtilitiesVariables {

	//Zmienne konfiguracyjne obiektów gry. Zmienne s¹ finalne, zatem dostêp do nich mo¿na upubliczniæ i nie jest to b³êdem
	//(podobnie jest np. z wartoœci¹ Math.PI w klasie Math
	
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
	
	
	
	private GameUtilitiesVariables(){
		
	}
	
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
	
	public static boolean validPort(String _port){
		try{
			int serverPort = Integer.parseInt(_port);
			if(serverPort<1||serverPort>65535){
				return false;
			}
			else{
				return true;
			}
			
		}
		catch(NumberFormatException e){
			return false;
		}
	}
	
}
