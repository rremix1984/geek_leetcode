package com.leetcode.animation.datastructure;

import javax.swing.*;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.awt.*;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.awt.event.ActionEvent;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.awt.event.ActionListener;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.util.ArrayList;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.util.List;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.util.PriorityQueue;
import com.leetcode.animation.AlgorithmTreeLauncher;

/**
 * NO.703 数据流中的第K大元素 - 动画演示
 * 
 * 算法思路：
 * 1. 使用最小堆维护K个最大元素
 * 2. 当堆大小超过K时，移除最小元素
 * 3. 堆顶元素就是第K大元素
 */
public class NO703_E_KthLargest_Animation extends JFrame {
    
    private static final int ANIMATION_DELAY = 1000;
    private static final Color HEAP_COLOR = new Color(100, 149, 237);
    private static final Color CURRENT_COLOR = new Color(255, 99, 71);
    private static final Color RESULT_COLOR = new Color(50, 205, 50);
    
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
    private JTextField kField;
    private JTextField numsField;
    private JTextField addField;
    
    // 算法状态
    private PriorityQueue<Integer> heap;
    private int k;
    private List<Integer> initialNums;
    private List<Integer> addSequence;
    private List<Integer> results;
    private int currentStep;
    private boolean isPlaying;
    private Timer animationTimer;
    
    public NO703_E_KthLargest_Animation() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        resetAnimation();
        
        setTitle("NO.703 数据流中的第K大元素 - 动画演示");
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
        inputPanel.setBorder(BorderFactory.createTitledBorder("参数设置"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("K值:"), gbc);
        gbc.gridx = 1;
        kField = new JTextField("3", 10);
        inputPanel.add(kField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("初始数组:"), gbc);
        gbc.gridx = 1;
        numsField = new JTextField("4,5,8,2", 15);
        inputPanel.add(numsField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("添加序列:"), gbc);
        gbc.gridx = 1;
        addField = new JTextField("3,5,10,9,4", 15);
        inputPanel.add(addField, gbc);
        
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
        if (currentStep == 0) {
            // 初始化阶段
            initializeKthLargest();
            currentStep++;
        } else if (currentStep - 1 < addSequence.size()) {
            // 添加元素阶段
            int value = addSequence.get(currentStep - 1);
            int result = addValue(value);
            results.add(result);
            currentStep++;
        } else {
            // 演示结束
            animationTimer.stop();
            isPlaying = false;
            startButton.setText("开始演示");
            logArea.append("\\n演示完成！\\n");
        }
        
        animationPanel.repaint();
    }
    
    private void resetAnimation() {
        animationTimer.stop();
        isPlaying = false;
        currentStep = 0;
        startButton.setText("开始演示");
        
        heap = new PriorityQueue<>();
        results = new ArrayList<>();
        
        logArea.setText("数据流中的第K大元素算法演示\\n");
        logArea.append("=================================\\n");
        logArea.append("算法说明：\\n");
        logArea.append("1. 使用最小堆维护K个最大元素\\n");
        logArea.append("2. 当堆大小超过K时，移除最小元素\\n");
        logArea.append("3. 堆顶元素就是第K大元素\\n\\n");
        
        animationPanel.repaint();
    }
    
    private void parseInput() {
        try {
            k = Integer.parseInt(kField.getText().trim());
            
            String[] numsStr = numsField.getText().trim().split(",");
            initialNums = new ArrayList<>();
            for (String s : numsStr) {
                if (!s.trim().isEmpty()) {
                    initialNums.add(Integer.parseInt(s.trim()));
                }
            }
            
            String[] addStr = addField.getText().trim().split(",");
            addSequence = new ArrayList<>();
            for (String s : addStr) {
                if (!s.trim().isEmpty()) {
                    addSequence.add(Integer.parseInt(s.trim()));
                }
            }
            
            logArea.append("输入解析完成：\\n");
            logArea.append("K = " + k + "\\n");
            logArea.append("初始数组: " + initialNums + "\\n");
            logArea.append("添加序列: " + addSequence + "\\n\\n");
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "输入格式错误，请检查输入！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void initializeKthLargest() {
        heap = new PriorityQueue<>();
        
        logArea.append("初始化 KthLargest(k=" + k + ", nums=" + initialNums + ")\\n");
        
        for (int num : initialNums) {
            heap.offer(num);
            if (heap.size() > k) {
                int removed = heap.poll();
                logArea.append("添加 " + num + "，堆大小超过K，移除最小元素 " + removed + "\\n");
            } else {
                logArea.append("添加 " + num + " 到堆中\\n");
            }
        }
        
        logArea.append("初始化完成，当前堆: " + heap + "\\n");
        logArea.append("当前第" + k + "大元素: " + (heap.isEmpty() ? "无" : heap.peek()) + "\\n\\n");
    }
    
    private int addValue(int val) {
        logArea.append("添加元素: " + val + "\\n");
        
        heap.offer(val);
        logArea.append("将 " + val + " 加入堆\\n");
        
        if (heap.size() > k) {
            int removed = heap.poll();
            logArea.append("堆大小超过K=" + k + "，移除最小元素 " + removed + "\\n");
        }
        
        int result = heap.peek();
        logArea.append("当前堆: " + heap + "\\n");
        logArea.append("第" + k + "大元素: " + result + "\\n\\n");
        
        return result;
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = animationPanel.getWidth();
        int height = animationPanel.getHeight();
        
        // 绘制标题
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 20));
        g2d.setColor(Color.BLACK);
        String title = "数据流中的第K大元素 (K=" + k + ")";
        FontMetrics fm = g2d.getFontMetrics();
        int titleX = (width - fm.stringWidth(title)) / 2;
        g2d.drawString(title, titleX, 30);
        
        if (heap == null) return;
        
        // 绘制堆的可视化
        drawHeap(g2d, width, height);
        
        // 绘制结果序列
        drawResults(g2d, width, height);
    }
    
    private void drawHeap(Graphics2D g2d, int width, int height) {
        if (heap.isEmpty()) return;
        
        // 将堆转换为数组进行可视化
        Integer[] heapArray = heap.toArray(new Integer[0]);
        
        int startY = 80;
        int heapHeight = 150;
        int nodeSize = 40;
        
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        
        // 绘制堆标题
        g2d.setColor(Color.BLACK);
        g2d.drawString("最小堆 (维护" + k + "个最大元素):", 50, startY - 10);
        
        // 绘制堆节点
        int x = 100;
        for (int i = 0; i < heapArray.length; i++) {
            // 绘制节点
            if (i == 0) {
                g2d.setColor(RESULT_COLOR); // 堆顶用绿色
            } else {
                g2d.setColor(HEAP_COLOR);
            }
            g2d.fillOval(x, startY, nodeSize, nodeSize);
            
            g2d.setColor(Color.WHITE);
            String value = heapArray[i].toString();
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (nodeSize - fm.stringWidth(value)) / 2;
            int textY = startY + (nodeSize + fm.getAscent()) / 2;
            g2d.drawString(value, textX, textY);
            
            x += nodeSize + 10;
        }
        
        // 绘制说明
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        g2d.drawString("绿色节点：第" + k + "大元素 (堆顶)", 100, startY + nodeSize + 20);
        g2d.drawString("蓝色节点：堆中其他元素", 100, startY + nodeSize + 35);
    }
    
    private void drawResults(Graphics2D g2d, int width, int height) {
        if (results.isEmpty()) return;
        
        int startY = 280;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("添加操作结果:", 50, startY);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        int y = startY + 25;
        
        for (int i = 0; i < results.size() && i < addSequence.size(); i++) {
            String text = "add(" + addSequence.get(i) + ") → " + results.get(i);
            if (i == results.size() - 1) {
                g2d.setColor(CURRENT_COLOR);
            } else {
                g2d.setColor(Color.BLACK);
            }
            g2d.drawString(text, 70, y);
            y += 20;
        }
    }
    
    private void returnToHome() {
        // 关闭当前窗口
        this.dispose();
        
        // 重新启动主界面
        SwingUtilities.invokeLater(() -> {
            try {
                dispose(); // 关闭当前动画窗口
                com.leetcode.animation.AlgorithmTreeLauncher.showMainWindow();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO703_E_KthLargest_Animation().setVisible(true);
        });
    }
}