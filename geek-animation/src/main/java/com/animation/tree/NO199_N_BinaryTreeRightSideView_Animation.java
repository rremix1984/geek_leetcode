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

    private JButton startButton, pauseButton, resumeButton, resetButton, backButton, homeButton;
    private JSlider speedSlider;
    private JTextField treeInputField;
    private JButton buildTreeButton;

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

        // Tree input panel
        JPanel treeInputPanel = new JPanel(new FlowLayout());
        treeInputPanel.add(new JLabel("二叉树输入(层序遍历):"));
        treeInputField = new JTextField("1,2,3,null,5,null,4", 20);
        buildTreeButton = new JButton("构建树");
        treeInputPanel.add(treeInputField);
        treeInputPanel.add(buildTreeButton);
        controlPanel.add(treeInputPanel, BorderLayout.NORTH);

        // Buttons
        JPanel buttonPanel = new JPanel();
        startButton = new JButton("开始");
        pauseButton = new JButton("暂停");
        resumeButton = new JButton("继续");
        resetButton = new JButton("重置");
        backButton = new JButton("返回");
        homeButton = new JButton("返回首页");

        buttonPanel.add(startButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(resumeButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(backButton);
        buttonPanel.add(homeButton);
        controlPanel.add(buttonPanel, BorderLayout.CENTER);

        // Speed slider - simplified
        JPanel speedPanel = new JPanel(new FlowLayout());
        speedPanel.add(new JLabel("速度:"));
        speedSlider = new JSlider(1, 10, 5);
        speedSlider.setMajorTickSpacing(3);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedPanel.add(speedSlider);
        controlPanel.add(speedPanel, BorderLayout.SOUTH);

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
        homeButton.addActionListener(e -> {
            dispose();
            AlgorithmTreeLauncher.getInstance().setVisible(true);
        });
        buildTreeButton.addActionListener(e -> buildTreeFromInput());

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
        SwingUtilities.invokeLater(() -> drawingPanel.repaint());
    }

    private TreeNode<Integer> constructTree() {
        TreeNode<Integer> root = new TreeNode<>(1);
        root.left = new TreeNode<>(2);
        root.right = new TreeNode<>(3);
        root.left.right = new TreeNode<>(5);
        root.right.right = new TreeNode<>(4);
        return root;
    }
    
    private void buildTreeFromInput() {
        try {
            String input = treeInputField.getText().trim();
            if (input.isEmpty()) {
                statusLabel.setText("请输入二叉树数据");
                return;
            }
            
            String[] values = input.split(",");
            TreeNode<Integer> newRoot = buildTreeFromArray(values);
            drawingPanel.root = newRoot;
            resetAnimationState();
            statusLabel.setText("树构建成功，可以开始动画");
        } catch (Exception e) {
            statusLabel.setText("输入格式错误，请检查");
        }
    }
    
    private TreeNode<Integer> buildTreeFromArray(String[] values) {
        if (values.length == 0 || values[0].trim().equals("null")) {
            return null;
        }
        
        TreeNode<Integer> root = new TreeNode<>(Integer.parseInt(values[0].trim()));
        Queue<TreeNode<Integer>> queue = new LinkedList<>();
        queue.offer(root);
        
        int i = 1;
        while (!queue.isEmpty() && i < values.length) {
            TreeNode<Integer> current = queue.poll();
            
            // Left child
            if (i < values.length && !values[i].trim().equals("null")) {
                current.left = new TreeNode<>(Integer.parseInt(values[i].trim()));
                queue.offer(current.left);
            }
            i++;
            
            // Right child
            if (i < values.length && !values[i].trim().equals("null")) {
                current.right = new TreeNode<>(Integer.parseInt(values[i].trim()));
                queue.offer(current.right);
            }
            i++;
        }
        
        return root;
    }

    @Override
    public void start() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        resetAnimationState();
        queue.offer(drawingPanel.root);
        int delay = (11 - speedSlider.getValue()) * 100; // 1000ms to 100ms
        animationTimer = new Timer(delay, e -> animationStep());
        animationTimer.start();
        statusLabel.setText("动画运行中...");
    }

    private void animationStep() {
        if (isPaused) return;

        animationTimer.setDelay((11 - speedSlider.getValue()) * 100);

        if (levelSize == 0) {
            if (queue.isEmpty()) {
                animationTimer.stop();
                statusLabel.setText("动画完成!");
                highlightCodeLine(-1);
                drawingPanel.setHighlightedNodes(new ArrayList<>(), null);
                SwingUtilities.invokeLater(() -> drawingPanel.repaint());
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
                // 更新右视图显示
                drawingPanel.setRightSideView(rightSideView);
            }
            drawingPanel.setHighlightedNodes(highlightedNodes, currentNode);
            SwingUtilities.invokeLater(() -> drawingPanel.repaint());
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
            
            // 设置高质量渲染
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            
            // 绘制渐变背景
            GradientPaint backgroundGradient = new GradientPaint(
                0, 0, new Color(240, 248, 255),
                getWidth(), getHeight(), new Color(230, 240, 250)
            );
            g2d.setPaint(backgroundGradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            
            // 绘制网格背景（可选）
            drawGridBackground(g2d);
            
            // 绘制二叉树
            drawTree(g2d, root, getWidth() / 2, 50, getWidth() / 4);
            
            // 绘制右视图
            drawRightSideView(g2d);
            
            // 绘制标题
            drawTitle(g2d);
        }
        
        private void drawGridBackground(Graphics2D g) {
            g.setColor(new Color(200, 220, 240, 50));
            g.setStroke(new BasicStroke(0.5f));
            
            // 绘制垂直线
            for (int x = 0; x < getWidth(); x += 40) {
                g.drawLine(x, 0, x, getHeight());
            }
            
            // 绘制水平线
            for (int y = 0; y < getHeight(); y += 40) {
                g.drawLine(0, y, getWidth(), y);
            }
        }
        
        private void drawTitle(Graphics2D g) {
            g.setColor(new Color(0, 123, 255));
            g.setFont(new Font("Arial", Font.BOLD, 24));
            FontMetrics fm = g.getFontMetrics();
            String title = "二叉树右视图算法演示";
            int titleWidth = fm.stringWidth(title);
            
            // 绘制标题阴影
            g.setColor(new Color(0, 0, 0, 50));
            g.drawString(title, (getWidth() - titleWidth) / 2 + 2, 27);
            
            // 绘制标题
            g.setColor(new Color(0, 123, 255));
            g.drawString(title, (getWidth() - titleWidth) / 2, 25);
        }

        private void drawTree(Graphics2D g, TreeNode<Integer> node, int x, int y, int xOffset) {
            if (node == null) return;

            // 绘制连接线，使用更美观的颜色和粗细
            g.setStroke(new BasicStroke(2.5f));
            if (node.left != null) {
                g.setColor(new Color(100, 100, 100, 180));
                g.drawLine(x, y, x - xOffset, y + 80);
                drawTree(g, node.left, x - xOffset, y + 80, xOffset / 2);
            }

            if (node.right != null) {
                g.setColor(new Color(100, 100, 100, 180));
                g.drawLine(x, y, x + xOffset, y + 80);
                drawTree(g, node.right, x + xOffset, y + 80, xOffset / 2);
            }

            // 绘制节点阴影效果
            g.setColor(new Color(0, 0, 0, 50));
            g.fillOval(x - NODE_SIZE / 2 + 3, y - NODE_SIZE / 2 + 3, NODE_SIZE, NODE_SIZE);

            // 根据节点状态设置不同的颜色和效果
            Color nodeColor;
            Color borderColor;
            if (currentNode == node) {
                // 当前处理节点：红色渐变
                nodeColor = new Color(255, 87, 87);
                borderColor = new Color(255, 0, 0);
                // 添加脉动效果
                g.setStroke(new BasicStroke(3.0f));
            } else if (highlightedNodes.contains(node)) {
                // 已访问节点：蓝色渐变
                nodeColor = new Color(64, 169, 255);
                borderColor = new Color(0, 123, 255);
                g.setStroke(new BasicStroke(2.5f));
            } else if (rightSideView.contains(node.val)) {
                // 右视图节点：绿色渐变
                nodeColor = new Color(40, 167, 69);
                borderColor = new Color(25, 135, 84);
                g.setStroke(new BasicStroke(2.5f));
            } else {
                // 普通节点：灰色渐变
                nodeColor = new Color(248, 249, 250);
                borderColor = new Color(173, 181, 189);
                g.setStroke(new BasicStroke(2.0f));
            }

            // 绘制渐变填充
            GradientPaint gradient = new GradientPaint(
                x - NODE_SIZE / 2, y - NODE_SIZE / 2, nodeColor.brighter(),
                x + NODE_SIZE / 2, y + NODE_SIZE / 2, nodeColor.darker()
            );
            g.setPaint(gradient);
            g.fillOval(x - NODE_SIZE / 2, y - NODE_SIZE / 2, NODE_SIZE, NODE_SIZE);
            
            // 绘制边框
            g.setColor(borderColor);
            g.drawOval(x - NODE_SIZE / 2, y - NODE_SIZE / 2, NODE_SIZE, NODE_SIZE);
            
            // 绘制节点值
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            FontMetrics fm = g.getFontMetrics();
            String text = String.valueOf(node.val);
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getAscent();
            g.drawString(text, x - textWidth / 2, y + textHeight / 2 - 2);
        }

        private void drawRightSideView(Graphics2D g) {
            // 绘制右视图面板背景
            g.setColor(new Color(248, 249, 250, 200));
            g.fillRoundRect(10, 10, 180, Math.max(100, rightSideView.size() * 50 + 80), 15, 15);
            
            // 绘制面板边框
            g.setStroke(new BasicStroke(2.0f));
            g.setColor(new Color(0, 123, 255));
            g.drawRoundRect(10, 10, 180, Math.max(100, rightSideView.size() * 50 + 80), 15, 15);
            
            // 绘制标题
            g.setColor(new Color(0, 123, 255));
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString("右视图结果", 25, 35);
            
            // 绘制分隔线
            g.setStroke(new BasicStroke(1.5f));
            g.setColor(new Color(0, 123, 255, 150));
            g.drawLine(25, 45, 175, 45);
            
            // 绘制右视图节点
            int y = 70;
            for (int i = 0; i < rightSideView.size(); i++) {
                Integer val = rightSideView.get(i);
                
                // 绘制节点阴影
                g.setColor(new Color(0, 0, 0, 30));
                g.fillOval(28, y - 12, 24, 24);
                
                // 绘制节点背景渐变
                GradientPaint nodeGradient = new GradientPaint(
                    25, y - 15, new Color(40, 167, 69).brighter(),
                    25 + 20, y - 15 + 20, new Color(40, 167, 69).darker()
                );
                g.setPaint(nodeGradient);
                g.fillOval(25, y - 15, 20, 20);
                
                // 绘制节点边框
                g.setStroke(new BasicStroke(2.0f));
                g.setColor(new Color(25, 135, 84));
                g.drawOval(25, y - 15, 20, 20);
                
                // 绘制节点值
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 12));
                FontMetrics fm = g.getFontMetrics();
                String text = String.valueOf(val);
                int textWidth = fm.stringWidth(text);
                g.drawString(text, 35 - textWidth / 2, y - 7);
                
                // 绘制层级标签
                g.setColor(new Color(73, 80, 87));
                g.setFont(new Font("Arial", Font.PLAIN, 14));
                g.drawString("第 " + (i + 1) + " 层", 55, y - 5);
                
                // 绘制箭头指向效果
                if (i < rightSideView.size() - 1) {
                    g.setStroke(new BasicStroke(1.5f));
                    g.setColor(new Color(108, 117, 125));
                    int arrowY = y + 10;
                    g.drawLine(35, arrowY, 35, arrowY + 15);
                    g.drawLine(35, arrowY + 15, 32, arrowY + 12);
                    g.drawLine(35, arrowY + 15, 38, arrowY + 12);
                }
                
                y += 40;
            }
            
            // 如果没有结果，显示提示
            if (rightSideView.isEmpty()) {
                g.setColor(new Color(108, 117, 125));
                g.setFont(new Font("Arial", Font.ITALIC, 14));
                g.drawString("等待遍历...", 25, 70);
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