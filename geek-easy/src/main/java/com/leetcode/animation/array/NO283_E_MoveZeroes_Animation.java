package com.leetcode.animation.array;

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

/**
 * LeetCode 283. 移动零 - 动画演示
 * 
 * 算法思路：
 * 使用双指针法，一个指针遍历数组，另一个指针指向下一个非零元素应该放置的位置
 * 遇到非零元素时，将其移动到指定位置，并将原位置置零
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class NO283_E_MoveZeroes_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 700;
    private static final int CELL_SIZE = 60;
    private static final int CELL_SPACING = 10;
    
    // 组件
    private JPanel animationPanel;
    private JButton startButton;
    private JButton stepButton;
    private JButton resetButton;
    private JButton homeButton;
    private JLabel statusLabel;
    private JLabel complexityLabel;
    
    // 动画状态
    private int[] originalArray;
    private int[] workingArray;
    private int i; // 遍历指针
    private int j; // 非零元素位置指针
    private int step;
    private boolean isAnimating;
    private Timer animationTimer;
    
    // 动画步骤记录
    private List<String> stepDescriptions;
    
    public NO283_E_MoveZeroes_Animation() {
        initComponents();
        setupLayout();
        setupEventHandlers();
        resetAnimation();
    }
    
    private void initComponents() {
        setTitle("LeetCode 283. 移动零 - 动画演示");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        animationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawAnimation(g);
            }
        };
        animationPanel.setBackground(Color.WHITE);
        animationPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, 500));
        
        startButton = new JButton("开始动画");
        stepButton = new JButton("单步执行");
        resetButton = new JButton("重置");
        homeButton = new JButton("返回主页");
        
        statusLabel = new JLabel("准备开始演示移动零算法");
        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        
        complexityLabel = new JLabel("时间复杂度: O(n) | 空间复杂度: O(1)");
        complexityLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        complexityLabel.setForeground(Color.BLUE);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 顶部信息面板
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("LeetCode 283. 移动零", JLabel.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(complexityLabel, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);
        
        // 中央动画面板
        add(animationPanel, BorderLayout.CENTER);
        
        // 底部控制面板
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(startButton);
        controlPanel.add(stepButton);
        controlPanel.add(resetButton);
        controlPanel.add(homeButton);
        
        bottomPanel.add(controlPanel, BorderLayout.CENTER);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        startButton.addActionListener(e -> startAnimation());
        stepButton.addActionListener(e -> stepAnimation());
        resetButton.addActionListener(e -> resetAnimation());
        homeButton.addActionListener(e -> {
            if (animationTimer != null && animationTimer.isRunning()) {
                animationTimer.stop();
            }
            dispose();
            SwingUtilities.invokeLater(() -> AlgorithmTreeLauncher.showMainWindow());
        });
    }
    
    private void startAnimation() {
        if (isAnimating) {
            animationTimer.stop();
            isAnimating = false;
            startButton.setText("开始动画");
            return;
        }
        
        isAnimating = true;
        startButton.setText("暂停动画");
        
        animationTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!stepAnimation()) {
                    animationTimer.stop();
                    isAnimating = false;
                    startButton.setText("开始动画");
                }
            }
        });
        animationTimer.start();
    }
    
    private boolean stepAnimation() {
        if (step >= stepDescriptions.size()) {
            statusLabel.setText("算法执行完成！所有零已移动到数组末尾");
            return false;
        }
        
        String description = stepDescriptions.get(step);
        statusLabel.setText("步骤 " + (step + 1) + ": " + description);
        
        if (step == 0) {
            // 初始化
            i = 0;
            j = 0;
        } else if (i < workingArray.length) {
            if (workingArray[i] != 0) {
                // 非零元素，移动到j位置
                workingArray[j] = workingArray[i];
                if (i != j) {
                    workingArray[i] = 0;
                }
                j++;
            }
            i++;
        }
        
        step++;
        animationPanel.repaint();
        return step < stepDescriptions.size();
    }
    
    private void resetAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        
        isAnimating = false;
        startButton.setText("开始动画");
        
        // 初始化数组 [0,1,0,3,12]
        originalArray = new int[]{0, 1, 0, 3, 12};
        workingArray = originalArray.clone();
        
        i = 0;
        j = 0;
        step = 0;
        
        // 生成步骤描述
        generateStepDescriptions();
        
        statusLabel.setText("准备开始演示移动零算法");
        animationPanel.repaint();
    }
    
    private void generateStepDescriptions() {
        stepDescriptions = new ArrayList<>();
        stepDescriptions.add("初始化：i=0(遍历指针), j=0(非零元素位置指针)");
        
        int tempI = 0;
        int tempJ = 0;
        int[] tempArray = originalArray.clone();
        
        while (tempI < tempArray.length) {
            if (tempArray[tempI] != 0) {
                stepDescriptions.add("i=" + tempI + ": 发现非零元素 " + tempArray[tempI] + 
                    "，移动到位置 " + tempJ);
                tempJ++;
            } else {
                stepDescriptions.add("i=" + tempI + ": 发现零，跳过");
            }
            tempI++;
        }
        
        stepDescriptions.add("遍历完成，所有零已移动到末尾");
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int startX = 50;
        int originalY = 120;
        int workingY = 250;
        
        // 绘制标题和说明
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(Color.BLACK);
        g2d.drawString("原始数组: [0, 1, 0, 3, 12]", startX, 50);
        g2d.drawString("目标: 将所有零移动到数组末尾，保持非零元素相对顺序", startX, 75);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        g2d.drawString("双指针法：i遍历数组，j指向下一个非零元素应放置的位置", startX, 95);
        
        // 绘制原始数组
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("原始数组:", startX, originalY - 10);
        drawArray(g2d, originalArray, startX, originalY, false);
        
        // 绘制工作数组
        g2d.drawString("当前状态:", startX, workingY - 10);
        drawArray(g2d, workingArray, startX, workingY, true);
        
        // 绘制指针
        if (step > 0) {
            drawPointers(g2d, startX, workingY);
        }
        
        // 绘制算法状态信息
        drawAlgorithmInfo(g2d, startX, workingY + 100);
    }
    
    private void drawArray(Graphics2D g2d, int[] array, int startX, int startY, boolean showColors) {
        for (int idx = 0; idx < array.length; idx++) {
            int x = startX + idx * (CELL_SIZE + CELL_SPACING);
            
            // 确定颜色
            Color cellColor = Color.LIGHT_GRAY;
            if (showColors && step > 0) {
                if (idx < j) {
                    cellColor = Color.GREEN; // 已处理的非零元素
                } else if (idx == i && i < array.length) {
                    cellColor = Color.YELLOW; // 当前i指针位置
                } else if (array[idx] == 0) {
                    cellColor = Color.PINK; // 零元素
                }
            }
            
            // 绘制单元格
            g2d.setColor(cellColor);
            g2d.fillRect(x, startY, CELL_SIZE, CELL_SIZE);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, startY, CELL_SIZE, CELL_SIZE);
            
            // 绘制数值
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            FontMetrics fm = g2d.getFontMetrics();
            String value = String.valueOf(array[idx]);
            int textX = x + (CELL_SIZE - fm.stringWidth(value)) / 2;
            int textY = startY + (CELL_SIZE + fm.getAscent()) / 2;
            
            // 零用红色显示
            if (array[idx] == 0) {
                g2d.setColor(Color.RED);
            } else {
                g2d.setColor(Color.BLACK);
            }
            g2d.drawString(value, textX, textY);
            
            // 绘制索引
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.setColor(Color.GRAY);
            String index = String.valueOf(idx);
            int indexX = x + (CELL_SIZE - g2d.getFontMetrics().stringWidth(index)) / 2;
            g2d.drawString(index, indexX, startY - 5);
        }
    }
    
    private void drawPointers(Graphics2D g2d, int startX, int startY) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        
        // i指针（遍历指针）
        if (i < workingArray.length) {
            int iX = startX + i * (CELL_SIZE + CELL_SPACING) + CELL_SIZE / 2;
            g2d.setColor(Color.BLUE);
            g2d.drawString("i", iX - 5, startY + CELL_SIZE + 20);
            g2d.fillPolygon(new int[]{iX - 5, iX + 5, iX}, 
                           new int[]{startY + CELL_SIZE + 5, startY + CELL_SIZE + 5, startY + CELL_SIZE + 15}, 3);
        }
        
        // j指针（非零元素位置指针）
        if (j < workingArray.length) {
            int jX = startX + j * (CELL_SIZE + CELL_SPACING) + CELL_SIZE / 2;
            g2d.setColor(Color.RED);
            g2d.drawString("j", jX - 5, startY + CELL_SIZE + 40);
            g2d.fillPolygon(new int[]{jX - 5, jX + 5, jX}, 
                           new int[]{startY + CELL_SIZE + 25, startY + CELL_SIZE + 25, startY + CELL_SIZE + 35}, 3);
        }
    }
    
    private void drawAlgorithmInfo(Graphics2D g2d, int startX, int startY) {
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.BLACK);
        
        if (step > 0) {
            g2d.drawString("当前 i = " + i + " (遍历指针)", startX, startY);
            g2d.drawString("当前 j = " + j + " (非零元素位置指针)", startX, startY + 25);
            
            // 统计零的个数
            int zeroCount = 0;
            for (int num : workingArray) {
                if (num == 0) zeroCount++;
            }
            g2d.drawString("零的个数: " + zeroCount, startX, startY + 50);
        }
        
        // 绘制颜色说明
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        int legendX = startX + 400;
        
        g2d.setColor(Color.GREEN);
        g2d.fillRect(legendX, startY, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("已处理的非零元素", legendX + 20, startY + 12);
        
        g2d.setColor(Color.YELLOW);
        g2d.fillRect(legendX, startY + 25, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("当前i指针位置", legendX + 20, startY + 37);
        
        g2d.setColor(Color.PINK);
        g2d.fillRect(legendX, startY + 50, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("零元素", legendX + 20, startY + 62);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO283_E_MoveZeroes_Animation().setVisible(true);
        });
    }
}