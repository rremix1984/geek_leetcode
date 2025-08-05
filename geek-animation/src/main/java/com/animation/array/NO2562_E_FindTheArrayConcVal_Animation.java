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
 * NO.2562 找出数组的串联值 - 动画演示
 * 
 * 算法描述：
 * 给你一个下标从0开始的整数数组nums。
 * 现定义两个数字的串联是由这两个数值串联起来形成的新数字。
 * 数组的串联值最初等于0。执行下述操作直到nums变为空：
 * - 如果nums中存在不止一个数字，分别选中nums中的第一个元素和最后一个元素，将二者串联得到的值加到串联值上，然后从nums中删除第一个和最后一个元素。
 * - 如果仅存在一个数字，将该数字的值加到串联值上，然后从nums中删除这个元素。
 * 返回数组的串联值。
 * 
 * 算法思路：
 * 1. 使用双指针，分别指向数组的开始和结束
 * 2. 当左指针小于右指针时，串联两个数字并累加
 * 3. 当左指针等于右指针时，直接累加中间的数字
 * 4. 移动指针直到处理完所有元素
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class NO2562_E_FindTheArrayConcVal_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final Color ARRAY_COLOR = new Color(33, 150, 243);       // 蓝色
    private static final Color LEFT_POINTER_COLOR = new Color(76, 175, 80); // 绿色
    private static final Color RIGHT_POINTER_COLOR = new Color(244, 67, 54);// 红色
    private static final Color PROCESSED_COLOR = new Color(158, 158, 158);  // 灰色
    private static final Color CONCAT_COLOR = new Color(156, 39, 176);      // 紫色
    
    private JPanel animationPanel;
    private JButton startButton, pauseButton, resetButton, nextStepButton;
    private JLabel statusLabel, complexityLabel;
    private JTextArea codeArea;
    private Timer animationTimer;
    
    // 算法相关变量
    private int[] nums;
    private int left;
    private int right;
    private long concatValue;
    private boolean isRunning;
    private boolean isPaused;
    private boolean algorithmComplete;
    private String operationText;
    private String currentConcat;
    
    // 测试用例
    private final int[][] testCases = {
        {7, 52, 2, 4},
        {5, 14, 13, 8, 12},
        {1, 2, 3},
        {42},
        {1, 23, 456, 7890}
    };
    private int currentTestCase = 0;
    
    public NO2562_E_FindTheArrayConcVal_Animation() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        resetAnimation();
    }
    
    private void initializeComponents() {
        setTitle("NO.2562 找出数组的串联值 - 动画演示");
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
        testCaseCombo.addItem("测试用例1: [7,52,2,4]");
        testCaseCombo.addItem("测试用例2: [5,14,13,8,12]");
        testCaseCombo.addItem("测试用例3: [1,2,3]");
        testCaseCombo.addItem("测试用例4: [42]");
        testCaseCombo.addItem("测试用例5: [1,23,456,7890]");
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
        left = 0;
        right = 0;
        concatValue = 0;
        algorithmComplete = false;
        operationText = "";
        currentConcat = "";
        
        nums = testCases[currentTestCase].clone();
        right = nums.length - 1;
        
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        nextStepButton.setEnabled(true);
        
        statusLabel.setText("准备开始动画演示");
        animationPanel.repaint();
    }
    
    private void nextAnimationStep() {
        if (algorithmComplete || left > right) return;
        
        if (left == right) {
            // 只有一个元素
            int value = nums[left];
            concatValue += value;
            currentConcat = String.valueOf(value);
            operationText = String.format("只剩一个元素 %d，直接加到串联值", value);
            statusLabel.setText(operationText);
            left++;
        } else {
            // 两个元素需要串联
            int leftVal = nums[left];
            int rightVal = nums[right];
            String concatStr = String.valueOf(leftVal) + String.valueOf(rightVal);
            long concatNum = Long.parseLong(concatStr);
            concatValue += concatNum;
            currentConcat = concatStr;
            
            operationText = String.format("串联 %d 和 %d 得到 %s，加到串联值", 
                leftVal, rightVal, concatStr);
            statusLabel.setText(operationText);
            
            left++;
            right--;
        }
        
        if (left > right) {
            algorithmComplete = true;
            statusLabel.setText(String.format("算法完成！最终串联值: %d", concatValue));
            
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
        int elementWidth = Math.min(80, (panelWidth - 100) / nums.length);
        int elementHeight = 60;
        int startX = (panelWidth - nums.length * elementWidth) / 2;
        
        // 绘制数组元素
        for (int i = 0; i < nums.length; i++) {
            int x = startX + i * elementWidth;
            int y = arrayStartY;
            
            // 确定颜色
            Color elementColor;
            if (i < left || i > right) {
                elementColor = PROCESSED_COLOR;  // 已处理的元素
            } else if (i == left && i == right) {
                elementColor = CONCAT_COLOR;     // 中间唯一元素
            } else if (i == left) {
                elementColor = LEFT_POINTER_COLOR;   // 左指针
            } else if (i == right) {
                elementColor = RIGHT_POINTER_COLOR;  // 右指针
            } else {
                elementColor = ARRAY_COLOR;      // 普通元素
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
        
        // 绘制指针标签
        if (left <= right) {
            // 左指针
            if (left < nums.length) {
                int leftPointerX = startX + left * elementWidth + elementWidth / 2;
                int leftPointerY = arrayStartY + elementHeight + 10;
                
                g2d.setColor(LEFT_POINTER_COLOR);
                g2d.fillPolygon(new int[]{leftPointerX - 5, leftPointerX + 5, leftPointerX}, 
                               new int[]{leftPointerY, leftPointerY, leftPointerY + 10}, 3);
                g2d.drawString("左", leftPointerX - 5, leftPointerY + 25);
            }
            
            // 右指针
            if (right < nums.length && left != right) {
                int rightPointerX = startX + right * elementWidth + elementWidth / 2;
                int rightPointerY = arrayStartY + elementHeight + 10;
                
                g2d.setColor(RIGHT_POINTER_COLOR);
                g2d.fillPolygon(new int[]{rightPointerX - 5, rightPointerX + 5, rightPointerX}, 
                               new int[]{rightPointerY, rightPointerY, rightPointerY + 10}, 3);
                g2d.drawString("右", rightPointerX - 5, rightPointerY + 25);
            }
        }
        
        // 绘制当前操作
        if (!operationText.isEmpty()) {
            int operationY = arrayStartY + elementHeight + 80;
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            g2d.setColor(Color.BLUE);
            g2d.drawString(operationText, 50, operationY);
            
            // 绘制当前串联结果
            if (!currentConcat.isEmpty()) {
                g2d.setColor(CONCAT_COLOR);
                g2d.fillRect(50, operationY + 20, 200, 30);
                g2d.setColor(Color.WHITE);
                g2d.drawString("当前串联: " + currentConcat, 60, operationY + 40);
            }
        }
        
        // 绘制累计串联值
        int concatValueY = arrayStartY + elementHeight + 160;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(Color.BLACK);
        g2d.fillRect(50, concatValueY, 300, 50);
        g2d.setColor(Color.WHITE);
        g2d.drawString("累计串联值: " + concatValue, 60, concatValueY + 30);
        
        // 绘制算法步骤说明
        int stepY = concatValueY + 80;
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        g2d.setColor(Color.BLACK);
        
        g2d.drawString("算法步骤:", 50, stepY);
        g2d.drawString("1. 使用双指针分别指向数组首尾", 50, stepY + 20);
        g2d.drawString("2. 如果左指针 < 右指针，串联两个数字并累加", 50, stepY + 40);
        g2d.drawString("3. 如果左指针 = 右指针，直接累加中间数字", 50, stepY + 60);
        g2d.drawString("4. 移动指针直到处理完所有元素", 50, stepY + 80);
        
        // 绘制图例
        int legendY = stepY + 110;
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        
        g2d.setColor(ARRAY_COLOR);
        g2d.fillRect(50, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("未处理", 80, legendY + 15);
        
        g2d.setColor(LEFT_POINTER_COLOR);
        g2d.fillRect(140, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("左指针", 170, legendY + 15);
        
        g2d.setColor(RIGHT_POINTER_COLOR);
        g2d.fillRect(230, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("右指针", 260, legendY + 15);
        
        g2d.setColor(CONCAT_COLOR);
        g2d.fillRect(320, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("当前处理", 350, legendY + 15);
        
        g2d.setColor(PROCESSED_COLOR);
        g2d.fillRect(430, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("已处理", 460, legendY + 15);
    }
    
    private String getAlgorithmCode() {
        return "public long findTheArrayConcVal(int[] nums) {\n" +
               "    long concatValue = 0;\n" +
               "    int left = 0, right = nums.length - 1;\n" +
               "    \n" +
               "    while (left <= right) {\n" +
               "        if (left == right) {\n" +
               "            // 只有一个元素\n" +
               "            concatValue += nums[left];\n" +
               "        } else {\n" +
               "            // 串联两个元素\n" +
               "            String concat = String.valueOf(nums[left]) + \n" +
               "                           String.valueOf(nums[right]);\n" +
               "            concatValue += Long.parseLong(concat);\n" +
               "        }\n" +
               "        left++;\n" +
               "        right--;\n" +
               "    }\n" +
               "    \n" +
               "    return concatValue;\n" +
               "}\n\n" +
               "// 优化版本（避免字符串操作）\n" +
               "public long findTheArrayConcVal(int[] nums) {\n" +
               "    long concatValue = 0;\n" +
               "    int left = 0, right = nums.length - 1;\n" +
               "    \n" +
               "    while (left <= right) {\n" +
               "        if (left == right) {\n" +
               "            concatValue += nums[left];\n" +
               "        } else {\n" +
               "            // 计算右边数字的位数\n" +
               "            int rightDigits = String.valueOf(nums[right]).length();\n" +
               "            // 串联：左边数字 * 10^位数 + 右边数字\n" +
               "            concatValue += nums[left] * \n" +
               "                          Math.pow(10, rightDigits) + nums[right];\n" +
               "        }\n" +
               "        left++;\n" +
               "        right--;\n" +
               "    }\n" +
               "    \n" +
               "    return concatValue;\n" +
               "}";
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO2562_E_FindTheArrayConcVal_Animation().setVisible(true);
        });
    }
}