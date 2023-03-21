package ca.mcmaster.island.Tiles.LandBiomeTiles.CanyonTypes;
import ca.mcmaster.island.Tiles.LandTile;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

public class CanyonTile extends LandTile{
    private String color_code = "128,115,97";
    public Property getColor() {
        Property p = Property.newBuilder().setKey("rgb_color").setValue(color_code).build();
        return p;
    }
}
