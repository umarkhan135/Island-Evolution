package ca.mcmaster.island;

import ca.mcmaster.island.Tiles.*;
import ca.mcmaster.island.properties.tilePropertyExtract;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.island.Configuration.*;

import java.util.*;

public class neighborCheck {

    public boolean checkNeighbors(Structs.Polygon poly, Structs.Mesh m, Tile t){
        
        String prop1 = t.getColorCode();

        List<Integer> n = poly.getNeighborIdxsList();
        List<Structs.Polygon> polys = m.getPolygonsList();

        tilePropertyExtract ext = new tilePropertyExtract();

        for(Structs.Polygon p: polys){
            String prop2 = ext.getTileProp(p);
            if(prop2.equals(prop1)){
                return true;
            }
        }
        return false;
    }
}