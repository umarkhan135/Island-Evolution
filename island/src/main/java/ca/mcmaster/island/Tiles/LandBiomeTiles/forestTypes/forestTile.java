package ca.mcmaster.island.Tiles.LandBiomeTiles.forestTypes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.island.Tiles.landTile;

public class forestTile extends landTile {
    private String color_code = "31,122,49";
    public Property getColor() {
        Property p = Property.newBuilder().setKey("rgb_color").setValue(color_code).build();
        return p;
    }

}
