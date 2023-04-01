package ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.island.properties.AquiferProperty;
import ca.mcmaster.island.properties.ElevationProperty;
import ca.mcmaster.island.properties.PercipitationProperty;
import ca.mcmaster.island.properties.TemperatureProperty;
import ca.mcmaster.island.properties.TileProperty;
import ca.mcmaster.island.properties.propertyAssignment;

public class AssignTemperature implements propertyAssignment{
    private static int avgTemperature;
    private static final String PERCIPITATION = "percipitation";
    private static final String ELEVATION = "elevation";
    private static final String TILE = "tile_type";
    private static final String AQUIFER = "aquifer";
    private static final String TEMPERATURE = "temperature";
    public AssignTemperature(int avgTemperature){
        this.avgTemperature = avgTemperature;
    }

    @Override
    public Mesh assignProperty(Mesh m){
        ElevationProperty elevationProperty = new ElevationProperty();
        TileProperty tileProperty = new TileProperty();
        AquiferProperty aquiferProperty = new AquiferProperty();
        PercipitationProperty percipitationProperty = new PercipitationProperty();
        List<Polygon> polygons = m.getPolygonsList();
        ArrayList<Polygon> newPolygons = new ArrayList<>();
        temperatureCalculator tC = new temperatureCalculator();
        Property propPer;
        Property propHieght;
        Property propTile;
        Property propAquifer;
        Property propTemp;
        Double temperature;
        for(Polygon p : polygons){
            Optional<String> hieght = elevationProperty.extract(p.getPropertiesList());
            Optional<String> tile = tileProperty.extract(p.getPropertiesList());
            Optional<String> aquifer = aquiferProperty.extract(p.getPropertiesList());
            Optional<Double> percipitation = percipitationProperty.extract(p.getPropertiesList());
            if(tile.isPresent()&&(!(tile.get().equals("oceanTile") && tile.get().equals("lagoonTile") && tile.get().equals("lakeTile")))){
                int h = (int)Double.parseDouble(hieght.get());
                temperature = tC.hieghtTemp(h, avgTemperature);
                propPer = Property.newBuilder().setKey(PERCIPITATION).setValue(percipitation.get().toString()).build();
                propHieght = Property.newBuilder().setKey(ELEVATION).setValue(hieght.get()).build();
                propTile = Property.newBuilder().setKey(TILE).setValue(tile.get()).build();
                propAquifer = Property.newBuilder().setKey(AQUIFER).setValue(aquifer.get()).build();
                propTemp = Property.newBuilder().setKey(TEMPERATURE).setValue(temperature.toString()).build();
                newPolygons.add(Polygon.newBuilder(p).addProperties(propPer).addProperties(propHieght).addProperties(propTile).addProperties(propAquifer).addProperties(propTemp).build());
            }else{
                newPolygons.add(p);
            }
            
        }
        Mesh newMesh = Mesh.newBuilder(m).clearPolygons().addAllPolygons(newPolygons).build();
        return newMesh;
    }
    
}
