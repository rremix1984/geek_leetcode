package com.animation.interval;

import javax.swing.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 面试题 08.09. 括号 - 动画演示
 * 演示生成所有有效括号组合的过程
 * 
 * 设计文档：
 * 1. 功能需求：可视化括号生成算法的执行过程
 * 2. 技术架构：基于Swing的动画演示系统
 * 3. 核心算法：回溯法生成有效括号组合
 * 4. 边界条件：n=0、n=1等特殊情况
 * 5. 性能考虑：时间复杂度O(4^n/√n)，空间复杂度O(n)
 */
public class Interval_08_09_N_GenerateParenthesis_Animation extends JFrame {
    private int n;
    private boolean isAnimating;
    private Timer animationTimer;
    private List<GenerationStep> steps;
    private int currentStep;
    
    // UI组件
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JTextField nInput;
    private JButton startButton, stepButton, resetButton, autoButton, homeButton;
    private JLabel statusLabel;
    private JLabel resultLabel;
    
    // 动画状态
    private List<String> results;
    private String currentCombination;
    private int openCount;
    private int closeCount;
    private String currentOperation;
    private boolean isBacktracking;
    
    // 字符动画相关
    private int charAnimationIndex;
    private Timer charAnimationTimer;
    private String targetCombination;
    private boolean isCharAnimating;
    
    // 生成步骤类
    private static class GenerationStep {
        String combination;
        int open;
        int close;
        String operation;
        boolean isBacktrack;
        List<String> currentResults;
        
        GenerationStep(String combination, int open, int close, String operation, boolean isBacktrack, List<String> results) {
            this.combination = combination;
            this.open = open;
            this.close = close;
            this.operation = operation;
            this.isBacktrack = isBacktrack;
            this.currentResults = new ArrayList<>(results);
        }
    }
    
    public Interval_08_09_N_GenerateParenthesis_Animation() {
        setTitle("面试题 08.09. 括号 - 动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        
        initData();
        initComponents();
        setupLayout();
        setupEventHandlers();
        setupAnimation();
    }
    
    private void initData() {
        n = 3;
        isAnimating = false;
        steps = new ArrayList<>();
        currentStep = 0;
        results = new ArrayList<>();
        currentCombination = "";
        openCount = 0;
        closeCount = 0;
        currentOperation = "准备开始";
        isBacktracking = false;
    }
    
    private void initComponents() {
        controlPanel = new JPanel();
        
        nInput = new JTextField("3", 5);
        startButton = new JButton("开始演示");
        stepButton = new JButton("单步执行");
        resetButton = new JButton("重置");
        autoButton = new JButton("自动演示");
        homeButton = new JButton("返回主页");
        statusLabel = new JLabel("状态: 准备开始");
        resultLabel = new JLabel("结果: 未开始");
        
        // 设置字体
        Font font = new Font("微软雅黑", Font.PLAIN, 14);
        statusLabel.setFont(font);
        resultLabel.setFont(font);
        
        stepButton.setEnabled(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 控制面板
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JLabel("括号对数 n:"));
        controlPanel.add(nInput);
        controlPanel.add(startButton);
        controlPanel.add(stepButton);
        controlPanel.add(autoButton);
        controlPanel.add(resetButton);
        controlPanel.add(homeButton);
        
        // 状态面板
        JPanel statusPanel = new JPanel(new GridLayout(2, 1));
        statusPanel.add(statusLabel);
        statusPanel.add(resultLabel);
        
        // 主面板
        mainPanel = new ParenthesisVisualizationPanel();
        
        add(controlPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        startButton.addActionListener(e -> startDemo());
        stepButton.addActionListener(e -> stepExecution());
        resetButton.addActionListener(e -> resetDemo());
        autoButton.addActionListener(e -> toggleAutoDemo());
        homeButton.addActionListener(e -> {
            dispose();
            AlgorithmTreeLauncher.showMainWindow();
        });
    }
    
    private void setupAnimation() {
        animationTimer = new Timer(1500, e -> {
            stepExecution();
        });
        
        // 字符动画定时器
        charAnimationTimer = new Timer(200, e -> {
            if (isCharAnimating && charAnimationIndex < targetCombination.length()) {
                charAnimationIndex++;
                repaint();
            } else {
                charAnimationTimer.stop();
                isCharAnimating = false;
                // 动画完成后更新当前组合
                currentCombination = targetCombination;
                repaint();
            }
        });
    }
    
    private void startDemo() {
        parseInput();
        generateSteps();
        currentStep = 0;
        startButton.setEnabled(false);
        stepButton.setEnabled(true);
        statusLabel.setText("状态: 开始生成括号组合");
        repaint();
    }
    
    private void parseInput() {
        try {
            n = Integer.parseInt(nInput.getText().trim());
            if (n < 1 || n > 4) {
                JOptionPane.showMessageDialog(this, "请输入1-4之间的数字");
                n = 3;
                nInput.setText("3");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "输入格式错误，使用默认值3");
            n = 3;
            nInput.setText("3");
        }
    }
    
    private void generateSteps() {
        steps.clear();
        results.clear();
        generateParenthesis("", 0, 0, n, results);
    }
    
    private void generateParenthesis(String current, int open, int close, int max, List<String> result) {
        // 记录当前状态
        steps.add(new GenerationStep(current, open, close, 
            String.format("当前组合: '%s', 左括号: %d, 右括号: %d", current, open, close), 
            false, result));
        
        // 基础情况：达到最大长度
        if (current.length() == max * 2) {
            result.add(current);
            steps.add(new GenerationStep(current, open, close, 
                "找到有效组合: " + current, false, result));
            return;
        }
        
        // 可以添加左括号
        if (open < max) {
            steps.add(new GenerationStep(current, open, close, 
                "可以添加左括号 '('", false, result));
            generateParenthesis(current + "(", open + 1, close, max, result);
            
            // 回溯
            steps.add(new GenerationStep(current, open, close, 
                "回溯到: '" + current + "'", true, result));
        }
        
        // 可以添加右括号
        if (close < open) {
            steps.add(new GenerationStep(current, open, close, 
                "可以添加右括号 ')'", false, result));
            generateParenthesis(current + ")", open, close + 1, max, result);
            
            // 回溯
            steps.add(new GenerationStep(current, open, close, 
                "回溯到: '" + current + "'", true, result));
        }
    }
    
    private void stepExecution() {
        if (currentStep < steps.size()) {
            GenerationStep step = steps.get(currentStep);
            
            // 启动字符动画
            targetCombination = step.combination;
            if (!targetCombination.equals(currentCombination)) {
                startCharacterAnimation();
            }
            
            openCount = step.open;
            closeCount = step.close;
            currentOperation = step.operation;
            isBacktracking = step.isBacktrack;
            results = new ArrayList<>(step.currentResults);
            
            statusLabel.setText("状态: " + currentOperation);
            
            if (currentStep == steps.size() - 1) {
                // 最后一步，显示最终结果
                resultLabel.setText("结果: 共生成 " + results.size() + " 个有效组合");
                stepButton.setEnabled(false);
                if (animationTimer.isRunning()) {
                    animationTimer.stop();
                    autoButton.setText("自动演示");
                }
            }
            
            currentStep++;
            repaint();
        }
    }
    
    private void startCharacterAnimation() {
        if (targetCombination.length() > currentCombination.length()) {
            // 添加字符的动画
            charAnimationIndex = currentCombination.length();
            isCharAnimating = true;
            charAnimationTimer.start();
        } else if (targetCombination.length() < currentCombination.length()) {
            // 回溯删除字符的动画
            charAnimationIndex = targetCombination.length();
            currentCombination = targetCombination;
            isCharAnimating = false;
        } else {
            // 长度相同，直接更新
            currentCombination = targetCombination;
            isCharAnimating = false;
        }
    }
    
    private void toggleAutoDemo() {
        if (animationTimer.isRunning()) {
            animationTimer.stop();
            autoButton.setText("自动演示");
        } else {
            animationTimer.start();
            autoButton.setText("暂停演示");
        }
    }
    
    private void resetDemo() {
        animationTimer.stop();
        charAnimationTimer.stop();
        initData();
        currentStep = 0;
        steps.clear();
        results.clear();
        
        // 重置字符动画相关变量
        charAnimationIndex = 0;
        targetCombination = "";
        isCharAnimating = false;
        
        startButton.setEnabled(true);
        stepButton.setEnabled(false);
        autoButton.setText("自动演示");
        statusLabel.setText("状态: 准备开始");
        resultLabel.setText("结果: 未开始");
        repaint();
    }
    
    private class ParenthesisVisualizationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            drawCurrentState(g2d);
            drawResults(g2d);
            drawAlgorithmInfo(g2d);
        }
        
        private void drawCurrentState(Graphics2D g2d) {
            // 显示当前操作
            if (currentOperation != null) {
                g2d.setColor(isBacktracking ? Color.RED : Color.BLUE);
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
                g2d.drawString("当前操作: " + currentOperation, 20, 30);
            }
            
            // 显示当前组合构建过程
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 18));
            g2d.drawString("当前构建: ", 20, 70);
            
            // 绘制当前括号组合
            int x = 120;
            g2d.setFont(new Font("Courier New", Font.BOLD, 24));
            
            // 绘制背景框
            g2d.setColor(new Color(240, 240, 240));
            g2d.fillRect(x - 5, 50, Math.max(200, currentCombination.length() * 20 + 10), 30);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x - 5, 50, Math.max(200, currentCombination.length() * 20 + 10), 30);
            
            // 显示当前组合和动画效果
            String displayCombination = currentCombination;
            if (isCharAnimating && targetCombination != null) {
                // 动画过程中显示部分字符
                displayCombination = targetCombination.substring(0, Math.min(charAnimationIndex, targetCombination.length()));
            }
            
            if (displayCombination == null || displayCombination.isEmpty()) {
                g2d.setColor(Color.GRAY);
                g2d.setFont(new Font("微软雅黑", Font.ITALIC, 16));
                g2d.drawString("(空字符串)", x, 70);
            } else {
                // 绘制括号字符
                for (int i = 0; i < displayCombination.length(); i++) {
                    char c = displayCombination.charAt(i);
                    
                    // 如果是正在添加的字符，使用特殊效果
                    if (isCharAnimating && i == charAnimationIndex - 1) {
                        // 闪烁效果
                        g2d.setColor(new Color(255, 255, 0, 150));
                        g2d.fillOval(x - 3, 52, 16, 16);
                    }
                    
                    if (c == '(') {
                        g2d.setColor(Color.BLUE);
                    } else {
                        g2d.setColor(Color.RED);
                    }
                    g2d.drawString(String.valueOf(c), x, 70);
                    x += 20;
                }
                
                // 如果正在动画，显示下一个要添加的字符的预览
                if (isCharAnimating && charAnimationIndex < targetCombination.length()) {
                    char nextChar = targetCombination.charAt(charAnimationIndex);
                    g2d.setColor(new Color(128, 128, 128, 100));
                    g2d.drawString(String.valueOf(nextChar), x, 70);
                }
            }
            
            // 显示计数器
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            g2d.drawString(String.format("左括号数: %d/%d", openCount, n), 20, 100);
            g2d.drawString(String.format("右括号数: %d/%d", closeCount, n), 150, 100);
            
            // 显示当前步骤
            g2d.drawString(String.format("步骤: %d/%d", currentStep, steps.size()), 280, 100);
            
            // 绘制括号匹配状态
            drawBracketStack(g2d);
        }
        
        private void drawBracketStack(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            g2d.drawString("括号栈状态:", 300, 70);
            
            int stackHeight = openCount - closeCount;
            int x = 300;
            int y = 90;
            
            for (int i = 0; i < stackHeight; i++) {
                g2d.setColor(Color.BLUE);
                g2d.fillRect(x + i * 25, y, 20, 20);
                g2d.setColor(Color.WHITE);
                g2d.drawString("(", x + i * 25 + 6, y + 15);
            }
            
            g2d.setColor(Color.BLACK);
            g2d.drawString("未匹配的左括号: " + stackHeight, 300, 130);
        }
        
        private void drawResults(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g2d.drawString("已生成的有效组合:", 20, 180);
            
            int x = 20;
            int y = 210;
            int maxPerRow = 6;
            
            g2d.setFont(new Font("Courier New", Font.PLAIN, 16));
            for (int i = 0; i < results.size(); i++) {
                if (i > 0 && i % maxPerRow == 0) {
                    y += 30;
                    x = 20;
                }
                
                // 高亮最新生成的组合
                if (i == results.size() - 1 && currentStep > 0) {
                    g2d.setColor(new Color(255, 255, 0, 100));
                    g2d.fillRect(x - 2, y - 18, 115, 22);
                }
                
                g2d.setColor(Color.GREEN);
                g2d.drawString(results.get(i), x, y);
                x += 120;
            }
            
            // 绘制递归决策树
            drawDecisionTree(g2d);
        }
        
        private void drawDecisionTree(Graphics2D g2d) {
            if (currentStep == 0 || steps.isEmpty()) return;
            
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            g2d.drawString("递归决策树:", 500, 180);
            
            // 绘制当前决策路径
            int treeX = 500;
            int treeY = 200;
            int levelHeight = 40;
            
            GenerationStep currentStepData = steps.get(Math.min(currentStep - 1, steps.size() - 1));
            String currentPath = currentStepData.combination;
            
            g2d.setFont(new Font("Courier New", Font.PLAIN, 12));
            
            // 绘制根节点
            g2d.setColor(Color.BLUE);
            g2d.fillOval(treeX, treeY, 20, 20);
            g2d.setColor(Color.WHITE);
            g2d.drawString("根", treeX + 25, treeY + 15);
            
            // 绘制当前路径
            int nodeX = treeX;
            int nodeY = treeY + levelHeight;
            
            for (int i = 0; i < currentPath.length(); i++) {
                char c = currentPath.charAt(i);
                
                // 绘制连接线
                g2d.setColor(Color.GRAY);
                g2d.drawLine(nodeX + 10, nodeY - levelHeight + 20, nodeX + 10, nodeY);
                
                // 绘制节点
                if (c == '(') {
                    g2d.setColor(Color.BLUE);
                } else {
                    g2d.setColor(Color.RED);
                }
                g2d.fillOval(nodeX, nodeY, 20, 20);
                
                // 绘制字符
                g2d.setColor(Color.WHITE);
                g2d.drawString(String.valueOf(c), nodeX + 6, nodeY + 15);
                
                // 绘制路径标签
                g2d.setColor(Color.BLACK);
                g2d.drawString(currentPath.substring(0, i + 1), nodeX + 25, nodeY + 15);
                
                nodeY += levelHeight;
            }
            
            // 显示可能的下一步选择
            if (currentStepData.open < n) {
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.fillOval(nodeX - 30, nodeY, 20, 20);
                g2d.setColor(Color.BLUE);
                g2d.drawString("(", nodeX - 24, nodeY + 15);
                g2d.setColor(Color.BLACK);
                g2d.drawString("可添加(", nodeX - 10, nodeY + 15);
            }
            
            if (currentStepData.close < currentStepData.open) {
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.fillOval(nodeX + 30, nodeY, 20, 20);
                g2d.setColor(Color.RED);
                g2d.drawString(")", nodeX + 36, nodeY + 15);
                g2d.setColor(Color.BLACK);
                g2d.drawString("可添加)", nodeX + 55, nodeY + 15);
            }
        }
        
        private void drawAlgorithmInfo(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            
            int y = getHeight() - 120;
            g2d.drawString("算法说明：", 20, y);
            g2d.drawString("1. 使用回溯法生成所有可能的括号组合", 20, y + 20);
            g2d.drawString("2. 在任意时刻，左括号数量不能超过n", 20, y + 40);
            g2d.drawString("3. 在任意时刻，右括号数量不能超过左括号数量", 20, y + 60);
            g2d.drawString("4. 当组合长度达到2n时，得到一个有效组合", 20, y + 80);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Interval_08_09_N_GenerateParenthesis_Animation().setVisible(true);
        });
    }
}