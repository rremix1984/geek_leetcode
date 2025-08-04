package com.animation.array;

import javax.swing.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.event.ActionEvent;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.event.ActionListener;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.util.ArrayList;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.util.Arrays;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.util.List;
import com.animation.launcher.AlgorithmTreeLauncher;

/**
 * NO.027 移除元素 - 动画演示
 * 演示使用双指针原地移除数组中指定元素的过程
 * 
 * 设计文档：
 * 1. 功能需求：可视化双指针移除元素算法的执行过程
 * 2. 技术架构：基于Swing的动画演示系统
 * 3. 核心算法：双指针技术，左指针记录有效位置，右指针遍历数组
 * 4. 边界条件：空数组、全部移除、无需移除等情况
 * 5. 性能考虑：时间复杂度O(n)，空间复杂度O(1)
 */
public class NO027_E_RemoveElement_Animation extends JFrame {
    private int[] originalArray;
    private int[] currentArray;
    private int targetValue;
    private int leftPointer;
    private int rightPointer;
    private int validLength;
    private boolean isAnimating;
    private Timer animationTimer;
    private String currentOperation;
    
    // UI组件
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JTextField arrayInput;
    private JTextField valueInput;
    private JButton startButton, stepButton, resetButton, autoButton, homeButton;
    private JLabel statusLabel;
    private JLabel resultLabel;
    
    // 动画步骤记录
    private List<AnimationStep> steps;
    private int currentStepIndex;
    
    private static class AnimationStep {
        int[] array;
        int left;
        int right;
        int target;
        String operation;
        String description;
        boolean isComparison;
        boolean isAssignment;
        
        AnimationStep(int[] array, int left, int right, int target, String operation, 
                     String description, boolean isComparison, boolean isAssignment) {
            this.array = Arrays.copyOf(array, array.length);
            this.left = left;
            this.right = right;
            this.target = target;
            this.operation = operation;
            this.description = description;
            this.isComparison = isComparison;
            this.isAssignment = isAssignment;
        }
    }
    
    public NO027_E_RemoveElement_Animation() {
        setTitle("NO.027 移除元素 - 动画演示");
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
        originalArray = new int[]{3, 2, 2, 3};
        currentArray = Arrays.copyOf(originalArray, originalArray.length);
        targetValue = 3;
        leftPointer = 0;
        rightPointer = 0;
        validLength = 0;
        isAnimating = false;
        currentOperation = "准备开始";
        steps = new ArrayList<>();
        currentStepIndex = 0;
    }
    
    private void initComponents() {
        mainPanel = new JPanel();
        controlPanel = new JPanel();
        
        arrayInput = new JTextField("3,2,2,3", 15);
        valueInput = new JTextField("3", 5);
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
        mainPanel.add(new RemoveElementVisualizationPanel(), BorderLayout.CENTER);
        
        // 控制面板
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        controlPanel.add(new JLabel("数组(逗号分隔):"), gbc);
        gbc.gridx = 1;
        controlPanel.add(arrayInput, gbc);
        
        gbc.gridx = 2; gbc.gridy = 0;
        controlPanel.add(new JLabel("移除值:"), gbc);
        gbc.gridx = 3;
        controlPanel.add(valueInput, gbc);
        

        
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
                    com.animation.launcher.AlgorithmTreeLauncher.showMainWindow();
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
            currentOperation = "开始移除元素";
            
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
        originalArray = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            originalArray[i] = Integer.parseInt(parts[i].trim());
        }
        currentArray = Arrays.copyOf(originalArray, originalArray.length);
        targetValue = Integer.parseInt(valueInput.getText().trim());
    }
    
    private void generateSteps() {
        steps.clear();
        int[] nums = Arrays.copyOf(originalArray, originalArray.length);
        int left = 0;
        
        steps.add(new AnimationStep(nums, left, 0, targetValue, "初始化", 
            "初始化左指针left=0，右指针right=0", false, false));
        
        for (int right = 0; right < nums.length; right++) {
            // 比较步骤
            steps.add(new AnimationStep(nums, left, right, targetValue, "比较", 
                String.format("比较 nums[%d]=%d 与 target=%d", right, nums[right], targetValue), 
                true, false));
            
            if (nums[right] != targetValue) {
                // 赋值步骤
                nums[left] = nums[right];
                steps.add(new AnimationStep(nums, left, right, targetValue, "赋值", 
                    String.format("nums[%d] != %d，将nums[%d]=%d赋值给nums[%d]", 
                    right, targetValue, right, nums[right], left), false, true));
                left++;
                
                // 左指针移动
                steps.add(new AnimationStep(nums, left, right, targetValue, "移动", 
                    String.format("左指针left移动到%d", left), false, false));
            } else {
                // 跳过步骤
                steps.add(new AnimationStep(nums, left, right, targetValue, "跳过", 
                    String.format("nums[%d] == %d，跳过该元素", right, targetValue), false, false));
            }
        }
        
        // 结果步骤
        steps.add(new AnimationStep(nums, left, nums.length, targetValue, "完成", 
            String.format("算法完成，新数组长度为%d", left), false, false));
    }
    
    private void stepExecution() {
        if (currentStepIndex >= steps.size()) {
            // 动画结束
            isAnimating = false;
            animationTimer.stop();
            autoButton.setText("自动演示");
            stepButton.setEnabled(false);
            
            resultLabel.setText("结果: 新数组长度为 " + leftPointer);
            return;
        }
        
        AnimationStep step = steps.get(currentStepIndex);
        currentArray = Arrays.copyOf(step.array, step.array.length);
        leftPointer = step.left;
        rightPointer = step.right;
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
        currentArray = Arrays.copyOf(originalArray, originalArray.length);
        leftPointer = 0;
        rightPointer = 0;
        
        startButton.setEnabled(true);
        stepButton.setEnabled(false);
        autoButton.setEnabled(false);
        autoButton.setText("自动演示");
        
        statusLabel.setText("状态: 准备开始");
        resultLabel.setText("结果: 未开始");
        
        repaint();
    }
    
    // 可视化面板
    private class RemoveElementVisualizationPanel extends JPanel {
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
            String title = "移除元素算法可视化 (双指针)";
            FontMetrics fm = g2d.getFontMetrics();
            int titleX = (width - fm.stringWidth(title)) / 2;
            g2d.drawString(title, titleX, 40);
            
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
            if (currentArray == null) return;
            
            int cellSize = 60;
            int startX = (width - currentArray.length * cellSize) / 2;
            int arrayY = 120;
            
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 18));
            
            for (int i = 0; i < currentArray.length; i++) {
                int x = startX + i * cellSize;
                
                // 确定颜色
                Color cellColor = Color.WHITE;
                Color textColor = Color.BLACK;
                
                if (currentStepIndex > 0 && !steps.isEmpty()) {
                    AnimationStep currentStep = steps.get(Math.min(currentStepIndex - 1, steps.size() - 1));
                    
                    if (i == rightPointer && currentStep.isComparison) {
                        cellColor = Color.YELLOW; // 当前比较的元素
                    } else if (i == leftPointer && currentStep.isAssignment) {
                        cellColor = Color.LIGHT_GRAY; // 被赋值的位置
                    } else if (i < leftPointer) {
                        cellColor = new Color(144, 238, 144); // 有效区域 (浅绿色)
                    } else if (currentArray[i] == targetValue) {
                        cellColor = Color.PINK; // 需要移除的元素
                        textColor = Color.RED;
                    }
                }
                
                // 绘制单元格
                g2d.setColor(cellColor);
                g2d.fillRect(x, arrayY, cellSize, cellSize);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, arrayY, cellSize, cellSize);
                
                // 绘制数值
                g2d.setColor(textColor);
                String value = String.valueOf(currentArray[i]);
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
            if (currentArray == null || currentStepIndex == 0) return;
            
            int cellSize = 60;
            int startX = (width - currentArray.length * cellSize) / 2;
            int arrayY = 120;
            
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            
            // 绘制左指针
            if (leftPointer < currentArray.length) {
                int leftX = startX + leftPointer * cellSize + cellSize / 2;
                g2d.setColor(Color.BLUE);
                g2d.fillPolygon(new int[]{leftX - 10, leftX + 10, leftX}, 
                               new int[]{arrayY - 30, arrayY - 30, arrayY - 15}, 3);
                g2d.drawString("left", leftX - 15, arrayY - 35);
            }
            
            // 绘制右指针
            if (rightPointer < currentArray.length) {
                int rightX = startX + rightPointer * cellSize + cellSize / 2;
                g2d.setColor(Color.RED);
                g2d.fillPolygon(new int[]{rightX - 10, rightX + 10, rightX}, 
                               new int[]{arrayY + cellSize + 30, arrayY + cellSize + 30, arrayY + cellSize + 15}, 3);
                g2d.drawString("right", rightX - 20, arrayY + cellSize + 45);
            }
        }
        
        private void drawAlgorithmExplanation(Graphics2D g2d, int width, int height) {
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            g2d.setColor(Color.BLUE);
            
            int x = 50;
            int y = 250;
            
            g2d.drawString("算法原理:", x, y);
            y += 25;
            g2d.drawString("1. 使用双指针技术", x, y);
            y += 20;
            g2d.drawString("2. left指针记录有效元素的位置", x, y);
            y += 20;
            g2d.drawString("3. right指针遍历整个数组", x, y);
            y += 20;
            g2d.drawString("4. 当nums[right] != val时，", x, y);
            y += 20;
            g2d.drawString("   将nums[right]赋值给nums[left]", x, y);
            y += 20;
            g2d.drawString("5. left指针向前移动", x, y);
            y += 20;
            g2d.drawString("6. 最终left的值就是新数组的长度", x, y);
        }
        
        private void drawStepExplanation(Graphics2D g2d, int width, int height) {
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            g2d.setColor(Color.BLACK);
            
            int x = width - 400;
            int y = 250;
            
            g2d.drawString("当前步骤:", x, y);
            y += 25;
            
            // 显示最近几个步骤
            int startStep = Math.max(0, currentStepIndex - 5);
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
            new NO027_E_RemoveElement_Animation().setVisible(true);
        });
    }
}