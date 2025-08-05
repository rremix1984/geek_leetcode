package com.animation.hard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 51. N-Queens 动画演示
 */
public class NO51_H_NQueens_Animation extends JFrame {

    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 800;
    private static final int BOARD_SIZE = 600;
    private static final int CELL_SIZE = BOARD_SIZE / 8;

    private int n = 8;
    private int[][] board;
    private List<int[][]> solutions = new ArrayList<>();
    private Timer timer;
    private boolean isSolving = false;

    private JPanel boardPanel;
    private JButton startButton, stopButton, nextButton, prevButton;
    private JSpinner nSpinner;
    private JLabel statusLabel;
    private int currentSolutionIndex = -1;

    public NO51_H_NQueens_Animation() {
        initUI();
    }

    private void initUI() {
        setTitle("N-Queens Animation");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBoard(g);
            }
        };
        boardPanel.setPreferredSize(new Dimension(BOARD_SIZE, BOARD_SIZE));

        JPanel controlPanel = new JPanel();
        nSpinner = new JSpinner(new SpinnerNumberModel(8, 4, 12, 1));
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        nextButton = new JButton("Next Solution");
        prevButton = new JButton("Prev Solution");
        statusLabel = new JLabel("Set N and press Start");

        controlPanel.add(new JLabel("N:"));
        controlPanel.add(nSpinner);
        controlPanel.add(startButton);
        controlPanel.add(stopButton);
        controlPanel.add(prevButton);
        controlPanel.add(nextButton);
        controlPanel.add(statusLabel);

        add(boardPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        startButton.addActionListener(e -> startSolving());
        stopButton.addActionListener(e -> stopSolving());
        nextButton.addActionListener(e -> showNextSolution());
        prevButton.addActionListener(e -> showPrevSolution());

        stopButton.setEnabled(false);
        nextButton.setEnabled(false);
        prevButton.setEnabled(false);
    }

    private void drawBoard(Graphics g) {
        int cellSize = BOARD_SIZE / n;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if ((row + col) % 2 == 0) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                }
                g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);

                if (board != null && board[row][col] == 1) {
                    g.setColor(Color.RED);
                    g.fillOval(col * cellSize + 5, row * cellSize + 5, cellSize - 10, cellSize - 10);
                }
            }
        }
    }

    private void startSolving() {
        n = (int) nSpinner.getValue();
        board = new int[n][n];
        solutions.clear();
        currentSolutionIndex = -1;
        isSolving = true;

        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        nSpinner.setEnabled(false);
        nextButton.setEnabled(false);
        prevButton.setEnabled(false);

        statusLabel.setText("Solving...");

        new Thread(() -> {
            solve(0);
            SwingUtilities.invokeLater(() -> {
                isSolving = false;
                stopButton.setEnabled(false);
                startButton.setEnabled(true);
                nSpinner.setEnabled(true);
                if (!solutions.isEmpty()) {
                    currentSolutionIndex = 0;
                    showSolution(currentSolutionIndex);
                    nextButton.setEnabled(solutions.size() > 1);
                } else {
                    statusLabel.setText("No solutions found.");
                }
            });
        }).start();
    }

    private void stopSolving() {
        isSolving = false;
        // The thread will stop naturally after finishing the current search path.
        // For a more immediate stop, a volatile flag should be checked within the solve method.
        statusLabel.setText("Solver stopped.");
    }

    private void solve(int row) {
        if (!isSolving) return;

        if (row == n) {
            int[][] solution = new int[n][n];
            for (int i = 0; i < n; i++) {
                solution[i] = board[i].clone();
            }
            solutions.add(solution);
            return;
        }

        for (int col = 0; col < n; col++) {
            if (isSafe(row, col)) {
                board[row][col] = 1;
                updateBoard();
                try {
                    Thread.sleep(50); // Animation delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                solve(row + 1);

                board[row][col] = 0;
                updateBoard();
                 try {
                    Thread.sleep(25); // Backtracking delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private boolean isSafe(int row, int col) {
        // Check column
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 1) return false;
        }
        // Check upper-left diagonal
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) return false;
        }
        // Check upper-right diagonal
        for (int i = row, j = col; i >= 0 && j < n; i--, j++) {
            if (board[i][j] == 1) return false;
        }
        return true;
    }

    private void updateBoard() {
        SwingUtilities.invokeLater(() -> boardPanel.repaint());
    }

    private void showSolution(int index) {
        if (index >= 0 && index < solutions.size()) {
            board = solutions.get(index);
            statusLabel.setText("Solution " + (index + 1) + " of " + solutions.size());
            updateBoard();
        }
    }

    private void showNextSolution() {
        if (currentSolutionIndex < solutions.size() - 1) {
            currentSolutionIndex++;
            showSolution(currentSolutionIndex);
            prevButton.setEnabled(true);
            nextButton.setEnabled(currentSolutionIndex < solutions.size() - 1);
        }
    }

    private void showPrevSolution() {
        if (currentSolutionIndex > 0) {
            currentSolutionIndex--;
            showSolution(currentSolutionIndex);
            nextButton.setEnabled(true);
            prevButton.setEnabled(currentSolutionIndex > 0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO51_H_NQueens_Animation().setVisible(true);
        });
    }
}