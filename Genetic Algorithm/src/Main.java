import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        int chromosomeCount = 50;
        Chromosome[] population = new Chromosome[chromosomeCount];
        for (int i = 0; i < chromosomeCount; i++) {
            Chromosome baseChromosome = new Chromosome();
            loadData("Genetic Algorithm/depends.rsf", baseChromosome);
            loadDeps("Genetic Algorithm/depends.rsf", baseChromosome);
            population[i] = baseChromosome;
        }
        int clusterCount = 50;
        for (int i = 0; i < chromosomeCount; i++) {
            assignClusters(population[i], clusterCount);
        }

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(population);
        for (int i = 0; i < chromosomeCount; i++) {
            System.out.println(geneticAlgorithm.toIntegerString(population[i]));
        }

    }

    public static void loadData(String filePath, Chromosome chromosome) throws FileNotFoundException {
        ArrayList<String> current = new ArrayList<>();
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        scanner.useLocale(Locale.UK);
        while (scanner.hasNext()) {
            String depends = scanner.next();
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
            String depends = scanner.next();
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
}
