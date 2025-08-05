package com.animation.atomics;

import com.animation.Animation;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * AtomicStampedReference 带版本戳的原子引用动画演示
 * 演示如何解决ABA问题
 */
public class AtomicStampedReferenceAnimation extends JFrame implements Animation {
    
    private AtomicStampedReference<String> atomicStampedRef;
    private JLabel currentValueLabel;
    private JLabel currentStampLabel;
    private JTextField expectedValueField;
    private JTextField expectedStampField;
    private JTextField newValueField;
    private JTextField newStampField;
    private JButton casButton;
    private JButton setButton;
    private JButton getButton;
    private JButton simulateABAButton;
    private JButton resetButton;
    private JTextArea logArea;
    private JPanel animationPanel;
    
    // 动画相关
    private Timer animationTimer;
    private Color currentColor = Color.WHITE;
    private boolean isAnimating = false;
    
    public AtomicStampedReferenceAnimation() {
        initComponents();
        setupLayout();
        setupEventHandlers();
        initializeReference();
    }
    
    private void initComponents() {
        setTitle("AtomicStampedReference 带版本戳原子引用动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // 初始化AtomicStampedReference
        atomicStampedRef = new AtomicStampedReference<>("初始值", 0);
        
        // 显示组件
        currentValueLabel = new JLabel("初始值", SwingConstants.CENTER);
        currentValueLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        currentValueLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        currentValueLabel.setOpaque(true);
        currentValueLabel.setBackground(Color.WHITE);
        currentValueLabel.setPreferredSize(new Dimension(200, 60));
        
        currentStampLabel = new JLabel("版本戳: 0", SwingConstants.CENTER);
        currentStampLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        currentStampLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        currentStampLabel.setOpaque(true);
        currentStampLabel.setBackground(Color.LIGHT_GRAY);
        currentStampLabel.setPreferredSize(new Dimension(200, 40));
        
        // 输入字段
        expectedValueField = new JTextField("初始值", 10);
        expectedStampField = new JTextField("0", 5);
        newValueField = new JTextField("新值", 10);
        newStampField = new JTextField("1", 5);
        
        // 按钮
        casButton = new JButton("CAS 操作");
        setButton = new JButton("直接设置");
        getButton = new JButton("获取当前值");
        simulateABAButton = new JButton("模拟ABA问题");
        resetButton = new JButton("重置");
        
        // 设置按钮颜色
        casButton.setBackground(new Color(144, 238, 144));
        setButton.setBackground(new Color(255, 182, 193));
        simulateABAButton.setBackground(new Color(255, 165, 0));
        resetButton.setBackground(new Color(211, 211, 211));
        
        // 日志区域
        logArea = new JTextArea(15, 50);
        logArea.setEditable(false);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        
        // 动画面板
        animationPanel = new JPanel();
        animationPanel.setPreferredSize(new Dimension(400, 150));
        animationPanel.setBorder(BorderFactory.createTitledBorder("引用状态可视化"));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 顶部 - 当前状态显示
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("当前状态"));
        
        JPanel valuePanel = new JPanel(new FlowLayout());
        valuePanel.add(new JLabel("当前值: "));
        valuePanel.add(currentValueLabel);
        valuePanel.add(Box.createHorizontalStrut(20));
        valuePanel.add(currentStampLabel);
        
        topPanel.add(valuePanel, BorderLayout.CENTER);
        topPanel.add(animationPanel, BorderLayout.SOUTH);
        
        // 中间 - 控制面板
        JPanel middlePanel = new JPanel(new GridBagLayout());
        middlePanel.setBorder(BorderFactory.createTitledBorder("操作控制"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // CAS操作区
        gbc.gridx = 0; gbc.gridy = 0;
        middlePanel.add(new JLabel("期望值:"), gbc);
        gbc.gridx = 1;
        middlePanel.add(expectedValueField, gbc);
        gbc.gridx = 2;
        middlePanel.add(new JLabel("期望版本戳:"), gbc);
        gbc.gridx = 3;
        middlePanel.add(expectedStampField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        middlePanel.add(new JLabel("新值:"), gbc);
        gbc.gridx = 1;
        middlePanel.add(newValueField, gbc);
        gbc.gridx = 2;
        middlePanel.add(new JLabel("新版本戳:"), gbc);
        gbc.gridx = 3;
        middlePanel.add(newStampField, gbc);
        
        // 按钮区
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        middlePanel.add(casButton, gbc);
        gbc.gridx = 1;
        middlePanel.add(setButton, gbc);
        gbc.gridx = 2;
        middlePanel.add(getButton, gbc);
        gbc.gridx = 3;
        middlePanel.add(resetButton, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 4;
        middlePanel.add(simulateABAButton, gbc);
        
        // 底部 - 日志面板
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createTitledBorder("操作日志"));
        bottomPanel.add(new JScrollPane(logArea), BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        casButton.addActionListener(e -> performCAS());
        setButton.addActionListener(e -> performSet());
        getButton.addActionListener(e -> performGet());
        simulateABAButton.addActionListener(e -> simulateABAProblem());
        resetButton.addActionListener(e -> resetReference());
    }
    
    private void initializeReference() {
        updateDisplay();
        
        logArea.append("AtomicStampedReference 初始化完成\n");
        logArea.append("初始值: \"初始值\", 版本戳: 0\n\n");
        logArea.append("AtomicStampedReference 特点:\n");
        logArea.append("1. 除了引用值，还维护一个版本戳(stamp)\n");
        logArea.append("2. CAS操作需要同时比较引用值和版本戳\n");
        logArea.append("3. 可以有效解决ABA问题\n\n");
        logArea.append("ABA问题说明:\n");
        logArea.append("线程1读取值A，准备CAS操作\n");
        logArea.append("线程2将A改为B，再改回A\n");
        logArea.append("线程1的CAS操作会成功，但实际上值已经被修改过\n\n");
    }
    
    private void performCAS() {
        String expectedValue = expectedValueField.getText();
        String newValue = newValueField.getText();
        
        int expectedStamp;
        int newStamp;
        
        try {
            expectedStamp = Integer.parseInt(expectedStampField.getText());
            newStamp = Integer.parseInt(newStampField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的版本戳数字");
            return;
        }
        
        // 获取当前状态
        int[] currentStamp = new int[1];
        String currentValue = atomicStampedRef.get(currentStamp);
        
        // 执行CAS操作
        boolean success = atomicStampedRef.compareAndSet(expectedValue, newValue, expectedStamp, newStamp);
        
        if (success) {
            animateSuccess();
            logArea.append("CAS操作成功!\n");
            logArea.append("  期望: (\"" + expectedValue + "\", " + expectedStamp + ")\n");
            logArea.append("  新值: (\"" + newValue + "\", " + newStamp + ")\n\n");
        } else {
            animateFailure();
            logArea.append("CAS操作失败!\n");
            logArea.append("  期望: (\"" + expectedValue + "\", " + expectedStamp + ")\n");
            logArea.append("  实际: (\"" + currentValue + "\", " + currentStamp[0] + ")\n");
            logArea.append("  新值: (\"" + newValue + "\", " + newStamp + ")\n\n");
        }
        
        updateDisplay();
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void performSet() {
        String newValue = newValueField.getText();
        int newStamp;
        
        try {
            newStamp = Integer.parseInt(newStampField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的版本戳数字");
            return;
        }
        
        // 获取旧值
        int[] oldStamp = new int[1];
        String oldValue = atomicStampedRef.get(oldStamp);
        
        // 直接设置
        atomicStampedRef.set(newValue, newStamp);
        
        animateDirectSet();
        updateDisplay();
        
        logArea.append("直接设置成功!\n");
        logArea.append("  旧值: (\"" + oldValue + "\", " + oldStamp[0] + ")\n");
        logArea.append("  新值: (\"" + newValue + "\", " + newStamp + ")\n\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void performGet() {
        int[] stamp = new int[1];
        String value = atomicStampedRef.get(stamp);
        
        animateGet();
        
        logArea.append("获取当前值: (\"" + value + "\", " + stamp[0] + ")\n\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void simulateABAProblem() {
        logArea.append("=== 模拟ABA问题演示 ===\n");
        
        // 初始状态: A, stamp=0
        atomicStampedRef.set("A", 0);
        updateDisplay();
        logArea.append("1. 初始状态: (\"A\", 0)\n");
        
        // 模拟线程1读取当前值
        int[] thread1Stamp = new int[1];
        String thread1Value = atomicStampedRef.get(thread1Stamp);
        logArea.append("2. 线程1读取: (\"" + thread1Value + "\", " + thread1Stamp[0] + ")\n");
        
        // 模拟线程2的操作: A -> B
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {}
        
        atomicStampedRef.set("B", 1);
        updateDisplay();
        logArea.append("3. 线程2操作: A -> B, 版本戳: 0 -> 1\n");
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {}
        
        // 模拟线程2的操作: B -> A (但版本戳不同)
        atomicStampedRef.set("A", 2);
        updateDisplay();
        logArea.append("4. 线程2操作: B -> A, 版本戳: 1 -> 2\n");
        
        // 模拟线程1尝试CAS (使用旧的版本戳)
        boolean success = atomicStampedRef.compareAndSet("A", "C", 0, 3);
        
        if (success) {
            animateSuccess();
            logArea.append("5. 线程1 CAS成功 (不应该发生!)\n");
        } else {
            animateFailure();
            logArea.append("5. 线程1 CAS失败 (正确行为!)\n");
            logArea.append("   期望版本戳: 0, 实际版本戳: 2\n");
            logArea.append("   AtomicStampedReference成功防止了ABA问题!\n");
        }
        
        updateDisplay();
        logArea.append("=== ABA问题演示结束 ===\n\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void resetReference() {
        atomicStampedRef.set("初始值", 0);
        updateDisplay();
        
        expectedValueField.setText("初始值");
        expectedStampField.setText("0");
        newValueField.setText("新值");
        newStampField.setText("1");
        
        logArea.append("引用已重置为初始状态\n\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void updateDisplay() {
        int[] stamp = new int[1];
        String value = atomicStampedRef.get(stamp);
        
        currentValueLabel.setText(value);
        currentStampLabel.setText("版本戳: " + stamp[0]);
        
        // 更新输入字段的默认值
        expectedValueField.setText(value);
        expectedStampField.setText(String.valueOf(stamp[0]));
        newStampField.setText(String.valueOf(stamp[0] + 1));
    }
    
    private void animateSuccess() {
        animateColor(Color.GREEN, "CAS成功");
    }
    
    private void animateFailure() {
        animateColor(Color.RED, "CAS失败");
    }
    
    private void animateDirectSet() {
        animateColor(Color.BLUE, "直接设置");
    }
    
    private void animateGet() {
        animateColor(Color.YELLOW, "获取值");
    }
    
    private void animateColor(Color color, String operation) {
        if (isAnimating) return;
        
        isAnimating = true;
        currentColor = color;
        currentValueLabel.setBackground(color);
        currentStampLabel.setBackground(color);
        
        // 显示操作文本
        Graphics g = animationPanel.getGraphics();
        if (g != null) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, animationPanel.getWidth(), animationPanel.getHeight());
            g.setColor(color);
            g.setFont(new Font("微软雅黑", Font.BOLD, 16));
            FontMetrics fm = g.getFontMetrics();
            int x = (animationPanel.getWidth() - fm.stringWidth(operation)) / 2;
            int y = animationPanel.getHeight() / 2;
            g.drawString(operation, x, y);
        }
        
        // 1秒后恢复
        Timer timer = new Timer(1000, e -> {
            currentValueLabel.setBackground(Color.WHITE);
            currentStampLabel.setBackground(Color.LIGHT_GRAY);
            if (animationPanel.getGraphics() != null) {
                animationPanel.getGraphics().clearRect(0, 0, animationPanel.getWidth(), animationPanel.getHeight());
            }
            animationPanel.repaint();
            isAnimating = false;
        });
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
        if (animationTimer != null) {
            animationTimer.stop();
        }
        dispose();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AtomicStampedReferenceAnimation().startAnimation();
        });
    }
}