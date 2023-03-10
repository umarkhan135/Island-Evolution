package ca.mcmaster.island.Tiles;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

public class landTile implements Tile{
    
    private String colour_code = "210,180,140";

    @Override
    public Property getColour() {
        Property p = Property.newBuilder().setValue(colour_code).build();
        return p;
    }
    
}
