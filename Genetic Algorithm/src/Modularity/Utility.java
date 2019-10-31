package Modularity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Utility {

    private int clusterCount;
    private ArrayList<Organization> orgs;

    public Utility(ArrayList<Organization> orgs) {
        this.orgs = orgs;
        this.clusterCount = orgs.size();
    }


    public double calculateModularity() {
        double sum = 0;
        double m2 = (2 * this.getNumberOfEdges());

        Set<Node> nodes = new HashSet<>();
        for (int i = 0; i < orgs.size(); ++i) {
            for (int j = 0; j < orgs.get(i).getNodes().size(); ++j) {
                nodes.add(orgs.get(i).getNodes().get(j));
            }
        }

        for (Node v1 : nodes) {
            for (Node v2 : nodes) {
                if (!v1.equals(v2)) {
                    sum += isNeighbor(v1, v2) - ((double) calculateDegree(v1) * (double) calculateDegree(v2) / m2);

                }
            }
        }
        return sum / m2;
    }


    private int calculateDegree(Node node) {
        int degree = 0;

        degree += node.getConnected().size();

        return degree;
    }


    private int isNeighbor(Node n1, Node n2) {
        if (n1.getParent().equals(n2.getParent())) {
            return 1;
        } else {
            return 0;
        }
    }

    private int getNumberOfEdges() {
        int aux = 0;
        for (int i = 0; i < orgs.size(); ++i) {
            for (int j = 0; j < orgs.get(i).getNodes().size(); ++j) {
                aux += orgs.get(i).getNodes().get(j).getInDepCount();
                aux += orgs.get(i).getNodes().get(j).getOutDepCount();
            }
        }

        return aux;
    }

    public int getClusterCount() {
        return clusterCount;
    }
}
