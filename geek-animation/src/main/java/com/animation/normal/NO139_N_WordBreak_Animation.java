package com.animation.normal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * NO.139 单词拆分算法动画演示
 * 
 * 算法描述：
 * 给你一个字符串 s 和一个字符串列表 wordDict 作为字典。
 * 请你判断是否可以利用字典中出现的单词拼接出 s。
 * 
 * 算法思路：
 * 使用动态规划解决
 * 1. 定义 dp[i] 表示字符串 s 的前 i 个字符是否可以被拆分
 * 2. 状态转移方程：dp[i] = dp[j] && wordDict.contains(s.substring(j, i))
 * 3. 初始化：dp[0] = true
 * 
 * 时间复杂度：O(n^2 * m) 其中 n 是字符串长度，m 是单词平均长度
 * 空间复杂度：O(n)
 */
public class NO139_N_WordBreak_Animation extends JFrame {
    private JTextField stringField;
    private JTextArea dictArea;
    private JButton checkButton;
    private JButton demoButton;
    private JButton clearButton;
    private JTextArea logArea;
    private WordBreakPanel visualPanel;
    
    private String s;
    private Set<String> wordDict;
    private boolean[] dp;
    private javax.swing.Timer animationTimer;
    private int currentI, currentJ;
    private boolean animationComplete;
    private java.util.List<String> foundWords;
    
    public NO139_N_WordBreak_Animation() {
        initializeUI();
        foundWords = new ArrayList<>();
    }
    
    private void initializeUI() {
        setTitle("NO.139 单词拆分算法动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // 控制面板
        JPanel controlPanel = new JPanel(new BorderLayout());
        
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("输入字符串:"));
        stringField = new JTextField("leetcode", 15);
        inputPanel.add(stringField);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        checkButton = new JButton("开始检查");
        demoButton = new JButton("演示样例");
        clearButton = new JButton("清空");
        
        buttonPanel.add(checkButton);
        buttonPanel.add(demoButton);
        buttonPanel.add(clearButton);
        
        controlPanel.add(inputPanel, BorderLayout.NORTH);
        controlPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // 字典输入区域
        JPanel dictPanel = new JPanel(new BorderLayout());
        dictPanel.add(new JLabel("字典 (每行一个单词):"), BorderLayout.NORTH);
        dictArea = new JTextArea(4, 30);
        dictArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        dictArea.setText("leet\ncode");
        JScrollPane dictScrollPane = new JScrollPane(dictArea);
        dictPanel.add(dictScrollPane, BorderLayout.CENTER);
        
        controlPanel.add(dictPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.NORTH);
        
        // 可视化面板
        visualPanel = new WordBreakPanel();
        visualPanel.setPreferredSize(new Dimension(800, 400));
        add(visualPanel, BorderLayout.CENTER);
        
        // 日志面板
        logArea = new JTextArea(8, 50);
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.SOUTH);
        
        // 事件监听
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startCheck();
            }
        });
        
        demoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stringField.setText("leetcode");
                dictArea.setText("leet\ncode");
                startCheck();
            }
        });
        
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAll();
            }
        });
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private void startCheck() {
        try {
            s = stringField.getText().trim();
            String dictInput = dictArea.getText().trim();
            
            if (s.isEmpty() || dictInput.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入字符串和字典");
                return;
            }
            
            String[] words = dictInput.split("\n");
            wordDict = new HashSet<>();
            for (String word : words) {
                String trimmed = word.trim();
                if (!trimmed.isEmpty()) {
                    wordDict.add(trimmed);
                }
            }
            
            if (wordDict.isEmpty()) {
                JOptionPane.showMessageDialog(this, "字典不能为空");
                return;
            }
            
            // 初始化DP数组
            dp = new boolean[s.length() + 1];
            dp[0] = true; // 空字符串可以被拆分
            
            currentI = 1;
            currentJ = 0;
            animationComplete = false;
            foundWords.clear();
            
            logArea.setText("");
            appendLog("开始单词拆分检查...");
            appendLog("输入字符串: \"" + s + "\"");
            appendLog("字典: " + wordDict);
            appendLog("使用动态规划算法\n");
            appendLog("初始化: dp[0] = true (空字符串可以被拆分)");
            
            startAnimation();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "输入错误: " + e.getMessage());
        }
    }
    
    private void startAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        
        animationTimer = new javax.swing.Timer(1200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!animationComplete) {
                    if (currentI <= s.length()) {
                        if (currentJ < currentI) {
                            appendLog("\n检查 dp[" + currentI + "], j = " + currentJ);
                            
                            if (dp[currentJ]) {
                                String substring = s.substring(currentJ, currentI);
                                appendLog("  dp[" + currentJ + "] = true, 检查子串 \"" + substring + "\"");
                                
                                if (wordDict.contains(substring)) {
                                    dp[currentI] = true;
                                    foundWords.add(substring);
                                    appendLog("  \"" + substring + "\" 在字典中! dp[" + currentI + "] = true");
                                    
                                    visualPanel.updateVisualization(s, wordDict, dp, currentI, currentJ, substring, true);
                                    
                                    // 找到一个有效拆分，跳到下一个i
                                    currentJ = currentI;
                                    currentI++;
                                    if (currentI <= s.length()) {
                                        currentJ = 0;
                                    }
                                } else {
                                    appendLog("  \"" + substring + "\" 不在字典中");
                                    visualPanel.updateVisualization(s, wordDict, dp, currentI, currentJ, substring, false);
                                    currentJ++;
                                }
                            } else {
                                appendLog("  dp[" + currentJ + "] = false, 跳过");
                                currentJ++;
                            }
                        } else {
                            if (!dp[currentI]) {
                                appendLog("  dp[" + currentI + "] = false (无法拆分到位置 " + currentI + ")");
                            }
                            currentI++;
                            currentJ = 0;
                        }
                    } else {
                        animationComplete = true;
                        animationTimer.stop();
                        
                        appendLog("\n检查完成!");
                        if (dp[s.length()]) {
                            appendLog("字符串 \"" + s + "\" 可以被拆分!");
                            appendLog("找到的单词: " + foundWords);
                        } else {
                            appendLog("字符串 \"" + s + "\" 无法被拆分");
                        }
                        
                        visualPanel.setAnimationComplete(true);
                        SwingUtilities.invokeLater(() -> visualPanel.repaint());
                    }
                }
            }
        });
        animationTimer.start();
    }
    
    private void clearAll() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        stringField.setText("");
        dictArea.setText("");
        logArea.setText("");
        foundWords.clear();
        visualPanel.clear();
    }
    
    private void appendLog(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }
    
    // 可视化面板
    private class WordBreakPanel extends JPanel {
        private String s;
        private Set<String> wordDict;
        private boolean[] dp;
        private int currentI, currentJ;
        private String currentSubstring;
        private boolean substringInDict;
        private boolean animationComplete;
        
        public void updateVisualization(String s, Set<String> wordDict, boolean[] dp, 
                                      int currentI, int currentJ, String substring, boolean inDict) {
            this.s = s;
            this.wordDict = new HashSet<>(wordDict);
            this.dp = dp.clone();
            this.currentI = currentI;
            this.currentJ = currentJ;
            this.currentSubstring = substring;
            this.substringInDict = inDict;
            SwingUtilities.invokeLater(() -> visualPanel.repaint());
        }
        
        public void setAnimationComplete(boolean complete) {
            this.animationComplete = complete;
        }
        
        public void clear() {
            s = null;
            wordDict = null;
            dp = null;
            currentSubstring = null;
            animationComplete = false;
            SwingUtilities.invokeLater(() -> visualPanel.repaint());
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (s == null || dp == null) return;
            
            int width = getWidth();
            int height = getHeight();
            
            // 绘制标题
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.setColor(Color.BLACK);
            g2d.drawString("单词拆分动态规划可视化", 20, 30);
            
            // 绘制字符串
            g2d.setFont(new Font("Arial", Font.PLAIN, 14));
            g2d.drawString("字符串: \"" + s + "\"", 20, 60);
            g2d.drawString("字典: " + wordDict, 20, 80);
            
            // 绘制字符串可视化
            int charStartY = 120;
            int charWidth = 30;
            int charHeight = 30;
            
            // 绘制字符
            for (int i = 0; i < s.length(); i++) {
                int x = 50 + i * charWidth;
                
                // 设置背景色
                Color bgColor = Color.WHITE;
                if (currentSubstring != null && i >= currentJ && i < currentI) {
                    bgColor = substringInDict ? Color.GREEN : Color.PINK;
                }
                
                g2d.setColor(bgColor);
                g2d.fillRect(x, charStartY, charWidth, charHeight);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, charStartY, charWidth, charHeight);
                
                // 绘制字符
                char c = s.charAt(i);
                FontMetrics fm = g2d.getFontMetrics();
                int textX = x + (charWidth - fm.charWidth(c)) / 2;
                int textY = charStartY + (charHeight + fm.getAscent()) / 2;
                g2d.drawString(String.valueOf(c), textX, textY);
                
                // 绘制索引
                g2d.setFont(new Font("Arial", Font.PLAIN, 10));
                g2d.drawString(String.valueOf(i), x + charWidth/2 - 3, charStartY - 5);
                g2d.setFont(new Font("Arial", Font.PLAIN, 14));
            }
            
            // 绘制DP数组
            int dpStartY = charStartY + 60;
            g2d.drawString("DP数组:", 20, dpStartY - 10);
            
            for (int i = 0; i <= s.length(); i++) {
                int x = 50 + i * charWidth;
                
                // 设置颜色
                Color bgColor;
                if (i == currentI && !animationComplete) {
                    bgColor = Color.YELLOW; // 当前计算位置
                } else if (dp[i]) {
                    bgColor = new Color(144, 238, 144); // true
                } else {
                    bgColor = Color.LIGHT_GRAY; // false
                }
                
                g2d.setColor(bgColor);
                g2d.fillRect(x, dpStartY, charWidth, charHeight);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, dpStartY, charWidth, charHeight);
                
                // 绘制值
                String value = dp[i] ? "T" : "F";
                FontMetrics fm = g2d.getFontMetrics();
                int textX = x + (charWidth - fm.stringWidth(value)) / 2;
                int textY = dpStartY + (charHeight + fm.getAscent()) / 2;
                g2d.drawString(value, textX, textY);
                
                // 绘制索引
                g2d.setFont(new Font("Arial", Font.PLAIN, 10));
                g2d.drawString(String.valueOf(i), x + charWidth/2 - 3, dpStartY - 5);
                g2d.setFont(new Font("Arial", Font.PLAIN, 14));
            }
            
            // 绘制当前状态信息
            if (!animationComplete) {
                g2d.setColor(Color.BLACK);
                int infoY = dpStartY + 60;
                g2d.drawString("当前检查: dp[" + currentI + "], j = " + currentJ, 20, infoY);
                
                if (currentSubstring != null) {
                    g2d.drawString("当前子串: \"" + currentSubstring + "\" (" + 
                                 (substringInDict ? "在字典中" : "不在字典中") + ")", 20, infoY + 20);
                }
            }
            
            // 绘制状态转移方程
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.setColor(Color.BLACK);
            int equationY = dpStartY + 100;
            g2d.drawString("状态转移方程: dp[i] = dp[j] && wordDict.contains(s.substring(j, i))", 20, equationY);
            
            // 绘制最终结果
            if (animationComplete) {
                g2d.setFont(new Font("Arial", Font.BOLD, 14));
                g2d.setColor(Color.RED);
                String result = dp[s.length()] ? "可以拆分" : "无法拆分";
                g2d.drawString("结果: " + result, 20, equationY + 30);
            }
            
            // 绘制图例
            int legendY = equationY + 60;
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.setColor(Color.BLACK);
            g2d.drawString("图例:", 20, legendY);
            
            // 绿色 - 匹配的子串
            g2d.setColor(Color.GREEN);
            g2d.fillRect(80, legendY - 15, 20, 15);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(80, legendY - 15, 20, 15);
            g2d.drawString("匹配子串", 110, legendY);
            
            // 粉色 - 不匹配的子串
            g2d.setColor(Color.PINK);
            g2d.fillRect(180, legendY - 15, 20, 15);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(180, legendY - 15, 20, 15);
            g2d.drawString("不匹配子串", 210, legendY);
            
            // 黄色 - 当前位置
            g2d.setColor(Color.YELLOW);
            g2d.fillRect(300, legendY - 15, 20, 15);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(300, legendY - 15, 20, 15);
            g2d.drawString("当前位置", 330, legendY);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO139_N_WordBreak_Animation().setVisible(true);
        });
    }
}