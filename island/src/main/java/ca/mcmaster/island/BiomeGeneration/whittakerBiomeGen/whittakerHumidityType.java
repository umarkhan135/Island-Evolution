package ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen;

public enum whittakerHumidityType{
    TROPICAL("tropical", 300), TEMPERATE("temperate", 150), DRY("dry", 50);
    private final String hum;
    private final int humidity;
    private whittakerHumidityType(String hum, int humidity){
        this.hum = hum;
        this.humidity = humidity;
    }

    public whittakerHumidityType create(String s){
        switch(s){
            case "tropical" : return TROPICAL;
            case "dry" : return DRY;
            default: return TEMPERATE;
        }
    }
    public int getHumidity(){
        return this.humidity;
    }

}