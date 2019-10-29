import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm {
    private Chromosome chromosome;

    public GeneticAlgorithm(Chromosome chromosome) {
        this.chromosome = chromosome;
    }

    /*public Chromosome createChromosome(Chromosome chromosome,int k){
        Random random = new Random();
        ArrayList<Gene> shortChromosome = new ArrayList<>();
        for(int i=0; i<k; i++){
            int randomPick = random.nextInt(chromosome.getGeneArray().size());
            shortChromosome.add(chromosome.getGeneArray().get(randomPick));
            chromosome.getGeneArray().remove(randomPick);
        }
        Chromosome newChromosome = new Chromosome();
        newChromosome.setGeneArray(shortChromosome);
        return newChromosome;
    }*/

    public void rouletteWheel(){

    }
    public void crossOver(){

    }
    public void mutation(){

    }
}
