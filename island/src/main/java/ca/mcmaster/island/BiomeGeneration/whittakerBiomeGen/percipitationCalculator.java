package ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen;

import java.lang.Math;
public class percipitationCalculator {
    public int hieghtPercipitation(int hieght, int per){
        int Percipitation = per + 5*hieght/4;
        return Percipitation;
    }
}
