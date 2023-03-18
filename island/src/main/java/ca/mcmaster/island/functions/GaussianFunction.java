package ca.mcmaster.island.functions;

public class GaussianFunction implements RadialFunction{
    private final double mean;
    private final double std;
    
    public GaussianFunction(double mean, double std) {
        this.mean = mean;
        this.std = std;
    }
    
    public double evaluate(double x) {
        return Math.exp(-(x - mean) * (x - mean) / (2 * std * std));
    }
}
