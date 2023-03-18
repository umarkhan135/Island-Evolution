package ca.mcmaster.island.shapes;

import java.awt.geom.*;

public class CircleIsland implements ShapeGenerator{

    private Path2D shape = new Path2D.Double();

    public void generateShape(){

        Ellipse2D e = new Ellipse2D.Double(100, 100, 300, 300);
        shape.append(e, false);
        
    }

    public Path2D getShape(){
        return shape;
    }
}
