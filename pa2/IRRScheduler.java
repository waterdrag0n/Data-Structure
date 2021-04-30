public interface IRRScheduler {
    void insert(int id);

    void done();

    void timeflow(int n);

    void changeDirection();

    int currentJob() throws IllegalStateException;
}
