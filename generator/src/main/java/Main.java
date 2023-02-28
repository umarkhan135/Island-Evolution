import ca.mcmaster.cas.se2aa4.a2.generator.DotGen;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

import org.apache.commons.cli.*;



import java.io.IOException;

public class Main {
    
    public static void main(String[] args) throws IOException, ParseException {
        Options options = new Options();
        options.addOption("h", false, "help");
        options.addOption("n", false, "number of vertices");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        cmd = parser.parse(options, args);
        
        if (cmd.hasOption("h")) {
            
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("generator", options);
            System.out.println("test");
            System.exit(0);
            
        }else{
            DotGen generator = new DotGen();
            Mesh myMesh = generator.iGenerate();
            //generator.iNeighbors();//we need to add a command line argument to display this
            MeshFactory factory = new MeshFactory();
            factory.write(myMesh, args[0]);
        }
    }
    
}
