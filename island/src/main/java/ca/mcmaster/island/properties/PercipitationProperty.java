package ca.mcmaster.island.properties;

import java.util.List;
import java.util.Optional;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

public class PercipitationProperty implements PropertyAccess<Double>{

        private static final String PERCIPITATION = "percipitation";

    @Override
    public Optional<Double> extract(List<Property> props){
        Double value;
        try{
            value = Double.parseDouble(new Reader(props).get(PERCIPITATION));
        }catch(Exception exception){
            return Optional.empty();
        }
        return Optional.of(value);
    }
}
