public interface IGraph {
    void insertVertex();

    void deleteVertex(int n);

    void insertEdge(int u, int v);

    void deleteEdge(int u, int v);

    int[][] matrix();

    int size();
}
