package com.animation.interval;

import com.animation.launcher.AlgorithmTreeLauncher;
import com.leetcode.util.ListNode;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Interval.02.05 链表求和 动画演示
 * 给定两个用链表表示的整数，每个节点包含一个数位。
 * 这些数位是反向存放的，也就是个位排在链表首部。
 * 编写函数对这两个整数求和，并用链表形式返回结果。
 */
public class Interval_02_05_N_AddTwoNumbers_Animation extends JFrame {
    private static final int PANEL_WIDTH = 1200;
    private static final int PANEL_HEIGHT = 800;
    private static final int NODE_SIZE = 60;
    private static final int NODE_SPACING = 100;
    
    private JPanel visualPanel;
    private JTextArea logArea;
    private JButton playButton, pauseButton, nextButton, resetButton;
    private JSlider speedSlider;
    private Timer animationTimer;
    private boolean isPlaying = false;
    private int currentStep = 0;
    
    // 算法相关变量
    private ListNode<Integer> l1, l2, result;
    private List<AnimationState> animationStates;
    private int carry = 0;
    private ListNode<Integer> currentL1, currentL2, currentResult, tail;
    
    public Interval_02_05_N_AddTwoNumbers_Animation() {
        initializeUI();
        initializeAlgorithm();
        generateAnimationStates();
        setupEventHandlers();
    }
    
    private void initializeUI() {
        setTitle("Interval.02.05 链表求和 - 动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(PANEL_WIDTH, PANEL_HEIGHT);
        setLocationRelativeTo(null);
        
        // 主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // 可视化面板
        visualPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawAnimation(g);
            }
        };
        visualPanel.setBackground(Color.WHITE);
        visualPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 500));
        visualPanel.setBorder(new TitledBorder("链表求和可视化"));
        
        // 控制面板
        JPanel controlPanel = createControlPanel();
        
        // 日志面板
        logArea = new JTextArea(8, 50);
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane logScrollPane = new JScrollPane(logArea);
        logScrollPane.setBorder(new TitledBorder("执行日志"));
        
        mainPanel.add(visualPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        mainPanel.add(logScrollPane, BorderLayout.EAST);
        
        add(mainPanel);
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        playButton = new JButton("播放");
        pauseButton = new JButton("暂停");
        nextButton = new JButton("下一步");
        resetButton = new JButton("重置");
        
        speedSlider = new JSlider(1, 10, 5);
        speedSlider.setMajorTickSpacing(3);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        
        panel.add(new JLabel("控制:"));
        panel.add(playButton);
        panel.add(pauseButton);
        panel.add(nextButton);
        panel.add(resetButton);
        panel.add(new JLabel("速度:"));
        panel.add(speedSlider);
        
        return panel;
    }
    
    private void initializeAlgorithm() {
        // 示例数据：(7 -> 1 -> 6) + (5 -> 9 -> 2) = (2 -> 1 -> 9)
        l1 = new ListNode<>(7, 1, 6);
        l2 = new ListNode<>(5, 9, 2);
        
        // 重置算法状态
        carry = 0;
        currentL1 = l1;
        currentL2 = l2;
        result = null;
        tail = null;
        animationStates = new ArrayList<>();
    }
    
    private void generateAnimationStates() {
        animationStates.clear();
        
        // 初始状态
        animationStates.add(new AnimationState(
            "开始链表求和",
            copyList(l1), copyList(l2), null,
            l1, l2, null, 0, 0, 0
        ));
        
        // 模拟算法执行过程
        ListNode<Integer> tempL1 = l1;
        ListNode<Integer> tempL2 = l2;
        ListNode<Integer> tempResult = null;
        ListNode<Integer> tempTail = null;
        int tempCarry = 0;
        
        while (tempL1 != null || tempL2 != null || tempCarry != 0) {
            int v1 = tempL1 != null ? tempL1.val : 0;
            int v2 = tempL2 != null ? tempL2.val : 0;
            int sum = v1 + v2 + tempCarry;
            int digit = sum % 10;
            tempCarry = sum / 10;
            
            // 创建新节点
            if (tempResult == null) {
                tempResult = new ListNode<>(digit);
                tempTail = tempResult;
            } else {
                tempTail.next = new ListNode<>(digit);
                tempTail = tempTail.next;
            }
            
            animationStates.add(new AnimationState(
                String.format("计算: %d + %d + %d = %d, 数位: %d, 进位: %d", 
                    v1, v2, tempCarry == 0 ? 0 : (sum - digit) / 10, sum, digit, tempCarry),
                copyList(l1), copyList(l2), copyList(tempResult),
                tempL1, tempL2, tempTail, v1, v2, tempCarry
            ));
            
            if (tempL1 != null) tempL1 = tempL1.next;
            if (tempL2 != null) tempL2 = tempL2.next;
        }
        
        // 完成状态
        animationStates.add(new AnimationState(
            "链表求和完成！",
            copyList(l1), copyList(l2), copyList(tempResult),
            null, null, null, 0, 0, 0
        ));
    }
    
    private ListNode<Integer> copyList(ListNode<Integer> head) {
        if (head == null) return null;
        
        ListNode<Integer> newHead = new ListNode<>(head.val);
        ListNode<Integer> current = newHead;
        ListNode<Integer> original = head.next;
        
        while (original != null) {
            current.next = new ListNode<>(original.val);
            current = current.next;
            original = original.next;
        }
        
        return newHead;
    }
    
    private void setupEventHandlers() {
        playButton.addActionListener(e -> startAnimation());
        pauseButton.addActionListener(e -> pauseAnimation());
        nextButton.addActionListener(e -> nextStep());
        resetButton.addActionListener(e -> resetAnimation());
        
        speedSlider.addChangeListener(e -> {
            if (animationTimer != null) {
                int delay = 1100 - speedSlider.getValue() * 100;
                animationTimer.setDelay(delay);
            }
        });
        
        // 窗口关闭时的处理
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (animationTimer != null) {
                    animationTimer.stop();
                }
                AlgorithmTreeLauncher.getInstance().setVisible(true);
            }
        });
    }
    
    private void startAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        
        int delay = 1100 - speedSlider.getValue() * 100;
        animationTimer = new Timer(delay, e -> nextStep());
        animationTimer.start();
        isPlaying = true;
        
        playButton.setEnabled(false);
        pauseButton.setEnabled(true);
    }
    
    private void pauseAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        isPlaying = false;
        
        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
    }
    
    private void nextStep() {
        if (currentStep < animationStates.size()) {
            AnimationState state = animationStates.get(currentStep);
            updateLog(state.description);
            visualPanel.repaint();
            currentStep++;
            
            if (currentStep >= animationStates.size()) {
                pauseAnimation();
                updateLog("动画播放完成！");
            }
        }
    }
    
    private void resetAnimation() {
        pauseAnimation();
        currentStep = 0;
        logArea.setText("");
        visualPanel.repaint();
        
        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
    }
    
    private void updateLog(String message) {
        logArea.append(String.format("[步骤 %d] %s\n", currentStep + 1, message));
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (currentStep == 0 || animationStates.isEmpty()) {
            drawInitialState(g2d);
            return;
        }
        
        if (currentStep > 0 && currentStep <= animationStates.size()) {
            AnimationState state = animationStates.get(currentStep - 1);
            drawAnimationState(g2d, state);
        }
    }
    
    private void drawInitialState(Graphics2D g2d) {
        int startY = 100;
        
        // 绘制标题
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.setColor(Color.BLACK);
        g2d.drawString("链表求和算法演示", 50, 50);
        
        // 绘制链表1
        g2d.drawString("链表1 (617):", 50, startY);
        drawLinkedList(g2d, l1, 50, startY + 30, Color.BLUE);
        
        // 绘制链表2
        g2d.drawString("链表2 (295):", 50, startY + 120);
        drawLinkedList(g2d, l2, 50, startY + 150, Color.GREEN);
        
        // 绘制结果链表占位
        g2d.drawString("结果链表:", 50, startY + 240);
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawString("等待计算...", 50, startY + 270);
    }
    
    private void drawAnimationState(Graphics2D g2d, AnimationState state) {
        int startY = 100;
        
        // 绘制标题
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.setColor(Color.BLACK);
        g2d.drawString("链表求和算法演示", 50, 50);
        
        // 绘制链表1
        g2d.drawString("链表1 (617):", 50, startY);
        drawLinkedList(g2d, state.l1, 50, startY + 30, Color.BLUE);
        if (state.currentL1 != null) {
            highlightCurrentNode(g2d, state.l1, state.currentL1, 50, startY + 30, Color.BLUE);
        }
        
        // 绘制链表2
        g2d.drawString("链表2 (295):", 50, startY + 120);
        drawLinkedList(g2d, state.l2, 50, startY + 150, Color.GREEN);
        if (state.currentL2 != null) {
            highlightCurrentNode(g2d, state.l2, state.currentL2, 50, startY + 150, Color.GREEN);
        }
        
        // 绘制结果链表
        g2d.drawString("结果链表:", 50, startY + 240);
        if (state.result != null) {
            drawLinkedList(g2d, state.result, 50, startY + 270, Color.RED);
            if (state.currentResult != null) {
                highlightCurrentNode(g2d, state.result, state.currentResult, 50, startY + 270, Color.RED);
            }
        }
        
        // 绘制计算信息
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        g2d.setColor(Color.BLACK);
        if (state.currentL1 != null || state.currentL2 != null) {
            g2d.drawString(String.format("当前计算: %d + %d + 进位%d", 
                state.val1, state.val2, state.carry), 50, startY + 350);
        }
    }
    
    private void drawLinkedList(Graphics2D g2d, ListNode<Integer> head, int startX, int startY, Color color) {
        if (head == null) return;
        
        ListNode<Integer> current = head;
        int x = startX;
        
        while (current != null) {
            // 绘制节点
            g2d.setColor(color);
            g2d.fillOval(x, startY, NODE_SIZE, NODE_SIZE);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            
            String value = current.val.toString();
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (NODE_SIZE - fm.stringWidth(value)) / 2;
            int textY = startY + (NODE_SIZE + fm.getAscent()) / 2;
            g2d.drawString(value, textX, textY);
            
            // 绘制箭头
            if (current.next != null) {
                g2d.setColor(Color.BLACK);
                int arrowStartX = x + NODE_SIZE;
                int arrowY = startY + NODE_SIZE / 2;
                int arrowEndX = x + NODE_SPACING - 10;
                
                g2d.drawLine(arrowStartX, arrowY, arrowEndX, arrowY);
                g2d.drawLine(arrowEndX - 5, arrowY - 3, arrowEndX, arrowY);
                g2d.drawLine(arrowEndX - 5, arrowY + 3, arrowEndX, arrowY);
            }
            
            current = current.next;
            x += NODE_SPACING;
        }
    }
    
    private void highlightCurrentNode(Graphics2D g2d, ListNode<Integer> head, ListNode<Integer> target, 
                                    int startX, int startY, Color baseColor) {
        if (head == null || target == null) return;
        
        ListNode<Integer> current = head;
        int x = startX;
        
        while (current != null) {
            if (current == target) {
                // 绘制高亮边框
                g2d.setColor(Color.YELLOW);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawOval(x - 2, startY - 2, NODE_SIZE + 4, NODE_SIZE + 4);
                g2d.setStroke(new BasicStroke(1));
                break;
            }
            current = current.next;
            x += NODE_SPACING;
        }
    }
    
    // 动画状态类
    private static class AnimationState {
        String description;
        ListNode<Integer> l1, l2, result;
        ListNode<Integer> currentL1, currentL2, currentResult;
        int val1, val2, carry;
        
        AnimationState(String description, ListNode<Integer> l1, ListNode<Integer> l2, 
                      ListNode<Integer> result, ListNode<Integer> currentL1, 
                      ListNode<Integer> currentL2, ListNode<Integer> currentResult,
                      int val1, int val2, int carry) {
            this.description = description;
            this.l1 = l1;
            this.l2 = l2;
            this.result = result;
            this.currentL1 = currentL1;
            this.currentL2 = currentL2;
            this.currentResult = currentResult;
            this.val1 = val1;
            this.val2 = val2;
            this.carry = carry;
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Interval_02_05_N_AddTwoNumbers_Animation().setVisible(true);
        });
    }
}