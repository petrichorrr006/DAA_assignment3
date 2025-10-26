import java.util.List;

public class MSTResult {
    public List<Edge> mst_edges;
    public int total_cost;
    public int operations_count;
    public double execution_time_ms;

    public MSTResult(List<Edge> mst_edges, int total_cost, int operations_count, double execution_time_ms) {
        this.mst_edges = mst_edges;
        this.total_cost = total_cost;
        this.operations_count = operations_count;
        this.execution_time_ms = execution_time_ms;
    }
}