import java.util.List;

public interface ISCC {
    boolean path(int u, int v);

    boolean connectivity();

    List<List<Integer>> findSCC();
}
