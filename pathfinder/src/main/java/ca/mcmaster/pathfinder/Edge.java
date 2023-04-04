package ca.mcmaster.pathfinder;

public class Edge {
    private Node source;
    private Node destination;
    private double weight;

    public Edge(Node source, Node destination, double weight){
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public Node getSource(){
        return source;
    }

    public void setSource(Node source){
        this.source = source;
    }

    public Node getDestination(){
        return destination;
    }

    public void setDestination(Node destination){
        this.destination = destination;
    }

    public double getWeight(){
        return weight;
    }

    public void setWeight(double weight){
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "source=" + source +
                ", destination=" + destination +
                ", weight=" + weight +
                '}';
    }
}
