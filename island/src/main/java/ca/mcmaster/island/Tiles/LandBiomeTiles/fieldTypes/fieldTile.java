package ca.mcmaster.island.Tiles.LandBiomeTiles.fieldTypes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.island.Tiles.LandTile;

public class fieldTile extends LandTile{
    private String color_code = "116,168,120";
    public Property getColor() {
        Property p = Property.newBuilder().setKey("rgb_color").setValue(color_code).build();
        return p;
    }
}
