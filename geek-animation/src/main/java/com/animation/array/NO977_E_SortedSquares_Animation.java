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
 * NO.977 有序数组的平方 - 动画演示
 * 演示双指针技术解决有序数组平方问题的过程
 * 
 * 设计文档：
 * 1. 功能需求：可视化双指针算法处理有序数组平方的过程
 * 2. 技术架构：基于Swing的动画演示系统
 * 3. 核心算法：双指针从两端向中间移动，比较平方值大小
 * 4. 边界条件：数组为空、全正数、全负数、混合数组等情况
 * 5. 性能考虑：时间复杂度O(n)，空间复杂度O(n)
 */
public class NO977_E_SortedSquares_Animation extends JFrame {
    private List<Integer> nums;
    private List<Integer> result;
    private int left, right, resultIndex;
    private boolean isAnimating;
    private Timer animationTimer;
    
    // UI组件
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JTextField arrayInput;
    private JButton startButton, stepButton, resetButton, autoButton, homeButton;
    private JLabel statusLabel;
    private JLabel resultLabel;
    
    // 动画状态
    private boolean completed;
    private String currentOperation;
    private int step;
    private int leftSquare, rightSquare;
    
    public NO977_E_SortedSquares_Animation() {
        setTitle("NO.977 有序数组的平方 - 动画演示");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 800);
        setLocationRelativeTo(null);
        
        initData();
        initComponents();
        setupLayout();
        setupEventHandlers();
        setupAnimation();
    }
    
    private void initData() {
        nums = new ArrayList<>();
        result = new ArrayList<>();
        left = 0;
        right = 0;
        resultIndex = 0;
        isAnimating = false;
        completed = false;
        currentOperation = "准备开始";
        step = 0;
        leftSquare = 0;
        rightSquare = 0;
        
        // 默认示例数据 - 有序数组（可能包含负数）
        int[] defaultArray = {-4, -1, 0, 3, 10};
        for (int num : defaultArray) {
            nums.add(num);
        }
    }
    
    private void initComponents() {
        mainPanel = new JPanel();
        controlPanel = new JPanel();
        
        arrayInput = new JTextField("-4,-1,0,3,10", 20);
        startButton = new JButton("开始演示");
        stepButton = new JButton("单步执行");
        resetButton = new JButton("重置");
        autoButton = new JButton("自动演示");
        homeButton = new JButton("返回首页");
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
        mainPanel.add(new SortedSquaresVisualizationPanel(), BorderLayout.CENTER);
        
        // 控制面板
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        controlPanel.add(new JLabel("有序数组(逗号分隔):"), gbc);
        gbc.gridx = 1;
        controlPanel.add(arrayInput, gbc);
        


        gbc.gridx = 0;
        controlPanel.add(startButton, gbc);
        gbc.gridx = 1;
        controlPanel.add(stepButton, gbc);
        gbc.gridx = 2;
        controlPanel.add(autoButton, gbc);
        gbc.gridx = 3;
        controlPanel.add(resetButton, gbc);
        gbc.gridx = 4;
        controlPanel.add(homeButton, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 4;
        controlPanel.add(statusLabel, gbc);
        
        gbc.gridy = 3;
        controlPanel.add(resultLabel, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        startButton.addActionListener(e -> startDemo());
        stepButton.addActionListener(e -> stepExecution());
        autoButton.addActionListener(e -> toggleAutoDemo());
        resetButton.addActionListener(e -> resetDemo());
        homeButton.addActionListener(e -> {
            // 停止动画
            isAnimating = false;
            if (animationTimer != null) {
                animationTimer.stop();
            }
            // 关闭当前窗口并返回主界面
            dispose();
            SwingUtilities.invokeLater(() -> {
                try {
                    com.animation.launcher.AlgorithmTreeLauncher.showMainWindow();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });
    }
    
    private void setupAnimation() {
        animationTimer = new Timer(1500, e -> {
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
            left = 0;
            right = nums.size() - 1;
            resultIndex = nums.size() - 1;
            result.clear();
            for (int i = 0; i < nums.size(); i++) {
                result.add(0); // 初始化结果数组
            }
            completed = false;
            step = 0;
            currentOperation = "初始化双指针: left=0, right=" + right;
            
            // 更新UI状态
            startButton.setEnabled(false);
            stepButton.setEnabled(true);
            autoButton.setEnabled(true);
            
            statusLabel.setText("状态: " + currentOperation);
            resultLabel.setText("结果: 开始执行算法");
            
            repaint();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "输入格式错误: " + ex.getMessage());
        }
    }
    
    private void parseInput() {
        nums.clear();
        String[] parts = arrayInput.getText().trim().split(",");
        for (String part : parts) {
            nums.add(Integer.parseInt(part.trim()));
        }
        
        // 边界条件检查
        if (nums.isEmpty()) {
            throw new IllegalArgumentException("数组不能为空");
        }
        
        // 检查数组是否有序
        for (int i = 1; i < nums.size(); i++) {
            if (nums.get(i) < nums.get(i-1)) {
                throw new IllegalArgumentException("数组必须是非递减有序的");
            }
        }
    }
    
    private void stepExecution() {
        if (left > right || completed) {
            // 算法完成
            isAnimating = false;
            animationTimer.stop();
            autoButton.setText("自动演示");
            stepButton.setEnabled(false);
            autoButton.setEnabled(false);
            startButton.setEnabled(true);
            
            completed = true;
            currentOperation = "算法完成！";
            resultLabel.setText("结果: " + result.toString());
            statusLabel.setText("状态: " + currentOperation);
            return;
        }
        
        // 执行双指针算法步骤
        step++;
        leftSquare = nums.get(left) * nums.get(left);
        rightSquare = nums.get(right) * nums.get(right);
        
        currentOperation = "第" + step + "步: 比较 " + nums.get(left) + "² (" + leftSquare + 
                          ") 与 " + nums.get(right) + "² (" + rightSquare + ")";
        
        if (leftSquare > rightSquare) {
            // 左边的平方更大
            result.set(resultIndex, leftSquare);
            currentOperation += " -> 选择左边 " + leftSquare + "，left++";
            left++;
        } else {
            // 右边的平方更大或相等
            result.set(resultIndex, rightSquare);
            currentOperation += " -> 选择右边 " + rightSquare + "，right--";
            right--;
        }
        
        resultIndex--;
        
        statusLabel.setText("状态: " + currentOperation);
        repaint();
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
        
        left = 0;
        right = 0;
        resultIndex = 0;
        result.clear();
        completed = false;
        step = 0;
        leftSquare = 0;
        rightSquare = 0;
        currentOperation = "准备开始";
        
        startButton.setEnabled(true);
        stepButton.setEnabled(false);
        autoButton.setEnabled(false);
        autoButton.setText("自动演示");
        
        statusLabel.setText("状态: " + currentOperation);
        resultLabel.setText("结果: 未开始");
        
        repaint();
    }
    
    // 可视化面板
    private class SortedSquaresVisualizationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            drawOriginalArray(g2d);
            drawSquaredValues(g2d);
            drawResultArray(g2d);
            drawPointers(g2d);
            drawAlgorithmInfo(g2d);
        }
        
        private void drawOriginalArray(Graphics2D g2d) {
            int startX = 50;
            int startY = 80;
            
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g2d.drawString("原始有序数组:", startX, startY - 10);
            
            int cellWidth = 60;
            int cellHeight = 40;
            
            for (int i = 0; i < nums.size(); i++) {
                int x = startX + i * (cellWidth + 10);
                int y = startY;
                
                // 绘制单元格
                Rectangle rect = new Rectangle(x, y, cellWidth, cellHeight);
                
                // 设置颜色
                if (step > 0 && i == left && left <= right) {
                    g2d.setColor(Color.CYAN); // 左指针位置
                } else if (step > 0 && i == right && left <= right) {
                    g2d.setColor(Color.PINK); // 右指针位置
                } else if (step > 0 && (i < left || i > right)) {
                    g2d.setColor(Color.LIGHT_GRAY); // 已处理
                } else {
                    g2d.setColor(Color.WHITE); // 未处理
                }
                
                g2d.fill(rect);
                g2d.setColor(Color.BLACK);
                g2d.draw(rect);
                
                // 绘制数值
                String value = nums.get(i).toString();
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
        
        private void drawSquaredValues(Graphics2D g2d) {
            int startX = 50;
            int startY = 180;
            
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g2d.drawString("平方值:", startX, startY - 10);
            
            int cellWidth = 60;
            int cellHeight = 40;
            
            for (int i = 0; i < nums.size(); i++) {
                int x = startX + i * (cellWidth + 10);
                int y = startY;
                
                // 绘制单元格
                Rectangle rect = new Rectangle(x, y, cellWidth, cellHeight);
                
                // 设置颜色
                if (step > 0 && i == left && left <= right) {
                    g2d.setColor(Color.CYAN); // 左指针位置
                } else if (step > 0 && i == right && left <= right) {
                    g2d.setColor(Color.PINK); // 右指针位置
                } else {
                    g2d.setColor(new Color(255, 255, 224));
                }
                
                g2d.fill(rect);
                g2d.setColor(Color.BLACK);
                g2d.draw(rect);
                
                // 绘制平方值
                int square = nums.get(i) * nums.get(i);
                String value = String.valueOf(square);
                FontMetrics fm = g2d.getFontMetrics();
                int textX = x + (cellWidth - fm.stringWidth(value)) / 2;
                int textY = y + (cellHeight + fm.getAscent()) / 2;
                g2d.drawString(value, textX, textY);
            }
        }
        
        private void drawResultArray(Graphics2D g2d) {
            if (result.isEmpty()) return;
            
            int startX = 50;
            int startY = 280;
            
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g2d.drawString("结果数组 (从后往前填充):", startX, startY - 10);
            
            int cellWidth = 60;
            int cellHeight = 40;
            
            for (int i = 0; i < result.size(); i++) {
                int x = startX + i * (cellWidth + 10);
                int y = startY;
                
                // 绘制单元格
                Rectangle rect = new Rectangle(x, y, cellWidth, cellHeight);
                
                // 设置颜色
                if (step > 0 && i == resultIndex + 1 && !completed) {
                    g2d.setColor(Color.GREEN); // 刚填入的位置
                } else if (step > 0 && i > resultIndex) {
                    g2d.setColor(new Color(144, 238, 144)); // 已填入
                } else {
                    g2d.setColor(Color.WHITE); // 未填入
                }
                
                g2d.fill(rect);
                g2d.setColor(Color.BLACK);
                g2d.draw(rect);
                
                // 绘制数值
                if (step > 0 && i > resultIndex) {
                    String value = result.get(i).toString();
                    FontMetrics fm = g2d.getFontMetrics();
                    int textX = x + (cellWidth - fm.stringWidth(value)) / 2;
                    int textY = y + (cellHeight + fm.getAscent()) / 2;
                    g2d.drawString(value, textX, textY);
                }
                
                // 绘制索引
                g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                g2d.drawString("" + i, x + cellWidth/2 - 5, y + cellHeight + 15);
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            }
        }
        
        private void drawPointers(Graphics2D g2d) {
            if (step == 0) return;
            
            int startX = 50;
            int startY = 80;
            int cellWidth = 60;
            int pointerY = startY - 30;
            
            // 绘制left指针
            if (left < nums.size() && left <= right) {
                int leftX = startX + left * (cellWidth + 10) + cellWidth/2;
                g2d.setColor(Color.BLUE);
                g2d.drawString("L", leftX - 5, pointerY);
                g2d.drawLine(leftX, pointerY + 5, leftX, startY - 5);
            }
            
            // 绘制right指针
            if (right >= 0 && right < nums.size() && left <= right) {
                int rightX = startX + right * (cellWidth + 10) + cellWidth/2;
                g2d.setColor(Color.RED);
                g2d.drawString("R", rightX - 5, pointerY);
                g2d.drawLine(rightX, pointerY + 5, rightX, startY - 5);
            }
            
            // 绘制结果指针
            if (resultIndex >= 0 && resultIndex < result.size() && !completed) {
                int resultX = startX + (resultIndex + 1) * (cellWidth + 10) + cellWidth/2;
                int resultPointerY = 280 + 40 + 20;
                g2d.setColor(Color.GREEN);
                g2d.drawString("↑", resultX - 5, resultPointerY);
                g2d.drawString("填充位置", resultX - 20, resultPointerY + 15);
            }
        }
        
        private void drawAlgorithmInfo(Graphics2D g2d) {
            int startX = 600;
            int startY = 80;
            
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            g2d.drawString("双指针算法:", startX, startY);
            
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
            String[] info = {
                "1. 初始化 left=0, right=n-1",
                "2. 创建结果数组，从后往前填充",
                "3. 比较 nums[left]² 和 nums[right]²",
                "4. 选择较大的平方值放入结果数组",
                "5. 移动对应指针，继续比较",
                "6. 直到 left > right",
                "",
                "关键思路:",
                "• 有序数组的平方最大值在两端",
                "• 从后往前填充避免覆盖",
                "• 双指针减少比较次数",
                "",
                "时间复杂度: O(n)",
                "空间复杂度: O(n)"
            };
            
            for (int i = 0; i < info.length; i++) {
                g2d.drawString(info[i], startX, startY + 20 + i * 15);
            }
            
            // 绘制当前比较信息
            if (step > 0 && left <= right) {
                g2d.setColor(Color.BLUE);
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 12));
                g2d.drawString("当前比较:", startX, startY + 280);
                g2d.drawString("left=" + left + ", nums[" + left + "]²=" + leftSquare, startX, startY + 295);
                g2d.drawString("right=" + right + ", nums[" + right + "]²=" + rightSquare, startX, startY + 310);
                g2d.drawString("resultIndex=" + resultIndex, startX, startY + 325);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO977_E_SortedSquares_Animation().setVisible(true);
        });
    }
}