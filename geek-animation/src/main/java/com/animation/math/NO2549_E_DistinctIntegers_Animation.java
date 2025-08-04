package com.animation.math;

import javax.swing.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.event.ActionEvent;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.event.ActionListener;
import com.animation.launcher.AlgorithmTreeLauncher;

/**
 * NO.2549 统计桌面上的不同数字 - 动画演示
 * 
 * 算法描述：
 * 给你一个正整数n，开始时，它放在桌面上。在每一秒中，对于桌面上的每个数字，你需要执行以下操作：
 * - 如果存在至少一个其他数字严格小于它，则将其减1。
 * - 否则，不对此数字执行任何操作。
 * 一旦数字变为0，它就会从桌面上消失。
 * 返回在桌面上永远存在的不同整数的数目。
 * 
 * 算法思路：
 * 通过观察规律可以发现：
 * - 最终桌面上会剩下1到n的所有数字
 * - 因为每次操作都是同时进行的，较大的数字会逐渐减小
 * - 最终会形成1,2,3,...,n的序列
 * 
 * 时间复杂度：O(1)
 * 空间复杂度：O(1)
 */
public class NO2549_E_DistinctIntegers_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final Color NUMBER_COLOR = new Color(33, 150, 243);      // 蓝色
    private static final Color REDUCING_COLOR = new Color(255, 152, 0);     // 橙色
    private static final Color STABLE_COLOR = new Color(76, 175, 80);       // 绿色
    private static final Color DISAPPEAR_COLOR = new Color(158, 158, 158);  // 灰色
    
    private JPanel animationPanel;
    private JButton startButton, pauseButton, resetButton, nextStepButton;
    private JLabel statusLabel, complexityLabel;
    private JTextArea codeArea;
    private Timer animationTimer;
    
    // 算法相关变量
    private int n;
    private java.util.List<Integer> numbers;
    private int currentStep;
    private boolean isRunning;
    private boolean isPaused;
    private boolean algorithmComplete;
    private String operationText;
    
    // 测试用例
    private final int[] testCases = {3, 4, 5, 6, 7, 700};
    private int currentTestCase = 0;
    
    public NO2549_E_DistinctIntegers_Animation() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        resetAnimation();
    }
    
    private void initializeComponents() {
        setTitle("NO.2549 统计桌面上的不同数字 - 动画演示");
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
        complexityLabel = new JLabel("<html><b>时间复杂度:</b> O(1) <b>空间复杂度:</b> O(1)</html>");
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
        testCaseCombo.addItem("n = 3");
        testCaseCombo.addItem("n = 4");
        testCaseCombo.addItem("n = 5");
        testCaseCombo.addItem("n = 6");
        testCaseCombo.addItem("n = 7");
        testCaseCombo.addItem("n = 700");
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
        currentStep = 0;
        algorithmComplete = false;
        operationText = "";
        
        n = testCases[currentTestCase];
        numbers = new java.util.ArrayList<>();
        numbers.add(n);  // 初始只有一个数字n
        
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        nextStepButton.setEnabled(true);
        
        statusLabel.setText("准备开始动画演示");
        animationPanel.repaint();
    }
    
    private void nextAnimationStep() {
        if (algorithmComplete) return;
        
        currentStep++;
        
        // 模拟每一秒的操作
        java.util.List<Integer> newNumbers = new java.util.ArrayList<>();
        boolean hasChanges = false;
        
        for (int num : numbers) {
            // 检查是否存在其他数字严格小于当前数字
            boolean hasSmaller = false;
            for (int other : numbers) {
                if (other < num) {
                    hasSmaller = true;
                    break;
                }
            }
            
            if (hasSmaller) {
                int newNum = num - 1;
                if (newNum > 0) {
                    newNumbers.add(newNum);
                    hasChanges = true;
                }
            } else {
                newNumbers.add(num);
            }
        }
        
        // 去重并排序
        java.util.Set<Integer> uniqueNumbers = new java.util.TreeSet<>(newNumbers);
        numbers = new java.util.ArrayList<>(uniqueNumbers);
        
        operationText = String.format("第 %d 秒后: %s", currentStep, numbers.toString());
        statusLabel.setText(operationText);
        
        // 检查是否达到稳定状态
        if (!hasChanges || isStableState()) {
            algorithmComplete = true;
            int result = numbers.size();
            statusLabel.setText(String.format("算法完成！桌面上永远存在 %d 个不同数字: %s", 
                result, numbers.toString()));
            
            animationTimer.stop();
            isRunning = false;
            startButton.setEnabled(true);
            pauseButton.setEnabled(false);
            nextStepButton.setEnabled(false);
        }
        
        animationPanel.repaint();
    }
    
    private boolean isStableState() {
        // 检查是否达到1,2,3,...,k的稳定状态
        if (numbers.isEmpty()) return true;
        
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i) != i + 1) {
                return false;
            }
        }
        return true;
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int panelWidth = animationPanel.getWidth();
        int panelHeight = animationPanel.getHeight();
        
        // 绘制标题
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 18));
        g2d.setColor(Color.BLACK);
        String title = String.format("桌面数字模拟 (n = %d)", n);
        FontMetrics fm = g2d.getFontMetrics();
        int titleWidth = fm.stringWidth(title);
        g2d.drawString(title, (panelWidth - titleWidth) / 2, 30);
        
        // 绘制当前步骤
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        String stepText = String.format("当前步骤: %d", currentStep);
        g2d.drawString(stepText, 50, 60);
        
        // 绘制桌面上的数字
        if (numbers != null && !numbers.isEmpty()) {
            int numbersStartY = 100;
            int numberSize = 60;
            int spacing = 20;
            int totalWidth = numbers.size() * numberSize + (numbers.size() - 1) * spacing;
            int startX = (panelWidth - totalWidth) / 2;
            
            for (int i = 0; i < numbers.size(); i++) {
                int x = startX + i * (numberSize + spacing);
                int y = numbersStartY;
                int num = numbers.get(i);
                
                // 确定颜色
                Color numberColor;
                if (algorithmComplete) {
                    numberColor = STABLE_COLOR;
                } else if (currentStep == 0) {
                    numberColor = NUMBER_COLOR;
                } else {
                    // 检查这个数字是否会在下一步减少
                    boolean willReduce = false;
                    for (int other : numbers) {
                        if (other < num) {
                            willReduce = true;
                            break;
                        }
                    }
                    numberColor = willReduce ? REDUCING_COLOR : STABLE_COLOR;
                }
                
                // 绘制数字圆圈
                g2d.setColor(numberColor);
                g2d.fillOval(x, y, numberSize, numberSize);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(x, y, numberSize, numberSize);
                
                // 绘制数字
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 20));
                String numStr = String.valueOf(num);
                FontMetrics numFm = g2d.getFontMetrics();
                int numWidth = numFm.stringWidth(numStr);
                int numHeight = numFm.getHeight();
                g2d.setColor(Color.WHITE);
                g2d.drawString(numStr, x + (numberSize - numWidth) / 2, 
                              y + (numberSize + numHeight) / 2 - 3);
            }
        }
        
        // 绘制操作说明
        int rulesY = 220;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("操作规则:", 50, rulesY);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        g2d.drawString("• 如果存在其他数字严格小于当前数字，则将当前数字减1", 50, rulesY + 25);
        g2d.drawString("• 否则，不对当前数字执行任何操作", 50, rulesY + 45);
        g2d.drawString("• 数字变为0时会从桌面消失", 50, rulesY + 65);
        g2d.drawString("• 所有操作同时进行", 50, rulesY + 85);
        
        // 绘制当前操作文本
        if (!operationText.isEmpty()) {
            int operationY = rulesY + 120;
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            g2d.setColor(Color.BLUE);
            g2d.drawString(operationText, 50, operationY);
        }
        
        // 绘制算法分析
        int analysisY = rulesY + 160;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("算法分析:", 50, analysisY);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        g2d.drawString("• 观察规律：最终桌面上会剩下1到n的所有数字", 50, analysisY + 25);
        g2d.drawString("• 因此答案总是 n（当n=1时为1，当n>1时为n）", 50, analysisY + 45);
        g2d.drawString("• 可以直接返回n，无需模拟整个过程", 50, analysisY + 65);
        
        // 绘制最终结果
        if (algorithmComplete) {
            int resultY = analysisY + 100;
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            g2d.setColor(STABLE_COLOR);
            g2d.fillRect(50, resultY, 400, 40);
            g2d.setColor(Color.WHITE);
            g2d.drawString(String.format("最终结果: %d 个不同数字", numbers.size()), 60, resultY + 25);
        }
        
        // 绘制图例
        int legendY = panelHeight - 80;
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        
        g2d.setColor(NUMBER_COLOR);
        g2d.fillOval(50, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("初始数字", 80, legendY + 15);
        
        g2d.setColor(REDUCING_COLOR);
        g2d.fillOval(160, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("将要减少", 190, legendY + 15);
        
        g2d.setColor(STABLE_COLOR);
        g2d.fillOval(270, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("稳定数字", 300, legendY + 15);
    }
    
    private String getAlgorithmCode() {
        return "public int distinctIntegers(int n) {\n" +
               "    // 直接返回结果，无需模拟\n" +
               "    return n == 1 ? 1 : n;\n" +
               "}\n\n" +
               "// 模拟过程的解法（用于理解）\n" +
               "public int distinctIntegers(int n) {\n" +
               "    Set<Integer> numbers = new HashSet<>();\n" +
               "    numbers.add(n);\n" +
               "    \n" +
               "    boolean changed = true;\n" +
               "    while (changed) {\n" +
               "        changed = false;\n" +
               "        Set<Integer> newNumbers = new HashSet<>();\n" +
               "        \n" +
               "        for (int num : numbers) {\n" +
               "            boolean hasSmaller = false;\n" +
               "            for (int other : numbers) {\n" +
               "                if (other < num) {\n" +
               "                    hasSmaller = true;\n" +
               "                    break;\n" +
               "                }\n" +
               "            }\n" +
               "            \n" +
               "            if (hasSmaller) {\n" +
               "                int newNum = num - 1;\n" +
               "                if (newNum > 0) {\n" +
               "                    newNumbers.add(newNum);\n" +
               "                    if (!numbers.contains(newNum)) {\n" +
               "                        changed = true;\n" +
               "                    }\n" +
               "                }\n" +
               "            } else {\n" +
               "                newNumbers.add(num);\n" +
               "            }\n" +
               "        }\n" +
               "        \n" +
               "        numbers = newNumbers;\n" +
               "    }\n" +
               "    \n" +
               "    return numbers.size();\n" +
               "}";
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO2549_E_DistinctIntegers_Animation().setVisible(true);
        });
    }
}