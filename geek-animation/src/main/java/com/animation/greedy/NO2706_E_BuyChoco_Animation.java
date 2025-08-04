package com.animation.greedy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * NO.2706 购买两块巧克力 - 动画演示
 * 
 * 算法思路：
 * 1. 对价格数组进行排序
 * 2. 选择最便宜的两块巧克力
 * 3. 计算剩余金额，如果不够则返回原金额
 * 
 * 时间复杂度：O(n*log(n))，主要是排序的复杂度
 * 空间复杂度：O(1)，只使用常数额外空间
 */
public class NO2706_E_BuyChoco_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 700;
    private static final Color BACKGROUND_COLOR = new Color(240, 248, 255);
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private static final Color HIGHLIGHT_COLOR = new Color(255, 69, 0);
    private static final Color SUCCESS_COLOR = new Color(34, 139, 34);
    private static final Color CHOCOLATE_COLOR = new Color(139, 69, 19);
    
    private JPanel animationPanel;
    private JButton startButton, resetButton, nextStepButton;
    private JTextField pricesField, moneyField;
    private Timer animationTimer;
    
    // 算法状态变量
    private int[] originalPrices;
    private int[] sortedPrices;
    private int money;
    private int currentStep; // 0: 初始, 1: 排序中, 2: 选择第一个, 3: 选择第二个, 4: 计算结果
    private int sortingIndex;
    private int selectedFirst, selectedSecond;
    private int result;
    private boolean isAnimating;
    private boolean canBuy;
    
    public NO2706_E_BuyChoco_Animation() {
        initializeComponents();
        setupLayout();
        setupEventListeners();
        resetAnimation();
    }
    
    private void initializeComponents() {
        setTitle("NO.2706 购买两块巧克力 - 动画演示");
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
        pricesField = new JTextField("1,2,2", 15);
        moneyField = new JTextField("3", 8);
        inputPanel.add(new JLabel("巧克力价格 (逗号分隔):"));
        inputPanel.add(pricesField);
        inputPanel.add(new JLabel("拥有金额:"));
        inputPanel.add(moneyField);
        
        // 控制按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(BACKGROUND_COLOR);
        startButton = new JButton("开始动画");
        nextStepButton = new JButton("下一步");
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
        buttonPanel.add(nextStepButton);
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
        nextStepButton.addActionListener(e -> nextStep());
        resetButton.addActionListener(e -> resetAnimation());
        
        // 动画定时器
        animationTimer = new Timer(1500, e -> {
            if (isAnimating) {
                nextStep();
            }
        });
    }
    
    private void startAnimation() {
        try {
            parseInput();
            isAnimating = true;
            startButton.setEnabled(false);
            nextStepButton.setEnabled(true);
            animationTimer.start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "输入格式错误！请输入正确的价格和金额。", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void parseInput() {
        String pricesInput = pricesField.getText().trim();
        String[] parts = pricesInput.split(",");
        originalPrices = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            originalPrices[i] = Integer.parseInt(parts[i].trim());
        }
        
        money = Integer.parseInt(moneyField.getText().trim());
        resetAnimation();
    }
    
    private void nextStep() {
        switch (currentStep) {
            case 0: // 开始排序
                sortedPrices = originalPrices.clone();
                Arrays.sort(sortedPrices);
                currentStep = 1;
                sortingIndex = 0;
                break;
                
            case 1: // 排序动画（简化显示）
                sortingIndex++;
                if (sortingIndex >= sortedPrices.length) {
                    currentStep = 2;
                }
                break;
                
            case 2: // 选择第一个最便宜的
                selectedFirst = 0;
                currentStep = 3;
                break;
                
            case 3: // 选择第二个最便宜的
                selectedSecond = 1;
                currentStep = 4;
                break;
                
            case 4: // 计算结果
                int totalCost = sortedPrices[0] + sortedPrices[1];
                canBuy = totalCost <= money;
                result = canBuy ? money - totalCost : money;
                
                // 动画完成
                isAnimating = false;
                animationTimer.stop();
                startButton.setEnabled(true);
                nextStepButton.setEnabled(false);
                break;
        }
        
        animationPanel.repaint();
    }
    
    private void resetAnimation() {
        currentStep = 0;
        sortingIndex = 0;
        selectedFirst = -1;
        selectedSecond = -1;
        result = 0;
        isAnimating = false;
        canBuy = false;
        sortedPrices = null;
        
        if (animationTimer != null) {
            animationTimer.stop();
        }
        
        startButton.setEnabled(true);
        nextStepButton.setEnabled(false);
        animationPanel.repaint();
    }
    
    private void drawAnimation(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        if (originalPrices == null) return;
        
        // 绘制标题
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 24));
        g2d.setColor(PRIMARY_COLOR);
        String title = "购买两块巧克力贪心算法演示";
        FontMetrics fm = g2d.getFontMetrics();
        int titleX = (WINDOW_WIDTH - fm.stringWidth(title)) / 2;
        g2d.drawString(title, titleX, 40);
        
        // 绘制算法说明
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawString("算法步骤：排序价格 → 选择最便宜的两个 → 计算剩余金额", 50, 70);
        
        // 绘制金额信息
        drawMoneyInfo(g2d);
        
        // 绘制原始价格数组
        drawOriginalPrices(g2d);
        
        // 绘制排序后的价格数组
        if (currentStep >= 1) {
            drawSortedPrices(g2d);
        }
        
        // 绘制选择过程
        if (currentStep >= 2) {
            drawSelection(g2d);
        }
        
        // 绘制结果
        if (currentStep >= 4) {
            drawResult(g2d);
        }
        
        // 绘制算法复杂度信息
        drawComplexityInfo(g2d);
    }
    
    private void drawMoneyInfo(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 18));
        g2d.setColor(SUCCESS_COLOR);
        g2d.drawString("拥有金额: $" + money, 50, 110);
    }
    
    private void drawOriginalPrices(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("原始价格:", 50, 150);
        
        drawPriceArray(g2d, originalPrices, 50, 170, CHOCOLATE_COLOR, -1, -1);
    }
    
    private void drawSortedPrices(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("排序后价格:", 50, 250);
        
        int highlight1 = (currentStep >= 2) ? selectedFirst : -1;
        int highlight2 = (currentStep >= 3) ? selectedSecond : -1;
        
        drawPriceArray(g2d, sortedPrices, 50, 270, new Color(205, 133, 63), highlight1, highlight2);
        
        // 显示排序进度
        if (currentStep == 1) {
            g2d.setFont(new Font("微软雅黑", Font.ITALIC, 14));
            g2d.setColor(HIGHLIGHT_COLOR);
            g2d.drawString("正在排序...", 50, 320);
        }
    }
    
    private void drawPriceArray(Graphics2D g2d, int[] prices, int startX, int startY, Color baseColor, int highlight1, int highlight2) {
        int cellWidth = 60;
        int cellHeight = 40;
        
        for (int i = 0; i < prices.length; i++) {
            int x = startX + i * (cellWidth + 10);
            
            // 设置颜色
            Color cellColor = baseColor;
            if (i == highlight1 || i == highlight2) {
                cellColor = HIGHLIGHT_COLOR;
            }
            
            // 绘制巧克力形状（圆角矩形）
            g2d.setColor(cellColor);
            g2d.fillRoundRect(x, startY, cellWidth, cellHeight, 12, 12);
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(x, startY, cellWidth, cellHeight, 12, 12);
            
            // 绘制价格
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g2d.setColor(Color.WHITE);
            String text = "$" + prices[i];
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (cellWidth - fm.stringWidth(text)) / 2;
            int textY = startY + (cellHeight + fm.getAscent()) / 2;
            g2d.drawString(text, textX, textY);
            
            // 绘制索引
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
            g2d.setColor(Color.GRAY);
            String indexText = "[" + i + "]";
            int indexX = x + (cellWidth - g2d.getFontMetrics().stringWidth(indexText)) / 2;
            g2d.drawString(indexText, indexX, startY + cellHeight + 15);
        }
    }
    
    private void drawSelection(Graphics2D g2d) {
        int startY = 350;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("选择过程:", 50, startY);
        
        if (currentStep >= 2 && sortedPrices != null) {
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            g2d.setColor(Color.BLACK);
            
            // 第一个选择
            g2d.drawString("1. 选择最便宜的: $" + sortedPrices[0], 70, startY + 25);
            
            if (currentStep >= 3) {
                // 第二个选择
                g2d.drawString("2. 选择第二便宜的: $" + sortedPrices[1], 70, startY + 45);
                
                // 总成本
                int totalCost = sortedPrices[0] + sortedPrices[1];
                g2d.setColor(HIGHLIGHT_COLOR);
                g2d.drawString("总成本: $" + totalCost, 70, startY + 70);
            }
        }
    }
    
    private void drawResult(Graphics2D g2d) {
        int startY = 450;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 18));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("计算结果:", 50, startY);
        
        int totalCost = sortedPrices[0] + sortedPrices[1];
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        g2d.setColor(Color.BLACK);
        g2d.drawString("总成本: $" + totalCost, 70, startY + 30);
        g2d.drawString("拥有金额: $" + money, 70, startY + 50);
        
        if (canBuy) {
            g2d.setColor(SUCCESS_COLOR);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g2d.drawString("✓ 可以购买！剩余金额: $" + result, 70, startY + 80);
        } else {
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g2d.drawString("✗ 金额不足！返回原金额: $" + result, 70, startY + 80);
        }
    }
    
    private void drawComplexityInfo(Graphics2D g2d) {
        int startY = 570;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("算法复杂度分析:", 50, startY);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("• 时间复杂度: O(n×log(n))，主要是排序的复杂度", 70, startY + 25);
        g2d.drawString("• 空间复杂度: O(1)，只使用常数额外空间", 70, startY + 45);
        g2d.drawString("• 贪心策略: 选择最便宜的两个巧克力以最大化剩余金额", 70, startY + 65);
        
        // 显示当前步骤
        g2d.setColor(HIGHLIGHT_COLOR);
        String stepText = "";
        switch (currentStep) {
            case 0: stepText = "准备开始"; break;
            case 1: stepText = "正在排序价格数组"; break;
            case 2: stepText = "选择最便宜的巧克力"; break;
            case 3: stepText = "选择第二便宜的巧克力"; break;
            case 4: stepText = "计算完成"; break;
        }
        g2d.drawString("当前步骤: " + stepText, 70, startY + 90);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO2706_E_BuyChoco_Animation().setVisible(true);
        });
    }
}