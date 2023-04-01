package ca.mcmaster.island;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen.percipitationCalculator;
import ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen.temperatureCalculator;
import ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen.whittakerPercipitationType;
import ca.mcmaster.island.BiomeGeneration.whittakerBiomeGen.whittakerTemperatureType;
public class whittakerTest {
    private static final Integer positiveHeight = 100;
    private static final Integer negativeHeight = -100;
    private static final Integer dry = 50;
    private static final Integer temperate = 150;
    private static final Integer tropical = 250;
    private static final Integer hot = 25;
    private static final Integer mild = 10;
    private static final Integer cold = -5;
    private static final whittakerPercipitationType WPT = new whittakerPercipitationType();
    private static final whittakerTemperatureType WTT = new whittakerTemperatureType();
    private static final percipitationCalculator PC = new percipitationCalculator();
    private static final temperatureCalculator TC = new temperatureCalculator();
    @Test
    public void percipitationTypeTest(){
        assertTrue(WPT.pT("dry") == dry);
        assertTrue(WPT.pT("temperate") == temperate);
        assertTrue(WPT.pT("tropical") == tropical);
    }
    @Test
    public void temperatureTypeTest(){
        assertTrue(WTT.tT("hot") == hot);
        assertTrue(WTT.tT("mild") == mild);
        assertTrue(WTT.tT("cold") == cold);
    }
    @Test 
    public void percipitationTest(){
        Integer positivePercipitation = temperate + 3*Math.abs(positiveHeight)/2;
        Integer negativePercipitation = temperate + 3*Math.abs(negativeHeight)/2;
        assertTrue(PC.hieghtPercipitation(positiveHeight, temperate) == positivePercipitation);
        assertTrue(PC.hieghtPercipitation(negativeHeight, temperate) == negativePercipitation);
    }
    @Test 
    public void temperatureTest(){
        Integer positiveTemperature;
        Integer negativeTemperature;
            positiveTemperature = mild - positiveHeight/8;
            negativeTemperature = mild;
        assertTrue(TC.hieghtTemp(positiveHeight, mild) == positiveTemperature);
        assertTrue(TC.hieghtTemp(negativeHeight, mild) == negativeTemperature);
    }
}

