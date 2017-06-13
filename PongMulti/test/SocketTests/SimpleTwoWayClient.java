package SocketTests;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import pongClient.model.PongBall;

public class SimpleTwoWayClient {

	static Socket socket;
	
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException {
		socket = new Socket("localHost", 9898);
		
		DataOutputStream dataWriter = new DataOutputStream(socket.getOutputStream());
		DataInputStream dataReader = new DataInputStream(socket.getInputStream());
		
		while(true){
		int zmienna = 2;
		System.out.println("Wysylam: " + zmienna);
		dataWriter.writeInt(zmienna);
		System.out.println("Czekam na odbior");
		zmienna = dataReader.readInt();
		System.out.println(zmienna);
		
		ObjectOutputStream pileczkaWriter = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream pileczkaReader = new ObjectInputStream(socket.getInputStream());
		
		PongBall pileczka = new PongBall();
		pileczka.setVelocity(1, 1);
		
		System.out.println("Wysylam pileczke predkosc: Vx = " + pileczka.getVelocityX() + "Vy = " + pileczka.getVelocityY());
		pileczkaWriter.writeObject(pileczka);
		System.out.println("Czekam na pilke...");
		pileczka = (PongBall) pileczkaReader.readObject();
		System.out.println("Odebralem pilke. Jej predkosc: Vx = " + pileczka.getVelocityX() + "Vy = " + pileczka.getVelocityY());
		
		Thread.sleep(2000);
		}
	}

}
