package ca.mcmaster.cas.se2aa4.a2.visualizer.renderer.properties;

import java.util.List;
import java.util.Optional;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

public class NumOfRiversProperty implements PropertyAccess<String> {

    private static final String RIVER_KEY = "RiverNum";

    @Override
    public Optional<String> extract(List<Property> props) {
        String value = new Reader(props).get(RIVER_KEY);
        if (value == null)
            return Optional.empty();
        return Optional.of(value);
    }
}