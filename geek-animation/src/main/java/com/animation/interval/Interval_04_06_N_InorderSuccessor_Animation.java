package com.animation.interval;

import javax.swing.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 面试题 04.06. 后继者 - 动画演示
 * 演示在二叉搜索树中查找指定节点的中序后继的过程
 * 
 * 设计文档：
 * 1. 功能需求：可视化中序后继查找算法的执行过程
 * 2. 技术架构：基于Swing的动画演示系统
 * 3. 核心算法：利用BST性质查找中序后继
 * 4. 边界条件：目标节点不存在、没有后继等情况
 * 5. 性能考虑：时间复杂度O(h)，空间复杂度O(1)
 */
public class Interval_04_06_N_InorderSuccessor_Animation extends JFrame {
    private TreeNode root;
    private TreeNode target;
    private boolean isAnimating;
    private Timer animationTimer;
    private List<SearchStep> steps;
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
    private TreeNode successor;
    private TreeNode currentNode;
    private String currentOperation;
    private boolean found;
    
    // 树节点类
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode parent; // 用于演示
        
        TreeNode(int val) {
            this.val = val;
        }
    }
    
    // 搜索步骤类
    private static class SearchStep {
        TreeNode currentNode;
        TreeNode candidateSuccessor;
        String operation;
        String direction;
        boolean isFound;
        
        SearchStep(TreeNode current, TreeNode candidate, String operation, String direction, boolean found) {
            this.currentNode = current;
            this.candidateSuccessor = candidate;
            this.operation = operation;
            this.direction = direction;
            this.isFound = found;
        }
    }
    
    public Interval_04_06_N_InorderSuccessor_Animation() {
        setTitle("面试题 04.06. 后继者 - 动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 800);
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
        successor = null;
        currentNode = null;
        currentOperation = "准备开始";
        found = false;
        
        // 默认示例数据：[5,3,6,2,4,null,null,1]
        root = new TreeNode(5);
        root.left = new TreeNode(3);
        root.right = new TreeNode(6);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(4);
        root.left.left.left = new TreeNode(1);
        
        // 设置父节点指针
        setupParentPointers(root, null);
        
        // 默认查找节点3的后继
        target = root.left; // 节点3
    }
    
    private void setupParentPointers(TreeNode node, TreeNode parent) {
        if (node == null) return;
        node.parent = parent;
        setupParentPointers(node.left, node);
        setupParentPointers(node.right, node);
    }
    
    private void initComponents() {
        mainPanel = new JPanel();
        controlPanel = new JPanel();
        
        treeInput = new JTextField("5,3,6,2,4,null,null,1", 20);
        targetInput = new JTextField("3", 5);
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
        controlPanel.add(new JLabel("目标节点:"));
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
        mainPanel = new SuccessorVisualizationPanel();
        
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
        statusLabel.setText("状态: 开始查找中序后继");
        SwingUtilities.invokeLater(() -> {
            if (mainPanel != null) {
                SwingUtilities.invokeLater(() -> mainPanel.repaint());
            }
        });
    }
    
    private void parseInput() {
        try {
            String input = treeInput.getText().trim();
            String[] values = input.split(",");
            root = buildTree(values);
            setupParentPointers(root, null);
            
            int targetVal = Integer.parseInt(targetInput.getText().trim());
            target = findNode(root, targetVal);
            
            if (target == null) {
                JOptionPane.showMessageDialog(this, "目标节点不存在，使用默认示例");
                initData();
            }
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
    
    private TreeNode findNode(TreeNode root, int val) {
        if (root == null) return null;
        if (root.val == val) return root;
        
        TreeNode left = findNode(root.left, val);
        if (left != null) return left;
        
        return findNode(root.right, val);
    }
    
    private void generateSteps() {
        steps.clear();
        
        steps.add(new SearchStep(target, null, 
            String.format("开始查找节点%d的中序后继", target.val), "", false));
        
        TreeNode result = inorderSuccessor(target);
        
        if (result != null) {
            steps.add(new SearchStep(result, result, 
                String.format("找到中序后继: %d", result.val), "", true));
        } else {
            steps.add(new SearchStep(null, null, "没有中序后继", "", false));
        }
    }
    
    private TreeNode inorderSuccessor(TreeNode p) {
        if (p == null) return null;
        
        // 情况1：如果节点有右子树，后继是右子树的最左节点
        if (p.right != null) {
            steps.add(new SearchStep(p, null, 
                String.format("节点%d有右子树，查找右子树的最左节点", p.val), "right", false));
            
            TreeNode current = p.right;
            steps.add(new SearchStep(current, current, 
                String.format("进入右子树，当前节点: %d", current.val), "right", false));
            
            while (current.left != null) {
                current = current.left;
                steps.add(new SearchStep(current, current, 
                    String.format("继续向左，当前节点: %d", current.val), "left", false));
            }
            
            steps.add(new SearchStep(current, current, 
                String.format("找到最左节点: %d", current.val), "", true));
            
            return current;
        }
        
        // 情况2：如果节点没有右子树，向上查找第一个左转的祖先
        steps.add(new SearchStep(p, null, 
            String.format("节点%d没有右子树，向上查找祖先", p.val), "up", false));
        
        TreeNode current = p;
        TreeNode parent = p.parent;
        
        while (parent != null && current == parent.right) {
            steps.add(new SearchStep(parent, null, 
                String.format("节点%d是父节点%d的右子节点，继续向上", current.val, parent.val), "up", false));
            current = parent;
            parent = parent.parent;
        }
        
        if (parent != null) {
            steps.add(new SearchStep(parent, parent, 
                String.format("找到第一个左转祖先: %d", parent.val), "up", true));
        } else {
            steps.add(new SearchStep(null, null, "到达根节点，没有后继", "up", false));
        }
        
        return parent;
    }
    
    private void stepExecution() {
        if (currentStep < steps.size()) {
            SearchStep step = steps.get(currentStep);
            currentNode = step.currentNode;
            successor = step.candidateSuccessor;
            currentOperation = step.operation;
            found = step.isFound;
            
            statusLabel.setText("状态: " + currentOperation);
            
            if (currentStep == steps.size() - 1) {
                // 最后一步，显示最终结果
                if (successor != null) {
                    resultLabel.setText("结果: 中序后继是 " + successor.val);
                } else {
                    resultLabel.setText("结果: 没有中序后继");
                }
                stepButton.setEnabled(false);
                if (animationTimer.isRunning()) {
                    animationTimer.stop();
                    autoButton.setText("自动演示");
                }
            }
            
            currentStep++;
            SwingUtilities.invokeLater(() -> {
                if (mainPanel != null) {
                    SwingUtilities.invokeLater(() -> mainPanel.repaint());
                }
            });
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
        successor = null;
        
        startButton.setEnabled(true);
        stepButton.setEnabled(false);
        autoButton.setText("自动演示");
        statusLabel.setText("状态: 准备开始");
        resultLabel.setText("结果: 未开始");
        SwingUtilities.invokeLater(() -> {
            if (mainPanel != null) {
                SwingUtilities.invokeLater(() -> mainPanel.repaint());
            }
        });
    }
    
    private class SuccessorVisualizationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            drawTree(g2d);
            drawInorderSequence(g2d);
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
            if (node == target) {
                nodeColor = Color.ORANGE; // 目标节点
            } else if (node == currentNode) {
                nodeColor = Color.YELLOW; // 当前访问节点
            } else if (node == successor && found) {
                nodeColor = Color.GREEN; // 找到的后继节点
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
        }
        
        private void drawInorderSequence(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g2d.drawString("中序遍历序列:", 20, 350);
            
            List<Integer> inorder = new ArrayList<>();
            inorderTraversal(root, inorder);
            
            int x = 20;
            int y = 380;
            g2d.setFont(new Font("Arial", Font.BOLD, 18));
            
            for (int i = 0; i < inorder.size(); i++) {
                int val = inorder.get(i);
                
                Color color = Color.BLACK;
                if (target != null && val == target.val) {
                    color = Color.ORANGE;
                } else if (successor != null && val == successor.val && found) {
                    color = Color.GREEN;
                }
                
                g2d.setColor(color);
                g2d.drawString(String.valueOf(val), x, y);
                
                if (i < inorder.size() - 1) {
                    g2d.setColor(Color.BLACK);
                    g2d.drawString(" → ", x + 20, y);
                }
                
                x += 50;
            }
            
            // 标注
            g2d.setColor(Color.ORANGE);
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
            g2d.drawString("目标节点", 20, 410);
            
            g2d.setColor(Color.GREEN);
            g2d.drawString("中序后继", 100, 410);
        }
        
        private void inorderTraversal(TreeNode node, List<Integer> result) {
            if (node == null) return;
            inorderTraversal(node.left, result);
            result.add(node.val);
            inorderTraversal(node.right, result);
        }
        
        private void drawAlgorithmInfo(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            
            int y = getHeight() - 140;
            g2d.drawString("算法说明：", 20, y);
            g2d.drawString("1. 如果节点有右子树，后继是右子树的最左节点", 20, y + 20);
            g2d.drawString("2. 如果节点没有右子树，向上查找第一个\"左转\"的祖先", 20, y + 40);
            g2d.drawString("3. 如果找不到这样的祖先，说明该节点没有后继", 20, y + 60);
            g2d.drawString("4. 中序后继是中序遍历序列中的下一个节点", 20, y + 80);
            
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
            new Interval_04_06_N_InorderSuccessor_Animation().setVisible(true);
        });
    }
}