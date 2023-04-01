package ca.mcmaster.island.Rivers;

import java.awt.Color;
import java.awt.geom.Path2D;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.island.neighborCheck;
import ca.mcmaster.island.Tiles.Tile;
import ca.mcmaster.island.Tiles.oceanTile;
import ca.mcmaster.island.properties.ColorProperty;
import ca.mcmaster.island.properties.ElevationProperty;
import ca.mcmaster.island.properties.NumOfRiversProperty;
import ca.mcmaster.island.properties.SegementElevationProperty;
import ca.mcmaster.island.properties.riverProperty;

public class MakeRiver {

    private Random random;

    public MakeRiver(){
        random = new Random();
    }
    public MakeRiver(Long seed){
        random = new Random(seed);
    }

    

    public Structs.Mesh RiverGen(Structs.Mesh m, int numberOfRivers) {
        neighborCheck neighbors = new neighborCheck();
        if(numberOfRivers == 0){
            return m;
        }
        
        SegmentElevation seg = new SegmentElevation();
        Structs.Mesh newMesh = seg.segmentElevationBuilder(m);
    
        List<Structs.Segment> riverSegments = new ArrayList<>();
        NumOfRiversProperty numRiversProperty = new NumOfRiversProperty();
        List<Structs.Segment> previousRiverSegments = new ArrayList<>();
        List<Structs.Segment> lastRiverSeg = new ArrayList<>();
        List<Structs.Segment> lastriverSegments = new ArrayList<>();



        for (int i = 0; i < numberOfRivers; i++) {
            RiverColor riverColor = new RiverColor();
            isRiver riverBool = new isRiver();
            numRiver riverNum = new numRiver();
            
    
            List<Structs.Segment> currentRiverFlow = riverList(newMesh);
            lastRiverSeg.add(currentRiverFlow.get(currentRiverFlow.size() - 1));
            riverSegments.addAll(currentRiverFlow);

            List<Structs.Segment> moreThan1 = new ArrayList<>();
            for (Structs.Segment segm : previousRiverSegments) {
                if (containsSegment(currentRiverFlow, segm)) {
                    moreThan1.add(segm);
                }
            }

            previousRiverSegments.addAll(currentRiverFlow);

            int round = i+1;
    
            List<Structs.Segment> updatedSegments = new ArrayList<>();
    
            for (Structs.Segment s : newMesh.getSegmentsList()) {
                
                if (containsSegment(riverSegments, s)) {  
                    if (round > 1 && Integer.parseInt(numRiversProperty.extract(s.getPropertiesList()).get()) >= 1 && containsSegment(moreThan1, s)){
                        updatedSegments.add(Structs.Segment.newBuilder(s).addProperties(riverBool.isARiver()).addProperties(riverNum.NumberOfRivers(Integer.parseInt(numRiversProperty.extract(s.getPropertiesList()).get()) + 1)).build());
                    }else{
                        if (round > 1){
                            if (Integer.parseInt(numRiversProperty.extract(s.getPropertiesList()).get()) >= 1){
                                updatedSegments.add(Structs.Segment.newBuilder(s).addProperties(riverBool.isARiver()).addProperties(riverNum.NumberOfRivers(Integer.parseInt(numRiversProperty.extract(s.getPropertiesList()).get()))).build());

                            }else{
                                updatedSegments.add(Structs.Segment.newBuilder(s).addProperties(riverBool.isARiver()).addProperties(riverNum.NumberOfRivers(1)).build());

                            }
                        }else{
                            updatedSegments.add(Structs.Segment.newBuilder(s).addProperties(riverColor.getSegmentSegmentColor()).addProperties(riverBool.isARiver()).addProperties(riverNum.NumberOfRivers(1)).build());

                        }
                    }
                } else {
                    if(round > 1){
                        updatedSegments.add(Structs.Segment.newBuilder(s).addProperties(riverNum.NumberOfRivers(0)).build());

                    }else{
                        updatedSegments.add(Structs.Segment.newBuilder(s).addProperties(riverColor.noColor()).addProperties(riverBool.notRiver()).addProperties(riverNum.NumberOfRivers(0)).build());

                    }
                }
            }
            lastriverSegments = updatedSegments;
            newMesh = Structs.Mesh.newBuilder(newMesh).clearSegments().clearPolygons().addAllPolygons(newMesh.getPolygonsList()).addAllSegments(updatedSegments).build();
        }
        
        
        List<Structs.Polygon> lakes = getPolygonWithSegments(lastRiverSeg, newMesh);
        List<Structs.Polygon> last = new ArrayList<>();

        for (Structs.Polygon pols : newMesh.getPolygonsList()){
            last.add(pols);
        }
        
        for (Structs.Polygon poly : lakes){
            if(!neighbors.checkNeighbors(poly, m, new oceanTile())){
                Structs.Polygon newPolygon = Structs.Polygon.newBuilder(poly).addProperties(lakeType()).addProperties(lakeColor()).build();
            last.add(newPolygon);
            }
        }

        Structs.Mesh newMesh2 = Structs.Mesh.newBuilder(newMesh).clearPolygons().clearSegments().addAllPolygons(last).addAllSegments(lastriverSegments).build();

        return newMesh2;
    }


    public Property lakeType() {
        Property c = Property.newBuilder().setKey("tile_type").setValue("Lake").build();
        return c; 
    }

    public Property lakeColor() {

        Property c = Property.newBuilder().setKey("rgb_color").setValue("0,141,151").build();
        return c;
        
    }
    private List<Structs.Polygon> getPolygonWithSegments(List<Structs.Segment> segments, Structs.Mesh m){
        List<Structs.Polygon> polygonsWithSegments = new ArrayList<>();
        ColorProperty colorProperty = new ColorProperty();
        String oceanColorString = new oceanTile().getColor().getValue();
        Color oceanColor = colorProperty.toColor(oceanColorString);

        for (Structs.Segment s : segments) {
            int segmentIndex = getSegmentIdx(s, m);
            if (segmentIndex != -1) {
                for (Structs.Polygon poly : m.getPolygonsList()) {
                    Optional<Color> polygonColor = colorProperty.extract(poly.getPropertiesList());
                    if (poly.getSegmentIdxsList().contains(segmentIndex)) {
                        if (!polygonsWithSegments.contains(poly)) {
                            if (polygonColor.isPresent() && !polygonColor.get().equals(oceanColor)){
                                polygonsWithSegments.add(poly);
                            }
                        }
                        break;
                    }
                }
            }
        }
        return polygonsWithSegments;
    }
    
    


    private boolean containsSegment(List<Structs.Segment> list, Structs.Segment segment) {
        for (Structs.Segment s : list) {
            if ((s.getV1Idx() == segment.getV1Idx() && s.getV2Idx() == segment.getV2Idx()) ||
                (s.getV1Idx() == segment.getV2Idx() && s.getV2Idx() == segment.getV1Idx())) {
                return true;
            }
        }
        return false;
    }

    

    public List<Structs.Segment> riverList(Structs.Mesh newMesh){
        
        Structs.Polygon pol;
        Optional<Color> polygonColor;

        int rand;
        ColorProperty colorProperty = new ColorProperty();
        String oceanColorString = new oceanTile().getColor().getValue();
        Color oceanColor = colorProperty.toColor(oceanColorString);

        neighborCheck neighbors = new neighborCheck();
        Tile ocean = new oceanTile();

        do{
            rand = random.nextInt(newMesh.getPolygonsCount());
            pol = newMesh.getPolygons(rand);
            polygonColor = colorProperty.extract(pol.getPropertiesList());
        }while (polygonColor.isPresent() && polygonColor.get().equals(oceanColor) || neighbors.checkNeighbors(pol, newMesh, ocean));

        List<Structs.Segment> riverPath = new ArrayList<>();

        int currentLowestSegmentIdx = pol.getSegmentIdxs(random.nextInt(pol.getSegmentIdxsCount()));
        Structs.Segment currentLowestSegment = newMesh.getSegments(currentLowestSegmentIdx);
        
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

        return riverPath;
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

