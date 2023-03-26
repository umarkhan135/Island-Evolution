package ca.mcmaster.island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.island.Elevation.Volcano;
import ca.mcmaster.island.Tiles.Tile;
import ca.mcmaster.island.Tiles.landTile;
import ca.mcmaster.island.Tiles.oceanTile;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class elevationTest {
    
    double elevation = 10;

    
    @Test
    public void testOceanElevation() {

        Tile ocean = new oceanTile();


        // test mesh
        Structs.Mesh.Builder meshBuilder =Structs.Mesh.newBuilder();
        meshBuilder.addVertices(Structs.Vertex.newBuilder().setX(0).setY(0));
        meshBuilder.addVertices(Structs.Vertex.newBuilder().setX(1).setY(0));
        meshBuilder.addVertices(Structs.Vertex.newBuilder().setX(1).setY(1));
        meshBuilder.addVertices(Structs.Vertex.newBuilder().setX(0).setY(1));

        Structs.Polygon.Builder polygonBuilder = Structs.Polygon.newBuilder();
        polygonBuilder.addSegmentIdxs(0);
        polygonBuilder.addSegmentIdxs(1);
        polygonBuilder.addSegmentIdxs(2);
        polygonBuilder.addSegmentIdxs(3);
        polygonBuilder.addProperties(Structs.Property.newBuilder().setKey("rgb_color").setValue(ocean.getColorCode()));
        Structs.Polygon p = polygonBuilder.build();

        

        meshBuilder.addPolygons(polygonBuilder.build());

        Structs.Mesh testMesh = meshBuilder.build();

        Volcano elevate = new Volcano(); 
        double testEl = elevate.getElevation(p, 10, testMesh);
        assertEquals(testEl, 0);
        
    }

    @Test
    public void testNonOceanElevation() {

        Tile land = new landTile();


        // test mesh
        Structs.Mesh.Builder meshBuilder =Structs.Mesh.newBuilder();
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

        

        meshBuilder.addPolygons(polygonBuilder.build());

        Structs.Mesh testMesh = meshBuilder.build();

        Volcano elevate = new Volcano(); 
        double testEl = elevate.getElevation(p, 10, testMesh);
        assertNotEquals(testEl, 0);
        
    }

}
