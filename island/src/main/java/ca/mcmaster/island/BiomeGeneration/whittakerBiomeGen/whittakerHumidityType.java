package ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen;

public enum whittakerHumidityType{
    TROPICAL("tropical"), TEMPERATE("temperate"), DRY("dry");
    int humidity;
    whittakerHumidityType(String s){
        if (s.equals("tropical")){
            humidity = 300;
        }else if(s.equals("temperate")){
            humidity = 150;
        }else if(s.equals("dry")){
            humidity = 50;
        }
    }
    public int getHumidity(){
        return this.humidity;
    }

}