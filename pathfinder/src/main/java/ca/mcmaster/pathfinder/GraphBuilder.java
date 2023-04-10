package ca.mcmaster.pathfinder;

import java.util.*;

public class GraphBuilder implements IGraphBuilder {
    private final Graph graph;

    public GraphBuilder() {
        this.graph = new Graph();
    }

    public IGraphBuilder addNode(int id) {
        INode node = new Node(id);
        graph.addNode(node);
        return this;
    }

    public IGraphBuilder addNodeAttribute(int nodeId, String name, Object value) {
        getNode(nodeId).ifPresent(node -> node.addAttribute(name, value));
        return this;
    }

    public IGraphBuilder addEdge(int id, int sourceId, int destinationId) {
        getNodes(sourceId, destinationId).ifPresent(nodes -> {
            IEdge edge = new Edge(id, nodes.get(0), nodes.get(1));
            graph.addEdge(edge);
        });
        return this;
    }

    public IGraphBuilder addEdgeAttribute(int edgeId, String name, Object value) {
        getEdge(edgeId).ifPresent(edge -> edge.addAttribute(name, value));
        return this;
    }

    public IGraphBuilder addUndirectedEdge(int id, int nodeId1, int nodeId2) {
        addEdge(id, nodeId1, nodeId2);
        addEdge(id + 1, nodeId2, nodeId1);
        return this;
    }

    public Graph build() {
        return graph;
    }

    private Optional<INode> getNode(int nodeId) {
        if (graph.containsNode(nodeId)) {
            return Optional.of(graph.getNode(nodeId));
        } else {
            return Optional.empty();
        }
    }

    private Optional<IEdge> getEdge(int edgeId) {
        if (graph.containsEdge(edgeId)) {
            return Optional.of(graph.getEdge(edgeId));
        } else {
            return Optional.empty();
        }
    }

    private Optional<List<INode>> getNodes(int sourceId, int destinationId) {
        Optional<INode> sourceNode = getNode(sourceId);
        Optional<INode> destNode = getNode(destinationId);
        if (sourceNode.isPresent() && destNode.isPresent()) {
            List<INode> nodes = new ArrayList<>();
            nodes.add(sourceNode.get());
            nodes.add(destNode.get());
            return Optional.of(nodes);
        } else {
            return Optional.empty();
        }
    }
}