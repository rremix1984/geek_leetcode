package com.animation.normal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * NO.322 零钱兑换算法动画演示
 * 
 * 算法描述：
 * 给你一个整数数组 coins，表示不同面额的硬币；以及一个整数 amount，表示总金额。
 * 计算并返回可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。
 * 
 * 算法思路：
 * 使用动态规划解决
 * 1. 定义 dp[i] 表示凑成金额 i 所需的最少硬币数
 * 2. 状态转移方程：dp[i] = min(dp[i], dp[i - coin] + 1)
 * 3. 初始化：dp[0] = 0，其他为无穷大
 * 
 * 时间复杂度：O(amount * coins.length)
 * 空间复杂度：O(amount)
 */
public class NO322_N_CoinChange_Animation extends JFrame {
    private JTextField coinsField;
    private JTextField amountField;
    private JButton calculateButton;
    private JButton demoButton;
    private JButton clearButton;
    private JTextArea logArea;
    private CoinChangePanel visualPanel;
    
    private int[] coins;
    private int amount;
    private int[] dp;
    private javax.swing.Timer animationTimer;
    private int currentAmount;
    private int currentCoinIndex;
    private boolean animationComplete;
    
    public NO322_N_CoinChange_Animation() {
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("NO.322 零钱兑换算法动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // 控制面板
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(new JLabel("硬币面额 (用逗号分隔):"));
        coinsField = new JTextField("1,3,4", 15);
        controlPanel.add(coinsField);
        
        controlPanel.add(new JLabel("目标金额:"));
        amountField = new JTextField("6", 8);
        controlPanel.add(amountField);
        
        calculateButton = new JButton("开始计算");
        demoButton = new JButton("演示样例");
        clearButton = new JButton("清空");
        
        controlPanel.add(calculateButton);
        controlPanel.add(demoButton);
        controlPanel.add(clearButton);
        
        add(controlPanel, BorderLayout.NORTH);
        
        // 可视化面板
        visualPanel = new CoinChangePanel();
        visualPanel.setPreferredSize(new Dimension(800, 500));
        add(visualPanel, BorderLayout.CENTER);
        
        // 日志面板
        logArea = new JTextArea(8, 50);
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.SOUTH);
        
        // 事件监听
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startCalculation();
            }
        });
        
        demoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coinsField.setText("1,3,4");
                amountField.setText("6");
                startCalculation();
            }
        });
        
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAll();
            }
        });
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private void startCalculation() {
        try {
            String coinsInput = coinsField.getText().trim();
            String amountInput = amountField.getText().trim();
            
            if (coinsInput.isEmpty() || amountInput.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入硬币面额和目标金额");
                return;
            }
            
            String[] coinStrs = coinsInput.split(",");
            coins = new int[coinStrs.length];
            for (int i = 0; i < coinStrs.length; i++) {
                coins[i] = Integer.parseInt(coinStrs[i].trim());
            }
            
            amount = Integer.parseInt(amountInput);
            
            if (amount < 0) {
                JOptionPane.showMessageDialog(this, "目标金额不能为负数");
                return;
            }
            
            // 初始化DP数组
            dp = new int[amount + 1];
            Arrays.fill(dp, Integer.MAX_VALUE);
            dp[0] = 0;
            
            currentAmount = 1;
            currentCoinIndex = 0;
            animationComplete = false;
            
            logArea.setText("");
            appendLog("开始零钱兑换计算...");
            appendLog("硬币面额: " + Arrays.toString(coins));
            appendLog("目标金额: " + amount);
            appendLog("使用动态规划算法\n");
            appendLog("初始化: dp[0] = 0, 其他为无穷大");
            
            startAnimation();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字");
        }
    }
    
    private void startAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        
        animationTimer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!animationComplete) {
                    if (currentAmount <= amount) {
                        if (currentCoinIndex < coins.length) {
                            int coin = coins[currentCoinIndex];
                            
                            appendLog("\n计算 dp[" + currentAmount + "], 尝试使用硬币 " + coin);
                            
                            if (currentAmount >= coin && dp[currentAmount - coin] != Integer.MAX_VALUE) {
                                int newValue = dp[currentAmount - coin] + 1;
                                if (newValue < dp[currentAmount]) {
                                    appendLog("  dp[" + currentAmount + "] = min(" + 
                                            (dp[currentAmount] == Integer.MAX_VALUE ? "∞" : dp[currentAmount]) + 
                                            ", dp[" + (currentAmount - coin) + "] + 1) = min(" +
                                            (dp[currentAmount] == Integer.MAX_VALUE ? "∞" : dp[currentAmount]) + 
                                            ", " + dp[currentAmount - coin] + " + 1) = " + newValue);
                                    dp[currentAmount] = newValue;
                                } else {
                                    appendLog("  dp[" + currentAmount + "] = min(" + 
                                            (dp[currentAmount] == Integer.MAX_VALUE ? "∞" : dp[currentAmount]) + 
                                            ", " + newValue + ") = " + 
                                            (dp[currentAmount] == Integer.MAX_VALUE ? "∞" : dp[currentAmount]) + " (无更新)");
                                }
                            } else {
                                if (currentAmount < coin) {
                                    appendLog("  硬币 " + coin + " 大于当前金额 " + currentAmount + ", 跳过");
                                } else {
                                    appendLog("  dp[" + (currentAmount - coin) + "] = ∞, 无法使用硬币 " + coin);
                                }
                            }
                            
                            visualPanel.updateVisualization(coins, amount, dp, currentAmount, currentCoinIndex);
                            
                            currentCoinIndex++;
                            if (currentCoinIndex >= coins.length) {
                                currentCoinIndex = 0;
                                currentAmount++;
                            }
                        }
                    } else {
                        animationComplete = true;
                        animationTimer.stop();
                        
                        appendLog("\n计算完成!");
                        if (dp[amount] == Integer.MAX_VALUE) {
                            appendLog("无法凑成目标金额 " + amount + ", 返回 -1");
                        } else {
                            appendLog("凑成目标金额 " + amount + " 所需的最少硬币数: " + dp[amount]);
                            
                            // 回溯找出具体的硬币组合
                            java.util.List<Integer> coinCombination = findCoinCombination();
                            if (!coinCombination.isEmpty()) {
                                appendLog("硬币组合: " + coinCombination);
                            }
                        }
                        
                        visualPanel.setAnimationComplete(true);
                        visualPanel.repaint();
                    }
                }
            }
        });
        animationTimer.start();
    }
    
    private java.util.List<Integer> findCoinCombination() {
        java.util.List<Integer> result = new ArrayList<>();
        if (dp[amount] == Integer.MAX_VALUE) {
            return result;
        }
        
        int currentAmount = amount;
        while (currentAmount > 0) {
            for (int coin : coins) {
                if (currentAmount >= coin && dp[currentAmount - coin] + 1 == dp[currentAmount]) {
                    result.add(coin);
                    currentAmount -= coin;
                    break;
                }
            }
        }
        
        return result;
    }
    
    private void clearAll() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        coinsField.setText("");
        amountField.setText("");
        logArea.setText("");
        visualPanel.clear();
    }
    
    private void appendLog(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }
    
    // 可视化面板
    private class CoinChangePanel extends JPanel {
        private int[] coins;
        private int amount;
        private int[] dp;
        private int currentAmount;
        private int currentCoinIndex;
        private boolean animationComplete;
        
        public void updateVisualization(int[] coins, int amount, int[] dp, int currentAmount, int currentCoinIndex) {
            this.coins = coins.clone();
            this.amount = amount;
            this.dp = dp.clone();
            this.currentAmount = currentAmount;
            this.currentCoinIndex = currentCoinIndex;
            repaint();
        }
        
        public void setAnimationComplete(boolean complete) {
            this.animationComplete = complete;
        }
        
        public void clear() {
            coins = null;
            dp = null;
            animationComplete = false;
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (coins == null || dp == null) return;
            
            int width = getWidth();
            int height = getHeight();
            
            // 绘制标题
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.setColor(Color.BLACK);
            g2d.drawString("零钱兑换动态规划可视化", 20, 30);
            
            // 绘制硬币信息
            g2d.setFont(new Font("Arial", Font.PLAIN, 14));
            g2d.drawString("硬币面额: " + Arrays.toString(coins), 20, 60);
            g2d.drawString("目标金额: " + amount, 20, 80);
            
            // 绘制DP数组
            int startY = 120;
            int cellWidth = Math.min(50, (width - 100) / (amount + 1));
            int cellHeight = 40;
            
            // 绘制索引
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            for (int i = 0; i <= amount; i++) {
                int x = 50 + i * cellWidth;
                g2d.setColor(Color.BLACK);
                g2d.drawString(String.valueOf(i), x + cellWidth/2 - 5, startY - 5);
            }
            
            // 绘制DP数组值
            for (int i = 0; i <= amount; i++) {
                int x = 50 + i * cellWidth;
                
                // 设置颜色
                if (i == currentAmount && !animationComplete) {
                    g2d.setColor(Color.YELLOW); // 当前计算的位置
                } else if (i == 0) {
                    g2d.setColor(Color.GREEN); // 初始值
                } else if (dp[i] == Integer.MAX_VALUE) {
                    g2d.setColor(Color.LIGHT_GRAY); // 无穷大
                } else {
                    g2d.setColor(Color.CYAN); // 已计算的值
                }
                
                g2d.fillRect(x, startY, cellWidth, cellHeight);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, startY, cellWidth, cellHeight);
                
                // 绘制数值
                String value = dp[i] == Integer.MAX_VALUE ? "∞" : String.valueOf(dp[i]);
                FontMetrics fm = g2d.getFontMetrics();
                int textX = x + (cellWidth - fm.stringWidth(value)) / 2;
                int textY = startY + (cellHeight + fm.getAscent()) / 2;
                g2d.drawString(value, textX, textY);
            }
            
            // 绘制当前状态信息
            if (!animationComplete) {
                g2d.setFont(new Font("Arial", Font.PLAIN, 14));
                g2d.setColor(Color.BLACK);
                g2d.drawString("当前计算: dp[" + currentAmount + "]", 20, startY + cellHeight + 30);
                if (currentCoinIndex < coins.length) {
                    g2d.drawString("当前硬币: " + coins[currentCoinIndex], 20, startY + cellHeight + 50);
                }
            }
            
            // 绘制硬币可视化
            int coinY = startY + cellHeight + 80;
            g2d.drawString("硬币:", 20, coinY);
            
            for (int i = 0; i < coins.length; i++) {
                int x = 80 + i * 60;
                
                // 高亮当前使用的硬币
                if (i == currentCoinIndex && !animationComplete) {
                    g2d.setColor(Color.ORANGE);
                } else {
                    g2d.setColor(new Color(173, 216, 230));
                }
                
                g2d.fillOval(x, coinY - 15, 40, 40);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(x, coinY - 15, 40, 40);
                
                // 绘制硬币面额
                String coinValue = String.valueOf(coins[i]);
                FontMetrics fm = g2d.getFontMetrics();
                int textX = x + (40 - fm.stringWidth(coinValue)) / 2;
                int textY = coinY + fm.getAscent() / 2;
                g2d.drawString(coinValue, textX, textY);
            }
            
            // 绘制状态转移方程
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.setColor(Color.BLACK);
            int equationY = coinY + 60;
            g2d.drawString("状态转移方程: dp[i] = min(dp[i], dp[i - coin] + 1)", 20, equationY);
            
            // 绘制最终结果
            if (animationComplete) {
                g2d.setFont(new Font("Arial", Font.BOLD, 14));
                g2d.setColor(Color.RED);
                String result = dp[amount] == Integer.MAX_VALUE ? 
                    "无解 (返回 -1)" : "最少硬币数: " + dp[amount];
                g2d.drawString("结果: " + result, 20, equationY + 30);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO322_N_CoinChange_Animation().setVisible(true);
        });
    }
}