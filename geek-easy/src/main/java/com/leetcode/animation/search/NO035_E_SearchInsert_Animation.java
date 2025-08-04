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
import java.util.Arrays;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.util.List;
import com.leetcode.animation.AlgorithmTreeLauncher;

/**
 * NO.035 搜索插入位置 - 动画演示
 * 演示使用二分查找算法寻找目标值插入位置的过程
 * 
 * 设计文档：
 * 1. 功能需求：可视化二分查找算法的执行过程
 * 2. 技术架构：基于Swing的动画演示系统
 * 3. 核心算法：二分查找，在有序数组中查找目标值或插入位置
 * 4. 边界条件：目标值存在、不存在、边界位置等情况
 * 5. 性能考虑：时间复杂度O(log n)，空间复杂度O(1)
 */
public class NO035_E_SearchInsert_Animation extends JFrame {
    private int[] array;
    private int target;
    private int left;
    private int right;
    private int mid;
    private int result;
    private boolean isAnimating;
    private Timer animationTimer;
    private String currentOperation;
    
    // UI组件
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JTextField arrayInput;
    private JTextField targetInput;
    private JButton startButton, stepButton, resetButton, autoButton, homeButton;
    private JLabel statusLabel;
    private JLabel resultLabel;
    
    // 动画步骤记录
    private List<AnimationStep> steps;
    private int currentStepIndex;
    
    private static class AnimationStep {
        int[] array;
        int target;
        int left;
        int right;
        int mid;
        String operation;
        String description;
        boolean found;
        int result;
        
        AnimationStep(int[] array, int target, int left, int right, int mid, 
                     String operation, String description, boolean found, int result) {
            this.array = Arrays.copyOf(array, array.length);
            this.target = target;
            this.left = left;
            this.right = right;
            this.mid = mid;
            this.operation = operation;
            this.description = description;
            this.found = found;
            this.result = result;
        }
    }
    
    public NO035_E_SearchInsert_Animation() {
        setTitle("NO.035 搜索插入位置 - 动画演示");
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
        array = new int[]{1, 3, 5, 6};
        target = 5;
        left = 0;
        right = array.length - 1;
        mid = 0;
        result = -1;
        isAnimating = false;
        currentOperation = "准备开始";
        steps = new ArrayList<>();
        currentStepIndex = 0;
    }
    
    private void initComponents() {
        mainPanel = new JPanel();
        controlPanel = new JPanel();
        
        arrayInput = new JTextField("1,3,5,6", 15);
        targetInput = new JTextField("5", 5);
        startButton = new JButton("开始演示");
        stepButton = new JButton("单步执行");
        resetButton = new JButton("重置");
        autoButton = new JButton("自动演示");
        homeButton = new JButton("返回首页");
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
        homeButton.addActionListener(e -> {
            // 停止动画
            isAnimating = false;
            if (animationTimer != null) {
                animationTimer.stop();
            }
            // 关闭当前窗口并返回主界面
            dispose();
            SwingUtilities.invokeLater(() -> {
                try {
                    com.leetcode.animation.AlgorithmTreeLauncher.showMainWindow();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });
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
            
            // 生成算法步骤
            generateSteps();
            
            // 重置状态
            currentStepIndex = 0;
            currentOperation = "开始二分查找";
            
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
        String[] parts = arrayInput.getText().trim().split(",");
        array = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            array[i] = Integer.parseInt(parts[i].trim());
        }
        target = Integer.parseInt(targetInput.getText().trim());
    }
    
    private void generateSteps() {
        steps.clear();
        int left = 0;
        int right = array.length - 1;
        int ans = array.length;
        
        steps.add(new AnimationStep(array, target, left, right, -1, "初始化", 
            String.format("初始化 left=%d, right=%d, ans=%d", left, right, ans), false, ans));
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            steps.add(new AnimationStep(array, target, left, right, mid, "计算中点", 
                String.format("计算中点 mid = left + (right - left) / 2 = %d", mid), false, ans));
            
            steps.add(new AnimationStep(array, target, left, right, mid, "比较", 
                String.format("比较 target=%d 与 nums[%d]=%d", target, mid, array[mid]), false, ans));
            
            if (target <= array[mid]) {
                ans = mid;
                right = mid - 1;
                steps.add(new AnimationStep(array, target, left, right, mid, "向左搜索", 
                    String.format("target <= nums[mid]，更新 ans=%d, right=%d", ans, right), false, ans));
            } else {
                left = mid + 1;
                steps.add(new AnimationStep(array, target, left, right, mid, "向右搜索", 
                    String.format("target > nums[mid]，更新 left=%d", left), false, ans));
            }
        }
        
        boolean found = false;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                found = true;
                break;
            }
        }
        
        String resultDesc = found ? 
            String.format("找到目标值，位置为 %d", ans) : 
            String.format("未找到目标值，插入位置为 %d", ans);
        
        steps.add(new AnimationStep(array, target, left, right, -1, "完成", resultDesc, found, ans));
    }
    
    private void stepExecution() {
        if (currentStepIndex >= steps.size()) {
            // 动画结束
            isAnimating = false;
            animationTimer.stop();
            autoButton.setText("自动演示");
            stepButton.setEnabled(false);
            
            AnimationStep lastStep = steps.get(steps.size() - 1);
            resultLabel.setText("结果: " + lastStep.description);
            return;
        }
        
        AnimationStep step = steps.get(currentStepIndex);
        left = step.left;
        right = step.right;
        mid = step.mid;
        result = step.result;
        currentOperation = step.operation;
        
        statusLabel.setText("状态: " + step.description);
        
        currentStepIndex++;
        repaint();
    }
    
    private void toggleAutoDemo() {
        if (isAnimating) {
            isAnimating = false;
            animationTimer.stop();
            autoButton.setText("自动演示");
        } else {
            isAnimating = true;
            animationTimer.start();
            autoButton.setText("暂停");
        }
    }
    
    private void resetDemo() {
        isAnimating = false;
        animationTimer.stop();
        
        currentStepIndex = 0;
        currentOperation = "准备开始";
        left = 0;
        right = array.length - 1;
        mid = 0;
        result = -1;
        
        startButton.setEnabled(true);
        stepButton.setEnabled(false);
        autoButton.setEnabled(false);
        autoButton.setText("自动演示");
        
        statusLabel.setText("状态: 准备开始");
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
            
            int width = getWidth();
            int height = getHeight();
            
            // 绘制标题
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 24));
            g2d.setColor(Color.BLACK);
            String title = "二分查找算法可视化";
            FontMetrics fm = g2d.getFontMetrics();
            int titleX = (width - fm.stringWidth(title)) / 2;
            g2d.drawString(title, titleX, 40);
            
            // 绘制目标值
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 18));
            g2d.setColor(Color.BLUE);
            String targetText = "目标值: " + target;
            g2d.drawString(targetText, 50, 80);
            
            // 绘制数组
            drawArray(g2d, width, height);
            
            // 绘制指针
            drawPointers(g2d, width, height);
            
            // 绘制算法说明
            drawAlgorithmExplanation(g2d, width, height);
            
            // 绘制步骤说明
            if (currentStepIndex > 0 && !steps.isEmpty()) {
                drawStepExplanation(g2d, width, height);
            }
        }
        
        private void drawArray(Graphics2D g2d, int width, int height) {
            if (array == null) return;
            
            int cellSize = 60;
            int startX = (width - array.length * cellSize) / 2;
            int arrayY = 120;
            
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 18));
            
            for (int i = 0; i < array.length; i++) {
                int x = startX + i * cellSize;
                
                // 确定颜色
                Color cellColor = Color.WHITE;
                Color textColor = Color.BLACK;
                Color borderColor = Color.BLACK;
                
                if (currentStepIndex > 0) {
                    if (i == mid) {
                        cellColor = Color.YELLOW; // 中点
                        borderColor = Color.ORANGE;
                    } else if (i >= left && i <= right) {
                        cellColor = new Color(173, 216, 230); // 搜索范围 (浅蓝色)
                    } else {
                        cellColor = Color.LIGHT_GRAY; // 已排除的区域
                        textColor = Color.GRAY;
                    }
                    
                    if (array[i] == target) {
                        cellColor = new Color(144, 238, 144); // 目标值 (浅绿色)
                        textColor = new Color(0, 100, 0); // 深绿色
                        borderColor = Color.GREEN;
                    }
                }
                
                // 绘制单元格
                g2d.setColor(cellColor);
                g2d.fillRect(x, arrayY, cellSize, cellSize);
                g2d.setColor(borderColor);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRect(x, arrayY, cellSize, cellSize);
                
                // 绘制数值
                g2d.setColor(textColor);
                String value = String.valueOf(array[i]);
                FontMetrics fm = g2d.getFontMetrics();
                int textX = x + (cellSize - fm.stringWidth(value)) / 2;
                int textY = arrayY + (cellSize + fm.getAscent()) / 2;
                g2d.drawString(value, textX, textY);
                
                // 绘制索引
                g2d.setColor(Color.GRAY);
                g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                String index = String.valueOf(i);
                fm = g2d.getFontMetrics();
                textX = x + (cellSize - fm.stringWidth(index)) / 2;
                g2d.drawString(index, textX, arrayY - 5);
            }
        }
        
        private void drawPointers(Graphics2D g2d, int width, int height) {
            if (array == null || currentStepIndex == 0) return;
            
            int cellSize = 60;
            int startX = (width - array.length * cellSize) / 2;
            int arrayY = 120;
            
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            g2d.setStroke(new BasicStroke(2));
            
            // 绘制左指针
            if (left < array.length) {
                int leftX = startX + left * cellSize + cellSize / 2;
                g2d.setColor(Color.BLUE);
                g2d.fillPolygon(new int[]{leftX - 10, leftX + 10, leftX}, 
                               new int[]{arrayY - 30, arrayY - 30, arrayY - 15}, 3);
                g2d.drawString("left", leftX - 15, arrayY - 35);
            }
            
            // 绘制右指针
            if (right >= 0 && right < array.length) {
                int rightX = startX + right * cellSize + cellSize / 2;
                g2d.setColor(Color.RED);
                g2d.fillPolygon(new int[]{rightX - 10, rightX + 10, rightX}, 
                               new int[]{arrayY + cellSize + 30, arrayY + cellSize + 30, arrayY + cellSize + 15}, 3);
                g2d.drawString("right", rightX - 20, arrayY + cellSize + 45);
            }
            
            // 绘制中点指针
            if (mid >= 0 && mid < array.length) {
                int midX = startX + mid * cellSize + cellSize / 2;
                g2d.setColor(Color.ORANGE);
                g2d.fillPolygon(new int[]{midX - 8, midX + 8, midX}, 
                               new int[]{arrayY - 50, arrayY - 50, arrayY - 35}, 3);
                g2d.drawString("mid", midX - 12, arrayY - 55);
            }
        }
        
        private void drawAlgorithmExplanation(Graphics2D g2d, int width, int height) {
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            g2d.setColor(Color.BLUE);
            
            int x = 50;
            int y = 250;
            
            g2d.drawString("算法原理:", x, y);
            y += 25;
            g2d.drawString("1. 在有序数组中使用二分查找", x, y);
            y += 20;
            g2d.drawString("2. 每次比较中点元素与目标值", x, y);
            y += 20;
            g2d.drawString("3. 如果target <= nums[mid]:", x, y);
            y += 20;
            g2d.drawString("   更新答案ans=mid，向左搜索", x, y);
            y += 20;
            g2d.drawString("4. 否则向右搜索", x, y);
            y += 20;
            g2d.drawString("5. 最终ans就是插入位置", x, y);
            y += 20;
            g2d.drawString("6. 时间复杂度: O(log n)", x, y);
        }
        
        private void drawStepExplanation(Graphics2D g2d, int width, int height) {
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            g2d.setColor(Color.BLACK);
            
            int x = width - 400;
            int y = 250;
            
            g2d.drawString("执行步骤:", x, y);
            y += 25;
            
            // 显示最近几个步骤
            int startStep = Math.max(0, currentStepIndex - 6);
            for (int i = startStep; i < Math.min(currentStepIndex, steps.size()); i++) {
                AnimationStep step = steps.get(i);
                
                if (i == currentStepIndex - 1) {
                    g2d.setColor(Color.RED);
                    g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
                } else {
                    g2d.setColor(Color.GRAY);
                    g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                }
                
                String stepText = String.format("%d. %s", i + 1, step.description);
                if (stepText.length() > 35) {
                    stepText = stepText.substring(0, 35) + "...";
                }
                g2d.drawString(stepText, x, y);
                y += 20;
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO035_E_SearchInsert_Animation().setVisible(true);
        });
    }
}