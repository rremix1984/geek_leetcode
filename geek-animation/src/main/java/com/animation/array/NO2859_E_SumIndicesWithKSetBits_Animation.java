package com.animation.array;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * NO.2859 计算K位置下标对应元素的和 - 动画演示
 * 
 * 算法思路：
 * 1. 遍历数组的每个下标
 * 2. 计算每个下标的二进制表示中1的个数（置位数）
 * 3. 如果置位数等于k，则将对应元素加入结果
 * 4. 使用Brian Kernighan算法优化置位数计算：i & (i-1)可以消除最右边的1
 * 
 * 时间复杂度：O(n * log(max_index))，其中n是数组长度
 * 空间复杂度：O(1)
 */
public class NO2859_E_SumIndicesWithKSetBits_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1400;
    private static final int WINDOW_HEIGHT = 900;
    private static final Color BACKGROUND_COLOR = new Color(240, 248, 255);
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private static final Color HIGHLIGHT_COLOR = new Color(255, 69, 0);
    private static final Color SUCCESS_COLOR = new Color(34, 139, 34);
    private static final Color ARRAY_COLOR = new Color(135, 206, 250);
    private static final Color BINARY_COLOR = new Color(255, 182, 193);
    private static final Color MATCH_COLOR = new Color(144, 238, 144);
    
    private JPanel animationPanel;
    private JButton startButton, nextButton, resetButton;
    private JTextField arrayField, kField;
    private Timer animationTimer;
    
    // 算法状态变量
    private List<Integer> nums;
    private int k;
    private int currentIndex;
    private int currentSum;
    private boolean isAnimating;
    private int animationStep;
    private String currentBinary;
    private int currentSetBits;
    private boolean isCurrentMatch;
    private List<Integer> matchedIndices;
    
    public NO2859_E_SumIndicesWithKSetBits_Animation() {
        initializeComponents();
        setupLayout();
        setupEventListeners();
        resetAnimation();
    }
    
    private void initializeComponents() {
        setTitle("NO.2859 计算K位置下标对应元素的和 - 动画演示");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // 创建控制面板
        JPanel controlPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        controlPanel.setBackground(BACKGROUND_COLOR);
        
        // 输入面板
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(BACKGROUND_COLOR);
        arrayField = new JTextField("5,10,1,5,2", 15);
        kField = new JTextField("1", 5);
        inputPanel.add(new JLabel("数组(逗号分隔):"));
        inputPanel.add(arrayField);
        inputPanel.add(new JLabel("K值:"));
        inputPanel.add(kField);
        
        // 控制按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(BACKGROUND_COLOR);
        startButton = new JButton("开始动画");
        nextButton = new JButton("下一步");
        resetButton = new JButton("重置");
        
        buttonPanel.add(startButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(resetButton);
        
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

        // Add window listener to show main window on close
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    com.animation.launcher.AlgorithmTreeLauncher.showMainWindow();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // 动画定时器
        animationTimer = new Timer(1500, e -> nextStep());
    }
    
    private void startAnimation() {
        try {
            // 解析输入
            String[] arrayStr = arrayField.getText().trim().split(",");
            nums = new ArrayList<>();
            for (String s : arrayStr) {
                nums.add(Integer.parseInt(s.trim()));
            }
            k = Integer.parseInt(kField.getText().trim());
            
            if (nums.isEmpty() || k < 0) {
                JOptionPane.showMessageDialog(this, "请输入有效的数组和K值！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // 开始动画
            currentIndex = 0;
            currentSum = 0;
            isAnimating = true;
            animationStep = 0;
            matchedIndices = new ArrayList<>();
            
            setButtonsEnabled(false);
            animationTimer.start();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void nextStep() {
        if (!isAnimating || currentIndex >= nums.size()) {
            finishAnimation();
            return;
        }
        
        if (animationStep == 0) {
            // 步骤1：计算当前下标的二进制表示和置位数
            currentBinary = Integer.toBinaryString(currentIndex);
            currentSetBits = countSetBits(currentIndex);
            animationStep++;
        } else if (animationStep == 1) {
            // 步骤2：检查是否匹配
            isCurrentMatch = (currentSetBits == k);
            if (isCurrentMatch) {
                currentSum += nums.get(currentIndex);
                matchedIndices.add(currentIndex);
            }
            animationStep++;
        } else {
            // 步骤3：移动到下一个下标
            currentIndex++;
            animationStep = 0;
        }
        
        animationPanel.repaint();
    }
    
    private int countSetBits(int n) {
        int count = 0;
        while (n != 0) {
            n &= n - 1; // Brian Kernighan算法
            count++;
        }
        return count;
    }
    
    private void finishAnimation() {
        isAnimating = false;
        animationTimer.stop();
        setButtonsEnabled(true);
        
        JOptionPane.showMessageDialog(this, 
            "算法完成！\n" +
            "K = " + k + " 的下标对应元素和为: " + currentSum + "\n" +
            "匹配的下标: " + matchedIndices, 
            "结果", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void setButtonsEnabled(boolean enabled) {
        startButton.setEnabled(enabled);
        nextButton.setEnabled(!enabled && isAnimating);
    }
    
    private void resetAnimation() {
        nums = null;
        k = 0;
        currentIndex = 0;
        currentSum = 0;
        isAnimating = false;
        animationStep = 0;
        currentBinary = "";
        currentSetBits = 0;
        isCurrentMatch = false;
        matchedIndices = new ArrayList<>();
        
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
        String title = "计算K位置下标对应元素的和算法演示";
        FontMetrics fm = g2d.getFontMetrics();
        int titleX = (WINDOW_WIDTH - fm.stringWidth(title)) / 2;
        g2d.drawString(title, titleX, 40);
        
        // 绘制算法说明
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawString("算法原理：遍历数组下标，计算每个下标二进制表示中1的个数，如果等于K则累加对应元素", 50, 70);
        
        if (nums == null) {
            g2d.setFont(new Font("微软雅黑", Font.ITALIC, 16));
            g2d.setColor(Color.GRAY);
            g2d.drawString("请输入数组和K值，然后点击开始动画", 50, 150);
            return;
        }
        
        // 绘制数组
        drawArray(g2d);
        
        // 绘制当前处理的下标信息
        drawCurrentIndexInfo(g2d);
        
        // 绘制二进制表示
        drawBinaryRepresentation(g2d);
        
        // 绘制匹配的下标列表
        drawMatchedIndices(g2d);
        
        // 绘制算法状态
        drawAlgorithmStatus(g2d);
        
        // 绘制复杂度信息
        drawComplexityInfo(g2d);
    }
    
    private void drawArray(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("数组:", 50, 120);
        
        int cellWidth = 60;
        int cellHeight = 40;
        int startX = 50;
        int startY = 130;
        
        for (int i = 0; i < nums.size(); i++) {
            int x = startX + i * (cellWidth + 10);
            
            // 设置颜色
            Color cellColor = ARRAY_COLOR;
            if (i == currentIndex && isAnimating) {
                cellColor = HIGHLIGHT_COLOR;
            } else if (matchedIndices.contains(i)) {
                cellColor = MATCH_COLOR;
            }
            
            // 绘制数组元素
            g2d.setColor(cellColor);
            g2d.fillRoundRect(x, startY, cellWidth, cellHeight, 8, 8);
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(x, startY, cellWidth, cellHeight, 8, 8);
            
            // 绘制数值
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            String text = String.valueOf(nums.get(i));
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (cellWidth - fm.stringWidth(text)) / 2;
            int textY = startY + (cellHeight + fm.getAscent()) / 2;
            g2d.drawString(text, textX, textY);
            
            // 绘制下标
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
            g2d.setColor(Color.BLUE);
            String indexText = String.valueOf(i);
            int indexX = x + (cellWidth - g2d.getFontMetrics().stringWidth(indexText)) / 2;
            g2d.drawString(indexText, indexX, startY - 5);
        }
    }
    
    private void drawCurrentIndexInfo(Graphics2D g2d) {
        if (!isAnimating) return;
        
        int startY = 220;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("当前处理:", 50, startY);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("下标: " + currentIndex, 70, startY + 25);
        g2d.drawString("元素值: " + (currentIndex < nums.size() ? nums.get(currentIndex) : "N/A"), 70, startY + 45);
        g2d.drawString("目标K值: " + k, 70, startY + 65);
        
        if (!currentBinary.isEmpty()) {
            g2d.drawString("二进制: " + currentBinary, 70, startY + 85);
            g2d.drawString("置位数: " + currentSetBits, 70, startY + 105);
            
            if (animationStep >= 2) {
                g2d.setColor(isCurrentMatch ? SUCCESS_COLOR : Color.RED);
                g2d.drawString("匹配结果: " + (isCurrentMatch ? "匹配" : "不匹配"), 70, startY + 125);
            }
        }
    }
    
    private void drawBinaryRepresentation(Graphics2D g2d) {
        if (!isAnimating || currentBinary.isEmpty()) return;
        
        int startY = 380;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("二进制表示分析:", 50, startY);
        
        // 绘制二进制位
        int bitWidth = 30;
        int bitHeight = 30;
        int startX = 70;
        int binaryY = startY + 20;
        
        for (int i = 0; i < currentBinary.length(); i++) {
            char bit = currentBinary.charAt(i);
            int x = startX + i * (bitWidth + 5);
            
            // 设置颜色
            Color bitColor = bit == '1' ? HIGHLIGHT_COLOR : Color.LIGHT_GRAY;
            
            // 绘制二进制位
            g2d.setColor(bitColor);
            g2d.fillRoundRect(x, binaryY, bitWidth, bitHeight, 5, 5);
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(x, binaryY, bitWidth, bitHeight, 5, 5);
            
            // 绘制位值
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            String bitText = String.valueOf(bit);
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (bitWidth - fm.stringWidth(bitText)) / 2;
            int textY = binaryY + (bitHeight + fm.getAscent()) / 2;
            g2d.setColor(bit == '1' ? Color.WHITE : Color.BLACK);
            g2d.drawString(bitText, textX, textY);
        }
        
        // 绘制Brian Kernighan算法说明
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawString("Brian Kernighan算法: n & (n-1) 可以消除最右边的1", 70, binaryY + bitHeight + 20);
        g2d.drawString("重复此操作直到n变为0，操作次数即为置位数", 70, binaryY + bitHeight + 35);
    }
    
    private void drawMatchedIndices(Graphics2D g2d) {
        int startY = 500;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("匹配的下标:", 50, startY);
        
        if (matchedIndices.isEmpty()) {
            g2d.setFont(new Font("微软雅黑", Font.ITALIC, 14));
            g2d.setColor(Color.GRAY);
            g2d.drawString("暂无匹配", 70, startY + 25);
        } else {
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            g2d.setColor(Color.BLACK);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < matchedIndices.size(); i++) {
                if (i > 0) sb.append(", ");
                int index = matchedIndices.get(i);
                sb.append(index).append("(").append(nums.get(index)).append(")");
            }
            g2d.drawString("下标(值): " + sb.toString(), 70, startY + 25);
        }
    }
    
    private void drawAlgorithmStatus(Graphics2D g2d) {
        int startY = 560;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("算法状态:", 50, startY);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("当前和: " + currentSum, 70, startY + 25);
        g2d.drawString("已处理: " + currentIndex + " / " + nums.size(), 70, startY + 45);
        
        if (isAnimating) {
            g2d.setColor(HIGHLIGHT_COLOR);
            String stepInfo = "";
            switch (animationStep) {
                case 0:
                    stepInfo = "正在计算下标 " + currentIndex + " 的二进制表示";
                    break;
                case 1:
                    stepInfo = "正在检查置位数是否等于 " + k;
                    break;
                case 2:
                    stepInfo = "准备处理下一个下标";
                    break;
            }
            g2d.drawString("当前步骤: " + stepInfo, 70, startY + 65);
        }
    }
    
    private void drawComplexityInfo(Graphics2D g2d) {
        int startY = 650;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("算法复杂度分析:", 50, startY);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("• 时间复杂度: O(n × log(max_index)) - 遍历n个元素，每个元素计算置位数", 70, startY + 25);
        g2d.drawString("• 空间复杂度: O(1) - 只使用常数额外空间", 70, startY + 45);
        g2d.drawString("• Brian Kernighan算法优化: 每次操作消除一个1，效率更高", 70, startY + 65);
        
        // 显示当前统计
        g2d.setColor(SUCCESS_COLOR);
        String stats = String.format("数组长度: %d | K值: %d | 当前结果: %d", 
            nums.size(), k, currentSum);
        g2d.drawString(stats, 70, startY + 90);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO2859_E_SumIndicesWithKSetBits_Animation().setVisible(true);
        });
    }
}