
package ca.mcmaster.cas.se2aa4.a2.generator;

import java.io.IOException;
import java.util.*;


import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;
import org.locationtech.jts.algorithm.Centroid;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.triangulate.quadedge.QuadEdge;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;
import org.locationtech.jts.algorithm.ConvexHull;
import org.apache.commons.cli.*;


public class DotGen {


    private final double width = 500;
    private final double height = 500;
    private final double square_size = 20;

    Random bag = new Random();
    protected ArrayList<QuadEdge> edges = new ArrayList<>();
    protected ArrayList<Vertex> vertices = new ArrayList<>();
    protected ArrayList<Vertex> verticesWithColors = new ArrayList<>();
    protected ArrayList<Segment> segments = new ArrayList<>();
    protected ArrayList<Segment> segmentsWithColors = new ArrayList<>();
    protected ArrayList<Vertex> centroids = new ArrayList<>();
    protected ArrayList<Coordinate> coords = new ArrayList<>();
    protected Map<Coordinate, ArrayList<Coordinate>> neighbors= new HashMap<>();
    protected DelaunayTriangulationBuilder DTB = new DelaunayTriangulationBuilder();
    protected PrecisionModel PM = new PrecisionModel();
    
    //This does not work as intended rn I'll have to edit it
    void iNeighbors(){

        DelaunayTriangulationBuilder DTB = new DelaunayTriangulationBuilder();
        PrecisionModel PM = new PrecisionModel();

        DTB.setSites(coords);
        Collection<QuadEdge> triangles = DTB.getSubdivision().getEdges();
        for ( Coordinate c: coords){
            ArrayList<Coordinate> close = new ArrayList<>();
            PM.makePrecise(c);
            for(QuadEdge e: triangles){    
                PM.makePrecise(e.orig().getCoordinate());
                PM.makePrecise(e.dest().getCoordinate());
                if(e.dest().getCoordinate().y<=500 && e.dest().getCoordinate().x<=500 && e.dest().getCoordinate().y>=0 && e.dest().getCoordinate().x>=0 && c.equals2D(e.orig().getCoordinate(), 0.5 )){
                    close.add(e.dest().getCoordinate());
                }    
            }
            if(c.y>=0 && c.x>=0 && c.y<=500 && c.x<=500){
                neighbors.put(c, close);
            }
            
        }
        for(Coordinate k: neighbors.keySet()){
            System.out.printf("Neighbors of (%d, %d): \n",Math.round(k.x),Math.round(k.y));
            for(Coordinate c: neighbors.get(k)){
                System.out.printf("(%d, %d)",Math.round(c.x),Math.round(c.y));
            }
            System.out.println();
        }
        System.out.printf("\n%d\n", neighbors.keySet().size());
    }
    
    

    public Mesh iGenerate(int polyNum, int relaxLev){

        VerticesGen vertGen = new VerticesGen();
        SegmentsGen segGen = new SegmentsGen();
        CentroidsGen centGen = new CentroidsGen();
        
        
        GeometryFactory Geo = new GeometryFactory();
        PrecisionModel PM = new PrecisionModel();
        VoronoiDiagramBuilder VDB = new VoronoiDiagramBuilder();
        Coordinate temp;
        List<org.locationtech.jts.geom.Polygon> polygons = new ArrayList<>();
        

        this.coords = centGen.createRandomCentroids(height, width, square_size, polyNum);

        Centroid centroid;
        for (int i = 0; i<15 ;i++){
            VDB = new VoronoiDiagramBuilder();
            VDB.setSites(coords);
            polygons = VDB.getSubdivision().getVoronoiCellPolygons(Geo);
            coords.clear();
            VDB.setSites(coords);
            for (org.locationtech.jts.geom.Polygon p: polygons){
                ConvexHull CV = new ConvexHull(p);
                centroid = new Centroid(CV.getConvexHull());
                temp = centroid.getCentroid();
                PM.makePrecise(temp);
                coords.add(temp);
            }
        }

        this.centroids = centGen.createIrregularFinalCentroids(coords);

        this.vertices = vertGen.createIrregularVertices(polygons);

        this.verticesWithColors = vertGen.addColourVertices(vertices);

        this.segments = segGen.createSegmentsPairs(verticesWithColors);

        this.segmentsWithColors = segGen.addColourSegments(segments, verticesWithColors);

        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllVertices(centroids).addAllSegments(segmentsWithColors).build();

    }

    public Mesh generate() {

        VerticesGen vertGen = new VerticesGen();
        SegmentsGen segGen = new SegmentsGen();
        CentroidsGen centGen = new CentroidsGen();

        // Generate vertices
        this.vertices = vertGen.createRegularVertices(width, height, square_size);

        this.centroids = centGen.createRegularCentroids(width, height, square_size);
        
        // Distribute colors randomly. Vertices are immutable, need to enrich them
        this.verticesWithColors = vertGen.addColourVertices(this.vertices);

        this.segments = segGen.createRegularSegments(this.verticesWithColors);

        this.segmentsWithColors = segGen.addColourSegments(this.segments, this.verticesWithColors);

        NewMesh mesh = new NewMesh(verticesWithColors, centroids, segmentsWithColors, width, square_size);
        //System.out.println(mesh.getPolygons());

        return Mesh.newBuilder().addAllVertices(mesh.getVertices()).addAllVertices(mesh.getCentroids()).addAllSegments(mesh.getSegments()).addAllPolygons(mesh.getPolygons()).build();

    }
}
