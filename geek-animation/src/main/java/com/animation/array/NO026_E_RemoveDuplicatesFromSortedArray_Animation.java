package com.animation.array;

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
 * LeetCode 26. 删除有序数组中的重复项 - 动画演示
 * 
 * 算法思路：
 * 使用双指针法，slow指针指向不重复元素的位置，fast指针遍历数组
 * 当fast指向的元素与slow指向的元素不同时，将fast的元素复制到slow+1位置
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class NO026_E_RemoveDuplicatesFromSortedArray_Animation extends JFrame {
    
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
    private int slow;
    private int fast;
    private int step;
    private boolean isAnimating;
    private Timer animationTimer;
    
    // 动画步骤记录
    private List<String> stepDescriptions;
    
    public NO026_E_RemoveDuplicatesFromSortedArray_Animation() {
        initComponents();
        setupLayout();
        setupEventHandlers();
        resetAnimation();
    }
    
    private void initComponents() {
        setTitle("LeetCode 26. 删除有序数组中的重复项 - 动画演示");
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
        animationPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, 400));
        
        startButton = new JButton("开始动画");
        stepButton = new JButton("单步执行");
        resetButton = new JButton("重置");
        homeButton = new JButton("返回主页");
        
        statusLabel = new JLabel("准备开始演示删除有序数组中的重复项算法");
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
        
        JLabel titleLabel = new JLabel("LeetCode 26. 删除有序数组中的重复项", JLabel.CENTER);
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
            statusLabel.setText("算法执行完成！最终长度为: " + (slow + 1));
            return false;
        }
        
        String description = stepDescriptions.get(step);
        statusLabel.setText("步骤 " + (step + 1) + ": " + description);
        
        if (step == 0) {
            // 初始化
            slow = 0;
            fast = 1;
        } else if (fast < workingArray.length) {
            if (workingArray[fast] != workingArray[slow]) {
                slow++;
                workingArray[slow] = workingArray[fast];
            }
            fast++;
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
        
        // 初始化数组 [1,1,2,2,3,3,4]
        originalArray = new int[]{1, 1, 2, 2, 3, 3, 4};
        workingArray = originalArray.clone();
        
        slow = 0;
        fast = 1;
        step = 0;
        
        // 生成步骤描述
        generateStepDescriptions();
        
        statusLabel.setText("准备开始演示删除有序数组中的重复项算法");
        animationPanel.repaint();
    }
    
    private void generateStepDescriptions() {
        stepDescriptions = new ArrayList<>();
        stepDescriptions.add("初始化：slow=0, fast=1，开始双指针遍历");
        
        int tempSlow = 0;
        int tempFast = 1;
        int[] tempArray = originalArray.clone();
        
        while (tempFast < tempArray.length) {
            if (tempArray[tempFast] != tempArray[tempSlow]) {
                tempSlow++;
                stepDescriptions.add("发现不同元素 " + tempArray[tempFast] + 
                    "，将其移动到位置 " + tempSlow);
            } else {
                stepDescriptions.add("元素 " + tempArray[tempFast] + 
                    " 与 " + tempArray[tempSlow] + " 相同，跳过");
            }
            tempFast++;
        }
        
        stepDescriptions.add("遍历完成，返回新长度: " + (tempSlow + 1));
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int startX = 50;
        int arrayY = 150;
        
        // 绘制标题
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(Color.BLACK);
        g2d.drawString("原始数组: [1,1,2,2,3,3,4]", startX, 50);
        g2d.drawString("目标: 删除重复项，返回新长度", startX, 80);
        
        // 绘制算法说明
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        g2d.drawString("双指针法：slow指向不重复元素位置，fast遍历数组", startX, 110);
        
        // 绘制数组
        for (int i = 0; i < workingArray.length; i++) {
            int x = startX + i * (CELL_SIZE + CELL_SPACING);
            
            // 确定颜色
            Color cellColor = Color.LIGHT_GRAY;
            if (step > 0) {
                if (i <= slow) {
                    cellColor = Color.GREEN; // 已处理的不重复元素
                } else if (i == fast && fast < workingArray.length) {
                    cellColor = Color.YELLOW; // 当前fast指针位置
                }
            }
            
            // 绘制单元格
            g2d.setColor(cellColor);
            g2d.fillRect(x, arrayY, CELL_SIZE, CELL_SIZE);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, arrayY, CELL_SIZE, CELL_SIZE);
            
            // 绘制数值
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            FontMetrics fm = g2d.getFontMetrics();
            String value = String.valueOf(workingArray[i]);
            int textX = x + (CELL_SIZE - fm.stringWidth(value)) / 2;
            int textY = arrayY + (CELL_SIZE + fm.getAscent()) / 2;
            g2d.drawString(value, textX, textY);
            
            // 绘制索引
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.setColor(Color.GRAY);
            String index = String.valueOf(i);
            int indexX = x + (CELL_SIZE - g2d.getFontMetrics().stringWidth(index)) / 2;
            g2d.drawString(index, indexX, arrayY - 5);
        }
        
        // 绘制指针
        if (step > 0) {
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            
            // slow指针
            int slowX = startX + slow * (CELL_SIZE + CELL_SPACING) + CELL_SIZE / 2;
            g2d.setColor(Color.RED);
            g2d.drawString("slow", slowX - 15, arrayY + CELL_SIZE + 20);
            g2d.fillPolygon(new int[]{slowX - 5, slowX + 5, slowX}, 
                           new int[]{arrayY + CELL_SIZE + 5, arrayY + CELL_SIZE + 5, arrayY + CELL_SIZE + 15}, 3);
            
            // fast指针
            if (fast < workingArray.length) {
                int fastX = startX + fast * (CELL_SIZE + CELL_SPACING) + CELL_SIZE / 2;
                g2d.setColor(Color.BLUE);
                g2d.drawString("fast", fastX - 15, arrayY + CELL_SIZE + 40);
                g2d.fillPolygon(new int[]{fastX - 5, fastX + 5, fastX}, 
                               new int[]{arrayY + CELL_SIZE + 25, arrayY + CELL_SIZE + 25, arrayY + CELL_SIZE + 35}, 3);
            }
        }
        
        // 绘制结果信息
        if (step > 0) {
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            g2d.setColor(Color.BLACK);
            g2d.drawString("当前新长度: " + (slow + 1), startX, arrayY + 100);
            
            // 绘制颜色说明
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
            int legendY = arrayY + 130;
            
            g2d.setColor(Color.GREEN);
            g2d.fillRect(startX, legendY, 15, 15);
            g2d.setColor(Color.BLACK);
            g2d.drawString("已处理的不重复元素", startX + 20, legendY + 12);
            
            g2d.setColor(Color.YELLOW);
            g2d.fillRect(startX + 200, legendY, 15, 15);
            g2d.setColor(Color.BLACK);
            g2d.drawString("当前fast指针位置", startX + 220, legendY + 12);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO026_E_RemoveDuplicatesFromSortedArray_Animation().setVisible(true);
        });
    }
}