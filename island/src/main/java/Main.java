import ca.mcmaster.island.IslandGenerator;
import ca.mcmaster.island.Configuration.Configuration;
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

        shape.generateShape();

        switch (config.mode()) {
            case "lagoon":
                exported = island.lagoon(aMesh);
                break;
            case "basic":
                exported = island.basic(aMesh, shape.getShape());
                break;
            default:
                exported = island.basic(aMesh, shape.getShape());
                break;
        }
        
        new MeshFactory().write(exported, config.output());
    }
}

