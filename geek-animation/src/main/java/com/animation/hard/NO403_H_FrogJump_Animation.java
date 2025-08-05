package com.animation.hard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * LeetCode 403. 青蛙过河 (Frog Jump) 动画演示
 * 
 * 问题描述：
 * 一只青蛙想要过河。假定河流被等分为若干个单元格，并且在一些单元格内会有石头。
 * 青蛙可以跳上石头，但是不可以跳入水中。
 * 
 * 给你石头的位置列表 stones（用单元格序号 升序 表示），请判定青蛙能否成功过河（即能否在最后一步跳到最后一个石头上）。
 * 
 * 开始时，青蛙默认已站在第一个石头上，并可以假定它第一步只能跳跃 1 个单位（即只能从单元格 1 跳至单元格 2 ）。
 * 
 * 如果青蛙上一步跳跃了 k 个单位，那么它接下来的跳跃距离只能选择为 k - 1、k 或 k + 1 个单位。
 * 另外，青蛙只能向前方向跳跃。
 * 
 * 算法思路：
 * 使用动态规划 + 状态转移
 * 1. 用 Map<Integer, Set<Integer>> 记录每个石头位置可以到达的跳跃步数
 * 2. 从第一个石头开始，尝试所有可能的跳跃步数
 * 3. 对于每个可达的石头，更新其可能的跳跃步数集合
 * 4. 最终检查最后一个石头是否可达
 * 
 * 时间复杂度：O(n²)
 * 空间复杂度：O(n²)
 */
public class NO403_H_FrogJump_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final int STONE_SIZE = 30;
    private static final int FROG_SIZE = 25;
    
    // 算法相关变量
    private int[] stones = {0, 1, 2, 3, 4, 5, 8, 9, 11, 12, 15, 16, 18, 20, 21};
    private Map<Integer, Set<Integer>> dp;
    private Map<Integer, Integer> stoneIndexMap;
    
    // 动画相关变量
    private int currentStone = 0;
    private int currentStep = 0;
    private boolean animationRunning = false;
    private double distanceScaleFactor = 0.8; // 距离缩放因子
    private javax.swing.Timer animationTimer;
    private boolean isJumping = false;
    private double jumpProgress = 0;
    private int jumpStartX, jumpStartY, jumpEndX, jumpEndY;
    
    // UI组件
    private JPanel drawPanel;
    private JButton startButton;
    private JButton resetButton;
    private JButton stepButton;
    private JTextArea logArea;
    private JLabel statusLabel;
    
    public NO403_H_FrogJump_Animation() {
        initializeUI();
        initializeAlgorithm();
    }
    
    private void initializeUI() {
        setTitle("LeetCode 403. 青蛙过河 (Frog Jump) - 动态规划动画演示");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // 设置Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // 创建主面板
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
        
        statusLabel = new JLabel("准备开始青蛙过河动画演示");
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
        dp = new HashMap<>();
        stoneIndexMap = new HashMap<>();
        
        // 建立石头位置到索引的映射
        for (int i = 0; i < stones.length; i++) {
            stoneIndexMap.put(stones[i], i);
        }
        
        // 初始化DP状态
        for (int stone : stones) {
            dp.put(stone, new HashSet<>());
        }
        
        // 青蛙从第一个石头开始，第一步只能跳1个单位
        dp.get(stones[0]).add(0);
        
        updateLog("初始化完成");
        updateLog("石头位置: " + Arrays.toString(stones));
        updateLog("青蛙从位置 " + stones[0] + " 开始");
        updateLog("第一步只能跳跃1个单位");
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制河流背景
        g2d.setColor(new Color(173, 216, 230));
        g2d.fillRect(0, 200, getWidth(), 200);
        
        // 绘制石头
        int startX = 50;
        int stoneY = 250;
        int maxStonePos = stones[stones.length - 1];
        int panelWidth = getWidth();
        int drawableWidth = (int) ((panelWidth - 2 * startX) * distanceScaleFactor);
        
        for (int i = 0; i < stones.length; i++) {
            int stoneX = startX + (int) ((double) stones[i] / maxStonePos * drawableWidth);
            
            // 石头颜色
            if (i == currentStone) {
                g2d.setColor(Color.GREEN); // 当前石头
            } else if (i < currentStone) {
                g2d.setColor(Color.LIGHT_GRAY); // 已访问的石头
            } else {
                g2d.setColor(Color.GRAY); // 未访问的石头
            }
            
            g2d.fillOval(stoneX - STONE_SIZE/2, stoneY - STONE_SIZE/2, STONE_SIZE, STONE_SIZE);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(stoneX - STONE_SIZE/2, stoneY - STONE_SIZE/2, STONE_SIZE, STONE_SIZE);
            
            // 绘制石头位置标签
            g2d.drawString(String.valueOf(stones[i]), stoneX - 5, stoneY + 35);
            
            // 绘制可能的跳跃步数
            if (dp.get(stones[i]) != null && !dp.get(stones[i]).isEmpty()) {
                g2d.setColor(Color.BLUE);
                g2d.drawString("步数: " + dp.get(stones[i]).toString(), stoneX - 20, stoneY - 30);
            }
        }
        
        // 绘制青蛙
        drawFrog(g2d, startX, stoneY, maxStonePos, drawableWidth);
        
        // 绘制算法说明
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g2d.drawString("青蛙过河 - 动态规划算法", 20, 30);
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        g2d.drawString("规则：青蛙上一步跳k个单位，下一步只能跳k-1、k或k+1个单位", 20, 50);
        g2d.drawString("目标：判断青蛙能否到达最后一个石头", 20, 70);
    }
    
    private void drawFrog(Graphics2D g2d, int startX, int stoneY, int maxStonePos, int drawableWidth) {
        int frogX, frogY;

        if (isJumping) {
            frogX = (int) (jumpStartX + (jumpEndX - jumpStartX) * jumpProgress);
            double jumpHeight = -100 * Math.sin(jumpProgress * Math.PI); // 抛物线轨迹
            frogY = (int) (jumpStartY + (jumpEndY - jumpStartY) * jumpProgress + jumpHeight);
        } else {
            if (currentStone >= stones.length) return;
            frogX = startX + (int) ((double) stones[currentStone] / maxStonePos * drawableWidth);
            frogY = stoneY - FROG_SIZE / 2;
        }

        g2d.setColor(Color.ORANGE);
        g2d.fillOval(frogX - FROG_SIZE / 2, frogY - FROG_SIZE / 2, FROG_SIZE, FROG_SIZE);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(frogX - FROG_SIZE / 2, frogY - FROG_SIZE / 2, FROG_SIZE, FROG_SIZE);
    }

    private void startAnimation() {
        if (animationRunning) {
            stopAnimation();
            return;
        }

        animationRunning = true;
        startButton.setText("停止动画");

        animationTimer = new javax.swing.Timer(20, e -> {
            if (isJumping) {
                jumpProgress += 0.02;
                if (jumpProgress >= 1.0) {
                    jumpProgress = 1.0;
                    isJumping = false;
                    updateLog("成功跳到 " + stones[currentStone]);
                    statusLabel.setText("青蛙跳到石头 " + stones[currentStone]);
                }
                repaint();
            } else {
                if (!stepAnimation()) {
                    stopAnimation();
                }
            }
        });
        animationTimer.start();
    }
    
    private void stopAnimation() {
        animationRunning = false;
        startButton.setText("开始动画");
        if (animationTimer != null) {
            animationTimer.stop();
        }
    }
    
    private boolean stepAnimation() {
        if (isJumping) return true;

        if (currentStone >= stones.length - 1) {
            boolean canReach = !dp.get(stones[stones.length - 1]).isEmpty();
            updateLog("算法结束！");
            updateLog("结果：青蛙" + (canReach ? "能够" : "无法") + "到达最后一个石头");
            statusLabel.setText("算法结束 - " + (canReach ? "成功过河" : "无法过河"));
            return false;
        }

        int currentPos = stones[currentStone];
        Set<Integer> lastSteps = dp.get(currentPos);

        updateLog("当前在石头位置: " + currentPos + " (索引: " + currentStone + ")");
        updateLog("可能的跳跃步数: " + lastSteps);

        // 寻找下一个要跳的石头
        int nextStoneIndex = -1;
        int jumpStep = 0;

        // 遍历所有可能的下一步
        for (int i = currentStone + 1; i < stones.length; i++) {
            int nextPos = stones[i];
            int dist = nextPos - currentPos;
            for (int lastStep : lastSteps) {
                if (dist >= lastStep - 1 && dist <= lastStep + 1) {
                    if (!dp.get(nextPos).contains(dist)) {
                        dp.get(nextPos).add(dist);
                        updateLog("  计算出可跳跃: 从 " + currentPos + " 到 " + nextPos + " (步长 " + dist + ")");
                    }
                }
            }
        }

        // 找到第一个有可行路径的下一个石头
        for (int i = currentStone + 1; i < stones.length; i++) {
            if (!dp.get(stones[i]).isEmpty()) {
                nextStoneIndex = i;
                break;
            }
        }

        if (nextStoneIndex != -1) {
            int fromStoneIndex = currentStone;
            currentStone = nextStoneIndex;

            int maxStonePos = stones[stones.length - 1];
            int panelWidth = getWidth();
            int drawableWidth = (int) ((panelWidth - 2 * 50) * distanceScaleFactor);

            jumpStartX = 50 + (int) ((double) stones[fromStoneIndex] / maxStonePos * drawableWidth);
            jumpStartY = 250 - FROG_SIZE / 2;
            jumpEndX = 50 + (int) ((double) stones[currentStone] / maxStonePos * drawableWidth);
            jumpEndY = 250 - FROG_SIZE / 2;

            isJumping = true;
            jumpProgress = 0;

            updateLog("从 " + stones[fromStoneIndex] + " 准备跳到 " + stones[currentStone]);
            statusLabel.setText("青蛙准备从 " + stones[fromStoneIndex] + " 跳到 " + stones[currentStone]);

        } else {
            updateLog("在石头 " + currentPos + " 找不到下一步可跳的石头。");
            statusLabel.setText("青蛙在石头 " + currentPos + " 被困住。");
            return false; // 动画结束
        }

        repaint();
        return true;
    }
    
    private void resetAnimation() {
        stopAnimation();
        currentStone = 0;
        currentStep = 0;
        initializeAlgorithm();
        statusLabel.setText("已重置，准备开始新的演示");
        repaint();
    }
    
    private void updateLog(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO403_H_FrogJump_Animation().setVisible(true);
        });
    }
}