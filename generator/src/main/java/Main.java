import ca.mcmaster.cas.se2aa4.a2.generator.DotGen;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;


import org.apache.commons.cli.*;



import java.io.IOException;

public class Main {
    
    public static void main(String[] args) throws IOException, ParseException {

        Options options = new Options();
        options.addOption(new Option("d", "debug", false, "Turn on debug."));

        options.addOption("h","help", false, "print this message");

        options.addOption("i","irregular",false,"display irregular mesh");

        options.addOption("r","relaxation", true, "number of relaxations");

        options.addOption("p","polygons", true, "number of polygons for irregulare");

        CommandLineParser parser = new BasicParser();
        //CommandLine cmd = parser.parse(options, args);



        DotGen generator = new DotGen();

        Mesh myMesh = generator.generate();


        options.addOption("h", false, "help");
        options.addOption("p", true, "number of polygons");
        options.addOption("r", true, "relaxation level of mesh");
        options.addOption("i", false, "prints irregular mesh");
        options.addOption("g", false, "prints regular mesh");
        options.addOption("n", false , "prints neighbors");

        CommandLine cmd;
        cmd = parser.parse(options, args);
        
        if (cmd.hasOption("h")) {
            
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("generator", options);
            System.exit(0);
            
        }
        


        //generator.iNeighbors();//we need to add a command line argument to display this

        MeshFactory factory = new MeshFactory();
        factory.write(myMesh, args[0]);
    }
    
}
