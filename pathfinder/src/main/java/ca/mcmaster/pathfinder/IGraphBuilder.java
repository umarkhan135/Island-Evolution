package ca.mcmaster.pathfinder;

public interface IGraphBuilder {
    IGraphBuilder addNode(int id);
    IGraphBuilder addNodeAttribute(int nodeId, String name, Object value);
    IGraphBuilder addEdge(int id, int sourceId, int destinationId);
    IGraphBuilder addEdgeAttribute(int edgeId, String name, Object value);
    IGraphBuilder addUndirectedEdge(int id, int nodeId1, int nodeId2);
    Graph build();
}
