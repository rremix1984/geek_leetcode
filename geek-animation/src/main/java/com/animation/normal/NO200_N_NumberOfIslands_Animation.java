package com.animation.normal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * NO.200 岛屿数量算法动画演示
 * 
 * 算法描述：
 * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
 * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
 * 
 * 算法思路：
 * 使用深度优先搜索（DFS）遍历网格
 * 1. 遍历网格中的每个位置
 * 2. 当遇到 '1' 时，岛屿数量加1，并使用DFS将相邻的所有 '1' 标记为已访问
 * 3. DFS会将整个岛屿的所有陆地都标记为已访问
 * 
 * 时间复杂度：O(m * n)
 * 空间复杂度：O(m * n)
 */
public class NO200_N_NumberOfIslands_Animation extends JFrame {
    private JTextArea gridInput;
    private JButton searchButton;
    private JButton demoButton;
    private JButton clearButton;
    private JTextArea logArea;
    private IslandPanel visualPanel;
    
    private char[][] grid;
    private boolean[][] visited;
    private int islandCount;
    private javax.swing.Timer animationTimer;
    private int currentRow, currentCol;
    private java.util.List<Point> currentIsland;
    private java.util.List<java.util.List<Point>> allIslands;
    
    public NO200_N_NumberOfIslands_Animation() {
        initializeUI();
        allIslands = new ArrayList<>();
    }
    
    private void initializeUI() {
        setTitle("NO.200 岛屿数量算法动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // 控制面板
        JPanel controlPanel = new JPanel(new BorderLayout());
        
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("输入网格 (每行用换行分隔, 1表示陆地, 0表示水):"));
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        searchButton = new JButton("开始搜索");
        demoButton = new JButton("演示样例");
        clearButton = new JButton("清空");
        
        buttonPanel.add(searchButton);
        buttonPanel.add(demoButton);
        buttonPanel.add(clearButton);
        
        controlPanel.add(inputPanel, BorderLayout.NORTH);
        controlPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // 网格输入区域
        gridInput = new JTextArea(5, 30);
        gridInput.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        gridInput.setText("11110\n11010\n11000\n00000");
        JScrollPane gridScrollPane = new JScrollPane(gridInput);
        controlPanel.add(gridScrollPane, BorderLayout.CENTER);
        
        add(controlPanel, BorderLayout.NORTH);
        
        // 可视化面板
        visualPanel = new IslandPanel();
        visualPanel.setPreferredSize(new Dimension(600, 400));
        add(visualPanel, BorderLayout.CENTER);
        
        // 日志面板
        logArea = new JTextArea(8, 50);
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.SOUTH);
        
        // 事件监听
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSearch();
            }
        });
        
        demoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gridInput.setText("11110\n11010\n11000\n00000");
                startSearch();
            }
        });
        
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAll();
            }
        });
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private void startSearch() {
        try {
            String input = gridInput.getText().trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入网格数据");
                return;
            }
            
            String[] rows = input.split("\n");
            int m = rows.length;
            int n = rows[0].length();
            
            grid = new char[m][n];
            visited = new boolean[m][n];
            
            for (int i = 0; i < m; i++) {
                if (rows[i].length() != n) {
                    JOptionPane.showMessageDialog(this, "每行的长度必须相同");
                    return;
                }
                grid[i] = rows[i].toCharArray();
            }
            
            islandCount = 0;
            allIslands.clear();
            currentRow = 0;
            currentCol = 0;
            
            logArea.setText("");
            appendLog("开始搜索岛屿...");
            appendLog("网格大小: " + m + " x " + n);
            appendLog("使用深度优先搜索(DFS)算法\n");
            
            startAnimation();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "输入格式错误: " + e.getMessage());
        }
    }
    
    private void startAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        
        animationTimer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentRow < grid.length) {
                    if (currentCol < grid[0].length) {
                        appendLog("检查位置 (" + currentRow + ", " + currentCol + "): " + grid[currentRow][currentCol]);
                        
                        if (grid[currentRow][currentCol] == '1' && !visited[currentRow][currentCol]) {
                            islandCount++;
                            currentIsland = new ArrayList<>();
                            appendLog("发现新岛屿 #" + islandCount + ", 开始DFS探索...");
                            
                            dfs(currentRow, currentCol);
                            
                            allIslands.add(new ArrayList<>(currentIsland));
                            appendLog("岛屿 #" + islandCount + " 探索完成，包含 " + currentIsland.size() + " 个陆地单元\n");
                        }
                        
                        visualPanel.updateVisualization(grid, visited, currentRow, currentCol, allIslands, islandCount);
                        
                        currentCol++;
                        if (currentCol >= grid[0].length) {
                            currentCol = 0;
                            currentRow++;
                        }
                    }
                } else {
                    animationTimer.stop();
                    appendLog("搜索完成!");
                    appendLog("总共找到 " + islandCount + " 个岛屿");
                    
                    for (int i = 0; i < allIslands.size(); i++) {
                        appendLog("岛屿 #" + (i + 1) + " 包含位置: " + allIslands.get(i));
                    }
                }
            }
        });
        animationTimer.start();
    }
    
    private void dfs(int row, int col) {
        // 边界检查和有效性检查
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length ||
            grid[row][col] == '0' || visited[row][col]) {
            return;
        }
        
        // 标记为已访问
        visited[row][col] = true;
        currentIsland.add(new Point(row, col));
        appendLog("  访问陆地 (" + row + ", " + col + ")");
        
        // 递归搜索四个方向
        dfs(row - 1, col); // 上
        dfs(row + 1, col); // 下
        dfs(row, col - 1); // 左
        dfs(row, col + 1); // 右
    }
    
    private void clearAll() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        gridInput.setText("");
        logArea.setText("");
        allIslands.clear();
        visualPanel.clear();
    }
    
    private void appendLog(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }
    
    // 可视化面板
    private class IslandPanel extends JPanel {
        private char[][] currentGrid;
        private boolean[][] currentVisited;
        private int currentRow, currentCol;
        private java.util.List<java.util.List<Point>> islands;
        private int islandCount;
        
        public void updateVisualization(char[][] grid, boolean[][] visited, int row, int col, 
                                      java.util.List<java.util.List<Point>> islands, int count) {
            this.currentGrid = grid;
            this.currentVisited = visited;
            this.currentRow = row;
            this.currentCol = col;
            this.islands = islands;
            this.islandCount = count;
            visualPanel.repaint();
        }
        
        public void clear() {
            currentGrid = null;
            currentVisited = null;
            islands = null;
            visualPanel.repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (currentGrid == null) return;
            
            int width = getWidth();
            int height = getHeight();
            
            // 绘制标题
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.setColor(Color.BLACK);
            g2d.drawString("岛屿搜索可视化", 20, 30);
            
            // 计算网格绘制参数
            int rows = currentGrid.length;
            int cols = currentGrid[0].length;
            int cellSize = Math.min((width - 100) / cols, (height - 100) / rows);
            cellSize = Math.max(cellSize, 20); // 最小单元格大小
            
            int startX = (width - cols * cellSize) / 2;
            int startY = 60;
            
            // 定义岛屿颜色
            Color[] islandColors = {
                Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, 
                Color.MAGENTA, Color.CYAN, Color.PINK, Color.YELLOW
            };
            
            // 绘制网格
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    int x = startX + j * cellSize;
                    int y = startY + i * cellSize;
                    
                    // 确定单元格颜色
                    Color cellColor = Color.WHITE;
                    
                    if (currentGrid[i][j] == '0') {
                        cellColor = new Color(173, 216, 230); // 水
                    } else if (currentGrid[i][j] == '1') {
                        if (currentVisited != null && currentVisited[i][j]) {
                            // 找到这个点属于哪个岛屿
                            int islandIndex = -1;
                            for (int k = 0; k < islands.size(); k++) {
                                for (Point p : islands.get(k)) {
                                    if (p.x == i && p.y == j) {
                                        islandIndex = k;
                                        break;
                                    }
                                }
                                if (islandIndex != -1) break;
                            }
                            
                            if (islandIndex != -1) {
                                cellColor = islandColors[islandIndex % islandColors.length];
                            } else {
                                cellColor = Color.GRAY; // 已访问但未分配到岛屿
                            }
                        } else {
                            cellColor = Color.WHITE; // 未访问的陆地
                        }
                    }
                    
                    // 高亮当前检查的位置
                    if (i == currentRow && j == currentCol) {
                        cellColor = Color.YELLOW;
                    }
                    
                    g2d.setColor(cellColor);
                    g2d.fillRect(x, y, cellSize, cellSize);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(x, y, cellSize, cellSize);
                    
                    // 绘制单元格内容
                    g2d.setFont(new Font("Arial", Font.BOLD, Math.max(cellSize / 3, 10)));
                    String content = String.valueOf(currentGrid[i][j]);
                    FontMetrics fm = g2d.getFontMetrics();
                    int textX = x + (cellSize - fm.stringWidth(content)) / 2;
                    int textY = y + (cellSize + fm.getAscent()) / 2;
                    g2d.drawString(content, textX, textY);
                }
            }
            
            // 绘制统计信息
            g2d.setFont(new Font("Arial", Font.PLAIN, 14));
            g2d.setColor(Color.BLACK);
            int infoY = startY + rows * cellSize + 30;
            g2d.drawString("当前检查位置: (" + currentRow + ", " + currentCol + ")", 20, infoY);
            g2d.drawString("已发现岛屿数量: " + islandCount, 20, infoY + 20);
            
            // 绘制图例
            int legendY = infoY + 50;
            g2d.drawString("图例:", 20, legendY);
            
            g2d.setColor(new Color(173, 216, 230));
            g2d.fillRect(80, legendY - 15, 20, 15);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(80, legendY - 15, 20, 15);
            g2d.drawString("水域 (0)", 110, legendY);
            
            g2d.setColor(Color.WHITE);
            g2d.fillRect(200, legendY - 15, 20, 15);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(200, legendY - 15, 20, 15);
            g2d.drawString("未访问陆地 (1)", 230, legendY);
            
            g2d.setColor(Color.YELLOW);
            g2d.fillRect(360, legendY - 15, 20, 15);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(360, legendY - 15, 20, 15);
            g2d.drawString("当前检查位置", 390, legendY);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO200_N_NumberOfIslands_Animation().setVisible(true);
        });
    }
}