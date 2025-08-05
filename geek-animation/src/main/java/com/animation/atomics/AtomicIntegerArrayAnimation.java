package com.animation.atomics;

import com.animation.Animation;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.Random;

/**
 * AtomicIntegerArray 原子数组动画演示
 * 演示原子数组的各种操作方法
 */
public class AtomicIntegerArrayAnimation extends JFrame implements Animation {
    private static final int ARRAY_SIZE = 10;
    
    private AtomicIntegerArray atomicArray;
    private JPanel arrayPanel;
    private JLabel[] arrayLabels;
    private JTextField indexField;
    private JTextField valueField;
    private JButton getButton;
    private JButton setButton;
    private JButton getAndSetButton;
    private JButton getAndIncrementButton;
    private JButton getAndDecrementButton;
    private JButton incrementAndGetButton;
    private JButton decrementAndGetButton;
    private JButton getAndAddButton;
    private JButton addAndGetButton;
    private JButton compareAndSetButton;
    private JButton resetButton;
    private JTextArea logArea;
    private JTextField expectedValueField;
    private JTextField addValueField;
    
    public AtomicIntegerArrayAnimation() {
        initComponents();
        setupLayout();
        setupEventHandlers();
        initializeArray();
    }
    
    private void initComponents() {
        setTitle("AtomicIntegerArray 原子数组动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        atomicArray = new AtomicIntegerArray(ARRAY_SIZE);
        
        // 数组显示面板
        arrayPanel = new JPanel(new GridLayout(1, ARRAY_SIZE, 5, 5));
        arrayLabels = new JLabel[ARRAY_SIZE];
        
        for (int i = 0; i < ARRAY_SIZE; i++) {
            arrayLabels[i] = new JLabel("0", SwingConstants.CENTER);
            arrayLabels[i].setFont(new Font("微软雅黑", Font.BOLD, 16));
            arrayLabels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            arrayLabels[i].setOpaque(true);
            arrayLabels[i].setBackground(Color.WHITE);
            arrayLabels[i].setPreferredSize(new Dimension(60, 40));
            arrayPanel.add(arrayLabels[i]);
        }
        
        // 输入字段
        indexField = new JTextField("0", 5);
        valueField = new JTextField("1", 5);
        expectedValueField = new JTextField("0", 5);
        addValueField = new JTextField("1", 5);
        
        // 操作按钮
        getButton = new JButton("get(index)");
        setButton = new JButton("set(index, value)");
        getAndSetButton = new JButton("getAndSet(index, value)");
        getAndIncrementButton = new JButton("getAndIncrement(index)");
        getAndDecrementButton = new JButton("getAndDecrement(index)");
        incrementAndGetButton = new JButton("incrementAndGet(index)");
        decrementAndGetButton = new JButton("decrementAndGet(index)");
        getAndAddButton = new JButton("getAndAdd(index, delta)");
        addAndGetButton = new JButton("addAndGet(index, delta)");
        compareAndSetButton = new JButton("compareAndSet(index, expected, value)");
        resetButton = new JButton("重置数组");
        
        // 日志区域
        logArea = new JTextArea(15, 40);
        logArea.setEditable(false);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 12));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 顶部 - 数组显示
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("AtomicIntegerArray 可视化"));
        
        // 添加索引标签
        JPanel indexPanel = new JPanel(new GridLayout(1, ARRAY_SIZE, 5, 5));
        for (int i = 0; i < ARRAY_SIZE; i++) {
            JLabel indexLabel = new JLabel("[" + i + "]", SwingConstants.CENTER);
            indexLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
            indexPanel.add(indexLabel);
        }
        
        topPanel.add(indexPanel, BorderLayout.NORTH);
        topPanel.add(arrayPanel, BorderLayout.CENTER);
        
        // 中间 - 控制面板
        JPanel middlePanel = new JPanel(new GridBagLayout());
        middlePanel.setBorder(BorderFactory.createTitledBorder("操作控制"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // 参数输入区
        gbc.gridx = 0; gbc.gridy = 0;
        middlePanel.add(new JLabel("索引:"), gbc);
        gbc.gridx = 1;
        middlePanel.add(indexField, gbc);
        
        gbc.gridx = 2; gbc.gridy = 0;
        middlePanel.add(new JLabel("值:"), gbc);
        gbc.gridx = 3;
        middlePanel.add(valueField, gbc);
        
        gbc.gridx = 4; gbc.gridy = 0;
        middlePanel.add(new JLabel("期望值:"), gbc);
        gbc.gridx = 5;
        middlePanel.add(expectedValueField, gbc);
        
        gbc.gridx = 6; gbc.gridy = 0;
        middlePanel.add(new JLabel("增量:"), gbc);
        gbc.gridx = 7;
        middlePanel.add(addValueField, gbc);
        
        // 操作按钮区
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        middlePanel.add(getButton, gbc);
        gbc.gridx = 2; gbc.gridwidth = 2;
        middlePanel.add(setButton, gbc);
        gbc.gridx = 4; gbc.gridwidth = 2;
        middlePanel.add(getAndSetButton, gbc);
        gbc.gridx = 6; gbc.gridwidth = 2;
        middlePanel.add(resetButton, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        middlePanel.add(getAndIncrementButton, gbc);
        gbc.gridx = 2; gbc.gridwidth = 2;
        middlePanel.add(getAndDecrementButton, gbc);
        gbc.gridx = 4; gbc.gridwidth = 2;
        middlePanel.add(incrementAndGetButton, gbc);
        gbc.gridx = 6; gbc.gridwidth = 2;
        middlePanel.add(decrementAndGetButton, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        middlePanel.add(getAndAddButton, gbc);
        gbc.gridx = 2; gbc.gridwidth = 2;
        middlePanel.add(addAndGetButton, gbc);
        gbc.gridx = 4; gbc.gridwidth = 4;
        middlePanel.add(compareAndSetButton, gbc);
        
        // 日志面板
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(BorderFactory.createTitledBorder("操作日志"));
        logPanel.add(new JScrollPane(logArea), BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(logPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        getButton.addActionListener(e -> performGet());
        setButton.addActionListener(e -> performSet());
        getAndSetButton.addActionListener(e -> performGetAndSet());
        getAndIncrementButton.addActionListener(e -> performGetAndIncrement());
        getAndDecrementButton.addActionListener(e -> performGetAndDecrement());
        incrementAndGetButton.addActionListener(e -> performIncrementAndGet());
        decrementAndGetButton.addActionListener(e -> performDecrementAndGet());
        getAndAddButton.addActionListener(e -> performGetAndAdd());
        addAndGetButton.addActionListener(e -> performAddAndGet());
        compareAndSetButton.addActionListener(e -> performCompareAndSet());
        resetButton.addActionListener(e -> resetArray());
    }
    
    private void initializeArray() {
        // 初始化数组为随机值
        Random random = new Random();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            int value = random.nextInt(10);
            atomicArray.set(i, value);
        }
        updateArrayDisplay();
        
        logArea.append("AtomicIntegerArray 初始化完成\n");
        logArea.append("数组大小: " + ARRAY_SIZE + "\n\n");
        logArea.append("可用操作:\n");
        logArea.append("- get(index): 获取指定索引的值\n");
        logArea.append("- set(index, value): 设置指定索引的值\n");
        logArea.append("- getAndSet(index, value): 获取旧值并设置新值\n");
        logArea.append("- getAndIncrement(index): 获取旧值并自增\n");
        logArea.append("- incrementAndGet(index): 自增并获取新值\n");
        logArea.append("- compareAndSet(index, expected, value): CAS操作\n\n");
    }
    
    private int getIndex() {
        try {
            int index = Integer.parseInt(indexField.getText());
            if (index < 0 || index >= ARRAY_SIZE) {
                throw new IndexOutOfBoundsException();
            }
            return index;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "请输入有效的索引 (0-" + (ARRAY_SIZE-1) + ")");
            return -1;
        }
    }
    
    private int getValue() {
        try {
            return Integer.parseInt(valueField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的整数值");
            return 0;
        }
    }
    
    private int getExpectedValue() {
        try {
            return Integer.parseInt(expectedValueField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的期望值");
            return 0;
        }
    }
    
    private int getAddValue() {
        try {
            return Integer.parseInt(addValueField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的增量值");
            return 0;
        }
    }
    
    private void performGet() {
        int index = getIndex();
        if (index == -1) return;
        
        int value = atomicArray.get(index);
        highlightIndex(index, Color.YELLOW);
        
        logArea.append("get(" + index + ") = " + value + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void performSet() {
        int index = getIndex();
        if (index == -1) return;
        int value = getValue();
        
        int oldValue = atomicArray.get(index);
        atomicArray.set(index, value);
        
        updateArrayDisplay();
        highlightIndex(index, Color.GREEN);
        
        logArea.append("set(" + index + ", " + value + ") - 旧值: " + oldValue + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void performGetAndSet() {
        int index = getIndex();
        if (index == -1) return;
        int value = getValue();
        
        int oldValue = atomicArray.getAndSet(index, value);
        
        updateArrayDisplay();
        highlightIndex(index, Color.CYAN);
        
        logArea.append("getAndSet(" + index + ", " + value + ") = " + oldValue + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void performGetAndIncrement() {
        int index = getIndex();
        if (index == -1) return;
        
        int oldValue = atomicArray.getAndIncrement(index);
        
        updateArrayDisplay();
        highlightIndex(index, Color.ORANGE);
        
        logArea.append("getAndIncrement(" + index + ") = " + oldValue + " (新值: " + atomicArray.get(index) + ")\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void performGetAndDecrement() {
        int index = getIndex();
        if (index == -1) return;
        
        int oldValue = atomicArray.getAndDecrement(index);
        
        updateArrayDisplay();
        highlightIndex(index, Color.ORANGE);
        
        logArea.append("getAndDecrement(" + index + ") = " + oldValue + " (新值: " + atomicArray.get(index) + ")\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void performIncrementAndGet() {
        int index = getIndex();
        if (index == -1) return;
        
        int newValue = atomicArray.incrementAndGet(index);
        
        updateArrayDisplay();
        highlightIndex(index, Color.MAGENTA);
        
        logArea.append("incrementAndGet(" + index + ") = " + newValue + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void performDecrementAndGet() {
        int index = getIndex();
        if (index == -1) return;
        
        int newValue = atomicArray.decrementAndGet(index);
        
        updateArrayDisplay();
        highlightIndex(index, Color.MAGENTA);
        
        logArea.append("decrementAndGet(" + index + ") = " + newValue + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void performGetAndAdd() {
        int index = getIndex();
        if (index == -1) return;
        int delta = getAddValue();
        
        int oldValue = atomicArray.getAndAdd(index, delta);
        
        updateArrayDisplay();
        highlightIndex(index, Color.PINK);
        
        logArea.append("getAndAdd(" + index + ", " + delta + ") = " + oldValue + " (新值: " + atomicArray.get(index) + ")\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void performAddAndGet() {
        int index = getIndex();
        if (index == -1) return;
        int delta = getAddValue();
        
        int newValue = atomicArray.addAndGet(index, delta);
        
        updateArrayDisplay();
        highlightIndex(index, Color.LIGHT_GRAY);
        
        logArea.append("addAndGet(" + index + ", " + delta + ") = " + newValue + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void performCompareAndSet() {
        int index = getIndex();
        if (index == -1) return;
        int expected = getExpectedValue();
        int value = getValue();
        
        int currentValue = atomicArray.get(index);
        boolean success = atomicArray.compareAndSet(index, expected, value);
        
        updateArrayDisplay();
        
        if (success) {
            highlightIndex(index, Color.GREEN);
            logArea.append("compareAndSet(" + index + ", " + expected + ", " + value + ") = true (成功)\n");
        } else {
            highlightIndex(index, Color.RED);
            logArea.append("compareAndSet(" + index + ", " + expected + ", " + value + ") = false (失败，当前值: " + currentValue + ")\n");
        }
        
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void resetArray() {
        for (int i = 0; i < ARRAY_SIZE; i++) {
            atomicArray.set(i, 0);
        }
        updateArrayDisplay();
        
        logArea.append("数组已重置为全零\n\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void updateArrayDisplay() {
        for (int i = 0; i < ARRAY_SIZE; i++) {
            arrayLabels[i].setText(String.valueOf(atomicArray.get(i)));
            arrayLabels[i].setBackground(Color.WHITE);
        }
    }
    
    private void highlightIndex(int index, Color color) {
        arrayLabels[index].setBackground(color);
        
        // 1秒后恢复原色
        Timer timer = new Timer(1000, e -> arrayLabels[index].setBackground(Color.WHITE));
        timer.setRepeats(false);
        timer.start();
    }
    
    @Override
    public void start() {
        setVisible(true);
    }
    
    public void startAnimation() {
        setVisible(true);
    }
    
    public void stopAnimation() {
        dispose();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AtomicIntegerArrayAnimation().startAnimation();
        });
    }
}