package ca.mcmaster.pathfinder;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        int numOfNodes = 10;
        int numOfEdges = 17;

        int sourceNodeId = 5;
        int destinationOfNodeId = 10;

        IRandomGraphGenerator generator = new RandomGraphGenerator();
        Graph graph = generator.generateRandomGraph(numOfNodes, numOfEdges);

        printNodes(graph);
        printEdges(graph);

        IPathFinder pathFinder = new DijkstraPathFinder();
        List<IEdge> shortestPath = pathFinder.findPath(graph, sourceNodeId, destinationOfNodeId);

        System.out.println("Shortest path from node " + sourceNodeId + " to " + destinationOfNodeId + " is: ");

        for (IEdge edge : shortestPath) {
            System.out.println("Edge " + edge.getId() + ": " + edge.getSource().getId() + " -> " + edge.getTarget().getId());
        }
    }

    private static void printNodes(Graph graph) {
        System.out.println("Nodes:");
        for (INode node : graph.getNodes()) {
            System.out.println("Node " + node.getId() + ":");
            for (Map.Entry<String, Object> entry : node.getAttributes().entrySet()) {
                System.out.println("  " + entry.getKey() + " = " + entry.getValue());
            }
        }
    }

    private static void printEdges(Graph graph) {
        System.out.println("Edges:");
        for (IEdge edge : graph.getEdges()) {
            System.out.println("Edge " + edge.getId() + ":");
            System.out.println("  Source: " + edge.getSource().getId());
            System.out.println("  Destination: " + edge.getTarget().getId());
            for (Map.Entry<String, Object> entry : edge.getAttributes().entrySet()) {
                System.out.println("  " + entry.getKey() + " = " + entry.getValue());
            }
        }
    }
}