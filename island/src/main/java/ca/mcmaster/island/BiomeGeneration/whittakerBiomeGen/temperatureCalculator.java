package ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen;
import java.lang.Math;
public class temperatureCalculator {
    public int hieghtTemp(int hieght, int temp){
        
        return temp - (int)Math.exp(0.5*hieght);
    }
}
