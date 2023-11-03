package UnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Server.Deserializer;

class DeserializerTest {

	private Deserializer deserializer;

    @BeforeEach
    public void setUp() {
        deserializer = new Deserializer();
    }

    @Test
    public void testDeserializeIntArray() throws Exception {
        String xmlString = "<serialized>" +
                "<object class='[I' id='1' length='3'>" +
                "<value>1</value>" +
                "<value>2</value>" +
                "<value>3</value>" +
                "</object>" +
                "</serialized>";

        ArrayList<Object> objects = deserializer.deserialize(xmlString);
        assertNotNull(objects);
        assertEquals(1, objects.size());
        assertTrue(objects.get(0) instanceof int[]);

        int[] array = (int[]) objects.get(0);
        assertEquals(3, array.length);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
    }

    @Test
    public void testDeserializeArrayList() throws Exception {
        String xmlString = "<serialized>" +
                "<object class='ArrayList' id='1' length='3'>" +
                "<value>10</value>" +
                "<value>7</value>" +
                "<value>3</value>" +
                "</object>" +
                "</serialized>";

        ArrayList<Object> objects = deserializer.deserialize(xmlString);
        assertNotNull(objects);
        assertEquals(1, objects.size());
        assertTrue(objects.get(0) instanceof int[]);

        int[] arrayList = (int[]) objects.get(0);
        assertEquals(3, arrayList.length);
        assertEquals(10, arrayList[0]);
        assertEquals(7, arrayList[1]);
        assertEquals(3, arrayList[2]);
    }

    @Test
    public void testDeserializeClassAArray() throws Exception {
        String xmlString = "<serialized>" +
                "<object class='ClassA[]' id='1'>" +
                "<value>1</value>" +
                "<value>0</value>" +
                "</object>" +
                "</serialized>";

        ArrayList<Object> objects = deserializer.deserialize(xmlString);
        assertNotNull(objects);
        assertEquals(1, objects.size());
        assertTrue(objects.get(0) instanceof ClassA[]);

        ClassA[] array = (ClassA[]) objects.get(0);
        assertEquals(2, array.length);
        assertEquals(1, array[0].getSomeField());
        assertEquals(0, array[1].getSomeField());
    }

}
