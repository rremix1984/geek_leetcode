package com.leetcode.animation.array;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * NO.2733 既不是最小值也不是最大值 - 动画演示
 * 
 * 算法思路：
 * 1. 如果数组长度小于等于2，返回-1（不存在既不是最小值也不是最大值的数）
 * 2. 优化方法：只需要比较前三个元素，找出中间值
 * 3. 通过比较找出既不是最小值也不是最大值的数字
 * 
 * 时间复杂度：O(1) - 只需要比较前三个元素
 * 空间复杂度：O(1) - 只使用常数额外空间
 */
public class NO2733_E_FindNonMinOrMax_Animation extends JFrame {
    
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final Color BACKGROUND_COLOR = new Color(240, 248, 255);
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private static final Color HIGHLIGHT_COLOR = new Color(255, 69, 0);
    private static final Color SUCCESS_COLOR = new Color(34, 139, 34);
    private static final Color ARRAY_COLOR = new Color(135, 206, 250);
    private static final Color MIN_COLOR = new Color(255, 182, 193);
    private static final Color MAX_COLOR = new Color(255, 215, 0);
    private static final Color MIDDLE_COLOR = new Color(144, 238, 144);
    
    private JPanel animationPanel;
    private JButton startButton, nextButton, resetButton;
    private JTextField arrayField;
    private Timer animationTimer;
    
    // 算法状态变量
    private int[] nums;
    private boolean isAnimating;
    private int animationStep;
    private int result;
    private int minValue, maxValue, middleValue;
    private int minIndex, maxIndex, middleIndex;
    private String comparisonInfo;
    
    public NO2733_E_FindNonMinOrMax_Animation() {
        initializeComponents();
        setupLayout();
        setupEventListeners();
        resetAnimation();
    }
    
    private void initializeComponents() {
        setTitle("NO.2733 既不是最小值也不是最大值 - 动画演示");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // 创建控制面板
        JPanel controlPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        controlPanel.setBackground(BACKGROUND_COLOR);
        
        // 输入面板
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(BACKGROUND_COLOR);
        arrayField = new JTextField("3,2,1,4", 15);
        inputPanel.add(new JLabel("数组(逗号分隔):"));
        inputPanel.add(arrayField);
        
        // 控制按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(BACKGROUND_COLOR);
        startButton = new JButton("开始动画");
        nextButton = new JButton("下一步");
        resetButton = new JButton("重置");
        
        // 返回首页按钮
        JButton homeButton = new JButton("返回首页");
        homeButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                try {
                    com.leetcode.animation.AlgorithmTreeLauncher.showMainWindow();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });
        
        buttonPanel.add(startButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(homeButton);
        
        controlPanel.add(inputPanel);
        controlPanel.add(buttonPanel);
        
        // 创建动画面板
        animationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawAnimation((Graphics2D) g);
            }
        };
        animationPanel.setBackground(BACKGROUND_COLOR);
        animationPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT - 120));
        
        add(controlPanel, BorderLayout.NORTH);
        add(animationPanel, BorderLayout.CENTER);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
    }
    
    private void setupEventListeners() {
        startButton.addActionListener(e -> startAnimation());
        nextButton.addActionListener(e -> nextStep());
        resetButton.addActionListener(e -> resetAnimation());
        
        // 动画定时器
        animationTimer = new Timer(1500, e -> nextStep());
    }
    
    private void startAnimation() {
        try {
            // 解析输入
            String[] arrayStr = arrayField.getText().trim().split(",");
            nums = new int[arrayStr.length];
            for (int i = 0; i < arrayStr.length; i++) {
                nums[i] = Integer.parseInt(arrayStr[i].trim());
            }
            
            if (nums.length == 0) {
                JOptionPane.showMessageDialog(this, "请输入有效的数组！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // 开始动画
            isAnimating = true;
            animationStep = 0;
            result = -1;
            minValue = maxValue = middleValue = 0;
            minIndex = maxIndex = middleIndex = -1;
            comparisonInfo = "";
            
            setButtonsEnabled(false);
            animationTimer.start();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void nextStep() {
        if (!isAnimating) {
            finishAnimation();
            return;
        }
        
        if (animationStep == 0) {
            // 步骤1：检查数组长度
            if (nums.length <= 2) {
                result = -1;
                comparisonInfo = "数组长度 <= 2，不存在既不是最小值也不是最大值的数";
                animationStep = 99; // 跳到结束
            } else {
                comparisonInfo = "数组长度 > 2，开始寻找中间值";
                animationStep++;
            }
        } else if (animationStep == 1) {
            // 步骤2：使用优化算法，只比较前三个元素
            int a = nums[0], b = nums[1], c = nums[2];
            
            if (a > b) {
                if (a > c) {
                    // a是最大值
                    maxValue = a;
                    maxIndex = 0;
                    middleValue = Math.max(b, c);
                    middleIndex = (b > c) ? 1 : 2;
                    minValue = Math.min(b, c);
                    minIndex = (b < c) ? 1 : 2;
                } else {
                    // c是最大值，a是中间值
                    maxValue = c;
                    maxIndex = 2;
                    middleValue = a;
                    middleIndex = 0;
                    minValue = b;
                    minIndex = 1;
                }
            } else {
                if (a > c) {
                    // b是最大值，a是中间值
                    maxValue = b;
                    maxIndex = 1;
                    middleValue = a;
                    middleIndex = 0;
                    minValue = c;
                    minIndex = 2;
                } else {
                    // b或c是最大值
                    if (b > c) {
                        maxValue = b;
                        maxIndex = 1;
                        middleValue = c;
                        middleIndex = 2;
                        minValue = a;
                        minIndex = 0;
                    } else {
                        maxValue = c;
                        maxIndex = 2;
                        middleValue = b;
                        middleIndex = 1;
                        minValue = a;
                        minIndex = 0;
                    }
                }
            }
            
            result = middleValue;
            comparisonInfo = String.format("比较前三个元素: %d, %d, %d", a, b, c);
            animationStep++;
        } else if (animationStep == 2) {
            // 步骤3：显示结果
            comparisonInfo = String.format("找到结果: 最小值=%d, 最大值=%d, 中间值=%d", 
                minValue, maxValue, middleValue);
            animationStep++;
        } else {
            // 动画完成
            finishAnimation();
        }
        
        animationPanel.repaint();
    }
    
    private void finishAnimation() {
        isAnimating = false;
        animationTimer.stop();
        setButtonsEnabled(true);
        
        String message = result == -1 ? 
            "没有找到既不是最小值也不是最大值的数字" : 
            "找到既不是最小值也不是最大值的数字: " + result;
        
        JOptionPane.showMessageDialog(this, message, "结果", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void setButtonsEnabled(boolean enabled) {
        startButton.setEnabled(enabled);
        nextButton.setEnabled(!enabled && isAnimating);
    }
    
    private void resetAnimation() {
        nums = null;
        isAnimating = false;
        animationStep = 0;
        result = -1;
        minValue = maxValue = middleValue = 0;
        minIndex = maxIndex = middleIndex = -1;
        comparisonInfo = "";
        
        if (animationTimer != null) {
            animationTimer.stop();
        }
        
        setButtonsEnabled(true);
        animationPanel.repaint();
    }
    
    private void drawAnimation(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // 绘制标题
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 24));
        g2d.setColor(PRIMARY_COLOR);
        String title = "既不是最小值也不是最大值算法演示";
        FontMetrics fm = g2d.getFontMetrics();
        int titleX = (WINDOW_WIDTH - fm.stringWidth(title)) / 2;
        g2d.drawString(title, titleX, 40);
        
        // 绘制算法说明
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawString("算法原理：优化方法只需比较前三个元素，找出中间值即为答案", 50, 70);
        
        if (nums == null) {
            g2d.setFont(new Font("微软雅黑", Font.ITALIC, 16));
            g2d.setColor(Color.GRAY);
            g2d.drawString("请输入数组，然后点击开始动画", 50, 150);
            return;
        }
        
        // 绘制数组
        drawArray(g2d);
        
        // 绘制比较过程
        drawComparison(g2d);
        
        // 绘制结果分析
        drawResultAnalysis(g2d);
        
        // 绘制算法状态
        drawAlgorithmStatus(g2d);
        
        // 绘制复杂度信息
        drawComplexityInfo(g2d);
    }
    
    private void drawArray(Graphics2D g2d) {
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("原数组:", 50, 120);
        
        int cellWidth = 60;
        int cellHeight = 40;
        int startX = 50;
        int startY = 130;
        
        for (int i = 0; i < nums.length; i++) {
            int x = startX + i * (cellWidth + 10);
            
            // 设置颜色
            Color cellColor = ARRAY_COLOR;
            if (isAnimating && animationStep >= 1) {
                if (i == minIndex) {
                    cellColor = MIN_COLOR;
                } else if (i == maxIndex) {
                    cellColor = MAX_COLOR;
                } else if (i == middleIndex) {
                    cellColor = MIDDLE_COLOR;
                } else if (i < 3) {
                    cellColor = HIGHLIGHT_COLOR;
                }
            }
            
            // 绘制数组元素
            g2d.setColor(cellColor);
            g2d.fillRoundRect(x, startY, cellWidth, cellHeight, 8, 8);
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(x, startY, cellWidth, cellHeight, 8, 8);
            
            // 绘制数值
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
            String text = String.valueOf(nums[i]);
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (cellWidth - fm.stringWidth(text)) / 2;
            int textY = startY + (cellHeight + fm.getAscent()) / 2;
            g2d.drawString(text, textX, textY);
            
            // 绘制下标
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
            g2d.setColor(Color.BLUE);
            String indexText = String.valueOf(i);
            int indexX = x + (cellWidth - g2d.getFontMetrics().stringWidth(indexText)) / 2;
            g2d.drawString(indexText, indexX, startY - 5);
            
            // 绘制标签
            if (isAnimating && animationStep >= 2) {
                g2d.setFont(new Font("微软雅黑", Font.PLAIN, 10));
                if (i == minIndex) {
                    g2d.setColor(Color.RED);
                    g2d.drawString("最小", x + 5, startY + cellHeight + 15);
                } else if (i == maxIndex) {
                    g2d.setColor(Color.RED);
                    g2d.drawString("最大", x + 5, startY + cellHeight + 15);
                } else if (i == middleIndex) {
                    g2d.setColor(SUCCESS_COLOR);
                    g2d.drawString("中间", x + 5, startY + cellHeight + 15);
                }
            }
        }
    }
    
    private void drawComparison(Graphics2D g2d) {
        if (!isAnimating || nums.length <= 2) return;
        
        int startY = 220;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("比较前三个元素:", 50, startY);
        
        if (animationStep >= 1) {
            // 绘制前三个元素的比较
            int cellWidth = 80;
            int cellHeight = 50;
            int startX = 70;
            int compY = startY + 20;
            
            for (int i = 0; i < 3; i++) {
                int x = startX + i * (cellWidth + 20);
                
                // 设置颜色
                Color cellColor = ARRAY_COLOR;
                if (animationStep >= 2) {
                    if (i == minIndex) {
                        cellColor = MIN_COLOR;
                    } else if (i == maxIndex) {
                        cellColor = MAX_COLOR;
                    } else if (i == middleIndex) {
                        cellColor = MIDDLE_COLOR;
                    }
                }
                
                // 绘制元素
                g2d.setColor(cellColor);
                g2d.fillRoundRect(x, compY, cellWidth, cellHeight, 10, 10);
                g2d.setColor(Color.BLACK);
                g2d.drawRoundRect(x, compY, cellWidth, cellHeight, 10, 10);
                
                // 绘制数值
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 18));
                String text = String.valueOf(nums[i]);
                FontMetrics fm = g2d.getFontMetrics();
                int textX = x + (cellWidth - fm.stringWidth(text)) / 2;
                int textY = compY + (cellHeight + fm.getAscent()) / 2;
                g2d.drawString(text, textX, textY);
                
                // 绘制标签
                if (animationStep >= 2) {
                    g2d.setFont(new Font("微软雅黑", Font.BOLD, 12));
                    if (i == minIndex) {
                        g2d.setColor(Color.RED);
                        g2d.drawString("最小值", x + 10, compY + cellHeight + 20);
                    } else if (i == maxIndex) {
                        g2d.setColor(Color.RED);
                        g2d.drawString("最大值", x + 10, compY + cellHeight + 20);
                    } else if (i == middleIndex) {
                        g2d.setColor(SUCCESS_COLOR);
                        g2d.drawString("中间值", x + 10, compY + cellHeight + 20);
                        g2d.drawString("(答案)", x + 10, compY + cellHeight + 35);
                    }
                }
            }
        }
    }
    
    private void drawResultAnalysis(Graphics2D g2d) {
        int startY = 360;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("结果分析:", 50, startY);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.BLACK);
        
        if (nums.length <= 2) {
            g2d.setColor(Color.RED);
            g2d.drawString("数组长度 <= 2，不存在既不是最小值也不是最大值的数", 70, startY + 25);
        } else if (animationStep >= 2) {
            g2d.drawString("最小值: " + minValue + " (下标: " + minIndex + ")", 70, startY + 25);
            g2d.drawString("最大值: " + maxValue + " (下标: " + maxIndex + ")", 70, startY + 45);
            g2d.setColor(SUCCESS_COLOR);
            g2d.drawString("中间值: " + middleValue + " (下标: " + middleIndex + ") ← 答案", 70, startY + 65);
        }
        
        // 显示比较信息
        if (!comparisonInfo.isEmpty()) {
            g2d.setFont(new Font("微软雅黑", Font.ITALIC, 14));
            g2d.setColor(HIGHLIGHT_COLOR);
            g2d.drawString(comparisonInfo, 70, startY + 90);
        }
    }
    
    private void drawAlgorithmStatus(Graphics2D g2d) {
        int startY = 480;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("算法状态:", 50, startY);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("数组长度: " + nums.length, 70, startY + 25);
        g2d.drawString("当前结果: " + (result == -1 ? "无" : String.valueOf(result)), 70, startY + 45);
        
        if (isAnimating) {
            g2d.setColor(HIGHLIGHT_COLOR);
            String stepInfo = "";
            switch (animationStep) {
                case 0:
                    stepInfo = "检查数组长度";
                    break;
                case 1:
                    stepInfo = "比较前三个元素";
                    break;
                case 2:
                    stepInfo = "确定最小值、最大值和中间值";
                    break;
                case 3:
                    stepInfo = "算法完成";
                    break;
            }
            g2d.drawString("当前步骤: " + stepInfo, 70, startY + 65);
        }
    }
    
    private void drawComplexityInfo(Graphics2D g2d) {
        int startY = 580;
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("算法复杂度分析:", 50, startY);
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("• 时间复杂度: O(1) - 只需要比较前三个元素", 70, startY + 25);
        g2d.drawString("• 空间复杂度: O(1) - 只使用常数额外空间", 70, startY + 45);
        g2d.drawString("• 优化思路: 不需要排序整个数组，只需找出前三个元素的中间值", 70, startY + 65);
        g2d.drawString("• 算法特点: 任意一个既不是最小值也不是最大值的数都是有效答案", 70, startY + 85);
        
        // 显示当前统计
        g2d.setColor(SUCCESS_COLOR);
        String stats = String.format("数组长度: %d | 结果: %s", 
            nums.length, result == -1 ? "无" : String.valueOf(result));
        g2d.drawString(stats, 70, startY + 110);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO2733_E_FindNonMinOrMax_Animation().setVisible(true);
        });
    }
}