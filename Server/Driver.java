package Server;

import java.util.HashMap;
import java.util.List;
import Client.ExampleClasses.ClassA;

public class Driver {

	String document;
	List<Object> objects;
	
	public void startProgram() throws Exception {
		
		this.recieveDocument();
		this.deserialize();
		this.visualize();
	}
	public void recieveDocument() {
		Server sut = new Server(5015);
    	sut.startServer();
    	sut.connectClient();
    	sut.recieve();
    	sut.closeServer();

		this.document = sut.getDocument();
		
		//System.out.println(document);
	}
	public void deserialize() throws Exception {
		
		Deserializer deserializer = new Deserializer();
		objects = deserializer.deserialize(document);
		
		System.out.println(objects);
	}
	public void visualize() throws Exception {
		
		
		for(Object obj:objects) {
			System.out.println(obj);

			Inspector inspector = new Inspector();
			inspector.inspect(obj,false);
		}
		
	
	}
}
