package Client;

import java.io.IOException;
import java.util.ArrayList;

import Client.ExampleClasses.Person;

public class Main {
	
    public static void main(String[] argv) throws Exception {
    	
    	ObjectCreator objectCreator = new ObjectCreator();
    	ArrayList<Object> objects = objectCreator.getSelectedObjects();
    	//printObjects(objects);
    	
    	
    	try {
           
            Serializer serializer = new Serializer();
            serializer.serialize(objects, "person.xml");
            System.out.println("Serialization complete.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void printObjects(ArrayList<Object> objects) {
    	
		System.out.println("selected Objects ...");

    	for(Object obj:objects) {
    		
    		
    		System.out.println(obj.getClass().getSimpleName());
    	}
    }
}
