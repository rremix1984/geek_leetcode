package com.animation.normal;

import com.animation.normal.lettercase.AnimationState;
import com.animation.normal.lettercase.BacktrackingLetterCase;
import com.animation.normal.lettercase.LetterCaseAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LetterCaseAnimation extends JPanel {

    private static final int ELEMENT_SIZE = 40;
    private static final int PADDING = 20;

    private final LetterCaseAlgorithm algorithm;
    private final List<AnimationState> states;
    private int currentStateIndex = 0;

    public LetterCaseAnimation() {
        this.algorithm = new BacktrackingLetterCase();
        this.states = new ArrayList<>();
        String s = "a1b2";
        algorithm.generatePermutations(s, states);

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
        drawAlgorithmDetails(g, state);
    }

    private void drawArray(Graphics g, AnimationState state) {
        g.drawString("Current String:", PADDING, PADDING);
        for (int i = 0; i < state.chars.length; i++) {
            g.setColor(i == state.index ? Color.YELLOW : Color.WHITE);
            g.fillRect(PADDING + i * ELEMENT_SIZE, PADDING + 10, ELEMENT_SIZE, ELEMENT_SIZE);
            g.setColor(Color.BLACK);
            g.drawRect(PADDING + i * ELEMENT_SIZE, PADDING + 10, ELEMENT_SIZE, ELEMENT_SIZE);
            g.drawString(String.valueOf(state.chars[i]), PADDING + i * ELEMENT_SIZE + 15, PADDING + 35);
        }
    }

    private void drawAlgorithmDetails(Graphics g, AnimationState state) {
        g.setColor(Color.BLACK);
        g.drawString("Action: " + state.action, PADDING, PADDING + 80);
        g.drawString("Index: " + state.index, PADDING, PADDING + 100);
    }


}