package Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Driver {
	
	private String serverAddress = "127.0.0.0";
	private int serverPort = 5004;
	private Document document;
	public Driver() {
		
	}
	
	public void startProgram() throws Exception {
		
		ObjectCreator objectCreator = new ObjectCreator();
    	ArrayList<Object> objects = objectCreator.getSelectedObjects();
    	printObjects(objects);
    	
    	try {
           
            Serializer serializer = new Serializer();
            document = serializer.serialize(objects, "output.xml");
            System.out.println("Serialization complete.");
        } catch (IOException e) {
            e.printStackTrace();
        }
		System.out.println("XML Document created");
    	this.documentOptions();
	}
	
	public void documentOptions() throws Exception{
		
		System.out.println("Options:");
		System.out.println("0 print document to terminal");
		System.out.println("1 send document over server"); 

		Scanner reader = new Scanner(System.in);  
		System.out.println("enter the option selected: ");
		int line = reader.nextInt();
		
		System.out.println(line);

		if(line == 0) {
			
			XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
		    String xmlString = xmlOutputter.outputString(document);
		    System.out.println(xmlString);
		}
		else if(line == 1){
			
			XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
		    String xmlString = xmlOutputter.outputString(document);
		    //System.out.println(xmlString);
		    
			Client client = new Client(this.serverAddress,this.serverPort);
			client.connectToServer();
			client.sendToServer(xmlString);
			client.closeClient();
		}
		else {
			documentOptions();
		}
		//reader.close();
	}
	public static void printObjects(ArrayList<Object> objects) {
	    	
		System.out.println("selected Objects ...");

    	for(Object obj:objects) {
    		
    		
    		System.out.println(obj.getClass().getSimpleName());
    	}
    }
}
