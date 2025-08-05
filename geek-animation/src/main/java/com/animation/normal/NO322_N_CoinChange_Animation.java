package com.animation.normal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;

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
        setTitle("NO.322 零钱兑换算法动画演示 - 美元钞票计数");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(900, 750);
        
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
        visualPanel.setBackground(Color.WHITE);
        visualPanel.setBorder(BorderFactory.createTitledBorder("动态规划可视化 - 美元钞票动画"));
        visualPanel.setPreferredSize(new Dimension(850, 500));
        add(visualPanel, BorderLayout.CENTER);
        
        // 日志面板
        logArea = new JTextArea(8, 50);
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("计算日志"));
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
    
    // 钞票动画类
    private class BillAnimation {
        private double currentX, currentY;
        private double targetX, targetY;
        private double startX, startY;
        private int billNumber;
        private boolean isMoving;
        private double progress;
        private static final double ANIMATION_SPEED = 0.05;
        
        public BillAnimation(int startX, int startY, int targetX, int targetY, int billNumber) {
            this.startX = this.currentX = startX;
            this.startY = this.currentY = startY;
            this.targetX = targetX;
            this.targetY = targetY;
            this.billNumber = billNumber;
            this.isMoving = true;
            this.progress = 0.0;
        }
        
        public boolean updatePosition() {
            if (!isMoving) return false;
            
            progress += ANIMATION_SPEED;
            if (progress >= 1.0) {
                progress = 1.0;
                isMoving = false;
            }
            
            // 使用缓动函数让动画更自然
            double easeProgress = easeInOutQuad(progress);
            currentX = startX + (targetX - startX) * easeProgress;
            currentY = startY + (targetY - startY) * easeProgress;
            
            return isMoving;
        }
        
        private double easeInOutQuad(double t) {
            return t < 0.5 ? 2 * t * t : -1 + (4 - 2 * t) * t;
        }
        
        public void draw(Graphics2D g2d) {
            // 绘制美元钞票
            int billWidth = 60;
            int billHeight = 25;
            
            // 钞票阴影
            g2d.setColor(new Color(0, 0, 0, 50));
            g2d.fillRoundRect((int)currentX + 2, (int)currentY + 2, billWidth, billHeight, 5, 5);
            
            // 钞票主体 - 绿色
            g2d.setColor(new Color(85, 170, 85));
            g2d.fillRoundRect((int)currentX, (int)currentY, billWidth, billHeight, 5, 5);
            
            // 钞票边框
            g2d.setColor(new Color(34, 139, 34));
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRoundRect((int)currentX, (int)currentY, billWidth, billHeight, 5, 5);
            
            // 美元符号
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.drawString("$", (int)currentX + 8, (int)currentY + 18);
            
            // 面额 (假设都是1美元)
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            g2d.drawString("1", (int)currentX + 25, (int)currentY + 18);
            
            // 钞票编号
            g2d.setFont(new Font("Arial", Font.PLAIN, 8));
            g2d.setColor(new Color(0, 100, 0));
            g2d.drawString("#" + billNumber, (int)currentX + 40, (int)currentY + 20);
            
            // 如果正在移动，添加一些特效
            if (isMoving) {
                g2d.setColor(new Color(255, 255, 0, 100));
                g2d.fillOval((int)currentX - 5, (int)currentY - 5, billWidth + 10, billHeight + 10);
            }
        }
        
        public double getCurrentX() { return currentX; }
        public double getCurrentY() { return currentY; }
        public boolean isMoving() { return isMoving; }
    }

    private class CoinAnimation {
        private int x, y, targetX, targetY;
        private double currentX, currentY;
        private double speed = 15.0;
        private boolean arrived = false;
        private int coinValue;

        public CoinAnimation(int startX, int startY, int targetX, int targetY, int coinValue) {
            this.x = startX;
            this.y = startY;
            this.currentX = startX;
            this.currentY = startY;
            this.targetX = targetX;
            this.targetY = targetY;
            this.coinValue = coinValue;
        }

        public boolean updatePosition() {
            if (arrived) return false;
            double dx = targetX - currentX;
            double dy = targetY - currentY;
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < speed) {
                currentX = targetX;
                currentY = targetY;
                arrived = true;
                return false;
            } else {
                currentX += (dx / distance) * speed;
                currentY += (dy / distance) * speed;
                return true;
            }
        }

        public void draw(Graphics2D g2d) {
            g2d.setColor(Color.ORANGE);
            g2d.fillOval((int) currentX, (int) currentY, 20, 20);
            g2d.setColor(Color.BLACK);
            g2d.drawOval((int) currentX, (int) currentY, 20, 20);
            String value = String.valueOf(coinValue);
            FontMetrics fm = g2d.getFontMetrics();
            int textX = (int) currentX + (20 - fm.stringWidth(value)) / 2;
            int textY = (int) currentY + (20 + fm.getAscent()) / 2 - 2;
            g2d.drawString(value, textX, textY);
        }
    }
    
    // 可视化面板
    private class CoinChangePanel extends JPanel {
        private int[] coins;
        private int amount;
        private int[] dp;
        private int currentAmount;
        private int currentCoinIndex;
        private boolean animationComplete;
        
        // 钞票动画相关
        private java.util.List<BillAnimation> billAnimations = new ArrayList<>();
        private java.util.List<CoinAnimation> coinAnimations = new ArrayList<>();
        private javax.swing.Timer animationTimer;
        private int totalItemsToShow = 0;
        private int currentItemCount = 0;
        
        public CoinChangePanel() {
            // 初始化动画定时器
            animationTimer = new javax.swing.Timer(200, e -> {
                if (currentItemCount < totalItemsToShow) {
                    // 决定是添加钞票还是硬币
                    if (Math.random() > 0.3) { // 70% 概率是钞票
                        addNewBillAnimation();
                    } else {
                        addNewCoinAnimation();
                    }
                    currentItemCount++;
                    repaint();
                } else {
                    animationTimer.stop();
                }
            });
        }
        
        public void updateVisualization(int[] coins, int amount, int[] dp, int currentAmount, int currentCoinIndex) {
            this.coins = coins.clone();
            this.amount = amount;
            this.dp = dp.clone();
            this.currentAmount = currentAmount;
            this.currentCoinIndex = currentCoinIndex;

            // 如果当前金额有解，启动动画
            if (currentAmount > 0 && dp[currentAmount] != Integer.MAX_VALUE) {
                startItemAnimation(dp[currentAmount]);
            }

            repaint();
        }
        
        public void setAnimationComplete(boolean complete) {
            this.animationComplete = complete;
            if (complete && amount > 0 && dp[amount] != Integer.MAX_VALUE) {
                // 最终结果的动画
                startFinalItemAnimation(dp[amount]);
            }
        }
        
        public void clear() {
            coins = null;
            dp = null;
            animationComplete = false;
            billAnimations.clear();
            coinAnimations.clear();
            if (animationTimer != null) {
                animationTimer.stop();
            }
            currentItemCount = 0;
            totalItemsToShow = 0;
            repaint();
        }
        
        private void startItemAnimation(int itemCount) {
            if (itemCount <= 0 || itemCount > 20) return; // 限制动画数量

            billAnimations.clear();
            coinAnimations.clear();
            totalItemsToShow = itemCount;
            currentItemCount = 0;
            animationTimer.setDelay(200);
            animationTimer.start();
        }
        
        private void startFinalItemAnimation(int itemCount) {
            if (itemCount <= 0 || itemCount > 20) return;

            billAnimations.clear();
            coinAnimations.clear();
            totalItemsToShow = itemCount;
            currentItemCount = 0;

            // 最终动画更快一些
            animationTimer.setDelay(150);
            animationTimer.start();
        }
        
        private void addNewBillAnimation() {
            int startX = getWidth() - 150;
            int startY = 50 + currentItemCount * 5; // 稍微错开位置
            int targetX = getWidth() - 300 + (currentItemCount % 5) * 25;
            int targetY = 200 + (currentItemCount / 5) * 30;

            BillAnimation bill = new BillAnimation(startX, startY, targetX, targetY, currentItemCount + 1);
            billAnimations.add(bill);

            // 启动这张钞票的移动动画
            javax.swing.Timer moveTimer = new javax.swing.Timer(50, null);
            moveTimer.addActionListener(e -> {
                if (bill.updatePosition()) {
                    repaint();
                } else {
                    moveTimer.stop();
                }
            });
            moveTimer.start();
        }

        private void addNewCoinAnimation() {
            int startX = getWidth() - 150;
            int startY = 50 + currentItemCount * 5;
            int targetX = getWidth() - 300 + (currentItemCount % 8) * 22;
            int targetY = 280 + (currentItemCount / 8) * 22;
            int coinValue = coins[new Random().nextInt(coins.length)];

            CoinAnimation coin = new CoinAnimation(startX, startY, targetX, targetY, coinValue);
            coinAnimations.add(coin);

            javax.swing.Timer moveTimer = new javax.swing.Timer(50, null);
            moveTimer.addActionListener(e -> {
                if (coin.updatePosition()) {
                    repaint();
                } else {
                    moveTimer.stop();
                }
            });
            moveTimer.start();
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
            
            // 绘制动画区域
            int itemAreaY = equationY + 50;
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.setColor(Color.BLACK);
            g2d.drawString("计数:", 20, itemAreaY);

            // 绘制计数器
            if (totalItemsToShow > 0) {
                g2d.setFont(new Font("Arial", Font.BOLD, 16));
                g2d.setColor(Color.BLUE);
                g2d.drawString("已数物件: " + currentItemCount + " / " + totalItemsToShow, 150, itemAreaY);
            }

            // 绘制所有动画
            for (BillAnimation bill : billAnimations) {
                bill.draw(g2d);
            }
            for (CoinAnimation coin : coinAnimations) {
                coin.draw(g2d);
            }

            // 绘制堆叠区域边框
            if (!billAnimations.isEmpty() || !coinAnimations.isEmpty()) {
                g2d.setColor(new Color(139, 69, 19, 100)); // 棕色半透明
                g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{5}, 0));
                g2d.drawRect(width - 350, 180, 200, 150);
                
                g2d.setFont(new Font("Arial", Font.PLAIN, 12));
                g2d.setColor(Color.BLACK);
                g2d.drawString("堆叠区", width - 340, 175);
            }
            
            // 绘制最终结果
            if (animationComplete) {
                g2d.setFont(new Font("Arial", Font.BOLD, 14));
                g2d.setColor(Color.RED);
                String result = dp[amount] == Integer.MAX_VALUE ? 
                    "无解 (返回 -1)" : "最少硬币数: " + dp[amount];
                g2d.drawString("结果: " + result, 20, itemAreaY + 30);
                
                // 绘制最终总数
                if (dp[amount] != Integer.MAX_VALUE && totalItemsToShow > 0) {
                    g2d.setFont(new Font("Arial", Font.BOLD, 16));
                    g2d.setColor(new Color(0, 128, 0));
                    g2d.drawString("💰 总计: " + totalItemsToShow, 20, itemAreaY + 55);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO322_N_CoinChange_Animation().setVisible(true);
        });
    }
}