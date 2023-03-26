package ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen;

public class whittakerTemperatureType {
    public int tT(String temperature){
        temperature = temperature.trim();
        switch(temperature){
            case "hot": return 25;
            case "cold": return -5;
            default: return 10;
        }

    }
}
