import java.util.ArrayList;

public class Gene {
    private String name;
    private ArrayList<Gene> dependsList;
    private int inDepCount;
    private int outDepCount;
    private int cluster;

    public Gene(String name) {
        this.name = name;
        this.dependsList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Gene> getDependsList() {
        return dependsList;
    }

    public int getCluster() {
        return cluster;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
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

    public void setDependsList(ArrayList<Gene> dependsList) {
        this.dependsList = dependsList;
    }
}
