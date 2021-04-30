public interface ICDLList {
    void insert(int value);

    void delete() throws IllegalStateException;

    Node getHead() throws IllegalStateException;

    void rotateForward() throws IllegalStateException;

    void rotateBackward() throws IllegalStateException;

    int size();

    boolean isEmpty();
}
