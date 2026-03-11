package com.leetcode.hard.animation;

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
            resultLabel.setText("错误: 请先输入价格数据");
            return;
        }
        isAnimating = true;
        startButton.setText("停止计算");
        stepButton.setEnabled(true);
        currentDay = 0;
        currentTransaction = 0;

        dp = new int[3][prices.length]; // 最多两次交易
        animationTimer = new Timer(800, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeNextStep();
            }
        });
        animationTimer.start();
    }

    private void stopAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        isAnimating = false;
        startButton.setText("开始计算");
        stepButton.setEnabled(false);
    }

    private void reset() {
        stopAnimation();
        prices = null;
        dp = null;
        currentDay = 0;
        currentTransaction = 0;
        maxProfit = 0;
        resultLabel.setText("结果: 已重置");
        updateDisplay();
    }

    private void executeNextStep() {
        if (!isAnimating || prices == null) return;

        // 简化的动态规划演示：两次交易
        if (currentTransaction < 2 && currentDay < prices.length) {
            int k = currentTransaction + 1;
            if (currentDay == 0) {
                dp[k][currentDay] = 0;
            } else {
                int noTrade = dp[k][currentDay - 1];
                int trade = Math.max(0, prices[currentDay] - prices[currentDay - 1]) + dp[k - 1][currentDay - 1];
                dp[k][currentDay] = Math.max(noTrade, trade);
            }
            maxProfit = Math.max(maxProfit, dp[k][currentDay]);
            currentDay++;
        } else if (currentTransaction < 2) {
            currentTransaction++;
            currentDay = 0;
        } else {
            stopAnimation();
        }

        resultLabel.setText("结果: 最大利润 = " + maxProfit);
        repaint();
    }

    private void updateDisplay() {
        // 触发重绘
        if (mainPanel != null) {
            mainPanel.repaint();
        }
    }

    private class StockVisualizationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (prices == null) return;

            int startX = 50;
            int startY = 350;
            int barWidth = 40;

            // 绘制价格曲线
            g2d.setColor(Color.BLACK);
            g2d.drawString("价格:", startX, 50);
            for (int i = 0; i < prices.length - 1; i++) {
                int x1 = startX + i * (barWidth + 5);
                int x2 = startX + (i + 1) * (barWidth + 5);
                int y1 = startY - prices[i] * 10;
                int y2 = startY - prices[i + 1] * 10;
                g2d.drawLine(x1, y1, x2, y2);
            }

            // 当前状态显示
            g2d.setColor(Color.BLUE);
            g2d.drawString("当前交易次数: " + currentTransaction, 600, 50);
            g2d.drawString("当前天数索引: " + currentDay, 600, 70);
            g2d.drawString("最大利润: " + maxProfit, 600, 90);
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
