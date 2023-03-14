package ca.mcmaster.island;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.island.Tiles.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private static final String oceanColorCode = "43,101,236";
    private static final String landColorCode = "155,118,83";
    private static final String beachColorCode = "194,178,128";
    private static final String lagoonColorCode = "0,98,111";

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
