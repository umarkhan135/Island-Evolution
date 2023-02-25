package ca.mcmaster.cas.se2aa4.a2.generator;

import java.io.IOException;
import java.util.*;


import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.triangulate.quadedge.QuadEdge;
import org.locationtech.jts.triangulate.quadedge.QuadEdgeSubdivision;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.algorithm.Centroid;


public class DotGen {

    public final double width = 500;
    private final double height = 500;
    public final double square_size = 20;

    Random bag = new Random();
    ArrayList<QuadEdge> edges = new ArrayList<>();
    ArrayList<Vertex> vertices = new ArrayList<>();
    ArrayList<Vertex> verticesWithColors = new ArrayList<>();
    ArrayList<Segment> segments = new ArrayList<>();
    ArrayList<Segment> segmentsWithColors = new ArrayList<>();
    ArrayList<Vertex> centroids = new ArrayList<>();
    ArrayList<Coordinate> coords = new ArrayList<>();

    PrecisionModel PM = new PrecisionModel();
    VoronoiDiagramBuilder VDB = new VoronoiDiagramBuilder();

    public Mesh iGenerate(){

        Coordinate temp;

        for (int y = 0; y < height-20; y += square_size) {
            for (int x = 0; x < width-20; x += square_size) {
                temp = new Coordinate(bag.nextDouble(width), bag.nextDouble(height));
                PM.makePrecise(temp);
                coords.add(temp);
            }
        }

        VDB.setSites(coords);
        QuadEdgeSubdivision compModel = VDB.getSubdivision();
        edges = (ArrayList<QuadEdge>) compModel.getEdges();

        for (QuadEdge e: edges){
            Vertex v1 = Vertex.newBuilder().setX(Math.round(e.orig().getX() * 100)/100).setY(Math.round(e.orig().getY() * 100)/100).build();
            Vertex v2 = Vertex.newBuilder().setX(Math.round(e.dest().getX() * 100)/100).setY(Math.round(e.dest().getY() * 100)/100).build();
            
            vertices.add(v1);
            vertices.add(v2);
        }

        this.addColourVertices();
        this.createSegmentsPairs();
        this.addColourSegments();

        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(segmentsWithColors).build();

    }

    public Mesh generate() {
        // Generate vertices
        for (int y = 0; y < height; y += square_size) {
            for (int x = 0; x < width; x += square_size) {
                vertices.add(Vertex.newBuilder().setX(Math.round( x * 100)/100).setY(Math.round( y * 100)/100).build());
            }
        }
        for (int y = 10; y < height-10; y += square_size) {
            for (int x = 10; x < width-10; x += square_size) {
                Property color = Property.newBuilder().setKey("rgb_color").setValue("0,0,0,127").build();
                centroids.add(Vertex.newBuilder().setX(Math.round( x * 100)/100).setY(Math.round( y * 100)/100).addProperties(color).build());
            }
        }

        // Distribute colors randomly. Vertices are immutable, need to enrich them
        this.addColourVertices();

        this.createSegmentsSquare();

        this.addColourSegments();

        NewMesh mesh = new NewMesh(verticesWithColors, centroids, segmentsWithColors, width, square_size);
        //System.out.println(mesh.getPolygons());


        return Mesh.newBuilder().addAllVertices(mesh.getVertices()).addAllVertices(mesh.getCentroids()).addAllSegments(mesh.getSegments()).addAllPolygons(mesh.getPolygons()).build();

    }

    private void addColourVertices(){
        for(Vertex v: this.vertices){
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            int alpha = 255;
            String colorCode = red + "," + green + "," + blue + "," + alpha;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Vertex colored = Vertex.newBuilder(v).addProperties(color).build();
            this.verticesWithColors.add(colored);
        }
    }

    private void createSegmentsSquare(){
        for(Vertex v: this.vertices){
            if((this.vertices.indexOf(v)+1)%25 != 0){
                Segment s = Segment.newBuilder().setV1Idx(this.vertices.indexOf(v)).setV2Idx(this.vertices.indexOf(v)+1).build();
                this.segments.add(s);
            }
            if((this.vertices.indexOf(v)+25) < this.vertices.size()){
                Segment s = Segment.newBuilder().setV1Idx(this.vertices.indexOf(v)).setV2Idx(this.vertices.indexOf(v)+25).build();
                this.segments.add(s);
            }
        }
    }

    private void createSegmentsPairs(){
        for(int i = 0; i < this.vertices.size(); i = i + 2){
            Segment s = Segment.newBuilder().setV1Idx(i).setV2Idx(i+1).build();
            this.segments.add(s);
        }
    }

    private void addColourSegments(){
        for(Segment s: this.segments){
            Property color = avgColor(this.verticesWithColors.get(s.getV1Idx()).getPropertiesList(), this.verticesWithColors.get(s.getV2Idx()).getPropertiesList());
            Segment colored = Segment.newBuilder(s).addProperties(color).build();
            this.segmentsWithColors.add(colored);
        }
    }

    private Property avgColor(List<Property> prop1, List<Property> prop2) {
    
        String val1 = null;
        String val2 = null;
        for(Property p: prop1) {
            if (p.getKey().equals("rgb_color")) {
                //System.out.println(p.getValue());
                val1 = p.getValue();
            }
        }
        for(Property p: prop2) {
            if (p.getKey().equals("rgb_color")) {
                //System.out.println(p.getValue());
                val2 = p.getValue();
            }
        }

        String[] raw1 = val1.split(",");
        String[] raw2 = val2.split(",");
        int red = (Integer.parseInt(raw1[0]) + Integer.parseInt(raw2[0]))/2;
        int green = (Integer.parseInt(raw1[1]) + Integer.parseInt(raw2[1]))/2;
        int blue = (Integer.parseInt(raw1[2]) + Integer.parseInt(raw2[2]))/2;
        int alpha = (Integer.parseInt(raw1[3]) + Integer.parseInt(raw2[3]))/2;
        String colorCode = red + "," + green + "," + blue + "," + alpha;
        Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
        return color;
    }
}
