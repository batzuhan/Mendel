import java.util.ArrayList;


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

        for (int i = 0; i < geneArray.size()-1; i++) {
            for (int j = i+1; j < geneArray.size(); j++) {
                if (!geneArray.get(i).equals(geneArray.get(j))) {
                    sum += isNeighbor(geneArray.get(i), geneArray.get(j)) - ((double) calculateDegree(geneArray.get(i)) * (double) calculateDegree(geneArray.get(j)) / m2);
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

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    public void printArray(){
        for(Gene g : this.getGeneArray()){
            System.out.print(g.getCluster()+" ");
        }
        System.out.println();
    }
}
