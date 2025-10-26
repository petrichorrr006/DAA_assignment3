import java.util.*;

public class KruskalAlgorithm {
    static class Subset { int parent, rank; }

    public static MSTResult runKruskal(Graph graph) {
        long start = System.nanoTime();

        List<Edge> result = new ArrayList<>();
        int V = graph.vertices;
        int operations = 0;

        Collections.sort(graph.edges);
        Subset[] subsets = new Subset[V];
        for (int i = 0; i < V; i++) {
            subsets[i] = new Subset();
            subsets[i].parent = i;
            subsets[i].rank = 0;
        }

        int edgeCount = 0;
        int i = 0;
        while (edgeCount < V - 1 && i < graph.edges.size()) {
            Edge nextEdge = graph.edges.get(i++);
            int x = find(subsets, nextEdge.from);
            int y = find(subsets, nextEdge.to);
            operations++;

            if (x != y) {
                result.add(nextEdge);
                union(subsets, x, y);
                edgeCount++;
            }
        }

        int totalCost = result.stream().mapToInt(e -> e.weight).sum();
        long end = System.nanoTime();
        double durationMs = (end - start) / 1_000_000.0;

        return new MSTResult(result, totalCost, operations, durationMs);
    }

    private static int find(Subset[] subsets, int i) {
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);
        return subsets[i].parent;
    }

    private static void union(Subset[] subsets, int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);
        if (subsets[xroot].rank < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank > subsets[yroot].rank)
            subsets[yroot].parent = xroot;
        else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }
}