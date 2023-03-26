package ca.mcmaster.island.shapes;

import java.awt.geom.*;
import java.util.Random;
import java.lang.Math;
import java.util.Arrays;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.island.MeshSize;

public class RandomIsland implements ShapeGenerator{
    
    private Path2D shape = new Path2D.Double();
    private int num_points;
    private Random rand;
    private double max_x;
    private double max_y;
    private double center_x;
    private double center_y;
    private double max_radius;
    private double min_radius;
    
    
    public RandomIsland(Structs.Mesh m){
        rand = new Random();
        MeshSize size = new MeshSize(m);
        max_x = size.getMaxX();
        max_y = size.getMaxY();
        center_x = max_x/2;
        center_y = max_y/2;
        if(max_x < max_y){
            max_radius = (max_x/5) * 2;
        }else{
            max_radius = (max_y/5) * 2;
        }
        min_radius = max_radius/3;
    }

    public RandomIsland(Structs.Mesh m, long seed){
        rand = new Random(seed);
        MeshSize size = new MeshSize(m);
        max_x = size.getMaxX();
        max_y = size.getMaxY();
        center_x = max_x/2;
        center_y = max_y/2;
        if(max_x < max_y){
            max_radius = (max_x/5) * 2;
        }else{
            max_radius = (max_y/5) * 2;
        }
        min_radius = max_radius/3;
    } 
    

    public void generateShape(){
        num_points = rand.nextInt(15, 40);
        double[] angles = new double[num_points];
        for (int i = 0; i < num_points; i++) {
            angles[i] = rand.nextDouble() * 2 * Math.PI;
        }
        Arrays.sort(angles);
        
        double[] radii = new double[num_points];
        for (int i = 0; i < num_points; i++) {
            radii[i] = min_radius + rand.nextDouble() * (max_radius - min_radius);
        }
        
        Point2D.Double[] points = new Point2D.Double[num_points];
        for (int i = 0; i < num_points; i++) {
            double x = center_x + radii[i] * Math.cos(angles[i]);
            double y = center_y + radii[i] * Math.sin(angles[i]);
            points[i] = new Point2D.Double(x, y);
        }
        
        shape.moveTo(points[0].x, points[0].y);
        for (int i = 1; i < num_points; i++) {
            shape.lineTo(points[i].x, points[i].y);

        }
        shape.closePath();
    }
    
    public Path2D getShape(){
        return shape;
    }
    
}