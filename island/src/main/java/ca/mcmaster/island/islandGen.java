package ca.mcmaster.island;

import ca.mcmaster.island.Tiles.*;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import java.util.*;

public class islandGen {
    public Structs.Mesh lagoon(Structs.Mesh m) {

        ArrayList<Structs.Polygon> tilePolygons = new ArrayList<Structs.Polygon>();
        final double inner_radius = 125.0;
        final double outer_radius = 200.0;
        Tile land = new landTile();
        Tile ocean = new oceanTile();
        Tile lagoon = new lagoonTile();
        Tile beach = new beachTile();


        for (Structs.Polygon p : m.getPolygonsList()) {

            Structs.Vertex v = m.getVertices(p.getCentroidIdx());
            double d = centerDistance(v);

            if (d <= inner_radius) {
                tilePolygons.add(Structs.Polygon.newBuilder(p).addProperties(0, lagoon.getColour()).build());
            }else if(d <= outer_radius){
                boolean b = checkNeighbors(p.getNeighborIdxsList(), m, inner_radius, outer_radius);
                if(b){
                    tilePolygons.add(Structs.Polygon.newBuilder(p).addProperties(0, beach.getColour()).build());
                }else{
                    tilePolygons.add(Structs.Polygon.newBuilder(p).addProperties(0, land.getColour()).build());
                }
            }else {
                tilePolygons.add(Structs.Polygon.newBuilder(p).addProperties(0, ocean.getColour()).build());
            }
        }


        Structs.Mesh newMesh = Structs.Mesh.newBuilder(m).clearPolygons().addAllPolygons(tilePolygons).build();

        return newMesh;
    }

    public double centerDistance(Structs.Vertex v){
        double d = Math.sqrt((250 - v.getY()) * (250 - v.getY()) + (250 - v.getX()) * (250 - v.getX()));
        return d;
    }

    public boolean checkNeighbors(List<Integer> n, Structs.Mesh m, double inner, double outer){
        List<Structs.Polygon> p = m.getPolygonsList();
        for(int i : n){
            Structs.Vertex v = m.getVertices(p.get(i).getCentroidIdx());
            double d = centerDistance(v);
            if(d > outer || d < inner){
                return true;
            }
        }
        return false;
    }
}