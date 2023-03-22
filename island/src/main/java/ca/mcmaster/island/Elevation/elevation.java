package ca.mcmaster.island.Elevation;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public interface elevation {
    public double getElevation(Structs.Polygon polygon, int radius, Structs.Mesh aMesh);

    public Property tileElevation ();
}
