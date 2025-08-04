package com.leetcode.hard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * NO.123 买卖股票的最佳时机 III - 动画演示
 * 演示动态规划计算最大利润
 */
public class NO123_H_BestTimeToBuyAndSellStockIII_Animation extends JFrame {
    private int[] prices;
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JTextField priceField;
    private JButton addPriceButton, startButton, resetButton, stepButton;
    private JLabel resultLabel;
    private Timer animationTimer;
    private boolean isAnimating = false;
    private int[][] dp;
    private int currentDay = 0;
    private int currentTransaction = 0;
    private int maxProfit = 0;

    public NO123_H_BestTimeToBuyAndSellStockIII_Animation() {
        setTitle("NO.123 买卖股票的最佳时机 III - 动画演示");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);

        initComponents();
        setupLayout();
        setupEventHandlers();
        initializeDefaultData();
    }

    private void initComponents() {
        mainPanel = new JPanel();
        controlPanel = new JPanel();

        priceField = new JTextField(5);
        addPriceButton = new JButton("添加价格");
        startButton = new JButton("开始计算");
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
        mainPanel.add(new StockVisualizationPanel(), BorderLayout.CENTER);

        // 控制面板
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JLabel("股票价格:"));
        controlPanel.add(priceField);
        controlPanel.add(addPriceButton);
        controlPanel.add(startButton);
        controlPanel.add(stepButton);
        controlPanel.add(resetButton);
        controlPanel.add(resultLabel);

        add(mainPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        addPriceButton.addActionListener(e -> {
            if (!isAnimating) {
                addPrice();
            }
        });

        startButton.addActionListener(e -> {
            if (!isAnimating) {
                startAnimation();
            } else {
                stopAnimation();
            }
        });

        stepButton.addActionListener(e -> executeNextStep());

        resetButton.addActionListener(e -> reset());
    }

    private void addPrice() {
        try {
            int price = Integer.parseInt(priceField.getText());
            int[] newPrices = prices == null ? new int[]{price} : Arrays.copyOf(prices, prices.length + 1);
            newPrices[newPrices.length - 1] = price;
            prices = newPrices;

            updateDisplay();
            resultLabel.setText("结果: 添加价格 " + price);
            priceField.setText("");
        } catch (NumberFormatException ex) {
            resultLabel.setText("错误: 请输入有效数字");
        }
    }

    private void initializeDefaultData() {
        // 初始化默认数据 [3, 3, 5, 0, 0, 3, 1, 4]
        prices = new int[]{3, 3, 5, 0, 0, 3, 1, 4};
        updateDisplay();
    }

    private void startAnimation() {
        if (prices == null || prices.length == 0) {
            resultLabel.setText("错误: 请添加股票价格");
            return;
        }

        isAnimating = true;
        currentDay = 0;
        currentTransaction = 0;
        maxProfit = 0;
        dp = new int[5][prices.length]; // 5个状态：0操作，1买，1卖，2买，2卖

        startButton.setText("停止动画");
        stepButton.setEnabled(true);
        addPriceButton.setEnabled(false);

        animationTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!executeNextStep()) {
                    stopAnimation();
                    resultLabel.setText("计算完成! 最大利润: " + maxProfit);
                }
            }
        });
        animationTimer.start();

        resultLabel.setText("开始计算最大利润...");
        updateDisplay();
    }

    private boolean executeNextStep() {
        if (currentDay < prices.length) {
            // 第一天的初始化
            if (currentDay == 0) {
                dp[0][0] = 0;                // 没有操作
                dp[1][0] = -prices[0];       // 第一次买入
                dp[2][0] = 0;                // 第一次卖出（不可能，设为0）
                dp[3][0] = -prices[0];       // 第二次买入（不可能，但设为-prices[0]）
                dp[4][0] = 0;                // 第二次卖出（不可能，设为0）
            } else {
                // dp[0] = 没有操作
                dp[0][currentDay] = dp[0][currentDay - 1];
                
                // dp[1] = 第一次买入后持股
                dp[1][currentDay] = Math.max(dp[1][currentDay - 1], dp[0][currentDay - 1] - prices[currentDay]);
                
                // dp[2] = 第一次卖出后不持股  
                dp[2][currentDay] = Math.max(dp[2][currentDay - 1], dp[1][currentDay - 1] + prices[currentDay]);
                
                // dp[3] = 第二次买入后持股
                dp[3][currentDay] = Math.max(dp[3][currentDay - 1], dp[2][currentDay - 1] - prices[currentDay]);
                
                // dp[4] = 第二次卖出后不持股（最终结果）
                dp[4][currentDay] = Math.max(dp[4][currentDay - 1], dp[3][currentDay - 1] + prices[currentDay]);
            }
            
            maxProfit = Math.max(maxProfit, Math.max(dp[2][currentDay], dp[4][currentDay]));
            currentDay++;
        } else {
            return false; // 动画结束
        }
        updateDisplay();
        return true;
    }

    private void stopAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        isAnimating = false;
        startButton.setText("开始计算");
        stepButton.setEnabled(false);
        addPriceButton.setEnabled(true);
    }

    private void reset() {
        stopAnimation();
        prices = null;
        currentDay = 0;
        currentTransaction = 0;
        maxProfit = 0;
        dp = null;

        initializeDefaultData();
        resultLabel.setText("结果: 已重置");
    }

    private void updateDisplay() {
        repaint();
    }

    // 股票价格与利润可视化面板
    private class StockVisualizationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (prices == null) return;

            drawPrices(g2d);
            drawDP(g2d);
            drawAlgorithmInfo(g2d);
        }

        private void drawPrices(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            g2d.drawString("股票价格:", 50, 50);

            int startX = 50;
            int startY = 100;
            int barWidth = 40;

            for (int i = 0; i < prices.length; i++) {
                String priceStr = String.valueOf(prices[i]);
                g2d.setColor(i == currentDay ? Color.RED : Color.LIGHT_GRAY);
                g2d.fillRect(startX + i * (barWidth + 10), startY - prices[i] * 5, barWidth, prices[i] * 5);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(startX + i * (barWidth + 10), startY - prices[i] * 5, barWidth, prices[i] * 5);
                g2d.drawString(priceStr, startX + i * (barWidth + 10) + 10, startY + 20);
            }
        }

        private void drawDP(Graphics2D g2d) {
            if (dp == null || currentDay == 0) return;

            g2d.setColor(Color.BLACK);
            g2d.drawString("DP状态:", 50, 300);

            int startX = 50;
            int startY = 320;
            int gridWidth = 80;
            int gridHeight = 30;

            // 确保不超出数组范围
            int maxDay = Math.min(currentDay, prices.length - 1);

            // 显示状态标签
            String[] stateLabels = {"无操作", "1买入", "1卖出", "2买入", "2卖出"};
            
            for (int t = 0; t < Math.min(5, dp.length); t++) {
                // 绘制状态标签
                g2d.setColor(Color.BLACK);
                g2d.drawString(stateLabels[t], startX - 60, startY + (t + 1) * gridHeight + 20);
                
                for (int d = 0; d <= maxDay; d++) {
                    String dpStr = String.valueOf(dp[t][d]);
                    g2d.setColor(d == maxDay && currentDay >= prices.length ? Color.GREEN : 
                                (d == currentDay - 1 ? Color.RED : Color.LIGHT_GRAY));
                    g2d.fillRect(startX + d * gridWidth, startY + (t + 1) * gridHeight, gridWidth, gridHeight);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(startX + d * gridWidth, startY + (t + 1) * gridHeight, gridWidth, gridHeight);
                    g2d.drawString(dpStr, startX + d * gridWidth + 20, startY + (t + 1) * gridHeight + 20);
                }
            }
        }

        private void drawAlgorithmInfo(Graphics2D g2d) {
            int infoX = 600;
            int infoY = 50;

            g2d.setColor(Color.BLACK);
            g2d.drawString("算法步骤:", infoX, infoY);
            g2d.drawString("1. 初始化DP数组", infoX, infoY + 20);
            g2d.drawString("2. 计算每一天的利润", infoX, infoY + 40);
            g2d.drawString("3. 更新最大利润", infoX, infoY + 60);
            g2d.drawString("4. 完成所有天数计算", infoX, infoY + 80);

            if (isAnimating) {
                g2d.setColor(Color.BLUE);
                g2d.drawString("当前状态:", infoX, infoY + 120);
                g2d.drawString("当前天: " + currentDay, infoX, infoY + 140);
                g2d.drawString("当前最大利润: " + maxProfit, infoX, infoY + 160);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO123_H_BestTimeToBuyAndSellStockIII_Animation().setVisible(true);
        });
    }
}
