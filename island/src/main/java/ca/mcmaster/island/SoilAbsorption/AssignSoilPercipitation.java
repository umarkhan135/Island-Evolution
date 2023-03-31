package ca.mcmaster.island.SoilAbsorption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.island.properties.propertyAssignment;
import ca.mcmaster.island.Tiles.Tile;
import ca.mcmaster.island.Tiles.lakeTile;
import ca.mcmaster.island.properties.AquiferProperty;
import ca.mcmaster.island.properties.ElevationProperty;
import ca.mcmaster.island.properties.PercipitationProperty;
import ca.mcmaster.island.properties.TemperatureProperty;
import ca.mcmaster.island.properties.TileProperty;

public class AssignSoilPercipitation implements propertyAssignment{
    private static final String AQUIFER = "aquifer";
    private static final String PERCIPITATION = "percipitation";
    private static final String TILE = "tile_type";
    private static final String ELEVATION = "elevation";

    @Override
    public Mesh assignProperty(Mesh m){
        AquiferProperty aquiferProperty = new AquiferProperty();
        ElevationProperty elevationProperty = new ElevationProperty();
        TileProperty tileProperty = new TileProperty();
        PercipitationProperty percipitationProperty = new PercipitationProperty();
        Property propPer;
        Property propHieght;
        Property propTile;
        List<Polygon> polygons = m.getPolygonsList();
        
        Integer nearby;
        Double finaltemp;
        ArrayList<Polygon> newPolygons = new ArrayList<>();
        for(Polygon p : polygons){
            Optional<String> hieght = elevationProperty.extract(p.getPropertiesList());
            Optional<String> tile = tileProperty.extract(p.getPropertiesList());
            Optional<Double> tempProp = percipitationProperty.extract(p.getPropertiesList());
            Optional<String> aquifer = aquiferProperty.extract(p.getPropertiesList());
            nearby = aquiferCheck(m, p);
            nearby += lakeCheck(m, p);
            if(tempProp.isPresent()){
                finaltemp = tempProp.get() + 7*nearby;  
                propPer = Property.newBuilder().setKey(PERCIPITATION).setValue(finaltemp.toString()).build();
                propHieght = Property.newBuilder().setKey(ELEVATION).setValue(hieght.get()).build();
                propTile = Property.newBuilder().setKey(TILE).setValue(tile.get()).build();
                newPolygons.add(Polygon.newBuilder(p).addProperties(propPer).addProperties(propHieght).addProperties(propTile).build());
            }else{
                newPolygons.add(p);
            }
        }
        Mesh newMesh = Mesh.newBuilder(m).clearPolygons().addAllPolygons(newPolygons).build();
        return newMesh;
    }

    public static Integer aquiferCheck(Mesh m , Polygon poly){
        AquiferProperty aquiferProperty = new AquiferProperty();
        Integer nearby = 0;
        List<Polygon> polygons = m.getPolygonsList();
        List<Integer> polyNieghborsID = poly.getNeighborIdxsList();
        if(aquiferProperty.extract(poly.getPropertiesList()).get().equals("true")){
            nearby += 3;
        }
        for(Integer i : polyNieghborsID){
            if(aquiferProperty.extract(polygons.get(i).getPropertiesList()).get().equals("true")){
                nearby += 2;
            }  
            for(Integer j : polygons.get(i).getNeighborIdxsList()){
                if(aquiferProperty.extract(polygons.get(j).getPropertiesList()).get().equals("true")){
                    nearby += 1;
                } 
            }
        }
        return nearby;
    }
    public static Integer lakeCheck(Mesh m , Polygon poly){
        TileProperty tileProperty = new TileProperty();
        Tile lake = new lakeTile();
        Integer nearby = 0;
        List<Polygon> polygons = m.getPolygonsList();
        List<Integer> polyNieghborsID = poly.getNeighborIdxsList();
        for(Integer i : polyNieghborsID){
            if(tileProperty.extract(polygons.get(i).getPropertiesList()).get().equals(lake.getTileProperty().getValue())){
                nearby += 2;
            }  
            for(Integer j : polygons.get(i).getNeighborIdxsList()){
                if(tileProperty.extract(polygons.get(j).getPropertiesList()).get().equals(lake.getTileProperty().getValue())){
                    nearby += 1;
                } 
            }
        }
        return nearby;
    }
}
