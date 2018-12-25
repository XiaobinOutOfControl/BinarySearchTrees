public interface BinarySearchTreeInterface<K extends Comparable<K>> {
    boolean insert(K key);
    boolean delete(K key);
    boolean remove(K key);
    boolean contains(K key);
    K keyBefore(K key);
    K keyAfter(K key);
    void visualize();
}