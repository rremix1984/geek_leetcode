package com.animation.normal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * NO.047 全排列II算法动画演示
 * 
 * 算法描述：
 * 给定一个可包含重复数字的序列nums，按任意顺序返回所有不重复的全排列。
 * 
 * 算法思路：
 * 使用回溯算法，通过排序和剪枝来避免重复排列
 * 1. 首先对数组进行排序，使相同元素相邻
 * 2. 使用visited数组记录元素是否被使用
 * 3. 在递归过程中，如果当前元素与前一个元素相同且前一个元素未被使用，则跳过
 * 4. 这样可以确保相同元素的使用顺序，避免重复排列
 * 
 * 时间复杂度：O(n! * n)
 * 空间复杂度：O(n)
 */
public class NO047_N_PermutationsII_Animation extends JFrame {
    private JTextField inputField;
    private JButton generateButton;
    private JButton demoButton;
    private JButton clearButton;
    private JButton playButton;
    private JButton pauseButton;
    private JButton nextStepButton;
    private JSlider speedSlider;
    private JTextArea logArea;
    private PermutationPanel visualPanel;
    
    private int[] nums;
    private List<List<Integer>> result;
    private List<Integer> currentPath;
    private boolean[] visited;
    private javax.swing.Timer animationTimer;
    private int animationStep;
    private boolean isPlaying;
    private int animationSpeed = 1000; // 毫秒
    
    // 动画状态记录
    private List<AnimationState> animationStates;
    private int currentStateIndex;
    
    // 动画状态类
    private static class AnimationState {
        int[] nums;
        List<Integer> currentPath;
        boolean[] visited;
        int currentIndex;
        String action; // "try", "skip", "backtrack", "found"
        int tryingValue;
        List<Integer> foundResult;
        String reason;
        
        public AnimationState(int[] nums, List<Integer> path, boolean[] visited, 
                            int index, String action, int tryingValue, 
                            List<Integer> foundResult, String reason) {
            this.nums = nums.clone();
            this.currentPath = new ArrayList<>(path);
            this.visited = visited.clone();
            this.currentIndex = index;
            this.action = action;
            this.tryingValue = tryingValue;
            this.foundResult = foundResult != null ? new ArrayList<>(foundResult) : null;
            this.reason = reason;
        }
    }
    
    public NO047_N_PermutationsII_Animation() {
        initializeUI();
        result = new ArrayList<>();
        currentPath = new ArrayList<>();
        animationStates = new ArrayList<>();
        currentStateIndex = 0;
        isPlaying = false;
    }
    
    private void initializeUI() {
        setTitle("NO.047 全排列II算法动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // 顶部控制面板
        JPanel topPanel = new JPanel(new BorderLayout());
        
        // 输入控制面板
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("输入数组(逗号分隔):"));
        inputField = new JTextField("1,1,2", 15);
        inputPanel.add(inputField);
        
        generateButton = new JButton("生成排列");
        demoButton = new JButton("演示样例");
        clearButton = new JButton("清空");
        
        inputPanel.add(generateButton);
        inputPanel.add(demoButton);
        inputPanel.add(clearButton);
        
        // 动画控制面板
        JPanel animationPanel = new JPanel(new FlowLayout());
        playButton = new JButton("播放动画");
        pauseButton = new JButton("暂停");
        nextStepButton = new JButton("下一步");
        speedSlider = new JSlider(100, 2000, 1000);
        speedSlider.setMajorTickSpacing(500);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        
        animationPanel.add(new JLabel("动画控制:"));
        animationPanel.add(playButton);
        animationPanel.add(pauseButton);
        animationPanel.add(nextStepButton);
        animationPanel.add(new JLabel("速度:"));
        animationPanel.add(speedSlider);
        
        topPanel.add(inputPanel, BorderLayout.NORTH);
        topPanel.add(animationPanel, BorderLayout.SOUTH);
        
        // 可视化面板
        visualPanel = new PermutationPanel();
        visualPanel.setPreferredSize(new Dimension(800, 400));
        
        // 日志面板
        logArea = new JTextArea(10, 30);
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane logScrollPane = new JScrollPane(logArea);
        logScrollPane.setPreferredSize(new Dimension(300, 400));
        
        // 返回按钮
        JButton backButton = new JButton("返回首页");
        backButton.addActionListener(e -> {
            dispose();
            com.animation.launcher.AlgorithmTreeLauncher.showMainWindow();
        });
        
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(backButton);
        
        add(topPanel, BorderLayout.NORTH);
        add(visualPanel, BorderLayout.CENTER);
        add(logScrollPane, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
        
        // 事件监听器
        generateButton.addActionListener(e -> generatePermutations());
        demoButton.addActionListener(e -> {
            inputField.setText("1,1,2");
            generatePermutations();
        });
        clearButton.addActionListener(e -> clearAll());
        playButton.addActionListener(e -> startAnimation());
        pauseButton.addActionListener(e -> pauseAnimation());
        nextStepButton.addActionListener(e -> nextStep());
        speedSlider.addChangeListener(e -> {
            animationSpeed = speedSlider.getValue();
            if (animationTimer != null) {
                animationTimer.setDelay(animationSpeed);
            }
        });
        
        setSize(1200, 600);
        setLocationRelativeTo(null);
    }
    
    private void generatePermutations() {
        try {
            String input = inputField.getText().trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入有效的数组！");
                return;
            }
            
            String[] parts = input.split(",");
            nums = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                nums[i] = Integer.parseInt(parts[i].trim());
            }
            
            // 排序数组
            Arrays.sort(nums);
            
            result.clear();
            animationStates.clear();
            currentStateIndex = 0;
            visited = new boolean[nums.length];
            
            appendLog("开始生成全排列II...");
            appendLog("输入数组: " + Arrays.toString(nums));
            appendLog("排序后: " + Arrays.toString(nums));
            
            generateAnimationStates();
            
            appendLog("生成完成！共找到 " + result.size() + " 个不重复排列");
            for (int i = 0; i < result.size(); i++) {
                appendLog((i + 1) + ". " + result.get(i));
            }
            
            visualPanel.updateVisualization(nums, new ArrayList<>(), new boolean[nums.length], 0, null);
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "输入格式错误，请输入数字！");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "输入格式错误: " + ex.getMessage());
        }
    }
    
    private void generateAnimationStates() {
        currentPath = new ArrayList<>();
        backtrackForStates();
    }
    
    private void backtrackForStates() {
        if (currentPath.size() == nums.length) {
            List<Integer> found = new ArrayList<>(currentPath);
            result.add(found);
            animationStates.add(new AnimationState(nums, currentPath, visited, 
                currentPath.size(), "found", -1, found, "找到一个完整排列"));
            return;
        }
        
        for (int i = 0; i < nums.length; i++) {
            // 如果当前元素已被使用，跳过
            if (visited[i]) {
                animationStates.add(new AnimationState(nums, currentPath, visited, 
                    i, "skip", nums[i], null, "元素已被使用"));
                continue;
            }
            
            // 剪枝：如果当前元素与前一个元素相同，且前一个元素未被使用，跳过
            if (i > 0 && nums[i] == nums[i-1] && !visited[i-1]) {
                animationStates.add(new AnimationState(nums, currentPath, visited, 
                    i, "skip", nums[i], null, "避免重复：相同元素" + nums[i] + "的前一个未使用"));
                continue;
            }
            
            // 尝试选择当前元素
            animationStates.add(new AnimationState(nums, currentPath, visited, 
                i, "try", nums[i], null, "尝试选择元素" + nums[i]));
            
            visited[i] = true;
            currentPath.add(nums[i]);
            
            backtrackForStates();
            
            // 回溯
            currentPath.remove(currentPath.size() - 1);
            visited[i] = false;
            animationStates.add(new AnimationState(nums, currentPath, visited, 
                i, "backtrack", nums[i], null, "回溯，移除元素" + nums[i]));
        }
    }
    
    private void startAnimation() {
        if (animationStates.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请先生成排列！");
            return;
        }
        
        isPlaying = true;
        currentStateIndex = 0;
        
        animationTimer = new javax.swing.Timer(animationSpeed, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentStateIndex < animationStates.size()) {
                    showCurrentState();
                    currentStateIndex++;
                } else {
                    pauseAnimation();
                }
            }
        });
        animationTimer.start();
    }
    
    private void pauseAnimation() {
        isPlaying = false;
        if (animationTimer != null) {
            animationTimer.stop();
        }
    }
    
    private void nextStep() {
        if (animationStates.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请先生成排列！");
            return;
        }
        
        if (currentStateIndex < animationStates.size()) {
            showCurrentState();
            currentStateIndex++;
        }
    }
    
    private void showCurrentState() {
        if (currentStateIndex >= animationStates.size()) return;
        
        AnimationState state = animationStates.get(currentStateIndex);
        visualPanel.updateVisualization(state);
        
        String logMessage = "步骤 " + (currentStateIndex + 1) + ": ";
        switch (state.action) {
            case "try":
                logMessage += "尝试选择 nums[" + state.currentIndex + "] = " + state.tryingValue;
                break;
            case "skip":
                logMessage += "跳过 nums[" + state.currentIndex + "] = " + state.tryingValue + " (" + state.reason + ")";
                break;
            case "backtrack":
                logMessage += "回溯，移除 " + state.tryingValue;
                break;
            case "found":
                logMessage += "找到一个排列: " + state.foundResult;
                break;
        }
        logMessage += " | 当前路径: " + state.currentPath;
        
        appendLog(logMessage);
    }
    
    private void clearAll() {
        inputField.setText("");
        result.clear();
        animationStates.clear();
        currentStateIndex = 0;
        logArea.setText("");
        visualPanel.clear();
        pauseAnimation();
    }
    
    private void appendLog(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private class PermutationPanel extends JPanel {
        private AnimationState currentState;
        private int[] nums;
        private List<Integer> currentPath;
        private boolean[] visited;
        private int currentIndex;
        private List<Integer> foundResult;
        
        public void updateVisualization(AnimationState state) {
            this.currentState = state;
            this.nums = state.nums;
            this.currentPath = state.currentPath;
            this.visited = state.visited;
            this.currentIndex = state.currentIndex;
            this.foundResult = state.foundResult;
            repaint();
        }
        
        public void updateVisualization(int[] nums, List<Integer> path, boolean[] visited, int index, List<Integer> result) {
            this.nums = nums;
            this.currentPath = path;
            this.visited = visited;
            this.currentIndex = index;
            this.foundResult = result;
            this.currentState = null;
            repaint();
        }
        
        public void clear() {
            this.currentState = null;
            this.nums = null;
            this.currentPath = null;
            this.visited = null;
            this.foundResult = null;
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (nums == null) return;
            
            int width = getWidth();
            int height = getHeight();
            
            // 绘制标题
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.setColor(Color.BLACK);
            g2d.drawString("全排列II动画演示 (避免重复)", 20, 30);
            
            // 绘制原数组
            g2d.setFont(new Font("Arial", Font.PLAIN, 14));
            g2d.drawString("排序后数组: " + Arrays.toString(nums), 20, 60);
            
            // 绘制当前路径
            if (currentPath != null) {
                g2d.drawString("当前路径: " + currentPath.toString(), 20, 90);
            }
            
            // 绘制数组元素选择状态
            int startX = 50;
            int startY = 120;
            int cellWidth = 50;
            int cellHeight = 40;
            
            for (int i = 0; i < nums.length; i++) {
                int x = startX + i * (cellWidth + 10);
                int y = startY;
                
                // 根据状态设置颜色
                if (visited != null && visited[i]) {
                    g2d.setColor(Color.GREEN); // 已选择
                } else if (currentState != null && i == currentState.currentIndex) {
                    if ("try".equals(currentState.action)) {
                        g2d.setColor(Color.YELLOW); // 正在尝试
                    } else if ("skip".equals(currentState.action)) {
                        g2d.setColor(Color.RED); // 跳过
                    } else {
                        g2d.setColor(Color.ORANGE); // 回溯
                    }
                } else {
                    g2d.setColor(Color.LIGHT_GRAY); // 未选择
                }
                
                g2d.fillRect(x, y, cellWidth, cellHeight);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y, cellWidth, cellHeight);
                
                // 绘制数字
                g2d.setFont(new Font("Arial", Font.BOLD, 16));
                FontMetrics fm = g2d.getFontMetrics();
                String numStr = String.valueOf(nums[i]);
                int textX = x + (cellWidth - fm.stringWidth(numStr)) / 2;
                int textY = y + (cellHeight + fm.getAscent()) / 2;
                g2d.drawString(numStr, textX, textY);
                
                // 绘制索引
                g2d.setFont(new Font("Arial", Font.PLAIN, 10));
                g2d.drawString("[" + i + "]", x + 2, y - 5);
            }
            
            // 绘制当前操作说明
            if (currentState != null) {
                g2d.setFont(new Font("Arial", Font.BOLD, 14));
                g2d.setColor(Color.BLUE);
                String actionText = "";
                switch (currentState.action) {
                    case "try":
                        actionText = "尝试选择: nums[" + currentState.currentIndex + "] = " + currentState.tryingValue;
                        break;
                    case "skip":
                        actionText = "跳过: " + currentState.reason;
                        break;
                    case "backtrack":
                        actionText = "回溯: 移除 " + currentState.tryingValue;
                        break;
                    case "found":
                        actionText = "找到排列: " + currentState.foundResult;
                        break;
                }
                g2d.drawString(actionText, 20, startY + 80);
            }
            
            // 绘制递归树（简化版）
            if (currentPath != null && !currentPath.isEmpty()) {
                g2d.setFont(new Font("Arial", Font.PLAIN, 12));
                g2d.setColor(Color.BLACK);
                g2d.drawString("递归路径:", 20, startY + 120);
                
                int treeX = 50;
                int treeY = startY + 140;
                
                for (int i = 0; i < currentPath.size(); i++) {
                    // 绘制路径节点
                    g2d.setColor(Color.CYAN);
                    g2d.fillOval(treeX + i * 60, treeY, 30, 30);
                    g2d.setColor(Color.BLACK);
                    g2d.drawOval(treeX + i * 60, treeY, 30, 30);
                    
                    // 绘制数字
                    String pathNum = String.valueOf(currentPath.get(i));
                    FontMetrics fm = g2d.getFontMetrics();
                    int textX = treeX + i * 60 + (30 - fm.stringWidth(pathNum)) / 2;
                    int textY = treeY + (30 + fm.getAscent()) / 2;
                    g2d.drawString(pathNum, textX, textY);
                    
                    // 绘制连接线
                    if (i > 0) {
                        g2d.drawLine(treeX + (i-1) * 60 + 30, treeY + 15, treeX + i * 60, treeY + 15);
                    }
                }
            }
            
            // 绘制所有结果
            if (result != null && !result.isEmpty()) {
                g2d.setFont(new Font("Arial", Font.PLAIN, 12));
                g2d.setColor(Color.BLACK);
                g2d.drawString("所有不重复排列 (共" + result.size() + "个):", 20, height - 120);
                
                int resultY = height - 100;
                int resultX = 20;
                int maxPerLine = 6;
                
                for (int i = 0; i < result.size(); i++) {
                    if (i > 0 && i % maxPerLine == 0) {
                        resultY += 20;
                        resultX = 20;
                    }
                    
                    List<Integer> resultList = result.get(i);
                    if (foundResult != null && foundResult.equals(resultList)) {
                        g2d.setColor(Color.RED);
                        g2d.setFont(new Font("Arial", Font.BOLD, 12));
                    } else {
                        g2d.setColor(Color.BLACK);
                        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
                    }
                    
                    String resultStr = resultList.toString();
                    g2d.drawString(resultStr, resultX, resultY);
                    resultX += g2d.getFontMetrics().stringWidth(resultStr) + 15;
                }
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO047_N_PermutationsII_Animation().setVisible(true);
        });
    }
}