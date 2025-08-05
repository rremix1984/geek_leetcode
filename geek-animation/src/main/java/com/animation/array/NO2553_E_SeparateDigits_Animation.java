package com.animation.array;

import javax.swing.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.event.ActionEvent;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.event.ActionListener;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.util.ArrayList;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.util.Arrays;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.util.List;
import com.animation.launcher.AlgorithmTreeLauncher;

/**
 * NO.2553 分割数组中数字的数位 - 动画演示
 * 演示将数字分解为各个数位的过程
 */
public class NO2553_E_SeparateDigits_Animation extends JFrame {
    private List<Integer> inputNumbers;
    private List<Integer> resultDigits;
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JTextField inputField;
    private JButton addNumberButton, startButton, resetButton, stepButton;
    private JLabel resultLabel;
    private Timer animationTimer;
    private int currentNumberIndex = 0;
    private int currentDigitIndex = 0;
    private boolean isAnimating = false;
    private String currentNumber = "";
    private List<Integer> currentDigits;
    private DigitVisualizationPanel visualizationPanel; // 绘图面板引用
    
    public NO2553_E_SeparateDigits_Animation() {
        setTitle("NO.2553 分割数组中数字的数位 - 动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        inputNumbers = new ArrayList<>();
        resultDigits = new ArrayList<>();
        currentDigits = new ArrayList<>();
        initComponents();
        setupLayout();
        setupEventHandlers();
        initializeDefaultData();
    }
    
    private void initComponents() {
        mainPanel = new JPanel();
        controlPanel = new JPanel();
        
        inputField = new JTextField(10);
        addNumberButton = new JButton("添加数字");
        startButton = new JButton("开始分割");
        resetButton = new JButton("重置");
        stepButton = new JButton("单步执行");
        resultLabel = new JLabel("结果: ");
        
        // 设置字体
        Font font = new Font("微软雅黑", Font.PLAIN, 14);
        resultLabel.setFont(font);
        
        stepButton.setEnabled(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 主面板
        mainPanel.setLayout(new BorderLayout());
        visualizationPanel = new DigitVisualizationPanel();
        mainPanel.add(visualizationPanel, BorderLayout.CENTER);
        
        // 控制面板
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JLabel("输入数字:"));
        controlPanel.add(inputField);
        controlPanel.add(addNumberButton);
        controlPanel.add(startButton);
        controlPanel.add(stepButton);
        controlPanel.add(resetButton);
        controlPanel.add(resultLabel);
        
        add(mainPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        addNumberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAnimating) {
                    addNumber();
                }
            }
        });
        
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAnimating) {
                    startAnimation();
                } else {
                    stopAnimation();
                }
            }
        });
        
        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeNextStep();
            }
        });
        
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });

        // Add window listener to show main window on close
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                AlgorithmTreeLauncher.showMainWindow();
            }
        });
    }
    
    private void addNumber() {
        try {
            int number = Integer.parseInt(inputField.getText());
            if (number > 0) {
                inputNumbers.add(number);
                updateDisplay();
                resultLabel.setText("结果: 添加数字 " + number);
                inputField.setText("");
            } else {
                resultLabel.setText("错误: 请输入正整数");
            }
        } catch (NumberFormatException ex) {
            resultLabel.setText("错误: 请输入有效数字");
        }
    }
    
    private void initializeDefaultData() {
        // 初始化默认数据 [13, 25, 83, 77]
        int[] defaultNumbers = {13, 25, 83, 77};
        for (int number : defaultNumbers) {
            inputNumbers.add(number);
        }
        updateDisplay();
    }
    
    private void startAnimation() {
        if (inputNumbers.isEmpty()) {
            resultLabel.setText("错误: 请先添加数字");
            return;
        }
        
        isAnimating = true;
        currentNumberIndex = 0;
        currentDigitIndex = 0;
        resultDigits.clear();
        
        startButton.setText("停止动画");
        stepButton.setEnabled(true);
        addNumberButton.setEnabled(false);
        
        animationTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!executeNextStep()) {
                    stopAnimation();
                    resultLabel.setText("分割完成! 结果: " + resultDigits.toString());
                }
            }
        });
        animationTimer.start();
        
        resultLabel.setText("开始分割数字...");
        updateDisplay();
    }
    
    private boolean executeNextStep() {
        if (currentNumberIndex >= inputNumbers.size()) {
            return false; // 动画结束
        }
        
        int currentNum = inputNumbers.get(currentNumberIndex);
        
        if (currentDigitIndex == 0) {
            // 开始处理新数字
            currentNumber = String.valueOf(currentNum);
            currentDigits.clear();
            resultLabel.setText("正在分割数字: " + currentNum + " → [" + String.join(",", currentNumber.split("")) + "]");
        }
        
        if (currentDigitIndex < currentNumber.length()) {
            // 添加当前数位
            int digit = Character.getNumericValue(currentNumber.charAt(currentDigitIndex));
            currentDigits.add(digit);
            resultDigits.add(digit);
            currentDigitIndex++;
            
            updateDisplay();
            return true;
        } else {
            // 当前数字处理完毕，移到下一个数字
            currentNumberIndex++;
            currentDigitIndex = 0;
            currentDigits.clear();
            
            if (currentNumberIndex < inputNumbers.size()) {
                resultLabel.setText("数字 " + currentNum + " 分割完成，继续下一个...");
            }
            
            updateDisplay();
            return true;
        }
    }
    
    private void stopAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        isAnimating = false;
        startButton.setText("开始分割");
        stepButton.setEnabled(false);
        addNumberButton.setEnabled(true);
    }
    
    private void reset() {
        stopAnimation();
        inputNumbers.clear();
        resultDigits.clear();
        currentDigits.clear();
        currentNumberIndex = 0;
        currentDigitIndex = 0;
        currentNumber = "";
        
        initializeDefaultData();
        resultLabel.setText("结果: 已重置");
    }
    
    private void updateDisplay() {
        visualizationPanel.repaint();
    }
    
    // 数位分割可视化面板
    private class DigitVisualizationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            drawInputNumbers(g2d);
            drawSeparationProcess(g2d);
            drawResultDigits(g2d);
            drawAlgorithmInfo(g2d);
        }
        
        private void drawInputNumbers(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            g2d.drawString("输入数组:", 50, 50);
            
            int x = 50;
            int y = 70;
            int width = 80;
            int height = 40;
            
            for (int i = 0; i < inputNumbers.size(); i++) {
                int number = inputNumbers.get(i);
                Rectangle rect = new Rectangle(x + i * (width + 20), y, width, height);
                
                // 高亮当前正在处理的数字
                if (isAnimating && i == currentNumberIndex) {
                    g2d.setColor(Color.YELLOW);
                    g2d.fill(rect);
                } else if (isAnimating && i < currentNumberIndex) {
                    g2d.setColor(Color.LIGHT_GRAY);
                    g2d.fill(rect);
                } else {
                    g2d.setColor(Color.WHITE);
                    g2d.fill(rect);
                }
                
                g2d.setColor(Color.BLACK);
                g2d.draw(rect);
                
                // 绘制数字
                String numStr = String.valueOf(number);
                FontMetrics fm = g2d.getFontMetrics();
                int textX = x + i * (width + 20) + (width - fm.stringWidth(numStr)) / 2;
                int textY = y + (height + fm.getHeight()) / 2 - 2;
                g2d.drawString(numStr, textX, textY);
            }
        }
        
        private void drawSeparationProcess(Graphics2D g2d) {
            if (!isAnimating || currentNumberIndex >= inputNumbers.size()) return;
            
            g2d.setColor(Color.BLACK);
            g2d.drawString("分割过程:", 50, 180);
            
            int currentNum = inputNumbers.get(currentNumberIndex);
            String numStr = String.valueOf(currentNum);
            
            // 绘制原始数字
            g2d.drawString("数字 " + currentNum + " → ", 50, 210);
            
            // 绘制箭头
            g2d.drawString("→", 200, 210);
            
            // 绘制分割后的数位
            int startX = 250;
            int digitWidth = 30;
            int digitHeight = 30;
            
            for (int i = 0; i < numStr.length(); i++) {
                Rectangle rect = new Rectangle(startX + i * (digitWidth + 10), 190, digitWidth, digitHeight);
                
                if (i < currentDigitIndex) {
                    g2d.setColor(Color.GREEN);
                    g2d.fill(rect);
                } else if (i == currentDigitIndex && isAnimating) {
                    g2d.setColor(Color.ORANGE);
                    g2d.fill(rect);
                } else {
                    g2d.setColor(Color.LIGHT_GRAY);
                    g2d.fill(rect);
                }
                
                g2d.setColor(Color.BLACK);
                g2d.draw(rect);
                
                // 绘制数位
                String digit = String.valueOf(numStr.charAt(i));
                FontMetrics fm = g2d.getFontMetrics();
                int textX = startX + i * (digitWidth + 10) + (digitWidth - fm.stringWidth(digit)) / 2;
                int textY = 190 + (digitHeight + fm.getHeight()) / 2 - 2;
                g2d.drawString(digit, textX, textY);
            }
        }
        
        private void drawResultDigits(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            g2d.drawString("结果数组:", 50, 300);
            
            if (resultDigits.isEmpty()) {
                g2d.setColor(Color.GRAY);
                g2d.drawString("[]", 50, 330);
                return;
            }
            
            int startX = 50;
            int startY = 320;
            int digitWidth = 25;
            int digitHeight = 25;
            
            for (int i = 0; i < resultDigits.size(); i++) {
                Rectangle rect = new Rectangle(startX + i * (digitWidth + 5), startY, digitWidth, digitHeight);
                
                g2d.setColor(Color.CYAN);
                g2d.fill(rect);
                g2d.setColor(Color.BLACK);
                g2d.draw(rect);
                
                // 绘制数位
                String digit = String.valueOf(resultDigits.get(i));
                FontMetrics fm = g2d.getFontMetrics();
                int textX = startX + i * (digitWidth + 5) + (digitWidth - fm.stringWidth(digit)) / 2;
                int textY = startY + (digitHeight + fm.getHeight()) / 2 - 2;
                g2d.drawString(digit, textX, textY);
            }
            
            // 显示结果数组的字符串表示
            g2d.setColor(Color.BLUE);
            g2d.drawString("结果: " + resultDigits.toString(), 50, 370);
        }
        
        private void drawAlgorithmInfo(Graphics2D g2d) {
            int infoX = 50;
            int infoY = getHeight() - 200;
            
            g2d.setColor(Color.BLACK);
            g2d.drawString("算法步骤:", infoX, infoY);
            g2d.drawString("1. 遍历输入数组中的每个数字", infoX, infoY + 20);
            g2d.drawString("2. 将数字转换为字符串", infoX, infoY + 40);
            g2d.drawString("3. 逐个提取每个字符（数位）", infoX, infoY + 60);
            g2d.drawString("4. 将数位转换为整数并添加到结果数组", infoX, infoY + 80);
            g2d.drawString("5. 重复步骤1-4，直到处理完所有数字", infoX, infoY + 100);
            
            // 显示当前状态
            if (isAnimating) {
                g2d.setColor(Color.BLUE);
                g2d.drawString("当前处理:", infoX + 400, infoY);
                g2d.drawString("数字索引: " + currentNumberIndex + "/" + inputNumbers.size(), infoX + 400, infoY + 20);
                if (currentNumberIndex < inputNumbers.size()) {
                    g2d.drawString("当前数字: " + inputNumbers.get(currentNumberIndex), infoX + 400, infoY + 40);
                    g2d.drawString("数位进度: " + currentDigitIndex + "/" + currentNumber.length(), infoX + 400, infoY + 60);
                }
                g2d.drawString("已生成数位: " + resultDigits.size(), infoX + 400, infoY + 80);
            }
            
            // 示例说明
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawString("示例: 13 → [1,3], 25 → [2,5], 83 → [8,3], 77 → [7,7]", infoX + 50, infoY + 130);
            g2d.drawString("最终结果: [1,3,2,5,8,3,7,7]", infoX + 50, infoY + 150);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new NO2553_E_SeparateDigits_Animation().setVisible(true);
            }
        });
    }
}
