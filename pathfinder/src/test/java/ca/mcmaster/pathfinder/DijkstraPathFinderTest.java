package ca.mcmaster.pathfinder;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class DijkstraPathFinderTest {

    @Test
    public void testFindPath_emptyGraph() {
        Graph graph = new Graph();
        DijkstraPathFinder pathFinder = new DijkstraPathFinder();

        List<IEdge> path = pathFinder.findPath(graph, 1, 2);

        assertTrue(path.isEmpty(), "The path should be empty if the graph is empty");
    }

    @Test
    public void testFindPath_sourceNodeNotFound() {
        Graph graph = new Graph();
        graph.addNode(new Node(1));
        graph.addNode(new Node(2));
        DijkstraPathFinder pathFinder = new DijkstraPathFinder();

        List<IEdge> path = pathFinder.findPath(graph, 3, 2);

        assertTrue(path.isEmpty(), "The path should be empty if the source node is not found in the graph");
    }

    @Test
    public void testFindPath_destinationNodeNotFound() {
        Graph graph = new Graph();
        graph.addNode(new Node(1));
        graph.addNode(new Node(2));
        DijkstraPathFinder pathFinder = new DijkstraPathFinder();

        List<IEdge> path = pathFinder.findPath(graph, 1, 3);

        assertTrue(path.isEmpty(), "The path should be empty if the destination node is not found in the graph");
    }

    @Test
    public void testFindPath_ReturnEmptyList_WhenSourceOrDestinationNodeIsNotInGraph() {
        Graph graph = new GraphBuilder().addNode(1).addNode(2).build();
        DijkstraPathFinder pathFinder = new DijkstraPathFinder();

        List<IEdge> path = pathFinder.findPath(graph, 3, 2);
        assertTrue(path.isEmpty());

        path = pathFinder.findPath(graph, 1, 3);
        assertTrue(path.isEmpty());
    }

    @Test
    public void testFindPath_ReturnCorrectPath_WhenSourceAndDestinationAreConnected() {
        Graph graph = new GraphBuilder().addNode(1).addNode(2).addEdge(1, 1, 2).build();
        DijkstraPathFinder pathFinder = new DijkstraPathFinder();

        List<IEdge> path = pathFinder.findPath(graph, 1, 2);
        assertEquals(1, path.size());
        assertEquals(1, path.get(0).getId());
        assertEquals(1, path.get(0).getSource().getId());
        assertEquals(2, path.get(0).getTarget().getId());
    }

    @Test
    public void testFindPath_ReturnCorrectPath_WhenSourceAndDestinationAreNotImmediatelyConnected() {
        Graph graph = new GraphBuilder().addNode(1).addNode(2).addNode(3).addEdge(1, 1, 2).addEdge(2, 2, 3).build();
        DijkstraPathFinder pathFinder = new DijkstraPathFinder();

        List<IEdge> path = pathFinder.findPath(graph, 1, 3);
        assertEquals(2, path.size());
        assertEquals(1, path.get(0).getId());
        assertEquals(1, path.get(0).getSource().getId());
        assertEquals(2, path.get(0).getTarget().getId());
        assertEquals(2, path.get(1).getId());
        assertEquals(2, path.get(1).getSource().getId());
        assertEquals(3, path.get(1).getTarget().getId());
    }
}
