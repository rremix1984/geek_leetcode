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
 * NO.2574 左右元素和的差值 - 动画演示
 * 
 * 算法描述：
 * 给你一个下标从0开始的整数数组nums，请你找出一个下标从0开始的整数数组answer，其中：
 * answer.length == nums.length
 * answer[i] = |leftSum[i] - rightSum[i]|
 * 其中：
 * leftSum[i]是数组nums中下标i左侧元素的和。如果不存在对应的元素，leftSum[i] = 0。
 * rightSum[i]是数组nums中下标i右侧元素的和。如果不存在对应的元素，rightSum[i] = 0。
 * 返回数组answer。
 * 
 * 算法思路：
 * 1. 计算数组总和
 * 2. 遍历数组，对每个位置计算左侧和右侧元素的和
 * 3. 左侧和可以通过累加得到，右侧和 = 总和 - 左侧和 - 当前元素
 * 4. 计算绝对差值
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)（不考虑输出数组）
 */
public class NO2574_E_LeftRightDifference_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final Color ARRAY_COLOR = new Color(33, 150, 243);       // 蓝色
    private static final Color CURRENT_COLOR = new Color(255, 193, 7);      // 黄色
    private static final Color LEFT_COLOR = new Color(76, 175, 80);         // 绿色
    private static final Color RIGHT_COLOR = new Color(244, 67, 54);        // 红色
    private static final Color RESULT_COLOR = new Color(156, 39, 176);      // 紫色
    
    private JPanel animationPanel;
    private JButton startButton, pauseButton, resetButton, nextStepButton;
    private JLabel statusLabel, complexityLabel;
    private JTextArea codeArea;
    private Timer animationTimer;
    
    // 算法相关变量
    private int[] nums;
    private int[] answer;
    private int currentIndex;
    private int totalSum;
    private int leftSum;
    private int rightSum;
    private boolean isRunning;
    private boolean isPaused;
    private boolean algorithmComplete;
    private String operationText;
    
    // 测试用例
    private final int[][] testCases = {
        {10, 4, 8, 3},
        {1},
        {1, 2, 3, 4, 5},
        {5, 1, 3, 2, 4},
        {2, 5, 1, 6}
    };
    private int currentTestCase = 0;
    
    public NO2574_E_LeftRightDifference_Animation() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        resetAnimation();
    }
    
    private void initializeComponents() {
        setTitle("NO.2574 左右元素和的差值 - 动画演示");
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
        complexityLabel = new JLabel("<html><b>时间复杂度:</b> O(n) <b>空间复杂度:</b> O(1)</html>");
        complexityLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        
        // 创建代码显示区域
        codeArea = new JTextArea();
        codeArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        codeArea.setEditable(false);
        codeArea.setText(getAlgorithmCode());
        codeArea.setBackground(new Color(248, 248, 248));
        
        // 创建动画定时器
        animationTimer = new Timer(2000, e -> nextAnimationStep());
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
        testCaseCombo.addItem("测试用例1: [10,4,8,3]");
        testCaseCombo.addItem("测试用例2: [1]");
        testCaseCombo.addItem("测试用例3: [1,2,3,4,5]");
        testCaseCombo.addItem("测试用例4: [5,1,3,2,4]");
        testCaseCombo.addItem("测试用例5: [2,5,1,6]");
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
        leftSum = 0;
        rightSum = 0;
        algorithmComplete = false;
        operationText = "";
        
        nums = testCases[currentTestCase].clone();
        answer = new int[nums.length];
        
        // 计算总和
        totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }
        
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        nextStepButton.setEnabled(true);
        
        statusLabel.setText("准备开始动画演示");
        SwingUtilities.invokeLater(() -> animationPanel.repaint());
    }
    
    private void nextAnimationStep() {
        if (algorithmComplete || currentIndex >= nums.length) return;
        
        // 计算当前位置的左侧和右侧和
        rightSum = totalSum - leftSum - nums[currentIndex];
        
        // 计算绝对差值
        int diff = Math.abs(leftSum - rightSum);
        answer[currentIndex] = diff;
        
        operationText = String.format("位置 %d: 左侧和=%d, 右侧和=%d, 差值=%d", 
            currentIndex, leftSum, rightSum, diff);
        statusLabel.setText(operationText);
        
        // 更新左侧和（为下一次迭代准备）
        leftSum += nums[currentIndex];
        currentIndex++;
        
        if (currentIndex >= nums.length) {
            algorithmComplete = true;
            statusLabel.setText(String.format("算法完成！结果数组: %s", Arrays.toString(answer)));
            
            animationTimer.stop();
            isRunning = false;
            startButton.setEnabled(true);
            pauseButton.setEnabled(false);
            nextStepButton.setEnabled(false);
        }
        
        SwingUtilities.invokeLater(() -> animationPanel.repaint());
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
        String title = "原数组: " + Arrays.toString(nums) + " (总和: " + totalSum + ")";
        FontMetrics fm = g2d.getFontMetrics();
        int titleWidth = fm.stringWidth(title);
        g2d.drawString(title, (panelWidth - titleWidth) / 2, 30);
        
        // 计算数组元素的绘制位置
        int arrayStartY = 70;
        int elementWidth = Math.min(80, (panelWidth - 100) / nums.length);
        int elementHeight = 60;
        int startX = (panelWidth - nums.length * elementWidth) / 2;
        
        // 绘制原数组
        for (int i = 0; i < nums.length; i++) {
            int x = startX + i * elementWidth;
            int y = arrayStartY;
            
            // 确定颜色
            Color elementColor;
            if (i == currentIndex - 1 && currentIndex > 0) {
                elementColor = CURRENT_COLOR;  // 刚处理的元素
            } else if (i < currentIndex) {
                elementColor = LEFT_COLOR;     // 左侧元素
            } else if (i > currentIndex) {
                elementColor = RIGHT_COLOR;    // 右侧元素
            } else {
                elementColor = CURRENT_COLOR;  // 当前处理的元素
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
        if (currentIndex < nums.length) {
            int pointerX = startX + currentIndex * elementWidth + elementWidth / 2;
            int pointerY = arrayStartY + elementHeight + 10;
            
            g2d.setColor(CURRENT_COLOR);
            g2d.fillPolygon(new int[]{pointerX - 5, pointerX + 5, pointerX}, 
                           new int[]{pointerY, pointerY, pointerY + 10}, 3);
            g2d.drawString("当前", pointerX - 10, pointerY + 25);
        }
        
        // 绘制结果数组
        int resultArrayY = arrayStartY + elementHeight + 80;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("结果数组:", startX, resultArrayY - 10);
        
        for (int i = 0; i < nums.length; i++) {
            int x = startX + i * elementWidth;
            int y = resultArrayY;
            
            // 确定颜色
            Color elementColor = (i < currentIndex) ? RESULT_COLOR : Color.LIGHT_GRAY;
            
            // 绘制元素矩形
            g2d.setColor(elementColor);
            g2d.fillRect(x, y, elementWidth - 2, elementHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, elementWidth - 2, elementHeight);
            
            // 绘制元素值
            if (i < currentIndex) {
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
                String value = String.valueOf(answer[i]);
                FontMetrics elementFm = g2d.getFontMetrics();
                int valueWidth = elementFm.stringWidth(value);
                int valueHeight = elementFm.getHeight();
                g2d.setColor(Color.WHITE);
                g2d.drawString(value, x + (elementWidth - 2 - valueWidth) / 2, 
                              y + (elementHeight + valueHeight) / 2 - 3);
            }
        }
        
        // 绘制计算过程
        if (!operationText.isEmpty()) {
            int calculationY = resultArrayY + elementHeight + 40;
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            g2d.setColor(Color.BLUE);
            g2d.drawString(operationText, 50, calculationY);
            
            // 详细计算过程
            if (currentIndex > 0) {
                int detailY = calculationY + 30;
                g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                g2d.setColor(Color.BLACK);
                
                int prevIndex = currentIndex - 1;
                int prevLeftSum = leftSum - nums[prevIndex];
                int prevRightSum = totalSum - prevLeftSum - nums[prevIndex];
                
                g2d.drawString(String.format("左侧和计算: %s = %d", 
                    getLeftSumString(prevIndex), prevLeftSum), 50, detailY);
                g2d.drawString(String.format("右侧和计算: %d - %d - %d = %d", 
                    totalSum, prevLeftSum, nums[prevIndex], prevRightSum), 50, detailY + 20);
                g2d.drawString(String.format("绝对差值: |%d - %d| = %d", 
                    prevLeftSum, prevRightSum, answer[prevIndex]), 50, detailY + 40);
            }
        }
        
        // 绘制算法步骤说明
        int stepY = resultArrayY + elementHeight + 160;
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        g2d.setColor(Color.BLACK);
        
        g2d.drawString("算法步骤:", 50, stepY);
        g2d.drawString("1. 计算数组总和", 50, stepY + 20);
        g2d.drawString("2. 遍历每个位置，计算左侧和（累加）", 50, stepY + 40);
        g2d.drawString("3. 计算右侧和 = 总和 - 左侧和 - 当前元素", 50, stepY + 60);
        g2d.drawString("4. 计算绝对差值并存入结果数组", 50, stepY + 80);
        
        // 绘制图例
        int legendY = stepY + 110;
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        
        g2d.setColor(LEFT_COLOR);
        g2d.fillRect(50, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("左侧元素", 80, legendY + 15);
        
        g2d.setColor(CURRENT_COLOR);
        g2d.fillRect(150, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("当前元素", 180, legendY + 15);
        
        g2d.setColor(RIGHT_COLOR);
        g2d.fillRect(250, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("右侧元素", 280, legendY + 15);
        
        g2d.setColor(RESULT_COLOR);
        g2d.fillRect(350, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("结果值", 380, legendY + 15);
    }
    
    private String getLeftSumString(int index) {
        if (index == 0) return "0";
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < index; i++) {
            if (i > 0) sb.append(" + ");
            sb.append(nums[i]);
        }
        return sb.toString();
    }
    
    private String getAlgorithmCode() {
        return "public int[] leftRightDifference(int[] nums) {\n" +
               "    int n = nums.length;\n" +
               "    int[] answer = new int[n];\n" +
               "    \n" +
               "    // 计算总和\n" +
               "    int totalSum = 0;\n" +
               "    for (int num : nums) {\n" +
               "        totalSum += num;\n" +
               "    }\n" +
               "    \n" +
               "    int leftSum = 0;\n" +
               "    for (int i = 0; i < n; i++) {\n" +
               "        // 右侧和 = 总和 - 左侧和 - 当前元素\n" +
               "        int rightSum = totalSum - leftSum - nums[i];\n" +
               "        answer[i] = Math.abs(leftSum - rightSum);\n" +
               "        \n" +
               "        // 更新左侧和\n" +
               "        leftSum += nums[i];\n" +
               "    }\n" +
               "    \n" +
               "    return answer;\n" +
               "}\n\n" +
               "// 空间优化版本（两次遍历）\n" +
               "public int[] leftRightDifference(int[] nums) {\n" +
               "    int n = nums.length;\n" +
               "    int[] leftSum = new int[n];\n" +
               "    int[] rightSum = new int[n];\n" +
               "    \n" +
               "    // 计算左侧和\n" +
               "    for (int i = 1; i < n; i++) {\n" +
               "        leftSum[i] = leftSum[i-1] + nums[i-1];\n" +
               "    }\n" +
               "    \n" +
               "    // 计算右侧和\n" +
               "    for (int i = n-2; i >= 0; i--) {\n" +
               "        rightSum[i] = rightSum[i+1] + nums[i+1];\n" +
               "    }\n" +
               "    \n" +
               "    // 计算差值\n" +
               "    int[] answer = new int[n];\n" +
               "    for (int i = 0; i < n; i++) {\n" +
               "        answer[i] = Math.abs(leftSum[i] - rightSum[i]);\n" +
               "    }\n" +
               "    \n" +
               "    return answer;\n" +
               "}";
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO2574_E_LeftRightDifference_Animation().setVisible(true);
        });
    }
}