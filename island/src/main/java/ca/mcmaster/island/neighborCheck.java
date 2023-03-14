package ca.mcmaster.island;

import ca.mcmaster.island.Tiles.*;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.*;

public class neighborCheck {

    public boolean checkNeighbors(Structs.Polygon p, Structs.Mesh m, double outer, double inner){
        List<Integer> n = p.getNeighborIdxsList();
        distance dis = new distance();
        List<Structs.Polygon> polys = m.getPolygonsList();
        for (int i : n) {
            Structs.Vertex v = m.getVertices(polys.get(i).getCentroidIdx());
            double d = dis.centerDistance(v, 250, 250);
            if (d > outer || d < inner) {
                return true;
            }
        }
        return false;
    }
}