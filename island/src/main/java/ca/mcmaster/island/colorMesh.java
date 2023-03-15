package ca.mcmaster.island;

import java.util.ArrayList;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

import ca.mcmaster.island.Tiles.*;
import ca.mcmaster.island.*;
import java.util.*;

public class colorMesh {
    static Tile land = new landTile();
    static Tile ocean = new oceanTile();
    static Tile lagoon = new lagoonTile();
    static Tile beach = new beachTile();

    public static Mesh setpolygonColor(Mesh m) {
        ArrayList<Structs.Polygon> tilePolygons = new ArrayList<Structs.Polygon>();
        for (Structs.Polygon p : m.getPolygonsList()) {
            if (p.getProperties(0).getValue().equals(land.getTileProperty().getValue())) {
                tilePolygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getColor()).build());

            } else if (p.getProperties(0).getValue().equals(ocean.getTileProperty().getValue())) {
                tilePolygons.add(Structs.Polygon.newBuilder(p).addProperties(ocean.getColor()).build());

            } else if (p.getProperties(0).getValue().equals(lagoon.getTileProperty().getValue())) {
                tilePolygons.add(Structs.Polygon.newBuilder(p).addProperties(lagoon.getColor()).build());

            } else if (p.getProperties(0).getValue().equals(beach.getTileProperty().getValue())) {
                tilePolygons.add(Structs.Polygon.newBuilder(p).addProperties(beach.getColor()).build());

            }

        }
        Structs.Mesh newMesh = Structs.Mesh.newBuilder(m).clearPolygons().addAllPolygons(tilePolygons).build();
        return newMesh;
    }
}
