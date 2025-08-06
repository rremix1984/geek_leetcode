package com.animation.normal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * NO053 最大子数组和 动画演示
 * 
 * 题目描述：
 * 给你一个整数数组 nums，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * 
 * @author AI Assistant
 */
public class NO053_N_MaximumSubarray_Animation extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    // UI组件
    private JTextField inputField;
    private JButton findButton;
    private JButton clearButton;
    private JButton demoButton;
    private JTextArea logArea;
    private JPanel visualPanel;
    
    // 数据
    private int[] nums;
    private int[] dp; // 动态规划数组
    private int currentIndex;
    private int maxSum;
    private int maxStart;
    private int maxEnd;
    private int currentSum;
    private boolean isAnimating;
    
    public NO053_N_MaximumSubarray_Animation() {
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("NO053 - 最大子数组和 动画演示");
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
        visualPanel.setPreferredSize(new Dimension(900, 600));
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
        
        addLog("最大子数组和算法初始化完成");
        addLog("请输入数组，格式: -2,1,-3,4,-1,2,1,-5,4");
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        panel.add(new JLabel("输入数组:"));
        inputField = new JTextField(25);
        inputField.setText("-2,1,-3,4,-1,2,1,-5,4");
        panel.add(inputField);
        
        findButton = new JButton("查找最大和");
        findButton.addActionListener(e -> findMaxSubarray());
        panel.add(findButton);
        
        demoButton = new JButton("演示样例");
        demoButton.addActionListener(e -> loadDemoData());
        panel.add(demoButton);
        
        clearButton = new JButton("清空");
        clearButton.addActionListener(e -> clearVisualization());
        panel.add(clearButton);
        
        return panel;
    }
    
    private void findMaxSubarray() {
        String input = inputField.getText().trim();
        if (input.isEmpty()) {
            addLog("请输入有效的数组");
            return;
        }
        
        try {
            String[] parts = input.split(",");
            nums = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                nums[i] = Integer.parseInt(parts[i].trim());
            }
        } catch (NumberFormatException e) {
            addLog("输入格式错误，请使用逗号分隔的整数");
            return;
        }
        
        dp = new int[nums.length];
        currentIndex = -1;
        maxSum = Integer.MIN_VALUE;
        maxStart = 0;
        maxEnd = 0;
        currentSum = 0;
        
        addLog("开始查找数组 " + Arrays.toString(nums) + " 的最大子数组和");
        addLog("使用动态规划算法 (Kadane's Algorithm)");
        
        // 启动动画线程
        new Thread(this::animateMaxSubarray).start();
    }
    
    private void animateMaxSubarray() {
        isAnimating = true;
        
        addLog("初始化: dp[0] = nums[0] = " + nums[0]);
        dp[0] = nums[0];
        maxSum = nums[0];
        maxStart = 0;
        maxEnd = 0;
        currentIndex = 0;
        
        SwingUtilities.invokeLater(() -> visualPanel.repaint());
        sleep(1000);
        
        int tempStart = 0; // 临时起始位置
        
        for (int i = 1; i < nums.length; i++) {
            currentIndex = i;
            
            addLog("\n处理 nums[" + i + "] = " + nums[i]);
            
            // 计算 dp[i]
            if (dp[i-1] > 0) {
                dp[i] = dp[i-1] + nums[i];
                addLog("dp[" + (i-1) + "] > 0, 所以 dp[" + i + "] = dp[" + (i-1) + "] + nums[" + i + "] = " + dp[i-1] + " + " + nums[i] + " = " + dp[i]);
            } else {
                dp[i] = nums[i];
                tempStart = i; // 重新开始
                addLog("dp[" + (i-1) + "] <= 0, 所以 dp[" + i + "] = nums[" + i + "] = " + dp[i]);
                addLog("子数组重新从位置 " + i + " 开始");
            }
            
            // 更新最大值
            if (dp[i] > maxSum) {
                maxSum = dp[i];
                maxStart = tempStart;
                maxEnd = i;
                addLog("✓ 更新最大和: " + maxSum + " (位置 " + maxStart + " 到 " + maxEnd + ")");
            }
            
            SwingUtilities.invokeLater(() -> visualPanel.repaint());
            sleep(1500);
        }
        
        addLog("\n算法完成！");
        addLog("最大子数组和: " + maxSum);
        addLog("最大子数组: " + Arrays.toString(Arrays.copyOfRange(nums, maxStart, maxEnd + 1)));
        addLog("位置: [" + maxStart + ", " + maxEnd + "]");
        
        SwingUtilities.invokeLater(() -> visualPanel.repaint());
        isAnimating = false;
    }
    
    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private void loadDemoData() {
        inputField.setText("-2,1,-3,4,-1,2,1,-5,4");
        addLog("加载演示数据: [-2,1,-3,4,-1,2,1,-5,4]");
    }
    
    private void clearVisualization() {
        nums = null;
        dp = null;
        currentIndex = -1;
        maxSum = Integer.MIN_VALUE;
        maxStart = 0;
        maxEnd = 0;
        currentSum = 0;
        logArea.setText("");
        SwingUtilities.invokeLater(() -> visualPanel.repaint());
        addLog("可视化已清空");
    }
    
    private void drawVisualization(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (nums == null) {
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.setColor(Color.GRAY);
            g2d.drawString("请输入数组开始演示", 50, 200);
            return;
        }
        
        int startX = 50;
        int startY = 60;
        int cellWidth = 60;
        int cellHeight = 40;
        
        // 绘制原数组
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("原数组 nums:", startX, startY - 10);
        
        for (int i = 0; i < nums.length; i++) {
            int x = startX + i * cellWidth;
            
            // 设置颜色
            if (i >= maxStart && i <= maxEnd) {
                g2d.setColor(Color.GREEN); // 最大子数组
            } else if (i == currentIndex) {
                g2d.setColor(Color.YELLOW); // 当前处理的元素
            } else {
                g2d.setColor(Color.LIGHT_GRAY);
            }
            
            // 绘制单元格背景
            g2d.fillRect(x, startY, cellWidth, cellHeight);
            
            // 绘制边框
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, startY, cellWidth, cellHeight);
            
            // 绘制数值
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            String text = String.valueOf(nums[i]);
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (cellWidth - fm.stringWidth(text)) / 2;
            int textY = startY + (cellHeight + fm.getAscent()) / 2;
            g2d.drawString(text, textX, textY);
            
            // 绘制索引
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.drawString(String.valueOf(i), x + cellWidth/2 - 5, startY - 5);
        }
        
        // 绘制DP数组
        if (dp != null) {
            int dpY = startY + 80;
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.setColor(Color.BLACK);
            g2d.drawString("DP数组 (以当前位置结尾的最大子数组和):", startX, dpY - 10);
            
            for (int i = 0; i < nums.length; i++) {
                int x = startX + i * cellWidth;
                
                // 设置颜色
                if (i <= currentIndex) {
                    if (i >= maxStart && i <= maxEnd) {
                        g2d.setColor(Color.GREEN); // 最大子数组对应的DP值
                    } else if (i == currentIndex) {
                        g2d.setColor(Color.YELLOW); // 当前计算的DP值
                    } else {
                        g2d.setColor(Color.CYAN); // 已计算的DP值
                    }
                } else {
                    g2d.setColor(Color.LIGHT_GRAY); // 未计算的DP值
                }
                
                // 绘制单元格背景
                g2d.fillRect(x, dpY, cellWidth, cellHeight);
                
                // 绘制边框
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, dpY, cellWidth, cellHeight);
                
                // 绘制DP值
                if (i <= currentIndex) {
                    g2d.setFont(new Font("Arial", Font.BOLD, 16));
                    String text = String.valueOf(dp[i]);
                    FontMetrics fm = g2d.getFontMetrics();
                    int textX = x + (cellWidth - fm.stringWidth(text)) / 2;
                    int textY = dpY + (cellHeight + fm.getAscent()) / 2;
                    g2d.drawString(text, textX, textY);
                } else {
                    g2d.setFont(new Font("Arial", Font.PLAIN, 14));
                    g2d.setColor(Color.GRAY);
                    String text = "?";
                    FontMetrics fm = g2d.getFontMetrics();
                    int textX = x + (cellWidth - fm.stringWidth(text)) / 2;
                    int textY = dpY + (cellHeight + fm.getAscent()) / 2;
                    g2d.drawString(text, textX, textY);
                }
            }
        }
        
        // 绘制当前状态信息
        int infoY = startY + 180;
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.setColor(Color.BLACK);
        
        if (currentIndex >= 0) {
            g2d.drawString("当前处理: nums[" + currentIndex + "] = " + nums[currentIndex], startX, infoY);
            
            if (currentIndex > 0) {
                g2d.drawString("状态转移: dp[" + currentIndex + "] = max(dp[" + (currentIndex-1) + "] + nums[" + currentIndex + "], nums[" + currentIndex + "])", startX, infoY + 25);
                g2d.drawString("         = max(" + dp[currentIndex-1] + " + " + nums[currentIndex] + ", " + nums[currentIndex] + ") = " + dp[currentIndex], startX, infoY + 45);
            }
        }
        
        // 绘制结果信息
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.setColor(Color.BLUE);
        g2d.drawString("当前最大子数组和: " + (maxSum == Integer.MIN_VALUE ? "未计算" : maxSum), startX, infoY + 90);
        
        if (maxSum != Integer.MIN_VALUE) {
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.drawString("最大子数组位置: [" + maxStart + ", " + maxEnd + "]", startX, infoY + 115);
            
            // 显示最大子数组内容
            StringBuilder sb = new StringBuilder("最大子数组: [");
            for (int i = maxStart; i <= maxEnd; i++) {
                sb.append(nums[i]);
                if (i < maxEnd) sb.append(", ");
            }
            sb.append("]");
            g2d.drawString(sb.toString(), startX, infoY + 140);
        }
        
        // 绘制算法说明
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.setColor(Color.GRAY);
        g2d.drawString("Kadane算法: dp[i] = max(dp[i-1] + nums[i], nums[i])", startX, infoY + 180);
        g2d.drawString("时间复杂度: O(n), 空间复杂度: O(n) 可优化为 O(1)", startX, infoY + 200);
        
        // 绘制颜色说明
        int legendY = infoY + 230;
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        
        g2d.setColor(Color.GREEN);
        g2d.fillRect(startX, legendY, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("最大子数组", startX + 20, legendY + 12);
        
        g2d.setColor(Color.YELLOW);
        g2d.fillRect(startX + 120, legendY, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("当前处理", startX + 140, legendY + 12);
        
        g2d.setColor(Color.CYAN);
        g2d.fillRect(startX + 220, legendY, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("已计算", startX + 240, legendY + 12);
    }
    
    private void addLog(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO053_N_MaximumSubarray_Animation().setVisible(true);
        });
    }
}