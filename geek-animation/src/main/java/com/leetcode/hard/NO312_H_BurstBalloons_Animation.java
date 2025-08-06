package com.leetcode.hard;

import com.animation.Animation;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * NO.312 戳气球 - 区间动态规划动画演示
 * 可视化展示区间DP的计算过程和最优解的构建
 */
public class NO312_H_BurstBalloons_Animation extends JFrame implements Animation {
    
    // 动画控制
    private Timer animationTimer;
    private int currentStep = 0;
    private boolean isAnimating = false;
    
    // 算法数据
    private int[] originalNums;
    private int[] points;  // 添加边界1后的数组
    private int[][] dp;
    private int n;
    private int maxCoins = 0;
    
    // 动画状态
    private int currentI = -1;
    private int currentJ = -1;
    private int currentK = -1;
    private int currentValue = 0;
    private List<AnimationStep> steps;
    
    // UI组件
    private JButton startButton;
    private JButton pauseButton;
    private JButton resetButton;
    private JButton stepButton;
    private JSlider speedSlider;
    private JLabel statusLabel;
    private JLabel resultLabel;
    
    // 动画步骤类
    private static class AnimationStep {
        int i, j, k;
        int value;
        String description;
        
        AnimationStep(int i, int j, int k, int value, String description) {
            this.i = i;
            this.j = j;
            this.k = k;
            this.value = value;
            this.description = description;
        }
    }
    
    public NO312_H_BurstBalloons_Animation() {
        this.originalNums = new int[]{3, 1, 5, 8}; // 默认示例
        initializeAlgorithm();
        initializeUI();
        setupLayout();
        setupEventHandlers();
    }
    
    public NO312_H_BurstBalloons_Animation(int[] nums) {
        this.originalNums = nums.clone();
        initializeAlgorithm();
        initializeUI();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initializeAlgorithm() {
        n = originalNums.length;
        points = new int[n + 2];
        points[0] = 1;
        points[n + 1] = 1;
        System.arraycopy(originalNums, 0, points, 1, n);
        
        dp = new int[n + 2][n + 2];
        steps = new ArrayList<>();
        generateSteps();
    }
    
    private void generateSteps() {
        steps.clear();
        int[][] tempDp = new int[n + 2][n + 2];
        
        for (int i = n; i >= 0; i--) {
            for (int j = i + 2; j <= n + 1; j++) {
                for (int k = i + 1; k < j; k++) {
                    int value = tempDp[i][k] + tempDp[k][j] + points[i] * points[k] * points[j];
                    if (value > tempDp[i][j]) {
                        tempDp[i][j] = value;
                        String desc = String.format("区间[%d,%d]，最后戳破气球%d，获得%d*%d*%d=%d硬币", 
                                                   i, j, k, points[i], points[k], points[j], 
                                                   points[i] * points[k] * points[j]);
                        steps.add(new AnimationStep(i, j, k, value, desc));
                    }
                }
            }
        }
        maxCoins = tempDp[0][n + 1];
    }
    
    private void initializeUI() {
        setTitle("NO.312 戳气球 - 区间动态规划动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        // 控制按钮
        startButton = new JButton("开始动画");
        pauseButton = new JButton("暂停");
        resetButton = new JButton("重置");
        stepButton = new JButton("单步执行");
        
        // 速度控制
        speedSlider = new JSlider(1, 10, 5);
        speedSlider.setMajorTickSpacing(3);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        
        // 状态标签
        statusLabel = new JLabel("准备开始动画演示");
        resultLabel = new JLabel("最大硬币数: 待计算");
        
        // 动画定时器
        animationTimer = new Timer(1000, e -> nextStep());
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 控制面板
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(new JLabel("控制:"));
        controlPanel.add(startButton);
        controlPanel.add(pauseButton);
        controlPanel.add(resetButton);
        controlPanel.add(stepButton);
        controlPanel.add(new JLabel("速度:"));
        controlPanel.add(speedSlider);
        
        // 状态面板
        JPanel statusPanel = new JPanel(new GridLayout(2, 1));
        statusPanel.add(statusLabel);
        statusPanel.add(resultLabel);
        
        // 主绘制面板
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawAnimation(g);
            }
        };
        mainPanel.setBackground(Color.WHITE);
        
        add(controlPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        startButton.addActionListener(e -> startAnimation());
        pauseButton.addActionListener(e -> pauseAnimation());
        resetButton.addActionListener(e -> resetAnimation());
        stepButton.addActionListener(e -> nextStep());
        speedSlider.addChangeListener(e -> {
            int speed = speedSlider.getValue();
            animationTimer.setDelay(1100 - speed * 100);
        });
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制标题
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 20));
        g2d.setColor(Color.BLACK);
        g2d.drawString("NO.312 戳气球 - 区间动态规划", 20, 30);
        
        // 绘制原始气球数组
        drawBalloons(g2d);
        
        // 绘制DP表
        drawDPTable(g2d);
        
        // 绘制当前计算信息
        drawCurrentCalculation(g2d);
    }
    
    private void drawBalloons(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g2d.drawString("原始气球数组:", 20, 70);
        
        // 绘制边界气球(虚拟的1)
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillOval(20, 80, 40, 40);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(20, 80, 40, 40);
        g2d.drawString("1", 37, 105);
        
        // 绘制实际气球
        for (int i = 0; i < originalNums.length; i++) {
            Color balloonColor = Color.ORANGE;
            if (currentK == i + 1) {
                balloonColor = Color.RED; // 当前考虑戳破的气球
            }
            
            g2d.setColor(balloonColor);
            g2d.fillOval(70 + i * 50, 80, 40, 40);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(70 + i * 50, 80, 40, 40);
            g2d.drawString(String.valueOf(originalNums[i]), 85 + i * 50, 105);
        }
        
        // 绘制边界气球(虚拟的1)
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillOval(70 + originalNums.length * 50, 80, 40, 40);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(70 + originalNums.length * 50, 80, 40, 40);
        g2d.drawString("1", 87 + originalNums.length * 50, 105);
    }
    
    private void drawDPTable(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g2d.drawString("DP表 (dp[i][j]表示戳破区间(i,j)内所有气球的最大硬币数):", 20, 160);
        
        int cellSize = 50;
        int startX = 20;
        int startY = 180;
        
        // 绘制表格
        for (int i = 0; i <= n + 1; i++) {
            for (int j = 0; j <= n + 1; j++) {
                int x = startX + j * cellSize;
                int y = startY + i * cellSize;
                
                // 背景颜色
                 if (i == currentI && j == currentJ) {
                     g2d.setColor(Color.YELLOW); // 当前计算的格子
                 } else if (dp[i][j] > 0) {
                     g2d.setColor(new Color(144, 238, 144)); // 已计算的格子 (浅绿色)
                 } else {
                     g2d.setColor(Color.WHITE);
                 }
                
                g2d.fillRect(x, y, cellSize, cellSize);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y, cellSize, cellSize);
                
                // 绘制值
                if (j > i + 1) { // 只有有效的区间才显示值
                    g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                    String value = String.valueOf(dp[i][j]);
                    FontMetrics fm = g2d.getFontMetrics();
                    int textX = x + (cellSize - fm.stringWidth(value)) / 2;
                    int textY = y + (cellSize + fm.getAscent()) / 2;
                    g2d.drawString(value, textX, textY);
                }
                
                // 绘制坐标标签
                if (i == 0) {
                    g2d.setFont(new Font("微软雅黑", Font.BOLD, 10));
                    g2d.drawString("j=" + j, x + 2, y - 5);
                }
                if (j == 0) {
                    g2d.setFont(new Font("微软雅黑", Font.BOLD, 10));
                    g2d.drawString("i=" + i, x - 25, y + 25);
                }
            }
        }
    }
    
    private void drawCurrentCalculation(Graphics2D g2d) {
        if (currentStep < steps.size()) {
            AnimationStep step = steps.get(currentStep);
            
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            g2d.setColor(Color.BLUE);
            
            int y = 180 + (n + 2) * 50 + 30;
            g2d.drawString("当前计算:", 20, y);
            g2d.drawString(step.description, 20, y + 20);
            g2d.drawString(String.format("dp[%d][%d] = %d", step.i, step.j, step.value), 20, y + 40);
            
            // 绘制状态转移方程
            g2d.setColor(Color.RED);
            g2d.drawString("状态转移: dp[i][j] = max(dp[i][k] + dp[k][j] + points[i]*points[k]*points[j])", 20, y + 60);
        }
    }
    
    @Override
    public void start() {
        startAnimation();
    }
    
    public void startAnimation() {
        if (!isAnimating) {
            isAnimating = true;
            animationTimer.start();
            startButton.setText("运行中...");
            statusLabel.setText("动画运行中...");
        }
    }
    
    public void pauseAnimation() {
        if (isAnimating) {
            isAnimating = false;
            animationTimer.stop();
            startButton.setText("继续");
            statusLabel.setText("动画已暂停");
        }
    }
    
    public void resetAnimation() {
        animationTimer.stop();
        isAnimating = false;
        currentStep = 0;
        currentI = -1;
        currentJ = -1;
        currentK = -1;
        
        // 重置DP表
        for (int i = 0; i <= n + 1; i++) {
            for (int j = 0; j <= n + 1; j++) {
                dp[i][j] = 0;
            }
        }
        
        startButton.setText("开始动画");
        statusLabel.setText("动画已重置");
        resultLabel.setText("最大硬币数: 待计算");
        SwingUtilities.invokeLater(() -> repaint());
    }
    
    private void nextStep() {
        if (currentStep < steps.size()) {
            AnimationStep step = steps.get(currentStep);
            currentI = step.i;
            currentJ = step.j;
            currentK = step.k;
            dp[step.i][step.j] = step.value;
            
            statusLabel.setText(String.format("步骤 %d/%d: %s", currentStep + 1, steps.size(), step.description));
            currentStep++;
            
            SwingUtilities.invokeLater(() -> repaint());
        } else {
            // 动画结束
            animationTimer.stop();
            isAnimating = false;
            startButton.setText("开始动画");
            statusLabel.setText("动画完成!");
            resultLabel.setText("最大硬币数: " + maxCoins);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO312_H_BurstBalloons_Animation().setVisible(true);
        });
    }
}