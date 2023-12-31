package ca.mcmaster.pathfinder;

import java.util.*;

public interface INode {
    int getId();
    void addAttribute(String name, Object value);
    Object getAttribute(String name);
    Map<String, Object> getAttributes();
}
