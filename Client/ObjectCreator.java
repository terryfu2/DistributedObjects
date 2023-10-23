package Client;

import java.util.ArrayList;
import java.util.Scanner;

import Client.ExampleClasses.ClassA;
import Client.ExampleClasses.ClassB;
import Client.ExampleClasses.ClassC;
import Client.ExampleClasses.ClassD;
import Server.Server;
import Client.ExampleClasses.Person;

public class ObjectCreator {
	
	private ArrayList<Class> classes = new ArrayList<>();;
	private ArrayList<Class> selectedClasses = new ArrayList<>();;
	private ArrayList<Object> selectedObjects = new ArrayList<>();;
	
	
	public ObjectCreator() throws Exception {
		
		System.out.println("Inside Object Creator: ");
		this.createClasses();
		this.printClasses(classes);
		this.userInput();
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
		reader.close();
		
		for (int i = 0; i < line.length(); i++) {
			
			char curr = line.charAt(i);
			
			if(curr == '0') {
				ClassA classA = new ClassA();
				selectedClasses.add(classA.getClass());
				selectedObjects.add(classA);
			}
			else if(curr == '1') {
				ClassB classB = new ClassB();
				selectedClasses.add(classB.getClass());
				selectedObjects.add(classB);
			}
			else if(curr == '2') {
				ClassD classD = new ClassD();
				selectedClasses.add(classD.getClass());
				selectedObjects.add(classD);
			}
			else if(curr == '3') {
				Client client = new Client("asdf",0);
				selectedClasses.add(client.getClass());
				selectedObjects.add(client);
			}
			else if(curr == '4') {
				Server server = new Server(0);
				selectedClasses.add(server.getClass());
				selectedObjects.add(server);

			}
			else if(curr == '5') {
				Person person = new Person("asdf",0);
				selectedClasses.add(person.getClass());
				selectedObjects.add(person);

			}
	    }
		
	}
	public boolean createClasses() throws Exception {
		
		ClassA classA = new ClassA();
		ClassB classB = new ClassB();
		ClassD classD = new ClassD();
		Client client = new Client("asdf",0);
		Server server = new Server(0);
		Person person = new Person("asdf",5);
		
		classes.add(classA.getClass());
		classes.add(classB.getClass());
		classes.add(classD.getClass());
		classes.add(client.getClass());
		classes.add(server.getClass());
		classes.add(person.getClass());

		return true;
	}
	
	public void printClasses(ArrayList<Class> list) {
		
		System.out.println("classes avaliable ...");

		int count = 0;
		for(Class curr:list) {
			
			System.out.println(count + " "+ curr.getName().toString());
			count ++;
		}

	}
	
	
}
