package Client;
 
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IdentityHashMap;

import Client.ExampleClasses.Person;

public class Serializer {
	
	private IdentityHashMap<Object, Integer> objectIds;
    private int objectIdCounter;
	
	public Serializer() {
		objectIds = new IdentityHashMap<>();
        objectIdCounter = 0;
	}
	public void serialize(ArrayList<Object>  objects, String xmlFileName) throws IOException {
        
		Document document = new Document();
        Element rootElement = new Element("serialized");
        document.setRootElement(rootElement);
        
        
        for(Object obj:objects) {
        	
            serializeObject(obj, rootElement);

        }
        
        // Serialize the document to an XML file
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        try (FileWriter fileWriter = new FileWriter(xmlFileName)) {
        	System.out.println("asdf");
            xmlOutput.output(document, fileWriter);
        }
    }
	  private void serializeObject(Object obj, Element parentElement) {
	        int objectId = objectIds.computeIfAbsent(obj, o -> objectIdCounter++);
	        String className = obj.getClass().getSimpleName();

	        Element objectElement = new Element("object");
	        objectElement.setAttribute("class", className);
	        objectElement.setAttribute("id", String.valueOf(objectId));
	        parentElement.addContent(objectElement);

	        // Serialize fields
	        // For simplicity, assume that fields are only primitive types or references to other objects
	        // You may need to handle other types and custom serialization logic
	        // Also, handle arrays differently
	        // ...

	        // Add more logic here to serialize object fields

	        // Example of serializing a field as a primitive
	        Element fieldElement = new Element("field");
	        fieldElement.setAttribute("name", "age");
	        fieldElement.setAttribute("declaringclass", className);
	        Element valueElement = new Element("value");
	        valueElement.setText("30");
	        fieldElement.addContent(valueElement);
	        objectElement.addContent(fieldElement);
	    }
	
}
