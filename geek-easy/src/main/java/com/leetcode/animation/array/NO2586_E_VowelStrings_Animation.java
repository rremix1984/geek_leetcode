package com.leetcode.animation.array;

import javax.swing.*;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.awt.*;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.awt.event.ActionEvent;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.awt.event.ActionListener;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.util.HashSet;
import com.leetcode.animation.AlgorithmTreeLauncher;
import java.util.Set;
import com.leetcode.animation.AlgorithmTreeLauncher;

/**
 * NO.2586 统计范围内的元音字符串数 - 动画演示
 * 
 * 算法描述：
 * 给你一个下标从0开始的字符串数组words以及一个整数left和一个整数right。
 * 如果字符串以元音字母开头并以元音字母结尾，那么该字符串就是一个元音字符串，其中元音字母是'a'、'e'、'i'、'o'、'u'。
 * 返回words[left..right]中元音字符串的数目。
 * 
 * 算法思路：
 * 1. 定义元音字母集合
 * 2. 遍历指定范围内的字符串
 * 3. 检查每个字符串的首尾字符是否都是元音字母
 * 4. 统计满足条件的字符串数量
 * 
 * 时间复杂度：O(right - left + 1)
 * 空间复杂度：O(1)
 */
public class NO2586_E_VowelStrings_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final Color ARRAY_COLOR = new Color(33, 150, 243);       // 蓝色
    private static final Color CURRENT_COLOR = new Color(255, 193, 7);      // 黄色
    private static final Color VOWEL_COLOR = new Color(76, 175, 80);        // 绿色
    private static final Color NON_VOWEL_COLOR = new Color(244, 67, 54);    // 红色
    private static final Color RANGE_COLOR = new Color(156, 39, 176);       // 紫色
    private static final Color COUNT_COLOR = new Color(255, 152, 0);        // 橙色
    
    private JPanel animationPanel;
    private JButton startButton, pauseButton, resetButton, nextStepButton;
    private JLabel statusLabel, complexityLabel;
    private JTextArea codeArea;
    private Timer animationTimer;
    
    // 算法相关变量
    private String[] words;
    private int left, right;
    private int currentIndex;
    private int vowelCount;
    private boolean isRunning;
    private boolean isPaused;
    private boolean algorithmComplete;
    private String operationText;
    private Set<Character> vowels;
    private boolean currentStringIsVowel;
    
    // 测试用例
    private final TestCase[] testCases = {
        new TestCase(new String[]{"are", "amy", "u"}, 0, 2),
        new TestCase(new String[]{"hey", "aeo", "mu", "ooo", "artro"}, 1, 4),
        new TestCase(new String[]{"a", "e", "i", "o", "u"}, 0, 4),
        new TestCase(new String[]{"hello", "world", "apple", "orange"}, 0, 3),
        new TestCase(new String[]{"programming", "algorithm", "awesome", "excellent"}, 1, 3)
    };
    private int currentTestCase = 0;
    
    private static class TestCase {
        String[] words;
        int left, right;
        
        TestCase(String[] words, int left, int right) {
            this.words = words;
            this.left = left;
            this.right = right;
        }
        
        @Override
        public String toString() {
            return String.format("words=%s, left=%d, right=%d", 
                java.util.Arrays.toString(words), left, right);
        }
    }
    
    public NO2586_E_VowelStrings_Animation() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        resetAnimation();
    }
    
    private void initializeComponents() {
        setTitle("NO.2586 统计范围内的元音字符串数 - 动画演示");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // 初始化元音字母集合
        vowels = new HashSet<>();
        vowels.add('a');
        vowels.add('e');
        vowels.add('i');
        vowels.add('o');
        vowels.add('u');
        
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
        complexityLabel = new JLabel("<html><b>时间复杂度:</b> O(right-left+1) <b>空间复杂度:</b> O(1)</html>");
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
                    com.leetcode.animation.AlgorithmTreeLauncher.showMainWindow();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });
        bottomPanel.add(homeButton);
        
        // 测试用例选择
        JComboBox<String> testCaseCombo = new JComboBox<>();
        testCaseCombo.addItem("测试用例1: [\"are\",\"amy\",\"u\"], left=0, right=2");
        testCaseCombo.addItem("测试用例2: [\"hey\",\"aeo\",\"mu\",\"ooo\",\"artro\"], left=1, right=4");
        testCaseCombo.addItem("测试用例3: [\"a\",\"e\",\"i\",\"o\",\"u\"], left=0, right=4");
        testCaseCombo.addItem("测试用例4: [\"hello\",\"world\",\"apple\",\"orange\"], left=0, right=3");
        testCaseCombo.addItem("测试用例5: [\"programming\",\"algorithm\",\"awesome\",\"excellent\"], left=1, right=3");
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
        currentIndex = -1;
        vowelCount = 0;
        algorithmComplete = false;
        operationText = "";
        currentStringIsVowel = false;
        
        TestCase testCase = testCases[currentTestCase];
        words = testCase.words.clone();
        left = testCase.left;
        right = testCase.right;
        
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        nextStepButton.setEnabled(true);
        
        statusLabel.setText("准备开始动画演示");
        animationPanel.repaint();
    }
    
    private void nextAnimationStep() {
        if (algorithmComplete) return;
        
        if (currentIndex < left) {
            currentIndex = left;
        } else {
            currentIndex++;
        }
        
        if (currentIndex > right) {
            algorithmComplete = true;
            statusLabel.setText(String.format("算法完成！范围[%d,%d]内的元音字符串数量: %d", 
                left, right, vowelCount));
            
            animationTimer.stop();
            isRunning = false;
            startButton.setEnabled(true);
            pauseButton.setEnabled(false);
            nextStepButton.setEnabled(false);
            animationPanel.repaint();
            return;
        }
        
        // 检查当前字符串是否为元音字符串
        String currentWord = words[currentIndex];
        char firstChar = currentWord.charAt(0);
        char lastChar = currentWord.charAt(currentWord.length() - 1);
        
        boolean isFirstVowel = vowels.contains(firstChar);
        boolean isLastVowel = vowels.contains(lastChar);
        currentStringIsVowel = isFirstVowel && isLastVowel;
        
        if (currentStringIsVowel) {
            vowelCount++;
        }
        
        operationText = String.format("检查 \"%s\": 首字符'%c'(%s), 尾字符'%c'(%s) -> %s", 
            currentWord, firstChar, isFirstVowel ? "元音" : "非元音", 
            lastChar, isLastVowel ? "元音" : "非元音",
            currentStringIsVowel ? "是元音字符串" : "不是元音字符串");
        
        statusLabel.setText(operationText);
        animationPanel.repaint();
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int panelWidth = animationPanel.getWidth();
        int panelHeight = animationPanel.getHeight();
        
        if (words == null || words.length == 0) return;
        
        // 绘制标题
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(Color.BLACK);
        String title = String.format("字符串数组: %s, 范围: [%d, %d]", 
            java.util.Arrays.toString(words), left, right);
        FontMetrics fm = g2d.getFontMetrics();
        int titleWidth = fm.stringWidth(title);
        g2d.drawString(title, (panelWidth - titleWidth) / 2, 30);
        
        // 绘制元音字母说明
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.BLUE);
        String vowelInfo = "元音字母: a, e, i, o, u";
        g2d.drawString(vowelInfo, 50, 60);
        
        // 计算字符串元素的绘制位置
        int arrayStartY = 90;
        int elementWidth = Math.min(120, (panelWidth - 100) / words.length);
        int elementHeight = 60;
        int startX = (panelWidth - words.length * elementWidth) / 2;
        
        // 绘制字符串数组
        for (int i = 0; i < words.length; i++) {
            int x = startX + i * elementWidth;
            int y = arrayStartY;
            
            // 确定颜色
            Color elementColor;
            if (i == currentIndex) {
                elementColor = CURRENT_COLOR;  // 当前检查的字符串
            } else if (i >= left && i <= right) {
                if (i < currentIndex) {
                    // 已检查的范围内字符串，根据是否为元音字符串着色
                    String word = words[i];
                    char first = word.charAt(0);
                    char last = word.charAt(word.length() - 1);
                    boolean isVowelString = vowels.contains(first) && vowels.contains(last);
                    elementColor = isVowelString ? VOWEL_COLOR : NON_VOWEL_COLOR;
                } else {
                    elementColor = RANGE_COLOR;  // 范围内未检查的字符串
                }
            } else {
                elementColor = Color.LIGHT_GRAY;  // 范围外的字符串
            }
            
            // 绘制元素矩形
            g2d.setColor(elementColor);
            g2d.fillRect(x, y, elementWidth - 2, elementHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, elementWidth - 2, elementHeight);
            
            // 绘制字符串值
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 12));
            String value = words[i];
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
        
        // 绘制范围指示器
        if (left < words.length && right < words.length) {
            int rangeStartX = startX + left * elementWidth;
            int rangeEndX = startX + (right + 1) * elementWidth - 2;
            int rangeY = arrayStartY + elementHeight + 10;
            
            g2d.setColor(RANGE_COLOR);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(rangeStartX, rangeY, rangeEndX, rangeY);
            g2d.drawLine(rangeStartX, rangeY - 5, rangeStartX, rangeY + 5);
            g2d.drawLine(rangeEndX, rangeY - 5, rangeEndX, rangeY + 5);
            
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 12));
            String rangeText = String.format("范围 [%d, %d]", left, right);
            FontMetrics rangeFm = g2d.getFontMetrics();
            int rangeTextWidth = rangeFm.stringWidth(rangeText);
            g2d.drawString(rangeText, (rangeStartX + rangeEndX - rangeTextWidth) / 2, rangeY + 20);
        }
        
        // 绘制当前指针
        if (currentIndex >= 0 && currentIndex < words.length) {
            int pointerX = startX + currentIndex * elementWidth + elementWidth / 2;
            int pointerY = arrayStartY + elementHeight + 50;
            
            g2d.setColor(CURRENT_COLOR);
            g2d.fillPolygon(new int[]{pointerX - 5, pointerX + 5, pointerX}, 
                           new int[]{pointerY, pointerY, pointerY + 10}, 3);
            g2d.drawString("当前", pointerX - 10, pointerY + 25);
        }
        
        // 绘制计数器
        int counterY = arrayStartY + elementHeight + 100;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(COUNT_COLOR);
        g2d.drawString(String.format("元音字符串计数: %d", vowelCount), 50, counterY);
        
        // 绘制当前检查的详细信息
        if (!operationText.isEmpty()) {
            int detailY = counterY + 40;
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            g2d.setColor(Color.BLUE);
            g2d.drawString(operationText, 50, detailY);
            
            // 绘制字符分析
            if (currentIndex >= 0 && currentIndex < words.length) {
                String currentWord = words[currentIndex];
                if (currentWord.length() > 0) {
                    int analysisY = detailY + 30;
                    g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                    g2d.setColor(Color.BLACK);
                    
                    char firstChar = currentWord.charAt(0);
                    char lastChar = currentWord.charAt(currentWord.length() - 1);
                    boolean isFirstVowel = vowels.contains(firstChar);
                    boolean isLastVowel = vowels.contains(lastChar);
                    
                    g2d.drawString(String.format("字符串: \"%s\"", currentWord), 50, analysisY);
                    g2d.drawString(String.format("首字符: '%c' - %s", firstChar, 
                        isFirstVowel ? "是元音" : "不是元音"), 50, analysisY + 20);
                    g2d.drawString(String.format("尾字符: '%c' - %s", lastChar, 
                        isLastVowel ? "是元音" : "不是元音"), 50, analysisY + 40);
                    g2d.drawString(String.format("结果: %s", 
                        currentStringIsVowel ? "是元音字符串 (+1)" : "不是元音字符串"), 50, analysisY + 60);
                }
            }
        }
        
        // 绘制算法步骤说明
        int stepY = counterY + 180;
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        g2d.setColor(Color.BLACK);
        
        g2d.drawString("算法步骤:", 50, stepY);
        g2d.drawString("1. 定义元音字母集合 {a, e, i, o, u}", 50, stepY + 20);
        g2d.drawString("2. 遍历指定范围 [left, right] 内的字符串", 50, stepY + 40);
        g2d.drawString("3. 检查每个字符串的首尾字符是否都是元音", 50, stepY + 60);
        g2d.drawString("4. 统计满足条件的字符串数量", 50, stepY + 80);
        
        // 绘制图例
        int legendY = stepY + 110;
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        
        g2d.setColor(RANGE_COLOR);
        g2d.fillRect(50, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("范围内", 80, legendY + 15);
        
        g2d.setColor(CURRENT_COLOR);
        g2d.fillRect(150, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("当前检查", 180, legendY + 15);
        
        g2d.setColor(VOWEL_COLOR);
        g2d.fillRect(270, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("元音字符串", 300, legendY + 15);
        
        g2d.setColor(NON_VOWEL_COLOR);
        g2d.fillRect(400, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("非元音字符串", 430, legendY + 15);
        
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(530, legendY, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("范围外", 560, legendY + 15);
    }
    
    private String getAlgorithmCode() {
        return "public int vowelStrings(String[] words, int left, int right) {\n" +
               "    // 定义元音字母集合\n" +
               "    Set<Character> vowels = new HashSet<>();\n" +
               "    vowels.add('a');\n" +
               "    vowels.add('e');\n" +
               "    vowels.add('i');\n" +
               "    vowels.add('o');\n" +
               "    vowels.add('u');\n" +
               "    \n" +
               "    int count = 0;\n" +
               "    \n" +
               "    // 遍历指定范围内的字符串\n" +
               "    for (int i = left; i <= right; i++) {\n" +
               "        String word = words[i];\n" +
               "        \n" +
               "        // 检查首尾字符是否都是元音\n" +
               "        char first = word.charAt(0);\n" +
               "        char last = word.charAt(word.length() - 1);\n" +
               "        \n" +
               "        if (vowels.contains(first) && vowels.contains(last)) {\n" +
               "            count++;\n" +
               "        }\n" +
               "    }\n" +
               "    \n" +
               "    return count;\n" +
               "}\n\n" +
               "// 优化版本：使用字符比较\n" +
               "public int vowelStrings(String[] words, int left, int right) {\n" +
               "    int count = 0;\n" +
               "    \n" +
               "    for (int i = left; i <= right; i++) {\n" +
               "        String word = words[i];\n" +
               "        char first = word.charAt(0);\n" +
               "        char last = word.charAt(word.length() - 1);\n" +
               "        \n" +
               "        if (isVowel(first) && isVowel(last)) {\n" +
               "            count++;\n" +
               "        }\n" +
               "    }\n" +
               "    \n" +
               "    return count;\n" +
               "}\n" +
               "\n" +
               "private boolean isVowel(char c) {\n" +
               "    return c == 'a' || c == 'e' || c == 'i' || \n" +
               "           c == 'o' || c == 'u';\n" +
               "}";
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO2586_E_VowelStrings_Animation().setVisible(true);
        });
    }
}