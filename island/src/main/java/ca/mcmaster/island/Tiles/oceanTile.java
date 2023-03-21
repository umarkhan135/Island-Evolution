package ca.mcmaster.island.tiles;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

public class OceanTile implements Tile {

    private String color_code = "26,50,100";

    @Override
    public Property getColor() {
        Property p = Property.newBuilder().setKey("rgb_color").setValue(color_code).build();
        return p;
    }

    public Property getTileProperty(){
        Property c = Property.newBuilder().setKey("tile_type").setValue("oceanTile").build();
        return c;
    }

    public String getColorCode(){
        return color_code;
    }

}
