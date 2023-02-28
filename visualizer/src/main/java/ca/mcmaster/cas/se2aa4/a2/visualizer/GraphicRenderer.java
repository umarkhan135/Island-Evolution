package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.List;


public class GraphicRenderer {
    
    private static final int THICKNESS = 3;
    private final double width = 500;
    private final double height = 500;
    
    public void render(Mesh aMesh, Graphics2D canvas, float Thickness, String visualizerStatus) {
        
        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(Thickness);
        canvas.setStroke(stroke);
        
        List <Vertex> points = aMesh.getVerticesList();
        for(Segment segment : aMesh.getSegmentsList()){
            
            double x1 = points.get(segment.getV1Idx()).getX();
            double y1 = points.get(segment.getV1Idx()).getY();
            double x2 = points.get(segment.getV2Idx()).getX();
            double y2 = points.get(segment.getV2Idx()).getY();
            
            if((0 <= x1 && x1 <= 500 && 0 <= y1 && y1 <= 500 ) || (0 <= x2 && x2 <= 500 && 0 <= y2 && y2 <= 500 ) ){
                Line2D lines = new Line2D.Double(x1,y1,x2,y2);
                lines = boundCheck(lines);
                if (visualizerStatus == "debug"){
                    Color lightGrey = new Color(100,100,100);
                    canvas.setColor(lightGrey);
                }else {
                    canvas.setColor(extractColor(segment.getPropertiesList()));
                }
                
                canvas.draw(lines);
            }
        }
        
        
        for (Vertex v: aMesh.getVerticesList()) {
            double centre_x = v.getX() - (THICKNESS/2.0d);
            double centre_y = v.getY() - (THICKNESS/2.0d);
            
            //Color old = canvas.getColor();
            
            if (visualizerStatus == "debug"){
                Color black = new Color(0,0,0);
                canvas.setColor(black);
            } else {
                canvas.setColor(extractColor(v.getPropertiesList()));
            }
            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, THICKNESS, THICKNESS);
            if(0 <= centre_x && centre_x <= width && 0 <= centre_y && centre_y <= height){
                canvas.fill(point);
            }
        }
    }
    
    private Color extractColor(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {
                
                val = p.getValue();

            }
        }
        if (val == null)
        return Color.BLACK;
        String[] raw = val.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        int alpha = Integer.parseInt(raw[3]);
        
        return new Color(red, green, blue, alpha);
    }
    
    private Line2D boundCheck(Line2D line){

        double x1 = line.getX1();
        double y1 = line.getY1();
        double x2 = line.getX2();
        double y2 = line.getY2();

        double m = (y2 - y1) / (x2 - x1);
        double b = y1 - m * x1;

        if(x1 > this.width){
            y1 = m * this.width + b;
            x1 = this.width;
        }
        if(x1 < 0){
            y1 = m * 0 + b;
            x1 = 0;
        }
        if(y1 > this.height){
            x1 = (this.height - b) / m;
            y1 = this.height;
        }
        if(y1 < 0){
            x1 = (0 - b) / m;
            y1 = 0;
        }
        if(x2 > this.width){
            y2 = m * this.width + b;
            x2 = this.width;
        }
        if(x2 < 0){
            y2 = m * 0 + b;
            x2 = 0;
        }
        if(y2 > this.height){
            x2 = (this.height - b) / m;
            y2 = this.height;
        }
        if(y2 < 0){
            x2 = (0 - b) / m;
            y2 = 0;
        }
        line.setLine(x1, y1, x2, y2);
        return line;
    }
}
