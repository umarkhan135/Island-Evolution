package ca.mcmaster.island.Tiles.LandBiomeTiles;

import ca.mcmaster.island.Tiles.landTile;

public class forestTile extends landTile {
    protected String color_code = "31, 122, 49";
    protected Integer annual_Precipitation;
    protected Integer average_Temperature;

    public forestTile(int aP, int aT) {
        this.annual_Precipitation = aP;
        this.average_Temperature = aT;
    }

    public forestTile() {
    }
}
