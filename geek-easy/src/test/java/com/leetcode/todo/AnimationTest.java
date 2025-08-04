package com.leetcode.todo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 动画测试程序 - 自动演示动画效果
 */
public class AnimationTest extends JFrame {
    private JPanel animationPanel;
    private int circleX = 50;
    private int circleY = 100;
    private int direction = 1;
    private Timer animationTimer;
    private Color circleColor = Color.BLUE;
    
    public AnimationTest() {
        setTitle("动画测试 - 确认Swing动画是否正常工作");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        
        initComponents();
        setupLayout();
        startAnimation();
        
        // 显示使用说明
        showInstructions();
    }
    
    private void initComponents() {
        animationPanel = new AnimationPanel();
        animationPanel.setPreferredSize(new Dimension(600, 300));
        animationPanel.setBackground(Color.WHITE);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("动画测试 - 如果你看到移动的圆圈，说明动画正常工作", JLabel.CENTER);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel controlPanel = new JPanel(new FlowLayout());
        JButton stopButton = new JButton("停止动画");
        JButton startButton = new JButton("开始动画");
        JButton colorButton = new JButton("改变颜色");
        
        stopButton.addActionListener(e -> stopAnimation());
        startButton.addActionListener(e -> startAnimation());
        colorButton.addActionListener(e -> changeColor());
        
        controlPanel.add(startButton);
        controlPanel.add(stopButton);
        controlPanel.add(colorButton);
        
        add(titleLabel, BorderLayout.NORTH);
        add(animationPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    private void startAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        
        animationTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 更新圆圈位置
                circleX += direction * 5;
                
                // 边界反弹
                if (circleX >= animationPanel.getWidth() - 50) {
                    direction = -1;
                } else if (circleX <= 0) {
                    direction = 1;
                }
                
                // 重绘面板
                animationPanel.repaint();
            }
        });
        
        animationTimer.start();
        System.out.println("动画已启动，定时器间隔: 50ms");
    }
    
    private void stopAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
            System.out.println("动画已停止");
        }
    }
    
    private void changeColor() {
        Color[] colors = {Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.MAGENTA};
        circleColor = colors[(int)(Math.random() * colors.length)];
        animationPanel.repaint();
    }
    
    private void showInstructions() {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, 
                "动画测试说明：\n\n" +
                "1. 如果你看到一个移动的圆圈，说明Swing动画正常工作\n" +
                "2. 可以使用底部的按钮控制动画\n" +
                "3. 如果没有看到动画，请检查:\n" +
                "   - Java GUI环境是否正确配置\n" +
                "   - 是否在图形界面环境中运行\n" +
                "   - macOS是否有相关权限\n\n" +
                "点击确定开始测试...", 
                "动画测试", 
                JOptionPane.INFORMATION_MESSAGE);
        });
    }
    
    // 自定义绘制面板
    private class AnimationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // 绘制移动的圆圈
            g2d.setColor(circleColor);
            g2d.fillOval(circleX, circleY, 40, 40);
            
            // 绘制边框
            g2d.setColor(Color.BLACK);
            g2d.drawOval(circleX, circleY, 40, 40);
            
            // 绘制位置信息
            g2d.setColor(Color.BLACK);
            g2d.drawString("位置: (" + circleX + ", " + circleY + ")", 10, 20);
            g2d.drawString("方向: " + (direction == 1 ? "右" : "左"), 10, 40);
            
            // 绘制参考线
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.drawLine(0, circleY + 20, getWidth(), circleY + 20);
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
                
                System.out.println("正在启动动画测试程序...");
                new AnimationTest().setVisible(true);
            }
        });
    }
}
