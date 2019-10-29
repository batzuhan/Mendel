import java.util.ArrayList;
import java.util.Arrays;

public class Chromosome {
    private ArrayList<Gene> population;

    public Chromosome() {
        this.population = new ArrayList<>();
    }

    public ArrayList<Gene> getPopulation() {
        return population;
    }


    public String toIntegerString() {
        int[] genes = new int[population.size()];
        for(int i=0; i<population.size(); i++){
            genes[i]=population.get(i).getCluster();
        }
        return "Chromosome= "+ Arrays.toString(genes)+"" ;
    }
}
