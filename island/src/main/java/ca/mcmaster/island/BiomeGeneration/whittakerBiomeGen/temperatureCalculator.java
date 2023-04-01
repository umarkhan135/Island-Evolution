package ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen;

public class temperatureCalculator {
    public double hieghtTemp(int hieght, int temp){
        if(hieght<0){
            return temp;
        }
        int Temperature = temp - hieght/8;
        return Temperature;
    }
}
