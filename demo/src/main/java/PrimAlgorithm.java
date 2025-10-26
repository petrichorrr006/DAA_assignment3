import java.util.*;

public class PrimAlgorithm {
    public static MSTResult runPrim(Graph graph) {
        long start = System.nanoTime();

        int V = graph.vertices;
        List<Edge> result = new ArrayList<>();
        boolean[] inMST = new boolean[V];
        int operations = 0;

        int[] key = new int[V];
        int[] parent = new int[V];
        Arrays.fill(key, Integer.MAX_VALUE);
        key[0] = 0;
        parent[0] = -1;

        for (int count = 0; count < V - 1; count++) {
            int u = minKey(key, inMST);
            inMST[u] = true;

            for (Edge edge : graph.getEdges()) {
                operations++;
                if (edge.from == u || edge.to == u) {
                    int v = (edge.from == u) ? edge.to : edge.from;
                    if (!inMST[v] && edge.weight < key[v]) {
                        parent[v] = u;
                        key[v] = edge.weight;
                    }
                }
            }
        }

        int totalCost = 0;
        for (int i = 1; i < V; i++) {
            for (Edge e : graph.getEdges()) {
                if ((e.from == parent[i] && e.to == i) || (e.to == parent[i] && e.from == i)) {
                    result.add(e);
                    totalCost += e.weight;
                    break;
                }
            }
        }

        long end = System.nanoTime();
        double durationMs = (end - start) / 1_000_000.0;

        return new MSTResult(result, totalCost, operations, durationMs);
    }

    private static int minKey(int[] key, boolean[] inMST) {
        int min = Integer.MAX_VALUE, minIndex = -1;
        for (int v = 0; v < key.length; v++) {
            if (!inMST[v] && key[v] < min) {
                min = key[v];
                minIndex = v;
            }
        }
        return minIndex;
    }
}