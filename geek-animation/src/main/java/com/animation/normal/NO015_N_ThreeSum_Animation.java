package com.animation.normal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * NO015 三数之和 动画演示
 * 
 * 题目描述：
 * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c，
 * 使得 a + b + c = 0？请你找出所有和为 0 且不重复的三元组。
 * 
 * @author AI Assistant
 */
public class NO015_N_ThreeSum_Animation extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    // UI组件
    private JTextField inputField;
    private JButton findButton;
    private JButton clearButton;
    private JButton demoButton;
    private JTextArea logArea;
    private JPanel visualPanel;
    
    // 数据
    private int[] nums;
    private List<List<Integer>> results;
    private int currentI;
    private int currentLeft;
    private int currentRight;
    private int currentSum;
    private boolean isAnimating;
    
    public NO015_N_ThreeSum_Animation() {
        results = new ArrayList<>();
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("NO015 - 三数之和 动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // 顶部控制面板
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.NORTH);
        
        // 中央可视化面板
        visualPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawVisualization(g);
            }
        };
        visualPanel.setBackground(Color.WHITE);
        visualPanel.setPreferredSize(new Dimension(900, 500));
        add(visualPanel, BorderLayout.CENTER);
        
        // 底部日志面板
        logArea = new JTextArea(8, 50);
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("操作日志"));
        add(scrollPane, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        
        addLog("三数之和算法初始化完成");
        addLog("请输入数组，格式: -1,0,1,2,-1,-4");
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        panel.add(new JLabel("输入数组:"));
        inputField = new JTextField(25);
        inputField.setText("-1,0,1,2,-1,-4");
        panel.add(inputField);
        
        findButton = new JButton("查找三元组");
        findButton.addActionListener(e -> findThreeSum());
        panel.add(findButton);
        
        demoButton = new JButton("演示样例");
        demoButton.addActionListener(e -> loadDemoData());
        panel.add(demoButton);
        
        clearButton = new JButton("清空");
        clearButton.addActionListener(e -> clearVisualization());
        panel.add(clearButton);
        
        return panel;
    }
    
    private void findThreeSum() {
        String input = inputField.getText().trim();
        if (input.isEmpty()) {
            addLog("请输入有效的数组");
            return;
        }
        
        try {
            String[] parts = input.split(",");
            nums = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                nums[i] = Integer.parseInt(parts[i].trim());
            }
        } catch (NumberFormatException e) {
            addLog("输入格式错误，请使用逗号分隔的整数");
            return;
        }
        
        results.clear();
        currentI = -1;
        currentLeft = -1;
        currentRight = -1;
        currentSum = 0;
        
        addLog("开始查找数组 " + Arrays.toString(nums) + " 的三数之和");
        addLog("首先对数组进行排序...");
        
        // 启动动画线程
        new Thread(this::animateThreeSum).start();
    }
    
    private void animateThreeSum() {
        isAnimating = true;
        
        // 排序
        Arrays.sort(nums);
        addLog("排序后: " + Arrays.toString(nums));
        
        SwingUtilities.invokeLater(() -> visualPanel.repaint());
        sleep(1000);
        
        for (int i = 0; i < nums.length - 2; i++) {
            // 跳过重复元素
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            currentI = i;
            int left = i + 1;
            int right = nums.length - 1;
            
            addLog("固定第一个数: nums[" + i + "] = " + nums[i]);
            
            while (left < right) {
                currentLeft = left;
                currentRight = right;
                currentSum = nums[i] + nums[left] + nums[right];
                
                addLog("检查三元组: [" + nums[i] + ", " + nums[left] + ", " + nums[right] + "] = " + currentSum);
                
                SwingUtilities.invokeLater(() -> visualPanel.repaint());
                sleep(800);
                
                if (currentSum == 0) {
                    List<Integer> triplet = Arrays.asList(nums[i], nums[left], nums[right]);
                    results.add(triplet);
                    addLog("✓ 找到三元组: " + triplet);
                    
                    // 跳过重复元素
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    
                    left++;
                    right--;
                } else if (currentSum < 0) {
                    addLog("和太小，左指针右移");
                    left++;
                } else {
                    addLog("和太大，右指针左移");
                    right--;
                }
                
                sleep(500);
            }
        }
        
        currentI = -1;
        currentLeft = -1;
        currentRight = -1;
        
        addLog("查找完成！共找到 " + results.size() + " 个三元组");
        for (List<Integer> result : results) {
            addLog("结果: " + result);
        }
        
        SwingUtilities.invokeLater(() -> visualPanel.repaint());
        isAnimating = false;
    }
    
    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private void loadDemoData() {
        inputField.setText("-1,0,1,2,-1,-4");
        addLog("加载演示数据: [-1,0,1,2,-1,-4]");
    }
    
    private void clearVisualization() {
        nums = null;
        results.clear();
        currentI = -1;
        currentLeft = -1;
        currentRight = -1;
        currentSum = 0;
        logArea.setText("");
        visualPanel.repaint();
        addLog("可视化已清空");
    }
    
    private void drawVisualization(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (nums == null) {
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.setColor(Color.GRAY);
            g2d.drawString("请输入数组开始演示", 50, 200);
            return;
        }
        
        int startX = 50;
        int startY = 80;
        int cellWidth = 50;
        int cellHeight = 40;
        
        // 绘制数组
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        for (int i = 0; i < nums.length; i++) {
            int x = startX + i * cellWidth;
            
            // 设置颜色
            if (i == currentI) {
                g2d.setColor(Color.RED); // 固定的第一个数
            } else if (i == currentLeft) {
                g2d.setColor(Color.GREEN); // 左指针
            } else if (i == currentRight) {
                g2d.setColor(Color.BLUE); // 右指针
            } else {
                g2d.setColor(Color.LIGHT_GRAY);
            }
            
            // 绘制单元格背景
            g2d.fillRect(x, startY, cellWidth, cellHeight);
            
            // 绘制边框
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, startY, cellWidth, cellHeight);
            
            // 绘制数值
            String text = String.valueOf(nums[i]);
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (cellWidth - fm.stringWidth(text)) / 2;
            int textY = startY + (cellHeight + fm.getAscent()) / 2;
            g2d.drawString(text, textX, textY);
            
            // 绘制索引
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.drawString(String.valueOf(i), x + cellWidth/2 - 5, startY - 10);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
        }
        
        // 绘制指针标签
        if (currentI != -1) {
            g2d.setColor(Color.RED);
            g2d.drawString("i", startX + currentI * cellWidth + cellWidth/2 - 5, startY + cellHeight + 20);
        }
        if (currentLeft != -1) {
            g2d.setColor(Color.GREEN);
            g2d.drawString("left", startX + currentLeft * cellWidth + cellWidth/2 - 10, startY + cellHeight + 35);
        }
        if (currentRight != -1) {
            g2d.setColor(Color.BLUE);
            g2d.drawString("right", startX + currentRight * cellWidth + cellWidth/2 - 10, startY + cellHeight + 50);
        }
        
        // 绘制当前和
        if (currentI != -1 && currentLeft != -1 && currentRight != -1) {
            g2d.setFont(new Font("Arial", Font.BOLD, 18));
            g2d.setColor(Color.BLACK);
            String sumText = "当前和: " + nums[currentI] + " + " + nums[currentLeft] + " + " + nums[currentRight] + " = " + currentSum;
            g2d.drawString(sumText, startX, startY + 100);
            
            // 显示判断结果
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            if (currentSum == 0) {
                g2d.setColor(Color.GREEN);
                g2d.drawString("✓ 找到目标三元组！", startX, startY + 130);
            } else if (currentSum < 0) {
                g2d.setColor(Color.ORANGE);
                g2d.drawString("和太小，需要增大 → 左指针右移", startX, startY + 130);
            } else {
                g2d.setColor(Color.ORANGE);
                g2d.drawString("和太大，需要减小 → 右指针左移", startX, startY + 130);
            }
        }
        
        // 绘制结果
        if (!results.isEmpty()) {
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.setColor(Color.BLUE);
            g2d.drawString("找到的三元组:", startX, startY + 180);
            
            g2d.setFont(new Font("Arial", Font.PLAIN, 14));
            int y = startY + 200;
            for (int i = 0; i < Math.min(results.size(), 6); i++) {
                g2d.drawString("• " + results.get(i), startX + 20, y);
                y += 20;
            }
            
            if (results.size() > 6) {
                g2d.drawString("... 还有 " + (results.size() - 6) + " 个", startX + 20, y);
            }
        }
        
        // 绘制算法说明
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.setColor(Color.GRAY);
        g2d.drawString("算法步骤: 1.排序 2.固定第一个数 3.双指针查找", startX, startY + 350);
        g2d.drawString("时间复杂度: O(n²), 空间复杂度: O(1)", startX, startY + 370);
    }
    
    private void addLog(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO015_N_ThreeSum_Animation().setVisible(true);
        });
    }
}