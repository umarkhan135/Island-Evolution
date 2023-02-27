package ca.mcmaster.cas.se2aa4.a2.generator;


import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;

import java.util.*;

public class PolygonsGen {

    public ArrayList<Polygon> createRegularPolygons(double width, double height, double square_size){

        ArrayList<Polygon> polygons = new ArrayList<>();
        List<List<Integer>> listOfNeighbors = new ArrayList<>();
        int incrementationSize = (int) (width/square_size - 1);

        List<Integer> topLeft = new ArrayList<>();
        topLeft.add(1);
        topLeft.add(Integer.valueOf(incrementationSize));
        topLeft.add(Integer.valueOf(incrementationSize + 1));
        listOfNeighbors.add(topLeft);

        for (int neighborCount = 1; neighborCount < (width/square_size - 2); neighborCount++) {
            List<Integer> newNeighborsIdx = new ArrayList<>();
            newNeighborsIdx.add(neighborCount - 1);
            newNeighborsIdx.add(neighborCount + 1);
            newNeighborsIdx.add(neighborCount + Integer.valueOf(incrementationSize - 1));
            newNeighborsIdx.add(neighborCount + Integer.valueOf(incrementationSize));
            newNeighborsIdx.add(neighborCount + Integer.valueOf(incrementationSize + 1));
            listOfNeighbors.add(newNeighborsIdx);
        }

        List<Integer> topRight = new ArrayList<>();
        topRight.add(Integer.valueOf( (incrementationSize - 2)));
        topRight.add(2 * Integer.valueOf( (incrementationSize - 1)));
        topRight.add(2 * Integer.valueOf( (incrementationSize - 1)) + 1);
        listOfNeighbors.add(topRight);

        for (int neighborCount = (int) incrementationSize; neighborCount < ((incrementationSize - 1)*(incrementationSize)); neighborCount++) {
            List<Integer> newNeighborsIdx = new ArrayList<>();
            if(neighborCount % (width/square_size - 1) == 0){

                newNeighborsIdx.add(neighborCount - Integer.valueOf(incrementationSize));
                newNeighborsIdx.add(neighborCount - Integer.valueOf(incrementationSize - 1));
                newNeighborsIdx.add(neighborCount + 1);
                newNeighborsIdx.add(neighborCount + Integer.valueOf(incrementationSize));
                newNeighborsIdx.add(neighborCount + Integer.valueOf(incrementationSize + 1));
                listOfNeighbors.add(newNeighborsIdx);
            }else if((neighborCount - (incrementationSize - 1)) % (incrementationSize) == 0 ){
                newNeighborsIdx.add(neighborCount - Integer.valueOf(incrementationSize));
                newNeighborsIdx.add(neighborCount - Integer.valueOf(incrementationSize + 1));
                newNeighborsIdx.add(neighborCount - 1);
                newNeighborsIdx.add(neighborCount + Integer.valueOf(incrementationSize - 1));
                newNeighborsIdx.add(neighborCount + Integer.valueOf(incrementationSize));
                listOfNeighbors.add(newNeighborsIdx);
            }else{
                newNeighborsIdx.add(neighborCount - Integer.valueOf(incrementationSize + 1));
                newNeighborsIdx.add(neighborCount - Integer.valueOf(incrementationSize));
                newNeighborsIdx.add(neighborCount - Integer.valueOf(incrementationSize - 1));
                newNeighborsIdx.add(neighborCount - 1);
                newNeighborsIdx.add(neighborCount + 1);
                newNeighborsIdx.add(neighborCount + Integer.valueOf(incrementationSize - 1));
                newNeighborsIdx.add(neighborCount + Integer.valueOf(incrementationSize));
                newNeighborsIdx.add(neighborCount + Integer.valueOf(incrementationSize + 1));
                listOfNeighbors.add(newNeighborsIdx);

            }
        }

        List<Integer> bottomLeft = new ArrayList<>();
        bottomLeft.add((incrementationSize * (incrementationSize - 2)));
        bottomLeft.add(((incrementationSize) * (incrementationSize - 2)) + 1);
        bottomLeft.add(Integer.valueOf((incrementationSize * (incrementationSize - 1)) + 1));
        listOfNeighbors.add(bottomLeft);

        for (int neighborCount = ((incrementationSize * (incrementationSize - 1)) + 1); neighborCount < ((incrementationSize * incrementationSize)-1); neighborCount++) {
            List<Integer> newNeighborsIdx = new ArrayList<>();
            newNeighborsIdx.add(neighborCount - 1);
            newNeighborsIdx.add(neighborCount + 1);
            newNeighborsIdx.add(neighborCount - Integer.valueOf(incrementationSize + 1));
            newNeighborsIdx.add(neighborCount - Integer.valueOf(incrementationSize));
            newNeighborsIdx.add(neighborCount - Integer.valueOf(incrementationSize - 1));
            listOfNeighbors.add(newNeighborsIdx);
        }

        List<Integer> bottomRight = new ArrayList<>();
        bottomRight.add((incrementationSize * (incrementationSize - 1)) - 2);
        bottomRight.add((incrementationSize * (incrementationSize - 1)) - 1);
        bottomRight.add(Integer.valueOf((incrementationSize * (incrementationSize)) - 2));
        listOfNeighbors.add(bottomRight);

        //System.out.println(listOfNeighbors);



        int neighbourCount = 0;
        int centroidCount = 0;
        int counter = 0;
        int j = 0;
        for (int rows = 0; rows < 23; rows++) {
            if (counter % 2 == 0) {
                for (int i = counter * 49; i < ((counter + 1) * 49); i += 2) {
                    if (i == (46+(49*counter))){
                        Polygon p = Polygon.newBuilder().addSegmentIdxs(i).addSegmentIdxs(i+2).addSegmentIdxs(i+49).addSegmentIdxs(i+1).setCentroidIdx(centroidCount).addAllNeighborIdxs(listOfNeighbors.get(neighbourCount)).build();
                        polygons.add(p);
                        centroidCount++;
                        neighbourCount++;
                    }else if(i == (48+(49*counter))){
                        continue;
                    }else {
                        Polygon p = Polygon.newBuilder().addSegmentIdxs(i).addSegmentIdxs(i + 3).addSegmentIdxs(i + 49).addSegmentIdxs(i + 1).setCentroidIdx(centroidCount).addAllNeighborIdxs(listOfNeighbors.get(neighbourCount)).build();
                        polygons.add(p);
                        centroidCount++;
                        neighbourCount++;
                    }
                }
            } else if (counter % 2 == 1) {
                for (int i = counter * 49; i < ((counter + 1) * 49); i += 2) {
                    if (i == (46+(49*counter))){
                        Polygon p = Polygon.newBuilder().addSegmentIdxs(i).addSegmentIdxs(i+2).addSegmentIdxs(i+49).addSegmentIdxs(i+1).setCentroidIdx(centroidCount).addAllNeighborIdxs(listOfNeighbors.get(neighbourCount)).build();
                        polygons.add(p);
                        centroidCount++;
                        neighbourCount++;
                    }else if(i == (48+(49*counter))){
                        continue;
                    }else{
                        Polygon p = Polygon.newBuilder().addSegmentIdxs(i).addSegmentIdxs(i + 3).addSegmentIdxs(i + 49).addSegmentIdxs(i + 1).setCentroidIdx(centroidCount).addAllNeighborIdxs(listOfNeighbors.get(neighbourCount)).build();
                        polygons.add(p);
                        centroidCount++;
                        neighbourCount++;
                    }
                }
            }
            counter++;
        }

        int otherCounter = 0;
        int centroidCounter2 = 552;
        for (int x = 1127; x < 1173; x += 2){
            Polygon p = Polygon.newBuilder().addSegmentIdxs(x).addSegmentIdxs(x + 3).addSegmentIdxs(x + 49 - otherCounter).addSegmentIdxs(x + 1).setCentroidIdx(centroidCounter2).addAllNeighborIdxs(listOfNeighbors.get(otherCounter)).build();
            polygons.add(p);
            otherCounter++;
            centroidCounter2++;
        }

        Polygon p = Polygon.newBuilder().addSegmentIdxs(1173).addSegmentIdxs(1175).addSegmentIdxs(1199).addSegmentIdxs(1174).setCentroidIdx(575).addAllNeighborIdxs(listOfNeighbors.get(575)).build();
        polygons.add(p);

        for (Polygon poly : polygons){
            Integer [] b = {3,4,5,6};
            ArrayList<Integer[]> bb = new ArrayList<>();
            bb.add(b);
            poly.newBuilder().addAllNeighborIdxs(List.of(b)).build();
            //System.out.println(poly.getNeighborIdxs(0));
        }

        return polygons;

    }

}
