package ca.mcmaster.pathfinder;

interface IGraphBuilder {
    IGraphBuilder addNode(int id);
    IGraphBuilder addNodeAttribute(int nodeId, String name, Object value);
    IGraphBuilder addEdge(int id, int sourceId, int destinationId);
    IGraphBuilder addEdgeAttribute(int edgeId, String name, Object value);
    Graph build();
}
