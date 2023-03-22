package ca.mcmaster.island.Aquifers;

import java.awt.geom.Path2D;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.island.shapes.CircleIsland;
import ca.mcmaster.island.shapes.ShapeGenerator;

public class CircleAquifier implements AquifersGen {

    @Override
    public boolean makeAquifier(int amount, Path2D shape, Mesh m) {

        Random random = new Random();

        int rand = random.nextInt(1300);

        Structs.Vertex v = m.getVertices(m.getPolygons(rand).getCentroidIdx());

        ShapeGenerator circle = new CircleIsland(v.getX(), v.getY(), 10);

        circle.generateShape();
        circle.getShape();

        if (shape.contains(circle.getShape().getBounds2D()));

        

        return true;
    }
    
}
