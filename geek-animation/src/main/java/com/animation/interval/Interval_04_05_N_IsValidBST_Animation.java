package com.animation.interval;

import javax.swing.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 面试题 04.05. 合法二叉搜索树 - 动画演示
 * 演示检查二叉树是否为二叉搜索树的过程
 * 
 * 设计文档：
 * 1. 功能需求：可视化二叉搜索树验证算法的执行过程
 * 2. 技术架构：基于Swing的动画演示系统
 * 3. 核心算法：递归验证每个节点的值范围
 * 4. 边界条件：空树、单节点、不满足BST性质等情况
 * 5. 性能考虑：时间复杂度O(n)，空间复杂度O(h)
 */
public class Interval_04_05_N_IsValidBST_Animation extends JFrame {
    private TreeNode root;
    private boolean isAnimating;
    private Timer animationTimer;
    private List<ValidationStep> steps;
    private int currentStep;
    
    // UI组件
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JTextField treeInput;
    private JButton startButton, stepButton, resetButton, autoButton, homeButton;
    private JLabel statusLabel;
    private JLabel resultLabel;
    
    // 动画状态
    private boolean isValid;
    private String currentOperation;
    private TreeNode currentNode;
    private Integer lowerBound, upperBound;
    
    // 树节点类
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode(int val) {
            this.val = val;
        }
    }
    
    // 验证步骤类
    private static class ValidationStep {
        TreeNode node;
        Integer lower;
        Integer upper;
        boolean result;
        String description;
        
        ValidationStep(TreeNode node, Integer lower, Integer upper, String description) {
            this.node = node;
            this.lower = lower;
            this.upper = upper;
            this.description = description;
        }
    }
    
    public Interval_04_05_N_IsValidBST_Animation() {
        setTitle("面试题 04.05. 合法二叉搜索树 - 动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        
        initData();
        initComponents();
        setupLayout();
        setupEventHandlers();
        setupAnimation();
    }
    
    private void initData() {
        isAnimating = false;
        steps = new ArrayList<>();
        currentStep = 0;
        isValid = true;
        currentOperation = "准备开始";
        
        // 默认示例数据：[5,1,4,null,null,3,6]
        root = new TreeNode(5);
        root.left = new TreeNode(1);
        root.right = new TreeNode(4);
        root.right.left = new TreeNode(3);
        root.right.right = new TreeNode(6);
    }
    
    private void initComponents() {
        mainPanel = new JPanel();
        controlPanel = new JPanel();
        
        treeInput = new JTextField("5,1,4,null,null,3,6", 20);
        startButton = new JButton("开始演示");
        stepButton = new JButton("单步执行");
        resetButton = new JButton("重置");
        autoButton = new JButton("自动演示");
        homeButton = new JButton("返回主页");
        statusLabel = new JLabel("状态: 准备开始");
        resultLabel = new JLabel("结果: 未开始");
        
        // 设置字体
        Font font = new Font("微软雅黑", Font.PLAIN, 14);
        statusLabel.setFont(font);
        resultLabel.setFont(font);
        
        stepButton.setEnabled(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 控制面板
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JLabel("二叉树(层序遍历):"));
        controlPanel.add(treeInput);
        controlPanel.add(startButton);
        controlPanel.add(stepButton);
        controlPanel.add(autoButton);
        controlPanel.add(resetButton);
        controlPanel.add(homeButton);
        
        // 状态面板
        JPanel statusPanel = new JPanel(new GridLayout(2, 1));
        statusPanel.add(statusLabel);
        statusPanel.add(resultLabel);
        
        // 主面板
        mainPanel = new BSTVisualizationPanel();
        
        add(controlPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        startButton.addActionListener(e -> startDemo());
        stepButton.addActionListener(e -> stepExecution());
        resetButton.addActionListener(e -> resetDemo());
        autoButton.addActionListener(e -> toggleAutoDemo());
        homeButton.addActionListener(e -> {
            dispose();
            AlgorithmTreeLauncher.showMainWindow();
        });
    }
    
    private void setupAnimation() {
        animationTimer = new Timer(2000, e -> {
            stepExecution();
        });
    }
    
    private void startDemo() {
        parseInput();
        generateSteps();
        currentStep = 0;
        startButton.setEnabled(false);
        stepButton.setEnabled(true);
        statusLabel.setText("状态: 开始验证二叉搜索树");
        repaint();
    }
    
    private void parseInput() {
        try {
            String input = treeInput.getText().trim();
            String[] values = input.split(",");
            root = buildTree(values);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "输入格式错误，使用默认示例");
            initData();
        }
    }
    
    private TreeNode buildTree(String[] values) {
        if (values.length == 0 || "null".equals(values[0])) return null;
        
        TreeNode root = new TreeNode(Integer.parseInt(values[0]));
        java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
        queue.offer(root);
        
        int i = 1;
        while (!queue.isEmpty() && i < values.length) {
            TreeNode node = queue.poll();
            
            if (i < values.length && !"null".equals(values[i])) {
                node.left = new TreeNode(Integer.parseInt(values[i]));
                queue.offer(node.left);
            }
            i++;
            
            if (i < values.length && !"null".equals(values[i])) {
                node.right = new TreeNode(Integer.parseInt(values[i]));
                queue.offer(node.right);
            }
            i++;
        }
        
        return root;
    }
    
    private void generateSteps() {
        steps.clear();
        validateBST(root, null, null);
    }
    
    private boolean validateBST(TreeNode node, Integer lower, Integer upper) {
        if (node == null) {
            steps.add(new ValidationStep(null, lower, upper, "空节点，返回true"));
            return true;
        }
        
        String desc = String.format("检查节点%d，范围[%s, %s]", 
            node.val, 
            lower == null ? "-∞" : lower.toString(),
            upper == null ? "+∞" : upper.toString());
        
        if ((lower != null && node.val <= lower) || (upper != null && node.val >= upper)) {
            steps.add(new ValidationStep(node, lower, upper, desc + " - 违反BST性质，返回false"));
            return false;
        }
        
        steps.add(new ValidationStep(node, lower, upper, desc + " - 满足条件"));
        
        return validateBST(node.left, lower, node.val) && validateBST(node.right, node.val, upper);
    }
    
    private void stepExecution() {
        if (currentStep < steps.size()) {
            ValidationStep step = steps.get(currentStep);
            currentNode = step.node;
            lowerBound = step.lower;
            upperBound = step.upper;
            currentOperation = step.description;
            
            statusLabel.setText("状态: " + currentOperation);
            
            if (currentStep == steps.size() - 1) {
                // 最后一步，显示最终结果
                isValid = !currentOperation.contains("返回false");
                resultLabel.setText("结果: " + (isValid ? "是合法的二叉搜索树" : "不是合法的二叉搜索树"));
                stepButton.setEnabled(false);
                if (animationTimer.isRunning()) {
                    animationTimer.stop();
                    autoButton.setText("自动演示");
                }
            }
            
            currentStep++;
            repaint();
        }
    }
    
    private void toggleAutoDemo() {
        if (animationTimer.isRunning()) {
            animationTimer.stop();
            autoButton.setText("自动演示");
        } else {
            animationTimer.start();
            autoButton.setText("暂停演示");
        }
    }
    
    private void resetDemo() {
        animationTimer.stop();
        initData();
        currentStep = 0;
        steps.clear();
        currentNode = null;
        lowerBound = null;
        upperBound = null;
        
        startButton.setEnabled(true);
        stepButton.setEnabled(false);
        autoButton.setText("自动演示");
        statusLabel.setText("状态: 准备开始");
        resultLabel.setText("结果: 未开始");
        repaint();
    }
    
    private class BSTVisualizationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            drawTree(g2d);
            drawAlgorithmInfo(g2d);
        }
        
        private void drawTree(Graphics2D g2d) {
            if (root == null) return;
            
            int startX = getWidth() / 2;
            int startY = 80;
            drawNode(g2d, root, startX, startY, getWidth() / 4);
        }
        
        private void drawNode(Graphics2D g2d, TreeNode node, int x, int y, int offset) {
            if (node == null) return;
            
            // 绘制连线
            if (node.left != null) {
                g2d.setColor(Color.BLACK);
                g2d.drawLine(x, y, x - offset, y + 80);
                drawNode(g2d, node.left, x - offset, y + 80, offset / 2);
            }
            
            if (node.right != null) {
                g2d.setColor(Color.BLACK);
                g2d.drawLine(x, y, x + offset, y + 80);
                drawNode(g2d, node.right, x + offset, y + 80, offset / 2);
            }
            
            // 绘制节点
            Color nodeColor = Color.LIGHT_GRAY;
            if (node == currentNode) {
                nodeColor = Color.YELLOW;
            }
            
            g2d.setColor(nodeColor);
            g2d.fillOval(x - 20, y - 20, 40, 40);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(x - 20, y - 20, 40, 40);
            
            // 绘制节点值
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            FontMetrics fm = g2d.getFontMetrics();
            String value = String.valueOf(node.val);
            int textX = x - fm.stringWidth(value) / 2;
            int textY = y + fm.getAscent() / 2;
            g2d.drawString(value, textX, textY);
            
            // 如果是当前节点，显示范围信息
            if (node == currentNode && lowerBound != null || upperBound != null) {
                g2d.setFont(new Font("Arial", Font.PLAIN, 12));
                String range = String.format("[%s, %s]", 
                    lowerBound == null ? "-∞" : lowerBound.toString(),
                    upperBound == null ? "+∞" : upperBound.toString());
                g2d.setColor(Color.RED);
                g2d.drawString(range, x - 30, y + 35);
            }
        }
        
        private void drawAlgorithmInfo(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            
            int y = getHeight() - 120;
            g2d.drawString("算法说明：", 20, y);
            g2d.drawString("1. 对于每个节点，检查其值是否在有效范围内", 20, y + 20);
            g2d.drawString("2. 左子树的所有节点值必须小于当前节点值", 20, y + 40);
            g2d.drawString("3. 右子树的所有节点值必须大于当前节点值", 20, y + 60);
            g2d.drawString("4. 递归检查所有子树", 20, y + 80);
            
            // 显示当前操作
            if (currentOperation != null) {
                g2d.setColor(Color.BLUE);
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
                g2d.drawString("当前操作: " + currentOperation, 20, 30);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Interval_04_05_N_IsValidBST_Animation().setVisible(true);
        });
    }
}