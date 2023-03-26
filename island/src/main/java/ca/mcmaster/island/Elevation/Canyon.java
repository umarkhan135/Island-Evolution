package ca.mcmaster.island.Elevation;

import java.util.Optional;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.island.MeshSize;
import ca.mcmaster.island.distance;
import ca.mcmaster.island.Tiles.oceanTile;
import ca.mcmaster.island.properties.ColorProperty;
import ca.mcmaster.island.shapes.EllipseIsland;
import ca.mcmaster.island.shapes.ShapeGenerator;

import java.awt.*;
import java.awt.geom.Path2D;


public class Canyon implements elevation {

    private double elevation;    

    @Override
    public double getElevation(Polygon polygon, double radius, Mesh aMesh) {

        ColorProperty colorProperty = new ColorProperty();

        double x = new MeshSize(aMesh).getMaxX();
        double y = new MeshSize(aMesh).getMaxY();
        
        String oceanColorString = new oceanTile().getColor().getValue();
        Color oceanColor = colorProperty.toColor(oceanColorString);
        Optional<Color> polygonColor = colorProperty.extract(polygon.getPropertiesList());
        

        if (polygonColor.isPresent() && polygonColor.get().equals(oceanColor)){
            this.elevation = 0;

        }else{

            int centroidIdx = polygon.getCentroidIdx(); // Get the index of the centroid vertex
            Structs.Vertex centroid = aMesh.getVertices(centroidIdx);

            distance dis = new distance();
            double distance = dis.centerDistance(centroid, x/2, y/2);
            double normalizedDistance = distance / radius;

            elevation = 0-Math.pow(normalizedDistance, 2) * 100 - radius/1.75;
            elevation = Math.round(elevation * 100) / 100.0;

        }
        return elevation;
    }

    public double pointToEllipseDistance(Structs.Vertex point, double centerX, double centerY, double a, double b) {

        final int numIterations = 30;
        double angle = 0;
        double minDistance = Double.MAX_VALUE;

        for (int i = 0; i < numIterations; i++) {
            angle = i * (2 * Math.PI / numIterations);
            double ellipseX = centerX + a * Math.cos(angle);
            double ellipseY = centerY + b * Math.sin(angle);
            double distance = Math.sqrt(Math.pow(ellipseX - point.getX(), 2) + Math.pow(ellipseY - point.getY(), 2));

            if (distance < minDistance) {
                minDistance = distance;
            }
        }

        return minDistance;
    }

    @Override
    public double getElevation(Polygon polygon, double radius, Mesh aMesh, Path2D shape) {

        double a = shape.getBounds().getWidth()/2;
        double b = shape.getBounds().getHeight()/2;
        

        
        ColorProperty colorProperty = new ColorProperty();
        double x = new MeshSize(aMesh).getMaxX();
        double y = new MeshSize(aMesh).getMaxY();
        double centerX = x / 2;
        double centerY = y / 2;
        

        String oceanColorString = new oceanTile().getColor().getValue();
        Color oceanColor = colorProperty.toColor(oceanColorString);
        Optional<Color> polygonColor = colorProperty.extract(polygon.getPropertiesList());

        if (polygonColor.isPresent() && polygonColor.get().equals(oceanColor)) {
            this.elevation = 0;
        } else {
            int centroidIdx = polygon.getCentroidIdx(); // Get the index of the centroid vertex
            Structs.Vertex centroid = aMesh.getVertices(centroidIdx);

            distance dist = new distance();
            double dis = dist.centerDistance(centroid, centerX, centerY);
           
            double distanceToEllipse = pointToEllipseDistance(centroid, centerX, centerY, a, b);
            double e = 1/distanceToEllipse * 3500;
            
            elevation = e;
            elevation = Math.round(elevation * 100) / 100.0 - radius;
        }
        return elevation;
    }

            

    @Override
    public Property tileElevation() {

        Property c = Property.newBuilder().setKey("elevation").setValue(String.valueOf(this.elevation)).build();
        return c;
        
    }

    
    
}
