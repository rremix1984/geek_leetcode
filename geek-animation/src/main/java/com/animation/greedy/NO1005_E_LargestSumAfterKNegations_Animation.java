package com.animation.greedy;

import javax.swing.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.event.ActionEvent;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.event.ActionListener;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.util.Arrays;
import com.animation.launcher.AlgorithmTreeLauncher;

/**
 * NO.1005 K次取反后最大化数组和 - 动画演示
 * 
 * 算法思路：
 * 1. 优先对负数进行取反操作
 * 2. 如果还有剩余次数，对绝对值最小的数进行取反
 * 3. 贪心策略：每次选择能使和增加最多的操作
 */
public class NO1005_E_LargestSumAfterKNegations_Animation extends JFrame {
    
    private static final int ANIMATION_DELAY = 1500;
    private static final Color NEGATIVE_COLOR = new Color(255, 99, 71);
    private static final Color POSITIVE_COLOR = new Color(50, 205, 50);
    private static final Color CURRENT_COLOR = new Color(255, 215, 0);
    private static final Color BACKGROUND_COLOR = new Color(240, 248, 255);
    
    // 动画组件
    private JPanel animationPanel;
    private JPanel controlPanel;
    private JPanel inputPanel;
    private JTextArea logArea;
    
    // 控制按钮
    private JButton startButton;
    private JButton stepButton;
    private JButton resetButton;
    private JButton returnButton;
    
    // 输入组件
    private JTextField arrayField;
    private JTextField kField;
    
    // 算法状态
    private int[] originalArray;
    private int[] currentArray;
    private int k;
    private int remainingK;
    private int currentStep;
    private int currentIndex;
    private boolean isPlaying;
    private Timer animationTimer;
    private int currentSum;
    private boolean isCompleted;
    
    public NO1005_E_LargestSumAfterKNegations_Animation() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        resetAnimation();
        
        setTitle("NO.1005 K次取反后最大化数组和 - 动画演示");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
    }
    
    private void initializeComponents() {
        // 动画面板
        animationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawAnimation(g);
            }
        };
        animationPanel.setBackground(Color.WHITE);
        animationPanel.setPreferredSize(new Dimension(800, 400));
        
        // 控制面板
        controlPanel = new JPanel(new FlowLayout());
        startButton = new JButton("开始演示");
        stepButton = new JButton("单步执行");
        resetButton = new JButton("重置");
        returnButton = new JButton("返回首页");
        
        controlPanel.add(startButton);
        controlPanel.add(stepButton);
        controlPanel.add(resetButton);
        controlPanel.add(returnButton);
        
        // 输入面板
        inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("参数设置"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("数组:"), gbc);
        gbc.gridx = 1;
        arrayField = new JTextField("4,2,3", 15);
        inputPanel.add(arrayField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("K值:"), gbc);
        gbc.gridx = 1;
        kField = new JTextField("1", 10);
        inputPanel.add(kField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JLabel hintLabel = new JLabel("输入格式: 数组用逗号分隔，如: -4,-2,-3");
        hintLabel.setFont(new Font("微软雅黑", Font.PLAIN, 10));
        hintLabel.setForeground(Color.GRAY);
        inputPanel.add(hintLabel, gbc);
        
        // 日志区域
        logArea = new JTextArea(8, 30);
        logArea.setEditable(false);
        logArea.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        logArea.setBackground(new Color(248, 248, 248));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 顶部：输入面板
        add(inputPanel, BorderLayout.NORTH);
        
        // 中央：动画面板
        add(animationPanel, BorderLayout.CENTER);
        
        // 底部：控制面板和日志
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(controlPanel, BorderLayout.NORTH);
        bottomPanel.add(new JScrollPane(logArea), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        startButton.addActionListener(e -> startAnimation());
        stepButton.addActionListener(e -> stepForward());
        resetButton.addActionListener(e -> resetAnimation());
        returnButton.addActionListener(e -> returnToHome());
        
        animationTimer = new Timer(ANIMATION_DELAY, e -> stepForward());
    }
    
    private void startAnimation() {
        if (!isPlaying) {
            parseInput();
            isPlaying = true;
            startButton.setText("暂停演示");
            animationTimer.start();
        } else {
            isPlaying = false;
            startButton.setText("继续演示");
            animationTimer.stop();
        }
    }
    
    private void stepForward() {
        if (!isCompleted && remainingK > 0) {
            performNextOperation();
            currentStep++;
        } else {
            // 演示结束
            animationTimer.stop();
            isPlaying = false;
            startButton.setText("开始演示");
            isCompleted = true;
            currentIndex = -1;
            logArea.append("\\n演示完成！\\n");
            logArea.append("最终数组和: " + currentSum + "\\n");
        }
        
        animationPanel.repaint();
    }
    
    private void resetAnimation() {
        animationTimer.stop();
        isPlaying = false;
        currentStep = 0;
        currentIndex = -1;
        isCompleted = false;
        startButton.setText("开始演示");
        
        logArea.setText("K次取反后最大化数组和算法演示\\n");
        logArea.append("=================================\\n");
        logArea.append("算法说明：\\n");
        logArea.append("1. 优先对负数进行取反操作\\n");
        logArea.append("2. 如果还有剩余次数，对绝对值最小的数取反\\n");
        logArea.append("3. 贪心策略：每次选择能使和增加最多的操作\\n\\n");
        
        animationPanel.repaint();
    }
    
    private void parseInput() {
        try {
            String[] arrayStr = arrayField.getText().trim().split(",");
            originalArray = new int[arrayStr.length];
            currentArray = new int[arrayStr.length];
            
            for (int i = 0; i < arrayStr.length; i++) {
                originalArray[i] = Integer.parseInt(arrayStr[i].trim());
                currentArray[i] = originalArray[i];
            }
            
            k = Integer.parseInt(kField.getText().trim());
            remainingK = k;
            currentSum = Arrays.stream(currentArray).sum();
            
            logArea.append("输入解析完成：\\n");
            logArea.append("原始数组: " + Arrays.toString(originalArray) + "\\n");
            logArea.append("K = " + k + "\\n");
            logArea.append("初始数组和: " + currentSum + "\\n\\n");
            
            // 对数组进行排序以便优先处理负数
            Arrays.sort(currentArray);
            logArea.append("排序后数组: " + Arrays.toString(currentArray) + "\\n");
            logArea.append("开始贪心操作...\\n\\n");
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "输入格式错误，请检查输入！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void performNextOperation() {
        // 找到最优的取反位置
        int bestIndex = findBestFlipIndex();
        currentIndex = bestIndex;
        
        int oldValue = currentArray[bestIndex];
        currentArray[bestIndex] = -currentArray[bestIndex];
        int newValue = currentArray[bestIndex];
        
        currentSum = currentSum - oldValue + newValue;
        remainingK--;
        
        logArea.append("步骤 " + (currentStep + 1) + ":\\n");
        logArea.append("选择位置 " + bestIndex + ": " + oldValue + " → " + newValue + "\\n");
        logArea.append("数组和变化: " + (currentSum + oldValue - newValue) + " → " + currentSum + "\\n");
        logArea.append("剩余操作次数: " + remainingK + "\\n");
        logArea.append("当前数组: " + Arrays.toString(currentArray) + "\\n\\n");
    }
    
    private int findBestFlipIndex() {
        // 如果还有负数，优先翻转最小的负数
        for (int i = 0; i < currentArray.length; i++) {
            if (currentArray[i] < 0) {
                return i;
            }
        }
        
        // 如果没有负数，翻转绝对值最小的正数
        int minIndex = 0;
        for (int i = 1; i < currentArray.length; i++) {
            if (Math.abs(currentArray[i]) < Math.abs(currentArray[minIndex])) {
                minIndex = i;
            }
        }
        return minIndex;
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = animationPanel.getWidth();
        int height = animationPanel.getHeight();
        
        // 绘制标题
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 20));
        g2d.setColor(Color.BLACK);
        String title = "K次取反后最大化数组和 (贪心算法)";
        FontMetrics fm = g2d.getFontMetrics();
        int titleX = (width - fm.stringWidth(title)) / 2;
        g2d.drawString(title, titleX, 30);
        
        if (currentArray == null) return;
        
        // 绘制状态信息
        drawStatusInfo(g2d, width, height);
        
        // 绘制数组
        drawArray(g2d, width, height);
        
        // 绘制操作说明
        drawOperationInfo(g2d, width, height);
    }
    
    private void drawStatusInfo(Graphics2D g2d, int width, int height) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(Color.BLACK);
        
        int y = 60;
        g2d.drawString("剩余操作次数: " + remainingK, 50, y);
        g2d.drawString("当前数组和: " + currentSum, 250, y);
        
        if (isCompleted) {
            g2d.setColor(new Color(0, 128, 0));
            g2d.drawString("✓ 操作完成", 450, y);
        } else if (currentIndex >= 0) {
            g2d.setColor(CURRENT_COLOR);
            g2d.drawString("→ 正在操作位置 " + currentIndex, 450, y);
        }
    }
    
    private void drawArray(Graphics2D g2d, int width, int height) {
        if (currentArray.length == 0) return;
        
        int startY = 120;
        int cellWidth = 60;
        int cellHeight = 50;
        int spacing = 10;
        int totalWidth = currentArray.length * cellWidth + (currentArray.length - 1) * spacing;
        int startX = (width - totalWidth) / 2;
        
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        
        for (int i = 0; i < currentArray.length; i++) {
            int x = startX + i * (cellWidth + spacing);
            int value = currentArray[i];
            
            // 选择颜色
            Color cellColor;
            if (i == currentIndex) {
                cellColor = CURRENT_COLOR;
            } else if (value < 0) {
                cellColor = NEGATIVE_COLOR;
            } else {
                cellColor = POSITIVE_COLOR;
            }
            
            // 绘制单元格
            g2d.setColor(cellColor);
            g2d.fillRoundRect(x, startY, cellWidth, cellHeight, 10, 10);
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(x, startY, cellWidth, cellHeight, 10, 10);
            
            // 绘制索引
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
            String indexStr = String.valueOf(i);
            FontMetrics fm = g2d.getFontMetrics();
            int indexX = x + (cellWidth - fm.stringWidth(indexStr)) / 2;
            g2d.drawString(indexStr, indexX, startY - 5);
            
            // 绘制值
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g2d.setColor(Color.WHITE);
            String valueStr = String.valueOf(value);
            fm = g2d.getFontMetrics();
            int valueX = x + (cellWidth - fm.stringWidth(valueStr)) / 2;
            int valueY = startY + (cellHeight + fm.getAscent()) / 2;
            g2d.drawString(valueStr, valueX, valueY);
        }
        
        // 绘制原始数组对比
        if (originalArray != null) {
            drawOriginalArray(g2d, startX, startY + cellHeight + 40, cellWidth, cellHeight, spacing);
        }
    }
    
    private void drawOriginalArray(Graphics2D g2d, int startX, int startY, int cellWidth, int cellHeight, int spacing) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("原始数组:", startX, startY - 10);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        
        for (int i = 0; i < originalArray.length; i++) {
            int x = startX + i * (cellWidth + spacing);
            int value = originalArray[i];
            
            // 绘制单元格
            g2d.setColor(BACKGROUND_COLOR);
            g2d.fillRoundRect(x, startY, cellWidth, cellHeight, 8, 8);
            g2d.setColor(Color.GRAY);
            g2d.drawRoundRect(x, startY, cellWidth, cellHeight, 8, 8);
            
            // 绘制值
            g2d.setColor(Color.BLACK);
            String valueStr = String.valueOf(value);
            FontMetrics fm = g2d.getFontMetrics();
            int valueX = x + (cellWidth - fm.stringWidth(valueStr)) / 2;
            int valueY = startY + (cellHeight + fm.getAscent()) / 2;
            g2d.drawString(valueStr, valueX, valueY);
        }
    }
    
    private void drawOperationInfo(Graphics2D g2d, int width, int height) {
        int startY = height - 120;
        
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("贪心策略:", 50, startY);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        g2d.drawString("1. 优先对负数取反（从最小的负数开始）", 70, startY + 20);
        g2d.drawString("2. 如果没有负数，对绝对值最小的数取反", 70, startY + 35);
        g2d.drawString("3. 每次操作都能最大化数组和的增长", 70, startY + 50);
        
        // 绘制图例
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        g2d.setColor(Color.BLACK);
        g2d.drawString("图例:", 400, startY);
        
        // 负数
        g2d.setColor(NEGATIVE_COLOR);
        g2d.fillRect(450, startY - 15, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("负数", 470, startY - 5);
        
        // 正数
        g2d.setColor(POSITIVE_COLOR);
        g2d.fillRect(450, startY + 5, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("正数", 470, startY + 15);
        
        // 当前操作
        g2d.setColor(CURRENT_COLOR);
        g2d.fillRect(450, startY + 25, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("当前操作", 470, startY + 35);
    }
    
    private void returnToHome() {
        // 关闭当前窗口
        this.dispose();
        
        // 重新启动主界面
        SwingUtilities.invokeLater(() -> {
            try {
                dispose(); // 关闭当前动画窗口
                com.animation.launcher.AlgorithmTreeLauncher.showMainWindow();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO1005_E_LargestSumAfterKNegations_Animation().setVisible(true);
        });
    }
}