package ca.mcmaster.island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.island.Aquifers.AquifersGen;
import ca.mcmaster.island.Aquifers.CircleAquifier;
import ca.mcmaster.island.Tiles.Tile;
import ca.mcmaster.island.Tiles.landTile;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class aquiferTest {

    @Test
    public void testLandAquifer() {

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

        AquifersGen aquifer = new CircleAquifier();
        boolean test = aquifer.makeAquifier(1, testMesh);
        assertEquals(test, true);
        
    }
}
