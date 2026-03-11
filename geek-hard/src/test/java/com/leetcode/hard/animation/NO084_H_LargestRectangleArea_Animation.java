package com.leetcode.hard.animation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

/**
 * NO.84 柱状图中最大的矩形 - 动画演示
 * 演示单调栈方法找到最大矩形面积的过程
 */
public class NO084_H_LargestRectangleArea_Animation extends JFrame {
    private int[] heights;
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JTextField heightField;
    private JButton addHeightButton, startButton, resetButton, stepButton;
    private JLabel resultLabel;
    private Timer animationTimer;
    private Stack<Integer> stack;
    private boolean isAnimating = false;
    private int currentIndex = 0;
    private int maxArea = 0;
    private int[] currentHistogram;
    
    public NO084_H_LargestRectangleArea_Animation() {
        setTitle("NO.84 柱状图中最大的矩形 - 动画演示");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        initComponents();
        setupLayout();
        setupEventHandlers();
        initializeDefaultData();
    }
    
    private void initComponents() {
        mainPanel = new JPanel();
        controlPanel = new JPanel();
        
        heightField = new JTextField(5);
        addHeightButton = new JButton("添加高度");
        startButton = new JButton("开始查找");
        resetButton = new JButton("重置");
        stepButton = new JButton("单步执行");
        resultLabel = new JLabel("结果: ");
        
        // 设置字体
        Font font = new Font("微软雅黑", Font.PLAIN, 14);
        resultLabel.setFont(font);
        
        stepButton.setEnabled(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 主面板
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new RectangleVisualizationPanel(), BorderLayout.CENTER);
        
        // 控制面板
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JLabel("柱子高度:"));
        controlPanel.add(heightField);
        controlPanel.add(addHeightButton);
        controlPanel.add(startButton);
        controlPanel.add(stepButton);
        controlPanel.add(resetButton);
        controlPanel.add(resultLabel);
        
        add(mainPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        addHeightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAnimating) {
                    addHeight();
                }
            }
        });
        
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAnimating) {
                    startAnimation();
                } else {
                    stopAnimation();
                }
            }
        });
        
        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeNextStep();
            }
        });
        
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
    }
    
    private void addHeight() {
        try {
            int height = Integer.parseInt(heightField.getText());
            if (height >= 0) {
                int[] newHeights = new int[heights == null ? 1 : heights.length + 1];
                if (heights != null) {
                    System.arraycopy(heights, 0, newHeights, 0, heights.length);
                }
                newHeights[heights == null ? 0 : heights.length] = height;
                heights = newHeights;
                heightField.setText("");
                resultLabel.setText("结果: 已添加高度");
                repaint();
            }
        } catch (NumberFormatException e) {
            // ignore
        }
    }
    
    private void startAnimation() {
        if (heights == null || heights.length == 0) return;
        isAnimating = true;
        startButton.setText("停止查找");
        stepButton.setEnabled(true);
        stack = new Stack<>();
        currentHistogram = heights.clone();
        currentIndex = 0;
        maxArea = 0;
        
        animationTimer = new Timer(800, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeNextStep();
            }
        });
        animationTimer.start();
    }
    
    private void stopAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        isAnimating = false;
        startButton.setText("开始查找");
        stepButton.setEnabled(false);
        addHeightButton.setEnabled(true);
    }
    
    private void reset() {
        stopAnimation();
        heights = null;
        currentIndex = 0;
        maxArea = 0;
        currentHistogram = null;
        stack = null;
        initializeDefaultData();
        resultLabel.setText("结果: 已重置");
    }
    
    private void initializeDefaultData() {
        heights = new int[]{2, 1, 5, 6, 2, 3};
        resultLabel.setText("结果: 默认示例已加载");
        repaint();
    }
    
    private void executeNextStep() {
        if (!isAnimating) return;
        
        if (currentIndex <= heights.length) {
            int currentHeight = (currentIndex == heights.length) ? 0 : heights[currentIndex];
            
            while (!stack.isEmpty() && (currentIndex == heights.length || currentHeight < heights[stack.peek()])) {
                int height = heights[stack.pop()];
                int width = stack.isEmpty() ? currentIndex : currentIndex - stack.peek() - 1;
                maxArea = Math.max(maxArea, height * width);
            }
            
            if (currentIndex < heights.length) {
                stack.push(currentIndex);
            } else {
                stopAnimation();
            }
            
            currentIndex++;
            resultLabel.setText("结果: 最大矩形面积 = " + maxArea);
            repaint();
        }
    }
    
    private class RectangleVisualizationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (currentHistogram == null) return;
            
            int startX = 50;
            int startY = 450;
            int barWidth = 40;
            
            for (int i = 0; i < currentHistogram.length; i++) {
                int barHeight = currentHistogram[i] * 20;
                
                g2d.setColor(Color.GRAY);
                g2d.fillRect(startX + i * (barWidth + 5), startY - barHeight, barWidth, barHeight);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(startX + i * (barWidth + 5), startY - barHeight, barWidth, barHeight);
                
                if (isAnimating && i == currentIndex - 1) {
                    g2d.setColor(Color.RED);
                    g2d.setStroke(new BasicStroke(3));
                    g2d.drawRect(startX + i * (barWidth + 5), startY - barHeight, barWidth, barHeight);
                    g2d.setStroke(new BasicStroke(1));
                }
                
                g2d.setColor(Color.BLACK);
                String hStr = String.valueOf(currentHistogram[i]);
                FontMetrics fm = g2d.getFontMetrics();
                int textX = startX + i * (barWidth + 5) + (barWidth - fm.stringWidth(hStr)) / 2;
                g2d.drawString(hStr, textX, startY + 20);
            }
            
            g2d.setColor(Color.BLUE);
            g2d.drawString("最大矩形面积: " + maxArea, 600, 50);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new NO084_H_LargestRectangleArea_Animation().setVisible(true);
            }
        });
    }
}
