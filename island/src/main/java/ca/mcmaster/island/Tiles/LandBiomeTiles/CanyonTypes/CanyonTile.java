package ca.mcmaster.island.Tiles.LandBiomeTiles.CanyonTypes;
import ca.mcmaster.island.Tiles.landTile;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

public class CanyonTile extends landTile{
    private String color_code = "120,62,24";
    private String color_code2 = "166,103,61";
    private String color_code3 = "209,135,86";
    public Property getColor(int hieght) {
        Property p;
        if(hieght<-110){
            p = Property.newBuilder().setKey("rgb_color").setValue(color_code).build();
        }else if(hieght<-100){
            p = Property.newBuilder().setKey("rgb_color").setValue(color_code2).build();
        }else{
            p = Property.newBuilder().setKey("rgb_color").setValue(color_code3).build();
        }
        return p;
    }
}
