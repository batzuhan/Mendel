package Modularity;

import java.util.ArrayList;
import java.util.HashMap;

public class Organization {
    private String name;
    private ArrayList<Node> nodes;
    private ArrayList<Organization> deps;
    private HashMap<Organization, Integer> depMap;

    public Organization(String name) {
        this.name = name;
        this.nodes = new ArrayList<>();
        this.deps = new ArrayList<>();
        this.depMap = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public ArrayList<Organization> getDeps() {
        return deps;
    }
}
