package com.leetcode.hard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * NO.188 买卖股票的最佳时机 IV - 动画演示
 * 演示动态规划计算最大利润
 */
public class NO188_H_BestTimeToBuyAndSellStockIV_Animation extends JFrame {
    private int[] prices;
    private int maxTransactions = 2;
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JTextField priceField, transactionField;
    private JButton addPriceButton, setTransactionButton, startButton, resetButton, stepButton;
    private JLabel resultLabel;
    private Timer animationTimer;
    private boolean isAnimating = false;
    private int[][] dp;
    private int currentDay = 0;
    private int maxProfit = 0;

    public NO188_H_BestTimeToBuyAndSellStockIV_Animation() {
        setTitle("NO.188 买卖股票的最佳时机 IV - 动画演示");
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
        transactionField = new JTextField(String.valueOf(maxTransactions), 5);
        addPriceButton = new JButton("添加价格");
        setTransactionButton = new JButton("设置交易次数");
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
        controlPanel.add(new JLabel("最大交易次数:"));
        controlPanel.add(transactionField);
        controlPanel.add(setTransactionButton);
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

        setTransactionButton.addActionListener(e -> {
            if (!isAnimating) {
                setTransactions();
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

    private void setTransactions() {
        try {
            int transactions = Integer.parseInt(transactionField.getText());
            if (transactions > 0) {
                maxTransactions = transactions;
                resultLabel.setText("结果: 设置最大交易次数为 " + maxTransactions);
            } else {
                resultLabel.setText("错误: 交易次数必须为正数");
            }
        } catch (NumberFormatException ex) {
            resultLabel.setText("错误: 请输入有效数字");
        }
    }

    private void initializeDefaultData() {
        // 初始化默认数据 [2, 4, 1]
        prices = new int[]{2, 4, 1};
        updateDisplay();
    }

    private void startAnimation() {
        if (prices == null || prices.length == 0) {
            resultLabel.setText("错误: 请添加股票价格");
            return;
        }

        isAnimating = true;
        currentDay = 0;
        maxProfit = 0;
        dp = new int[maxTransactions + 1][prices.length];

        startButton.setText("停止动画");
        stepButton.setEnabled(true);
        addPriceButton.setEnabled(false);
        setTransactionButton.setEnabled(false);

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
            if (currentDay == 0) {
                // 第一天初始化
                for (int t = 1; t <= maxTransactions; t++) {
                    dp[t][0] = -prices[0]; // 第一天买入
                }
            } else {
                // 从第二天开始的状态转移
                for (int t = 1; t <= maxTransactions; t++) {
                    // 不持股状态：要么保持不持股，要么今天卖出
                    dp[t][currentDay] = Math.max(
                        dp[t][currentDay - 1], 
                        (t > 1 ? dp[t - 1][currentDay - 1] : 0) + prices[currentDay]
                    );
                    
                    // 持股状态：要么保持持股，要么今天买入
                    int buyState = Math.max(
                        (t <= maxTransactions && currentDay > 0) ? dp[t][currentDay - 1] : -prices[currentDay],
                        (t > 1 ? dp[t - 1][currentDay - 1] : 0) - prices[currentDay]
                    );
                    
                    dp[t][currentDay] = Math.max(dp[t][currentDay], buyState);
                }
            }
            
            maxProfit = Math.max(maxProfit, dp[maxTransactions][currentDay]);
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
        setTransactionButton.setEnabled(true);
    }

    private void reset() {
        stopAnimation();
        prices = null;
        currentDay = 0;
        maxProfit = 0;
        dp = null;
        maxTransactions = 2;

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

            for (int t = 1; t <= maxTransactions; t++) {
                // 绘制交易数标签
                g2d.setColor(Color.BLACK);
                g2d.drawString("T" + t, startX - 30, startY + t * gridHeight + 20);
                
                for (int d = 0; d <= maxDay; d++) {
                    String dpStr = String.valueOf(dp[t][d]);
                    g2d.setColor(d == maxDay && currentDay >= prices.length ? Color.GREEN : 
                                (d == currentDay - 1 ? Color.RED : Color.LIGHT_GRAY));
                    g2d.fillRect(startX + d * gridWidth, startY + t * gridHeight, gridWidth, gridHeight);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(startX + d * gridWidth, startY + t * gridHeight, gridWidth, gridHeight);
                    g2d.drawString(dpStr, startX + d * gridWidth + 20, startY + t * gridHeight + 20);
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
            new NO188_H_BestTimeToBuyAndSellStockIV_Animation().setVisible(true);
        });
    }
}
