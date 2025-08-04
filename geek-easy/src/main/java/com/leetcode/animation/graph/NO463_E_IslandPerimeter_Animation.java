package com.leetcode.animation.graph;

import javax.swing.*;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.awt.*;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.awt.event.ActionEvent;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.awt.event.ActionListener;
import com.leetcode.animation.AlgorithmTreeLauncher;

/**
 * NO.463 岛屿的周长 - 动画演示
 * 
 * 算法描述：
 * 给定一个 row x col 的二维网格地图 grid，其中：grid[i][j] = 1 表示陆地，
 * grid[i][j] = 0 表示水域。计算岛屿的周长。
 * 
 * 动画特色：
 * • 可视化网格遍历过程
 * • 显示当前检查的格子
 * • 展示四个方向的边界检查
 * • 动态计算周长累加过程
 */
public class NO463_E_IslandPerimeter_Animation extends JFrame {
    
    // 动画控制
    private Timer animationTimer;
    private int currentStep = 0;
    private boolean isAnimating = false;
    
    // 算法数据
    private int[][] grid = {
        {0, 1, 0, 0},
        {1, 1, 1, 0},
        {0, 1, 0, 0},
        {1, 1, 0, 0}
    };
    private int currentRow = 0;
    private int currentCol = 0;
    private int currentDirection = 0; // 0:右, 1:下, 2:左, 3:上
    private int totalPerimeter = 0;
    private int currentCellPerimeter = 0;
    
    // 方向数组
    private int[] dx = {0, 1, 0, -1};
    private int[] dy = {1, 0, -1, 0};
    private String[] directionNames = {"右", "下", "左", "上"};
    
    // UI组件
    private JPanel controlPanel;
    private JButton startButton, stepButton, resetButton, homeButton;
    private JLabel statusLabel, perimeterLabel;
    private GridVisualizationPanel visualPanel;
    
    public NO463_E_IslandPerimeter_Animation() {
        setTitle("NO.463 岛屿的周长 - 动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null);
        
        initComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initComponents() {
        // 控制面板
        controlPanel = new JPanel();
        startButton = new JButton("开始动画");
        stepButton = new JButton("单步执行");
        resetButton = new JButton("重置");
        homeButton = new JButton("返回首页");
        statusLabel = new JLabel("点击开始按钮启动动画演示");
        perimeterLabel = new JLabel("当前周长：0");
        
        // 设置字体
        Font buttonFont = new Font("微软雅黑", Font.PLAIN, 14);
        startButton.setFont(buttonFont);
        stepButton.setFont(buttonFont);
        resetButton.setFont(buttonFont);
        homeButton.setFont(buttonFont);
        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        perimeterLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        perimeterLabel.setForeground(Color.BLUE);
        
        // 可视化面板
        visualPanel = new GridVisualizationPanel();
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 控制面板布局
        controlPanel.add(startButton);
        controlPanel.add(stepButton);
        controlPanel.add(resetButton);
        controlPanel.add(homeButton);
        controlPanel.add(Box.createHorizontalStrut(20));
        controlPanel.add(perimeterLabel);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(controlPanel, BorderLayout.CENTER);
        topPanel.add(statusLabel, BorderLayout.SOUTH);
        
        add(topPanel, BorderLayout.NORTH);
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
        
        animationTimer = new Timer(1000, e -> stepExecution());
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
        if (currentRow >= grid.length) {
            statusLabel.setText("算法执行完成！岛屿总周长为：" + totalPerimeter);
            stopAnimation();
            return;
        }
        
        if (currentCol >= grid[0].length) {
            currentRow++;
            currentCol = 0;
            currentDirection = 0;
            visualPanel.repaint();
            return;
        }
        
        if (grid[currentRow][currentCol] == 1) {
            if (currentDirection == 0) {
                statusLabel.setText("检查陆地格子 (" + currentRow + "," + currentCol + ")");
                currentCellPerimeter = 0;
            } else {
                checkDirection();
            }
            
            currentDirection++;
            if (currentDirection > 4) {
                totalPerimeter += currentCellPerimeter;
                perimeterLabel.setText("当前周长：" + totalPerimeter);
                statusLabel.setText("格子 (" + currentRow + "," + currentCol + ") 贡献周长：" + currentCellPerimeter);
                currentCol++;
                currentDirection = 0;
            }
        } else {
            statusLabel.setText("跳过水域格子 (" + currentRow + "," + currentCol + ")");
            currentCol++;
            currentDirection = 0;
        }
        
        visualPanel.repaint();
    }
    
    private void checkDirection() {
        int dirIndex = currentDirection - 1;
        int newRow = currentRow + dx[dirIndex];
        int newCol = currentCol + dy[dirIndex];
        
        boolean isEdge = false;
        if (newRow < 0 || newRow >= grid.length || newCol < 0 || newCol >= grid[0].length || grid[newRow][newCol] == 0) {
            isEdge = true;
            currentCellPerimeter++;
        }
        
        statusLabel.setText("检查" + directionNames[dirIndex] + "方向 (" + newRow + "," + newCol + ") - " + 
                          (isEdge ? "边界/水域，周长+1" : "陆地，无贡献"));
    }
    
    private void resetAnimation() {
        stopAnimation();
        currentStep = 0;
        currentRow = 0;
        currentCol = 0;
        currentDirection = 0;
        totalPerimeter = 0;
        currentCellPerimeter = 0;
        
        statusLabel.setText("点击开始按钮启动动画演示");
        perimeterLabel.setText("当前周长：0");
        visualPanel.repaint();
    }
    
    // 网格可视化面板
    private class GridVisualizationPanel extends JPanel {
        private final int CELL_SIZE = 80;
        private final int GRID_START_X = 100;
        private final int GRID_START_Y = 100;
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            drawGrid(g2d);
            drawCurrentPosition(g2d);
            drawDirectionCheck(g2d);
            drawLegend(g2d);
        }
        
        private void drawGrid(Graphics2D g2d) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    int x = GRID_START_X + j * CELL_SIZE;
                    int y = GRID_START_Y + i * CELL_SIZE;
                    
                    // 绘制格子
                    if (grid[i][j] == 1) {
                        g2d.setColor(new Color(34, 139, 34)); // 森林绿 - 陆地
                    } else {
                        g2d.setColor(new Color(135, 206, 235)); // 天蓝色 - 水域
                    }
                    g2d.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                    
                    // 绘制边框
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                    
                    // 绘制坐标
                    g2d.setFont(new Font("Arial", Font.BOLD, 12));
                    g2d.setColor(Color.WHITE);
                    g2d.drawString("(" + i + "," + j + ")", x + 5, y + 15);
                    
                    // 绘制值
                    g2d.setFont(new Font("Arial", Font.BOLD, 24));
                    g2d.drawString(String.valueOf(grid[i][j]), x + CELL_SIZE/2 - 8, y + CELL_SIZE/2 + 8);
                }
            }
        }
        
        private void drawCurrentPosition(Graphics2D g2d) {
            if (currentRow < grid.length && currentCol < grid[0].length) {
                int x = GRID_START_X + currentCol * CELL_SIZE;
                int y = GRID_START_Y + currentRow * CELL_SIZE;
                
                // 高亮当前格子
                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(4));
                g2d.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                
                // 显示当前格子的周长贡献
                if (grid[currentRow][currentCol] == 1 && currentDirection > 0) {
                    g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
                    g2d.setColor(Color.RED);
                    g2d.drawString("周长: " + currentCellPerimeter, x + 5, y + CELL_SIZE - 5);
                }
            }
        }
        
        private void drawDirectionCheck(Graphics2D g2d) {
            if (currentRow < grid.length && currentCol < grid[0].length && 
                grid[currentRow][currentCol] == 1 && currentDirection > 0 && currentDirection <= 4) {
                
                int dirIndex = currentDirection - 1;
                int checkRow = currentRow + dx[dirIndex];
                int checkCol = currentCol + dy[dirIndex];
                
                // 绘制检查方向的箭头
                int startX = GRID_START_X + currentCol * CELL_SIZE + CELL_SIZE/2;
                int startY = GRID_START_Y + currentRow * CELL_SIZE + CELL_SIZE/2;
                int endX = startX + dy[dirIndex] * CELL_SIZE/2;
                int endY = startY + dx[dirIndex] * CELL_SIZE/2;
                
                g2d.setColor(Color.ORANGE);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawLine(startX, startY, endX, endY);
                
                // 绘制箭头头部
                drawArrowHead(g2d, startX, startY, endX, endY);
                
                // 高亮检查的格子
                if (checkRow >= 0 && checkRow < grid.length && checkCol >= 0 && checkCol < grid[0].length) {
                    int x = GRID_START_X + checkCol * CELL_SIZE;
                    int y = GRID_START_Y + checkRow * CELL_SIZE;
                    
                    g2d.setColor(Color.YELLOW);
                    g2d.setStroke(new BasicStroke(3));
                    g2d.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                }
            }
        }
        
        private void drawArrowHead(Graphics2D g2d, int x1, int y1, int x2, int y2) {
            double angle = Math.atan2(y2 - y1, x2 - x1);
            int arrowLength = 10;
            double arrowAngle = Math.PI / 6;
            
            int x3 = (int) (x2 - arrowLength * Math.cos(angle - arrowAngle));
            int y3 = (int) (y2 - arrowLength * Math.sin(angle - arrowAngle));
            int x4 = (int) (x2 - arrowLength * Math.cos(angle + arrowAngle));
            int y4 = (int) (y2 - arrowLength * Math.sin(angle + arrowAngle));
            
            g2d.drawLine(x2, y2, x3, y3);
            g2d.drawLine(x2, y2, x4, y4);
        }
        
        private void drawLegend(Graphics2D g2d) {
            int legendX = GRID_START_X + grid[0].length * CELL_SIZE + 50;
            int legendY = GRID_START_Y;
            
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            g2d.setColor(Color.BLACK);
            g2d.drawString("图例：", legendX, legendY);
            
            // 陆地
            g2d.setColor(new Color(34, 139, 34));
            g2d.fillRect(legendX, legendY + 20, 20, 20);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(legendX, legendY + 20, 20, 20);
            g2d.drawString("陆地 (1)", legendX + 30, legendY + 35);
            
            // 水域
            g2d.setColor(new Color(135, 206, 235));
            g2d.fillRect(legendX, legendY + 50, 20, 20);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(legendX, legendY + 50, 20, 20);
            g2d.drawString("水域 (0)", legendX + 30, legendY + 65);
            
            // 当前位置
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawRect(legendX, legendY + 80, 20, 20);
            g2d.setColor(Color.BLACK);
            g2d.drawString("当前位置", legendX + 30, legendY + 95);
            
            // 检查方向
            g2d.setColor(Color.ORANGE);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(legendX + 5, legendY + 115, legendX + 15, legendY + 115);
            g2d.setColor(Color.BLACK);
            g2d.drawString("检查方向", legendX + 30, legendY + 120);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO463_E_IslandPerimeter_Animation().setVisible(true);
        });
    }
}