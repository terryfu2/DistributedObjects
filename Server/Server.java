package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private int serverPort;
	ServerSocket serverSocket;
	Socket clientSocket;
	
	public Server(int serverPort) {
			
		this.serverPort=serverPort;
	}
	
	public boolean startServer() {
		
		try {
			
			serverSocket = new ServerSocket(serverPort);
            System.out.println("Server is waiting for a connection...");
            
            return true;
            
		}catch (IOException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	public boolean connectClient() {
	
		try {
			
			clientSocket = serverSocket.accept();
            System.out.println("Connection established with the client.");
            
            return true;
            
		}catch (IOException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	public boolean closeServer() {
		
		try {
			
			serverSocket.close();

	        return true;
	        
		} catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public boolean recieve() {
		
		try {
            System.out.println("Server is waiting for a message...");

			ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            // Receive data from the client
            Object receivedObject = in.readObject();
            System.out.println("Received: " + receivedObject);

            // Process data or perform any actions

            // Send a response back to the client
            out.writeObject("XML Document Recieved");
			
            return true;
            
		}catch (IOException | ClassNotFoundException e) {
	        e.printStackTrace();
	        return false;
	    } 
	}
	
    public static void main(String[] args) {
    	
    	Server sut = new Server(5010);
    	sut.startServer();
    	sut.connectClient();
    	sut.recieve();
    	sut.closeServer();
    	/*
        try {
            ServerSocket serverSocket = new ServerSocket(5000); // Use any available port number

            System.out.println("Server is waiting for a connection...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connection established with the client.");

            // Create input and output streams
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            // Receive data from the client
            Object receivedObject = in.readObject();
            System.out.println("Received: " + receivedObject);

            // Process data or perform any actions

            // Send a response back to the client
            out.writeObject("Hello from the server!");

            // Close the server
            serverSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    }
}
