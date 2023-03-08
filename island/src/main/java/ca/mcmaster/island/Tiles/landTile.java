package ca.mcmaster.island.Tiles;

public class landTile implements Tile{
    
    private String colour_code = "210,180,140";

    @Override
    public String getColour() {
        return colour_code;
    }
    
}
