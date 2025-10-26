import com.google.gson.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        String inputPath = "demo/src/main/resources/data/input_extra_large.json";
        String outputPath = "demo/src/main/resources/output/output_extra_large.json";

        JsonObject outputRoot = new JsonObject();
        JsonArray resultsArray = new JsonArray();

        try (Reader reader = new FileReader(inputPath)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray graphs = jsonObject.getAsJsonArray("graphs");

            for (JsonElement gEl : graphs) {
                JsonObject g = gEl.getAsJsonObject();
                int id = g.get("id").getAsInt();
                int vertices = g.getAsJsonArray("nodes").size();
                int edges = g.getAsJsonArray("edges").size();

                Graph graph = new Graph(vertices);
                for (JsonElement eEl : g.getAsJsonArray("edges")) {
                    JsonObject e = eEl.getAsJsonObject();
                    int from = parseNode(e.get("from").getAsString());
                    int to = parseNode(e.get("to").getAsString());
                    int w = e.get("weight").getAsInt();
                    graph.addEdge(from, to, w);
                }

                MSTResult prim = PrimAlgorithm.runPrim(graph);
                MSTResult kruskal = KruskalAlgorithm.runKruskal(graph);

                GraphResult graphRes = new GraphResult(id, vertices, edges, prim, kruskal);

                Gson gson = new Gson();
                JsonElement graphJson = gson.toJsonTree(graphRes);
                resultsArray.add(graphJson);
            }

            outputRoot.add("results", resultsArray);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Writer writer = new FileWriter(outputPath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(outputRoot, writer);
            System.out.println("Output written to " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int parseNode(String label) {
        label = label.replaceAll("[^0-9]", "");
        if (!label.isEmpty()) return Integer.parseInt(label) - 1;
        return Character.toUpperCase(label.charAt(0)) - 'A';
    }
}