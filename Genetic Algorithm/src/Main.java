import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Chromosome chromosome = new Chromosome();
        loadData("Genetic Algorithm/depends.rsf", chromosome);
        loadDeps("Genetic Algorithm/depends.rsf", chromosome);
        assignClusters(chromosome);
        //showClusters(chromosome);
        System.out.println(chromosome.toIntegerString());
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(chromosome);
        /*int maxDivisor = findMaxDivisor(chromosome.getGeneArray().size());
        for(int i =0; i<23; i++){
            System.out.println(geneticAlgorithm.createChromosome(chromosome,10).toIntegerString());
        }*/
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
            for(int i = 0; i<chromosome.getGeneArray().size(); i++){
                if(chromosome.getGeneArray().get(i).getName().equals(firstGene)){
                    for (int j = 0; j<chromosome.getGeneArray().size(); j++){
                        if(chromosome.getGeneArray().get(j).getName().equals(secondGene)){
                            chromosome.getGeneArray().get(i).getDependsList().add(chromosome.getGeneArray().get(j));
                            chromosome.getGeneArray().get(j).getDependsList().add(chromosome.getGeneArray().get(i));
                        }
                    }
                }

            }

        }
        scanner.close();
    }
    public static void assignClusters(Chromosome chromosome){
        Random random = new Random();
        int k = random.nextInt(236);
        for (Gene gene : chromosome.getGeneArray())
        {
            gene.setCluster(random.nextInt(k));
        }
    }
    public static void showClusters(Chromosome chromosome){
        Random random = new Random();
        for (Gene gene : chromosome.getGeneArray())
        {
            System.out.println(gene.getName()+"     "+gene.getCluster());
        }
    }
    public static int findMaxDivisor(int n){
            for (int i = n / 2; i >= 2; i--) {
                if (n % i == 0) {
                    return i;
                }
            }
            return 1;
    }
}
