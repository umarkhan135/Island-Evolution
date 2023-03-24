package ca.mcmaster.island.Rivers;

import java.awt.Color;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.island.Tiles.oceanTile;
import ca.mcmaster.island.properties.ColorProperty;
import ca.mcmaster.island.properties.ElevationProperty;

public class MakeRiver {

    public void PlayAround(Structs.Mesh m){
        Structs.Polygon pol;
        Optional<Color> polygonColor;
        Random random = new Random();
        int rand;
        ColorProperty colorProperty = new ColorProperty();
        String oceanColorString = new oceanTile().getColor().getValue();
        Color oceanColor = colorProperty.toColor(oceanColorString);


        do{
            rand = random.nextInt(m.getPolygonsCount());
            pol = m.getPolygons(rand);
            polygonColor = colorProperty.extract(pol.getPropertiesList());
        }while (polygonColor.isPresent() && polygonColor.get().equals(oceanColor));


        int z = pol.getSegmentIdxs(random.nextInt(pol.getSegmentIdxsCount()));

        Structs.Polygon lowerPolygon = findLowerElevationPolygon(z, m);

        int listOfSegements[] = segmentNeighbors(z, m);
        System.out.println(Arrays.toString(listOfSegements));

    }

    private int[] segmentNeighbors (int segment, Structs.Mesh mesh){
        int segments[] = new int[4];
        int counter = 0;

        for (int i = 0; i < mesh.getSegmentsList().size(); i++) {
            Structs.Segment ss = mesh.getSegmentsList().get(i);
            if ((ss.getV1Idx() == mesh.getSegments(segment).getV1Idx() || ss.getV2Idx() == mesh.getSegments(segment).getV1Idx() || ss.getV1Idx() == mesh.getSegments(segment).getV2Idx() || ss.getV2Idx() == mesh.getSegments(segment).getV2Idx()) && i != segment){
                System.out.println("Index of ss: " + i);
                System.out.println(ss);
                segments[counter] = i;
                counter++;
            }
        }
        return segments;
    }


    private Structs.Polygon findLowerElevationPolygon(int segmentIdx, Structs.Mesh mesh) {
        Structs.Polygon lowerElevationPolygon = mesh.getPolygons(10);
        double minElevation = Double.MAX_VALUE;
        ElevationProperty elevate = new ElevationProperty();
        for (Structs.Polygon polygon : mesh.getPolygonsList()) {
            if (polygon.getSegmentIdxsList().contains(segmentIdx)) {
                Optional<String> elevationString = elevate.extract(polygon.getPropertiesList());
                if (elevationString.isPresent()) {
                    double elevation = Double.parseDouble(elevationString.get());
                    if (elevation < minElevation) {
                        minElevation = elevation;
                        lowerElevationPolygon = polygon;
                    }
                }
            }
        }
        return lowerElevationPolygon;
    }
}