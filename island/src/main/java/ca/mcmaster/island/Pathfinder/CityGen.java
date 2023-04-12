package ca.mcmaster.island.Pathfinder;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.pathfinder.DijkstraPathFinder;
import ca.mcmaster.pathfinder.Edge;
import ca.mcmaster.pathfinder.Graph;
import ca.mcmaster.pathfinder.IEdge;

import java.util.*;

public class CityGen {

    public List<Integer> generateCityList(Structs.Mesh mesh, int numOfCities) {
        List<Integer> cities = new ArrayList<>();
        Cities cityFinder = new Cities();

        // Add the capital city to the list
        int capitalCity = cityFinder.capitalCity(mesh);
        cities.add(capitalCity);

        // Generate the remaining cities
        Set<Integer> uniqueCities = new HashSet<>();
        uniqueCities.add(capitalCity); // Ensure that we don't generate the same city twice
        while (uniqueCities.size() < numOfCities) {
            int city = cityFinder.randomCity(mesh);
            if (!uniqueCities.contains(city)) {
                uniqueCities.add(city);
                cities.add(city);
            }
        }

        System.out.println(cities);

        return cities;
    }


    public Structs.Mesh addCityVertices(Structs.Mesh mesh, List<Integer> cityList){
        List<Structs.Vertex> newVertices = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < cityList.size(); i++){
            CityType cityType = i == 0 ? CityType.CAPITAL : CityType.values()[random.nextInt(CityType.values().length - 1) + 1];
            Structs.Property cityVert = Structs.Property.newBuilder().setKey("cityType").setValue(String.valueOf(cityType)).build();
            Structs.Vertex vert = Structs.Vertex.newBuilder(mesh.getVertices(cityList.get(i))).addProperties(cityVert).build();
            newVertices.add(vert);
        }

        Structs.Mesh.Builder meshBuilder = Structs.Mesh.newBuilder(mesh);
        meshBuilder.addAllVertices(newVertices);
        return meshBuilder.build();
    }

    public Structs.Mesh buildCityRoads(Structs.Mesh mesh, List<Integer> cityList){
        MeshToGraphAdapter adapter = new MeshToGraphAdapter(mesh);
        Graph graphV = adapter.getGraph();
        List<Structs.Segment> newSegments = new ArrayList<>();

        for (int i = 1; i < cityList.size(); i++){
            int sourcePolyIdx = cityList.get(0);
            int targetPolyIdx = cityList.get(i);
            List<IEdge> path = new DijkstraPathFinder().findPath(graphV, sourcePolyIdx, targetPolyIdx);

            for (IEdge edge : path) {
                Structs.Property roadProperty = Structs.Property.newBuilder().setKey("roadEdge").setValue("road").build();
                Structs.Segment segment = Structs.Segment.newBuilder().setV1Idx(edge.getSource().getId()).setV2Idx(edge.getTarget().getId()).addProperties(roadProperty).build();
                newSegments.add(segment);
            }
        }

        Structs.Mesh.Builder meshBuilder = Structs.Mesh.newBuilder(mesh);
        meshBuilder.addAllSegments(newSegments);
        return meshBuilder.build();
    }
}