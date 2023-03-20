package ca.mcmaster.island.shapes;

import java.awt.geom.Path2D;
import java.util.Random;
import java.lang.Math;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.island.MeshSize;
import ca.mcmaster.island.functions.*;

public class RandomIsland implements ShapeGenerator{
    
    private Path2D.Double shape = new Path2D.Double();
    private Random rand = new Random();
    private double max_x;
    private double max_y;
    private double center_x;
    private double center_y;
    private double radius;
    

    public RandomIsland(Structs.Mesh m){
        MeshSize size = new MeshSize(m);
        max_x = size.getMaxX();
        max_y = size.getMaxY();
        center_x = max_x/2;
        center_y = max_y/2;
        if(max_x < max_y){
            radius = (max_x/5) * 1.5;
        }else{
            radius = (max_y/5) * 1.5;
        }
    }
    
    
    public void generateShape() {

        double[] coefficients = {1, 0.5, 0.2};
        RadialFunction radialFunction = new RadialFunction(coefficients);

        for(int i = 0; i < 2 * Math.PI; i += 0.1){
            System.out.println(Math.PI);
            double radius  = radialFunction.evaluate(i);
            double x_comp = center_x + (radius * Math.cos(i));
            double y_comp = center_y + (radius * Math.sin(i));
            if(i == 0){
                shape.moveTo(x_comp, y_comp);
            }else{
                shape.lineTo(x_comp, y_comp);
            }
        }
        shape.closePath();
    }
    
    public Path2D getShape(){
        return shape;
    }
}