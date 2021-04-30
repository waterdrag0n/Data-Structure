public interface ISet {
    void insert(int value);

    void delete(int value) throws IllegalStateException;

    void union(ISet set);

    void intersection(ISet set);

    void subtraction(ISet set);

    int[] show();
}