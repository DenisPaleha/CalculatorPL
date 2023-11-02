import static org.paleha.calculator_pl.constanse.ConstantLibrary.*;

import org.paleha.calculator_pl.constanse.HashMap;
import junit.framework.TestCase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class HashMapTest extends TestCase {

    /**
     * Checks if the requested data exists in the table
     */
    @Test
    public void testHasKey() {
        HashMap hashmap = new HashMap(8);
        hashmap.put("key", "values");
        boolean x = hashmap.hasKey("key");
        assertTrue(x);
    }

    /**
     * Test for storing and retrieving data
     */
    @Test
    public void testPutAndGet() {
        HashMap hashmap = new HashMap(8);
        hashmap.put("key", "value");
        String testValue;
        testValue = hashmap.get("key");
        assertEquals(testValue, "value");
    }

    /**
     * Tests data overwrite if the key exists
     */
    @Test
    public void testRewrite() {
        HashMap hashmap = new HashMap(8);
        hashmap.put("key", "value");
        hashmap.put("key", "newValue");

        String testValue;
        testValue = hashmap.get("key");
        assertEquals(testValue, "newValue");
    }

    /**
     * Tests exception handling when requesting data with a non-existing key
     */
    @Test
    public void testGetIfNoKey() {
        HashMap hashmap = new HashMap(8);
        hashmap.put("key", "value");
        String testValue;
        testValue = hashmap.get("Wrong Key");
        assertNull(testValue); // the get() function returns null
    }

    /**
     * Tests key and data removal
     */
    @Test
    public void testRemoveFromHashmap() {
        HashMap hashmap = new HashMap(8);
        hashmap.put("key", "value");
//        String testMessage;
        try {
            hashmap.removeFromHashmap("key");
        } catch (Exception e) {
//            testMessage = (e.getMessage());
        }
        boolean x = hashmap.hasKey("key");
        assertFalse(x);
    }

    /**
     * Tests exception handling when attempting to remove a non-existing key
     */
    @Test
    public void testRemoveException() {
        HashMap hashmap = new HashMap(8); // Create a table
        hashmap.put("key", "value");
        String testMessage = null;

        try {
            hashmap.removeFromHashmap("Wrong Key");
        } catch (Exception e) {
            testMessage = (e.getMessage());
        }
        assertEquals("You can't delete what doesn't exist...", testMessage);
    }

    /**
     * Tests dynamic array size extension
     */
    @Test
    public void testArrayLengthExtension() {
        HashMap hashmap = new HashMap(1); // Create a table with an initial depth of 1
        hashmap.put("key", "value");
        assertEquals(2, hashmap.getCapacity());
    }

    /**
     * Tests proper data loading
     */
    @Test

    public void testLoadHasMap() {
        HashMap hashmap = new HashMap(8);
        hashmap.loadMainHashMap();
        String result = hashmap.get("oct"); // Take an arbitrary constant from the list
        assertEquals(result, TO_OCTAL);
    }

    /**
     * Checks the number of Key-Value pairs contained in the table
     */
    @Test
    public void testSizeOfDate() {
        HashMap hashmap = new HashMap(8);
        hashmap.put("key1", "value1");
        hashmap.put("key2", "value2");
        hashmap.put("key3", "value3");
        int result = hashmap.size();
        assertEquals(3, result);
    }

    /** Test prints the contents of the hashmap to the screen */
    @Test
    @Disabled
    public void testPrintDataHashMap() {
        HashMap hashmap = new HashMap(8);
        hashmap.loadMainHashMap();
        hashmap.printHashMapKeys(); // Print keys to the screen
        hashmap.printHashMapValues(); // Print values to the screen
    }
}
