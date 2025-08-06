package com.animation.tree;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * NO.145 二叉树的后序遍历 - 动画演示
 * 
 * 算法思路：
 * 后序遍历（左-右-根）：
 * 1. 递归遍历左子树
 * 2. 递归遍历右子树
 * 3. 访问根节点
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(h) - h为树的高度
 */
public class NO145_E_PostorderTraversal_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final int NODE_SIZE = 35;
    
    // 树节点类
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        int x, y; // 绘制坐标
        boolean isVisited = false; // 是否已访问
        boolean isCurrent = false; // 是否是当前访问的节点
        
        TreeNode(int val) {
            this.val = val;
        }
    }
    
    private TreeNode root;
    private TreeNode currentNode;
    private List<Integer> traversalResult = new ArrayList<>();
    private List<String> traversalSteps = new ArrayList<>();
    private Stack<TreeNode> callStack = new Stack<>();
    private boolean isCompleted = false;
    private String statusMessage = "点击开始按钮开始后序遍历";
    private String currentStep = "";
    private JPanel drawPanel;
    
    private Timer animationTimer;
    private JButton startButton;
    private JButton resetButton;
    private JComboBox<String> testCaseCombo;
    
    // 测试用例
    private final String[] testCaseNames = {
        "示例1: [1,null,2,3] → [3,2,1]",
        "示例2: [1,2,3,4,5,6,7] → [4,5,2,6,7,3,1]",
        "示例3: [3,9,20,null,null,15,7] → [9,15,7,20,3]",
        "示例4: [1] → [1]",
        "示例5: [] → []"
    };
    
    public NO145_E_PostorderTraversal_Animation() {
        initializeGUI();
        setupAnimation();
        loadTestCase(0);
    }
    
    private void initializeGUI() {
        setTitle("NO.145 二叉树的后序遍历 - 动画演示");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // 创建控制面板
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.setBackground(new Color(240, 248, 255));
        
        // 测试用例选择
        testCaseCombo = new JComboBox<>(testCaseNames);
        testCaseCombo.addActionListener(e -> {
            if (!animationTimer.isRunning()) {
                loadTestCase(testCaseCombo.getSelectedIndex());
                SwingUtilities.invokeLater(() -> drawPanel.repaint());
            }
        });
        
        startButton = new JButton("开始遍历");
        startButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        startButton.addActionListener(e -> startAnimation());
        
        resetButton = new JButton("重置");
        resetButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        resetButton.addActionListener(e -> resetAnimation());
        
        controlPanel.add(new JLabel("选择测试用例:"));
        controlPanel.add(testCaseCombo);
        controlPanel.add(startButton);
        controlPanel.add(resetButton);
        
        add(controlPanel, BorderLayout.NORTH);
        
        // 创建绘图面板
        drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawAnimation(g);
            }
        };
        drawPanel.setBackground(Color.WHITE);
        add(drawPanel, BorderLayout.CENTER);
    }
    
    private void setupAnimation() {
        animationTimer = new Timer(1500, new ActionListener() {
            private boolean initialized = false;
            private Iterator<TreeNode> traversalIterator;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!initialized) {
                    // 初始化遍历
                    traversalResult.clear();
                    traversalSteps.clear();
                    callStack.clear();
                    resetVisitedFlags(root);
                    
                    if (root == null) {
                        isCompleted = true;
                        statusMessage = "空树，遍历结束";
                        animationTimer.stop();
                        startButton.setText("开始遍历");
                        SwingUtilities.invokeLater(() -> drawPanel.repaint());
                        return;
                    }
                    
                    // 创建后序遍历迭代器
                    traversalIterator = createPostorderIterator();
                    statusMessage = "开始后序遍历（左-右-根）";
                    initialized = true;
                    SwingUtilities.invokeLater(() -> drawPanel.repaint());
                    return;
                }
                
                if (traversalIterator.hasNext()) {
                    // 清除之前的当前节点标记
                    if (currentNode != null) {
                        currentNode.isCurrent = false;
                        currentNode.isVisited = true;
                    }
                    
                    // 访问下一个节点
                    currentNode = traversalIterator.next();
                    currentNode.isCurrent = true;
                    
                    traversalResult.add(currentNode.val);
                    currentStep = "访问节点: " + currentNode.val;
                    traversalSteps.add(currentStep);
                    
                    statusMessage = "正在进行后序遍历...";
                } else {
                    // 遍历完成
                    if (currentNode != null) {
                        currentNode.isCurrent = false;
                        currentNode.isVisited = true;
                    }
                    
                    isCompleted = true;
                    animationTimer.stop();
                    statusMessage = "后序遍历完成！";
                    startButton.setText("开始遍历");
                    currentStep = "遍历结果: " + traversalResult.toString();
                }
                
                SwingUtilities.invokeLater(() -> drawPanel.repaint());
            }
        });
    }
    
    private Iterator<TreeNode> createPostorderIterator() {
        List<TreeNode> result = new ArrayList<>();
        postorderTraversal(root, result);
        return result.iterator();
    }
    
    private void postorderTraversal(TreeNode node, List<TreeNode> result) {
        if (node == null) return;
        
        postorderTraversal(node.left, result); // 左
        postorderTraversal(node.right, result); // 右
        result.add(node); // 根
    }
    
    private void resetVisitedFlags(TreeNode node) {
        if (node == null) return;
        node.isVisited = false;
        node.isCurrent = false;
        resetVisitedFlags(node.left);
        resetVisitedFlags(node.right);
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制标题
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 24));
        g2d.setColor(new Color(51, 51, 51));
        String title = "二叉树后序遍历算法演示";
        FontMetrics fm = g2d.getFontMetrics();
        int titleX = (getWidth() - fm.stringWidth(title)) / 2;
        g2d.drawString(title, titleX, 50);
        
        // 绘制二叉树
        if (root != null) {
            assignCoordinates(root, getWidth() / 2, 120, getWidth() / 6);
            drawTree(g2d, root);
        } else {
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 18));
            g2d.setColor(new Color(128, 128, 128));
            g2d.drawString("空树", getWidth() / 2 - 20, 200);
        }
        
        // 绘制遍历结果
        drawTraversalResult(g2d);
        
        // 绘制遍历步骤
        drawTraversalSteps(g2d);
        
        // 绘制算法说明
        drawAlgorithmDescription(g2d);
        
        // 绘制状态信息
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(new Color(255, 69, 0));
        g2d.drawString("状态: " + statusMessage, 50, 450);
        
        if (!currentStep.isEmpty()) {
            g2d.setColor(new Color(0, 100, 0));
            g2d.drawString("当前步骤: " + currentStep, 50, 475);
        }
        
        // 绘制复杂度信息
        drawComplexityInfo(g2d);
    }
    
    private void assignCoordinates(TreeNode node, int x, int y, int offset) {
        if (node == null) return;
        
        node.x = x;
        node.y = y;
        
        if (node.left != null) {
            assignCoordinates(node.left, x - offset, y + 80, offset / 2);
        }
        
        if (node.right != null) {
            assignCoordinates(node.right, x + offset, y + 80, offset / 2);
        }
    }
    
    private void drawTree(Graphics2D g2d, TreeNode node) {
        if (node == null) return;
        
        // 绘制连线
        if (node.left != null) {
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(node.x, node.y, node.left.x, node.left.y);
            drawTree(g2d, node.left);
        }
        
        if (node.right != null) {
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(node.x, node.y, node.right.x, node.right.y);
            drawTree(g2d, node.right);
        }
        
        // 绘制节点
        Color nodeColor;
        if (node.isCurrent) {
            nodeColor = new Color(255, 69, 0); // 当前访问的节点
        } else if (node.isVisited) {
            nodeColor = new Color(144, 238, 144); // 已访问的节点
        } else {
            nodeColor = new Color(173, 216, 230); // 未访问的节点
        }
        
        g2d.setColor(nodeColor);
        g2d.fillOval(node.x - NODE_SIZE/2, node.y - NODE_SIZE/2, NODE_SIZE, NODE_SIZE);
        
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(node.x - NODE_SIZE/2, node.y - NODE_SIZE/2, NODE_SIZE, NODE_SIZE);
        
        // 绘制节点值
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        String value = String.valueOf(node.val);
        FontMetrics fm = g2d.getFontMetrics();
        int textX = node.x - fm.stringWidth(value) / 2;
        int textY = node.y + fm.getAscent() / 2;
        g2d.drawString(value, textX, textY);
    }
    
    private void drawTraversalResult(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(new Color(51, 51, 51));
        g2d.drawString("后序遍历结果:", 50, 380);
        
        if (!traversalResult.isEmpty()) {
            g2d.setFont(new Font("Arial", Font.BOLD, 18));
            g2d.setColor(new Color(0, 100, 0));
            String result = traversalResult.toString();
            g2d.drawString(result, 200, 380);
        }
    }
    
    private void drawTraversalSteps(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(new Color(51, 51, 51));
        g2d.drawString("遍历步骤:", 700, 200);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        int startY = 220;
        int maxSteps = Math.min(traversalSteps.size(), 8);
        int startIndex = Math.max(0, traversalSteps.size() - maxSteps);
        
        for (int i = startIndex; i < traversalSteps.size(); i++) {
            g2d.setColor(new Color(0, 100, 0));
            g2d.drawString((i + 1) + ". " + traversalSteps.get(i), 700, startY + (i - startIndex) * 18);
        }
    }
    
    private void drawAlgorithmDescription(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(new Color(51, 51, 51));
        
        String[] description = {
            "后序遍历算法（左-右-根）:",
            "1. 递归遍历左子树",
            "2. 递归遍历右子树",
            "3. 访问根节点",
            "",
            "特点:",
            "• 根节点总是最后被访问",
            "• 适用于删除树节点、计算目录大小"
        };
        
        int startY = 520;
        for (int i = 0; i < description.length; i++) {
            if (i == 0 || i == 5) {
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
                g2d.setColor(new Color(255, 69, 0));
            } else {
                g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                g2d.setColor(new Color(51, 51, 51));
            }
            g2d.drawString(description[i], 50, startY + i * 18);
        }
    }
    
    private void drawComplexityInfo(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g2d.setColor(new Color(0, 100, 0));
        g2d.drawString("时间复杂度: O(n)", 700, 580);
        g2d.drawString("空间复杂度: O(h)", 700, 600);
        g2d.drawString("h为树的高度", 700, 620);
    }
    
    private TreeNode buildTestCase(int caseIndex) {
        switch (caseIndex) {
            case 0: // [1,null,2,3]
                TreeNode root0 = new TreeNode(1);
                root0.right = new TreeNode(2);
                root0.right.left = new TreeNode(3);
                return root0;
                
            case 1: // [1,2,3,4,5,6,7]
                TreeNode root1 = new TreeNode(1);
                root1.left = new TreeNode(2);
                root1.right = new TreeNode(3);
                root1.left.left = new TreeNode(4);
                root1.left.right = new TreeNode(5);
                root1.right.left = new TreeNode(6);
                root1.right.right = new TreeNode(7);
                return root1;
                
            case 2: // [3,9,20,null,null,15,7]
                TreeNode root2 = new TreeNode(3);
                root2.left = new TreeNode(9);
                root2.right = new TreeNode(20);
                root2.right.left = new TreeNode(15);
                root2.right.right = new TreeNode(7);
                return root2;
                
            case 3: // [1]
                return new TreeNode(1);
                
            case 4: // []
                return null;
                
            default:
                return null;
        }
    }
    
    private void loadTestCase(int index) {
        root = buildTestCase(index);
        currentNode = null;
        isCompleted = false;
        currentStep = "";
        traversalResult.clear();
        traversalSteps.clear();
        callStack.clear();
        statusMessage = "已加载测试用例 " + (index + 1) + "，点击开始按钮开始遍历";
    }
    
    private void startAnimation() {
        if (animationTimer.isRunning()) {
            animationTimer.stop();
            startButton.setText("开始遍历");
        } else {
            resetAnimation();
            animationTimer.start();
            startButton.setText("暂停");
        }
    }
    
    private void resetAnimation() {
        animationTimer.stop();
        loadTestCase(testCaseCombo.getSelectedIndex());
        startButton.setText("开始遍历");
        SwingUtilities.invokeLater(() -> drawPanel.repaint());
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO145_E_PostorderTraversal_Animation().setVisible(true);
        });
    }
}