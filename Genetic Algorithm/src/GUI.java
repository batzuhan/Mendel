import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI implements ActionListener {
    private JTextField population;
    private JTextField iteration;
    private JTextField cluster;
    private JFileChooser fileChooser;

    GUI() {
        JFrame frame = new JFrame("Mendel");
        frame.setLayout(new BorderLayout());
        frame.setSize(200, 140);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        population = new JTextField(10);
        iteration = new JTextField(10);
        cluster = new JTextField(10);
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
        frame.add(inFieldPane, BorderLayout.NORTH);
        JPanel submitPane = new JPanel();
        submitPane.setLayout(new FlowLayout());
        JButton submitButton = new JButton("Start!");
        submitButton.addActionListener(this);
        submitPane.add(submitButton);
        frame.add(submitPane, BorderLayout.CENTER);
        JPanel outFieldPane = new JPanel();
        outFieldPane.setLayout(new GridLayout(1, 2));
        frame.add(outFieldPane, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Start!")) {
            int populationCount = Integer.parseInt(population.getText());
            int iterationLimit = Integer.parseInt(iteration.getText());
            int clusterCount = Integer.parseInt(cluster.getText());
            try {
                Main.initialize(fileChooser.getSelectedFile().getPath(), populationCount, iterationLimit, clusterCount);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            Main.log.out.close();
            System.exit(0);
        }
    }
}