package SocketTests;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.animation.AnimationTimer;
import pongClient.model.PongBall;

public class AnimationClientServerControllerTest {

	static Socket socket;
	static GameAnimationTimer animator;
	
	static ObjectOutputStream objectWriter;
	static ObjectInputStream objectReader;
	
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException {
		
		animator = new GameAnimationTimer();
		socket = new Socket("localHost", 9898);
		System.out.println("Polaczone");
		
		objectWriter = new ObjectOutputStream(socket.getOutputStream());
		objectReader = new ObjectInputStream(socket.getInputStream());
		
		animator.start();
				
		
		Thread.sleep(10000);
		//socket.close();
		}
	

	static class GameAnimationTimer extends AnimationTimer {

		@Override
		public void handle(long currentNanoTime) {

			PongBall pileczka = new PongBall();
			pileczka.setVelocity(1, 1);
			
			System.out.println("Wysylam pileczke predkosc: Vx = " + pileczka.getVelocityX() + "Vy = " + pileczka.getVelocityY());
			try {
				objectWriter.writeObject(pileczka);
				pileczka = (PongBall) objectReader.readObject();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
			

	}

	
}
