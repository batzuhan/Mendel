import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Random random = new Random();
        int pickedNumber = 20;
        Chromosome[] population = initializePopulation(10, pickedNumber);
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(population, pickedNumber);
        //geneticAlgorithm.printPopulation();
        int iterationLimit = 1000;
        int iteration = 0;
        do {
            ArrayList<Chromosome> fittestPair = geneticAlgorithm.selection();
            //ArrayList<Chromosome> selectedPair = geneticAlgorithm.rouletteWheel();
            Chromosome[] newChildren = geneticAlgorithm.crossover(fittestPair);
            Chromosome fittestChild = null;
            if(newChildren[0].calculateFitness()>newChildren[1].calculateFitness()){
                fittestChild = newChildren[0];
            }else{
                fittestChild = newChildren[1];
            }
            boolean val = new Random().nextInt(250) == 0;
            System.out.println(val);
            if (val) {
                geneticAlgorithm.mutation(fittestChild);
            }
            geneticAlgorithm.addChild(fittestChild);

            //System.out.println(geneticAlgorithm.selection().get(0).calculateFitness());
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
