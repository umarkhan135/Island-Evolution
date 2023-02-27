package ca.mcmaster.cas.se2aa4.a2.generator;

import java.io.IOException;
import java.util.*;


import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

public class NewMesh {
    
    private ArrayList<Vertex> vertices = new ArrayList<>();
    private ArrayList<Vertex> centroids = new ArrayList<>();
    private ArrayList<Segment> segments = new ArrayList<>();
    private ArrayList<Polygon> polygons = new ArrayList<>();

    private double square_size;
    private double width;

    public NewMesh(){
        this.vertices = new ArrayList<>();
        this.centroids = new ArrayList<>();
        this.segments = new ArrayList<>();
        this.polygons = new ArrayList<>();
    }

    public NewMesh(NewMesh m){
        this.vertices = m.getVertices();
        this.centroids = m.getCentroids();
        this.segments = m.getSegments();
        this.polygons = m.getPolygons();
        this.width = m.getWidth();
        this.square_size = m.getSquare_size();
    }

    public NewMesh(ArrayList<Vertex> vertices, ArrayList<Vertex> centroids, ArrayList<Segment> segments, double w, double s_s){
        this.addAllVertices(vertices);
        this.addAllCentroids(centroids);
        this.addAllSegments(segments);
        this.setWidth(w);
        this.setSquare_size(s_s);
        this.createAllPolygons();
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setSquare_size(double square_size) {
        this.square_size = square_size;
    }

    public void addAllVertices(ArrayList<Vertex> vertices){
        this.vertices.clear();
        this.vertices.addAll(vertices);
    }

    public void addAllCentroids(ArrayList<Vertex> centroids){
        this.centroids.clear();
        this.centroids.addAll(centroids);
    }

    public void addAllSegments(ArrayList<Segment> segments){
        this.segments.clear();
        this.segments.addAll(segments);
    }

    public void createAllPolygons(){
        List<List<Integer>> listOfNeighbors = new ArrayList<>();
        int incrementationSize = (int) (width/square_size - 1);

        List<Integer> topLeft = new ArrayList<>();
        topLeft.add(1);
        topLeft.add(Integer.valueOf(incrementationSize));
        topLeft.add(Integer.valueOf(incrementationSize + 1));
        listOfNeighbors.add(topLeft);

        for (int neighborCount = 1; neighborCount < (width/square_size - 2); neighborCount++) {
            List<Integer> newNeighborsIdx = new ArrayList<>();
            newNeighborsIdx.add(neighborCount - 1);
            newNeighborsIdx.add(neighborCount + 1);
            newNeighborsIdx.add(neighborCount + Integer.valueOf(incrementationSize - 1));
            newNeighborsIdx.add(neighborCount + Integer.valueOf(incrementationSize));
            newNeighborsIdx.add(neighborCount + Integer.valueOf(incrementationSize + 1));
            listOfNeighbors.add(newNeighborsIdx);
        }

        List<Integer> topRight = new ArrayList<>();
        topRight.add(Integer.valueOf( (incrementationSize - 2)));
        topRight.add(2 * Integer.valueOf( (incrementationSize - 1)));
        topRight.add(2 * Integer.valueOf( (incrementationSize - 1)) + 1);
        listOfNeighbors.add(topRight);

        for (int neighborCount = (int) incrementationSize; neighborCount < ((incrementationSize - 1)*(incrementationSize)); neighborCount++) {
            List<Integer> newNeighborsIdx = new ArrayList<>();
            if(neighborCount % (width/square_size - 1) == 0){

                newNeighborsIdx.add(neighborCount - Integer.valueOf(incrementationSize));
                newNeighborsIdx.add(neighborCount - Integer.valueOf(incrementationSize - 1));
                newNeighborsIdx.add(neighborCount + 1);
                newNeighborsIdx.add(neighborCount + Integer.valueOf(incrementationSize));
                newNeighborsIdx.add(neighborCount + Integer.valueOf(incrementationSize + 1));
                listOfNeighbors.add(newNeighborsIdx);
            }else if((neighborCount - (incrementationSize - 1)) % (incrementationSize) == 0 ){
                newNeighborsIdx.add(neighborCount - Integer.valueOf(incrementationSize));
                newNeighborsIdx.add(neighborCount - Integer.valueOf(incrementationSize + 1));
                newNeighborsIdx.add(neighborCount - 1);
                newNeighborsIdx.add(neighborCount + Integer.valueOf(incrementationSize - 1));
                newNeighborsIdx.add(neighborCount + Integer.valueOf(incrementationSize));
                listOfNeighbors.add(newNeighborsIdx);
            }else{
                newNeighborsIdx.add(neighborCount - Integer.valueOf(incrementationSize + 1));
                newNeighborsIdx.add(neighborCount - Integer.valueOf(incrementationSize));
                newNeighborsIdx.add(neighborCount - Integer.valueOf(incrementationSize - 1));
                newNeighborsIdx.add(neighborCount - 1);
                newNeighborsIdx.add(neighborCount + 1);
                newNeighborsIdx.add(neighborCount + Integer.valueOf(incrementationSize - 1));
                newNeighborsIdx.add(neighborCount + Integer.valueOf(incrementationSize));
                newNeighborsIdx.add(neighborCount + Integer.valueOf(incrementationSize + 1));
                listOfNeighbors.add(newNeighborsIdx);

            }
        }

        List<Integer> bottomLeft = new ArrayList<>();
        bottomLeft.add((incrementationSize * (incrementationSize - 2)));
        bottomLeft.add(((incrementationSize) * (incrementationSize - 2)) + 1);
        bottomLeft.add(Integer.valueOf((incrementationSize * (incrementationSize - 1)) + 1));
        listOfNeighbors.add(bottomLeft);

        for (int neighborCount = ((incrementationSize * (incrementationSize - 1)) + 1); neighborCount < ((incrementationSize * incrementationSize)-1); neighborCount++) {
            List<Integer> newNeighborsIdx = new ArrayList<>();
            newNeighborsIdx.add(neighborCount - 1);
            newNeighborsIdx.add(neighborCount + 1);
            newNeighborsIdx.add(neighborCount - Integer.valueOf(incrementationSize + 1));
            newNeighborsIdx.add(neighborCount - Integer.valueOf(incrementationSize));
            newNeighborsIdx.add(neighborCount - Integer.valueOf(incrementationSize - 1));
            listOfNeighbors.add(newNeighborsIdx);
        }

        List<Integer> bottomRight = new ArrayList<>();
        bottomRight.add((incrementationSize * (incrementationSize - 1)) - 2);
        bottomRight.add((incrementationSize * (incrementationSize - 1)) - 1);
        bottomRight.add(Integer.valueOf((incrementationSize * (incrementationSize)) - 2));
        listOfNeighbors.add(bottomRight);

        //System.out.println(listOfNeighbors);



        int neighbourCount = 0;
        int centroidCount = 0;
        int counter = 0;
        int j = 0;
        for (int rows = 0; rows < 23; rows++) {
            if (counter % 2 == 0) {
                for (int i = counter * 49; i < ((counter + 1) * 49); i += 2) {
                    if (i == (46+(49*counter))){
                        Polygon p = Polygon.newBuilder().addSegmentIdxs(i).addSegmentIdxs(i+2).addSegmentIdxs(i+49).addSegmentIdxs(i+1).setCentroidIdx(centroidCount).addAllNeighborIdxs(listOfNeighbors.get(neighbourCount)).build();
                        polygons.add(p);
                        centroidCount++;
                        neighbourCount++;
                    }else if(i == (48+(49*counter))){
                        continue;
                    }else {
                        Polygon p = Polygon.newBuilder().addSegmentIdxs(i).addSegmentIdxs(i + 3).addSegmentIdxs(i + 49).addSegmentIdxs(i + 1).setCentroidIdx(centroidCount).addAllNeighborIdxs(listOfNeighbors.get(neighbourCount)).build();
                        polygons.add(p);
                        centroidCount++;
                        neighbourCount++;
                    }
                }
            } else if (counter % 2 == 1) {
                for (int i = counter * 49; i < ((counter + 1) * 49); i += 2) {
                    if (i == (46+(49*counter))){
                        Polygon p = Polygon.newBuilder().addSegmentIdxs(i).addSegmentIdxs(i+2).addSegmentIdxs(i+49).addSegmentIdxs(i+1).setCentroidIdx(centroidCount).addAllNeighborIdxs(listOfNeighbors.get(neighbourCount)).build();
                        polygons.add(p);
                        centroidCount++;
                        neighbourCount++;
                    }else if(i == (48+(49*counter))){
                        continue;
                    }else{
                        Polygon p = Polygon.newBuilder().addSegmentIdxs(i).addSegmentIdxs(i + 3).addSegmentIdxs(i + 49).addSegmentIdxs(i + 1).setCentroidIdx(centroidCount).addAllNeighborIdxs(listOfNeighbors.get(neighbourCount)).build();
                        polygons.add(p);
                        centroidCount++;
                        neighbourCount++;
                    }
                }
            }
            counter++;
        }

        int otherCounter = 0;
        int centroidCounter2 = 552;
        for (int x = 1127; x < 1173; x += 2){
            Polygon p = Polygon.newBuilder().addSegmentIdxs(x).addSegmentIdxs(x + 3).addSegmentIdxs(x + 49 - otherCounter).addSegmentIdxs(x + 1).setCentroidIdx(centroidCounter2).addAllNeighborIdxs(listOfNeighbors.get(otherCounter)).build();
            polygons.add(p);
            otherCounter++;
            centroidCounter2++;
        }

        Polygon p = Polygon.newBuilder().addSegmentIdxs(1173).addSegmentIdxs(1175).addSegmentIdxs(1199).addSegmentIdxs(1174).setCentroidIdx(575).addAllNeighborIdxs(listOfNeighbors.get(575)).build();
        polygons.add(p);

        for (Polygon poly : polygons){
            Integer [] b = {3,4,5,6};
            ArrayList<Integer[]> bb = new ArrayList<>();
            bb.add(b);
            poly.newBuilder().addAllNeighborIdxs(List.of(b)).build();
            //System.out.println(poly.getNeighborIdxs(0));
        }

    }
    public ArrayList<Polygon> getPolygons() {
        return new ArrayList<Polygon>(this.polygons);
    }

    public ArrayList<Segment> getSegments() {
        return new ArrayList<Segment>(this.segments);
    }

    public ArrayList<Vertex> getVertices() {
        return new ArrayList<Vertex>(this.vertices);
    }

    public ArrayList<Vertex> getCentroids() {
        return new ArrayList<Vertex>(this.centroids);
    }

    public double getSquare_size() {
        double x = square_size;
        return x;
    }

    public double getWidth() {
        double x = width;
        return x;
    }
}    

