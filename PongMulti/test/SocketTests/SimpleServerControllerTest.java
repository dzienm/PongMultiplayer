package SocketTests;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import pongClient.model.PongBall;

public class SimpleServerControllerTest {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		ServerSocket listener = new ServerSocket(9898);
		System.out.println("Czekam na polaczenie");
		Socket socket = listener.accept();
		
		System.out.println("Polaczono");
		
		DataOutputStream dataWriter = new DataOutputStream(socket.getOutputStream());
		DataInputStream dataReader = new DataInputStream(socket.getInputStream());
		
		while(true){
			
			try{
			
			ObjectOutputStream pileczkaWriter = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream pileczkaReader = new ObjectInputStream(socket.getInputStream());
			
			PongBall pileczka = (PongBall) pileczkaReader.readObject();
			System.out.println("Odebralem pileczke predkosc: Vx = " + pileczka.getVelocityX() + "Vy = " + pileczka.getVelocityY());
			pileczka.setVelocity(2*pileczka.getVelocityX(), 2*pileczka.getVelocityY());
			//System.out.println("Wysylam pilke predkosc x 2");
			//pileczkaWriter.writeObject(pileczka);
			}
			catch(IOException ex){
				System.out.println("Utracono polaczenie.");
				break;
			}
			finally{
				//System.out.println("Zamykam socket.");
				//listener.close();
			}
		}
		System.out.println("Zamykam socket.");
		try{
		listener.close();
		}
		catch(IOException ex){
			System.out.println("Blad zamkniecia serwera.");
			ex.printStackTrace();
		}
	}

}