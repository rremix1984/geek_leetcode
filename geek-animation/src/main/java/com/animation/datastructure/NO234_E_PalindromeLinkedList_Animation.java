package com.animation.datastructure;

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
import java.util.Stack;
import com.animation.launcher.AlgorithmTreeLauncher;

/**
 * NO.234 回文链表 - 动画演示
 * 演示判断链表是否为回文链表的过程
 * 
 * 设计文档：
 * 1. 功能需求：可视化回文链表判断算法的执行过程
 * 2. 技术架构：基于Swing的动画演示系统
 * 3. 核心算法：快慢指针 + 栈方法
 * 4. 动画特色：节点移动、栈操作、比较过程可视化
 * 5. 交互设计：支持自定义链表、步进控制、重置功能
 */
public class NO234_E_PalindromeLinkedList_Animation extends JFrame {
    
    // 链表节点类
    static class ListNode {
        int val;
        ListNode next;
        
        ListNode(int val) {
            this.val = val;
        }
    }
    
    // 动画步骤类
    static class AnimationStep {
        ListNode fast;
        ListNode slow;
        Stack<Integer> stack;
        String phase;
        String description;
        boolean isComparison;
        Integer compareValue;
        boolean result;
        boolean finished;
        
        AnimationStep(ListNode fast, ListNode slow, Stack<Integer> stack, String phase, 
                     String description, boolean isComparison, Integer compareValue, 
                     boolean result, boolean finished) {
            this.fast = fast;
            this.slow = slow;
            this.stack = new Stack<>();
            if (stack != null) {
                this.stack.addAll(stack);
            }
            this.phase = phase;
            this.description = description;
            this.isComparison = isComparison;
            this.compareValue = compareValue;
            this.result = result;
            this.finished = finished;
        }
    }
    
    private ListNode head;
    private List<AnimationStep> steps;
    private int currentStep;
    private Timer animationTimer;
    
    // UI组件
    private JPanel drawPanel;
    private JButton startButton;
    private JButton nextButton;
    private JButton prevButton;
    private JButton resetButton;
    private JTextField inputField;
    private JLabel stepLabel;
    private JLabel descriptionLabel;
    private JLabel resultLabel;
    
    // 绘制参数
    private static final int NODE_SIZE = 40;
    private static final int NODE_SPACING = 80;
    private static final int START_X = 50;
    private static final int LINKED_LIST_Y = 100;
    private static final int STACK_X = 50;
    private static final int STACK_Y = 300;
    private static final int STACK_WIDTH = 60;
    private static final int STACK_HEIGHT = 30;
    
    public NO234_E_PalindromeLinkedList_Animation() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        
        // 设置默认链表
        setDefaultLinkedList();
        
        setTitle("NO.234 回文链表 - 动画演示");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    private void initializeComponents() {
        drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawAnimation(g);
            }
        };
        drawPanel.setBackground(Color.WHITE);
        drawPanel.setPreferredSize(new Dimension(900, 500));
        
        startButton = new JButton("开始动画");
        nextButton = new JButton("下一步");
        prevButton = new JButton("上一步");
        resetButton = new JButton("重置");
        
        inputField = new JTextField("1,2,2,1", 15);
        stepLabel = new JLabel("步骤: 0/0");
        descriptionLabel = new JLabel("请输入链表值，用逗号分隔");
        resultLabel = new JLabel("");
        
        nextButton.setEnabled(false);
        prevButton.setEnabled(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 顶部控制面板
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("链表值:"));
        topPanel.add(inputField);
        topPanel.add(startButton);
        topPanel.add(prevButton);
        topPanel.add(nextButton);
        topPanel.add(resetButton);
        
        // 底部信息面板
        JPanel bottomPanel = new JPanel(new GridLayout(3, 1));
        bottomPanel.add(stepLabel);
        bottomPanel.add(descriptionLabel);
        bottomPanel.add(resultLabel);
        
        add(topPanel, BorderLayout.NORTH);
        add(drawPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        startButton.addActionListener(e -> startAnimation());
        nextButton.addActionListener(e -> nextStep());
        prevButton.addActionListener(e -> prevStep());
        resetButton.addActionListener(e -> resetAnimation());
    }
    
    private void setDefaultLinkedList() {
        createLinkedListFromInput("1,2,2,1");
    }
    
    private void createLinkedListFromInput(String input) {
        try {
            String[] values = input.trim().split(",");
            if (values.length == 0) return;
            
            head = new ListNode(Integer.parseInt(values[0].trim()));
            ListNode current = head;
            
            for (int i = 1; i < values.length; i++) {
                current.next = new ListNode(Integer.parseInt(values[i].trim()));
                current = current.next;
            }
            
            drawPanel.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字，用逗号分隔");
        }
    }
    
    private void startAnimation() {
        String input = inputField.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入链表值");
            return;
        }
        
        createLinkedListFromInput(input);
        generateSteps();
        currentStep = 0;
        updateUI();
        
        startButton.setEnabled(false);
        nextButton.setEnabled(true);
        prevButton.setEnabled(false);
    }
    
    private void generateSteps() {
        steps = new ArrayList<>();
        
        if (head == null) {
            steps.add(new AnimationStep(null, null, null, "完成", 
                "空链表是回文链表", false, null, true, true));
            return;
        }
        
        ListNode fast = head;
        ListNode slow = head;
        Stack<Integer> stack = new Stack<>();
        
        // 第一阶段：使用快慢指针找到中点，同时将前半部分压入栈
        steps.add(new AnimationStep(fast, slow, stack, "初始化", 
            "初始化快慢指针，都指向头节点", false, null, false, false));
        
        while (fast != null) {
            if (fast.next != null) {
                // 快指针走两步，慢指针走一步，将慢指针值压入栈
                stack.push(slow.val);
                fast = fast.next.next;
                slow = slow.next;
                
                steps.add(new AnimationStep(fast, slow, stack, "遍历", 
                    String.format("快指针走两步，慢指针走一步，将值 %d 压入栈", stack.peek()), 
                    false, null, false, false));
            } else {
                // 奇数个节点，快指针走一步
                fast = fast.next;
                steps.add(new AnimationStep(fast, slow, stack, "奇数处理", 
                    "链表长度为奇数，快指针走一步", false, null, false, false));
            }
        }
        
        // 第二阶段：比较栈中元素与后半部分链表
        steps.add(new AnimationStep(null, slow, stack, "比较开始", 
            "开始比较栈中元素与后半部分链表", false, null, false, false));
        
        boolean isPalindrome = true;
        while (slow != null && !stack.isEmpty()) {
            int stackValue = stack.pop();
            int nodeValue = slow.val;
            
            if (stackValue != nodeValue) {
                isPalindrome = false;
                steps.add(new AnimationStep(null, slow, stack, "比较", 
                    String.format("栈顶值 %d ≠ 节点值 %d，不是回文链表", stackValue, nodeValue), 
                    true, stackValue, false, false));
                break;
            } else {
                steps.add(new AnimationStep(null, slow, stack, "比较", 
                    String.format("栈顶值 %d = 节点值 %d，继续比较", stackValue, nodeValue), 
                    true, stackValue, true, false));
            }
            
            slow = slow.next;
        }
        
        // 最终结果
        steps.add(new AnimationStep(null, null, stack, "完成", 
            isPalindrome ? "所有比较都相等，是回文链表" : "发现不相等，不是回文链表", 
            false, null, isPalindrome, true));
    }
    
    private void nextStep() {
        if (currentStep < steps.size() - 1) {
            currentStep++;
            updateUI();
        }
    }
    
    private void prevStep() {
        if (currentStep > 0) {
            currentStep--;
            updateUI();
        }
    }
    
    private void resetAnimation() {
        currentStep = 0;
        steps = null;
        startButton.setEnabled(true);
        nextButton.setEnabled(false);
        prevButton.setEnabled(false);
        resultLabel.setText("");
        drawPanel.repaint();
    }
    
    private void updateUI() {
        if (steps == null || steps.isEmpty()) return;
        
        AnimationStep step = steps.get(currentStep);
        
        stepLabel.setText(String.format("步骤: %d/%d", currentStep + 1, steps.size()));
        descriptionLabel.setText(step.description);
        
        if (step.finished) {
            resultLabel.setText(step.result ? "结果: 是回文链表" : "结果: 不是回文链表");
            resultLabel.setForeground(step.result ? Color.GREEN : Color.RED);
        }
        
        nextButton.setEnabled(currentStep < steps.size() - 1);
        prevButton.setEnabled(currentStep > 0);
        
        drawPanel.repaint();
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (head == null) return;
        
        // 绘制链表
        drawLinkedList(g2d);
        
        // 绘制栈
        drawStack(g2d);
        
        // 绘制指针
        if (steps != null && currentStep < steps.size()) {
            drawPointers(g2d);
        }
        
        // 绘制说明
        drawLegend(g2d);
    }
    
    private void drawLinkedList(Graphics2D g2d) {
        ListNode current = head;
        int x = START_X;
        int nodeIndex = 0;
        
        while (current != null) {
            // 绘制节点
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillOval(x, LINKED_LIST_Y, NODE_SIZE, NODE_SIZE);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(x, LINKED_LIST_Y, NODE_SIZE, NODE_SIZE);
            
            // 绘制节点值
            String value = String.valueOf(current.val);
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (NODE_SIZE - fm.stringWidth(value)) / 2;
            int textY = LINKED_LIST_Y + (NODE_SIZE + fm.getAscent()) / 2;
            g2d.drawString(value, textX, textY);
            
            // 绘制箭头
            if (current.next != null) {
                int arrowStartX = x + NODE_SIZE;
                int arrowY = LINKED_LIST_Y + NODE_SIZE / 2;
                int arrowEndX = x + NODE_SPACING - 10;
                
                g2d.drawLine(arrowStartX, arrowY, arrowEndX, arrowY);
                g2d.drawLine(arrowEndX - 5, arrowY - 3, arrowEndX, arrowY);
                g2d.drawLine(arrowEndX - 5, arrowY + 3, arrowEndX, arrowY);
            }
            
            current = current.next;
            x += NODE_SPACING;
            nodeIndex++;
        }
    }
    
    private void drawStack(Graphics2D g2d) {
        if (steps == null || currentStep >= steps.size()) return;
        
        AnimationStep step = steps.get(currentStep);
        Stack<Integer> stack = step.stack;
        
        // 绘制栈标题
        g2d.setColor(Color.BLACK);
        g2d.drawString("栈 (Stack)", STACK_X, STACK_Y - 10);
        
        // 绘制栈底
        g2d.setColor(Color.GRAY);
        g2d.drawRect(STACK_X, STACK_Y + 200, STACK_WIDTH, 5);
        
        if (stack != null && !stack.isEmpty()) {
            Object[] stackArray = stack.toArray();
            for (int i = 0; i < stackArray.length; i++) {
                int y = STACK_Y + 200 - (i + 1) * STACK_HEIGHT;
                
                // 绘制栈元素
                g2d.setColor(new Color(173, 216, 230));
                g2d.fillRect(STACK_X, y, STACK_WIDTH, STACK_HEIGHT);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(STACK_X, y, STACK_WIDTH, STACK_HEIGHT);
                
                // 绘制值
                String value = stackArray[i].toString();
                FontMetrics fm = g2d.getFontMetrics();
                int textX = STACK_X + (STACK_WIDTH - fm.stringWidth(value)) / 2;
                int textY = y + (STACK_HEIGHT + fm.getAscent()) / 2;
                g2d.drawString(value, textX, textY);
            }
        }
    }
    
    private void drawPointers(Graphics2D g2d) {
        AnimationStep step = steps.get(currentStep);
        
        // 绘制快指针
        if (step.fast != null) {
            int fastIndex = getNodeIndex(step.fast);
            if (fastIndex >= 0) {
                int x = START_X + fastIndex * NODE_SPACING + NODE_SIZE / 2;
                g2d.setColor(Color.RED);
                g2d.fillOval(x - 5, LINKED_LIST_Y - 20, 10, 10);
                g2d.drawString("快", x - 8, LINKED_LIST_Y - 25);
            }
        }
        
        // 绘制慢指针
        if (step.slow != null) {
            int slowIndex = getNodeIndex(step.slow);
            if (slowIndex >= 0) {
                int x = START_X + slowIndex * NODE_SPACING + NODE_SIZE / 2;
                g2d.setColor(Color.BLUE);
                g2d.fillOval(x - 5, LINKED_LIST_Y + NODE_SIZE + 10, 10, 10);
                g2d.drawString("慢", x - 8, LINKED_LIST_Y + NODE_SIZE + 25);
            }
        }
        
        // 高亮比较的节点
        if (step.isComparison && step.slow != null) {
            int slowIndex = getNodeIndex(step.slow);
            if (slowIndex >= 0) {
                int x = START_X + slowIndex * NODE_SPACING;
                g2d.setColor(step.result ? Color.GREEN : Color.RED);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawOval(x - 2, LINKED_LIST_Y - 2, NODE_SIZE + 4, NODE_SIZE + 4);
                g2d.setStroke(new BasicStroke(1));
            }
        }
    }
    
    private int getNodeIndex(ListNode target) {
        ListNode current = head;
        int index = 0;
        
        while (current != null) {
            if (current == target) {
                return index;
            }
            current = current.next;
            index++;
        }
        
        return -1;
    }
    
    private void drawLegend(Graphics2D g2d) {
        int legendX = 600;
        int legendY = 300;
        
        g2d.setColor(Color.BLACK);
        g2d.drawString("图例:", legendX, legendY);
        
        // 快指针
        g2d.setColor(Color.RED);
        g2d.fillOval(legendX, legendY + 20, 10, 10);
        g2d.setColor(Color.BLACK);
        g2d.drawString("快指针", legendX + 20, legendY + 30);
        
        // 慢指针
        g2d.setColor(Color.BLUE);
        g2d.fillOval(legendX, legendY + 50, 10, 10);
        g2d.setColor(Color.BLACK);
        g2d.drawString("慢指针", legendX + 20, legendY + 60);
        
        // 比较节点
        g2d.setColor(Color.GREEN);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawOval(legendX, legendY + 80, 15, 15);
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.BLACK);
        g2d.drawString("比较相等", legendX + 20, legendY + 90);
        
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawOval(legendX, legendY + 110, 15, 15);
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.BLACK);
        g2d.drawString("比较不等", legendX + 20, legendY + 120);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO234_E_PalindromeLinkedList_Animation().setVisible(true);
        });
    }
}