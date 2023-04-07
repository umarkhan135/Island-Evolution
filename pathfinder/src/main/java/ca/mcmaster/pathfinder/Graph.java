package ca.mcmaster.pathfinder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class Graph {
    private final Map<Integer, INode> nodes;
    private final Map<Integer, IEdge> edges;

    public Graph() {
        this.nodes = new HashMap<>();
        this.edges = new HashMap<>();
    }

    public void addNode(INode node) {
        nodes.put(node.getId(), node);
    }

    public void addEdge(IEdge edge) {
        edges.put(edge.getId(), edge);
    }

    public INode getNode(int id) {
        return nodes.get(id);
    }

    public IEdge getEdge(int id) {
        return edges.get(id);
    }

    public boolean containsNode(int id) {
        return nodes.containsKey(id);
    }

    public boolean containsEdge(int id) {
        return edges.containsKey(id);
    }

    public Collection<INode> getNodes() {
        return nodes.values();
    }

    public Collection<IEdge> getEdges() {
        return edges.values();
    }
}