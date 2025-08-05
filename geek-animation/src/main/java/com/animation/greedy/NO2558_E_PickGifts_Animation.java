package com.animation.greedy;

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
import java.util.PriorityQueue;
import com.animation.launcher.AlgorithmTreeLauncher;

/**
 * NO.2558 从数量最多的堆取走礼物 - 动画演示
 * 演示优先队列（最大堆）的使用过程
 */
public class NO2558_E_PickGifts_Animation extends JFrame {
    private PriorityQueue<Integer> pq;
    private List<Integer> originalGifts;
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JTextField inputField, kField;
    private JButton addGiftButton, pickGiftButton, resetButton, autoPlayButton;
    private JLabel resultLabel, totalLabel;
    private Timer animationTimer;
    private int currentK;
    private boolean isAnimating = false;
    private GiftsVisualizationPanel visualizationPanel;
    
    public NO2558_E_PickGifts_Animation() {
        setTitle("NO.2558 从数量最多的堆取走礼物 - 动画演示");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        
        pq = new PriorityQueue<>((a, b) -> b - a); // 最大堆
        originalGifts = new ArrayList<>();
        initComponents();
        setupLayout();
        setupEventHandlers();
        initializeDefaultData();
    }
    
    private void initComponents() {
        mainPanel = new JPanel();
        controlPanel = new JPanel();
        
        inputField = new JTextField(10);
        kField = new JTextField("4", 5);
        addGiftButton = new JButton("添加礼物");
        pickGiftButton = new JButton("取走一次");
        resetButton = new JButton("重置");
        autoPlayButton = new JButton("自动演示");
        resultLabel = new JLabel("结果: ");
        totalLabel = new JLabel("剩余总数: 0");
        
        // 设置字体
        Font font = new Font("微软雅黑", Font.PLAIN, 14);
        resultLabel.setFont(font);
        totalLabel.setFont(font);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 主面板
        mainPanel.setLayout(new BorderLayout());
        visualizationPanel = new GiftsVisualizationPanel();
        mainPanel.add(visualizationPanel, BorderLayout.CENTER);
        
        // 控制面板
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JLabel("礼物数量:"));
        controlPanel.add(inputField);
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

        controlPanel.add(addGiftButton);
        controlPanel.add(new JLabel("操作次数:"));
        controlPanel.add(kField);
        controlPanel.add(pickGiftButton);
        controlPanel.add(autoPlayButton);
        controlPanel.add(resetButton);
        controlPanel.add(homeButton);
        controlPanel.add(resultLabel);
        controlPanel.add(totalLabel);
        
        add(mainPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        addGiftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int value = Integer.parseInt(inputField.getText());
                    if (value > 0) {
                        pq.offer(value);
                        originalGifts.add(value);
                        updateDisplay();
                        resultLabel.setText("结果: 添加礼物 " + value);
                        inputField.setText("");
                    } else {
                        resultLabel.setText("错误: 请输入正整数");
                    }
                } catch (NumberFormatException ex) {
                    resultLabel.setText("错误: 请输入有效数字");
                }
            }
        });
        
        pickGiftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAnimating && !pq.isEmpty()) {
                    pickOneGift();
                }
            }
        });
        
        autoPlayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAnimating) {
                    try {
                        currentK = Integer.parseInt(kField.getText());
                        startAutoPlay();
                    } catch (NumberFormatException ex) {
                        resultLabel.setText("错误: K值必须是有效数字");
                    }
                } else {
                    stopAutoPlay();
                }
            }
        });
        
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
    }
    
    private void initializeDefaultData() {
        // 初始化默认数据 [25,64,9,4,100]
        int[] defaultGifts = {25, 64, 9, 4, 100};
        for (int gift : defaultGifts) {
            pq.offer(gift);
            originalGifts.add(gift);
        }
        updateDisplay();
    }
    
    private void pickOneGift() {
        if (!pq.isEmpty()) {
            int maxGift = pq.poll();
            int newValue = (int) Math.sqrt(maxGift);
            pq.offer(newValue);
            updateDisplay();
            resultLabel.setText("结果: 取走 " + maxGift + ", 剩余 " + newValue);
        }
    }
    
    private void startAutoPlay() {
        if (currentK > 0 && !pq.isEmpty()) {
            isAnimating = true;
            autoPlayButton.setText("停止");
            pickGiftButton.setEnabled(false);
            
            animationTimer = new Timer(1500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentK > 0 && !pq.isEmpty()) {
                        pickOneGift();
                        currentK--;
                        resultLabel.setText(resultLabel.getText() + " (剩余步数: " + currentK + ")");
                    } else {
                        stopAutoPlay();
                        long total = calculateTotal();
                        resultLabel.setText("完成! 最终剩余总数: " + total);
                    }
                }
            });
            animationTimer.start();
        }
    }
    
    private void stopAutoPlay() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        isAnimating = false;
        autoPlayButton.setText("自动演示");
        pickGiftButton.setEnabled(true);
    }
    
    private void reset() {
        stopAutoPlay();
        pq.clear();
        originalGifts.clear();
        initializeDefaultData();
        resultLabel.setText("结果: 已重置");
    }
    
    private long calculateTotal() {
        long total = 0;
        for (Integer gift : pq) {
            total += gift;
        }
        return total;
    }
    
    private void updateDisplay() {
        long total = calculateTotal();
        totalLabel.setText("剩余总数: " + total);
        visualizationPanel.repaint();
    }
    
    // 礼物可视化面板
    private class GiftsVisualizationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            drawGiftsHeap(g2d);
            drawAlgorithmInfo(g2d);
        }
        
        private void drawGiftsHeap(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            g2d.drawString("优先队列 (最大堆):", 50, 30);
            
            // 将优先队列转换为数组以便绘制
            Integer[] gifts = pq.toArray(new Integer[0]);
            
            // 绘制堆的结构
            int startX = 100;
            int startY = 80;
            int nodeWidth = 60;
            int nodeHeight = 40;
            int levelHeight = 80;
            
            for (int i = 0; i < gifts.length; i++) {
                int level = (int) (Math.log(i + 1) / Math.log(2));
                int positionInLevel = i - (int) Math.pow(2, level) + 1;
                int maxInLevel = (int) Math.pow(2, level);
                
                int x = startX + (getWidth() - 200) / maxInLevel * positionInLevel + 
                       (getWidth() - 200) / maxInLevel / 2 - nodeWidth / 2;
                int y = startY + level * levelHeight;
                
                // 绘制连接线（除了根节点）
                if (i > 0) {
                    int parentIndex = (i - 1) / 2;
                    int parentLevel = (int) (Math.log(parentIndex + 1) / Math.log(2));
                    int parentPositionInLevel = parentIndex - (int) Math.pow(2, parentLevel) + 1;
                    int parentMaxInLevel = (int) Math.pow(2, parentLevel);
                    
                    int parentX = startX + (getWidth() - 200) / parentMaxInLevel * parentPositionInLevel + 
                                 (getWidth() - 200) / parentMaxInLevel / 2;
                    int parentY = startY + parentLevel * levelHeight + nodeHeight / 2;
                    
                    g2d.setColor(Color.GRAY);
                    g2d.drawLine(parentX, parentY, x + nodeWidth / 2, y);
                }
                
                // 绘制节点
                Rectangle rect = new Rectangle(x, y, nodeWidth, nodeHeight);
                if (i == 0) { // 根节点（最大值）用红色高亮
                    g2d.setColor(Color.RED);
                } else {
                    g2d.setColor(Color.LIGHT_GRAY);
                }
                g2d.fill(rect);
                g2d.setColor(Color.BLACK);
                g2d.draw(rect);
                
                // 绘制数值
                String value = gifts[i].toString();
                FontMetrics fm = g2d.getFontMetrics();
                int textX = x + (nodeWidth - fm.stringWidth(value)) / 2;
                int textY = y + (nodeHeight + fm.getHeight()) / 2 - 2;
                g2d.drawString(value, textX, textY);
            }
            
            // 绘制说明
            if (gifts.length > 0) {
                g2d.setColor(Color.RED);
                g2d.drawString("← 最大值 (将被取走)", startX + nodeWidth + 10, startY + 25);
            }
        }
        
        private void drawAlgorithmInfo(Graphics2D g2d) {
            int infoX = 50;
            int infoY = getHeight() - 200;
            
            g2d.setColor(Color.BLACK);
            g2d.drawString("算法步骤:", infoX, infoY);
            g2d.drawString("1. 从堆中取出最大值", infoX, infoY + 20);
            g2d.drawString("2. 计算该值的平方根(向下取整)", infoX, infoY + 40);
            g2d.drawString("3. 将新值放回堆中", infoX, infoY + 60);
            g2d.drawString("4. 重复k次", infoX, infoY + 80);
            
            // 显示当前操作
            if (isAnimating) {
                g2d.setColor(Color.BLUE);
                g2d.drawString("正在执行第 " + (Integer.parseInt(kField.getText()) - currentK + 1) + " 步...", 
                              infoX, infoY + 120);
            }
            
            // 绘制示例
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawString("示例: 100 → √100 = 10", infoX + 300, infoY + 20);
            g2d.drawString("示例: 64 → √64 = 8", infoX + 300, infoY + 40);
            g2d.drawString("示例: 25 → √25 = 5", infoX + 300, infoY + 60);
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
                new NO2558_E_PickGifts_Animation().setVisible(true);
            }
        });
    }
}
