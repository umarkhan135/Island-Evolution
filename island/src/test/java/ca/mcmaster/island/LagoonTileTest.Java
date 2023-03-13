import org.junit.Test;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;

public class LagoonTileTest extends TileTest {
    private ColorProperty oceanTile = new ColorProperty(43, 101 236);
    private ColorProperty lagoonTile = new ColorProperty(0,98,111);
    private ColorProperty landTile = new ColorProperty(155,118,83);
    private ColorProperty beachTile = new ColorProperty(194, 178, 128);
    @Override
    public boolean oceanTest(double rad, Structs.Mesh m) {
        for (Structs.Polygon p : m.getPolygonsList()) {
            Structs.Vertex v = new islandGen().m.getVertices(p.getCentroidIdx());
            double d = new islandGen().centerDistance(v);
            if (d.assertGreaterThan(rad) && !p.setKey("rgb_value").getPropertiesList().assertEquals(oceanTile)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean landTest(double outerRad, double innerRad, Structs.Mesh m) {
        for (Structs.Polygon p : m.getPolygonsList()) {
            Structs.Vertex v = new islandGen().m.getVertices(p.getCentroidIdx());
            double d = new islandGen().centerDistance(v);
            if (d.assertlessThan(outerRad) && (p.setKey("rgb_value").getPropertiesList().assertEquals(oceanTile) || p.setKey("rgb_value").getPropertiesList().assertEquals(lagoonTile))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean beachTest() {
        return true;
    }

    @Override
    public boolean lagoonTest(double rad, Structs.Mesh m) {
        for (Structs.Polygon p : m.getPolygonsList()) {
            Structs.Vertex v = new islandGen().m.getVertices(p.getCentroidIdx());
            double d = new islandGen().centerDistance(v);
            if (d.assertGreaterThan(rad) && !p.setKey("rgb_value").getPropertiesList().assertEquals(lagoonTile)) {
                return false;
            }
        }
        return true;
    }

    public static void public static void main(String[] args) {
        System.out.println((oceanTest) ? "All ocean tiles are in the right place" : "Some ocean tiles are not in the right place");
        System.out.println((landTest) ? "All land and beach tiles are in the right place" : "Some land and beach tiles are not in the right place");
        System.out.println((lagoonTest) ? "All lagoon tiles are in the right place" : "Some lagoon tiles are not in the right place");
    }
}