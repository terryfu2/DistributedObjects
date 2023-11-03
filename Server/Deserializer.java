package Server;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.StringReader;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Client.ExampleClasses.ClassA;
import Client.ExampleClasses.ClassB;
import Client.ExampleClasses.ClassD;
import Server.Server;
import Client.ExampleClasses.Person;
import Client.ExampleClasses.DemoAll;


public class Deserializer {
    private final Map<Integer, Object> objectMap = new HashMap<>();
    private int maxObjectId = 0;

    //public List<Object> deserialize(String xmlString) throws Exception {

    public ArrayList<Object> deserialize(String xmlString) throws Exception {
        objectMap.clear();
        maxObjectId = 0;
        ArrayList<Object> objects = new ArrayList<>();;
        
        Document document = new SAXBuilder().build(new StringReader(xmlString));
        Element root = document.getRootElement();
        List<Element> objectElements = root.getChildren("object");

	    System.out.println(xmlString);
	    
        for (Element objectElement : objectElements) {
            int objectId = Integer.parseInt(objectElement.getAttributeValue("id"));
            String className = objectElement.getAttributeValue("class");
            
            //System.out.println(className);
            if(className.contains("[")) {
            	//System.out.println("is a array");
               
                String type = className.substring(0, className.indexOf('[')).trim();
                System.out.println(type);
                
                if(type.equals("int")){
                	
                	int[] obj = deserializeIntArray(objectElement);
                	objects.add(obj);
                }
            	
            	continue;
            }
            Object obj = createObject(className);
           

            List<Element> fieldElements = objectElement.getChildren("field");
            for (Element fieldElement : fieldElements) {
                String fieldName = fieldElement.getAttributeValue("name");
                String fieldDeclaringClass = fieldElement.getAttributeValue("declaringclass");
                Element valueElement = fieldElement.getChild("value");
                Element referenceElement = fieldElement.getChild("reference");
                
                //System.out.println(fieldName);
                
                if (fieldElement.getChildText("value") != null) {
                	
                	//System.out.println("asdf");
                    setFieldValue(obj, fieldName, fieldDeclaringClass, fieldElement.getChildText("value"));
                } else if (referenceElement != null) {
                    int referencedObjectId = Integer.parseInt(referenceElement.getText());
                    setFieldValue(obj, fieldName, fieldDeclaringClass, objectMap.get(referencedObjectId));
                }
            }
            objects.add(obj);
            
            objectMap.put(objectId, obj);
        }

        //return objectMap.values().stream().toList();
        return objects;
    }
    
    private int[] deserializeIntArray(Element objectElement) throws NumberFormatException, NegativeArraySizeException, ClassNotFoundException {
    	
       
        
        String length = objectElement.getAttributeValue("length");
        //System.out.println(length);
        
       
        int[] array = new int[Integer.parseInt(length)];
        	
        List<Element> valueElements = objectElement.getChildren("value");
            
        //System.out.println(valueElements);
        for(int i = 0;i< Integer.parseInt(length);i++) {
        	
        	Element valueElement = valueElements.get(i);
            if (valueElement != null) {

            	Array.set(array, i, Integer.parseInt(valueElement.getText()));
            }
        	
        }
        
        //System.out.println(Arrays.toString(array));
        return array;
    }
    private Object createObject(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName("Client.ExampleClasses." +className);
        return clazz.newInstance();
    }

    private void setFieldValue(Object obj, String fieldName, String declaringClass, Object value) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        Class<?> clazz = obj.getClass();

        if (!clazz.getName().equals("Client.ExampleClasses." +declaringClass)) {
            clazz = Class.forName("Client.ExampleClasses." +declaringClass);
        }

        while (clazz != null) {
            try {
            	//System.out.println("asdf2");

                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);

                if (field.getType() == int.class) {
                	//System.out.println("asdf4");

                    field.set(obj, Integer.parseInt(value.toString()));
                    
                    //System.out.println(field.get(obj));
                } else if (field.getType() == String.class) {
                    field.set(obj, value.toString());
                }else if(field.getType() == boolean.class){
                	if(value.equals("false")) {
            			field.set(obj,false);
                	}
                	else {
            			field.set(obj,true);
                	}
        		}
        		else if(field.getType() == double.class){
        			field.set(obj, Double.parseDouble(value.toString()));
        		}
                return;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
    }
}
