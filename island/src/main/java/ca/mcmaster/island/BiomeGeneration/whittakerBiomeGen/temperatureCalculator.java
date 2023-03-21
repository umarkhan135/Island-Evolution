package ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen;

public class temperatureCalculator {
    public double hieghtTemp(int hieght, int temp){
        int Temperature = temp - hieght/8;
        return Temperature;
    }
}
