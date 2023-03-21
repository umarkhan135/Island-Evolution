package ca.mcmaster.island.functions;

import java.lang.Math;

public class RadialFunction {
    
    private double[] coefficients;
    
    public RadialFunction(double[] coefficients) {
        this.coefficients = coefficients;
    }
    
    public double evaluate(double angle) {
        /* Evaluates the radial function at the given angle. */
        double radius = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            radius += this.coefficients[i] * Math.cos(i * angle);
        }
        return radius;
    }
}


