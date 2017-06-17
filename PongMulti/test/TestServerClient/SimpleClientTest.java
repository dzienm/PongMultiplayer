package TestServerClient;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class SimpleClientTest {

	private static Socket socket;
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		socket = new Socket("localhost", 8989);

	}

}
