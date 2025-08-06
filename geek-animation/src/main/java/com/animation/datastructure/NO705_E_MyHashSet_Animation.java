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
import java.util.LinkedList;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.util.List;
import com.animation.launcher.AlgorithmTreeLauncher;

/**
 * NO.705 设计哈希集合 - 动画演示
 * 
 * 算法思路：
 * 1. 使用数组+链表实现哈希集合
 * 2. 通过哈希函数计算索引
 * 3. 使用链地址法解决哈希冲突
 */
public class NO705_E_MyHashSet_Animation extends JFrame {
    
    private static final int ANIMATION_DELAY = 1500;
    private static final Color BUCKET_COLOR = new Color(173, 216, 230);
    private static final Color CURRENT_COLOR = new Color(255, 99, 71);
    private static final Color SUCCESS_COLOR = new Color(50, 205, 50);
    private static final Color FAIL_COLOR = new Color(255, 69, 0);
    
    // 动画组件
    private JPanel animationPanel;
    private JPanel controlPanel;
    private JPanel inputPanel;
    private JTextArea logArea;
    
    // 控制按钮
    private JButton startButton;
    private JButton stepButton;
    private JButton resetButton;
    private JButton returnButton;
    
    // 输入组件
    private JTextField operationsField;
    
    // 算法状态
    private MyHashSet hashSet;
    private List<String> operations;
    private List<Integer> operands;
    private List<Boolean> results;
    private int currentStep;
    private boolean isPlaying;
    private Timer animationTimer;
    private String currentOperation;
    private int currentOperand;
    private int currentBucket;
    
    // 简化的哈希集合实现
    private class MyHashSet {
        private static final int SIZE = 7; // 使用较小的大小便于演示
        private LinkedList<Integer>[] buckets;
        
        public MyHashSet() {
            buckets = new LinkedList[SIZE];
            for (int i = 0; i < SIZE; i++) {
                buckets[i] = new LinkedList<>();
            }
        }
        
        private int hash(int key) {
            return key % SIZE;
        }
        
        public void add(int key) {
            int bucket = hash(key);
            if (!buckets[bucket].contains(key)) {
                buckets[bucket].add(key);
            }
        }
        
        public void remove(int key) {
            int bucket = hash(key);
            buckets[bucket].removeFirstOccurrence(key);
        }
        
        public boolean contains(int key) {
            int bucket = hash(key);
            return buckets[bucket].contains(key);
        }
        
        public LinkedList<Integer>[] getBuckets() {
            return buckets;
        }
        
        public int getSize() {
            return SIZE;
        }
    }
    
    public NO705_E_MyHashSet_Animation() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        resetAnimation();
        
        setTitle("NO.705 设计哈希集合 - 动画演示");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
    }
    
    private void initializeComponents() {
        // 动画面板
        animationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawAnimation(g);
            }
        };
        animationPanel.setBackground(Color.WHITE);
        animationPanel.setPreferredSize(new Dimension(800, 400));
        
        // 控制面板
        controlPanel = new JPanel(new FlowLayout());
        startButton = new JButton("开始演示");
        stepButton = new JButton("单步执行");
        resetButton = new JButton("重置");
        returnButton = new JButton("返回首页");
        
        controlPanel.add(startButton);
        controlPanel.add(stepButton);
        controlPanel.add(resetButton);
        controlPanel.add(returnButton);
        
        // 输入面板
        inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("操作序列"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("操作序列:"), gbc);
        gbc.gridx = 1;
        operationsField = new JTextField("add(1),add(2),contains(1),contains(3),add(2),contains(2),remove(2),contains(2)", 40);
        inputPanel.add(operationsField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        JLabel hintLabel = new JLabel("格式: add(1),remove(2),contains(3) (用逗号分隔)");
        hintLabel.setFont(new Font("微软雅黑", Font.PLAIN, 10));
        hintLabel.setForeground(Color.GRAY);
        inputPanel.add(hintLabel, gbc);
        
        // 日志区域
        logArea = new JTextArea(8, 30);
        logArea.setEditable(false);
        logArea.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        logArea.setBackground(new Color(248, 248, 248));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 顶部：输入面板
        add(inputPanel, BorderLayout.NORTH);
        
        // 中央：动画面板
        add(animationPanel, BorderLayout.CENTER);
        
        // 底部：控制面板和日志
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(controlPanel, BorderLayout.NORTH);
        bottomPanel.add(new JScrollPane(logArea), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        startButton.addActionListener(e -> startAnimation());
        stepButton.addActionListener(e -> stepForward());
        resetButton.addActionListener(e -> resetAnimation());
        returnButton.addActionListener(e -> returnToHome());
        
        animationTimer = new Timer(ANIMATION_DELAY, e -> stepForward());
    }
    
    private void startAnimation() {
        if (!isPlaying) {
            parseInput();
            isPlaying = true;
            startButton.setText("暂停演示");
            animationTimer.start();
        } else {
            isPlaying = false;
            startButton.setText("继续演示");
            animationTimer.stop();
        }
    }
    
    private void stepForward() {
        if (currentStep < operations.size()) {
            executeOperation(currentStep);
            currentStep++;
        } else {
            // 演示结束
            animationTimer.stop();
            isPlaying = false;
            startButton.setText("开始演示");
            logArea.append("\\n演示完成！\\n");
            currentOperation = null;
            currentBucket = -1;
        }
        
        SwingUtilities.invokeLater(() -> animationPanel.repaint());
    }
    
    private void resetAnimation() {
        animationTimer.stop();
        isPlaying = false;
        currentStep = 0;
        startButton.setText("开始演示");
        
        hashSet = new MyHashSet();
        operations = new ArrayList<>();
        operands = new ArrayList<>();
        results = new ArrayList<>();
        currentOperation = null;
        currentOperand = 0;
        currentBucket = -1;
        
        logArea.setText("哈希集合设计算法演示\\n");
        logArea.append("=================================\\n");
        logArea.append("算法说明：\\n");
        logArea.append("1. 使用数组+链表实现哈希集合\\n");
        logArea.append("2. 哈希函数: hash(key) = key % 7\\n");
        logArea.append("3. 链地址法解决哈希冲突\\n\\n");
        
        SwingUtilities.invokeLater(() -> animationPanel.repaint());
    }
    
    private void parseInput() {
        try {
            operations.clear();
            operands.clear();
            results.clear();
            
            String input = operationsField.getText().trim();
            String[] ops = input.split(",");
            
            for (String op : ops) {
                op = op.trim();
                if (op.startsWith("add(") && op.endsWith(")")) {
                    operations.add("add");
                    operands.add(Integer.parseInt(op.substring(4, op.length() - 1)));
                } else if (op.startsWith("remove(") && op.endsWith(")")) {
                    operations.add("remove");
                    operands.add(Integer.parseInt(op.substring(7, op.length() - 1)));
                } else if (op.startsWith("contains(") && op.endsWith(")")) {
                    operations.add("contains");
                    operands.add(Integer.parseInt(op.substring(9, op.length() - 1)));
                }
            }
            
            logArea.append("操作序列解析完成，共 " + operations.size() + " 个操作\\n\\n");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "输入格式错误，请检查输入！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void executeOperation(int step) {
        String op = operations.get(step);
        int operand = operands.get(step);
        currentOperation = op;
        currentOperand = operand;
        currentBucket = hashSet.hash(operand);
        
        logArea.append("步骤 " + (step + 1) + ": " + op + "(" + operand + ")\\n");
        logArea.append("哈希值: " + operand + " % 7 = " + currentBucket + "\\n");
        
        boolean result = false;
        switch (op) {
            case "add":
                boolean existed = hashSet.contains(operand);
                hashSet.add(operand);
                if (existed) {
                    logArea.append("元素 " + operand + " 已存在，无需添加\\n");
                } else {
                    logArea.append("将元素 " + operand + " 添加到桶 " + currentBucket + "\\n");
                }
                break;
            case "remove":
                boolean removed = hashSet.contains(operand);
                hashSet.remove(operand);
                if (removed) {
                    logArea.append("从桶 " + currentBucket + " 中移除元素 " + operand + "\\n");
                } else {
                    logArea.append("元素 " + operand + " 不存在，无需移除\\n");
                }
                break;
            case "contains":
                result = hashSet.contains(operand);
                logArea.append("在桶 " + currentBucket + " 中查找元素 " + operand + ": " + (result ? "找到" : "未找到") + "\\n");
                results.add(result);
                break;
        }
        
        logArea.append("\\n");
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = animationPanel.getWidth();
        int height = animationPanel.getHeight();
        
        // 绘制标题
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 20));
        g2d.setColor(Color.BLACK);
        String title = "哈希集合 (链地址法)";
        FontMetrics fm = g2d.getFontMetrics();
        int titleX = (width - fm.stringWidth(title)) / 2;
        g2d.drawString(title, titleX, 30);
        
        if (hashSet == null) return;
        
        // 绘制当前操作信息
        if (currentOperation != null) {
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g2d.setColor(CURRENT_COLOR);
            String opInfo = "当前操作: " + currentOperation + "(" + currentOperand + ") → 桶[" + currentBucket + "]";
            g2d.drawString(opInfo, 50, 60);
        }
        
        // 绘制哈希表
        drawHashTable(g2d, width, height);
        
        // 绘制哈希函数说明
        drawHashFunction(g2d, width, height);
    }
    
    private void drawHashTable(Graphics2D g2d, int width, int height) {
        LinkedList<Integer>[] buckets = hashSet.getBuckets();
        int bucketCount = hashSet.getSize();
        
        int startX = 100;
        int startY = 100;
        int bucketWidth = 80;
        int bucketHeight = 40;
        int nodeSize = 30;
        
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 12));
        
        for (int i = 0; i < bucketCount; i++) {
            // 绘制桶索引
            g2d.setColor(Color.BLACK);
            g2d.drawString("桶[" + i + "]", startX, startY - 10);
            
            // 绘制桶
            Color bucketColor = (i == currentBucket) ? CURRENT_COLOR : BUCKET_COLOR;
            g2d.setColor(bucketColor);
            g2d.fillRect(startX, startY, bucketWidth, bucketHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(startX, startY, bucketWidth, bucketHeight);
            
            // 绘制链表中的元素
            LinkedList<Integer> bucket = buckets[i];
            int nodeX = startX + bucketWidth + 10;
            int nodeY = startY + (bucketHeight - nodeSize) / 2;
            
            for (int j = 0; j < bucket.size(); j++) {
                Integer value = bucket.get(j);
                
                // 绘制节点
                if (value == currentOperand && i == currentBucket) {
                    g2d.setColor(CURRENT_COLOR);
                } else {
                    g2d.setColor(SUCCESS_COLOR);
                }
                g2d.fillOval(nodeX, nodeY, nodeSize, nodeSize);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(nodeX, nodeY, nodeSize, nodeSize);
                
                // 绘制值
                g2d.setColor(Color.WHITE);
                String valueStr = value.toString();
                FontMetrics fm = g2d.getFontMetrics();
                int textX = nodeX + (nodeSize - fm.stringWidth(valueStr)) / 2;
                int textY = nodeY + (nodeSize + fm.getAscent()) / 2;
                g2d.drawString(valueStr, textX, textY);
                
                // 绘制箭头
                if (j < bucket.size() - 1) {
                    g2d.setColor(Color.BLACK);
                    g2d.drawLine(nodeX + nodeSize, nodeY + nodeSize / 2, 
                               nodeX + nodeSize + 10, nodeY + nodeSize / 2);
                    g2d.drawLine(nodeX + nodeSize + 5, nodeY + nodeSize / 2 - 3,
                               nodeX + nodeSize + 10, nodeY + nodeSize / 2);
                    g2d.drawLine(nodeX + nodeSize + 5, nodeY + nodeSize / 2 + 3,
                               nodeX + nodeSize + 10, nodeY + nodeSize / 2);
                }
                
                nodeX += nodeSize + 15;
            }
            
            // 如果桶为空，显示NULL
            if (bucket.isEmpty()) {
                g2d.setColor(Color.GRAY);
                g2d.drawString("NULL", nodeX, nodeY + nodeSize / 2 + 5);
            }
            
            startY += bucketHeight + 20;
        }
    }
    
    private void drawHashFunction(Graphics2D g2d, int width, int height) {
        int startY = height - 80;
        
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("哈希函数: hash(key) = key % 7", 100, startY);
        
        if (currentOperation != null) {
            g2d.setColor(CURRENT_COLOR);
            String example = "示例: hash(" + currentOperand + ") = " + currentOperand + " % 7 = " + currentBucket;
            g2d.drawString(example, 100, startY + 20);
        }
        
        // 绘制图例
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        g2d.setColor(Color.BLACK);
        g2d.drawString("图例:", 400, startY);
        
        // 当前操作桶
        g2d.setColor(CURRENT_COLOR);
        g2d.fillRect(450, startY - 15, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("当前操作桶", 470, startY - 5);
        
        // 普通桶
        g2d.setColor(BUCKET_COLOR);
        g2d.fillRect(450, startY + 5, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("普通桶", 470, startY + 15);
    }
    
    private void returnToHome() {
        // 关闭当前窗口
        this.dispose();
        
        // 重新启动主界面
        SwingUtilities.invokeLater(() -> {
            try {
                dispose(); // 关闭当前动画窗口
                com.animation.launcher.AlgorithmTreeLauncher.showMainWindow();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO705_E_MyHashSet_Animation().setVisible(true);
        });
    }
}