package com.animation.lonch;

import com.animation.Animation;
import com.animation.util.MatrixNode;

import javax.swing.*;
import java.awt.*;

import static com.animation.util.MatrixNode.initC;

public class NO5_MatrixLinkedListAnimation extends JFrame {

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

    private MatrixNode<Character> head;
    private Timer timer;
    private java.util.List<MatrixNode<Character>> drawnNodes = new java.util.ArrayList<>();
    private JTextArea descriptionArea;
    private JButton startButton, backButton;
    private DrawingPanel drawingPanel;
    private MatrixNode<Character> currentRow;
    private MatrixNode<Character> currentCol;

    public NO5_MatrixLinkedListAnimation() {
        setTitle("NO.5 MatrixLinkedList Animation");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        head = initC(6);
        currentRow = head;

        drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);

        descriptionArea = new JTextArea();
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setPreferredSize(new Dimension(WINDOW_WIDTH, 100));
        add(scrollPane, BorderLayout.SOUTH);

        JPanel controlPanel = new JPanel();
        startButton = new JButton("开始动画");
        backButton = new JButton("返回首页");
        controlPanel.add(startButton);
        controlPanel.add(backButton);
        add(controlPanel, BorderLayout.NORTH);

        startButton.addActionListener(e -> {
            if (timer != null && timer.isRunning()) {
                timer.stop();
            }
            drawnNodes.clear();
            currentRow = head;
            currentCol = head;
            descriptionArea.setText("动画开始，正在初始化矩阵...\n");
            timer.start();
        });

        backButton.addActionListener(e -> dispose());

        timer = new Timer(500, e -> {
            if (currentCol != null) {
                if (!drawnNodes.contains(currentCol)) {
                    drawnNodes.add(currentCol);
                    descriptionArea.append(String.format("正在处理 [行: %s, 列: %s], 值为: %s\n", getRowIndex(currentCol), getColIndex(currentCol), currentCol.val));
                    drawingPanel.repaint();
                }
                currentCol = currentCol.right;
                if (currentCol == null) {
                    currentRow = currentRow.down;
                    currentCol = currentRow;
                }
            } else {
                descriptionArea.append("矩阵绘制完成!\n");
                timer.stop();
            }
        });
    }

    private String getRowAsString(MatrixNode<Character> rowHead) {
        StringBuilder sb = new StringBuilder();
        MatrixNode<Character> temp = rowHead;
        while (temp != null) {
            sb.append(temp.val).append(" ");
            temp = temp.right;
        }
        return sb.toString();
    }

    private int getRowIndex(MatrixNode<Character> node) {
        int index = 0;
        MatrixNode<Character> temp = head;
        while (temp != null && temp != node) {
            if (isSameRow(temp, node)) {
                return index;
            }
            temp = temp.down;
            index++;
        }
        return index;
    }

    private int getColIndex(MatrixNode<Character> node) {
        int index = 0;
        MatrixNode<Character> temp = getRowHead(node);
        while (temp != null && temp != node) {
            temp = temp.right;
            index++;
        }
        return index;
    }

    private boolean isSameRow(MatrixNode<Character> rowHead, MatrixNode<Character> node) {
        MatrixNode<Character> temp = rowHead;
        while (temp != null) {
            if (temp == node) {
                return true;
            }
            temp = temp.right;
        }
        return false;
    }

    private MatrixNode<Character> getRowHead(MatrixNode<Character> node) {
        MatrixNode<Character> temp = node;
        while (temp.left != null) {
            temp = temp.left;
        }
        return temp;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NO5_MatrixLinkedListAnimation animation = new NO5_MatrixLinkedListAnimation();
            animation.setVisible(true);
        });
    }

    private class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (head != null) {
                drawMatrix(g);
            }
        }
    }

    private void drawMatrix(Graphics g) {
        int nodeSize = 30;
        int padding = 20;
        java.util.Map<MatrixNode<Character>, Point> nodePositions = new java.util.HashMap<>();

        // First pass: calculate positions
        MatrixNode<Character> tempRow = head;
        int y = 50;
        while (tempRow != null) {
            MatrixNode<Character> tempCol = tempRow;
            int x = 50;
            while (tempCol != null) {
                nodePositions.put(tempCol, new Point(x, y));
                tempCol = tempCol.right;
                x += nodeSize + padding;
            }
            tempRow = tempRow.down;
            y += nodeSize + padding;
        }

        // Second pass: draw nodes and connections
        for (MatrixNode<Character> node : drawnNodes) {
            Point pos = nodePositions.get(node);
            if (pos != null) {
                g.setColor(Color.ORANGE);
                g.fillRect(pos.x, pos.y, nodeSize, nodeSize);
                g.setColor(Color.BLACK);
                g.drawRect(pos.x, pos.y, nodeSize, nodeSize);
                g.drawString(String.valueOf(node.val), pos.x + 10, pos.y + 20);

                if (node.right != null && drawnNodes.contains(node.right)) {
                    Point rightPos = nodePositions.get(node.right);
                    g.drawLine(pos.x + nodeSize, pos.y + nodeSize / 2, rightPos.x, rightPos.y + nodeSize / 2);
                }
                if (node.down != null && drawnNodes.contains(node.down)) {
                    Point downPos = nodePositions.get(node.down);
                    g.drawLine(pos.x + nodeSize / 2, pos.y + nodeSize, downPos.x + nodeSize / 2, downPos.y);
                }
            }
        }
    }
}