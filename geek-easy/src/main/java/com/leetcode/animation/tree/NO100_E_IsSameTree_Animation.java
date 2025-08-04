package com.leetcode.animation.tree;

import javax.swing.*;
import com.leetcode.animation.AlgorithmTreeLauncher;
import javax.swing.Timer;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.awt.*;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.awt.event.ActionEvent;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.awt.event.ActionListener;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.util.*;
import com.leetcode.animation.AlgorithmTreeLauncher;

/**
 * NO.100 相同的树 - 动画演示
 * 
 * 算法思路：
 * 递归比较两棵树：
 * 1. 如果两个节点都为空，返回true
 * 2. 如果其中一个为空，另一个不为空，返回false
 * 3. 如果两个节点值不相等，返回false
 * 4. 递归比较左子树和右子树
 * 
 * 时间复杂度：O(min(m,n))，m和n分别是两棵树的节点数
 * 空间复杂度：O(min(m,n))，递归栈的深度
 */
public class NO100_E_IsSameTree_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1400;
    private static final int WINDOW_HEIGHT = 800;
    private static final int NODE_SIZE = 35;
    
    // 树节点类
    static class TreeNode {
        Integer val; // 使用Integer支持null值
        TreeNode left;
        TreeNode right;
        int x, y; // 绘制坐标
        
        TreeNode(Integer val) {
            this.val = val;
        }
    }
    
    private TreeNode tree1, tree2;
    private TreeNode currentNode1, currentNode2;
    private boolean result = false;
    private boolean isCompleted = false;
    private String statusMessage = "点击开始按钮开始演示";
    private String comparisonMessage = "";
    private java.util.List<String> comparisonSteps = new ArrayList<>();
    
    private Timer animationTimer;
    private JButton startButton;
    private JButton resetButton;
    private JComboBox<String> testCaseCombo;
    
    // 测试用例
    private final String[] testCaseNames = {
        "示例1: 相同的树 [1,2,3] vs [1,2,3] → true",
        "示例2: 不同结构 [1,2] vs [1,null,2] → false",
        "示例3: 不同值 [1,2,1] vs [1,1,2] → false",
        "示例4: 都为空 null vs null → true",
        "示例5: 一空一非空 [1] vs null → false"
    };
    
    public NO100_E_IsSameTree_Animation() {
        initializeGUI();
        setupAnimation();
        loadTestCase(0);
    }
    
    private void initializeGUI() {
        setTitle("NO.100 相同的树 - 动画演示");
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
        animationTimer = new Timer(2000, new ActionListener() {
            private java.util.Queue<TreeNode[]> queue = new LinkedList<>();
            private boolean initialized = false;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!initialized) {
                    // 初始化BFS队列
                    queue.clear();
                    comparisonSteps.clear();
                    queue.offer(new TreeNode[]{tree1, tree2});
                    initialized = true;
                    statusMessage = "开始比较两棵树，使用BFS遍历";
                    repaint();
                    return;
                }
                
                if (!queue.isEmpty()) {
                    TreeNode[] pair = queue.poll();
                    currentNode1 = pair[0];
                    currentNode2 = pair[1];
                    
                    boolean stepResult = compareNodes(currentNode1, currentNode2);
                    
                    if (!stepResult) {
                        // 发现不同，结束比较
                        result = false;
                        isCompleted = true;
                        animationTimer.stop();
                        statusMessage = "发现不同，两棵树不相同！";
                        startButton.setText("开始演示");
                    } else if (currentNode1 != null && currentNode2 != null) {
                        // 继续比较子节点
                        queue.offer(new TreeNode[]{currentNode1.left, currentNode2.left});
                        queue.offer(new TreeNode[]{currentNode1.right, currentNode2.right});
                    }
                } else {
                    // 所有节点都比较完成
                    result = true;
                    isCompleted = true;
                    animationTimer.stop();
                    statusMessage = "所有节点比较完成，两棵树相同！";
                    startButton.setText("开始演示");
                }
                
                repaint();
            }
        });
    }
    
    private boolean compareNodes(TreeNode p, TreeNode q) {
        String step;
        
        if (p == null && q == null) {
            step = "比较: null vs null → 相同";
            comparisonMessage = step;
            comparisonSteps.add(step);
            return true;
        }
        
        if (p == null || q == null) {
            step = "比较: " + (p == null ? "null" : p.val) + " vs " + (q == null ? "null" : q.val) + " → 不同";
            comparisonMessage = step;
            comparisonSteps.add(step);
            return false;
        }
        
        if (!p.val.equals(q.val)) {
            step = "比较: " + p.val + " vs " + q.val + " → 值不同";
            comparisonMessage = step;
            comparisonSteps.add(step);
            return false;
        }
        
        step = "比较: " + p.val + " vs " + q.val + " → 相同";
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
        String title = "NO.100 相同的树算法演示";
        FontMetrics fm = g2d.getFontMetrics();
        int titleX = (getWidth() - fm.stringWidth(title)) / 2;
        g2d.drawString(title, titleX, 50);
        
        // 绘制两棵树
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 18));
        g2d.setColor(new Color(51, 51, 51));
        g2d.drawString("树1", 200, 90);
        g2d.drawString("树2", 900, 90);
        
        if (tree1 != null) {
            drawTree(g2d, tree1, 300, 120, 100, true);
        } else {
            g2d.setColor(new Color(128, 128, 128));
            g2d.drawString("null", 290, 150);
        }
        
        if (tree2 != null) {
            drawTree(g2d, tree2, 1000, 120, 100, false);
        } else {
            g2d.setColor(new Color(128, 128, 128));
            g2d.drawString("null", 990, 150);
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
            String resultText = "结果: " + (result ? "两棵树相同" : "两棵树不同");
            g2d.drawString(resultText, 50, 510);
        }
        
        // 绘制复杂度信息
        drawComplexityInfo(g2d);
    }
    
    private void drawTree(Graphics2D g2d, TreeNode node, int x, int y, int offset, boolean isTree1) {
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
            drawTree(g2d, node.left, leftX, leftY, offset / 2, isTree1);
        }
        
        if (node.right != null) {
            int rightX = x + offset;
            int rightY = y + 60;
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(x, y, rightX, rightY);
            drawTree(g2d, node.right, rightX, rightY, offset / 2, isTree1);
        }
        
        // 绘制节点
        Color nodeColor;
        if ((isTree1 && node == currentNode1) || (!isTree1 && node == currentNode2)) {
            nodeColor = new Color(255, 69, 0); // 当前比较的节点
        } else {
            nodeColor = isTree1 ? new Color(173, 216, 230) : new Color(144, 238, 144); // 树1蓝色，树2绿色
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
    }
    
    private void drawComparisonSteps(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(new Color(51, 51, 51));
        g2d.drawString("比较步骤:", 50, 350);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        int startY = 370;
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
            "递归比较算法步骤:",
            "1. 如果两个节点都为空 → 返回true",
            "2. 如果其中一个为空 → 返回false", 
            "3. 如果两个节点值不相等 → 返回false",
            "4. 递归比较左子树和右子树",
            "5. 只有所有比较都为true时，树才相同"
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
        g2d.drawString("时间复杂度: O(min(m,n))", 800, 580);
        g2d.drawString("空间复杂度: O(min(m,n))", 800, 600);
        g2d.drawString("核心技术: 递归遍历", 800, 620);
    }
    
    private TreeNode[] buildTestCase(int caseIndex) {
        switch (caseIndex) {
            case 0: // 相同的树 [1,2,3] vs [1,2,3]
                TreeNode tree1_1 = new TreeNode(1);
                tree1_1.left = new TreeNode(2);
                tree1_1.right = new TreeNode(3);
                
                TreeNode tree2_1 = new TreeNode(1);
                tree2_1.left = new TreeNode(2);
                tree2_1.right = new TreeNode(3);
                
                return new TreeNode[]{tree1_1, tree2_1};
                
            case 1: // 不同结构 [1,2] vs [1,null,2]
                TreeNode tree1_2 = new TreeNode(1);
                tree1_2.left = new TreeNode(2);
                
                TreeNode tree2_2 = new TreeNode(1);
                tree2_2.right = new TreeNode(2);
                
                return new TreeNode[]{tree1_2, tree2_2};
                
            case 2: // 不同值 [1,2,1] vs [1,1,2]
                TreeNode tree1_3 = new TreeNode(1);
                tree1_3.left = new TreeNode(2);
                tree1_3.right = new TreeNode(1);
                
                TreeNode tree2_3 = new TreeNode(1);
                tree2_3.left = new TreeNode(1);
                tree2_3.right = new TreeNode(2);
                
                return new TreeNode[]{tree1_3, tree2_3};
                
            case 3: // 都为空
                return new TreeNode[]{null, null};
                
            case 4: // 一空一非空
                TreeNode tree1_5 = new TreeNode(1);
                return new TreeNode[]{tree1_5, null};
                
            default:
                return new TreeNode[]{null, null};
        }
    }
    
    private void loadTestCase(int index) {
        TreeNode[] trees = buildTestCase(index);
        tree1 = trees[0];
        tree2 = trees[1];
        currentNode1 = null;
        currentNode2 = null;
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
        repaint();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO100_E_IsSameTree_Animation().setVisible(true);
        });
    }
}