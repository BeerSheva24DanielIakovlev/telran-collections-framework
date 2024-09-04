package telran.util;

public abstract class AbstractMapTest {
Integer[] keys = {1, -1};
Map<Integer, Integer> map;
void setUp() {
   //TODO
}
abstract <T> void runTest(T [] expected, T [] actual);
//TODO tests
}