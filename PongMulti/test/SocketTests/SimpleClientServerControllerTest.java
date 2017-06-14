package SocketTests;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import pongClient.model.PongBall;

public class SimpleClientServerControllerTest {

	static Socket socket;
	
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException {
		socket = new Socket("localHost", 9898);
		System.out.println("Polaczone");
		ObjectOutputStream pileczkaWriter = new ObjectOutputStream(socket.getOutputStream());
		//ObjectInputStream pileczkaReader = new ObjectInputStream(socket.getInputStream());
		
		//while(true){
		
		PongBall pileczka = new PongBall();
		pileczka.setVelocity(1, 1);
		
		System.out.println("Wysylam pileczke predkosc: Vx = " + pileczka.getVelocityX() + "Vy = " + pileczka.getVelocityY());
		pileczkaWriter.writeObject(pileczka);
		//System.out.println("Czekam na pilke...");
		//pileczka = (PongBall) pileczkaReader.readObject();
		//System.out.println("Odebralem pilke. Jej predkosc: Vx = " + pileczka.getVelocityX() + "Vy = " + pileczka.getVelocityY());
		
		Thread.sleep(1000);
		socket.close();
		//}
	}

}
