package ca.mcmaster.cas.se2aa4.a2.generator;

import org.locationtech.jts.geom.*;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;

import java.util.*;

public class VerticesGen{

    public ArrayList<Vertex> createRegularVertices(double width, double height, double square_size){

        ArrayList<Vertex> vertices = new ArrayList<>();

        for (int y = 0; y < height; y += square_size) {
            for (int x = 0; x < width; x += square_size) {
                vertices.add(Vertex.newBuilder().setX(Math.round( x * 100)/100).setY(Math.round( y * 100)/100).build());
            }
        }

        return vertices;

    }

    public ArrayList<Vertex> createIrregularVertices(List<org.locationtech.jts.geom.Polygon> polygons){

        ArrayList<Vertex> vertices = new ArrayList<>();
        PrecisionModel PM = new PrecisionModel();

        for (org.locationtech.jts.geom.Polygon p: polygons){
            Coordinate[] temps = p.getCoordinates();
            for(Coordinate c: temps){
                PM.makePrecise(c);
                Vertex v = Vertex.newBuilder().setX(Math.round(c.x)).setY(Math.round(c.y)).build();
                vertices.add(v);
            }
                vertices.add(Vertex.newBuilder().setX(Math.round(-100)).setY(Math.round(-100)).build());
        }

        return vertices;
    }

    public ArrayList<Vertex> addColourVertices(ArrayList<Vertex> vertices){

        Random bag = new Random();
        ArrayList<Vertex> verticesWithColors = new ArrayList<>();

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

        return verticesWithColors;

    }

}