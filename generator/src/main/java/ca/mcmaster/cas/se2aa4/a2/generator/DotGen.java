
package ca.mcmaster.cas.se2aa4.a2.generator;

import java.io.IOException;
import java.util.*;


import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;
import org.locationtech.jts.algorithm.Centroid;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.triangulate.quadedge.QuadEdge;
import org.locationtech.jts.triangulate.quadedge.QuadEdgeSubdivision;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;



public class DotGen {


    private final double width = 500;
    private final double height = 500;
    private final double square_size = 20;

    Random bag = new Random();
    ArrayList<QuadEdge> edges = new ArrayList<>();
    ArrayList<Vertex> vertices = new ArrayList<>();
    ArrayList<Vertex> verticesWithColors = new ArrayList<>();
    ArrayList<Segment> segments = new ArrayList<>();
    ArrayList<Segment> segmentsWithColors = new ArrayList<>();
    ArrayList<Vertex> centroids = new ArrayList<>();
    
    
    
    

    public Mesh iGenerate(){
        ArrayList<Coordinate> coords = new ArrayList<>();
        GeometryFactory Geo = new GeometryFactory();
        PrecisionModel PM = new PrecisionModel();
        VoronoiDiagramBuilder VDB = new VoronoiDiagramBuilder();
        DelaunayTriangulationBuilder DTB = new DelaunayTriangulationBuilder();
        Coordinate temp;
        List<org.locationtech.jts.geom.Polygon> polygons = new ArrayList<>();
        ArrayList<Coordinate> polygonv = new ArrayList<>();
        Map<Coordinate, ArrayList<Coordinate>> neighbors= new HashMap<>();

        for (int y = 0; y < (height-20)*(width-20); y += square_size*square_size) {
            temp = new Coordinate(bag.nextDouble(width), bag.nextDouble(height));
            PM.makePrecise(temp);
            coords.add(temp);
        }

        Centroid centroid;
        for (int i = 0; i<15 ;i++){
            VDB = new VoronoiDiagramBuilder();
            VDB.setSites(coords);
            polygons = VDB.getSubdivision().getVoronoiCellPolygons(Geo);
            coords.clear();
            VDB.setSites(coords);
            for (org.locationtech.jts.geom.Polygon p: polygons){
                centroid = new Centroid(p);
                temp = centroid.getCentroid();
                PM.makePrecise(temp);
                coords.add(temp);
            }
        }

        DTB.setSites(coords);
        Collection<QuadEdge> triangles = DTB.getSubdivision().getEdges();
        for ( Coordinate c: coords){
            ArrayList<Coordinate> close = new ArrayList<>();
            for(QuadEdge e: triangles){      
                if(c.equals(e.orig().getCoordinate())){
                    close.add(e.dest().getCoordinate());
                }    
            }
            neighbors.put(c, close);
        }


        for (Coordinate c: coords){
            Vertex v = Vertex.newBuilder().setX(Math.round(c.x)).setY(Math.round(c.y)).build();
            centroids.add(v);
        }

        for (org.locationtech.jts.geom.Polygon p: polygons){
            Coordinate[] temps = p.getCoordinates();
            for(Coordinate c: temps){
                PM.makePrecise(c);
                Vertex v = Vertex.newBuilder().setX(Math.round(c.x)).setY(Math.round(c.y)).build();
                vertices.add(v);
            }
                vertices.add(Vertex.newBuilder().setX(Math.round(-100)).setY(Math.round(-100)).build());
        }
        this.addColourVertices();
        this.createSegmentsPairs();
        this.addColourSegments();

        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllVertices(centroids).addAllSegments(segmentsWithColors).build();

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
        for(int i = 0; i < this.vertices.size(); i = i + 1){
            if(!(vertices.get(i).equals(Vertex.newBuilder().setX(Math.round(-100)).setY(Math.round(-100)).build()))&&!(vertices.get(i+1).equals(Vertex.newBuilder().setX(Math.round(-100)).setY(Math.round(-100)).build()))){
                Segment s = Segment.newBuilder().setV1Idx(i).setV2Idx(i+1).build();
                this.segments.add(s);
            }
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
