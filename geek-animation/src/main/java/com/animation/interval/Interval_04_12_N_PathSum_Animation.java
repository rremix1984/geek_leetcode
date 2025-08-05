package com.animation.interval;

import javax.swing.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 面试题 04.12. 求和路径 - 动画演示
 * 演示计算二叉树中路径和等于给定值的路径数量的过程
 * 
 * 设计文档：
 * 1. 功能需求：可视化路径和计算算法的执行过程
 * 2. 技术架构：基于Swing的动画演示系统
 * 3. 核心算法：前缀和 + 回溯法
 * 4. 边界条件：空树、单节点、负数等情况
 * 5. 性能考虑：时间复杂度O(n)，空间复杂度O(n)
 */
public class Interval_04_12_N_PathSum_Animation extends JFrame {
    private TreeNode root;
    private int targetSum;
    private boolean isAnimating;
    private Timer animationTimer;
    private List<PathStep> steps;
    private int currentStep;
    
    // UI组件
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JTextField treeInput;
    private JTextField targetInput;
    private JButton startButton, stepButton, resetButton, autoButton, homeButton;
    private JLabel statusLabel;
    private JLabel resultLabel;
    
    // 动画状态
    private int totalPaths;
    private TreeNode currentNode;
    private List<Integer> currentPath;
    private List<Integer> prefixSums;
    private String currentOperation;
    private int currentPathSum;
    private List<List<TreeNode>> validPaths;
    private List<TreeNode> currentNodePath; // 当前路径中的节点
    private Timer highlightTimer; // 高亮动画计时器
    private boolean highlightState = false; // 高亮状态切换
    
    // 树节点类
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode(int val) {
            this.val = val;
        }
    }
    
    // 路径步骤类
    private static class PathStep {
        TreeNode currentNode;
        List<Integer> path;
        List<Integer> prefixSums;
        int pathCount;
        String operation;
        boolean isBacktrack;
        List<List<TreeNode>> foundPaths;
        List<TreeNode> nodePath; // 当前路径中的节点
        
        PathStep(TreeNode node, List<Integer> path, List<TreeNode> nodePath, List<Integer> sums, int count, String op, boolean backtrack, List<List<TreeNode>> paths) {
            this.currentNode = node;
            this.path = new ArrayList<>(path);
            this.nodePath = new ArrayList<>(nodePath);
            this.prefixSums = new ArrayList<>(sums);
            this.pathCount = count;
            this.operation = op;
            this.isBacktrack = backtrack;
            this.foundPaths = new ArrayList<>();
            for (List<TreeNode> p : paths) {
                this.foundPaths.add(new ArrayList<>(p));
            }
        }
    }
    
    public Interval_04_12_N_PathSum_Animation() {
        setTitle("面试题 04.12. 求和路径 - 动画演示");
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
        targetSum = 8;
        isAnimating = false;
        steps = new ArrayList<>();
        currentStep = 0;
        totalPaths = 0;
        currentNode = null;
        currentPath = new ArrayList<>();
        currentNodePath = new ArrayList<>(); // 初始化当前节点路径
        prefixSums = new ArrayList<>();
        currentOperation = "准备开始";
        currentPathSum = 0;
        validPaths = new ArrayList<>();
        
        // 默认示例数据：[10,5,-3,3,2,null,11,3,-2,null,1]
        root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(-3);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(2);
        root.right.right = new TreeNode(11);
        root.left.left.left = new TreeNode(3);
        root.left.left.right = new TreeNode(-2);
        root.left.right.right = new TreeNode(1);
    }
    
    private void initComponents() {
        mainPanel = new JPanel();
        controlPanel = new JPanel();
        
        treeInput = new JTextField("10,5,-3,3,2,null,11,3,-2,null,1", 25);
        targetInput = new JTextField("8", 5);
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
        controlPanel.add(new JLabel("二叉树:"));
        controlPanel.add(treeInput);
        controlPanel.add(new JLabel("目标和:"));
        controlPanel.add(targetInput);
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
        mainPanel = new PathSumVisualizationPanel();
        
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
        animationTimer = new Timer(1500, e -> {
            stepExecution();
        });
        
        // 高亮动画计时器，用于闪烁效果
        highlightTimer = new Timer(500, e -> {
            highlightState = !highlightState;
            repaint();
        });
        highlightTimer.start();
    }
    
    private void startDemo() {
        parseInput();
        generateSteps();
        currentStep = 0;
        startButton.setEnabled(false);
        stepButton.setEnabled(true);
        statusLabel.setText("状态: 开始计算路径和");
        repaint();
    }
    
    private void parseInput() {
        try {
            String input = treeInput.getText().trim();
            String[] values = input.split(",");
            root = buildTree(values);
            
            targetSum = Integer.parseInt(targetInput.getText().trim());
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
        validPaths.clear();
        totalPaths = 0;
        
        List<Integer> path = new ArrayList<>();
        List<TreeNode> nodePath = new ArrayList<>();
        dfs(root, path, nodePath, 0);
    }
    
    private void dfs(TreeNode node, List<Integer> path, List<TreeNode> nodePath, int currentSum) {
        if (node == null) return;
        
        // 添加当前节点到路径
        path.add(node.val);
        nodePath.add(node);
        currentSum += node.val;
        
        steps.add(new PathStep(node, path, nodePath, new ArrayList<>(), totalPaths, 
            String.format("访问节点%d，当前路径和: %d", node.val, currentSum), false, validPaths));
        
        // 检查以当前节点结尾的所有路径
        int tempSum = 0;
        for (int i = path.size() - 1; i >= 0; i--) {
            tempSum += path.get(i);
            if (tempSum == targetSum) {
                totalPaths++;
                
                // 记录找到的路径
                List<TreeNode> foundPath = new ArrayList<>();
                for (int j = i; j < nodePath.size(); j++) {
                    foundPath.add(nodePath.get(j));
                }
                validPaths.add(foundPath);
                
                steps.add(new PathStep(node, path, nodePath, new ArrayList<>(), totalPaths, 
                    String.format("找到路径和为%d的路径，总数: %d", targetSum, totalPaths), false, validPaths));
            }
        }
        
        // 递归遍历左右子树
        if (node.left != null) {
            steps.add(new PathStep(node, path, nodePath, new ArrayList<>(), totalPaths, 
                "向左子树递归", false, validPaths));
            dfs(node.left, path, nodePath, currentSum);
        }
        
        if (node.right != null) {
            steps.add(new PathStep(node, path, nodePath, new ArrayList<>(), totalPaths, 
                "向右子树递归", false, validPaths));
            dfs(node.right, path, nodePath, currentSum);
        }
        
        // 回溯
        steps.add(new PathStep(node, path, nodePath, new ArrayList<>(), totalPaths, 
            String.format("回溯，移除节点%d", node.val), true, validPaths));
        
        path.remove(path.size() - 1);
        nodePath.remove(nodePath.size() - 1);
    }
    
    private void stepExecution() {
        if (currentStep < steps.size()) {
            PathStep step = steps.get(currentStep);
            currentNode = step.currentNode;
            currentPath = new ArrayList<>(step.path);
            currentNodePath = new ArrayList<>(step.nodePath); // 设置当前节点路径
            prefixSums = new ArrayList<>(step.prefixSums);
            totalPaths = step.pathCount;
            currentOperation = step.operation;
            validPaths = new ArrayList<>();
            for (List<TreeNode> p : step.foundPaths) {
                validPaths.add(new ArrayList<>(p));
            }
            
            // 计算当前路径和
            currentPathSum = 0;
            for (int val : currentPath) {
                currentPathSum += val;
            }
            
            statusLabel.setText("状态: " + currentOperation);
            
            if (currentStep == steps.size() - 1) {
                // 最后一步，显示最终结果
                resultLabel.setText("结果: 共找到 " + totalPaths + " 条路径");
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
        validPaths.clear();
        currentPath.clear();
        prefixSums.clear();
        
        startButton.setEnabled(true);
        stepButton.setEnabled(false);
        autoButton.setText("自动演示");
        statusLabel.setText("状态: 准备开始");
        resultLabel.setText("结果: 未开始");
        repaint();
    }
    
    private class PathSumVisualizationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            drawTree(g2d);
            drawCurrentPath(g2d);
            drawValidPaths(g2d);
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
            
            // 绘制连线，当前路径的连线用不同颜色和动画效果
            if (node.left != null) {
                if (isInCurrentPath(node) && isInCurrentPath(node.left)) {
                    // 当前路径连线：极其鲜艳的动态颜色和超粗线
                    if (highlightState) {
                        g2d.setColor(new Color(255, 0, 0)); // 纯红色闪烁
                    } else {
                        g2d.setColor(new Color(0, 100, 255)); // 鲜艳蓝色
                    }
                    g2d.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    
                    // 绘制主连线
                    g2d.drawLine(x, y, x - offset, y + 60);
                    
                    // 绘制方向箭头
                    drawArrow(g2d, x, y, x - offset, y + 60);
                } else {
                    g2d.setColor(new Color(220, 220, 220)); // 更浅的灰色
                    g2d.setStroke(new BasicStroke(1));
                    g2d.drawLine(x, y, x - offset, y + 60);
                }
                drawNode(g2d, node.left, x - offset, y + 60, offset / 2);
            }
            
            if (node.right != null) {
                if (isInCurrentPath(node) && isInCurrentPath(node.right)) {
                    // 当前路径连线：极其鲜艳的动态颜色和超粗线
                    if (highlightState) {
                        g2d.setColor(new Color(255, 0, 0)); // 纯红色闪烁
                    } else {
                        g2d.setColor(new Color(0, 100, 255)); // 鲜艳蓝色
                    }
                    g2d.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    
                    // 绘制主连线
                    g2d.drawLine(x, y, x + offset, y + 60);
                    
                    // 绘制方向箭头
                    drawArrow(g2d, x, y, x + offset, y + 60);
                } else {
                    g2d.setColor(new Color(220, 220, 220)); // 更浅的灰色
                    g2d.setStroke(new BasicStroke(1));
                    g2d.drawLine(x, y, x + offset, y + 60);
                }
                drawNode(g2d, node.right, x + offset, y + 60, offset / 2);
            }
            
            // 重置画笔
            g2d.setStroke(new BasicStroke(1));
            
            // 确定节点颜色和效果
            Color nodeColor = new Color(240, 240, 240); // 默认浅灰色
            Color borderColor = Color.BLACK;
            int borderWidth = 2;
            
            if (node == currentNode) {
                // 当前访问节点：极其鲜艳的红色闪烁效果
                if (highlightState) {
                    nodeColor = new Color(255, 0, 0); // 纯红色
                    borderColor = new Color(139, 0, 0); // 深红色边框
                } else {
                    nodeColor = new Color(255, 69, 0); // 橙红色
                    borderColor = new Color(178, 34, 34); // 火砖红边框
                }
                borderWidth = 8; // 更粗的边框
                
                // 绘制多层光晕效果
                g2d.setColor(new Color(255, 0, 0, 150)); // 强红色光晕
                g2d.fillOval(x - 35, y - 35, 70, 70);
                g2d.setColor(new Color(255, 100, 100, 100)); // 浅红色外圈
                g2d.fillOval(x - 30, y - 30, 60, 60);
                
            } else if (isInCurrentPath(node)) {
                // 当前路径中的节点：鲜艳的蓝色效果
                if (highlightState) {
                    nodeColor = new Color(0, 100, 255); // 鲜艳蓝色
                    borderColor = new Color(0, 0, 139); // 深蓝色边框
                } else {
                    nodeColor = new Color(30, 144, 255); // 道奇蓝
                    borderColor = new Color(0, 0, 205); // 中蓝色边框
                }
                borderWidth = 5; // 较粗边框
                
                // 绘制蓝色光晕
                g2d.setColor(new Color(0, 100, 255, 120)); // 蓝色光晕
                g2d.fillOval(x - 28, y - 28, 56, 56);
                g2d.setColor(new Color(100, 150, 255, 80)); // 浅蓝色外圈
                g2d.fillOval(x - 25, y - 25, 50, 50);
                
            } else if (isInValidPath(node)) {
                // 有效路径中的节点：鲜艳绿色
                nodeColor = new Color(0, 255, 0); // 纯绿色
                borderColor = new Color(0, 128, 0); // 深绿色边框
                borderWidth = 4;
                
                // 绘制绿色光晕
                g2d.setColor(new Color(0, 255, 0, 100));
                g2d.fillOval(x - 23, y - 23, 46, 46);
            }
            
            // 绘制节点主体
            g2d.setColor(nodeColor);
            g2d.fillOval(x - 20, y - 20, 40, 40);
            
            // 绘制边框
            g2d.setColor(borderColor);
            g2d.setStroke(new BasicStroke(borderWidth));
            g2d.drawOval(x - 20, y - 20, 40, 40);
            g2d.setStroke(new BasicStroke(1));
            
            // 绘制节点值
            if (node == currentNode) {
                // 当前节点：白色粗体文字，更大字号
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Arial", Font.BOLD, 20));
            } else if (isInCurrentPath(node)) {
                // 路径节点：白色文字
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Arial", Font.BOLD, 18));
            } else {
                // 普通节点：黑色文字
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Arial", Font.BOLD, 16));
            }
            
            FontMetrics fm = g2d.getFontMetrics();
            String value = String.valueOf(node.val);
            int textX = x - fm.stringWidth(value) / 2;
            int textY = y + fm.getAscent() / 2;
            g2d.drawString(value, textX, textY);
            
            // 为当前节点添加更醒目的步骤指示器
            if (node == currentNode) {
                g2d.setColor(new Color(255, 255, 0)); // 黄色背景
                g2d.fillRoundRect(x - 25, y - 45, 50, 20, 10, 10);
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Arial", Font.BOLD, 14));
                g2d.drawString("正在访问", x - 22, y - 32);
            } else if (isInCurrentPath(node)) {
                g2d.setColor(new Color(0, 100, 255, 150)); // 半透明蓝色背景
                g2d.fillRoundRect(x - 20, y - 40, 40, 15, 8, 8);
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Arial", Font.BOLD, 12));
                g2d.drawString("路径中", x - 18, y - 30);
            }
        }
        
        // 绘制箭头指示路径方向
        private void drawArrow(Graphics2D g2d, int x1, int y1, int x2, int y2) {
            // 计算箭头位置（在连线的中点附近）
            int midX = (x1 + x2) / 2;
            int midY = (y1 + y2) / 2;
            
            // 计算方向向量
            double dx = x2 - x1;
            double dy = y2 - y1;
            double length = Math.sqrt(dx * dx + dy * dy);
            
            if (length > 0) {
                // 标准化方向向量
                dx /= length;
                dy /= length;
                
                // 箭头大小
                int arrowLength = 12;
                double arrowAngle = Math.PI / 6; // 30度
                
                // 计算箭头的两个端点
                int arrowX1 = (int) (midX - arrowLength * Math.cos(Math.atan2(dy, dx) - arrowAngle));
                int arrowY1 = (int) (midY - arrowLength * Math.sin(Math.atan2(dy, dx) - arrowAngle));
                int arrowX2 = (int) (midX - arrowLength * Math.cos(Math.atan2(dy, dx) + arrowAngle));
                int arrowY2 = (int) (midY - arrowLength * Math.sin(Math.atan2(dy, dx) + arrowAngle));
                
                // 绘制箭头
                g2d.setStroke(new BasicStroke(3));
                g2d.drawLine(midX, midY, arrowX1, arrowY1);
                g2d.drawLine(midX, midY, arrowX2, arrowY2);
            }
        }
        
        private boolean isInCurrentPath(TreeNode node) {
            if (currentNodePath == null) return false;
            return currentNodePath.contains(node);
        }
        
        private boolean isInValidPath(TreeNode node) {
            for (List<TreeNode> path : validPaths) {
                if (path.contains(node)) {
                    return true;
                }
            }
            return false;
        }
        
        private void drawCurrentPath(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g2d.drawString("当前路径:", 20, 350);
            
            if (!currentPath.isEmpty()) {
                int x = 120;
                int y = 350;
                g2d.setFont(new Font("Arial", Font.BOLD, 16));
                
                for (int i = 0; i < currentPath.size(); i++) {
                    // 绘制路径节点背景
                    if (i == currentPath.size() - 1) {
                        // 最新添加的节点用闪烁效果
                        if (highlightState) {
                            g2d.setColor(new Color(255, 200, 200));
                        } else {
                            g2d.setColor(new Color(255, 150, 150));
                        }
                    } else {
                        g2d.setColor(new Color(200, 220, 255));
                    }
                    g2d.fillRoundRect(x - 5, y - 18, 30, 25, 8, 8);
                    
                    // 绘制节点值
                    g2d.setColor(Color.BLACK);
                    g2d.drawString(String.valueOf(currentPath.get(i)), x, y);
                    
                    if (i < currentPath.size() - 1) {
                        g2d.setColor(new Color(100, 100, 100));
                        g2d.drawString(" → ", x + 25, y);
                    }
                    
                    x += 50;
                }
                
                // 绘制路径和信息
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                String sumText = "路径和: " + currentPathSum;
                if (currentPathSum == targetSum) {
                    g2d.setColor(new Color(0, 150, 0));
                    sumText += " ✓ 匹配目标值!";
                }
                g2d.drawString(sumText, 20, 380);
                
                // 绘制操作信息
                g2d.setColor(new Color(100, 100, 100));
                g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                g2d.drawString(currentOperation != null ? currentOperation : "", 20, 400);
            }
        }
        
        private void drawValidPaths(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g2d.drawString("找到的有效路径 (和为" + targetSum + "):", 20, 420);
            
            int y = 450;
            g2d.setFont(new Font("Arial", Font.PLAIN, 14));
            
            for (int i = 0; i < validPaths.size() && i < 5; i++) {
                List<TreeNode> path = validPaths.get(i);
                int x = 20;
                
                g2d.setColor(Color.GREEN);
                g2d.drawString("路径" + (i + 1) + ": ", x, y);
                x += 60;
                
                for (int j = 0; j < path.size(); j++) {
                    g2d.drawString(String.valueOf(path.get(j).val), x, y);
                    
                    if (j < path.size() - 1) {
                        g2d.drawString(" → ", x + 20, y);
                    }
                    
                    x += 40;
                }
                
                y += 25;
            }
            
            if (validPaths.size() > 5) {
                g2d.setColor(Color.GRAY);
                g2d.drawString("... 还有 " + (validPaths.size() - 5) + " 条路径", 20, y);
            }
        }
        
        private void drawAlgorithmInfo(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            
            int y = getHeight() - 120;
            g2d.drawString("算法说明：", 20, y);
            g2d.drawString("1. 使用深度优先搜索遍历所有节点", 20, y + 20);
            g2d.drawString("2. 对于每个节点，检查以该节点结尾的所有路径", 20, y + 40);
            g2d.drawString("3. 路径可以从任意节点开始，到任意节点结束", 20, y + 60);
            g2d.drawString("4. 使用回溯法确保正确处理所有可能的路径", 20, y + 80);
            
            // 显示当前操作
            if (currentOperation != null) {
                g2d.setColor(Color.BLUE);
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
                g2d.drawString("当前操作: " + currentOperation, 20, 30);
            }
            
            // 显示统计信息
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g2d.drawString("已找到路径数: " + totalPaths, 500, 30);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Interval_04_12_N_PathSum_Animation().setVisible(true);
        });
    }
}