package ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.island.Configuration.Configuration;
import ca.mcmaster.island.Tiles.Tile;
import ca.mcmaster.island.Tiles.landTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.RainForestTypes.rainForest;
import ca.mcmaster.island.Tiles.LandBiomeTiles.RainForestTypes.tropicalRainForest;
import ca.mcmaster.island.Tiles.LandBiomeTiles.fieldTypes.fieldTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.fieldTypes.savannaTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.fieldTypes.tundraTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.forestTypes.forestTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.forestTypes.tiagaTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.forestTypes.tropicalForestTile;
import ca.mcmaster.island.properties.ColorProperty;
import ca.mcmaster.island.properties.TileProperty;
import ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen.whittakerHumidityType;
public class whittakerGen {
    private static int temp; 
    private static int hum;
    
    public whittakerGen(String temp, String hum){
        this.temp = whittakerTemperatureType.MILD.create(temp).getTemperature();
        this.hum = whittakerHumidityType.TEMPERATE.create(hum).getHumidity();
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
    
        TileProperty tileProperty = new TileProperty();
        ArrayList<Polygon> polygons = new ArrayList<>();
        for (Structs.Polygon p : m.getPolygonsList()) {
            Optional<String> tile = tileProperty.extract(p.getPropertiesList());
            if(tile.isPresent()){
                if(tile.get().equals(land.getTileProperty().getValue())){
                    switch(hum){
                        case 50: 
                        switch(temp){
                            case -5: polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(tundra.getColor()).build());break;
                            case 25: polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(savanna.getColor()).build());break;
                            default:polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(field.getColor()).build());break;
                        };break;
                        case 300 : 
                        switch(temp){
                            case -5: polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(taiga.getColor()).build());break;
                            case 25: polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(tropicalForest.getColor()).build());break;
                            default:polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(forest.getColor()).build());break;
                        };break;
                        default:
                        switch(temp){
                            case 25: polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(tropicalRain.getColor()).build());break;
                            case -5: polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(taiga.getColor()).build());break;
                            default:polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(rainforest.getColor()).build());break;
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

}
