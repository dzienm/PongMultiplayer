package pongClient.model;

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
