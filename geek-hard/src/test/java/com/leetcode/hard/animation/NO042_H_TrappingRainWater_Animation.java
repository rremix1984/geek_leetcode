package com.leetcode.hard.animation;

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

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });

        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stepAnimation();
            }
        });
    }

    private void initializeDefaultData() {
        heights = new int[] { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 };
        calculateLeftRightMax();
        calculateWaterLevel();
        resultLabel.setText("结果: 总水量 = " + totalWater);
    }

    private void addHeight() {
        try {
            String text = heightField.getText().trim();
            if (text.isEmpty())
                return;
            int height = Integer.parseInt(text);
            if (height < 0 || height > 10)
                return;

            if (heights == null) {
                heights = new int[] { height };
            } else {
                int[] newHeights = Arrays.copyOf(heights, heights.length + 1);
                newHeights[heights.length] = height;
                heights = newHeights;
            }

            heightField.setText("");
            calculateLeftRightMax();
            calculateWaterLevel();
            resultLabel.setText("结果: 总水量 = " + totalWater);
            repaint();
        } catch (NumberFormatException ex) {
            // ignore invalid input
        }
    }

    private void startAnimation() {
        if (heights == null || heights.length == 0)
            return;
        isAnimating = true;
        startButton.setText("停止动画");
        stepButton.setEnabled(true);

        animationTimer = new Timer(800, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stepAnimation();
            }
        });
        animationTimer.start();
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
        currentIndex = 0;
        totalWater = 0;
        leftMax = null;
        rightMax = null;
        waterLevel = null;

        initializeDefaultData();
        resultLabel.setText("结果: 已重置");
    }

    private void stepAnimation() {
        if (!isAnimating)
            return;

        if (currentStep == 0) {
            if (currentIndex < heights.length) {
                leftMax[currentIndex] = Math.max(currentIndex == 0 ? 0 : leftMax[currentIndex - 1],
                        heights[currentIndex]);
                currentIndex++;
            } else {
                currentStep = 1;
                currentIndex = heights.length - 1;
            }
        } else if (currentStep == 1) {
            if (currentIndex >= 0) {
                rightMax[currentIndex] = Math.max(currentIndex == heights.length - 1 ? 0 : rightMax[currentIndex + 1],
                        heights[currentIndex]);
                currentIndex--;
            } else {
                currentStep = 2;
                currentIndex = 0;
            }
        } else if (currentStep == 2) {
            if (currentIndex < heights.length) {
                int level = Math.min(leftMax[currentIndex], rightMax[currentIndex]);
                waterLevel[currentIndex] = Math.max(0, level - heights[currentIndex]);
                currentIndex++;
            } else {
                currentStep = 3;
                totalWater = Arrays.stream(waterLevel).sum();
                resultLabel.setText("结果: 总水量 = " + totalWater);
                stopAnimation();
            }
        }

        repaint();
    }

    private void calculateLeftRightMax() {
        if (heights == null || heights.length == 0)
            return;
        leftMax = new int[heights.length];
        rightMax = new int[heights.length];

        int max = 0;
        for (int i = 0; i < heights.length; i++) {
            max = Math.max(max, heights[i]);
            leftMax[i] = max;
        }

        max = 0;
        for (int i = heights.length - 1; i >= 0; i--) {
            max = Math.max(max, heights[i]);
            rightMax[i] = max;
        }
    }

    private void calculateWaterLevel() {
        if (heights == null)
            return;
        waterLevel = new int[heights.length];
        for (int i = 0; i < heights.length; i++) {
            int level = Math.min(leftMax[i], rightMax[i]);
            waterLevel[i] = Math.max(0, level - heights[i]);
        }
        totalWater = Arrays.stream(waterLevel).sum();
    }

    // 可视化面板
    private class RainWaterVisualizationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (heights == null)
                return;

            drawHistogram(g2d);
            drawWater(g2d);
            drawAlgorithmInfo(g2d);
            drawArrays(g2d);
        }

        private void drawHistogram(Graphics2D g2d) {
            int startX = 50;
            int startY = 350;
            int barWidth = 40;
            int maxHeight = getMaxHeight();

            for (int i = 0; i < heights.length; i++) {
                int barHeight = heights[i] * 20;

                // 柱子
                g2d.setColor(Color.GRAY);
                g2d.fillRect(startX + i * (barWidth + 5), startY - barHeight, barWidth, barHeight);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(startX + i * (barWidth + 5), startY - barHeight, barWidth, barHeight);

                // 当前步骤高亮
                if (isAnimating) {
                    if ((currentStep == 0 && i == currentIndex) || (currentStep == 1 && i == currentIndex)) {
                        g2d.setColor(Color.RED);
                        g2d.setStroke(new BasicStroke(3));
                        g2d.drawRect(startX + i * (barWidth + 5), startY - barHeight, barWidth, barHeight);
                        g2d.setStroke(new BasicStroke(1));
                    }
                }

                // 高度显示
                g2d.setColor(Color.BLACK);
                String hStr = String.valueOf(heights[i]);
                FontMetrics fm = g2d.getFontMetrics();
                int textX = startX + i * (barWidth + 5) + (barWidth - fm.stringWidth(hStr)) / 2;
                g2d.drawString(hStr, textX, startY + 20);
            }
        }

        private void drawWater(Graphics2D g2d) {
            if (waterLevel == null)
                return;

            int startX = 50;
            int startY = 350;
            int barWidth = 40;

            for (int i = 0; i < heights.length; i++) {
                int barHeight = heights[i] * 20;
                int water = waterLevel[i] * 20;

                if (water > 0) {
                    g2d.setColor(new Color(135, 206, 250));
                    g2d.fillRect(startX + i * (barWidth + 5), startY - barHeight - water, barWidth, water);
                    g2d.setColor(Color.BLUE);
                    g2d.drawRect(startX + i * (barWidth + 5), startY - barHeight - water, barWidth, water);
                }
            }
        }

        private void drawAlgorithmInfo(Graphics2D g2d) {
            int infoX = 600;
            int infoY = 50;

            g2d.setColor(Color.BLACK);
            g2d.drawString("算法步骤:", infoX, infoY);
            g2d.drawString("1. 计算每个位置左侧最大高度", infoX, infoY + 20);
            g2d.drawString("2. 计算每个位置右侧最大高度", infoX, infoY + 40);
            g2d.drawString("3. 对每个位置计算水位: min(leftMax, rightMax) - height", infoX, infoY + 60);

            if (isAnimating) {
                g2d.setColor(Color.BLUE);
                g2d.drawString("当前步骤: " + currentStep, infoX, infoY + 100);
                g2d.drawString("当前索引: " + currentIndex, infoX, infoY + 120);
                g2d.drawString("总水量: " + totalWater, infoX, infoY + 140);
            }
        }

        private void drawArrays(Graphics2D g2d) {
            if (!isAnimating)
                return;

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
                    String value = String.valueOf(waterLevel[i]);
                    Color color = (currentStep == 2 && i == currentIndex) ? Color.BLUE : Color.BLACK;
                    g2d.setColor(color);
                    g2d.drawString(value, 50 + i * 50, 540);
                }
            }
        }

        private int getMaxHeight() {
            if (heights == null)
                return 1;
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
