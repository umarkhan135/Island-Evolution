package ca.mcmaster.island.Rivers;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

public class RiverColor {
    private final String riverColor = "255,255,255";

    public Property getSegmentSegmentColor() {
        Property p = Property.newBuilder().setKey("rgb_color").setValue(riverColor).build();
        return p;
    }

    public Property noColor() {
        Property p = Property.newBuilder().setKey("rgb_color").setValue("0,0,0").build();
        return p;
    }
    
}
