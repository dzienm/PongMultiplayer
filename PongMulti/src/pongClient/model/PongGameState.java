package pongClient.model;

/**
 * 
 * @author mdziendzikowski
 *
 * Klasa zawiera stan gry serwera, tj. pileczki, paletki itp. Stan gry jest przekazywany po sockecie klientom przy kazdym zapytaniu
 *
 */

public class PongGameState {

	private GameStateEnum gameState;
	
	public GameStateEnum getGameState() {
		return gameState;
	}

	public void setGameState(GameStateEnum gameState) {
		this.gameState = gameState;
	}

	public PongGameState(){
		this.gameState = GameStateEnum.TitleScreen;
	}
}
