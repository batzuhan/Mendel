import java.util.ArrayList;


public class Chromosome {
    private ArrayList<Gene> geneArray;

    public Chromosome() {
        this.geneArray = new ArrayList<>();
    }

    public ArrayList<Gene> getGeneArray() {
        return this.geneArray;
    }

    public void setGeneArray(ArrayList<Gene> geneArray) {
        this.geneArray = geneArray;
    }

    public Double getFitness() {
        return calculateFitness();
    }

    public Double calculateFitness() {
        Double sum = 0.0;
        Double m2 = (double) (2 * this.getNumberOfEdges());

        for (int i = 0; i < this.geneArray.size()-1; i++) {
            for (int j = i+1; j < this.geneArray.size(); j++) {
                if (!this.geneArray.get(i).equals(this.geneArray.get(j))) {
                    sum += isNeighbor(this.geneArray.get(i), this.geneArray.get(j)) - ((double) calculateDegree(this.geneArray.get(i)) * (double) calculateDegree(this.geneArray.get(j)) / m2);
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
        for (int i = 0; i < this.geneArray.size(); ++i) {
            aux += this.geneArray.get(i).getInDepCount();
            aux += this.geneArray.get(i).getOutDepCount();

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
