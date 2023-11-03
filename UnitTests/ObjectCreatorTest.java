package UnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Client.ObjectCreator;
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
import Client.ExampleClasses.CircularA;

class ObjectCreatorTest {

	private ObjectCreator objectCreator;

    @BeforeEach
    public void setUp() {
        try {
            objectCreator = new ObjectCreator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateClasses() {
        assertTrue(objectCreator.createClasses());
        ArrayList<Class> selectedClasses = objectCreator.getSelectedClasses();
        assertEquals(6, selectedClasses.size());
        assertTrue(selectedClasses.contains(Client.ExampleClasses.DemoAll.class));
        assertTrue(selectedClasses.contains(Client.ExampleClasses.SimpleObject.class));
    }

    @Test
    public void testUserInput() {
        
        ArrayList<Class> selectedClasses = objectCreator.getSelectedClasses();
        ArrayList<Object> selectedObjects = objectCreator.getSelectedObjects();

        assertTrue(selectedClasses.contains(Client.ExampleClasses.DemoAll.class));
        assertTrue(selectedClasses.contains(Client.ExampleClasses.SimpleObject.class));
        assertEquals(2, selectedObjects.size());
    }

 
    @Test
    public void testGetIdentityHashMap() {
        IdentityHashMap<Object, Integer> objectIds = objectCreator.getIdentityHashMap();
        assertNotNull(objectIds);
    }

    @Test
    public void testGetId() {
        int objectIdCounter = objectCreator.getId();
        assertEquals(0, objectIdCounter); 
    }

}
