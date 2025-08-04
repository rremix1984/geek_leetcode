package com.animation.heap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.PriorityQueue;
import java.util.Collections;

/**
 * NO.2558 从数量最多的堆取走礼物 - 动画演示
 * 
 * 算法思路：
 * 1. 使用最大堆存储所有礼物堆的数量
 * 2. 重复k次：取出最大值，计算其平方根的向下取整，再放回堆中
 * 3. 最后计算堆中所有元素的和
 * 
 * 时间复杂度：O(k*log(n))，其中n是礼物堆的数量
 * 空间复杂度：O(n)，用于存储堆
 */
public class NO2558_E_PickGifts_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final Color BACKGROUND_COLOR = new Color(240, 248, 255);
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private static final Color HIGHLIGHT_COLOR = new Color(255, 69, 0);
    private static final Color SUCCESS_COLOR = new Color(34, 139, 34);
    private static final Color HEAP_COLOR = new Color(255, 182, 193);
    private static final Color GIFT_COLOR = new Color(255, 215, 0);
    
    private JPanel animationPanel;
    private JButton startButton, resetButton, nextStepButton;
    private JTextField giftsField, kField;
    private Timer animationTimer;
    
    // 算法状态变量
    private int[] originalGifts;
    private PriorityQueue<Integer> maxHeap;
    private int k;
    private int currentStep;
    private int currentMax;
    private int newValue;
    private boolean isAnimating;
    private long totalGifts;
    
    public NO2558_E_PickGifts_Animation() {
        initializeComponents();
        setupLayout();
        setupEventListeners();
        resetAnimation();
    }
    
    private void initializeComponents() {
        setTitle("NO.2558 从数量最多的堆取走礼物 - 动画演示");
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
        giftsField = new JTextField("25,64,9,4,100", 20);
        kField = new JTextField("4", 8);
        inputPanel.add(new JLabel("礼物堆数量 (逗号分隔):"));
        inputPanel.add(giftsField);
        inputPanel.add(new JLabel("操作次数 k:"));
        inputPanel.add(kField);
        
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
        animationTimer = new Timer(2000, e -> {
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
            JOptionPane.showMessageDialog(this, "输入格式错误！请输入正确的礼物数量和操作次数。", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void parseInput() {
        String giftsInput = giftsField.getText().trim();
        String[] parts = giftsInput.split(",");
        originalGifts = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            originalGifts[i] = Integer.parseInt(parts[i].trim());
        }
        
        k = Integer.parseInt(kField.getText().trim());
        resetAnimation();
    }
    
    private void nextStep() {
        if (currentStep >= k) {
            // 计算最终结果
            totalGifts = 0;
            for (int gift : maxHeap) {
                totalGifts += gift;
            }
            
            // 动画完成
            isAnimating = false;
            animationTimer.stop();
            startButton.setEnabled(true);
            nextStepButton.setEnabled(false);
            return;
        }
        
        // 执行一次操作
        if (!maxHeap.isEmpty()) {
            currentMax = maxHeap.poll(); // 取出最大值
            newValue = (int) Math.sqrt(currentMax); // 计算平方根的向下取整
            maxHeap.offer(newValue); // 放回堆中
            currentStep++;
        }
        
        animationPanel.repaint();
    }
    
    private void resetAnimation() {
        currentStep = 0;
        currentMax = 0;
        newValue = 0;
        isAnimating = false;
        totalGifts = 0;
        
        // 初始化最大堆
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        if (originalGifts != null) {
            for (int gift : originalGifts) {
                maxHeap.offer(gift);
            }
        }
        
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
        
        if (originalGifts == null) return;
        
        // 绘制标题
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 24));
        g2d.setColor(PRIMARY_COLOR);
        String title = "从数量最多的堆取走礼物算法演示";
        FontMetrics fm = g2d.getFontMetrics();
        int titleX = (WINDOW_WIDTH - fm.stringWidth(title)) / 2;
        g2d.drawString(title, titleX, 40);
        
        // 绘制算法说明
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawString("算法步骤：构建最大堆 → 取出最大值 → 计算平方根 → 放回堆中 → 重复k次", 50, 70);
        
        // 绘制操作信息
        drawOperationInfo(g2d);
        
        // 绘制原始数组
        drawOriginalArray(g2d);
        
        // 绘制最大堆
        drawMaxHeap(g2d);
        
        // 绘制当前操作
        drawCurrentOperation(g2d);
        
        // 绘制结果
        if (currentStep >= k) {
            drawResult(g2d);
        }
        
        // 绘制算法复杂度信息
        drawComplexityInfo(g2d);
    }
    
    private void drawOperationInfo(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("操作次数: " + k + " | 已完成: " + currentStep, 50, 110);
    }
    
    private void drawOriginalArray(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("原始礼物堆:", 50, 150);
        
        int startX = 50;
        int startY = 170;
        int cellWidth = 60;
        int cellHeight = 40;
        
        for (int i = 0; i < originalGifts.length; i++) {
            int x = startX + i * (cellWidth + 10);
            
            // 绘制礼物堆
            g2d.setColor(GIFT_COLOR);
            g2d.fillRoundRect(x, startY, cellWidth, cellHeight, 8, 8);
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(x, startY, cellWidth, cellHeight, 8, 8);
            
            // 绘制数量
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            String text = String.valueOf(originalGifts[i]);
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (cellWidth - fm.stringWidth(text)) / 2;
            int textY = startY + (cellHeight + fm.getAscent()) / 2;
            g2d.drawString(text, textX, textY);
        }
    }
    
    private void drawMaxHeap(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("最大堆状态:", 50, 270);
        
        if (maxHeap.isEmpty()) {
            g2d.setFont(new Font("微软雅黑", Font.ITALIC, 14));
            g2d.setColor(Color.GRAY);
            g2d.drawString("堆为空", 50, 300);
            return;
        }
        
        // 将堆转换为数组以便显示
        Integer[] heapArray = maxHeap.toArray(new Integer[0]);
        
        int startX = 50;
        int startY = 290;
        int cellWidth = 50;
        int cellHeight = 40;
        
        for (int i = 0; i < heapArray.length; i++) {
            int x = startX + i * (cellWidth + 10);
            
            // 设置颜色
            Color cellColor = HEAP_COLOR;
            if (i == 0) { // 堆顶元素（最大值）
                cellColor = HIGHLIGHT_COLOR;
            }
            
            // 绘制堆元素
            g2d.setColor(cellColor);
            g2d.fillRoundRect(x, startY, cellWidth, cellHeight, 8, 8);
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(x, startY, cellWidth, cellHeight, 8, 8);
            
            // 绘制数值
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            String text = String.valueOf(heapArray[i]);
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (cellWidth - fm.stringWidth(text)) / 2;
            int textY = startY + (cellHeight + fm.getAscent()) / 2;
            g2d.drawString(text, textX, textY);
            
            // 标记堆顶
            if (i == 0) {
                g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                g2d.setColor(Color.RED);
                g2d.drawString("MAX", x + 5, startY - 5);
            }
        }
    }
    
    private void drawCurrentOperation(Graphics2D g2d) {
        if (!isAnimating || currentStep == 0) return;
        
        int startY = 380;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("当前操作 (第 " + currentStep + " 次):", 50, startY);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        g2d.setColor(Color.BLACK);
        
        // 显示操作步骤
        g2d.drawString("1. 取出最大值: " + currentMax, 70, startY + 30);
        g2d.drawString("2. 计算平方根: √" + currentMax + " = " + newValue, 70, startY + 50);
        g2d.drawString("3. 放回堆中: " + newValue, 70, startY + 70);
        
        // 绘制计算过程的可视化
        drawCalculationVisualization(g2d, startY + 100);
    }
    
    private void drawCalculationVisualization(Graphics2D g2d, int startY) {
        if (currentMax == 0) return;
        
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g2d.setColor(HIGHLIGHT_COLOR);
        g2d.drawString("计算过程可视化:", 70, startY);
        
        // 绘制原值
        int boxWidth = 80;
        int boxHeight = 50;
        int x1 = 70;
        int y1 = startY + 20;
        
        g2d.setColor(new Color(255, 99, 71));
        g2d.fillRoundRect(x1, y1, boxWidth, boxHeight, 10, 10);
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(x1, y1, boxWidth, boxHeight, 10, 10);
        
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(Color.WHITE);
        String maxText = String.valueOf(currentMax);
        FontMetrics fm = g2d.getFontMetrics();
        int textX1 = x1 + (boxWidth - fm.stringWidth(maxText)) / 2;
        int textY1 = y1 + (boxHeight + fm.getAscent()) / 2;
        g2d.drawString(maxText, textX1, textY1);
        
        // 绘制箭头和平方根符号
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 20));
        g2d.drawString("→ √", x1 + boxWidth + 10, y1 + 35);
        
        // 绘制结果值
        int x2 = x1 + boxWidth + 80;
        g2d.setColor(SUCCESS_COLOR);
        g2d.fillRoundRect(x2, y1, boxWidth, boxHeight, 10, 10);
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(x2, y1, boxWidth, boxHeight, 10, 10);
        
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(Color.WHITE);
        String newText = String.valueOf(newValue);
        int textX2 = x2 + (boxWidth - fm.stringWidth(newText)) / 2;
        int textY2 = y1 + (boxHeight + fm.getAscent()) / 2;
        g2d.drawString(newText, textX2, textY2);
    }
    
    private void drawResult(Graphics2D g2d) {
        int startY = 550;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 18));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("最终结果:", 50, startY);
        
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 20));
        g2d.setColor(SUCCESS_COLOR);
        g2d.drawString("剩余礼物总数: " + totalGifts, 70, startY + 30);
        
        // 显示操作完成信息
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("已完成 " + k + " 次操作，每次都选择数量最多的堆", 70, startY + 55);
    }
    
    private void drawComplexityInfo(Graphics2D g2d) {
        int startY = 630;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("算法复杂度分析:", 50, startY);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("• 时间复杂度: O(k×log(n))，其中 n 是礼物堆数量", 70, startY + 25);
        g2d.drawString("• 空间复杂度: O(n)，用于存储最大堆", 70, startY + 45);
        g2d.drawString("• 数据结构: 最大堆（优先队列），保证每次都能快速找到最大值", 70, startY + 65);
        
        // 显示当前进度
        if (isAnimating) {
            g2d.setColor(HIGHLIGHT_COLOR);
            String progress = String.format("进度: %d/%d (%.1f%%)", currentStep, k, (double) currentStep / k * 100);
            g2d.drawString(progress, 70, startY + 90);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO2558_E_PickGifts_Animation().setVisible(true);
        });
    }
}