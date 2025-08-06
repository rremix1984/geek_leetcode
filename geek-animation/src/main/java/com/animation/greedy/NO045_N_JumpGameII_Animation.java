package com.animation.greedy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 45. 跳跃游戏 II (Jump Game II) 动画演示
 * 
 * 问题描述：
 * 给你一个非负整数数组 nums，你最初位于数组的第一个位置。
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
 * 假设你总是可以到达数组的最后一个位置。
 * 
 * 算法思路：
 * 使用贪心算法
 * 1. 维护当前能到达的最远位置 maxReach
 * 2. 维护当前跳跃的边界 reach
 * 3. 当到达边界时，必须进行下一次跳跃
 * 4. 每次跳跃都选择能到达最远位置的策略
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class NO045_N_JumpGameII_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    private static final int CELL_SIZE = 80;
    private static final int START_X = 50;
    private static final int START_Y = 250;
    
    // 算法相关变量
    private int[] nums = {2, 3, 1, 1, 4};
    private int currentPosition = 0;
    private int targetPosition = 0;
    private int jumpCount = 0;
    private int maxReach = 0;
    private int reach = 0;
    private List<Integer> jumpPath;
    
    // 动画相关变量
    private double animationProgress = 0;
    private boolean isAnimating = false;
    private javax.swing.Timer animationTimer;
    private boolean algorithmComplete = false;
    
    // UI组件
    private JButton startButton;
    private JButton resetButton;
    private JButton stepButton;
    private JButton backButton;
    private JTextArea logArea;
    private JLabel statusLabel;
    private JPanel drawPanel;
    
    public NO045_N_JumpGameII_Animation() {
        initializeUI();
        initializeAlgorithm();
    }
    
    private void initializeUI() {
        setTitle("LeetCode 45. 跳跃游戏 II (Jump Game II) - 贪心算法动画演示");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // 设置Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        setLayout(new BorderLayout());
        
        // 创建绘图面板
        drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawAnimation(g);
            }
        };
        drawPanel.setBackground(Color.WHITE);
        drawPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, 400));
        
        // 创建控制面板
        JPanel controlPanel = createControlPanel();
        
        // 创建信息面板
        JPanel infoPanel = createInfoPanel();
        
        add(drawPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        add(infoPanel, BorderLayout.EAST);
        
        // 设置动画定时器
        animationTimer = new javax.swing.Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isAnimating) {
                    animationProgress += 0.05;
                    if (animationProgress >= 1.0) {
                        animationProgress = 1.0;
                        currentPosition = targetPosition;
                        isAnimating = false;
                        
                        if (currentPosition >= nums.length - 1) {
                            algorithmComplete = true;
                            updateLog("算法完成！最少跳跃次数：" + jumpCount);
                            statusLabel.setText("算法完成 - 最少跳跃次数：" + jumpCount);
                            animationTimer.stop();
                        }
                    }
                    SwingUtilities.invokeLater(() -> drawPanel.repaint());
                }
            }
        });
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        startButton = new JButton("开始动画");
        resetButton = new JButton("重置");
        stepButton = new JButton("单步执行");
        
        startButton.addActionListener(e -> startAnimation());
        resetButton.addActionListener(e -> resetAnimation());
        stepButton.addActionListener(e -> stepAnimation());
        
        backButton = new JButton("返回首页");
        backButton.addActionListener(e -> {
            dispose(); // 关闭当前窗口
            com.animation.launcher.AlgorithmTreeLauncher.showMainWindow(); // 显示首页
        });
        
        panel.add(startButton);
        panel.add(stepButton);
        panel.add(resetButton);
        panel.add(backButton);
        
        statusLabel = new JLabel("准备开始跳跃游戏II动画演示");
        panel.add(statusLabel);
        
        return panel;
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(300, 0));
        
        JLabel titleLabel = new JLabel("算法信息", JLabel.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void initializeAlgorithm() {
        currentPosition = 0;
        targetPosition = 0;
        jumpCount = 0;
        maxReach = 0;
        reach = 0;
        jumpPath = new ArrayList<>();
        jumpPath.add(0);
        algorithmComplete = false;
        
        updateLog("初始化完成");
        updateLog("数组: [" + arrayToString() + "]");
        updateLog("目标: 用最少跳跃次数到达最后位置");
        updateLog("算法: 贪心算法 - 每次跳跃选择能到达最远的位置");
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制数组
        drawArray(g2d);
        
        // 绘制跳跃范围
        drawJumpRanges(g2d);
        
        // 绘制路径
        drawPath(g2d);
        
        // 绘制玩家
        drawPlayer(g2d);
        
        // 绘制算法信息
        drawAlgorithmInfo(g2d);
    }
    
    private void drawArray(Graphics2D g2d) {
        for (int i = 0; i < nums.length; i++) {
            int x = START_X + i * CELL_SIZE;
            int y = START_Y;
            
            // 背景颜色
            if (i == nums.length - 1) {
                g2d.setColor(Color.YELLOW); // 目标位置
            } else if (i == currentPosition) {
                g2d.setColor(Color.LIGHT_GRAY); // 当前位置
            } else if (i <= reach) {
                g2d.setColor(new Color(200, 255, 200)); // 当前跳跃边界内
            } else if (i <= maxReach) {
                g2d.setColor(new Color(255, 255, 200)); // 下次跳跃可达
            } else {
                g2d.setColor(Color.WHITE); // 未到达
            }
            
            g2d.fillRect(x, y, CELL_SIZE, CELL_SIZE);
            
            // 边框
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, CELL_SIZE, CELL_SIZE);
            
            // 数值
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
            String text = String.valueOf(nums[i]);
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (CELL_SIZE - fm.stringWidth(text)) / 2;
            int textY = y + CELL_SIZE / 2 + fm.getAscent() / 2;
            g2d.drawString(text, textX, textY);
            
            // 索引
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.setColor(Color.GRAY);
            g2d.drawString(String.valueOf(i), x + 5, y + 15);
        }
    }
    
    private void drawJumpRanges(Graphics2D g2d) {
        if (!isAnimating && currentPosition < nums.length) {
            // 当前位置可跳跃范围
            g2d.setColor(new Color(0, 255, 0, 100));
            for (int i = 1; i <= nums[currentPosition] && currentPosition + i < nums.length; i++) {
                int x = START_X + (currentPosition + i) * CELL_SIZE;
                int y = START_Y;
                g2d.fillRect(x, y, CELL_SIZE, CELL_SIZE);
            }
            
            // 绘制边界线
            if (reach < nums.length - 1) {
                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(3));
                int boundaryX = START_X + (reach + 1) * CELL_SIZE;
                g2d.drawLine(boundaryX, START_Y - 20, boundaryX, START_Y + CELL_SIZE + 20);
                g2d.drawString("跳跃边界", boundaryX + 5, START_Y - 5);
            }
        }
    }
    
    private void drawPath(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(4));
        g2d.setColor(Color.BLUE);
        
        for (int i = 0; i < jumpPath.size() - 1; i++) {
            int fromX = START_X + jumpPath.get(i) * CELL_SIZE + CELL_SIZE / 2;
            int fromY = START_Y + CELL_SIZE / 2;
            int toX = START_X + jumpPath.get(i + 1) * CELL_SIZE + CELL_SIZE / 2;
            int toY = START_Y + CELL_SIZE / 2;
            
            // 绘制弧线
            int midX = (fromX + toX) / 2;
            int midY = fromY - 40;
            
            drawBezierCurve(g2d, fromX, fromY, midX, midY, toX, toY);
            
            // 绘制箭头
            drawArrow(g2d, toX, toY);
        }
    }
    
    private void drawBezierCurve(Graphics2D g2d, int x1, int y1, int x2, int y2, int x3, int y3) {
        for (double t = 0; t <= 1; t += 0.02) {
            double x = (1-t)*(1-t)*x1 + 2*(1-t)*t*x2 + t*t*x3;
            double y = (1-t)*(1-t)*y1 + 2*(1-t)*t*y2 + t*t*y3;
            g2d.fillOval((int)x-2, (int)y-2, 4, 4);
        }
    }
    
    private void drawArrow(Graphics2D g2d, int x, int y) {
        int[] arrowX = {x, x-8, x+8};
        int[] arrowY = {y-15, y-25, y-25};
        g2d.fillPolygon(arrowX, arrowY, 3);
    }
    
    private void drawPlayer(Graphics2D g2d) {
        double currentX, currentY;
        
        if (isAnimating) {
            double startPosX = START_X + currentPosition * CELL_SIZE + CELL_SIZE / 2;
            double endPosX = START_X + targetPosition * CELL_SIZE + CELL_SIZE / 2;
            currentX = startPosX + (endPosX - startPosX) * animationProgress;
            
            double jumpHeight = Math.sin(animationProgress * Math.PI) * 40;
            currentY = START_Y + CELL_SIZE / 2 - jumpHeight;
        } else {
            currentX = START_X + currentPosition * CELL_SIZE + CELL_SIZE / 2;
            currentY = START_Y + CELL_SIZE / 2;
        }
        
        // 绘制玩家
        g2d.setColor(Color.RED);
        g2d.fillOval((int)currentX - 15, (int)currentY - 30, 30, 30);
        
        // 绘制眼睛
        g2d.setColor(Color.WHITE);
        g2d.fillOval((int)currentX - 8, (int)currentY - 25, 6, 6);
        g2d.fillOval((int)currentX + 2, (int)currentY - 25, 6, 6);
        
        g2d.setColor(Color.BLACK);
        g2d.fillOval((int)currentX - 6, (int)currentY - 23, 2, 2);
        g2d.fillOval((int)currentX + 4, (int)currentY - 23, 2, 2);
    }
    
    private void drawAlgorithmInfo(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.drawString("跳跃游戏 II - 贪心算法", 20, 30);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.drawString("当前位置: " + currentPosition, 20, 60);
        g2d.drawString("跳跃次数: " + jumpCount, 150, 60);
        g2d.drawString("当前边界: " + reach, 280, 60);
        g2d.drawString("最远可达: " + maxReach, 400, 60);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawString("绿色区域: 当前位置可跳跃范围", 20, 400);
        g2d.drawString("浅绿色: 当前跳跃边界内", 20, 420);
        g2d.drawString("浅黄色: 下次跳跃可达", 20, 440);
        g2d.drawString("红线: 跳跃边界", 20, 460);
    }
    
    private void startAnimation() {
        if (!animationTimer.isRunning()) {
            animationTimer.start();
        }
        
        javax.swing.Timer autoTimer = new javax.swing.Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAnimating && !algorithmComplete) {
                    stepAnimation();
                } else if (algorithmComplete) {
                    ((javax.swing.Timer) e.getSource()).stop();
                }
            }
        });
        autoTimer.start();
    }
    
    private void stepAnimation() {
        if (isAnimating || algorithmComplete) return;
        
        // 贪心算法核心逻辑
        maxReach = Math.max(maxReach, currentPosition + nums[currentPosition]);
        
        updateLog("位置 " + currentPosition + ": 值=" + nums[currentPosition] + 
                 ", 可达最远位置=" + (currentPosition + nums[currentPosition]));
        updateLog("更新最远可达位置: " + maxReach);
        
        if (currentPosition == reach) {
            // 到达边界，需要跳跃
            reach = maxReach;
            jumpCount++;
            
            // 选择下一个跳跃位置（贪心选择）
            int bestPosition = currentPosition + 1;
            int bestReach = bestPosition + nums[bestPosition];
            
            for (int i = currentPosition + 1; i <= Math.min(currentPosition + nums[currentPosition], nums.length - 1); i++) {
                if (i + nums[i] > bestReach) {
                    bestReach = i + nums[i];
                    bestPosition = i;
                }
            }
            
            targetPosition = bestPosition;
            jumpPath.add(targetPosition);
            
            updateLog("到达边界，进行第 " + jumpCount + " 次跳跃");
            updateLog("选择跳跃到位置 " + targetPosition + " (能到达最远位置 " + bestReach + ")");
            updateLog("新的跳跃边界: " + reach);
            
            animationProgress = 0;
            isAnimating = true;
            
            statusLabel.setText("第 " + jumpCount + " 次跳跃: " + currentPosition + " → " + targetPosition);
        } else {
            // 继续前进但不跳跃
            currentPosition++;
            updateLog("前进到位置 " + currentPosition + " (未到达跳跃边界)");
            statusLabel.setText("前进到位置 " + currentPosition);
            SwingUtilities.invokeLater(() -> drawPanel.repaint());
        }
    }
    
    private void resetAnimation() {
        if (animationTimer.isRunning()) {
            animationTimer.stop();
        }
        
        initializeAlgorithm();
        animationProgress = 0;
        isAnimating = false;
        statusLabel.setText("已重置，准备开始新的演示");
        SwingUtilities.invokeLater(() -> drawPanel.repaint());
    }
    
    private void updateLog(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private String arrayToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            sb.append(nums[i]);
            if (i < nums.length - 1) sb.append(", ");
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO045_N_JumpGameII_Animation().setVisible(true);
        });
    }
}