package com.animation.bit;

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
 * LeetCode 136. 只出现一次的数字 - 动画演示
 * 
 * 算法思路：
 * 使用异或运算的性质：
 * 1. 任何数和0做异或运算，结果仍然是原来的数
 * 2. 任何数和其自身做异或运算，结果是0
 * 3. 异或运算满足交换律和结合律
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class NO136_E_SingleNumber_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 700;
    private static final int CELL_SIZE = 60;
    private static final int CELL_SPACING = 10;
    
    // 组件
    private JPanel animationPanel;
    private JButton startButton;
    private JButton stepButton;
    private JButton resetButton;
    private JButton homeButton;
    private JLabel statusLabel;
    private JLabel complexityLabel;
    
    // 动画状态
    private int[] nums;
    private int currentIndex;
    private int result;
    private boolean isAnimating;
    private Timer animationTimer;
    
    // 动画步骤记录
    private List<String> stepDescriptions;
    private List<String> binaryOperations;
    
    public NO136_E_SingleNumber_Animation() {
        initComponents();
        setupLayout();
        setupEventHandlers();
        resetAnimation();
    }
    
    private void initComponents() {
        setTitle("LeetCode 136. 只出现一次的数字 - 动画演示");
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
        
        statusLabel = new JLabel("准备开始演示只出现一次的数字算法");
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
        
        JLabel titleLabel = new JLabel("LeetCode 136. 只出现一次的数字", JLabel.CENTER);
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
        if (currentIndex >= nums.length) {
            statusLabel.setText("算法执行完成！只出现一次的数字是: " + result);
            return false;
        }
        
        // 执行异或运算
        result ^= nums[currentIndex];
        
        statusLabel.setText(stepDescriptions.get(currentIndex));
        currentIndex++;
        SwingUtilities.invokeLater(() -> animationPanel.repaint());
        
        return currentIndex <= nums.length;
    }
    
    private void resetAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        
        isAnimating = false;
        startButton.setText("开始动画");
        
        // 初始化数组 [4,1,2,1,2]
        nums = new int[]{4, 1, 2, 1, 2};
        currentIndex = 0;
        result = 0;
        
        // 生成步骤描述
        generateStepDescriptions();
        
        statusLabel.setText("准备开始演示只出现一次的数字算法");
        SwingUtilities.invokeLater(() -> animationPanel.repaint());
    }
    
    private void generateStepDescriptions() {
        stepDescriptions = new ArrayList<>();
        binaryOperations = new ArrayList<>();
        
        stepDescriptions.add("初始化: result = 0");
        binaryOperations.add("result = 0 (二进制: 000)");
        
        int tempResult = 0;
        for (int i = 0; i < nums.length; i++) {
            int oldResult = tempResult;
            tempResult ^= nums[i];
            
            stepDescriptions.add("第" + (i + 1) + "步: result ^= " + nums[i] + 
                " → " + oldResult + " ^ " + nums[i] + " = " + tempResult);
            
            binaryOperations.add(String.format("二进制: %s ^ %s = %s", 
                toBinaryString(oldResult), toBinaryString(nums[i]), toBinaryString(tempResult)));
        }
    }
    
    private String toBinaryString(int num) {
        return String.format("%3s", Integer.toBinaryString(num)).replace(' ', '0');
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int startX = 50;
        int arrayY = 120;
        
        // 绘制标题和说明
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(Color.BLACK);
        g2d.drawString("数组: [4, 1, 2, 1, 2] - 找出只出现一次的数字", startX, 50);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        g2d.drawString("异或运算性质: a⊕0=a, a⊕a=0, 满足交换律和结合律", startX, 75);
        g2d.drawString("相同的数字异或后为0，最终结果就是只出现一次的数字", startX, 95);
        
        // 绘制数组
        for (int i = 0; i < nums.length; i++) {
            int x = startX + i * (CELL_SIZE + CELL_SPACING);
            
            // 确定颜色
            Color cellColor = Color.LIGHT_GRAY;
            if (i < currentIndex) {
                cellColor = Color.CYAN; // 已处理
            } else if (i == currentIndex) {
                cellColor = Color.YELLOW; // 当前处理
            }
            
            // 绘制单元格
            g2d.setColor(cellColor);
            g2d.fillRect(x, arrayY, CELL_SIZE, CELL_SIZE);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, arrayY, CELL_SIZE, CELL_SIZE);
            
            // 绘制数值
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            FontMetrics fm = g2d.getFontMetrics();
            String value = String.valueOf(nums[i]);
            int textX = x + (CELL_SIZE - fm.stringWidth(value)) / 2;
            int textY = arrayY + (CELL_SIZE + fm.getAscent()) / 2;
            g2d.drawString(value, textX, textY);
            
            // 绘制索引
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.setColor(Color.GRAY);
            String index = String.valueOf(i);
            int indexX = x + (CELL_SIZE - g2d.getFontMetrics().stringWidth(index)) / 2;
            g2d.drawString(index, indexX, arrayY - 5);
            
            // 绘制二进制表示
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            g2d.setColor(Color.BLUE);
            String binary = toBinaryString(nums[i]);
            int binaryX = x + (CELL_SIZE - g2d.getFontMetrics().stringWidth(binary)) / 2;
            g2d.drawString(binary, binaryX, arrayY + CELL_SIZE + 15);
        }
        
        // 绘制当前指针
        if (currentIndex < nums.length) {
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            g2d.setColor(Color.RED);
            int pointerX = startX + currentIndex * (CELL_SIZE + CELL_SPACING) + CELL_SIZE / 2;
            g2d.drawString("↑", pointerX - 5, arrayY + CELL_SIZE + 35);
            g2d.drawString("当前", pointerX - 15, arrayY + CELL_SIZE + 50);
        }
        
        // 绘制异或运算过程
        drawXorProcess(g2d, startX, arrayY + 100);
        
        // 绘制结果
        drawResult(g2d, startX, arrayY + 250);
    }
    
    private void drawXorProcess(Graphics2D g2d, int startX, int startY) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("异或运算过程:", startX, startY);
        
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        
        if (currentIndex > 0) {
            for (int i = 0; i < Math.min(currentIndex, stepDescriptions.size()); i++) {
                g2d.setColor(Color.BLACK);
                g2d.drawString(stepDescriptions.get(i), startX, startY + 25 + i * 20);
                
                if (i < binaryOperations.size()) {
                    g2d.setColor(Color.BLUE);
                    g2d.drawString(binaryOperations.get(i), startX + 300, startY + 25 + i * 20);
                }
            }
        }
    }
    
    private void drawResult(Graphics2D g2d, int startX, int startY) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(Color.RED);
        g2d.drawString("当前结果: " + result, startX, startY);
        
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.setColor(Color.BLUE);
        g2d.drawString("二进制: " + toBinaryString(result), startX, startY + 25);
        
        // 绘制颜色说明
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        int legendX = startX + 300;
        
        g2d.setColor(Color.CYAN);
        g2d.fillRect(legendX, startY, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("已处理", legendX + 20, startY + 12);
        
        g2d.setColor(Color.YELLOW);
        g2d.fillRect(legendX, startY + 25, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("当前处理", legendX + 20, startY + 37);
        
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(legendX, startY + 50, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("未处理", legendX + 20, startY + 62);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO136_E_SingleNumber_Animation().setVisible(true);
        });
    }
}