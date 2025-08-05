package com.animation.hard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * LeetCode 403. 青蛙过河 (Frog Jump) 动画演示
 * 
 * 问题描述：
 * 一只青蛙想要过河。假定河流被等分为若干个单元格，并且在一些单元格内会有石头。
 * 青蛙可以跳上石头，但是不可以跳入水中。
 * 
 * 给你石头的位置列表 stones（用单元格序号 升序 表示），请判定青蛙能否成功过河（即能否在最后一步跳到最后一个石头上）。
 * 
 * 开始时，青蛙默认已站在第一个石头上，并可以假定它第一步只能跳跃 1 个单位（即只能从单元格 1 跳至单元格 2 ）。
 * 
 * 如果青蛙上一步跳跃了 k 个单位，那么它接下来的跳跃距离只能选择为 k - 1、k 或 k + 1 个单位。
 * 另外，青蛙只能向前方向跳跃。
 * 
 * 算法思路：
 * 使用动态规划 + 状态转移
 * 1. 用 Map<Integer, Set<Integer>> 记录每个石头位置可以到达的跳跃步数
 * 2. 从第一个石头开始，尝试所有可能的跳跃步数
 * 3. 对于每个可达的石头，更新其可能的跳跃步数集合
 * 4. 最终检查最后一个石头是否可达
 * 
 * 时间复杂度：O(n²)
 * 空间复杂度：O(n²)
 */
public class NO403_H_FrogJump_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final int STONE_SIZE = 30;
    private static final int FROG_SIZE = 25;
    
    // 算法相关变量
    private int[] stones = {0, 1, 2, 3, 4, 5, 8, 9, 11, 12, 15, 16, 18, 20, 21};
    private Map<Integer, Set<Integer>> dp;
    private Map<Integer, Integer> stoneIndexMap;
    
    // 动画相关变量
    private int currentStone = 0;
    private int currentStep = 0;
    private boolean animationRunning = false;
    private double distanceScaleFactor = 0.8; // 距离缩放因子
    private javax.swing.Timer animationTimer;
    private boolean isJumping = false;
    private double jumpProgress = 0;
    private int jumpStartX, jumpStartY, jumpEndX, jumpEndY;
    
    // UI组件
    private JPanel drawPanel;
    private JButton startButton;
    private JButton resetButton;
    private JButton stepButton;
    private JButton toggleInfoButton;
    private JTextArea logArea;
    private JLabel statusLabel;
    private JPanel infoPanel;
    
    // 多线程日志处理
    private BlockingQueue<String> logQueue;
    private ExecutorService logExecutor;
    private volatile boolean logThreadRunning = false;
    
    public NO403_H_FrogJump_Animation() {
        initializeUI();
        initializeLogSystem();
        initializeAlgorithm();
    }
    
    private void initializeLogSystem() {
        logQueue = new LinkedBlockingQueue<>();
        logExecutor = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r, "LogUpdateThread");
            t.setDaemon(true);
            return t;
        });
        logThreadRunning = true;
        
        // 启动日志处理线程
        logExecutor.submit(() -> {
            while (logThreadRunning) {
                try {
                    String message = logQueue.take();
                    SwingUtilities.invokeLater(() -> {
                        if (logArea != null) {
                            logArea.append(message + "\n");
                            logArea.setCaretPosition(logArea.getDocument().getLength());
                        }
                    });
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }
    
    private void initializeUI() {
        setTitle("LeetCode 403. 青蛙过河 (Frog Jump) - 动态规划动画演示");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 添加窗口关闭监听器，清理资源
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                cleanup();
            }
        });
        setLocationRelativeTo(null);
        
        // 设置Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // 创建主面板
        setLayout(new BorderLayout());
        
        // 创建绘图面板
        drawPanel = new JPanel() {
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
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        startButton = new JButton("开始动画");
        resetButton = new JButton("重置");
        stepButton = new JButton("单步执行");
        toggleInfoButton = new JButton("隐藏信息");
        
        startButton.addActionListener(e -> startAnimation());
        resetButton.addActionListener(e -> resetAnimation());
        stepButton.addActionListener(e -> stepAnimation());
        toggleInfoButton.addActionListener(e -> toggleInfoPanel());
        
        panel.add(startButton);
        panel.add(stepButton);
        panel.add(resetButton);
        panel.add(toggleInfoButton);
        
        statusLabel = new JLabel("准备开始青蛙过河动画演示");
        panel.add(statusLabel);
        
        return panel;
    }
    
    private JPanel createInfoPanel() {
        infoPanel = new JPanel(new BorderLayout());
        infoPanel.setPreferredSize(new Dimension(300, 0));
        
        JLabel titleLabel = new JLabel("算法信息", JLabel.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        
        infoPanel.add(titleLabel, BorderLayout.NORTH);
        infoPanel.add(scrollPane, BorderLayout.CENTER);
        
        return infoPanel;
    }
    
    private void initializeAlgorithm() {
        dp = new HashMap<>();
        stoneIndexMap = new HashMap<>();
        
        // 建立石头位置到索引的映射
        for (int i = 0; i < stones.length; i++) {
            stoneIndexMap.put(stones[i], i);
        }
        
        // 初始化DP状态
        for (int stone : stones) {
            dp.put(stone, new HashSet<>());
        }
        
        // 青蛙从第一个石头开始，第一步只能跳1个单位
        // 修复：应该添加步长1，而不是0，这样青蛙才能开始跳跃
        dp.get(stones[0]).add(1);
        
        updateLog("初始化完成");
        updateLog("石头位置: " + Arrays.toString(stones));
        updateLog("青蛙从位置 " + stones[0] + " 开始");
        updateLog("第一步只能跳跃1个单位");
        updateLog("DP初始状态: " + dp.toString());
        updateLog("石头索引映射: " + stoneIndexMap.toString());
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制河流背景
        g2d.setColor(new Color(173, 216, 230));
        g2d.fillRect(0, 200, getWidth(), 200);
        
        // 绘制石头
        int startX = 50;
        int stoneY = 250;
        int maxStonePos = stones[stones.length - 1];
        int panelWidth = getWidth();
        // 确保 panelWidth 有效，如果为 0 则使用默认值（与 stepAnimation 中的逻辑保持一致）
        if (panelWidth <= 0) {
            panelWidth = WINDOW_WIDTH;
        }
        int drawableWidth = (int) ((panelWidth - 2 * startX) * distanceScaleFactor);
        
        for (int i = 0; i < stones.length; i++) {
            int stoneX = startX + (int) ((double) stones[i] / maxStonePos * drawableWidth);
            
            // 石头颜色
            if (i == currentStone) {
                g2d.setColor(Color.GREEN); // 当前石头
            } else if (i < currentStone) {
                g2d.setColor(Color.LIGHT_GRAY); // 已访问的石头
            } else {
                g2d.setColor(Color.GRAY); // 未访问的石头
            }
            
            g2d.fillOval(stoneX - STONE_SIZE/2, stoneY - STONE_SIZE/2, STONE_SIZE, STONE_SIZE);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(stoneX - STONE_SIZE/2, stoneY - STONE_SIZE/2, STONE_SIZE, STONE_SIZE);
            
            // 绘制石头位置标签
            g2d.drawString(String.valueOf(stones[i]), stoneX - 5, stoneY + 35);
            
            // 绘制可能的跳跃步数
            if (dp.get(stones[i]) != null && !dp.get(stones[i]).isEmpty()) {
                g2d.setColor(Color.BLUE);
                g2d.drawString("步数: " + dp.get(stones[i]).toString(), stoneX - 20, stoneY - 30);
            }
        }
        
        // 绘制青蛙
        drawFrog(g2d, startX, stoneY, maxStonePos, drawableWidth);
        
        // 绘制算法说明
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g2d.drawString("青蛙过河 - 动态规划算法", 20, 30);
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        g2d.drawString("规则：青蛙上一步跳k个单位，下一步只能跳k-1、k或k+1个单位", 20, 50);
        g2d.drawString("目标：判断青蛙能否到达最后一个石头", 20, 70);
    }
    
    private void drawFrog(Graphics2D g2d, int startX, int stoneY, int maxStonePos, int drawableWidth) {
        int frogX, frogY;

        if (isJumping) {
            frogX = (int) (jumpStartX + (jumpEndX - jumpStartX) * jumpProgress);
            double jumpHeight = -100 * Math.sin(jumpProgress * Math.PI); // 抛物线轨迹
            frogY = (int) (jumpStartY + (jumpEndY - jumpStartY) * jumpProgress + jumpHeight);
            // 添加跳跃时的坐标调试日志
            if (jumpProgress * 100 % 20 < 5) {
                updateLog("绘制青蛙跳跃: frogX=" + frogX + ", frogY=" + frogY + ", progress=" + String.format("%.2f", jumpProgress));
            }
        } else {
            if (currentStone >= stones.length) return;
            frogX = startX + (int) ((double) stones[currentStone] / maxStonePos * drawableWidth);
            frogY = stoneY - FROG_SIZE / 2;
            // 添加静止时的坐标调试日志
            updateLog("绘制青蛙静止: frogX=" + frogX + ", frogY=" + frogY + ", currentStone=" + currentStone + ", stonePos=" + stones[currentStone]);
        }

        g2d.setColor(Color.ORANGE);
        g2d.fillOval(frogX - FROG_SIZE / 2, frogY - FROG_SIZE / 2, FROG_SIZE, FROG_SIZE);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(frogX - FROG_SIZE / 2, frogY - FROG_SIZE / 2, FROG_SIZE, FROG_SIZE);
    }

    private void startAnimation() {
        if (animationRunning) {
            stopAnimation();
            return;
        }

        animationRunning = true;
        startButton.setText("停止动画");
        updateLog("=== 开始动画 ===");
        updateLog("初始状态: currentStone=" + currentStone + ", isJumping=" + isJumping);

        // 修复：增加动画间隔，减少日志输出频率，避免阻塞EDT线程
        animationTimer = new javax.swing.Timer(100, e -> {
            if (isJumping) {
                jumpProgress += 0.05;
                // 添加跳跃进度调试日志（每10次输出一次，避免日志过多）
                if (jumpProgress * 100 % 20 < 5) {
                    updateLog("跳跃进度: " + String.format("%.2f", jumpProgress * 100) + "%");
                }
                if (jumpProgress >= 1.0) {
                    jumpProgress = 1.0;
                    isJumping = false;
                    // 使用多线程日志系统，可以安全地输出跳跃完成信息
                    updateLog("成功跳到 " + stones[currentStone] + ", isJumping=" + isJumping);
                    statusLabel.setText("青蛙跳到石头 " + stones[currentStone]);
                }
                drawPanel.repaint();
            } else {
                // 修复：无论stepAnimation()返回什么，都要调用repaint()保持视觉更新
                boolean canContinue = stepAnimation();
                drawPanel.repaint(); // 确保每次都重绘
                if (!canContinue) {
                    // 延迟停止动画，让用户能看到最终状态
                    javax.swing.Timer delayTimer = new javax.swing.Timer(1000, evt -> stopAnimation());
                    delayTimer.setRepeats(false);
                    delayTimer.start();
                    // 暂时不停止动画定时器，让它继续重绘
                }
            }
        });
        animationTimer.start();
    }
    
    private void stopAnimation() {
        animationRunning = false;
        startButton.setText("开始动画");
        if (animationTimer != null) {
            animationTimer.stop();
        }
    }
    
    private boolean stepAnimation() {
        updateLog("=== stepAnimation调用 ===");
        updateLog("当前状态: isJumping=" + isJumping + ", currentStone=" + currentStone);
        
        if (isJumping) {
            updateLog("青蛙正在跳跃中，返回true");
            return true;
        }

        // 检查是否已经到达最后一个石头
        if (currentStone >= stones.length - 1) {
            boolean canReach = !dp.get(stones[stones.length - 1]).isEmpty();
            updateLog("算法结束！");
            updateLog("结果：青蛙" + (canReach ? "能够" : "无法") + "到达最后一个石头");
            statusLabel.setText("算法结束 - " + (canReach ? "成功过河" : "无法过河"));
            return false;
        }

        int currentPos = stones[currentStone];
        Set<Integer> lastSteps = dp.get(currentPos);

        // 使用多线程日志系统，可以安全地在动画过程中输出日志
        updateLog("当前在石头位置: " + currentPos + " (索引: " + currentStone + ")");
        updateLog("可能的跳跃步数: " + lastSteps);

        // 寻找下一个要跳的石头 - 只计算从当前位置能直接到达的下一个石头
        int nextStoneIndex = -1;
        int actualJumpStep = 0;
        StringBuilder logBuffer = new StringBuilder();

        // 添加详细的调试信息
        updateLog("开始搜索下一个可跳的石头，当前石头索引: " + currentStone + ", 总石头数: " + stones.length);
        updateLog("当前位置可用的跳跃步数: " + lastSteps + ", 步数集合大小: " + lastSteps.size());
        
        // 只遍历从当前位置能直接跳到的石头
        for (int i = currentStone + 1; i < stones.length; i++) {
            int nextPos = stones[i];
            int dist = nextPos - currentPos;
            
            updateLog("检查石头 " + i + ": 位置 " + nextPos + ", 距离 " + dist);
            
            // 检查这个距离是否是当前位置允许的跳跃步数
            boolean canJumpToThisStone = false;
            for (int lastStep : lastSteps) {
                updateLog("  检查步长 " + lastStep + ": 距离 " + dist + " 是否在范围 [" + (lastStep-1) + ", " + (lastStep+1) + "] 内");
                if (dist >= lastStep - 1 && dist <= lastStep + 1) {
                    updateLog("  ✓ 可以跳跃！距离 " + dist + " 在步长 " + lastStep + " 的允许范围内");
                    canJumpToThisStone = true;
                    // 找到第一个可以跳到的石头
                    if (nextStoneIndex == -1) {
                        nextStoneIndex = i;
                        actualJumpStep = dist;
                        // 更新目标石头的DP状态
                        dp.get(nextPos).add(dist);
                        logBuffer.append("  执行跳跃: 从 ").append(currentPos).append(" 到 ").append(nextPos).append(" (步长 ").append(dist).append(")\n");
                        updateLog("找到可跳跃的石头: 索引 " + i + ", 位置 " + nextPos);
                        break;
                    }
                } else {
                    updateLog("  ✗ 不能跳跃，距离 " + dist + " 不在步长 " + lastStep + " 的允许范围内");
                }
            }
            if (!canJumpToThisStone) {
                updateLog("  石头 " + i + " (位置 " + nextPos + ") 无法到达");
            }
            if (nextStoneIndex != -1) break; // 找到第一个可跳的石头就停止
        }
        
        // 使用多线程日志系统输出计算日志
        if (logBuffer.length() > 0) {
            String finalLog = logBuffer.toString();
            updateLog(finalLog);
        }

        updateLog("搜索完成，nextStoneIndex = " + nextStoneIndex);
        
        if (nextStoneIndex != -1) {
            int fromStoneIndex = currentStone;
            currentStone = nextStoneIndex;

            int maxStonePos = stones[stones.length - 1];
            int panelWidth = getWidth();
            // 确保 panelWidth 有效，如果为 0 则使用默认值
            if (panelWidth <= 0) {
                panelWidth = WINDOW_WIDTH;
            }
            int drawableWidth = (int) ((panelWidth - 2 * 50) * distanceScaleFactor);

            jumpStartX = 50 + (int) ((double) stones[fromStoneIndex] / maxStonePos * drawableWidth);
            jumpStartY = 250 - FROG_SIZE / 2;
            jumpEndX = 50 + (int) ((double) stones[currentStone] / maxStonePos * drawableWidth);
            jumpEndY = 250 - FROG_SIZE / 2;

            // 添加坐标调试日志
            updateLog("跳跃坐标设置: panelWidth=" + panelWidth + ", drawableWidth=" + drawableWidth);
            updateLog("起始坐标: (" + jumpStartX + ", " + jumpStartY + "), 结束坐标: (" + jumpEndX + ", " + jumpEndY + ")");

            isJumping = true;
            jumpProgress = 0;

            // 使用多线程日志系统，可以安全地输出跳跃信息
            updateLog("从 " + stones[fromStoneIndex] + " 准备跳到 " + stones[currentStone] + ", isJumping=" + isJumping);
            statusLabel.setText("青蛙准备从 " + stones[fromStoneIndex] + " 跳到 " + stones[currentStone]);

        } else {
            updateLog("在石头 " + currentPos + " 找不到下一步可跳的石头。");
            statusLabel.setText("青蛙在石头 " + currentPos + " 被困住。");
            // 修复：不立即返回false，而是保持当前状态，让动画继续显示
            // 检查是否已经到达最后一个石头
            if (currentStone >= stones.length - 1) {
                boolean canReach = !dp.get(stones[stones.length - 1]).isEmpty();
                updateLog("算法结束！青蛙" + (canReach ? "成功" : "失败") + "过河");
                statusLabel.setText("算法结束 - " + (canReach ? "成功过河" : "无法过河"));
                return false; // 只有在真正结束时才返回false
            }
            // 如果不是最后一个石头，继续保持动画运行，显示被困状态
            return true;
        }

        drawPanel.repaint();
        return true;
    }
    
    private void resetAnimation() {
        stopAnimation();
        currentStone = 0;
        currentStep = 0;
        initializeAlgorithm();
        statusLabel.setText("已重置，准备开始新的演示");
        drawPanel.repaint();
    }
    
    private void updateLog(String message) {
        // 使用队列异步处理日志更新，避免阻塞动画线程
        if (logQueue != null && logThreadRunning) {
            try {
                logQueue.offer(message);
            } catch (Exception e) {
                // 如果队列出现问题，回退到直接更新
                SwingUtilities.invokeLater(() -> {
                    if (logArea != null) {
                        logArea.append(message + "\n");
                        logArea.setCaretPosition(logArea.getDocument().getLength());
                    }
                });
            }
        }
    }
    
    private void toggleInfoPanel() {
        if (infoPanel.isVisible()) {
            infoPanel.setVisible(false);
            toggleInfoButton.setText("显示信息");
        } else {
            infoPanel.setVisible(true);
            toggleInfoButton.setText("隐藏信息");
        }
        // 重新布局窗口
        this.revalidate();
        drawPanel.repaint();
    }
    
    private void cleanup() {
        // 停止动画
        if (animationTimer != null) {
            animationTimer.stop();
        }
        
        // 停止日志线程
        logThreadRunning = false;
        if (logExecutor != null) {
            logExecutor.shutdown();
            try {
                if (!logExecutor.awaitTermination(1, java.util.concurrent.TimeUnit.SECONDS)) {
                    logExecutor.shutdownNow();
                }
            } catch (InterruptedException e) {
                logExecutor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO403_H_FrogJump_Animation().setVisible(true);
        });
    }
}