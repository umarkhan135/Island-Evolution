package ca.mcmaster.island.Tiles.LandBiomeTiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.island.Tiles.Tile;
import ca.mcmaster.island.Tiles.landTile;
import ca.mcmaster.island.properties.ColorProperty;
import ca.mcmaster.island.properties.TileProperty;


public class landBiomeGen {
    public Mesh randomLandGen(Mesh m){
        Random random = new Random();
        Tile land = new landTile();
        forestTile forest = new forestTile();
        savannaTile savanna = new savannaTile();
        TileProperty tileProperty = new TileProperty();
        ColorProperty colorProperty = new ColorProperty();
        ArrayList<Polygon> polygons = new ArrayList<>();
        List<Tile> tileTypes= new ArrayList<>();
        tileTypes.add(forest);
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
