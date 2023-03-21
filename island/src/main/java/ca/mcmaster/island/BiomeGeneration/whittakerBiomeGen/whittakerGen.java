package ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen;
import java.util.ArrayList;

import java.util.Optional;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

import ca.mcmaster.island.Tiles.Tile;
import ca.mcmaster.island.Tiles.landTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.CanyonTypes.CanyonTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.MountainTypes.MountainTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.MountainTypes.SnowyMountainTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.RainForestTypes.rainForest;
import ca.mcmaster.island.Tiles.LandBiomeTiles.RainForestTypes.tropicalRainForest;
import ca.mcmaster.island.Tiles.LandBiomeTiles.fieldTypes.fieldTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.fieldTypes.savannaTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.fieldTypes.tundraTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.forestTypes.forestTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.forestTypes.tiagaTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.forestTypes.tropicalForestTile;

import ca.mcmaster.island.properties.ElevationProperty;
import ca.mcmaster.island.properties.TileProperty;

public class whittakerGen {
    private static int temp; 
    private static int per;
    
    public whittakerGen(String temp, String per){
        this.temp = whittakerTemperatureType.MILD.create(temp).getTemperature();
        this.per = whittakerPercipitationType.TEMPERATE.create(per).getHumidity();
    }
    public static Mesh biomeGen(Mesh m){
        Tile land = new landTile();
        Tile forest = new forestTile();
        Tile taiga = new tiagaTile();
        Tile tropicalForest = new tropicalForestTile();
        Tile rainforest = new rainForest();
        Tile tropicalRain = new tropicalRainForest();
        Tile field = new fieldTile();
        Tile savanna = new savannaTile();
        Tile tundra = new tundraTile();
        Tile mountain = new MountainTile();
        Tile snowyMountain = new SnowyMountainTile();
        CanyonTile canyon = new CanyonTile();
    
        TileProperty tileProperty = new TileProperty();
        ElevationProperty elevationProperty = new ElevationProperty();
        ArrayList<Polygon> polygons = new ArrayList<>();
        percipitationCalculator pC = new percipitationCalculator();
        temperatureCalculator tP = new temperatureCalculator();
        double temperature;
        double percipitation;
        for (Structs.Polygon p : m.getPolygonsList()) {
            Optional<String> tile = tileProperty.extract(p.getPropertiesList());
            Optional<String> hieght = elevationProperty.extract(p.getPropertiesList());
            if(tile.isPresent() && hieght.isPresent()){
                int h = (int)Double.parseDouble(hieght.get());
                temperature = tP.hieghtTemp(h, temp);
                percipitation = pC.hieghtPercipitation(h, per);
                if(tile.get().equals(land.getTileProperty().getValue())){
                    switch(compareP(percipitation, h)){
                        case 1: 
                        switch(compareT(temperature)){
                            case -1: polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(tundra.getColor()).build());break;
                            case 1: polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(savanna.getColor()).build());break;
                            default:polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(field.getColor()).build());break;
                        };break;
                        case -1 : 
                        switch(compareT(temperature)){
                            case -1: polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(taiga.getColor()).build());break;
                            case 1: polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(tropicalRain.getColor()).build());break;
                            default:polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(rainforest.getColor()).build());break;
                        };break;
                        case 2:
                        switch(compareT(temperature)){
                            case -1: polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(snowyMountain.getColor()).build());break;
                            case 1: polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(mountain.getColor()).build());break;
                            default:polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(mountain.getColor()).build());break;
                        };break;
                        case -2:
                        switch(compareT(temperature)){
                            case -1: polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(snowyMountain.getColor()).build());break;
                            case 1: polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(canyon.getColor(h)).build());break;
                            default:polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(canyon.getColor(h)).build());break;
                        };break;
                        default:
                        switch(compareT(temperature)){
                            case 1: polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(tropicalForest.getColor()).build());break;
                            case -1: polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(taiga.getColor()).build());break;
                            default:polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(forest.getColor()).build());break;
                        };break;
                    }
                }else{
                    polygons.add(p);
                }
            }
        }
        Structs.Mesh newMesh = Structs.Mesh.newBuilder(m).clearPolygons().addAllPolygons(polygons).build();

        return newMesh;
    }
    private static int compareP(double percipitation, int h) {
        if (h>75){
            return 2;
        }
        if(h<-85){
            return -2;
        }
        
        if (percipitation< 100){
                return 1;
        }
        else if(250<percipitation){
            return -1;
        }
        return 0;
    }
    private static int compareT(double temperature){
        
        if(temperature>20){
            return 1;
        }else if(temperature<0){
            return -1;
        }
        return 0;
    }

}
