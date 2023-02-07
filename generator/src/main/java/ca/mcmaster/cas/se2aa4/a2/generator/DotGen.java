package ca.mcmaster.cas.se2aa4.a2.generator;

import java.io.IOException;
import java.util.*;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;



public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;

    public Mesh generate() {
        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Vertex> verticesWithColors = new ArrayList<>();
        ArrayList<Segment> segments = new ArrayList<>();
        ArrayList<Segment> segmentsWithColors = new ArrayList<>();


        // Create all the vertices
        int testing = 0;
        int counter = 0;

        for (int x = 0; x < width; x += square_size) {
            for (int y = 0; y < height; y += square_size) {
                vertices.add(counter, Vertex.newBuilder().setX((double) x).setY((double) y).build());
                counter++;
                vertices.add(counter, Vertex.newBuilder().setX((double) x + square_size).setY((double) y).build());
                counter++;
                vertices.add(counter, Vertex.newBuilder().setX((double) x).setY((double) y + square_size).build());
                counter++;
                vertices.add(counter, Vertex.newBuilder().setX((double) x + square_size).setY((double) y + square_size).build());
                counter++;

                segments.add(Segment.newBuilder().setV1Idx(testing).setV2Idx(testing + 1).build());
                segments.add(Segment.newBuilder().setV1Idx(testing).setV2Idx(testing + 2).build());

                if (x + square_size == width){
                    segments.add(Segment.newBuilder().setV1Idx(testing + 1).setV2Idx(testing + 3).build());
                }

                if (y + square_size == height){
                    segments.add(Segment.newBuilder().setV1Idx(testing + 2).setV2Idx(testing + 3).build());
                }
                testing += 4;
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
        for(Segment s: segments){
            Property color = avgColor(verticesWithColors.get(s.getV1Idx()).getPropertiesList(), verticesWithColors.get(s.getV2Idx()).getPropertiesList());
            Segment colored = Segment.newBuilder(s).addProperties(color).build();
            segmentsWithColors.add(colored);
        }
        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(segmentsWithColors).build();

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

}
