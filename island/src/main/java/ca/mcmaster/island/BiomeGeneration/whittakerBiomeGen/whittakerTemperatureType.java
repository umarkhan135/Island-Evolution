package ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen;

public enum whittakerTemperatureType {
    HOT("hot", 25), MILD("mild", 10), COLD("cold", -5);
    String temperature;
    int temp;
    whittakerTemperatureType(String s, int temp){
       this.temp = temp;
       this.temperature = s;
    }
    public whittakerTemperatureType create(String s){
        switch(s){
            case "hot": return HOT;
            case "cold": return COLD;
            default: return MILD;
        }
    }
    public int getTemperature() {
        return temp;
    }
}
