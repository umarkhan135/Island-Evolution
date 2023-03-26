package ca.mcmaster.island.shapes;

import java.awt.geom.*;

public interface ShapeGenerator {
    public Path2D getShape();
    public void generateShape();
    
    //public void generateShape(double width, double height);
}
