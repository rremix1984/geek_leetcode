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
    
    // 动画增强
    private javax.swing.Timer animationTimer;
    private int animationStep;
    private boolean isOddExpansion;
    private List<PalindromeAnimation> palindromeAnimations;
    private int maxPalindromeLength;
    
    public NO005_N_LongestPalindromicSubstring_Animation() {
        allPalindromes = new ArrayList<>();
        palindromeAnimations = new ArrayList<>();
        maxPalindromeLength = 0;
        initializeUI();
        initializeAnimation();
    }
    
    private void initializeAnimation() {
        animationTimer = new javax.swing.Timer(150, e -> {
            if (isAnimating) {
                SwingUtilities.invokeLater(() -> visualPanel.repaint());
            }
        });
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
        
        if (isAnimating) {
            addLog("动画正在进行中，请稍候...");
            return;
        }
        
        inputString = input;
        longestPalindrome = "";
        allPalindromes.clear();
        palindromeAnimations.clear();
        maxPalindromeLength = 0;
        currentCenter = -1;
        currentLeft = -1;
        currentRight = -1;
        isAnimating = true;
        animationStep = 0;
        
        addLog("🎯 开始查找字符串 \"" + input + "\" 的最长回文子串");
        addLog("📊 使用中心扩展算法进行动画演示");
        
        // 启动动画
        animationTimer.start();
        
        // 在后台线程执行算法
        new Thread(() -> {
            String result = expandAroundCenterAnimated(input);
            SwingUtilities.invokeLater(() -> {
                longestPalindrome = result;
                isAnimating = false;
                animationTimer.stop();
                addLog("✅ 查找完成！最长回文子串: \"" + result + "\"");
                addLog("📏 长度: " + result.length());
                SwingUtilities.invokeLater(() -> visualPanel.repaint());
            });
        }).start();
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
    
    private String expandAroundCenterAnimated(String s) {
        if (s == null || s.length() < 1) return "";
        
        int start = 0, end = 0;
        
        for (int i = 0; i < s.length(); i++) {
            final int currentI = i;
            SwingUtilities.invokeLater(() -> {
                currentCenter = currentI;
                animationStep++;
                addLog("🔍 检查中心位置: " + currentI + " ('" + s.charAt(currentI) + "')");
            });
            
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "";
            }
            
            // 奇数长度回文
            isOddExpansion = true;
            int len1 = expandAroundCenterHelperAnimated(s, i, i);
            
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "";
            }
            
            // 偶数长度回文
            isOddExpansion = false;
            int len2 = expandAroundCenterHelperAnimated(s, i, i + 1);
            
            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
                
                String newPalindrome = s.substring(start, end + 1);
                if (newPalindrome.length() > maxPalindromeLength) {
                    maxPalindromeLength = newPalindrome.length();
                    SwingUtilities.invokeLater(() -> {
                        addLog("🎉 发现更长回文: \"" + newPalindrome + "\" (长度: " + newPalindrome.length() + ")");
                    });
                }
            }
            
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "";
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
    
    private int expandAroundCenterHelperAnimated(String s, int left, int right) {
        final int initialLeft = left;
        final int initialRight = right;
        SwingUtilities.invokeLater(() -> {
            String expansionType = isOddExpansion ? "奇数" : "偶数";
            addLog("  📐 " + expansionType + "长度扩展: 起始位置 [" + initialLeft + ", " + initialRight + "]");
        });
        
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            final int finalLeft = left;
            final int finalRight = right;
            
            SwingUtilities.invokeLater(() -> {
                currentLeft = finalLeft;
                currentRight = finalRight;
                
                // 记录找到的回文
                String palindrome = s.substring(finalLeft, finalRight + 1);
                if (palindrome.length() > 1 && !allPalindromes.contains(palindrome)) {
                    allPalindromes.add(palindrome);
                    
                    // 创建动画对象
                    PalindromeAnimation animation = new PalindromeAnimation(finalLeft, finalRight, palindrome);
                    palindromeAnimations.add(animation);
                    
                    addLog("  ✅ 发现回文: \"" + palindrome + "\" (位置: " + finalLeft + "-" + finalRight + ", 长度: " + palindrome.length() + ")");
                }
                
                SwingUtilities.invokeLater(() -> visualPanel.repaint());
            });
            
            left--;
            right++;
            
            try {
                Thread.sleep(150);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                return 0;
            }
        }
        
        final int finalLeft = left;
        final int finalRight = right;
        SwingUtilities.invokeLater(() -> {
            addLog("  🔚 扩展结束: 最终长度 " + (finalRight - finalLeft - 1));
        });
        
        return right - left - 1;
    }
    
    private void loadDemoData() {
        inputField.setText("babad");
        addLog("加载演示数据: babad");
    }
    
    private void clearVisualization() {
        // 停止动画
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        
        inputString = null;
        longestPalindrome = null;
        allPalindromes.clear();
        palindromeAnimations.clear();
        currentCenter = -1;
        currentLeft = -1;
        currentRight = -1;
        isAnimating = false;
        animationStep = 0;
        maxPalindromeLength = 0;
        
        inputField.setText("");
        logArea.setText("");
        SwingUtilities.invokeLater(() -> visualPanel.repaint());
        
        addLog("✨ 已清除所有数据和动画状态");
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
        
        // 更新动画对象
        palindromeAnimations.removeIf(animation -> {
            animation.update();
            return animation.shouldRemove();
        });
        
        int startX = 50;
        int startY = 100;
        int charWidth = 45;
        int charHeight = 45;
        
        // 绘制动画状态信息
        if (isAnimating) {
            g2d.setColor(new Color(0, 150, 255));
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            String status = "🔄 动画进行中... 步骤: " + animationStep;
            g2d.drawString(status, startX, 30);
            
            String expansionType = isOddExpansion ? "奇数长度扩展" : "偶数长度扩展";
            g2d.setColor(new Color(255, 140, 0));
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            g2d.drawString("当前模式: " + expansionType, startX, 50);
        }
        
        // 绘制字符串背景动画效果
        for (PalindromeAnimation animation : palindromeAnimations) {
            if (animation.isHighlighted) {
                g2d.setColor(animation.getColor());
                int animStartX = startX + animation.getLeft() * charWidth;
                int animWidth = (animation.getRight() - animation.getLeft() + 1) * charWidth;
                
                // 绘制发光效果
                for (int i = 0; i < 3; i++) {
                    Color glowColor = new Color(animation.getColor().getRed(), 
                                              animation.getColor().getGreen(), 
                                              animation.getColor().getBlue(), 
                                              30 - i * 10);
                    g2d.setColor(glowColor);
                    g2d.fillRoundRect(animStartX - i * 2, startY - i * 2, 
                                    animWidth + i * 4, charHeight + i * 4, 8, 8);
                }
            }
        }
        
        // 绘制字符串
        g2d.setFont(new Font("Consolas", Font.BOLD, 22));
        for (int i = 0; i < inputString.length(); i++) {
            int x = startX + i * charWidth;
            int y = startY;
            
            // 设置背景颜色
            Color bgColor = Color.WHITE;
            if (i == currentCenter) {
                bgColor = new Color(255, 100, 100, 200); // 红色 - 当前中心
            } else if (i >= currentLeft && i <= currentRight && currentLeft >= 0) {
                bgColor = new Color(100, 255, 100, 180); // 绿色 - 当前扩展范围
            }
            
            // 绘制字符背景
            g2d.setColor(bgColor);
            g2d.fillRoundRect(x, y, charWidth, charHeight, 8, 8);
            
            // 绘制边框
            if (i == currentCenter) {
                g2d.setStroke(new BasicStroke(3));
                g2d.setColor(new Color(255, 0, 0));
            } else if (i >= currentLeft && i <= currentRight && currentLeft >= 0) {
                g2d.setStroke(new BasicStroke(2));
                g2d.setColor(new Color(0, 200, 0));
            } else {
                g2d.setStroke(new BasicStroke(1));
                g2d.setColor(Color.BLACK);
            }
            g2d.drawRoundRect(x, y, charWidth, charHeight, 8, 8);
            
            // 绘制字符
            g2d.setColor(Color.BLACK);
            FontMetrics fm = g2d.getFontMetrics();
            int charX = x + (charWidth - fm.charWidth(inputString.charAt(i))) / 2;
            int charY = y + (charHeight + fm.getAscent()) / 2;
            g2d.drawString(String.valueOf(inputString.charAt(i)), charX, charY);
            
            // 绘制索引
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            g2d.setColor(new Color(0, 100, 200));
            String index = String.valueOf(i);
            int indexX = x + (charWidth - g2d.getFontMetrics().stringWidth(index)) / 2;
            g2d.drawString(index, indexX, y - 8);
            g2d.setFont(new Font("Consolas", Font.BOLD, 22));
        }
        
        // 绘制当前扩展信息
        if (currentLeft >= 0 && currentRight >= 0 && currentLeft < inputString.length() && currentRight < inputString.length()) {
            g2d.setColor(new Color(0, 150, 0));
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            String currentPalindrome = inputString.substring(currentLeft, currentRight + 1);
            String info = "当前检查: \"" + currentPalindrome + "\" [" + currentLeft + ", " + currentRight + "]";
            g2d.drawString(info, startX, startY + charHeight + 25);
        }
        
        // 绘制最长回文结果
        if (longestPalindrome != null) {
            g2d.setColor(new Color(255, 215, 0));
            g2d.fillRoundRect(startX - 5, startY + 90, 700, 35, 10, 10);
            g2d.setColor(new Color(200, 0, 0));
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRoundRect(startX - 5, startY + 90, 700, 35, 10, 10);
            
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 18));
            String result = "🏆 最长回文子串: \"" + longestPalindrome + "\" (长度: " + longestPalindrome.length() + ")";
            g2d.drawString(result, startX, startY + 110);
        }
        
        // 绘制统计信息
        if (!allPalindromes.isEmpty()) {
            g2d.setColor(new Color(0, 100, 200));
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            String stats = "📊 已发现 " + allPalindromes.size() + " 个回文子串";
            g2d.drawString(stats, startX, startY + 150);
            
            // 绘制回文列表（限制显示数量）
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            int y = startY + 175;
            int count = 0;
            for (String palindrome : allPalindromes) {
                if (count >= 8) break; // 限制显示数量
                
                Color textColor = palindrome.length() == maxPalindromeLength ? 
                    new Color(255, 140, 0) : new Color(100, 100, 100);
                g2d.setColor(textColor);
                
                String prefix = palindrome.length() == maxPalindromeLength ? "🌟 " : "• ";
                g2d.drawString(prefix + palindrome + " (长度: " + palindrome.length() + ")", 
                              startX + 20, y);
                y += 22;
                count++;
            }
            
            if (allPalindromes.size() > 8) {
                g2d.setColor(Color.GRAY);
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
    
    // 回文动画类
    private class PalindromeAnimation {
        private int left, right;
        private String palindrome;
        private long creationTime;
        private boolean isHighlighted;
        private double pulsePhase;
        
        public PalindromeAnimation(int left, int right, String palindrome) {
            this.left = left;
            this.right = right;
            this.palindrome = palindrome;
            this.creationTime = System.currentTimeMillis();
            this.isHighlighted = true;
            this.pulsePhase = 0;
        }
        
        public void update() {
            pulsePhase += 0.2;
            if (pulsePhase > Math.PI * 2) {
                pulsePhase = 0;
            }
            
            // 3秒后停止高亮
            if (System.currentTimeMillis() - creationTime > 3000) {
                isHighlighted = false;
            }
        }
        
        public boolean shouldRemove() {
            return System.currentTimeMillis() - creationTime > 5000;
        }
        
        public Color getColor() {
            if (!isHighlighted) {
                return new Color(200, 200, 200, 100);
            }
            
            // 脉冲效果
            int alpha = (int)(100 + 100 * Math.sin(pulsePhase));
            if (palindrome.length() == maxPalindromeLength) {
                return new Color(255, 215, 0, alpha); // 金色 - 最长回文
            } else {
                return new Color(144, 238, 144, alpha); // 浅绿色 - 普通回文
            }
        }
        
        public int getLeft() { return left; }
        public int getRight() { return right; }
        public String getPalindrome() { return palindrome; }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO005_N_LongestPalindromicSubstring_Animation().setVisible(true);
        });
    }
}