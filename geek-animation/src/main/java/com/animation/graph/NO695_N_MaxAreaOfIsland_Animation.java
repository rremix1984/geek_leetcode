package com.animation.graph;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class NO695_N_MaxAreaOfIsland_Animation extends JFrame {
    private static final int CELL_SIZE = 40;
    private static final int ANIMATION_DELAY = 300;

    private int[][] grid = {
            {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
            {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}
    };

    private int rows = grid.length;
    private int cols = grid[0].length;
    private boolean[][] visited = new boolean[rows][cols];
    private int maxArea = 0;
    private int currentArea = 0;

    private JPanel gridPanel;
    private JLabel statusLabel;

    public NO695_N_MaxAreaOfIsland_Animation() {
        setTitle("NO.695 岛屿的最大面积动画");
        setSize(cols * CELL_SIZE + 50, rows * CELL_SIZE + 100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        startAnimation();
    }

    private void initComponents() {
        gridPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGrid(g);
            }
        };
        gridPanel.setPreferredSize(new Dimension(cols * CELL_SIZE, rows * CELL_SIZE));
        add(gridPanel, BorderLayout.CENTER);

        statusLabel = new JLabel("动画开始");
        add(statusLabel, BorderLayout.SOUTH);
    }

    private void drawGrid(Graphics g) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (visited[i][j]) {
                    g.setColor(Color.LIGHT_GRAY);
                } else if (grid[i][j] == 1) {
                    g.setColor(Color.ORANGE);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    private void startAnimation() {
        new Timer().schedule(new TimerTask() {
            int r = 0, c = 0;

            @Override
            public void run() {
                if (r >= rows) {
                    statusLabel.setText("搜索完成，最大岛屿面积为: " + maxArea);
                    this.cancel();
                    return;
                }

                if (grid[r][c] == 1 && !visited[r][c]) {
                    currentArea = 0;
                    dfs(r, c);
                    maxArea = Math.max(maxArea, currentArea);
                    statusLabel.setText("发现新岛屿，面积为: " + currentArea + ", 当前最大面积: " + maxArea);
                }

                c++;
                if (c >= cols) {
                    c = 0;
                    r++;
                }
                gridPanel.repaint();
            }
        }, 1000, ANIMATION_DELAY);
    }

    private void dfs(int r, int c) {
        if (r < 0 || r >= rows || c < 0 || c >= cols || visited[r][c] || grid[r][c] == 0) {
            return;
        }

        visited[r][c] = true;
        currentArea++;
        gridPanel.repaint();
        try {
            Thread.sleep(ANIMATION_DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dfs(r + 1, c);
        dfs(r - 1, c);
        dfs(r, c + 1);
        dfs(r, c - 1);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NO695_N_MaxAreaOfIsland_Animation().setVisible(true));
    }
}