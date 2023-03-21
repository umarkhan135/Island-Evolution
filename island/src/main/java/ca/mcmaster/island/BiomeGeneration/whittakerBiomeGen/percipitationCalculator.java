package ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen;

public class percipitationCalculator {
    public int hieghtPercipitation(int hieght, int per){
        int Percipitation = per + 5*hieght/4;
        return Percipitation;
    }
}
