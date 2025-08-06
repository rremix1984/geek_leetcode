package com.animation.hard;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * NO.773 滑动谜题算法动画演示
 * 
 * 算法核心：
 * 1. 使用BFS广度优先搜索找到最短路径
 * 2. 将2x3棋盘状态序列化为字符串进行状态管理
 * 3. 通过邻接关系定义可移动的位置
 * 4. 目标状态为"123450"
 * 
 * 动画特点：
 * - 实时显示BFS搜索过程
 * - 可视化队列状态和已访问状态
 * - 展示状态转换过程
 * - 高亮当前处理状态和目标状态
 */
public class NO773_H_SlidingPuzzle_Animation extends JFrame {
    
    // 界面组件
    private JPanel drawPanel;
    private JButton startButton;
    private JButton resetButton;
    private JComboBox<String> testCaseCombo;
    private Timer animationTimer;
    
    // 算法相关
    private int[][] currentBoard;
    private Queue<String> queue;
    private Set<String> visited;
    private String currentState;
    private String targetState = "123450";
    private int step;
    private boolean isCompleted;
    private String statusMessage;
    private List<String> searchHistory;
    private int currentLevel;
    
    // 邻接关系：每个位置可以移动到的相邻位置
    private final int[][] neighbors = {
        {1, 3},     // 位置0可以移动到位置1,3
        {0, 2, 4},  // 位置1可以移动到位置0,2,4
        {1, 5},     // 位置2可以移动到位置1,5
        {0, 4},     // 位置3可以移动到位置0,4
        {1, 3, 5},  // 位置4可以移动到位置1,3,5
        {2, 4}      // 位置5可以移动到位置2,4
    };
    
    // 测试用例
    private final int[][][] testCases = {
        {{1, 2, 3}, {4, 0, 5}}, // 示例1：1步解决
        {{4, 1, 2}, {5, 0, 3}}, // 示例3：5步解决
        {{1, 2, 3}, {5, 4, 0}}, // 示例2：无解
        {{0, 1, 3}, {4, 2, 5}}  // 自定义测试用例
    };
    
    private final String[] testCaseNames = {
        "示例1: [[1,2,3],[4,0,5]] - 1步",
        "示例3: [[4,1,2],[5,0,3]] - 5步", 
        "示例2: [[1,2,3],[5,4,0]] - 无解",
        "自定义: [[0,1,3],[4,2,5]]"
    };
    
    public NO773_H_SlidingPuzzle_Animation() {
        initializeGUI();
        loadTestCase(0);
    }
    
    private void initializeGUI() {
        setTitle("NO.773 滑动谜题算法动画演示");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // 创建控制面板
        JPanel controlPanel = new JPanel(new FlowLayout());
        
        // 测试用例选择
        testCaseCombo = new JComboBox<>(testCaseNames);
        testCaseCombo.addActionListener(e -> {
            if (!animationTimer.isRunning()) {
                loadTestCase(testCaseCombo.getSelectedIndex());
                SwingUtilities.invokeLater(() -> drawPanel.repaint());
            }
        });
        controlPanel.add(new JLabel("测试用例:"));
        controlPanel.add(testCaseCombo);
        
        // 开始按钮
        startButton = new JButton("开始演示");
        startButton.addActionListener(e -> startAnimation());
        controlPanel.add(startButton);
        
        // 重置按钮
        resetButton = new JButton("重置");
        resetButton.addActionListener(e -> resetAnimation());
        controlPanel.add(resetButton);
        
        add(controlPanel, BorderLayout.NORTH);
        
        // 创建绘图面板
        drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawAnimation(g);
            }
        };
        drawPanel.setBackground(Color.WHITE);
        add(drawPanel, BorderLayout.CENTER);
        
        // 创建动画定时器
        animationTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performBFSStep();
                SwingUtilities.invokeLater(() -> drawPanel.repaint());
            }
        });
    }
    
    private void loadTestCase(int index) {
        currentBoard = new int[2][3];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                currentBoard[i][j] = testCases[index][i][j];
            }
        }
        
        // 重置算法状态
        queue = new LinkedList<>();
        visited = new HashSet<>();
        searchHistory = new ArrayList<>();
        
        currentState = boardToString(currentBoard);
        queue.offer(currentState);
        visited.add(currentState);
        searchHistory.add(currentState);
        
        step = 0;
        currentLevel = 0;
        isCompleted = false;
        statusMessage = "已加载测试用例 " + (index + 1) + "，点击开始按钮开始BFS搜索";
    }
    
    private String boardToString(int[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                sb.append(board[i][j]);
            }
        }
        return sb.toString();
    }
    
    private int[][] stringToBoard(String state) {
        int[][] board = new int[2][3];
        for (int i = 0; i < 6; i++) {
            board[i / 3][i % 3] = state.charAt(i) - '0';
        }
        return board;
    }
    
    private void performBFSStep() {
        if (queue.isEmpty() || isCompleted) {
            animationTimer.stop();
            startButton.setText("开始演示");
            if (!isCompleted) {
                statusMessage = "搜索完成，无解！";
            }
            return;
        }
        
        // 处理当前层的所有状态
        int levelSize = queue.size();
        boolean foundTarget = false;
        
        for (int i = 0; i < levelSize && !foundTarget; i++) {
            currentState = queue.poll();
            
            // 检查是否到达目标状态
            if (targetState.equals(currentState)) {
                isCompleted = true;
                foundTarget = true;
                statusMessage = "找到解！最少步数: " + currentLevel;
                animationTimer.stop();
                startButton.setText("开始演示");
                break;
            }
            
            // 生成所有可能的下一状态
            List<String> nextStates = getNextStates(currentState);
            for (String nextState : nextStates) {
                if (!visited.contains(nextState)) {
                    queue.offer(nextState);
                    visited.add(nextState);
                    searchHistory.add(nextState);
                }
            }
        }
        
        if (!foundTarget) {
            currentLevel++;
            statusMessage = "BFS第 " + currentLevel + " 层搜索中，队列大小: " + queue.size() + 
                          "，已访问状态: " + visited.size();
        }
    }
    
    private List<String> getNextStates(String state) {
        List<String> nextStates = new ArrayList<>();
        char[] array = state.toCharArray();
        int zeroPos = state.indexOf('0');
        
        for (int neighbor : neighbors[zeroPos]) {
            // 交换0和邻居位置
            char temp = array[zeroPos];
            array[zeroPos] = array[neighbor];
            array[neighbor] = temp;
            
            nextStates.add(new String(array));
            
            // 恢复原状态
            array[neighbor] = array[zeroPos];
            array[zeroPos] = temp;
        }
        
        return nextStates;
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制标题
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 24));
        g2d.setColor(new Color(51, 51, 51));
        String title = "NO.773 滑动谜题算法演示 (BFS广度优先搜索)";
        FontMetrics fm = g2d.getFontMetrics();
        int titleX = (getWidth() - fm.stringWidth(title)) / 2;
        g2d.drawString(title, titleX, 40);
        
        // 绘制当前棋盘状态
        drawCurrentBoard(g2d);
        
        // 绘制目标状态
        drawTargetBoard(g2d);
        
        // 绘制队列状态
        drawQueueStatus(g2d);
        
        // 绘制搜索历史
        drawSearchHistory(g2d);
        
        // 绘制算法说明
        drawAlgorithmInfo(g2d);
        
        // 绘制状态信息
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(new Color(255, 69, 0));
        g2d.drawString("状态: " + statusMessage, 50, getHeight() - 50);
        
        // 绘制复杂度信息
        drawComplexityInfo(g2d);
    }
    
    private void drawCurrentBoard(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(new Color(51, 51, 51));
        g2d.drawString("当前状态:", 50, 90);
        
        if (currentState != null) {
            int[][] board = stringToBoard(currentState);
            drawBoard(g2d, board, 50, 110, true);
            
            // 显示状态字符串
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.setColor(new Color(0, 100, 0));
            g2d.drawString("状态: " + currentState, 50, 250);
        }
    }
    
    private void drawTargetBoard(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(new Color(51, 51, 51));
        g2d.drawString("目标状态:", 300, 90);
        
        int[][] targetBoard = {{1, 2, 3}, {4, 5, 0}};
        drawBoard(g2d, targetBoard, 300, 110, false);
        
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.setColor(new Color(0, 100, 0));
        g2d.drawString("状态: " + targetState, 300, 250);
    }
    
    private void drawBoard(Graphics2D g2d, int[][] board, int startX, int startY, boolean isCurrent) {
        int cellSize = 40;
        int gap = 2;
        
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                int x = startX + j * (cellSize + gap);
                int y = startY + i * (cellSize + gap);
                
                if (board[i][j] == 0) {
                    // 空格
                    g2d.setColor(new Color(240, 240, 240));
                    g2d.fillRect(x, y, cellSize, cellSize);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(x, y, cellSize, cellSize);
                } else {
                    // 数字块
                    Color bgColor = isCurrent ? new Color(173, 216, 230) : new Color(144, 238, 144);
                    g2d.setColor(bgColor);
                    g2d.fillRect(x, y, cellSize, cellSize);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(x, y, cellSize, cellSize);
                    
                    // 绘制数字
                    g2d.setFont(new Font("Arial", Font.BOLD, 20));
                    String num = String.valueOf(board[i][j]);
                    FontMetrics fm = g2d.getFontMetrics();
                    int textX = x + (cellSize - fm.stringWidth(num)) / 2;
                    int textY = y + (cellSize + fm.getAscent()) / 2;
                    g2d.drawString(num, textX, textY);
                }
            }
        }
    }
    
    private void drawQueueStatus(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(new Color(51, 51, 51));
        g2d.drawString("BFS队列状态:", 550, 90);
        
        if (queue != null) {
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.setColor(new Color(0, 0, 255));
            g2d.drawString("队列大小: " + queue.size(), 550, 110);
            g2d.drawString("当前层级: " + currentLevel, 550, 130);
            g2d.drawString("已访问状态数: " + (visited != null ? visited.size() : 0), 550, 150);
            
            // 显示队列前几个状态
            if (!queue.isEmpty()) {
                g2d.setColor(new Color(51, 51, 51));
                g2d.drawString("队列前5个状态:", 550, 180);
                
                int count = 0;
                for (String state : queue) {
                    if (count >= 5) break;
                    g2d.setFont(new Font("Arial", Font.PLAIN, 12));
                    g2d.setColor(new Color(100, 100, 100));
                    g2d.drawString((count + 1) + ". " + state, 550, 200 + count * 20);
                    count++;
                }
            }
        }
    }
    
    private void drawSearchHistory(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(new Color(51, 51, 51));
        g2d.drawString("搜索历史 (最近10个状态):", 50, 300);
        
        if (searchHistory != null && !searchHistory.isEmpty()) {
            int startIndex = Math.max(0, searchHistory.size() - 10);
            for (int i = startIndex; i < searchHistory.size(); i++) {
                String state = searchHistory.get(i);
                g2d.setFont(new Font("Arial", Font.PLAIN, 12));
                
                if (state.equals(currentState)) {
                    g2d.setColor(new Color(255, 69, 0)); // 当前状态
                } else if (state.equals(targetState)) {
                    g2d.setColor(new Color(0, 128, 0)); // 目标状态
                } else {
                    g2d.setColor(new Color(100, 100, 100));
                }
                
                g2d.drawString((i + 1) + ". " + state, 50, 320 + (i - startIndex) * 18);
            }
        }
    }
    
    private void drawAlgorithmInfo(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(new Color(51, 51, 51));
        
        String[] info = {
            "算法原理:",
            "1. 使用BFS广度优先搜索保证找到最短路径",
            "2. 将2x3棋盘状态序列化为6位字符串",
            "3. 根据空格(0)位置确定可移动方向",
            "4. 逐层搜索直到找到目标状态'123450'",
            "",
            "邻接关系:",
            "位置0→{1,3}, 位置1→{0,2,4}, 位置2→{1,5}",
            "位置3→{0,4}, 位置4→{1,3,5}, 位置5→{2,4}"
        };
        
        int startY = 500;
        for (int i = 0; i < info.length; i++) {
            if (i == 0 || i == 6) {
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
                g2d.setColor(new Color(255, 69, 0));
            } else {
                g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                g2d.setColor(new Color(51, 51, 51));
            }
            g2d.drawString(info[i], 550, startY + i * 18);
        }
    }
    
    private void drawComplexityInfo(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g2d.setColor(new Color(0, 100, 0));
        g2d.drawString("时间复杂度: O(6!)", 800, getHeight() - 80);
        g2d.drawString("空间复杂度: O(6!)", 800, getHeight() - 60);
        g2d.drawString("核心技术: BFS + 状态压缩", 800, getHeight() - 40);
    }
    
    private void startAnimation() {
        if (animationTimer.isRunning()) {
            animationTimer.stop();
            startButton.setText("开始演示");
        } else {
            if (isCompleted) {
                resetAnimation();
            }
            animationTimer.start();
            startButton.setText("暂停");
        }
    }
    
    private void resetAnimation() {
        animationTimer.stop();
        loadTestCase(testCaseCombo.getSelectedIndex());
        startButton.setText("开始演示");
        SwingUtilities.invokeLater(() -> drawPanel.repaint());
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO773_H_SlidingPuzzle_Animation().setVisible(true);
        });
    }
}