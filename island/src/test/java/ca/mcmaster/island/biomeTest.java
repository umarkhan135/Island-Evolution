package ca.mcmaster.island;
import static org.junit.Assert.assertTrue;



import org.junit.jupiter.api.Test;

import ca.mcmaster.island.Tiles.LandBiomeTiles.CanyonTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.MountainTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.fieldTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.forestTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.rainForest;
public class biomeTest {
    private static final Integer cold = -5;
    private static final Integer mild = 10;
    private static final Integer hot = 25;

    private static final String lowcanyon = "120,62,24";
    private static final String midcanyon = "166,103,61";
    private static final String highcanyon = "209,135,86";
    private static final String coldcanyon = "216,230,226";
    private static final String warmmountain = "99,92,90";
    private static final String coldmountain = "216,230,226";
    private static final String mildforest = "79,140,67";
    private static final String hotforest = "113,140,67";
    private static final String coldforest = "67,140,134";
    private static final String mildfield = "103,230,103";
    private static final String hotfield = "230,173,103";
    private static final String coldfield = "103,219,230";
    private static final String mildrainforest = "11,84,51";
    private static final String hotrainforest = "68,84,11";
    private static final String coldrainforest = "67,140,134";
    private static final Integer radius = 200;
    private static final Double low = -radius/1.35;
    private static final Double mid = -radius/1.75;
    private static final Double high = -radius/2.0;
    private static final CanyonTile canyon = new CanyonTile();
    private static final MountainTile mountian = new MountainTile();
    private static final forestTile forest = new forestTile();
    private static final fieldTile field = new fieldTile();
    private static final rainForest rainForest = new rainForest();

    @Test
    public void canyonTest(){
        assertTrue(lowcanyon.equals(canyon.getColor(low, mild, radius).getValue()));
        assertTrue(midcanyon.equals(canyon.getColor(mid, mild, radius).getValue()));
        assertTrue(highcanyon.equals(canyon.getColor(high, mild, radius).getValue()));
        assertTrue(coldcanyon.equals(canyon.getColor(mid, cold, radius).getValue()));
    }
    @Test
    public void mountainTest(){
        assertTrue(warmmountain.equals(mountian.getColor(mild).getValue()));
        assertTrue(coldmountain.equals(mountian.getColor(cold).getValue()));
    }
    @Test
    public void forestTest(){
        assertTrue(hotforest.equals(forest.getColor(hot).getValue()));
        assertTrue(mildforest.equals(forest.getColor(mild).getValue()));
        assertTrue(coldforest.equals(forest.getColor(cold).getValue()));
    }
    @Test
    public void fieldTest(){
        assertTrue(hotfield.equals(field.getColor(hot).getValue()));
        assertTrue(mildfield.equals(field.getColor(mild).getValue()));
        assertTrue(coldfield.equals(field.getColor(cold).getValue()));
    }
    @Test
    public void rainForestTest(){
        assertTrue(hotrainforest.equals(rainForest.getColor(hot).getValue()));
        assertTrue(mildrainforest.equals(rainForest.getColor(mild).getValue()));
        assertTrue(coldrainforest.equals(rainForest.getColor(cold).getValue()));
    }




}
