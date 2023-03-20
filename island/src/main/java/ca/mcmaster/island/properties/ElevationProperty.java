package ca.mcmaster.island.properties;

import java.util.Optional;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

import java.util.List;

public class ElevationProperty implements PropertyAccess<String> {

    private static final String ELEVATION = "elevation";

    @Override
    public Optional<String> extract(List<Property> props){
        String value = new Reader(props).get(ELEVATION);
        if (value == null)
            return Optional.empty();
        return Optional.of(value);
    }
    
}
