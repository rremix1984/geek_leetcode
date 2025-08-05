package com.animation.greedy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 55. 跳跃游戏 动画演示
 * 
 * 问题描述：
 * 给定一个非负整数数组 nums，你最初位于数组的第一个下标。
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * 判断你是否能够到达最后一个下标。
 * 
 * 算法思路：
 * 使用贪心算法，维护一个最远可达位置 maxReach
 * 遍历数组，更新最远可达位置
 * 如果当前位置超过最远可达位置，则无法到达
 * 如果最远可达位置大于等于最后一个位置，则可以到达
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 * 
 * @author 算法动画演示
 */
public class NO055_N_JumpGame_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 700;
    private static final int CELL_SIZE = 60;
    private static final int CELL_SPACING = 10;
    
    // 数据
    private int[] nums = {2, 3, 1, 1, 4}; // 默认测试用例
    private int currentIndex = 0;
    private int maxReach = 0;
    private boolean canReachEnd = false;
    private boolean animationFinished = false;
    
    // UI组件
    private JButton startButton;
    private JButton stepButton;
    private JButton resetButton;
    private JTextArea logArea;
    private Timer animationTimer;
    
    // 动画状态
    private List<String> animationSteps;
    private int currentStep = 0;
    
    public NO055_N_JumpGame_Animation() {
        initializeUI();
        initializeAnimation();
    }
    
    private void initializeUI() {
        setTitle("LeetCode 55. 跳跃游戏 - 贪心算法动画演示");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // 创建控制面板
        JPanel controlPanel = new JPanel(new FlowLayout());
        startButton = new JButton("开始动画");
        stepButton = new JButton("单步执行");
        resetButton = new JButton("重置");
        
        startButton.addActionListener(e -> startAnimation());
        stepButton.addActionListener(e -> stepAnimation());
        resetButton.addActionListener(e -> resetAnimation());
        
        controlPanel.add(startButton);
        controlPanel.add(stepButton);
        controlPanel.add(resetButton);
        
        // 创建信息面板
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder("算法信息"));
        
        logArea = new JTextArea(8, 30);
        logArea.setEditable(false);
        logArea.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        
        infoPanel.add(scrollPane, BorderLayout.CENTER);
        
        // 创建主绘制面板
        JPanel drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawAnimation(g);
            }
        };
        drawPanel.setBackground(Color.WHITE);
        drawPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, 400));
        
        add(controlPanel, BorderLayout.NORTH);
        add(drawPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);
        
        // 初始化动画定时器
        animationTimer = new Timer(1500, e -> stepAnimation());
        
        updateLog("算法说明：使用贪心算法判断是否能到达数组最后一个位置");
        updateLog("数组: " + java.util.Arrays.toString(nums));
        updateLog("点击'开始动画'或'单步执行'开始演示");
    }
    
    private void initializeAnimation() {
        animationSteps = new ArrayList<>();
        animationSteps.add("初始化：当前位置 = 0，最远可达位置 = 0");
        
        int tempMaxReach = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > tempMaxReach) {
                animationSteps.add("位置 " + i + " 超过最远可达位置 " + tempMaxReach + "，无法到达终点");
                break;
            }
            
            int newReach = i + nums[i];
            if (newReach > tempMaxReach) {
                animationSteps.add("位置 " + i + "：值=" + nums[i] + "，更新最远可达位置：" + tempMaxReach + " → " + newReach);
                tempMaxReach = newReach;
            } else {
                animationSteps.add("位置 " + i + "：值=" + nums[i] + "，最远可达位置保持：" + tempMaxReach);
            }
            
            if (tempMaxReach >= nums.length - 1) {
                animationSteps.add("最远可达位置 " + tempMaxReach + " >= 最后位置 " + (nums.length - 1) + "，可以到达终点！");
                break;
            }
        }
    }
    
    private void startAnimation() {
        if (animationFinished) {
            resetAnimation();
        }
        animationTimer.start();
        startButton.setText("暂停动画");
        stepButton.setEnabled(false);
    }
    
    private void stepAnimation() {
        if (currentStep >= animationSteps.size()) {
            animationFinished = true;
            animationTimer.stop();
            startButton.setText("重新开始");
            stepButton.setEnabled(true);
            return;
        }
        
        String step = animationSteps.get(currentStep);
        updateLog("步骤 " + (currentStep + 1) + ": " + step);
        
        // 更新动画状态
        if (currentStep == 0) {
            currentIndex = 0;
            maxReach = 0;
        } else {
            // 解析步骤信息更新状态
            if (step.contains("位置")) {
                String[] parts = step.split("位置 ")[1].split("：")[0].split(" ");
                int pos = Integer.parseInt(parts[0]);
                currentIndex = pos;
                
                if (pos <= maxReach) {
                    int newReach = pos + nums[pos];
                    maxReach = Math.max(maxReach, newReach);
                    
                    if (maxReach >= nums.length - 1) {
                        canReachEnd = true;
                    }
                }
            }
        }
        
        currentStep++;
        repaint();
        
        if (currentStep >= animationSteps.size()) {
            animationTimer.stop();
            startButton.setText("重新开始");
            stepButton.setEnabled(true);
            animationFinished = true;
        }
    }
    
    private void resetAnimation() {
        animationTimer.stop();
        currentStep = 0;
        currentIndex = 0;
        maxReach = 0;
        canReachEnd = false;
        animationFinished = false;
    }

    private void updateLog(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    private void drawAnimation(Graphics g) {
        // 绘制数组
        for (int i = 0; i < nums.length; i++) {
            int x = 50 + i * (CELL_SIZE + CELL_SPACING);
            int y = 100;

            if (i == currentIndex) {
                g.setColor(Color.ORANGE);
            } else if (i <= maxReach) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.LIGHT_GRAY);
            }
            g.fillRect(x, y, CELL_SIZE, CELL_SIZE);

            g.setColor(Color.BLACK);
            g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
            g.drawString(String.valueOf(nums[i]), x + CELL_SIZE / 2 - 5, y + CELL_SIZE / 2 + 5);
            g.drawString(String.valueOf(i), x + CELL_SIZE / 2 - 5, y + CELL_SIZE + 15);
        }

        // 绘制当前位置指针
        g.setColor(Color.RED);
        int pointerX = 50 + currentIndex * (CELL_SIZE + CELL_SPACING) + CELL_SIZE / 2;
        g.fillPolygon(new int[]{pointerX, pointerX - 10, pointerX + 10}, new int[]{80, 70, 70}, 3);

        // 绘制最远可达位置
        g.setColor(Color.BLUE);
        g.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g.drawString("最远可达: " + maxReach, 50, 220);

        // 绘制结果
        if (animationFinished) {
            g.setFont(new Font("微软雅黑", Font.BOLD, 24));
            if (canReachEnd) {
                g.setColor(new Color(0, 128, 0)); // 深绿色
                g.drawString("可以到达终点！", 50, 260);
            } else {
                g.setColor(Color.RED);
                g.drawString("无法到达终点！", 50, 260);
            }
        }
    }
}