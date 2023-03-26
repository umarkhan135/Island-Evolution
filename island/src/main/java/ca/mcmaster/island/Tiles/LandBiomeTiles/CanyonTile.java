package ca.mcmaster.island.Tiles.LandBiomeTiles;
import ca.mcmaster.island.Tiles.landTile;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

public class CanyonTile extends landTile{
    private String color_code = "120,62,24";
    private String color_code2 = "166,103,61";
    private String color_code3 = "209,135,86";
    private String snow_code = "216,230,226";
    public Property getColor(double hieght, double temp, double radius) {
        Property p;
        if(temp<0){
            p = Property.newBuilder().setKey("rgb_color").setValue(snow_code).build();
        }else{
            if(hieght<=-radius/1.35){
                p = Property.newBuilder().setKey("rgb_color").setValue(color_code).build();
            }else if(hieght<=-radius/1.75){
                p = Property.newBuilder().setKey("rgb_color").setValue(color_code2).build();
            }else{
                p = Property.newBuilder().setKey("rgb_color").setValue(color_code3).build();
            }
        }
        return p;
    }
}
