import ca.mcmaster.cas.se2aa4.a2.generator.DotGen;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

import org.apache.commons.cli.*;



import java.io.IOException;

public class Main {
    
    public static void main(String[] args) throws IOException, ParseException {

        Options options = new Options();
        options.addOption("h", false, "help");
        options.addOption("p", true, "number of polygons");
        options.addOption("r", true, "relaxation level of mesh");
        options.addOption("t", true, "type of mesh, 0 for regular, 1 for irregular");
        options.addOption("n", false , "prints neighbors");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        cmd = parser.parse(options, args);
        
        if (cmd.hasOption("h")) {
            
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("generator", options);
            System.exit(0);
            
        }

        DotGen generator = new DotGen();
        Mesh myMesh = Mesh.newBuilder().build();

        if(cmd.hasOption("t")){
            if(cmd.getOptionValue("t").equals("0")){
                
                myMesh = generator.generate();
            }
            else if(cmd.getOptionValue("t").equals("1")){
                
                int r = 15;
                int p = 625;

                if(cmd.hasOption("p")){
                    p = Integer.parseInt(cmd.getOptionValue("p"));
                }
                if(cmd.hasOption("r")){
                    r = Integer.parseInt(cmd.getOptionValue("r"));
                }

                myMesh = generator.iGenerate(p, r);
                if(cmd.hasOption("n")){
                    generator.iNeighbors();
                }
            }
        }else{
            myMesh = generator.generate();
        }
    
        MeshFactory factory = new MeshFactory();
        factory.write(myMesh, args[0]);
    }
    
}
