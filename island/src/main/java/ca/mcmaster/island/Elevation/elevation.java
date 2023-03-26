package ca.mcmaster.island.Elevation;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

import java.awt.geom.Path2D;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public interface elevation {
    public double getElevation(Structs.Polygon polygon, double radius, Structs.Mesh aMesh, Path2D shape);
    public double getElevation(Structs.Polygon polygon, double radius, Structs.Mesh aMesh);

    public Property tileElevation ();
}
