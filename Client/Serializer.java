package Client;
 
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import Client.ObjectCreator.Parser;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.ListIterator;
import java.util.Scanner;

public class Serializer {
	
	private IdentityHashMap<Object, Integer> objectIds;
    private int objectIdCounter;
    ArrayList<Object> objects;
    String xmlFileName = "output.xml";
    private boolean change;
	
	public Serializer(IdentityHashMap<Object, Integer> objectIds,int objectIdCounter,boolean change) {
		this.objectIds = objectIds;
        this.objectIdCounter = objectIdCounter;
        this.change = change;
	}
	
	public Document serialize(ArrayList<Object>  objects) throws IOException, IllegalArgumentException, IllegalAccessException {
        
		this.objects = objects;
		Document document = new Document();
        Element rootElement = new Element("serialized");
        document.setRootElement(rootElement);
        
        /*
        for (Object obj: objectIds.keySet()) {
            System.out.println(obj + " " + objectIds.get(obj));
            serializeObject(obj, rootElement);

        }*/
        
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
	
	class Parser{
		
		int intVal;
		boolean boolVal;
		double doubleVal;
		String line;
		Class type;
		Parser(String line, Class type){
			
			this.line = line;
			this.type =type;
			
		}
		
		void parse() {
			
			if(type==int.class) {
				this.intVal = Integer.parseInt(line);
			}
			if(type==boolean.class) {
				
				if(line == "false") {
					this.boolVal = false;

				}
				else if(line == "true"){
					this.boolVal = true;
				}
			}
			if(type == double.class) {
				
				this.doubleVal = Double.parseDouble(line);
			}
		}
	}

	private void serializeObject(Object obj, Element parentElement) throws IllegalArgumentException, IllegalAccessException {
		int objectId = objectIds.computeIfAbsent(obj, o -> objectIdCounter++);
		
		//int objectId = objectIds.get(obj);
		String className = obj.getClass().getSimpleName();
		
		//System.out.println(className.toString());
		Element objectElement = new Element("object");
		//Element objectElement = new Element(className.toString());

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
			
			changePrimitiveField(fieldValue,obj,field);
			
			//valueElement.setText(fieldValue.toString());
			valueElement.setText(field.get(obj).toString());
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
                
        		//int referencedObjectId = objectIds.get(obj);

                Element referenceElement = new Element("reference");
                referenceElement.setText(String.valueOf(referencedObjectId));
                fieldElement.addContent(referenceElement);
            }
        }

		return fieldElement;
	}
	
	public void changePrimitiveField(Object fieldValue, Object obj, Field field) throws IllegalArgumentException, IllegalAccessException {
		
		if(!change) {
			return;
		}
		System.out.println(field.getName() + " from " + field.getDeclaringClass().getSimpleName() + " with value " + field.get(obj));
		System.out.println("what would u like to change it to");
		
		Scanner reader = new Scanner(System.in);  
		String line = reader.next();
				
		Parser parser = new Parser (line,field.getType());
		parser.parse();
		
		if(field.getType()== int.class){
			field.set(obj, parser.intVal);
		}
		
		else if(field.getType() == boolean.class){
			field.set(obj, parser.boolVal);
		}
		else if(field.getType() == double.class){
			field.set(obj, parser.doubleVal);
		}
		System.out.println(field.getName() + " changed to " + field.get(obj));
	}
}




















