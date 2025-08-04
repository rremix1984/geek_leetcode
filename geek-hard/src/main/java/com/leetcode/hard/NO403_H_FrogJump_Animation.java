package com.leetcode.hard;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * NO.403 青蛙过河 - 动画演示
 * 
 * 设计文档：
 * 1. 功能需求：可视化青蛙跳跃过程，展示动态规划状态转移
 * 2. 核心算法：动态规划 + 状态压缩
 * 3. 动画特色：
 *    - 实时显示青蛙位置和可能的跳跃步长
 *    - 可视化DP状态表
 *    - 动态展示跳跃路径
 *    - 支持自定义石子位置
 * 4. 技术实现：Swing + 2D Graphics + Timer动画
 * 5. 用户交互：开始/暂停/重置/单步执行/自定义输入
 * 
 * @author 开发工程师
 * @version 1.0
 */
public class NO403_H_FrogJump_Animation extends JFrame {
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final int ANIMATION_DELAY = 1500;
    
    // 动画控制
    private Timer animationTimer;
    private boolean isAnimating = false;
    private int currentStep = 0;
    
    // 算法相关
    private int[] stones;
    private boolean[][] dp;
    private int n;
    private List<String> animationSteps;
    private List<Point> jumpPath;
    private int currentFrogPosition = 0;
    private int currentJumpStep = 1;
    private boolean canCrossResult = false;
    
    // UI组件
    private JPanel controlPanel;
    private JPanel visualPanel;
    private JButton startButton;
    private JButton pauseButton;
    private JButton resetButton;
    private JButton nextStepButton;
    private JTextField inputField;
    private JButton customInputButton;
    private JLabel statusLabel;
    private JTextArea stepsArea;
    private JScrollPane stepsScrollPane;
    
    // 绘图相关
    private final Color WATER_COLOR = new Color(173, 216, 230);
    private final Color STONE_COLOR = new Color(105, 105, 105);
    private final Color FROG_COLOR = new Color(34, 139, 34);
    private final Color PATH_COLOR = new Color(255, 215, 0);
    private final Color HIGHLIGHT_COLOR = new Color(255, 0, 0);
    
    public NO403_H_FrogJump_Animation() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        initializeDefaultData();
        resetAnimation();
    }
    
    private void initializeComponents() {
        setTitle("NO.403 青蛙过河 - 动画演示");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // 控制面板组件
        controlPanel = new JPanel();
        startButton = new JButton("开始动画");
        pauseButton = new JButton("暂停");
        resetButton = new JButton("重置");
        nextStepButton = new JButton("单步执行");
        inputField = new JTextField("0,1,3,5,6,8,12,17", 20);
        customInputButton = new JButton("自定义输入");
        statusLabel = new JLabel("准备开始动画演示");
        
        // 可视化面板
        visualPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawAnimation(g);
            }
        };
        visualPanel.setBackground(Color.WHITE);
        visualPanel.setPreferredSize(new Dimension(800, 400));
        
        // 步骤显示区域
        stepsArea = new JTextArea(10, 30);
        stepsArea.setEditable(false);
        stepsArea.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        stepsScrollPane = new JScrollPane(stepsArea);
        
        // 初始状态
        pauseButton.setEnabled(false);
        nextStepButton.setEnabled(true);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 控制面板布局
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JLabel("石子位置:"));
        controlPanel.add(inputField);
        controlPanel.add(customInputButton);
        controlPanel.add(Box.createHorizontalStrut(20));
        controlPanel.add(startButton);
        controlPanel.add(pauseButton);
        controlPanel.add(resetButton);
        controlPanel.add(nextStepButton);
        
        // 主面板布局
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(visualPanel, BorderLayout.CENTER);
        
        // 右侧信息面板
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder("算法步骤"));
        infoPanel.add(stepsScrollPane, BorderLayout.CENTER);
        infoPanel.setPreferredSize(new Dimension(350, 400));
        
        // 状态面板
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.add(statusLabel);
        
        add(controlPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.EAST);
        add(statusPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        startButton.addActionListener(e -> startAnimation());
        pauseButton.addActionListener(e -> pauseAnimation());
        resetButton.addActionListener(e -> resetAnimation());
        nextStepButton.addActionListener(e -> nextStep());
        customInputButton.addActionListener(e -> setCustomInput());
        
        // 动画定时器
        animationTimer = new Timer(ANIMATION_DELAY, e -> nextStep());
    }
    
    private void initializeDefaultData() {
        stones = new int[]{0, 1, 3, 5, 6, 8, 12, 17};
        n = stones.length;
        animationSteps = new ArrayList<>();
        jumpPath = new ArrayList<>();
    }
    
    private void startAnimation() {
        if (!isAnimating) {
            isAnimating = true;
            startButton.setEnabled(false);
            pauseButton.setEnabled(true);
            nextStepButton.setEnabled(false);
            statusLabel.setText("动画进行中...");
            
            // 初始化算法
            initializeAlgorithm();
            animationTimer.start();
        }
    }
    
    private void pauseAnimation() {
        if (isAnimating) {
            isAnimating = false;
            animationTimer.stop();
            startButton.setEnabled(true);
            pauseButton.setEnabled(false);
            nextStepButton.setEnabled(true);
            statusLabel.setText("动画已暂停");
        }
    }
    
    private void resetAnimation() {
        animationTimer.stop();
        isAnimating = false;
        currentStep = 0;
        currentFrogPosition = 0;
        currentJumpStep = 1;
        canCrossResult = false;
        
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        nextStepButton.setEnabled(true);
        statusLabel.setText("准备开始动画演示");
        
        animationSteps.clear();
        jumpPath.clear();
        stepsArea.setText("");
        
        // 重新初始化算法
        initializeAlgorithm();
        visualPanel.repaint();
    }
    
    private void nextStep() {
        if (currentStep < animationSteps.size()) {
            String step = animationSteps.get(currentStep);
            stepsArea.append(step + "\n");
            stepsArea.setCaretPosition(stepsArea.getDocument().getLength());
            
            // 解析步骤并更新可视化状态
            parseAnimationStep(step);
            
            currentStep++;
            visualPanel.repaint();
            
            if (currentStep >= animationSteps.size()) {
                // 动画结束
                animationTimer.stop();
                isAnimating = false;
                startButton.setEnabled(true);
                pauseButton.setEnabled(false);
                nextStepButton.setEnabled(false);
                statusLabel.setText("动画演示完成 - 结果: " + (canCrossResult ? "可以过河" : "无法过河"));
            }
        }
    }
    
    private void setCustomInput() {
        try {
            String input = inputField.getText().trim();
            String[] parts = input.split(",");
            int[] newStones = new int[parts.length];
            
            for (int i = 0; i < parts.length; i++) {
                newStones[i] = Integer.parseInt(parts[i].trim());
            }
            
            // 验证输入有效性
            if (newStones.length < 2 || newStones[0] != 0) {
                JOptionPane.showMessageDialog(this, "输入无效！第一个石子必须在位置0", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // 检查是否升序
            for (int i = 1; i < newStones.length; i++) {
                if (newStones[i] <= newStones[i-1]) {
                    JOptionPane.showMessageDialog(this, "石子位置必须严格递增！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            stones = newStones;
            n = stones.length;
            resetAnimation();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "输入格式错误！请使用逗号分隔的数字", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void initializeAlgorithm() {
        n = stones.length;
        dp = new boolean[n][n];
        animationSteps.clear();
        jumpPath.clear();
        
        animationSteps.add("=== 青蛙过河算法开始 ===");
        animationSteps.add("石子位置: " + Arrays.toString(stones));
        animationSteps.add("初始化DP数组: dp[i][k]表示能否以步长k跳到第i个石子");
        animationSteps.add("设置初始状态: dp[0][0] = true (青蛙在第一个石子上)");
        
        // 执行算法
        canCrossResult = canCross();
        
        animationSteps.add("=== 算法执行完成 ===");
        animationSteps.add("最终结果: " + (canCrossResult ? "青蛙可以成功过河！" : "青蛙无法过河"));
    }
    
    private boolean canCross() {
        dp[0][0] = true;
        
        for (int i = 1; i < n; i++) {
            animationSteps.add("检查第" + i + "个石子 (位置" + stones[i] + "):");
            
            for (int j = i - 1; j >= 0; j--) {
                int k = stones[i] - stones[j]; // 从石子j跳到石子i需要的步长
                
                if (k > j + 1) {
                    animationSteps.add("  从石子" + j + "跳到石子" + i + "需要步长" + k + " > " + (j+1) + "，跳跃距离过大，跳出内层循环");
                    break;
                }
                
                animationSteps.add("  尝试从石子" + j + "(位置" + stones[j] + ")跳到石子" + i + "(位置" + stones[i] + ")，需要步长" + k);
                
                // 检查三种可能的前一步长：k-1, k, k+1
                boolean canJump = false;
                if (k >= 1 && k <= j + 1) {
                    if ((k-1 >= 0 && dp[j][k-1]) || dp[j][k] || (k+1 < n && dp[j][k+1])) {
                        dp[i][k] = true;
                        canJump = true;
                        animationSteps.add("    ✓ 可以跳跃！设置dp[" + i + "][" + k + "] = true");
                        
                        // 记录跳跃路径
                        jumpPath.add(new Point(j, i));
                        
                        if (i == n - 1) {
                            animationSteps.add("    ★ 到达最后一个石子！");
                            return true;
                        }
                    }
                }
                
                if (!canJump) {
                    animationSteps.add("    ✗ 无法跳跃，dp[" + i + "][" + k + "] = false");
                }
            }
        }
        
        return false;
    }
    
    private void parseAnimationStep(String step) {
        // 解析动画步骤，更新青蛙位置等状态
        if (step.contains("检查第") && step.contains("个石子")) {
            try {
                int pos = step.indexOf("检查第") + 3;
                int endPos = step.indexOf("个石子");
                if (pos < endPos) {
                    int stoneIndex = Integer.parseInt(step.substring(pos, endPos));
                    currentFrogPosition = stoneIndex;
                }
            } catch (Exception e) {
                // 忽略解析错误
            }
        }
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int panelWidth = visualPanel.getWidth();
        int panelHeight = visualPanel.getHeight();
        
        // 绘制背景（河流）
        g2d.setColor(WATER_COLOR);
        g2d.fillRect(0, panelHeight/2 - 50, panelWidth, 100);
        
        if (stones == null || stones.length == 0) return;
        
        // 计算石子绘制位置
        int maxStonePos = stones[stones.length - 1];
        int startX = 50;
        int endX = panelWidth - 50;
        int riverY = panelHeight / 2;
        
        // 绘制石子
        for (int i = 0; i < stones.length; i++) {
            int x = startX + (endX - startX) * stones[i] / maxStonePos;
            int y = riverY;
            
            // 石子
            g2d.setColor(STONE_COLOR);
            g2d.fillOval(x - 20, y - 20, 40, 40);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(x - 20, y - 20, 40, 40);
            
            // 石子编号
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            FontMetrics fm = g2d.getFontMetrics();
            String label = String.valueOf(i);
            int labelWidth = fm.stringWidth(label);
            g2d.drawString(label, x - labelWidth/2, y + 5);
            
            // 石子位置
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            String posLabel = "(" + stones[i] + ")";
            int posWidth = fm.stringWidth(posLabel);
            g2d.drawString(posLabel, x - posWidth/2, y + 35);
        }
        
        // 绘制青蛙
        if (currentFrogPosition < stones.length) {
            int frogX = startX + (endX - startX) * stones[currentFrogPosition] / maxStonePos;
            int frogY = riverY - 40;
            
            g2d.setColor(FROG_COLOR);
            g2d.fillOval(frogX - 15, frogY - 15, 30, 30);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(frogX - 15, frogY - 15, 30, 30);
            
            // 青蛙眼睛
            g2d.setColor(Color.WHITE);
            g2d.fillOval(frogX - 10, frogY - 10, 8, 8);
            g2d.fillOval(frogX + 2, frogY - 10, 8, 8);
            g2d.setColor(Color.BLACK);
            g2d.fillOval(frogX - 8, frogY - 8, 4, 4);
            g2d.fillOval(frogX + 4, frogY - 8, 4, 4);
        }
        
        // 绘制跳跃路径
        g2d.setColor(PATH_COLOR);
        g2d.setStroke(new BasicStroke(3));
        for (Point jump : jumpPath) {
            int fromX = startX + (endX - startX) * stones[jump.x] / maxStonePos;
            int toX = startX + (endX - startX) * stones[jump.y] / maxStonePos;
            int fromY = riverY - 20;
            int toY = riverY - 20;
            
            // 绘制弧形跳跃路径
            int midX = (fromX + toX) / 2;
            int midY = fromY - 30;
            
            g2d.drawLine(fromX, fromY, midX, midY);
            g2d.drawLine(midX, midY, toX, toY);
            
            // 箭头
            drawArrow(g2d, midX, midY, toX, toY);
        }
        
        // 绘制DP状态表（简化版）
        drawDPTable(g2d, panelWidth - 300, 50);
    }
    
    private void drawArrow(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        int arrowLength = 8;
        double angle = Math.atan2(y2 - y1, x2 - x1);
        
        int arrowX1 = (int) (x2 - arrowLength * Math.cos(angle - Math.PI / 6));
        int arrowY1 = (int) (y2 - arrowLength * Math.sin(angle - Math.PI / 6));
        int arrowX2 = (int) (x2 - arrowLength * Math.cos(angle + Math.PI / 6));
        int arrowY2 = (int) (y2 - arrowLength * Math.sin(angle + Math.PI / 6));
        
        g2d.drawLine(x2, y2, arrowX1, arrowY1);
        g2d.drawLine(x2, y2, arrowX2, arrowY2);
    }
    
    private void drawDPTable(Graphics2D g2d, int startX, int startY) {
        if (dp == null) return;
        
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));
        g2d.drawString("DP状态表 (部分)", startX, startY - 10);
        
        int cellSize = 20;
        int maxShow = Math.min(8, n); // 最多显示8行
        
        for (int i = 0; i < maxShow; i++) {
            for (int j = 0; j < Math.min(8, n); j++) {
                int x = startX + j * cellSize;
                int y = startY + i * cellSize;
                
                if (dp[i][j]) {
                    g2d.setColor(Color.GREEN);
                    g2d.fillRect(x, y, cellSize, cellSize);
                } else {
                    g2d.setColor(Color.LIGHT_GRAY);
                    g2d.fillRect(x, y, cellSize, cellSize);
                }
                
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y, cellSize, cellSize);
                
                if (dp[i][j]) {
                    g2d.drawString("T", x + 6, y + 14);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO403_H_FrogJump_Animation().setVisible(true);
        });
    }
}