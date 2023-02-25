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
import org.locationtech.jts.triangulate.quadedge.QuadEdgeSubdivision;


public class DotGen {

    public final double width = 500;
    private final double height = 500;
    public final double square_size = 20;
    Random bag = new Random();
    ArrayList<Vertex> vertices = new ArrayList<>();
    ArrayList<Vertex> verticesWithColors = new ArrayList<>();
    ArrayList<Segment> segments = new ArrayList<>();
    ArrayList<Segment> segmentsWithColors = new ArrayList<>();
    ArrayList<Vertex> centroids = new ArrayList<>();
    ArrayList<Coordinate> coords = new ArrayList<>();
    PrecisionModel PM = new PrecisionModel();
    VoronoiDiagramBuilder VDB = new VoronoiDiagramBuilder();
    public QuadEdgeSubdivision iGenerate(){
        Coordinate temp;
        for (int y = 0; y < height-20; y += square_size) {
            for (int x = 0; x < width-20; x += square_size) {
                temp = new Coordinate(bag.nextInt(x), bag.nextInt(y));
                PM.makePrecise(temp);
                coords.add(temp);
            }
        }
        VDB.setSites(coords);
        return VDB.getSubdivision();
    }
    public Mesh generate() {
//        ArrayList<Vertex> vertices = new ArrayList<>();
//        ArrayList<Vertex> verticesWithColors = new ArrayList<>();
//        ArrayList<Segment> segments = new ArrayList<>();
//        ArrayList<Segment> segmentsWithColors = new ArrayList<>();
//        ArrayList<Vertex> centroids = new ArrayList<>();

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
        
        for(Vertex v: vertices){
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            int alpha = 255;
            String colorCode = red + "," + green + "," + blue + "," + alpha;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Vertex colored = Vertex.newBuilder(v).addProperties(color).build();
            verticesWithColors.add(colored);
        }

        for(Vertex v: vertices){
            if((vertices.indexOf(v)+1)%25 != 0){
                Segment s = Segment.newBuilder().setV1Idx(vertices.indexOf(v)).setV2Idx(vertices.indexOf(v)+1).build();
                segments.add(s);
            }
            if((vertices.indexOf(v)+25) < vertices.size()){
                Segment s = Segment.newBuilder().setV1Idx(vertices.indexOf(v)).setV2Idx(vertices.indexOf(v)+25).build();
                segments.add(s);
            }
        }

        for(Segment s: segments){
            Property color = avgColor(verticesWithColors.get(s.getV1Idx()).getPropertiesList(), verticesWithColors.get(s.getV2Idx()).getPropertiesList());
            Segment colored = Segment.newBuilder(s).addProperties(color).build();
            segmentsWithColors.add(colored);
        }

        NewMesh mesh = new NewMesh(verticesWithColors, centroids, segmentsWithColors, width, square_size);
        System.out.println(mesh.getPolygons());


        return Mesh.newBuilder().addAllVertices(mesh.getVertices()).addAllVertices(mesh.getCentroids()).addAllSegments(mesh.getSegments()).addAllPolygons(mesh.getPolygons()).build();

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
