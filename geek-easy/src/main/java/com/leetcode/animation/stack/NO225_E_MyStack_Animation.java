package com.leetcode.animation.stack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

/**
 * NO.225 用队列实现栈 - 动画演示
 * 
 * 算法思路：
 * 1. 使用两个队列实现栈的LIFO特性
 * 2. push操作：直接加入主队列
 * 3. pop操作：将主队列除最后一个元素外的所有元素移到辅助队列，然后交换两个队列
 * 4. top操作：类似pop，但不删除元素
 * 
 * 时间复杂度：push O(1), pop O(n), top O(n), empty O(1)
 * 空间复杂度：O(n)，用于存储栈元素
 */
public class NO225_E_MyStack_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final Color BACKGROUND_COLOR = new Color(240, 248, 255);
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private static final Color HIGHLIGHT_COLOR = new Color(255, 69, 0);
    private static final Color SUCCESS_COLOR = new Color(34, 139, 34);
    private static final Color QUEUE1_COLOR = new Color(135, 206, 250);
    private static final Color QUEUE2_COLOR = new Color(255, 182, 193);
    private static final Color STACK_COLOR = new Color(144, 238, 144);
    
    private JPanel animationPanel;
    private JButton pushButton, popButton, topButton, emptyButton, resetButton;
    private JTextField inputField;
    private Timer animationTimer;
    
    // 栈实现的状态变量
    private Queue<Integer> queue1;
    private Queue<Integer> queue2;
    private boolean isAnimating;
    private String currentOperation;
    private int operationStep;
    private Integer operationValue;
    private Integer resultValue;
    private String operationResult;
    
    public NO225_E_MyStack_Animation() {
        initializeComponents();
        setupLayout();
        setupEventListeners();
        resetStack();
    }
    
    private void initializeComponents() {
        setTitle("NO.225 用队列实现栈 - 动画演示");
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
        inputField = new JTextField("5", 8);
        inputPanel.add(new JLabel("输入值:"));
        inputPanel.add(inputField);
        
        // 控制按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(BACKGROUND_COLOR);
        pushButton = new JButton("Push");
        popButton = new JButton("Pop");
        topButton = new JButton("Top");
        emptyButton = new JButton("Empty");
        resetButton = new JButton("重置");
        
        // 返回首页按钮
        JButton homeButton = new JButton("返回首页");
        homeButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                try {
                    com.leetcode.animation.AlgorithmTreeLauncher.showMainWindow();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });
        
        buttonPanel.add(pushButton);
        buttonPanel.add(popButton);
        buttonPanel.add(topButton);
        buttonPanel.add(emptyButton);
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
        pushButton.addActionListener(e -> performPush());
        popButton.addActionListener(e -> performPop());
        topButton.addActionListener(e -> performTop());
        emptyButton.addActionListener(e -> performEmpty());
        resetButton.addActionListener(e -> resetStack());
        
        // 动画定时器
        animationTimer = new Timer(1000, e -> {
            if (isAnimating) {
                nextAnimationStep();
            }
        });
    }
    
    private void performPush() {
        if (isAnimating) return;
        
        try {
            int value = Integer.parseInt(inputField.getText().trim());
            currentOperation = "PUSH";
            operationValue = value;
            operationStep = 0;
            isAnimating = true;
            setButtonsEnabled(false);
            animationTimer.start();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "请输入有效的整数！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void performPop() {
        if (isAnimating) return;
        
        if (isEmpty()) {
            JOptionPane.showMessageDialog(this, "栈为空，无法执行pop操作！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        currentOperation = "POP";
        operationStep = 0;
        isAnimating = true;
        setButtonsEnabled(false);
        animationTimer.start();
    }
    
    private void performTop() {
        if (isAnimating) return;
        
        if (isEmpty()) {
            JOptionPane.showMessageDialog(this, "栈为空，无法执行top操作！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        currentOperation = "TOP";
        operationStep = 0;
        isAnimating = true;
        setButtonsEnabled(false);
        animationTimer.start();
    }
    
    private void performEmpty() {
        if (isAnimating) return;
        
        currentOperation = "EMPTY";
        operationResult = isEmpty() ? "true" : "false";
        JOptionPane.showMessageDialog(this, "栈是否为空: " + operationResult, "结果", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void nextAnimationStep() {
        switch (currentOperation) {
            case "PUSH":
                animatePush();
                break;
            case "POP":
                animatePop();
                break;
            case "TOP":
                animateTop();
                break;
        }
        animationPanel.repaint();
    }
    
    private void animatePush() {
        if (operationStep == 0) {
            // 步骤1：将元素添加到主队列
            queue1.offer(operationValue);
            operationResult = "元素 " + operationValue + " 已添加到队列1";
            operationStep++;
        } else {
            // 动画完成
            finishAnimation();
        }
    }
    
    private void animatePop() {
        if (operationStep == 0) {
            // 步骤1：将除最后一个元素外的所有元素移到队列2
            int size = queue1.size();
            for (int i = 0; i < size - 1; i++) {
                queue2.offer(queue1.poll());
            }
            operationResult = "将前 " + (size - 1) + " 个元素移到队列2";
            operationStep++;
        } else if (operationStep == 1) {
            // 步骤2：取出最后一个元素（栈顶元素）
            resultValue = queue1.poll();
            operationResult = "取出栈顶元素: " + resultValue;
            operationStep++;
        } else if (operationStep == 2) {
            // 步骤3：交换两个队列
            Queue<Integer> temp = queue1;
            queue1 = queue2;
            queue2 = temp;
            operationResult = "交换队列，队列2成为新的主队列";
            operationStep++;
        } else {
            // 动画完成
            finishAnimation();
            JOptionPane.showMessageDialog(this, "Pop结果: " + resultValue, "结果", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void animateTop() {
        if (operationStep == 0) {
            // 步骤1：将除最后一个元素外的所有元素移到队列2
            int size = queue1.size();
            for (int i = 0; i < size - 1; i++) {
                queue2.offer(queue1.poll());
            }
            operationResult = "将前 " + (size - 1) + " 个元素移到队列2";
            operationStep++;
        } else if (operationStep == 1) {
            // 步骤2：查看最后一个元素（栈顶元素）
            resultValue = queue1.peek();
            operationResult = "查看栈顶元素: " + resultValue;
            operationStep++;
        } else if (operationStep == 2) {
            // 步骤3：将栈顶元素也移到队列2
            queue2.offer(queue1.poll());
            operationResult = "将栈顶元素也移到队列2";
            operationStep++;
        } else if (operationStep == 3) {
            // 步骤4：交换两个队列
            Queue<Integer> temp = queue1;
            queue1 = queue2;
            queue2 = temp;
            operationResult = "交换队列，恢复原始状态";
            operationStep++;
        } else {
            // 动画完成
            finishAnimation();
            JOptionPane.showMessageDialog(this, "Top结果: " + resultValue, "结果", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void finishAnimation() {
        isAnimating = false;
        animationTimer.stop();
        setButtonsEnabled(true);
        currentOperation = "";
        operationStep = 0;
        operationValue = null;
        resultValue = null;
        operationResult = "";
    }
    
    private void setButtonsEnabled(boolean enabled) {
        pushButton.setEnabled(enabled);
        popButton.setEnabled(enabled);
        topButton.setEnabled(enabled);
        emptyButton.setEnabled(enabled);
    }
    
    private boolean isEmpty() {
        return queue1.isEmpty();
    }
    
    private void resetStack() {
        queue1 = new LinkedList<>();
        queue2 = new LinkedList<>();
        currentOperation = "";
        operationStep = 0;
        operationValue = null;
        resultValue = null;
        operationResult = "";
        isAnimating = false;
        
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
        String title = "用队列实现栈算法演示";
        FontMetrics fm = g2d.getFontMetrics();
        int titleX = (WINDOW_WIDTH - fm.stringWidth(title)) / 2;
        g2d.drawString(title, titleX, 40);
        
        // 绘制算法说明
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawString("算法原理：使用两个队列模拟栈的LIFO特性，通过队列间的元素转移实现栈操作", 50, 70);
        
        // 绘制栈的逻辑视图
        drawStackView(g2d);
        
        // 绘制队列1
        drawQueue(g2d, queue1, "队列1 (主队列)", 50, 200, QUEUE1_COLOR);
        
        // 绘制队列2
        drawQueue(g2d, queue2, "队列2 (辅助队列)", 50, 320, QUEUE2_COLOR);
        
        // 绘制当前操作信息
        drawOperationInfo(g2d);
        
        // 绘制算法复杂度信息
        drawComplexityInfo(g2d);
    }
    
    private void drawStackView(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("栈的逻辑视图 (LIFO):", 700, 130);
        
        if (queue1.isEmpty()) {
            g2d.setFont(new Font("微软雅黑", Font.ITALIC, 14));
            g2d.setColor(Color.GRAY);
            g2d.drawString("栈为空", 700, 160);
            return;
        }
        
        // 将队列转换为数组以便显示
        Integer[] stackArray = queue1.toArray(new Integer[0]);
        
        int cellWidth = 60;
        int cellHeight = 40;
        int startX = 700;
        int startY = 150;
        
        // 从上到下绘制栈元素（栈顶在上）
        for (int i = stackArray.length - 1; i >= 0; i--) {
            int y = startY + (stackArray.length - 1 - i) * (cellHeight + 5);
            
            // 设置颜色
            Color cellColor = STACK_COLOR;
            if (i == stackArray.length - 1) { // 栈顶元素
                cellColor = HIGHLIGHT_COLOR;
            }
            
            // 绘制栈元素
            g2d.setColor(cellColor);
            g2d.fillRoundRect(startX, y, cellWidth, cellHeight, 8, 8);
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(startX, y, cellWidth, cellHeight, 8, 8);
            
            // 绘制数值
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            String text = String.valueOf(stackArray[i]);
            FontMetrics fm = g2d.getFontMetrics();
            int textX = startX + (cellWidth - fm.stringWidth(text)) / 2;
            int textY = y + (cellHeight + fm.getAscent()) / 2;
            g2d.drawString(text, textX, textY);
            
            // 标记栈顶
            if (i == stackArray.length - 1) {
                g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                g2d.setColor(Color.RED);
                g2d.drawString("TOP", startX + cellWidth + 10, y + 25);
            }
        }
    }
    
    private void drawQueue(Graphics2D g2d, Queue<Integer> queue, String title, int startX, int startY, Color baseColor) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString(title, startX, startY);
        
        if (queue.isEmpty()) {
            g2d.setFont(new Font("微软雅黑", Font.ITALIC, 14));
            g2d.setColor(Color.GRAY);
            g2d.drawString("队列为空", startX, startY + 30);
            return;
        }
        
        // 将队列转换为数组以便显示
        Integer[] queueArray = queue.toArray(new Integer[0]);
        
        int cellWidth = 50;
        int cellHeight = 40;
        int queueY = startY + 20;
        
        // 绘制队列头标记
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        g2d.setColor(Color.BLUE);
        g2d.drawString("HEAD", startX, queueY - 5);
        
        for (int i = 0; i < queueArray.length; i++) {
            int x = startX + i * (cellWidth + 10);
            
            // 绘制队列元素
            g2d.setColor(baseColor);
            g2d.fillRoundRect(x, queueY, cellWidth, cellHeight, 8, 8);
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(x, queueY, cellWidth, cellHeight, 8, 8);
            
            // 绘制数值
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            String text = String.valueOf(queueArray[i]);
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (cellWidth - fm.stringWidth(text)) / 2;
            int textY = queueY + (cellHeight + fm.getAscent()) / 2;
            g2d.drawString(text, textX, textY);
        }
        
        // 绘制队列尾标记
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        g2d.setColor(Color.BLUE);
        int tailX = startX + queueArray.length * (cellWidth + 10);
        g2d.drawString("TAIL", tailX, queueY + cellHeight + 15);
    }
    
    private void drawOperationInfo(Graphics2D g2d) {
        int startY = 450;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("当前操作:", 50, startY);
        
        if (!currentOperation.isEmpty()) {
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 16));
            g2d.setColor(Color.BLACK);
            g2d.drawString("操作类型: " + currentOperation, 70, startY + 25);
            
            if (operationValue != null) {
                g2d.drawString("操作值: " + operationValue, 70, startY + 45);
            }
            
            if (!operationResult.isEmpty()) {
                g2d.setColor(HIGHLIGHT_COLOR);
                g2d.drawString("步骤: " + operationResult, 70, startY + 65);
            }
        } else {
            g2d.setFont(new Font("微软雅黑", Font.ITALIC, 14));
            g2d.setColor(Color.GRAY);
            g2d.drawString("请选择操作", 70, startY + 25);
        }
    }
    
    private void drawComplexityInfo(Graphics2D g2d) {
        int startY = 550;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("算法复杂度分析:", 50, startY);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("• Push操作: O(1) - 直接添加到队列尾部", 70, startY + 25);
        g2d.drawString("• Pop操作: O(n) - 需要移动n-1个元素", 70, startY + 45);
        g2d.drawString("• Top操作: O(n) - 需要移动所有元素后恢复", 70, startY + 65);
        g2d.drawString("• Empty操作: O(1) - 直接检查队列是否为空", 70, startY + 85);
        g2d.drawString("• 空间复杂度: O(n) - 存储n个栈元素", 70, startY + 105);
        
        // 显示栈状态
        g2d.setColor(SUCCESS_COLOR);
        String status = "栈大小: " + queue1.size() + " | 栈顶: " + (queue1.isEmpty() ? "无" : queue1.toArray()[queue1.size() - 1]);
        g2d.drawString(status, 70, startY + 130);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO225_E_MyStack_Animation().setVisible(true);
        });
    }
}