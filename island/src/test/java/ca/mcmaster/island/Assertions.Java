import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.visualizer.renderer.properties.ColorProperty;

public class Assertions {
    
    public static boolean assertEquals(double a, double b) {
        return a == b;
    }
    public static boolean assertGreaterThan(double a, double b) {
        return assert (a > b);
    }
    public static boolean assertLessThan(double a, double b) {
        return assert (a < b);
    }
    public static assertEquals(Property a, Property b) {
        return a.equals(b);
    }
}