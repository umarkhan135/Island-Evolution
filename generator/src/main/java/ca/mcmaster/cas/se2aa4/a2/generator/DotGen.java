package ca.mcmaster.cas.se2aa4.a2.generator;

import java.io.IOException;
import java.util.*;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;



public class DotGen {

    public final double width = 500;
    private final double height = 500;
    public final double square_size = 20;

    ArrayList<Vertex> vertices = new ArrayList<>();
    ArrayList<Vertex> verticesWithColors = new ArrayList<>();
    ArrayList<Segment> segments = new ArrayList<>();
    ArrayList<Segment> segmentsWithColors = new ArrayList<>();
    ArrayList<Vertex> centroids = new ArrayList<>();

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
                Property color = Property.newBuilder().setKey("rgb_color").setValue("255,0,0").build();
                centroids.add(Vertex.newBuilder().setX(Math.round( x * 100)/100).setY(Math.round( y * 100)/100).addProperties(color).build());
            }
        }

        // Distribute colors randomly. Vertices are immutable, need to enrich them
        Random bag = new Random();
        for(Vertex v: vertices){
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            String colorCode = red + "," + green + "," + blue;
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
        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllVertices(centroids).addAllSegments(segmentsWithColors).build();

    }


    private Property avgColor(List<Property> prop1, List<Property> prop2) {
    
        String val1 = null;
        String val2 = null;
        for(Property p: prop1) {
            if (p.getKey().equals("rgb_color")) {
                System.out.println(p.getValue());
                val1 = p.getValue();
            }
        }
        for(Property p: prop2) {
            if (p.getKey().equals("rgb_color")) {
                System.out.println(p.getValue());
                val2 = p.getValue();
            }
        }

        String[] raw1 = val1.split(",");
        String[] raw2 = val2.split(",");
        int red = (Integer.parseInt(raw1[0]) + Integer.parseInt(raw2[0]))/2;
        int green = (Integer.parseInt(raw1[1]) + Integer.parseInt(raw2[1]))/2;
        int blue = (Integer.parseInt(raw1[2]) + Integer.parseInt(raw2[2]))/2;
        String colorCode = red + "," + green + "," + blue;
        Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
        return color;

    }

public class newMesh extends DotGen{
    ArrayList<Polygon> polygons = new ArrayList<>();
        public newMesh(){

            int counter = 0;
            int j = 0;
            for (int rows = 0; rows < 23; rows++) {
                if (counter % 2 == 0) {
                    for (int i = counter * 49; i < ((counter + 1) * 49); i += 2) {
                        if (i == (46+(49*counter))){
                            Polygon p = Polygon.newBuilder().addSegmentIdxs(i).addSegmentIdxs(i+2).addSegmentIdxs(i+49).addSegmentIdxs(i+1).build();
                            polygons.add(p);
                        }else if(i == (48+(49*counter))){
                            continue;
                        }else {
                            Polygon p = Polygon.newBuilder().addSegmentIdxs(i).addSegmentIdxs(i + 3).addSegmentIdxs(i + 49).addSegmentIdxs(i + 1).build();
                            polygons.add(p);
                        }
                    }
                } else if (counter % 2 == 1) {
                    for (int i = counter * 49; i < ((counter + 1) * 49); i += 2) {
                        if (i == (46+(49*counter))){
                            Polygon p = Polygon.newBuilder().addSegmentIdxs(i).addSegmentIdxs(i+2).addSegmentIdxs(i+49).addSegmentIdxs(i+1).build();
                            polygons.add(p);
                        }else if(i == (48+(49*counter))){
                            continue;
                        }else{
                            Polygon p = Polygon.newBuilder().addSegmentIdxs(i).addSegmentIdxs(i + 3).addSegmentIdxs(i + 49).addSegmentIdxs(i + 1).build();
                            polygons.add(p);
                        }
                    }
                }
                counter++;
            }

            int otherCounter = 0;
            for (int x = 1127; x < 1173; x += 2){
                Polygon p = Polygon.newBuilder().addSegmentIdxs(x).addSegmentIdxs(x + 3).addSegmentIdxs(x + 49 - otherCounter).addSegmentIdxs(x + 1).build();
                otherCounter++;
                polygons.add(p);
            }

            Polygon p = Polygon.newBuilder().addSegmentIdxs(1173).addSegmentIdxs(1175).addSegmentIdxs(1199).addSegmentIdxs(1174).build();
            polygons.add(p);




        }
    }

}
