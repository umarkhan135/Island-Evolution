package ca.mcmaster.island.Tiles;

public class waterTile implements Tile{

    private String colour_code = "0,0,255";

    @Override
    public String getColour() {
        return colour_code;
    }
    
}
