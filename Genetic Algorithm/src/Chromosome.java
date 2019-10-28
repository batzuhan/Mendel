import java.util.ArrayList;

public class Chromosome {
    private ArrayList<Gene> population;

    public Chromosome() {
        this.population = new ArrayList<>();
    }

    public ArrayList<Gene> getPopulation() {
        return population;
    }

}
