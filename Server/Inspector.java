package Server;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.ArrayList;

public class Inspector {
    public void inspect(Object obj, boolean recursive) throws Exception {
        inspectObject(obj, recursive, 0);
    }
    
    public void print(String desc, String value,int depth, Class<?> objClass) {
    	
    	String offset = "";
    	if(depth >0) {
    		offset = "******";
    	}
    	System.out.println("("+objClass.getName().toString()+")"+ offset+ desc + ": " + value);
    }
    
    @SuppressWarnings("unused")
	private void inspectObject(Object obj, boolean recursive, int depth) throws Exception{
        System.out.println("Starting Inspection of Item: "+ obj.getClass() + " with " + recursive);
        
    	Class<?> objClass = obj.getClass();
    	
    	if (obj == null) {
            System.out.println("Object is null");
            return;
        }
        if(obj.getClass().getName() == "java.lang.Class") {
        	System.out.println("Object is Java lang");
        	return;
        }
        
    	objClass = isArray(obj,objClass,depth);
    	//obj = objClass.getDeclaredConstructor().newInstance();
    	
    	System.out.println(obj.getClass().getName());
    	
    	if(obj.getClass().getName().contains("[")) {
        	//System.out.println(obj.getClass().getComponentType().getName());
        	if(obj.getClass().getComponentType().getName().equals("int")) {
        		
        		for(int i = 0;i<Array.getLength(obj);i++) {
        			print("------Field " + i +" ",Array.get(obj,i).toString(),depth,objClass);
        		}
        	}
        	if(obj.getClass().getComponentType().getName().equals("Client.ExampleClasses.ClassA")) {
        		
        		for(int i = 0;i<Array.getLength(obj);i++) {
        			
        			if(Array.get(obj,i)==null) {

        				continue;
        			}

        			print("------Field " + i +" ("+ Array.get(obj, i).hashCode()+") ",Array.get(obj,i).toString(),depth,objClass);
        		}
        	}
        	classDescription(recursive,depth,objClass);
        	return;
    	}
    	classDescription(recursive,depth,objClass);
    	
    	//checkSuperClass(objClass,recursive,depth);

    	//methodDescription(recursive,depth,objClass);
    	
    	//constructorDescription(recursive,depth,objClass);
    	//
    	fieldDescription(obj,recursive,depth,objClass);
    	
    }
    
    public void checkSuperClass(Class<?> objClass, boolean recursive, int depth) throws Exception {
    	
    	Class<?> superClass = objClass.getSuperclass();
        Object objSuper = null;
    	if(superClass.getName().contains("java")== false) {
    		
    		Class<?>superClass2 = superClass;
    		if(Modifier.isAbstract( superClass.getModifiers() )) {
    			System.out.println(superClass.getSimpleName() + " is abstract, checking abstact class's superclass");
    			superClass2 = superClass.getSuperclass();
    		}
    		
        	//System.out.println(superClass2.getSuperclass());
        	//System.out.println(superClass);
        	
        	objSuper = superClass2.getDeclaredConstructor().newInstance();
            inspectObject(objSuper, recursive, depth+1);
    	}
    }
    
    public Class<?> isArray(Object obj,Class<?> objClass,int depth) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
    	if(obj.getClass().getName().contains("[")) {
    		
        	System.out.println("Obj is an array");
        	
        	print("------ Array of type",obj.getClass().getComponentType().toString(),depth,objClass);
    		print("------Array Size", Integer.toString(Array.getLength(obj)),depth,objClass);
    		
        	if(obj.getClass().getComponentType().getName().contains("[")) {
            	System.out.println("Obj is an 2d array");

        		print("------ Array of type",obj.getClass().getComponentType().toString(),depth,objClass);
        		print("------Array Size", Integer.toString(Array.getLength(obj)),depth,objClass);
        		
        		objClass = obj.getClass().getComponentType().getComponentType();
            	obj = objClass.getDeclaredConstructor().newInstance();
        	}
        	else {
	        	objClass = obj.getClass().getComponentType();
	        	
	        	//obj = objClass.getDeclaredConstructor().newInstance();
        	//obj = new ClassA();
        	}
        }
    	return objClass;
    }
    
    public String className(boolean recursive, int depth, Class<?> objClass) {
    	
    	String className = objClass.getName();
        //System.out.println("Class: " + className);
        print("Class",className,depth,objClass);
        
        return className;
    }
    
    public String superClassName(boolean recursive, int depth, Class<?> objClass) throws Exception {
    	
    	Class<?> superClass = objClass.getSuperclass();
         //System.out.println("Superclass: " + superClass.getName());
    	if(superClass == null) {
    		return "";
    	}
    	print("Superclass",superClass.getName(),depth,objClass);
    	
        return superClass.getName();
    }
    
    public String classInterfaces(boolean recursive, int depth, Class<?> objClass) throws Exception {
    	
    	Class<?>[] interfaces = objClass.getInterfaces();
        //System.out.println("Interfaces: " + Arrays.toString(interfaces));
   	    print("Interface",Arrays.toString(interfaces),depth,objClass);
   	    
   	    for(Class<?> interfacet:interfaces) {
   	    	
   	    	//System.out.println(Arrays.toString(interfacet.getDeclaredMethods()));
   	    	//Object objInterface = interfacet.getDeclaredConstructor().newInstance();
            //inspectObject(objInterface, recursive, depth+1);
   	    	
   	    	classDescription(recursive,depth+1,interfacet);
   	    	methodDescription(recursive,depth+1,interfacet);
   	    	constructorDescription(recursive,depth+1,interfacet);
   	    	
   	    }
        return Arrays.toString(interfaces);
    }
    
    public void classDescription(boolean recursive, int depth, Class<?> objClass) throws Exception {
    	
    	
       className(recursive,depth,objClass);
        
       //superClassName(recursive,depth,objClass);
        
       //classInterfaces(recursive,depth,objClass);
    }
    
    public ArrayList<String> methodNames(boolean recursive, int depth, Class<?> objClass) {
    	
    	ArrayList<String> output = new ArrayList<String>();
    	
   	 	Method[] methods = objClass.getDeclaredMethods();
		int count = 1;
		print("Num of Methods",Integer.toString(methods.length),depth,objClass);
		
		for (Method method : methods) {
			 
			 methodValues(recursive, depth, objClass,method,count);
			 output.add(method.getName());
			 count +=1;
		}
		
		return output;
    }
    
    public String methodValues(boolean recursive, int depth, Class<?> objClass, Method method,int count) {
    	
    	
    	print("---Method "+count, method.getName(),depth,objClass);
    	print("------Return Type", method.getReturnType().getName(),depth,objClass);
    	print("------Modifiers", Modifier.toString(method.getModifiers()),depth,objClass);
    	
        Class<?>[] paramTypes = method.getParameterTypes();
        print("------Paramter Types", Arrays.toString(paramTypes),depth,objClass);
        
        Class<?>[] exceptionTypes = method.getExceptionTypes();
        print("------Exception Types",Arrays.toString(exceptionTypes),depth,objClass);
        
        String output = method.getReturnType().getName() + Modifier.toString(method.getModifiers()) +Arrays.toString(paramTypes) + Arrays.toString(exceptionTypes);
        return output;
    }
    private void methodDescription(boolean recursive, int depth, Class<?> objClass) {
    
    	methodNames(recursive, depth, objClass);
    	
    }
    
    public ArrayList<String> constructorNames(boolean recursive, int depth, Class<?> objClass) {
    	ArrayList<String> output = new ArrayList<String>();
    	
    	Constructor<?>[] constructors = objClass.getDeclaredConstructors();
		int count = 1;
		print("Num of Constructors",Integer.toString(constructors.length),depth,objClass);
		
		for (Constructor<?> constructor : constructors) {
			 
			 constructorValues(recursive, depth, objClass,constructor,count);
			 output.add(constructor.getName());

			 count +=1;
		}
		
		return output;
    }
    
    public String constructorValues(boolean recursive, int depth, Class<?> objClass, Constructor<?> constructor,int count) {
    	
    	String output = "";
    	print("---Constructor "+count,constructor.getName(),depth,objClass);
    	print("------Modifiers: ",Modifier.toString(constructor.getModifiers()),depth,objClass);
        Class<?>[] paramTypes = constructor.getParameterTypes();
        print("------Parameter Types: ",Arrays.toString(paramTypes),depth,objClass);
        
        output+= constructor.getName() + Modifier.toString(constructor.getModifiers()) + Arrays.toString(constructor.getParameterTypes());
        //System.out.println(output);
        return output;
    }

    
    private void constructorDescription(boolean recursive, int depth, Class<?> objClass) {
    	
    	constructorNames(recursive, depth, objClass);

    }
    
    public ArrayList<String> fieldNames(Object obj, boolean recursive, int depth, Class<?> objClass) throws Exception {
    	ArrayList<String> output = new ArrayList<String>();
    	
    	Field[] fields = objClass.getDeclaredFields();
		int count = 1;
		print("Num of Fields",Integer.toString(fields.length),depth,objClass);
		
		//System.out.println(Arrays.toString(fields));

		for (Field field : fields) {
			 
			 fieldValues(obj,recursive, depth, objClass,field,count);
			 output.add(field.getName());
			 
			 count +=1;
		}
		return output;
    }
    
    public String parseObjectArray(Object fieldValue) {
    	
    	String output = "[";
		
		for(int i = 0;i<Array.getLength(fieldValue);i++) {
			
			if(Array.get(fieldValue, i) == null) {
				continue;
			}
			output += Array.get(fieldValue, i);
			if(i != Array.getLength(fieldValue)-1) {
				output+= ",";
			}
		}
		output += "]";
		return output;
    }
    public String fieldValues(Object obj, boolean recursive, int depth, Class<?> objClass, Field field,int count) throws Exception {
    	
    	field.setAccessible(true);
    	print("---Field "+count, field.getName(),depth,objClass);
    	print("------Type: ",field.getType().getName(),depth,objClass);
    	//print("------Modifiers: ",Modifier.toString(field.getModifiers()),depth,objClass);
        try {
        	Object fieldValue;
        	fieldValue = field.get(obj);
        	
        	//System.out.println(field.getName());
        	
            if (fieldValue != null) {
            	
            	if(field.getName().contains("arrylist")) {

            		//System.out.println("------Field Value is an ArrayList");
            		return "";
            	}
            	if(fieldValue.toString().contains("[")) {
            		
            		print("------Field Value is an Array of type",fieldValue.getClass().getComponentType().toString(),depth,objClass);
            		print("------Array Size", Integer.toString(Array.getLength(fieldValue)),depth,objClass);
            		
            		//Object temp = fieldValue.getClass().getComponentType();
                    //inspectObject(field.getType().getComponentType(), true, depth + 1);

            	}
            	if (recursive && !field.getType().isPrimitive()) {
                
                    System.out.println("------Field Value: (Recursively inspecting)");
                    inspectObject(fieldValue, true, depth + 1);
                } else {
                	if(fieldValue.toString().contains("[")){
                		
                		return "";
                				/*
                		if(!field.getType().isPrimitive()) {
                    		print("------Field Value",parseObjectArray(fieldValue)+ " (" + fieldValue.hashCode()+")",depth,objClass);

                		}else {
                    		print("------Field Value",parseObjectArray(fieldValue),depth,objClass);

                		}*/
                	}
                	else if(!field.getType().isPrimitive()) {
                		print("------Field Value: ",fieldValue.toString() + " (" + fieldValue.hashCode()+")",depth,objClass);
                	}
                	else {
                		print("------Field Value: ",fieldValue.toString(),depth,objClass);

                	}
                }
            } else {
            	print("------Field Value",null,depth,objClass);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        
        String output = field.getName()+field.getType().getName()+Modifier.toString(field.getModifiers());
        return output;
    }
    private void fieldDescription(Object obj, boolean recursive, int depth, Class<?> objClass) throws Exception {
    	
    	fieldNames( obj,recursive, depth, objClass);
    }
    
}