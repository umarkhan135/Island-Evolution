package ca.mcmaster.island.Tiles.LandBiomeTiles.MountainTypes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.island.Tiles.landTile;

public class MountainTile extends landTile{
    private String color_code = "99,92,90";
    public Property getColor() {
        Property p = Property.newBuilder().setKey("rgb_color").setValue(color_code).build();
        return p;
    }
}
