package ca.mcmaster.island.Tiles.LandBiomeTiles;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.island.Tiles.landTile;

public class fieldTile extends landTile{
    private String mild_code = "116,168,120";
    private String hot_code = "168,115,50";
    private String cold_code = "116,168,168";
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
