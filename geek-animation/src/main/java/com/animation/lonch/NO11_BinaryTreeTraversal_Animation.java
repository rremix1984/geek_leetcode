/**
 * copyright 2024-2025
 * 二叉树遍历算法动画演示
 * 基于 com.lonch.NO11_BinaryTreeTraversal 实现
 */
package com.animation.lonch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * 二叉树遍历算法动画演示
 * 支持前序、中序、后序三种遍历方式
 * 
 * @author wangxiaozhe
 */
public class NO11_BinaryTreeTraversal_Animation extends JFrame {
    
    // 树节点类
    static class Node<E> {
        public E data;
        public Node<E> parent, left, right;
        
        public Node(E data) {
            this.data = data;
        }
    }
    
    // 动画相关常量
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final int NODE_RADIUS = 25;
    private static final Color NORMAL_COLOR = Color.LIGHT_GRAY;
    private static final Color VISITING_COLOR = Color.YELLOW;
    private static final Color VISITED_COLOR = Color.GREEN;
    private static final Color CURRENT_COLOR = Color.RED;
    
    // 遍历类型枚举
    enum TraversalType {
        PREORDER("前序遍历"),
        INORDER("中序遍历"), 
        POSTORDER("后序遍历");
        
        private final String name;
        
        TraversalType(String name) {
            this.name = name;
        }
        
        @Override
        public String toString() {
            return name;
        }
    }
    
    // 动画步骤类
    private static class AnimationStep {
        Node<Character> currentNode;
        String operation;
        TraversalType type;
        List<Character> result;
        
        AnimationStep(Node<Character> currentNode, String operation, 
                     TraversalType type, List<Character> result) {
            this.currentNode = currentNode;
            this.operation = operation;
            this.type = type;
            this.result = new ArrayList<>(result);
        }
    }
    
    // GUI组件
    private JPanel drawingPanel;
    private JButton startButton, resetButton, nextButton, prevButton;
    private JComboBox<TraversalType> traversalTypeCombo;
    private JLabel statusLabel, resultLabel;
    private javax.swing.Timer animationTimer;
    
    // 动画状态
    private Node<Character> root;
    private List<AnimationStep> animationSteps;
    private int currentStepIndex;
    private boolean isAnimating;
    private Set<Node<Character>> visitedNodes;
    private Node<Character> currentNode;
    private TraversalType currentTraversalType;
    
    public NO11_BinaryTreeTraversal_Animation() {
        initializeGUI();
        buildTestCase();
        resetAnimation();
    }
    
    private void initializeGUI() {
        setTitle("二叉树遍历算法动画演示");
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
        
        // 遍历类型选择
        panel.add(new JLabel("遍历类型:"));
        traversalTypeCombo = new JComboBox<>(TraversalType.values());
        traversalTypeCombo.addActionListener(e -> {
            if (!isAnimating) {
                resetAnimation();
            }
        });
        panel.add(traversalTypeCombo);
        
        // 控制按钮
        startButton = new JButton("开始动画");
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
        
        statusLabel = new JLabel("准备开始遍历");
        statusLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        panel.add(statusLabel, BorderLayout.WEST);
        
        resultLabel = new JLabel("遍历结果: []");
        resultLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        panel.add(resultLabel, BorderLayout.EAST);
        
        return panel;
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (root == null) return;
        
        // 绘制树
        drawTree(g2d, root, getWidth() / 2, 80, getWidth() / 4);
        
        // 绘制算法信息
        drawAlgorithmInfo(g2d);
        
        // 绘制当前步骤信息
        drawStepInfo(g2d);
    }
    
    private void drawTree(Graphics2D g2d, Node<Character> node, int x, int y, int xOffset) {
        if (node == null) return;
        
        // 绘制连线到子节点
        if (node.left != null) {
            int childX = x - xOffset;
            int childY = y + 80;
            g2d.setColor(Color.BLACK);
            g2d.drawLine(x, y, childX, childY);
            drawTree(g2d, node.left, childX, childY, xOffset / 2);
        }
        
        if (node.right != null) {
            int childX = x + xOffset;
            int childY = y + 80;
            g2d.setColor(Color.BLACK);
            g2d.drawLine(x, y, childX, childY);
            drawTree(g2d, node.right, childX, childY, xOffset / 2);
        }
        
        // 绘制节点
        Color nodeColor = NORMAL_COLOR;
        if (node == currentNode) {
            nodeColor = CURRENT_COLOR;
        } else if (visitedNodes.contains(node)) {
            nodeColor = VISITED_COLOR;
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
            "二叉树遍历算法:",
            "• 前序遍历: 根 → 左 → 右",
            "• 中序遍历: 左 → 根 → 右", 
            "• 后序遍历: 左 → 右 → 根",
            "",
            "当前遍历: " + currentTraversalType.toString()
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
        g2d.drawString(stepInfo, getWidth() - 400, getHeight() - 100);
        
        String resultInfo = "当前结果: " + step.result.toString();
        g2d.drawString(resultInfo, getWidth() - 400, getHeight() - 80);
    }
    
    private void buildTestCase() {
        // 构建测试用例: 创建一个平衡二叉树
        //       A
        //      / \
        //     B   C
        //    / \ / \
        //   D  E F  G
        
        root = new Node<>('A');
        root.left = new Node<>('B');
        root.right = new Node<>('C');
        root.left.parent = root;
        root.right.parent = root;
        
        root.left.left = new Node<>('D');
        root.left.right = new Node<>('E');
        root.left.left.parent = root.left;
        root.left.right.parent = root.left;
        
        root.right.left = new Node<>('F');
        root.right.right = new Node<>('G');
        root.right.left.parent = root.right;
        root.right.right.parent = root.right;
    }
    
    private void generateAnimationSteps() {
        animationSteps = new ArrayList<>();
        currentTraversalType = (TraversalType) traversalTypeCombo.getSelectedItem();
        
        List<Character> result = new ArrayList<>();
        
        switch (currentTraversalType) {
            case PREORDER:
                preorderTraversal(root, result);
                break;
            case INORDER:
                inorderTraversal(root, result);
                break;
            case POSTORDER:
                postorderTraversal(root, result);
                break;
        }
    }
    
    private void preorderTraversal(Node<Character> node, List<Character> result) {
        if (node == null) return;
        
        // 访问根节点
        animationSteps.add(new AnimationStep(node, 
            "访问根节点: " + node.data, currentTraversalType, result));
        result.add(node.data);
        
        // 遍历左子树
        if (node.left != null) {
            animationSteps.add(new AnimationStep(node.left, 
                "进入左子树", currentTraversalType, result));
            preorderTraversal(node.left, result);
        }
        
        // 遍历右子树
        if (node.right != null) {
            animationSteps.add(new AnimationStep(node.right, 
                "进入右子树", currentTraversalType, result));
            preorderTraversal(node.right, result);
        }
    }
    
    private void inorderTraversal(Node<Character> node, List<Character> result) {
        if (node == null) return;
        
        // 遍历左子树
        if (node.left != null) {
            animationSteps.add(new AnimationStep(node.left, 
                "进入左子树", currentTraversalType, result));
            inorderTraversal(node.left, result);
        }
        
        // 访问根节点
        animationSteps.add(new AnimationStep(node, 
            "访问根节点: " + node.data, currentTraversalType, result));
        result.add(node.data);
        
        // 遍历右子树
        if (node.right != null) {
            animationSteps.add(new AnimationStep(node.right, 
                "进入右子树", currentTraversalType, result));
            inorderTraversal(node.right, result);
        }
    }
    
    private void postorderTraversal(Node<Character> node, List<Character> result) {
        if (node == null) return;
        
        // 遍历左子树
        if (node.left != null) {
            animationSteps.add(new AnimationStep(node.left, 
                "进入左子树", currentTraversalType, result));
            postorderTraversal(node.left, result);
        }
        
        // 遍历右子树
        if (node.right != null) {
            animationSteps.add(new AnimationStep(node.right, 
                "进入右子树", currentTraversalType, result));
            postorderTraversal(node.right, result);
        }
        
        // 访问根节点
        animationSteps.add(new AnimationStep(node, 
            "访问根节点: " + node.data, currentTraversalType, result));
        result.add(node.data);
    }
    
    private void startAnimation(ActionEvent e) {
        if (isAnimating) {
            stopAnimation();
            return;
        }
        
        generateAnimationSteps();
        isAnimating = true;
        startButton.setText("停止动画");
        
        animationTimer = new javax.swing.Timer(1000, event -> {
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
        startButton.setText("开始动画");
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
        currentNode = step.currentNode;
        
        // 更新已访问节点集合
        visitedNodes.clear();
        for (int i = 0; i <= currentStepIndex; i++) {
            AnimationStep prevStep = animationSteps.get(i);
            if (prevStep.operation.contains("访问根节点")) {
                visitedNodes.add(prevStep.currentNode);
            }
        }
        
        // 更新状态标签
        statusLabel.setText(step.operation);
        resultLabel.setText("遍历结果: " + step.result.toString());
        
        // 更新按钮状态
        prevButton.setEnabled(currentStepIndex > 0);
        nextButton.setEnabled(currentStepIndex < animationSteps.size() - 1);
        
        SwingUtilities.invokeLater(() -> drawingPanel.repaint());
    }
    
    private void resetAnimation() {
        stopAnimation();
        currentStepIndex = -1;
        visitedNodes = new HashSet<>();
        currentNode = null;
        animationSteps = new ArrayList<>();
        
        statusLabel.setText("准备开始遍历");
        resultLabel.setText("遍历结果: []");
        
        prevButton.setEnabled(false);
        nextButton.setEnabled(false);
        
        SwingUtilities.invokeLater(() -> drawingPanel.repaint());
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO11_BinaryTreeTraversal_Animation().setVisible(true);
        });
    }
}