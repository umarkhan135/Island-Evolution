package ca.mcmaster.pathfinder;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomGraphGenerator implements IRandomGraphGenerator {
    public Graph generateRandomGraph(int numNodes, int numEdges) {
        GraphBuilder builder = new GraphBuilder();
        Random rand = new Random();
        Set<String> addedEdges = new HashSet<>();

        for (int i = 1; i <= numNodes; i++) {
            builder.addNode(i);
            builder.addNodeAttribute(i, "name", Character.toString((char) ('A' + i - 1)));
        }

        // First create edges between immediate neighbors
        int immediateEdges = Math.min(numNodes - 1, numEdges);
        for (int i = 1; i <= immediateEdges; i++) {
            int sourceId = i;
            int destinationId = i + 1;
            String edgeKey = sourceId + "," + destinationId;
            builder.addEdge(i, sourceId, destinationId);
            builder.addEdgeAttribute(i, "weight", rand.nextDouble() * 10);
            addedEdges.add(edgeKey);
        }

        // Create remaining random edges
        for (int i = immediateEdges + 1; i <= numEdges; i++) {
            int sourceId = rand.nextInt(numNodes) + 1;
            int destinationId = rand.nextInt(numNodes) + 1;
            String edgeKey = sourceId + "," + destinationId;
            if (sourceId != destinationId && !addedEdges.contains(edgeKey)) {
                builder.addEdge(i, sourceId, destinationId);
                builder.addEdgeAttribute(i, "weight", rand.nextDouble() * 10);
                addedEdges.add(edgeKey);
            } else {
                i--;
            }
        }

        return builder.build();
    }
}
