package com.animation.lonch;

import com.animation.Animation;
import com.animation.util.MatrixNode;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.animation.util.MatrixNode.init;

public class NO6_MatrixLinkedListShortestPathAnimation extends JFrame {

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

    private MatrixNode<Integer> node;
    private MatrixNode<Integer> start;
    private MatrixNode<Integer> end;
    private List<List<MatrixNode<Integer>>> shortestPaths = new ArrayList<>();
    private Set<MatrixNode<Integer>> visitedNodes = new HashSet<>();
    private Set<MatrixNode<Integer>> pathNodes = new HashSet<>();
    private Timer timer;
    private java.util.Iterator<MatrixNode<Integer>> pathIterator;

    public NO6_MatrixLinkedListShortestPathAnimation() {
        setTitle("NO.6 MatrixLinkedListShortestPath Animation");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        node = init(5);
        start = node.pos(2);
        end = node.pos(5);
        findShortestPaths(end, start);

        DrawingPanel drawingPanel = new DrawingPanel();
        add(drawingPanel);

        if (!shortestPaths.isEmpty()) {
            pathIterator = shortestPaths.get(0).iterator();
            timer = new Timer(500, e -> {
                if (pathIterator.hasNext()) {
                    pathNodes.add(pathIterator.next());
                    drawingPanel.repaint();
                } else {
                    timer.stop();
                }
            });
            timer.start();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NO6_MatrixLinkedListShortestPathAnimation animation = new NO6_MatrixLinkedListShortestPathAnimation();
            animation.setVisible(true);
        });
    }

    private class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawMatrix(g, node, start, end, pathNodes);
        }
    }

    private void drawMatrix(Graphics g, MatrixNode<Integer> head, MatrixNode<Integer> start, MatrixNode<Integer> end, Set<MatrixNode<Integer>> pathNodes) {
        if (head == null) {
            return;
        }

        int x = 50;
        int y = 50;
        int nodeSize = 30;
        int padding = 20;

        MatrixNode<Integer> currRow = head;
        while (currRow != null) {
            MatrixNode<Integer> currCol = currRow;
            int currentX = x;
            while (currCol != null) {
                if (pathNodes.contains(currCol)) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.ORANGE);
                }
                if (currCol == start) {
                    g.setColor(Color.BLUE);
                } else if (currCol == end) {
                    g.setColor(Color.RED);
                }
                g.fillRect(currentX, y, nodeSize, nodeSize);
                g.setColor(Color.BLACK);
                g.drawRect(currentX, y, nodeSize, nodeSize);
                g.drawString(String.valueOf(currCol.val), currentX + 10, y + 20);

                if (currCol.right != null) {
                    g.drawLine(currentX + nodeSize, y + nodeSize / 2, currentX + nodeSize + padding, y + nodeSize / 2);
                }
                if (currCol.down != null) {
                    g.drawLine(currentX + nodeSize / 2, y + nodeSize, currentX + nodeSize / 2, y + nodeSize + padding);
                }

                currCol = currCol.right;
                currentX += nodeSize + padding;
            }
            currRow = currRow.down;
            y += nodeSize + padding;
        }
    }

    public List<List<MatrixNode<Integer>>> findShortestPaths(MatrixNode<Integer> start, MatrixNode<Integer> end) {
        dfs(shortestPaths, new ArrayList<>(), start, end, new HashSet<>());
        return shortestPaths;
    }

    private void dfs(List<List<MatrixNode<Integer>>> res, List<MatrixNode<Integer>> list,
                     MatrixNode<Integer> start, MatrixNode<Integer> end,
                     Set<MatrixNode<Integer>> visited) {
        if (visited.contains(start)) {
            return;
        }

        list.add(start);
        visited.add(start);

        if (start == end) {
            if (res.isEmpty() || list.size() < res.get(0).size()) {
                res.clear();
                res.add(new ArrayList<>(list));
            } else if (list.size() == res.get(0).size()) {
                res.add(new ArrayList<>(list));
            }
        } else {
            start.getNeighbors().forEach(
                    curr -> dfs(res, list, curr, end, visited)
            );
        }

        list.remove(start);
        visited.remove(start);
    }
}