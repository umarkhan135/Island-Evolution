package ca.mcmaster.island.Lakes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.island.Tiles.lakeTile;

import ca.mcmaster.island.properties.ColorProperty;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;

public class LakeGen {

    private lakeTile lake;

    public LakeGen() {
        lake = new lakeTile();
    }

    public Structs.Mesh generateLakes(Structs.Mesh m, int numberOfLakes) {
        ColorProperty colorProperty = new ColorProperty();
        ArrayList<Structs.Polygon> tilePolygons = new ArrayList<>();

        for (int i = 0; i < numberOfLakes; i++) {
            // Randomly select a lake center on land
            Structs.Polygon lakeCenter;
            do {
                int idx = new Random().nextInt(m.getPolygonsList().size());
                lakeCenter = m.getPolygonsList().get(idx);
            } while (colorProperty.toColor(lakeCenter.getProperties(0).getValue()).equals(lake.getColorCode()));

            // Generate lake area
            Area lakeArea = new Area();
            lakeArea.add(new Area(new Ellipse2D.Double(lakeCenter.getCentroidIdx(), lakeCenter.getCentroidIdx(), 20, 20)));

            for (Structs.Polygon p : m.getPolygonsList()) {
                Structs.Vertex v = m.getVertices(p.getCentroidIdx());
                if (lakeArea.contains(v.getX(), v.getY())) {
                    tilePolygons.add(Structs.Polygon.newBuilder(p).setProperties(0, lake.getColor()).build());
                } else {
                    tilePolygons.add(p);
                }
            }
        }

        return Structs.Mesh.newBuilder(m).clearPolygons().addAllPolygons(tilePolygons).build();
    }
}