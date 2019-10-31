import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Chromosome[] population = initializePopulation(50, 50);
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(population, 50);
        geneticAlgorithm.printPopulation();
        int iterationLimit = 100;
        int iteration = 0;
        do {
            ArrayList<Chromosome> fittestPair = geneticAlgorithm.selection();
            //ArrayList<Chromosome> selectedPair = geneticAlgorithm.rouletteWheel();
            Chromosome newChild = geneticAlgorithm.crossover(fittestPair);
            boolean val = new Random().nextInt(250) == 0;
            if (val) {
                geneticAlgorithm.mutation(newChild);
            }
            geneticAlgorithm.addChild(newChild);

            System.out.println(geneticAlgorithm.calculatePopulationFitness());
            iteration++;
        } while (iteration < iterationLimit);
    }

    public static Chromosome[] initializePopulation(int chromosomeCount, int clusterCount) throws FileNotFoundException {
        Chromosome[] population = new Chromosome[chromosomeCount];
        for (int i = 0; i < chromosomeCount; i++) {
            Chromosome baseChromosome = new Chromosome();
            loadData("Genetic Algorithm/depends.rsf", baseChromosome);
            loadDeps("Genetic Algorithm/depends.rsf", baseChromosome);
            population[i] = baseChromosome;
        }
        for (int i = 0; i < chromosomeCount; i++) {
            assignClusters(population[i], clusterCount);
        }
        for (int i = 0; i < chromosomeCount; i++) {
            updateInOutDeps(population[i]);
        }
        return population;
    }

    public static void loadData(String filePath, Chromosome chromosome) throws FileNotFoundException {
        ArrayList<String> current = new ArrayList<>();
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        scanner.useLocale(Locale.UK);
        while (scanner.hasNext()) {
            String depends = scanner.next(); //ignore
            String firstGene = scanner.next();
            String secondGene = scanner.next();
            if (!current.contains(firstGene)) {
                current.add(firstGene);
                Gene gene = new Gene(firstGene);
                chromosome.getGeneArray().add(gene);
            }
            if (!current.contains(secondGene)) {
                current.add(secondGene);
                Gene gene = new Gene(secondGene);
                chromosome.getGeneArray().add(gene);
            }
        }
        scanner.close();
    }

    public static void loadDeps(String filePath, Chromosome chromosome) throws FileNotFoundException {
        ArrayList<String> current = new ArrayList<>();
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        scanner.useLocale(Locale.UK);
        while (scanner.hasNext()) {
            String depends = scanner.next(); //ignore
            String firstGene = scanner.next();
            String secondGene = scanner.next();
            for (int i = 0; i < chromosome.getGeneArray().size(); i++) {
                if (chromosome.getGeneArray().get(i).getName().equals(firstGene)) {
                    for (int j = 0; j < chromosome.getGeneArray().size(); j++) {
                        if (chromosome.getGeneArray().get(j).getName().equals(secondGene)) {
                            chromosome.getGeneArray().get(i).getDependsList().add(chromosome.getGeneArray().get(j));
                            chromosome.getGeneArray().get(j).getDependsList().add(chromosome.getGeneArray().get(i));
                        }
                    }
                }

            }

        }
        scanner.close();
    }

    public static void assignClusters(Chromosome chromosome, int clusterCount) {
        Random random = new Random();
        for (int i = 0; i < chromosome.getGeneArray().size(); i++) {
            chromosome.getGeneArray().get(i).setCluster(random.nextInt(clusterCount));
        }
    }

    public static void updateInOutDeps(Chromosome chromosome) {
        for (int i = 0; i < chromosome.getGeneArray().size(); i++) {
            for (int j = 0; j < chromosome.getGeneArray().get(i).getDependsList().size(); j++) {
                if (chromosome.getGeneArray().get(i).getCluster() == chromosome.getGeneArray().get(i).getDependsList().get(j).getCluster()) {
                    chromosome.getGeneArray().get(i).setInDepCount(chromosome.getGeneArray().get(i).getInDepCount() + 1);

                } else {
                    chromosome.getGeneArray().get(i).setOutDepCount(chromosome.getGeneArray().get(i).getOutDepCount() + 1);
                }
            }
        }
    }


}
