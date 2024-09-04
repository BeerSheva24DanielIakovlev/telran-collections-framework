package telran.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractMapTest {
    Integer[] keys = {1, -1, 2, 3, 4};
    Integer[] values = {10, 20, 30, 40, 50};
    Map<Integer, Integer> map;

    @BeforeEach
    void setUp() {
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], values[i]);
        }
    }

    @Test
    void getTest() {
        for (int i = 0; i < keys.length; i++) {
            assertEquals(values[i], map.get(keys[i]));
        }
        assertNull(map.get(100)); 
    }

    @Test
    void putTest() {
        Integer oldValue = map.put(1, 100);
        assertEquals((Integer) 10, oldValue);
        assertEquals((Integer) 100, map.get(1));
    }

    @Test
    void containsKeyTest() {
        for (Integer key : keys) {
            assertTrue(map.containsKey(key));
        }
        assertFalse(map.containsKey(100)); 
    }

    @Test
    void containsValueTest() {
        for (Integer value : values) {
            assertTrue(map.containsValue(value));
        }
        assertFalse(map.containsValue(999)); 
    }

    @Test
    void keySetTest() {
        Set<Integer> keySet = map.keySet();
        assertEquals(keys.length, keySet.size());
        for (Integer key : keys) {
            assertTrue(keySet.contains(key));
        }
    }

    @Test
    void valuesTest() {
        Collection<Integer> valuesCollection = map.values();
        assertEquals(values.length, valuesCollection.size());
        for (Integer value : values) {
            assertTrue(valuesCollection.contains(value));
        }
    }

    @Test
    void testSize() {
        assertEquals(keys.length, map.size());
    }

    @Test
    void isEmptyTest() {
        assertFalse(map.isEmpty());
        map = new HashMap<>();
        assertTrue(map.isEmpty());
    }

    abstract <T> void runTest(T[] expected, T[] actual);
}
