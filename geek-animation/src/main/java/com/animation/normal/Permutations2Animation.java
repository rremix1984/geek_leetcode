package com.animation.normal;

import com.animation.normal.permutations2.AnimationState;
import com.animation.normal.permutations2.BacktrackingPermutation2;
import com.animation.normal.permutations2.Permutation2Algorithm;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Permutations2Animation extends JPanel {

    private static final int ELEMENT_SIZE = 40;
    private static final int PADDING = 20;

    private final Permutation2Algorithm algorithm;
    private final List<AnimationState> states;
    private int currentStateIndex = 0;

    public Permutations2Animation() {
        this.algorithm = new BacktrackingPermutation2();
        this.states = new ArrayList<>();
        int[] nums = {1, 1, 2};
        algorithm.generatePermutations(nums, states);

        setLayout(new BorderLayout());
        initializeUI();
    }

    private void initializeUI() {
        JPanel controlPanel = new JPanel();
        JButton prevButton = new JButton("Previous");
        prevButton.addActionListener(e -> prevStep());
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> nextStep());

        controlPanel.add(prevButton);
        controlPanel.add(nextButton);

        this.add(controlPanel, BorderLayout.SOUTH);
    }

    public void nextStep() {
        if (currentStateIndex < states.size() - 1) {
            currentStateIndex++;
            repaint();
        }
    }

    public void prevStep() {
        if (currentStateIndex > 0) {
            currentStateIndex--;
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (states.isEmpty()) {
            return;
        }

        AnimationState state = states.get(currentStateIndex);
        drawArray(g, state);
        drawCurrentPermutation(g, state);
        drawAlgorithmDetails(g, state);
    }

    private void drawArray(Graphics g, AnimationState state) {
        g.drawString("Array:", PADDING, PADDING);
        for (int i = 0; i < state.array.length; i++) {
            g.setColor(getElementColor(state, i));
            g.fillRect(PADDING + i * ELEMENT_SIZE, PADDING + 10, ELEMENT_SIZE, ELEMENT_SIZE);
            g.setColor(Color.BLACK);
            g.drawRect(PADDING + i * ELEMENT_SIZE, PADDING + 10, ELEMENT_SIZE, ELEMENT_SIZE);
            g.drawString(String.valueOf(state.array[i]), PADDING + i * ELEMENT_SIZE + 15, PADDING + 35);
        }
    }

    private void drawCurrentPermutation(Graphics g, AnimationState state) {
        g.setColor(Color.BLACK);
        g.drawString("Current Permutation:", PADDING, PADDING + 80);
        for (int i = 0; i < state.currentPermutation.size(); i++) {
            g.drawRect(PADDING + i * ELEMENT_SIZE, PADDING + 90, ELEMENT_SIZE, ELEMENT_SIZE);
            g.drawString(String.valueOf(state.currentPermutation.get(i)), PADDING + i * ELEMENT_SIZE + 15, PADDING + 115);
        }
    }

    private void drawAlgorithmDetails(Graphics g, AnimationState state) {
        g.setColor(Color.BLACK);
        g.drawString("Action: " + getActionText(state), PADDING, PADDING + 150);
        g.drawString("Depth: " + state.depth, PADDING, PADDING + 170);
    }

    private Color getElementColor(AnimationState state, int index) {
        if (state.details != null && state.details.containsKey("currentIndex") && (int) state.details.get("currentIndex") == index) {
            return Color.YELLOW;
        }
        if (state.used[index]) {
            return Color.LIGHT_GRAY;
        }
        return Color.WHITE;
    }

    private String getActionText(AnimationState state) {
        String action = state.action;
        if (state.details != null) {
            switch (action) {
                case "check_condition":
                    return "Checking element at index " + state.details.get("currentIndex");
                case "select":
                    return "Selecting element " + state.array[(int) state.details.get("currentIndex")] + " at index " + state.details.get("currentIndex");
                case "backtrack":
                    return "Backtracking from element at index " + state.details.get("removedIndex");
                case "found_solution":
                    return "Found a unique permutation!";
                default:
                    return action;
            }
        }
        return action;
    }

}