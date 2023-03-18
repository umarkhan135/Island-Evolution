package ca.mcmaster.island.functions;

public class LaplacianFunction implements RadialFunction{
    private final double mean;
    private final double std;
    
    public LaplacianFunction(double mean, double std) {
        this.mean = mean;
        this.std = std;
    }
    public double evaluate(double x) {
        return Math.exp(-(x - mean) * (x - mean) / (2 * std * std));
    }
}
