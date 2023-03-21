package ca.mcmaster.island.BiomeGeneration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.island.Tiles.Tile;
import ca.mcmaster.island.Tiles.LandTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.RainForestTypes.rainForest;
import ca.mcmaster.island.Tiles.LandBiomeTiles.RainForestTypes.tropicalRainForest;
import ca.mcmaster.island.Tiles.LandBiomeTiles.fieldTypes.fieldTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.fieldTypes.savannaTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.fieldTypes.tundraTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.forestTypes.forestTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.forestTypes.tiagaTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.forestTypes.tropicalForestTile;
import ca.mcmaster.island.properties.TileProperty;


public class randomBiomeGen {
    public Mesh randomLandGen(Mesh m){
        Random random = new Random();
        Tile land = new LandTile();
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
        List<Tile> tileTypes= new ArrayList<>();
        tileTypes.add(forest);
        tileTypes.add(field);
        tileTypes.add(rainforest);
        tileTypes.add(taiga);
        tileTypes.add(tropicalForest);
        tileTypes.add(tropicalRain);
        tileTypes.add(tundra);
        tileTypes.add(savanna);
        Property temp;

        for (Structs.Polygon p : m.getPolygonsList()) {
            Optional<String> tile = tileProperty.extract(p.getPropertiesList());
            if(tile.isPresent()){
                if(tile.get().equals(land.getTileProperty().getValue())){
                    temp=tileTypes.get(random.nextInt(tileTypes.size())).getColor();
                    polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(temp).build());
                }else{
                    polygons.add(p);
                }
            }
        }
        Structs.Mesh newMesh = Structs.Mesh.newBuilder(m).clearPolygons().addAllPolygons(polygons).build();

        return newMesh;
    }
}
