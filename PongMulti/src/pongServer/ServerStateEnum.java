package pongServer;

import java.io.Serializable;

/**
 * Klasa wyliczeniowa mozliwych stanow serwera.
 * 
 * @author mdziendzikowski
 *
 */
public enum ServerStateEnum implements Serializable{
		
		NotConnected,
		Initialized, 
		ConnectionEstablished, 
		GameStarted,
		GamePaused,
		GameOver;
}
	

