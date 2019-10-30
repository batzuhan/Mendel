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

    public void setGeneArray(ArrayList<Gene> geneArray) {
        this.geneArray = geneArray;
    }

}
