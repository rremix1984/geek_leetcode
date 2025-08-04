package com.leetcode.animation.string;

import javax.swing.*;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.awt.*;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.awt.event.ActionEvent;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.awt.event.ActionListener;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.util.ArrayList;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.util.List;
import com.leetcode.animation.AlgorithmTreeLauncher;

/**
 * NO.1002 查找共用字符 - 动画演示
 * 
 * 算法描述：
 * 给你一个字符串数组words，请你找出所有在words的每个字符串中都出现的共用字符
 * （包括重复字符），并以数组形式返回。
 * 
 * 动画特色：
 * • 可视化字符频率统计过程
 * • 显示每个字符串的字符计数
 * • 展示最小频率的计算过程
 * • 动态显示结果构建过程
 */
public class NO1002_E_CommonChars_Animation extends JFrame {
    
    // 动画控制
    private Timer animationTimer;
    private int currentStep = 0;
    private boolean isAnimating = false;
    
    // 算法数据
    private String[] words = {"bella", "label", "roller"};
    private int[][] charCounts;  // 每个字符串的字符计数
    private int[] minCounts;     // 最小字符计数
    private List<String> result; // 结果列表
    private int currentWordIndex = 0;
    private int currentCharIndex = 0;
    private int currentResultIndex = 0;
    
    // UI组件
    private JPanel controlPanel;
    private JButton startButton, stepButton, resetButton, homeButton;
    private JLabel statusLabel;
    private CharVisualizationPanel visualPanel;
    
    public NO1002_E_CommonChars_Animation() {
        setTitle("NO.1002 查找共用字符 - 动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        initializeData();
        initComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initializeData() {
        charCounts = new int[words.length][26];
        minCounts = new int[26];
        result = new ArrayList<>();
        
        // 初始化最小计数为最大值
        for (int i = 0; i < 26; i++) {
            minCounts[i] = Integer.MAX_VALUE;
        }
    }
    
    private void initComponents() {
        // 控制面板
        controlPanel = new JPanel();
        startButton = new JButton("开始动画");
        stepButton = new JButton("单步执行");
        resetButton = new JButton("重置");
        homeButton = new JButton("返回首页");
        statusLabel = new JLabel("点击开始按钮启动动画演示");
        
        // 设置字体
        Font buttonFont = new Font("微软雅黑", Font.PLAIN, 14);
        startButton.setFont(buttonFont);
        stepButton.setFont(buttonFont);
        resetButton.setFont(buttonFont);
        homeButton.setFont(buttonFont);
        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        
        // 可视化面板
        visualPanel = new CharVisualizationPanel();
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 控制面板布局
        controlPanel.add(startButton);
        controlPanel.add(stepButton);
        controlPanel.add(resetButton);
        controlPanel.add(homeButton);
        controlPanel.add(statusLabel);
        
        add(controlPanel, BorderLayout.NORTH);
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
        
        animationTimer = new Timer(1500, e -> stepExecution());
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
                statusLabel.setText("开始统计第一个字符串的字符频率");
                currentWordIndex = 0;
                currentCharIndex = 0;
                break;
            case 1:
                countCharactersInCurrentWord();
                break;
            case 2:
                if (currentWordIndex < words.length - 1) {
                    currentWordIndex++;
                    currentCharIndex = 0;
                    statusLabel.setText("统计第" + (currentWordIndex + 1) + "个字符串的字符频率");
                    currentStep = 1;
                } else {
                    statusLabel.setText("开始计算最小字符频率");
                    currentStep = 3;
                }
                break;
            case 3:
                calculateMinimumCounts();
                break;
            case 4:
                statusLabel.setText("构建结果数组");
                currentResultIndex = 0;
                break;
            case 5:
                buildResult();
                break;
            case 6:
                statusLabel.setText("算法执行完成！共用字符为：" + result.toString());
                stopAnimation();
                return;
        }
        
        currentStep++;
        visualPanel.repaint();
    }
    
    private void countCharactersInCurrentWord() {
        String word = words[currentWordIndex];
        
        if (currentCharIndex < word.length()) {
            char c = word.charAt(currentCharIndex);
            charCounts[currentWordIndex][c - 'a']++;
            statusLabel.setText("统计字符 '" + c + "' 在字符串 \"" + word + "\" 中的频率");
            currentCharIndex++;
        } else {
            statusLabel.setText("完成字符串 \"" + word + "\" 的字符统计");
            currentStep = 2;
        }
    }
    
    private void calculateMinimumCounts() {
        // 计算每个字符在所有字符串中的最小出现次数
        for (int i = 0; i < 26; i++) {
            int min = charCounts[0][i];
            for (int j = 1; j < words.length; j++) {
                min = Math.min(min, charCounts[j][i]);
            }
            minCounts[i] = min;
        }
        statusLabel.setText("完成最小字符频率计算");
    }
    
    private void buildResult() {
        if (currentResultIndex < 26) {
            if (minCounts[currentResultIndex] > 0) {
                char c = (char) (currentResultIndex + 'a');
                for (int i = 0; i < minCounts[currentResultIndex]; i++) {
                    result.add(String.valueOf(c));
                }
                statusLabel.setText("添加字符 '" + c + "' 到结果中，数量：" + minCounts[currentResultIndex]);
            }
            currentResultIndex++;
        } else {
            currentStep = 6;
        }
    }
    
    private void resetAnimation() {
        stopAnimation();
        currentStep = 0;
        currentWordIndex = 0;
        currentCharIndex = 0;
        currentResultIndex = 0;
        
        // 重置数据
        charCounts = new int[words.length][26];
        minCounts = new int[26];
        result.clear();
        
        for (int i = 0; i < 26; i++) {
            minCounts[i] = Integer.MAX_VALUE;
        }
        
        statusLabel.setText("点击开始按钮启动动画演示");
        visualPanel.repaint();
    }
    
    // 可视化面板
    private class CharVisualizationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            drawWords(g2d);
            drawCharacterCounts(g2d);
            drawMinimumCounts(g2d);
            drawResult(g2d);
        }
        
        private void drawWords(Graphics2D g2d) {
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g2d.setColor(Color.BLACK);
            g2d.drawString("输入字符串数组：", 20, 30);
            
            int x = 20;
            int y = 60;
            for (int i = 0; i < words.length; i++) {
                if (i == currentWordIndex && currentStep >= 1 && currentStep <= 2) {
                    g2d.setColor(Color.RED);
                } else {
                    g2d.setColor(Color.BLACK);
                }
                
                g2d.drawString("\"" + words[i] + "\"", x, y);
                x += 100;
            }
        }
        
        private void drawCharacterCounts(Graphics2D g2d) {
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            g2d.setColor(Color.BLACK);
            g2d.drawString("字符频率统计：", 20, 120);
            
            int startY = 150;
            for (int i = 0; i < words.length; i++) {
                g2d.setColor(Color.BLACK);
                g2d.drawString(words[i] + ":", 20, startY + i * 80);
                
                // 绘制字符频率条形图
                int x = 80;
                for (int j = 0; j < 26; j++) {
                    if (charCounts[i][j] > 0) {
                        char c = (char) (j + 'a');
                        
                        // 高亮当前正在处理的字符
                        if (i == currentWordIndex && currentStep == 1) {
                            g2d.setColor(Color.ORANGE);
                        } else {
                            g2d.setColor(Color.BLUE);
                        }
                        
                        int height = charCounts[i][j] * 20;
                        g2d.fillRect(x, startY + i * 80 + 20 - height, 15, height);
                        
                        g2d.setColor(Color.BLACK);
                        g2d.drawString(String.valueOf(c), x + 2, startY + i * 80 + 35);
                        g2d.drawString(String.valueOf(charCounts[i][j]), x + 2, startY + i * 80 + 50);
                        
                        x += 25;
                    }
                }
            }
        }
        
        private void drawMinimumCounts(Graphics2D g2d) {
            if (currentStep >= 3) {
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
                g2d.setColor(Color.BLACK);
                g2d.drawString("最小字符频率：", 20, 400);
                
                int x = 20;
                int y = 430;
                for (int i = 0; i < 26; i++) {
                    if (minCounts[i] > 0 && minCounts[i] != Integer.MAX_VALUE) {
                        char c = (char) (i + 'a');
                        
                        g2d.setColor(Color.GREEN);
                        int height = minCounts[i] * 20;
                        g2d.fillRect(x, y + 20 - height, 15, height);
                        
                        g2d.setColor(Color.BLACK);
                        g2d.drawString(String.valueOf(c), x + 2, y + 35);
                        g2d.drawString(String.valueOf(minCounts[i]), x + 2, y + 50);
                        
                        x += 25;
                    }
                }
            }
        }
        
        private void drawResult(Graphics2D g2d) {
            if (currentStep >= 5) {
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
                g2d.setColor(Color.BLACK);
                g2d.drawString("结果数组：", 20, 520);
                
                g2d.setColor(Color.MAGENTA);
                g2d.drawString(result.toString(), 120, 520);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO1002_E_CommonChars_Animation().setVisible(true);
        });
    }
}