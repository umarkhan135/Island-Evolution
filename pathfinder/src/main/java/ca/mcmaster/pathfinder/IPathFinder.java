package ca.mcmaster.pathfinder;

import java.util.List;

public interface IPathFinder {
    List<IEdge> findPath(Graph graph, int sourceNodeId, int destinationNodeId);
}
