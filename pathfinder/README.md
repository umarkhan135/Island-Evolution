# Java Dijkstra Path Finder Library

This Java library provides an implementation of Dijkstra's algorithm for finding the shortest path between two nodes in a graph. It includes support for creating custom graphs, generating random graphs, and finding the shortest path between nodes.

## Author 

Muhammd Umar Khan [khanm417] 

## Rationale 

The main purpose of this library is to provide an easy-to-use, efficient, and extensible implementation of pathfinding algorithms in Java. The initial implementation focuses on Dijkstra's algorithm, but the library is designed to allow the addition of other pathfinding algorithms in the future.

## Features

Easy-to-use graph builder for creating custom graphs
Graph data structure with support for nodes and edges
Random graph generator for creating graphs with random edge weights
Dijkstra's algorithm implementation for finding the shortest path between nodes

## Getting Started
To use this library, simply import the ca.mcmaster.pathfinder package into your Java project.

## Creating a Graph
Use the GraphBuilder class to create a custom graph:

```
GraphBuilder builder = new GraphBuilder();
builder.addNode(1)
       .addNode(2)
       .addEdge(1, 1, 2)
       .addEdgeAttribute(1, "weight", 5.0);
Graph graph = builder.build();
```

## Generating a Random Graph
Use the RandomGraphGenerator class to generate a random graph with a specified number of nodes and edges:

```
RandomGraphGenerator generator = new RandomGraphGenerator();
Graph randomGraph = generator.generateRandomGraph(10, 15); // 10 nodes, 15 edges
```

## Finding the Shortest Path
Use the DijkstraPathFinder class to find the shortest path between two nodes in a graph:

```
DijkstraPathFinder pathFinder = new DijkstraPathFinder();
List<IEdge> shortestPath = pathFinder.findPath(graph, 1, 2); // Path from node 1 to node 2
```

## Extending the Library 

To extend the library by implementing a new algorithm, follow these steps:

  1. Create a new class for your algorithm that implements the IPathFinder interface. This interface has a single method: List<IEdge> findPath(Graph graph, int sourceNodeId, int destinationNodeId)

```
package ca.mcmaster.pathfinder;

import java.util.List;

public class YourAlgorithm implements IPathFinder {
    @Override
    public List<IEdge> findPath(Graph graph, int sourceNodeId, int destinationNodeId) {
        // Implement your algorithm here
    }
}
```

  2. Implement your algorithm in the findPath() method. This method should take a Graph object, source node ID, and destination node ID as input and return a list of IEdge objects representing the shortest path between the source and destination nodes.

  3. In your main application, instantiate your new algorithm class and use it to find the shortest path:

```
YourAlgorithm pathFinder = new YourAlgorithm();
List<IEdge> shortestPath = pathFinder.findPath(graph, 1, 2); // Path from node 1 to node 2
```

Use these steps to easily extend the library. 

## Main 

This library also features a main which creates a random graph based off of a hardcoded set of nodes and edges and picks two edges to find the shortest path between. 
