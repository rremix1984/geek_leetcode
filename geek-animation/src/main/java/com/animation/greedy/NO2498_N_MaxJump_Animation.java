package com.animation.greedy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 2498. 青蛙过河 II (Frog Jump II) 动画演示
 * 
 * 问题描述：
 * 给你一个下标从 0 开始的整数数组 stones，数组中的元素 严格递增 ，表示一条河中石头的位置。
 * 一只青蛙一开始在第一块石头上，它想到达最后一块石头，然后回到第一块石头。
 * 同时，它想要用最少的跳跃次数。
 * 青蛙的 跳跃距离 是它两次跳跃之间的最大距离。
 * 更正式的，如果青蛙从石头 i 跳到石头 j，跳跃距离就是 |stones[i] - stones[j]|。
 * 请你返回青蛙完成旅程的 最小跳跃距离。
 * 
 * 算法思路：
 * 使用贪心算法
 * 1. 去程：跳过所有奇数索引的石头，只在偶数索引石头上跳跃
 * 2. 回程：跳过所有偶数索引的石头，只在奇数索引石头上跳跃
 * 3. 这样可以保证每块石头最多被访问一次
 * 4. 最大跳跃距离就是所有跳跃中的最大值
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class NO2498_N_MaxJump_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    private static final int STONE_SIZE = 60;
    private static final int RIVER_Y = 300;
    private static final int MARGIN = 50;
    
    // 算法相关变量
    private int[] stones = {0, 2, 5, 6, 7};
    private int currentPosition = 0;
    private int targetPosition = 0;
    private boolean isForwardJourney = true;
    private List<Integer> forwardPath;
    private List<Integer> backwardPath;
    private List<Integer> jumpDistances;
    private int maxJumpDistance = 0;
    private int currentJumpDistance = 0;
    private double distanceScaleFactor = 0.5; // 距离缩放因子
    
    // 动画相关变量
    private double animationProgress = 0;
    private boolean isAnimating = false;
    private javax.swing.Timer animationTimer;
    private boolean algorithmComplete = false;
    private int stepCount = 0;
    
    // UI组件
    private JButton startButton;
    private JButton resetButton;
    private JButton stepButton;
    private JTextArea logArea;
    private JLabel statusLabel;
    
    public NO2498_N_MaxJump_Animation() {
        initializeUI();
        initializeAlgorithm();
    }
    
    private void initializeUI() {
        setTitle("LeetCode 2498. 青蛙过河 II (Frog Jump II) - 贪心算法动画演示");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // 设置Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        setLayout(new BorderLayout());
        
        // 创建绘图面板
        JPanel drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawAnimation(g);
            }
        };
        drawPanel.setBackground(new Color(135, 206, 235)); // 天蓝色背景
        drawPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, 450));
        
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
                    animationProgress += 0.06;
                    if (animationProgress >= 1.0) {
                        animationProgress = 1.0;
                        currentPosition = targetPosition;
                        isAnimating = false;
                        
                        // 检查是否完成当前旅程
                        if (isForwardJourney && currentPosition == stones.length - 1) {
                            updateLog("去程完成！开始回程...");
                            isForwardJourney = false;
                        } else if (!isForwardJourney && currentPosition == 0) {
                            algorithmComplete = true;
                            updateLog("回程完成！算法结束");
                            updateLog("最小跳跃距离: " + maxJumpDistance);
                            statusLabel.setText("算法完成 - 最小跳跃距离: " + maxJumpDistance);
                            animationTimer.stop();
                        }
                    }
                    repaint();
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
        
        panel.add(startButton);
        panel.add(stepButton);
        panel.add(resetButton);
        
        statusLabel = new JLabel("准备开始青蛙过河II动画演示");
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
        isForwardJourney = true;
        forwardPath = new ArrayList<>();
        backwardPath = new ArrayList<>();
        jumpDistances = new ArrayList<>();
        maxJumpDistance = 0;
        currentJumpDistance = 0;
        algorithmComplete = false;
        stepCount = 0;
        
        // 计算去程路径（只访问偶数索引）
        for (int i = 0; i < stones.length; i += 2) {
            forwardPath.add(i);
        }
        
        // 计算回程路径（只访问奇数索引，然后回到起点）
        for (int i = stones.length - 2; i >= 1; i -= 2) {
            backwardPath.add(i);
        }
        backwardPath.add(0);
        
        updateLog("初始化完成");
        updateLog("石头位置: [" + arrayToString() + "]");
        updateLog("去程路径（偶数索引）: " + pathToString(forwardPath));
        updateLog("回程路径（奇数索引+起点）: " + pathToString(backwardPath));
        updateLog("算法: 贪心策略 - 分别使用偶数和奇数索引石头");
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制河流
        drawRiver(g2d);
        
        // 绘制石头
        drawStones(g2d);
        
        // 绘制路径
        drawPaths(g2d);
        
        // 绘制青蛙
        drawFrog(g2d);
        
        // 绘制算法信息
        drawAlgorithmInfo(g2d);
    }
    
    private void drawRiver(Graphics2D g2d) {
        // 绘制河流背景
        g2d.setColor(new Color(64, 164, 223));
        g2d.fillRect(0, RIVER_Y - 50, getWidth(), 200);
        
        // 绘制水波纹效果
        g2d.setColor(new Color(100, 180, 255, 100));
        for (int i = 0; i < getWidth(); i += 20) {
            g2d.drawOval(i, RIVER_Y + 20, 15, 8);
            g2d.drawOval(i + 10, RIVER_Y + 40, 12, 6);
        }
    }
    
    private void drawStones(Graphics2D g2d) {
        int drawableWidth = (int)((getWidth() - 2 * MARGIN) * distanceScaleFactor);
        int stoneSpacing = drawableWidth / (stones.length - 1);
        
        for (int i = 0; i < stones.length; i++) {
            int x = MARGIN + i * stoneSpacing - STONE_SIZE / 2;
            int y = RIVER_Y - STONE_SIZE / 2;
            
            // 石头颜色
            if (i == currentPosition) {
                g2d.setColor(Color.YELLOW); // 当前位置
            } else if (isForwardJourney && forwardPath.contains(i)) {
                g2d.setColor(new Color(144, 238, 144)); // 去程路径
            } else if (!isForwardJourney && backwardPath.contains(i)) {
                g2d.setColor(new Color(255, 182, 193)); // 回程路径
            } else {
                g2d.setColor(Color.GRAY); // 普通石头
            }
            
            // 绘制石头（椭圆形）
            g2d.fillOval(x, y, STONE_SIZE, STONE_SIZE);
            
            // 石头边框
            g2d.setColor(Color.DARK_GRAY);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(x, y, STONE_SIZE, STONE_SIZE);
            
            // 石头上的位置值
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            String text = String.valueOf(stones[i]);
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (STONE_SIZE - fm.stringWidth(text)) / 2;
            int textY = y + STONE_SIZE / 2 + fm.getAscent() / 2;
            g2d.drawString(text, textX, textY);
            
            // 索引
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.setColor(Color.BLUE);
            g2d.drawString("i=" + i, x + 5, y - 5);
        }
    }
    
    private void drawPaths(Graphics2D g2d) {
        int drawableWidth = (int)((getWidth() - 2 * MARGIN) * distanceScaleFactor);
        int stoneSpacing = drawableWidth / (stones.length - 1);
        
        // 绘制去程路径
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(new Color(0, 255, 0, 150));
        for (int i = 0; i < forwardPath.size() - 1; i++) {
            int fromIndex = forwardPath.get(i);
            int toIndex = forwardPath.get(i + 1);
            
            int fromX = MARGIN + fromIndex * stoneSpacing;
            int fromY = RIVER_Y - 20;
            int toX = MARGIN + toIndex * stoneSpacing;
            int toY = RIVER_Y - 20;
            
            drawArrowLine(g2d, fromX, fromY, toX, toY);
        }
        
        // 绘制回程路径
        g2d.setColor(new Color(255, 0, 0, 150));
        List<Integer> fullBackPath = new ArrayList<>();
        fullBackPath.add(stones.length - 1);
        fullBackPath.addAll(backwardPath);
        
        for (int i = 0; i < fullBackPath.size() - 1; i++) {
            int fromIndex = fullBackPath.get(i);
            int toIndex = fullBackPath.get(i + 1);
            
            int fromX = MARGIN + fromIndex * stoneSpacing;
            int fromY = RIVER_Y + 20;
            int toX = MARGIN + toIndex * stoneSpacing;
            int toY = RIVER_Y + 20;
            
            drawArrowLine(g2d, fromX, fromY, toX, toY);
        }
    }
    
    private void drawArrowLine(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        g2d.drawLine(x1, y1, x2, y2);
        
        // 绘制箭头
        double angle = Math.atan2(y2 - y1, x2 - x1);
        int arrowLength = 10;
        double arrowAngle = Math.PI / 6;
        
        int x3 = (int) (x2 - arrowLength * Math.cos(angle - arrowAngle));
        int y3 = (int) (y2 - arrowLength * Math.sin(angle - arrowAngle));
        int x4 = (int) (x2 - arrowLength * Math.cos(angle + arrowAngle));
        int y4 = (int) (y2 - arrowLength * Math.sin(angle + arrowAngle));
        
        g2d.drawLine(x2, y2, x3, y3);
        g2d.drawLine(x2, y2, x4, y4);
    }
    
    private void drawFrog(Graphics2D g2d) {
        int drawableWidth = (int)((getWidth() - 2 * MARGIN) * distanceScaleFactor);
        int stoneSpacing = drawableWidth / (stones.length - 1);
        double currentX, currentY;
        
        if (isAnimating) {
            double startPosX = MARGIN + currentPosition * stoneSpacing;
            double endPosX = MARGIN + targetPosition * stoneSpacing;
            currentX = startPosX + (endPosX - startPosX) * animationProgress;
            
            double jumpHeight = Math.sin(animationProgress * Math.PI) * 60;
            currentY = RIVER_Y - jumpHeight;
        } else {
            currentX = MARGIN + currentPosition * stoneSpacing;
            currentY = RIVER_Y;
        }
        
        // 绘制青蛙身体
        g2d.setColor(Color.GREEN);
        g2d.fillOval((int)currentX - 20, (int)currentY - 40, 40, 30);
        
        // 绘制青蛙眼睛
        g2d.setColor(Color.WHITE);
        g2d.fillOval((int)currentX - 15, (int)currentY - 45, 10, 10);
        g2d.fillOval((int)currentX + 5, (int)currentY - 45, 10, 10);
        
        g2d.setColor(Color.BLACK);
        g2d.fillOval((int)currentX - 12, (int)currentY - 42, 4, 4);
        g2d.fillOval((int)currentX + 8, (int)currentY - 42, 4, 4);
        
        // 绘制青蛙腿
        g2d.setColor(Color.GREEN);
        g2d.fillOval((int)currentX - 25, (int)currentY - 20, 15, 8);
        g2d.fillOval((int)currentX + 10, (int)currentY - 20, 15, 8);
    }
    
    private void drawAlgorithmInfo(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.drawString("青蛙过河 II - 贪心算法", 20, 30);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.drawString("当前位置: " + currentPosition + " (石头位置: " + stones[currentPosition] + ")", 20, 60);
        g2d.drawString("旅程: " + (isForwardJourney ? "去程" : "回程"), 300, 60);
        g2d.drawString("当前跳跃距离: " + currentJumpDistance, 400, 60);
        g2d.drawString("最大跳跃距离: " + maxJumpDistance, 550, 60);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawString("绿色箭头: 去程路径（偶数索引）", 20, 400);
        g2d.drawString("红色箭头: 回程路径（奇数索引）", 200, 400);
        g2d.drawString("策略: 去程跳过奇数索引，回程跳过偶数索引", 20, 420);
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
        
        List<Integer> currentPath = isForwardJourney ? forwardPath : backwardPath;
        
        if (stepCount >= currentPath.size() - 1) {
            if (isForwardJourney) {
                // 切换到回程
                isForwardJourney = false;
                stepCount = 0;
                currentPath = backwardPath;
                currentPosition = stones.length - 1;
                updateLog("开始回程，从最后一块石头返回");
            } else {
                // 算法完成
                algorithmComplete = true;
                updateLog("算法完成！最小跳跃距离: " + maxJumpDistance);
                statusLabel.setText("算法完成 - 最小跳跃距离: " + maxJumpDistance);
                return;
            }
        }
        
        if (stepCount < currentPath.size() - 1) {
            int fromIndex = isForwardJourney ? currentPath.get(stepCount) : 
                           (stepCount == 0 ? stones.length - 1 : currentPath.get(stepCount - 1));
            int toIndex = currentPath.get(stepCount + (isForwardJourney ? 1 : 0));
            
            targetPosition = toIndex;
            currentJumpDistance = Math.abs(stones[toIndex] - stones[fromIndex]);
            maxJumpDistance = Math.max(maxJumpDistance, currentJumpDistance);
            
            updateLog((isForwardJourney ? "去程" : "回程") + " 跳跃: " + fromIndex + " → " + toIndex + 
                     " (距离: " + currentJumpDistance + ")");
            updateLog("石头位置: " + stones[fromIndex] + " → " + stones[toIndex]);
            
            animationProgress = 0;
            isAnimating = true;
            stepCount++;
            
            statusLabel.setText((isForwardJourney ? "去程" : "回程") + " - 跳跃距离: " + currentJumpDistance);
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
        repaint();
    }
    
    private void updateLog(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private String arrayToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stones.length; i++) {
            sb.append(stones[i]);
            if (i < stones.length - 1) sb.append(", ");
        }
        return sb.toString();
    }
    
    private String pathToString(List<Integer> path) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            sb.append(path.get(i));
            if (i < path.size() - 1) sb.append(" → ");
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO2498_N_MaxJump_Animation().setVisible(true);
        });
    }
}