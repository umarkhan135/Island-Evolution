package ca.mcmaster.pathfinder;

import java.util.HashMap;
import java.util.Map;

class Node implements INode {
    private final int id;
    private final Map<String, Object> attributes;

    public Node(int id) {
        this.id = id;
        this.attributes = new HashMap<>();
    }

    public int getId() {
        return id;
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