package ca.mcmaster.island.configuration;

import org.apache.commons.cli.*;


public class Configuration {

    public static final String OUTPUT = "o";
    public static final String INPUT = "i";
    public static final String MODE = "m";
    public static final String SHAPE = "s";
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
        options.addOption(new Option(SHAPE, "shape", true, "Shape of Island"));
        options.addOption(new Option(SEED, "seed", true, "Seed for Island Generation"));
        return options;
    }

}
