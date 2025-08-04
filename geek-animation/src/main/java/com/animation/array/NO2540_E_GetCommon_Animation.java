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
 * NO.2540 最小公共值 - 动画演示
 * 
 * 算法描述：
 * 给你两个整数数组nums1和nums2，它们已经按非递减顺序排列，请你返回两个数组的最小公共整数。
 * 如果两个数组没有公共整数，请你返回-1。
 * 如果一个整数在两个数组中都至少出现一次，那么这个整数是公共的。
 * 
 * 算法思路：
 * 使用双指针技术：
 * 1. 分别用两个指针指向两个数组的开始
 * 2. 比较两个指针指向的元素
 * 3. 如果相等，找到公共元素，返回
 * 4. 如果不等，移动指向较小元素的指针
 * 5. 如果任一指针到达数组末尾，返回-1
 * 
 * 时间复杂度：O(m + n)
 * 空间复杂度：O(1)
 */
public class NO2540_E_GetCommon_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final Color ARRAY1_COLOR = new Color(33, 150, 243);      // 蓝色
    private static final Color ARRAY2_COLOR = new Color(244, 67, 54);       // 红色
    private static final Color COMMON_COLOR = new Color(76, 175, 80);       // 绿色
    private static final Color POINTER1_COLOR = new Color(63, 81, 181);     // 深蓝色
    private static final Color POINTER2_COLOR = new Color(233, 30, 99);     // 深红色
    
    private JPanel animationPanel;
    private JButton startButton, pauseButton, resetButton, nextStepButton;
    private JLabel statusLabel, complexityLabel;
    private JTextArea codeArea;
    private Timer animationTimer;
    
    // 算法相关变量
    private int[] nums1;
    private int[] nums2;
    private int pointer1;
    private int pointer2;
    private int result;
    private boolean isRunning;
    private boolean isPaused;
    private boolean algorithmComplete;
    private String comparisonText;
    
    // 测试用例
    private final int[][][] testCases = {
        {{1, 2, 3}, {2, 4}},
        {{1, 2, 3, 6}, {2, 3, 4, 5, 6}},
        {{1, 3, 5}, {2, 4, 6}},
        {{1, 2, 3}, {1, 2, 3}},
        {{5, 10, 15}, {3, 5, 7, 10, 12}}
    };
    private int currentTestCase = 0;
    
    public NO2540_E_GetCommon_Animation() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        resetAnimation();
    }
    
    private void initializeComponents() {
        setTitle("NO.2540 最小公共值 - 动画演示");
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
        complexityLabel = new JLabel("<html><b>时间复杂度:</b> O(m+n) <b>空间复杂度:</b> O(1)</html>");
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
        testCaseCombo.addItem("测试用例1: [1,2,3] & [2,4]");
        testCaseCombo.addItem("测试用例2: [1,2,3,6] & [2,3,4,5,6]");
        testCaseCombo.addItem("测试用例3: [1,3,5] & [2,4,6]");
        testCaseCombo.addItem("测试用例4: [1,2,3] & [1,2,3]");
        testCaseCombo.addItem("测试用例5: [5,10,15] & [3,5,7,10,12]");
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
        pointer1 = 0;
        pointer2 = 0;
        result = -1;
        algorithmComplete = false;
        comparisonText = "";
        
        nums1 = testCases[currentTestCase][0].clone();
        nums2 = testCases[currentTestCase][1].clone();
        
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        nextStepButton.setEnabled(true);
        
        statusLabel.setText("准备开始动画演示");
        animationPanel.repaint();
    }
    
    private void nextAnimationStep() {
        if (algorithmComplete) return;
        
        if (pointer1 < nums1.length && pointer2 < nums2.length) {
            int val1 = nums1[pointer1];
            int val2 = nums2[pointer2];
            
            comparisonText = String.format("比较: nums1[%d]=%d 与 nums2[%d]=%d", 
                pointer1, val1, pointer2, val2);
            
            if (val1 == val2) {
                result = val1;
                algorithmComplete = true;
                statusLabel.setText(String.format("找到最小公共值: %d", result));
                
                animationTimer.stop();
                isRunning = false;
                startButton.setEnabled(true);
                pauseButton.setEnabled(false);
                nextStepButton.setEnabled(false);
            } else if (val1 < val2) {
                pointer1++;
                statusLabel.setText(String.format("%d < %d，移动指针1", val1, val2));
            } else {
                pointer2++;
                statusLabel.setText(String.format("%d > %d，移动指针2", val1, val2));
            }
        } else {
            // 其中一个数组遍历完毕
            result = -1;
            algorithmComplete = true;
            statusLabel.setText("没有找到公共值，返回 -1");
            
            animationTimer.stop();
            isRunning = false;
            startButton.setEnabled(true);
            pauseButton.setEnabled(false);
            nextStepButton.setEnabled(false);
        }
        
        animationPanel.repaint();
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int panelWidth = animationPanel.getWidth();
        int panelHeight = animationPanel.getHeight();
        
        if (nums1 == null || nums2 == null) return;
        
        // 绘制标题
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(Color.BLACK);
        String title1 = "nums1: " + Arrays.toString(nums1);
        String title2 = "nums2: " + Arrays.toString(nums2);
        FontMetrics fm = g2d.getFontMetrics();
        
        g2d.drawString(title1, 50, 30);
        g2d.drawString(title2, 50, 55);
        
        // 绘制第一个数组
        int array1StartY = 80;
        int elementWidth = 60;
        int elementHeight = 50;
        int array1StartX = 50;
        
        for (int i = 0; i < nums1.length; i++) {
            int x = array1StartX + i * elementWidth;
            int y = array1StartY;
            
            // 确定颜色
            Color elementColor = ARRAY1_COLOR;
            if (i == pointer1 && !algorithmComplete) {
                elementColor = POINTER1_COLOR;
            } else if (algorithmComplete && result != -1 && nums1[i] == result && i <= pointer1) {
                elementColor = COMMON_COLOR;
            }
            
            // 绘制元素矩形
            g2d.setColor(elementColor);
            g2d.fillRect(x, y, elementWidth - 2, elementHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, elementWidth - 2, elementHeight);
            
            // 绘制元素值
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            String value = String.valueOf(nums1[i]);
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
        
        // 绘制第一个数组的指针
        if (pointer1 < nums1.length) {
            int pointerX = array1StartX + pointer1 * elementWidth + elementWidth / 2;
            int pointerY = array1StartY + elementHeight + 10;
            
            g2d.setColor(POINTER1_COLOR);
            g2d.fillPolygon(new int[]{pointerX - 5, pointerX + 5, pointerX}, 
                           new int[]{pointerY, pointerY, pointerY + 10}, 3);
            g2d.drawString("指针1", pointerX - 15, pointerY + 25);
        }
        
        // 绘制第二个数组
        int array2StartY = array1StartY + elementHeight + 80;
        int array2StartX = 50;
        
        for (int i = 0; i < nums2.length; i++) {
            int x = array2StartX + i * elementWidth;
            int y = array2StartY;
            
            // 确定颜色
            Color elementColor = ARRAY2_COLOR;
            if (i == pointer2 && !algorithmComplete) {
                elementColor = POINTER2_COLOR;
            } else if (algorithmComplete && result != -1 && nums2[i] == result && i <= pointer2) {
                elementColor = COMMON_COLOR;
            }
            
            // 绘制元素矩形
            g2d.setColor(elementColor);
            g2d.fillRect(x, y, elementWidth - 2, elementHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, elementWidth - 2, elementHeight);
            
            // 绘制元素值
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            String value = String.valueOf(nums2[i]);
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
        
        // 绘制第二个数组的指针
        if (pointer2 < nums2.length) {
            int pointerX = array2StartX + pointer2 * elementWidth + elementWidth / 2;
            int pointerY = array2StartY + elementHeight + 10;
            
            g2d.setColor(POINTER2_COLOR);
            g2d.fillPolygon(new int[]{pointerX - 5, pointerX + 5, pointerX}, 
                           new int[]{pointerY, pointerY, pointerY + 10}, 3);
            g2d.drawString("指针2", pointerX - 15, pointerY + 25);
        }
        
        // 绘制比较信息
        if (!comparisonText.isEmpty()) {
            int comparisonY = array2StartY + elementHeight + 80;
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            g2d.setColor(Color.BLACK);
            g2d.drawString(comparisonText, 50, comparisonY);
        }
        
        // 绘制结果
        if (algorithmComplete) {
            int resultY = array2StartY + elementHeight + 120;
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 18));
            
            if (result != -1) {
                g2d.setColor(COMMON_COLOR);
                g2d.fillRect(50, resultY, 300, 50);
                g2d.setColor(Color.WHITE);
                g2d.drawString("最小公共值: " + result, 60, resultY + 30);
            } else {
                g2d.setColor(Color.GRAY);
                g2d.fillRect(50, resultY, 300, 50);
                g2d.setColor(Color.WHITE);
                g2d.drawString("没有公共值: -1", 60, resultY + 30);
            }
        }
        
        // 绘制算法步骤说明
        int stepY = array2StartY + elementHeight + 200;
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        g2d.setColor(Color.BLACK);
        
        g2d.drawString("算法步骤:", 50, stepY);
        g2d.drawString("1. 使用两个指针分别指向两个数组的开始", 50, stepY + 20);
        g2d.drawString("2. 比较两个指针指向的元素", 50, stepY + 40);
        g2d.drawString("3. 如果相等，找到最小公共值", 50, stepY + 60);
        g2d.drawString("4. 如果不等，移动指向较小元素的指针", 50, stepY + 80);
        g2d.drawString("5. 重复直到找到公共值或遍历完成", 50, stepY + 100);
        
        // 绘制图例
        int legendY = stepY + 130;
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        
        g2d.setColor(ARRAY1_COLOR);
        g2d.fillRect(50, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("数组1", 80, legendY + 15);
        
        g2d.setColor(ARRAY2_COLOR);
        g2d.fillRect(130, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("数组2", 160, legendY + 15);
        
        g2d.setColor(POINTER1_COLOR);
        g2d.fillRect(210, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("指针1", 240, legendY + 15);
        
        g2d.setColor(POINTER2_COLOR);
        g2d.fillRect(290, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("指针2", 320, legendY + 15);
        
        g2d.setColor(COMMON_COLOR);
        g2d.fillRect(370, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("公共值", 400, legendY + 15);
    }
    
    private String getAlgorithmCode() {
        return "public int getCommon(int[] nums1, int[] nums2) {\n" +
               "    int i = 0, j = 0;\n" +
               "    \n" +
               "    while (i < nums1.length && j < nums2.length) {\n" +
               "        if (nums1[i] == nums2[j]) {\n" +
               "            return nums1[i];\n" +
               "        } else if (nums1[i] < nums2[j]) {\n" +
               "            i++;\n" +
               "        } else {\n" +
               "            j++;\n" +
               "        }\n" +
               "    }\n" +
               "    \n" +
               "    return -1;\n" +
               "}\n\n" +
               "// 使用HashSet的解法\n" +
               "public int getCommon(int[] nums1, int[] nums2) {\n" +
               "    Set<Integer> set = new HashSet<>();\n" +
               "    for (int num : nums1) {\n" +
               "        set.add(num);\n" +
               "    }\n" +
               "    \n" +
               "    for (int num : nums2) {\n" +
               "        if (set.contains(num)) {\n" +
               "            return num;\n" +
               "        }\n" +
               "    }\n" +
               "    \n" +
               "    return -1;\n" +
               "}";
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO2540_E_GetCommon_Animation().setVisible(true);
        });
    }
}