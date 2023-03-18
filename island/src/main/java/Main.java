import ca.mcmaster.island.IslandGenerator;
import ca.mcmaster.island.configuration.Configuration;
import ca.mcmaster.island.shapes.CircleIsland;
import ca.mcmaster.island.shapes.EllipseIsland;
import ca.mcmaster.island.shapes.RandomShape;
import ca.mcmaster.island.shapes.ShapeGenerator;
import ca.mcmaster.island.shapes.StarIsland;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

import java.io.IOException;
import ca.mcmaster.island.ColorMesh;

public class Main {

    public static void main(String[] args) throws IOException {

        Configuration config = new Configuration(args);
        Structs.Mesh aMesh = new MeshFactory().read(config.input());
        IslandGenerator island = new IslandGenerator();
        Structs.Mesh exported = Mesh.newBuilder().build();
        ColorMesh cm = new ColorMesh();
        ShapeGenerator shape;

        switch (config.shape()){
            case "random":
                shape = new RandomShape();
            case "star":
                shape = new StarIsland();
                break;
            case "ellipse":
                shape = new EllipseIsland();
                break;
            case "circle":
                shape = new CircleIsland();
                break;
            default:
                shape = new CircleIsland();
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

