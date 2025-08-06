/**
 * copyright 2024/12/19
 */
package com.animation.tree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * NO.112 路径总和动画演示
 * 演示如何判断二叉树中是否存在根节点到叶子节点的路径，使得路径上所有节点值相加等于目标和
 */
public class NO112_E_HasPathSum_Animation extends JPanel {
    
    // 静态内部类定义树节点
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode parent;
        int x, y; // 绘制坐标
        
        TreeNode(int val) {
            this.val = val;
        }
        
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
            if (left != null) left.parent = this;
            if (right != null) right.parent = this;
        }
    }
    
    // 动画步骤类
    private static class PathStep {
        TreeNode currentNode;
        List<TreeNode> currentPath;
        int currentSum;
        int targetSum;
        String message;
        boolean isLeaf;
        boolean found;
        boolean isBacktrack;
        
        PathStep(TreeNode node, List<TreeNode> path, int sum, int target, 
                String msg, boolean leaf, boolean found, boolean backtrack) {
            this.currentNode = node;
            this.currentPath = new ArrayList<>(path);
            this.currentSum = sum;
            this.targetSum = target;
            this.message = msg;
            this.isLeaf = leaf;
            this.found = found;
            this.isBacktrack = backtrack;
        }
    }
    
    // GUI组件
    private TreeNode root;
    private int targetSum;
    private List<PathStep> steps;
    private int currentStep;
    private javax.swing.Timer animationTimer;
    private boolean animationRunning;
    private boolean pathFound;
    private List<TreeNode> foundPath;
    
    // 绘制相关
    private final int NODE_RADIUS = 25;
    private final int LEVEL_HEIGHT = 80;
    private final Color NODE_COLOR = new Color(173, 216, 230);
    private final Color CURRENT_NODE_COLOR = new Color(255, 165, 0);
    private final Color PATH_NODE_COLOR = new Color(144, 238, 144);
    private final Color FOUND_PATH_COLOR = new Color(50, 205, 50);
    private final Color BACKTRACK_COLOR = new Color(255, 182, 193);
    
    public NO112_E_HasPathSum_Animation() {
        initializeGUI();
        buildTestCase();
        generateSteps();
    }
    
    private void initializeGUI() {
        setPreferredSize(new Dimension(1000, 700));
        setBackground(Color.WHITE);
        
        steps = new ArrayList<>();
        currentStep = 0;
        animationRunning = false;
        pathFound = false;
        foundPath = new ArrayList<>();
        
        // 创建控制面板
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        
        JButton startButton = new JButton("开始动画");
        JButton pauseButton = new JButton("暂停");
        JButton resetButton = new JButton("重置");
        JButton stepButton = new JButton("单步执行");
        
        startButton.addActionListener(e -> startAnimation());
        pauseButton.addActionListener(e -> pauseAnimation());
        resetButton.addActionListener(e -> resetAnimation());
        stepButton.addActionListener(e -> stepAnimation());
        
        controlPanel.add(startButton);
        controlPanel.add(pauseButton);
        controlPanel.add(resetButton);
        controlPanel.add(stepButton);
        
        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.SOUTH);
        
        // 设置动画定时器
        animationTimer = new javax.swing.Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentStep < steps.size()) {
                    currentStep++;
                    SwingUtilities.invokeLater(() -> NO112_E_HasPathSum_Animation.this.repaint());
                } else {
                    animationTimer.stop();
                    animationRunning = false;
                }
            }
        });
    }
    
    private void buildTestCase() {
        // 构建测试用例：[5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1]
        root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(8);
        
        root.left.left = new TreeNode(11);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(2);
        
        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);
        root.right.right.right = new TreeNode(1);
        
        // 设置父节点关系
        assignParents(root);
        
        targetSum = 22;
    }
    
    private void assignParents(TreeNode node) {
        if (node == null) return;
        if (node.left != null) {
            node.left.parent = node;
            assignParents(node.left);
        }
        if (node.right != null) {
            node.right.parent = node;
            assignParents(node.right);
        }
    }
    
    private void generateSteps() {
        steps.clear();
        pathFound = false;
        foundPath.clear();
        
        List<TreeNode> path = new ArrayList<>();
        dfs(root, path, 0);
    }
    
    private boolean dfs(TreeNode node, List<TreeNode> path, int currentSum) {
        if (node == null) return false;
        
        // 添加当前节点到路径
        path.add(node);
        currentSum += node.val;
        
        // 记录访问当前节点的步骤
        steps.add(new PathStep(node, path, currentSum, targetSum,
            String.format("访问节点 %d，当前路径和: %d", node.val, currentSum),
            false, false, false));
        
        // 检查是否为叶子节点
        boolean isLeaf = (node.left == null && node.right == null);
        if (isLeaf) {
            if (currentSum == targetSum) {
                // 找到目标路径
                steps.add(new PathStep(node, path, currentSum, targetSum,
                    String.format("找到目标路径！叶子节点 %d，路径和 %d = 目标和 %d", 
                    node.val, currentSum, targetSum),
                    true, true, false));
                pathFound = true;
                foundPath = new ArrayList<>(path);
                path.remove(path.size() - 1); // 回溯
                return true;
            } else {
                // 叶子节点但路径和不匹配
                steps.add(new PathStep(node, path, currentSum, targetSum,
                    String.format("叶子节点 %d，路径和 %d ≠ 目标和 %d，回溯", 
                    node.val, currentSum, targetSum),
                    true, false, false));
            }
        }
        
        // 递归左子树
        if (node.left != null) {
            if (dfs(node.left, path, currentSum)) {
                path.remove(path.size() - 1); // 回溯
                return true;
            }
        }
        
        // 递归右子树
        if (node.right != null) {
            if (dfs(node.right, path, currentSum)) {
                path.remove(path.size() - 1); // 回溯
                return true;
            }
        }
        
        // 回溯
        if (!isLeaf) {
            steps.add(new PathStep(node, path, currentSum, targetSum,
                String.format("从节点 %d 回溯", node.val),
                false, false, true));
        }
        
        path.remove(path.size() - 1);
        return false;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制标题
        drawTitle(g2d);
        
        // 绘制问题描述
        drawProblemDescription(g2d);
        
        // 绘制二叉树
        if (root != null) {
            assignCoordinates();
            drawTree(g2d);
        }
        
        // 绘制当前步骤信息
        drawStepInfo(g2d);
        
        // 绘制算法说明
        drawAlgorithmInfo(g2d);
        
        // 绘制结果
        drawResult(g2d);
    }
    
    private void drawTitle(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 24));
        g2d.setColor(Color.BLACK);
        String title = "NO.112 路径总和 - 深度优先搜索动画演示";
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(title)) / 2;
        g2d.drawString(title, x, 30);
    }
    
    private void drawProblemDescription(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.DARK_GRAY);
        String desc = String.format("目标：判断是否存在根到叶路径，使得路径和等于 %d", targetSum);
        g2d.drawString(desc, 20, 60);
    }
    
    private void assignCoordinates() {
        if (root == null) return;
        assignCoordinates(root, getWidth() / 2, 100, getWidth() / 4);
    }
    
    private void assignCoordinates(TreeNode node, int x, int y, int offset) {
        if (node == null) return;
        
        node.x = x;
        node.y = y;
        
        if (node.left != null) {
            assignCoordinates(node.left, x - offset, y + LEVEL_HEIGHT, offset / 2);
        }
        if (node.right != null) {
            assignCoordinates(node.right, x + offset, y + LEVEL_HEIGHT, offset / 2);
        }
    }
    
    private void drawTree(Graphics2D g2d) {
        if (root == null) return;
        
        // 绘制边
        drawEdges(g2d, root);
        
        // 绘制节点
        drawNodes(g2d, root);
    }
    
    private void drawEdges(Graphics2D g2d, TreeNode node) {
        if (node == null) return;
        
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.GRAY);
        
        if (node.left != null) {
            g2d.drawLine(node.x, node.y, node.left.x, node.left.y);
            drawEdges(g2d, node.left);
        }
        if (node.right != null) {
            g2d.drawLine(node.x, node.y, node.right.x, node.right.y);
            drawEdges(g2d, node.right);
        }
    }
    
    private void drawNodes(Graphics2D g2d, TreeNode node) {
        if (node == null) return;
        
        // 确定节点颜色
        Color nodeColor = NODE_COLOR;
        if (currentStep > 0 && currentStep <= steps.size()) {
            PathStep step = steps.get(currentStep - 1);
            
            if (pathFound && foundPath.contains(node)) {
                nodeColor = FOUND_PATH_COLOR;
            } else if (step.currentNode == node) {
                if (step.isBacktrack) {
                    nodeColor = BACKTRACK_COLOR;
                } else {
                    nodeColor = CURRENT_NODE_COLOR;
                }
            } else if (step.currentPath.contains(node)) {
                nodeColor = PATH_NODE_COLOR;
            }
        }
        
        // 绘制节点
        g2d.setColor(nodeColor);
        g2d.fillOval(node.x - NODE_RADIUS, node.y - NODE_RADIUS, 
                    NODE_RADIUS * 2, NODE_RADIUS * 2);
        
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(node.x - NODE_RADIUS, node.y - NODE_RADIUS, 
                    NODE_RADIUS * 2, NODE_RADIUS * 2);
        
        // 绘制节点值
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g2d.getFontMetrics();
        String value = String.valueOf(node.val);
        int textX = node.x - fm.stringWidth(value) / 2;
        int textY = node.y + fm.getAscent() / 2;
        g2d.setColor(Color.BLACK);
        g2d.drawString(value, textX, textY);
        
        // 递归绘制子节点
        drawNodes(g2d, node.left);
        drawNodes(g2d, node.right);
    }
    
    private void drawStepInfo(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.BLACK);
        
        int y = 400;
        g2d.drawString("当前步骤信息:", 20, y);
        
        if (currentStep > 0 && currentStep <= steps.size()) {
            PathStep step = steps.get(currentStep - 1);
            y += 25;
            g2d.drawString("步骤 " + currentStep + ": " + step.message, 20, y);
            
            y += 25;
            StringBuilder pathStr = new StringBuilder("当前路径: ");
            for (int i = 0; i < step.currentPath.size(); i++) {
                if (i > 0) pathStr.append(" → ");
                pathStr.append(step.currentPath.get(i).val);
            }
            g2d.drawString(pathStr.toString(), 20, y);
            
            y += 25;
            g2d.drawString("当前路径和: " + step.currentSum, 20, y);
        }
    }
    
    private void drawAlgorithmInfo(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        g2d.setColor(Color.BLUE);
        
        int x = 500;
        int y = 400;
        
        g2d.drawString("算法步骤:", x, y);
        y += 20;
        g2d.drawString("1. 从根节点开始深度优先搜索", x, y);
        y += 15;
        g2d.drawString("2. 将当前节点加入路径，累加路径和", x, y);
        y += 15;
        g2d.drawString("3. 如果是叶子节点，检查路径和是否等于目标和", x, y);
        y += 15;
        g2d.drawString("4. 如果不是叶子节点，递归搜索左右子树", x, y);
        y += 15;
        g2d.drawString("5. 回溯时移除当前节点", x, y);
        
        // 颜色说明
        y += 30;
        g2d.drawString("颜色说明:", x, y);
        y += 20;
        
        g2d.setColor(CURRENT_NODE_COLOR);
        g2d.fillOval(x, y - 10, 15, 15);
        g2d.setColor(Color.BLUE);
        g2d.drawString("当前访问节点", x + 20, y);
        
        y += 20;
        g2d.setColor(PATH_NODE_COLOR);
        g2d.fillOval(x, y - 10, 15, 15);
        g2d.setColor(Color.BLUE);
        g2d.drawString("当前路径节点", x + 20, y);
        
        y += 20;
        g2d.setColor(FOUND_PATH_COLOR);
        g2d.fillOval(x, y - 10, 15, 15);
        g2d.setColor(Color.BLUE);
        g2d.drawString("找到的目标路径", x + 20, y);
        
        y += 20;
        g2d.setColor(BACKTRACK_COLOR);
        g2d.fillOval(x, y - 10, 15, 15);
        g2d.setColor(Color.BLUE);
        g2d.drawString("回溯节点", x + 20, y);
    }
    
    private void drawResult(Graphics2D g2d) {
        if (currentStep >= steps.size()) {
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            if (pathFound) {
                g2d.setColor(new Color(0, 128, 0));
                g2d.drawString("结果: 找到路径和等于 " + targetSum + " 的路径！", 20, 600);
                
                StringBuilder pathStr = new StringBuilder("目标路径: ");
                for (int i = 0; i < foundPath.size(); i++) {
                    if (i > 0) pathStr.append(" → ");
                    pathStr.append(foundPath.get(i).val);
                }
                g2d.drawString(pathStr.toString(), 20, 625);
            } else {
                g2d.setColor(Color.RED);
                g2d.drawString("结果: 不存在路径和等于 " + targetSum + " 的路径", 20, 600);
            }
            
            // 复杂度信息
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            g2d.setColor(Color.BLACK);
            g2d.drawString("时间复杂度: O(N) - 最坏情况下访问所有节点", 20, 650);
            g2d.drawString("空间复杂度: O(H) - H为树的高度，递归栈空间", 20, 670);
        }
    }
    
    private void startAnimation() {
        if (!animationRunning) {
            animationRunning = true;
            animationTimer.start();
        }
    }
    
    private void pauseAnimation() {
        if (animationRunning) {
            animationTimer.stop();
            animationRunning = false;
        }
    }
    
    private void resetAnimation() {
        animationTimer.stop();
        animationRunning = false;
        currentStep = 0;
        generateSteps();
        SwingUtilities.invokeLater(() -> repaint());
    }
    
    private void stepAnimation() {
        if (currentStep < steps.size()) {
            currentStep++;
            SwingUtilities.invokeLater(() -> repaint());
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("NO.112 路径总和动画演示");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new NO112_E_HasPathSum_Animation());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}