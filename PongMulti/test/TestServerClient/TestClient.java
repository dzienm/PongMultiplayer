package TestServerClient;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import pongClient.model.PongBall;

public class TestClient {

	private static Socket socket = null;
	private ObjectInputStream inputStream = null;
	private static ObjectOutputStream outputStream = null;
	private static boolean isConnected = false;
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		 while (!isConnected) {
			 try {
			 socket = new Socket("localHost", 4445);
			 System.out.println("Connected");
			 //isConnected = true;
			 
			 outputStream = new ObjectOutputStream(socket.getOutputStream());
			 PongBall pilka = new PongBall();
			 pilka.setVelocity(1, 1);
			 System.out.println("Object to be written = " + pilka);
			 //outputStream = new ObjectOutputStream(socket.getOutputStream());
			 outputStream.writeObject(pilka);
			 PongBall pilka2 = new PongBall();
			 pilka2.setVelocity(2,1);
			 outputStream.writeObject(pilka2);
			 
			 DataOutputStream dataStream = new DataOutputStream(socket.getOutputStream());
			 dataStream.writeBoolean(true);
			 
			 Thread.sleep(20000);  //bez tego wysupuje sie komunikacja z serwerem bo jest za szybko trzeba pomyslec o jakims handshaku
			 //trzeba pomyslec jak klient ma czekac na to zeby serwer skoczyl czytac onaczej jest connection reset exception
			 
			 //ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
			 //pilka = (PongBall) inStream.readObject();
			 //System.out.println("Predkosc pilki w kierunku x po zmianie przez server = " + pilka.getVelocityX());
			 
			 } catch (SocketException se) {
			 se.printStackTrace();
			 // System.exit(0);
			 } catch (IOException e) {
			 e.printStackTrace();
			 }
		
	}

}
	
}
