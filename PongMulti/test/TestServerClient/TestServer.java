package TestServerClient;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
			ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream()); 
			
			PongBall pilka = (PongBall) inStream.readObject();
			System.out.println("Otrzymalem pilke. Jej predkosc w kierunku X = " + pilka.getVelocityX());
			
			PongBall pilka2 = (PongBall) inStream.readObject();
			System.out.println("Otrzymalem pilke2. Jej predkosc w kierunku X = " + pilka2.getVelocityX());
			
			DataInputStream dataIn = new DataInputStream(socket.getInputStream());
			boolean testB = dataIn.readBoolean();
			if(testB){
				System.out.println("test");
			}
			
			//socket = serverSocket.accept();
			
			//ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream()); 
			//pilka.setVelocity(2, 2);
			//outStream.flush();
			//System.out.println("Wyslalem pilke. Jej predkosc w kierunku X = " + pilka.getVelocityX());
			
			//socket.close();

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
