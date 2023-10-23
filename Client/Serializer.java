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
		objectIds = new IdentityHashMap<>();
        objectIdCounter = 0;
	}
	
	public void serialize(ArrayList<Object>  objects, String xmlFileName) throws IOException, IllegalArgumentException, IllegalAccessException {
        
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
    }
	
	private void serializeObject(Object obj, Element parentElement) throws IllegalArgumentException, IllegalAccessException {
		int objectId = objectIds.computeIfAbsent(obj, o -> objectIdCounter++);
		String className = obj.getClass().getSimpleName();
		
		Element objectElement = new Element("object");
		objectElement.setAttribute("class", className);
		objectElement.setAttribute("id", String.valueOf(objectId));
		parentElement.addContent(objectElement);
		
		Field[] fields = obj.getClass().getDeclaredFields();
		
		for(Field field:fields) {
			Element fieldElement = new Element("field");

			serializeFields(obj,field,fieldElement,parentElement);
			objectElement.addContent(fieldElement);
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
			System.out.println("adf");
			
	        int arrayLength = Array.getLength(fieldValue);
	        
	        fieldElement.setAttribute("length", String.valueOf(arrayLength));

	        for (int i = 0; i < arrayLength; i++) {
	            Object element = Array.get(fieldValue, i);
	            Element arrayElement = new Element("value");

	            if (element != null) {
	                if (objectIds.containsKey(element)) {
	                    // Reference to another object
	                    Element referenceElement = new Element("reference");
	                    referenceElement.setText(String.valueOf(objectIds.get(element)));
	                    arrayElement.addContent(referenceElement);
	                } else {
	                    // Element is a standalone object
	                	System.out.println(fieldValue.getClass().getName());
	                	System.out.println(fieldValue.getClass().getComponentType());
	                	
	                	//parse pirmitive array
	                	///
	                	
	     
	                	if(fieldValue.getClass().getName().contains("java")==false) {
		                    serializeObject(fieldValue.getClass().getComponentType(), arrayElement);

	                	}
	                }
	            }
	            fieldElement.addContent(arrayElement);
	        }
	    }
		if(field.getType().isPrimitive()) {
			Element valueElement = new Element("value");
			valueElement.setText(fieldValue.toString());
			fieldElement.addContent(valueElement);

		}else {
            if (fieldValue != null) {
            	if(fieldValue.getClass().getName().contains("java")==false) {
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




















