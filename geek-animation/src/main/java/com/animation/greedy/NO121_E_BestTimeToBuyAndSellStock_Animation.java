package com.animation.greedy;

import javax.swing.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 121. 买卖股票的最佳时机 - 动画演示
 * 
 * 算法思路：
 * 一次遍历，记录历史最低价格，在每一天计算如果今天卖出能获得的最大利润
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class NO121_E_BestTimeToBuyAndSellStock_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final int CHART_WIDTH = 800;
    private static final int CHART_HEIGHT = 300;
    
    // 组件
    private JPanel animationPanel;
    private JButton startButton;
    private JButton stepButton;
    private JButton resetButton;
    private JButton homeButton;
    private JLabel statusLabel;
    private JLabel complexityLabel;
    
    // 动画状态
    private int[] prices;
    private int currentDay;
    private int minPrice;
    private int maxProfit;
    private int bestBuyDay;
    private int bestSellDay;
    private boolean isAnimating;
    private Timer animationTimer;
    
    // 动画步骤记录
    private List<String> stepDescriptions;
    
    public NO121_E_BestTimeToBuyAndSellStock_Animation() {
        initComponents();
        setupLayout();
        setupEventHandlers();
        resetAnimation();
    }
    
    private void initComponents() {
        setTitle("LeetCode 121. 买卖股票的最佳时机 - 动画演示");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        animationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawAnimation(g);
            }
        };
        animationPanel.setBackground(Color.WHITE);
        animationPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, 500));
        
        startButton = new JButton("开始动画");
        stepButton = new JButton("单步执行");
        resetButton = new JButton("重置");
        homeButton = new JButton("返回主页");
        
        statusLabel = new JLabel("准备开始演示买卖股票的最佳时机算法");
        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        
        complexityLabel = new JLabel("时间复杂度: O(n) | 空间复杂度: O(1)");
        complexityLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        complexityLabel.setForeground(Color.BLUE);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 顶部信息面板
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("LeetCode 121. 买卖股票的最佳时机", JLabel.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(complexityLabel, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);
        
        // 中央动画面板
        add(animationPanel, BorderLayout.CENTER);
        
        // 底部控制面板
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(startButton);
        controlPanel.add(stepButton);
        controlPanel.add(resetButton);
        controlPanel.add(homeButton);
        
        bottomPanel.add(controlPanel, BorderLayout.CENTER);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        startButton.addActionListener(e -> startAnimation());
        stepButton.addActionListener(e -> stepAnimation());
        resetButton.addActionListener(e -> resetAnimation());
        homeButton.addActionListener(e -> {
            if (animationTimer != null && animationTimer.isRunning()) {
                animationTimer.stop();
            }
            dispose();
            SwingUtilities.invokeLater(() -> AlgorithmTreeLauncher.showMainWindow());
        });
    }
    
    private void startAnimation() {
        if (isAnimating) {
            animationTimer.stop();
            isAnimating = false;
            startButton.setText("开始动画");
            return;
        }
        
        isAnimating = true;
        startButton.setText("暂停动画");
        
        animationTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!stepAnimation()) {
                    animationTimer.stop();
                    isAnimating = false;
                    startButton.setText("开始动画");
                }
            }
        });
        animationTimer.start();
    }
    
    private boolean stepAnimation() {
        if (currentDay >= prices.length) {
            statusLabel.setText("算法执行完成！最大利润为: " + maxProfit + 
                " (第" + (bestBuyDay + 1) + "天买入，第" + (bestSellDay + 1) + "天卖出)");
            return false;
        }
        
        // 执行算法逻辑
        if (prices[currentDay] < minPrice) {
            minPrice = prices[currentDay];
        } else {
            int profit = prices[currentDay] - minPrice;
            if (profit > maxProfit) {
                maxProfit = profit;
                // 找到最佳买入日
                for (int i = 0; i < currentDay; i++) {
                    if (prices[i] == minPrice) {
                        bestBuyDay = i;
                        break;
                    }
                }
                bestSellDay = currentDay;
            }
        }
        
        statusLabel.setText(stepDescriptions.get(currentDay));
        currentDay++;
        SwingUtilities.invokeLater(() -> animationPanel.repaint());
        
        return currentDay <= prices.length;
    }
    
    private void resetAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        
        isAnimating = false;
        startButton.setText("开始动画");
        
        // 初始化股票价格数组 [7,1,5,3,6,4]
        prices = new int[]{7, 1, 5, 3, 6, 4};
        currentDay = 0;
        minPrice = Integer.MAX_VALUE;
        maxProfit = 0;
        bestBuyDay = -1;
        bestSellDay = -1;
        
        // 生成步骤描述
        generateStepDescriptions();
        
        statusLabel.setText("准备开始演示买卖股票的最佳时机算法");
        SwingUtilities.invokeLater(() -> animationPanel.repaint());
    }
    
    private void generateStepDescriptions() {
        stepDescriptions = new ArrayList<>();
        
        int tempMinPrice = Integer.MAX_VALUE;
        int tempMaxProfit = 0;
        
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < tempMinPrice) {
                tempMinPrice = prices[i];
                stepDescriptions.add("第" + (i + 1) + "天: 价格" + prices[i] + 
                    "，更新最低价格为" + tempMinPrice);
            } else {
                int profit = prices[i] - tempMinPrice;
                if (profit > tempMaxProfit) {
                    tempMaxProfit = profit;
                    stepDescriptions.add("第" + (i + 1) + "天: 价格" + prices[i] + 
                        "，如果今天卖出利润为" + profit + "，更新最大利润");
                } else {
                    stepDescriptions.add("第" + (i + 1) + "天: 价格" + prices[i] + 
                        "，如果今天卖出利润为" + profit + "，不更新最大利润");
                }
            }
        }
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int startX = 50;
        int chartY = 100;
        
        // 绘制标题和说明
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(Color.BLACK);
        g2d.drawString("股票价格: [7, 1, 5, 3, 6, 4]", startX, 50);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        g2d.drawString("算法：一次遍历，记录最低价格，计算每天卖出的最大利润", startX, 75);
        
        // 绘制价格图表
        drawPriceChart(g2d, startX, chartY);
        
        // 绘制当前状态信息
        drawCurrentState(g2d, startX, chartY + CHART_HEIGHT + 50);
    }
    
    private void drawPriceChart(Graphics2D g2d, int startX, int startY) {
        int cellWidth = CHART_WIDTH / prices.length;
        int maxPrice = 7; // 已知最大价格
        
        // 绘制坐标轴
        g2d.setColor(Color.BLACK);
        g2d.drawLine(startX, startY + CHART_HEIGHT, startX + CHART_WIDTH, startY + CHART_HEIGHT); // X轴
        g2d.drawLine(startX, startY, startX, startY + CHART_HEIGHT); // Y轴
        
        // 绘制价格柱状图
        for (int i = 0; i < prices.length; i++) {
            int x = startX + i * cellWidth + 10;
            int height = (prices[i] * CHART_HEIGHT) / maxPrice;
            int y = startY + CHART_HEIGHT - height;
            
            // 确定颜色
            Color barColor = Color.LIGHT_GRAY;
            if (i < currentDay) {
                if (bestBuyDay != -1 && bestSellDay != -1 && i == bestBuyDay) {
                    barColor = Color.GREEN; // 最佳买入日
                } else if (bestBuyDay != -1 && bestSellDay != -1 && i == bestSellDay) {
                    barColor = Color.RED; // 最佳卖出日
                } else {
                    barColor = Color.CYAN; // 已处理的日期
                }
            } else if (i == currentDay) {
                barColor = Color.YELLOW; // 当前处理的日期
            }
            
            // 绘制柱状图
            g2d.setColor(barColor);
            g2d.fillRect(x, y, cellWidth - 20, height);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, cellWidth - 20, height);
            
            // 绘制价格标签
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            String priceStr = String.valueOf(prices[i]);
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (cellWidth - 20 - fm.stringWidth(priceStr)) / 2;
            g2d.drawString(priceStr, textX, y - 5);
            
            // 绘制日期标签
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            String dayStr = "第" + (i + 1) + "天";
            int dayX = x + (cellWidth - 20 - g2d.getFontMetrics().stringWidth(dayStr)) / 2;
            g2d.drawString(dayStr, dayX, startY + CHART_HEIGHT + 15);
        }
        
        // 绘制最佳买卖连线
        if (bestBuyDay != -1 && bestSellDay != -1 && currentDay > bestSellDay) {
            g2d.setColor(Color.MAGENTA);
            g2d.setStroke(new BasicStroke(3));
            
            int buyX = startX + bestBuyDay * cellWidth + cellWidth / 2;
            int buyY = startY + CHART_HEIGHT - (prices[bestBuyDay] * CHART_HEIGHT) / maxPrice;
            int sellX = startX + bestSellDay * cellWidth + cellWidth / 2;
            int sellY = startY + CHART_HEIGHT - (prices[bestSellDay] * CHART_HEIGHT) / maxPrice;
            
            g2d.drawLine(buyX, buyY, sellX, sellY);
            g2d.setStroke(new BasicStroke(1));
        }
    }
    
    private void drawCurrentState(Graphics2D g2d, int startX, int startY) {
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.BLACK);
        
        if (currentDay > 0) {
            g2d.drawString("当前最低价格: " + (minPrice == Integer.MAX_VALUE ? "未设置" : minPrice), 
                startX, startY);
            g2d.drawString("当前最大利润: " + maxProfit, startX, startY + 25);
            
            if (bestBuyDay != -1 && bestSellDay != -1) {
                g2d.drawString("最佳买入日: 第" + (bestBuyDay + 1) + "天 (价格: " + prices[bestBuyDay] + ")", 
                    startX, startY + 50);
                g2d.drawString("最佳卖出日: 第" + (bestSellDay + 1) + "天 (价格: " + prices[bestSellDay] + ")", 
                    startX, startY + 75);
            }
        }
        
        // 绘制颜色说明
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        int legendX = startX + 400;
        int legendY = startY;
        
        g2d.setColor(Color.GREEN);
        g2d.fillRect(legendX, legendY, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("最佳买入日", legendX + 20, legendY + 12);
        
        g2d.setColor(Color.RED);
        g2d.fillRect(legendX, legendY + 25, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("最佳卖出日", legendX + 20, legendY + 37);
        
        g2d.setColor(Color.YELLOW);
        g2d.fillRect(legendX, legendY + 50, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("当前处理日", legendX + 20, legendY + 62);
        
        g2d.setColor(Color.CYAN);
        g2d.fillRect(legendX, legendY + 75, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("已处理日期", legendX + 20, legendY + 87);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO121_E_BestTimeToBuyAndSellStock_Animation().setVisible(true);
        });
    }
}