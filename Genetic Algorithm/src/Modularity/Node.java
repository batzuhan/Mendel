package Modularity;

import java.util.ArrayList;

public class Node {
    private String name;
    private Organization parent;
    private int inDepCount;
    private int outDepCount;
    private ArrayList<Node> connected;

    public Node(String name) {
        this.name = name;
        this.inDepCount = 0;
        this.outDepCount = 0;
        this.connected = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Organization getParent() {
        return parent;
    }

    public void setParent(Organization parent) {
        this.parent = parent;
    }

    public int getInDepCount() {
        return inDepCount;
    }

    public void setInDepCount(int inDepCount) {
        this.inDepCount = inDepCount;
    }

    public int getOutDepCount() {
        return outDepCount;
    }

    public void setOutDepCount(int outDepCount) {
        this.outDepCount = outDepCount;
    }

    public ArrayList<Node> getConnected() {
        return connected;
    }

}
