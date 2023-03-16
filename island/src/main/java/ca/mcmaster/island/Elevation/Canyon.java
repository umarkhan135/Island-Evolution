package ca.mcmaster.island.Elevation;

import java.util.Optional;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.island.distance;
import ca.mcmaster.island.Tiles.oceanTile;
import ca.mcmaster.island.properties.ColorProperty;
import ca.mcmaster.island.properties.ElevationProperty;
import ca.mcmaster.island.properties.TileProperty;
import java.awt.*;


public class Canyon implements elevation {

    private double elevation;

    @Override
    public double getElevation(Polygon polygon, int radius, Structs.Mesh aMesh) {

        ColorProperty colorProperty = new ColorProperty();

        
        
        String oceanColorString = new oceanTile().getColor().getValue();
        Color oceanColor = colorProperty.toColor(oceanColorString);
        Optional<Color> polygonColor = colorProperty.extract(polygon.getPropertiesList());
        

        if (polygonColor.isPresent() && polygonColor.get().equals(oceanColor)){
            this.elevation = 0;

        }else{

            int centroidIdx = polygon.getCentroidIdx(); // Get the index of the centroid vertex
            Structs.Vertex centroid = aMesh.getVertices(centroidIdx);

            distance dis = new distance();
            double distance = dis.centerDistance(centroid, 250, 250);
            double normalizedDistance = distance / radius;

            elevation = Math.pow(normalizedDistance, 2) * 100;
            elevation = Math.round(elevation * 100) / 100.0;

        }
        return elevation;
    }

    @Override
    public Property tileElevation() {

        Property c = Property.newBuilder().setKey("elevation").setValue(String.valueOf(this.elevation)).build();
        return c;
        
    }

    
    
}
