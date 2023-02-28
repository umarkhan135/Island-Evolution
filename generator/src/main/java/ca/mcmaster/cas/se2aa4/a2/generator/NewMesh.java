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
        this.polygons.clear();
        PolygonsGen polyGen = new PolygonsGen();
        this.polygons = polyGen.createRegularPolygons(width, width, square_size);
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

