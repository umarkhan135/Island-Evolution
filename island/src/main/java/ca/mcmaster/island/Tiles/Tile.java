package ca.mcmaster.island.tiles;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property; 

public interface Tile {
    public Property getColor();
    public Property getTileProperty();
    public String getColorCode();
}
