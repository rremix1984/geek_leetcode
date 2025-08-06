package com.animation.tree;

import com.animation.Animation;
import com.animation.launcher.AlgorithmTreeLauncher;
import com.leetcode.util.TreeNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;
import javax.swing.text.DefaultHighlighter;

public class BinaryTreeTopView_Animation extends JFrame implements Animation {

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

    private javax.swing.Timer animationTimer;
    private Queue<TreeNode<Integer>> queue = new LinkedList<>();
    private Queue<Integer> distanceQueue = new LinkedList<>();
    private Map<Integer, Integer> topViewMap = new TreeMap<>();
    private List<TreeNode<Integer>> highlightedNodes = new ArrayList<>();
    private TreeNode<Integer> currentNode;
    private int currentDistance;
    private boolean animationComplete = false;

    private volatile boolean isPaused = false;

    public BinaryTreeTopView_Animation() {
        setTitle("二叉树的顶层视图 - 动画演示");
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
        treeInputField = new JTextField("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15", 25);
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
        pauseButton.addActionListener(e -> pause());
        resumeButton.addActionListener(e -> resume());
        resetButton.addActionListener(e -> reset());
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
                if (animationTimer != null && animationTimer.isRunning()) {
                    animationTimer.stop();
                }
                dispose();
                AlgorithmTreeLauncher.getInstance().setVisible(true);
            }
        });
    }

    private void resetAnimationState() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        queue.clear();
        distanceQueue.clear();
        topViewMap.clear();
        highlightedNodes.clear();
        currentNode = null;
        currentDistance = 0;
        animationComplete = false;
        drawingPanel.setTopView(new ArrayList<>());
        drawingPanel.setHighlightedNodes(highlightedNodes, null);
        statusLabel.setText("准备开始");
        highlightCodeLine(-1);
        SwingUtilities.invokeLater(() -> drawingPanel.repaint());
    }

    private TreeNode<Integer> constructTree() {
        TreeNode<Integer> root = new TreeNode<>(1);
        root.left = new TreeNode<>(2);
        root.right = new TreeNode<>(3);
        root.left.left = new TreeNode<>(4);
        root.left.right = new TreeNode<>(5);
        root.right.left = new TreeNode<>(6);
        root.right.right = new TreeNode<>(7);
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
        if (drawingPanel.root != null) {
            queue.offer(drawingPanel.root);
            distanceQueue.offer(0);
        }
        int delay = (11 - speedSlider.getValue()) * 100; // 1000ms to 100ms
        animationTimer = new javax.swing.Timer(delay, e -> animationStep());
        animationTimer.start();
        statusLabel.setText("动画运行中...");
    }

    private void animationStep() {
        if (isPaused) return;

        animationTimer.setDelay((11 - speedSlider.getValue()) * 100);

        if (queue.isEmpty()) {
            animationTimer.stop();
            animationComplete = true;
            statusLabel.setText("动画完成! 顶层视图: " + new ArrayList<>(topViewMap.values()));
            highlightCodeLine(-1);
            drawingPanel.setHighlightedNodes(new ArrayList<>(), null);
            SwingUtilities.invokeLater(() -> drawingPanel.repaint());
            return;
        }

        currentNode = queue.poll();
        currentDistance = distanceQueue.poll();
        highlightedNodes.clear();
        highlightedNodes.add(currentNode);
        
        statusLabel.setText("处理节点: " + currentNode.val + ", 水平距离: " + currentDistance);
        highlightCodeLine(3);

        // 如果这个水平距离还没有被添加到map中，则添加
        if (!topViewMap.containsKey(currentDistance)) {
            topViewMap.put(currentDistance, currentNode.val);
            statusLabel.setText("添加到顶层视图: " + currentNode.val + " (距离: " + currentDistance + ")");
            highlightCodeLine(5);
            
            // 更新顶层视图显示
            drawingPanel.setTopView(new ArrayList<>(topViewMap.values()));
        }

        // 添加左右子节点到队列
        if (currentNode.left != null) {
            queue.offer(currentNode.left);
            distanceQueue.offer(currentDistance - 1);
            highlightCodeLine(7);
        }
        if (currentNode.right != null) {
            queue.offer(currentNode.right);
            distanceQueue.offer(currentDistance + 1);
            highlightCodeLine(9);
        }

        drawingPanel.setHighlightedNodes(highlightedNodes, currentNode);
        SwingUtilities.invokeLater(() -> drawingPanel.repaint());
    }

    public void pause() {
        isPaused = true;
        statusLabel.setText("动画已暂停");
    }

    public void resume() {
        isPaused = false;
        statusLabel.setText("动画继续运行");
    }

    public void reset() {
        resetAnimationState();
    }

    private void highlightCodeLine(int line) {
        codeArea.getHighlighter().removeAllHighlights();
        if (line >= 0 && line < codeArea.getLineCount()) {
            try {
                int start = codeArea.getLineStartOffset(line);
                int end = codeArea.getLineEndOffset(line);
                codeArea.getHighlighter().addHighlight(start, end, 
                    new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW));
                codeArea.setCaretPosition(start);
            } catch (Exception e) {
                // Ignore highlighting errors
            }
        }
    }

    private String getAlgorithmCode() {
        return "public List<Integer> topView(TreeNode root) {\n" +
               "    Map<Integer, Integer> map = new TreeMap<>();\n" +
               "    Queue<TreeNode> queue = new LinkedList<>();\n" +
               "    Queue<Integer> distance = new LinkedList<>();\n" +
               "    \n" +
               "    queue.offer(root);\n" +
               "    distance.offer(0);\n" +
               "    \n" +
               "    while (!queue.isEmpty()) {\n" +
               "        TreeNode current = queue.poll();\n" +
               "        int hd = distance.poll();\n" +
               "        \n" +
               "        // 如果这个水平距离还没有被添加\n" +
               "        if (!map.containsKey(hd))\n" +
               "            map.put(hd, current.val);\n" +
               "        \n" +
               "        if (current.left != null) {\n" +
               "            queue.offer(current.left);\n" +
               "            distance.offer(hd - 1);\n" +
               "        }\n" +
               "        \n" +
               "        if (current.right != null) {\n" +
               "            queue.offer(current.right);\n" +
               "            distance.offer(hd + 1);\n" +
               "        }\n" +
               "    }\n" +
               "    \n" +
               "    return new ArrayList<>(map.values());\n" +
               "}";
    }

    private static class DrawingPanel extends JPanel {
        private TreeNode<Integer> root;
        private List<Integer> topView = new ArrayList<>();
        private List<TreeNode<Integer>> highlightedNodes = new ArrayList<>();
        private TreeNode<Integer> currentNode;

        public DrawingPanel(TreeNode<Integer> root) {
            this.root = root;
            setBackground(Color.WHITE);
        }

        public void setTopView(List<Integer> topView) {
            this.topView = new ArrayList<>(topView);
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
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // 绘制网格背景
            drawGridBackground(g2d);

            // 绘制标题
            drawTitle(g2d);

            // 绘制二叉树
            if (root != null) {
                drawTree(g2d, root, getWidth() / 2, 120, getWidth() / 6, 0);
            }

            // 绘制顶层视图结果
            drawTopView(g2d);
        }

        private void drawGridBackground(Graphics2D g) {
            g.setColor(new Color(240, 240, 240));
            g.setStroke(new BasicStroke(0.5f));
            
            int gridSize = 20;
            for (int x = 0; x < getWidth(); x += gridSize) {
                g.drawLine(x, 0, x, getHeight());
            }
            for (int y = 0; y < getHeight(); y += gridSize) {
                g.drawLine(0, y, getWidth(), y);
            }
        }

        private void drawTitle(Graphics2D g) {
            g.setFont(new Font("微软雅黑", Font.BOLD, 24));
            g.setColor(new Color(51, 51, 51));
            String title = "二叉树顶层视图算法演示";
            FontMetrics fm = g.getFontMetrics();
            int titleX = (getWidth() - fm.stringWidth(title)) / 2;
            g.drawString(title, titleX, 50);
            
            // 绘制水平距离说明
            g.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            g.setColor(new Color(100, 100, 100));
            String subtitle = "水平距离: 左子节点 = 父节点 - 1, 右子节点 = 父节点 + 1";
            FontMetrics fm2 = g.getFontMetrics();
            int subtitleX = (getWidth() - fm2.stringWidth(subtitle)) / 2;
            g.drawString(subtitle, subtitleX, 75);
        }

        private void drawTree(Graphics2D g, TreeNode<Integer> node, int x, int y, int xOffset, int distance) {
            if (node == null) return;

            // 绘制连接线
            g.setStroke(new BasicStroke(2.0f));
            g.setColor(new Color(100, 100, 100));
            
            if (node.left != null) {
                int leftX = x - xOffset;
                int leftY = y + 80;
                g.drawLine(x, y + NODE_SIZE/2, leftX, leftY - NODE_SIZE/2);
                drawTree(g, node.left, leftX, leftY, xOffset / 2, distance - 1);
            }
            
            if (node.right != null) {
                int rightX = x + xOffset;
                int rightY = y + 80;
                g.drawLine(x, y + NODE_SIZE/2, rightX, rightY - NODE_SIZE/2);
                drawTree(g, node.right, rightX, rightY, xOffset / 2, distance + 1);
            }

            // 绘制节点
            boolean isHighlighted = highlightedNodes.contains(node);
            boolean isCurrent = node == currentNode;
            
            if (isCurrent) {
                // 当前处理的节点 - 红色
                g.setColor(new Color(255, 100, 100));
            } else if (isHighlighted) {
                // 高亮节点 - 橙色
                g.setColor(new Color(255, 165, 0));
            } else {
                // 普通节点 - 渐变蓝色
                GradientPaint gradient = new GradientPaint(
                    x - NODE_SIZE/2, y - NODE_SIZE/2, new Color(135, 206, 250),
                    x + NODE_SIZE/2, y + NODE_SIZE/2, new Color(70, 130, 180)
                );
                g.setPaint(gradient);
            }
            
            g.fillOval(x - NODE_SIZE/2, y - NODE_SIZE/2, NODE_SIZE, NODE_SIZE);
            
            // 绘制节点边框
            g.setColor(new Color(50, 50, 50));
            g.setStroke(new BasicStroke(2.0f));
            g.drawOval(x - NODE_SIZE/2, y - NODE_SIZE/2, NODE_SIZE, NODE_SIZE);
            
            // 绘制节点值
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            FontMetrics fm = g.getFontMetrics();
            String text = String.valueOf(node.val);
            int textX = x - fm.stringWidth(text) / 2;
            int textY = y + fm.getAscent() / 2 - 2;
            g.drawString(text, textX, textY);
            
            // 绘制水平距离标签
            g.setColor(new Color(255, 0, 0));
            g.setFont(new Font("Arial", Font.BOLD, 12));
            String distanceText = String.valueOf(distance);
            FontMetrics fm2 = g.getFontMetrics();
            int distanceX = x - fm2.stringWidth(distanceText) / 2;
            int distanceY = y - NODE_SIZE/2 - 5;
            g.drawString(distanceText, distanceX, distanceY);
        }

        private void drawTopView(Graphics2D g) {
            if (topView.isEmpty()) return;
            
            // 绘制顶层视图面板背景
            int panelX = 20;
            int panelY = getHeight() - 150;
            int panelWidth = getWidth() - 40;
            int panelHeight = 120;
            
            // 渐变背景
            GradientPaint bgGradient = new GradientPaint(
                panelX, panelY, new Color(240, 248, 255),
                panelX, panelY + panelHeight, new Color(220, 235, 255)
            );
            g.setPaint(bgGradient);
            g.fillRoundRect(panelX, panelY, panelWidth, panelHeight, 15, 15);
            
            // 边框
            g.setColor(new Color(100, 149, 237));
            g.setStroke(new BasicStroke(2.0f));
            g.drawRoundRect(panelX, panelY, panelWidth, panelHeight, 15, 15);
            
            // 标题
            g.setColor(new Color(25, 25, 112));
            g.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g.drawString("顶层视图结果", panelX + 20, panelY + 25);
            
            // 绘制顶层视图节点
            int nodeY = panelY + 60;
            int startX = panelX + 50;
            int nodeSpacing = 60;
            
            for (int i = 0; i < topView.size(); i++) {
                int nodeX = startX + i * nodeSpacing;
                
                // 节点背景
                GradientPaint nodeGradient = new GradientPaint(
                    nodeX - 20, nodeY - 20, new Color(144, 238, 144),
                    nodeX + 20, nodeY + 20, new Color(34, 139, 34)
                );
                g.setPaint(nodeGradient);
                g.fillOval(nodeX - 20, nodeY - 20, 40, 40);
                
                // 节点边框
                g.setColor(new Color(0, 100, 0));
                g.setStroke(new BasicStroke(2.0f));
                g.drawOval(nodeX - 20, nodeY - 20, 40, 40);
                
                // 节点值
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 14));
                String text = String.valueOf(topView.get(i));
                FontMetrics fm = g.getFontMetrics();
                int textX = nodeX - fm.stringWidth(text) / 2;
                int textY = nodeY + fm.getAscent() / 2 - 2;
                g.drawString(text, textX, textY);
                
                // 箭头指示
                if (i < topView.size() - 1) {
                    g.setColor(new Color(100, 100, 100));
                    g.setStroke(new BasicStroke(2.0f));
                    int arrowStartX = nodeX + 25;
                    int arrowEndX = nodeX + nodeSpacing - 25;
                    g.drawLine(arrowStartX, nodeY, arrowEndX, nodeY);
                    
                    // 箭头头部
                    g.drawLine(arrowEndX - 8, nodeY - 4, arrowEndX, nodeY);
                    g.drawLine(arrowEndX - 8, nodeY + 4, arrowEndX, nodeY);
                }
            }
            
            // 等待提示
            if (topView.isEmpty()) {
                g.setColor(new Color(128, 128, 128));
                g.setFont(new Font("微软雅黑", Font.ITALIC, 14));
                g.drawString("等待动画开始...", panelX + 20, nodeY);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BinaryTreeTopView_Animation().setVisible(true);
        });
    }
}