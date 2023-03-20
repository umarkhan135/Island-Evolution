package ca.mcmaster.island.shapes;

import java.awt.geom.*;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.island.MeshSize;

public class EllipseIsland implements ShapeGenerator{

    private Path2D shape = new Path2D.Double();
    private double max_x;
    private double max_y;
    private double center_x;
    private double center_y;
    private double radius;
    

    public EllipseIsland(Structs.Mesh m){
        MeshSize size = new MeshSize(m);
        max_x = size.getMaxX();
        max_y = size.getMaxY();
        center_x = max_x/2;
        center_y = max_y/2;
        if(max_x < max_y){
            radius = (max_x/5);
        }else{
            radius = (max_y/5);
        }

    }

    public void generateShape(){

        Ellipse2D e = new Ellipse2D.Double(center_x - radius, center_y - (radius*2), radius*2, radius*4);
        shape.append(e, false);
        
    }

    public Path2D getShape(){
        return shape;
    }

}
