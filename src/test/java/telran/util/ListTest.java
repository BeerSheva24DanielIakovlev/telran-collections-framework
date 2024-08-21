package telran.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public abstract class ListTest extends CollectionTest {
    List<Integer> list;

    @Override
    void setUp() {
        super.setUp();
        list = (List<Integer>) collection;
    }

    @Test
    void getTest() {
        assertEquals(3, list.get(0));
        assertEquals(17, list.get(array.length - 1));
    }

    @Test
    void addAtIndexTest() {
        list.add(2, 50);
        assertEquals(50, list.get(2));
    }

    @Test
    void removeAtIndexTest() {
        int removed = list.remove(2);
        assertEquals(20, removed);
        assertEquals(array.length - 1, list.size());
    }

    @Test
    void indexOutOfBoundsTest() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(array.length));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, 100));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(array.length));
    }

    @Test
    void indexOfTest() {
        assertEquals(2, list.indexOf(20));
        assertEquals(-1, list.indexOf(999));
    }

    @Test
    void lastIndexOfTest() {
        list.add(10);
        assertEquals(4, list.lastIndexOf(10));
    }
}
