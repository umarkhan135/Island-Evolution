package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;

import java.util.*;

public class AverageColorGen {

    public Property avgColor(List<Property> prop1, List<Property> prop2) {
    
        String val1 = null;
        String val2 = null;
        for(Property p: prop1) {
            if (p.getKey().equals("rgb_color")) {
                //System.out.println(p.getValue());
                val1 = p.getValue();
            }
        }
        for(Property p: prop2) {
            if (p.getKey().equals("rgb_color")) {
                //System.out.println(p.getValue());
                val2 = p.getValue();
            }
        }

        String[] raw1 = val1.split(",");
        String[] raw2 = val2.split(",");
        int red = (Integer.parseInt(raw1[0]) + Integer.parseInt(raw2[0]))/2;
        int green = (Integer.parseInt(raw1[1]) + Integer.parseInt(raw2[1]))/2;
        int blue = (Integer.parseInt(raw1[2]) + Integer.parseInt(raw2[2]))/2;
        int alpha = (Integer.parseInt(raw1[3]) + Integer.parseInt(raw2[3]))/2;
        String colorCode = red + "," + green + "," + blue + "," + alpha;
        Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
        return color;
    }

}
