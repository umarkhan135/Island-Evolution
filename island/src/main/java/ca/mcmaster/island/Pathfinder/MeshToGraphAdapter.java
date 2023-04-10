package ca.mcmaster.island.Pathfinder;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.pathfinder.*;

import java.util.List;

public class MeshToGraphAdapter {
    private final Structs.Mesh mesh;
    private final Graph graph;

    public MeshToGraphAdapter(Structs.Mesh mesh) {
        this.mesh = mesh;
        this.graph = meshToGraph();
    }

    private Graph meshToGraph() {
        GraphBuilder graphBuilder = new GraphBuilder();

        // Add centroids as nodes with a "centroidId" attribute and a "neighbors" attribute
        for (int i = 0; i < mesh.getPolygonsCount(); i++) {
            Structs.Vertex centroid = mesh.getVertices(mesh.getPolygons(i).getCentroidIdx());
            int nodeId = i;

            graphBuilder.addNode(nodeId);
            graphBuilder.addNodeAttribute(nodeId, "centroid", centroid);
            graphBuilder.addNodeAttribute(nodeId, "neighbors", mesh.getPolygons(i).getNeighborIdxsList());
        }

        // Build the graph so far to get the nodes and iterate over them
        Graph tempGraph = graphBuilder.build();
        for (INode node : tempGraph.getNodes()) {
            List<Integer> neighbors = (List<Integer>) tempGraph.getNode(node.getId()).getAttribute("neighbors");

            for (int neighborId : neighbors) {
                int edgeId = node.getId() * 1000 + neighborId;
                if (!tempGraph.containsEdge(edgeId)) {
                    graphBuilder.addEdge(edgeId, node.getId(), neighborId);
                    graphBuilder.addEdgeAttribute(edgeId, "weight", 1);
                }
            }
        }

        return graphBuilder.build();
    }

    public Graph getGraph() {
        return graph;
    }
}
