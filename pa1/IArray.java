public interface IArray {
    void insert(int value);

    void delete(int value) throws IllegalStateException;

    int search(int value) throws IllegalStateException;

    void sort();

    void unsort();

    int atIndex(int index) throws IndexOutOfBoundsException;

    int size();

    boolean isSorted();

    boolean isEmpty();
}