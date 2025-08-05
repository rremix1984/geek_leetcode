package com.animation.tree;

import com.animation.Animation;
import com.animation.launcher.AlgorithmTreeLauncher;
import com.leetcode.util.TreeNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.text.DefaultHighlighter;

public class NO199_N_BinaryTreeRightSideView_Animation extends JFrame implements Animation {

    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final int CONTROL_PANEL_HEIGHT = 150;
    private static final int NODE_SIZE = 35;

    private DrawingPanel drawingPanel;
    private JTextArea codeArea;
    private JLabel statusLabel;

    private JButton startButton, pauseButton, resumeButton, resetButton, backButton;
    private JSlider speedSlider;

    private Timer animationTimer;
    private Queue<TreeNode<Integer>> queue = new LinkedList<>();
    private List<Integer> rightSideView = new ArrayList<>();
    private List<TreeNode<Integer>> highlightedNodes = new ArrayList<>();
    private TreeNode<Integer> currentNode;
    private int levelSize = 0;
    private int processedInLevel = 0;

    private volatile boolean isPaused = false;

    public NO199_N_BinaryTreeRightSideView_Animation() {
        setTitle("NO.199 二叉树的右视图 - 动画演示");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        // Drawing panel
        drawingPanel = new DrawingPanel(constructTree());
        mainPanel.add(drawingPanel, BorderLayout.CENTER);

        // Control panel
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, CONTROL_PANEL_HEIGHT));
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        // Code and status panel
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(400, WINDOW_HEIGHT));
        mainPanel.add(rightPanel, BorderLayout.EAST);

        codeArea = new JTextArea();
        codeArea.setEditable(false);
        codeArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        codeArea.setText(getAlgorithmCode());
        JScrollPane codeScrollPane = new JScrollPane(codeArea);
        rightPanel.add(codeScrollPane, BorderLayout.CENTER);

        statusLabel = new JLabel("准备开始", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Serif", Font.BOLD, 16));
        rightPanel.add(statusLabel, BorderLayout.SOUTH);

        // Buttons
        JPanel buttonPanel = new JPanel();
        startButton = new JButton("开始");
        pauseButton = new JButton("暂停");
        resumeButton = new JButton("继续");
        resetButton = new JButton("重置");
        backButton = new JButton("返回");

        buttonPanel.add(startButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(resumeButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(backButton);
        controlPanel.add(buttonPanel, BorderLayout.CENTER);

        // Speed slider
        speedSlider = new JSlider(0, 1000, 500);
        speedSlider.setMajorTickSpacing(200);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.setBorder(BorderFactory.createTitledBorder("动画速度"));
        controlPanel.add(speedSlider, BorderLayout.SOUTH);

        setupActionListeners();
        resetAnimationState();
    }

    private void setupActionListeners() {
        startButton.addActionListener(e -> start());
        pauseButton.addActionListener(e -> isPaused = true);
        resumeButton.addActionListener(e -> isPaused = false);
        resetButton.addActionListener(e -> resetAnimationState());
        backButton.addActionListener(e -> {
            dispose();
            AlgorithmTreeLauncher.getInstance().setVisible(true);
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                AlgorithmTreeLauncher.getInstance().setVisible(true);
            }
        });
    }

    private void resetAnimationState() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        isPaused = false;
        queue.clear();
        rightSideView.clear();
        highlightedNodes.clear();
        currentNode = null;
        levelSize = 0;
        processedInLevel = 0;
        drawingPanel.setRightSideView(rightSideView);
        drawingPanel.setHighlightedNodes(highlightedNodes, null);
        statusLabel.setText("准备开始");
        highlightCodeLine(-1);
        drawingPanel.repaint();
    }

    private TreeNode<Integer> constructTree() {
        TreeNode<Integer> root = new TreeNode<>(1);
        root.left = new TreeNode<>(2);
        root.right = new TreeNode<>(3);
        root.left.right = new TreeNode<>(5);
        root.right.right = new TreeNode<>(4);
        return root;
    }

    @Override
    public void start() {
        resetAnimationState();
        queue.offer(drawingPanel.root);
        animationTimer = new Timer(speedSlider.getValue(), e -> animationStep());
        animationTimer.start();
    }

    private void animationStep() {
        if (isPaused) return;

        animationTimer.setDelay(1000 - speedSlider.getValue());

        if (levelSize == 0) {
            if (queue.isEmpty()) {
                animationTimer.stop();
                statusLabel.setText("动画完成!");
                highlightCodeLine(-1);
                drawingPanel.setHighlightedNodes(new ArrayList<>(), null);
                drawingPanel.repaint();
                return;
            }
            levelSize = queue.size();
            processedInLevel = 0;
            highlightedNodes.clear();
            statusLabel.setText("开始遍历新的一层");
            highlightCodeLine(5);
        } else {
            currentNode = queue.poll();
            highlightedNodes.add(currentNode);
            statusLabel.setText("处理节点: " + currentNode.val);
            highlightCodeLine(9);

            if (currentNode.left != null) {
                queue.offer(currentNode.left);
            }
            if (currentNode.right != null) {
                queue.offer(currentNode.right);
            }

            processedInLevel++;

            if (processedInLevel == levelSize) {
                rightSideView.add(currentNode.val);
                statusLabel.setText("找到本层最右节点: " + currentNode.val);
                highlightCodeLine(11);
                levelSize = 0; // Reset for next level
            }
            drawingPanel.setHighlightedNodes(highlightedNodes, currentNode);
            drawingPanel.repaint();
        }
    }

    private void highlightCodeLine(int line) {
        try {
            codeArea.getHighlighter().removeAllHighlights();
            if (line >= 0) {
                int startIndex = codeArea.getLineStartOffset(line);
                int endIndex = codeArea.getLineEndOffset(line);
                codeArea.getHighlighter().addHighlight(startIndex, endIndex, new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW));
            }
        } catch (Exception e) {
            // ignore
        }
    }

    private String getAlgorithmCode() {
        return "public List<Integer> rightSideView(TreeNode root) {\n" +
               "    if (root == null) return new ArrayList<>();\n" +
               "    Queue<TreeNode> queue = new LinkedList<>();\n" +
               "    queue.offer(root);\n" +
               "    List<Integer> res = new ArrayList<>();\n" +
               "    while (!queue.isEmpty()) {\n" +
               "        int levelSize = queue.size();\n" +
               "        for (int i = 0; i < levelSize; i++) {\n" +
               "            TreeNode node = queue.poll();\n" +
               "            if (i == levelSize - 1) {\n" +
               "                res.add(node.val);\n" +
               "            }\n" +
               "            if (node.left != null) queue.offer(node.left);\n" +
               "            if (node.right != null) queue.offer(node.right);\n" +
               "        }\n" +
               "    }\n" +
               "    return res;\n" +
               "}";
    }

    private static class DrawingPanel extends JPanel {
        private TreeNode<Integer> root;
        private List<Integer> rightSideView = new ArrayList<>();
        private List<TreeNode<Integer>> highlightedNodes = new ArrayList<>();
        private TreeNode<Integer> currentNode;

        public DrawingPanel(TreeNode<Integer> root) {
            this.root = root;
            setBackground(Color.WHITE);
        }

        public void setRightSideView(List<Integer> rightSideView) {
            this.rightSideView = new ArrayList<>(rightSideView);
        }

        public void setHighlightedNodes(List<TreeNode<Integer>> highlightedNodes, TreeNode<Integer> currentNode) {
            this.highlightedNodes = new ArrayList<>(highlightedNodes);
            this.currentNode = currentNode;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            drawTree(g2d, root, getWidth() / 2, 50, getWidth() / 4);
            drawRightSideView(g2d);
        }

        private void drawTree(Graphics2D g, TreeNode<Integer> node, int x, int y, int xOffset) {
            if (node == null) return;

            if (node.left != null) {
                g.setColor(Color.GRAY);
                g.drawLine(x, y, x - xOffset, y + 80);
                drawTree(g, node.left, x - xOffset, y + 80, xOffset / 2);
            }

            if (node.right != null) {
                g.setColor(Color.GRAY);
                g.drawLine(x, y, x + xOffset, y + 80);
                drawTree(g, node.right, x + xOffset, y + 80, xOffset / 2);
            }

            if (currentNode == node) {
                g.setColor(Color.ORANGE);
            } else if (highlightedNodes.contains(node)) {
                g.setColor(Color.CYAN);
            } else {
                g.setColor(Color.LIGHT_GRAY);
            }
            g.fillOval(x - NODE_SIZE / 2, y - NODE_SIZE / 2, NODE_SIZE, NODE_SIZE);
            g.setColor(Color.BLACK);
            g.drawOval(x - NODE_SIZE / 2, y - NODE_SIZE / 2, NODE_SIZE, NODE_SIZE);
            g.drawString(String.valueOf(node.val), x - 5, y + 5);
        }

        private void drawRightSideView(Graphics2D g) {
            g.setColor(Color.BLUE);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("Right Side View:", 20, 40);
            int y = 60;
            for (Integer val : rightSideView) {
                g.drawString(String.valueOf(val), 20, y);
                y += 20;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NO199_N_BinaryTreeRightSideView_Animation animation = new NO199_N_BinaryTreeRightSideView_Animation();
            animation.setVisible(true);
        });
    }
}