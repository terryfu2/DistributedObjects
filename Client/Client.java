package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
	
	private String serverAddress;
	private int serverPort;
	Socket socket;
	
	public Client(String serverAddress, int serverPort) {
		
		System.out.println("Inside Client ...");

		this.serverAddress=serverAddress;
		this.serverPort=serverPort;
		
		
	}
	
	public boolean connectToServer() {
		
		try {

			this.socket = new Socket(serverAddress, serverPort);
	        System.out.println("connected to server socket");
	        //return true;
	        
		} catch (IOException e) {
	        e.printStackTrace();
	        //return false;
	    }
		return true;
	}
	
	public boolean sendToServer(String data) {
		
		try {
			
			ObjectOutputStream out = new ObjectOutputStream(this.socket.getOutputStream());
	        ObjectInputStream in = new ObjectInputStream(this.socket.getInputStream());
	        
	        out.writeObject(data);
	        
            Object response = in.readObject();
            System.out.println("Server Response: " + response);

	        return true;
	        
		} catch (IOException | ClassNotFoundException e) {
	        e.printStackTrace();
	        return false;
	    }
		
	}
	
	public boolean closeClient() {
		
		try {
			
			this.socket.close();

	        return true;
	        
		} catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
    public static void main(String[] args) {
    	
    	//terrymacbook 192.168.0.192
    	Client sut = new Client("127.0.0.1",5010);
    	sut.connectToServer();
    	sut.sendToServer("testing");
    	sut.closeClient();
    }	
}
