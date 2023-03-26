package ca.mcmaster.island.Tiles;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import java.awt.*;


public class oceanTile implements Tile {

    private String color_code = "26,50,100";

    @Override
    public Property getColor() {
        Property p = Property.newBuilder().setKey("rgb_color").setValue(color_code).build();
        return p;
    }
    @Override
    public Property getTileProperty() {
        Property c = Property.newBuilder().setKey("tile_type").setValue("oceanTile").build();
        return c;
    }

    public String getColorCode() {
        return color_code;
    }

    public Color getColorColor(){
        return new Color(43,101,236);
    }

}
