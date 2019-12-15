import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GeneticAlgorithm {
    private Chromosome[] population;
    private int clusterCount;

    GeneticAlgorithm(Chromosome[] population, int clusterCount) {
        this.population = population;
        this.clusterCount = clusterCount;
    }

    Chromosome[] selection() {
        int fittest = 0;
        int secondFittest = 0;
        for (int i = 0; i < this.population.length; i++) {
            if (this.population[i].getFitness() > this.population[fittest].getFitness()) {
                secondFittest = fittest;
                fittest = i;
            }else if ((this.population[i].getFitness() > this.population[secondFittest].getFitness()) && (!this.population[i].equals(this.population[fittest]))) {
                secondFittest = i;
            }
        }
        Chromosome[] selected = new Chromosome[2];
        selected[0] = this.population[fittest];
        selected[1] = this.population[secondFittest];
        return selected;
    }


    public Chromosome[] rouletteWheel(boolean naturalFitnessScores, int selectionSize) {
        Random random = new Random();
        double[] cumulativeFitnesses = new double[this.population.length];
        cumulativeFitnesses[0] = this.population[0].getFitness();
        for (int i = 1; i < this.population.length; i++) {
            double fitness = this.population[i].getFitness();
            cumulativeFitnesses[i] = cumulativeFitnesses[i - 1] + fitness;
        }

        Chromosome[] selection = new Chromosome[selectionSize];
        for (int i = 0; i < selectionSize; i++) {
            double randomFitness = random.nextDouble() * cumulativeFitnesses[cumulativeFitnesses.length - 1];
            int index = Arrays.binarySearch(cumulativeFitnesses, randomFitness);
            if (index < 0) {
                index = Math.abs(index + 1);
            }
            selection[i] = this.population[index];
        }
        return selection;
    }

    Chromosome crossover(Chromosome[] fittestPair) {
        Chromosome chromosomeA = fittestPair[0];
        Chromosome chromosomeB = fittestPair[1];
        Random random = new Random();
        int pivot = random.nextInt(41) + 30;

        ArrayList<Gene> childGeneArray = new ArrayList<>();
        ArrayList<Gene> secondChildGeneArray = new ArrayList<>();
        for (int i = 0; i < pivot; i++) {
            Gene newGeneA = new Gene(chromosomeB.getGeneArray().get(i).getName());
            newGeneA.setCluster(chromosomeB.getGeneArray().get(i).getCluster());
            newGeneA.setInDepCount(chromosomeB.getGeneArray().get(i).getInDepCount());
            newGeneA.setOutDepCount(chromosomeB.getGeneArray().get(i).getOutDepCount());
            newGeneA.setDependsList(chromosomeB.getGeneArray().get(i).getDependsList());
            childGeneArray.add(newGeneA);
            Gene newGeneB = new Gene(chromosomeA.getGeneArray().get(i).getName());
            newGeneB.setCluster(chromosomeA.getGeneArray().get(i).getCluster());
            newGeneB.setInDepCount(chromosomeA.getGeneArray().get(i).getInDepCount());
            newGeneB.setOutDepCount(chromosomeA.getGeneArray().get(i).getOutDepCount());
            newGeneB.setDependsList(chromosomeA.getGeneArray().get(i).getDependsList());
            secondChildGeneArray.add(newGeneB);
        }
        for (int j = pivot; j < chromosomeA.getGeneArray().size(); j++) {
            Gene newGeneC = new Gene(chromosomeA.getGeneArray().get(j).getName());
            newGeneC.setCluster(chromosomeA.getGeneArray().get(j).getCluster());
            newGeneC.setInDepCount(chromosomeA.getGeneArray().get(j).getInDepCount());
            newGeneC.setOutDepCount(chromosomeA.getGeneArray().get(j).getOutDepCount());
            newGeneC.setDependsList(chromosomeA.getGeneArray().get(j).getDependsList());
            childGeneArray.add(newGeneC);
            Gene newGeneD = new Gene(chromosomeB.getGeneArray().get(j).getName());
            newGeneD.setCluster(chromosomeB.getGeneArray().get(j).getCluster());
            newGeneD.setInDepCount(chromosomeB.getGeneArray().get(j).getInDepCount());
            newGeneD.setOutDepCount(chromosomeB.getGeneArray().get(j).getOutDepCount());
            newGeneD.setDependsList(chromosomeB.getGeneArray().get(j).getDependsList());
            secondChildGeneArray.add(newGeneD);
        }
        Chromosome childChromosome = new Chromosome();
        childChromosome.setGeneArray(childGeneArray);
        Chromosome secondChildChromosome = new Chromosome();
        secondChildChromosome.setGeneArray(secondChildGeneArray);
        Chromosome fittestChild = null;
        if (childChromosome.calculateFitness() > secondChildChromosome.calculateFitness()) {
            fittestChild = childChromosome;
        } else {
            fittestChild = secondChildChromosome;
        }
        return fittestChild;
    }

    void mutation(Chromosome chromosome) {
        Random random = new Random();
        for (Gene var : chromosome.getGeneArray()) {
            boolean val = new Random().nextInt(25) == 0;
            if (val) {
                var.setCluster(random.nextInt(this.clusterCount));
            }

        }
    }

    void dependencyMutation(Chromosome chromosome) {
        Random random = new Random();
        for (Gene var : chromosome.getGeneArray()) {
            double out = var.getOutDepCount();
            double in = var.getInDepCount();
            double sum = in + out;
            double probability = (out) / ((sum) * 100);

            boolean val = new Random().nextDouble() < probability;
            if (val) {
                var.setCluster(random.nextInt(this.clusterCount));
            }

        }
    }

    void printPopulation() {
        for (Chromosome chromosome : this.population) {
            Main.log.toFile(this.toIntegerString(chromosome));
        }
    }

    void printArrayNumbers() {
        for (Chromosome chromosome : this.population) {
            Main.log.toFile(chromosome.getGeneArray().toString());
        }
    }

    private String toIntegerString(Chromosome chromosome) {
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

    void addChild(Chromosome newChild) {
        int toDie = 0;
        double min = Double.MAX_VALUE;
        for (int i = 0; i < this.population.length; i++) {
            if (this.population[i].calculateFitness() < min) {
                min = this.population[i].calculateFitness();
                toDie = i;
            }
        }
        if (this.population[toDie].calculateFitness() < newChild.calculateFitness()) {
            this.population[toDie] = newChild;
        }
    }

    static class Data {
        private ArrayList<String> geneStrings;
        private String[] first;
        private String[] second;

        Data(ArrayList<String> geneStrings, String[] first, String[] second) {
            this.geneStrings = geneStrings;
            this.first = first;
            this.second = second;
        }

        ArrayList<String> getGeneStrings() {
            return geneStrings;
        }

        String[] getFirst() {
            return first;
        }

        String[] getSecond() {
            return second;
        }
    }
}
