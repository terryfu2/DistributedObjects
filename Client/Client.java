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
		
		this.serverAddress=serverAddress;
		this.serverPort=serverPort;
		
		try {
	        System.out.println("connected");

			this.socket = new Socket(serverAddress, serverPort);
	        System.out.println("connected");
	        //return true;
	        
		} catch (IOException e) {
	        e.printStackTrace();
	        //return false;
	    }
	}
	
	public boolean connectToServer() {
		
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
    	Client sut = new Client("127.0.0.1",5014);
    	sut.connectToServer();
    	sut.sendToServer("testing");
    	sut.closeClient();
    }	
    	/*
        String serverAddress = "127.0.0.1"; // Replace with the IP address of the server
        int serverPort = 5000; // Use the same port as in the Server

        try {
            System.out.println("connected");

            Socket socket = new Socket(serverAddress, serverPort);
            System.out.println("connected");

            // Create input and output streams
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Send data to the server
            out.writeObject("Hello from the client!");

            // Receive a response from the server
            Object response = in.readObject();
            System.out.println("Server Response: " + response);

            // Close the client socket
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/
}
