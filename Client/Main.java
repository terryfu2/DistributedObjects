package Client;

import java.util.ArrayList;

public class Main {
	
    public static void main(String[] argv) throws Exception {
    	
    	ObjectCreator objectCreator = new ObjectCreator();
    	ArrayList<Object> objects = objectCreator.getSelectedObjects();
    	printObjects(objects);
    }
    
    public static void printObjects(ArrayList<Object> objects) {
    	
		System.out.println("selected Objects ...");

    	for(Object obj:objects) {
    		
    		
    		System.out.println(obj.getClass().getSimpleName());
    	}
    }
}
