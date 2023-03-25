package ca.mcmaster.island.Tiles;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

public class landTile implements Tile {

    private String color_code = "155,118,83";

    @Override
    public Property getColor() {
        Property p = Property.newBuilder().setKey("rgb_color").setValue(color_code).build();
        return p;
    }
    @Override
    public Property getTileProperty() {
        Property c = Property.newBuilder().setKey("tile_type").setValue("landTile").build();
        return c;
    }

    public String getColorCode() {
        return color_code;
    }

}
