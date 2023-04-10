package ca.mcmaster.pathfinder;

import java.util.HashMap;
import java.util.Map;

public class Edge implements IEdge {
    private final int id;
    private final INode source;
    private final INode target;
    private final Map<String, Object> attributes;

    public Edge(int id, INode source, INode target) {
        this.id = id;
        this.source = source;
        this.target = target;
        this.attributes = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public INode getSource() {
        return source;
    }

    public INode getTarget() {
        return target;
    }

    public void addAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }
}