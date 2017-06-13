package pongServer.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PongServerThreads {

	private ServerSocket serverSocket;
		
	public void runServer(int port) throws IOException{
		serverSocket = new ServerSocket(port);
		while(true)
		{
			new ClientUpdater(serverSocket.accept()).start();
		}
	}
	
	private class ClientUpdater extends Thread{
		private Socket socket;
		
		public ClientUpdater(Socket listener){
			socket = listener;
		}
		
		public void run(){
			
		}
		
	}
	
	
}
