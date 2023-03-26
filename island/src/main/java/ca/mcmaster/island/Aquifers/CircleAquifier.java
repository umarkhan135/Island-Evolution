package ca.mcmaster.island.Aquifers;

import java.awt.Color;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.island.Tiles.oceanTile;
import ca.mcmaster.island.properties.ColorProperty;
import ca.mcmaster.island.shapes.CircleIsland;
import ca.mcmaster.island.shapes.ShapeGenerator;



public class CircleAquifier implements AquifersGen {

    private boolean aquifer;
    private List<Structs.Polygon> aquiferNeighbor = new ArrayList<>();

    @Override
    public boolean makeAquifier(int amount, Mesh m) {
        

        List<Integer> neighbors = new ArrayList<>();
        
        

        Random random = new Random();
        ColorProperty colorProperty = new ColorProperty();
        String oceanColorString = new oceanTile().getColor().getValue();
        Color oceanColor = colorProperty.toColor(oceanColorString);
        boolean found = false;

        for (int i = 0; i < amount; i++){
            
            int rand;
            Structs.Vertex v;
            Structs.Polygon p;
            Optional<Color> polygonColor;
            

            do{
                rand = random.nextInt(m.getPolygonsCount());
                p = m.getPolygons(rand);
                v = m.getVertices(p.getCentroidIdx());
                polygonColor = colorProperty.extract(p.getPropertiesList());
                if (polygonColor.isPresent() && !polygonColor.get().equals(oceanColor)){
                    found = true;
                }
            }while (polygonColor.isPresent() && polygonColor.get().equals(oceanColor));

            neighbors = p.getNeighborIdxsList();
            List<Structs.Polygon> aquiferNeighborList = markAquifer(neighbors, m);
            for (Structs.Polygon aP : aquiferNeighborList){
                aquiferNeighbor.add(aP);
            }
            aquiferNeighbor.add(p);    
        }
        this.aquifer = found;
        return this.aquifer;
    }


    public List<Structs.Polygon> markAquifer(List<Integer> neighbors, Structs.Mesh mesh){

        ColorProperty colorProperty = new ColorProperty();
        String oceanColorString = new oceanTile().getColor().getValue();
        Color oceanColor = colorProperty.toColor(oceanColorString);
        List<Structs.Polygon> poly = new ArrayList<>();
        

        for (Integer i : neighbors){
            Structs.Polygon p = mesh.getPolygons(i.intValue());
            Optional<Color> polygonColor = colorProperty.extract(p.getPropertiesList());
            if (polygonColor.isPresent() && !polygonColor.get().equals(oceanColor)){
                poly.add(p);
            }
        }
        return poly;
    }

    @Override
    public Property Aquifers() {

        Property c = Property.newBuilder().setKey("aquifer").setValue(String.valueOf(this.aquifer)).build();
        return c;
        
    }

    @Override
    public Mesh meshWithAquifers(List<Structs.Polygon> polygons, int amount,  Mesh m){
        ArrayList<Structs.Polygon> poly = new ArrayList<>();
        

        makeAquifier(amount, m);
        for (Structs.Polygon p : polygons){
            boolean isAquifer = aquiferNeighbor.contains(p);
            Property aquiferProperty = Property.newBuilder()
                                               .setKey("aquifer")
                                               .setValue(String.valueOf(isAquifer))
                                               .build();
            Structs.Polygon newPolygon = Structs.Polygon.newBuilder(p).addProperties(aquiferProperty).build();
            poly.add(newPolygon);
             
        }
        
        Structs.Mesh newMeshWithAquifer = Structs.Mesh.newBuilder(m).clearPolygons().addAllPolygons(poly).build();
        return newMeshWithAquifer;
    
    }
}