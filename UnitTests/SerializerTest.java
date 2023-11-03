package UnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Client.Serializer;

class SerializerTest {

	private Serializer serializer;

    @BeforeEach
    public void setUp() {
        IdentityHashMap<Object, Integer> objectIds = new IdentityHashMap<>();
        serializer = new Serializer(objectIds, 1, false);
    }

    @Test
    public void testSerializeArrayList() throws IOException, IllegalAccessException {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);

        Document document = serializer.serialize(arrayList);

       
        Element rootElement = document.getRootElement();
        Element objectElement = rootElement.getChild("object");
        assertEquals("ArrayList-Integer", objectElement.getAttributeValue("class"));
        assertTrue(objectElement.getAttributeValue("id").matches("\\d+"));
        int length = Integer.parseInt(objectElement.getAttributeValue("length"));
        assertEquals(arrayList.size(), length);
    }

    @Test
    public void testSerializeObject() throws IOException, IllegalAccessException {
        CollectionObject collectionObject = new CollectionObject();
        collectionObject.setItems(new ArrayList<>());

        Document document = serializer.serialize(new ArrayList<>(Collections.singletonList(collectionObject)));

        Element rootElement = document.getRootElement();
        Element objectElement = rootElement.getChild("object");
    }


}
