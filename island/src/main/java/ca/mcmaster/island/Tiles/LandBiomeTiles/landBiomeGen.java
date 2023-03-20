package ca.mcmaster.island.Tiles.LandBiomeTiles;

import java.util.ArrayList;
import java.util.Optional;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.island.Tiles.Tile;
import ca.mcmaster.island.Tiles.landTile;
import ca.mcmaster.island.properties.ColorProperty;
import ca.mcmaster.island.properties.TileProperty;

public class landBiomeGen {
    public Mesh randomLandGen(Mesh m){
        Tile land = new landTile();
        forestTile forest = new forestTile();
        TileProperty tileProperty = new TileProperty();
        ColorProperty colorProperty = new ColorProperty();
        ArrayList<Polygon> polygons = new ArrayList<>();
        for (Structs.Polygon p : m.getPolygonsList()) {
            Optional<String> tile = tileProperty.extract(p.getPropertiesList());
            if(tile.isPresent()){
                if(tile.get().equals(land.getTileProperty().getValue())){
                    polygons.add(Structs.Polygon.newBuilder(p).addProperties(forest.getColor()).build());
                }else{
                    polygons.add(p);
                }
            }
        }
        Structs.Mesh newMesh = Structs.Mesh.newBuilder(m).clearPolygons().addAllPolygons(polygons).build();

        return newMesh;
    }
}
