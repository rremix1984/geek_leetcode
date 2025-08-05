package com.animation.hard;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class NO52_H_NQueensII_Animation extends JFrame {

    private final int n;
    private final int[][] board;
    private final BoardPanel boardPanel;
    private int solutions = 0;

    public NO52_H_NQueensII_Animation(int n) {
        this.n = n;
        this.board = new int[n][n];
        setTitle("N-Queens II Animation (n=" + n + ")");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        boardPanel = new BoardPanel();
        add(boardPanel, BorderLayout.CENTER);

        new Thread(this::solve).start();
    }

    private void solve() {
        backtrack(0, new HashSet<>(), new HashSet<>(), new HashSet<>());
        JOptionPane.showMessageDialog(this, "Found " + solutions + " solutions.");
    }

    private void backtrack(int row, Set<Integer> cols, Set<Integer> diag1, Set<Integer> diag2) {
        if (row == n) {
            solutions++;
            boardPanel.repaint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }

        for (int col = 0; col < n; col++) {
            if (cols.contains(col) || diag1.contains(row - col) || diag2.contains(row + col)) {
                continue;
            }

            cols.add(col);
            diag1.add(row - col);
            diag2.add(row + col);
            board[row][col] = 1;
            boardPanel.repaint();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            backtrack(row + 1, cols, diag1, diag2);

            cols.remove(col);
            diag1.remove(row - col);
            diag2.remove(row + col);
            board[row][col] = 0;
            boardPanel.repaint();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class BoardPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int cellSize = Math.min(getWidth(), getHeight()) / n;
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    if ((row + col) % 2 == 0) {
                        g.setColor(Color.WHITE);
                    } else {
                        g.setColor(Color.BLACK);
                    }
                    g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);

                    if (board[row][col] == 1) {
                        g.setColor(Color.RED);
                        g.fillOval(col * cellSize + cellSize / 4, row * cellSize + cellSize / 4, cellSize / 2, cellSize / 2);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NO52_H_NQueensII_Animation animation = new NO52_H_NQueensII_Animation(4);
            animation.setVisible(true);
        });
    }
}