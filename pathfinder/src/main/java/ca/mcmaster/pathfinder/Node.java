package ca.mcmaster.pathfinder;

public class Node {
    private int id;
    private String name;
    private double elevation;

    public Node(int id, String name, double elevation){
        this.id = id;
        this.name = name;
        this.elevation = elevation;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public double getElevation(){
        return elevation;
    }

    public void setElevation(double elevation){
        this.elevation = elevation;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", elevation=" + elevation +
                '}';
    }

}
