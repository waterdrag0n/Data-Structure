public interface IHeap {
    public void insert(Integer value);

    public Integer removeMax() throws IllegalStateException;

    public Integer max() throws IllegalStateException;

    public int size();

    public Node getRoot() throws IllegalStateException;

    public boolean isEmpty();
}
