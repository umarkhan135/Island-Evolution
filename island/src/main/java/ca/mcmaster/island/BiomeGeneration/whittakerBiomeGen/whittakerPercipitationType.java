package ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen;

public class whittakerPercipitationType{
    public int pT(String percipitation){
        switch(percipitation){
            case "dry": return 50;
            case "tropical": return 250;
            default: return 150;
        }
    }
}