package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import org.apache.batik.apps.rasterizer.Main;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.List;

import javax.sound.sampled.Line;

import java.awt.geom.Line2D;

public class GraphicRenderer {

    private static final int THICKNESS = 3;



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
            Line2D lines = new Line2D.Double(x1,y1,x2,y2);
            //Color old = canvas.getColor();
            //canvas.setColor(extractColor(segment.getPropertiesList()/*, Transparency */));

//            Color black = new Color(60,60,60);
//            canvas.setColor(black);

            if (visualizerStatus == "debug"){
                Color black = new Color(60,60,60);
                canvas.setColor(black);
            }else {
                canvas.setColor(extractColor(segment.getPropertiesList()));
            }

            canvas.draw(lines);

            //canvas.setColor(old);
        }


        for (Vertex v: aMesh.getVerticesList()) {
            double centre_x = v.getX() - (THICKNESS/2.0d);
            double centre_y = v.getY() - (THICKNESS/2.0d);
            Color old = canvas.getColor();
            canvas.setColor(extractColor(v.getPropertiesList()));
            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, THICKNESS, THICKNESS);
            canvas.fill(point);
            canvas.setColor(old);
        }
    }

    private Color extractColor(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {
                //System.out.println(p.getValue());
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

        return new Color(red, green, blue, alpha/*, Transparency*/);
    }

}
