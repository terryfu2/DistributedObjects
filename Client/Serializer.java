package Client;
 
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.IdentityHashMap;

public class Serializer {
	
	private IdentityHashMap<Object, Integer> objectIds;
    private int objectIdCounter;
    ArrayList<Object> objects;
	
	public Serializer() {
		System.out.println("Inside Serializer ... ");
		objectIds = new IdentityHashMap<>();
        objectIdCounter = 0;
	}
	
	public Document serialize(ArrayList<Object>  objects, String xmlFileName) throws IOException, IllegalArgumentException, IllegalAccessException {
        
		this.objects = objects;
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

        	xmlOutput.output(document, fileWriter);
        }
        return document;
    }
	
	private void serializeObject(Object obj, Element parentElement) throws IllegalArgumentException, IllegalAccessException {
		int objectId = objectIds.computeIfAbsent(obj, o -> objectIdCounter++);
		String className = obj.getClass().getSimpleName();
		
		
		Element objectElement = new Element("object");
		objectElement.setAttribute("class", className);
		objectElement.setAttribute("id", String.valueOf(objectId));
		parentElement.addContent(objectElement);
		
		if(obj.getClass().getName().contains("[")) {
			
			
			serializeArray(obj,objectElement);
		}
		
		
		Field[] fields = obj.getClass().getDeclaredFields();
		
		for(Field field:fields) {
			Element fieldElement = new Element("field");

			serializeFields(obj,field,fieldElement,parentElement);
			objectElement.addContent(fieldElement);
		}

	}
	
	private void serializeArray(Object obj,Element parentElement) {
		
		parentElement.setAttribute("length", String.valueOf(Array.getLength(obj)));

		for(int i = 0;i<Array.getLength(obj);i++) {
					
			if(Array.get(obj, i) == null) {
				continue;
			}
			
			if(Array.get(obj,i).getClass().toString().contains("java.lang")) {
				Element valueElement = new Element("value");
				valueElement.setText(Array.get(obj, i).toString());
				parentElement.addContent(valueElement);
			}
			else {
				int referencedObjectId = objectIds.computeIfAbsent(Array.get(obj, i), o -> objectIdCounter++);
	            Element referenceElement = new Element("reference");
	            referenceElement.setText(String.valueOf(referencedObjectId));
	            parentElement.addContent(referenceElement);
			}
			
		}
	}

	
	private Element serializeFields(Object obj, Field field, Element fieldElement,Element parentElement) throws IllegalArgumentException, IllegalAccessException {
		
    	field.setAccessible(true);

		String className = obj.getClass().getSimpleName();
		fieldElement.setAttribute("name", field.getName());
		fieldElement.setAttribute("declaringclass", className);
		
		Object fieldValue;
		fieldValue = field.get(obj);
		
		if(fieldValue == null) {
			return fieldElement;
		}
		if (fieldValue.toString().contains("[")) {
			
			//serializeArray(obj,field,fieldElement,parentElement,fieldValue);
			if(fieldValue.getClass().getName().contains("java")==false) {
	            serializeObject(fieldValue, parentElement);

        	}

	    }
		if(field.getType().isPrimitive()) {
			Element valueElement = new Element("value");
			valueElement.setText(fieldValue.toString());
			fieldElement.addContent(valueElement);

		}
		else if(field.getType().toString().contains("String")) {
			Element valueElement = new Element("value");
			valueElement.setText(fieldValue.toString());
			fieldElement.addContent(valueElement);
		}
		else {
            if (fieldValue != null) {
            	if(fieldValue.getClass().getName().contains("java")==false && fieldValue.toString().contains("[") == false) {
                	serializeObject(fieldValue,parentElement);

            	}
                int referencedObjectId = objectIds.computeIfAbsent(fieldValue, o -> objectIdCounter++);
                Element referenceElement = new Element("reference");
                referenceElement.setText(String.valueOf(referencedObjectId));
                fieldElement.addContent(referenceElement);
            }
        }

		return fieldElement;
	}
}




















