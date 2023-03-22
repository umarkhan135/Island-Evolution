package ca.mcmaster.island.Tiles.LandBiomeTiles.MountainTypes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

public class SnowyMountainTile extends MountainTile{
    private String color_code = "216,230,226";
    public Property getColor() {
        Property p = Property.newBuilder().setKey("rgb_color").setValue(color_code).build();
        return p;
    }
}
