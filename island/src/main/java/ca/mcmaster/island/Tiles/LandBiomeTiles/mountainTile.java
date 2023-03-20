package ca.mcmaster.island.Tiles.LandBiomeTiles;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.island.Tiles.landTile;

public class mountainTile extends landTile{
    private String color_code = "50,54,52";
    public Property getColor() {
        Property p = Property.newBuilder().setKey("rgb_color").setValue(color_code).build();
        return p;
    }
}
