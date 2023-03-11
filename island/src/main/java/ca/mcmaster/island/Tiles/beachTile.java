package ca.mcmaster.island.Tiles;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

public class beachTile implements Tile{

    private String colour_code = "194,178,128";

    @Override
    public Property getColour() {
        Property p = Property.newBuilder().setValue(colour_code).build();
        return p;
    }
    
}