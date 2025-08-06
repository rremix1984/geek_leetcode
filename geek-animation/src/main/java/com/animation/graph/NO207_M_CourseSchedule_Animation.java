package com.animation.graph;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class NO207_M_CourseSchedule_Animation extends JFrame {
    private static final int NODE_SIZE = 40;
    private static final int ANIMATION_DELAY = 1000;

    private int numCourses = 4;
    private int[][] prerequisites = {{1, 0}, {2, 0}, {3, 1}, {3, 2}};

    private List<List<Integer>> adj = new ArrayList<>();
    private int[] visited; // 0: unvisited, 1: visiting, 2: visited
    private boolean hasCycle = false;
    private List<Integer> topologicalOrder = new ArrayList<>();

    private JPanel graphPanel;
    private JLabel statusLabel;

    public NO207_M_CourseSchedule_Animation() {
        setTitle("NO.207 课程表动画");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        startAnimation();
    }

    private void initComponents() {
        graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGraph(g);
            }
        };
        add(graphPanel, BorderLayout.CENTER);

        statusLabel = new JLabel("动画开始");
        add(statusLabel, BorderLayout.SOUTH);
    }

    private void drawGraph(Graphics g) {
        for (int i = 0; i < numCourses; i++) {
            Point p = getNodePosition(i);
            if (visited[i] == 1) {
                g.setColor(Color.GRAY);
            } else if (visited[i] == 2) {
                g.setColor(Color.BLACK);
            } else {
                g.setColor(Color.WHITE);
            }
            g.fillOval(p.x, p.y, NODE_SIZE, NODE_SIZE);
            g.setColor(Color.BLACK);
            g.drawOval(p.x, p.y, NODE_SIZE, NODE_SIZE);
            g.drawString(String.valueOf(i), p.x + NODE_SIZE / 2 - 5, p.y + NODE_SIZE / 2 + 5);
        }

        g.setColor(Color.BLUE);
        for (int i = 0; i < numCourses; i++) {
            for (int neighbor : adj.get(i)) {
                Point p1 = getNodePosition(i);
                Point p2 = getNodePosition(neighbor);
                g.drawLine(p1.x + NODE_SIZE / 2, p1.y + NODE_SIZE / 2, p2.x + NODE_SIZE / 2, p2.y + NODE_SIZE / 2);
            }
        }
    }

    private Point getNodePosition(int course) {
        int x = 50 + (course % 2) * 200;
        int y = 50 + (course / 2) * 100;
        return new Point(x, y);
    }

    private void startAnimation() {
        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] p : prerequisites) {
            adj.get(p[1]).add(p[0]);
        }
        visited = new int[numCourses];

        new Timer().schedule(new TimerTask() {
            int currentCourse = 0;

            @Override
            public void run() {
                if (currentCourse >= numCourses) {
                    if (hasCycle) {
                        statusLabel.setText("检测到环，无法完成所有课程。");
                    } else {
                        statusLabel.setText("拓扑排序结果: " + topologicalOrder);
                    }
                    this.cancel();
                    return;
                }

                if (visited[currentCourse] == 0) {
                    dfs(currentCourse);
                }
                currentCourse++;
            }
        }, 1000, ANIMATION_DELAY);
    }

    private void dfs(int course) {
        if (hasCycle) return;

        visited[course] = 1; // Mark as visiting
        statusLabel.setText("正在访问课程: " + course);
        SwingUtilities.invokeLater(() -> graphPanel.repaint());
        try {
            Thread.sleep(ANIMATION_DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int neighbor : adj.get(course)) {
            if (visited[neighbor] == 0) {
                dfs(neighbor);
            } else if (visited[neighbor] == 1) {
                hasCycle = true;
                statusLabel.setText("检测到环: " + course + " -> " + neighbor);
                SwingUtilities.invokeLater(() -> graphPanel.repaint());
                return;
            }
        }

        visited[course] = 2; // Mark as visited
        topologicalOrder.add(0, course);
        statusLabel.setText("完成课程: " + course);
        SwingUtilities.invokeLater(() -> graphPanel.repaint());
        try {
            Thread.sleep(ANIMATION_DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NO207_M_CourseSchedule_Animation().setVisible(true));
    }
}