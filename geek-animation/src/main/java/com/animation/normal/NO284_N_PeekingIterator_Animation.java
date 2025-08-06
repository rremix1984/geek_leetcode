package com.animation.normal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * NO284 顶端迭代器 动画演示
 * 
 * 题目描述：
 * 给定一个迭代器类的接口，接口包含两个方法： next() 和 hasNext()。
 * 设计并实现一个支持 peek() 操作的顶端迭代器 -- 其本质就是把原本应由 next() 方法返回的元素 peek() 出来。
 * 
 * @author AI Assistant
 */
public class NO284_N_PeekingIterator_Animation extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    // PeekingIterator实现
    private static class PeekingIterator implements Iterator<Integer> {
        private Iterator<Integer> iterator;
        private Integer peekedValue;
        private boolean hasPeeked;
        
        public PeekingIterator(Iterator<Integer> iterator) {
            this.iterator = iterator;
            this.hasPeeked = false;
            this.peekedValue = null;
        }
        
        // 返回下一个元素，但不移动迭代器
        public Integer peek() {
            if (!hasPeeked) {
                peekedValue = iterator.next();
                hasPeeked = true;
            }
            return peekedValue;
        }
        
        @Override
        public Integer next() {
            if (hasPeeked) {
                Integer result = peekedValue;
                hasPeeked = false;
                peekedValue = null;
                return result;
            }
            return iterator.next();
        }
        
        @Override
        public boolean hasNext() {
            return hasPeeked || iterator.hasNext();
        }
        
        // 获取当前状态信息（用于可视化）
        public String getStateInfo() {
            return "hasPeeked: " + hasPeeked + ", peekedValue: " + peekedValue;
        }
    }
    
    // UI组件
    private JTextField arrayField;
    private JButton initButton;
    private JButton peekButton;
    private JButton nextButton;
    private JButton hasNextButton;
    private JButton resetButton;
    private JTextArea logArea;
    private JPanel visualPanel;
    private JLabel stateLabel;
    
    // 数据
    private List<Integer> originalList;
    private PeekingIterator peekingIterator;
    private int currentPosition;
    private Integer lastPeekedValue;
    private Integer lastNextValue;
    private boolean lastHasNextResult;
    private java.util.List<String> operationHistory;
    
    // 可视化相关
    private static final int ELEMENT_WIDTH = 50;
    private static final int ELEMENT_HEIGHT = 40;
    private static final Color CURRENT_COLOR = Color.YELLOW;
    private static final Color PEEKED_COLOR = Color.ORANGE;
    private static final Color CONSUMED_COLOR = Color.LIGHT_GRAY;
    private static final Color NORMAL_COLOR = Color.WHITE;
    
    public NO284_N_PeekingIterator_Animation() {
        operationHistory = new ArrayList<>();
        initializeUI();
        loadDefaultData();
    }
    
    private void initializeUI() {
        setTitle("NO284 - 顶端迭代器 动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // 顶部控制面板
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.NORTH);
        
        // 中央面板
        JPanel centerPanel = new JPanel(new BorderLayout());
        
        // 可视化面板
        visualPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawVisualization(g);
            }
        };
        visualPanel.setBackground(Color.WHITE);
        visualPanel.setPreferredSize(new Dimension(700, 300));
        centerPanel.add(visualPanel, BorderLayout.CENTER);
        
        // 右侧信息面板
        JPanel infoPanel = createInfoPanel();
        centerPanel.add(infoPanel, BorderLayout.EAST);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // 底部日志面板
        logArea = new JTextArea(8, 50);
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("操作日志"));
        add(scrollPane, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        
        addLog("顶端迭代器动画演示初始化完成");
        addLog("请输入数组并初始化迭代器，然后进行操作");
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("操作控制"));
        
        panel.add(new JLabel("输入数组 (用逗号分隔):"));
        
        arrayField = new JTextField("1,2,3,4,5", 15);
        panel.add(arrayField);
        
        initButton = new JButton("初始化迭代器");
        initButton.addActionListener(e -> initializeIterator());
        panel.add(initButton);
        
        peekButton = new JButton("peek()");
        peekButton.addActionListener(e -> performPeek());
        peekButton.setEnabled(false);
        panel.add(peekButton);
        
        nextButton = new JButton("next()");
        nextButton.addActionListener(e -> performNext());
        nextButton.setEnabled(false);
        panel.add(nextButton);
        
        hasNextButton = new JButton("hasNext()");
        hasNextButton.addActionListener(e -> performHasNext());
        hasNextButton.setEnabled(false);
        panel.add(hasNextButton);
        
        resetButton = new JButton("重置");
        resetButton.addActionListener(e -> reset());
        panel.add(resetButton);
        
        // 预设按钮
        JButton demoButton = new JButton("演示数据");
        demoButton.addActionListener(e -> loadDemoData());
        panel.add(demoButton);
        
        return panel;
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("迭代器状态"));
        panel.setPreferredSize(new Dimension(250, 300));
        
        stateLabel = new JLabel("<html>状态: 未初始化</html>");
        stateLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        panel.add(stateLabel);
        
        panel.add(Box.createVerticalStrut(20));
        
        JLabel instructionLabel = new JLabel("<html><b>PeekingIterator 说明:</b><br/>" +
            "• <b>peek()</b>: 返回下一个元素但不移动迭代器<br/>" +
            "• <b>next()</b>: 返回下一个元素并移动迭代器<br/>" +
            "• <b>hasNext()</b>: 检查是否还有下一个元素<br/><br/>" +
            "<b>实现原理:</b><br/>" +
            "使用一个缓存变量存储peek的值，" +
            "当调用next()时优先返回缓存的值<br/><br/>" +
            "<b>可视化说明:</b><br/>" +
            "• 白色: 未访问的元素<br/>" +
            "• 黄色: 当前位置<br/>" +
            "• 橙色: 已peek但未消费<br/>" +
            "• 灰色: 已消费的元素<br/><br/>" +
            "<b>时间复杂度:</b><br/>" +
            "• peek(): O(1)<br/>" +
            "• next(): O(1)<br/>" +
            "• hasNext(): O(1)</html>");
        instructionLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        panel.add(instructionLabel);
        
        return panel;
    }
    
    private void loadDefaultData() {
        arrayField.setText("1,2,3,4,5");
    }
    
    private void initializeIterator() {
        try {
            String input = arrayField.getText().trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入数组", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String[] parts = input.split(",");
            originalList = new ArrayList<>();
            
            for (String part : parts) {
                originalList.add(Integer.parseInt(part.trim()));
            }
            
            if (originalList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "数组不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // 创建PeekingIterator
            peekingIterator = new PeekingIterator(originalList.iterator());
            currentPosition = 0;
            lastPeekedValue = null;
            lastNextValue = null;
            lastHasNextResult = false;
            operationHistory.clear();
            
            // 启用操作按钮
            peekButton.setEnabled(true);
            nextButton.setEnabled(true);
            hasNextButton.setEnabled(true);
            
            updateStateLabel();
            addLog("初始化PeekingIterator，数组: " + originalList);
            operationHistory.add("初始化: " + originalList);
            SwingUtilities.invokeLater(() -> visualPanel.repaint());
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void performPeek() {
        if (peekingIterator == null) {
            JOptionPane.showMessageDialog(this, "请先初始化迭代器", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            if (!peekingIterator.hasNext()) {
                JOptionPane.showMessageDialog(this, "迭代器已到达末尾", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            lastPeekedValue = peekingIterator.peek();
            addLog("peek() 返回: " + lastPeekedValue + " (不移动迭代器)");
            operationHistory.add("peek() -> " + lastPeekedValue);
            
            updateStateLabel();
            SwingUtilities.invokeLater(() -> visualPanel.repaint());
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "peek操作失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void performNext() {
        if (peekingIterator == null) {
            JOptionPane.showMessageDialog(this, "请先初始化迭代器", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            if (!peekingIterator.hasNext()) {
                JOptionPane.showMessageDialog(this, "迭代器已到达末尾", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            lastNextValue = peekingIterator.next();
            currentPosition++;
            lastPeekedValue = null; // 清除peek状态
            
            addLog("next() 返回: " + lastNextValue + " (移动迭代器)");
            operationHistory.add("next() -> " + lastNextValue);
            
            updateStateLabel();
            SwingUtilities.invokeLater(() -> visualPanel.repaint());
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "next操作失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void performHasNext() {
        if (peekingIterator == null) {
            JOptionPane.showMessageDialog(this, "请先初始化迭代器", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            lastHasNextResult = peekingIterator.hasNext();
            addLog("hasNext() 返回: " + lastHasNextResult);
            operationHistory.add("hasNext() -> " + lastHasNextResult);
            
            updateStateLabel();
            
            String message = "hasNext() 返回: " + lastHasNextResult;
            JOptionPane.showMessageDialog(this, message, "hasNext结果", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "hasNext操作失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateStateLabel() {
        if (peekingIterator == null) {
            stateLabel.setText("<html>状态: 未初始化</html>");
            return;
        }
        
        String stateInfo = peekingIterator.getStateInfo();
        String positionInfo = "当前位置: " + currentPosition + "/" + originalList.size();
        String lastOpInfo = "";
        
        if (lastPeekedValue != null) {
            lastOpInfo += "<br/>最近peek: " + lastPeekedValue;
        }
        if (lastNextValue != null) {
            lastOpInfo += "<br/>最近next: " + lastNextValue;
        }
        
        stateLabel.setText("<html>" + stateInfo + "<br/>" + positionInfo + lastOpInfo + "</html>");
    }
    
    private void reset() {
        peekingIterator = null;
        originalList = null;
        currentPosition = 0;
        lastPeekedValue = null;
        lastNextValue = null;
        lastHasNextResult = false;
        operationHistory.clear();
        
        // 禁用操作按钮
        peekButton.setEnabled(false);
        nextButton.setEnabled(false);
        hasNextButton.setEnabled(false);
        
        updateStateLabel();
        addLog("重置迭代器状态");
        SwingUtilities.invokeLater(() -> visualPanel.repaint());
    }
    
    private void loadDemoData() {
        String[] demos = {"1,2,3,4,5", "10,20,30", "7,14,21,28"};
        int choice = JOptionPane.showOptionDialog(this, 
            "选择演示数据:", "演示数据", 
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
            demos, demos[0]);
        
        if (choice >= 0) {
            arrayField.setText(demos[choice]);
            addLog("加载演示数据: " + demos[choice]);
        }
    }
    
    private void drawVisualization(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (originalList == null) {
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
            g2d.setColor(Color.GRAY);
            String message = "请先初始化迭代器";
            FontMetrics fm = g2d.getFontMetrics();
            int x = (visualPanel.getWidth() - fm.stringWidth(message)) / 2;
            int y = visualPanel.getHeight() / 2;
            g2d.drawString(message, x, y);
            return;
        }
        
        drawArray(g2d);
        drawIteratorState(g2d);
        drawOperationHistory(g2d);
    }
    
    private void drawArray(Graphics2D g2d) {
        int n = originalList.size();
        int totalWidth = n * ELEMENT_WIDTH + (n - 1) * 10;
        int startX = (visualPanel.getWidth() - totalWidth) / 2;
        int startY = 80;
        
        // 绘制标题
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("数组: " + originalList, 20, 30);
        
        // 绘制数组元素
        for (int i = 0; i < n; i++) {
            int x = startX + i * (ELEMENT_WIDTH + 10);
            int y = startY;
            
            // 确定颜色
            Color elementColor;
            if (i < currentPosition) {
                elementColor = CONSUMED_COLOR; // 已消费
            } else if (i == currentPosition && peekingIterator != null && 
                      peekingIterator.getStateInfo().contains("true")) {
                elementColor = PEEKED_COLOR; // 已peek但未消费
            } else if (i == currentPosition) {
                elementColor = CURRENT_COLOR; // 当前位置
            } else {
                elementColor = NORMAL_COLOR; // 未访问
            }
            
            // 绘制元素框
            g2d.setColor(elementColor);
            g2d.fillRect(x, y, ELEMENT_WIDTH, ELEMENT_HEIGHT);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, ELEMENT_WIDTH, ELEMENT_HEIGHT);
            
            // 绘制元素值
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
            String value = String.valueOf(originalList.get(i));
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (ELEMENT_WIDTH - fm.stringWidth(value)) / 2;
            int textY = y + (ELEMENT_HEIGHT + fm.getAscent()) / 2;
            g2d.drawString(value, textX, textY);
            
            // 绘制索引
            g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
            String index = String.valueOf(i);
            int indexX = x + (ELEMENT_WIDTH - g2d.getFontMetrics().stringWidth(index)) / 2;
            g2d.drawString(index, indexX, y + ELEMENT_HEIGHT + 15);
        }
        
        // 绘制迭代器指针
        if (currentPosition < n) {
            int pointerX = startX + currentPosition * (ELEMENT_WIDTH + 10) + ELEMENT_WIDTH / 2;
            int pointerY = startY - 20;
            
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(2));
            
            // 绘制箭头
            int[] xPoints = {pointerX - 5, pointerX + 5, pointerX};
            int[] yPoints = {pointerY, pointerY, pointerY + 10};
            g2d.fillPolygon(xPoints, yPoints, 3);
            
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
            g2d.drawString("迭代器", pointerX - 15, pointerY - 5);
        }
    }
    
    private void drawIteratorState(Graphics2D g2d) {
        if (peekingIterator == null) return;
        
        int stateY = 160;
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        g2d.setColor(Color.BLUE);
        g2d.drawString("迭代器内部状态:", 20, stateY);
        
        g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        g2d.setColor(Color.BLACK);
        g2d.drawString(peekingIterator.getStateInfo(), 20, stateY + 20);
        
        // 绘制最近操作结果
        if (lastPeekedValue != null) {
            g2d.setColor(Color.ORANGE);
            g2d.drawString("最近peek结果: " + lastPeekedValue, 20, stateY + 40);
        }
        
        if (lastNextValue != null) {
            g2d.setColor(Color.GREEN);
            g2d.drawString("最近next结果: " + lastNextValue, 20, stateY + 60);
        }
    }
    
    private void drawOperationHistory(Graphics2D g2d) {
        if (operationHistory.isEmpty()) return;
        
        int historyY = 240;
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawString("操作历史:", 20, historyY);
        
        g2d.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
        int y = historyY + 20;
        int maxDisplay = Math.min(3, operationHistory.size());
        
        for (int i = operationHistory.size() - maxDisplay; i < operationHistory.size(); i++) {
            g2d.drawString((i + 1) + ". " + operationHistory.get(i), 30, y);
            y += 15;
        }
        
        if (operationHistory.size() > 3) {
            g2d.setColor(Color.GRAY);
            g2d.drawString("... (显示最近3个操作)", 30, y);
        }
    }
    
    private void addLog(String message) {
        logArea.append("[" + java.time.LocalTime.now().toString().substring(0, 8) + "] " + message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO284_N_PeekingIterator_Animation().setVisible(true);
        });
    }
}