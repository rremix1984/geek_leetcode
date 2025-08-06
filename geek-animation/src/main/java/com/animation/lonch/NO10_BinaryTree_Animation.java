/**
 * copyright 2024-2025
 * n阶满二叉树创建算法动画演示
 * 基于 com.lonch.NO10_BinaryTree 实现
 */
package com.animation.lonch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * n阶满二叉树创建算法动画演示
 * 展示如何递归创建满二叉树，数据值按顺序 A-Z 循环赋值
 * 
 * @author wangxiaozhe
 */
public class NO10_BinaryTree_Animation extends JFrame {
    
    // 树节点类
    static class Node<E> {
        public E data;
        public Node<E> parent, left, right;
        public boolean isNewlyCreated = false; // 标记是否为新创建的节点
        
        public Node(E data) {
            this.data = data;
            this.isNewlyCreated = true;
        }
    }
    
    // 动画相关常量
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final int NODE_RADIUS = 25;
    private static final Color NORMAL_COLOR = Color.LIGHT_GRAY;
    private static final Color NEW_NODE_COLOR = Color.YELLOW;
    private static final Color CURRENT_COLOR = Color.RED;
    private static final Color COMPLETED_COLOR = Color.GREEN;
    
    // 动画步骤类
    private static class AnimationStep {
        Node<Character> currentNode;
        String operation;
        int depth;
        int currentIndex;
        Node<Character> treeRoot;
        
        AnimationStep(Node<Character> currentNode, String operation, 
                     int depth, int currentIndex, Node<Character> treeRoot) {
            this.currentNode = currentNode;
            this.operation = operation;
            this.depth = depth;
            this.currentIndex = currentIndex;
            this.treeRoot = copyTree(treeRoot); // 深拷贝当前树状态
        }
        
        // 深拷贝树结构
        private Node<Character> copyTree(Node<Character> node) {
            if (node == null) return null;
            
            Node<Character> copy = new Node<>(node.data);
            copy.isNewlyCreated = node.isNewlyCreated;
            
            if (node.left != null) {
                copy.left = copyTree(node.left);
                copy.left.parent = copy;
            }
            
            if (node.right != null) {
                copy.right = copyTree(node.right);
                copy.right.parent = copy;
            }
            
            return copy;
        }
    }
    
    // GUI组件
    private JPanel drawingPanel;
    private JButton startButton, resetButton, nextButton, prevButton;
    private JSpinner depthSpinner;
    private JLabel statusLabel, infoLabel;
    private javax.swing.Timer animationTimer;
    
    // 动画状态
    private Node<Character> root;
    private List<AnimationStep> animationSteps;
    private int currentStepIndex;
    private boolean isAnimating;
    private int targetDepth;
    
    public NO10_BinaryTree_Animation() {
        initializeGUI();
        resetAnimation();
    }
    
    private void initializeGUI() {
        setTitle("n阶满二叉树创建算法动画演示");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // 创建主面板
        setLayout(new BorderLayout());
        
        // 创建绘图面板
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawAnimation(g);
            }
        };
        drawingPanel.setBackground(Color.WHITE);
        add(drawingPanel, BorderLayout.CENTER);
        
        // 创建控制面板
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.SOUTH);
        
        // 创建信息面板
        JPanel infoPanel = createInfoPanel();
        add(infoPanel, BorderLayout.NORTH);
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        // 深度选择
        panel.add(new JLabel("树的深度:"));
        depthSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));
        depthSpinner.addChangeListener(e -> {
            if (!isAnimating) {
                resetAnimation();
            }
        });
        panel.add(depthSpinner);
        
        // 控制按钮
        startButton = new JButton("开始创建");
        startButton.addActionListener(this::startAnimation);
        panel.add(startButton);
        
        resetButton = new JButton("重置");
        resetButton.addActionListener(e -> resetAnimation());
        panel.add(resetButton);
        
        prevButton = new JButton("上一步");
        prevButton.addActionListener(e -> previousStep());
        panel.add(prevButton);
        
        nextButton = new JButton("下一步");
        nextButton.addActionListener(e -> nextStep());
        panel.add(nextButton);
        
        return panel;
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        statusLabel = new JLabel("准备创建满二叉树");
        statusLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        panel.add(statusLabel, BorderLayout.WEST);
        
        infoLabel = new JLabel("节点数据按 A-Z 循环赋值");
        infoLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        panel.add(infoLabel, BorderLayout.EAST);
        
        return panel;
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制算法信息
        drawAlgorithmInfo(g2d);
        
        // 绘制当前步骤信息
        drawStepInfo(g2d);
        
        // 绘制树
        if (animationSteps.size() > 0 && currentStepIndex >= 0 && currentStepIndex < animationSteps.size()) {
            AnimationStep step = animationSteps.get(currentStepIndex);
            if (step.treeRoot != null) {
                drawTree(g2d, step.treeRoot, getWidth() / 2, 120, getWidth() / 4);
            }
        }
    }
    
    private void drawTree(Graphics2D g2d, Node<Character> node, int x, int y, int xOffset) {
        if (node == null) return;
        
        // 绘制连线到子节点
        if (node.left != null) {
            int childX = x - xOffset;
            int childY = y + 80;
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(x, y, childX, childY);
            drawTree(g2d, node.left, childX, childY, xOffset / 2);
        }
        
        if (node.right != null) {
            int childX = x + xOffset;
            int childY = y + 80;
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(x, y, childX, childY);
            drawTree(g2d, node.right, childX, childY, xOffset / 2);
        }
        
        // 绘制节点
        Color nodeColor = NORMAL_COLOR;
        if (node.isNewlyCreated) {
            nodeColor = NEW_NODE_COLOR;
        }
        
        // 检查是否为当前操作的节点
        if (animationSteps.size() > 0 && currentStepIndex >= 0 && currentStepIndex < animationSteps.size()) {
            AnimationStep step = animationSteps.get(currentStepIndex);
            if (step.currentNode != null && node.data.equals(step.currentNode.data)) {
                nodeColor = CURRENT_COLOR;
            }
        }
        
        g2d.setColor(nodeColor);
        g2d.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
        
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
        
        // 绘制节点值
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g2d.getFontMetrics();
        String text = node.data.toString();
        int textX = x - fm.stringWidth(text) / 2;
        int textY = y + fm.getAscent() / 2;
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, textX, textY);
    }
    
    private void drawAlgorithmInfo(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        
        String[] info = {
            "n阶满二叉树创建算法:",
            "• 递归创建左右子树",
            "• 节点数据按 A-Z 循环赋值", 
            "• 每个节点的索引为 2*parent+1 (左) 和 2*parent+2 (右)",
            "",
            "当前深度: " + targetDepth
        };
        
        int startY = getHeight() - 150;
        for (int i = 0; i < info.length; i++) {
            g2d.drawString(info[i], 20, startY + i * 20);
        }
    }
    
    private void drawStepInfo(Graphics2D g2d) {
        if (animationSteps.isEmpty() || currentStepIndex < 0 || currentStepIndex >= animationSteps.size()) {
            return;
        }
        
        AnimationStep step = animationSteps.get(currentStepIndex);
        
        g2d.setColor(Color.BLUE);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        
        String stepInfo = String.format("步骤 %d/%d: %s", 
            currentStepIndex + 1, animationSteps.size(), step.operation);
        g2d.drawString(stepInfo, getWidth() - 500, getHeight() - 120);
        
        String depthInfo = String.format("当前深度: %d, 节点索引: %d", 
            step.depth, step.currentIndex);
        g2d.drawString(depthInfo, getWidth() - 500, getHeight() - 100);
        
        if (step.currentNode != null) {
            String nodeInfo = String.format("当前节点: %s", step.currentNode.data);
            g2d.drawString(nodeInfo, getWidth() - 500, getHeight() - 80);
        }
    }
    
    private void generateAnimationSteps() {
        animationSteps = new ArrayList<>();
        targetDepth = (Integer) depthSpinner.getValue();
        root = null;
        
        animationSteps.add(new AnimationStep(null, 
            "开始创建 " + targetDepth + " 阶满二叉树", targetDepth, 0, root));
        
        root = createTree(targetDepth, 0);
        
        animationSteps.add(new AnimationStep(null, 
            "满二叉树创建完成！", targetDepth, 0, root));
    }
    
    private Node<Character> createTree(int depth, int currentIndex) {
        if (depth == 0) {
            animationSteps.add(new AnimationStep(null, 
                "深度为0，返回null", depth, currentIndex, root));
            return null;
        }
        
        // 创建当前节点
        char nodeValue = (char) ('A' + currentIndex % 26);
        Node<Character> node = new Node<>(nodeValue);
        
        animationSteps.add(new AnimationStep(node, 
            String.format("创建节点 %c (索引: %d)", nodeValue, currentIndex), 
            depth, currentIndex, root));
        
        // 重置新创建标记
        resetNewlyCreatedFlags(root);
        node.isNewlyCreated = true;
        
        // 递归创建左子树
        if (depth > 1) {
            animationSteps.add(new AnimationStep(node, 
                String.format("为节点 %c 创建左子树", nodeValue), 
                depth, currentIndex, root));
            
            node.left = createTree(depth - 1, 2 * currentIndex + 1);
            if (node.left != null) {
                node.left.parent = node;
            }
        }
        
        // 递归创建右子树
        if (depth > 1) {
            animationSteps.add(new AnimationStep(node, 
                String.format("为节点 %c 创建右子树", nodeValue), 
                depth, currentIndex, root));
            
            node.right = createTree(depth - 1, 2 * currentIndex + 2);
            if (node.right != null) {
                node.right.parent = node;
            }
        }
        
        animationSteps.add(new AnimationStep(node, 
            String.format("节点 %c 创建完成", nodeValue), 
            depth, currentIndex, root));
        
        return node;
    }
    
    private void resetNewlyCreatedFlags(Node<Character> node) {
        if (node == null) return;
        
        node.isNewlyCreated = false;
        resetNewlyCreatedFlags(node.left);
        resetNewlyCreatedFlags(node.right);
    }
    
    private void startAnimation(ActionEvent e) {
        if (isAnimating) {
            stopAnimation();
            return;
        }
        
        generateAnimationSteps();
        isAnimating = true;
        startButton.setText("停止动画");
        
        animationTimer = new javax.swing.Timer(1500, event -> {
            if (currentStepIndex < animationSteps.size() - 1) {
                nextStep();
            } else {
                stopAnimation();
            }
        });
        animationTimer.start();
    }
    
    private void stopAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        isAnimating = false;
        startButton.setText("开始创建");
    }
    
    private void nextStep() {
        if (currentStepIndex < animationSteps.size() - 1) {
            currentStepIndex++;
            updateAnimationState();
        }
    }
    
    private void previousStep() {
        if (currentStepIndex > 0) {
            currentStepIndex--;
            updateAnimationState();
        }
    }
    
    private void updateAnimationState() {
        if (animationSteps.isEmpty() || currentStepIndex < 0 || currentStepIndex >= animationSteps.size()) {
            return;
        }
        
        AnimationStep step = animationSteps.get(currentStepIndex);
        
        // 更新状态标签
        statusLabel.setText(step.operation);
        
        // 更新按钮状态
        prevButton.setEnabled(currentStepIndex > 0);
        nextButton.setEnabled(currentStepIndex < animationSteps.size() - 1);
        
        SwingUtilities.invokeLater(() -> drawingPanel.repaint());
    }
    
    private void resetAnimation() {
        stopAnimation();
        currentStepIndex = -1;
        animationSteps = new ArrayList<>();
        root = null;
        
        statusLabel.setText("准备创建满二叉树");
        
        prevButton.setEnabled(false);
        nextButton.setEnabled(false);
        
        SwingUtilities.invokeLater(() -> drawingPanel.repaint());
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO10_BinaryTree_Animation().setVisible(true);
        });
    }
}