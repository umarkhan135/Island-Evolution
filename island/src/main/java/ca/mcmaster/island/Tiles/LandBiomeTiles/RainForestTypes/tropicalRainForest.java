package ca.mcmaster.island.Tiles.LandBiomeTiles.RainForestTypes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

public class tropicalRainForest extends rainForest{
    private String color_code = "25,51,6";
    public Property getColor() {
        Property p = Property.newBuilder().setKey("rgb_color").setValue(color_code).build();
        return p;
    }
}
