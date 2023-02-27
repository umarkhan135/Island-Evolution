package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;

import java.util.*;

public class SegmentsGen{

    public ArrayList<Segment> createRegularSegments(ArrayList<Vertex> vertices){

        ArrayList<Segment> segments = new ArrayList<>();

        for(Vertex v: vertices){
            if((vertices.indexOf(v)+1)%25 != 0){
                Segment s = Segment.newBuilder().setV1Idx(vertices.indexOf(v)).setV2Idx(vertices.indexOf(v)+1).build();
                segments.add(s);
            }
            if((vertices.indexOf(v)+25) < vertices.size()){
                Segment s = Segment.newBuilder().setV1Idx(vertices.indexOf(v)).setV2Idx(vertices.indexOf(v)+25).build();
                segments.add(s);
            }
        }

        return segments;
    }

    public ArrayList<Segment> createSegmentsPairs(ArrayList<Vertex> vertices){

        ArrayList<Segment> segments = new ArrayList<>();

        for(int i = 0; i < vertices.size(); i = i + 1){
            if(vertices.get(i).getX()>=-50 && vertices.get(i).getX()<=550 && vertices.get(i).getY()>=-50 && vertices.get(i).getY()<=550 && vertices.get(i+1).getX()>=-50 && vertices.get(i+1).getX()<=550 && vertices.get(i+1).getY()>=-50 && vertices.get(i+1).getY()<=550){
                Segment s = Segment.newBuilder().setV1Idx(i).setV2Idx(i+1).build();
                segments.add(s);
            }
        }

        return segments;
    }

    public ArrayList<Segment> addColourSegments(ArrayList<Segment> segments, ArrayList<Vertex> verticesWithColors){

        AverageColorGen avgColorGen = new AverageColorGen();
        ArrayList<Segment> segmentsWithColors = new ArrayList<>();

        for(Segment s: segments){
            Property color = avgColorGen.avgColor(verticesWithColors.get(s.getV1Idx()).getPropertiesList(), verticesWithColors.get(s.getV2Idx()).getPropertiesList());
            Segment colored = Segment.newBuilder(s).addProperties(color).build();
            segmentsWithColors.add(colored);
        }

        return segmentsWithColors;
    }
}