import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GeneticAlgorithm {
    private Chromosome[] population;
    private int clusterCount;

    public GeneticAlgorithm(Chromosome[] population, int clusterCount) {
        this.population = population;
        this.clusterCount = clusterCount;
    }

    public ArrayList<Chromosome> selection() {
        int fittest = 0;
        double max = Double.MIN_VALUE;
        for (int i = 0; i < this.population.length; i++) {
            if (this.population[i].calculateFitness() > max) {
                max = this.population[i].calculateFitness();
                fittest = i;
            }
        }
        int secondFit = 0;
        ArrayList<Chromosome> selected = new ArrayList<>();
        selected.add(this.population[fittest]);
        selected.add(getSecondFittest());
        return selected;
    }

    public Chromosome getSecondFittest() {
        int maxFit1 = 0;
        int maxFit2 = 0;
        for (int i = 0; i < this.population.length; i++) {
            if (this.population[i].getFitness() > this.population[maxFit1].getFitness()) {
                maxFit2 = maxFit1;
                maxFit1 = i;
            } else if (this.population[i].getFitness() > this.population[maxFit2].getFitness()) {
                maxFit2 = i;
            }
        }
        return this.population[maxFit2];
    }
    /*public ArrayList<Chromosome> rouletteWheel(boolean naturalFitnessScores, int selectionSize) {
        Random random = new Random();
        double[] cumulativeFitnesses = new double[this.population.length];
        cumulativeFitnesses[0] = getAdjustedFitness(this.population[0].getFitness(), naturalFitnessScores);
        for (int i = 1; i < this.population.length; i++) {
            double fitness = getAdjustedFitness(this.population[i].getFitness(), naturalFitnessScores);
            cumulativeFitnesses[i] = cumulativeFitnesses[i - 1] + fitness;
        }

        ArrayList<Chromosome> selection = new ArrayList<>(selectionSize);
        for (int i = 0; i < selectionSize; i++) {
            double randomFitness = random.nextDouble() * cumulativeFitnesses[cumulativeFitnesses.length - 1];
            int index = Arrays.binarySearch(cumulativeFitnesses, randomFitness);
            if (index < 0) {
                index = Math.abs(index + 1);
            }
            selection.add(this.population[index]);
        }
        return selection;
    }

    private double getAdjustedFitness(double rawFitness, boolean naturalFitness) {
        if (naturalFitness) {
            return rawFitness;
        } else {
            return rawFitness == 0 ? Double.POSITIVE_INFINITY : 1 / rawFitness;
        }
    }*/

    public Chromosome crossover(ArrayList<Chromosome> fittestPair) {
        Chromosome chromosomeA = fittestPair.get(0);
        Chromosome chromosomeB = fittestPair.get(1);
        Random random = new Random();
        double fatherPercentage = random.nextInt(41) + 30;
        double motherPercentage = 100 - fatherPercentage;
        ArrayList<Gene> childGeneArray = new ArrayList<>();
        for (int i = 0; i < chromosomeA.getGeneArray().size(); i++) {
            if (i < (chromosomeA.getGeneArray().size() * (fatherPercentage / 100))) {
                childGeneArray.add(chromosomeA.getGeneArray().get(i));
            } else {
                childGeneArray.add(chromosomeB.getGeneArray().get(i));
            }
        }
        Chromosome childChromosome = new Chromosome();
        childChromosome.setGeneArray(childGeneArray);
        return childChromosome;
    }

    public void mutation(Chromosome chromosome) {
        Random random = new Random();
        chromosome.getGeneArray().get(random.nextInt(chromosome.getGeneArray().size())).setCluster(random.nextInt(this.clusterCount));
    }

    public void printPopulation() {
        for (int i = 0; i < this.population.length; i++) {
            System.out.println(this.toIntegerString(this.population[i]));
        }
    }

    public String toIntegerString(Chromosome chromosome) {
        int[] genes = new int[chromosome.getGeneArray().size()];
        for (int i = 0; i < chromosome.getGeneArray().size(); i++) {
            genes[i] = chromosome.getGeneArray().get(i).getCluster();
        }
        return "Chromosome= " + Arrays.toString(genes) + "";
    }

    public double calculatePopulationFitness() {
        double cumulativeFitness = 0;
        for (int i = 0; i < this.population.length; i++) {
            cumulativeFitness = this.population[i].calculateFitness() + cumulativeFitness;
        }
        return cumulativeFitness;
    }

    public void addChild(Chromosome newChild) {
        int toDie = 0;
        double min = Double.MAX_VALUE;
        for (int i = 0; i < this.population.length; i++) {
            if (this.population[i].calculateFitness() < min) {
                min = this.population[i].calculateFitness();
                toDie = i;
            }
        }
        this.population[toDie] = newChild;
    }
}
