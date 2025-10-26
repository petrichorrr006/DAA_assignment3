import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class PrimKruskalCorrectnessTest {

    @Test
    void testSameTotalCost() {
        Graph graph = makeSampleGraph();
        MSTResult prim = PrimAlgorithm.runPrim(graph);
        MSTResult kruskal = KruskalAlgorithm.runKruskal(graph);
        assertEquals(prim.total_cost, kruskal.total_cost,
                "Prim and Kruskal should produce the same total MST cost");
    }

    @Test
    void testMSTHasVMinusOneEdges() {
        Graph graph = makeSampleGraph();
        MSTResult prim = PrimAlgorithm.runPrim(graph);
        assertEquals(graph.vertices - 1, prim.mst_edges.size(),
                "MST must contain V-1 edges");
    }

    @Test
    void testMSTAcyclic() {
        Graph graph = makeSampleGraph();
        MSTResult prim = PrimAlgorithm.runPrim(graph);
        assertTrue(isAcyclic(graph.vertices, prim.mst_edges),
                "MST must be acyclic");
    }

    @Test
    void testMSTConnected() {
        Graph graph = makeSampleGraph();
        MSTResult prim = PrimAlgorithm.runPrim(graph);
        assertTrue(isConnected(graph.vertices, prim.mst_edges),
                "MST must connect all vertices");
    }

    @Test
    void testDisconnectedGraphHandled() {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 5);
        MSTResult prim = PrimAlgorithm.runPrim(graph);
        assertTrue(prim.total_cost >= 0,
                "Disconnected graph should not crash (handle gracefully)");
    }

    private Graph makeSampleGraph() {
        Graph g = new Graph(5);
        g.addEdge(0, 1, 4);
        g.addEdge(0, 2, 3);
        g.addEdge(1, 2, 2);
        g.addEdge(1, 3, 5);
        g.addEdge(2, 3, 7);
        g.addEdge(3, 4, 6);
        return g;
    }

    private boolean isAcyclic(int vertices, List<Edge> edges) {
        int[] parent = new int[vertices];
        for (int i = 0; i < vertices; i++) parent[i] = i;
        for (Edge e : edges) {
            int x = find(parent, e.from);
            int y = find(parent, e.to);
            if (x == y) return false;
            union(parent, x, y);
        }
        return true;
    }

    private boolean isConnected(int vertices, List<Edge> edges) {
        Set<Integer> visited = new HashSet<>();
        Map<Integer, List<Integer>> adj = new HashMap<>();
        for (Edge e : edges) {
            adj.computeIfAbsent(e.from, k -> new ArrayList<>()).add(e.to);
            adj.computeIfAbsent(e.to, k -> new ArrayList<>()).add(e.from);
        }
        dfs(0, adj, visited);
        return visited.size() == vertices;
    }

    private void dfs(int node, Map<Integer, List<Integer>> adj, Set<Integer> visited) {
        if (visited.contains(node)) return;
        visited.add(node);
        for (int next : adj.getOrDefault(node, List.of()))
            dfs(next, adj, visited);
    }

    private int find(int[] parent, int i) {
        if (parent[i] != i)
            parent[i] = find(parent, parent[i]);
        return parent[i];
    }

    private void union(int[] parent, int x, int y) {
        parent[find(parent, x)] = find(parent, y);
    }
}
