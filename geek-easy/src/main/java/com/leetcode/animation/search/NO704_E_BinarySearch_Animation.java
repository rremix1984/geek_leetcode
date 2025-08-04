package com.leetcode.animation.search;

import javax.swing.*;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.awt.*;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.awt.event.ActionEvent;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.awt.event.ActionListener;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.util.ArrayList;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.util.List;
import com.leetcode.animation.AlgorithmTreeLauncher;

/**
 * NO.704 二分查找 - 动画演示
 * 演示二分查找算法的执行过程
 * 
 * 设计文档：
 * 1. 功能需求：可视化二分查找算法的执行过程
 * 2. 技术架构：基于Swing的动画演示系统
 * 3. 核心算法：二分查找，通过left、right、mid指针缩小搜索范围
 * 4. 边界条件：数组为空、目标不存在、数组未排序等情况
 * 5. 性能考虑：时间复杂度O(log n)，空间复杂度O(1)
 */
public class NO704_E_BinarySearch_Animation extends JFrame {
    private List<Integer> nums;
    private int target;
    private int left, right, mid;
    private boolean isAnimating;
    private Timer animationTimer;
    
    // UI组件
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JTextField arrayInput;
    private JTextField targetInput;
    private JButton startButton, stepButton, resetButton, autoButton;
    private JLabel statusLabel;
    private JLabel resultLabel;
    
    // 动画状态
    private boolean found;
    private int result;
    private String currentOperation;
    private int step;
    
    public NO704_E_BinarySearch_Animation() {
        setTitle("NO.704 二分查找 - 动画演示");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        initData();
        initComponents();
        setupLayout();
        setupEventHandlers();
        setupAnimation();
    }
    
    private void initData() {
        nums = new ArrayList<>();
        left = 0;
        right = 0;
        mid = 0;
        isAnimating = false;
        found = false;
        result = -1;
        currentOperation = "准备开始";
        step = 0;
        
        // 默认示例数据 - 有序数组
        int[] defaultArray = {-1, 0, 3, 5, 9, 12};
        for (int num : defaultArray) {
            nums.add(num);
        }
        target = 9;
    }
    
    private void initComponents() {
        mainPanel = new JPanel();
        controlPanel = new JPanel();
        
        arrayInput = new JTextField("-1,0,3,5,9,12", 20);
        targetInput = new JTextField("9", 5);
        startButton = new JButton("开始演示");
        stepButton = new JButton("单步执行");
        resetButton = new JButton("重置");
        autoButton = new JButton("自动演示");
        statusLabel = new JLabel("状态: 准备开始");
        resultLabel = new JLabel("结果: 未开始");
        
        // 设置字体
        Font font = new Font("微软雅黑", Font.PLAIN, 14);
        statusLabel.setFont(font);
        resultLabel.setFont(font);
        
        stepButton.setEnabled(false);
        autoButton.setEnabled(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 主面板 - 可视化区域
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new BinarySearchVisualizationPanel(), BorderLayout.CENTER);
        
        // 返回首页按钮
        JButton homeButton = new JButton("返回首页");
        homeButton.addActionListener(e -> {
            dispose(); // 关闭当前窗口
            // 启动主界面
            SwingUtilities.invokeLater(() -> {
                 try {
                     dispose(); // 关闭当前动画窗口
                     com.leetcode.animation.AlgorithmTreeLauncher.showMainWindow();
                 } catch (Exception ex) {
                     ex.printStackTrace();
                 }
             });
        });
        
        // 控制面板
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        controlPanel.add(new JLabel("有序数组(逗号分隔):"), gbc);
        gbc.gridx = 1;
        controlPanel.add(arrayInput, gbc);
        
        gbc.gridx = 2; gbc.gridy = 0;
        controlPanel.add(new JLabel("目标值:"), gbc);
        gbc.gridx = 3;
        controlPanel.add(targetInput, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        controlPanel.add(startButton, gbc);
        gbc.gridx = 1;
        controlPanel.add(stepButton, gbc);
        gbc.gridx = 2;
        controlPanel.add(autoButton, gbc);
        gbc.gridx = 3;
        controlPanel.add(resetButton, gbc);
        gbc.gridx = 4;
        controlPanel.add(homeButton, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 5;
        controlPanel.add(statusLabel, gbc);
        
        gbc.gridy = 3;
        controlPanel.add(resultLabel, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        startButton.addActionListener(e -> startDemo());
        stepButton.addActionListener(e -> stepExecution());
        autoButton.addActionListener(e -> toggleAutoDemo());
        resetButton.addActionListener(e -> resetDemo());
    }
    
    private void setupAnimation() {
        animationTimer = new Timer(1500, e -> {
            if (isAnimating) {
                stepExecution();
            }
        });
    }
    
    private void startDemo() {
        try {
            // 解析输入
            parseInput();
            
            // 重置状态
            left = 0;
            right = nums.size() - 1;
            mid = 0;
            found = false;
            result = -1;
            step = 0;
            currentOperation = "初始化: left=0, right=" + right;
            
            // 更新UI状态
            startButton.setEnabled(false);
            stepButton.setEnabled(true);
            autoButton.setEnabled(true);
            
            statusLabel.setText("状态: " + currentOperation);
            resultLabel.setText("结果: 开始执行算法");
            
            repaint();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "输入格式错误: " + ex.getMessage());
        }
    }
    
    private void parseInput() {
        nums.clear();
        String[] parts = arrayInput.getText().trim().split(",");
        for (String part : parts) {
            nums.add(Integer.parseInt(part.trim()));
        }
        target = Integer.parseInt(targetInput.getText().trim());
        
        // 边界条件检查
        if (nums.isEmpty()) {
            throw new IllegalArgumentException("数组不能为空");
        }
        
        // 检查数组是否有序
        for (int i = 1; i < nums.size(); i++) {
            if (nums.get(i) < nums.get(i-1)) {
                throw new IllegalArgumentException("数组必须是有序的");
            }
        }
    }
    
    private void stepExecution() {
        if (left > right) {
            // 查找结束，未找到
            isAnimating = false;
            animationTimer.stop();
            autoButton.setText("自动演示");
            stepButton.setEnabled(false);
            autoButton.setEnabled(false);
            startButton.setEnabled(true);
            
            currentOperation = "查找结束，未找到目标值";
            resultLabel.setText("结果: -1 (未找到)");
            statusLabel.setText("状态: " + currentOperation);
            return;
        }
        
        if (found) {
            // 已找到，结束
            isAnimating = false;
            animationTimer.stop();
            autoButton.setText("自动演示");
            stepButton.setEnabled(false);
            autoButton.setEnabled(false);
            startButton.setEnabled(true);
            
            currentOperation = "找到目标值！";
            resultLabel.setText("结果: " + result + " (索引位置)");
            statusLabel.setText("状态: " + currentOperation);
            return;
        }
        
        // 执行二分查找步骤
        step++;
        mid = left + (right - left) / 2;
        int midValue = nums.get(mid);
        
        currentOperation = "第" + step + "步: mid=" + mid + ", nums[" + mid + "]=" + midValue;
        
        if (midValue == target) {
            // 找到目标
            found = true;
            result = mid;
            currentOperation += " -> 找到目标值！";
        } else if (midValue > target) {
            // 目标在左半部分
            right = mid - 1;
            currentOperation += " > " + target + " -> 搜索左半部分, right=" + right;
        } else {
            // 目标在右半部分
            left = mid + 1;
            currentOperation += " < " + target + " -> 搜索右半部分, left=" + left;
        }
        
        statusLabel.setText("状态: " + currentOperation);
        repaint();
    }
    
    private void toggleAutoDemo() {
        if (isAnimating) {
            isAnimating = false;
            animationTimer.stop();
            autoButton.setText("自动演示");
            stepButton.setEnabled(true);
        } else {
            isAnimating = true;
            animationTimer.start();
            autoButton.setText("暂停");
            stepButton.setEnabled(false);
        }
    }
    
    private void resetDemo() {
        isAnimating = false;
        animationTimer.stop();
        
        left = 0;
        right = 0;
        mid = 0;
        found = false;
        result = -1;
        step = 0;
        currentOperation = "准备开始";
        
        startButton.setEnabled(true);
        stepButton.setEnabled(false);
        autoButton.setEnabled(false);
        autoButton.setText("自动演示");
        
        statusLabel.setText("状态: " + currentOperation);
        resultLabel.setText("结果: 未开始");
        
        repaint();
    }
    
    // 可视化面板
    private class BinarySearchVisualizationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            drawArray(g2d);
            drawPointers(g2d);
            drawAlgorithmInfo(g2d);
            drawSearchRange(g2d);
        }
        
        private void drawArray(Graphics2D g2d) {
            int startX = 50;
            int startY = 100;
            
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g2d.drawString("有序数组:", startX, startY - 10);
            
            int cellWidth = 60;
            int cellHeight = 40;
            
            for (int i = 0; i < nums.size(); i++) {
                int x = startX + i * (cellWidth + 10);
                int y = startY;
                
                // 绘制单元格
                Rectangle rect = new Rectangle(x, y, cellWidth, cellHeight);
                
                // 设置颜色
                if (found && i == result) {
                    g2d.setColor(Color.GREEN); // 找到的目标
                } else if (i == mid && step > 0) {
                    g2d.setColor(Color.YELLOW); // 当前mid位置
                } else if (i >= left && i <= right && step > 0) {
                    g2d.setColor(Color.LIGHT_GRAY); // 搜索范围内
                } else {
                    g2d.setColor(Color.WHITE); // 搜索范围外
                }
                
                g2d.fill(rect);
                g2d.setColor(Color.BLACK);
                g2d.draw(rect);
                
                // 绘制数值
                String value = nums.get(i).toString();
                FontMetrics fm = g2d.getFontMetrics();
                int textX = x + (cellWidth - fm.stringWidth(value)) / 2;
                int textY = y + (cellHeight + fm.getAscent()) / 2;
                g2d.drawString(value, textX, textY);
                
                // 绘制索引
                g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                g2d.drawString("" + i, x + cellWidth/2 - 5, y + cellHeight + 15);
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            }
            
            // 绘制目标值
            g2d.setColor(Color.RED);
            g2d.drawString("目标值: " + target, startX, startY + cellHeight + 40);
        }
        
        private void drawPointers(Graphics2D g2d) {
            if (step == 0) return;
            
            int startX = 50;
            int startY = 100;
            int cellWidth = 60;
            int pointerY = startY - 30;
            
            // 绘制left指针
            if (left < nums.size()) {
                int leftX = startX + left * (cellWidth + 10) + cellWidth/2;
                g2d.setColor(Color.BLUE);
                g2d.drawString("L", leftX - 5, pointerY);
                g2d.drawLine(leftX, pointerY + 5, leftX, startY - 5);
            }
            
            // 绘制right指针
            if (right >= 0 && right < nums.size()) {
                int rightX = startX + right * (cellWidth + 10) + cellWidth/2;
                g2d.setColor(Color.RED);
                g2d.drawString("R", rightX - 5, pointerY);
                g2d.drawLine(rightX, pointerY + 5, rightX, startY - 5);
            }
            
            // 绘制mid指针
            if (mid >= 0 && mid < nums.size()) {
                int midX = startX + mid * (cellWidth + 10) + cellWidth/2;
                g2d.setColor(Color.ORANGE);
                g2d.drawString("M", midX - 5, pointerY - 15);
                g2d.drawLine(midX, pointerY - 10, midX, startY - 5);
            }
        }
        
        private void drawSearchRange(Graphics2D g2d) {
            if (step == 0) return;
            
            int startX = 50;
            int startY = 200;
            
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            g2d.drawString("当前搜索范围:", startX, startY);
            
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
            g2d.drawString("left = " + left, startX, startY + 20);
            g2d.drawString("right = " + right, startX, startY + 35);
            g2d.drawString("mid = " + mid, startX, startY + 50);
            
            if (mid >= 0 && mid < nums.size()) {
                g2d.drawString("nums[mid] = " + nums.get(mid), startX, startY + 65);
            }
            
            g2d.drawString("搜索范围大小: " + Math.max(0, right - left + 1), startX, startY + 80);
        }
        
        private void drawAlgorithmInfo(Graphics2D g2d) {
            int startX = 400;
            int startY = 200;
            
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            g2d.drawString("二分查找算法:", startX, startY);
            
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
            String[] info = {
                "1. 初始化 left=0, right=n-1",
                "2. 当 left <= right 时:",
                "   a) 计算 mid = left + (right-left)/2",
                "   b) 如果 nums[mid] == target，返回 mid",
                "   c) 如果 nums[mid] > target，right = mid-1",
                "   d) 如果 nums[mid] < target，left = mid+1",
                "3. 如果未找到，返回 -1",
                "",
                "时间复杂度: O(log n)",
                "空间复杂度: O(1)",
                "",
                "前提条件: 数组必须有序"
            };
            
            for (int i = 0; i < info.length; i++) {
                g2d.drawString(info[i], startX, startY + 20 + i * 15);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO704_E_BinarySearch_Animation().setVisible(true);
        });
    }
}