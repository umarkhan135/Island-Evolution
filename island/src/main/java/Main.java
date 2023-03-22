import ca.mcmaster.island.IslandGenerator;
import ca.mcmaster.island.Configuration.Configuration;
import ca.mcmaster.island.Elevation.Arctic;
import ca.mcmaster.island.Elevation.Canyon;
import ca.mcmaster.island.Elevation.RandomElevation;
import ca.mcmaster.island.Elevation.Volcano;
import ca.mcmaster.island.Elevation.elevation;
import ca.mcmaster.island.shapes.*;
import ca.mcmaster.cas.se2aa4.a2.io.*;

import java.io.IOException;
import ca.mcmaster.island.colorMesh;

public class Main {

    public static void main(String[] args) throws IOException {

        Configuration config = new Configuration(args);
        Structs.Mesh aMesh = new MeshFactory().read(config.input());

        IslandGenerator island = new IslandGenerator();
        Structs.Mesh exported = Structs.Mesh.newBuilder().build();

        colorMesh cm = new colorMesh();
        ShapeGenerator shape;
        elevation elevate;

        switch (config.shape()){
            case "star":
                shape = new StarIsland(aMesh);
                break;
            case "random":
                shape = new RandomIsland(aMesh);
                break;
            case "ellipse":
                shape = new EllipseIsland(aMesh);
                break;
            case "circle":
                shape = new CircleIsland(aMesh);
                break;
            default:
                shape = new CircleIsland(aMesh);
                break;
        }

        switch (config.getAltitude()){
            case "volcano":
                elevate = new Volcano();
                break;
            case "canyon":
                elevate = new Canyon();
                break;
            case "arctic":
                elevate = new Arctic();
                break;
            case "random":
                elevate = new RandomElevation();
                break;
            default:
                elevate = new Volcano();
                break;
        }

        shape.generateShape();

        switch (config.mode()) {
            case "lagoon":
                exported = island.lagoon(aMesh);
                break;
            case "basic":
                exported = island.basic(aMesh, shape.getShape(),  elevate);
                break;
            default:
                exported = island.basic(aMesh, shape.getShape(), elevate);
                break;
        }
        
        new MeshFactory().write(exported, config.output());
        System.out.println(config.getAltitude());
    }
}

