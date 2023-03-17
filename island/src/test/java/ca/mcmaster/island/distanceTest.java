package ca.mcmaster.island;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.island.Configuration.Configuration;
import ca.mcmaster.island.Tiles.*;

public class distanceTest {

    double x = 3;
    double y = 4;

    @Test
    public void distanceTest() {
        double z = x * x + y * y;
        double c = Math.sqrt(z);

        Structs.Vertex v = Structs.Vertex.newBuilder().setX(3).setY(4).build();
        distance d = new distance();
        assertEquals(c, d.centerDistance(v, 0, 0));
    }
}
