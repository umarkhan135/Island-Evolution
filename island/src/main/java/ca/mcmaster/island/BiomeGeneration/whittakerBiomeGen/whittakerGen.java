package ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen;
import java.util.ArrayList;

import java.util.Optional;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.island.neighborCheck;
import ca.mcmaster.island.Configuration.Configuration;
import ca.mcmaster.island.SoilAbsorption.AssignSoilPercipitation;
import ca.mcmaster.island.Tiles.Tile;
import ca.mcmaster.island.Tiles.beachTile;
import ca.mcmaster.island.Tiles.landTile;
import ca.mcmaster.island.Tiles.oceanTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.CanyonTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.MountainTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.fieldTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.forestTile;
import ca.mcmaster.island.Tiles.LandBiomeTiles.rainForest;


import ca.mcmaster.island.properties.ElevationProperty;
import ca.mcmaster.island.properties.PercipitationProperty;
import ca.mcmaster.island.properties.TemperatureProperty;
import ca.mcmaster.island.properties.TileProperty;

public class whittakerGen {
    private static int temp; 
    private static int per;
    private static Configuration config;
    whittakerPercipitationType WPT = new whittakerPercipitationType();
    whittakerTemperatureType WTT = new whittakerTemperatureType();

    public whittakerGen(String temp, String per, Configuration config){
        this.temp = WTT.tT(temp);
        this.per = WPT.pT(per);
        this.config = config;
    }
    
    public Mesh biomeGen(Mesh m, double radius, int thickness){
        Tile land = new landTile();
        rainForest rainforest = new rainForest();
        fieldTile field = new fieldTile();
        forestTile forest = new forestTile();
        MountainTile mountain = new MountainTile();
        CanyonTile canyon = new CanyonTile();
        AssignPerrcipitation assignPerrcipitation = new AssignPerrcipitation(per);
        AssignSoilPercipitation assignSoilPercipitation = new AssignSoilPercipitation();
        AssignTemperature assignTemperature = new AssignTemperature(temp);
    
        TileProperty tileProperty = new TileProperty();
        ElevationProperty elevationProperty = new ElevationProperty();
        PercipitationProperty percipitationProperty = new PercipitationProperty();
        TemperatureProperty temperatureProperty = new TemperatureProperty();
        beachGen beachGen = new beachGen();
        double temperature;
        double percipitation;
        Mesh newMesh1 = assignSoilPercipitation.assignProperty(assignPerrcipitation.assignProperty(m));
        Mesh newMesh2 = assignTemperature.assignProperty(newMesh1);

        
        ArrayList<Polygon> polygons = new ArrayList<>();
        for (Structs.Polygon p : newMesh2.getPolygonsList()) {
            Optional<String> tile = tileProperty.extract(p.getPropertiesList());
            Optional<String> hieght = elevationProperty.extract(p.getPropertiesList());
            Optional<Double> percipitationProp = percipitationProperty.extract(p.getPropertiesList());
            Optional<Double> temperatureProp = temperatureProperty.extract(p.getPropertiesList());
            if(tile.isPresent() && hieght.isPresent() && percipitationProp.isPresent() && temperatureProp.isPresent()){
                int h = (int)Double.parseDouble(hieght.get());
                temperature = temperatureProp.get();
                percipitation = percipitationProp.get();
                if(tile.get().equals(land.getTileProperty().getValue())){
                    switch(compareP(percipitation, h, radius)){
                        case "tropical": polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(rainforest.getColor(temperature)).build());break;
                        case "dry" : polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(field.getColor(temperature)).build());break; 
                        case "mountain":polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(mountain.getColor(temperature)).build());break;
                        case "canyon":polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(canyon.getColor(h,temperature, radius)).build());break;
                        default:polygons.add(Structs.Polygon.newBuilder(p).addProperties(land.getTileProperty()).addProperties(forest.getColor(temperature)).build());break;
                    } 
                }else{
                    polygons.add(p);
                }  
            }else{
                polygons.add(p);
            }
            
        }
        if(config.hasSeed()){
            if(thickness > 0){
                for(Polygon p : beachGen.createbeach(newMesh2, thickness, Integer.parseInt(config.seed()))){
                    polygons.add(p);
                }
            }
            
        }else{
            if(thickness > 0){
                for(Polygon p : beachGen.createbeach(newMesh2, thickness)){
                    polygons.add(p);
                }
            }
        }
        

        Structs.Mesh newMesh3 = Structs.Mesh.newBuilder(m).clearPolygons().addAllPolygons(polygons).build();
        return newMesh3;
    }



    private static String compareP(double percipitation, int h, double radius) {
        if (h>radius/2){
            return "mountain";
        }
        if(h<-radius/2){
            return "canyon";
        }
        if (percipitation< 100){
                return "dry";
        }
        else if(percipitation>200){
            return "tropical";
        }
        return "temperate";
    }
}
