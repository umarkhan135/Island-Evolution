package ca.mcmaster.island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.island.Lakes.LakeGen;
import ca.mcmaster.island.Tiles.Tile;
import ca.mcmaster.island.Tiles.landTile;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;


public class lakeTest {

    @Test
    public void testMakeLakes() {
        Tile land = new landTile();

        // Test mesh
        Structs.Mesh.Builder meshBuilder = Structs.Mesh.newBuilder();
        meshBuilder.addVertices(Structs.Vertex.newBuilder().setX(0).setY(0));
        meshBuilder.addVertices(Structs.Vertex.newBuilder().setX(1).setY(0));
        meshBuilder.addVertices(Structs.Vertex.newBuilder().setX(1).setY(1));
        meshBuilder.addVertices(Structs.Vertex.newBuilder().setX(0).setY(1));

        Structs.Polygon.Builder polygonBuilder = Structs.Polygon.newBuilder();
        polygonBuilder.addSegmentIdxs(0);
        polygonBuilder.addSegmentIdxs(1);
        polygonBuilder.addSegmentIdxs(2);
        polygonBuilder.addSegmentIdxs(3);
        polygonBuilder.addProperties(Structs.Property.newBuilder().setKey("rgb_color").setValue(land.getColorCode()));
        Structs.Polygon p = polygonBuilder.build();

        meshBuilder.addPolygons(p);

        Structs.Mesh testMesh = meshBuilder.build();

        LakeGen lakeGen = new LakeGen();
        boolean test = lakeGen.makeLakes(1, testMesh);
        assertEquals(test, true);
    }

    @Test
    public void testMarkLakes(){
        Tile land = new landTile();

        // Test mesh
        Structs.Mesh.Builder meshBuilder = Structs.Mesh.newBuilder();
        meshBuilder.addVertices(Structs.Vertex.newBuilder().setX(0).setY(0));
        meshBuilder.addVertices(Structs.Vertex.newBuilder().setX(1).setY(0));
        meshBuilder.addVertices(Structs.Vertex.newBuilder().setX(1).setY(1));
        meshBuilder.addVertices(Structs.Vertex.newBuilder().setX(0).setY(1));

        // First polygon
        Structs.Polygon.Builder polygonBuilder1 = Structs.Polygon.newBuilder();
        polygonBuilder1.addSegmentIdxs(0);
        polygonBuilder1.addSegmentIdxs(1);
        polygonBuilder1.addSegmentIdxs(2);
        polygonBuilder1.addSegmentIdxs(3);
        polygonBuilder1.addProperties(Structs.Property.newBuilder().setKey("rgb_color").setValue(land.getColorCode()));
        polygonBuilder1.addNeighborIdxs(1); // Set the neighbor
        meshBuilder.addPolygons(polygonBuilder1.build());

        // Second polygon
        Structs.Polygon.Builder polygonBuilder2 = Structs.Polygon.newBuilder();
        polygonBuilder2.addSegmentIdxs(0);
        polygonBuilder2.addSegmentIdxs(1);
        polygonBuilder2.addSegmentIdxs(2);
        polygonBuilder2.addSegmentIdxs(3);
        polygonBuilder2.addProperties(Structs.Property.newBuilder().setKey("rgb_color").setValue(land.getColorCode()));
        polygonBuilder2.addNeighborIdxs(0); // Set the neighbor
        meshBuilder.addPolygons(polygonBuilder2.build());

        Structs.Mesh testMesh = meshBuilder.build();

        LakeGen lakeGen = new LakeGen();
        List<Integer> neighbors = Arrays.asList(1);
        List<Structs.Polygon> markedLakes = lakeGen.markLake(neighbors, testMesh);

        // Check if the markedLakes list has one element
        assertEquals(1, markedLakes.size());

        // Get the index of the marked lake in the original polygons list
        int markedLakeIndex = testMesh.getPolygonsList().indexOf(markedLakes.get(0));

        // Check if the marked polygon is the correct one
        assertEquals(1, markedLakeIndex);
    }
}
