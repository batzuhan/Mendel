import java.util.ArrayList;
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

    public ArrayList<Chromosome> rouletteWheel(Chromosome[] population, boolean naturalFitnessScores, int selectionSize){
        Random random = new Random();
        double[] cumulativeFitnesses = new double[population.length];
        cumulativeFitnesses[0] = getAdjustedFitness(population[0].getFitness(), naturalFitnessScores);
        for (int i = 1; i < population.length; i++)
        {
            double fitness = getAdjustedFitness(population[i].getFitness(), naturalFitnessScores);
            cumulativeFitnesses[i] = cumulativeFitnesses[i - 1] + fitness;
        }

        ArrayList<Chromosome> selection = new ArrayList<>(selectionSize);
        for (int i = 0; i < selectionSize; i++)
        {
            double randomFitness = random.nextDouble() * cumulativeFitnesses[cumulativeFitnesses.length - 1];
            int index = Arrays.binarySearch(cumulativeFitnesses, randomFitness);
            if (index < 0)
            {
                index = Math.abs(index + 1);
            }
            selection.add(population[index]);
        }
        return selection;
    }
    private double getAdjustedFitness(double rawFitness, boolean naturalFitness){
        if (naturalFitness)
        {
            return rawFitness;
        }
        else
        {
            return rawFitness == 0 ? Double.POSITIVE_INFINITY : 1 / rawFitness;
        }
    }

    public Chromosome crossOver(Chromosome chromosomeA, Chromosome chromosomeB) {
        Random random = new Random();
        double fatherPercentage = random.nextInt(41) + 30;
        double motherPercentage = 100 - fatherPercentage;
        ArrayList<Gene> childGeneArray = new ArrayList<>();
        for (int i = 0; i < chromosomeA.getGeneArray().size(); i++) {
            if(i<(chromosomeA.getGeneArray().size()*(fatherPercentage/100))){
                childGeneArray.add(chromosomeA.getGeneArray().get(i));
            }else{
                childGeneArray.add(chromosomeB.getGeneArray().get(i));
            }
        }
        Chromosome childChromosome = new Chromosome();
        childChromosome.setGeneArray(childGeneArray);
        return childChromosome;
    }

    public void mutation(Chromosome chromosome) {
        Random random = new Random();
        chromosome.getGeneArray().get(random.nextInt(236)).setCluster( random.nextInt(chromosome.getGeneArray().size()));
    }
}
