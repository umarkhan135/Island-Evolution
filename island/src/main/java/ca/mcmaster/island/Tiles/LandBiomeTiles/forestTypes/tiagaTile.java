package ca.mcmaster.island.Tiles.LandBiomeTiles.forestTypes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

public class tiagaTile extends forestTile{
    private String color_code = "31,117,122";
    public Property getColor() {
        Property p = Property.newBuilder().setKey("rgb_color").setValue(color_code).build();
        return p;
    }
}
