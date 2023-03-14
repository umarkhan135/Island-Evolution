package ca.mcmaster.island;

import ca.mcmaster.island.Tiles.*;
import ca.mcmaster.island.*;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import java.util.*;


public class islandGen {
    
    public Structs.Mesh lagoon(Structs.Mesh m) {
        
        ArrayList<Structs.Polygon> tilePolygons = new ArrayList<Structs.Polygon>();
        neighborCheck n = new neighborCheck();
        distance dis = new distance();
        
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
                tilePolygons.add(Structs.Polygon.newBuilder(p).addProperties(lagoon.getColor()).build());
            }else if(d <= outer_radius && n.checkNeighbors(p, m, outer_radius, inner_radius)){
                tilePolygons.add(Structs.Polygon.newBuilder(p).addProperties(beach.getColor()).build()); 
            }else if(d <= outer_radius){
                tilePolygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getColor()).build());
            }else {
                tilePolygons.add(Structs.Polygon.newBuilder(p).addProperties(ocean.getColor()).build());
            }

        }
        
        Structs.Mesh newMesh = Structs.Mesh.newBuilder(m).clearPolygons().addAllPolygons(tilePolygons).build();
        
        return newMesh;
    }
    
}