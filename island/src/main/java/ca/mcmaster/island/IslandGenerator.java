package ca.mcmaster.island;

import ca.mcmaster.island.Tiles.*;
import ca.mcmaster.island.properties.TileProperty;
import ca.mcmaster.island.NeighborCheck;
import ca.mcmaster.island.BiomeGeneration.randomBiomeGen;
import ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen.whittakerGen;
import ca.mcmaster.island.Configuration.Configuration;
import ca.mcmaster.island.Elevation.Canyon;
import ca.mcmaster.island.Elevation.Volcano;
import ca.mcmaster.island.Elevation.elevation;
import ca.mcmaster.island.Distance;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

import java.awt.Color;
import java.awt.geom.Path2D;
import java.util.*;
import ca.mcmaster.island.properties.*;


public class IslandGenerator {
   
    private Configuration config;

    public IslandGenerator(Configuration config) {
        this.config = config;
    }
    public Structs.Mesh basic(Structs.Mesh m, Path2D s){
        whittakerGen wGen = new whittakerGen(config.getTemperature(), config.getPrecipitation());
        ArrayList<Structs.Polygon> tilePolygons = new ArrayList<Structs.Polygon>();
        ArrayList<Structs.Polygon> poly = new ArrayList<>();
        elevation elevate = createElevationProfile(config.getAltitude());


        Tile land = new LandTile();
        Tile ocean = new OceanTile();

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

            //elevation elevate = new Volcano();
            elevate.getElevation(p, 200, m);
            Structs.Polygon newPolygon = Structs.Polygon.newBuilder(p).addProperties(elevate.tileElevation()).build();
            poly.add(newPolygon);
        }
        Structs.Mesh newMeshWithElevation = Structs.Mesh.newBuilder(newMesh).clearPolygons().addAllPolygons(poly).build();
        Structs.Mesh newMesh2 = whittakerGen.biomeGen(newMeshWithElevation);
        return newMesh2;

    }
    private elevation createElevationProfile(String altitudeProfile) {
        switch (altitudeProfile.toLowerCase()) {
            case "volcano":
                return new Volcano();
            case "canyon":
                return new Canyon();
            default:
                throw new IllegalArgumentException("Invalid altitude profile: " + altitudeProfile);
        }
    }

    public Structs.Mesh lagoon(Structs.Mesh m) {

        

        elevation elevate = createElevationProfile(config.getAltitude());
        whittakerGen whitGen = new whittakerGen(config.getTemperature(), config.getPrecipitation());

        MeshSize size = new MeshSize(m);
        Structs.Vertex maxSize = size.findLargestXYVertex(m);

        ArrayList<Structs.Polygon> tilePolygons1 = new ArrayList<Structs.Polygon>();
        ArrayList<Structs.Polygon> tilePolygons2 = new ArrayList<Structs.Polygon>();

        NeighborCheck n = new NeighborCheck();
        Distance dis = new Distance();
        TileProperty tileProperty = new TileProperty();
        ColorProperty colorProperty = new ColorProperty();
        randomBiomeGen landBiome = new randomBiomeGen();
       

        final double inner_radius = 125.0;
        final double outer_radius = 200.0;
        final double x = maxSize.getX();
        final double y = maxSize.getY();

        

        Tile land = new LandTile();
        Tile ocean = new OceanTile();
        Tile lagoon = new LagoonTile();
        Tile beach = new BeachTile();


        
        

        for (Structs.Polygon p : m.getPolygonsList()) {

            Structs.Vertex v = m.getVertices(p.getCentroidIdx());
            double d = dis.centerDistance(v, x/2, y/2);

            if (d <= inner_radius) {                
                tilePolygons1.add(Structs.Polygon.newBuilder(p).addProperties(lagoon.getTileProperty()).addProperties(lagoon.getColor()).build());
            } else if (d <= outer_radius) {
                tilePolygons1.add(Structs.Polygon.newBuilder(p).addProperties(land.getColor()).addProperties(land.getTileProperty()).build());
            } else {
                tilePolygons1.add(Structs.Polygon.newBuilder(p).addProperties(ocean.getColor()).addProperties(ocean.getTileProperty()).build());
            }

        }

        Structs.Mesh newMesh = Structs.Mesh.newBuilder(m).clearPolygons().addAllPolygons(tilePolygons1).build();


        ArrayList<Structs.Polygon> poly = new ArrayList<Structs.Polygon>();

        for (Structs.Polygon p : tilePolygons1) {

            //elevation elevate = new Volcano();
            elevate.getElevation(p, 200, m);
            Structs.Polygon newPolygon = Structs.Polygon.newBuilder(p).addProperties(elevate.tileElevation()).build();
            poly.add(newPolygon);
        }
        Structs.Mesh newMeshWithElevation = Structs.Mesh.newBuilder(newMesh).clearPolygons().addAllPolygons(poly).build();

        for (Structs.Polygon p : poly) {
            //elevation elevate = new Volcano();
            elevate.getElevation(p, 200, m);
            Optional<Color> tile = colorProperty.extract(p.getPropertiesList());
            if (tile.isPresent()) {
                if (tile.get().equals(colorProperty.toColor(land.getColorCode()))) {
                    if (n.checkNeighbors(p, newMeshWithElevation, ocean) || n.checkNeighbors(p, newMeshWithElevation, lagoon)) {
                        tilePolygons2.add(Structs.Polygon.newBuilder(p).clearProperties().addProperties(beach.getColor()).addProperties(beach.getTileProperty()).addProperties(elevate.tileElevation()).build());
                    } else {
                        tilePolygons2.add(p);
                    }
                } else {
                    tilePolygons2.add(p);
                }
            }
        }

        Structs.Mesh newMesh2 = Structs.Mesh.newBuilder(newMesh).clearPolygons().addAllPolygons(tilePolygons2).build();
        Structs.Mesh newMesh3 = whittakerGen.biomeGen(newMesh2);
        return newMesh3;
    }

}
