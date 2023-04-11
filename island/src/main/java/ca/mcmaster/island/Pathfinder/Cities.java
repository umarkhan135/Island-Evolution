package ca.mcmaster.island.Pathfinder;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.island.MeshSize;
import ca.mcmaster.island.Tiles.lakeTile;
import ca.mcmaster.island.Tiles.oceanTile;
import ca.mcmaster.island.distance;
import ca.mcmaster.island.properties.ColorProperty;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class Cities {

    public int capitalCity(Structs.Mesh mesh) {
        MeshSize meshSize = new MeshSize(mesh);
        distance distance = new distance();
        double minDistance = Double.MAX_VALUE;

        ColorProperty colorProperty = new ColorProperty();
        String oceanColorString = new oceanTile().getColor().getValue();
        Color oceanColor = colorProperty.toColor(oceanColorString);
        String lakeColorString = new lakeTile().getColor().getValue();
        Color lakeColor = colorProperty.toColor(lakeColorString);

        int capitalCityCentroidIdx = -1;

        for (int i = 0; i < mesh.getPolygonsList().size(); i++) {
            Structs.Polygon p = mesh.getPolygons(i);
            Optional<Color> polygonColor = colorProperty.extract(p.getPropertiesList());
            if (polygonColor.isPresent() && !polygonColor.get().equals(oceanColor) && !polygonColor.get().equals(lakeColor)) {
                Structs.Vertex centroid = mesh.getVertices(p.getCentroidIdx());
                double currentDistance = distance.centerDistance(centroid, meshSize.getMaxX()/2, meshSize.getMaxY()/2);
                if (currentDistance < minDistance) {
                    minDistance = currentDistance;
                    capitalCityCentroidIdx = p.getCentroidIdx();
                }
            }
        }
        return capitalCityCentroidIdx;
    }

    public int randomCity(Structs.Mesh mesh) {
        ColorProperty colorProperty = new ColorProperty();
        String oceanColorString = new oceanTile().getColor().getValue();
        Color oceanColor = colorProperty.toColor(oceanColorString);
        String lakeColorString = new lakeTile().getColor().getValue();
        Color lakeColor = colorProperty.toColor(lakeColorString);

        List<Integer> validCityIndices = new ArrayList<>();

        for (int i = 0; i < mesh.getPolygonsCount(); i++) {
            Structs.Polygon cityPolygon = mesh.getPolygons(i);
            Optional<Color> polygonColor = colorProperty.extract(cityPolygon.getPropertiesList());
            if (polygonColor.isPresent() && !polygonColor.get().equals(oceanColor) && !polygonColor.get().equals(lakeColor)) {
                validCityIndices.add(cityPolygon.getCentroidIdx());
            }
        }

        if (validCityIndices.isEmpty()) {
            return -1;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(validCityIndices.size());
        return validCityIndices.get(randomIndex);
    }
}
