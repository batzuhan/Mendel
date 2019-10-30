import java.util.Arrays;
import java.util.Random;

public class GeneticAlgorithm {
    private Chromosome[] population;

    public GeneticAlgorithm(Chromosome[] population) {
        this.population = population;
    }

    public String toIntegerString(Chromosome chromosome) {
        int[] genes = new int[chromosome.getGeneArray().size()];
        for (int i = 0; i < chromosome.getGeneArray().size(); i++) {
            genes[i] = chromosome.getGeneArray().get(i).getCluster();
        }
        return "Chromosome= " + Arrays.toString(genes) + "";
    }

    public void rouletteWheel() {

    }

    public void crossOver() {

    }

    public void mutation(Chromosome chromosome) {
            Random random = new Random();
            chromosome.getGeneArray().get(random.nextInt(236)).setCluster( random.nextInt(chromosome.getGeneArray().size()));
    }
}
