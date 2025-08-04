package com.animation.normal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * NO.046 全排列算法动画演示
 * 
 * 算法描述：
 * 给定一个不含重复数字的数组 nums，返回其所有可能的全排列。
 * 
 * 算法思路：
 * 使用回溯算法，通过递归的方式生成所有排列
 * 1. 选择一个元素加入当前排列
 * 2. 递归生成剩余元素的排列
 * 3. 回溯，撤销选择
 * 
 * 时间复杂度：O(n! * n)
 * 空间复杂度：O(n)
 */
public class NO046_N_Permutations_Animation extends JFrame {
    private JTextField inputField;
    private JButton generateButton;
    private JButton demoButton;
    private JButton clearButton;
    private JTextArea logArea;
    private PermutationPanel visualPanel;
    
    private int[] nums;
    private List<List<Integer>> result;
    private List<Integer> currentPath;
    private boolean[] used;
    private javax.swing.Timer animationTimer;
    private int animationStep;
    
    public NO046_N_Permutations_Animation() {
        initializeUI();
        result = new ArrayList<>();
        currentPath = new ArrayList<>();
    }
    
    private void initializeUI() {
        setTitle("NO.046 全排列算法动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // 控制面板
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(new JLabel("输入数组 (用逗号分隔):"));
        inputField = new JTextField("1,2,3", 15);
        controlPanel.add(inputField);
        
        generateButton = new JButton("生成全排列");
        demoButton = new JButton("演示样例");
        clearButton = new JButton("清空");
        
        controlPanel.add(generateButton);
        controlPanel.add(demoButton);
        controlPanel.add(clearButton);
        
        add(controlPanel, BorderLayout.NORTH);
        
        // 可视化面板
        visualPanel = new PermutationPanel();
        visualPanel.setPreferredSize(new Dimension(800, 400));
        add(visualPanel, BorderLayout.CENTER);
        
        // 日志面板
        logArea = new JTextArea(8, 50);
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.SOUTH);
        
        // 事件监听
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePermutations();
            }
        });
        
        demoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputField.setText("1,2,3");
                generatePermutations();
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
    
    private void generatePermutations() {
        try {
            String input = inputField.getText().trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入数组元素");
                return;
            }
            
            String[] parts = input.split(",");
            nums = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                nums[i] = Integer.parseInt(parts[i].trim());
            }
            
            result.clear();
            currentPath.clear();
            used = new boolean[nums.length];
            animationStep = 0;
            
            logArea.setText("");
            appendLog("开始生成全排列...");
            appendLog("输入数组: " + Arrays.toString(nums));
            appendLog("使用回溯算法生成所有排列\n");
            
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
                if (animationStep == 0) {
                    backtrack(0);
                    animationTimer.stop();
                    appendLog("\n全排列生成完成!");
                    appendLog("总共找到 " + result.size() + " 个排列:");
                    for (int i = 0; i < result.size(); i++) {
                        appendLog((i + 1) + ": " + result.get(i));
                    }
                }
                animationStep++;
            }
        });
        animationTimer.start();
    }
    
    private void backtrack(int depth) {
        // 递归终止条件
        if (depth == nums.length) {
            List<Integer> permutation = new ArrayList<>(currentPath);
            result.add(permutation);
            appendLog("找到排列: " + permutation);
            visualPanel.updateVisualization(nums, currentPath, used, depth, permutation);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return;
        }
        
        // 尝试每个未使用的元素
        for (int i = 0; i < nums.length; i++) {
            if (!used[i]) {
                // 选择
                currentPath.add(nums[i]);
                used[i] = true;
                appendLog("选择元素 " + nums[i] + ", 当前路径: " + currentPath);
                visualPanel.updateVisualization(nums, currentPath, used, depth + 1, null);
                
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
                // 递归
                backtrack(depth + 1);
                
                // 回溯
                currentPath.remove(currentPath.size() - 1);
                used[i] = false;
                appendLog("回溯，撤销选择 " + nums[i] + ", 当前路径: " + currentPath);
                visualPanel.updateVisualization(nums, currentPath, used, depth, null);
                
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    
    private void clearAll() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        inputField.setText("");
        logArea.setText("");
        result.clear();
        currentPath.clear();
        visualPanel.clear();
    }
    
    private void appendLog(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }
    
    // 可视化面板
    private class PermutationPanel extends JPanel {
        private int[] currentNums;
        private List<Integer> currentPath;
        private boolean[] used;
        private int currentDepth;
        private List<Integer> foundPermutation;
        
        public void updateVisualization(int[] nums, List<Integer> path, boolean[] used, int depth, List<Integer> permutation) {
            this.currentNums = nums.clone();
            this.currentPath = new ArrayList<>(path);
            this.used = used.clone();
            this.currentDepth = depth;
            this.foundPermutation = permutation;
            repaint();
        }
        
        public void clear() {
            currentNums = null;
            currentPath = null;
            used = null;
            foundPermutation = null;
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (currentNums == null) return;
            
            int width = getWidth();
            int height = getHeight();
            
            // 绘制标题
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.setColor(Color.BLACK);
            g2d.drawString("全排列生成过程可视化", 20, 30);
            
            // 绘制原始数组
            g2d.setFont(new Font("Arial", Font.PLAIN, 14));
            g2d.drawString("原始数组:", 20, 60);
            
            int startX = 120;
            int startY = 45;
            int cellSize = 40;
            
            for (int i = 0; i < currentNums.length; i++) {
                // 根据是否被使用设置颜色
                if (used != null && used[i]) {
                    g2d.setColor(Color.LIGHT_GRAY);
                } else {
                    g2d.setColor(Color.WHITE);
                }
                
                g2d.fillRect(startX + i * (cellSize + 5), startY, cellSize, cellSize);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(startX + i * (cellSize + 5), startY, cellSize, cellSize);
                
                // 绘制数字
                String num = String.valueOf(currentNums[i]);
                FontMetrics fm = g2d.getFontMetrics();
                int textX = startX + i * (cellSize + 5) + (cellSize - fm.stringWidth(num)) / 2;
                int textY = startY + (cellSize + fm.getAscent()) / 2;
                g2d.drawString(num, textX, textY);
            }
            
            // 绘制当前路径
            if (currentPath != null && !currentPath.isEmpty()) {
                g2d.drawString("当前路径:", 20, 120);
                
                for (int i = 0; i < currentPath.size(); i++) {
                    g2d.setColor(Color.GREEN);
                    g2d.fillRect(startX + i * (cellSize + 5), 105, cellSize, cellSize);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(startX + i * (cellSize + 5), 105, cellSize, cellSize);
                    
                    String num = String.valueOf(currentPath.get(i));
                    FontMetrics fm = g2d.getFontMetrics();
                    int textX = startX + i * (cellSize + 5) + (cellSize - fm.stringWidth(num)) / 2;
                    int textY = 105 + (cellSize + fm.getAscent()) / 2;
                    g2d.drawString(num, textX, textY);
                }
            }
            
            // 绘制找到的排列
            if (foundPermutation != null) {
                g2d.setColor(Color.BLUE);
                g2d.drawString("找到排列: " + foundPermutation, 20, 180);
                
                for (int i = 0; i < foundPermutation.size(); i++) {
                    g2d.setColor(new Color(0, 255, 255));
                    g2d.fillRect(startX + i * (cellSize + 5), 190, cellSize, cellSize);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(startX + i * (cellSize + 5), 190, cellSize, cellSize);
                    
                    String num = String.valueOf(foundPermutation.get(i));
                    FontMetrics fm = g2d.getFontMetrics();
                    int textX = startX + i * (cellSize + 5) + (cellSize - fm.stringWidth(num)) / 2;
                    int textY = 190 + (cellSize + fm.getAscent()) / 2;
                    g2d.drawString(num, textX, textY);
                }
            }
            
            // 绘制递归深度
            g2d.setColor(Color.BLACK);
            g2d.drawString("递归深度: " + currentDepth, 20, 280);
            
            // 绘制已找到的排列数量
            g2d.drawString("已找到排列数: " + result.size(), 20, 300);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO046_N_Permutations_Animation().setVisible(true);
        });
    }
}