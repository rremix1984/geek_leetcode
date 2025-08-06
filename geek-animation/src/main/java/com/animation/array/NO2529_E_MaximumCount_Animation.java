package com.animation.array;

import javax.swing.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.event.ActionEvent;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.event.ActionListener;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.util.Arrays;
import com.animation.launcher.AlgorithmTreeLauncher;

/**
 * NO.2529 正整数和负整数的最大计数 - 动画演示
 * 
 * 算法描述：
 * 给你一个按非递减顺序排列的数组nums，返回正整数数目和负整数数目中的最大值。
 * 注意：0既不是正整数也不是负整数。
 * 
 * 算法思路：
 * 1. 遍历数组，分别统计正整数和负整数的数量
 * 2. 返回两者中的最大值
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class NO2529_E_MaximumCount_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 700;
    private static final Color POSITIVE_COLOR = new Color(76, 175, 80);  // 绿色
    private static final Color NEGATIVE_COLOR = new Color(244, 67, 54);  // 红色
    private static final Color ZERO_COLOR = new Color(158, 158, 158);    // 灰色
    private static final Color HIGHLIGHT_COLOR = new Color(255, 193, 7); // 黄色
    
    private JPanel animationPanel;
    private JButton startButton, pauseButton, resetButton, nextStepButton;
    private JLabel statusLabel, complexityLabel;
    private JTextArea codeArea;
    private Timer animationTimer;
    
    // 算法相关变量
    private int[] nums;
    private int currentIndex;
    private int positiveCount;
    private int negativeCount;
    private boolean isRunning;
    private boolean isPaused;
    private int currentStep;
    
    // 测试用例
    private final int[][] testCases = {
        {-2, -1, -1, 1, 2, 3},
        {-3, -2, -1, 0, 0, 1, 2},
        {5, 20, 66, 1314},
        {-5, -4, -3, -2, -1},
        {0, 0, 0}
    };
    private int currentTestCase = 0;
    
    public NO2529_E_MaximumCount_Animation() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        resetAnimation();
    }
    
    private void initializeComponents() {
        setTitle("NO.2529 正整数和负整数的最大计数 - 动画演示");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // 创建动画面板
        animationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawAnimation(g);
            }
        };
        animationPanel.setBackground(Color.WHITE);
        animationPanel.setPreferredSize(new Dimension(WINDOW_WIDTH - 300, 400));
        
        // 创建控制按钮
        startButton = new JButton("开始动画");
        pauseButton = new JButton("暂停");
        resetButton = new JButton("重置");
        nextStepButton = new JButton("下一步");
        
        pauseButton.setEnabled(false);
        nextStepButton.setEnabled(false);
        
        // 创建状态标签
        statusLabel = new JLabel("准备开始动画演示");
        statusLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        
        // 创建复杂度标签
        complexityLabel = new JLabel("<html><b>时间复杂度:</b> O(n) <b>空间复杂度:</b> O(1)</html>");
        complexityLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        
        // 创建代码显示区域
        codeArea = new JTextArea();
        codeArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        codeArea.setEditable(false);
        codeArea.setText(getAlgorithmCode());
        codeArea.setBackground(new Color(248, 248, 248));
        
        // 创建动画定时器
        animationTimer = new Timer(1000, e -> nextAnimationStep());
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 顶部信息面板
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        topPanel.add(statusLabel, BorderLayout.WEST);
        topPanel.add(complexityLabel, BorderLayout.EAST);
        
        // 中央动画面板
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("算法可视化"));
        centerPanel.add(animationPanel, BorderLayout.CENTER);
        
        // 右侧代码面板
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("算法代码"));
        rightPanel.setPreferredSize(new Dimension(300, 0));
        JScrollPane codeScrollPane = new JScrollPane(codeArea);
        rightPanel.add(codeScrollPane, BorderLayout.CENTER);
        
        // 底部控制面板
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(startButton);
        bottomPanel.add(pauseButton);
        bottomPanel.add(nextStepButton);
        bottomPanel.add(resetButton);
        

        
        // 测试用例选择
        JComboBox<String> testCaseCombo = new JComboBox<>();
        testCaseCombo.addItem("测试用例1: [-2,-1,-1,1,2,3]");
        testCaseCombo.addItem("测试用例2: [-3,-2,-1,0,0,1,2]");
        testCaseCombo.addItem("测试用例3: [5,20,66,1314]");
        testCaseCombo.addItem("测试用例4: [-5,-4,-3,-2,-1]");
        testCaseCombo.addItem("测试用例5: [0,0,0]");
        testCaseCombo.addActionListener(e -> {
            currentTestCase = testCaseCombo.getSelectedIndex();
            resetAnimation();
        });
        bottomPanel.add(new JLabel("选择测试用例:"));
        bottomPanel.add(testCaseCombo);
        
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        startButton.addActionListener(e -> startAnimation());
        pauseButton.addActionListener(e -> pauseAnimation());
        resetButton.addActionListener(e -> resetAnimation());
        nextStepButton.addActionListener(e -> nextAnimationStep());

        // Add window listener to show main window on close
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                AlgorithmTreeLauncher.showMainWindow();
            }
        });
    }
    
    private void startAnimation() {
        if (!isRunning) {
            isRunning = true;
            isPaused = false;
            startButton.setEnabled(false);
            pauseButton.setEnabled(true);
            nextStepButton.setEnabled(false);
            animationTimer.start();
            statusLabel.setText("动画运行中...");
        }
    }
    
    private void pauseAnimation() {
        if (isRunning && !isPaused) {
            isPaused = true;
            animationTimer.stop();
            startButton.setEnabled(true);
            pauseButton.setEnabled(false);
            nextStepButton.setEnabled(true);
            statusLabel.setText("动画已暂停");
        }
    }
    
    private void resetAnimation() {
        animationTimer.stop();
        isRunning = false;
        isPaused = false;
        currentIndex = 0;
        positiveCount = 0;
        negativeCount = 0;
        currentStep = 0;
        
        nums = testCases[currentTestCase].clone();
        
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        nextStepButton.setEnabled(true);
        
        statusLabel.setText("准备开始动画演示");
        SwingUtilities.invokeLater(() -> animationPanel.repaint());
    }
    
    private void nextAnimationStep() {
        if (currentIndex < nums.length) {
            int currentNum = nums[currentIndex];
            
            if (currentNum > 0) {
                positiveCount++;
                statusLabel.setText(String.format("发现正整数 %d，正整数计数: %d", currentNum, positiveCount));
            } else if (currentNum < 0) {
                negativeCount++;
                statusLabel.setText(String.format("发现负整数 %d，负整数计数: %d", currentNum, negativeCount));
            } else {
                statusLabel.setText(String.format("发现零 %d，跳过计数", currentNum));
            }
            
            currentIndex++;
            SwingUtilities.invokeLater(() -> animationPanel.repaint());
            
            if (currentIndex >= nums.length) {
                // 算法完成
                int result = Math.max(positiveCount, negativeCount);
                statusLabel.setText(String.format("算法完成！正整数: %d个，负整数: %d个，最大值: %d", 
                    positiveCount, negativeCount, result));
                
                animationTimer.stop();
                isRunning = false;
                startButton.setEnabled(true);
                pauseButton.setEnabled(false);
                nextStepButton.setEnabled(false);
            }
        }
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int panelWidth = animationPanel.getWidth();
        int panelHeight = animationPanel.getHeight();
        
        if (nums == null || nums.length == 0) return;
        
        // 绘制标题
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(Color.BLACK);
        String title = "数组: " + Arrays.toString(nums);
        FontMetrics fm = g2d.getFontMetrics();
        int titleWidth = fm.stringWidth(title);
        g2d.drawString(title, (panelWidth - titleWidth) / 2, 30);
        
        // 计算数组元素的绘制位置
        int arrayStartY = 80;
        int elementWidth = Math.min(60, (panelWidth - 100) / nums.length);
        int elementHeight = 50;
        int startX = (panelWidth - nums.length * elementWidth) / 2;
        
        // 绘制数组元素
        for (int i = 0; i < nums.length; i++) {
            int x = startX + i * elementWidth;
            int y = arrayStartY;
            
            // 确定颜色
            Color elementColor;
            if (nums[i] > 0) {
                elementColor = POSITIVE_COLOR;
            } else if (nums[i] < 0) {
                elementColor = NEGATIVE_COLOR;
            } else {
                elementColor = ZERO_COLOR;
            }
            
            // 如果是当前处理的元素，使用高亮色
            if (i == currentIndex - 1 && currentIndex > 0) {
                elementColor = HIGHLIGHT_COLOR;
            }
            
            // 绘制元素矩形
            g2d.setColor(elementColor);
            g2d.fillRect(x, y, elementWidth - 2, elementHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, elementWidth - 2, elementHeight);
            
            // 绘制元素值
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            String value = String.valueOf(nums[i]);
            FontMetrics elementFm = g2d.getFontMetrics();
            int valueWidth = elementFm.stringWidth(value);
            int valueHeight = elementFm.getHeight();
            g2d.setColor(Color.WHITE);
            g2d.drawString(value, x + (elementWidth - 2 - valueWidth) / 2, 
                          y + (elementHeight + valueHeight) / 2 - 3);
            
            // 绘制索引
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
            g2d.drawString(String.valueOf(i), x + elementWidth / 2 - 5, y - 5);
        }
        
        // 绘制当前指针
        if (currentIndex > 0 && currentIndex <= nums.length) {
            int pointerX = startX + (currentIndex - 1) * elementWidth + elementWidth / 2;
            int pointerY = arrayStartY + elementHeight + 10;
            
            g2d.setColor(Color.RED);
            g2d.fillPolygon(new int[]{pointerX - 5, pointerX + 5, pointerX}, 
                           new int[]{pointerY, pointerY, pointerY + 10}, 3);
            g2d.drawString("当前", pointerX - 10, pointerY + 25);
        }
        
        // 绘制统计信息
        int statsY = arrayStartY + elementHeight + 80;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        
        // 正整数计数
        g2d.setColor(POSITIVE_COLOR);
        g2d.fillRect(50, statsY, 200, 40);
        g2d.setColor(Color.WHITE);
        g2d.drawString("正整数计数: " + positiveCount, 60, statsY + 25);
        
        // 负整数计数
        g2d.setColor(NEGATIVE_COLOR);
        g2d.fillRect(300, statsY, 200, 40);
        g2d.setColor(Color.WHITE);
        g2d.drawString("负整数计数: " + negativeCount, 310, statsY + 25);
        
        // 当前最大值
        if (currentIndex > 0) {
            int maxCount = Math.max(positiveCount, negativeCount);
            g2d.setColor(Color.BLUE);
            g2d.fillRect(550, statsY, 200, 40);
            g2d.setColor(Color.WHITE);
            g2d.drawString("当前最大值: " + maxCount, 560, statsY + 25);
        }
        
        // 绘制图例
        int legendY = statsY + 80;
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        
        g2d.setColor(POSITIVE_COLOR);
        g2d.fillRect(50, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("正整数", 80, legendY + 15);
        
        g2d.setColor(NEGATIVE_COLOR);
        g2d.fillRect(150, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("负整数", 180, legendY + 15);
        
        g2d.setColor(ZERO_COLOR);
        g2d.fillRect(250, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("零", 280, legendY + 15);
        
        g2d.setColor(HIGHLIGHT_COLOR);
        g2d.fillRect(320, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("当前处理", 350, legendY + 15);
    }
    
    private String getAlgorithmCode() {
        return "public int maximumCount(int[] nums) {\n" +
               "    long countPos = Arrays.stream(nums)\n" +
               "        .filter((e) -> e > 0).count();\n" +
               "    long countNeg = Arrays.stream(nums)\n" +
               "        .filter((e) -> e < 0).count();\n" +
               "    return (int) Math.max(countNeg, countPos);\n" +
               "}\n\n" +
               "// 优化版本（一次遍历）\n" +
               "public int maximumCount(int[] nums) {\n" +
               "    int positiveCount = 0;\n" +
               "    int negativeCount = 0;\n" +
               "    \n" +
               "    for (int num : nums) {\n" +
               "        if (num > 0) {\n" +
               "            positiveCount++;\n" +
               "        } else if (num < 0) {\n" +
               "            negativeCount++;\n" +
               "        }\n" +
               "        // 0既不是正数也不是负数，跳过\n" +
               "    }\n" +
               "    \n" +
               "    return Math.max(positiveCount, negativeCount);\n" +
               "}\n\n" +
               "// 二分查找优化版本（O(log n)）\n" +
               "public int maximumCount(int[] nums) {\n" +
               "    // 找到第一个正数的位置\n" +
               "    int firstPos = findFirst(nums, 1);\n" +
               "    // 找到第一个非负数的位置\n" +
               "    int firstNonNeg = findFirst(nums, 0);\n" +
               "    \n" +
               "    int positiveCount = nums.length - firstPos;\n" +
               "    int negativeCount = firstNonNeg;\n" +
               "    \n" +
               "    return Math.max(positiveCount, negativeCount);\n" +
               "}\n\n" +
               "private int findFirst(int[] nums, int target) {\n" +
               "    int left = 0, right = nums.length;\n" +
               "    while (left < right) {\n" +
               "        int mid = left + (right - left) / 2;\n" +
               "        if (nums[mid] >= target) {\n" +
               "            right = mid;\n" +
               "        } else {\n" +
               "            left = mid + 1;\n" +
               "        }\n" +
               "    }\n" +
               "    return left;\n" +
               "}";
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO2529_E_MaximumCount_Animation().setVisible(true);
        });
    }
}