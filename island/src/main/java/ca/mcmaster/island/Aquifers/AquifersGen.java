package ca.mcmaster.island.Aquifers;

import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;



public interface AquifersGen {
    public boolean makeAquifier(int amount, Mesh m);
    public Mesh meshWithAquifers(List<Structs.Polygon> polygons, int amount, Mesh m);
    public Property Aquifers ();
    
}
