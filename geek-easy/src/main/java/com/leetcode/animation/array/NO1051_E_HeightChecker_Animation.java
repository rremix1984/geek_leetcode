package com.leetcode.animation.array;

import javax.swing.*;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.awt.*;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.awt.event.ActionEvent;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.awt.event.ActionListener;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.util.Arrays;
import com.leetcode.animation.AlgorithmTreeLauncher;

/**
 * NO.1051 高度检查器 - 动画演示
 * 
 * 算法描述：
 * 学生需要按照非递减的高度顺序排成一行。给你一个整数数组heights，
 * 表示当前学生站位的高度情况。返回满足heights[i] != expected[i]的下标数量。
 * 
 * 动画特色：
 * • 可视化排序过程
 * • 显示原始数组和期望数组的比较
 * • 展示计数排序的过程
 * • 动态显示不匹配位置的统计
 */
public class NO1051_E_HeightChecker_Animation extends JFrame {
    
    // 动画控制
    private Timer animationTimer;
    private int currentStep = 0;
    private boolean isAnimating = false;
    
    // 算法数据
    private int[] heights = {1, 1, 4, 2, 1, 3};
    private int[] expected;
    private int[] countArray;
    private int maxHeight;
    private int currentIndex = 0;
    private int mismatchCount = 0;
    private int currentCountIndex = 0;
    private int currentSortIndex = 0;
    
    // UI组件
    private JPanel controlPanel;
    private JButton startButton, stepButton, resetButton, homeButton;
    private JLabel statusLabel, mismatchLabel;
    private HeightVisualizationPanel visualPanel;
    
    public NO1051_E_HeightChecker_Animation() {
        setTitle("NO.1051 高度检查器 - 动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        initializeData();
        initComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initializeData() {
        maxHeight = Arrays.stream(heights).max().getAsInt();
        countArray = new int[maxHeight + 1];
        expected = new int[heights.length];
        mismatchCount = 0;
        currentIndex = 0;
        currentCountIndex = 0;
        currentSortIndex = 0;
    }
    
    private void initComponents() {
        // 控制面板
        controlPanel = new JPanel();
        startButton = new JButton("开始动画");
        stepButton = new JButton("单步执行");
        resetButton = new JButton("重置");
        homeButton = new JButton("返回首页");
        statusLabel = new JLabel("点击开始按钮启动动画演示");
        mismatchLabel = new JLabel("不匹配数量：0");
        
        // 设置字体
        Font buttonFont = new Font("微软雅黑", Font.PLAIN, 14);
        startButton.setFont(buttonFont);
        stepButton.setFont(buttonFont);
        resetButton.setFont(buttonFont);
        homeButton.setFont(buttonFont);
        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        mismatchLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        mismatchLabel.setForeground(Color.RED);
        
        // 可视化面板
        visualPanel = new HeightVisualizationPanel();
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 控制面板布局
        controlPanel.add(startButton);
        controlPanel.add(stepButton);
        controlPanel.add(resetButton);
        controlPanel.add(homeButton);
        controlPanel.add(Box.createHorizontalStrut(20));
        controlPanel.add(mismatchLabel);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(controlPanel, BorderLayout.CENTER);
        topPanel.add(statusLabel, BorderLayout.SOUTH);
        
        add(topPanel, BorderLayout.NORTH);
        add(visualPanel, BorderLayout.CENTER);
    }
    
    private void setupEventHandlers() {
        startButton.addActionListener(e -> {
            if (!isAnimating) {
                startAnimation();
            } else {
                stopAnimation();
            }
        });
        
        stepButton.addActionListener(e -> {
            if (!isAnimating) {
                stepExecution();
            }
        });
        
        resetButton.addActionListener(e -> resetAnimation());
        
        homeButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                try {
                    dispose(); // 关闭当前动画窗口
                    com.leetcode.animation.AlgorithmTreeLauncher.showMainWindow();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });
    }
    
    private void startAnimation() {
        isAnimating = true;
        startButton.setText("停止动画");
        
        animationTimer = new Timer(1200, e -> stepExecution());
        animationTimer.start();
    }
    
    private void stopAnimation() {
        isAnimating = false;
        startButton.setText("开始动画");
        
        if (animationTimer != null) {
            animationTimer.stop();
        }
    }
    
    private void stepExecution() {
        switch (currentStep) {
            case 0:
                statusLabel.setText("开始计数排序 - 统计每个高度的出现次数");
                currentIndex = 0;
                break;
            case 1:
                if (currentIndex < heights.length) {
                    countArray[heights[currentIndex]]++;
                    statusLabel.setText("统计高度 " + heights[currentIndex] + " 的出现次数");
                    currentIndex++;
                } else {
                    statusLabel.setText("完成高度统计，开始构建期望数组");
                    currentStep = 2;
                    currentCountIndex = 1;
                    currentSortIndex = 0;
                }
                break;
            case 2:
                if (currentCountIndex <= maxHeight) {
                    if (countArray[currentCountIndex] > 0) {
                        expected[currentSortIndex] = currentCountIndex;
                        countArray[currentCountIndex]--;
                        statusLabel.setText("将高度 " + currentCountIndex + " 放入期望数组位置 " + currentSortIndex);
                        currentSortIndex++;
                    } else {
                        currentCountIndex++;
                    }
                    
                    if (currentSortIndex >= heights.length) {
                        statusLabel.setText("期望数组构建完成，开始比较");
                        currentStep = 3;
                        currentIndex = 0;
                    }
                } else {
                    currentCountIndex = 1;
                }
                break;
            case 3:
                if (currentIndex < heights.length) {
                    if (heights[currentIndex] != expected[currentIndex]) {
                        mismatchCount++;
                        statusLabel.setText("位置 " + currentIndex + " 不匹配：" + 
                                          heights[currentIndex] + " != " + expected[currentIndex]);
                    } else {
                        statusLabel.setText("位置 " + currentIndex + " 匹配：" + 
                                          heights[currentIndex] + " == " + expected[currentIndex]);
                    }
                    mismatchLabel.setText("不匹配数量：" + mismatchCount);
                    currentIndex++;
                } else {
                    statusLabel.setText("算法执行完成！总共有 " + mismatchCount + " 个位置不匹配");
                    stopAnimation();
                    return;
                }
                break;
        }
        
        if (currentStep < 3) {
            currentStep++;
        }
        visualPanel.repaint();
    }
    
    private void resetAnimation() {
        stopAnimation();
        currentStep = 0;
        currentIndex = 0;
        currentCountIndex = 0;
        currentSortIndex = 0;
        mismatchCount = 0;
        
        // 重置数据
        countArray = new int[maxHeight + 1];
        expected = new int[heights.length];
        
        statusLabel.setText("点击开始按钮启动动画演示");
        mismatchLabel.setText("不匹配数量：0");
        visualPanel.repaint();
    }
    
    // 高度可视化面板
    private class HeightVisualizationPanel extends JPanel {
        private final int BAR_WIDTH = 60;
        private final int BAR_SPACING = 80;
        private final int START_X = 50;
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            drawOriginalHeights(g2d);
            drawCountArray(g2d);
            drawExpectedHeights(g2d);
            drawComparison(g2d);
        }
        
        private void drawOriginalHeights(Graphics2D g2d) {
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g2d.setColor(Color.BLACK);
            g2d.drawString("原始高度数组：", START_X, 30);
            
            int baseY = 100;
            for (int i = 0; i < heights.length; i++) {
                int x = START_X + i * BAR_SPACING;
                int height = heights[i] * 20;
                
                // 高亮当前处理的元素
                if (currentStep == 1 && i == currentIndex) {
                    g2d.setColor(Color.RED);
                } else if (currentStep == 3 && i == currentIndex) {
                    if (heights[i] != expected[i]) {
                        g2d.setColor(Color.RED);
                    } else {
                        g2d.setColor(Color.GREEN);
                    }
                } else if (currentStep == 3 && i < currentIndex) {
                    if (heights[i] != expected[i]) {
                        g2d.setColor(Color.PINK);
                    } else {
                        g2d.setColor(Color.LIGHT_GRAY);
                    }
                } else {
                    g2d.setColor(Color.BLUE);
                }
                
                g2d.fillRect(x, baseY - height, BAR_WIDTH, height);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, baseY - height, BAR_WIDTH, height);
                
                // 绘制数值
                g2d.setFont(new Font("Arial", Font.BOLD, 14));
                g2d.drawString(String.valueOf(heights[i]), x + BAR_WIDTH/2 - 5, baseY + 20);
                
                // 绘制索引
                g2d.setFont(new Font("Arial", Font.PLAIN, 12));
                g2d.setColor(Color.GRAY);
                g2d.drawString("[" + i + "]", x + BAR_WIDTH/2 - 8, baseY + 35);
            }
        }
        
        private void drawCountArray(Graphics2D g2d) {
            if (currentStep >= 1) {
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
                g2d.setColor(Color.BLACK);
                g2d.drawString("高度计数数组：", START_X, 180);
                
                int baseY = 250;
                for (int i = 1; i <= maxHeight; i++) {
                    int x = START_X + (i - 1) * BAR_SPACING;
                    int height = countArray[i] * 30;
                    
                    if (currentStep == 2 && i == currentCountIndex && countArray[i] > 0) {
                        g2d.setColor(Color.ORANGE);
                    } else {
                        g2d.setColor(Color.CYAN);
                    }
                    
                    if (height > 0) {
                        g2d.fillRect(x, baseY - height, BAR_WIDTH, height);
                    }
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(x, baseY - height, BAR_WIDTH, Math.max(height, 20));
                    
                    // 绘制高度值
                    g2d.setFont(new Font("Arial", Font.BOLD, 12));
                    g2d.drawString("高度" + i, x + 5, baseY + 15);
                    
                    // 绘制计数
                    g2d.setFont(new Font("Arial", Font.BOLD, 14));
                    g2d.setColor(Color.RED);
                    g2d.drawString(String.valueOf(countArray[i]), x + BAR_WIDTH/2 - 5, baseY - 5);
                }
            }
        }
        
        private void drawExpectedHeights(Graphics2D g2d) {
            if (currentStep >= 2) {
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
                g2d.setColor(Color.BLACK);
                g2d.drawString("期望高度数组（排序后）：", START_X, 330);
                
                int baseY = 400;
                for (int i = 0; i < expected.length; i++) {
                    int x = START_X + i * BAR_SPACING;
                    int height = expected[i] * 20;
                    
                    // 高亮当前构建的位置
                    if (currentStep == 2 && i == currentSortIndex) {
                        g2d.setColor(Color.YELLOW);
                    } else if (currentStep == 3 && i == currentIndex) {
                        if (heights[i] != expected[i]) {
                            g2d.setColor(Color.RED);
                        } else {
                            g2d.setColor(Color.GREEN);
                        }
                    } else if (currentStep == 3 && i < currentIndex) {
                        if (heights[i] != expected[i]) {
                            g2d.setColor(Color.PINK);
                        } else {
                            g2d.setColor(Color.LIGHT_GRAY);
                        }
                    } else {
                        g2d.setColor(Color.GREEN);
                    }
                    
                    if (expected[i] > 0 || currentStep >= 3) {
                        g2d.fillRect(x, baseY - height, BAR_WIDTH, height);
                    }
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(x, baseY - height, BAR_WIDTH, Math.max(height, 20));
                    
                    // 绘制数值
                    if (expected[i] > 0 || currentStep >= 3) {
                        g2d.setFont(new Font("Arial", Font.BOLD, 14));
                        g2d.drawString(String.valueOf(expected[i]), x + BAR_WIDTH/2 - 5, baseY + 20);
                    }
                    
                    // 绘制索引
                    g2d.setFont(new Font("Arial", Font.PLAIN, 12));
                    g2d.setColor(Color.GRAY);
                    g2d.drawString("[" + i + "]", x + BAR_WIDTH/2 - 8, baseY + 35);
                }
            }
        }
        
        private void drawComparison(Graphics2D g2d) {
            if (currentStep >= 3) {
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
                g2d.setColor(Color.BLACK);
                g2d.drawString("比较结果：", START_X, 480);
                
                // 绘制比较箭头和结果
                for (int i = 0; i < Math.min(currentIndex + 1, heights.length); i++) {
                    int x = START_X + i * BAR_SPACING + BAR_WIDTH/2;
                    
                    // 绘制箭头
                    g2d.setColor(Color.GRAY);
                    g2d.drawLine(x, 120, x, 380);
                    
                    // 绘制比较结果
                    if (heights[i] != expected[i]) {
                        g2d.setColor(Color.RED);
                        g2d.drawString("✗", x - 5, 250);
                    } else {
                        g2d.setColor(Color.GREEN);
                        g2d.drawString("✓", x - 5, 250);
                    }
                }
                
                // 显示统计信息
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
                g2d.setColor(Color.BLACK);
                g2d.drawString("已检查：" + Math.min(currentIndex, heights.length) + "/" + heights.length, 
                              START_X, 520);
                g2d.setColor(Color.RED);
                g2d.drawString("不匹配：" + mismatchCount, START_X + 150, 520);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO1051_E_HeightChecker_Animation().setVisible(true);
        });
    }
}