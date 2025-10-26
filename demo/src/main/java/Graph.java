import java.util.ArrayList;
import java.util.List;

public class Graph {
    int vertices;
    List<Edge> edges;

    public Graph(int vertices) {
        this.vertices = vertices;
        edges = new ArrayList<>();
    }

    public void addEdge(int from, int to, int weight) {
        edges.add(new Edge(from, to, weight));
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
