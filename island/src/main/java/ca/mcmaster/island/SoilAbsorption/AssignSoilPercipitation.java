package ca.mcmaster.island.SoilAbsorption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.island.properties.propertyAssignment;
import ca.mcmaster.island.Rivers.RiverColor;
import ca.mcmaster.island.Rivers.isRiver;
import ca.mcmaster.island.Tiles.Tile;
import ca.mcmaster.island.Tiles.lagoonTile;
import ca.mcmaster.island.Tiles.lakeTile;
import ca.mcmaster.island.Tiles.landTile;
import ca.mcmaster.island.Tiles.oceanTile;
import ca.mcmaster.island.properties.AquiferProperty;
import ca.mcmaster.island.properties.ColorProperty;
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
        Property propAquifer;
        List<Polygon> polygons = m.getPolygonsList();
        Tile land = new landTile();
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
            nearby += oceanCheck(m, p);
            nearby += lagoonCheck(m, p);
            nearby += riverCheck(m, p);
            if(tile.get().equals(land.getTileProperty().getValue())){
                finaltemp = tempProp.get() + 3*nearby;  
                propPer = Property.newBuilder().setKey(PERCIPITATION).setValue(finaltemp.toString()).build();
                propHieght = Property.newBuilder().setKey(ELEVATION).setValue(hieght.get()).build();
                propTile = Property.newBuilder().setKey(TILE).setValue(tile.get()).build();
                propAquifer = Property.newBuilder().setKey(AQUIFER).setValue(aquifer.get()).build();
                newPolygons.add(Polygon.newBuilder(p).addProperties(propPer).addProperties(propHieght).addProperties(propTile).addProperties(propAquifer).build());
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
    public static Integer oceanCheck(Mesh m , Polygon poly){
        TileProperty tileProperty = new TileProperty();
        Tile ocean = new oceanTile();
        
        Integer nearby = 0;
        List<Polygon> polygons = m.getPolygonsList();
        List<Integer> polyNieghborsID = poly.getNeighborIdxsList();
        for(Integer i : polyNieghborsID){
            if(tileProperty.extract(polygons.get(i).getPropertiesList()).get().equals(ocean.getTileProperty().getValue())){
                nearby += 1;
            }  
            for(Integer j : polygons.get(i).getNeighborIdxsList()){
                if(tileProperty.extract(polygons.get(j).getPropertiesList()).get().equals(ocean.getTileProperty().getValue())){
                    nearby += 1;
                } 
            }
        }
        return nearby;
    }
    public static Integer lagoonCheck(Mesh m , Polygon poly){
        TileProperty tileProperty = new TileProperty();
        Tile lagoon = new lagoonTile();
        
        Integer nearby = 0;
        List<Polygon> polygons = m.getPolygonsList();
        List<Integer> polyNieghborsID = poly.getNeighborIdxsList();
        for(Integer i : polyNieghborsID){
            if(tileProperty.extract(polygons.get(i).getPropertiesList()).get().equals(lagoon.getTileProperty().getValue())){
                nearby += 2;
            }  
            for(Integer j : polygons.get(i).getNeighborIdxsList()){
                if(tileProperty.extract(polygons.get(j).getPropertiesList()).get().equals(lagoon.getTileProperty().getValue())){
                    nearby += 1;
                } 
            }
        }
        return nearby;
    }
    public static Integer riverCheck(Mesh m , Polygon poly){
        List<Segment> segmentList= m.getSegmentsList();
        List<Vertex> vertexList = m.getVerticesList();
        List<Integer> polysegment = poly.getSegmentIdxsList();
        ColorProperty colorProperty = new ColorProperty();
        RiverColor river = new RiverColor();
        Integer nearby = 0;
        Double rx1;
        Double ry1;
        Double rx2;
        Double ry2;
        Double px1;
        Double py1;
        Double px2;
        Double py2;
            for(Segment s : segmentList){
                if(river.getSegmentSegmentColor().getValue().equals(colorProperty.extract(s.getPropertiesList()).get().toString())){
                    rx1 = vertexList.get(s.getV1Idx()).getX();
                    ry1 = vertexList.get(s.getV1Idx()).getX();
                    rx2 = vertexList.get(s.getV2Idx()).getX();
                    ry2 = vertexList.get(s.getV2Idx()).getX();
                    System.out.println("river");
                    for(Integer i : polysegment){
                        px1 = vertexList.get(segmentList.get(i).getV1Idx()).getX();
                        py1 = vertexList.get(segmentList.get(i).getV1Idx()).getX();
                        px2 = vertexList.get(segmentList.get(i).getV2Idx()).getX();
                        py2 = vertexList.get(segmentList.get(i).getV2Idx()).getX();
                        if(rx1==px1 && rx2==px2 && ry1==py1 && ry2==py2){
                            nearby +=2;
                        }
                    }
                }
            }
            return nearby;
        }
        
    
}
