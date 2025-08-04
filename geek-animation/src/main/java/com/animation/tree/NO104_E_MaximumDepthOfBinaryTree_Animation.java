package com.animation.tree;

import javax.swing.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import javax.swing.Timer;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.event.ActionEvent;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.event.ActionListener;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.util.*;
import com.animation.launcher.AlgorithmTreeLauncher;

/**
 * NO.104 二叉树的最大深度 - 动画演示
 * 
 * 算法思路：
 * 递归计算二叉树的最大深度：
 * 1. 如果节点为空，返回0
 * 2. 递归计算左子树的最大深度
 * 3. 递归计算右子树的最大深度
 * 4. 返回左右子树深度的最大值 + 1
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(h)，h为树的高度
 */
public class NO104_E_MaximumDepthOfBinaryTree_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final int NODE_SIZE = 35;
    
    // 树节点类
    static class TreeNode {
        Integer val;
        TreeNode left;
        TreeNode right;
        int x, y; // 绘制坐标
        int depth; // 当前节点的深度
        boolean isVisited = false; // 是否已访问
        boolean isCalculated = false; // 是否已计算完成
        int leftDepth = 0; // 左子树深度
        int rightDepth = 0; // 右子树深度
        int maxDepth = 0; // 当前节点的最大深度
        
        TreeNode(Integer val) {
            this.val = val;
        }
    }
    
    private TreeNode root;
    private TreeNode currentNode;
    private int finalMaxDepth = 0;
    private boolean isCompleted = false;
    private String statusMessage = "点击开始按钮开始演示";
    private String calculationMessage = "";
    private java.util.List<String> calculationSteps = new ArrayList<>();
    private Stack<TreeNode> callStack = new Stack<>();
    
    private Timer animationTimer;
    private JButton startButton;
    private JButton resetButton;
    private JComboBox<String> testCaseCombo;
    
    // 测试用例
    private final String[] testCaseNames = {
        "示例1: [3,9,20,null,null,15,7] → 深度3",
        "示例2: [1,null,2] → 深度2",
        "示例3: [1] → 深度1",
        "示例4: [] → 深度0",
        "示例5: [1,2,3,4,5] → 深度3"
    };
    
    public NO104_E_MaximumDepthOfBinaryTree_Animation() {
        initializeGUI();
        setupAnimation();
        loadTestCase(0);
    }
    
    private void initializeGUI() {
        setTitle("NO.104 二叉树的最大深度 - 动画演示");
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
                repaint();
            }
        });
        
        startButton = new JButton("开始演示");
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
        JPanel drawPanel = new JPanel() {
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
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!initialized) {
                    // 初始化
                    calculationSteps.clear();
                    callStack.clear();
                    if (root == null) {
                        finalMaxDepth = 0;
                        isCompleted = true;
                        statusMessage = "空树的深度为0";
                        animationTimer.stop();
                        startButton.setText("开始演示");
                    } else {
                        callStack.push(root);
                        statusMessage = "开始计算二叉树的最大深度";
                    }
                    initialized = true;
                    repaint();
                    return;
                }
                
                if (!callStack.isEmpty()) {
                    TreeNode node = callStack.peek();
                    
                    if (!node.isVisited) {
                        // 第一次访问节点
                        node.isVisited = true;
                        currentNode = node;
                        calculationMessage = "访问节点 " + node.val + "，开始计算其最大深度";
                        calculationSteps.add("访问节点 " + node.val);
                        
                        // 如果有子节点，先处理子节点
                        if (node.right != null && !node.right.isCalculated) {
                            callStack.push(node.right);
                        }
                        if (node.left != null && !node.left.isCalculated) {
                            callStack.push(node.left);
                        }
                    } else if (!node.isCalculated) {
                        // 计算当前节点的深度
                        node.leftDepth = (node.left != null) ? node.left.maxDepth : 0;
                        node.rightDepth = (node.right != null) ? node.right.maxDepth : 0;
                        node.maxDepth = Math.max(node.leftDepth, node.rightDepth) + 1;
                        node.isCalculated = true;
                        
                        calculationMessage = String.format("节点 %d: max(%d, %d) + 1 = %d", 
                            node.val, node.leftDepth, node.rightDepth, node.maxDepth);
                        calculationSteps.add(calculationMessage);
                        
                        callStack.pop();
                        
                        if (node == root) {
                            // 根节点计算完成
                            finalMaxDepth = node.maxDepth;
                            isCompleted = true;
                            animationTimer.stop();
                            statusMessage = "计算完成！二叉树的最大深度为: " + finalMaxDepth;
                            startButton.setText("开始演示");
                        }
                    }
                } else {
                    // 栈为空，计算完成
                    isCompleted = true;
                    animationTimer.stop();
                    statusMessage = "计算完成！";
                    startButton.setText("开始演示");
                }
                
                repaint();
            }
        });
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制标题
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 24));
        g2d.setColor(new Color(51, 51, 51));
        String title = "NO.104 二叉树的最大深度算法演示";
        FontMetrics fm = g2d.getFontMetrics();
        int titleX = (getWidth() - fm.stringWidth(title)) / 2;
        g2d.drawString(title, titleX, 50);
        
        // 绘制二叉树
        if (root != null) {
            assignCoordinates(root, getWidth() / 2, 100, getWidth() / 6);
            drawTree(g2d, root);
        } else {
            g2d.setColor(new Color(128, 128, 128));
            g2d.drawString("空树", getWidth() / 2 - 20, 150);
        }
        
        // 绘制调用栈
        drawCallStack(g2d);
        
        // 绘制计算步骤
        drawCalculationSteps(g2d);
        
        // 绘制算法步骤说明
        drawAlgorithmSteps(g2d);
        
        // 绘制状态信息
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(new Color(255, 69, 0));
        g2d.drawString("状态: " + statusMessage, 50, 450);
        
        if (!calculationMessage.isEmpty()) {
            g2d.setColor(new Color(0, 100, 0));
            g2d.drawString("当前计算: " + calculationMessage, 50, 475);
        }
        
        // 绘制结果
        if (isCompleted) {
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 20));
            g2d.setColor(new Color(34, 139, 34));
            String resultText = "最大深度: " + finalMaxDepth;
            g2d.drawString(resultText, 50, 510);
        }
        
        // 绘制复杂度信息
        drawComplexityInfo(g2d);
    }
    
    private void assignCoordinates(TreeNode node, int x, int y, int offset) {
        if (node == null) return;
        
        node.x = x;
        node.y = y;
        
        if (node.left != null) {
            assignCoordinates(node.left, x - offset, y + 60, offset / 2);
        }
        
        if (node.right != null) {
            assignCoordinates(node.right, x + offset, y + 60, offset / 2);
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
        if (node == currentNode) {
            nodeColor = new Color(255, 69, 0); // 当前访问的节点
        } else if (node.isCalculated) {
            nodeColor = new Color(144, 238, 144); // 已计算完成的节点
        } else if (node.isVisited) {
            nodeColor = new Color(255, 255, 0); // 已访问但未计算完成的节点
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
        
        // 绘制深度信息
        if (node.isCalculated) {
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            g2d.setColor(new Color(255, 0, 0));
            String depthText = "深度:" + node.maxDepth;
            int depthX = node.x - fm.stringWidth(depthText) / 2;
            g2d.drawString(depthText, depthX, node.y + NODE_SIZE/2 + 15);
        }
    }
    
    private void drawCallStack(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(new Color(51, 51, 51));
        g2d.drawString("递归调用栈:", 700, 120);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        int startY = 140;
        
        if (callStack.isEmpty()) {
            g2d.setColor(new Color(128, 128, 128));
            g2d.drawString("栈为空", 700, startY);
        } else {
            for (int i = callStack.size() - 1; i >= 0; i--) {
                TreeNode node = callStack.get(i);
                g2d.setColor(new Color(0, 100, 0));
                String stackItem = "maxDepth(" + node.val + ")";
                g2d.drawString(stackItem, 700, startY + (callStack.size() - 1 - i) * 20);
            }
        }
    }
    
    private void drawCalculationSteps(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(new Color(51, 51, 51));
        g2d.drawString("计算步骤:", 50, 370);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        int startY = 390;
        int maxSteps = Math.min(calculationSteps.size(), 4); // 只显示最近的4步
        int startIndex = Math.max(0, calculationSteps.size() - maxSteps);
        
        for (int i = startIndex; i < calculationSteps.size(); i++) {
            g2d.setColor(new Color(0, 100, 0));
            g2d.drawString((i + 1) + ". " + calculationSteps.get(i), 50, startY + (i - startIndex) * 18);
        }
    }
    
    private void drawAlgorithmSteps(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(new Color(51, 51, 51));
        
        String[] steps = {
            "最大深度计算算法:",
            "1. 如果节点为空，返回0",
            "2. 递归计算左子树的最大深度",
            "3. 递归计算右子树的最大深度",
            "4. 返回 max(左深度, 右深度) + 1",
            "",
            "递归公式:",
            "maxDepth(node) = max(maxDepth(left), maxDepth(right)) + 1"
        };
        
        int startY = 550;
        for (int i = 0; i < steps.length; i++) {
            if (i == 0 || i == 6) {
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
                g2d.setColor(new Color(255, 69, 0));
            } else {
                g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                g2d.setColor(new Color(51, 51, 51));
            }
            g2d.drawString(steps[i], 50, startY + i * 18);
        }
    }
    
    private void drawComplexityInfo(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g2d.setColor(new Color(0, 100, 0));
        g2d.drawString("时间复杂度: O(n)", 800, 580);
        g2d.drawString("空间复杂度: O(h)", 800, 600);
        g2d.drawString("核心技术: 递归 + 分治", 800, 620);
    }
    
    private TreeNode buildTestCase(int caseIndex) {
        switch (caseIndex) {
            case 0: // [3,9,20,null,null,15,7]
                TreeNode root1 = new TreeNode(3);
                root1.left = new TreeNode(9);
                root1.right = new TreeNode(20);
                root1.right.left = new TreeNode(15);
                root1.right.right = new TreeNode(7);
                return root1;
                
            case 1: // [1,null,2]
                TreeNode root2 = new TreeNode(1);
                root2.right = new TreeNode(2);
                return root2;
                
            case 2: // [1]
                return new TreeNode(1);
                
            case 3: // []
                return null;
                
            case 4: // [1,2,3,4,5]
                TreeNode root5 = new TreeNode(1);
                root5.left = new TreeNode(2);
                root5.right = new TreeNode(3);
                root5.left.left = new TreeNode(4);
                root5.left.right = new TreeNode(5);
                return root5;
                
            default:
                return null;
        }
    }
    
    private void loadTestCase(int index) {
        root = buildTestCase(index);
        resetNodeStates(root);
        currentNode = null;
        finalMaxDepth = 0;
        isCompleted = false;
        calculationMessage = "";
        calculationSteps.clear();
        callStack.clear();
        statusMessage = "已加载测试用例 " + (index + 1) + "，点击开始按钮开始演示";
    }
    
    private void resetNodeStates(TreeNode node) {
        if (node == null) return;
        
        node.isVisited = false;
        node.isCalculated = false;
        node.leftDepth = 0;
        node.rightDepth = 0;
        node.maxDepth = 0;
        
        resetNodeStates(node.left);
        resetNodeStates(node.right);
    }
    
    private void startAnimation() {
        if (animationTimer.isRunning()) {
            animationTimer.stop();
            startButton.setText("开始演示");
        } else {
            resetAnimation();
            animationTimer.start();
            startButton.setText("暂停");
        }
    }
    
    private void resetAnimation() {
        animationTimer.stop();
        loadTestCase(testCaseCombo.getSelectedIndex());
        startButton.setText("开始演示");
        repaint();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO104_E_MaximumDepthOfBinaryTree_Animation().setVisible(true);
        });
    }
}