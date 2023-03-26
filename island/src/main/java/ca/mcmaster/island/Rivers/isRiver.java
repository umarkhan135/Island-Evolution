package ca.mcmaster.island.Rivers;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

public class isRiver {
    
    public Property isARiver() {
        Property p = Property.newBuilder().setKey("River").setValue("true").build();
        return p;
    }

    public Property notRiver() {
        Property p = Property.newBuilder().setKey("River").setValue("false").build();
        return p;
    }
}
