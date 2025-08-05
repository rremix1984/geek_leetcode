package com.animation.math;

import javax.swing.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.event.ActionEvent;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.event.ActionListener;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.util.ArrayList;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.util.List;
import com.animation.launcher.AlgorithmTreeLauncher;

/**
 * NO.066 加一 - 动画演示
 * 
 * 算法思路：
 * 1. 从数组末尾开始遍历
 * 2. 当前位加1，如果小于10则直接返回
 * 3. 如果等于10，则当前位设为0，继续向前进位
 * 4. 如果所有位都需要进位，则创建新数组，首位为1
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(1) 或 O(n)（需要创建新数组时）
 */
public class NO066_E_PlusOne_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 700;
    private static final int CELL_SIZE = 60;
    private static final int CELL_SPACING = 10;
    
    private int[] digits;
    private int[] originalDigits; // 保存原始数组，用于显示
    private int[] result;
    private int currentIndex = -1;
    private boolean isCompleted = false;
    private boolean needNewArray = false;
    private String statusMessage = "点击开始按钮开始演示";
    
    private Timer animationTimer;
    private JButton startButton;
    private JButton resetButton;
    private JComboBox<String> testCaseCombo;
    
    // 测试用例
    private final int[][] testCases = {
        {1, 2, 3},
        {4, 3, 2, 1},
        {9, 9, 9},
        {1, 2, 3, 9},
        {0}
    };
    
    private final String[] testCaseNames = {
        "示例1: [1,2,3] → [1,2,4]",
        "示例2: [4,3,2,1] → [4,3,2,2]", 
        "示例3: [9,9,9] → [1,0,0,0]",
        "示例4: [1,2,3,9] → [1,2,4,0]",
        "示例5: [0] → [1]"
    };
    
    public NO066_E_PlusOne_Animation() {
        initializeGUI();
        setupAnimation();
        loadTestCase(0);
    }
    
    private void initializeGUI() {
        setTitle("NO.066 加一 - 动画演示");
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
                repaint();
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
        animationTimer = new Timer(1500, new ActionListener() {
            private int step = 0;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (step == 0) {
                    // 开始从末尾遍历
                    currentIndex = digits.length - 1;
                    statusMessage = "从数组末尾开始，当前位置: " + currentIndex;
                } else if (step <= digits.length) {
                    // 执行加一操作
                    performPlusOneStep();
                } else {
                    // 动画完成
                    animationTimer.stop();
                    isCompleted = true;
                    statusMessage = "加一操作完成！";
                    startButton.setText("开始演示");
                }
                
                step++;
                repaint();
            }
        });
    }
    
    private void performPlusOneStep() {
        if (currentIndex >= 0) {
            digits[currentIndex]++;
            statusMessage = "位置 " + currentIndex + " 加1，值变为: " + digits[currentIndex];
            
            if (digits[currentIndex] < 10) {
                // 不需要进位，完成
                result = digits.clone();
                isCompleted = true;
                statusMessage += " (无需进位，操作完成)";
                animationTimer.stop();
                startButton.setText("开始演示");
            } else {
                // 需要进位
                digits[currentIndex] = 0;
                statusMessage = "发生进位，当前位设为0，继续向前进位";
                currentIndex--;
                
                if (currentIndex < 0) {
                    // 需要创建新数组
                    needNewArray = true;
                    result = new int[digits.length + 1];
                    result[0] = 1;
                    System.arraycopy(digits, 0, result, 1, digits.length);
                    statusMessage = "所有位都需要进位，创建新数组 [1,0,0,...]";
                    isCompleted = true;
                    animationTimer.stop();
                    startButton.setText("开始演示");
                }
            }
        }
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制标题
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 24));
        g2d.setColor(new Color(51, 51, 51));
        String title = "NO.066 加一算法演示";
        FontMetrics fm = g2d.getFontMetrics();
        int titleX = (getWidth() - fm.stringWidth(title)) / 2;
        g2d.drawString(title, titleX, 50);
        
        // 绘制原始数组
        drawArray(g2d, originalDigits, 100, "原始数组:", currentIndex, false);
        
        // 绘制结果数组（如果有）
        if (isCompleted && result != null) {
            drawArray(g2d, result, 250, "结果数组:", -1, true);
        }
        
        // 绘制算法步骤说明
        drawAlgorithmSteps(g2d);
        
        // 绘制状态信息
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(new Color(255, 69, 0));
        g2d.drawString("状态: " + statusMessage, 50, 450);
        
        // 绘制复杂度信息
        drawComplexityInfo(g2d);
    }
    
    private void drawArray(Graphics2D g2d, int[] array, int y, String label, int highlightIndex, boolean isResult) {
        // 绘制标签
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(new Color(51, 51, 51));
        g2d.drawString(label, 50, y - 10);
        
        // 计算数组绘制起始位置
        int totalWidth = array.length * CELL_SIZE + (array.length - 1) * CELL_SPACING;
        int startX = (getWidth() - totalWidth) / 2;
        
        // 绘制数组元素
        for (int i = 0; i < array.length; i++) {
            int x = startX + i * (CELL_SIZE + CELL_SPACING);
            
            // 设置颜色
            if (i == highlightIndex) {
                g2d.setColor(new Color(255, 69, 0)); // 当前操作位置
            } else if (isResult) {
                g2d.setColor(new Color(34, 139, 34)); // 结果数组
            } else {
                g2d.setColor(new Color(70, 130, 180)); // 普通元素
            }
            
            // 绘制矩形
            g2d.fillRoundRect(x, y, CELL_SIZE, CELL_SIZE, 10, 10);
            
            // 绘制边框
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRoundRect(x, y, CELL_SIZE, CELL_SIZE, 10, 10);
            
            // 绘制数字
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.setColor(Color.WHITE);
            String value = String.valueOf(array[i]);
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (CELL_SIZE - fm.stringWidth(value)) / 2;
            int textY = y + (CELL_SIZE + fm.getAscent()) / 2;
            g2d.drawString(value, textX, textY);
            
            // 绘制索引
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
            g2d.setColor(new Color(128, 128, 128));
            String index = String.valueOf(i);
            int indexX = x + (CELL_SIZE - g2d.getFontMetrics().stringWidth(index)) / 2;
            g2d.drawString(index, indexX, y + CELL_SIZE + 20);
        }
    }
    
    private void drawAlgorithmSteps(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(new Color(51, 51, 51));
        
        String[] steps = {
            "算法步骤:",
            "1. 从数组最后一位开始遍历",
            "2. 当前位加1",
            "3. 如果结果 < 10，直接返回数组",
            "4. 如果结果 = 10，当前位设为0，向前进位",
            "5. 如果所有位都进位，创建新数组[1,0,0,...]"
        };
        
        int startY = 500;
        for (int i = 0; i < steps.length; i++) {
            if (i == 0) {
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
                g2d.setColor(new Color(255, 69, 0));
            } else {
                g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                g2d.setColor(new Color(51, 51, 51));
            }
            g2d.drawString(steps[i], 50, startY + i * 20);
        }
    }
    
    private void drawComplexityInfo(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g2d.setColor(new Color(0, 100, 0));
        g2d.drawString("时间复杂度: O(n)", 600, 520);
        g2d.drawString("空间复杂度: O(1) 或 O(n)", 600, 540);
        g2d.drawString("核心技术: 数组遍历 + 进位处理", 600, 560);
    }
    
    private void loadTestCase(int index) {
        originalDigits = testCases[index].clone(); // 保存原始数组用于显示
        digits = testCases[index].clone(); // 工作数组用于计算
        result = null;
        currentIndex = -1;
        isCompleted = false;
        needNewArray = false;
        statusMessage = "已加载测试用例 " + (index + 1) + "，点击开始按钮开始演示";
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
        repaint();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO066_E_PlusOne_Animation().setVisible(true);
        });
    }
}