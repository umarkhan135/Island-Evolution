package ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen;

import java.lang.Math;
public class percipitationCalculator {
    public int hieghtPercipitation(int hieght, int per){
        return per + (int)Math.exp(hieght);
    }
}
