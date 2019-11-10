import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Random random = new Random();
        int pickedNumber = 20;
        Chromosome[] population = initializePopulation(2360, pickedNumber);
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(population, pickedNumber);
        geneticAlgorithm.printPopulation();
        int iterationLimit = 200 * 236;
        int iteration = 0;
        do {
            Chromosome[] fittestPair = geneticAlgorithm.selection();
            //ArrayList<Chromosome> selectedPair = geneticAlgorithm.rouletteWheel();
            Chromosome fittestChild = geneticAlgorithm.crossover(fittestPair);
            boolean val = new Random().nextInt(25) == 0;
            if (val) {
                geneticAlgorithm.mutation(fittestChild);
            }
            geneticAlgorithm.addChild(fittestChild);

            System.out.println(geneticAlgorithm.selection()[0].calculateFitness());
            iteration++;
        } while (iteration < iterationLimit);
    }

    private static Chromosome[] initializePopulation(int chromosomeCount, int clusterCount) throws Exception {
        Chromosome[] population = new Chromosome[chromosomeCount];
        GeneticAlgorithm.Data data = loadData("Genetic Algorithm/depends.rsf");
        for (int i = 0; i < chromosomeCount; i++) {
            Chromosome baseChromosome = new Chromosome();
            loadDeps(data, baseChromosome);
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

    private static GeneticAlgorithm.Data loadData(String filePath) throws FileNotFoundException {
        ArrayList<String> current = new ArrayList<>();
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        scanner.useLocale(Locale.UK);
        int count = 0;
        while (scanner.hasNextLine()) {
            count++;
            scanner.nextLine();
        }
        String[] first = new String[count];
        String[] second = new String[count];
        int counter = 0;
        scanner.close();
        scanner = new Scanner(file);
        scanner.useLocale(Locale.UK);
        while (scanner.hasNext()) {
            String depends = scanner.next(); //ignore
            first[counter] = scanner.next();
            second[counter] = scanner.next();
            if (!current.contains(first[counter])) {
                current.add(first[counter]);
            }
            if (!current.contains(second[counter])) {
                current.add(second[counter]);
            }
            counter++;
        }
        scanner.close();
        return new GeneticAlgorithm.Data(current, first, second);
    }

    private static void loadDeps(GeneticAlgorithm.Data data, Chromosome chromosome) {
        for (String geneString : data.getGeneStrings()) {
            Gene gene = new Gene(geneString);
            chromosome.getGeneArray().add(gene);
        }
        for (int a = 0; a < data.getFirst().length; a++) {
            for (int i = 0; i < chromosome.getGeneArray().size(); i++) {
                if (chromosome.getGeneArray().get(i).getName().equals(data.getFirst()[a])) {
                    for (int j = 0; j < chromosome.getGeneArray().size(); j++) {
                        if (chromosome.getGeneArray().get(j).getName().equals(data.getSecond()[a])) {
                            chromosome.getGeneArray().get(i).getDependsList().add(chromosome.getGeneArray().get(j));
                            chromosome.getGeneArray().get(j).getDependsList().add(chromosome.getGeneArray().get(i));
                        }
                    }
                }
            }
        }
    }

    private static void assignClusters(Chromosome chromosome, int clusterCount) {
        Random random = new Random();
        for (int i = 0; i < chromosome.getGeneArray().size(); i++) {
            chromosome.getGeneArray().get(i).setCluster(random.nextInt(clusterCount));
        }
    }

    private static void updateInOutDeps(Chromosome chromosome) {
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
