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
import java.util.Arrays;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.util.List;
import com.animation.launcher.AlgorithmTreeLauncher;

/**
 * NO.088 合并两个有序数组 - 动画演示
 * 演示双指针从后往前合并两个有序数组的过程
 * 
 * 设计文档：
 * 1. 功能需求：可视化双指针算法合并两个有序数组的过程
 * 2. 技术架构：基于Swing的动画演示系统
 * 3. 核心算法：双指针从后往前比较，避免覆盖原数据
 * 4. 边界条件：数组为空、一个数组为空、所有元素相等等情况
 * 5. 性能考虑：时间复杂度O(m+n)，空间复杂度O(1)
 */
public class NO088_E_MergeSortedArray_Animation extends JFrame {
    private List<Integer> nums1;
    private List<Integer> nums2;
    private int m, n; // 有效元素个数
    private int p1, p2, tail; // 三个指针
    private boolean isAnimating;
    private Timer animationTimer;
    
    // UI组件
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JTextField nums1Input, nums2Input;
    private JTextField mInput, nInput;
    private JButton startButton, stepButton, resetButton, autoButton;
    private JLabel statusLabel;
    private JLabel resultLabel;
    
    // 动画状态
    private boolean completed;
    private String currentOperation;
    private int step;
    private int currentValue;
    private String comparisonInfo;
    
    public NO088_E_MergeSortedArray_Animation() {
        setTitle("NO.088 合并两个有序数组 - 动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 900);
        setLocationRelativeTo(null);
        
        initData();
        initComponents();
        setupLayout();
        setupEventHandlers();
        setupAnimation();
    }
    
    private void initData() {
        nums1 = new ArrayList<>();
        nums2 = new ArrayList<>();
        m = 3;
        n = 3;
        p1 = m - 1;
        p2 = n - 1;
        tail = m + n - 1;
        isAnimating = false;
        completed = false;
        currentOperation = "准备开始";
        step = 0;
        currentValue = 0;
        comparisonInfo = "";
        
        // 默认示例数据
        nums1.addAll(Arrays.asList(1, 2, 3, 0, 0, 0));
        nums2.addAll(Arrays.asList(2, 5, 6));
    }
    
    private void initComponents() {
        mainPanel = new JPanel();
        controlPanel = new JPanel();
        
        nums1Input = new JTextField("1,2,3,0,0,0", 15);
        nums2Input = new JTextField("2,5,6", 15);
        mInput = new JTextField("3", 5);
        nInput = new JTextField("3", 5);
        startButton = new JButton("开始演示");
        stepButton = new JButton("单步执行");
        resetButton = new JButton("重置");
        autoButton = new JButton("自动演示");
        statusLabel = new JLabel("状态: 准备开始");
        resultLabel = new JLabel("结果: 未开始");
        
        // 设置字体
        Font font = new Font("微软雅黑", Font.PLAIN, 14);
        statusLabel.setFont(font);
        resultLabel.setFont(font);
        
        stepButton.setEnabled(false);
        autoButton.setEnabled(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 主面板 - 可视化区域
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new MergeArrayVisualizationPanel(), BorderLayout.CENTER);
        
        // 控制面板
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        controlPanel.add(new JLabel("nums1(逗号分隔):"), gbc);
        gbc.gridx = 1;
        controlPanel.add(nums1Input, gbc);
        gbc.gridx = 2;
        controlPanel.add(new JLabel("m:"), gbc);
        gbc.gridx = 3;
        controlPanel.add(mInput, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        controlPanel.add(new JLabel("nums2(逗号分隔):"), gbc);
        gbc.gridx = 1;
        controlPanel.add(nums2Input, gbc);
        gbc.gridx = 2;
        controlPanel.add(new JLabel("n:"), gbc);
        gbc.gridx = 3;
        controlPanel.add(nInput, gbc);
        
        gbc.gridx = 0;
        controlPanel.add(startButton, gbc);
        gbc.gridx = 1;
        controlPanel.add(stepButton, gbc);
        gbc.gridx = 2;
        controlPanel.add(autoButton, gbc);
        gbc.gridx = 3;
        controlPanel.add(resetButton, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 4;
        controlPanel.add(statusLabel, gbc);
        
        gbc.gridy = 4;
        controlPanel.add(resultLabel, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        startButton.addActionListener(e -> startDemo());
        stepButton.addActionListener(e -> stepExecution());
        autoButton.addActionListener(e -> toggleAutoDemo());
        resetButton.addActionListener(e -> resetDemo());

        // Add window listener to show main window on close
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                AlgorithmTreeLauncher.showMainWindow();
            }
        });
    }
    
    private void setupAnimation() {
        animationTimer = new Timer(2000, e -> {
            if (isAnimating) {
                stepExecution();
            }
        });
    }
    
    private void startDemo() {
        try {
            // 解析输入
            parseInput();
            
            // 重置状态
            p1 = m - 1;
            p2 = n - 1;
            tail = m + n - 1;
            completed = false;
            step = 0;
            currentOperation = "初始化指针: p1=" + p1 + ", p2=" + p2 + ", tail=" + tail;
            comparisonInfo = "";
            
            // 更新UI状态
            startButton.setEnabled(false);
            stepButton.setEnabled(true);
            autoButton.setEnabled(true);
            
            statusLabel.setText("状态: " + currentOperation);
            resultLabel.setText("结果: 开始执行算法");
            
            SwingUtilities.invokeLater(() -> repaint());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "输入格式错误: " + ex.getMessage());
        }
    }
    
    private void parseInput() {
        // 解析nums1
        nums1.clear();
        String[] parts1 = nums1Input.getText().trim().split(",");
        for (String part : parts1) {
            nums1.add(Integer.parseInt(part.trim()));
        }
        
        // 解析nums2
        nums2.clear();
        String[] parts2 = nums2Input.getText().trim().split(",");
        for (String part : parts2) {
            nums2.add(Integer.parseInt(part.trim()));
        }
        
        // 解析m和n
        m = Integer.parseInt(mInput.getText().trim());
        n = Integer.parseInt(nInput.getText().trim());
        
        // 边界条件检查
        if (m < 0 || n < 0) {
            throw new IllegalArgumentException("m和n必须非负");
        }
        if (nums1.size() != m + n) {
            throw new IllegalArgumentException("nums1长度必须等于m+n");
        }
        if (nums2.size() != n) {
            throw new IllegalArgumentException("nums2长度必须等于n");
        }
        
        // 检查数组是否有序
        for (int i = 1; i < m; i++) {
            if (nums1.get(i) < nums1.get(i-1)) {
                throw new IllegalArgumentException("nums1的前m个元素必须有序");
            }
        }
        for (int i = 1; i < n; i++) {
            if (nums2.get(i) < nums2.get(i-1)) {
                throw new IllegalArgumentException("nums2必须有序");
            }
        }
    }
    
    private void stepExecution() {
        if (p1 < 0 && p2 < 0) {
            // 算法完成
            isAnimating = false;
            animationTimer.stop();
            autoButton.setText("自动演示");
            stepButton.setEnabled(false);
            autoButton.setEnabled(false);
            startButton.setEnabled(true);
            
            completed = true;
            currentOperation = "算法完成！";
            resultLabel.setText("结果: " + nums1.toString());
            statusLabel.setText("状态: " + currentOperation);
            return;
        }
        
        // 执行合并步骤
        step++;
        
        if (p1 < 0) {
            // nums1已经处理完，直接复制nums2
            currentValue = nums2.get(p2);
            nums1.set(tail, currentValue);
            comparisonInfo = "nums1已处理完，直接取nums2[" + p2 + "]=" + currentValue;
            currentOperation = "第" + step + "步: " + comparisonInfo + " -> tail=" + tail;
            p2--;
        } else if (p2 < 0) {
            // nums2已经处理完，nums1元素已在正确位置
            currentValue = nums1.get(p1);
            comparisonInfo = "nums2已处理完，nums1[" + p1 + "]=" + currentValue + "已在正确位置";
            currentOperation = "第" + step + "步: " + comparisonInfo;
            p1--;
        } else {
            // 比较两个元素
            int val1 = nums1.get(p1);
            int val2 = nums2.get(p2);
            
            if (val1 > val2) {
                currentValue = val1;
                nums1.set(tail, currentValue);
                comparisonInfo = "nums1[" + p1 + "]=" + val1 + " > nums2[" + p2 + "]=" + val2 + 
                               " -> 选择" + val1;
                p1--;
            } else {
                currentValue = val2;
                nums1.set(tail, currentValue);
                comparisonInfo = "nums1[" + p1 + "]=" + val1 + " <= nums2[" + p2 + "]=" + val2 + 
                               " -> 选择" + val2;
                p2--;
            }
            currentOperation = "第" + step + "步: " + comparisonInfo + " -> tail=" + tail;
        }
        
        tail--;
        
        statusLabel.setText("状态: " + currentOperation);
        SwingUtilities.invokeLater(() -> repaint());
    }
    
    private void toggleAutoDemo() {
        if (isAnimating) {
            isAnimating = false;
            animationTimer.stop();
            autoButton.setText("自动演示");
            stepButton.setEnabled(true);
        } else {
            isAnimating = true;
            animationTimer.start();
            autoButton.setText("暂停");
            stepButton.setEnabled(false);
        }
    }
    
    private void resetDemo() {
        isAnimating = false;
        animationTimer.stop();
        
        // 重新解析输入数据
        try {
            parseInput();
        } catch (Exception ex) {
            // 使用默认数据
            nums1.clear();
            nums2.clear();
            nums1.addAll(Arrays.asList(1, 2, 3, 0, 0, 0));
            nums2.addAll(Arrays.asList(2, 5, 6));
            m = 3;
            n = 3;
        }
        
        p1 = m - 1;
        p2 = n - 1;
        tail = m + n - 1;
        completed = false;
        step = 0;
        currentValue = 0;
        comparisonInfo = "";
        currentOperation = "准备开始";
        
        startButton.setEnabled(true);
        stepButton.setEnabled(false);
        autoButton.setEnabled(false);
        autoButton.setText("自动演示");
        
        statusLabel.setText("状态: " + currentOperation);
        resultLabel.setText("结果: 未开始");
        
        SwingUtilities.invokeLater(() -> repaint());
    }
    
    // 可视化面板
    private class MergeArrayVisualizationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            drawArrays(g2d);
            drawPointers(g2d);
            drawAlgorithmInfo(g2d);
            drawMergeProcess(g2d);
        }
        
        private void drawArrays(Graphics2D g2d) {
            int startX = 50;
            int startY = 80;
            int cellWidth = 50;
            int cellHeight = 40;
            int spacing = 10;
            
            // 绘制nums1数组
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g2d.drawString("nums1 (长度=" + nums1.size() + ", 有效元素m=" + m + "):", startX, startY - 10);
            
            for (int i = 0; i < nums1.size(); i++) {
                int x = startX + i * (cellWidth + spacing);
                int y = startY;
                
                Rectangle rect = new Rectangle(x, y, cellWidth, cellHeight);
                
                // 设置颜色
                if (step > 0 && i == p1 + 1 && p1 >= 0) {
                    g2d.setColor(Color.CYAN); // p1指针位置
                } else if (step > 0 && i == tail + 1 && !completed) {
                    g2d.setColor(Color.GREEN); // tail指针位置
                } else if (i < m) {
                    g2d.setColor(new Color(173, 216, 230)); // 有效元素
                } else if (step > 0 && i > tail + 1) {
                    g2d.setColor(new Color(144, 238, 144)); // 已合并
                } else {
                    g2d.setColor(Color.LIGHT_GRAY); // 占位符
                }
                
                g2d.fill(rect);
                g2d.setColor(Color.BLACK);
                g2d.draw(rect);
                
                // 绘制数值
                String value = nums1.get(i).toString();
                FontMetrics fm = g2d.getFontMetrics();
                int textX = x + (cellWidth - fm.stringWidth(value)) / 2;
                int textY = y + (cellHeight + fm.getAscent()) / 2;
                g2d.drawString(value, textX, textY);
                
                // 绘制索引
                g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                g2d.drawString("" + i, x + cellWidth/2 - 5, y + cellHeight + 15);
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            }
            
            // 绘制nums2数组
            startY += 100;
            g2d.setColor(Color.BLACK);
            g2d.drawString("nums2 (长度=" + nums2.size() + "):", startX, startY - 10);
            
            for (int i = 0; i < nums2.size(); i++) {
                int x = startX + i * (cellWidth + spacing);
                int y = startY;
                
                Rectangle rect = new Rectangle(x, y, cellWidth, cellHeight);
                
                // 设置颜色
                if (step > 0 && i == p2 + 1 && p2 >= 0) {
                    g2d.setColor(Color.PINK); // p2指针位置
                } else if (step > 0 && i <= p2) {
                    g2d.setColor(Color.YELLOW); // 待处理
                } else {
                    g2d.setColor(Color.LIGHT_GRAY); // 已处理
                }
                
                g2d.fill(rect);
                g2d.setColor(Color.BLACK);
                g2d.draw(rect);
                
                // 绘制数值
                String value = nums2.get(i).toString();
                FontMetrics fm = g2d.getFontMetrics();
                int textX = x + (cellWidth - fm.stringWidth(value)) / 2;
                int textY = y + (cellHeight + fm.getAscent()) / 2;
                g2d.drawString(value, textX, textY);
                
                // 绘制索引
                g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                g2d.drawString("" + i, x + cellWidth/2 - 5, y + cellHeight + 15);
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            }
        }
        
        private void drawPointers(Graphics2D g2d) {
            if (step == 0) return;
            
            int startX = 50;
            int cellWidth = 50;
            int spacing = 10;
            
            // p1指针
            if (p1 >= 0) {
                int p1X = startX + (p1 + 1) * (cellWidth + spacing) + cellWidth/2;
                int p1Y = 80 - 30;
                g2d.setColor(Color.BLUE);
                g2d.drawString("p1", p1X - 10, p1Y);
                g2d.drawLine(p1X, p1Y + 5, p1X, 80 - 5);
            }
            
            // p2指针
            if (p2 >= 0) {
                int p2X = startX + (p2 + 1) * (cellWidth + spacing) + cellWidth/2;
                int p2Y = 180 - 30;
                g2d.setColor(Color.RED);
                g2d.drawString("p2", p2X - 10, p2Y);
                g2d.drawLine(p2X, p2Y + 5, p2X, 180 - 5);
            }
            
            // tail指针
            if (!completed) {
                int tailX = startX + (tail + 1) * (cellWidth + spacing) + cellWidth/2;
                int tailY = 80 + 40 + 25;
                g2d.setColor(Color.GREEN);
                g2d.drawString("tail", tailX - 15, tailY);
                g2d.drawLine(tailX, tailY - 5, tailX, 80 + 40 + 5);
            }
        }
        
        private void drawMergeProcess(Graphics2D g2d) {
            if (step == 0) return;
            
            int startX = 50;
            int startY = 300;
            
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            g2d.drawString("合并过程:", startX, startY);
            
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
            g2d.setColor(Color.BLUE);
            g2d.drawString("当前步骤: " + step, startX, startY + 20);
            g2d.drawString(comparisonInfo, startX, startY + 35);
            
            if (!completed) {
                g2d.drawString("指针状态: p1=" + p1 + ", p2=" + p2 + ", tail=" + tail, startX, startY + 50);
            }
        }
        
        private void drawAlgorithmInfo(Graphics2D g2d) {
            int startX = 700;
            int startY = 80;
            
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            g2d.drawString("双指针合并算法:", startX, startY);
            
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
            String[] info = {
                "1. 初始化三个指针:",
                "   p1 = m-1 (nums1有效元素末尾)",
                "   p2 = n-1 (nums2末尾)",
                "   tail = m+n-1 (合并数组末尾)",
                "",
                "2. 从后往前比较和填充:",
                "   • 比较nums1[p1]和nums2[p2]",
                "   • 选择较大值放入nums1[tail]",
                "   • 移动对应指针",
                "",
                "3. 处理剩余元素:",
                "   • 如果p1<0，复制nums2剩余元素",
                "   • 如果p2<0，nums1元素已在正确位置",
                "",
                "关键优势:",
                "• 从后往前避免覆盖",
                "• 原地合并，空间复杂度O(1)",
                "• 时间复杂度O(m+n)",
                "",
                "边界情况:",
                "• 其中一个数组为空",
                "• 所有元素相等",
                "• 一个数组的所有元素都大于另一个"
            };
            
            for (int i = 0; i < info.length; i++) {
                g2d.drawString(info[i], startX, startY + 20 + i * 15);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO088_E_MergeSortedArray_Animation().setVisible(true);
        });
    }
}