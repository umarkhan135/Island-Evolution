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
import ca.mcmaster.island.configuration.Configuration;
import ca.mcmaster.island.tiles.*;

/**
 * Unit test for simple App.
 */
public class tileTest {
    private static final String oceanColorCode = "26,50,100";
    private static final String landColorCode = "155,118,83";
    private static final String beachColorCode = "194,178,128";
    private static final String lagoonColorCode = "0,150,200";
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

    

}

    

