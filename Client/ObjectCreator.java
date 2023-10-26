package Client;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import Client.ExampleClasses.ClassA;
import Client.ExampleClasses.ClassB;
import Client.ExampleClasses.ClassD;
import Server.Server;
import Client.ExampleClasses.Person;

public class ObjectCreator {
	
	private ArrayList<Class> classes = new ArrayList<>();;
	private ArrayList<Class> selectedClasses = new ArrayList<>();;
	private ArrayList<Object> selectedObjects = new ArrayList<>();;
	private ArrayList<String> avaliableClasses = new ArrayList<>();
	private ArrayList<Object> avaliableObjects = new ArrayList<>();;

	
	public ObjectCreator() throws Exception {
		
		System.out.println("Inside Object Creator: ");
		this.createClasses();
		this.printClasses(avaliableObjects);
		this.userInput();
		
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

	public ArrayList<Class> getSelectedClasses(){
		
		return this.selectedClasses;
	}
	
	public ArrayList<Object> getSelectedObjects(){
		
		return this.selectedObjects;
	}
	
	public void userInput() throws Exception {
		
		Scanner reader = new Scanner(System.in);  
		System.out.println("enter the numbers for the objects that wish to be created, with no spaces");
		String line = reader.next();
		//reader.close();
		
		int count  = 0;
		for(Object obj:avaliableObjects) {
			
			if(line.contains(Integer.toString(count))) {
				selectedObjects.add(obj);
				selectedClasses.add(obj.getClass());
			}
			count++;
		}
		
	}
	
	public void changeFields() throws IllegalArgumentException, IllegalAccessException {
		
		for(Object obj:selectedObjects) {
			
			Field[] fields = obj.getClass().getDeclaredFields();
			//System.out.println(Arrays.toString(fields));
			
			for(Field field:fields) {
				
				field.setAccessible(true);
				
				//System.out.println(field.getType());

				if(field.getType().isPrimitive()) {
					
					System.out.println(field.getName() + " from " + field.getDeclaringClass().getSimpleName() + " is a " + field.getType() + " with value " + field.get(obj));
					System.out.println("what would u like to change it to");
					
					Scanner reader = new Scanner(System.in);  
					String line = reader.next();
					
					//System.out.println(line);
					
					Parser parser = new Parser (line,field.getType());
					parser.parse();
					
					if(field.getType()== int.class){
						//System.out.println(parser.intVal);
						field.set(obj, parser.intVal);
					}
					
					else if(field.getType() == boolean.class){
						//System.out.println(parser.intVal);
						field.set(obj, parser.boolVal);
					}
					else if(field.getType() == double.class){
						//System.out.println(parser.intVal);
						field.set(obj, parser.doubleVal);
					}
					System.out.println(field.getName() + " changed to " + field.get(obj));
					
				}
				
			}
			
		}
	}
	
	
	
	public boolean createClasses() throws Exception {
		
		avaliableClasses.add("ClassA");
		avaliableClasses.add("ClassB");
		avaliableClasses.add("ClassD");
		avaliableClasses.add("Client");
		avaliableClasses.add("Server");
		avaliableClasses.add("Person");

		
		for(String name: avaliableClasses) {
			
			Class tempClass = Class.forName("Client.ExampleClasses." +name);
			Object tempObj = tempClass.newInstance();
			classes.add(tempClass.getClass());
			avaliableObjects.add(tempObj);
		}
		return true;
	}
	
	public void printClasses(ArrayList<Object> list) {
		
		System.out.println("objects avaliable ...");

		int count = 0;
		for(Object curr:list) {
			
			System.out.println(count + " "+ curr.getClass().getName());
			count ++;
		}

	}
	
	
}
