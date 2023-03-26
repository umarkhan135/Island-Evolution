package ca.mcmaster.island;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;


public class distanceTest {

    double x = 3;
    double y = 4;

    @Test
    public void disTest(){
        double z = x*x + y*y;
        double c = Math.sqrt(z);

        Structs.Vertex v = Structs.Vertex.newBuilder().setX(3).setY(4).build();
        distance d = new distance();
        assertEquals(c, d.centerDistance(v,0,0));
    }
}
