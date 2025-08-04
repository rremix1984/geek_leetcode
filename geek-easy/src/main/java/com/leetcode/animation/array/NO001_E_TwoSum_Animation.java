package com.leetcode.animation.array;

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
import java.util.HashMap;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.util.List;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.util.Map;
import com.leetcode.animation.AlgorithmTreeLauncher;

/**
 * NO.001 两数之和 - 动画演示
 * 演示使用HashMap解决两数之和问题的过程
 * 
 * 设计文档：
 * 1. 功能需求：可视化两数之和算法的执行过程
 * 2. 技术架构：基于Swing的动画演示系统
 * 3. 核心算法：HashMap存储已遍历元素，查找目标差值
 * 4. 边界条件：数组为空、无解、多解等情况
 * 5. 性能考虑：时间复杂度O(n)，空间复杂度O(n)
 */
public class NO001_E_TwoSum_Animation extends JFrame {
    private List<Integer> nums;
    private int target;
    private Map<Integer, Integer> map;
    private int currentIndex;
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
    private int[] result;
    private boolean found;
    private String currentOperation;
    
    public NO001_E_TwoSum_Animation() {
        setTitle("NO.001 两数之和 - 动画演示");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        
        initData();
        initComponents();
        setupLayout();
        setupEventHandlers();
        setupAnimation();
    }
    
    private void initData() {
        nums = new ArrayList<>();
        map = new HashMap<>();
        currentIndex = 0;
        isAnimating = false;
        found = false;
        result = null;
        currentOperation = "准备开始";
        
        // 默认示例数据
        nums.add(2);
        nums.add(7);
        nums.add(11);
        nums.add(15);
        target = 9;
    }
    
    private void initComponents() {
        mainPanel = new JPanel();
        controlPanel = new JPanel();
        
        arrayInput = new JTextField("2,7,11,15", 15);
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
        mainPanel.add(new TwoSumVisualizationPanel(), BorderLayout.CENTER);
        
        // 控制面板
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        controlPanel.add(new JLabel("数组(逗号分隔):"), gbc);
        gbc.gridx = 1;
        controlPanel.add(arrayInput, gbc);
        
        gbc.gridx = 2; gbc.gridy = 0;
        controlPanel.add(new JLabel("目标值:"), gbc);
        gbc.gridx = 3;
        controlPanel.add(targetInput, gbc);
        
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

        gbc.gridx = 0;
        controlPanel.add(startButton, gbc);
        gbc.gridx = 1;
        controlPanel.add(stepButton, gbc);
        gbc.gridx = 2;
        controlPanel.add(autoButton, gbc);
        gbc.gridx = 3;
        controlPanel.add(resetButton, gbc);
        gbc.gridx = 4;
        controlPanel.add(homeButton, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 4;
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
            map.clear();
            currentIndex = 0;
            found = false;
            result = null;
            currentOperation = "开始遍历数组";
            
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
        if (nums.size() < 2) {
            throw new IllegalArgumentException("数组至少需要2个元素");
        }
    }
    
    private void stepExecution() {
        if (found || currentIndex >= nums.size()) {
            // 算法结束
            isAnimating = false;
            animationTimer.stop();
            autoButton.setText("自动演示");
            stepButton.setEnabled(false);
            autoButton.setEnabled(false);
            startButton.setEnabled(true);
            
            if (found) {
                currentOperation = "找到答案！";
                resultLabel.setText("结果: 索引 [" + result[0] + ", " + result[1] + "]");
            } else {
                currentOperation = "未找到答案";
                resultLabel.setText("结果: 无解");
            }
            statusLabel.setText("状态: " + currentOperation);
            return;
        }
        
        // 执行算法步骤
        int currentNum = nums.get(currentIndex);
        int complement = target - currentNum;
        
        currentOperation = "检查元素 " + currentNum + " (索引" + currentIndex + "), 寻找 " + complement;
        
        if (map.containsKey(complement)) {
            // 找到答案
            result = new int[]{map.get(complement), currentIndex};
            found = true;
            currentOperation = "找到答案: " + complement + " + " + currentNum + " = " + target;
        } else {
            // 将当前元素加入map
            map.put(currentNum, currentIndex);
            currentOperation += " -> 将 " + currentNum + " 存入HashMap";
        }
        
        statusLabel.setText("状态: " + currentOperation);
        currentIndex++;
        
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
        
        map.clear();
        currentIndex = 0;
        found = false;
        result = null;
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
    private class TwoSumVisualizationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            drawArray(g2d, 50, 80);
            drawHashMap(g2d);
            drawAlgorithmInfo(g2d);
        }
        
        private void drawArray(Graphics2D g2d, int startX, int startY) {
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g2d.drawString("数组 nums:", startX, startY - 10);
            
            int cellWidth = 60;
            int cellHeight = 40;
            
            for (int i = 0; i < nums.size(); i++) {
                int x = startX + i * (cellWidth + 10);
                int y = startY;
                
                // 绘制单元格
                Rectangle rect = new Rectangle(x, y, cellWidth, cellHeight);
                
                // 设置颜色
                if (i < currentIndex) {
                    g2d.setColor(Color.LIGHT_GRAY); // 已处理
                } else if (i == currentIndex && !found) {
                    g2d.setColor(Color.YELLOW); // 当前处理
                } else if (found && result != null && (i == result[0] || i == result[1])) {
                    g2d.setColor(Color.GREEN); // 答案
                } else {
                    g2d.setColor(Color.WHITE); // 未处理
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
        
        private void drawHashMap(Graphics2D g2d) {
            int startX = 50;
            int startY = 200;
            
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g2d.drawString("HashMap (值 -> 索引):", startX, startY - 10);
            
            int row = 0;
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                int x = startX;
                int y = startY + row * 30;
                
                g2d.setColor(Color.CYAN);
                Rectangle rect = new Rectangle(x, y, 120, 25);
                g2d.fill(rect);
                g2d.setColor(Color.BLACK);
                g2d.draw(rect);
                
                String text = entry.getKey() + " -> " + entry.getValue();
                g2d.drawString(text, x + 5, y + 18);
                
                row++;
            }
            
            if (map.isEmpty()) {
                g2d.setColor(Color.GRAY);
                g2d.drawString("(空)", startX, startY + 20);
            }
        }
        
        private void drawAlgorithmInfo(Graphics2D g2d) {
            int startX = 400;
            int startY = 200;
            
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            g2d.drawString("算法说明:", startX, startY);
            
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
            String[] info = {
                "1. 遍历数组，对每个元素 nums[i]",
                "2. 计算目标差值: complement = target - nums[i]", 
                "3. 检查HashMap中是否存在complement",
                "4. 如果存在，返回 [map[complement], i]",
                "5. 否则，将 nums[i] -> i 存入HashMap",
                "",
                "时间复杂度: O(n)",
                "空间复杂度: O(n)"
            };
            
            for (int i = 0; i < info.length; i++) {
                g2d.drawString(info[i], startX, startY + 20 + i * 15);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO001_E_TwoSum_Animation().setVisible(true);
        });
    }
}