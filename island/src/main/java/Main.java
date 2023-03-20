import ca.mcmaster.island.islandGen;
import ca.mcmaster.island.Configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

import java.io.IOException;
import ca.mcmaster.island.colorMesh;

public class Main {

    public static void main(String[] args) throws IOException {

        Configuration config = new Configuration(args);
        Structs.Mesh aMesh = new MeshFactory().read(config.input());
        islandGen island = new islandGen(config);
        Structs.Mesh exported = Mesh.newBuilder().build();
        colorMesh cm = new colorMesh();
        switch (config.mode()) {
            case "lagoon":
                exported = island.lagoon(aMesh);
                break;
            default:
                exported = island.lagoon(aMesh);
                break;

        }
        new MeshFactory().write(exported, config.output());
    }
}
