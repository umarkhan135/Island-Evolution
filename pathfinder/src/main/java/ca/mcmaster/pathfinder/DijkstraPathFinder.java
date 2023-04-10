package ca.mcmaster.pathfinder;

import java.util.*;

public class DijkstraPathFinder implements IPathFinder {
    private final Map<Integer, Double> distances;
    private final Map<Integer, IEdge> previousEdges;

    public DijkstraPathFinder() {
        distances = new HashMap<>();
        previousEdges = new HashMap<>();
    }

    @Override
    public List<IEdge> findPath(Graph graph, int sourceNodeId, int destinationNodeId) {
        INode sourceNode = graph.getNode(sourceNodeId);
        INode destinationNode = graph.getNode(destinationNodeId);

        if (sourceNode == null || destinationNode == null) {
            return Collections.emptyList();
        }

        initializeDistances(graph.getNodes(), sourceNode);

        PriorityQueue<INode> queue = new PriorityQueue<>(Comparator.comparingDouble(node -> distances.get(node.getId())));
        queue.add(sourceNode);

        while (!queue.isEmpty()) {
            INode currentNode = queue.poll();

            if (currentNode.getId() == destinationNodeId) {
                break;
            }

            for (IEdge edge : getNeighborEdges(graph, currentNode)) {
                INode neighbor = edge.getTarget();
                double currentDistance = distances.get(currentNode.getId()) + getEdgeWeight(edge);

                if (currentDistance < distances.get(neighbor.getId())) {
                    distances.put(neighbor.getId(), currentDistance);
                    previousEdges.put(neighbor.getId(), edge);
                    queue.add(neighbor);
                }
            }
        }

        return reconstructPath(sourceNodeId, destinationNodeId);
    }

    private void initializeDistances(Collection<INode> nodes, INode sourceNode) {
        for (INode node : nodes) {
            distances.put(node.getId(), Double.MAX_VALUE);
        }
        distances.put(sourceNode.getId(), 0.0);
    }

    private List<IEdge> getNeighborEdges(Graph graph, INode node) {
        List<IEdge> neighborEdges = new ArrayList<>();
        for (IEdge edge : graph.getEdges()) {
            if (edge.getSource().getId() == node.getId()) {
                neighborEdges.add(edge);
            } else if (edge.getTarget().getId() == node.getId()) {
                neighborEdges.add(new Edge(edge.getId(), edge.getTarget(), edge.getSource()));
            }
        }
        return neighborEdges;
    }

    private double getEdgeWeight(IEdge edge) {
        Object weight = edge.getAttribute("weight");
        if (weight instanceof Number) {
            return ((Number) weight).doubleValue();
        } else {
            return 1.0;
        }
    }

    private List<IEdge> reconstructPath(int sourceNodeId, int destinationNodeId) {
        List<IEdge> path = new LinkedList<>();
        IEdge currentEdge = previousEdges.get(destinationNodeId);

        while (currentEdge != null && currentEdge.getSource().getId() != sourceNodeId) {
            path.add(0, currentEdge);
            currentEdge = previousEdges.get(currentEdge.getSource().getId());
        }

        if (currentEdge != null) {
            path.add(0, currentEdge);
        }

        return path;
    }
}
