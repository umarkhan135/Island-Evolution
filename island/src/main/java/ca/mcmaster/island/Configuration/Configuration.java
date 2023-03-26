package ca.mcmaster.island.Configuration;

import org.apache.commons.cli.*;

public class Configuration {

    public static final String OUTPUT = "o";
    public static final String INPUT = "i";
    public static final String MODE = "m";
    public static final String SHAPE = "s";
    public static final String ALTITUDE = "altitude";
    public static final String TEMPERATURE = "temperature";
    public static final String PERCIPITATION = "percipitation";
    public static final String AQUIFERNUMBER = "numOfAquifer";
    public static final String RIVERNUMBER = "numOfRivers";
    public static final String BEACHWIDTH = "beachWidth";
    public static final String SEED = "seed";

    private CommandLine cli;

    public Configuration(String[] args) {
        try {
            this.cli = parser().parse(options(), args);
        } catch (ParseException pe) {
            throw new IllegalArgumentException(pe);
        }
    }

    private CommandLineParser parser() {
        return new DefaultParser();
    }

    public String input() {
        return this.cli.getOptionValue(INPUT);
    }

    public String output() {
        return this.cli.getOptionValue(OUTPUT, "output.svg");
    }

    public String mode(){
        return this.cli.getOptionValue(MODE, "basic");
    }

    public String shape(){
        return this.cli.getOptionValue(SHAPE, "circle");
    }


    public String getAltitude() {
        return this.cli.getOptionValue(ALTITUDE, "volcano");
    }
    public String getTemperature(){
        return this.cli.getOptionValue(TEMPERATURE, "mild");
    }
    public String getPrecipitation(){
        return this.cli.getOptionValue(PERCIPITATION, "temperate");
    }

    public String getAquifer(){
        return this.cli.getOptionValue(AQUIFERNUMBER,"0");
    }

    public String getRiver(){
        return this.cli.getOptionValue(RIVERNUMBER,"0");
    }
    public String getBeachWidth(){
        return this.cli.getOptionValue(BEACHWIDTH, "0");
    }

    public String seed(){
        return this.cli.getOptionValue(SEED, "0");
    }

    public boolean hasSeed(){
        return this.cli.hasOption(SEED);
    }

    private Options options() {
        Options options = new Options();
        options.addOption(new Option(INPUT, true, "Input file (SVG)"));
        options.addOption(new Option(OUTPUT, true, "Output file (MESH)"));
        options.addOption(new Option(MODE, "mode", true, "Island Generation Type"));
        options.addOption(new Option(ALTITUDE, true, "Altitude Profile"));
        options.addOption(new Option(AQUIFERNUMBER, true, "Number of Aquifers"));
        options.addOption(new Option(RIVERNUMBER, true, "Number of Rivers"));
        options.addOption(new Option(TEMPERATURE, true, "Whittaker Temperature Type, enter \"hot\", \"mild\", or \"cold\""));
        options.addOption(new Option(PERCIPITATION, true, "Whittaker Percipitation Type, enter \"tropical\", \"temperate\", or \"dry\""));
        options.addOption(new Option(BEACHWIDTH, true, "input the thickness of the beaches"));
        options.addOption(new Option(SHAPE, "shape", true, "Shape of Island, enter \"cirlce\", \"ellipse\", \"star\" or \"random\""));
        options.addOption(new Option(SEED, "seed", true, "Seed for Island Generation"));

        return options;
    }


}
