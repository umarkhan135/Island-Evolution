package ca.mcmaster.island.shapes;

import java.util.Random;

import ca.mcmaster.island.functions.*;

import java.awt.geom.Path2D;

public class RandomShape implements ShapeGenerator {
    
    private Path2D shape = new Path2D.Double();
    private final static int WIDTH = 400;  // image width
    private final static int HEIGHT = 400; // image height
    private final static int NUM_CENTERS = 10; // number of centers to use
    private final static double THRESHOLD = 0.5; // threshold value for binarizing the image
    
    
    public void generateShape(){
        
        Random random = new Random();
        
        // Define a set of radial functions
        double[] weights = {0.5, 0.3, 0.2}; // weights for each radial function
        RadialFunction[] functions = {
            new GaussianFunction(0.1, 0.1), // Gaussian function with mean = 0.1, std = 0.1
            new GaussianFunction(0.3, 0.2), // Gaussian function with mean = 0.3, std = 0.2
            new LaplacianFunction(0.5, 0.2) // Laplacian function with mean = 0.5, scale = 0.2
        };
        
        // Generate a set of random centers
        double[][] centers = new double[NUM_CENTERS][2];
        for (int i = 0; i < NUM_CENTERS; i++) {
            centers[i][0] = random.nextDouble() * WIDTH;
            centers[i][1] = random.nextDouble() * HEIGHT;
        }
        
        // Evaluate each radial function at each point in the image
        double[][] values = new double[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                double value = 0;
                for (int k = 0; k < functions.length; k++) {
                    double[] center = centers[random.nextInt(NUM_CENTERS)];
                    double distance = Math.sqrt(Math.pow(center[0] - i, 2) + Math.pow(center[1] - j, 2));
                    value += weights[k] * functions[k].evaluate(distance);
                }
                values[i][j] = value;
            }
        }
        
        // Threshold the image
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (values[i][j] > THRESHOLD) {
                    shape.lineTo(i, j);
                }
            }
        }
    }
    
    public Path2D getShape(){
        return shape;
    }
}
