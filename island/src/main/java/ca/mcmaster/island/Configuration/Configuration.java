package ca.mcmaster.island.Configuration;

import org.apache.commons.cli.*;

public class Configuration {

    public static final String OUTPUT = "o";
    public static final String INPUT = "i";
    public static final String MODE = "m";
    public static final String ALTITUDE = "altitude";
    public static final String TEMPERATURE = "tempurature";
    public static final String HUMIDITY = "humidity";

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

    public String mode() {
        return this.cli.getOptionValue(MODE, "lagoon");
    }

    public String getAltitude() {
        return this.cli.getOptionValue(ALTITUDE, "volcano");
    }
    public String getTemperature(){
        return this.cli.getOptionValue(TEMPERATURE, "mild");
    }
    public String getHumidity(){
        return this.cli.getOptionValue(HUMIDITY, "temperate");
    }

    private Options options() {
        Options options = new Options();
        options.addOption(new Option(INPUT, true, "Input file (SVG)"));
        options.addOption(new Option(OUTPUT, true, "Output file (MESH)"));
        options.addOption(new Option(MODE, "mode", true, "Island Generation Type"));
        options.addOption(new Option(ALTITUDE, true, "Altitude Profile"));
        options.addOption(new Option(TEMPERATURE, true, "Whittaker Temperature Type"));
        options.addOption(new Option(HUMIDITY, true, "Whittaker Humidity Type"));
        return options;
    }

}
