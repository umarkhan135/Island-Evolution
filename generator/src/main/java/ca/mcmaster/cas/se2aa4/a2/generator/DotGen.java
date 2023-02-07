package ca.mcmaster.cas.se2aa4.a2.generator;

import java.io.IOException;
import java.util.*;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;



public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;

    public Mesh generate() {
        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Segment> line = new ArrayList<>();
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

                line.add(Segment.newBuilder().setV1Idx(testing).setV2Idx(testing + 1).build());
                line.add(Segment.newBuilder().setV1Idx(testing).setV2Idx(testing + 2).build());

                if (x + square_size == width){
                    line.add(Segment.newBuilder().setV1Idx(testing + 1).setV2Idx(testing + 3).build());
                }

                if (y + square_size == height){
                    line.add(Segment.newBuilder().setV1Idx(testing + 2).setV2Idx(testing + 3).build());
                }




                testing += 4;
            }
        }




        // Distribute colors randomly. Vertices are immutable, need to enrich them
        ArrayList<Vertex> verticesWithColors = new ArrayList<>();
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



        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(line).build();
    }

}
