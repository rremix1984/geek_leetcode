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
import java.util.List;
import com.animation.launcher.AlgorithmTreeLauncher;

/**
 * NO.094 二叉树的中序遍历 - 动画演示
 * 
 * 算法思路：
 * 中序遍历顺序：左子树 → 根节点 → 右子树
 * 
 * 迭代法实现：
 * 1. 使用栈来模拟递归过程
 * 2. 先将所有左子节点入栈
 * 3. 弹出栈顶节点，访问该节点
 * 4. 转向右子树，重复过程
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(h)，h为树的高度
 */
public class NO094_E_InorderTraversal_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final int NODE_SIZE = 40;
    
    // 树节点类
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        int x, y; // 绘制坐标
        
        TreeNode(int val) {
            this.val = val;
        }
    }
    
    private TreeNode root;
    private Stack<TreeNode> stack;
    private List<Integer> result;
    private TreeNode currentNode;
    private TreeNode visitedNode;
    private boolean isCompleted = false;
    private String statusMessage = "点击开始按钮开始演示";
    private int step = 0;
    
    private Timer animationTimer;
    private JButton startButton;
    private JButton resetButton;
    private JComboBox<String> testCaseCombo;
    
    // 测试用例
    private final String[] testCaseNames = {
        "示例1: [1,null,2,3] → [1,3,2]",
        "示例2: [3,9,20,null,null,15,7] → [9,3,15,20,7]",
        "示例3: [1,2,3,4,5] → [4,2,5,1,3]",
        "示例4: [1] → [1]"
    };
    
    public NO094_E_InorderTraversal_Animation() {
        initializeGUI();
        setupAnimation();
        loadTestCase(0);
    }
    
    private void initializeGUI() {
        setTitle("NO.094 二叉树的中序遍历 - 动画演示");
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
            @Override
            public void actionPerformed(ActionEvent e) {
                performInorderStep();
                repaint();
            }
        });
    }
    
    private void performInorderStep() {
        if (step == 0) {
            // 初始化
            stack = new Stack<>();
            result = new ArrayList<>();
            currentNode = root;
            statusMessage = "开始中序遍历，当前节点: " + (currentNode != null ? currentNode.val : "null");
            step++;
        } else if (currentNode != null || !stack.isEmpty()) {
            if (currentNode != null) {
                // 将当前节点入栈，转向左子树
                stack.push(currentNode);
                statusMessage = "节点 " + currentNode.val + " 入栈，转向左子树";
                currentNode = currentNode.left;
            } else {
                // 弹出栈顶节点，访问该节点
                visitedNode = stack.pop();
                result.add(visitedNode.val);
                statusMessage = "访问节点 " + visitedNode.val + "，加入结果集";
                currentNode = visitedNode.right;
                
                // 延迟清除访问标记
                Timer clearTimer = new Timer(800, evt -> {
                    visitedNode = null;
                    repaint();
                    ((Timer) evt.getSource()).stop();
                });
                clearTimer.setRepeats(false);
                clearTimer.start();
            }
        } else {
            // 遍历完成
            animationTimer.stop();
            isCompleted = true;
            statusMessage = "中序遍历完成！结果: " + result.toString();
            startButton.setText("开始演示");
        }
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制标题
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 24));
        g2d.setColor(new Color(51, 51, 51));
        String title = "NO.094 二叉树的中序遍历算法演示";
        FontMetrics fm = g2d.getFontMetrics();
        int titleX = (getWidth() - fm.stringWidth(title)) / 2;
        g2d.drawString(title, titleX, 50);
        
        // 绘制二叉树
        if (root != null) {
            drawTree(g2d, root, getWidth() / 2, 100, getWidth() / 4);
        }
        
        // 绘制栈状态
        drawStack(g2d);
        
        // 绘制结果
        drawResult(g2d);
        
        // 绘制算法步骤说明
        drawAlgorithmSteps(g2d);
        
        // 绘制状态信息
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(new Color(255, 69, 0));
        g2d.drawString("状态: " + statusMessage, 50, 550);
        
        // 绘制复杂度信息
        drawComplexityInfo(g2d);
    }
    
    private void drawTree(Graphics2D g2d, TreeNode node, int x, int y, int offset) {
        if (node == null) return;
        
        node.x = x;
        node.y = y;
        
        // 绘制连线
        if (node.left != null) {
            int leftX = x - offset;
            int leftY = y + 80;
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(x, y, leftX, leftY);
            drawTree(g2d, node.left, leftX, leftY, offset / 2);
        }
        
        if (node.right != null) {
            int rightX = x + offset;
            int rightY = y + 80;
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(x, y, rightX, rightY);
            drawTree(g2d, node.right, rightX, rightY, offset / 2);
        }
        
        // 绘制节点
        Color nodeColor;
        if (node == visitedNode) {
            nodeColor = new Color(255, 69, 0); // 正在访问的节点
        } else if (node == currentNode) {
            nodeColor = new Color(255, 215, 0); // 当前节点
        } else if (stack != null && stack.contains(node)) {
            nodeColor = new Color(173, 216, 230); // 栈中的节点
        } else if (result != null && result.contains(node.val)) {
            nodeColor = new Color(144, 238, 144); // 已访问的节点
        } else {
            nodeColor = new Color(220, 220, 220); // 未访问的节点
        }
        
        g2d.setColor(nodeColor);
        g2d.fillOval(x - NODE_SIZE/2, y - NODE_SIZE/2, NODE_SIZE, NODE_SIZE);
        
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(x - NODE_SIZE/2, y - NODE_SIZE/2, NODE_SIZE, NODE_SIZE);
        
        // 绘制节点值
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.setColor(Color.BLACK);
        String value = String.valueOf(node.val);
        FontMetrics fm = g2d.getFontMetrics();
        int textX = x - fm.stringWidth(value) / 2;
        int textY = y + fm.getAscent() / 2;
        g2d.drawString(value, textX, textY);
    }
    
    private void drawStack(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(new Color(51, 51, 51));
        g2d.drawString("栈状态:", 50, 400);
        
        if (stack != null && !stack.isEmpty()) {
            int startX = 50;
            int startY = 420;
            int cellWidth = 40;
            int cellHeight = 30;
            
            for (int i = 0; i < stack.size(); i++) {
                TreeNode node = stack.get(i);
                int x = startX + i * (cellWidth + 5);
                
                g2d.setColor(new Color(173, 216, 230));
                g2d.fillRect(x, startY, cellWidth, cellHeight);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, startY, cellWidth, cellHeight);
                
                g2d.setFont(new Font("Arial", Font.BOLD, 14));
                String value = String.valueOf(node.val);
                FontMetrics fm = g2d.getFontMetrics();
                int textX = x + (cellWidth - fm.stringWidth(value)) / 2;
                int textY = startY + (cellHeight + fm.getAscent()) / 2;
                g2d.drawString(value, textX, textY);
            }
            
            // 绘制栈顶指示
            if (!stack.isEmpty()) {
                int topX = startX + (stack.size() - 1) * (cellWidth + 5) + cellWidth / 2;
                g2d.setColor(Color.RED);
                g2d.drawString("栈顶", topX - 10, startY - 5);
            }
        } else {
            g2d.setColor(new Color(128, 128, 128));
            g2d.drawString("栈为空", 50, 440);
        }
    }
    
    private void drawResult(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(new Color(51, 51, 51));
        g2d.drawString("遍历结果:", 300, 400);
        
        if (result != null && !result.isEmpty()) {
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < result.size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append(result.get(i));
            }
            sb.append("]");
            
            g2d.setColor(new Color(34, 139, 34));
            g2d.drawString(sb.toString(), 300, 420);
        } else {
            g2d.setColor(new Color(128, 128, 128));
            g2d.drawString("[]", 300, 420);
        }
    }
    
    private void drawAlgorithmSteps(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(new Color(51, 51, 51));
        
        String[] steps = {
            "中序遍历算法步骤 (迭代法):",
            "1. 初始化栈和结果列表",
            "2. 当前节点不为空或栈不为空时:",
            "   - 如果当前节点不为空:",
            "     * 将当前节点入栈",
            "     * 转向左子树",
            "   - 否则:",
            "     * 弹出栈顶节点并访问",
            "     * 转向右子树",
            "3. 重复直到遍历完成"
        };
        
        int startY = 580;
        for (int i = 0; i < steps.length; i++) {
            if (i == 0) {
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
        g2d.drawString("时间复杂度: O(n)", 800, 600);
        g2d.drawString("空间复杂度: O(h)", 800, 620);
        g2d.drawString("核心技术: 栈 + 迭代", 800, 640);
    }
    
    private TreeNode buildTestCase(int caseIndex) {
        switch (caseIndex) {
            case 0: // [1,null,2,3]
                TreeNode root1 = new TreeNode(1);
                root1.right = new TreeNode(2);
                root1.right.left = new TreeNode(3);
                return root1;
                
            case 1: // [3,9,20,null,null,15,7]
                TreeNode root2 = new TreeNode(3);
                root2.left = new TreeNode(9);
                root2.right = new TreeNode(20);
                root2.right.left = new TreeNode(15);
                root2.right.right = new TreeNode(7);
                return root2;
                
            case 2: // [1,2,3,4,5]
                TreeNode root3 = new TreeNode(1);
                root3.left = new TreeNode(2);
                root3.right = new TreeNode(3);
                root3.left.left = new TreeNode(4);
                root3.left.right = new TreeNode(5);
                return root3;
                
            case 3: // [1]
                return new TreeNode(1);
                
            default:
                return null;
        }
    }
    
    private void loadTestCase(int index) {
        root = buildTestCase(index);
        stack = null;
        result = null;
        currentNode = null;
        visitedNode = null;
        isCompleted = false;
        step = 0;
        statusMessage = "已加载测试用例 " + (index + 1) + "，点击开始按钮开始演示";
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
            new NO094_E_InorderTraversal_Animation().setVisible(true);
        });
    }
}