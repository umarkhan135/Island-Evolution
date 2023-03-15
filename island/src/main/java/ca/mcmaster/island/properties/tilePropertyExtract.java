package ca.mcmaster.island.properties;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import java.util.*;

public class tilePropertyExtract {
    public String getTileProp(Structs.Polygon poly){

        List<Structs.Property> prop = poly.getPropertiesList();
        System.out.println(prop.size());
        String val1 = null;

        for(Structs.Property p: prop) {
            if (p.getKey().equals("tile_type")) {
                //System.out.println(p.getValue());
                val1 = p.getValue();
            }
        }

        return val1;
    }
}
