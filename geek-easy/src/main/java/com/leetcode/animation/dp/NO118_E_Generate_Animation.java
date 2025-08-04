package com.leetcode.animation.dp;

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
 * NO.118 杨辉三角 - 动画演示
 * 演示杨辉三角的生成过程，每个数是上方两个数的和
 * 
 * 设计文档：
 * 1. 功能需求：可视化杨辉三角的逐行生成过程
 * 2. 技术架构：基于Swing的动画演示系统
 * 3. 核心算法：动态规划，每个位置的值等于上一行对应位置和前一位置的和
 * 4. 边界条件：第一行、每行的首尾元素都是1
 * 5. 性能考虑：时间复杂度O(numRows²)，空间复杂度O(numRows²)
 */
public class NO118_E_Generate_Animation extends JFrame {
    private List<List<Integer>> triangle;
    private int numRows;
    private int currentRow;
    private int currentCol;
    private boolean isAnimating;
    private Timer animationTimer;
    
    // UI组件
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JTextField rowsInput;
    private JButton startButton, stepButton, resetButton, autoButton;
    private JLabel statusLabel;
    private JLabel resultLabel;
    
    // 动画状态
    private boolean completed;
    private String currentOperation;
    private int step;
    private boolean isCalculatingElement;
    private int leftParent, rightParent;
    
    public NO118_E_Generate_Animation() {
        setTitle("NO.118 杨辉三角 - 动画演示");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        
        initData();
        initComponents();
        setupLayout();
        setupEventHandlers();
        setupAnimation();
    }
    
    private void initData() {
        triangle = new ArrayList<>();
        numRows = 5;
        currentRow = 0;
        currentCol = 0;
        isAnimating = false;
        completed = false;
        currentOperation = "准备开始";
        step = 0;
        isCalculatingElement = false;
        leftParent = 0;
        rightParent = 0;
    }
    
    private void initComponents() {
        mainPanel = new JPanel();
        controlPanel = new JPanel();
        
        rowsInput = new JTextField("5", 10);
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
        mainPanel.add(new PascalTriangleVisualizationPanel(), BorderLayout.CENTER);
        
        // 控制面板
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        controlPanel.add(new JLabel("行数:"), gbc);
        gbc.gridx = 1;
        controlPanel.add(rowsInput, gbc);
        
        // 返回首页按钮
        JButton homeButton = new JButton("返回首页");
        homeButton.addActionListener(e -> {
            dispose(); // 关闭当前窗口
            // 启动主界面
            SwingUtilities.invokeLater(() -> {
                 try {
                     dispose(); // 关闭当前动画窗口
                     com.leetcode.animation.AlgorithmTreeLauncher.showMainWindow();
                 } catch (Exception ex) {
                     ex.printStackTrace();
                 }
             });
        });

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
    }
    
    private void setupAnimation() {
        animationTimer = new Timer(1000, e -> {
            if (isAnimating) {
                stepExecution();
            }
        });
    }
    
    private void startDemo() {
        try {
            // 解析输入
            numRows = Integer.parseInt(rowsInput.getText().trim());
            
            // 边界条件检查
            if (numRows <= 0 || numRows > 15) {
                throw new IllegalArgumentException("行数必须在1-15之间");
            }
            
            // 重置状态
            triangle.clear();
            currentRow = 0;
            currentCol = 0;
            completed = false;
            step = 0;
            isCalculatingElement = false;
            currentOperation = "开始生成杨辉三角，共" + numRows + "行";
            
            // 更新UI状态
            startButton.setEnabled(false);
            stepButton.setEnabled(true);
            autoButton.setEnabled(true);
            
            statusLabel.setText("状态: " + currentOperation);
            resultLabel.setText("结果: 开始生成");
            
            repaint();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "输入格式错误: " + ex.getMessage());
        }
    }
    
    private void stepExecution() {
        if (currentRow >= numRows) {
            // 算法完成
            isAnimating = false;
            animationTimer.stop();
            autoButton.setText("自动演示");
            stepButton.setEnabled(false);
            autoButton.setEnabled(false);
            startButton.setEnabled(true);
            
            completed = true;
            currentOperation = "杨辉三角生成完成！";
            resultLabel.setText("结果: 共生成" + numRows + "行");
            statusLabel.setText("状态: " + currentOperation);
            return;
        }
        
        if (currentCol == 0) {
            // 开始新的一行
            triangle.add(new ArrayList<>());
            currentOperation = "第" + (currentRow + 1) + "行: 开始生成";
        }
        
        step++;
        
        // 计算当前位置的值
        int value;
        if (currentCol == 0 || currentCol == currentRow) {
            // 边界元素，值为1
            value = 1;
            currentOperation = "第" + step + "步: 第" + (currentRow + 1) + "行第" + (currentCol + 1) + 
                             "列 - 边界元素，值为1";
            leftParent = 0;
            rightParent = 0;
        } else {
            // 内部元素，等于上一行对应位置的两个数之和
            leftParent = triangle.get(currentRow - 1).get(currentCol - 1);
            rightParent = triangle.get(currentRow - 1).get(currentCol);
            value = leftParent + rightParent;
            currentOperation = "第" + step + "步: 第" + (currentRow + 1) + "行第" + (currentCol + 1) + 
                             "列 = " + leftParent + " + " + rightParent + " = " + value;
        }
        
        // 添加到当前行
        triangle.get(currentRow).add(value);
        isCalculatingElement = true;
        
        // 移动到下一个位置
        currentCol++;
        if (currentCol > currentRow) {
            // 当前行完成，移动到下一行
            currentRow++;
            currentCol = 0;
            isCalculatingElement = false;
        }
        
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
        
        triangle.clear();
        currentRow = 0;
        currentCol = 0;
        completed = false;
        step = 0;
        isCalculatingElement = false;
        leftParent = 0;
        rightParent = 0;
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
    private class PascalTriangleVisualizationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            drawTriangle(g2d);
            drawAlgorithmInfo(g2d);
            drawCurrentCalculation(g2d);
        }
        
        private void drawTriangle(Graphics2D g2d) {
            if (triangle.isEmpty()) return;
            
            int centerX = getWidth() / 2;
            int startY = 80;
            int cellSize = 40;
            int rowSpacing = 50;
            
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            
            for (int row = 0; row < triangle.size(); row++) {
                List<Integer> currentRowData = triangle.get(row);
                int rowWidth = currentRowData.size() * cellSize + (currentRowData.size() - 1) * 10;
                int startX = centerX - rowWidth / 2;
                int y = startY + row * rowSpacing;
                
                for (int col = 0; col < currentRowData.size(); col++) {
                    int x = startX + col * (cellSize + 10);
                    
                    // 绘制单元格
                    Rectangle rect = new Rectangle(x, y, cellSize, cellSize);
                    
                    // 设置颜色
                    if (row == currentRow - 1 && col == currentCol - 1 && isCalculatingElement) {
                        g2d.setColor(Color.GREEN); // 当前正在计算的元素
                    } else if (row == currentRow - 2 && isCalculatingElement && 
                              (col == currentCol - 2 || col == currentCol - 1)) {
                        g2d.setColor(Color.YELLOW); // 参与计算的父元素
                    } else if (row < currentRow || (row == currentRow && col < currentCol)) {
                        g2d.setColor(new Color(173, 216, 230)); // 已生成的元素
                    } else {
                        g2d.setColor(Color.WHITE); // 未生成的元素
                    }
                    
                    g2d.fill(rect);
                    g2d.setColor(Color.BLACK);
                    g2d.draw(rect);
                    
                    // 绘制数值
                    String value = currentRowData.get(col).toString();
                    FontMetrics fm = g2d.getFontMetrics();
                    int textX = x + (cellSize - fm.stringWidth(value)) / 2;
                    int textY = y + (cellSize + fm.getAscent()) / 2;
                    g2d.drawString(value, textX, textY);
                }
                
                // 绘制行号
                g2d.setColor(Color.GRAY);
                g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                g2d.drawString("第" + (row + 1) + "行", startX - 50, y + cellSize/2 + 5);
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
                g2d.setColor(Color.BLACK);
            }
            
            // 绘制连接线（显示计算关系）
            if (isCalculatingElement && currentRow > 1) {
                drawCalculationLines(g2d, centerX, startY, cellSize, rowSpacing);
            }
        }
        
        private void drawCalculationLines(Graphics2D g2d, int centerX, int startY, 
                                        int cellSize, int rowSpacing) {
            int prevRow = currentRow - 2;
            int currRow = currentRow - 1;
            int col = currentCol - 1;
            
            if (prevRow >= 0 && col > 0 && col < triangle.get(currRow).size() - 1) {
                // 计算位置
                List<Integer> prevRowData = triangle.get(prevRow);
                List<Integer> currRowData = triangle.get(currRow);
                
                int prevRowWidth = prevRowData.size() * cellSize + (prevRowData.size() - 1) * 10;
                int currRowWidth = currRowData.size() * cellSize + (currRowData.size() - 1) * 10;
                
                int prevStartX = centerX - prevRowWidth / 2;
                int currStartX = centerX - currRowWidth / 2;
                
                int prevY = startY + prevRow * rowSpacing;
                int currY = startY + currRow * rowSpacing;
                
                // 左父元素位置
                int leftParentX = prevStartX + (col - 1) * (cellSize + 10) + cellSize / 2;
                int leftParentY = prevY + cellSize;
                
                // 右父元素位置
                int rightParentX = prevStartX + col * (cellSize + 10) + cellSize / 2;
                int rightParentY = prevY + cellSize;
                
                // 当前元素位置
                int currentX = currStartX + col * (cellSize + 10) + cellSize / 2;
                int currentY = currY;
                
                // 绘制连接线
                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(leftParentX, leftParentY, currentX, currentY);
                g2d.drawLine(rightParentX, rightParentY, currentX, currentY);
                g2d.setStroke(new BasicStroke(1));
            }
        }
        
        private void drawCurrentCalculation(Graphics2D g2d) {
            if (!isCalculatingElement || currentRow <= 1) return;
            
            int startX = 50;
            int startY = getHeight() - 120;
            
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            g2d.drawString("当前计算:", startX, startY);
            
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
            g2d.setColor(Color.BLUE);
            
            if (currentCol == 1 || currentCol == currentRow) {
                g2d.drawString("边界元素，直接设为1", startX, startY + 20);
            } else {
                g2d.drawString("第" + currentRow + "行第" + currentCol + "列 = " + 
                             leftParent + " + " + rightParent + " = " + 
                             (leftParent + rightParent), startX, startY + 20);
                g2d.drawString("(上一行第" + (currentCol - 1) + "列 + 上一行第" + currentCol + "列)", 
                             startX, startY + 35);
            }
        }
        
        private void drawAlgorithmInfo(Graphics2D g2d) {
            int startX = getWidth() - 300;
            int startY = 80;
            
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            g2d.drawString("杨辉三角算法:", startX, startY);
            
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
            String[] info = {
                "1. 初始化结果列表",
                "",
                "2. 对于每一行 i (0 到 numRows-1):",
                "   • 创建新的行列表",
                "   • 对于每一列 j (0 到 i):",
                "     - 如果 j==0 或 j==i:",
                "       设置为 1 (边界元素)",
                "     - 否则:",
                "       设置为上一行 [j-1] + [j]",
                "",
                "3. 数学性质:",
                "   • 每行首尾都是1",
                "   • 每个内部元素是上方两数之和",
                "   • 第n行有n+1个元素",
                "   • 对称性质",
                "",
                "4. 应用:",
                "   • 二项式系数",
                "   • 组合数学",
                "   • 概率计算",
                "",
                "时间复杂度: O(numRows²)",
                "空间复杂度: O(numRows²)"
            };
            
            for (int i = 0; i < info.length; i++) {
                g2d.drawString(info[i], startX, startY + 20 + i * 15);
            }
            
            // 显示当前进度
            if (step > 0) {
                g2d.setColor(Color.BLUE);
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 12));
                g2d.drawString("进度: " + triangle.size() + "/" + numRows + " 行", 
                             startX, startY + 380);
                g2d.drawString("当前: 第" + (currentRow + 1) + "行第" + (currentCol + 1) + "列", 
                             startX, startY + 395);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO118_E_Generate_Animation().setVisible(true);
        });
    }
}