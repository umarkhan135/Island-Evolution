package ca.mcmaster.island;

import ca.mcmaster.island.properties.ColorProperty;
import ca.mcmaster.island.Tiles.*;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class neighborCheck {

    public boolean checkNeighbors(Structs.Polygon poly, Structs.Mesh m, Tile t) {

        String props = t.getColorCode();
        Color prop1 = new ColorProperty().toColor(props);

        List<Integer> n = poly.getNeighborIdxsList();
        List<Structs.Polygon> polys = m.getPolygonsList();

        for (Integer i : n) {
            Optional<Color> tile = new ColorProperty().extract(polys.get(i).getPropertiesList());
            if (tile.isPresent()) {
                Color prop2 = tile.get();
                if (prop2.equals(prop1)) {
                    return true;
                }
            }
        }
        return false;
    }
}