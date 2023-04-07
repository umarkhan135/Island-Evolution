package ca.mcmaster.pathfinder;

import java.util.Map;

interface IEdge {
    int getId();
    INode getSource();
    INode getTarget();
    void addAttribute(String name, Object value);
    Object getAttribute(String name);
    Map<String, Object> getAttributes();
}
