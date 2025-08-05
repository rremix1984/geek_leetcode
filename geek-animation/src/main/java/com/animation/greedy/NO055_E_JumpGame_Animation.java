package com.animation.greedy;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * NO.055 跳跃游戏 - 动画演示
 * 
 * 设计文档：
 * 1. 功能需求：
 *    - 可视化展示跳跃游戏的贪心算法解决过程
 *    - 实时显示当前位置、最远可达位置和跳跃范围
 *    - 支持自定义数组输入和动画控制
 * 
 * 2. 技术实现：
 *    - 使用Swing绘制数组可视化界面
 *    - 采用贪心算法：维护最远可达位置
 *    - 动画展示每一步的跳跃过程和决策逻辑
 * 
 * 3. 界面设计：
 *    - 顶部：算法描述和输入控制
 *    - 中部：数组可视化区域，显示跳跃过程
 *    - 底部：控制按钮和状态信息
 * 
 * 4. 算法复杂度：
 *    - 时间复杂度：O(n)
 *    - 空间复杂度：O(1)
 * 
 * @author 开发工程师
 * @version 1.0
 */
public class NO055_E_JumpGame_Animation extends JFrame {
    
    // 窗口尺寸常量
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 700;
    
    // UI组件
    private JPanel visualPanel;
    private JTextArea algorithmStepsArea;
    private JTextField inputField;
    private JButton startButton;
    private JButton pauseButton;
    private JButton resetButton;
    private JButton homeButton;
    private JLabel statusLabel;
    
    // 动画相关变量
    private Timer animationTimer;
    private boolean isAnimating = false;
    private int currentStep = 0;
    
    // 算法相关变量
    private int[] nums;
    private int currentPosition = 0;
    private int maxReach = 0;
    private boolean canReachEnd = false;
    private List<String> animationSteps;
    private List<Integer> visitedPositions;
    
    
    
    public NO055_E_JumpGame_Animation() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        initializeDefaultData();
        resetAnimation();
    }
    
    private void initializeComponents() {
        setTitle("NO.055 跳跃游戏 - 动画演示");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // 创建可视化面板
        visualPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawAnimation(g);
            }
        };
        visualPanel.setBackground(Color.WHITE);
        visualPanel.setPreferredSize(new Dimension(WINDOW_WIDTH - 50, 300));
        
        // 创建算法步骤显示区域
        algorithmStepsArea = new JTextArea();
        algorithmStepsArea.setEditable(false);
        algorithmStepsArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        algorithmStepsArea.setBackground(new Color(248, 249, 250));
        
        // 创建输入字段
        inputField = new JTextField("2,3,1,1,4");
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputField.setPreferredSize(new Dimension(200, 30));
        
        // 创建控制按钮
        startButton = new JButton("开始动画");
        pauseButton = new JButton("暂停");
        resetButton = new JButton("重置");
        homeButton = new JButton("返回主页");
        
        // 设置按钮样式
        Font buttonFont = new Font("微软雅黑", Font.BOLD, 12);
        startButton.setFont(buttonFont);
        pauseButton.setFont(buttonFont);
        resetButton.setFont(buttonFont);
        homeButton.setFont(buttonFont);
        
        pauseButton.setEnabled(false);
        
        // 创建状态标签
        statusLabel = new JLabel("准备开始动画演示");
        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        
        // 创建动画定时器
        animationTimer = new Timer(1500, e -> {
            if (currentStep < animationSteps.size()) {
                performAnimationStep();
                currentStep++;
                visualPanel.repaint();
            } else {
                stopAnimation();
            }
        });
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 顶部面板：标题和输入
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("NO.055 跳跃游戏 - 贪心算法动画演示");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("输入数组 (逗号分隔):"));
        inputPanel.add(inputField);
        
        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(inputPanel, BorderLayout.SOUTH);
        
        // 中部面板：可视化区域
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("算法可视化"));
        centerPanel.add(visualPanel, BorderLayout.CENTER);
        
        // 右侧面板：算法步骤
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("算法步骤"));
        rightPanel.setPreferredSize(new Dimension(300, 0));
        
        JScrollPane stepsScrollPane = new JScrollPane(algorithmStepsArea);
        stepsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        rightPanel.add(stepsScrollPane, BorderLayout.CENTER);
        
        // 底部面板：控制按钮
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(startButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(homeButton);
        
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);
        
        // 主分割面板
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainSplitPane.setLeftComponent(centerPanel);
        mainSplitPane.setRightComponent(rightPanel);
        mainSplitPane.setDividerLocation(650);
        mainSplitPane.setResizeWeight(0.7);
        
        add(topPanel, BorderLayout.NORTH);
        add(mainSplitPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        startButton.addActionListener(e -> startAnimation());
        pauseButton.addActionListener(e -> pauseAnimation());
        resetButton.addActionListener(e -> resetAnimation());
        
        homeButton.addActionListener(e -> {
            // 停止动画并关闭窗口
            if (animationTimer.isRunning()) {
                animationTimer.stop();
            }
            dispose();
            
            // 返回主页
            SwingUtilities.invokeLater(() -> {
                try {
                    com.animation.launcher.AlgorithmTreeLauncher.showMainWindow();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });
    }
    
    private void initializeDefaultData() {
        nums = new int[]{2, 3, 1, 1, 4};
        animationSteps = new ArrayList<>();
        visitedPositions = new ArrayList<>();
    }
    
    private void startAnimation() {
        if (!isAnimating) {
            parseInput();
            initializeAlgorithm();
            isAnimating = true;
            startButton.setEnabled(false);
            pauseButton.setEnabled(true);
            statusLabel.setText("动画进行中...");
            animationTimer.start();
        }
    }
    
    private void pauseAnimation() {
        if (isAnimating) {
            animationTimer.stop();
            isAnimating = false;
            startButton.setEnabled(true);
            pauseButton.setEnabled(false);
            statusLabel.setText("动画已暂停");
        }
    }
    
    private void resetAnimation() {
        animationTimer.stop();
        
        isAnimating = false;
        currentStep = 0;
        currentPosition = 0;
        maxReach = 0;
        canReachEnd = false;
        
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        statusLabel.setText("准备开始动画演示");
        
        parseInput();
        initializeAlgorithm();
        updateStepsDisplay();
        visualPanel.repaint();
    }
    
    private void parseInput() {
        try {
            String input = inputField.getText().trim();
            String[] parts = input.split(",");
            nums = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                nums[i] = Integer.parseInt(parts[i].trim());
            }
        } catch (Exception e) {
            nums = new int[]{2, 3, 1, 1, 4}; // 默认值
            inputField.setText("2,3,1,1,4");
            JOptionPane.showMessageDialog(this, "输入格式错误，使用默认数组", "输入错误", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void initializeAlgorithm() {
        animationSteps.clear();
        visitedPositions.clear();
        currentPosition = 0;
        maxReach = 0;
        canReachEnd = false;
        
        animationSteps.add("=== 跳跃游戏算法开始 ===");
        animationSteps.add("数组: " + Arrays.toString(nums));
        animationSteps.add("目标: 判断能否从第一个位置跳到最后一个位置");
        animationSteps.add("算法: 贪心算法 - 维护最远可达位置");
        animationSteps.add("");
        
        // 执行算法并记录步骤
        canJumpWithSteps();
    }
    
    private void canJumpWithSteps() {
        maxReach = 0;
        
        for (int i = 0; i < nums.length; i++) {
            animationSteps.add("步骤 " + (i + 1) + ": 检查位置 " + i);
            
            if (i > maxReach) {
                animationSteps.add("  位置 " + i + " > 最远可达位置 " + maxReach);
                animationSteps.add("  无法到达位置 " + i + "，返回 false");
                canReachEnd = false;
                return;
            }
            
            int newReach = i + nums[i];
            if (newReach > maxReach) {
                animationSteps.add("  从位置 " + i + " 可跳跃 " + nums[i] + " 步");
                animationSteps.add("  更新最远可达位置: " + maxReach + " -> " + newReach);
                maxReach = newReach;
            } else {
                animationSteps.add("  从位置 " + i + " 可跳跃 " + nums[i] + " 步，但不能扩展最远可达位置");
            }
            
            if (maxReach >= nums.length - 1) {
                animationSteps.add("  ★ 最远可达位置 " + maxReach + " >= 最后位置 " + (nums.length - 1));
                animationSteps.add("  可以到达最后位置，返回 true");
                canReachEnd = true;
                return;
            }
            
            animationSteps.add("  当前最远可达位置: " + maxReach);
            animationSteps.add("");
        }
        
        canReachEnd = maxReach >= nums.length - 1;
    }
    
    private void performAnimationStep() {
        String step = animationSteps.get(currentStep);
        
        // 解析步骤，更新当前位置
        if (step.contains("检查位置")) {
            try {
                // 从"检查位置 X"中提取位置数字
                String[] parts = step.split("检查位置");
                if (parts.length > 1) {
                    String positionStr = parts[1].trim();
                    // 提取数字部分
                    String[] numParts = positionStr.split(" ");
                    if (numParts.length > 0) {
                        currentPosition = Integer.parseInt(numParts[0]);
                        visitedPositions.add(currentPosition);
                    }
                }
            } catch (Exception e) {
                // 忽略解析错误
            }
        }
        
        // 解析maxReach更新信息
        if (step.contains("更新最远可达位置:")) {
            try {
                String[] parts = step.split("更新最远可达位置:")[1].trim().split(" -> ");
                if (parts.length == 2) {
                    maxReach = Integer.parseInt(parts[1]);
                }
            } catch (Exception e) {
                // 忽略解析错误
            }
        }
        
        // 解析当前最远可达位置信息
        if (step.contains("当前最远可达位置:")) {
            try {
                String[] parts = step.split("当前最远可达位置:");
                if (parts.length == 2) {
                    maxReach = Integer.parseInt(parts[1].trim());
                }
            } catch (Exception e) {
                // 忽略解析错误
            }
        }
        
        updateStepsDisplay();
    }
    
    private void updateStepsDisplay() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= Math.min(currentStep, animationSteps.size() - 1); i++) {
            if (i == currentStep) {
                sb.append(">>> ").append(animationSteps.get(i)).append("\n");
            } else {
                sb.append(animationSteps.get(i)).append("\n");
            }
        }
        algorithmStepsArea.setText(sb.toString());
        
        // 自动滚动到底部
        algorithmStepsArea.setCaretPosition(algorithmStepsArea.getDocument().getLength());
    }
    

    
    private void stopAnimation() {
        animationTimer.stop();
        
        isAnimating = false;
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        
        String result = canReachEnd ? "可以到达最后位置" : "无法到达最后位置";
        statusLabel.setText("动画完成 - " + result);
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (nums == null || nums.length == 0) return;
        
        int panelWidth = visualPanel.getWidth();
        int panelHeight = visualPanel.getHeight();
        
        // 计算绘制参数
        int arrayStartX = 50;
        int arrayY = panelHeight / 2;
        int cellWidth = Math.min(60, (panelWidth - 100) / nums.length);
        int cellHeight = 40;
        
        // 绘制数组
        for (int i = 0; i < nums.length; i++) {
            int x = arrayStartX + i * (cellWidth + 10);
            
            // 确定颜色
            Color cellColor;
            if (i == currentPosition && currentPosition >= 0) {
                cellColor = new Color(255, 193, 7); // 当前位置 - 黄色
            } else if (visitedPositions.contains(i)) {
                cellColor = new Color(40, 167, 69); // 已访问 - 绿色
            } else if (i <= maxReach) {
                cellColor = new Color(108, 117, 125); // 可达范围 - 灰色
            } else {
                cellColor = new Color(220, 53, 69); // 不可达 - 红色
            }
            
            // 绘制单元格
            g2d.setColor(cellColor);
            g2d.fillRect(x, arrayY - cellHeight/2, cellWidth, cellHeight);
            
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, arrayY - cellHeight/2, cellWidth, cellHeight);
            
            // 绘制数值
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            FontMetrics fm = g2d.getFontMetrics();
            String value = String.valueOf(nums[i]);
            int textX = x + (cellWidth - fm.stringWidth(value)) / 2;
            int textY = arrayY + fm.getAscent() / 2;
            
            g2d.setColor(Color.WHITE);
            g2d.drawString(value, textX, textY);
            
            // 绘制索引
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            String index = String.valueOf(i);
            int indexX = x + (cellWidth - fm.stringWidth(index)) / 2;
            g2d.drawString(index, indexX, arrayY + cellHeight/2 + 20);
        }
        
        // 绘制跳跃范围指示（始终显示当前位置的所有可能跳跃）
        if (currentPosition < nums.length && currentPosition >= 0) {
            int currentX = arrayStartX + currentPosition * (cellWidth + 10);
            int jumpRange = nums[currentPosition];
            
            // 绘制跳跃弧线
            g2d.setColor(new Color(255, 193, 7, 120));
            g2d.setStroke(new BasicStroke(3));
            
            for (int j = 1; j <= jumpRange && currentPosition + j < nums.length; j++) {
                int targetX = arrayStartX + (currentPosition + j) * (cellWidth + 10);
                int arcHeight = 30 + j * 5;
                
                // 绘制弧线
                g2d.drawArc(currentX + cellWidth/2, arrayY - arcHeight, 
                           targetX - currentX, arcHeight * 2, 0, 180);
            }
        }
        
        // 绘制最远可达位置指示线
        if (maxReach < nums.length) {
            int reachX = arrayStartX + maxReach * (cellWidth + 10) + cellWidth;
            g2d.setColor(new Color(40, 167, 69));
            g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{5}, 0));
            g2d.drawLine(reachX, arrayY - 60, reachX, arrayY + 60);
            
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            g2d.drawString("最远可达: " + maxReach, reachX + 5, arrayY - 65);
        }
        
        // 绘制图例
        drawLegend(g2d, panelWidth, panelHeight);
    }
    
    private void drawLegend(Graphics2D g2d, int panelWidth, int panelHeight) {
        int legendX = panelWidth - 200;
        int legendY = 30;
        int legendItemHeight = 25;
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        
        // 图例标题
        g2d.setColor(Color.BLACK);
        g2d.drawString("图例:", legendX, legendY);
        
        // 当前位置
        g2d.setColor(new Color(255, 193, 7));
        g2d.fillRect(legendX, legendY + 10, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(legendX, legendY + 10, 15, 15);
        g2d.drawString("当前位置", legendX + 20, legendY + 22);
        
        // 已访问
        g2d.setColor(new Color(40, 167, 69));
        g2d.fillRect(legendX, legendY + 10 + legendItemHeight, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(legendX, legendY + 10 + legendItemHeight, 15, 15);
        g2d.drawString("已访问", legendX + 20, legendY + 22 + legendItemHeight);
        
        // 可达范围
        g2d.setColor(new Color(108, 117, 125));
        g2d.fillRect(legendX, legendY + 10 + legendItemHeight * 2, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(legendX, legendY + 10 + legendItemHeight * 2, 15, 15);
        g2d.drawString("可达范围", legendX + 20, legendY + 22 + legendItemHeight * 2);
        
        // 不可达
        g2d.setColor(new Color(220, 53, 69));
        g2d.fillRect(legendX, legendY + 10 + legendItemHeight * 3, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(legendX, legendY + 10 + legendItemHeight * 3, 15, 15);
        g2d.drawString("不可达", legendX + 20, legendY + 22 + legendItemHeight * 3);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO055_E_JumpGame_Animation().setVisible(true);
        });
    }
}