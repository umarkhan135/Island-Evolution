package ca.mcmaster.island;

import java.util.ArrayList;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

import ca.mcmaster.island.Tiles.*;
import ca.mcmaster.island.Tiles.LandBiomeTiles.forestTile;
import ca.mcmaster.island.*;

import ca.mcmaster.island.properties.*;
import ca.mcmaster.island.Tiles.*;

import java.util.*;


public class colorMesh {
    Tile land = new landTile();
    Tile ocean = new oceanTile();
    Tile lagoon = new lagoonTile();
    Tile beach = new beachTile();
    Tile lake = new lakeTile();
    TileProperty tileProperty = new TileProperty();

    public Mesh setpolygonColor(Mesh m) {
        ArrayList<Structs.Polygon> tilePolygons = new ArrayList<Structs.Polygon>();
        for (Structs.Polygon p : m.getPolygonsList()) {
            Optional<String> tile = tileProperty.extract(p.getPropertiesList());
            if (tile.isPresent()) {
                if (tile.get().equals(land.getTileProperty().getValue())) {
                    tilePolygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getColor()).build());

                } else if (tile.get().equals(land.getTileProperty().getValue())) {
                    tilePolygons.add(Structs.Polygon.newBuilder(p).addProperties(ocean.getColor()).build());

                } else if (tile.get().equals(land.getTileProperty().getValue())) {
                    tilePolygons.add(Structs.Polygon.newBuilder(p).addProperties(lagoon.getColor()).build());

                } else if (tile.get().equals(land.getTileProperty().getValue())) {
                    tilePolygons.add(Structs.Polygon.newBuilder(p).addProperties(beach.getColor()).build());

                } else if (tile.get().equals(land.getTileProperty().getValue())) {
                    tilePolygons.add(Structs.Polygon.newBuilder(p).addProperties(lake.getColor()).build());
                }
            }
        }
        Structs.Mesh newMesh = Structs.Mesh.newBuilder(m).clearPolygons().addAllPolygons(tilePolygons).build();
        return newMesh;
    }
}
