package com.animation.bfs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * LeetCode 1654. 到家的最少跳跃次数 (Minimum Jumps to Reach Home) 动画演示
 * 
 * 问题描述：
 * 有一只跳蚤的家在数轴上的位置 x 处。请你帮助它从位置 0 出发，到达它的家。
 * 跳蚤跳跃的规则如下：
 * - 它可以 往前 跳恰好 a 个位置（即往右跳）。
 * - 它可以 往后 跳恰好 b 个位置（即往左跳）。
 * - 它不能 连续 往后跳 2 次。
 * - 它不能跳到任何 forbidden 数组中的位置。
 * 跳蚤可以往前跳 超过 它的家的位置，但是它 不能跳到负数 的位置。
 * 给你一个整数数组 forbidden ，其中 forbidden[i] 是跳蚤不能跳到的位置，
 * 返回跳蚤到家的最少跳跃次数。如果没有恰好到达的方法，则返回 -1 。
 * 
 * 算法思路：
 * 使用广度优先搜索(BFS)
 * 1. 状态定义：(position, canJumpBack) - 位置和是否可以向后跳
 * 2. 从(0, true)开始BFS
 * 3. 对于每个状态，尝试向前跳和向后跳（如果允许）
 * 4. 避免访问禁止位置和重复状态
 * 5. 设置合理的搜索边界避免无限搜索
 * 
 * 时间复杂度：O(max(x, max(forbidden)) * 2)
 * 空间复杂度：O(max(x, max(forbidden)) * 2)
 */
public class NO1654_N_MinimumJumps_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    private static final int CELL_SIZE = 40;
    private static final int START_X = 50;
    private static final int START_Y = 300;
    private static final int MAX_DISPLAY = 25; // 最多显示25个位置
    
    // 算法相关变量
    private int[] forbidden = {14, 4, 18, 1, 15};
    private int a = 3; // 向前跳跃距离
    private int b = 2; // 向后跳跃距离
    private int x = 11; // 目标位置
    
    private Queue<State> bfsQueue;
    private Set<String> visited;
    private Set<Integer> forbiddenSet;
    private int currentPosition = 0;
    private boolean canJumpBack = true;
    private int jumpCount = 0;
    private boolean foundTarget = false;
    private boolean algorithmComplete = false;
    private List<Integer> searchPath;
    private State currentState;
    
    // 动画相关变量
    private double animationProgress = 0;
    private boolean isAnimating = false;
    private javax.swing.Timer animationTimer;
    private int animationFromPos = -1;
    private int animationToPos = -1;
    
    // UI组件
    private JButton startButton;
    private JButton resetButton;
    private JButton stepButton;
    private JTextArea logArea;
    private JLabel statusLabel;
    
    // 状态类
    private static class State {
        int position;
        boolean canJumpBack;
        int steps;
        
        State(int position, boolean canJumpBack, int steps) {
            this.position = position;
            this.canJumpBack = canJumpBack;
            this.steps = steps;
        }
        
        String getKey() {
            return position + "," + canJumpBack;
        }
    }
    
    public NO1654_N_MinimumJumps_Animation() {
        initializeUI();
        initializeAlgorithm();
    }
    
    private void initializeUI() {
        setTitle("LeetCode 1654. 到家的最少跳跃次数 (Minimum Jumps to Reach Home) - BFS算法动画演示");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // 设置Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        setLayout(new BorderLayout());
        
        // 创建绘图面板
        JPanel drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawAnimation(g);
            }
        };
        drawPanel.setBackground(Color.WHITE);
        drawPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, 450));
        
        // 创建控制面板
        JPanel controlPanel = createControlPanel();
        
        // 创建信息面板
        JPanel infoPanel = createInfoPanel();
        
        add(drawPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        add(infoPanel, BorderLayout.EAST);
        
        // 设置动画定时器
        animationTimer = new javax.swing.Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isAnimating) {
                    animationProgress += 0.08;
                    if (animationProgress >= 1.0) {
                        animationProgress = 1.0;
                        currentPosition = animationToPos;
                        isAnimating = false;
                        animationFromPos = -1;
                        animationToPos = -1;
                    }
                    repaint();
                }
            }
        });
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        startButton = new JButton("开始动画");
        resetButton = new JButton("重置");
        stepButton = new JButton("单步执行");
        
        startButton.addActionListener(e -> startAnimation());
        resetButton.addActionListener(e -> resetAnimation());
        stepButton.addActionListener(e -> stepAnimation());
        
        panel.add(startButton);
        panel.add(stepButton);
        panel.add(resetButton);
        
        statusLabel = new JLabel("准备开始到家的最少跳跃次数动画演示");
        panel.add(statusLabel);
        
        return panel;
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(300, 0));
        
        JLabel titleLabel = new JLabel("算法信息", JLabel.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void initializeAlgorithm() {
        bfsQueue = new LinkedList<>();
        visited = new HashSet<>();
        forbiddenSet = new HashSet<>();
        searchPath = new ArrayList<>();
        
        for (int pos : forbidden) {
            forbiddenSet.add(pos);
        }
        
        currentPosition = 0;
        canJumpBack = true;
        jumpCount = 0;
        foundTarget = false;
        algorithmComplete = false;
        
        currentState = new State(0, true, 0);
        bfsQueue.offer(currentState);
        visited.add(currentState.getKey());
        searchPath.add(0);
        
        updateLog("初始化完成");
        updateLog("禁止位置: " + Arrays.toString(forbidden));
        updateLog("向前跳跃距离: " + a);
        updateLog("向后跳跃距离: " + b);
        updateLog("目标位置: " + x);
        updateLog("起始位置: 0");
        updateLog("算法: 广度优先搜索(BFS)");
        updateLog("状态: (位置, 是否可向后跳)");
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制数轴
        drawNumberLine(g2d);
        
        // 绘制搜索路径
        drawSearchPath(g2d);
        
        // 绘制可能的跳跃
        drawPossibleJumps(g2d);
        
        // 绘制跳蚤
        drawFlea(g2d);
        
        // 绘制BFS队列信息
        drawBFSQueue(g2d);
        
        // 绘制算法信息
        drawAlgorithmInfo(g2d);
    }
    
    private void drawNumberLine(Graphics2D g2d) {
        // 绘制数轴
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(START_X, START_Y, START_X + MAX_DISPLAY * CELL_SIZE, START_Y);
        
        for (int i = 0; i <= MAX_DISPLAY; i++) {
            int x = START_X + i * CELL_SIZE;
            int y = START_Y;
            
            // 背景颜色
            if (i == this.x) {
                g2d.setColor(Color.YELLOW); // 目标位置
                g2d.fillRect(x - CELL_SIZE/2, y - CELL_SIZE/2, CELL_SIZE, CELL_SIZE);
            } else if (forbiddenSet.contains(i)) {
                g2d.setColor(Color.RED); // 禁止位置
                g2d.fillRect(x - CELL_SIZE/2, y - CELL_SIZE/2, CELL_SIZE, CELL_SIZE);
            } else if (i == currentPosition) {
                g2d.setColor(Color.LIGHT_GRAY); // 当前位置
                g2d.fillRect(x - CELL_SIZE/2, y - CELL_SIZE/2, CELL_SIZE, CELL_SIZE);
            }
            
            // 刻度线
            g2d.setColor(Color.BLACK);
            g2d.drawLine(x, y - 10, x, y + 10);
            
            // 数字标签
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            String label = String.valueOf(i);
            FontMetrics fm = g2d.getFontMetrics();
            g2d.drawString(label, x - fm.stringWidth(label)/2, y + 25);
            
            // 边框
            if (i == this.x || forbiddenSet.contains(i) || i == currentPosition) {
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x - CELL_SIZE/2, y - CELL_SIZE/2, CELL_SIZE, CELL_SIZE);
            }
        }
    }
    
    private void drawSearchPath(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(new Color(0, 150, 0, 150));
        
        for (int i = 0; i < searchPath.size() - 1; i++) {
            int from = searchPath.get(i);
            int to = searchPath.get(i + 1);
            
            if (from <= MAX_DISPLAY && to <= MAX_DISPLAY) {
                int fromX = START_X + from * CELL_SIZE;
                int fromY = START_Y - 30;
                int toX = START_X + to * CELL_SIZE;
                int toY = START_Y - 30;
                
                drawArrowLine(g2d, fromX, fromY, toX, toY);
            }
        }
    }
    
    private void drawPossibleJumps(Graphics2D g2d) {
        if (!isAnimating && !algorithmComplete && currentPosition <= MAX_DISPLAY) {
            g2d.setColor(new Color(0, 255, 0, 100));
            
            // 向前跳跃
            int forwardPos = currentPosition + a;
            if (forwardPos <= MAX_DISPLAY && !forbiddenSet.contains(forwardPos)) {
                int x = START_X + forwardPos * CELL_SIZE;
                g2d.fillOval(x - 15, START_Y - 15, 30, 30);
                
                // 绘制箭头
                g2d.setColor(Color.GREEN);
                g2d.setStroke(new BasicStroke(2));
                drawArrowLine(g2d, START_X + currentPosition * CELL_SIZE, START_Y - 50,
                             START_X + forwardPos * CELL_SIZE, START_Y - 50);
                g2d.drawString("+" + a, START_X + currentPosition * CELL_SIZE + 10, START_Y - 55);
            }
            
            // 向后跳跃（如果允许）
            if (canJumpBack) {
                int backwardPos = currentPosition - b;
                if (backwardPos >= 0 && backwardPos <= MAX_DISPLAY && !forbiddenSet.contains(backwardPos)) {
                    int x = START_X + backwardPos * CELL_SIZE;
                    g2d.setColor(new Color(255, 255, 0, 100));
                    g2d.fillOval(x - 15, START_Y - 15, 30, 30);
                    
                    // 绘制箭头
                    g2d.setColor(Color.ORANGE);
                    g2d.setStroke(new BasicStroke(2));
                    drawArrowLine(g2d, START_X + currentPosition * CELL_SIZE, START_Y + 50,
                                 START_X + backwardPos * CELL_SIZE, START_Y + 50);
                    g2d.drawString("-" + b, START_X + currentPosition * CELL_SIZE - 20, START_Y + 45);
                }
            }
        }
    }
    
    private void drawArrowLine(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        g2d.drawLine(x1, y1, x2, y2);
        
        // 绘制箭头
        double angle = Math.atan2(y2 - y1, x2 - x1);
        int arrowLength = 8;
        double arrowAngle = Math.PI / 6;
        
        int x3 = (int) (x2 - arrowLength * Math.cos(angle - arrowAngle));
        int y3 = (int) (y2 - arrowLength * Math.sin(angle - arrowAngle));
        int x4 = (int) (x2 - arrowLength * Math.cos(angle + arrowAngle));
        int y4 = (int) (y2 - arrowLength * Math.sin(angle + arrowAngle));
        
        g2d.drawLine(x2, y2, x3, y3);
        g2d.drawLine(x2, y2, x4, y4);
    }
    
    private void drawFlea(Graphics2D g2d) {
        double currentX, currentY;
        
        if (isAnimating && animationFromPos >= 0 && animationToPos >= 0 && 
            animationFromPos <= MAX_DISPLAY && animationToPos <= MAX_DISPLAY) {
            double startPosX = START_X + animationFromPos * CELL_SIZE;
            double endPosX = START_X + animationToPos * CELL_SIZE;
            currentX = startPosX + (endPosX - startPosX) * animationProgress;
            
            double jumpHeight = Math.sin(animationProgress * Math.PI) * 40;
            currentY = START_Y - jumpHeight;
        } else if (currentPosition <= MAX_DISPLAY) {
            currentX = START_X + currentPosition * CELL_SIZE;
            currentY = START_Y;
        } else {
            return; // 不在显示范围内
        }
        
        // 绘制跳蚤
        if (foundTarget) {
            g2d.setColor(Color.GREEN); // 找到目标时变绿
        } else {
            g2d.setColor(Color.BLUE);
        }
        
        // 跳蚤身体
        g2d.fillOval((int)currentX - 12, (int)currentY - 25, 24, 20);
        
        // 跳蚤腿
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine((int)currentX - 8, (int)currentY - 10, (int)currentX - 15, (int)currentY + 5);
        g2d.drawLine((int)currentX + 8, (int)currentY - 10, (int)currentX + 15, (int)currentY + 5);
        
        // 跳蚤眼睛
        g2d.setColor(Color.WHITE);
        g2d.fillOval((int)currentX - 8, (int)currentY - 22, 6, 6);
        g2d.fillOval((int)currentX + 2, (int)currentY - 22, 6, 6);
        
        g2d.setColor(Color.BLACK);
        g2d.fillOval((int)currentX - 6, (int)currentY - 20, 2, 2);
        g2d.fillOval((int)currentX + 4, (int)currentY - 20, 2, 2);
        
        // 状态指示
        if (!canJumpBack) {
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD, 10));
            g2d.drawString("不能后跳", (int)currentX - 15, (int)currentY - 35);
        }
    }
    
    private void drawBFSQueue(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g2d.drawString("BFS队列大小: " + bfsQueue.size(), 20, 400);
        
        g2d.setFont(new Font("Consolas", Font.PLAIN, 12));
        g2d.drawString("已访问状态数: " + visited.size(), 20, 420);
        
        if (currentState != null) {
            g2d.drawString("当前状态: (" + currentState.position + ", " + 
                          (currentState.canJumpBack ? "可后跳" : "不可后跳") + ", 步数:" + currentState.steps + ")", 20, 440);
        }
    }
    
    private void drawAlgorithmInfo(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.drawString("到家的最少跳跃次数 - 广度优先搜索", 20, 30);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.drawString("当前位置: " + currentPosition, 20, 60);
        g2d.drawString("目标位置: " + x, 150, 60);
        g2d.drawString("跳跃次数: " + jumpCount, 250, 60);
        g2d.drawString("向前跳: +" + a, 350, 60);
        g2d.drawString("向后跳: -" + b, 450, 60);
        g2d.drawString("可后跳: " + (canJumpBack ? "是" : "否"), 550, 60);
        
        if (foundTarget) {
            g2d.setColor(Color.GREEN);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g2d.drawString("找到目标！最少跳跃次数: " + jumpCount, 20, 90);
        }
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawString("黄色: 目标位置", 20, 460);
        g2d.drawString("红色: 禁止位置", 120, 460);
        g2d.drawString("绿色圆圈: 可向前跳", 220, 460);
        g2d.drawString("黄色圆圈: 可向后跳", 350, 460);
    }
    
    private void startAnimation() {
        if (!animationTimer.isRunning()) {
            animationTimer.start();
        }
        
        javax.swing.Timer autoTimer = new javax.swing.Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAnimating && !algorithmComplete) {
                    stepAnimation();
                } else if (algorithmComplete) {
                    ((javax.swing.Timer) e.getSource()).stop();
                }
            }
        });
        autoTimer.start();
    }
    
    private void stepAnimation() {
        if (isAnimating || algorithmComplete) return;
        
        if (bfsQueue.isEmpty()) {
            algorithmComplete = true;
            updateLog("BFS完成！无法到达目标位置");
            statusLabel.setText("搜索完成 - 无法到达目标");
            return;
        }
        
        currentState = bfsQueue.poll();
        currentPosition = currentState.position;
        canJumpBack = currentState.canJumpBack;
        jumpCount = currentState.steps;
        
        updateLog("访问状态: (" + currentPosition + ", " + (canJumpBack ? "可后跳" : "不可后跳") + 
                 ", 步数:" + jumpCount + ")");
        
        if (currentPosition == x) {
            foundTarget = true;
            algorithmComplete = true;
            updateLog("找到目标！最少跳跃次数: " + jumpCount);
            statusLabel.setText("成功到达目标 - 最少跳跃次数: " + jumpCount);
            return;
        }
        
        // 向前跳跃
        int forwardPos = currentPosition + a;
        if (forwardPos <= Math.max(x, Collections.max(forbiddenSet)) + a && !forbiddenSet.contains(forwardPos)) {
            String forwardKey = forwardPos + ",true";
            if (!visited.contains(forwardKey)) {
                visited.add(forwardKey);
                bfsQueue.offer(new State(forwardPos, true, jumpCount + 1));
                updateLog("添加向前跳跃状态: (" + forwardPos + ", 可后跳, 步数:" + (jumpCount + 1) + ")");
            }
        }
        
        // 向后跳跃（如果允许）
        if (canJumpBack) {
            int backwardPos = currentPosition - b;
            if (backwardPos >= 0 && !forbiddenSet.contains(backwardPos)) {
                String backwardKey = backwardPos + ",false";
                if (!visited.contains(backwardKey)) {
                    visited.add(backwardKey);
                    bfsQueue.offer(new State(backwardPos, false, jumpCount + 1));
                    updateLog("添加向后跳跃状态: (" + backwardPos + ", 不可后跳, 步数:" + (jumpCount + 1) + ")");
                }
            }
        }
        
        statusLabel.setText("位置: " + currentPosition + ", 步数: " + jumpCount + ", 队列: " + bfsQueue.size());
        repaint();
    }
    
    private void resetAnimation() {
        if (animationTimer.isRunning()) {
            animationTimer.stop();
        }
        
        initializeAlgorithm();
        animationProgress = 0;
        isAnimating = false;
        animationFromPos = -1;
        animationToPos = -1;
        statusLabel.setText("已重置，准备开始新的演示");
        repaint();
    }
    
    private void updateLog(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO1654_N_MinimumJumps_Animation().setVisible(true);
        });
    }
}