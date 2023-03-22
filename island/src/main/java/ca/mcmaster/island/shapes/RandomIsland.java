package ca.mcmaster.island.shapes;

import java.awt.geom.Path2D;
import java.util.Random;

import org.locationtech.jts.awt.PointShapeFactory.X;

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
            radius = (max_x/5) * 2;
        }else{
            radius = (max_y/5) * 2;
        }
    }
    
    
    public void generateShape() {
        int num_cooefficients = rand.nextInt(10) + 1;
        double[] coefficients = new double[num_cooefficients];
        for(int  i = 0; i < num_cooefficients; i++){
            double coef = rand.nextDouble(0,200)/100;
            if(rand.nextBoolean()){
                coef = -coef;
            }
            coefficients[i] = coef;
        }
        RadialFunction radialFunction = new RadialFunction(coefficients);

        for(double i = 0; i < 2 * Math.PI; i += 0.05){
            double radial_radius  = radialFunction.evaluate(i);
            double x_comp = (radial_radius * Math.cos(i)) * (radius/2);
            double y_comp = (radial_radius * Math.sin(i)) * (radius/2);
            if(x_comp > radius){
                x_comp = radius - (x_comp - radius);
            }else if(x_comp < -radius){
                x_comp = -radius - (x_comp + radius);
            }
            if(y_comp > radius){
                y_comp = radius - (y_comp - radius);
            }else if(y_comp < -radius){
                y_comp = -radius - (y_comp + radius);
            }
            if(i == 0){
                shape.moveTo(center_x + x_comp, center_y + y_comp);
            }else{
                shape.lineTo(center_x + x_comp, center_y + y_comp);
            }
        }
        shape.closePath();
    }
    
    public Path2D getShape(){
        return shape;
    }
}