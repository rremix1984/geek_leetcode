package com.animation.string;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * NO.2828 判别首字母缩略词 - 动画演示
 * 
 * 算法思路：
 * 1. 首先检查单词数组长度是否等于目标字符串长度
 * 2. 逐个比较每个单词的首字母与目标字符串对应位置的字符
 * 3. 如果所有首字母都匹配，则返回true，否则返回false
 * 
 * 时间复杂度：O(n)，其中n是单词数组的长度
 * 空间复杂度：O(1)，只使用常数额外空间
 */
public class NO2828_E_IsAcronym_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1400;
    private static final int WINDOW_HEIGHT = 900;
    private static final Color BACKGROUND_COLOR = new Color(240, 248, 255);
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private static final Color HIGHLIGHT_COLOR = new Color(255, 69, 0);
    private static final Color SUCCESS_COLOR = new Color(34, 139, 34);
    private static final Color ERROR_COLOR = new Color(220, 20, 60);
    private static final Color WORD_COLOR = new Color(135, 206, 250);
    private static final Color TARGET_COLOR = new Color(255, 215, 0);
    private static final Color MATCH_COLOR = new Color(144, 238, 144);
    private static final Color MISMATCH_COLOR = new Color(255, 182, 193);
    
    private JPanel animationPanel;
    private JButton startButton, nextButton, resetButton;
    private JTextField wordsField, targetField;
    private Timer animationTimer;
    
    // 算法状态变量
    private List<String> words;
    private String target;
    private boolean isAnimating;
    private int animationStep;
    private int currentIndex;
    private boolean result;
    private boolean lengthMismatch;
    private String currentFirstLetter;
    private String currentTargetChar;
    private boolean currentMatch;
    private List<Boolean> matchResults;
    
    public NO2828_E_IsAcronym_Animation() {
        initializeComponents();
        setupLayout();
        setupEventListeners();
        resetAnimation();
    }
    
    private void initializeComponents() {
        setTitle("NO.2828 判别首字母缩略词 - 动画演示");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // 创建控制面板
        JPanel controlPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        controlPanel.setBackground(BACKGROUND_COLOR);
        
        // 输入面板
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(BACKGROUND_COLOR);
        wordsField = new JTextField("alice,bob,charlie", 20);
        targetField = new JTextField("abc", 10);
        inputPanel.add(new JLabel("单词数组(逗号分隔):"));
        inputPanel.add(wordsField);
        inputPanel.add(new JLabel("目标字符串:"));
        inputPanel.add(targetField);
        
        // 控制按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(BACKGROUND_COLOR);
        startButton = new JButton("开始动画");
        nextButton = new JButton("下一步");
        resetButton = new JButton("重置");
        
        // 返回首页按钮
        JButton homeButton = new JButton("返回首页");
        homeButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                try {
                    com.animation.launcher.AlgorithmTreeLauncher.showMainWindow();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });
        
        buttonPanel.add(startButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(homeButton);
        
        controlPanel.add(inputPanel);
        controlPanel.add(buttonPanel);
        
        // 创建动画面板
        animationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawAnimation((Graphics2D) g);
            }
        };
        animationPanel.setBackground(BACKGROUND_COLOR);
        animationPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT - 120));
        
        add(controlPanel, BorderLayout.NORTH);
        add(animationPanel, BorderLayout.CENTER);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
    }
    
    private void setupEventListeners() {
        startButton.addActionListener(e -> startAnimation());
        nextButton.addActionListener(e -> nextStep());
        resetButton.addActionListener(e -> resetAnimation());
        
        // 动画定时器
        animationTimer = new Timer(1500, e -> nextStep());
    }
    
    private void startAnimation() {
        try {
            // 解析输入
            String[] wordsStr = wordsField.getText().trim().split(",");
            words = new ArrayList<>();
            for (String word : wordsStr) {
                words.add(word.trim());
            }
            target = targetField.getText().trim();
            
            if (words.isEmpty() || target.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入有效的单词数组和目标字符串！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // 开始动画
            isAnimating = true;
            animationStep = 0;
            currentIndex = 0;
            result = false;
            lengthMismatch = false;
            currentFirstLetter = "";
            currentTargetChar = "";
            currentMatch = false;
            matchResults = new ArrayList<>();
            
            setButtonsEnabled(false);
            animationTimer.start();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "输入格式错误！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void nextStep() {
        if (!isAnimating) {
            finishAnimation();
            return;
        }
        
        if (animationStep == 0) {
            // 步骤1：检查长度
            if (words.size() != target.length()) {
                lengthMismatch = true;
                result = false;
                animationStep = 99; // 跳到结束
            } else {
                lengthMismatch = false;
                animationStep++;
            }
        } else if (animationStep == 1) {
            // 步骤2：逐个比较首字母
            if (currentIndex < words.size()) {
                currentFirstLetter = String.valueOf(words.get(currentIndex).charAt(0));
                currentTargetChar = String.valueOf(target.charAt(currentIndex));
                currentMatch = currentFirstLetter.equals(currentTargetChar);
                matchResults.add(currentMatch);
                
                if (!currentMatch) {
                    result = false;
                    animationStep = 99; // 跳到结束
                } else {
                    currentIndex++;
                    if (currentIndex >= words.size()) {
                        result = true;
                        animationStep++;
                    }
                }
            }
        } else {
            // 动画完成
            finishAnimation();
        }
        
        SwingUtilities.invokeLater(() -> animationPanel.repaint());
    }
    
    private void finishAnimation() {
        isAnimating = false;
        animationTimer.stop();
        setButtonsEnabled(true);
        
        String message;
        if (lengthMismatch) {
            message = "长度不匹配！单词数组长度: " + words.size() + "，目标字符串长度: " + target.length();
        } else {
            message = result ? "是首字母缩略词！" : "不是首字母缩略词！";
        }
        
        JOptionPane.showMessageDialog(this, message, "结果", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void setButtonsEnabled(boolean enabled) {
        startButton.setEnabled(enabled);
        nextButton.setEnabled(!enabled && isAnimating);
    }
    
    private void resetAnimation() {
        words = null;
        target = "";
        isAnimating = false;
        animationStep = 0;
        currentIndex = 0;
        result = false;
        lengthMismatch = false;
        currentFirstLetter = "";
        currentTargetChar = "";
        currentMatch = false;
        matchResults = new ArrayList<>();
        
        if (animationTimer != null) {
            animationTimer.stop();
        }
        
        setButtonsEnabled(true);
        SwingUtilities.invokeLater(() -> animationPanel.repaint());
    }
    
    private void drawAnimation(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // 绘制标题
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 24));
        g2d.setColor(PRIMARY_COLOR);
        String title = "判别首字母缩略词算法演示";
        FontMetrics fm = g2d.getFontMetrics();
        int titleX = (WINDOW_WIDTH - fm.stringWidth(title)) / 2;
        g2d.drawString(title, titleX, 40);
        
        // 绘制算法说明
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawString("算法原理：检查单词数组每个单词的首字母是否能组成目标字符串", 50, 70);
        
        if (words == null) {
            g2d.setFont(new Font("微软雅黑", Font.ITALIC, 16));
            g2d.setColor(Color.GRAY);
            g2d.drawString("请输入单词数组和目标字符串，然后点击开始动画", 50, 150);
            return;
        }
        
        // 绘制长度检查
        drawLengthCheck(g2d);
        
        // 绘制单词数组
        drawWordsArray(g2d);
        
        // 绘制目标字符串
        drawTargetString(g2d);
        
        // 绘制首字母提取
        drawFirstLetters(g2d);
        
        // 绘制比较过程
        drawComparison(g2d);
        
        // 绘制算法状态
        drawAlgorithmStatus(g2d);
        
        // 绘制复杂度信息
        drawComplexityInfo(g2d);
    }
    
    private void drawLengthCheck(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("长度检查:", 50, 120);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("单词数组长度: " + words.size(), 70, 140);
        g2d.drawString("目标字符串长度: " + target.length(), 70, 160);
        
        if (animationStep >= 0) {
            if (lengthMismatch) {
                g2d.setColor(ERROR_COLOR);
                g2d.drawString("✗ 长度不匹配，无法形成首字母缩略词", 70, 180);
            } else {
                g2d.setColor(SUCCESS_COLOR);
                g2d.drawString("✓ 长度匹配，继续检查首字母", 70, 180);
            }
        }
    }
    
    private void drawWordsArray(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("单词数组:", 50, 220);
        
        int wordHeight = 40;
        int startX = 50;
        int startY = 230;
        int maxWordWidth = 0;
        
        // 计算最大单词宽度
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        FontMetrics fm = g2d.getFontMetrics();
        for (String word : words) {
            maxWordWidth = Math.max(maxWordWidth, fm.stringWidth(word) + 20);
        }
        
        for (int i = 0; i < words.size(); i++) {
            int x = startX + i * (maxWordWidth + 10);
            
            // 设置颜色
            Color wordColor = WORD_COLOR;
            if (isAnimating && !lengthMismatch) {
                if (i < currentIndex) {
                    wordColor = matchResults.get(i) ? MATCH_COLOR : MISMATCH_COLOR;
                } else if (i == currentIndex && animationStep >= 1) {
                    wordColor = HIGHLIGHT_COLOR;
                }
            }
            
            // 绘制单词框
            g2d.setColor(wordColor);
            g2d.fillRoundRect(x, startY, maxWordWidth, wordHeight, 8, 8);
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(x, startY, maxWordWidth, wordHeight, 8, 8);
            
            // 绘制单词
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            String word = words.get(i);
            int textX = x + (maxWordWidth - fm.stringWidth(word)) / 2;
            int textY = startY + (wordHeight + fm.getAscent()) / 2;
            g2d.drawString(word, textX, textY);
            
            // 高亮首字母
            if (isAnimating && !lengthMismatch && i <= currentIndex) {
                g2d.setColor(Color.RED);
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
                String firstChar = String.valueOf(word.charAt(0));
                g2d.drawString(firstChar, textX, textY);
            }
            
            // 绘制下标
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
            g2d.setColor(Color.BLUE);
            String indexText = String.valueOf(i);
            int indexX = x + (maxWordWidth - g2d.getFontMetrics().stringWidth(indexText)) / 2;
            g2d.drawString(indexText, indexX, startY - 5);
        }
    }
    
    private void drawTargetString(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("目标字符串:", 50, 320);
        
        int charSize = 40;
        int startX = 50;
        int startY = 330;
        
        for (int i = 0; i < target.length(); i++) {
            int x = startX + i * (charSize + 10);
            
            // 设置颜色
            Color charColor = TARGET_COLOR;
            if (isAnimating && !lengthMismatch) {
                if (i < currentIndex) {
                    charColor = matchResults.get(i) ? MATCH_COLOR : MISMATCH_COLOR;
                } else if (i == currentIndex && animationStep >= 1) {
                    charColor = HIGHLIGHT_COLOR;
                }
            }
            
            // 绘制字符框
            g2d.setColor(charColor);
            g2d.fillRoundRect(x, startY, charSize, charSize, 8, 8);
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(x, startY, charSize, charSize, 8, 8);
            
            // 绘制字符
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 18));
            String charText = String.valueOf(target.charAt(i));
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (charSize - fm.stringWidth(charText)) / 2;
            int textY = startY + (charSize + fm.getAscent()) / 2;
            g2d.drawString(charText, textX, textY);
            
            // 绘制下标
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
            g2d.setColor(Color.BLUE);
            String indexText = String.valueOf(i);
            int indexX = x + (charSize - g2d.getFontMetrics().stringWidth(indexText)) / 2;
            g2d.drawString(indexText, indexX, startY - 5);
        }
    }
    
    private void drawFirstLetters(Graphics2D g2d) {
        if (lengthMismatch || !isAnimating) return;
        
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("提取的首字母:", 50, 420);
        
        int charSize = 40;
        int startX = 50;
        int startY = 430;
        
        for (int i = 0; i < Math.min(currentIndex + 1, words.size()); i++) {
            int x = startX + i * (charSize + 10);
            
            // 设置颜色
            Color charColor = WORD_COLOR;
            if (i < matchResults.size()) {
                charColor = matchResults.get(i) ? MATCH_COLOR : MISMATCH_COLOR;
            } else if (i == currentIndex && animationStep >= 1) {
                charColor = HIGHLIGHT_COLOR;
            }
            
            // 绘制字符框
            g2d.setColor(charColor);
            g2d.fillRoundRect(x, startY, charSize, charSize, 8, 8);
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(x, startY, charSize, charSize, 8, 8);
            
            // 绘制首字母
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 18));
            String firstChar = String.valueOf(words.get(i).charAt(0));
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (charSize - fm.stringWidth(firstChar)) / 2;
            int textY = startY + (charSize + fm.getAscent()) / 2;
            g2d.drawString(firstChar, textX, textY);
            
            // 绘制匹配状态
            if (i < matchResults.size()) {
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 12));
                if (matchResults.get(i)) {
                    g2d.setColor(SUCCESS_COLOR);
                    g2d.drawString("✓", x + charSize - 15, startY + 15);
                } else {
                    g2d.setColor(ERROR_COLOR);
                    g2d.drawString("✗", x + charSize - 15, startY + 15);
                }
            }
        }
    }
    
    private void drawComparison(Graphics2D g2d) {
        if (lengthMismatch || !isAnimating || animationStep < 1) return;
        
        int startY = 520;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("当前比较:", 50, startY);
        
        if (currentIndex < words.size()) {
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            g2d.setColor(Color.BLACK);
            g2d.drawString("单词: \"" + words.get(currentIndex) + "\"", 70, startY + 25);
            g2d.drawString("首字母: '" + currentFirstLetter + "'", 70, startY + 45);
            g2d.drawString("目标字符: '" + currentTargetChar + "'", 70, startY + 65);
            
            if (currentMatch) {
                g2d.setColor(SUCCESS_COLOR);
                g2d.drawString("✓ 匹配成功", 70, startY + 85);
            } else {
                g2d.setColor(ERROR_COLOR);
                g2d.drawString("✗ 匹配失败", 70, startY + 85);
            }
        }
    }
    
    private void drawAlgorithmStatus(Graphics2D g2d) {
        int startY = 620;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("算法状态:", 50, startY);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("已检查: " + currentIndex + " / " + words.size(), 70, startY + 25);
        
        if (lengthMismatch) {
            g2d.setColor(ERROR_COLOR);
            g2d.drawString("结果: 长度不匹配", 70, startY + 45);
        } else if (animationStep >= 1) {
            if (result) {
                g2d.setColor(SUCCESS_COLOR);
                g2d.drawString("结果: 是首字母缩略词", 70, startY + 45);
            } else if (currentIndex >= words.size() || !currentMatch) {
                g2d.setColor(ERROR_COLOR);
                g2d.drawString("结果: 不是首字母缩略词", 70, startY + 45);
            }
        }
        
        if (isAnimating) {
            g2d.setColor(HIGHLIGHT_COLOR);
            String stepInfo = "";
            switch (animationStep) {
                case 0:
                    stepInfo = "检查数组长度与目标字符串长度";
                    break;
                case 1:
                    stepInfo = "比较第 " + (currentIndex + 1) + " 个单词的首字母";
                    break;
                default:
                    stepInfo = "算法完成";
                    break;
            }
            g2d.drawString("当前步骤: " + stepInfo, 70, startY + 65);
        }
    }
    
    private void drawComplexityInfo(Graphics2D g2d) {
        int startY = 720;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("算法复杂度分析:", 50, startY);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("• 时间复杂度: O(n) - 需要检查n个单词的首字母", 70, startY + 25);
        g2d.drawString("• 空间复杂度: O(1) - 只使用常数额外空间", 70, startY + 45);
        g2d.drawString("• 优化: 一旦发现不匹配就立即返回false", 70, startY + 65);
        
        // 显示当前统计
        g2d.setColor(SUCCESS_COLOR);
        String stats = String.format("单词数量: %d | 目标长度: %d | 匹配数量: %d", 
            words.size(), target.length(), matchResults.size());
        g2d.drawString(stats, 70, startY + 90);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO2828_E_IsAcronym_Animation().setVisible(true);
        });
    }
}