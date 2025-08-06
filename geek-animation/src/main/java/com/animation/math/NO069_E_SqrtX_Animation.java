package com.animation.math;

import javax.swing.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.event.ActionEvent;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.event.ActionListener;
import com.animation.launcher.AlgorithmTreeLauncher;

/**
 * NO.069 x的平方根 - 动画演示
 * 
 * 算法思路：
 * 使用二分查找法：
 * 1. 设置左边界left=0，右边界right=x
 * 2. 计算中点mid = left + (right - left) / 2
 * 3. 如果mid*mid <= x，说明答案在右半部分，left = mid + 1
 * 4. 如果mid*mid > x，说明答案在左半部分，right = mid - 1
 * 5. 重复直到left > right，返回right
 * 
 * 时间复杂度：O(log x)
 * 空间复杂度：O(1)
 */
public class NO069_E_SqrtX_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 700;
    
    private int x;
    private int left, right, mid;
    private int result = -1;
    private boolean isCompleted = false;
    private String statusMessage = "点击开始按钮开始演示";
    private String comparisonMessage = "";
    
    private Timer animationTimer;
    private JButton startButton;
    private JButton resetButton;
    private JComboBox<String> testCaseCombo;
    
    // 测试用例
    private final int[] testCases = {4, 8, 16, 25, 2147395599, 1, 0};
    private final String[] testCaseNames = {
        "示例1: x=4, sqrt=2",
        "示例2: x=8, sqrt=2", 
        "示例3: x=16, sqrt=4",
        "示例4: x=25, sqrt=5",
        "示例5: x=2147395599, sqrt=46339",
        "示例6: x=1, sqrt=1",
        "示例7: x=0, sqrt=0"
    };
    
    public NO069_E_SqrtX_Animation() {
        initializeGUI();
        setupAnimation();
        loadTestCase(0);
    }
    
    private void initializeGUI() {
        setTitle("NO.069 x的平方根 - 动画演示");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // 创建控制面板
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.setBackground(new Color(240, 248, 255));
        
        // 测试用例选择
        testCaseCombo = new JComboBox<>(testCaseNames);
        testCaseCombo.addActionListener(e -> {
            if (!animationTimer.isRunning()) {
                loadTestCase(testCaseCombo.getSelectedIndex());
                SwingUtilities.invokeLater(() -> repaint());
            }
        });
        
        startButton = new JButton("开始演示");
        startButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        startButton.addActionListener(e -> startAnimation());
        
        resetButton = new JButton("重置");
        resetButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        resetButton.addActionListener(e -> resetAnimation());
        
        controlPanel.add(new JLabel("选择测试用例:"));
        controlPanel.add(testCaseCombo);
        controlPanel.add(startButton);
        controlPanel.add(resetButton);
        
        add(controlPanel, BorderLayout.NORTH);
        
        // 创建绘图面板
        JPanel drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawAnimation(g);
            }
        };
        drawPanel.setBackground(Color.WHITE);
        add(drawPanel, BorderLayout.CENTER);
    }
    
    private void setupAnimation() {
        animationTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (left <= right) {
                    performBinarySearchStep();
                } else {
                    // 动画完成
                    animationTimer.stop();
                    isCompleted = true;
                    result = right;
                    statusMessage = "二分查找完成！x=" + x + " 的平方根是: " + result;
                    startButton.setText("开始演示");
                }
                SwingUtilities.invokeLater(() -> repaint());
            }
        });
    }
    
    private void performBinarySearchStep() {
        mid = left + (right - left) / 2;
        long midSquared = (long) mid * mid;
        
        statusMessage = String.format("当前范围: [%d, %d], 中点: %d", left, right, mid);
        comparisonMessage = String.format("计算: %d² = %d", mid, midSquared);
        
        if (midSquared <= x) {
            comparisonMessage += String.format(" ≤ %d，答案在右半部分", x);
            left = mid + 1;
        } else {
            comparisonMessage += String.format(" > %d，答案在左半部分", x);
            right = mid - 1;
        }
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制标题
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 24));
        g2d.setColor(new Color(51, 51, 51));
        String title = "NO.069 x的平方根算法演示 (二分查找法)";
        FontMetrics fm = g2d.getFontMetrics();
        int titleX = (getWidth() - fm.stringWidth(title)) / 2;
        g2d.drawString(title, titleX, 50);
        
        // 绘制问题描述
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 18));
        g2d.setColor(new Color(255, 69, 0));
        String problem = "求 x = " + x + " 的平方根（整数部分）";
        int problemX = (getWidth() - g2d.getFontMetrics().stringWidth(problem)) / 2;
        g2d.drawString(problem, problemX, 80);
        
        // 绘制数轴
        drawNumberLine(g2d);
        
        // 绘制当前状态
        drawCurrentState(g2d);
        
        // 绘制算法步骤说明
        drawAlgorithmSteps(g2d);
        
        // 绘制状态信息
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(new Color(255, 69, 0));
        g2d.drawString("状态: " + statusMessage, 50, 420);
        
        if (!comparisonMessage.isEmpty()) {
            g2d.setColor(new Color(0, 100, 0));
            g2d.drawString("比较: " + comparisonMessage, 50, 445);
        }
        
        // 绘制复杂度信息
        drawComplexityInfo(g2d);
    }
    
    private void drawNumberLine(Graphics2D g2d) {
        int lineY = 150;
        int lineStartX = 100;
        int lineEndX = 800;
        int lineLength = lineEndX - lineStartX;
        
        // 绘制数轴
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.BLACK);
        g2d.drawLine(lineStartX, lineY, lineEndX, lineY);
        
        // 计算显示范围
        int maxRange = Math.min(x + 1, 100); // 限制显示范围
        if (x > 100) {
            maxRange = (int) Math.sqrt(x) + 10;
        }
        
        // 绘制刻度
        for (int i = 0; i <= maxRange; i += Math.max(1, maxRange / 20)) {
            int tickX = lineStartX + (i * lineLength) / maxRange;
            g2d.drawLine(tickX, lineY - 5, tickX, lineY + 5);
            
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            String label = String.valueOf(i);
            FontMetrics fm = g2d.getFontMetrics();
            g2d.drawString(label, tickX - fm.stringWidth(label) / 2, lineY + 20);
        }
        
        if (!isCompleted && left <= right) {
            // 绘制搜索范围
            int leftX = lineStartX + (left * lineLength) / maxRange;
            int rightX = lineStartX + (right * lineLength) / maxRange;
            int midX = lineStartX + (mid * lineLength) / maxRange;
            
            // 绘制搜索区间
            g2d.setColor(new Color(173, 216, 230, 100));
            g2d.fillRect(leftX, lineY - 10, rightX - leftX, 20);
            
            // 绘制边界点
            g2d.setColor(Color.BLUE);
            g2d.fillOval(leftX - 5, lineY - 5, 10, 10);
            g2d.fillOval(rightX - 5, lineY - 5, 10, 10);
            
            // 绘制中点
            g2d.setColor(Color.RED);
            g2d.fillOval(midX - 5, lineY - 5, 10, 10);
            
            // 标注
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 12));
            g2d.setColor(Color.BLUE);
            g2d.drawString("L=" + left, leftX - 10, lineY - 15);
            g2d.drawString("R=" + right, rightX - 10, lineY - 15);
            g2d.setColor(Color.RED);
            g2d.drawString("M=" + mid, midX - 10, lineY + 35);
        }
        
        // 绘制结果
        if (isCompleted && result >= 0) {
            int resultX = lineStartX + (result * lineLength) / maxRange;
            g2d.setColor(new Color(34, 139, 34));
            g2d.fillOval(resultX - 8, lineY - 8, 16, 16);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            g2d.drawString("结果=" + result, resultX - 20, lineY - 20);
        }
    }
    
    private void drawCurrentState(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(new Color(51, 51, 51));
        
        int startY = 200;
        if (!isCompleted && left <= right) {
            g2d.drawString("当前搜索范围: [" + left + ", " + right + "]", 50, startY);
            g2d.drawString("中点: " + mid, 50, startY + 25);
            g2d.drawString("中点平方: " + mid + "² = " + ((long) mid * mid), 50, startY + 50);
            g2d.drawString("目标值: " + x, 50, startY + 75);
        } else if (isCompleted) {
            g2d.setColor(new Color(34, 139, 34));
            g2d.drawString("最终结果: √" + x + " = " + result, 50, startY);
            g2d.drawString("验证: " + result + "² = " + ((long) result * result) + " ≤ " + x, 50, startY + 25);
            g2d.drawString("验证: " + (result + 1) + "² = " + ((long) (result + 1) * (result + 1)) + " > " + x, 50, startY + 50);
        }
    }
    
    private void drawAlgorithmSteps(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(new Color(51, 51, 51));
        
        String[] steps = {
            "二分查找算法步骤:",
            "1. 初始化: left = 0, right = x",
            "2. 计算中点: mid = left + (right - left) / 2",
            "3. 比较 mid² 与 x:",
            "   - 如果 mid² ≤ x: left = mid + 1",
            "   - 如果 mid² > x: right = mid - 1",
            "4. 重复直到 left > right",
            "5. 返回 right 作为结果"
        };
        
        int startY = 480;
        for (int i = 0; i < steps.length; i++) {
            if (i == 0) {
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
                g2d.setColor(new Color(255, 69, 0));
            } else {
                g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                g2d.setColor(new Color(51, 51, 51));
            }
            g2d.drawString(steps[i], 50, startY + i * 18);
        }
    }
    
    private void drawComplexityInfo(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g2d.setColor(new Color(0, 100, 0));
        g2d.drawString("时间复杂度: O(log x)", 600, 500);
        g2d.drawString("空间复杂度: O(1)", 600, 520);
        g2d.drawString("核心技术: 二分查找", 600, 540);
    }
    
    private void loadTestCase(int index) {
        x = testCases[index];
        left = 0;
        right = x;
        mid = 0;
        result = -1;
        isCompleted = false;
        statusMessage = "已加载测试用例: x = " + x + "，点击开始按钮开始演示";
        comparisonMessage = "";
    }
    
    private void startAnimation() {
        if (animationTimer.isRunning()) {
            animationTimer.stop();
            startButton.setText("开始演示");
        } else {
            resetAnimation();
            animationTimer.start();
            startButton.setText("暂停");
        }
    }
    
    private void resetAnimation() {
        animationTimer.stop();
        loadTestCase(testCaseCombo.getSelectedIndex());
        startButton.setText("开始演示");
        SwingUtilities.invokeLater(() -> repaint());
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO069_E_SqrtX_Animation().setVisible(true);
        });
    }
}