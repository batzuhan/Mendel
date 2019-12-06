import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI implements ActionListener {
    private JTextField population;
    private JTextField iteration;
    private JTextField cluster;
    private JFileChooser fileChooser;
    private JRadioButton button1;
    private JRadioButton button2;

    GUI() {
        JFrame frame = new JFrame("Mendel");
        frame.setLayout(new BorderLayout());
        frame.setSize(200, 190);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        population = new JTextField("50",10);
        iteration = new JTextField("50",10);
        cluster = new JTextField("20",10);
        fileChooser = new JFileChooser("Genetic Algorithm/");
        JPanel inFieldPane = new JPanel();
        inFieldPane.setLayout(new GridLayout(3, 2));
        fileChooser.showSaveDialog(null);
        inFieldPane.add(new JLabel("Population:"));
        inFieldPane.add(population);
        population.addActionListener(this);
        inFieldPane.add(new JLabel("Generation:"));
        inFieldPane.add(iteration);
        iteration.addActionListener(this);
        inFieldPane.add(new JLabel("Cluster:"));
        inFieldPane.add(cluster);
        cluster.addActionListener(this);
        JPanel radioPane = new JPanel();
        radioPane.setLayout(new GridLayout(2, 2));
        radioPane.add(new JLabel("Mutation ratio:"));
        ButtonGroup buttonGroup = new ButtonGroup();
        button1 = new JRadioButton("Fixed", true);
        buttonGroup.add(button1);
        button2 = new JRadioButton("Optimal", false);
        buttonGroup.add(button2);
        radioPane.add(button1);
        radioPane.add(new JLabel(""));
        radioPane.add(button2);
        button1.addActionListener(this);
        button2.addActionListener(this);
        frame.add(inFieldPane, BorderLayout.NORTH);
        frame.add(radioPane, BorderLayout.CENTER);
        JPanel submitPane = new JPanel();
        submitPane.setLayout(new FlowLayout());
        JButton submitButton = new JButton("Start!");
        submitButton.addActionListener(this);
        submitPane.add(submitButton);
        frame.add(submitPane, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Start!")) {
            int populationCount = Integer.parseInt(population.getText());
            int iterationLimit = Integer.parseInt(iteration.getText());
            int clusterCount = Integer.parseInt(cluster.getText());
            boolean mutation = button1.isSelected();
            try {
                Main.initialize(fileChooser.getSelectedFile().getPath(), populationCount, iterationLimit, clusterCount, mutation);//false for dependencyMutation
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Main.log.out.close();
            System.exit(0);
        }
    }
}