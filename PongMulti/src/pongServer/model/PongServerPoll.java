package pongServer.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pongServer.ServerController;

public class PongServerPoll {

	private ServerSocket serverSocket;
	private Thread serverThread;
	private Socket clientSocket;
	
	 public void startServer(int port, ServerController serverControl) {
	        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);

	        Runnable serverTask = new Runnable() {
	            @Override
	            public void run(){
	                try {
	                    serverSocket = new ServerSocket(port);
	                    System.out.println("Waiting for clients to connect...");
	                    serverControl.setServerStarted(true);
	                    while (true) {
	                    	try{
	                        clientSocket = serverSocket.accept();}
	                    	catch(IOException e){
	                    		System.out.println("Socket closed.");
	                    		break;
	                    	}
	                        clientProcessingPool.submit(new ClientTask(clientSocket));
	                    }
	                } catch (IOException e) {
	                    System.err.println("Unable to process client request");
	                    e.printStackTrace();
	                    serverControl.serverError();
	                }
	            }
	        };
	        serverThread = new Thread(serverTask);
	        serverThread.start();

	    }
	 
	 public void stopServer(ServerController serverControl){
		 try {
			serverSocket.close();
			serverThread.interrupt();
			serverControl.setServerStarted(false);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }

	    private class ClientTask implements Runnable {
	        private final Socket clientSocket;

	        private ClientTask(Socket clientSocket) {
	            this.clientSocket = clientSocket;
	        }

	        @Override
	        public void run() {
	            //System.out.println("Got a client !");

	            // Do whatever required to process the client's request

	            try {
	                clientSocket.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }

}
