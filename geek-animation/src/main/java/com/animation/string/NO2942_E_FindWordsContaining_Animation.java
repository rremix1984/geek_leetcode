package com.animation.string;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * NO.2942 查找包含给定字符的单词 - 动画演示
 * 
 * 算法思路：
 * 1. 遍历字符串数组中的每个单词
 * 2. 对于每个单词，检查是否包含目标字符
 * 3. 如果包含，将该单词的下标添加到结果列表中
 * 4. 返回包含目标字符的单词下标列表
 * 
 * 时间复杂度：O(n * m)，其中n是单词数量，m是单词平均长度
 * 空间复杂度：O(k)，其中k是包含目标字符的单词数量
 */
public class NO2942_E_FindWordsContaining_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1400;
    private static final int WINDOW_HEIGHT = 900;
    private static final Color BACKGROUND_COLOR = new Color(240, 248, 255);
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private static final Color HIGHLIGHT_COLOR = new Color(255, 69, 0);
    private static final Color SUCCESS_COLOR = new Color(34, 139, 34);
    private static final Color WORD_COLOR = new Color(135, 206, 250);
    private static final Color CHAR_COLOR = new Color(255, 182, 193);
    private static final Color MATCH_COLOR = new Color(144, 238, 144);
    private static final Color TARGET_COLOR = new Color(255, 215, 0);
    
    private JPanel animationPanel;
    private JButton startButton, nextButton, resetButton;
    private JTextField wordsField, targetField;
    private Timer animationTimer;
    
    // 算法状态变量
    private String[] words;
    private char targetChar;
    private int currentWordIndex;
    private int currentCharIndex;
    private boolean isAnimating;
    private int animationStep;
    private List<Integer> resultIndices;
    private boolean currentWordMatched;
    private boolean currentCharMatched;
    
    public NO2942_E_FindWordsContaining_Animation() {
        initializeComponents();
        setupLayout();
        setupEventListeners();
        resetAnimation();
    }
    
    private void initializeComponents() {
        setTitle("NO.2942 查找包含给定字符的单词 - 动画演示");
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
        wordsField = new JTextField("leet,code,hello,world", 20);
        targetField = new JTextField("e", 3);
        inputPanel.add(new JLabel("单词数组(逗号分隔):"));
        inputPanel.add(wordsField);
        inputPanel.add(new JLabel("目标字符:"));
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
        animationTimer = new Timer(1000, e -> nextStep());
    }
    
    private void startAnimation() {
        try {
            // 解析输入
            String[] wordsStr = wordsField.getText().trim().split(",");
            words = new String[wordsStr.length];
            for (int i = 0; i < wordsStr.length; i++) {
                words[i] = wordsStr[i].trim();
            }
            
            String targetStr = targetField.getText().trim();
            if (targetStr.length() != 1) {
                JOptionPane.showMessageDialog(this, "目标字符必须是单个字符！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            targetChar = targetStr.charAt(0);
            
            if (words.length == 0) {
                JOptionPane.showMessageDialog(this, "请输入有效的单词数组！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // 开始动画
            currentWordIndex = 0;
            currentCharIndex = 0;
            isAnimating = true;
            animationStep = 0;
            resultIndices = new ArrayList<>();
            currentWordMatched = false;
            currentCharMatched = false;
            
            setButtonsEnabled(false);
            animationTimer.start();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "输入格式错误！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void nextStep() {
        if (!isAnimating || currentWordIndex >= words.length) {
            finishAnimation();
            return;
        }
        
        String currentWord = words[currentWordIndex];
        
        if (animationStep == 0) {
            // 步骤1：开始检查当前单词
            currentCharIndex = 0;
            currentWordMatched = false;
            currentCharMatched = false;
            animationStep++;
        } else if (animationStep == 1) {
            // 步骤2：检查当前字符
            if (currentCharIndex < currentWord.length()) {
                currentCharMatched = (currentWord.charAt(currentCharIndex) == targetChar);
                if (currentCharMatched) {
                    currentWordMatched = true;
                }
                currentCharIndex++;
            } else {
                // 当前单词检查完毕
                animationStep++;
            }
        } else if (animationStep == 2) {
            // 步骤3：如果单词匹配，添加到结果中
            if (currentWordMatched) {
                resultIndices.add(currentWordIndex);
            }
            animationStep++;
        } else {
            // 步骤4：移动到下一个单词
            currentWordIndex++;
            animationStep = 0;
        }
        
        animationPanel.repaint();
    }
    
    private void finishAnimation() {
        isAnimating = false;
        animationTimer.stop();
        setButtonsEnabled(true);
        
        JOptionPane.showMessageDialog(this, 
            "算法完成！\n" +
            "包含字符 '" + targetChar + "' 的单词下标: " + resultIndices, 
            "结果", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void setButtonsEnabled(boolean enabled) {
        startButton.setEnabled(enabled);
        nextButton.setEnabled(!enabled && isAnimating);
    }
    
    private void resetAnimation() {
        words = null;
        targetChar = ' ';
        currentWordIndex = 0;
        currentCharIndex = 0;
        isAnimating = false;
        animationStep = 0;
        resultIndices = new ArrayList<>();
        currentWordMatched = false;
        currentCharMatched = false;
        
        if (animationTimer != null) {
            animationTimer.stop();
        }
        
        setButtonsEnabled(true);
        animationPanel.repaint();
    }
    
    private void drawAnimation(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // 绘制标题
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 24));
        g2d.setColor(PRIMARY_COLOR);
        String title = "查找包含给定字符的单词算法演示";
        FontMetrics fm = g2d.getFontMetrics();
        int titleX = (WINDOW_WIDTH - fm.stringWidth(title)) / 2;
        g2d.drawString(title, titleX, 40);
        
        // 绘制算法说明
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawString("算法原理：遍历每个单词，检查是否包含目标字符，如果包含则记录下标", 50, 70);
        
        if (words == null) {
            g2d.setFont(new Font("微软雅黑", Font.ITALIC, 16));
            g2d.setColor(Color.GRAY);
            g2d.drawString("请输入单词数组和目标字符，然后点击开始动画", 50, 150);
            return;
        }
        
        // 绘制目标字符
        drawTargetChar(g2d);
        
        // 绘制单词数组
        drawWordsArray(g2d);
        
        // 绘制当前处理的单词详情
        drawCurrentWordDetail(g2d);
        
        // 绘制结果列表
        drawResultList(g2d);
        
        // 绘制算法状态
        drawAlgorithmStatus(g2d);
        
        // 绘制复杂度信息
        drawComplexityInfo(g2d);
    }
    
    private void drawTargetChar(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 18));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("目标字符:", 50, 120);
        
        // 绘制目标字符框
        int charX = 150;
        int charY = 100;
        int charSize = 40;
        
        g2d.setColor(TARGET_COLOR);
        g2d.fillRoundRect(charX, charY, charSize, charSize, 8, 8);
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(charX, charY, charSize, charSize, 8, 8);
        
        // 绘制字符
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 20));
        String charText = String.valueOf(targetChar);
        FontMetrics fm = g2d.getFontMetrics();
        int textX = charX + (charSize - fm.stringWidth(charText)) / 2;
        int textY = charY + (charSize + fm.getAscent()) / 2;
        g2d.drawString(charText, textX, textY);
    }
    
    private void drawWordsArray(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("单词数组:", 50, 180);
        
        int wordHeight = 40;
        int startX = 50;
        int startY = 190;
        int maxWordWidth = 0;
        
        // 计算最大单词宽度
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        FontMetrics fm = g2d.getFontMetrics();
        for (String word : words) {
            maxWordWidth = Math.max(maxWordWidth, fm.stringWidth(word) + 20);
        }
        
        for (int i = 0; i < words.length; i++) {
            int x = startX + i * (maxWordWidth + 10);
            
            // 设置颜色
            Color wordColor = WORD_COLOR;
            if (i == currentWordIndex && isAnimating) {
                wordColor = HIGHLIGHT_COLOR;
            } else if (resultIndices.contains(i)) {
                wordColor = MATCH_COLOR;
            }
            
            // 绘制单词框
            g2d.setColor(wordColor);
            g2d.fillRoundRect(x, startY, maxWordWidth, wordHeight, 8, 8);
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(x, startY, maxWordWidth, wordHeight, 8, 8);
            
            // 绘制单词
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            String word = words[i];
            int textX = x + (maxWordWidth - fm.stringWidth(word)) / 2;
            int textY = startY + (wordHeight + fm.getAscent()) / 2;
            g2d.drawString(word, textX, textY);
            
            // 绘制下标
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
            g2d.setColor(Color.BLUE);
            String indexText = String.valueOf(i);
            int indexX = x + (maxWordWidth - g2d.getFontMetrics().stringWidth(indexText)) / 2;
            g2d.drawString(indexText, indexX, startY - 5);
        }
    }
    
    private void drawCurrentWordDetail(Graphics2D g2d) {
        if (!isAnimating || currentWordIndex >= words.length) return;
        
        int startY = 280;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("当前检查的单词:", 50, startY);
        
        String currentWord = words[currentWordIndex];
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("单词: \"" + currentWord + "\" (下标: " + currentWordIndex + ")", 70, startY + 25);
        
        // 绘制字符详情
        int charSize = 30;
        int charStartX = 70;
        int charY = startY + 40;
        
        for (int i = 0; i < currentWord.length(); i++) {
            char c = currentWord.charAt(i);
            int x = charStartX + i * (charSize + 5);
            
            // 设置颜色
            Color charColor = CHAR_COLOR;
            if (i < currentCharIndex) {
                if (c == targetChar) {
                    charColor = MATCH_COLOR;
                } else {
                    charColor = Color.LIGHT_GRAY;
                }
            } else if (i == currentCharIndex && animationStep == 1) {
                charColor = HIGHLIGHT_COLOR;
            }
            
            // 绘制字符框
            g2d.setColor(charColor);
            g2d.fillRoundRect(x, charY, charSize, charSize, 5, 5);
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(x, charY, charSize, charSize, 5, 5);
            
            // 绘制字符
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            String charText = String.valueOf(c);
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (charSize - fm.stringWidth(charText)) / 2;
            int textY = charY + (charSize + fm.getAscent()) / 2;
            g2d.setColor(Color.BLACK);
            g2d.drawString(charText, textX, textY);
            
            // 标记匹配的字符
            if (c == targetChar && i < currentCharIndex) {
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 10));
                g2d.setColor(Color.RED);
                g2d.drawString("✓", x + charSize - 10, charY + 12);
            }
        }
        
        // 显示当前检查状态
        if (animationStep >= 1) {
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            g2d.setColor(Color.BLACK);
            g2d.drawString("已检查字符: " + currentCharIndex + " / " + currentWord.length(), 70, charY + charSize + 20);
            
            if (currentWordMatched) {
                g2d.setColor(SUCCESS_COLOR);
                g2d.drawString("✓ 找到匹配字符！", 70, charY + charSize + 40);
            }
        }
    }
    
    private void drawResultList(Graphics2D g2d) {
        int startY = 450;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("匹配结果:", 50, startY);
        
        if (resultIndices.isEmpty()) {
            g2d.setFont(new Font("微软雅黑", Font.ITALIC, 14));
            g2d.setColor(Color.GRAY);
            g2d.drawString("暂无匹配的单词", 70, startY + 25);
        } else {
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            g2d.setColor(Color.BLACK);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < resultIndices.size(); i++) {
                if (i > 0) sb.append(", ");
                int index = resultIndices.get(i);
                sb.append(index).append("(\"").append(words[index]).append("\")");
            }
            g2d.drawString("匹配的下标(单词): " + sb.toString(), 70, startY + 25);
        }
    }
    
    private void drawAlgorithmStatus(Graphics2D g2d) {
        int startY = 510;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("算法状态:", 50, startY);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("已处理单词: " + currentWordIndex + " / " + words.length, 70, startY + 25);
        g2d.drawString("找到匹配: " + resultIndices.size() + " 个", 70, startY + 45);
        
        if (isAnimating && currentWordIndex < words.length) {
            g2d.setColor(HIGHLIGHT_COLOR);
            String stepInfo = "";
            switch (animationStep) {
                case 0:
                    stepInfo = "开始检查单词 \"" + words[currentWordIndex] + "\"";
                    break;
                case 1:
                    stepInfo = "检查字符 '" + (currentCharIndex < words[currentWordIndex].length() ? 
                        words[currentWordIndex].charAt(currentCharIndex) : "完成") + "'";
                    break;
                case 2:
                    stepInfo = "单词检查完毕，" + (currentWordMatched ? "找到匹配" : "未找到匹配");
                    break;
                case 3:
                    stepInfo = "准备检查下一个单词";
                    break;
            }
            g2d.drawString("当前步骤: " + stepInfo, 70, startY + 65);
        }
    }
    
    private void drawComplexityInfo(Graphics2D g2d) {
        int startY = 620;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("算法复杂度分析:", 50, startY);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("• 时间复杂度: O(n × m) - 遍历n个单词，每个单词最多检查m个字符", 70, startY + 25);
        g2d.drawString("• 空间复杂度: O(k) - 存储k个匹配的下标", 70, startY + 45);
        g2d.drawString("• 优化: 找到匹配字符后可以立即跳出内层循环", 70, startY + 65);
        
        // 显示当前统计
        g2d.setColor(SUCCESS_COLOR);
        String stats = String.format("单词数量: %d | 目标字符: '%c' | 匹配数量: %d", 
            words.length, targetChar, resultIndices.size());
        g2d.drawString(stats, 70, startY + 90);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO2942_E_FindWordsContaining_Animation().setVisible(true);
        });
    }
}