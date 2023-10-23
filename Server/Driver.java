package Server;

public class Driver {

	public static void main(String[] argv) {
		
		
		Server sut = new Server(5015);
    	sut.startServer();
    	sut.connectClient();
    	sut.recieve();
    	sut.closeServer();

		
	}

}
