package ca.mcmaster.island;

import ca.mcmaster.island.Tiles.*;
import ca.mcmaster.island.properties.tilePropertyExtract;
import ca.mcmaster.island.*;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import java.util.*;


public class islandGen {
    
    public Structs.Mesh lagoon(Structs.Mesh m) {
        
        ArrayList<Structs.Polygon> tilePolygons1 = new ArrayList<Structs.Polygon>();
        ArrayList<Structs.Polygon> tilePolygons2 = new ArrayList<Structs.Polygon>();

        neighborCheck n = new neighborCheck();
        distance dis = new distance();
        tilePropertyExtract ext = new tilePropertyExtract();
        
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
                tilePolygons1.add(Structs.Polygon.newBuilder(p).addProperties(lagoon.getTileProperty()).build()); 
            }else if(d <= outer_radius){
                tilePolygons1.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).build());
            }else {
                tilePolygons1.add(Structs.Polygon.newBuilder(p).addProperties(ocean.getTileProperty()).build());
            }

        }

        for(Structs.Polygon p: tilePolygons1){
            String prop = ext.getTileProp(p);
            if(prop.equals("landTile")){
                if(n.checkNeighbors(p, m, ocean) || n.checkNeighbors(p, m, lagoon)){
                    tilePolygons2.add(Structs.Polygon.newBuilder(p).addProperties(beach.getTileProperty()).build());
                }
            }else{
                tilePolygons2.add(p);
            }
        }
        
        Structs.Mesh newMesh = Structs.Mesh.newBuilder(m).clearPolygons().addAllPolygons(tilePolygons2).build();
        
        return newMesh;
    }
}
    