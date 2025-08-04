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
 * NO.108 将有序数组转换为二叉搜索树 - 动画演示
 * 
 * 算法思路：
 * 使用分治法构建平衡二叉搜索树：
 * 1. 选择数组中间元素作为根节点
 * 2. 递归构建左子树（左半部分数组）
 * 3. 递归构建右子树（右半部分数组）
 * 4. 这样构建的树是高度平衡的
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(log n)
 */
public class NO108_E_SortedArrayToBST_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final int NODE_SIZE = 35;
    
    // 树节点类
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        int x, y; // 绘制坐标
        boolean isNewlyCreated = false; // 是否是新创建的节点
        
        TreeNode(int val) {
            this.val = val;
        }
    }
    
    // 数组区间类
    static class ArrayRange {
        int left, right, mid;
        int level; // 递归层级
        TreeNode parentNode; // 父节点
        boolean isLeftChild; // 是否是左子节点
        
        ArrayRange(int left, int right, int level, TreeNode parent, boolean isLeft) {
            this.left = left;
            this.right = right;
            this.mid = left + (right - left) / 2;
            this.level = level;
            this.parentNode = parent;
            this.isLeftChild = isLeft;
        }
    }
    
    private int[] nums;
    private TreeNode root;
    private TreeNode currentNode;
    private ArrayRange currentRange;
    private boolean isCompleted = false;
    private String statusMessage = "点击开始按钮开始演示";
    private String constructionMessage = "";
    private java.util.List<String> constructionSteps = new ArrayList<>();
    private Stack<ArrayRange> constructionStack = new Stack<>();
    
    private Timer animationTimer;
    private JButton startButton;
    private JButton resetButton;
    private JComboBox<String> testCaseCombo;
    
    // 测试用例
    private final String[] testCaseNames = {
        "示例1: [-10,-3,0,5,9]",
        "示例2: [1,3]",
        "示例3: [1,2,3,4,5,6,7]",
        "示例4: [1]",
        "示例5: [1,2,3,4,5]"
    };
    
    public NO108_E_SortedArrayToBST_Animation() {
        initializeGUI();
        setupAnimation();
        loadTestCase(0);
    }
    
    private void initializeGUI() {
        setTitle("NO.108 将有序数组转换为二叉搜索树 - 动画演示");
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
            private boolean initialized = false;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!initialized) {
                    // 初始化
                    constructionSteps.clear();
                    constructionStack.clear();
                    root = null;
                    currentNode = null;
                    currentRange = null;
                    
                    if (nums.length == 0) {
                        isCompleted = true;
                        statusMessage = "空数组，无法构建树";
                        animationTimer.stop();
                        startButton.setText("开始演示");
                    } else {
                        constructionStack.push(new ArrayRange(0, nums.length - 1, 0, null, false));
                        statusMessage = "开始构建平衡二叉搜索树";
                    }
                    initialized = true;
                    repaint();
                    return;
                }
                
                if (!constructionStack.isEmpty()) {
                    ArrayRange range = constructionStack.pop();
                    currentRange = range;
                    
                    if (range.left <= range.right) {
                        // 创建当前节点
                        TreeNode newNode = new TreeNode(nums[range.mid]);
                        newNode.isNewlyCreated = true;
                        currentNode = newNode;
                        
                        // 连接到父节点
                        if (range.parentNode == null) {
                            root = newNode;
                        } else {
                            if (range.isLeftChild) {
                                range.parentNode.left = newNode;
                            } else {
                                range.parentNode.right = newNode;
                            }
                        }
                        
                        constructionMessage = String.format("创建节点 %d (索引 %d)，区间 [%d, %d]", 
                            nums[range.mid], range.mid, range.left, range.right);
                        constructionSteps.add(constructionMessage);
                        
                        // 添加子区间到栈中（注意顺序，右子树先入栈）
                        if (range.mid + 1 <= range.right) {
                            constructionStack.push(new ArrayRange(range.mid + 1, range.right, 
                                range.level + 1, newNode, false));
                        }
                        if (range.left <= range.mid - 1) {
                            constructionStack.push(new ArrayRange(range.left, range.mid - 1, 
                                range.level + 1, newNode, true));
                        }
                    }
                } else {
                    // 构建完成
                    isCompleted = true;
                    animationTimer.stop();
                    statusMessage = "平衡二叉搜索树构建完成！";
                    startButton.setText("开始演示");
                    currentRange = null;
                    
                    // 重置新创建标记
                    resetNewlyCreatedFlags(root);
                }
                
                repaint();
            }
        });
    }
    
    private void resetNewlyCreatedFlags(TreeNode node) {
        if (node == null) return;
        node.isNewlyCreated = false;
        resetNewlyCreatedFlags(node.left);
        resetNewlyCreatedFlags(node.right);
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制标题
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 24));
        g2d.setColor(new Color(51, 51, 51));
        String title = "NO.108 将有序数组转换为二叉搜索树算法演示";
        FontMetrics fm = g2d.getFontMetrics();
        int titleX = (getWidth() - fm.stringWidth(title)) / 2;
        g2d.drawString(title, titleX, 50);
        
        // 绘制数组
        drawArray(g2d);
        
        // 绘制二叉树
        if (root != null) {
            assignCoordinates(root, getWidth() / 2, 200, getWidth() / 6);
            drawTree(g2d, root);
        }
        
        // 绘制构建栈
        drawConstructionStack(g2d);
        
        // 绘制构建步骤
        drawConstructionSteps(g2d);
        
        // 绘制算法步骤说明
        drawAlgorithmSteps(g2d);
        
        // 绘制状态信息
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(new Color(255, 69, 0));
        g2d.drawString("状态: " + statusMessage, 50, 450);
        
        if (!constructionMessage.isEmpty()) {
            g2d.setColor(new Color(0, 100, 0));
            g2d.drawString("当前操作: " + constructionMessage, 50, 475);
        }
        
        // 绘制结果
        if (isCompleted && root != null) {
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 20));
            g2d.setColor(new Color(34, 139, 34));
            String resultText = "构建完成！高度平衡的二叉搜索树";
            g2d.drawString(resultText, 50, 510);
        }
        
        // 绘制复杂度信息
        drawComplexityInfo(g2d);
    }
    
    private void drawArray(Graphics2D g2d) {
        if (nums == null || nums.length == 0) return;
        
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(new Color(51, 51, 51));
        g2d.drawString("有序数组:", 50, 100);
        
        int startX = 150;
        int cellWidth = 40;
        int cellHeight = 30;
        
        for (int i = 0; i < nums.length; i++) {
            int x = startX + i * cellWidth;
            int y = 80;
            
            // 绘制单元格背景
            Color cellColor;
            if (currentRange != null && i >= currentRange.left && i <= currentRange.right) {
                if (i == currentRange.mid) {
                    cellColor = new Color(255, 69, 0); // 当前中间元素
                } else {
                    cellColor = new Color(255, 255, 0); // 当前区间
                }
            } else {
                cellColor = new Color(173, 216, 230); // 普通元素
            }
            
            g2d.setColor(cellColor);
            g2d.fillRect(x, y, cellWidth, cellHeight);
            
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect(x, y, cellWidth, cellHeight);
            
            // 绘制数值
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            String value = String.valueOf(nums[i]);
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (cellWidth - fm.stringWidth(value)) / 2;
            int textY = y + (cellHeight + fm.getAscent()) / 2;
            g2d.drawString(value, textX, textY);
            
            // 绘制索引
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.setColor(new Color(128, 128, 128));
            String index = String.valueOf(i);
            int indexX = x + (cellWidth - fm.stringWidth(index)) / 2;
            g2d.drawString(index, indexX, y + cellHeight + 15);
        }
        
        // 绘制当前区间标记
        if (currentRange != null) {
            g2d.setColor(new Color(255, 0, 0));
            g2d.setStroke(new BasicStroke(2));
            int leftX = startX + currentRange.left * cellWidth;
            int rightX = startX + (currentRange.right + 1) * cellWidth;
            g2d.drawLine(leftX, 75, rightX, 75);
            g2d.drawString("[" + currentRange.left + ", " + currentRange.right + "]", leftX, 70);
        }
    }
    
    private void assignCoordinates(TreeNode node, int x, int y, int offset) {
        if (node == null) return;
        
        node.x = x;
        node.y = y;
        
        if (node.left != null) {
            assignCoordinates(node.left, x - offset, y + 60, offset / 2);
        }
        
        if (node.right != null) {
            assignCoordinates(node.right, x + offset, y + 60, offset / 2);
        }
    }
    
    private void drawTree(Graphics2D g2d, TreeNode node) {
        if (node == null) return;
        
        // 绘制连线
        if (node.left != null) {
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(node.x, node.y, node.left.x, node.left.y);
            drawTree(g2d, node.left);
        }
        
        if (node.right != null) {
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(node.x, node.y, node.right.x, node.right.y);
            drawTree(g2d, node.right);
        }
        
        // 绘制节点
        Color nodeColor;
        if (node == currentNode) {
            nodeColor = new Color(255, 69, 0); // 当前创建的节点
        } else if (node.isNewlyCreated) {
            nodeColor = new Color(144, 238, 144); // 新创建的节点
        } else {
            nodeColor = new Color(173, 216, 230); // 普通节点
        }
        
        g2d.setColor(nodeColor);
        g2d.fillOval(node.x - NODE_SIZE/2, node.y - NODE_SIZE/2, NODE_SIZE, NODE_SIZE);
        
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(node.x - NODE_SIZE/2, node.y - NODE_SIZE/2, NODE_SIZE, NODE_SIZE);
        
        // 绘制节点值
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        String value = String.valueOf(node.val);
        FontMetrics fm = g2d.getFontMetrics();
        int textX = node.x - fm.stringWidth(value) / 2;
        int textY = node.y + fm.getAscent() / 2;
        g2d.drawString(value, textX, textY);
    }
    
    private void drawConstructionStack(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(new Color(51, 51, 51));
        g2d.drawString("构建栈:", 700, 200);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        int startY = 220;
        
        if (constructionStack.isEmpty()) {
            g2d.setColor(new Color(128, 128, 128));
            g2d.drawString("栈为空", 700, startY);
        } else {
            for (int i = constructionStack.size() - 1; i >= 0; i--) {
                ArrayRange range = constructionStack.get(i);
                g2d.setColor(new Color(0, 100, 0));
                String stackItem = String.format("[%d, %d] → %d", range.left, range.right, nums[range.mid]);
                g2d.drawString(stackItem, 700, startY + (constructionStack.size() - 1 - i) * 20);
            }
        }
    }
    
    private void drawConstructionSteps(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(new Color(51, 51, 51));
        g2d.drawString("构建步骤:", 50, 370);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        int startY = 390;
        int maxSteps = Math.min(constructionSteps.size(), 4); // 只显示最近的4步
        int startIndex = Math.max(0, constructionSteps.size() - maxSteps);
        
        for (int i = startIndex; i < constructionSteps.size(); i++) {
            g2d.setColor(new Color(0, 100, 0));
            g2d.drawString((i + 1) + ". " + constructionSteps.get(i), 50, startY + (i - startIndex) * 18);
        }
    }
    
    private void drawAlgorithmSteps(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(new Color(51, 51, 51));
        
        String[] steps = {
            "有序数组转BST算法:",
            "1. 选择数组中间元素作为根节点",
            "2. 递归构建左子树（左半部分）",
            "3. 递归构建右子树（右半部分）",
            "4. 保证树的高度平衡",
            "",
            "分治策略:",
            "mid = left + (right - left) / 2"
        };
        
        int startY = 550;
        for (int i = 0; i < steps.length; i++) {
            if (i == 0 || i == 6) {
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
        g2d.drawString("空间复杂度: O(log n)", 800, 600);
        g2d.drawString("核心技术: 分治法", 800, 620);
    }
    
    private int[] buildTestCase(int caseIndex) {
        switch (caseIndex) {
            case 0:
                return new int[]{-10, -3, 0, 5, 9};
            case 1:
                return new int[]{1, 3};
            case 2:
                return new int[]{1, 2, 3, 4, 5, 6, 7};
            case 3:
                return new int[]{1};
            case 4:
                return new int[]{1, 2, 3, 4, 5};
            default:
                return new int[]{};
        }
    }
    
    private void loadTestCase(int index) {
        nums = buildTestCase(index);
        root = null;
        currentNode = null;
        currentRange = null;
        isCompleted = false;
        constructionMessage = "";
        constructionSteps.clear();
        constructionStack.clear();
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
            new NO108_E_SortedArrayToBST_Animation().setVisible(true);
        });
    }
}