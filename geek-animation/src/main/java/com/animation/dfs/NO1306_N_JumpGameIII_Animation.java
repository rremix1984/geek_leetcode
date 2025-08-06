package com.animation.dfs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * LeetCode 1306. 跳跃游戏 III (Jump Game III) 动画演示
 * 
 * 问题描述：
 * 这里有一个非负整数数组 arr，你最开始位于该数组的起始下标 start 处。
 * 当你位于下标 i 处时，你可以跳到 i + arr[i] 或者 i - arr[i]。
 * 请你判断自己是否能够跳到对应元素值为 0 的任一下标处。
 * 
 * 算法思路：
 * 使用深度优先搜索(DFS)或广度优先搜索(BFS)
 * 1. 从起始位置开始搜索
 * 2. 对于每个位置，可以向前跳 arr[i] 步或向后跳 arr[i] 步
 * 3. 记录已访问的位置避免无限循环
 * 4. 如果找到值为0的位置，返回true
 * 5. 如果所有可能路径都搜索完毕仍未找到，返回false
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 */
public class NO1306_N_JumpGameIII_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    private static final int CELL_SIZE = 80;
    private static final int START_X = 50;
    private static final int START_Y = 250;
    
    // 算法相关变量
    private int[] arr = {4, 2, 3, 0, 3, 1, 2};
    private int startIndex = 5;
    private int currentPosition = 5;
    private Set<Integer> visited;
    private Stack<Integer> dfsStack;
    private List<Integer> searchPath;
    private boolean foundTarget = false;
    private boolean algorithmComplete = false;
    
    // 动画相关变量
    private double animationProgress = 0;
    private boolean isAnimating = false;
    private javax.swing.Timer animationTimer;
    private int animationFromPos = -1;
    private int animationToPos = -1;
    private javax.swing.Timer autoTimer; // 自动执行动画的Timer
    
    // UI组件
    private JButton startButton;
    private JButton resetButton;
    private JButton stepButton;
    private JButton backButton;
    private JTextArea logArea;
    private JLabel statusLabel;
    
    public NO1306_N_JumpGameIII_Animation() {
        initializeUI();
        initializeAlgorithm();
    }
    
    private void initializeUI() {
        setTitle("LeetCode 1306. 跳跃游戏 III (Jump Game III) - DFS算法动画演示");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        drawPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, 400));
        
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
                        // 动画结束后，如果不是自动模式，则不需要做什么
                        // 如果是自动模式，理论上 autoTimer 会触发下一次 step
                        // 但为了更流畅，可以在这里检查是否需要立即进行下一步
                        if (autoTimer != null && autoTimer.isRunning()) {
                             // 立即触发下一次逻辑，而不是等待 autoTimer 的延迟
                             stepAnimation(); 
                        }
                    }
                    SwingUtilities.invokeLater(() -> repaint());
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
        
        backButton = new JButton("返回首页");
        backButton.addActionListener(e -> {
            dispose(); // 关闭当前窗口
            com.animation.launcher.AlgorithmTreeLauncher.showMainWindow(); // 显示首页
        });
        
        panel.add(startButton);
        panel.add(stepButton);
        panel.add(resetButton);
        panel.add(backButton);
        
        statusLabel = new JLabel("准备开始跳跃游戏III动画演示");
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
        currentPosition = startIndex;
        visited = new HashSet<>();
        dfsStack = new Stack<>();
        searchPath = new ArrayList<>();
        foundTarget = false;
        algorithmComplete = false;
        
        dfsStack.push(startIndex);
        searchPath.add(startIndex);
        
        updateLog("初始化完成");
        updateLog("数组: [" + arrayToString() + "]");
        updateLog("起始位置: " + startIndex + " (值: " + arr[startIndex] + ")");
        updateLog("目标: 找到任一值为0的位置");
        updateLog("算法: 深度优先搜索(DFS)");
        updateLog("可跳跃方向: 前进 arr[i] 步或后退 arr[i] 步");
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制数组
        drawArray(g2d);
        
        // 绘制搜索路径
        drawSearchPath(g2d);
        
        // 绘制可跳跃位置
        drawPossibleJumps(g2d);
        
        // 绘制玩家
        drawPlayer(g2d);
        
        // 绘制DFS栈信息
        drawDFSStack(g2d);
        
        // 绘制算法信息
        drawAlgorithmInfo(g2d);
    }
    
    private void drawArray(Graphics2D g2d) {
        for (int i = 0; i < arr.length; i++) {
            int x = START_X + i * CELL_SIZE;
            int y = START_Y;
            
            // 背景颜色
            if (arr[i] == 0) {
                g2d.setColor(Color.YELLOW); // 目标位置
            } else if (i == currentPosition) {
                g2d.setColor(Color.LIGHT_GRAY); // 当前位置
            } else if (visited.contains(i)) {
                g2d.setColor(new Color(200, 200, 255)); // 已访问
            } else {
                g2d.setColor(Color.WHITE); // 未访问
            }
            
            g2d.fillRect(x, y, CELL_SIZE, CELL_SIZE);
            
            // 边框
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, CELL_SIZE, CELL_SIZE);
            
            // 数值
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
            String text = String.valueOf(arr[i]);
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (CELL_SIZE - fm.stringWidth(text)) / 2;
            int textY = y + CELL_SIZE / 2 + fm.getAscent() / 2;
            
            if (arr[i] == 0) {
                g2d.setColor(Color.RED); // 目标值用红色
            } else {
                g2d.setColor(Color.BLACK);
            }
            g2d.drawString(text, textX, textY);
            
            // 索引
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.setColor(Color.GRAY);
            g2d.drawString(String.valueOf(i), x + 5, y + 15);
        }
    }
    
    private void drawSearchPath(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(new Color(0, 150, 0, 150));
        
        for (int i = 0; i < searchPath.size() - 1; i++) {
            int fromX = START_X + searchPath.get(i) * CELL_SIZE + CELL_SIZE / 2;
            int fromY = START_Y + CELL_SIZE / 2;
            int toX = START_X + searchPath.get(i + 1) * CELL_SIZE + CELL_SIZE / 2;
            int toY = START_Y + CELL_SIZE / 2;
            
            // 绘制弧线
            int midY = fromY - 30;
            drawBezierCurve(g2d, fromX, fromY, (fromX + toX) / 2, midY, toX, toY);
            
            // 绘制箭头
            drawArrow(g2d, toX, toY, fromX < toX);
        }
    }
    
    private void drawPossibleJumps(Graphics2D g2d) {
        if (!isAnimating && !algorithmComplete && currentPosition >= 0 && currentPosition < arr.length) {
            g2d.setColor(new Color(0, 255, 0, 100));
            
            // 向前跳跃
            int forwardPos = currentPosition + arr[currentPosition];
            if (forwardPos >= 0 && forwardPos < arr.length && !visited.contains(forwardPos)) {
                int x = START_X + forwardPos * CELL_SIZE;
                int y = START_Y;
                g2d.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                
                // 绘制箭头指示
                g2d.setColor(Color.GREEN);
                g2d.setStroke(new BasicStroke(2));
                int fromX = START_X + currentPosition * CELL_SIZE + CELL_SIZE / 2;
                int toX = START_X + forwardPos * CELL_SIZE + CELL_SIZE / 2;
                int arrowY = START_Y - 10;
                g2d.drawLine(fromX, arrowY, toX, arrowY);
                drawArrow(g2d, toX, arrowY, true);
            }
            
            // 向后跳跃
            int backwardPos = currentPosition - arr[currentPosition];
            if (backwardPos >= 0 && backwardPos < arr.length && !visited.contains(backwardPos)) {
                int x = START_X + backwardPos * CELL_SIZE;
                int y = START_Y;
                g2d.setColor(new Color(0, 255, 0, 100));
                g2d.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                
                // 绘制箭头指示
                g2d.setColor(Color.GREEN);
                g2d.setStroke(new BasicStroke(2));
                int fromX = START_X + currentPosition * CELL_SIZE + CELL_SIZE / 2;
                int toX = START_X + backwardPos * CELL_SIZE + CELL_SIZE / 2;
                int arrowY = START_Y + CELL_SIZE + 10;
                g2d.drawLine(fromX, arrowY, toX, arrowY);
                drawArrow(g2d, toX, arrowY, false);
            }
        }
    }
    
    private void drawBezierCurve(Graphics2D g2d, int x1, int y1, int x2, int y2, int x3, int y3) {
        for (double t = 0; t <= 1; t += 0.02) {
            double x = (1-t)*(1-t)*x1 + 2*(1-t)*t*x2 + t*t*x3;
            double y = (1-t)*(1-t)*y1 + 2*(1-t)*t*y2 + t*t*y3;
            g2d.fillOval((int)x-2, (int)y-2, 4, 4);
        }
    }
    
    private void drawArrow(Graphics2D g2d, int x, int y, boolean pointingRight) {
        int[] arrowX, arrowY;
        if (pointingRight) {
            arrowX = new int[]{x, x-8, x-8};
            arrowY = new int[]{y, y-5, y+5};
        } else {
            arrowX = new int[]{x, x+8, x+8};
            arrowY = new int[]{y, y-5, y+5};
        }
        g2d.fillPolygon(arrowX, arrowY, 3);
    }
    
    private void drawPlayer(Graphics2D g2d) {
        double currentX, currentY;
        
        if (isAnimating && animationFromPos >= 0 && animationToPos >= 0) {
            double startPosX = START_X + animationFromPos * CELL_SIZE + CELL_SIZE / 2;
            double endPosX = START_X + animationToPos * CELL_SIZE + CELL_SIZE / 2;
            currentX = startPosX + (endPosX - startPosX) * animationProgress;
            
            double jumpHeight = Math.sin(animationProgress * Math.PI) * 40;
            currentY = START_Y + CELL_SIZE / 2 - jumpHeight;
        } else {
            currentX = START_X + currentPosition * CELL_SIZE + CELL_SIZE / 2;
            currentY = START_Y + CELL_SIZE / 2;
        }
        
        // 绘制玩家
        if (foundTarget) {
            g2d.setColor(Color.GREEN); // 找到目标时变绿
        } else {
            g2d.setColor(Color.RED);
        }
        g2d.fillOval((int)currentX - 15, (int)currentY - 30, 30, 30);
        
        // 绘制眼睛
        g2d.setColor(Color.WHITE);
        g2d.fillOval((int)currentX - 8, (int)currentY - 25, 6, 6);
        g2d.fillOval((int)currentX + 2, (int)currentY - 25, 6, 6);
        
        g2d.setColor(Color.BLACK);
        g2d.fillOval((int)currentX - 6, (int)currentY - 23, 2, 2);
        g2d.fillOval((int)currentX + 4, (int)currentY - 23, 2, 2);
    }
    
    private void drawDFSStack(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g2d.drawString("DFS栈:", 20, 480);
        
        g2d.setFont(new Font("Consolas", Font.PLAIN, 12));
        StringBuilder stackStr = new StringBuilder("[");
        Object[] stackArray = dfsStack.toArray();
        for (int i = 0; i < stackArray.length; i++) {
            stackStr.append(stackArray[i]);
            if (i < stackArray.length - 1) stackStr.append(", ");
        }
        stackStr.append("]");
        g2d.drawString(stackStr.toString(), 80, 480);
        
        g2d.drawString("已访问: " + visited.toString(), 20, 500);
    }
    
    private void drawAlgorithmInfo(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.drawString("跳跃游戏 III - 深度优先搜索", 20, 30);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.drawString("当前位置: " + currentPosition, 20, 60);
        g2d.drawString("当前值: " + (currentPosition >= 0 && currentPosition < arr.length ? arr[currentPosition] : "N/A"), 150, 60);
        g2d.drawString("已访问数量: " + visited.size(), 280, 60);
        g2d.drawString("栈大小: " + dfsStack.size(), 420, 60);
        
        if (foundTarget) {
            g2d.setColor(Color.GREEN);
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g2d.drawString("找到目标！位置 " + currentPosition + " 的值为 0", 20, 90);
        }
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawString("绿色区域: 可跳跃位置", 20, 520);
        g2d.drawString("蓝色区域: 已访问位置", 20, 540);
        g2d.drawString("黄色区域: 目标位置(值为0)", 20, 560);
    }
    
    private void startAnimation() {
        if (!animationTimer.isRunning()) {
            animationTimer.start();
        }
        
        autoTimer = new javax.swing.Timer(1500, new ActionListener() {
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

        if (dfsStack.isEmpty()) {
            algorithmComplete = true;
            updateLog("搜索完成！未找到值为0的位置");
            statusLabel.setText("搜索完成 - 未找到目标");
            if (autoTimer != null) autoTimer.stop();
            SwingUtilities.invokeLater(this::repaint);
            return;
        }

        int pos = dfsStack.peek();

        // 如果当前位置已经访问过，则跳过
        if (visited.contains(pos)) {
            dfsStack.pop();
            updateLog("位置 " + pos + " 已被访问过，从栈中移除并跳过。");
            statusLabel.setText("跳过已访问位置: " + pos);
            SwingUtilities.invokeLater(this::repaint);
            return; // 本次step只做“跳过”这一件事
        }

        // 处理新位置
        dfsStack.pop();
        visited.add(pos);

        // 触发移动动画
        if (pos != currentPosition) {
            animationFromPos = currentPosition;
            animationToPos = pos;
            animationProgress = 0;
            isAnimating = true;
            searchPath.add(pos);
        } else {
            // 如果DFS的下一个节点就是当前位置（例如，初始状态），也需要刷新状态
            currentPosition = pos;
        }

        updateLog("访问位置 " + pos + ", 值: " + arr[pos]);

        if (arr[pos] == 0) {
            foundTarget = true;
            algorithmComplete = true;
            updateLog("找到目标！位置 " + pos + " 的值为 0");
            statusLabel.setText("成功找到目标位置: " + pos);
            if (autoTimer != null) autoTimer.stop();
            SwingUtilities.invokeLater(this::repaint);
            return;
        }

        // 将邻居节点加入栈
        int forward = pos + arr[pos];
        if (forward >= 0 && forward < arr.length && !visited.contains(forward)) {
            dfsStack.push(forward);
            updateLog("添加前进位置到栈: " + forward);
        }

        int backward = pos - arr[pos];
        if (backward >= 0 && backward < arr.length && !visited.contains(backward)) {
            dfsStack.push(backward);
            updateLog("添加后退位置到栈: " + backward);
        }

        statusLabel.setText("当前位置: " + pos + ", 值: " + arr[pos] + ", 栈大小: " + dfsStack.size());
        SwingUtilities.invokeLater(this::repaint);
    }
    
    private void resetAnimation() {
        if (animationTimer.isRunning()) {
            animationTimer.stop();
        }
        if (autoTimer != null && autoTimer.isRunning()) {
            autoTimer.stop();
        }
        
        initializeAlgorithm();
        animationProgress = 0;
        isAnimating = false;
        animationFromPos = -1;
        animationToPos = -1;
        statusLabel.setText("已重置，准备开始新的演示");
        SwingUtilities.invokeLater(() -> repaint());
    }
    
    private void updateLog(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private String arrayToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(", ");
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO1306_N_JumpGameIII_Animation().setVisible(true);
        });
    }
}