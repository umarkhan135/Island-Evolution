package ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen;
public class percipitationCalculator {
    public int hieghtPercipitation(int hieght, int per){
        int Percipitation = per + 3*Math.abs(hieght)/2;
        return Percipitation;
    }
}
