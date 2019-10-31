import java.util.ArrayList;

public class Gene {
    private String name;
    private ArrayList<Gene> dependsList;
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
}
