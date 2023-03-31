package ca.mcmaster.island.Lakes;

import java.awt.Color;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import ca.mcmaster.island.neighborCheck;
import ca.mcmaster.island.Tiles.lakeTile;
import ca.mcmaster.island.Tiles.landTile;
import ca.mcmaster.island.Tiles.oceanTile;

import ca.mcmaster.island.properties.ColorProperty;
import ca.mcmaster.island.properties.TileProperty;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.*;

public class LakeGen {
    private boolean lake;
    private Random random;
    public List<Structs.Polygon> lakeNeighbor = new ArrayList<>();


    public LakeGen() {
        random = new Random();
    }

    public LakeGen(long seed) {
        random = new Random(seed);
    }

    public boolean makeLakes(int numLakes, Mesh m){
        List<Integer> neighbors = new ArrayList<>();
        TileProperty tileProperty = new TileProperty();
        ColorProperty colorProperty = new ColorProperty();
        String oceanColorString = new oceanTile().getColor().getValue();
        Color oceanColor = colorProperty.toColor(oceanColorString);
        boolean found = false;

        for (int i = 0; i < numLakes; i++) {
            int rand;
            Structs.Polygon p;
            Optional<String> polygonTileType;
            Optional<Color> polygonColor;

            do{
                rand = random.nextInt(m.getPolygonsCount());
                p = m.getPolygons(rand);
                polygonColor = colorProperty.extract(p.getPropertiesList());
                if (polygonColor.isPresent() && !polygonColor.get().equals(oceanColor)){
                    found = true;
                }
            }while (polygonColor.isPresent() && polygonColor.get().equals(oceanColor));

            neighbors = p.getNeighborIdxsList();
            List<Polygon> polygons = m.getPolygonsList();
            List<Integer> filteredNeighbors = new ArrayList<>();
            for (Integer neighborIdx : neighbors) {
                Structs.Polygon neighbor = polygons.get(neighborIdx);
                Optional<Color> neighborColor = colorProperty.extract(neighbor.getPropertiesList());
                if (neighborColor.isPresent() && !neighborColor.get().equals(oceanColor)) {
                    filteredNeighbors.add(neighborIdx);
                }
            }
            List<Structs.Polygon> lakeNeighborList = markLake(filteredNeighbors, m);
            
            for (Structs.Polygon aP : lakeNeighborList) {
                lakeNeighbor.add(aP);
            }
            lakeNeighbor.add(p);
        }
        this.lake = found;
        return this.lake;
    }

    public List<Structs.Polygon> markLake(List<Integer> neighbors, Structs.Mesh mesh) {
        ColorProperty colorProperty = new ColorProperty();
        String oceanColorString = new oceanTile().getColor().getValue();
        Color oceanColor = colorProperty.toColor(oceanColorString);
        neighborCheck neighborCheck = new neighborCheck();
        List<Structs.Polygon> poly = new ArrayList<>();


        for (Integer i : neighbors){
            Structs.Polygon p = mesh.getPolygons(i.intValue());
            Optional<Color> polygonColor = colorProperty.extract(p.getPropertiesList());
            if (polygonColor.isPresent() && !polygonColor.get().equals(oceanColor) && !neighborCheck.checkNeighbors(p, mesh, new oceanTile())){
                poly.add(p);
            }
        }
        return poly;
    }

    public Property Lakes() {

        Property c = Property.newBuilder().setKey("rgb_color").setValue("0,141,151").build();
        return c;

    }

    public Mesh meshWithLakes(List<Structs.Polygon> polygons, int amount, Mesh m) {
        ArrayList<Structs.Polygon> poly = new ArrayList<>();
        lakeTile lake = new lakeTile();

        makeLakes(amount, m);
        for (Structs.Polygon p : polygons) {
            boolean isLake = lakeNeighbor.contains(p);
            if (isLake){
                Property lakeColorProperty = lake.getColor();
                Property lakeTileProperty = lake.getTileProperty();
                Structs.Polygon newPolygon = Structs.Polygon.newBuilder(p).addProperties(lakeColorProperty).addProperties(lakeTileProperty).build();
                poly.add(newPolygon);
            } else {
                poly.add(p);
            }
        }

        Structs.Mesh newMeshWithLake = Structs.Mesh.newBuilder(m).clearPolygons().addAllPolygons(poly).build();
        return newMeshWithLake;

    }
}