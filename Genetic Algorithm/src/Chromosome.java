import Modularity.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Chromosome {
    private ArrayList<Gene> geneArray;

    public Chromosome() {
        this.geneArray = new ArrayList<>();
    }

    public ArrayList<Gene> getGeneArray() {
        return geneArray;
    }

    public void setGeneArray(ArrayList<Gene> geneArray) {
        this.geneArray = geneArray;
    }

    public double getFitness() {
        return calculateFitness();
    }

    public double calculateFitness() {
        double sum = 0;
        double m2 = (2 * this.getNumberOfEdges());

        Set<Gene> nodes = new HashSet<>();
        for (int i = 0; i < geneArray.size(); ++i) {
            nodes.add(geneArray.get(i));
        }
        for (Gene v1 : nodes) {
            for (Gene v2 : nodes) {
                if (!v1.equals(v2)) {
                    sum += isNeighbor(v1, v2) - ((double) calculateDegree(v1) * (double) calculateDegree(v2) / m2);

                }
            }
        }
        return sum / m2;
    }

    private int calculateDegree(Gene gene) {
        int degree = 0;
        degree += gene.getDependsList().size();
        return degree;
    }


    private int isNeighbor(Gene g1, Gene g2) {
        if (g1.getCluster() == (g2.getCluster())) {
            return 1;
        } else {
            return 0;
        }
    }

    private int getNumberOfEdges() {
        int aux = 0;
        for (int i = 0; i < geneArray.size(); ++i) {
            aux += geneArray.get(i).getInDepCount();
            aux += geneArray.get(i).getOutDepCount();

        }
        return aux;
    }

}
