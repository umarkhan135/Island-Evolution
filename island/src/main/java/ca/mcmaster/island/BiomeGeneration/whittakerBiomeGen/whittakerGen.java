package ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen;
import java.util.ArrayList;

import java.util.Optional;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.island.neighborCheck;
import ca.mcmaster.island.Tiles.Tile;
import ca.mcmaster.island.Tiles.beachTile;
import ca.mcmaster.island.Tiles.landTile;
import ca.mcmaster.island.Tiles.oceanTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.CanyonTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.MountainTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.fieldTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.forestTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.rainForest;


import ca.mcmaster.island.properties.ElevationProperty;
import ca.mcmaster.island.properties.TileProperty;

public class whittakerGen {
    private static int temp; 
    private static int per;
    whittakerPercipitationType WPT = new whittakerPercipitationType();
    whittakerTemperatureType WTT = new whittakerTemperatureType();

    public whittakerGen(String temp, String per){
        this.temp = WTT.tT(temp);
        this.per = WPT.pT(per);
    }
    
    public Mesh biomeGen(Mesh m, double radius){
        Tile land = new landTile();
        Tile ocean = new oceanTile();
        Tile beach = new beachTile();
        rainForest rainforest = new rainForest();
        fieldTile field = new fieldTile();
        forestTile forest = new forestTile();
        MountainTile mountain = new MountainTile();
        CanyonTile canyon = new CanyonTile();
    
        TileProperty tileProperty = new TileProperty();
        ElevationProperty elevationProperty = new ElevationProperty();
        ArrayList<Polygon> polygons = new ArrayList<>();
        percipitationCalculator pC = new percipitationCalculator();
        temperatureCalculator tP = new temperatureCalculator();
        neighborCheck nC = new neighborCheck();
        double temperature;
        double percipitation;
        for (Structs.Polygon p : m.getPolygonsList()) {
            Optional<String> tile = tileProperty.extract(p.getPropertiesList());
            Optional<String> hieght = elevationProperty.extract(p.getPropertiesList());
            if(tile.isPresent() && hieght.isPresent()){
                int h = (int)Double.parseDouble(hieght.get());
                temperature = tP.hieghtTemp(h, this.temp);
                percipitation = pC.hieghtPercipitation(h, this.per);
                
                if(tile.get().equals(land.getTileProperty().getValue())){
                    if(nC.checkNeighbors(p, m, ocean)){
                        polygons.add(Structs.Polygon.newBuilder(p).addProperties(beach.getTileProperty()).addProperties(beach.getColor()).build());
                    }else{
                        switch(compareP(percipitation, h)){
                            case "tropical": polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(rainforest.getColor(temperature)).build());break;
                            case "dry" : polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(field.getColor(temperature)).build());break; 
                            case "mountain":polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(mountain.getColor(temperature)).build());break;
                            case "canyon":polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(canyon.getColor(h,temperature, radius)).build());break;
                            default:polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(forest.getColor(temperature)).build());break;
                        }
                    }
                }else{
                    polygons.add(p);
                }
            }
        }
        Structs.Mesh newMesh = Structs.Mesh.newBuilder(m).clearPolygons().addAllPolygons(polygons).build();

        return newMesh;
    }
    private static String compareP(double percipitation, int h) {
        if (h>65){
            return "mountain";
        }
        if(h<-85){
            return "canyon";
        }
        
        if (percipitation< 100){
                return "dry";
        }
        else if(percipitation>200){
            return "tropical";
        }
        return "temperate";
    }
}
