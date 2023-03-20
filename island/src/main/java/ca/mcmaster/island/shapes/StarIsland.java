package ca.mcmaster.island.shapes;

import java.awt.geom.*;

import ca.mcmaster.island.MeshSize;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import java.util.Random;

public class StarIsland implements ShapeGenerator{

    private Path2D shape = new Path2D.Double();
    private double max_x;
    private double max_y;
    private double center_x;
    private double center_y;
    private double radius;
    private Random rand = new Random();
    

    public StarIsland(Structs.Mesh m){
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

    public void generateShape(){

        double radian = (rand.nextInt(0, 200)/2.0) * Math.PI;

        for(int i = 0; i < 5; i++){
            if(i == 0)
                shape.moveTo(center_x + (radius * Math.cos(radian)), center_y + (radius * Math.sin(radian)));
            else
                shape.lineTo(center_x + (radius * Math.cos(radian)), center_y + (radius * Math.sin(radian)));
            
            radian += (Math.PI * 6)/5;
        }
        shape.closePath();
        
    }

    public Path2D getShape(){
        return shape;
    }
    
}
