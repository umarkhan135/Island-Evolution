package ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen;

public enum whittakerPercipitationType{
    TROPICAL("tropical", 300), TEMPERATE("temperate", 150), DRY("dry", 50);
    private final String per;
    private final int percipitation;
    private whittakerPercipitationType(String per, int percipitation){
        this.per = per;
        this.percipitation = percipitation;
    }

    public whittakerPercipitationType create(String s){
        switch(s){
            case "tropical" : return TROPICAL;
            case "dry" : return DRY;
            default: return TEMPERATE;
        }
    }
    public int getHumidity(){
        return this.percipitation;
    }

}