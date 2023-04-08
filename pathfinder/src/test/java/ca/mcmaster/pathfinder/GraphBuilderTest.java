package ca.mcmaster.pathfinder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphBuilderTest {
    @Test
    void testEmptyGraph() {
        GraphBuilder builder = new GraphBuilder();
        Graph graph = builder.build();
        assertTrue(graph.getNodes().isEmpty());
        assertTrue(graph.getEdges().isEmpty());
    }

    @Test
    void testSingleNode() {
        GraphBuilder builder = new GraphBuilder();
        builder.addNode(1);
        Graph graph = builder.build();
        assertEquals(1, graph.getNodes().size());
        assertNotNull(graph.getNode(1));
    }

    @Test
    void testMultipleNodes() {
        GraphBuilder builder = new GraphBuilder();
        builder.addNode(1).addNode(2).addNode(3);
        Graph graph = builder.build();
        assertEquals(3, graph.getNodes().size());
        assertNotNull(graph.getNode(1));
        assertNotNull(graph.getNode(2));
        assertNotNull(graph.getNode(3));
    }

    @Test
    void testMultipleEdges() {
        GraphBuilder builder = new GraphBuilder();
        builder.addNode(1).addNode(2).addNode(3)
                .addEdge(1, 1, 2)
                .addEdge(2, 2, 3);
        Graph graph = builder.build();
        assertEquals(2, graph.getEdges().size());
        assertNotNull(graph.getEdge(1));
        assertNotNull(graph.getEdge(2));
    }

    @Test
    void testAddingUndirectedEdges() {
        GraphBuilder builder = new GraphBuilder();
        builder.addNode(1).addNode(2).addUndirectedEdge(1, 1, 2);
        Graph graph = builder.build();

        // Check that the graph contains both nodes.
        assertEquals(2, graph.getNodes().size());
        assertNotNull(graph.getNode(1));
        assertNotNull(graph.getNode(2));

        // Check that the graph contains both undirected edges.
        assertNotNull(graph.getEdge(1));
        assertNotNull(graph.getEdge(2));

        // Check that the edges have the correct source and target nodes.
        assertEquals(1, graph.getEdge(1).getSource().getId());
        assertEquals(2, graph.getEdge(1).getTarget().getId());
        assertEquals(2, graph.getEdge(2).getSource().getId());
        assertEquals(1, graph.getEdge(2).getTarget().getId());
    }

    @Test
    void testAddingEdgeWithNonExistentNodes() {
        GraphBuilder builder = new GraphBuilder();
        builder.addNode(1).addEdge(1, 1, 2);
        Graph graph = builder.build();

        // Check that the graph contains only one node with id 1.
        assertEquals(1, graph.getNodes().size());
        assertNotNull(graph.getNode(1));

        // Check that the graph doesn't contain an edge with id 1, as node 2 doesn't exist.
        assertNull(graph.getEdge(1));
    }
}
