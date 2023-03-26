package ca.mcmaster.island;

import ca.mcmaster.island.properties.ColorProperty;
import ca.mcmaster.island.properties.TileProperty;
import ca.mcmaster.island.Tiles.*;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class neighborCheck {

    public boolean checkNeighbors(Structs.Polygon poly, Structs.Mesh m, Tile t) {

        String props = t.getColorCode();
        Color prop1 = new ColorProperty().toColor(props);

        List<Integer> n = poly.getNeighborIdxsList();
        List<Structs.Polygon> polys = m.getPolygonsList();

        for (Integer i : n) {
            Optional<Color> tile = new ColorProperty().extract(polys.get(i).getPropertiesList());
            if (tile.isPresent()) {
                Color prop2 = tile.get();
                if (prop2.equals(prop1)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public List<Structs.Polygon> neighborSet(Structs.Polygon poly, Structs.Mesh m, Tile TileType, Tile newTileType, Integer num){

        ArrayList<Structs.Polygon> polygons = new ArrayList<>();

        Structs.Property props = TileType.getTileProperty();
        String property = props.getValue();
        
        List<Integer> n = poly.getNeighborIdxsList();
        List<Integer> nt = n;

        List<Structs.Polygon> polyNieghbor = m.getPolygonsList();
        TileProperty tileProp = new TileProperty();
        Optional<String> tile = tileProp.extract(poly.getPropertiesList());
        polygons.add(Structs.Polygon.newBuilder(poly).addProperties(newTileType.getTileProperty()).addProperties(newTileType.getColor()).build());
        while (num>0){
            for(Integer i : n){
                nt = polyNieghbor.get(i).getNeighborIdxsList();
                tile = tileProp.extract(polyNieghbor.get(i).getPropertiesList());
                if(tile.get() == property && num == 1){
                    polygons.add(Structs.Polygon.newBuilder(polyNieghbor.get(i)).addProperties(newTileType.getTileProperty()).addProperties(newTileType.getColor()).build());
                }else{
                    for(Integer j : nt){
                        tile = tileProp.extract(polyNieghbor.get(j).getPropertiesList());
                        if(tile.get() == property){
                            polygons.add(Structs.Polygon.newBuilder(polyNieghbor.get(j)).addProperties(newTileType.getTileProperty()).addProperties(newTileType.getColor()).build());
                        }
                    }
                }
                
            }
            n = nt;
            --num;
        }
        return polygons;
    }
        
}
