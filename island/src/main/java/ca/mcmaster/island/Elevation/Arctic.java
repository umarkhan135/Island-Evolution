package ca.mcmaster.island.Elevation;

import java.util.Optional;
import java.util.Random;
import java.awt.*;
import java.awt.geom.Path2D;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.island.Tiles.oceanTile;
import ca.mcmaster.island.properties.ColorProperty;

public class Arctic implements elevation {

    private int elevation;    

    @Override
    public double getElevation(Polygon polygon, double radius, Mesh aMesh) {


        ColorProperty colorProperty = new ColorProperty();

        String oceanColorString = new oceanTile().getColor().getValue();
        Color oceanColor = colorProperty.toColor(oceanColorString);
        Optional<Color> polygonColor = colorProperty.extract(polygon.getPropertiesList());
        

        if (polygonColor.isPresent() && polygonColor.get().equals(oceanColor)){
            this.elevation = 0;

        }else{

            Random random = new Random();
            elevation = random.nextInt(6);

        }
        return elevation;
    }

    @Override
    public double getElevation(Polygon polygon, double radius, Mesh aMesh, Path2D shape) {

        ColorProperty colorProperty = new ColorProperty();

        String oceanColorString = new oceanTile().getColor().getValue();
        Color oceanColor = colorProperty.toColor(oceanColorString);
        Optional<Color> polygonColor = colorProperty.extract(polygon.getPropertiesList());
        

        if (polygonColor.isPresent() && polygonColor.get().equals(oceanColor)){
            this.elevation = 0;

        }else{

            Random random = new Random();
            elevation = random.nextInt(6);

        }
        return elevation;
    }

    @Override
    public Property tileElevation() {
        Property c = Property.newBuilder().setKey("elevation").setValue(String.valueOf(this.elevation)).build();
        return c;   
    }
    
}
