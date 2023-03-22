package ca.mcmaster.island.Tiles.LandBiomeTiles;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.island.Tiles.landTile;

public class rainForest extends landTile{
    private String mild_code = "6,51,31";
    private String hot_code = "25,51,6";
    private String cold_code = "31,117,122";
    public Property getColor(double temp) {
        Property p;
        if (temp<0){
            p = Property.newBuilder().setKey("rgb_color").setValue(cold_code).build();
        }else if(temp>20){
            p = Property.newBuilder().setKey("rgb_color").setValue(hot_code).build();
        }else{
            p = Property.newBuilder().setKey("rgb_color").setValue(mild_code).build();
        }
        
        return p;
    }
}
