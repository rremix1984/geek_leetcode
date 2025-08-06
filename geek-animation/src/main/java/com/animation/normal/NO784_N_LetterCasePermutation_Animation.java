package com.animation.normal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * NO.784 字母大小写全排列算法动画演示
 * 
 * 算法描述：
 * 给定一个字符串s，通过将字符串s中的每个字母转变大小写，
 * 我们可以获得一个新的字符串。返回所有可能得到的字符串集合。
 * 
 * 算法思路：
 * 使用回溯算法，对每个字母字符有两种选择：保持原样或转换大小写
 * 1. 遍历字符串的每个字符
 * 2. 如果是字母，有两种选择：保持原样或转换大小写
 * 3. 如果是数字，只有一种选择：保持原样
 * 4. 递归处理下一个字符
 * 
 * 时间复杂度：O(2^n * n) 其中n是字母字符的个数
 * 空间复杂度：O(2^n * n)
 */
public class NO784_N_LetterCasePermutation_Animation extends JFrame {
    private JTextField inputField;
    private JButton generateButton;
    private JButton demoButton;
    private JButton clearButton;
    private JButton playButton;
    private JButton pauseButton;
    private JButton nextStepButton;
    private JSlider speedSlider;
    private JTextArea logArea;
    private PermutationPanel visualPanel;
    
    private String inputString;
    private List<String> result;
    private StringBuilder currentPath;
    private javax.swing.Timer animationTimer;
    private int animationStep;
    private boolean isPlaying;
    private int animationSpeed = 1000; // 毫秒
    
    // 动画状态记录
    private List<AnimationState> animationStates;
    private int currentStateIndex;
    
    // 动画状态类
    private static class AnimationState {
        String inputString;
        StringBuilder currentPath;
        int currentIndex;
        String action; // "choose_original", "choose_toggle", "backtrack", "found"
        char currentChar;
        String foundResult;
        
        public AnimationState(String inputString, StringBuilder path, int index, 
                            String action, char currentChar, String foundResult) {
            this.inputString = inputString;
            this.currentPath = new StringBuilder(path);
            this.currentIndex = index;
            this.action = action;
            this.currentChar = currentChar;
            this.foundResult = foundResult;
        }
    }
    
    public NO784_N_LetterCasePermutation_Animation() {
        initializeUI();
        result = new ArrayList<>();
        currentPath = new StringBuilder();
        animationStates = new ArrayList<>();
        currentStateIndex = 0;
        isPlaying = false;
    }
    
    private void initializeUI() {
        setTitle("NO.784 字母大小写全排列算法动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // 顶部控制面板
        JPanel topPanel = new JPanel(new BorderLayout());
        
        // 输入控制面板
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("输入字符串:"));
        inputField = new JTextField("a1b2", 15);
        inputPanel.add(inputField);
        
        generateButton = new JButton("生成排列");
        demoButton = new JButton("演示样例");
        clearButton = new JButton("清空");
        
        inputPanel.add(generateButton);
        inputPanel.add(demoButton);
        inputPanel.add(clearButton);
        
        // 动画控制面板
        JPanel animationPanel = new JPanel(new FlowLayout());
        playButton = new JButton("播放动画");
        pauseButton = new JButton("暂停");
        nextStepButton = new JButton("下一步");
        speedSlider = new JSlider(100, 2000, 1000);
        speedSlider.setMajorTickSpacing(500);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        
        animationPanel.add(new JLabel("动画控制:"));
        animationPanel.add(playButton);
        animationPanel.add(pauseButton);
        animationPanel.add(nextStepButton);
        animationPanel.add(new JLabel("速度:"));
        animationPanel.add(speedSlider);
        
        topPanel.add(inputPanel, BorderLayout.NORTH);
        topPanel.add(animationPanel, BorderLayout.SOUTH);
        
        // 可视化面板
        visualPanel = new PermutationPanel();
        visualPanel.setPreferredSize(new Dimension(800, 400));
        
        // 日志面板
        logArea = new JTextArea(10, 30);
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane logScrollPane = new JScrollPane(logArea);
        logScrollPane.setPreferredSize(new Dimension(300, 400));
        
        // 返回按钮
        JButton backButton = new JButton("返回首页");
        backButton.addActionListener(e -> {
            dispose();
            com.animation.launcher.AlgorithmTreeLauncher.showMainWindow();
        });
        
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(backButton);
        
        add(topPanel, BorderLayout.NORTH);
        add(visualPanel, BorderLayout.CENTER);
        add(logScrollPane, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
        
        // 事件监听器
        generateButton.addActionListener(e -> generatePermutations());
        demoButton.addActionListener(e -> {
            inputField.setText("a1b2");
            generatePermutations();
        });
        clearButton.addActionListener(e -> clearAll());
        playButton.addActionListener(e -> startAnimation());
        pauseButton.addActionListener(e -> pauseAnimation());
        nextStepButton.addActionListener(e -> nextStep());
        speedSlider.addChangeListener(e -> {
            animationSpeed = speedSlider.getValue();
            if (animationTimer != null) {
                animationTimer.setDelay(animationSpeed);
            }
        });
        
        setSize(1200, 600);
        setLocationRelativeTo(null);
    }
    
    private void generatePermutations() {
        try {
            inputString = inputField.getText().trim();
            if (inputString.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入有效的字符串！");
                return;
            }
            
            result.clear();
            animationStates.clear();
            currentStateIndex = 0;
            
            appendLog("开始生成字母大小写全排列...");
            appendLog("输入字符串: " + inputString);
            
            generateAnimationStates();
            
            appendLog("生成完成！共找到 " + result.size() + " 个排列");
            for (int i = 0; i < result.size(); i++) {
                appendLog((i + 1) + ". " + result.get(i));
            }
            
            visualPanel.updateVisualization(inputString, new StringBuilder(), 0, null);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "输入格式错误: " + ex.getMessage());
        }
    }
    
    private void generateAnimationStates() {
        currentPath = new StringBuilder();
        backtrackForStates(0);
    }
    
    private void backtrackForStates(int index) {
        if (index == inputString.length()) {
            String found = currentPath.toString();
            result.add(found);
            animationStates.add(new AnimationState(inputString, currentPath, index, "found", '\0', found));
            return;
        }
        
        char ch = inputString.charAt(index);
        
        if (Character.isLetter(ch)) {
            // 选择保持原样
            currentPath.append(ch);
            animationStates.add(new AnimationState(inputString, currentPath, index, "choose_original", ch, null));
            backtrackForStates(index + 1);
            currentPath.deleteCharAt(currentPath.length() - 1);
            
            // 选择转换大小写
            char toggledChar = Character.isLowerCase(ch) ? Character.toUpperCase(ch) : Character.toLowerCase(ch);
            currentPath.append(toggledChar);
            animationStates.add(new AnimationState(inputString, currentPath, index, "choose_toggle", toggledChar, null));
            backtrackForStates(index + 1);
            currentPath.deleteCharAt(currentPath.length() - 1);
        } else {
            // 数字字符，只有一种选择
            currentPath.append(ch);
            animationStates.add(new AnimationState(inputString, currentPath, index, "choose_original", ch, null));
            backtrackForStates(index + 1);
            currentPath.deleteCharAt(currentPath.length() - 1);
        }
    }
    
    private void startAnimation() {
        if (animationStates.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请先生成排列！");
            return;
        }
        
        isPlaying = true;
        currentStateIndex = 0;
        
        animationTimer = new javax.swing.Timer(animationSpeed, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentStateIndex < animationStates.size()) {
                    showCurrentState();
                    currentStateIndex++;
                } else {
                    pauseAnimation();
                }
            }
        });
        animationTimer.start();
    }
    
    private void pauseAnimation() {
        isPlaying = false;
        if (animationTimer != null) {
            animationTimer.stop();
        }
    }
    
    private void nextStep() {
        if (animationStates.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请先生成排列！");
            return;
        }
        
        if (currentStateIndex < animationStates.size()) {
            showCurrentState();
            currentStateIndex++;
        }
    }
    
    private void showCurrentState() {
        if (currentStateIndex >= animationStates.size()) return;
        
        AnimationState state = animationStates.get(currentStateIndex);
        visualPanel.updateVisualization(state);
        
        String logMessage = "步骤 " + (currentStateIndex + 1) + ": ";
        switch (state.action) {
            case "choose_original":
                logMessage += "选择字符 '" + state.currentChar + "' (保持原样)";
                break;
            case "choose_toggle":
                logMessage += "选择字符 '" + state.currentChar + "' (转换大小写)";
                break;
            case "found":
                logMessage += "找到一个排列: " + state.foundResult;
                break;
        }
        logMessage += " | 当前路径: " + state.currentPath.toString();
        
        appendLog(logMessage);
    }
    
    private void clearAll() {
        inputField.setText("");
        result.clear();
        animationStates.clear();
        currentStateIndex = 0;
        logArea.setText("");
        visualPanel.clear();
        pauseAnimation();
    }
    
    private void appendLog(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private class PermutationPanel extends JPanel {
        private AnimationState currentState;
        private String inputString;
        private StringBuilder currentPath;
        private int currentIndex;
        private String foundResult;
        
        public void updateVisualization(AnimationState state) {
            this.currentState = state;
            this.inputString = state.inputString;
            this.currentPath = state.currentPath;
            this.currentIndex = state.currentIndex;
            this.foundResult = state.foundResult;
            repaint();
        }
        
        public void updateVisualization(String inputString, StringBuilder path, int index, String result) {
            this.inputString = inputString;
            this.currentPath = path;
            this.currentIndex = index;
            this.foundResult = result;
            this.currentState = null;
            repaint();
        }
        
        public void clear() {
            this.currentState = null;
            this.inputString = null;
            this.currentPath = null;
            this.foundResult = null;
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (inputString == null) return;
            
            int width = getWidth();
            int height = getHeight();
            
            // 绘制标题
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.setColor(Color.BLACK);
            g2d.drawString("字母大小写全排列动画演示", 20, 30);
            
            // 绘制输入字符串
            g2d.setFont(new Font("Arial", Font.PLAIN, 14));
            g2d.drawString("输入字符串: " + inputString, 20, 60);
            
            // 绘制当前路径
            if (currentPath != null) {
                g2d.drawString("当前路径: " + currentPath.toString(), 20, 90);
            }
            
            // 绘制字符选择过程
            int startX = 50;
            int startY = 130;
            int charWidth = 60;
            int charHeight = 40;
            
            for (int i = 0; i < inputString.length(); i++) {
                char ch = inputString.charAt(i);
                int x = startX + i * (charWidth + 10);
                int y = startY;
                
                // 绘制原字符框
                if (i < currentIndex) {
                    g2d.setColor(Color.GREEN);
                } else if (i == currentIndex && currentState != null) {
                    g2d.setColor(Color.YELLOW);
                } else {
                    g2d.setColor(Color.LIGHT_GRAY);
                }
                g2d.fillRect(x, y, charWidth, charHeight);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y, charWidth, charHeight);
                
                // 绘制字符
                g2d.setFont(new Font("Arial", Font.BOLD, 18));
                FontMetrics fm = g2d.getFontMetrics();
                int textX = x + (charWidth - fm.stringWidth(String.valueOf(ch))) / 2;
                int textY = y + (charHeight + fm.getAscent()) / 2;
                g2d.drawString(String.valueOf(ch), textX, textY);
                
                // 如果是字母，绘制大小写选择
                if (Character.isLetter(ch)) {
                    char toggledChar = Character.isLowerCase(ch) ? Character.toUpperCase(ch) : Character.toLowerCase(ch);
                    
                    // 绘制转换后的字符框
                    int toggleY = y + charHeight + 20;
                    g2d.setColor(Color.CYAN);
                    g2d.fillRect(x, toggleY, charWidth, charHeight);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(x, toggleY, charWidth, charHeight);
                    
                    // 绘制转换后的字符
                    int toggleTextX = x + (charWidth - fm.stringWidth(String.valueOf(toggledChar))) / 2;
                    int toggleTextY = toggleY + (charHeight + fm.getAscent()) / 2;
                    g2d.drawString(String.valueOf(toggledChar), toggleTextX, toggleTextY);
                    
                    // 绘制选择箭头
                    if (i < currentIndex && currentPath != null && i < currentPath.length()) {
                        char chosenChar = currentPath.charAt(i);
                        if (chosenChar == ch) {
                            // 选择了原字符
                            g2d.setColor(Color.RED);
                            g2d.fillPolygon(new int[]{x + charWidth + 5, x + charWidth + 15, x + charWidth + 5}, 
                                          new int[]{y + 10, y + 20, y + 30}, 3);
                        } else {
                            // 选择了转换字符
                            g2d.setColor(Color.RED);
                            g2d.fillPolygon(new int[]{x + charWidth + 5, x + charWidth + 15, x + charWidth + 5}, 
                                          new int[]{toggleY + 10, toggleY + 20, toggleY + 30}, 3);
                        }
                    }
                }
            }
            
            // 绘制结果
            if (foundResult != null) {
                g2d.setFont(new Font("Arial", Font.BOLD, 16));
                g2d.setColor(Color.BLUE);
                g2d.drawString("找到排列: " + foundResult, 20, height - 50);
            }
            
            // 绘制所有结果
            if (result != null && !result.isEmpty()) {
                g2d.setFont(new Font("Arial", Font.PLAIN, 12));
                g2d.setColor(Color.BLACK);
                g2d.drawString("所有排列 (共" + result.size() + "个):", 20, height - 120);
                
                int resultY = height - 100;
                int resultX = 20;
                int maxPerLine = 8;
                
                for (int i = 0; i < result.size(); i++) {
                    if (i > 0 && i % maxPerLine == 0) {
                        resultY += 20;
                        resultX = 20;
                    }
                    
                    String resultStr = result.get(i);
                    if (foundResult != null && foundResult.equals(resultStr)) {
                        g2d.setColor(Color.RED);
                        g2d.setFont(new Font("Arial", Font.BOLD, 12));
                    } else {
                        g2d.setColor(Color.BLACK);
                        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
                    }
                    
                    g2d.drawString(resultStr, resultX, resultY);
                    resultX += g2d.getFontMetrics().stringWidth(resultStr) + 15;
                }
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO784_N_LetterCasePermutation_Animation().setVisible(true);
        });
    }
}