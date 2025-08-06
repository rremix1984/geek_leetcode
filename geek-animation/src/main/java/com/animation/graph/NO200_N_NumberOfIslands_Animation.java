package com.animation.graph;

import javax.swing.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * NO.200 岛屿数量 - 动画演示
 * 
 * 算法描述：
 * 给你一个由'1'（陆地）和'0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
 * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
 * 
 * 动画特色：
 * • 可视化DFS搜索过程
 * • 显示当前搜索的位置
 * • 展示岛屿发现和标记过程
 * • 动态显示岛屿数量统计
 */
public class NO200_N_NumberOfIslands_Animation extends JFrame {
    
    // 动画控制
    private javax.swing.Timer animationTimer;
    private boolean isAnimating = false;
    
    // 算法数据
    private char[][] originalGrid = {
        {'1', '1', '1', '1', '0'},
        {'1', '1', '0', '1', '0'},
        {'1', '1', '0', '0', '0'},
        {'0', '0', '0', '0', '0'}
    };
    private char[][] grid;
    private boolean[][] visited;
    private int currentRow = 0;
    private int currentCol = 0;
    private int islandCount = 0;
    private boolean foundNewIsland = false;
    private List<Point> currentIslandCells = new ArrayList<>();
    private String currentPhase = "扫描";
    
    // 方向数组 - 上下左右
    private int[] dx = {-1, 1, 0, 0};
    private int[] dy = {0, 0, -1, 1};
    
    // UI组件
    private JPanel controlPanel;
    private JButton startButton, stepButton, resetButton, homeButton;
    private JLabel statusLabel, islandCountLabel;
    private GridVisualizationPanel visualPanel;
    
    public NO200_N_NumberOfIslands_Animation() {
        setTitle("NO.200 岛屿数量 - 动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 800);
        setLocationRelativeTo(null);
        
        initializeData();
        initComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initializeData() {
        grid = new char[originalGrid.length][originalGrid[0].length];
        visited = new boolean[originalGrid.length][originalGrid[0].length];
        resetData();
    }
    
    private void resetData() {
        // 复制原始网格
        for (int i = 0; i < originalGrid.length; i++) {
            for (int j = 0; j < originalGrid[0].length; j++) {
                grid[i][j] = originalGrid[i][j];
                visited[i][j] = false;
            }
        }
        currentRow = 0;
        currentCol = 0;
        islandCount = 0;
        foundNewIsland = false;
        currentIslandCells.clear();
        currentPhase = "扫描";
    }
    
    private void initComponents() {
        // 控制面板
        controlPanel = new JPanel();
        startButton = new JButton("开始动画");
        stepButton = new JButton("单步执行");
        resetButton = new JButton("重置");
        homeButton = new JButton("返回首页");
        statusLabel = new JLabel("点击开始按钮启动动画演示");
        islandCountLabel = new JLabel("发现岛屿数量：0");
        
        // 设置字体
        Font buttonFont = new Font("微软雅黑", Font.PLAIN, 14);
        startButton.setFont(buttonFont);
        stepButton.setFont(buttonFont);
        resetButton.setFont(buttonFont);
        homeButton.setFont(buttonFont);
        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        islandCountLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        islandCountLabel.setForeground(Color.BLUE);
        
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
        controlPanel.add(islandCountLabel);
        
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
        
        animationTimer = new javax.swing.Timer(1200, e -> stepExecution());
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
            statusLabel.setText("算法执行完成！总共发现 " + islandCount + " 个岛屿");
            stopAnimation();
            return;
        }
        
        if (currentCol >= grid[0].length) {
            currentRow++;
            currentCol = 0;
            SwingUtilities.invokeLater(() -> visualPanel.repaint());
            return;
        }
        
        // 检查当前位置
        if (grid[currentRow][currentCol] == '1' && !visited[currentRow][currentCol]) {
            // 发现新岛屿
            islandCount++;
            islandCountLabel.setText("发现岛屿数量：" + islandCount);
            statusLabel.setText("在位置 (" + currentRow + "," + currentCol + ") 发现第 " + islandCount + " 个岛屿，开始DFS标记");
            foundNewIsland = true;
            currentPhase = "DFS标记";
            currentIslandCells.clear();
            
            // 开始DFS标记这个岛屿
            dfsMarkIsland(currentRow, currentCol);
        } else {
            if (grid[currentRow][currentCol] == '0') {
                statusLabel.setText("位置 (" + currentRow + "," + currentCol + ") 是水域，跳过");
            } else {
                statusLabel.setText("位置 (" + currentRow + "," + currentCol + ") 已访问过，跳过");
            }
            foundNewIsland = false;
            currentPhase = "扫描";
        }
        
        currentCol++;
        SwingUtilities.invokeLater(() -> visualPanel.repaint());
    }
    
    private void dfsMarkIsland(int row, int col) {
        // 边界检查
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length || 
            visited[row][col] || grid[row][col] == '0') {
            return;
        }
        
        // 标记为已访问
        visited[row][col] = true;
        currentIslandCells.add(new Point(col, row));
        
        // 递归访问四个方向
        for (int i = 0; i < 4; i++) {
            dfsMarkIsland(row + dx[i], col + dy[i]);
        }
    }
    
    private void resetAnimation() {
        stopAnimation();
        resetData();
        
        statusLabel.setText("点击开始按钮启动动画演示");
        islandCountLabel.setText("发现岛屿数量：0");
        SwingUtilities.invokeLater(() -> visualPanel.repaint());
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
            drawIslandCells(g2d);
            drawLegend(g2d);
            drawAlgorithmInfo(g2d);
        }
        
        private void drawGrid(Graphics2D g2d) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    int x = GRID_START_X + j * CELL_SIZE;
                    int y = GRID_START_Y + i * CELL_SIZE;
                    
                    // 绘制格子背景
                    if (visited[i][j]) {
                        g2d.setColor(new Color(255, 182, 193)); // 浅粉色 - 已访问的陆地
                    } else if (grid[i][j] == '1') {
                        g2d.setColor(new Color(34, 139, 34)); // 森林绿 - 未访问陆地
                    } else {
                        g2d.setColor(new Color(135, 206, 235)); // 天蓝色 - 水域
                    }
                    g2d.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                    
                    // 绘制边框
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                    
                    // 绘制坐标
                    g2d.setFont(new Font("Arial", Font.BOLD, 10));
                    g2d.setColor(Color.WHITE);
                    g2d.drawString("(" + i + "," + j + ")", x + 2, y + 12);
                    
                    // 绘制值
                    g2d.setFont(new Font("Arial", Font.BOLD, 24));
                    if (visited[i][j]) {
                        g2d.setColor(Color.RED);
                        g2d.drawString("✓", x + CELL_SIZE/2 - 8, y + CELL_SIZE/2 + 8);
                    } else {
                        g2d.setColor(Color.WHITE);
                        g2d.drawString(String.valueOf(grid[i][j]), x + CELL_SIZE/2 - 8, y + CELL_SIZE/2 + 8);
                    }
                }
            }
        }
        
        private void drawCurrentPosition(Graphics2D g2d) {
            if (currentRow < grid.length && currentCol < grid[0].length) {
                int x = GRID_START_X + currentCol * CELL_SIZE;
                int y = GRID_START_Y + currentRow * CELL_SIZE;
                
                // 高亮当前扫描位置
                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(4));
                g2d.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                
                // 绘制扫描指示器
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 12));
                g2d.setColor(Color.RED);
                g2d.drawString("当前扫描", x + 5, y - 5);
            }
        }
        
        private void drawIslandCells(Graphics2D g2d) {
            if (foundNewIsland && !currentIslandCells.isEmpty()) {
                g2d.setColor(Color.ORANGE);
                g2d.setStroke(new BasicStroke(3));
                
                for (Point cell : currentIslandCells) {
                    int x = GRID_START_X + cell.x * CELL_SIZE;
                    int y = GRID_START_Y + cell.y * CELL_SIZE;
                    g2d.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                }
            }
        }
        
        private void drawLegend(Graphics2D g2d) {
            int legendX = GRID_START_X + grid[0].length * CELL_SIZE + 50;
            int legendY = GRID_START_Y;
            
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            g2d.setColor(Color.BLACK);
            g2d.drawString("图例：", legendX, legendY);
            
            // 未访问陆地
            g2d.setColor(new Color(34, 139, 34));
            g2d.fillRect(legendX, legendY + 20, 20, 20);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(legendX, legendY + 20, 20, 20);
            g2d.drawString("未访问陆地 (1)", legendX + 30, legendY + 35);
            
            // 已访问陆地
            g2d.setColor(new Color(255, 182, 193));
            g2d.fillRect(legendX, legendY + 50, 20, 20);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(legendX, legendY + 50, 20, 20);
            g2d.drawString("已访问陆地", legendX + 30, legendY + 65);
            
            // 水域
            g2d.setColor(new Color(135, 206, 235));
            g2d.fillRect(legendX, legendY + 80, 20, 20);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(legendX, legendY + 80, 20, 20);
            g2d.drawString("水域 (0)", legendX + 30, legendY + 95);
            
            // 当前位置
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawRect(legendX, legendY + 110, 20, 20);
            g2d.setColor(Color.BLACK);
            g2d.drawString("当前扫描位置", legendX + 30, legendY + 125);
            
            // 当前岛屿
            g2d.setColor(Color.ORANGE);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect(legendX, legendY + 140, 20, 20);
            g2d.setColor(Color.BLACK);
            g2d.drawString("当前岛屿", legendX + 30, legendY + 155);
        }
        
        private void drawAlgorithmInfo(Graphics2D g2d) {
            int infoX = GRID_START_X;
            int infoY = GRID_START_Y + grid.length * CELL_SIZE + 50;
            
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g2d.setColor(Color.BLACK);
            g2d.drawString("算法步骤：", infoX, infoY);
            
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            g2d.drawString("1. 遍历网格中的每个位置", infoX, infoY + 25);
            g2d.drawString("2. 遇到未访问的陆地'1'时，岛屿数量+1", infoX, infoY + 45);
            g2d.drawString("3. 使用DFS标记整个岛屿为已访问", infoX, infoY + 65);
            g2d.drawString("4. 继续扫描直到遍历完所有位置", infoX, infoY + 85);
            
            // 当前阶段
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            g2d.setColor(Color.BLUE);
            g2d.drawString("当前阶段：" + currentPhase, infoX + 300, infoY + 25);
            
            if (foundNewIsland) {
                g2d.setColor(Color.RED);
                g2d.drawString("发现新岛屿！正在DFS标记...", infoX + 300, infoY + 45);
                g2d.drawString("当前岛屿包含 " + currentIslandCells.size() + " 个格子", infoX + 300, infoY + 65);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO200_N_NumberOfIslands_Animation().setVisible(true);
        });
    }
}