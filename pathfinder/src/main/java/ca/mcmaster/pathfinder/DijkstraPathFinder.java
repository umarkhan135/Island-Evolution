package ca.mcmaster.pathfinder;

import java.util.*;

class DijkstraPathFinder implements IPathFinder {
    private final Map<Integer, Double> distances;
    private final Set<Integer> visited;
    private final PriorityQueue<NodeDistance> unvisited;

    public DijkstraPathFinder() {
        this.distances = new HashMap<>();
        this.visited = new HashSet<>();
        this.unvisited = new PriorityQueue<>(Comparator.comparingDouble(NodeDistance::getDistance));
    }

    @Override
    public List<IEdge> findPath(Graph graph, int sourceNodeId, int destinationNodeId) {
        initializeDistances(graph, sourceNodeId);

        while (!unvisited.isEmpty()) {
            NodeDistance currentNodeDistance = unvisited.poll();
            INode currentNode = currentNodeDistance.getNode();
            double currentDistance = currentNodeDistance.getDistance();

            if (currentNode.getId() == destinationNodeId) {
                return reconstructPath(graph, sourceNodeId, destinationNodeId);
            }

            if (visited.contains(currentNode.getId())) {
                continue;
            }

            visited.add(currentNode.getId());

            for (IEdge edge : graph.getEdges()) {
                if (edge.getSource().getId() == currentNode.getId()) {
                    INode neighbor = edge.getTarget();
                    double newDistance = currentDistance + (double) edge.getAttribute("weight");

                    if (newDistance < distances.get(neighbor.getId())) {
                        distances.put(neighbor.getId(), newDistance);
                        neighbor.addAttribute("previous", edge);
                        unvisited.offer(new NodeDistance(neighbor, newDistance));
                    }
                }
            }
        }

        return Collections.emptyList();
    }

    private void initializeDistances(Graph graph, int sourceNodeId) {
        for (INode node : graph.getNodes()) {
            if (node.getId() == sourceNodeId) {
                distances.put(node.getId(), 0.0);
                unvisited.offer(new NodeDistance(node, 0.0));
            } else {
                distances.put(node.getId(), Double.MAX_VALUE);
            }
        }
    }

    private List<IEdge> reconstructPath(Graph graph, int sourceNodeId, int destinationNodeId) {
        List<IEdge> path = new ArrayList<>();
        INode currentNode = graph.getNode(destinationNodeId);

        while (currentNode != null && currentNode.getId() != sourceNodeId) {
            IEdge previousEdge = (IEdge) currentNode.getAttribute("previous");
            if (previousEdge != null) {
                path.add(0, previousEdge);
                currentNode = previousEdge.getSource();
            } else {
                currentNode = null;
            }
        }

        return path;
    }

    private static class NodeDistance {
        private final INode node;
        private final double distance;

        public NodeDistance(INode node, double distance) {
            this.node = node;
            this.distance = distance;
        }

        public INode getNode() {
            return node;
        }

        public double getDistance() {
            return distance;
        }
    }
}