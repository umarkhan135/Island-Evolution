package ca.mcmaster.island.shapes;

import java.awt.geom.*;

public class EllipseIsland implements ShapeGenerator{

    private Path2D shape = new Path2D.Double();

    public void generateShape(){

        Ellipse2D e = new Ellipse2D.Double(150, 75, 200, 350);
        shape.append(e, false);
        
    }

    public Path2D getShape(){
        return shape;
    }

}
