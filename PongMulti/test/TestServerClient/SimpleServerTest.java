package TestServerClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServerTest {

	private static ServerSocket serverPong;
	private static Socket clientSocket;
	private static Thread clientThread;
	
	public static void main(String[] args) throws IOException {
		serverPong = new ServerSocket(8989);
		
		Runnable clientListener = new Runnable(){

			@Override
			public void run() {
				try {
					System.out.println("Czekam na polaczenie");
					clientSocket = serverPong.accept();
					System.out.println("Polaczono.");
					
				} catch (IOException e) {
					e.printStackTrace();
					
				} 

			
				

			}

		};

		clientThread = new Thread(clientListener);
		clientThread.start();

	}

}
