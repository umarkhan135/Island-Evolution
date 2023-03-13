package ca.mcmaster.island.Tiles;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

public class oceanTile implements Tile{

    private String colour_code = "43,101,236";

    @Override
    public Property getColour() {
        Property p = Property.newBuilder().setValue(colour_code).build();
        return p;
    }
    
}
