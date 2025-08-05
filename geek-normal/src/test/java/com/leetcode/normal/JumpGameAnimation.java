package com.leetcode.normal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class JumpGameAnimation extends JFrame {
    private int[] nums;
    private int currentPosition;
    private int targetPosition;
    private int steps;
    private double animationProgress;
    private boolean isAnimating;
    private Timer animationTimer;
    private List<Integer> jumpPath;
    private int cellSize = 80;
    private int startX = 50;
    private int startY = 200;
    private Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.MAGENTA, Color.CYAN};
    
    public JumpGameAnimation() {
        this.nums = new int[]{2, 3, 1, 1, 4,2, 5, 3, 1, 2, 5, 1};
        this.currentPosition = 0;
        this.targetPosition = 0;
        this.steps = 0;
        this.animationProgress = 0;
        this.isAnimating = false;
        this.jumpPath = new ArrayList<>();
        this.jumpPath.add(0);
        
        setupUI();
        setupAnimation();
    }
    
    private void setupUI() {
        setTitle("跳跃游戏 II - 可视化演示");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel buttonPanel = new JPanel();
        JButton startButton = new JButton("开始演示");
        JButton resetButton = new JButton("重置");
        JButton stepButton = new JButton("单步执行");
        
        startButton.addActionListener(e -> startAnimation());
        resetButton.addActionListener(e -> resetAnimation());
        stepButton.addActionListener(e -> stepAnimation());
        
        buttonPanel.add(startButton);
        buttonPanel.add(stepButton);
        buttonPanel.add(resetButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void setupAnimation() {
        animationTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isAnimating) {
                    animationProgress += 0.05;
                    if (animationProgress >= 1.0) {
                        animationProgress = 1.0;
                        currentPosition = targetPosition;
                        isAnimating = false;
                        
                        // 检查是否到达终点
                        if (currentPosition >= nums.length - 1) {
                            animationTimer.stop();
                            JOptionPane.showMessageDialog(JumpGameAnimation.this, 
                                "恭喜！用 " + steps + " 步到达终点！", 
                                "完成", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            // 自动继续下一步（如果是自动模式）
                            Timer nextStepTimer = new Timer(1000, evt -> {
                                if (!isAnimating) {
                                    nextJump();
                                }
                                ((Timer) evt.getSource()).stop();
                            });
                            nextStepTimer.setRepeats(false);
                            nextStepTimer.start();
                        }
                    }
                    repaint();
                }
            }
        });
    }
    
    private void startAnimation() {
        resetAnimation();
        animationTimer.start();
        nextJump();
    }
    
    private void stepAnimation() {
        if (!isAnimating && currentPosition < nums.length - 1) {
            if (!animationTimer.isRunning()) {
                animationTimer.start();
            }
            nextJump();
        }
    }
    
    private void resetAnimation() {
        animationTimer.stop();
        currentPosition = 0;
        targetPosition = 0;
        steps = 0;
        animationProgress = 0;
        isAnimating = false;
        jumpPath.clear();
        jumpPath.add(0);
        repaint();
    }
    
    private void nextJump() {
        if (currentPosition >= nums.length - 1) return;
        
        // 使用贪心算法找到下一个最优跳跃位置
        int maxReach = 0;
        int bestPosition = currentPosition;
        
        for (int i = 1; i <= nums[currentPosition] && currentPosition + i < nums.length; i++) {
            int nextPos = currentPosition + i;
            int reach = nextPos + nums[nextPos];
            if (reach > maxReach) {
                maxReach = reach;
                bestPosition = nextPos;
            }
        }
        
        targetPosition = bestPosition;
        steps++;
        jumpPath.add(targetPosition);
        animationProgress = 0;
        isAnimating = true;
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        drawArray(g2d);
        drawJumpRanges(g2d);
        drawPath(g2d);
        drawPlayer(g2d);
        drawInfo(g2d);
    }
    
    private void drawArray(Graphics2D g2d) {
        // 绘制数组格子
        for (int i = 0; i < nums.length; i++) {
            int x = startX + i * cellSize;
            int y = startY;
            
            // 格子背景
            if (i == nums.length - 1) {
                g2d.setColor(Color.YELLOW); // 终点
            } else if (i == currentPosition) {
                g2d.setColor(Color.LIGHT_GRAY); // 当前位置
            } else {
                g2d.setColor(Color.WHITE);
            }
            g2d.fillRect(x, y, cellSize, cellSize);
            
            // 边框
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, cellSize, cellSize);
            
            // 数值
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            String text = String.valueOf(nums[i]);
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (cellSize - fm.stringWidth(text)) / 2;
            int textY = y + cellSize / 2 + fm.getAscent() / 2;
            g2d.drawString(text, textX, textY);
            
            // 索引
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.setColor(Color.GRAY);
            g2d.drawString(String.valueOf(i), x + 5, y + 15);
        }
    }
    
    private void drawJumpRanges(Graphics2D g2d) {
        if (!isAnimating && currentPosition < nums.length) {
            // 显示当前位置可以跳跃的范围
            g2d.setColor(new Color(0, 255, 0, 50));
            for (int i = 1; i <= nums[currentPosition] && currentPosition + i < nums.length; i++) {
                int x = startX + (currentPosition + i) * cellSize;
                int y = startY;
                g2d.fillRect(x, y, cellSize, cellSize);
            }
        }
    }
    
    private void drawPath(Graphics2D g2d) {
        // 绘制已经走过的路径
        g2d.setStroke(new BasicStroke(3));
        for (int i = 0; i < jumpPath.size() - 1; i++) {
            int fromX = startX + jumpPath.get(i) * cellSize + cellSize / 2;
            int fromY = startY + cellSize / 2;
            int toX = startX + jumpPath.get(i + 1) * cellSize + cellSize / 2;
            int toY = startY + cellSize / 2;
            
            g2d.setColor(colors[i % colors.length]);
            
            // 绘制弧线路径
            int midX = (fromX + toX) / 2;
            int midY = Math.min(fromY, toY) - 50;
            
            // 使用二次贝塞尔曲线绘制弧线
            drawBezierCurve(g2d, fromX, fromY, midX, midY, toX, toY);
            
            // 绘制箭头
            drawArrow(g2d, toX, toY);
        }
    }
    
    private void drawBezierCurve(Graphics2D g2d, int x1, int y1, int x2, int y2, int x3, int y3) {
        for (double t = 0; t <= 1; t += 0.01) {
            double x = (1-t)*(1-t)*x1 + 2*(1-t)*t*x2 + t*t*x3;
            double y = (1-t)*(1-t)*y1 + 2*(1-t)*t*y2 + t*t*y3;
            g2d.fillOval((int)x-1, (int)y-1, 2, 2);
        }
    }
    
    private void drawArrow(Graphics2D g2d, int x, int y) {
        int[] arrowX = {x, x-5, x+5};
        int[] arrowY = {y-10, y-20, y-20};
        g2d.fillPolygon(arrowX, arrowY, 3);
    }
    
    private void drawPlayer(Graphics2D g2d) {
        // 计算当前动画位置
        double currentX, currentY;
        if (isAnimating) {
            double startPosX = startX + currentPosition * cellSize + cellSize / 2;
            double endPosX = startX + targetPosition * cellSize + cellSize / 2;
            currentX = startPosX + (endPosX - startPosX) * animationProgress;
            
            // 跳跃弧线效果
            double jumpHeight = Math.sin(animationProgress * Math.PI) * 30;
            currentY = startY + cellSize / 2 - jumpHeight;
        } else {
            currentX = startX + currentPosition * cellSize + cellSize / 2;
            currentY = startY + cellSize / 2;
        }
        
        // 绘制小人（简单的圆形 + 身体）
        g2d.setColor(Color.RED);
        
        // 头部
        g2d.fillOval((int)currentX - 8, (int)currentY - 20, 16, 16);
        
        // 身体
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine((int)currentX, (int)currentY - 4, (int)currentX, (int)currentY + 10);
        
        // 手臂
        g2d.drawLine((int)currentX, (int)currentY - 2, (int)currentX - 8, (int)currentY + 2);
        g2d.drawLine((int)currentX, (int)currentY - 2, (int)currentX + 8, (int)currentY + 2);
        
        // 腿部
        g2d.drawLine((int)currentX, (int)currentY + 10, (int)currentX - 6, (int)currentY + 18);
        g2d.drawLine((int)currentX, (int)currentY + 10, (int)currentX + 6, (int)currentY + 18);
    }
    
    private void drawInfo(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.drawString("当前位置: " + currentPosition, 50, 50);
        g2d.drawString("当前步数: " + steps, 200, 50);
        g2d.drawString("数组: [" + arrayToString() + "]", 50, 80);
        
        if (nums[currentPosition] > 0) {
            g2d.setColor(Color.BLUE);
            g2d.drawString("可跳跃范围: 1-" + nums[currentPosition] + " 步", 50, 110);
        }
        
        // 算法说明
        g2d.setColor(Color.DARK_GRAY);
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        g2d.drawString("贪心算法: 每次选择能跳得最远的位置", 50, 400);
        g2d.drawString("绿色区域: 当前位置可跳跃的范围", 50, 420);
        g2d.drawString("红色小人: 当前位置，黄色格子: 终点", 50, 440);
    }
    
    private String arrayToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            sb.append(nums[i]);
            if (i < nums.length - 1) sb.append(", ");
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new JumpGameAnimation().setVisible(true);
        });
    }
}

