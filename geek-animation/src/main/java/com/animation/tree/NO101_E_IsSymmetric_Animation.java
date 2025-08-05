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
 * NO.101 对称二叉树 - 动画演示
 * 
 * 算法思路：
 * 递归检查二叉树是否轴对称：
 * 1. 检查根节点的左右子树是否镜像对称
 * 2. 两个节点镜像对称的条件：
 *    - 都为空，或者
 *    - 值相等，且左节点的左子树与右节点的右子树对称，
 *      左节点的右子树与右节点的左子树对称
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(h)，h为树的高度
 */
public class NO101_E_IsSymmetric_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final int NODE_SIZE = 35;
    
    // 树节点类
    static class TreeNode {
        Integer val;
        TreeNode left;
        TreeNode right;
        int x, y; // 绘制坐标
        
        TreeNode(Integer val) {
            this.val = val;
        }
    }
    
    private TreeNode root;
    private TreeNode currentLeft, currentRight;
    private boolean result = false;
    private boolean isCompleted = false;
    private String statusMessage = "点击开始按钮开始演示";
    private String comparisonMessage = "";
    private java.util.List<String> comparisonSteps = new ArrayList<>();
    
    private Timer animationTimer;
    private JButton startButton;
    private JButton resetButton;
    private JComboBox<String> testCaseCombo;
    private JPanel drawPanel; // 绘图面板引用
    
    // 测试用例
    private final String[] testCaseNames = {
        "示例1: 对称树 [1,2,2,3,4,4,3] → true",
        "示例2: 非对称树 [1,2,2,null,3,null,3] → false",
        "示例3: 单节点 [1] → true",
        "示例4: 简单对称 [1,2,2] → true",
        "示例5: 值不对称 [1,2,3] → false"
    };
    
    public NO101_E_IsSymmetric_Animation() {
        initializeGUI();
        setupAnimation();
        loadTestCase(0);
    }
    
    private void initializeGUI() {
        setTitle("NO.101 对称二叉树 - 动画演示");
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
                drawPanel.repaint();
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
        animationTimer = new Timer(2000, new ActionListener() {
            private java.util.Queue<TreeNode[]> queue = new LinkedList<>();
            private boolean initialized = false;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!initialized) {
                    // 初始化
                    queue.clear();
                    comparisonSteps.clear();
                    if (root == null) {
                        result = true;
                        isCompleted = true;
                        statusMessage = "空树是对称的";
                        animationTimer.stop();
                        startButton.setText("开始演示");
                    } else {
                        queue.offer(new TreeNode[]{root.left, root.right});
                        statusMessage = "开始检查根节点的左右子树是否对称";
                    }
                    initialized = true;
                    drawPanel.repaint();
                    return;
                }
                
                if (!queue.isEmpty()) {
                    TreeNode[] pair = queue.poll();
                    currentLeft = pair[0];
                    currentRight = pair[1];
                    
                    boolean stepResult = checkSymmetric(currentLeft, currentRight);
                    
                    if (!stepResult) {
                        // 发现不对称，结束检查
                        result = false;
                        isCompleted = true;
                        animationTimer.stop();
                        statusMessage = "发现不对称，树不是对称的！";
                        startButton.setText("开始演示");
                    } else if (currentLeft != null && currentRight != null) {
                        // 继续检查子节点
                        queue.offer(new TreeNode[]{currentLeft.left, currentRight.right});
                        queue.offer(new TreeNode[]{currentLeft.right, currentRight.left});
                    }
                } else {
                    // 所有节点都检查完成
                    result = true;
                    isCompleted = true;
                    animationTimer.stop();
                    statusMessage = "所有节点检查完成，树是对称的！";
                    startButton.setText("开始演示");
                }
                
                drawPanel.repaint();
            }
        });
    }
    
    private boolean checkSymmetric(TreeNode left, TreeNode right) {
        String step;
        
        if (left == null && right == null) {
            step = "比较: null vs null → 对称";
            comparisonMessage = step;
            comparisonSteps.add(step);
            return true;
        }
        
        if (left == null || right == null) {
            step = "比较: " + (left == null ? "null" : left.val) + " vs " + (right == null ? "null" : right.val) + " → 不对称";
            comparisonMessage = step;
            comparisonSteps.add(step);
            return false;
        }
        
        if (!left.val.equals(right.val)) {
            step = "比较: " + left.val + " vs " + right.val + " → 值不同，不对称";
            comparisonMessage = step;
            comparisonSteps.add(step);
            return false;
        }
        
        step = "比较: " + left.val + " vs " + right.val + " → 值相同，继续检查子树";
        comparisonMessage = step;
        comparisonSteps.add(step);
        return true;
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制标题
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 24));
        g2d.setColor(new Color(51, 51, 51));
        String title = "NO.101 对称二叉树算法演示";
        FontMetrics fm = g2d.getFontMetrics();
        int titleX = (getWidth() - fm.stringWidth(title)) / 2;
        g2d.drawString(title, titleX, 50);
        
        // 绘制对称轴
        g2d.setColor(new Color(255, 0, 0, 100));
        g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{10, 5}, 0));
        g2d.drawLine(getWidth() / 2, 80, getWidth() / 2, 350);
        
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g2d.setColor(Color.RED);
        g2d.drawString("对称轴", getWidth() / 2 + 10, 100);
        
        // 绘制二叉树
        if (root != null) {
            drawTree(g2d, root, getWidth() / 2, 120, getWidth() / 6);
        } else {
            g2d.setColor(new Color(128, 128, 128));
            g2d.drawString("空树", getWidth() / 2 - 20, 150);
        }
        
        // 绘制比较步骤
        drawComparisonSteps(g2d);
        
        // 绘制算法步骤说明
        drawAlgorithmSteps(g2d);
        
        // 绘制状态信息
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(new Color(255, 69, 0));
        g2d.drawString("状态: " + statusMessage, 50, 450);
        
        if (!comparisonMessage.isEmpty()) {
            g2d.setColor(new Color(0, 100, 0));
            g2d.drawString("当前比较: " + comparisonMessage, 50, 475);
        }
        
        // 绘制结果
        if (isCompleted) {
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 20));
            g2d.setColor(result ? new Color(34, 139, 34) : new Color(220, 20, 60));
            String resultText = "结果: " + (result ? "树是对称的" : "树不是对称的");
            g2d.drawString(resultText, 50, 510);
        }
        
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
            int leftY = y + 60;
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(x, y, leftX, leftY);
            drawTree(g2d, node.left, leftX, leftY, offset / 2);
        }
        
        if (node.right != null) {
            int rightX = x + offset;
            int rightY = y + 60;
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(x, y, rightX, rightY);
            drawTree(g2d, node.right, rightX, rightY, offset / 2);
        }
        
        // 绘制节点
        Color nodeColor;
        if (node == currentLeft || node == currentRight) {
            nodeColor = new Color(255, 69, 0); // 当前比较的节点
        } else {
            nodeColor = new Color(173, 216, 230); // 普通节点
        }
        
        g2d.setColor(nodeColor);
        g2d.fillOval(x - NODE_SIZE/2, y - NODE_SIZE/2, NODE_SIZE, NODE_SIZE);
        
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(x - NODE_SIZE/2, y - NODE_SIZE/2, NODE_SIZE, NODE_SIZE);
        
        // 绘制节点值
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        String value = String.valueOf(node.val);
        FontMetrics fm = g2d.getFontMetrics();
        int textX = x - fm.stringWidth(value) / 2;
        int textY = y + fm.getAscent() / 2;
        g2d.drawString(value, textX, textY);
        
        // 如果是当前比较的节点，绘制连接线
        if (node == currentLeft && currentRight != null) {
            g2d.setColor(new Color(255, 69, 0, 150));
            g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{5, 5}, 0));
            g2d.drawLine(x, y, currentRight.x, currentRight.y);
        }
    }
    
    private void drawComparisonSteps(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(new Color(51, 51, 51));
        g2d.drawString("比较步骤:", 50, 370);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        int startY = 390;
        int maxSteps = Math.min(comparisonSteps.size(), 4); // 只显示最近的4步
        int startIndex = Math.max(0, comparisonSteps.size() - maxSteps);
        
        for (int i = startIndex; i < comparisonSteps.size(); i++) {
            g2d.setColor(new Color(0, 100, 0));
            g2d.drawString((i + 1) + ". " + comparisonSteps.get(i), 50, startY + (i - startIndex) * 18);
        }
    }
    
    private void drawAlgorithmSteps(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(new Color(51, 51, 51));
        
        String[] steps = {
            "对称二叉树检查算法:",
            "1. 如果根节点为空 → 对称",
            "2. 检查左右子树是否镜像对称:",
            "   - 都为空 → 对称",
            "   - 一个为空，一个不为空 → 不对称",
            "   - 值不相等 → 不对称",
            "   - 递归检查: left.left vs right.right",
            "                left.right vs right.left"
        };
        
        int startY = 550;
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
        g2d.drawString("时间复杂度: O(n)", 800, 580);
        g2d.drawString("空间复杂度: O(h)", 800, 600);
        g2d.drawString("核心技术: 递归 + 镜像比较", 800, 620);
    }
    
    private TreeNode buildTestCase(int caseIndex) {
        switch (caseIndex) {
            case 0: // 对称树 [1,2,2,3,4,4,3]
                TreeNode root1 = new TreeNode(1);
                root1.left = new TreeNode(2);
                root1.right = new TreeNode(2);
                root1.left.left = new TreeNode(3);
                root1.left.right = new TreeNode(4);
                root1.right.left = new TreeNode(4);
                root1.right.right = new TreeNode(3);
                return root1;
                
            case 1: // 非对称树 [1,2,2,null,3,null,3]
                TreeNode root2 = new TreeNode(1);
                root2.left = new TreeNode(2);
                root2.right = new TreeNode(2);
                root2.left.right = new TreeNode(3);
                root2.right.right = new TreeNode(3);
                return root2;
                
            case 2: // 单节点 [1]
                return new TreeNode(1);
                
            case 3: // 简单对称 [1,2,2]
                TreeNode root4 = new TreeNode(1);
                root4.left = new TreeNode(2);
                root4.right = new TreeNode(2);
                return root4;
                
            case 4: // 值不对称 [1,2,3]
                TreeNode root5 = new TreeNode(1);
                root5.left = new TreeNode(2);
                root5.right = new TreeNode(3);
                return root5;
                
            default:
                return null;
        }
    }
    
    private void loadTestCase(int index) {
        root = buildTestCase(index);
        currentLeft = null;
        currentRight = null;
        result = false;
        isCompleted = false;
        comparisonMessage = "";
        comparisonSteps.clear();
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
        drawPanel.repaint();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO101_E_IsSymmetric_Animation().setVisible(true);
        });
    }
}