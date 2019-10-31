package Modularity;

import java.io.*;
import java.util.*;

public class Fitness {
    static ArrayList<Organization> orgs = new ArrayList<>();

    public double calculateFitnessScore() {

        String depends = "depends.rsf";
        String contains = "contains.rsf";

        //loadOrganizations(contains);
        //loadNodes(contains);
        loadDeps(depends);
        Utility utility = new Utility(orgs);

        return (utility.calculateModularity());
    }

    private void loadOrganizations(String reader) {
        try {
            System.out.println("Trying to load cluster names from the file.");
            File file = new File(reader);
            Scanner s = new Scanner(file);
            while (s.hasNextLine()) {
                String str = s.nextLine();
                StringTokenizer tokenizer = new StringTokenizer(str);
                tokenizer.nextToken();
                String s2 = tokenizer.nextToken();
                if (findOrganization(s2) == null) {
                    Organization org = new Organization(s2);
                    orgs.add(org);
                    System.out.println(org.getName());
                } else {
                    //continue
                }
            }
            System.out.println("Cluster names loaded successfully.");
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private void loadNodes(String reader) {
        try {
            System.out.println("Trying to load the classes from the file.");
            File file = new File(reader);
            Scanner s = new Scanner(file);
            while (s.hasNextLine()) {
                String str = s.nextLine();
                StringTokenizer tokenizer = new StringTokenizer(str);
                tokenizer.nextToken();
                String orgName = tokenizer.nextToken();
                Node node = new Node(tokenizer.nextToken());
                findOrganization(orgName).getNodes().add(node);
                node.setParent(findOrganization(orgName));
            }
            System.out.println("Classes loaded successfully.");
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private void loadDeps(String reader) {
        try {
            System.out.println("Trying to load dependencies from the file.");
            File file = new File("depends.rsf");
            Scanner s = new Scanner(file);
            while (s.hasNextLine()) {
                String str = s.nextLine();
                StringTokenizer tokenizer = new StringTokenizer(str);
                tokenizer.nextToken();
                String firstNodeName = tokenizer.nextToken();
                String secondNodeName = tokenizer.nextToken();
                Node sourceNode = findNode(firstNodeName);
                Node dependentNode = findNode(secondNodeName);
                if (sourceNode.getParent().equals(dependentNode.getParent())) {
                    sourceNode.getConnected().add(dependentNode);
                    dependentNode.getConnected().add(sourceNode);
                    sourceNode.setInDepCount(sourceNode.getInDepCount() + 1);
                    dependentNode.setInDepCount(dependentNode.getInDepCount() + 1);
                } else {
                    sourceNode.getConnected().add(dependentNode);
                    dependentNode.getConnected().add(sourceNode);
                    sourceNode.setOutDepCount(sourceNode.getOutDepCount() + 1);
                    dependentNode.setOutDepCount(dependentNode.getOutDepCount() + 1);
                }
                sourceNode.getParent().getDeps().add(dependentNode.getParent());
            }
            System.out.println("Dependencies loaded successfully.");
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private  Organization findOrganization(String name) {
        for (int i = 0; i < orgs.size(); i++) {
            Organization tmp = orgs.get(i);
            if (tmp.getName().equals(name)) return tmp;
        }
        return null;
    }

    private  Node findNode(String nodeName) {
        for (int i = 0; i < orgs.size(); i++) {
            Organization tmp = orgs.get(i);
            for (int j = 0; j < tmp.getNodes().size(); j++) {
                if (tmp.getNodes().get(j).getName().equals(nodeName)) return tmp.getNodes().get(j);
            }
        }
        return null;
    }
}
