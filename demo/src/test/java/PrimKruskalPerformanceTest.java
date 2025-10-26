import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PrimKruskalPerformanceTest {

    @Test
    void testExecutionTimeAndOperationsNonNegative() {
        Graph graph = new Graph(6);
        graph.addEdge(0, 1, 4);
        graph.addEdge(1, 2, 6);
        graph.addEdge(2, 3, 5);
        graph.addEdge(3, 4, 3);
        graph.addEdge(4, 5, 8);
        graph.addEdge(0, 5, 7);

        MSTResult prim = PrimAlgorithm.runPrim(graph);
        MSTResult kruskal = KruskalAlgorithm.runKruskal(graph);

        assertTrue(prim.execution_time_ms >= 0);
        assertTrue(kruskal.execution_time_ms >= 0);
        assertTrue(prim.operations_count >= 0);
        assertTrue(kruskal.operations_count >= 0);
    }

    @Test
    void testReproducibility() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 4);
        graph.addEdge(1, 2, 2);
        graph.addEdge(2, 3, 3);
        graph.addEdge(3, 4, 1);
        graph.addEdge(0, 4, 5);

        MSTResult run1 = PrimAlgorithm.runPrim(graph);
        MSTResult run2 = PrimAlgorithm.runPrim(graph);

        assertEquals(run1.total_cost, run2.total_cost,
                "Same dataset should produce same MST result");
    }
}