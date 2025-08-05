package com.animation.interval;

import javax.swing.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 面试题 04.10. 检查子树 - 动画演示
 * 演示检查一个二叉树是否为另一个二叉树的子树的过程
 * 
 * 设计文档：
 * 1. 功能需求：可视化子树检查算法的执行过程
 * 2. 技术架构：基于Swing的动画演示系统
 * 3. 核心算法：通过序列化比较树结构
 * 4. 边界条件：空树、单节点、完全不匹配等情况
 * 5. 性能考虑：时间复杂度O(m+n)，空间复杂度O(m+n)
 */
public class Interval_04_10_N_CheckSubTree_Animation extends JFrame {
    private TreeNode mainTree;
    private TreeNode subTree;
    private boolean isAnimating;
    private Timer animationTimer;
    private List<CheckStep> steps;
    private int currentStep;
    
    // UI组件
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JTextField mainTreeInput;
    private JTextField subTreeInput;
    private JButton startButton, stepButton, resetButton, autoButton, homeButton;
    private JLabel statusLabel;
    private JLabel resultLabel;
    
    // 动画状态
    private boolean isSubTree;
    private String currentOperation;
    private String mainTreeSerialization;
    private String subTreeSerialization;
    private int highlightIndex;
    
    // 树节点类
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode(int val) {
            this.val = val;
        }
    }
    
    // 检查步骤类
    private static class CheckStep {
        String operation;
        String mainSer;
        String subSer;
        int highlightPos;
        boolean found;
        
        CheckStep(String operation, String mainSer, String subSer, int highlightPos, boolean found) {
            this.operation = operation;
            this.mainSer = mainSer;
            this.subSer = subSer;
            this.highlightPos = highlightPos;
            this.found = found;
        }
    }
    
    public Interval_04_10_N_CheckSubTree_Animation() {
        setTitle("面试题 04.10. 检查子树 - 动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 900);
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
        isSubTree = false;
        currentOperation = "准备开始";
        highlightIndex = -1;
        
        // 默认示例数据
        // 主树：[3,4,5,1,2]
        mainTree = new TreeNode(3);
        mainTree.left = new TreeNode(4);
        mainTree.right = new TreeNode(5);
        mainTree.left.left = new TreeNode(1);
        mainTree.left.right = new TreeNode(2);
        
        // 子树：[4,1,2]
        subTree = new TreeNode(4);
        subTree.left = new TreeNode(1);
        subTree.right = new TreeNode(2);
    }
    
    private void initComponents() {
        mainPanel = new JPanel();
        controlPanel = new JPanel();
        
        mainTreeInput = new JTextField("3,4,5,1,2", 15);
        subTreeInput = new JTextField("4,1,2", 15);
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
        controlPanel.add(new JLabel("主树:"));
        controlPanel.add(mainTreeInput);
        controlPanel.add(new JLabel("子树:"));
        controlPanel.add(subTreeInput);
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
        mainPanel = new SubTreeVisualizationPanel();
        
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
        statusLabel.setText("状态: 开始检查子树");
        mainPanel.repaint();
    }
    
    private void parseInput() {
        try {
            String mainInput = mainTreeInput.getText().trim();
            String subInput = subTreeInput.getText().trim();
            String[] mainValues = mainInput.split(",");
            String[] subValues = subInput.split(",");
            mainTree = buildTree(mainValues);
            subTree = buildTree(subValues);
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
        
        // 步骤1：序列化主树
        steps.add(new CheckStep("开始序列化主树", "", "", -1, false));
        mainTreeSerialization = serialize(mainTree);
        steps.add(new CheckStep("主树序列化完成: " + mainTreeSerialization, mainTreeSerialization, "", -1, false));
        
        // 步骤2：序列化子树
        steps.add(new CheckStep("开始序列化子树", mainTreeSerialization, "", -1, false));
        subTreeSerialization = serialize(subTree);
        steps.add(new CheckStep("子树序列化完成: " + subTreeSerialization, mainTreeSerialization, subTreeSerialization, -1, false));
        
        // 步骤3：在主树序列化中查找子树序列化
        steps.add(new CheckStep("在主树序列化中查找子树序列化", mainTreeSerialization, subTreeSerialization, -1, false));
        
        boolean found = mainTreeSerialization.contains(subTreeSerialization);
        if (found) {
            int index = mainTreeSerialization.indexOf(subTreeSerialization);
            steps.add(new CheckStep("找到匹配位置: " + index, mainTreeSerialization, subTreeSerialization, index, true));
        } else {
            steps.add(new CheckStep("未找到匹配", mainTreeSerialization, subTreeSerialization, -1, false));
        }
        
        isSubTree = found;
    }
    
    private String serialize(TreeNode root) {
        if (root == null) return "#";
        return root.val + "," + serialize(root.left) + "," + serialize(root.right);
    }
    
    private void stepExecution() {
        if (currentStep < steps.size()) {
            CheckStep step = steps.get(currentStep);
            currentOperation = step.operation;
            mainTreeSerialization = step.mainSer;
            subTreeSerialization = step.subSer;
            highlightIndex = step.highlightPos;
            
            statusLabel.setText("状态: " + currentOperation);
            
            if (currentStep == steps.size() - 1) {
                // 最后一步，显示最终结果
                resultLabel.setText("结果: " + (isSubTree ? "是子树" : "不是子树"));
                stepButton.setEnabled(false);
                if (animationTimer.isRunning()) {
                    animationTimer.stop();
                    autoButton.setText("自动演示");
                }
            }
            
            currentStep++;
            mainPanel.repaint();
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
        mainTreeSerialization = "";
        subTreeSerialization = "";
        highlightIndex = -1;
        
        startButton.setEnabled(true);
        stepButton.setEnabled(false);
        autoButton.setText("自动演示");
        statusLabel.setText("状态: 准备开始");
        resultLabel.setText("结果: 未开始");
        mainPanel.repaint();
    }
    
    private class SubTreeVisualizationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            drawTrees(g2d);
            drawSerialization(g2d);
            drawAlgorithmInfo(g2d);
        }
        
        private void drawTrees(Graphics2D g2d) {
            // 绘制主树
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g2d.drawString("主树", 150, 30);
            
            if (mainTree != null) {
                drawTree(g2d, mainTree, 200, 60, 80, Color.LIGHT_GRAY);
            }
            
            // 绘制子树
            g2d.drawString("子树", 450, 30);
            if (subTree != null) {
                drawTree(g2d, subTree, 500, 60, 60, Color.CYAN);
            }
        }
        
        private void drawTree(Graphics2D g2d, TreeNode node, int x, int y, int offset, Color nodeColor) {
            if (node == null) return;
            
            // 绘制连线
            if (node.left != null) {
                g2d.setColor(Color.BLACK);
                g2d.drawLine(x, y, x - offset, y + 60);
                drawTree(g2d, node.left, x - offset, y + 60, offset / 2, nodeColor);
            }
            
            if (node.right != null) {
                g2d.setColor(Color.BLACK);
                g2d.drawLine(x, y, x + offset, y + 60);
                drawTree(g2d, node.right, x + offset, y + 60, offset / 2, nodeColor);
            }
            
            // 绘制节点
            g2d.setColor(nodeColor);
            g2d.fillOval(x - 15, y - 15, 30, 30);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(x - 15, y - 15, 30, 30);
            
            // 绘制节点值
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            FontMetrics fm = g2d.getFontMetrics();
            String value = String.valueOf(node.val);
            int textX = x - fm.stringWidth(value) / 2;
            int textY = y + fm.getAscent() / 2;
            g2d.drawString(value, textX, textY);
        }
        
        private void drawSerialization(Graphics2D g2d) {
            int y = 300;
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g2d.drawString("序列化结果:", 20, y);
            
            // 主树序列化
            if (!mainTreeSerialization.isEmpty()) {
                g2d.setFont(new Font("Courier New", Font.PLAIN, 14));
                g2d.drawString("主树: " + mainTreeSerialization, 20, y + 30);
            }
            
            // 子树序列化
            if (!subTreeSerialization.isEmpty()) {
                g2d.drawString("子树: " + subTreeSerialization, 20, y + 60);
                
                // 高亮匹配位置
                if (highlightIndex >= 0) {
                    g2d.setColor(Color.RED);
                    FontMetrics fm = g2d.getFontMetrics();
                    String prefix = "主树: " + mainTreeSerialization.substring(0, highlightIndex);
                    int startX = 20 + fm.stringWidth(prefix);
                    int width = fm.stringWidth(subTreeSerialization);
                    g2d.fillRect(startX, y + 15, width, 20);
                    
                    g2d.setColor(Color.WHITE);
                    g2d.drawString(subTreeSerialization, startX, y + 30);
                }
            }
        }
        
        private void drawAlgorithmInfo(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            
            int y = getHeight() - 150;
            g2d.drawString("算法说明：", 20, y);
            g2d.drawString("1. 将主树和子树分别进行前序遍历序列化", 20, y + 20);
            g2d.drawString("2. 在序列化过程中用特殊符号标记空节点", 20, y + 40);
            g2d.drawString("3. 检查子树的序列化字符串是否为主树序列化字符串的子串", 20, y + 60);
            g2d.drawString("4. 如果是子串，则子树是主树的子树", 20, y + 80);
            
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
            new Interval_04_10_N_CheckSubTree_Animation().setVisible(true);
        });
    }
}