package ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen;
import java.lang.Math;
public class temperatureCalculator {
    public double hieghtTemp(int hieght, int temp){
        int Temperature = temp - hieght;
        System.out.printf("T: %d\n", Temperature);
        return Temperature;
    }
}
