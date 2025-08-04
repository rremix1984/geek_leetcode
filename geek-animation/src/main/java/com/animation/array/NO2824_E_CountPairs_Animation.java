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
import java.util.List;
import com.animation.launcher.AlgorithmTreeLauncher;

/**
 * NO.2824 统计和小于目标的下标对数目 - 动画演示
 * 演示双重循环查找满足条件的下标对的过程
 */
public class NO2824_E_CountPairs_Animation extends JFrame {
    private List<Integer> nums;
    private int target;
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JTextField numField, targetField;
    private JButton addNumButton, setTargetButton, startButton, resetButton, stepButton;
    private JLabel resultLabel, targetLabel;
    private Timer animationTimer;
    private int currentI = 0;
    private int currentJ = 1;
    private int pairCount = 0;
    private boolean isAnimating = false;
    private List<int[]> validPairs;
    private boolean foundCurrentPair = false;
    
    public NO2824_E_CountPairs_Animation() {
        setTitle("NO.2824 统计和小于目标的下标对数目 - 动画演示");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        nums = new ArrayList<>();
        validPairs = new ArrayList<>();
        target = 2;
        initComponents();
        setupLayout();
        setupEventHandlers();
        initializeDefaultData();
    }
    
    private void initComponents() {
        mainPanel = new JPanel();
        controlPanel = new JPanel();
        
        numField = new JTextField(5);
        targetField = new JTextField("2", 5);
        addNumButton = new JButton("添加数字");
        setTargetButton = new JButton("设置目标");
        startButton = new JButton("开始计数");
        resetButton = new JButton("重置");
        stepButton = new JButton("单步执行");
        resultLabel = new JLabel("结果: ");
        targetLabel = new JLabel("目标值: " + target);
        
        // 设置字体
        Font font = new Font("微软雅黑", Font.PLAIN, 14);
        resultLabel.setFont(font);
        targetLabel.setFont(font);
        
        stepButton.setEnabled(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 主面板
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new PairVisualizationPanel(), BorderLayout.CENTER);
        
        // 控制面板
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JLabel("数字:"));
        controlPanel.add(numField);
        // 返回首页按钮
        JButton homeButton = new JButton("返回首页");
        homeButton.addActionListener(e -> {
            dispose(); // 关闭当前窗口
            // 启动主界面
            SwingUtilities.invokeLater(() -> {
                 try {
                     dispose(); // 关闭当前动画窗口
                     com.animation.launcher.AlgorithmTreeLauncher.showMainWindow();
                 } catch (Exception ex) {
                     ex.printStackTrace();
                 }
             });
        });

        controlPanel.add(addNumButton);
        controlPanel.add(new JLabel("目标值:"));
        controlPanel.add(targetField);
        controlPanel.add(setTargetButton);
        controlPanel.add(startButton);
        controlPanel.add(stepButton);
        controlPanel.add(resetButton);
        controlPanel.add(homeButton);
        controlPanel.add(targetLabel);
        controlPanel.add(resultLabel);
        
        add(mainPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        addNumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAnimating) {
                    addNumber();
                }
            }
        });
        
        setTargetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAnimating) {
                    setTarget();
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
    }
    
    private void addNumber() {
        try {
            int num = Integer.parseInt(numField.getText());
            nums.add(num);
            updateDisplay();
            resultLabel.setText("结果: 添加数字 " + num);
            numField.setText("");
        } catch (NumberFormatException ex) {
            resultLabel.setText("错误: 请输入有效数字");
        }
    }
    
    private void setTarget() {
        try {
            int newTarget = Integer.parseInt(targetField.getText());
            target = newTarget;
            targetLabel.setText("目标值: " + target);
            updateDisplay();
            resultLabel.setText("结果: 设置目标为 " + target);
        } catch (NumberFormatException ex) {
            resultLabel.setText("错误: 请输入有效数字");
        }
    }
    
    private void initializeDefaultData() {
        // 初始化默认数据 [-1, 1, 2, 3, 1], target = 2
        int[] defaultNums = {-1, 1, 2, 3, 1};
        for (int num : defaultNums) {
            nums.add(num);
        }
        updateDisplay();
    }
    
    private void startAnimation() {
        if (nums.size() < 2) {
            resultLabel.setText("错误: 至少需要2个数字");
            return;
        }
        
        isAnimating = true;
        currentI = 0;
        currentJ = 1;
        pairCount = 0;
        validPairs.clear();
        foundCurrentPair = false;
        
        startButton.setText("停止动画");
        stepButton.setEnabled(true);
        addNumButton.setEnabled(false);
        setTargetButton.setEnabled(false);
        
        animationTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!executeNextStep()) {
                    stopAnimation();
                    resultLabel.setText("计数完成! 满足条件的下标对数目: " + pairCount);
                }
            }
        });
        animationTimer.start();
        
        resultLabel.setText("开始查找满足条件的下标对...");
        updateDisplay();
    }
    
    private boolean executeNextStep() {
        if (currentI >= nums.size() - 1) {
            return false; // 动画结束
        }
        
        if (currentJ >= nums.size()) {
            // 移动到下一个i
            currentI++;
            if (currentI >= nums.size() - 1) {
                return false; // 动画结束
            }
            currentJ = currentI + 1;
        }
        
        // 检查当前对是否满足条件
        int sum = nums.get(currentI) + nums.get(currentJ);
        foundCurrentPair = (sum < target);
        
        if (foundCurrentPair) {
            pairCount++;
            validPairs.add(new int[]{currentI, currentJ});
            resultLabel.setText("找到有效对 (" + currentI + "," + currentJ + "): " + 
                              nums.get(currentI) + " + " + nums.get(currentJ) + " = " + sum + " < " + target);
        } else {
            resultLabel.setText("检查对 (" + currentI + "," + currentJ + "): " + 
                              nums.get(currentI) + " + " + nums.get(currentJ) + " = " + sum + " >= " + target);
        }
        
        currentJ++;
        updateDisplay();
        return true;
    }
    
    private void stopAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        isAnimating = false;
        startButton.setText("开始计数");
        stepButton.setEnabled(false);
        addNumButton.setEnabled(true);
        setTargetButton.setEnabled(true);
    }
    
    private void reset() {
        stopAnimation();
        nums.clear();
        validPairs.clear();
        currentI = 0;
        currentJ = 1;
        pairCount = 0;
        foundCurrentPair = false;
        target = 2;
        targetField.setText("2");
        targetLabel.setText("目标值: " + target);
        
        initializeDefaultData();
        resultLabel.setText("结果: 已重置");
    }
    
    private void updateDisplay() {
        repaint();
    }
    
    // 下标对可视化面板
    private class PairVisualizationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            drawArray(g2d);
            drawCurrentPair(g2d);
            drawPairMatrix(g2d);
            drawValidPairs(g2d);
            drawAlgorithmInfo(g2d);
        }
        
        private void drawArray(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            g2d.drawString("数组:", 50, 50);
            
            int x = 50;
            int y = 70;
            int width = 60;
            int height = 40;
            
            for (int i = 0; i < nums.size(); i++) {
                int num = nums.get(i);
                Rectangle rect = new Rectangle(x + i * (width + 10), y, width, height);
                
                // 高亮当前正在比较的元素
                if (isAnimating && (i == currentI || i == currentJ)) {
                    if (i == currentI) {
                        g2d.setColor(Color.YELLOW);
                    } else {
                        g2d.setColor(Color.ORANGE);
                    }
                    g2d.fill(rect);
                } else {
                    g2d.setColor(Color.LIGHT_GRAY);
                    g2d.fill(rect);
                }
                
                g2d.setColor(Color.BLACK);
                g2d.draw(rect);
                
                // 绘制数字
                String numStr = String.valueOf(num);
                FontMetrics fm = g2d.getFontMetrics();
                int textX = x + i * (width + 10) + (width - fm.stringWidth(numStr)) / 2;
                int textY = y + (height + fm.getHeight()) / 2 - 2;
                g2d.drawString(numStr, textX, textY);
                
                // 绘制下标
                g2d.setColor(Color.BLUE);
                g2d.drawString(String.valueOf(i), x + i * (width + 10) + width/2 - 5, y + height + 15);
            }
            
            // 绘制当前指针
            if (isAnimating && currentI < nums.size()) {
                g2d.setColor(Color.RED);
                g2d.drawString("i=" + currentI, x + currentI * (width + 10), y - 10);
                if (currentJ < nums.size()) {
                    g2d.drawString("j=" + currentJ, x + currentJ * (width + 10), y - 10);
                }
            }
        }
        
        private void drawCurrentPair(Graphics2D g2d) {
            if (!isAnimating || currentI >= nums.size() || currentJ >= nums.size()) return;
            
            g2d.setColor(Color.BLACK);
            g2d.drawString("当前检查的对:", 50, 180);
            
            int sum = nums.get(currentI) + nums.get(currentJ);
            String pairInfo = "(" + currentI + "," + currentJ + "): " + 
                             nums.get(currentI) + " + " + nums.get(currentJ) + " = " + sum;
            
            g2d.setColor(foundCurrentPair ? Color.GREEN : Color.RED);
            g2d.drawString(pairInfo, 50, 200);
            
            if (foundCurrentPair) {
                g2d.drawString("✓ " + sum + " < " + target + " (满足条件)", 50, 220);
            } else {
                g2d.drawString("✗ " + sum + " >= " + target + " (不满足条件)", 50, 220);
            }
        }
        
        private void drawPairMatrix(Graphics2D g2d) {
            if (nums.isEmpty()) return;
            
            g2d.setColor(Color.BLACK);
            g2d.drawString("对比矩阵 (i < j):", 400, 50);
            
            int startX = 400;
            int startY = 70;
            int cellSize = 30;
            
            // 绘制表头
            for (int j = 0; j < nums.size(); j++) {
                g2d.drawString("j=" + j, startX + (j + 1) * cellSize + 5, startY - 5);
            }
            
            for (int i = 0; i < nums.size(); i++) {
                g2d.drawString("i=" + i, startX - 25, startY + i * cellSize + 20);
                
                for (int j = 0; j < nums.size(); j++) {
                    Rectangle cell = new Rectangle(startX + (j + 1) * cellSize, startY + i * cellSize, cellSize, cellSize);
                    
                    if (i < j) {
                        // 计算和
                        int sum = nums.get(i) + nums.get(j);
                        boolean isValid = sum < target;
                        
                        // 高亮当前检查的对
                        if (isAnimating && i == currentI && j == currentJ) {
                            g2d.setColor(foundCurrentPair ? Color.GREEN : Color.RED);
                            g2d.fill(cell);
                        } else if (isValidPair(i, j)) {
                            g2d.setColor(Color.GREEN);
                            g2d.fill(cell);
                        } else if (isValid) {
                            g2d.setColor(Color.CYAN);
                            g2d.fill(cell);
                        } else {
                            g2d.setColor(Color.PINK);
                            g2d.fill(cell);
                        }
                        
                        g2d.setColor(Color.BLACK);
                        g2d.draw(cell);
                        
                        // 绘制和
                        String sumStr = String.valueOf(sum);
                        FontMetrics fm = g2d.getFontMetrics();
                        int textX = startX + (j + 1) * cellSize + (cellSize - fm.stringWidth(sumStr)) / 2;
                        int textY = startY + i * cellSize + (cellSize + fm.getHeight()) / 2 - 2;
                        g2d.drawString(sumStr, textX, textY);
                    } else {
                        // 无效区域（i >= j）
                        g2d.setColor(Color.GRAY);
                        g2d.fill(cell);
                        g2d.setColor(Color.BLACK);
                        g2d.draw(cell);
                    }
                }
            }
            
            // 绘制图例
            int legendY = startY + nums.size() * cellSize + 30;
            g2d.setColor(Color.GREEN);
            g2d.fillRect(startX, legendY, 15, 15);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(startX, legendY, 15, 15);
            g2d.drawString("已找到的有效对", startX + 20, legendY + 12);
            
            g2d.setColor(Color.CYAN);
            g2d.fillRect(startX + 150, legendY, 15, 15);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(startX + 150, legendY, 15, 15);
            g2d.drawString("有效对 (和 < " + target + ")", startX + 170, legendY + 12);
            
            g2d.setColor(Color.PINK);
            g2d.fillRect(startX + 320, legendY, 15, 15);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(startX + 320, legendY, 15, 15);
            g2d.drawString("无效对 (和 >= " + target + ")", startX + 340, legendY + 12);
        }
        
        private boolean isValidPair(int i, int j) {
            for (int[] pair : validPairs) {
                if (pair[0] == i && pair[1] == j) {
                    return true;
                }
            }
            return false;
        }
        
        private void drawValidPairs(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            g2d.drawString("找到的有效下标对:", 50, 320);
            
            if (validPairs.isEmpty()) {
                g2d.setColor(Color.GRAY);
                g2d.drawString("暂无", 50, 340);
                return;
            }
            
            int y = 340;
            for (int k = 0; k < validPairs.size(); k++) {
                int[] pair = validPairs.get(k);
                int i = pair[0];
                int j = pair[1];
                int sum = nums.get(i) + nums.get(j);
                
                g2d.setColor(Color.BLUE);
                String pairStr = "(" + i + "," + j + "): " + nums.get(i) + " + " + nums.get(j) + " = " + sum + " < " + target;
                g2d.drawString(pairStr, 50, y + k * 20);
            }
            
            g2d.setColor(Color.RED);
            g2d.drawString("总计: " + validPairs.size() + " 个有效对", 50, y + validPairs.size() * 20 + 20);
        }
        
        private void drawAlgorithmInfo(Graphics2D g2d) {
            int infoX = 50;
            int infoY = getHeight() - 150;
            
            g2d.setColor(Color.BLACK);
            g2d.drawString("算法步骤:", infoX, infoY);
            g2d.drawString("1. 使用双重循环遍历所有可能的下标对 (i,j)，其中 i < j", infoX, infoY + 20);
            g2d.drawString("2. 计算 nums[i] + nums[j]", infoX, infoY + 40);
            g2d.drawString("3. 如果和小于目标值，计数器加1", infoX, infoY + 60);
            g2d.drawString("4. 继续到下一个下标对", infoX, infoY + 80);
            g2d.drawString("5. 返回满足条件的下标对总数", infoX, infoY + 100);
            
            // 显示当前状态
            if (isAnimating) {
                g2d.setColor(Color.BLUE);
                g2d.drawString("当前状态:", infoX + 400, infoY);
                g2d.drawString("外层循环 i: " + currentI + "/" + (nums.size() - 1), infoX + 400, infoY + 20);
                g2d.drawString("内层循环 j: " + currentJ + "/" + nums.size(), infoX + 400, infoY + 40);
                g2d.drawString("已找到对数: " + pairCount, infoX + 400, infoY + 60);
                
                if (currentI < nums.size() && currentJ < nums.size()) {
                    int sum = nums.get(currentI) + nums.get(currentJ);
                    g2d.drawString("当前和: " + sum, infoX + 400, infoY + 80);
                    g2d.drawString("目标: < " + target, infoX + 400, infoY + 100);
                }
            }
            
            // 示例说明
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawString("示例: nums=[-1,1,2,3,1], target=2", infoX + 50, infoY + 130);
            g2d.drawString("满足条件的对: (0,1), (0,2), (0,4) → 结果: 3", infoX + 50, infoY + 150);
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
                new NO2824_E_CountPairs_Animation().setVisible(true);
            }
        });
    }
}
