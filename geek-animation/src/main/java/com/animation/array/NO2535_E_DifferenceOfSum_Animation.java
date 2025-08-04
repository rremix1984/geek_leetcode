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
 * NO.2535 数组元素和与数字和的绝对差 - 动画演示
 * 
 * 算法描述：
 * 给你一个正整数数组nums。
 * 元素和是nums中的所有元素相加求和。
 * 数字和是nums中每一个元素的每一数位（重复数位需多次求和）相加求和。
 * 返回元素和与数字和的绝对差。
 * 
 * 算法思路：
 * 1. 计算数组所有元素的和（元素和）
 * 2. 计算数组中每个元素的各位数字之和的总和（数字和）
 * 3. 返回两者的绝对差
 * 
 * 时间复杂度：O(n * log m)，其中n是数组长度，m是数组中最大元素
 * 空间复杂度：O(1)
 */
public class NO2535_E_DifferenceOfSum_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final Color ELEMENT_COLOR = new Color(33, 150, 243);     // 蓝色
    private static final Color DIGIT_COLOR = new Color(76, 175, 80);        // 绿色
    private static final Color HIGHLIGHT_COLOR = new Color(255, 193, 7);    // 黄色
    private static final Color RESULT_COLOR = new Color(156, 39, 176);      // 紫色
    
    private JPanel animationPanel;
    private JButton startButton, pauseButton, resetButton, nextStepButton;
    private JLabel statusLabel, complexityLabel;
    private JTextArea codeArea;
    private Timer animationTimer;
    
    // 算法相关变量
    private int[] nums;
    private int currentIndex;
    private int elementSum;
    private int digitSum;
    private int currentElementSum;
    private int currentDigitSum;
    private boolean isRunning;
    private boolean isPaused;
    private int currentStep;
    private String currentDigits;
    
    // 测试用例
    private final int[][] testCases = {
        {1, 15, 6, 3},
        {1, 2, 3, 4},
        {99, 123, 456},
        {10, 20, 30},
        {1000, 2000, 3000}
    };
    private int currentTestCase = 0;
    
    public NO2535_E_DifferenceOfSum_Animation() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        resetAnimation();
    }
    
    private void initializeComponents() {
        setTitle("NO.2535 数组元素和与数字和的绝对差 - 动画演示");
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
        animationPanel.setPreferredSize(new Dimension(WINDOW_WIDTH - 300, 500));
        
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
        complexityLabel = new JLabel("<html><b>时间复杂度:</b> O(n*log m) <b>空间复杂度:</b> O(1)</html>");
        complexityLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        
        // 创建代码显示区域
        codeArea = new JTextArea();
        codeArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        codeArea.setEditable(false);
        codeArea.setText(getAlgorithmCode());
        codeArea.setBackground(new Color(248, 248, 248));
        
        // 创建动画定时器
        animationTimer = new Timer(1500, e -> nextAnimationStep());
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
        
        // 返回首页按钮
        JButton homeButton = new JButton("返回首页");
        homeButton.addActionListener(e -> {
            dispose(); // 关闭当前窗口
            SwingUtilities.invokeLater(() -> {
                try {
                    com.animation.launcher.AlgorithmTreeLauncher.showMainWindow();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });
        bottomPanel.add(homeButton);
        
        // 测试用例选择
        JComboBox<String> testCaseCombo = new JComboBox<>();
        testCaseCombo.addItem("测试用例1: [1,15,6,3]");
        testCaseCombo.addItem("测试用例2: [1,2,3,4]");
        testCaseCombo.addItem("测试用例3: [99,123,456]");
        testCaseCombo.addItem("测试用例4: [10,20,30]");
        testCaseCombo.addItem("测试用例5: [1000,2000,3000]");
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
        elementSum = 0;
        digitSum = 0;
        currentElementSum = 0;
        currentDigitSum = 0;
        currentStep = 0;
        currentDigits = "";
        
        nums = testCases[currentTestCase].clone();
        
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        nextStepButton.setEnabled(true);
        
        statusLabel.setText("准备开始动画演示");
        animationPanel.repaint();
    }
    
    private void nextAnimationStep() {
        if (currentIndex < nums.length) {
            int currentNum = nums[currentIndex];
            
            // 计算当前元素的数字和
            int digitSumForCurrent = digitSum(currentNum);
            currentDigits = getDigitsString(currentNum);
            
            // 更新累计和
            currentElementSum = elementSum + currentNum;
            currentDigitSum = digitSum + digitSumForCurrent;
            
            statusLabel.setText(String.format("处理元素 %d: 数字分解为 %s，数字和为 %d", 
                currentNum, currentDigits, digitSumForCurrent));
            
            elementSum = currentElementSum;
            digitSum = currentDigitSum;
            currentIndex++;
            
            animationPanel.repaint();
            
            if (currentIndex >= nums.length) {
                // 算法完成
                int result = Math.abs(elementSum - digitSum);
                statusLabel.setText(String.format("算法完成！元素和: %d，数字和: %d，绝对差: %d", 
                    elementSum, digitSum, result));
                
                animationTimer.stop();
                isRunning = false;
                startButton.setEnabled(true);
                pauseButton.setEnabled(false);
                nextStepButton.setEnabled(false);
            }
        }
    }
    
    private int digitSum(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }
    
    private String getDigitsString(int num) {
        StringBuilder sb = new StringBuilder();
        String numStr = String.valueOf(num);
        for (int i = 0; i < numStr.length(); i++) {
            if (i > 0) sb.append(" + ");
            sb.append(numStr.charAt(i));
        }
        return sb.toString();
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
        int arrayStartY = 70;
        int elementWidth = Math.min(80, (panelWidth - 100) / nums.length);
        int elementHeight = 60;
        int startX = (panelWidth - nums.length * elementWidth) / 2;
        
        // 绘制数组元素
        for (int i = 0; i < nums.length; i++) {
            int x = startX + i * elementWidth;
            int y = arrayStartY;
            
            // 确定颜色
            Color elementColor = (i == currentIndex - 1 && currentIndex > 0) ? 
                HIGHLIGHT_COLOR : ELEMENT_COLOR;
            
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
        
        // 绘制数字分解过程
        if (currentIndex > 0 && !currentDigits.isEmpty()) {
            int decompositionY = arrayStartY + elementHeight + 60;
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            g2d.setColor(Color.BLACK);
            
            String decomposition = String.format("当前元素 %d 的数字分解: %s = %d", 
                nums[currentIndex - 1], currentDigits, digitSum(nums[currentIndex - 1]));
            FontMetrics decompFm = g2d.getFontMetrics();
            int decompWidth = decompFm.stringWidth(decomposition);
            g2d.drawString(decomposition, (panelWidth - decompWidth) / 2, decompositionY);
        }
        
        // 绘制统计信息
        int statsY = arrayStartY + elementHeight + 120;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        
        // 元素和
        g2d.setColor(ELEMENT_COLOR);
        g2d.fillRect(50, statsY, 250, 50);
        g2d.setColor(Color.WHITE);
        g2d.drawString("元素和: " + elementSum, 60, statsY + 30);
        
        // 数字和
        g2d.setColor(DIGIT_COLOR);
        g2d.fillRect(350, statsY, 250, 50);
        g2d.setColor(Color.WHITE);
        g2d.drawString("数字和: " + digitSum, 360, statsY + 30);
        
        // 绝对差
        if (currentIndex >= nums.length && elementSum > 0) {
            int result = Math.abs(elementSum - digitSum);
            g2d.setColor(RESULT_COLOR);
            g2d.fillRect(650, statsY, 250, 50);
            g2d.setColor(Color.WHITE);
            g2d.drawString("绝对差: " + result, 660, statsY + 30);
        }
        
        // 绘制计算过程
        int processY = statsY + 80;
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.BLACK);
        
        if (currentIndex > 0) {
            // 显示当前的计算过程
            StringBuilder elementSumStr = new StringBuilder("元素和: ");
            StringBuilder digitSumStr = new StringBuilder("数字和: ");
            
            for (int i = 0; i < currentIndex; i++) {
                if (i > 0) {
                    elementSumStr.append(" + ");
                    digitSumStr.append(" + ");
                }
                elementSumStr.append(nums[i]);
                digitSumStr.append(digitSum(nums[i]));
            }
            
            elementSumStr.append(" = ").append(elementSum);
            digitSumStr.append(" = ").append(digitSum);
            
            g2d.drawString(elementSumStr.toString(), 50, processY);
            g2d.drawString(digitSumStr.toString(), 50, processY + 25);
            
            if (currentIndex >= nums.length) {
                int result = Math.abs(elementSum - digitSum);
                g2d.drawString(String.format("绝对差: |%d - %d| = %d", elementSum, digitSum, result), 
                    50, processY + 50);
            }
        }
        
        // 绘制图例
        int legendY = processY + 100;
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        
        g2d.setColor(ELEMENT_COLOR);
        g2d.fillRect(50, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("数组元素", 80, legendY + 15);
        
        g2d.setColor(DIGIT_COLOR);
        g2d.fillRect(170, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("数字和", 200, legendY + 15);
        
        g2d.setColor(HIGHLIGHT_COLOR);
        g2d.fillRect(270, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("当前处理", 300, legendY + 15);
        
        g2d.setColor(RESULT_COLOR);
        g2d.fillRect(390, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("最终结果", 420, legendY + 15);
    }
    
    private String getAlgorithmCode() {
        return "public int differenceOfSum(int[] nums) {\n" +
               "    int elementSum = 0;\n" +
               "    int digitSum = 0;\n" +
               "    \n" +
               "    for (int num : nums) {\n" +
               "        elementSum += num;\n" +
               "        digitSum += digitSum(num);\n" +
               "    }\n" +
               "    \n" +
               "    return Math.abs(elementSum - digitSum);\n" +
               "}\n\n" +
               "private int digitSum(int num) {\n" +
               "    int sum = 0;\n" +
               "    while (num > 0) {\n" +
               "        sum += num % 10;\n" +
               "        num /= 10;\n" +
               "    }\n" +
               "    return sum;\n" +
               "}\n\n" +
               "// 优化版本（使用流）\n" +
               "public int differenceOfSum(int[] nums) {\n" +
               "    int elementSum = Arrays.stream(nums).sum();\n" +
               "    int digitSum = Arrays.stream(nums)\n" +
               "        .map(this::digitSum)\n" +
               "        .sum();\n" +
               "    return Math.abs(elementSum - digitSum);\n" +
               "}";
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO2535_E_DifferenceOfSum_Animation().setVisible(true);
        });
    }
}