package ca.mcmaster.island.Rivers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.island.properties.ElevationProperty;
import ca.mcmaster.island.properties.SegementElevationProperty;

public class SegmentElevation {
    private double elevation;

    public Structs.Mesh segmentElevationBuilder(Structs.Mesh m){

        List<Structs.Segment> segmentList = new ArrayList<>();

        for (int i = 0; i < m.getSegmentsCount(); i++) {
            findLowerElevationPolygon(i, m);
            segmentList.add(Structs.Segment.newBuilder(m.getSegments(i)).addProperties(getSegmentElevation()).build());
        }

        
        SegementElevationProperty elevate = new SegementElevationProperty();
        for (Structs.Segment s : segmentList){
            Optional<String> elevationString = elevate.extract(s.getPropertiesList());
            double elevation = Double.parseDouble(elevationString.get());
            if (elevation != 0){
                
            }

        }
        return Structs.Mesh.newBuilder(m).clearSegments().addAllSegments(segmentList).build();

    }
    

    private double findLowerElevationPolygon(int segmentIdx, Structs.Mesh mesh) {
        ElevationProperty elevate = new ElevationProperty();
        List<Structs.Polygon> polyList = new ArrayList<>();

        for (Structs.Polygon polygon : mesh.getPolygonsList()) {
            if (polygon.getSegmentIdxsList().contains(segmentIdx)) {
                polyList.add(polygon);  
            }
        }
        double elevation = 0;
        for (Structs.Polygon p : polyList){
            Optional<String> eleavtionString = elevate.extract(p.getPropertiesList());
            elevation += Double.parseDouble(eleavtionString.get());
        }
    
        this.elevation = (elevation)/polyList.size();
        
        return this.elevation;
    }
    
    public Property getSegmentElevation() {
        Property p = Property.newBuilder().setKey("segmentElevation").setValue(Double.toString(this.elevation)).build();
        return p;
    }



}
