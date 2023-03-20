package ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen;

public enum whittakerTemperatureType {
    HOT("hot"), MILD("mild"), COLD("cold");
    int temperature;
    whittakerTemperatureType(String s){
        if(s.equals("hot")){
            temperature = 25;
        }else if(s.equals("mild")){
            temperature = 10;
        }else if(s.equals("cold")){
            temperature = -5;
        }
    }
    public int getTemperature() {
        return temperature;
    }
}
