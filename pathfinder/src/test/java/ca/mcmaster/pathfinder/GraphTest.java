package ca.mcmaster.pathfinder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class GraphTest {
    @Test
    public void testAddAndGetEdge() {
        Graph graph = new Graph();
        IEdge edge = new Edge(1, new Node(1), new Node(2));
        graph.addEdge(edge);
        assertEquals(edge, graph.getEdge(1));
    }

    @Test
    public void testContainsEdge() {
        Graph graph = new Graph();
        IEdge edge = new Edge(1, new Node(1), new Node(2));
        graph.addEdge(edge);
        assertTrue(graph.containsEdge(1));
        assertFalse(graph.containsEdge(2));
    }
}

