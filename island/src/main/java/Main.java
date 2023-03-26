import ca.mcmaster.island.IslandGenerator;
import ca.mcmaster.island.Configuration.Configuration;
import ca.mcmaster.island.Elevation.Arctic;
import ca.mcmaster.island.Elevation.Canyon;
import ca.mcmaster.island.Elevation.RandomElevation;
import ca.mcmaster.island.Elevation.Volcano;
import ca.mcmaster.island.Elevation.elevation;
import ca.mcmaster.island.Rivers.MakeRiver;
import ca.mcmaster.island.Rivers.RiverGen;
import ca.mcmaster.island.shapes.*;
import ca.mcmaster.cas.se2aa4.a2.io.*;

import java.io.IOException;
import java.util.Random;

import ca.mcmaster.island.colorMesh;

public class Main {

    public static void main(String[] args) throws IOException {

        Configuration config = new Configuration(args);
        Structs.Mesh aMesh = new MeshFactory().read(config.input());

        IslandGenerator island = new IslandGenerator(config);
        Structs.Mesh exported = Structs.Mesh.newBuilder().build();

        colorMesh cm = new colorMesh();
        ShapeGenerator shape;
        elevation elevate;

        switch (config.shape()){
            case "star":
                shape = new StarIsland(aMesh);
                break;
            case "random":
                if(config.hasSeed())
                    shape = new RandomIsland(aMesh, Long.parseLong(config.seed()));
                else
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
            if(config.hasSeed())
                elevate = new RandomElevation(Long.parseLong(config.seed()));
            else
                elevate = new RandomElevation();
                break;
            default:
                elevate = new Volcano();
                break;
        }

        shape.generateShape();
        String aquiferNumStr = config.getAquifer();
        int aquiferNum = Integer.parseInt(aquiferNumStr);
        String riverNum = config.getRiver();
        int rivers = Integer.parseInt(riverNum);

        String numLakesStr = config.getLake();
        int numLakes = Integer.parseInt(numLakesStr);

        switch (config.mode()) {
            case "lagoon":
                exported = island.lagoon(aMesh, aquiferNum, numLakes);
                break;
            case "basic":
                exported = island.basic(aMesh, shape.getShape(),  elevate, aquiferNum, numLakes, rivers);
                break;
            default:
                exported = island.basic(aMesh, shape.getShape(), elevate, aquiferNum, numLakes, rivers);

                break;
        }
        
        new MeshFactory().write(exported, config.output());
        
    }
}
