package ca.mcmaster.pathfinder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Map;

class EdgeTest {
    @Test
    public void testGetId() {
        Edge edge = new Edge(1, null, null);
        assertEquals(1, edge.getId());
    }

    @Test
    public void testGetSource() {
        INode sourceNode = new Node(1);
        Edge edge = new Edge(1, sourceNode, null);
        assertEquals(sourceNode, edge.getSource());
    }

    @Test
    public void testGetTarget() {
        INode targetNode = new Node(2);
        Edge edge = new Edge(1, null, targetNode);
        assertEquals(targetNode, edge.getTarget());
    }

    @Test
    public void testAddAttribute() {
        Edge edge = new Edge(1, null, null);
        edge.addAttribute("test", "value");
        assertEquals("value", edge.getAttribute("test"));
    }

    @Test
    public void testGetAttribute() {
        Edge edge = new Edge(1, null, null);
        edge.addAttribute("test", "value");
        assertEquals("value", edge.getAttribute("test"));
    }

    @Test
    public void testGetAttributes() {
        Edge edge = new Edge(1, null, null);
        edge.addAttribute("test", "value");
        Map<String, Object> attributes = edge.getAttributes();
        assertEquals(1, attributes.size());
        assertEquals("value", attributes.get("test"));
    }
}

