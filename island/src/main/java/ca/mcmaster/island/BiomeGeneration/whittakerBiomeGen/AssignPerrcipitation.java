package ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.locationtech.jts.algorithm.HCoordinate;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.island.properties.propertyAssignment;
import ca.mcmaster.island.properties.TileProperty;
import ca.mcmaster.island.neighborCheck;
import ca.mcmaster.island.properties.AquiferProperty;
import ca.mcmaster.island.properties.ElevationProperty;
import ca.mcmaster.island.properties.PercipitationProperty;

public class AssignPerrcipitation implements propertyAssignment{
    private static int avgPercipitation;
    private static final String PERCIPITATION = "percipitation";
    private static final String ELEVATION = "elevation";
    private static final String TILE = "tile_type";
    private static final String AQUIFER = "aquifer";
    public AssignPerrcipitation(int avgPercipitation){
        this.avgPercipitation = avgPercipitation;
    }
    @Override
    public Mesh assignProperty(Mesh m){
        ElevationProperty elevationProperty = new ElevationProperty();
        TileProperty tileProperty = new TileProperty();
        AquiferProperty aquiferProperty = new AquiferProperty();
        Property propPer;
        Property propHieght;
        Property propTile;
        Property propAquifer;
        percipitationCalculator pC = new percipitationCalculator();
        List<Polygon> polygons = m.getPolygonsList();
        ArrayList<Polygon> newPolygons = new ArrayList<>();
        Integer percipitation;
        for (Polygon p : polygons) {
            Optional<String> hieght = elevationProperty.extract(p.getPropertiesList());
            Optional<String> tile = tileProperty.extract(p.getPropertiesList());
            Optional<String> aquifer = aquiferProperty.extract(p.getPropertiesList());
            System.out.printf("Aquifer:"+aquifer.get()+"\n\n");
            if(hieght.isPresent() && tile.isPresent()){
                int h = (int)Double.parseDouble(hieght.get());
                percipitation = pC.hieghtPercipitation(h, avgPercipitation);
                propPer = Property.newBuilder().setKey(PERCIPITATION).setValue(percipitation.toString()).build();
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
}
