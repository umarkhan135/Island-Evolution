package ca.mcmaster.island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;


public class MeshSize {

    private Structs.Vertex largestVertex;

    public MeshSize(Structs.Mesh mesh){
        findLargestXYVertex(mesh);
    }

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

    public double getMaxX(){
        return largestVertex.getX();
    }

    public double getMaxY(){
        return largestVertex.getY();
    }

}

