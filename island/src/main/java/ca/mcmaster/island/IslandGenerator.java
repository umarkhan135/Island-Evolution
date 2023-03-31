package ca.mcmaster.island;

import ca.mcmaster.island.Tiles.*;
import ca.mcmaster.island.properties.TileProperty;
import ca.mcmaster.island.shapes.CircleIsland;
import ca.mcmaster.island.shapes.ShapeGenerator;
import ca.mcmaster.island.neighborCheck;
import ca.mcmaster.island.Aquifers.AquifersGen;
import ca.mcmaster.island.Aquifers.CircleAquifier;
import ca.mcmaster.island.Lakes.LakeGen;
import ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen.beachGen;

import ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen.whittakerGen;
import ca.mcmaster.island.Configuration.Configuration;
import ca.mcmaster.island.Elevation.Arctic;
import ca.mcmaster.island.Elevation.Canyon;
import ca.mcmaster.island.Elevation.RandomElevation;
import ca.mcmaster.island.Elevation.Volcano;
import ca.mcmaster.island.Elevation.elevation;
import ca.mcmaster.island.Rivers.MakeRiver;
import ca.mcmaster.island.distance;
import ca.mcmaster.island.Rivers.MakeRiver;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

import java.awt.Color;
import java.awt.geom.Path2D;
import java.util.*;
import ca.mcmaster.island.properties.*;


public class IslandGenerator {
   
    private Configuration config;
    private double max_x;
    private double max_y;
    private double center_x;
    private double center_y;
    private double radius;

    public IslandGenerator(Configuration config) {
        this.config = config;
    }

    public Structs.Mesh basic(Structs.Mesh m, Path2D s,elevation elevate, int aquiferNum, int numLakes, int rivers){

        MeshSize size = new MeshSize(m);
        max_x = size.getMaxX();
        max_y = size.getMaxY();
        center_x = max_x/2;
        center_y = max_y/2;
        if(max_x < max_y){
            this.radius = (max_x/5) * 2;
        }else{
            this.radius = (max_y/5) * 2;
        }
        radius = radius/2;

        whittakerGen wGen = new whittakerGen(config.getTemperature(), config.getPrecipitation(), config);
        ArrayList<Structs.Polygon> tilePolygons = new ArrayList<Structs.Polygon>();
        ArrayList<Structs.Polygon> poly = new ArrayList<>();
        AquifersGen aquifer = new CircleAquifier();
        LakeGen lakeGen;
        MakeRiver riverGen =  new MakeRiver();
        
        if(config.hasSeed()){
            lakeGen = new LakeGen(Long.parseLong(config.seed()));
        }else{
            lakeGen = new LakeGen();
        }


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
            elevate.getElevation(p, radius, m,s);
            Structs.Polygon newPolygon = Structs.Polygon.newBuilder(p).addProperties(elevate.tileElevation()).build();
            poly.add(newPolygon);
        }
        Structs.Mesh newMeshWithElevation = Structs.Mesh.newBuilder(newMesh).clearPolygons().addAllPolygons(poly).build();

        MakeRiver ppp;
        if(config.hasSeed()){
            ppp = new MakeRiver(Long.parseLong(config.seed()));
        }else{
            ppp = new MakeRiver();
        }

   
        Structs.Mesh lastMesh = riverGen.RiverGen(newMeshWithElevation,rivers);        
        Structs.Mesh newMeshWithAquifer = aquifer.meshWithAquifers(lastMesh.getPolygonsList(), aquiferNum, lastMesh);
        Structs.Mesh newMeshWithLakesV2 = lakeGen.meshWithLakes(newMeshWithAquifer.getPolygonsList(), numLakes, newMeshWithAquifer);
        Structs.Mesh newMesh2 = wGen.biomeGen(newMeshWithLakesV2, radius, Integer.parseInt(config.getBeachWidth()));

        


        return newMesh2;


    }
    private elevation createElevationProfile(String altitudeProfile) {
        switch (altitudeProfile.toLowerCase()) {
            case "volcano":
                return new Volcano();
            case "canyon":
                return new Canyon();
            case "arctic":
                return new Arctic();
            case "random":
                return new RandomElevation();
            default:
                throw new IllegalArgumentException("Invalid altitude profile: " + altitudeProfile);
        }
    }


    public Structs.Mesh lagoon(Structs.Mesh m, int aquiferNum, int numLakes) {

        elevation elevate = createElevationProfile(config.getAltitude());
        
        whittakerGen wGen = new whittakerGen(config.getTemperature(), config.getPrecipitation(), config);


        MeshSize size = new MeshSize(m);
        Structs.Vertex maxSize = size.findLargestXYVertex(m);

        ArrayList<Structs.Polygon> tilePolygons1 = new ArrayList<Structs.Polygon>();
        ArrayList<Structs.Polygon> tilePolygons2 = new ArrayList<Structs.Polygon>();

        neighborCheck n = new neighborCheck();
        distance dis = new distance();
        ColorProperty colorProperty = new ColorProperty();
       

        final double inner_radius = 125.0;
        final double outer_radius = 200.0;
        final double x = maxSize.getX();
        final double y = maxSize.getY();

        

        Tile land = new landTile();
        Tile ocean = new oceanTile();
        Tile lagoon = new lagoonTile();
        Tile beach = new beachTile();


        
        

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

            elevate.getElevation(p, radius, m);
            Structs.Polygon newPolygon = Structs.Polygon.newBuilder(p).addProperties(elevate.tileElevation()).build();
            poly.add(newPolygon);
        }
        Structs.Mesh newMeshWithElevation = Structs.Mesh.newBuilder(newMesh).clearPolygons().addAllPolygons(poly).build();

        for (Structs.Polygon p : poly) {
            
            elevate.getElevation(p, radius, m);
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

        Structs.Mesh newMesh3 = new CircleAquifier().meshWithAquifers(tilePolygons2, aquiferNum, newMesh2);

        Structs.Mesh newMesh4 = new LakeGen().meshWithLakes(newMesh3.getPolygonsList(), numLakes, newMesh3);

        Structs.Mesh newMesh5 = wGen.biomeGen(newMesh4, radius, Integer.parseInt(config.getBeachWidth()));

        Structs.Mesh finalMesh = new LakeGen().meshWithLakes(newMesh5.getPolygonsList(), numLakes, newMesh5);

        return finalMesh;

    }

}
