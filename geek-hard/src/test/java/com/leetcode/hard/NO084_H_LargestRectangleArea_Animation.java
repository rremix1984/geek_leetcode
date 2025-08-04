package com.leetcode.hard;

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
                newHeights[newHeights.length - 1] = height;
                heights = newHeights;
                
                updateDisplay();
                resultLabel.setText("结果: 添加高度 " + height);
                heightField.setText("");
            } else {
                resultLabel.setText("错误: 请输入正整数");
            }
        } catch (NumberFormatException ex) {
            resultLabel.setText("错误: 请输入有效数字");
        }
    }
    
    private void initializeDefaultData() {
        // 初始化默认数据 [2, 1, 5, 6, 2, 3]
        heights = new int[]{2, 1, 5, 6, 2, 3};
        updateDisplay();
    }
    
    private void startAnimation() {
        if (heights == null || heights.length == 0) {
            resultLabel.setText("错误: 请添加至少一个柱子");
            return;
        }
        
        isAnimating = true;
        currentIndex = 0;
        maxArea = 0;
        stack = new Stack<>();
        stack.push(-1);
        currentHistogram = heights.clone();
        
        startButton.setText("停止动画");
        stepButton.setEnabled(true);
        addHeightButton.setEnabled(false);
        
        animationTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!executeNextStep()) {
                    stopAnimation();
                    resultLabel.setText("计算完成! 最大矩形面积: " + maxArea);
                }
            }
        });
        animationTimer.start();
        
        resultLabel.setText("开始查找最大矩形面积...");
        updateDisplay();
    }
    
    private boolean executeNextStep() {
        if (currentIndex <= heights.length) {
            while (!stack.isEmpty() && (currentIndex == heights.length || heights[currentIndex] <= heights[stack.peek()])) {
                int height = heights[stack.pop()];
                int width = (stack.isEmpty() ? currentIndex : currentIndex - stack.peek() - 1);
                int area = height * width;
                maxArea = Math.max(maxArea, area);
                resultLabel.setText("步骤: 高度 = " + height + ", 宽度 = " + width + " → 面积 = " + area + ", 最大面积 = " + maxArea);
                
                if (currentIndex < heights.length) {
                    currentHistogram[currentIndex - 1] = height;
                }
                updateDisplay();
                return true;
            }
            
            if (currentIndex < heights.length) {
                stack.push(currentIndex);
                currentIndex++;
            }
        } else {
            return false; // 动画结束
        }
        return true;
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
        
        initializeDefaultData();
        resultLabel.setText("结果: 已重置");
    }
    
    private void updateDisplay() {
        repaint();
    }
    
    // 矩形最大面积可视化面板
    private class RectangleVisualizationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (heights == null) return;
            
            drawHistogram(g2d);
            drawCurrentSteps(g2d);
            drawAlgorithmInfo(g2d);
        }
        
        private void drawHistogram(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            g2d.drawString("柱状图:", 50, 50);
            
            int startX = 50;
            int startY = 350;
            int barWidth = 40;
            int maxHeight = getMaxHeight();
            int scale = 200 / Math.max(maxHeight, 1);
            
            for (int i = 0; i < currentHistogram.length; i++) {
                int barHeight = currentHistogram[i] * scale;
                Rectangle bar = new Rectangle(startX + i * (barWidth + 5), startY - barHeight, barWidth, barHeight);
                
                g2d.setColor(isAnimating && (i == currentIndex - 1) ? Color.RED : Color.LIGHT_GRAY);
                g2d.fill(bar);
                g2d.setColor(Color.BLACK);
                g2d.draw(bar);
                
                String heightStr = String.valueOf(currentHistogram[i]);
                FontMetrics fm = g2d.getFontMetrics();
                int textX = startX + i * (barWidth + 5) + (barWidth - fm.stringWidth(heightStr)) / 2;
                g2d.drawString(heightStr, textX, startY + 20);
            }
        }
        
        private void drawCurrentSteps(Graphics2D g2d) {
            if (!isAnimating) return;
            
            g2d.setColor(Color.BLUE);
            g2d.drawString("当前堆栈:", 50, 450);
            int x = 50;
            for (Integer index : stack) {
                if (index == -1) continue;
                String indexStr = "index " + index;
                g2d.drawString(indexStr, x, 470);
                x += 70;
            }
        }
        
        private void drawAlgorithmInfo(Graphics2D g2d) {
            int infoX = 600;
            int infoY = 50;
            
            g2d.setColor(Color.BLACK);
            g2d.drawString("算法步骤 (单调栈):", infoX, infoY);
            g2d.drawString("1. 遍历每个柱子的索引", infoX, infoY + 20);
            g2d.drawString("2. 维护一个递减栈保存柱子的高度索引", infoX, infoY + 40);
            g2d.drawString("3. 当当前柱子的高度小于或等于栈顶高度时，弹出栈顶计算面积", infoX, infoY + 60);
            g2d.drawString("4. 更新最大面积", infoX, infoY + 80);
            g2d.drawString("5. 继续到下个柱子或结束", infoX, infoY + 100);
            
            // 显示当前状态
            if (isAnimating) {
                g2d.setColor(Color.BLUE);
                g2d.drawString("当前状态:", infoX, infoY + 150);
                g2d.drawString("当前索引: " + currentIndex, infoX, infoY + 170);
                g2d.drawString("最大面积: " + maxArea, infoX, infoY + 190);
            }
            
            // 示例说明
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawString("示例: heights = [2,1,5,6,2,3]", infoX, infoY + 250);
            g2d.drawString("结果: 最大矩形面积 = 10", infoX, infoY + 270);
        }
        
        private int getMaxHeight() {
            if (heights == null) return 1;
            int max = 0;
            for (int h : heights) {
                max = Math.max(max, h);
            }
            return max;
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
