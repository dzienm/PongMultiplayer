package TestServerClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import pongClient.model.PongBall;

public class TestServer {

	private static ServerSocket serverSocket = null;
	private static Socket socket = null;
	private static ObjectInputStream inStream = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		try {
			System.out.println("Uruchamiam socket");
			serverSocket = new ServerSocket(4445);
			socket = serverSocket.accept();
			System.out.println("Connected");
			inStream = new ObjectInputStream(socket.getInputStream());

			PongBall pilka = (PongBall) inStream.readObject();
			System.out.println("Otrzymalem pilke. Jej predkosc w kierunku X = " + pilka.getVelocityX());
			socket.close();

			} catch (SocketException se) {
				se.printStackTrace();
			System.exit(0);
			} catch (IOException e) {
			e.printStackTrace();
			} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
			}
	}

}
