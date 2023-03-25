package ca.mcmaster.island.Rivers;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.island.Tiles.oceanTile;
import ca.mcmaster.island.properties.ColorProperty;
import ca.mcmaster.island.properties.ElevationProperty;
import ca.mcmaster.island.properties.SegementElevationProperty;

public class MakeRiver {

    private String lightBlueColorString = "255, 255, 255";

    public void PlayAround(Structs.Mesh m){


        SegmentElevation seg = new SegmentElevation();
        Structs.Mesh newMesh = seg.segmentElevationBuilder(m);

        Structs.Polygon pol;
        Optional<Color> polygonColor;
        
        Random random = new Random();
        int rand;
        ColorProperty colorProperty = new ColorProperty();
        String oceanColorString = new oceanTile().getColor().getValue();
        Color oceanColor = colorProperty.toColor(oceanColorString);
        

        do{
            rand = random.nextInt(newMesh.getPolygonsCount());
            pol = newMesh.getPolygons(rand);
            polygonColor = colorProperty.extract(pol.getPropertiesList());
        }while (polygonColor.isPresent() && polygonColor.get().equals(oceanColor));

        List<Structs.Segment> riverPath = new ArrayList<>();

        int currentLowestSegmentIdx = pol.getSegmentIdxs(random.nextInt(pol.getSegmentIdxsCount()));
        Structs.Segment currentLowestSegment = newMesh.getSegments(currentLowestSegmentIdx);
        //System.out.println(currentLowestSegment);
        Structs.Segment currentSegment;
        riverPath.add(currentLowestSegment);
        boolean run = true;
        do{
            int[] array = segmentNeighbors(currentLowestSegmentIdx, newMesh);
            currentSegment = getLowestSegmentNeighbor(currentLowestSegment, array, newMesh);
            if (currentLowestSegment.equals(currentSegment)){
                run = false;
            }else{
                riverPath.add(currentSegment);
                currentLowestSegment = currentSegment;
                currentLowestSegmentIdx = getSegmentIdx(currentSegment, newMesh);
            }
            
        }while(run);
        System.out.println(riverPath);
        
        
    }

    private int getSegmentIdx(Structs.Segment s, Structs.Mesh m){
        for (int i = 0; i < m.getSegmentsCount(); i++) {
            Structs.Segment currentSegment = m.getSegments(i);
            if ((currentSegment.getV1Idx() == s.getV1Idx() && currentSegment.getV2Idx() == s.getV2Idx()) ||
                (currentSegment.getV1Idx() == s.getV2Idx() && currentSegment.getV2Idx() == s.getV1Idx())) {
                return i;
            }
        }
        return -1;
    }

    private int[] segmentNeighbors (int segment, Structs.Mesh mesh){
        int segments[] = new int[4];
        int counter = 0;

        for (int i = 0; i < mesh.getSegmentsList().size(); i++) {
            Structs.Segment ss = mesh.getSegmentsList().get(i);
            if ((ss.getV1Idx() == mesh.getSegments(segment).getV1Idx() || ss.getV2Idx() == mesh.getSegments(segment).getV1Idx() || ss.getV1Idx() == mesh.getSegments(segment).getV2Idx() || ss.getV2Idx() == mesh.getSegments(segment).getV2Idx()) && i != segment){
                segments[counter] = i;
                counter++;
            }
        }
        return segments;
    }

    private Structs.Segment getLowestSegmentNeighbor(Structs.Segment s, int[] segmentArray, Structs.Mesh m){
        
        SegementElevationProperty elevate = new SegementElevationProperty();
        Structs.Segment lowestSegment = s;
        for (int i : segmentArray){
            Structs.Segment currentSegment = m.getSegments(i);

            Optional<String> currentLowest = elevate.extract(lowestSegment.getPropertiesList());
            Optional<String> current = elevate.extract(currentSegment.getPropertiesList());
            
            double elevation1 = Double.parseDouble(currentLowest.get());
            double elevation2 = Double.parseDouble(current.get());
            
            Random random = new Random();
            int ran = random.nextInt(3);

            if (elevation2 < elevation1){
                lowestSegment = currentSegment;
            }
            
        }
        
        return lowestSegment;
    }


    
    

    private Structs.Polygon getMaxElevationPolygon(Structs.Mesh mesh) {
        ElevationProperty elevationProperty = new ElevationProperty();
        Structs.Polygon maxElevationPolygon = mesh.getPolygons(10);
        double maxElevation = Double.NEGATIVE_INFINITY;
    
        for (Structs.Polygon polygon : mesh.getPolygonsList()) {
            Optional<String> elevationString = elevationProperty.extract(polygon.getPropertiesList());
    
            if (elevationString.isPresent()) {
                double elevation = Double.parseDouble(elevationString.get());
    
                if (elevation > maxElevation) {
                    maxElevation = elevation;
                    maxElevationPolygon = polygon;
                }
            }
        }
    
        return maxElevationPolygon;
    }


    
}
