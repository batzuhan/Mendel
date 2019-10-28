import java.util.ArrayList;

public class Gene {
    private String name;
    private ArrayList<Gene> dependsList;

    public Gene(String name) {
        this.name = name;
        this.dependsList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Gene> getDependsList() {
        return dependsList;
    }
}
