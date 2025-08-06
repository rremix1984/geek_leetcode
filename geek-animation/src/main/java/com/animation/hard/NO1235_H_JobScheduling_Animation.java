package com.animation.hard;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;

/**
 * NO1235 规划兼职工作 - 动画演示
 * 
 * 动画要点：
 * 1. 工作时间轴可视化：显示每个工作的时间段和报酬
 * 2. 动态规划过程：展示dp数组的更新过程
 * 3. 二分查找过程：显示查找兼容工作的过程
 * 4. 最优解构建：高亮显示最终选择的工作组合
 * 5. 算法复杂度：时间O(nlogn)，空间O(n)
 */
public class NO1235_H_JobScheduling_Animation extends JFrame {
    
    // GUI组件
    private JPanel drawPanel;
    private JComboBox<String> testCaseCombo;
    private JButton startButton, pauseButton, resetButton, nextStepButton;
    private JLabel statusLabel, complexityLabel;
    private Timer animationTimer;
    
    // 动画状态
    private boolean isAnimating = false;
    private boolean isPaused = false;
    private int currentStep = 0;
    private int animationSpeed = 1500; // 毫秒
    
    // 算法数据
    private int[] startTime, endTime, profit;
    private int[][] jobs;
    private int[] dp;
    private int n;
    private List<AnimationStep> steps;
    
    // 可视化数据
    private int currentJobIndex = -1;
    private int currentK = -1;
    private boolean showBinarySearch = false;
    private int binaryLeft = -1, binaryRight = -1, binaryMid = -1;
    private Set<Integer> selectedJobs = new HashSet<>();
    private int maxProfit = 0;
    
    // 颜色定义
    private static final Color PRIMARY_COLOR = new Color(52, 152, 219);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color WARNING_COLOR = new Color(241, 196, 15);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color LIGHT_GRAY = new Color(236, 240, 241);
    
    // 测试用例
    private TestCase[] testCases = {
        new TestCase("示例1", 
            new int[]{1, 2, 3, 3}, 
            new int[]{3, 4, 5, 6}, 
            new int[]{50, 10, 40, 70}),
        new TestCase("示例2", 
            new int[]{1, 2, 3, 4, 6}, 
            new int[]{3, 5, 10, 6, 9}, 
            new int[]{20, 20, 100, 70, 60}),
        new TestCase("示例3", 
            new int[]{1, 1, 1}, 
            new int[]{2, 3, 4}, 
            new int[]{5, 6, 4}),
        new TestCase("复杂示例", 
            new int[]{1, 3, 6, 8, 10}, 
            new int[]{4, 7, 9, 11, 12}, 
            new int[]{30, 40, 50, 20, 60})
    };
    
    // 动画步骤类
    private static class AnimationStep {
        String type;
        String description;
        int jobIndex;
        int k;
        int dpValue;
        boolean showBinarySearch;
        int binaryLeft, binaryRight, binaryMid;
        
        AnimationStep(String type, String description, int jobIndex, int k, int dpValue) {
            this.type = type;
            this.description = description;
            this.jobIndex = jobIndex;
            this.k = k;
            this.dpValue = dpValue;
            this.showBinarySearch = false;
        }
        
        AnimationStep(String type, String description, int jobIndex, int k, int dpValue,
                     int binaryLeft, int binaryRight, int binaryMid) {
            this.type = type;
            this.description = description;
            this.jobIndex = jobIndex;
            this.k = k;
            this.dpValue = dpValue;
            this.showBinarySearch = true;
            this.binaryLeft = binaryLeft;
            this.binaryRight = binaryRight;
            this.binaryMid = binaryMid;
        }
    }
    
    // 测试用例类
    private static class TestCase {
        String name;
        int[] startTime, endTime, profit;
        
        TestCase(String name, int[] startTime, int[] endTime, int[] profit) {
            this.name = name;
            this.startTime = startTime;
            this.endTime = endTime;
            this.profit = profit;
        }
        
        @Override
        public String toString() {
            return name;
        }
    }
    
    public NO1235_H_JobScheduling_Animation() {
        initializeGUI();
        loadTestCase(0);
    }
    
    private void initializeGUI() {
        setTitle("NO1235 规划兼职工作 - 动画演示");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // 创建绘图面板
        drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawAnimation(g);
            }
        };
        drawPanel.setPreferredSize(new Dimension(1200, 700));
        drawPanel.setBackground(Color.WHITE);
        
        // 创建控制面板
        JPanel controlPanel = createControlPanel();
        
        // 创建信息面板
        JPanel infoPanel = createInfoPanel();
        
        add(drawPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        // 测试用例选择
        panel.add(new JLabel("测试用例:"));
        testCaseCombo = new JComboBox<>();
        for (TestCase testCase : testCases) {
            testCaseCombo.addItem(testCase.toString());
        }
        testCaseCombo.addActionListener(e -> {
            if (!isAnimating) {
                loadTestCase(testCaseCombo.getSelectedIndex());
                SwingUtilities.invokeLater(() -> drawPanel.repaint());
            }
        });
        panel.add(testCaseCombo);
        
        // 控制按钮
        startButton = new JButton("开始动画");
        startButton.addActionListener(e -> startAnimation());
        panel.add(startButton);
        
        pauseButton = new JButton("暂停");
        pauseButton.addActionListener(e -> pauseAnimation());
        pauseButton.setEnabled(false);
        panel.add(pauseButton);
        
        resetButton = new JButton("重置");
        resetButton.addActionListener(e -> resetAnimation());
        panel.add(resetButton);
        
        nextStepButton = new JButton("下一步");
        nextStepButton.addActionListener(e -> nextStep());
        panel.add(nextStepButton);
        
        // 速度控制
        panel.add(new JLabel("速度:"));
        JSlider speedSlider = new JSlider(500, 3000, animationSpeed);
        speedSlider.addChangeListener(e -> animationSpeed = speedSlider.getValue());
        panel.add(speedSlider);
        
        return panel;
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        
        statusLabel = new JLabel("准备开始动画演示");
        statusLabel.setBorder(BorderFactory.createTitledBorder("算法状态"));
        panel.add(statusLabel);
        
        complexityLabel = new JLabel("时间复杂度: O(nlogn), 空间复杂度: O(n)");
        complexityLabel.setBorder(BorderFactory.createTitledBorder("复杂度分析"));
        panel.add(complexityLabel);
        
        return panel;
    }
    
    private void loadTestCase(int index) {
        TestCase testCase = testCases[index];
        this.startTime = testCase.startTime.clone();
        this.endTime = testCase.endTime.clone();
        this.profit = testCase.profit.clone();
        this.n = startTime.length;
        
        resetAnimation();
    }
    
    private void startAnimation() {
        if (isPaused) {
            isPaused = false;
            animationTimer.start();
        } else {
            setupAnimation();
            isAnimating = true;
            currentStep = 0;
            
            animationTimer = new Timer(animationSpeed, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentStep < steps.size()) {
                        performStep(steps.get(currentStep));
                        currentStep++;
                        SwingUtilities.invokeLater(() -> drawPanel.repaint());
                    } else {
                        stopAnimation();
                    }
                }
            });
            animationTimer.start();
        }
        
        startButton.setEnabled(false);
        pauseButton.setEnabled(true);
        testCaseCombo.setEnabled(false);
    }
    
    private void pauseAnimation() {
        isPaused = true;
        animationTimer.stop();
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
    }
    
    private void stopAnimation() {
        isAnimating = false;
        isPaused = false;
        if (animationTimer != null) {
            animationTimer.stop();
        }
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        testCaseCombo.setEnabled(true);
    }
    
    private void resetAnimation() {
        stopAnimation();
        currentStep = 0;
        currentJobIndex = -1;
        currentK = -1;
        showBinarySearch = false;
        binaryLeft = binaryRight = binaryMid = -1;
        selectedJobs.clear();
        maxProfit = 0;
        
        if (steps != null) {
            steps.clear();
        }
        
        statusLabel.setText("准备开始动画演示");
        SwingUtilities.invokeLater(() -> drawPanel.repaint());
    }
    
    private void nextStep() {
        if (!isAnimating && currentStep < steps.size()) {
            performStep(steps.get(currentStep));
            currentStep++;
            SwingUtilities.invokeLater(() -> drawPanel.repaint());
        } else if (steps == null || steps.isEmpty()) {
            setupAnimation();
            if (!steps.isEmpty()) {
                performStep(steps.get(currentStep));
                currentStep++;
                SwingUtilities.invokeLater(() -> drawPanel.repaint());
            }
        }
    }
    
    private void setupAnimation() {
        steps = new ArrayList<>();
        
        // 创建工作数组并排序
        jobs = new int[n][];
        for (int i = 0; i < n; i++) {
            jobs[i] = new int[]{startTime[i], endTime[i], profit[i]};
        }
        
        steps.add(new AnimationStep("init", "初始化：创建工作数组", -1, -1, 0));
        
        Arrays.sort(jobs, Comparator.comparingInt(a -> a[1]));
        steps.add(new AnimationStep("sort", "按结束时间排序工作", -1, -1, 0));
        
        // 初始化dp数组
        dp = new int[n + 1];
        steps.add(new AnimationStep("dp_init", "初始化DP数组，dp[0] = 0", -1, -1, 0));
        
        // 动态规划过程
        for (int i = 1; i <= n; i++) {
            int k = binarySearch(jobs, i - 1, jobs[i - 1][0], steps, i);
            
            int option1 = dp[i - 1];
            int option2 = dp[k] + jobs[i - 1][2];
            dp[i] = Math.max(option1, option2);
            
            steps.add(new AnimationStep("dp_update", 
                String.format("dp[%d] = max(%d, %d + %d) = %d", 
                    i, option1, dp[k], jobs[i - 1][2], dp[i]), 
                i - 1, k, dp[i]));
        }
        
        maxProfit = dp[n];
        steps.add(new AnimationStep("result", "算法完成，最大报酬: " + maxProfit, -1, -1, maxProfit));
        
        // 回溯找到最优解
        findOptimalSolution();
    }
    
    private int binarySearch(int[][] jobs, int right, int target, List<AnimationStep> steps, int currentI) {
        int left = 0;
        steps.add(new AnimationStep("binary_start", 
            String.format("二分查找：寻找结束时间 <= %d 的工作", target), 
            currentI - 1, -1, 0, left, right, -1));
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            steps.add(new AnimationStep("binary_mid", 
                String.format("二分查找：left=%d, right=%d, mid=%d", left, right, mid), 
                currentI - 1, -1, 0, left, right, mid));
            
            if (jobs[mid][1] > target) {
                right = mid;
                steps.add(new AnimationStep("binary_right", 
                    String.format("jobs[%d].endTime=%d > %d，right = %d", mid, jobs[mid][1], target, mid), 
                    currentI - 1, -1, 0, left, right, mid));
            } else {
                left = mid + 1;
                steps.add(new AnimationStep("binary_left", 
                    String.format("jobs[%d].endTime=%d <= %d，left = %d", mid, jobs[mid][1], target, mid + 1), 
                    currentI - 1, -1, 0, left, right, mid));
            }
        }
        
        steps.add(new AnimationStep("binary_end", 
            String.format("二分查找结束，k = %d", left), 
            currentI - 1, left, 0));
        
        return left;
    }
    
    private void findOptimalSolution() {
        selectedJobs.clear();
        int i = n;
        while (i > 0) {
            int k = binarySearchSimple(jobs, i - 1, jobs[i - 1][0]);
            if (dp[k] + jobs[i - 1][2] > dp[i - 1]) {
                selectedJobs.add(i - 1);
                i = k;
            } else {
                i--;
            }
        }
    }
    
    private int binarySearchSimple(int[][] jobs, int right, int target) {
        int left = 0;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (jobs[mid][1] > target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
    
    private void performStep(AnimationStep step) {
        currentJobIndex = step.jobIndex;
        currentK = step.k;
        showBinarySearch = step.showBinarySearch;
        binaryLeft = step.binaryLeft;
        binaryRight = step.binaryRight;
        binaryMid = step.binaryMid;
        
        statusLabel.setText(step.description);
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制标题
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 20));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("NO1235 规划兼职工作", 20, 30);
        
        if (jobs != null) {
            // 绘制时间轴和工作
            drawTimelineAndJobs(g2d);
            
            // 绘制DP数组
            drawDPArray(g2d);
            
            // 绘制二分查找过程
            if (showBinarySearch) {
                drawBinarySearch(g2d);
            }
            
            // 绘制算法信息
            drawAlgorithmInfo(g2d);
        }
        
        g2d.dispose();
    }
    
    private void drawTimelineAndJobs(Graphics2D g2d) {
        int startX = 50;
        int startY = 80;
        int timelineWidth = 800;
        int jobHeight = 30;
        int jobSpacing = 35;
        
        // 计算时间范围
        int minTime = Arrays.stream(jobs).mapToInt(job -> job[0]).min().orElse(0);
        int maxTime = Arrays.stream(jobs).mapToInt(job -> job[1]).max().orElse(10);
        
        // 绘制时间轴
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(startX, startY + n * jobSpacing + 20, 
                    startX + timelineWidth, startY + n * jobSpacing + 20);
        
        // 绘制时间刻度
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        for (int t = minTime; t <= maxTime; t++) {
            int x = startX + (t - minTime) * timelineWidth / (maxTime - minTime);
            g2d.drawLine(x, startY + n * jobSpacing + 15, x, startY + n * jobSpacing + 25);
            g2d.drawString(String.valueOf(t), x - 5, startY + n * jobSpacing + 40);
        }
        
        // 绘制工作
        for (int i = 0; i < n; i++) {
            int[] job = jobs[i];
            int jobStartX = startX + (job[0] - minTime) * timelineWidth / (maxTime - minTime);
            int jobEndX = startX + (job[1] - minTime) * timelineWidth / (maxTime - minTime);
            int jobY = startY + i * jobSpacing;
            
            // 设置工作颜色
            Color jobColor;
            if (selectedJobs.contains(i)) {
                jobColor = SUCCESS_COLOR;
            } else if (i == currentJobIndex) {
                jobColor = WARNING_COLOR;
            } else {
                jobColor = LIGHT_GRAY;
            }
            
            // 绘制工作矩形
            g2d.setColor(jobColor);
            g2d.fill(new Rectangle2D.Double(jobStartX, jobY, jobEndX - jobStartX, jobHeight));
            
            // 绘制工作边框
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(1));
            g2d.draw(new Rectangle2D.Double(jobStartX, jobY, jobEndX - jobStartX, jobHeight));
            
            // 绘制工作信息
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 12));
            String jobInfo = String.format("Job%d: [%d,%d] $%d", i, job[0], job[1], job[2]);
            g2d.drawString(jobInfo, jobStartX + 5, jobY + 20);
        }
    }
    
    private void drawDPArray(Graphics2D g2d) {
        if (dp == null) return;
        
        int startX = 900;
        int startY = 80;
        int cellWidth = 50;
        int cellHeight = 30;
        
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("DP数组:", startX, startY - 10);
        
        for (int i = 0; i <= n; i++) {
            int x = startX;
            int y = startY + i * (cellHeight + 5);
            
            // 设置颜色
            if (i == currentJobIndex + 1) {
                g2d.setColor(WARNING_COLOR);
            } else {
                g2d.setColor(LIGHT_GRAY);
            }
            
            // 绘制单元格
            g2d.fillRect(x, y, cellWidth, cellHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, cellWidth, cellHeight);
            
            // 绘制值
            String value = String.valueOf(dp[i]);
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (cellWidth - fm.stringWidth(value)) / 2;
            int textY = y + (cellHeight + fm.getAscent()) / 2;
            g2d.drawString(value, textX, textY);
            
            // 绘制索引
            g2d.drawString("dp[" + i + "]", x + cellWidth + 10, y + 20);
        }
    }
    
    private void drawBinarySearch(Graphics2D g2d) {
        int startX = 50;
        int startY = 400;
        int cellWidth = 60;
        int cellHeight = 30;
        
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("二分查找过程:", startX, startY - 10);
        
        for (int i = 0; i < currentJobIndex; i++) {
            int x = startX + i * (cellWidth + 5);
            int y = startY;
            
            // 设置颜色
            Color cellColor = LIGHT_GRAY;
            if (i >= binaryLeft && i < binaryRight) {
                cellColor = new Color(173, 216, 230); // 浅蓝色
            }
            if (i == binaryMid) {
                cellColor = WARNING_COLOR;
            }
            
            // 绘制单元格
            g2d.setColor(cellColor);
            g2d.fillRect(x, y, cellWidth, cellHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, cellWidth, cellHeight);
            
            // 绘制工作结束时间
            String endTime = String.valueOf(jobs[i][1]);
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (cellWidth - fm.stringWidth(endTime)) / 2;
            int textY = y + (cellHeight + fm.getAscent()) / 2;
            g2d.drawString(endTime, textX, textY);
        }
        
        // 绘制指针
        if (binaryLeft >= 0) {
            int leftX = startX + binaryLeft * (cellWidth + 5) + cellWidth / 2;
            g2d.setColor(Color.BLUE);
            g2d.drawString("L", leftX - 5, startY + cellHeight + 15);
        }
        
        if (binaryRight >= 0 && binaryRight < currentJobIndex) {
            int rightX = startX + binaryRight * (cellWidth + 5) + cellWidth / 2;
            g2d.setColor(Color.RED);
            g2d.drawString("R", rightX - 5, startY + cellHeight + 15);
        }
        
        if (binaryMid >= 0) {
            int midX = startX + binaryMid * (cellWidth + 5) + cellWidth / 2;
            g2d.setColor(WARNING_COLOR);
            g2d.drawString("M", midX - 5, startY + cellHeight + 30);
        }
    }
    
    private void drawAlgorithmInfo(Graphics2D g2d) {
        int startX = 50;
        int startY = 500;
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.BLACK);
        
        g2d.drawString("算法信息:", startX, startY);
        g2d.drawString("• 当前处理工作: " + (currentJobIndex >= 0 ? "Job" + currentJobIndex : "无"), 
                      startX, startY + 20);
        g2d.drawString("• 兼容工作数量: " + (currentK >= 0 ? currentK : "无"), 
                      startX, startY + 40);
        g2d.drawString("• 当前最大报酬: " + maxProfit, startX, startY + 60);
        g2d.drawString("• 选中工作数量: " + selectedJobs.size(), startX, startY + 80);
        
        // 图例
        startX = 400;
        g2d.drawString("图例:", startX, startY);
        
        // 普通工作
        g2d.setColor(LIGHT_GRAY);
        g2d.fillRect(startX, startY + 15, 20, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(startX, startY + 15, 20, 15);
        g2d.drawString("普通工作", startX + 25, startY + 27);
        
        // 当前工作
        g2d.setColor(WARNING_COLOR);
        g2d.fillRect(startX, startY + 35, 20, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(startX, startY + 35, 20, 15);
        g2d.drawString("当前工作", startX + 25, startY + 47);
        
        // 选中工作
        g2d.setColor(SUCCESS_COLOR);
        g2d.fillRect(startX, startY + 55, 20, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(startX, startY + 55, 20, 15);
        g2d.drawString("最优解工作", startX + 25, startY + 67);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO1235_H_JobScheduling_Animation().setVisible(true);
        });
    }
}