package com.animation.graph;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

public class NO994_N_RottingOranges_Animation extends JFrame {
    private static final int CELL_SIZE = 50;
    private static final int ANIMATION_DELAY = 1000;

    private int[][] grid = {
            {2, 1, 1},
            {1, 1, 0},
            {0, 1, 1}
    };

    private int rows = grid.length;
    private int cols = grid[0].length;
    private int freshOranges = 0;
    private int minutes = 0;

    private JPanel gridPanel;
    private JLabel statusLabel;

    public NO994_N_RottingOranges_Animation() {
        setTitle("NO.994 腐烂的橘子动画");
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
                if (grid[i][j] == 2) {
                    g.setColor(Color.RED);
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
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 2) {
                    queue.offer(new int[]{i, j});
                } else if (grid[i][j] == 1) {
                    freshOranges++;
                }
            }
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (freshOranges == 0) {
                    statusLabel.setText("所有橘子都腐烂了，总共用时: " + minutes + " 分钟。");
                    this.cancel();
                    return;
                }

                int size = queue.size();
                if (size == 0) {
                    statusLabel.setText("无法腐烂所有橘子。");
                    this.cancel();
                    return;
                }

                minutes++;
                statusLabel.setText("第 " + minutes + " 分钟...");

                for (int i = 0; i < size; i++) {
                    int[] point = queue.poll();
                    int r = point[0];
                    int c = point[1];

                    int[] dr = {-1, 1, 0, 0};
                    int[] dc = {0, 0, -1, 1};

                    for (int k = 0; k < 4; k++) {
                        int nr = r + dr[k];
                        int nc = c + dc[k];

                        if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc] == 1) {
                            grid[nr][nc] = 2;
                            freshOranges--;
                            queue.offer(new int[]{nr, nc});
                        }
                    }
                }
                SwingUtilities.invokeLater(() -> gridPanel.repaint());
            }
        }, 1000, ANIMATION_DELAY);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NO994_N_RottingOranges_Animation().setVisible(true));
    }
}