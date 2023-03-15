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

/**
 * Unit test for simple App.
 */
public class AppTest {
    private static final String oceanColorCode = "43,101,236";
    private static final String landColorCode = "155,118,83";
    private static final String beachColorCode = "194,178,128";
    private static final String lagoonColorCode = "0,98,111";
    double x = 3;
    double y = 4;

    /**
     * Rigorous Test :-)
     */
    @Test
    public void oceanTest() throws IOException {
        assertTrue(new oceanTile().getColor().getValue().equals(oceanColorCode));
    }

    @Test
    public void landTest() throws IOException {
        assertTrue(new landTile().getColor().getValue().equals(landColorCode));
    }

    @Test
    public void beachTest() throws IOException {
        assertTrue(new beachTile().getColor().getValue().equals(beachColorCode));
    }

    @Test
    public void lagoonTest() throws IOException {
        assertTrue(new lagoonTile().getColor().getValue().equals(lagoonColorCode));
    }

    @Test
    public void distanceTest(){
        double z = x*x + y*y;
        double c = Math.sqrt(z);

        Structs.Vertex v = Structs.Vertex.newBuilder().setX(3).setY(4).build();
        distance d = new distance();
        assertEquals(c, d.centerDistance(v,0,0));
    }

}

    

