package ca.mcmaster.island.Aquifers;

import java.awt.geom.Path2D;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

public interface AquifersGen {
    public boolean makeAquifier(int amount, Path2D shape, Mesh m);
    
}
