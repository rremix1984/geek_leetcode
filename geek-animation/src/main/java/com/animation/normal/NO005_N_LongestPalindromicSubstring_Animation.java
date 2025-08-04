package com.animation.normal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * NO005 最长回文子串 动画演示
 * 
 * 题目描述：
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 * 
 * @author AI Assistant
 */
public class NO005_N_LongestPalindromicSubstring_Animation extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    // UI组件
    private JTextField inputField;
    private JButton findButton;
    private JButton clearButton;
    private JButton demoButton;
    private JTextArea logArea;
    private JPanel visualPanel;
    
    // 数据
    private String inputString;
    private String longestPalindrome;
    private int currentCenter;
    private int currentLeft;
    private int currentRight;
    private boolean isAnimating;
    private List<String> allPalindromes;
    
    public NO005_N_LongestPalindromicSubstring_Animation() {
        allPalindromes = new ArrayList<>();
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("NO005 - 最长回文子串 动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // 顶部控制面板
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.NORTH);
        
        // 中央可视化面板
        visualPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawVisualization(g);
            }
        };
        visualPanel.setBackground(Color.WHITE);
        visualPanel.setPreferredSize(new Dimension(800, 400));
        add(visualPanel, BorderLayout.CENTER);
        
        // 底部日志面板
        logArea = new JTextArea(8, 50);
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("操作日志"));
        add(scrollPane, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        
        addLog("最长回文子串算法初始化完成");
        addLog("请输入字符串，点击'查找回文'开始演示");
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        panel.add(new JLabel("输入字符串:"));
        inputField = new JTextField(20);
        inputField.setText("babad");
        panel.add(inputField);
        
        findButton = new JButton("查找回文");
        findButton.addActionListener(e -> findLongestPalindrome());
        panel.add(findButton);
        
        demoButton = new JButton("演示样例");
        demoButton.addActionListener(e -> loadDemoData());
        panel.add(demoButton);
        
        clearButton = new JButton("清空");
        clearButton.addActionListener(e -> clearVisualization());
        panel.add(clearButton);
        
        return panel;
    }
    
    private void findLongestPalindrome() {
        String input = inputField.getText().trim();
        if (input.isEmpty()) {
            addLog("请输入有效的字符串");
            return;
        }
        
        inputString = input;
        longestPalindrome = "";
        allPalindromes.clear();
        currentCenter = -1;
        currentLeft = -1;
        currentRight = -1;
        
        addLog("开始查找字符串 \"" + input + "\" 的最长回文子串");
        
        // 使用中心扩展算法
        String result = expandAroundCenter(input);
        longestPalindrome = result;
        
        addLog("查找完成！最长回文子串: \"" + result + "\"");
        addLog("长度: " + result.length());
        
        visualPanel.repaint();
    }
    
    private String expandAroundCenter(String s) {
        if (s == null || s.length() < 1) return "";
        
        int start = 0, end = 0;
        
        for (int i = 0; i < s.length(); i++) {
            // 奇数长度回文
            int len1 = expandAroundCenterHelper(s, i, i);
            // 偶数长度回文
            int len2 = expandAroundCenterHelper(s, i, i + 1);
            
            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
            
            // 记录当前中心位置用于可视化
            currentCenter = i;
            
            try {
                Thread.sleep(200); // 动画延迟
                SwingUtilities.invokeLater(() -> visualPanel.repaint());
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        
        return s.substring(start, end + 1);
    }
    
    private int expandAroundCenterHelper(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            currentLeft = left;
            currentRight = right;
            
            // 记录找到的回文
            String palindrome = s.substring(left, right + 1);
            if (palindrome.length() > 1 && !allPalindromes.contains(palindrome)) {
                allPalindromes.add(palindrome);
                addLog("发现回文: \"" + palindrome + "\" (位置: " + left + "-" + right + ")");
            }
            
            left--;
            right++;
            
            try {
                Thread.sleep(100);
                SwingUtilities.invokeLater(() -> visualPanel.repaint());
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        return right - left - 1;
    }
    
    private void loadDemoData() {
        inputField.setText("babad");
        addLog("加载演示数据: babad");
    }
    
    private void clearVisualization() {
        inputString = null;
        longestPalindrome = null;
        allPalindromes.clear();
        currentCenter = -1;
        currentLeft = -1;
        currentRight = -1;
        logArea.setText("");
        visualPanel.repaint();
        addLog("可视化已清空");
    }
    
    private void drawVisualization(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (inputString == null) {
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.setColor(Color.GRAY);
            g2d.drawString("请输入字符串开始演示", 50, 200);
            return;
        }
        
        int startX = 50;
        int startY = 100;
        int charWidth = 40;
        int charHeight = 40;
        
        // 绘制字符串
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        for (int i = 0; i < inputString.length(); i++) {
            int x = startX + i * charWidth;
            
            // 设置颜色
            if (i == currentCenter) {
                g2d.setColor(Color.RED); // 当前中心
            } else if (i >= currentLeft && i <= currentRight && currentLeft != -1) {
                g2d.setColor(Color.GREEN); // 当前扩展范围
            } else {
                g2d.setColor(Color.LIGHT_GRAY);
            }
            
            // 绘制字符背景
            g2d.fillRect(x, startY, charWidth, charHeight);
            
            // 绘制边框
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, startY, charWidth, charHeight);
            
            // 绘制字符
            g2d.drawString(String.valueOf(inputString.charAt(i)), 
                          x + charWidth/2 - 8, startY + charHeight/2 + 8);
            
            // 绘制索引
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.drawString(String.valueOf(i), x + charWidth/2 - 5, startY - 10);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
        }
        
        // 绘制结果信息
        if (longestPalindrome != null) {
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.setColor(Color.BLUE);
            g2d.drawString("最长回文子串: " + longestPalindrome, startX, startY + 80);
            g2d.drawString("长度: " + longestPalindrome.length(), startX, startY + 100);
        }
        
        // 绘制所有找到的回文
        if (!allPalindromes.isEmpty()) {
            g2d.setFont(new Font("Arial", Font.PLAIN, 14));
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawString("发现的回文子串:", startX, startY + 140);
            
            int y = startY + 160;
            for (int i = 0; i < Math.min(allPalindromes.size(), 8); i++) {
                g2d.drawString("• " + allPalindromes.get(i), startX + 20, y);
                y += 20;
            }
            
            if (allPalindromes.size() > 8) {
                g2d.drawString("... 还有 " + (allPalindromes.size() - 8) + " 个", startX + 20, y);
            }
        }
    }
    
    private void addLog(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO005_N_LongestPalindromicSubstring_Animation().setVisible(true);
        });
    }
}