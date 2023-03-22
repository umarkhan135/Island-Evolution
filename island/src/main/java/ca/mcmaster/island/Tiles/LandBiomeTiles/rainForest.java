package ca.mcmaster.island.Tiles.LandBiomeTiles;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.island.Tiles.landTile;

public class rainForest extends landTile{
    private String mild_code = "11,84,51";
    private String hot_code = "68,84,11";
    private String cold_code = "67,140,134";
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
