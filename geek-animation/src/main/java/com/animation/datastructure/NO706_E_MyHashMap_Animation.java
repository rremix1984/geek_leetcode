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

/**
 * NO.706 设计哈希映射 - 动画演示
 * 
 * 算法思路：
 * 1. 使用数组存储键值对
 * 2. 通过哈希函数计算索引
 * 3. 线性探测法解决哈希冲突
 */
public class NO706_E_MyHashMap_Animation extends JFrame {
    
    private static final int ANIMATION_DELAY = 1500;
    private static final Color EMPTY_COLOR = new Color(240, 240, 240);
    private static final Color OCCUPIED_COLOR = new Color(173, 216, 230);
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
    private MyHashMap hashMap;
    private List<String> operations;
    private List<int[]> operands; // [key, value] 或 [key]
    private List<Integer> results;
    private int currentStep;
    private boolean isPlaying;
    private Timer animationTimer;
    private String currentOperation;
    private int currentKey;
    private int currentValue;
    private int currentIndex;
    
    // 简化的哈希映射实现
    private class MyHashMap {
        private static final int SIZE = 11; // 使用质数作为大小
        private static final int EMPTY = -1;
        private int[] keys;
        private int[] values;
        
        public MyHashMap() {
            keys = new int[SIZE];
            values = new int[SIZE];
            for (int i = 0; i < SIZE; i++) {
                keys[i] = EMPTY;
            }
        }
        
        private int hash(int key) {
            return key % SIZE;
        }
        
        private int findSlot(int key) {
            int index = hash(key);
            while (keys[index] != EMPTY && keys[index] != key) {
                index = (index + 1) % SIZE;
            }
            return index;
        }
        
        public void put(int key, int value) {
            int index = findSlot(key);
            keys[index] = key;
            values[index] = value;
        }
        
        public int get(int key) {
            int index = findSlot(key);
            return (keys[index] == key) ? values[index] : -1;
        }
        
        public void remove(int key) {
            int index = findSlot(key);
            if (keys[index] == key) {
                keys[index] = EMPTY;
                // 重新哈希后续元素
                rehashAfterRemoval(index);
            }
        }
        
        private void rehashAfterRemoval(int removedIndex) {
            int index = (removedIndex + 1) % SIZE;
            while (keys[index] != EMPTY) {
                int key = keys[index];
                int value = values[index];
                keys[index] = EMPTY;
                put(key, value);
                index = (index + 1) % SIZE;
            }
        }
        
        public int[] getKeys() {
            return keys;
        }
        
        public int[] getValues() {
            return values;
        }
        
        public int getSize() {
            return SIZE;
        }
        
        public boolean isEmpty(int index) {
            return keys[index] == EMPTY;
        }
    }
    
    public NO706_E_MyHashMap_Animation() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        resetAnimation();
        
        setTitle("NO.706 设计哈希映射 - 动画演示");
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
        operationsField = new JTextField("put(1,1),put(2,2),get(1),get(3),put(2,1),get(2),remove(2),get(2)", 40);
        inputPanel.add(operationsField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        JLabel hintLabel = new JLabel("格式: put(1,1),get(2),remove(3) (用逗号分隔)");
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
            currentIndex = -1;
        }
        
        animationPanel.repaint();
    }
    
    private void resetAnimation() {
        animationTimer.stop();
        isPlaying = false;
        currentStep = 0;
        startButton.setText("开始演示");
        
        hashMap = new MyHashMap();
        operations = new ArrayList<>();
        operands = new ArrayList<>();
        results = new ArrayList<>();
        currentOperation = null;
        currentKey = 0;
        currentValue = 0;
        currentIndex = -1;
        
        logArea.setText("哈希映射设计算法演示\\n");
        logArea.append("=================================\\n");
        logArea.append("算法说明：\\n");
        logArea.append("1. 使用数组存储键值对\\n");
        logArea.append("2. 哈希函数: hash(key) = key % 11\\n");
        logArea.append("3. 线性探测法解决哈希冲突\\n\\n");
        
        animationPanel.repaint();
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
                if (op.startsWith("put(") && op.endsWith(")")) {
                    operations.add("put");
                    String params = op.substring(4, op.length() - 1);
                    String[] parts = params.split(",");
                    operands.add(new int[]{Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim())});
                } else if (op.startsWith("get(") && op.endsWith(")")) {
                    operations.add("get");
                    operands.add(new int[]{Integer.parseInt(op.substring(4, op.length() - 1))});
                } else if (op.startsWith("remove(") && op.endsWith(")")) {
                    operations.add("remove");
                    operands.add(new int[]{Integer.parseInt(op.substring(7, op.length() - 1))});
                }
            }
            
            logArea.append("操作序列解析完成，共 " + operations.size() + " 个操作\\n\\n");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "输入格式错误，请检查输入！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void executeOperation(int step) {
        String op = operations.get(step);
        int[] operand = operands.get(step);
        currentOperation = op;
        currentKey = operand[0];
        
        logArea.append("步骤 " + (step + 1) + ": " + op + "(");
        
        switch (op) {
            case "put":
                currentValue = operand[1];
                logArea.append(currentKey + ", " + currentValue + ")\\n");
                currentIndex = hashMap.findSlot(currentKey);
                logArea.append("哈希值: " + currentKey + " % 11 = " + hashMap.hash(currentKey) + "\\n");
                if (currentIndex != hashMap.hash(currentKey)) {
                    logArea.append("发生冲突，线性探测到位置 " + currentIndex + "\\n");
                }
                hashMap.put(currentKey, currentValue);
                logArea.append("在位置 " + currentIndex + " 存储键值对 (" + currentKey + ", " + currentValue + ")\\n");
                break;
                
            case "get":
                logArea.append(currentKey + ")\\n");
                currentIndex = hashMap.findSlot(currentKey);
                int result = hashMap.get(currentKey);
                logArea.append("哈希值: " + currentKey + " % 11 = " + hashMap.hash(currentKey) + "\\n");
                if (currentIndex != hashMap.hash(currentKey)) {
                    logArea.append("线性探测到位置 " + currentIndex + "\\n");
                }
                logArea.append("查找结果: " + (result == -1 ? "未找到" : result) + "\\n");
                results.add(result);
                break;
                
            case "remove":
                logArea.append(currentKey + ")\\n");
                currentIndex = hashMap.findSlot(currentKey);
                boolean existed = hashMap.get(currentKey) != -1;
                logArea.append("哈希值: " + currentKey + " % 11 = " + hashMap.hash(currentKey) + "\\n");
                if (currentIndex != hashMap.hash(currentKey)) {
                    logArea.append("线性探测到位置 " + currentIndex + "\\n");
                }
                hashMap.remove(currentKey);
                if (existed) {
                    logArea.append("从位置 " + currentIndex + " 移除键 " + currentKey + "\\n");
                } else {
                    logArea.append("键 " + currentKey + " 不存在\\n");
                }
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
        String title = "哈希映射 (线性探测法)";
        FontMetrics fm = g2d.getFontMetrics();
        int titleX = (width - fm.stringWidth(title)) / 2;
        g2d.drawString(title, titleX, 30);
        
        if (hashMap == null) return;
        
        // 绘制当前操作信息
        if (currentOperation != null) {
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g2d.setColor(CURRENT_COLOR);
            String opInfo = "当前操作: " + currentOperation + "(" + currentKey;
            if ("put".equals(currentOperation)) {
                opInfo += ", " + currentValue;
            }
            opInfo += ") → 位置[" + currentIndex + "]";
            g2d.drawString(opInfo, 50, 60);
        }
        
        // 绘制哈希表
        drawHashTable(g2d, width, height);
        
        // 绘制哈希函数说明
        drawHashFunction(g2d, width, height);
    }
    
    private void drawHashTable(Graphics2D g2d, int width, int height) {
        int[] keys = hashMap.getKeys();
        int[] values = hashMap.getValues();
        int size = hashMap.getSize();
        
        int startX = 100;
        int startY = 100;
        int cellWidth = 60;
        int cellHeight = 40;
        
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 12));
        
        // 绘制表头
        g2d.setColor(Color.BLACK);
        g2d.drawString("索引", startX - 40, startY + cellHeight / 2 + 5);
        g2d.drawString("键", startX + cellWidth / 2 - 10, startY - 10);
        g2d.drawString("值", startX + cellWidth + cellWidth / 2 - 10, startY - 10);
        
        for (int i = 0; i < size; i++) {
            int y = startY + i * (cellHeight + 5);
            
            // 绘制索引
            g2d.setColor(Color.BLACK);
            g2d.drawString(String.valueOf(i), startX - 30, y + cellHeight / 2 + 5);
            
            // 绘制键的单元格
            Color cellColor;
            if (i == currentIndex) {
                cellColor = CURRENT_COLOR;
            } else if (!hashMap.isEmpty(i)) {
                cellColor = OCCUPIED_COLOR;
            } else {
                cellColor = EMPTY_COLOR;
            }
            
            g2d.setColor(cellColor);
            g2d.fillRect(startX, y, cellWidth, cellHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(startX, y, cellWidth, cellHeight);
            
            // 绘制键值
            if (!hashMap.isEmpty(i)) {
                g2d.setColor(Color.BLACK);
                String keyStr = String.valueOf(keys[i]);
                FontMetrics fm = g2d.getFontMetrics();
                int keyX = startX + (cellWidth - fm.stringWidth(keyStr)) / 2;
                int keyY = y + (cellHeight + fm.getAscent()) / 2;
                g2d.drawString(keyStr, keyX, keyY);
            }
            
            // 绘制值的单元格
            g2d.setColor(cellColor);
            g2d.fillRect(startX + cellWidth, y, cellWidth, cellHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(startX + cellWidth, y, cellWidth, cellHeight);
            
            // 绘制值
            if (!hashMap.isEmpty(i)) {
                g2d.setColor(Color.BLACK);
                String valueStr = String.valueOf(values[i]);
                FontMetrics fm = g2d.getFontMetrics();
                int valueX = startX + cellWidth + (cellWidth - fm.stringWidth(valueStr)) / 2;
                int valueY = y + (cellHeight + fm.getAscent()) / 2;
                g2d.drawString(valueStr, valueX, valueY);
            }
        }
    }
    
    private void drawHashFunction(Graphics2D g2d, int width, int height) {
        int startY = height - 80;
        
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("哈希函数: hash(key) = key % 11", 300, startY);
        
        if (currentOperation != null) {
            g2d.setColor(CURRENT_COLOR);
            int hashValue = hashMap.hash(currentKey);
            String example = "示例: hash(" + currentKey + ") = " + currentKey + " % 11 = " + hashValue;
            g2d.drawString(example, 300, startY + 20);
            
            if (currentIndex != hashValue) {
                g2d.drawString("线性探测: " + hashValue + " → " + currentIndex, 300, startY + 40);
            }
        }
        
        // 绘制图例
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        g2d.setColor(Color.BLACK);
        g2d.drawString("图例:", 600, startY);
        
        // 当前操作位置
        g2d.setColor(CURRENT_COLOR);
        g2d.fillRect(650, startY - 15, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("当前操作", 670, startY - 5);
        
        // 已占用位置
        g2d.setColor(OCCUPIED_COLOR);
        g2d.fillRect(650, startY + 5, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("已占用", 670, startY + 15);
        
        // 空位置
        g2d.setColor(EMPTY_COLOR);
        g2d.fillRect(650, startY + 25, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("空位置", 670, startY + 35);
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
            new NO706_E_MyHashMap_Animation().setVisible(true);
        });
    }
}