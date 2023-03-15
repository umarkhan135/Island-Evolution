package ca.mcmaster.island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class distance {
    public double centerDistance(Structs.Vertex v, double x, double y){
        double d = Math.sqrt((y - v.getY()) * (y - v.getY()) + (x - v.getX()) * (x - v.getX()));
        return d;
    }
}
