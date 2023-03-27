package ca.mcmaster.island;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import ca.mcmaster.island.Tiles.*;

/**
 * Unit test for simple App.
 */
public class tileTest {
    private static final String oceanColorCode = "26,50,100";
    private static final String landColorCode = "155,118,83";
    private static final String beachColorCode = "194,178,128";
    private static final String lagoonColorCode = "0,150,200";
    private static final String lakeColorCode = "0,141,151";
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
    public void lakeTest() throws IOException {
        assertTrue(new lakeTile().getColor().getValue().equals(lakeColorCode));
    }

}

    

