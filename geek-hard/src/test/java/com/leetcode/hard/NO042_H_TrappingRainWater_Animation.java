package com.leetcode.hard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * NO.42 接雨水 - 动画演示
 * 演示动态规划方法计算雨水容量的过程
 */
public class NO042_H_TrappingRainWater_Animation extends JFrame {
    private int[] heights;
    private int[] leftMax;
    private int[] rightMax;
    private int[] waterLevel;
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JTextField heightField;
    private JButton addHeightButton, startButton, resetButton, stepButton;
    private JLabel resultLabel;
    private Timer animationTimer;
    private int currentStep = 0;
    private int currentIndex = 0;
    private boolean isAnimating = false;
    private int totalWater = 0;
    
    public NO042_H_TrappingRainWater_Animation() {
        setTitle("NO.42 接雨水 - 动画演示");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        
        initComponents();
        setupLayout();
        setupEventHandlers();
        initializeDefaultData();
    }
    
    private void initComponents() {
        mainPanel = new JPanel();
        controlPanel = new JPanel();
        
        heightField = new JTextField(5);
        addHeightButton = new JButton("添加高度");
        startButton = new JButton("开始接雨水");
        resetButton = new JButton("重置");
        stepButton = new JButton("单步执行");
        resultLabel = new JLabel("结果: ");
        
        // 设置字体
        Font font = new Font("微软雅黑", Font.PLAIN, 14);
        resultLabel.setFont(font);
        
        stepButton.setEnabled(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 主面板
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new RainWaterVisualizationPanel(), BorderLayout.CENTER);
        
        // 控制面板
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JLabel("柱子高度:"));
        controlPanel.add(heightField);
        controlPanel.add(addHeightButton);
        controlPanel.add(startButton);
        controlPanel.add(stepButton);
        controlPanel.add(resetButton);
        controlPanel.add(resultLabel);
        
        add(mainPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        addHeightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAnimating) {
                    addHeight();
                }
            }
        });
        
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAnimating) {
                    startAnimation();
                } else {
                    stopAnimation();
                }
            }
        });
        
        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeNextStep();
            }
        });
        
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
    }
    
    private void addHeight() {
        try {
            int height = Integer.parseInt(heightField.getText());
            if (height >= 0) {
                List<Integer> heightList = new ArrayList<>();
                if (heights != null) {
                    for (int h : heights) {
                        heightList.add(h);
                    }
                }
                heightList.add(height);
                heights = heightList.stream().mapToInt(i -> i).toArray();
                
                updateDisplay();
                resultLabel.setText("结果: 添加高度 " + height);
                heightField.setText("");
            } else {
                resultLabel.setText("错误: 请输入非负整数");
            }
        } catch (NumberFormatException ex) {
            resultLabel.setText("错误: 请输入有效数字");
        }
    }
    
    private void initializeDefaultData() {
        // 初始化默认数据 [0,1,0,2,1,0,1,3,2,1,2,1]
        heights = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        updateDisplay();
    }
    
    private void startAnimation() {
        if (heights == null || heights.length < 3) {
            resultLabel.setText("错误: 至少需要3个柱子");
            return;
        }
        
        isAnimating = true;
        currentStep = 0;
        currentIndex = 0;
        totalWater = 0;
        
        // 初始化数组
        leftMax = new int[heights.length];
        rightMax = new int[heights.length];
        waterLevel = new int[heights.length];
        
        startButton.setText("停止动画");
        stepButton.setEnabled(true);
        addHeightButton.setEnabled(false);
        
        animationTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!executeNextStep()) {
                    stopAnimation();
                    resultLabel.setText("接雨水完成! 总容量: " + totalWater + " 单位");
                }
            }
        });
        animationTimer.start();
        
        resultLabel.setText("开始计算接雨水...");
        updateDisplay();
    }
    
    private boolean executeNextStep() {
        switch (currentStep) {
            case 0:
                // 第一步：计算左边最大值数组
                if (currentIndex == 0) {
                    leftMax[0] = heights[0];
                    resultLabel.setText("步骤1: 计算左边界数组 - 初始化 leftMax[0] = " + heights[0]);
                    currentIndex++;
                } else if (currentIndex < heights.length) {
                    leftMax[currentIndex] = Math.max(leftMax[currentIndex - 1], heights[currentIndex]);
                    resultLabel.setText("步骤1: leftMax[" + currentIndex + "] = max(" + leftMax[currentIndex - 1] + ", " + heights[currentIndex] + ") = " + leftMax[currentIndex]);
                    currentIndex++;
                } else {
                    currentStep++;
                    currentIndex = heights.length - 1;
                    rightMax[heights.length - 1] = heights[heights.length - 1];
                    resultLabel.setText("步骤2: 计算右边界数组 - 初始化 rightMax[" + (heights.length - 1) + "] = " + heights[heights.length - 1]);
                    currentIndex--;
                }
                break;
                
            case 1:
                // 第二步：计算右边最大值数组
                if (currentIndex >= 0) {
                    rightMax[currentIndex] = Math.max(rightMax[currentIndex + 1], heights[currentIndex]);
                    resultLabel.setText("步骤2: rightMax[" + currentIndex + "] = max(" + rightMax[currentIndex + 1] + ", " + heights[currentIndex] + ") = " + rightMax[currentIndex]);
                    currentIndex--;
                } else {
                    currentStep++;
                    currentIndex = 0;
                    resultLabel.setText("步骤3: 开始计算每个位置的积水量");
                }
                break;
                
            case 2:
                // 第三步：计算每个位置的积水量
                if (currentIndex < heights.length) {
                    int water = Math.min(leftMax[currentIndex], rightMax[currentIndex]) - heights[currentIndex];
                    waterLevel[currentIndex] = Math.min(leftMax[currentIndex], rightMax[currentIndex]);
                    totalWater += water;
                    resultLabel.setText("步骤3: 位置" + currentIndex + "积水量 = min(" + leftMax[currentIndex] + ", " + rightMax[currentIndex] + ") - " + heights[currentIndex] + " = " + water);
                    currentIndex++;
                } else {
                    return false; // 动画结束
                }
                break;
        }
        
        updateDisplay();
        return true;
    }
    
    private void stopAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        isAnimating = false;
        startButton.setText("开始接雨水");
        stepButton.setEnabled(false);
        addHeightButton.setEnabled(true);
    }
    
    private void reset() {
        stopAnimation();
        heights = null;
        leftMax = null;
        rightMax = null;
        waterLevel = null;
        currentStep = 0;
        currentIndex = 0;
        totalWater = 0;
        
        initializeDefaultData();
        resultLabel.setText("结果: 已重置");
    }
    
    private void updateDisplay() {
        repaint();
    }
    
    // 接雨水可视化面板
    private class RainWaterVisualizationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (heights == null) return;
            
            drawHeights(g2d);
            drawWater(g2d);
            drawArrays(g2d);
            drawAlgorithmInfo(g2d);
        }
        
        private void drawHeights(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            g2d.drawString("柱状图 (接雨水):", 50, 30);
            
            int startX = 50;
            int startY = 350;
            int barWidth = 40;
            int maxHeight = getMaxHeight();
            int scale = 200 / Math.max(maxHeight, 1);
            
            for (int i = 0; i < heights.length; i++) {
                int barHeight = heights[i] * scale;
                
                // 绘制柱子
                Rectangle bar = new Rectangle(startX + i * (barWidth + 5), startY - barHeight, barWidth, barHeight);
                g2d.setColor(Color.DARK_GRAY);
                g2d.fill(bar);
                g2d.setColor(Color.BLACK);
                g2d.draw(bar);
                
                // 绘制水位
                if (isAnimating && waterLevel != null && currentStep >= 2 && i <= currentIndex) {
                    int waterHeight = (waterLevel[i] - heights[i]) * scale;
                    if (waterHeight > 0) {
                        Rectangle water = new Rectangle(startX + i * (barWidth + 5), startY - waterLevel[i] * scale, barWidth, waterHeight);
                        g2d.setColor(new Color(0, 100, 255, 150)); // 半透明蓝色
                        g2d.fill(water);
                    }
                }
                
                // 高亮当前处理的柱子
                if (isAnimating && i == currentIndex) {
                    g2d.setColor(Color.RED);
                    g2d.setStroke(new BasicStroke(3));
                    g2d.draw(bar);
                    g2d.setStroke(new BasicStroke(1));
                }
                
                // 绘制高度值
                g2d.setColor(Color.BLACK);
                String heightStr = String.valueOf(heights[i]);
                FontMetrics fm = g2d.getFontMetrics();
                int textX = startX + i * (barWidth + 5) + (barWidth - fm.stringWidth(heightStr)) / 2;
                g2d.drawString(heightStr, textX, startY + 20);
                
                // 绘制索引
                g2d.setColor(Color.BLUE);
                g2d.drawString(String.valueOf(i), textX, startY + 35);
            }
        }
        
        private void drawWater(Graphics2D g2d) {
            if (!isAnimating || waterLevel == null) return;
            
            // 绘制水位线
            int startX = 50;
            int startY = 350;
            int barWidth = 40;
            int scale = 200 / Math.max(getMaxHeight(), 1);
            
            for (int i = 0; i <= currentIndex && i < waterLevel.length; i++) {
                if (currentStep >= 2) {
                    int waterTop = startY - waterLevel[i] * scale;
                    g2d.setColor(Color.BLUE);
                    g2d.drawLine(startX + i * (barWidth + 5), waterTop, 
                               startX + i * (barWidth + 5) + barWidth, waterTop);
                }
            }
        }
        
        private void drawArrays(Graphics2D g2d) {
            if (!isAnimating) return;
            
            g2d.setColor(Color.BLACK);
            
            // 绘制leftMax数组
            if (leftMax != null) {
                g2d.drawString("leftMax数组:", 50, 420);
                for (int i = 0; i < leftMax.length; i++) {
                    String value = (currentStep == 0 && i > currentIndex) ? "?" : String.valueOf(leftMax[i]);
                    Color color = (currentStep == 0 && i == currentIndex) ? Color.RED : Color.BLACK;
                    g2d.setColor(color);
                    g2d.drawString(value, 50 + i * 50, 440);
                }
            }
            
            // 绘制rightMax数组  
            if (rightMax != null) {
                g2d.setColor(Color.BLACK);
                g2d.drawString("rightMax数组:", 50, 470);
                for (int i = 0; i < rightMax.length; i++) {
                    String value = (currentStep == 1 && i < currentIndex) ? "?" : String.valueOf(rightMax[i]);
                    Color color = (currentStep == 1 && i == currentIndex) ? Color.RED : Color.BLACK;
                    g2d.setColor(color);
                    g2d.drawString(value, 50 + i * 50, 490);
                }
            }
            
            // 绘制水位数组
            if (waterLevel != null && currentStep >= 2) {
                g2d.setColor(Color.BLACK);
                g2d.drawString("水位高度:", 50, 520);
                for (int i = 0; i < waterLevel.length; i++) {
                    if (i <= currentIndex) {
                        Color color = (i == currentIndex) ? Color.RED : Color.BLUE;
                        g2d.setColor(color);
                        g2d.drawString(String.valueOf(waterLevel[i]), 50 + i * 50, 540);
                    }
                }
            }
        }
        
        private void drawAlgorithmInfo(Graphics2D g2d) {
            int infoX = 600;
            int infoY = 50;
            
            g2d.setColor(Color.BLACK);
            g2d.drawString("算法步骤 (动态规划):", infoX, infoY);
            g2d.drawString("1. 计算每个位置左边的最大高度", infoX, infoY + 20);
            g2d.drawString("2. 计算每个位置右边的最大高度", infoX, infoY + 40);
            g2d.drawString("3. 每个位置的积水量 = min(leftMax, rightMax) - height", infoX, infoY + 60);
            g2d.drawString("4. 累加所有位置的积水量", infoX, infoY + 80);
            
            // 显示当前状态
            if (isAnimating) {
                g2d.setColor(Color.BLUE);
                g2d.drawString("当前状态:", infoX, infoY + 120);
                
                String stepDesc = "";
                switch (currentStep) {
                    case 0: stepDesc = "计算leftMax数组"; break;
                    case 1: stepDesc = "计算rightMax数组"; break;
                    case 2: stepDesc = "计算积水量"; break;
                }
                g2d.drawString("步骤: " + stepDesc, infoX, infoY + 140);
                g2d.drawString("当前索引: " + currentIndex, infoX, infoY + 160);
                g2d.drawString("累计积水: " + totalWater + " 单位", infoX, infoY + 180);
            }
            
            // 绘制公式
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawString("积水公式:", infoX, infoY + 220);
            g2d.drawString("water[i] = min(leftMax[i], rightMax[i]) - height[i]", infoX, infoY + 240);
            
            // 示例说明
            g2d.drawString("示例: heights = [0,1,0,2,1,0,1,3,2,1,2,1]", infoX, infoY + 280);
            g2d.drawString("结果: 6 单位雨水", infoX, infoY + 300);
        }
        
        private int getMaxHeight() {
            if (heights == null) return 1;
            int max = 0;
            for (int h : heights) {
                max = Math.max(max, h);
            }
            return max;
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new NO042_H_TrappingRainWater_Animation().setVisible(true);
            }
        });
    }
}
