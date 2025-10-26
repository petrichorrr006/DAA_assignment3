import java.util.HashMap;
import java.util.Map;

public class GraphResult {
    public int graph_id;
    public Map<String, Integer> input_stats = new HashMap<>();
    public MSTResult prim;
    public MSTResult kruskal;

    public GraphResult(int id, int vertices, int edges, MSTResult prim, MSTResult kruskal) {
        this.graph_id = id;
        this.input_stats.put("vertices", vertices);
        this.input_stats.put("edges", edges);
        this.prim = prim;
        this.kruskal = kruskal;
    }
}