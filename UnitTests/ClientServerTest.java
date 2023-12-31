package UnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Client.Client;
import Server.Server;

class ClientServerTest {
	
	@Test
	void testConnections() {
		Server sut = new Server(5012);
		assertEquals(sut.startServer(),true);
		
		Client cut = new Client("127.0.0.1",5012);
		
		
		assertEquals(cut.connectToServer(),true);
		//assertEquals(sut.connectClient(),true);

		sut.closeServer();
		cut.closeClient();
	}
	
	@Test
	void testSendingandRecieving() {
		
		Server sut = new Server(5011);
		
		assertEquals(sut.startServer(),true);
		Client cut = new Client("127.0.0.1",5011);
		
		
		
		assertEquals(cut.connectToServer(),true);
		
		assertEquals(sut.connectClient(),true);
		

		//cut.sendToServer("testing");

		//sut.recieve();

		
		sut.closeServer();
		cut.closeClient();
	}

}
