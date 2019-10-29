import java.util.ArrayList;
import java.util.Arrays;

public class Chromosome {
    private ArrayList<Gene> geneArray;

    public Chromosome() {
        this.geneArray = new ArrayList<>();
    }

    public ArrayList<Gene> getGeneArray() {
        return geneArray;
    }


    public String toIntegerString() {
        int[] genes = new int[geneArray.size()];
        for(int i = 0; i< geneArray.size(); i++){
            genes[i]= geneArray.get(i).getCluster();
        }
        return "Chromosome= "+ Arrays.toString(genes)+"" ;
    }

    public void setGeneArray(ArrayList<Gene> geneArray) {
        this.geneArray = geneArray;
    }
}
