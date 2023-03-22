
package ca.mcmaster.island;

import ca.mcmaster.island.*;
import ca.mcmaster.island.properties.*;
import ca.mcmaster.island.Tiles.*;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.island.Elevation.*;


import java.awt.geom.*;
import java.awt.Color;
import java.util.*;

public class IslandGenerator {
    

    public Structs.Mesh basic(Structs.Mesh m, Path2D s, elevation elevate){

        ArrayList<Structs.Polygon> tilePolygons = new ArrayList<Structs.Polygon>();
        ArrayList<Structs.Polygon> poly = new ArrayList<Structs.Polygon>();

        double x = new MeshSize(m).getMaxX();


        Tile land = new landTile();
        Tile ocean = new oceanTile();

        for (Structs.Polygon p : m.getPolygonsList()) {
            
            Structs.Vertex v = m.getVertices(p.getCentroidIdx());
            
            if(s.contains(v.getX(), v.getY())){
                tilePolygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getColor()).addProperties(land.getTileProperty()).build());
            }else{
                tilePolygons.add(Structs.Polygon.newBuilder(p).addProperties(ocean.getColor()).addProperties(ocean.getTileProperty()).build());
            }
        }

        Structs.Mesh newMesh = Structs.Mesh.newBuilder(m).clearPolygons().addAllPolygons(tilePolygons).build();

        for (Structs.Polygon p : tilePolygons) {

            elevate.getElevation(p, x, m);
            Structs.Polygon newPolygon = Structs.Polygon.newBuilder(p).addProperties(elevate.tileElevation()).build();
            poly.add(newPolygon);
        }
        Structs.Mesh newMeshWithElevation = Structs.Mesh.newBuilder(newMesh).clearPolygons().addAllPolygons(poly).build();
        
        return newMeshWithElevation;

    }

    public Structs.Mesh lagoon(Structs.Mesh m) {

        
        
        ArrayList<Structs.Polygon> tilePolygons1 = new ArrayList<Structs.Polygon>();
        ArrayList<Structs.Polygon> poly = new ArrayList<Structs.Polygon>();
        ArrayList<Structs.Polygon> tilePolygons2 = new ArrayList<Structs.Polygon>();
        
        neighborCheck n = new neighborCheck();
        distance dis = new distance();
        TileProperty tileProperty = new TileProperty();
        ColorProperty colorProperty = new ColorProperty();
        
        final double inner_radius = 125.0;
        final double outer_radius = 200.0;
        
        Tile land = new landTile();
        Tile ocean = new oceanTile();
        Tile lagoon = new lagoonTile();
        Tile beach = new beachTile();
        
        for (Structs.Polygon p : m.getPolygonsList()) {
            
            Structs.Vertex v = m.getVertices(p.getCentroidIdx());
            double d = dis.centerDistance(v, 250, 250);
            
            if (d <= inner_radius) {
                tilePolygons1.add(Structs.Polygon.newBuilder(p).addProperties(lagoon.getColor()).addProperties(lagoon.getTileProperty()).build());
            } else if (d <= outer_radius) {
                tilePolygons1.add(Structs.Polygon.newBuilder(p).addProperties(land.getColor()).addProperties(land.getTileProperty()).build());
            } else {
                tilePolygons1.add(Structs.Polygon.newBuilder(p).addProperties(ocean.getColor()).addProperties(ocean.getTileProperty()).build());
            }
            
        }


        Structs.Mesh newMesh = Structs.Mesh.newBuilder(m).clearPolygons().addAllPolygons(tilePolygons1).build();

        for (Structs.Polygon p : tilePolygons1) {

            elevation elevate = new Volcano();
            elevate.getElevation(p, 200, m);
            Structs.Polygon newPolygon = Structs.Polygon.newBuilder(p).addProperties(elevate.tileElevation()).build();
            poly.add(newPolygon);
        }
        Structs.Mesh newMeshWithElevation = Structs.Mesh.newBuilder(newMesh).clearPolygons().addAllPolygons(poly).build();
        
        
        for (Structs.Polygon p : poly) {
            Optional<Color> tile = colorProperty.extract(p.getPropertiesList());
            if (tile.isPresent()) {
                if (tile.get().equals(colorProperty.toColor(land.getColorCode()))) {
                    if (n.checkNeighbors(p, newMeshWithElevation, ocean) || n.checkNeighbors(p, newMeshWithElevation, lagoon)) {
                        tilePolygons2.add(Structs.Polygon.newBuilder(p).addProperties(beach.getColor()).build());
                    } else {
                        tilePolygons2.add(p);
                    }
                } else {
                    tilePolygons2.add(p);
                }
            }
        }
        
        Structs.Mesh newMesh2 = Structs.Mesh.newBuilder(newMeshWithElevation).clearPolygons().addAllPolygons(tilePolygons2).build();
        
        return newMesh2;
    }
}

