package ca.mcmaster.cas.se2aa4.a2.visualizer.renderer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.visualizer.renderer.properties.ColorProperty;
import ca.mcmaster.cas.se2aa4.a2.visualizer.renderer.properties.NumOfRiversProperty;
import ca.mcmaster.cas.se2aa4.a2.visualizer.renderer.properties.riverProperty;
import ca.mcmaster.cas.se2aa4.a2.visualizer.renderer.properties.RoadEdgeProperty;


import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.batik.ext.awt.image.codec.png.PNGEncodeParam.RGB;

public class GraphicRenderer implements Renderer {
    
    private static final int THICKNESS = 3;
    
    public void render(Mesh aMesh, Graphics2D canvas) {
        canvas.setColor(Color.BLACK);
        canvas.setBackground(Color.RED);
        Stroke stroke = new BasicStroke(0.2f);
        canvas.setStroke(stroke);
        drawPolygons(aMesh, canvas);
        riverSegments(aMesh, canvas);
        roadEdge(aMesh, canvas);
        
    }
    
    
    private void riverSegments(Mesh m, Graphics2D canvas){
        riverProperty ifRiver = new riverProperty();

        Color riverColor = new Color(0,141,151);

        NumOfRiversProperty numRivers = new NumOfRiversProperty();
        
        for (Structs.Segment ss : m.getSegmentsList()) {
            if(ifRiver.extract(ss.getPropertiesList()).isPresent()){
                if (ifRiver.extract(ss.getPropertiesList()).get().equals("true")) {
                    int thickness = Integer.parseInt(numRivers.extract(ss.getPropertiesList()).get());
                    drawSegment(ss, m, canvas, riverColor, thickness);
                } 
            }
        }
        
    }

    private void roadEdge(Mesh mesh, Graphics2D canvas){
        RoadEdgeProperty road = new RoadEdgeProperty();

        Color roadColor = new Color(255, 140, 0);

        for (Structs.Segment s : mesh.getSegmentsList()){
            if(road.extract(s.getPropertiesList()).isPresent()){
                if (road.extract(s.getPropertiesList()).get().equals("road")){
                    int thickness = 2;
                    drawSegment(s, mesh, canvas,roadColor, thickness);
                }
            }
        }
    }
    
    private void drawSegment(Structs.Segment segment, Mesh m, Graphics2D canvas, Color color, int thickness) {
        Structs.Vertex v1 = m.getVertices(segment.getV1Idx());
        Structs.Vertex v2 = m.getVertices(segment.getV2Idx());
        
        Color oldColor = canvas.getColor();
        canvas.setColor(color);
        
        Stroke oldStroke = canvas.getStroke();
        canvas.setStroke(new BasicStroke(thickness*1.5f));
        //System.out.println(thickness);
        
        canvas.drawLine((int) v1.getX(), (int) v1.getY(), (int) v2.getX(), (int) v2.getY());
        
        canvas.setColor(oldColor);
        canvas.setStroke(oldStroke);
    }
    
    
    private void drawPolygons(Mesh aMesh, Graphics2D canvas) {
        for (Structs.Polygon p : aMesh.getPolygonsList()) {
            drawAPolygon(p, aMesh, canvas);
            
        }
    }
    
    private void drawAPolygon(Structs.Polygon p, Mesh aMesh, Graphics2D canvas) {
        Hull hull = new Hull();
        for (Integer segmentIdx : p.getSegmentIdxsList()) {
            hull.add(aMesh.getSegments(segmentIdx), aMesh);
        }
        Path2D path = new Path2D.Float();
        Iterator<Vertex> vertices = hull.iterator();
        Vertex current = vertices.next();
        path.moveTo(current.getX(), current.getY());
        while (vertices.hasNext()) {
            current = vertices.next();
            path.lineTo(current.getX(), current.getY());
        }
        path.closePath();
        canvas.draw(path);
        Optional<Color> fill = new ColorProperty().extract(p.getPropertiesList());
        if (fill.isPresent()) {
            Color old = canvas.getColor();
            canvas.setColor(fill.get());
            canvas.fill(path);
            canvas.setColor(old);
        }
    }
    
}
