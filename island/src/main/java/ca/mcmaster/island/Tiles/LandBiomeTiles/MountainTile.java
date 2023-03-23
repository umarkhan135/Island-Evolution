package ca.mcmaster.island.Tiles.LandBiomeTiles;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.island.Tiles.landTile;

public class MountainTile extends landTile{
    private String color_code = "99,92,90";
    private String snow_code = "216,230,226";
    public Property getColor(double temp) {
        Property p;
        if(temp<0){
            p = Property.newBuilder().setKey("rgb_color").setValue(snow_code).build();
        }else{
            p = Property.newBuilder().setKey("rgb_color").setValue(color_code).build();
        }
        
        return p;
    }
}
