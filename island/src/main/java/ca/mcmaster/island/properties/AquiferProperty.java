package ca.mcmaster.island.properties;


import java.util.Optional;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

import java.util.List;

public class AquiferProperty implements PropertyAccess<String> {

    private static final String AQUIFER = "aquifer";

    @Override
    public Optional<String> extract(List<Property> props){
        String value = new Reader(props).get(AQUIFER);
        if (value == null)
            return Optional.empty();
        return Optional.of(value);
    }
    
}
