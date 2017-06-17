package pongServer;

import java.io.Serializable;

public enum ServerStateEnum implements Serializable{
		
		NotConnected,
		Initialized, 
		ConnectionEstablished, 
		GameStarted,
		GamePaused,
		GameOver;
}
	

