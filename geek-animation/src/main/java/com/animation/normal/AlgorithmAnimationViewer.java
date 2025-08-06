package com.animation.normal;

import javax.swing.*;
import java.awt.*;

public class AlgorithmAnimationViewer extends JFrame {

    private JComboBox<String> algorithmComboBox;
    private JPanel animationPanel;
    private CardLayout cardLayout;

    public AlgorithmAnimationViewer() {
        setTitle("Algorithm Animation Viewer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel for algorithm selection
        JPanel topPanel = new JPanel();
        algorithmComboBox = new JComboBox<>(new String[]{"NO046 - Permutations", "NO047 - Permutations II", "NO784 - Letter Case Permutation"});
        topPanel.add(new JLabel("Select Algorithm:"));
        topPanel.add(algorithmComboBox);
        add(topPanel, BorderLayout.NORTH);

        // Animation panel using CardLayout
        cardLayout = new CardLayout();
        animationPanel = new JPanel(cardLayout);
        animationPanel.add(new PermutationsAnimation(), "NO046 - Permutations");
        animationPanel.add(new Permutations2Animation(), "NO047 - Permutations II");
        animationPanel.add(new LetterCaseAnimation(), "NO784 - Letter Case Permutation");
        add(animationPanel, BorderLayout.CENTER);

        // Controller
        algorithmComboBox.addActionListener(e -> {
            String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
            cardLayout.show(animationPanel, selectedAlgorithm);
        });

        // Initial view
        cardLayout.show(animationPanel, "NO046 - Permutations");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AlgorithmAnimationViewer viewer = new AlgorithmAnimationViewer();
            viewer.setVisible(true);
        });
    }
}