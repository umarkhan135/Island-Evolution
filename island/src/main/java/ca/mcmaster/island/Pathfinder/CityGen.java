package ca.mcmaster.island.Pathfinder;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.pathfinder.DijkstraPathFinder;
import ca.mcmaster.pathfinder.Graph;
import ca.mcmaster.pathfinder.IEdge;

import java.util.*;
import java.util.stream.IntStream;

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

        return cities;
    }

    public Structs.Mesh addCityVertices(Structs.Mesh mesh, List<Integer> cityList) {
        List<Structs.Vertex> vertices = new ArrayList<>();
        for (int i = 0; i < cityList.size(); i++) {
            CityType type = (i == 0) ? CityType.CAPITAL : CityType.values()[new Random().nextInt(CityType.values().length - 1) + 1];
            Structs.Property cityVert = Structs.Property.newBuilder().setKey("cityType").setValue(String.valueOf(type)).build();
            Structs.Vertex vert = Structs.Vertex.newBuilder(mesh.getVertices(cityList.get(i))).addProperties(cityVert).build();
            vertices.add(vert);
        }
        List<Structs.Vertex> Vert = new ArrayList<>(mesh.getVerticesList());
        Vert.addAll(vertices);

        return Structs.Mesh.newBuilder(mesh).clearVertices().addAllVertices(Vert).build();
    }

    public Structs.Mesh buildCityRoads(Structs.Mesh mesh, List<Integer> cityList) {
        MeshToGraphAdapter adapter = new MeshToGraphAdapter(mesh);
        Graph bigGraph = adapter.getGraph();
        List<Structs.Segment> segments = new ArrayList<>();

        for (int i = 1; i < cityList.size(); i++) {
            int sourceCityIdx = cityList.get(0);
            int targetCityIdx = cityList.get(i);

            int sourcePolyIdx = IntStream.range(0, mesh.getPolygonsCount())
                    .filter(index -> mesh.getPolygons(index).getCentroidIdx() == sourceCityIdx)
                    .findFirst()
                    .orElse(-1);

            int targetPolyIdx = IntStream.range(0, mesh.getPolygonsCount())
                    .filter(index -> mesh.getPolygons(index).getCentroidIdx() == targetCityIdx)
                    .findFirst()
                    .orElse(-1);

            List<IEdge> edges = new DijkstraPathFinder().findPath(bigGraph, bigGraph.getNode(sourcePolyIdx).getId(), bigGraph.getNode(targetPolyIdx).getId());

            for (IEdge e : edges) {
                Structs.Property property = Structs.Property.newBuilder().setKey("roadEdge").setValue("road").build();
                Structs.Segment seg = Structs.Segment.newBuilder()
                        .setV1Idx(mesh.getPolygons(e.getSource().getId()).getCentroidIdx())
                        .setV2Idx(mesh.getPolygons(e.getTarget().getId()).getCentroidIdx())
                        .addProperties(property)
                        .build();
                segments.add(seg);
            }
        }
        List<Structs.Segment> roads = new ArrayList<>(mesh.getSegmentsList());
        roads.addAll(segments);

        return Structs.Mesh.newBuilder(mesh).clearSegments().addAllSegments(roads).build();
    }

}
