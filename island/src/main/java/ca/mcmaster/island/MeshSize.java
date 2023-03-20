package ca.mcmaster.island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;


public class MeshSize {

    private Structs.Vertex largestVertex;


    public Structs.Vertex findLargestXYVertex(Structs.Mesh mesh) {
        largestVertex = mesh.getVertices(0);
        double maxDistance = 0;
        distance dis = new distance();

        for (Structs.Vertex vertex : mesh.getVerticesList()) {
            double currentDistance = dis.centerDistance(vertex, 0, 0);
            if (currentDistance > maxDistance) {
                maxDistance = currentDistance;
                largestVertex = vertex;
            }
        }

        return largestVertex;
    }

}
