package com.animation.bit;

import javax.swing.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.event.ActionEvent;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.event.ActionListener;
import com.animation.launcher.AlgorithmTreeLauncher;

/**
 * NO.190 颠倒二进制位 - 动画演示
 * 
 * 算法描述：
 * 颠倒给定的 32 位无符号整数的二进制位。
 * 
 * 动画特色：
 * • 可视化二进制位的逐位处理过程
 * • 显示位移操作和位运算
 * • 展示结果的构建过程
 * • 动态显示原数和结果数的二进制表示
 */
public class NO190_E_ReverseBits_Animation extends JFrame {
    
    // 动画控制
    private Timer animationTimer;
    private int currentStep = 0;
    private boolean isAnimating = false;
    
    // 算法数据
    private int originalNumber = 43261596; // 示例数字
    private String originalBinary;
    private int result = 0;
    private int currentBit = 0;
    private int processedBits = 0;
    
    // UI组件
    private JPanel controlPanel;
    private JButton startButton, stepButton, resetButton, homeButton;
    private JLabel statusLabel;
    private BitsVisualizationPanel visualPanel;
    
    public NO190_E_ReverseBits_Animation() {
        setTitle("NO.190 颠倒二进制位 - 动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        
        initializeData();
        initComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initializeData() {
        originalBinary = String.format("%32s", Integer.toBinaryString(originalNumber)).replace(' ', '0');
        result = 0;
        currentBit = 0;
        processedBits = 0;
    }
    
    private void initComponents() {
        // 控制面板
        controlPanel = new JPanel();
        startButton = new JButton("开始动画");
        stepButton = new JButton("单步执行");
        resetButton = new JButton("重置");
        homeButton = new JButton("返回首页");
        statusLabel = new JLabel("点击开始按钮启动动画演示");
        
        // 设置字体
        Font buttonFont = new Font("微软雅黑", Font.PLAIN, 14);
        startButton.setFont(buttonFont);
        stepButton.setFont(buttonFont);
        resetButton.setFont(buttonFont);
        homeButton.setFont(buttonFont);
        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        
        // 可视化面板
        visualPanel = new BitsVisualizationPanel();
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 控制面板布局
        controlPanel.add(startButton);
        controlPanel.add(stepButton);
        controlPanel.add(resetButton);
        controlPanel.add(homeButton);
        controlPanel.add(Box.createHorizontalStrut(20));
        controlPanel.add(statusLabel);
        
        add(controlPanel, BorderLayout.NORTH);
        add(visualPanel, BorderLayout.CENTER);
    }
    
    private void setupEventHandlers() {
        startButton.addActionListener(e -> {
            if (!isAnimating) {
                startAnimation();
            } else {
                stopAnimation();
            }
        });
        
        stepButton.addActionListener(e -> {
            if (!isAnimating) {
                stepExecution();
            }
        });
        
        resetButton.addActionListener(e -> resetAnimation());
        
        homeButton.addActionListener(e -> {
            dispose();
             SwingUtilities.invokeLater(() -> {
                 AlgorithmTreeLauncher.showMainWindow();
             });
        });
    }
    
    private void startAnimation() {
        isAnimating = true;
        startButton.setText("停止动画");
        
        animationTimer = new Timer(800, e -> stepExecution());
        animationTimer.start();
    }
    
    private void stopAnimation() {
        isAnimating = false;
        startButton.setText("开始动画");
        
        if (animationTimer != null) {
            animationTimer.stop();
        }
    }
    
    private void stepExecution() {
        if (processedBits >= 32) {
            statusLabel.setText("算法执行完成！结果：" + result + " (二进制：" + 
                              String.format("%32s", Integer.toBinaryString(result)).replace(' ', '0') + ")");
            stopAnimation();
            return;
        }
        
        // 获取当前位
        currentBit = (originalNumber >>> processedBits) & 1;
        
        // 将当前位放到结果的对应位置
        result |= (currentBit << (31 - processedBits));
        
        statusLabel.setText("处理第 " + processedBits + " 位：" + currentBit + 
                          " -> 放到第 " + (31 - processedBits) + " 位");
        
        processedBits++;
        SwingUtilities.invokeLater(() -> visualPanel.repaint());
    }
    
    private void resetAnimation() {
        stopAnimation();
        currentStep = 0;
        result = 0;
        currentBit = 0;
        processedBits = 0;
        
        statusLabel.setText("点击开始按钮启动动画演示");
        SwingUtilities.invokeLater(() -> visualPanel.repaint());
    }
    
    // 二进制位可视化面板
    private class BitsVisualizationPanel extends JPanel {
        private final int BIT_SIZE = 25;
        private final int BIT_SPACING = 30;
        private final int START_X = 50;
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            drawTitle(g2d);
            drawOriginalBits(g2d);
            drawResultBits(g2d);
            drawCurrentOperation(g2d);
            drawNumbers(g2d);
        }
        
        private void drawTitle(Graphics2D g2d) {
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 18));
            g2d.setColor(Color.BLACK);
            g2d.drawString("二进制位颠倒过程演示", START_X, 30);
        }
        
        private void drawOriginalBits(Graphics2D g2d) {
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            g2d.setColor(Color.BLACK);
            g2d.drawString("原始数字的二进制表示：", START_X, 80);
            
            // 绘制位索引
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            for (int i = 0; i < 32; i++) {
                int x = START_X + i * BIT_SPACING;
                g2d.setColor(Color.GRAY);
                g2d.drawString(String.valueOf(31 - i), x + 8, 100);
            }
            
            // 绘制二进制位
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            for (int i = 0; i < 32; i++) {
                int x = START_X + i * BIT_SPACING;
                int y = 120;
                
                char bit = originalBinary.charAt(i);
                
                // 高亮当前处理的位
                if (i == processedBits && processedBits < 32) {
                    g2d.setColor(Color.RED);
                    g2d.fillRect(x, y - 15, BIT_SIZE, BIT_SIZE);
                    g2d.setColor(Color.WHITE);
                } else if (i < processedBits) {
                    g2d.setColor(Color.LIGHT_GRAY);
                    g2d.fillRect(x, y - 15, BIT_SIZE, BIT_SIZE);
                    g2d.setColor(Color.BLACK);
                } else {
                    g2d.setColor(bit == '1' ? Color.BLUE : Color.WHITE);
                    g2d.fillRect(x, y - 15, BIT_SIZE, BIT_SIZE);
                    g2d.setColor(Color.BLACK);
                }
                
                g2d.drawRect(x, y - 15, BIT_SIZE, BIT_SIZE);
                g2d.drawString(String.valueOf(bit), x + 8, y);
            }
        }
        
        private void drawResultBits(Graphics2D g2d) {
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            g2d.setColor(Color.BLACK);
            g2d.drawString("颠倒后的二进制表示：", START_X, 200);
            
            String resultBinary = String.format("%32s", Integer.toBinaryString(result)).replace(' ', '0');
            
            // 绘制位索引
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            for (int i = 0; i < 32; i++) {
                int x = START_X + i * BIT_SPACING;
                g2d.setColor(Color.GRAY);
                g2d.drawString(String.valueOf(31 - i), x + 8, 220);
            }
            
            // 绘制二进制位
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            for (int i = 0; i < 32; i++) {
                int x = START_X + i * BIT_SPACING;
                int y = 240;
                
                char bit = resultBinary.charAt(i);
                
                // 高亮当前设置的位
                if (processedBits > 0 && i == (31 - (processedBits - 1))) {
                    g2d.setColor(Color.GREEN);
                    g2d.fillRect(x, y - 15, BIT_SIZE, BIT_SIZE);
                    g2d.setColor(Color.WHITE);
                } else if (i <= (31 - processedBits + 1) && processedBits > 0) {
                    g2d.setColor(bit == '1' ? Color.BLUE : Color.WHITE);
                    g2d.fillRect(x, y - 15, BIT_SIZE, BIT_SIZE);
                    g2d.setColor(Color.BLACK);
                } else {
                    g2d.setColor(Color.LIGHT_GRAY);
                    g2d.fillRect(x, y - 15, BIT_SIZE, BIT_SIZE);
                    g2d.setColor(Color.GRAY);
                }
                
                g2d.drawRect(x, y - 15, BIT_SIZE, BIT_SIZE);
                if (i <= (31 - processedBits + 1) && processedBits > 0) {
                    g2d.drawString(String.valueOf(bit), x + 8, y);
                } else {
                    g2d.drawString("?", x + 8, y);
                }
            }
        }
        
        private void drawCurrentOperation(Graphics2D g2d) {
            if (processedBits > 0 && processedBits <= 32) {
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
                g2d.setColor(Color.RED);
                
                int sourcePos = processedBits - 1;
                int targetPos = 31 - sourcePos;
                
                // 绘制箭头从源位置到目标位置
                int sourceX = START_X + sourcePos * BIT_SPACING + BIT_SIZE/2;
                int sourceY = 120;
                int targetX = START_X + targetPos * BIT_SPACING + BIT_SIZE/2;
                int targetY = 240;
                
                // 绘制弧形箭头
                g2d.setStroke(new BasicStroke(2));
                g2d.setColor(Color.ORANGE);
                
                // 计算控制点
                int controlX = (sourceX + targetX) / 2;
                int controlY = (sourceY + targetY) / 2 - 50;
                
                // 绘制贝塞尔曲线
                for (int i = 0; i <= 20; i++) {
                    double t = i / 20.0;
                    int x1 = (int) ((1-t)*(1-t)*sourceX + 2*(1-t)*t*controlX + t*t*targetX);
                    int y1 = (int) ((1-t)*(1-t)*sourceY + 2*(1-t)*t*controlY + t*t*targetY);
                    
                    if (i > 0) {
                        double prevT = (i-1) / 20.0;
                        int x0 = (int) ((1-prevT)*(1-prevT)*sourceX + 2*(1-prevT)*prevT*controlX + prevT*prevT*targetX);
                        int y0 = (int) ((1-prevT)*(1-prevT)*sourceY + 2*(1-prevT)*prevT*controlY + prevT*prevT*targetY);
                        g2d.drawLine(x0, y0, x1, y1);
                    }
                }
                
                // 绘制箭头头部
                drawArrowHead(g2d, controlX, controlY, targetX, targetY);
                
                // 显示操作说明
                g2d.setColor(Color.BLACK);
                g2d.drawString("位 " + sourcePos + " -> 位 " + targetPos + " (值: " + currentBit + ")", 
                              START_X, 300);
            }
        }
        
        private void drawArrowHead(Graphics2D g2d, int x1, int y1, int x2, int y2) {
            double angle = Math.atan2(y2 - y1, x2 - x1);
            int arrowLength = 8;
            double arrowAngle = Math.PI / 6;
            
            int x3 = (int) (x2 - arrowLength * Math.cos(angle - arrowAngle));
            int y3 = (int) (y2 - arrowLength * Math.sin(angle - arrowAngle));
            int x4 = (int) (x2 - arrowLength * Math.cos(angle + arrowAngle));
            int y4 = (int) (y2 - arrowLength * Math.sin(angle + arrowAngle));
            
            g2d.drawLine(x2, y2, x3, y3);
            g2d.drawLine(x2, y2, x4, y4);
        }
        
        private void drawNumbers(Graphics2D g2d) {
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g2d.setColor(Color.BLACK);
            
            g2d.drawString("原始数字：" + originalNumber, START_X, 350);
            g2d.drawString("当前结果：" + result, START_X, 380);
            g2d.drawString("已处理位数：" + processedBits + "/32", START_X, 410);
            
            if (processedBits >= 32) {
                g2d.setColor(Color.GREEN);
                g2d.drawString("最终结果：" + result, START_X, 440);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO190_E_ReverseBits_Animation().setVisible(true);
        });
    }
}