package com.animation.atomics;

import com.animation.Animation;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * AtomicMarkableReference 带标记的原子引用动画演示
 * 演示带布尔标记的原子引用操作
 */
public class AtomicMarkableReferenceAnimation extends JFrame implements Animation {
    
    private AtomicMarkableReference<String> atomicMarkableRef;
    private JLabel currentValueLabel;
    private JLabel currentMarkLabel;
    private JTextField expectedValueField;
    private JCheckBox expectedMarkCheckBox;
    private JTextField newValueField;
    private JCheckBox newMarkCheckBox;
    private JButton casButton;
    private JButton setButton;
    private JButton getButton;
    private JButton attemptMarkButton;
    private JButton resetButton;
    private JTextArea logArea;
    private JPanel animationPanel;
    
    // 动画相关
    private Timer animationTimer;
    private boolean isAnimating = false;
    
    public AtomicMarkableReferenceAnimation() {
        initComponents();
        setupLayout();
        setupEventHandlers();
        initializeReference();
    }
    
    private void initComponents() {
        setTitle("AtomicMarkableReference 带标记原子引用动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 650);
        setLocationRelativeTo(null);
        
        // 初始化AtomicMarkableReference
        atomicMarkableRef = new AtomicMarkableReference<>("初始数据", false);
        
        // 显示组件
        currentValueLabel = new JLabel("初始数据", SwingConstants.CENTER);
        currentValueLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        currentValueLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        currentValueLabel.setOpaque(true);
        currentValueLabel.setBackground(Color.WHITE);
        currentValueLabel.setPreferredSize(new Dimension(200, 50));
        
        currentMarkLabel = new JLabel("标记: false", SwingConstants.CENTER);
        currentMarkLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        currentMarkLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        currentMarkLabel.setOpaque(true);
        currentMarkLabel.setBackground(Color.LIGHT_GRAY);
        currentMarkLabel.setPreferredSize(new Dimension(200, 35));
        
        // 输入字段
        expectedValueField = new JTextField("初始数据", 12);
        expectedMarkCheckBox = new JCheckBox("期望标记", false);
        newValueField = new JTextField("新数据", 12);
        newMarkCheckBox = new JCheckBox("新标记", true);
        
        // 按钮
        casButton = new JButton("CAS 操作");
        setButton = new JButton("直接设置");
        getButton = new JButton("获取当前值");
        attemptMarkButton = new JButton("尝试标记");
        resetButton = new JButton("重置");
        
        // 设置按钮颜色
        casButton.setBackground(new Color(144, 238, 144));
        setButton.setBackground(new Color(255, 182, 193));
        attemptMarkButton.setBackground(new Color(255, 215, 0));
        resetButton.setBackground(new Color(211, 211, 211));
        
        // 日志区域
        logArea = new JTextArea(18, 50);
        logArea.setEditable(false);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 11));
        
        // 动画面板
        animationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawMarkVisualization(g);
            }
        };
        animationPanel.setPreferredSize(new Dimension(400, 100));
        animationPanel.setBorder(BorderFactory.createTitledBorder("标记状态可视化"));
        animationPanel.setBackground(Color.WHITE);
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
        valuePanel.add(currentMarkLabel);
        
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
        middlePanel.add(expectedMarkCheckBox, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        middlePanel.add(new JLabel("新值:"), gbc);
        gbc.gridx = 1;
        middlePanel.add(newValueField, gbc);
        gbc.gridx = 2;
        middlePanel.add(newMarkCheckBox, gbc);
        
        // 按钮区
        gbc.gridx = 0; gbc.gridy = 2;
        middlePanel.add(casButton, gbc);
        gbc.gridx = 1;
        middlePanel.add(setButton, gbc);
        gbc.gridx = 2;
        middlePanel.add(getButton, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        middlePanel.add(attemptMarkButton, gbc);
        gbc.gridx = 1;
        middlePanel.add(resetButton, gbc);
        
        // 说明文本
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder("使用场景说明"));
        
        JTextArea infoArea = new JTextArea(3, 50);
        infoArea.setEditable(false);
        infoArea.setBackground(getBackground());
        infoArea.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        infoArea.setText("AtomicMarkableReference 适用场景:\n" +
                        "• 需要标记引用是否已被删除或处理\n" +
                        "• 实现简单的版本控制或状态标记\n" +
                        "• 避免ABA问题的轻量级解决方案");
        infoPanel.add(infoArea, BorderLayout.CENTER);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(middlePanel, BorderLayout.CENTER);
        centerPanel.add(infoPanel, BorderLayout.SOUTH);
        
        // 底部 - 日志面板
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createTitledBorder("操作日志"));
        bottomPanel.add(new JScrollPane(logArea), BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        casButton.addActionListener(e -> performCAS());
        setButton.addActionListener(e -> performSet());
        getButton.addActionListener(e -> performGet());
        attemptMarkButton.addActionListener(e -> performAttemptMark());
        resetButton.addActionListener(e -> resetReference());
    }
    
    private void initializeReference() {
        updateDisplay();
        
        logArea.append("AtomicMarkableReference 初始化完成\n");
        logArea.append("初始值: \"初始数据\", 标记: false\n\n");
        logArea.append("AtomicMarkableReference 特点:\n");
        logArea.append("1. 维护一个引用值和一个布尔标记\n");
        logArea.append("2. CAS操作需要同时比较引用值和标记\n");
        logArea.append("3. 标记通常用于表示状态(如已删除、已处理等)\n");
        logArea.append("4. 比AtomicStampedReference更轻量级\n\n");
        logArea.append("常见使用场景:\n");
        logArea.append("• 标记对象是否已被删除\n");
        logArea.append("• 标记任务是否已完成\n");
        logArea.append("• 简单的状态标记\n\n");
    }
    
    private void performCAS() {
        String expectedValue = expectedValueField.getText();
        String newValue = newValueField.getText();
        boolean expectedMark = expectedMarkCheckBox.isSelected();
        boolean newMark = newMarkCheckBox.isSelected();
        
        // 获取当前状态
        boolean[] currentMark = new boolean[1];
        String currentValue = atomicMarkableRef.get(currentMark);
        
        // 执行CAS操作
        boolean success = atomicMarkableRef.compareAndSet(expectedValue, newValue, expectedMark, newMark);
        
        if (success) {
            animateSuccess();
            logArea.append("CAS操作成功!\n");
            logArea.append("  期望: (\"" + expectedValue + "\", " + expectedMark + ")\n");
            logArea.append("  新值: (\"" + newValue + "\", " + newMark + ")\n\n");
        } else {
            animateFailure();
            logArea.append("CAS操作失败!\n");
            logArea.append("  期望: (\"" + expectedValue + "\", " + expectedMark + ")\n");
            logArea.append("  实际: (\"" + currentValue + "\", " + currentMark[0] + ")\n");
            logArea.append("  新值: (\"" + newValue + "\", " + newMark + ")\n\n");
        }
        
        updateDisplay();
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void performSet() {
        String newValue = newValueField.getText();
        boolean newMark = newMarkCheckBox.isSelected();
        
        // 获取旧值
        boolean[] oldMark = new boolean[1];
        String oldValue = atomicMarkableRef.get(oldMark);
        
        // 直接设置
        atomicMarkableRef.set(newValue, newMark);
        
        animateDirectSet();
        updateDisplay();
        
        logArea.append("直接设置成功!\n");
        logArea.append("  旧值: (\"" + oldValue + "\", " + oldMark[0] + ")\n");
        logArea.append("  新值: (\"" + newValue + "\", " + newMark + ")\n\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void performGet() {
        boolean[] mark = new boolean[1];
        String value = atomicMarkableRef.get(mark);
        
        animateGet();
        
        logArea.append("获取当前值: (\"" + value + "\", " + mark[0] + ")\n\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void performAttemptMark() {
        String expectedValue = expectedValueField.getText();
        boolean newMark = newMarkCheckBox.isSelected();
        
        // 获取当前状态
        boolean[] currentMark = new boolean[1];
        String currentValue = atomicMarkableRef.get(currentMark);
        
        // 尝试标记
        boolean success = atomicMarkableRef.attemptMark(expectedValue, newMark);
        
        if (success) {
            animateAttemptMark(true);
            logArea.append("尝试标记成功!\n");
            logArea.append("  引用值: \"" + expectedValue + "\"\n");
            logArea.append("  新标记: " + newMark + "\n\n");
        } else {
            animateAttemptMark(false);
            logArea.append("尝试标记失败!\n");
            logArea.append("  期望引用值: \"" + expectedValue + "\"\n");
            logArea.append("  实际引用值: \"" + currentValue + "\"\n");
            logArea.append("  当前标记: " + currentMark[0] + "\n\n");
        }
        
        updateDisplay();
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void resetReference() {
        atomicMarkableRef.set("初始数据", false);
        updateDisplay();
        
        expectedValueField.setText("初始数据");
        expectedMarkCheckBox.setSelected(false);
        newValueField.setText("新数据");
        newMarkCheckBox.setSelected(true);
        
        logArea.append("引用已重置为初始状态\n\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void updateDisplay() {
        boolean[] mark = new boolean[1];
        String value = atomicMarkableRef.get(mark);
        
        currentValueLabel.setText(value);
        currentMarkLabel.setText("标记: " + mark[0]);
        
        // 根据标记状态改变颜色
        if (mark[0]) {
            currentMarkLabel.setBackground(new Color(144, 238, 144)); // 浅绿色
            currentMarkLabel.setForeground(Color.BLACK);
        } else {
            currentMarkLabel.setBackground(Color.LIGHT_GRAY);
            currentMarkLabel.setForeground(Color.BLACK);
        }
        
        // 更新输入字段的默认值
        expectedValueField.setText(value);
        expectedMarkCheckBox.setSelected(mark[0]);
        
        // 重绘动画面板
        animationPanel.repaint();
    }
    
    private void drawMarkVisualization(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        boolean[] mark = new boolean[1];
        String value = atomicMarkableRef.get(mark);
        
        int width = animationPanel.getWidth();
        int height = animationPanel.getHeight();
        
        // 绘制引用框
        int refX = 50;
        int refY = 20;
        int refWidth = 150;
        int refHeight = 30;
        
        g2d.setColor(Color.BLACK);
        g2d.drawRect(refX, refY, refWidth, refHeight);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(refX + 1, refY + 1, refWidth - 2, refHeight - 2);
        
        // 绘制引用值
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        FontMetrics fm = g2d.getFontMetrics();
        int textX = refX + (refWidth - fm.stringWidth(value)) / 2;
        int textY = refY + (refHeight + fm.getAscent()) / 2;
        g2d.drawString(value, textX, textY);
        
        // 绘制标记框
        int markX = refX + refWidth + 30;
        int markY = refY;
        int markSize = 30;
        
        if (mark[0]) {
            g2d.setColor(new Color(144, 238, 144));
            g2d.fillRect(markX, markY, markSize, markSize);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(markX, markY, markSize, markSize);
            
            // 绘制勾号
            g2d.setStroke(new BasicStroke(3));
            g2d.setColor(new Color(0, 128, 0));
            g2d.drawLine(markX + 6, markY + 15, markX + 12, markY + 21);
            g2d.drawLine(markX + 12, markY + 21, markX + 24, markY + 9);
        } else {
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(markX, markY, markSize, markSize);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(markX, markY, markSize, markSize);
            
            // 绘制X号
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(Color.RED);
            g2d.drawLine(markX + 8, markY + 8, markX + 22, markY + 22);
            g2d.drawLine(markX + 22, markY + 8, markX + 8, markY + 22);
        }
        
        // 绘制标签
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 10));
        g2d.drawString("引用值", refX, refY + refHeight + 15);
        g2d.drawString("标记", markX, markY + markSize + 15);
        
        // 绘制连接线
        g2d.setColor(Color.GRAY);
        g2d.drawLine(refX + refWidth, refY + refHeight/2, markX, markY + markSize/2);
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
    
    private void animateAttemptMark(boolean success) {
        if (success) {
            animateColor(new Color(255, 215, 0), "标记成功");
        } else {
            animateColor(Color.ORANGE, "标记失败");
        }
    }
    
    private void animateColor(Color color, String operation) {
        if (isAnimating) return;
        
        isAnimating = true;
        currentValueLabel.setBackground(color);
        
        // 1秒后恢复
        Timer timer = new Timer(1000, e -> {
            currentValueLabel.setBackground(Color.WHITE);
            updateDisplay(); // 恢复标记颜色
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
            new AtomicMarkableReferenceAnimation().startAnimation();
        });
    }
}