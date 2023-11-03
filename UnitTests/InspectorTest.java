package UnitTests;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Client.ExampleClasses.ClassA;
import Client.ExampleClasses.ClassB;
import Client.ExampleClasses.ClassD;
import Server.Server;
import Client.ExampleClasses.Person;
import Client.ExampleClasses.DemoAll;
import Client.ExampleClasses.SimpleObject;
import Client.ExampleClasses.ObjectArray;
import Client.ExampleClasses.ReferenceObject;
import Client.ExampleClasses.SimpleArrayObject;
import Client.ExampleClasses.CollectionObject;
import Server.Inspector;

class InspectorTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testInspectorClassName() throws Exception {
		
		Inspector sut = new Inspector();
		//ClassA uut = new ClassA();
		Object uut= new ClassA();
		
		//assertEquals(sut.classDescription(uut, false, 0,  uut.getClass()),new String[] {"ClassA","java.lang.Object"});
		assertEquals(sut.className( false, 0,  uut.getClass()),"ClassA");

	}
	
	@Test
	void testInspectorSuperClassName() throws Exception {
		
		Inspector sut = new Inspector();
		//ClassA uut = new ClassA();
		Object uut= new ClassB();
		
		assertEquals(sut.superClassName(false, 0,  uut.getClass()),"ClassC");

	}
	
	@Test
	void testInspectorInferfaces() throws Exception {
		
		Inspector sut = new Inspector();
		//ClassA uut = new ClassA();
		Object uut= new ClassA();
				
		assertEquals(sut.classInterfaces(false, 0,  uut.getClass()),"[interface java.io.Serializable, interface java.lang.Runnable]");

	}
	
	@Test
	void testMethodNames() throws Exception {
		
		Inspector sut = new Inspector();
		//ClassA uut = new ClassA();
		Object uut= new ClassA();
		
    	ArrayList<String> tested = sut.methodNames(false, 0,  uut.getClass());
    	ArrayList<String> expected = new ArrayList<String>();
    	expected.add("run");
    	expected.add("toString");
    	expected.add("getVal");
    	expected.add("setVal");
    	expected.add("printSomething");

    	assertEquals(tested.size(),expected.size());
    	
    	for(String method:expected) {
    		if(tested.contains(method)) {
    			continue;
    		}
    		else {
    			assertEquals(false,true);
    		}
    	}
    	assertEquals(true,true);

	}
	
	@Test
	void testConstructorNames() throws Exception {
		
		Inspector sut = new Inspector();
		//ClassA uut = new ClassA();
		Object uut= new ClassA();
		
    	ArrayList<String> tested = sut.constructorNames(false, 0,  uut.getClass());
    	ArrayList<String> expected = new ArrayList<String>();
    	expected.add("ClassA");
    	expected.add("ClassA");

    	assertEquals(tested.size(),expected.size());
    	
    	for(String constructor:expected) {
    		if(tested.contains(constructor)) {
    			continue;
    		}
    		else {
    			assertEquals(false,true);
    		}
    	}
    	assertEquals(true,true);

	}
	
	@Test
	void testFieldNames() throws Exception {
		
		Inspector sut = new Inspector();
		//ClassA uut = new ClassA();
		Object uut= new ClassA();
		
    	ArrayList<String> tested = sut.fieldNames(uut, false, 0,  uut.getClass());
    	ArrayList<String> expected = new ArrayList<String>();
    	expected.add("val");
    	expected.add("val2");
    	expected.add("val3");

    	assertEquals(tested.size(),expected.size());
    	
    	for(String field:expected) {
    		if(tested.contains(field)) {
    			continue;
    		}
    		else {
    			assertEquals(false,true);
    		}
    	}
    	assertEquals(true,true);

	}
	
	@Test
	void testMethodValues() throws Exception{
		
		Inspector sut = new Inspector();
		//ClassA uut = new ClassA();
		Object uut= new ClassA();
		
   	 	Method[] methods = uut.getClass().getDeclaredMethods();
   	 	
   	 	for(Method method:methods) {
   	 		
   			String actual = sut.methodValues(false,0, uut.getClass(),method,0);
   			
   			if(method.getName() == "run") {
   				assertEquals(actual,"voidpublic[][]");
   			}
   			if(method.getName() == "getVal") {
   				assertEquals(actual,"intpublic[][]");
   			}
   			if(method.getName() == "setVal") {
   				assertEquals(actual,"voidpublic[int][class java.lang.Exception]");
   			}
   			if(method.getName() == "toString") {
   				assertEquals(actual,"java.lang.Stringpublic[][]");
   			}
   			if(method.getName() == "printSomething") {
   				assertEquals(actual,"voidprivate[][]");
   			}
   	 	}
	}
	
	@Test
	void testConstructorValues() throws Exception{
		
		Inspector sut = new Inspector();
		//ClassA uut = new ClassA();
		Object uut= new ClassA();
		
		Constructor<?>[] constructors = uut.getClass().getDeclaredConstructors();
   	 	
		//System.out.println(Arrays.toString(constructors));
   	 	for(Constructor<?> constructor:constructors) {
   	 		
   			String actual = sut.constructorValues(false,0, uut.getClass(),constructor,0);
   			if(actual == "ClassApublic[]") {

   		    	assertEquals(true,true);
   		    	continue;
   			}
   			else if(actual == "ClassApublic[int]") {
   		    	assertEquals(true,true);
   		    	continue;
   			}
   			
   	 	}
	}
	
	@Test
	void testFieldValues() throws Exception{
		
		Inspector sut = new Inspector();
		//ClassA uut = new ClassA();
		Object uut= new ClassA();
		
		Field[] fields = uut.getClass().getDeclaredFields();
   	 	
   	 	for(Field field:fields) {
   	 		
   			String actual = sut.fieldValues(uut,false,0, uut.getClass(),field,0);
   			
   			if(field.getName() == "val") {
   				assertEquals(actual,"valintprivate");
   			}
   			if(field.getName() == "val2") {
   				assertEquals(actual,"val2doubleprivate");
   			}
   			if(field.getName() == "val3") {
   				assertEquals(actual,"val3booleanprivate");
   			}
   			
   	 	}
	}
	

}



















