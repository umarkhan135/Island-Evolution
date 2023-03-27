package ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.island.neighborCheck;
import ca.mcmaster.island.Tiles.beachTile;
import ca.mcmaster.island.Tiles.lagoonTile;
import ca.mcmaster.island.Tiles.landTile;
import ca.mcmaster.island.Tiles.oceanTile;
import ca.mcmaster.island.properties.TileProperty;

public class beachGen {
    Random random = new Random();
    public List<Structs.Polygon> createbeach(Mesh m, Integer num){
        ArrayList<Structs.Polygon> polygons2 = new ArrayList<>();
        num--;
        landTile land = new landTile();
        beachTile beach = new beachTile();
        oceanTile ocean = new oceanTile();
        lagoonTile lagoon = new lagoonTile();
        neighborCheck nC = new neighborCheck();
        TileProperty tileProperty = new TileProperty();
        for (Structs.Polygon p : m.getPolygonsList()){
            
            Optional<String> tile = tileProperty.extract(p.getPropertiesList());
            if(tile.get().equals(land.getTileProperty().getValue()) ){
                if(nC.checkNeighbors(p, m, ocean)|| nC.checkNeighbors(p, m, lagoon)){
                    if(random.nextBoolean() && random.nextBoolean()){
                        for( Structs.Polygon poly : nC.neighborSet(p, m, land, beach, num)){
                            if(random.nextBoolean()){
                                polygons2.add(poly);   
                            }   
                            
                        }
                    }
                }
            }
        }
        return polygons2;
    }
    public List<Structs.Polygon> createbeach(Mesh m, Integer num, int seed){
        random.setSeed(seed);
        ArrayList<Structs.Polygon> polygons2 = new ArrayList<>();
        num--;
        landTile land = new landTile();
        beachTile beach = new beachTile();
        oceanTile ocean = new oceanTile();
        lagoonTile lagoon = new lagoonTile();
        neighborCheck nC = new neighborCheck();
        TileProperty tileProperty = new TileProperty();
        for (Structs.Polygon p : m.getPolygonsList()){
            
            Optional<String> tile = tileProperty.extract(p.getPropertiesList());
            if(tile.get().equals(land.getTileProperty().getValue()) ){
                if(nC.checkNeighbors(p, m, ocean) || nC.checkNeighbors(p, m, lagoon)){
                    if(random.nextBoolean() && random.nextBoolean()){
                        for( Structs.Polygon poly : nC.neighborSet(p, m, land, beach, num)){
                            if(random.nextBoolean()){
                                polygons2.add(poly);   
                            }
                            
                        }
                    }
                }
            }
        }
        return polygons2;
    }
}
