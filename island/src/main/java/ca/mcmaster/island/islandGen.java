package ca.mcmaster.island;

import ca.mcmaster.island.Tiles.*;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import java.util.*;

public class islandGen {
    public Structs.Mesh lagoon(Structs.Mesh m) {

        ArrayList<Structs.Polygon> tilePolygons = new ArrayList<Structs.Polygon>();
        final double radius = 100.0;
        Tile land = new landTile();
        Tile water = new waterTile();

        for (Structs.Polygon p : m.getPolygonsList()) {

            Structs.Vertex v = m.getVertices(p.getCentroidIdx());
            double d = Math.sqrt((250 - v.getY()) * (250 - v.getY()) + (250 - v.getX()) * (250 - v.getX()));

            if (d <= radius) {
                tilePolygons.add(Structs.Polygon.newBuilder(p).addProperties(0, land.getColour()).build());
            } else {
                tilePolygons.add(Structs.Polygon.newBuilder(p).addProperties(0, water.getColour()).build());
            }
        }

        Structs.Mesh newMesh = Structs.Mesh.newBuilder(m).clearPolygons().addAllPolygons(tilePolygons).build();

        return newMesh;
    }
}