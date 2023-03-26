package ca.mcmaster.island.Rivers;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

public class numRiver {

    
    public Property NumberOfRivers(int num) {
        Property p = Property.newBuilder().setKey("RiverNum").setValue(Integer.toString(num)).build();
        return p;
    }
    
}
