package ca.mcmaster.island.SoilAbsorption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.island.properties.propertyAssignment;
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
        ElevationProperty elevationProperty = new ElevationProperty();
        TileProperty tileProperty = new TileProperty();
        PercipitationProperty percipitationProperty = new PercipitationProperty();
        Property propPer;
        Property propHieght;
        Property propTile;
        List<Polygon> polygons = m.getPolygonsList();
        
        Integer distance;
        Double finaltemp;
        ArrayList<Polygon> newPolygons = new ArrayList<>();
        for(Polygon p : polygons){
            Optional<String> hieght = elevationProperty.extract(p.getPropertiesList());
            Optional<String> tile = tileProperty.extract(p.getPropertiesList());
            Optional<Double> tempProp = percipitationProperty.extract(p.getPropertiesList());
            distance = aquiferCheck(m, p);
            if(tempProp.isPresent()){
                //System.out.printf("Distance:%d\n",distance);
                finaltemp = tempProp.get();
                //System.out.printf("percipitation after :%f\n\n",finaltemp);
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
        Integer maxDistance = 3;
        Integer distance = 0;
        List<Polygon> polygons = m.getPolygonsList();
        List<Integer> polyNieghborsID = poly.getNeighborIdxsList();
        List<Integer> nieghborsID = polyNieghborsID;
        ArrayList<Integer> tempNieghbors = new ArrayList<>();
        Integer i = maxDistance;
        Boolean T = true;
        if(aquiferProperty.extract(poly.getPropertiesList()).get().equals(T.toString())){
            distance+=maxDistance;
        }
        while (i > 1){
            i--;
            tempNieghbors = new ArrayList<>();
            for(Integer j : polyNieghborsID){
                Optional<String> property1 = aquiferProperty.extract(polygons.get(j).getPropertiesList());
                if(property1.isPresent() && property1.get().equals(T.toString())){
                    distance += i;
                }
                nieghborsID = polygons.get(i).getNeighborIdxsList();
                for(Integer k : nieghborsID){
                    tempNieghbors.add(i);
                    Optional<String> property2 = aquiferProperty.extract(polygons.get(k).getPropertiesList());
                    if(property2.isPresent() && property2.get().equals(T.toString())){
                        distance += (i + 1);
                        System.out.printf("Distance:%d\n",distance);
                    }
                }
            }
            polyNieghborsID = tempNieghbors;
        }
        return distance;
    }
}
