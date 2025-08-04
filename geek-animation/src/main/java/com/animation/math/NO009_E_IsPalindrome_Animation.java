package com.animation.math;

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
import java.util.List;
import com.animation.launcher.AlgorithmTreeLauncher;

/**
 * NO.009 回文数 - 动画演示
 * 演示判断整数是否为回文数的算法过程
 * 
 * 设计文档：
 * 1. 功能需求：可视化回文数判断算法的执行过程
 * 2. 技术架构：基于Swing的动画演示系统
 * 3. 核心算法：反转一半数字进行比较
 * 4. 边界条件：负数、末尾为0的数字、单位数等情况
 * 5. 性能考虑：时间复杂度O(log n)，空间复杂度O(1)
 */
public class NO009_E_IsPalindrome_Animation extends JFrame {
    private int originalNumber;
    private int currentNumber;
    private int reversedNumber;
    private int step;
    private boolean isAnimating;
    private Timer animationTimer;
    private boolean isPalindrome;
    private String currentOperation;
    
    // UI组件
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JTextField numberInput;
    private JButton startButton, stepButton, resetButton, autoButton, homeButton;
    private JLabel statusLabel;
    private JLabel resultLabel;
    
    // 动画步骤记录
    private List<AnimationStep> steps;
    private int currentStepIndex;
    
    private static class AnimationStep {
        int originalNum;
        int currentNum;
        int reversedNum;
        String operation;
        String description;
        
        AnimationStep(int originalNum, int currentNum, int reversedNum, String operation, String description) {
            this.originalNum = originalNum;
            this.currentNum = currentNum;
            this.reversedNum = reversedNum;
            this.operation = operation;
            this.description = description;
        }
    }
    
    public NO009_E_IsPalindrome_Animation() {
        setTitle("NO.009 回文数 - 动画演示");
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
        originalNumber = 121;
        currentNumber = 121;
        reversedNumber = 0;
        step = 0;
        isAnimating = false;
        isPalindrome = false;
        currentOperation = "准备开始";
        steps = new ArrayList<>();
        currentStepIndex = 0;
    }
    
    private void initComponents() {
        mainPanel = new JPanel();
        controlPanel = new JPanel();
        
        numberInput = new JTextField("121", 10);
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
        mainPanel.add(new PalindromeVisualizationPanel(), BorderLayout.CENTER);
        
        // 控制面板
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        controlPanel.add(new JLabel("输入数字:"), gbc);
        gbc.gridx = 1;
        controlPanel.add(numberInput, gbc);
        

        
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
            originalNumber = Integer.parseInt(numberInput.getText().trim());
            
            // 生成算法步骤
            generateSteps();
            
            // 重置状态
            currentStepIndex = 0;
            currentOperation = "开始判断回文数";
            
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
    
    private void generateSteps() {
        steps.clear();
        int x = originalNumber;
        int reversed = 0;
        
        // 特殊情况检查
        if (x < 0) {
            steps.add(new AnimationStep(originalNumber, x, reversed, "检查负数", "负数不是回文数"));
            return;
        }
        
        if (x != 0 && x % 10 == 0) {
            steps.add(new AnimationStep(originalNumber, x, reversed, "检查末尾0", "末尾为0的数字(除0外)不是回文数"));
            return;
        }
        
        steps.add(new AnimationStep(originalNumber, x, reversed, "开始", "开始反转数字的一半"));
        
        // 反转一半数字
        while (x > reversed) {
            int digit = x % 10;
            reversed = reversed * 10 + digit;
            x /= 10;
            steps.add(new AnimationStep(originalNumber, x, reversed, 
                "反转", String.format("取出末位数字%d，反转数字变为%d，剩余数字为%d", digit, reversed, x)));
        }
        
        // 判断结果
        boolean result = (x == reversed) || (x == reversed / 10);
        String explanation = result ? 
            (x == reversed ? "剩余数字等于反转数字，是回文数" : "剩余数字等于反转数字除以10，是回文数(奇数位)") :
            "剩余数字不等于反转数字，不是回文数";
        steps.add(new AnimationStep(originalNumber, x, reversed, "结果", explanation));
    }
    
    private void stepExecution() {
        if (currentStepIndex >= steps.size()) {
            // 动画结束
            isAnimating = false;
            animationTimer.stop();
            autoButton.setText("自动演示");
            stepButton.setEnabled(false);
            
            AnimationStep lastStep = steps.get(steps.size() - 1);
            boolean result = lastStep.operation.equals("结果") && 
                (lastStep.description.contains("是回文数"));
            resultLabel.setText("结果: " + (result ? "是回文数" : "不是回文数"));
            return;
        }
        
        AnimationStep step = steps.get(currentStepIndex);
        currentNumber = step.currentNum;
        reversedNumber = step.reversedNum;
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
        
        startButton.setEnabled(true);
        stepButton.setEnabled(false);
        autoButton.setEnabled(false);
        autoButton.setText("自动演示");
        
        statusLabel.setText("状态: 准备开始");
        resultLabel.setText("结果: 未开始");
        
        repaint();
    }
    
    // 可视化面板
    private class PalindromeVisualizationPanel extends JPanel {
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
            String title = "回文数判断算法可视化";
            FontMetrics fm = g2d.getFontMetrics();
            int titleX = (width - fm.stringWidth(title)) / 2;
            g2d.drawString(title, titleX, 40);
            
            // 绘制原始数字
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 20));
            g2d.setColor(Color.BLUE);
            String originalText = "原始数字: " + originalNumber;
            g2d.drawString(originalText, 50, 100);
            
            if (currentStepIndex > 0 && !steps.isEmpty()) {
                // 绘制当前状态
                g2d.setFont(new Font("微软雅黑", Font.PLAIN, 18));
                
                // 当前剩余数字
                g2d.setColor(Color.RED);
                String currentText = "剩余数字: " + currentNumber;
                g2d.drawString(currentText, 50, 150);
                
                // 反转数字
                g2d.setColor(Color.GREEN);
                String reversedText = "反转数字: " + reversedNumber;
                g2d.drawString(reversedText, 50, 180);
                
                // 绘制数字分解过程
                drawNumberDecomposition(g2d, width, height);
                
                // 绘制算法说明
                drawAlgorithmExplanation(g2d, width, height);
            }
        }
        
        private void drawNumberDecomposition(Graphics2D g2d, int width, int height) {
            if (currentStepIndex == 0) return;
            
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 16));
            g2d.setColor(Color.BLACK);
            
            int y = 250;
            g2d.drawString("算法步骤:", 50, y);
            
            // 显示已执行的步骤
            for (int i = 0; i < Math.min(currentStepIndex, steps.size()); i++) {
                AnimationStep step = steps.get(i);
                y += 25;
                
                if (i == currentStepIndex - 1) {
                    g2d.setColor(Color.RED);
                    g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
                } else {
                    g2d.setColor(Color.GRAY);
                    g2d.setFont(new Font("微软雅黑", Font.PLAIN, 16));
                }
                
                String stepText = String.format("%d. %s", i + 1, step.description);
                g2d.drawString(stepText, 70, y);
            }
        }
        
        private void drawAlgorithmExplanation(Graphics2D g2d, int width, int height) {
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            g2d.setColor(Color.BLUE);
            
            int x = width - 350;
            int y = 120;
            
            g2d.drawString("算法原理:", x, y);
            y += 25;
            g2d.drawString("1. 负数不是回文数", x, y);
            y += 20;
            g2d.drawString("2. 末尾为0的数字(除0外)不是回文数", x, y);
            y += 20;
            g2d.drawString("3. 只需反转数字的一半进行比较", x, y);
            y += 20;
            g2d.drawString("4. 当 x <= reversed 时停止", x, y);
            y += 20;
            g2d.drawString("5. 比较 x == reversed 或", x, y);
            y += 20;
            g2d.drawString("   x == reversed/10 (奇数位)", x, y);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO009_E_IsPalindrome_Animation().setVisible(true);
        });
    }
}