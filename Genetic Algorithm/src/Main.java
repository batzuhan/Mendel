import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Chromosome chromosome = new Chromosome();
        loadData("Genetic Algorithm/depends.rsf", chromosome);
        loadDeps("Genetic Algorithm/depends.rsf", chromosome);
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
                chromosome.getPopulation().add(gene);
            }
            if (!current.contains(secondGene)) {
                current.add(secondGene);
                Gene gene = new Gene(secondGene);
                chromosome.getPopulation().add(gene);
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
            for(int i =0; i<chromosome.getPopulation().size(); i++){
                if(chromosome.getPopulation().get(i).getName().equals(firstGene)){
                    for (int j = 0; j<chromosome.getPopulation().size(); j++){
                        if(chromosome.getPopulation().get(j).getName().equals(secondGene)){
                            chromosome.getPopulation().get(i).getDependsList().add(chromosome.getPopulation().get(j));
                            chromosome.getPopulation().get(j).getDependsList().add(chromosome.getPopulation().get(i));
                        }
                    }
                }

            }

        }
        scanner.close();
    }

}
