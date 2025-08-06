package com.animation.normal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.animation.normal.permutations.AnimationState;
import com.animation.normal.permutations.BacktrackingPermutation;
import com.animation.normal.permutations.HeapPermutation;
import com.animation.normal.permutations.LexicographicalPermutation;
import com.animation.normal.permutations.PermutationAlgorithm;
import com.animation.normal.permutations2.BacktrackingPermutation2;
import com.animation.normal.Permutation2Adapter;

import java.util.*;
import java.util.List;

/**
 * NO.046 全排列算法动画演示 - 增强版
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
 * 动画特色：
 * - 递归树可视化
 * - 步骤控制（播放/暂停/下一步）
 * - 高亮显示当前选择和回溯过程
 * - 实时显示排列生成过程
 * 
 * 时间复杂度：O(n! * n)
 * 空间复杂度：O(n)
 */
public class PermutationsAnimation extends JPanel {
    private JTextField inputField;
    private JButton generateButton;
    private JButton demoButton;
    private JButton clearButton;
    private JComboBox<String> algorithmComboBox;
    private JButton playButton;
    private JButton pauseButton;
    private JButton nextStepButton;
    private JSlider speedSlider;
    private JTextArea logArea;
    private PermutationPanel visualPanel;
    
    private int[] nums;
    private List<List<Integer>> result;
    private List<Integer> currentPath;
    private boolean[] used;
    private javax.swing.Timer animationTimer;
    private int animationStep;
    private boolean isPlaying;
    private int animationSpeed = 1000; // 毫秒
    
    // 动画状态记录
    private List<AnimationState> animationStates;
    private int currentStateIndex;
    
    public PermutationsAnimation() {
        setLayout(new BorderLayout());
        initializeUI();
        result = new ArrayList<>();
        currentPath = new ArrayList<>();
        animationStates = new ArrayList<>();
        currentStateIndex = 0;
        isPlaying = false;
    }
    
    private void initializeUI() {
        
        // 顶部控制面板
        JPanel topPanel = new JPanel(new BorderLayout());
        
        // 输入控制面板
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("输入数组 (用逗号分隔):"));
        inputField = new JTextField("1,2,3", 15);
        inputPanel.add(inputField);
        
        generateButton = new JButton("生成全排列");
        demoButton = new JButton("演示样例");
        clearButton = new JButton("清空");
        
        inputPanel.add(generateButton);
        inputPanel.add(demoButton);
        inputPanel.add(clearButton);

        String[] algorithms = {"NO046 - 回溯算法", "NO046 - 字典序算法", "NO046 - Heap算法"};
        algorithmComboBox = new JComboBox<>(algorithms);
        inputPanel.add(new JLabel("选择算法:"));
        inputPanel.add(algorithmComboBox);
        
        topPanel.add(inputPanel, BorderLayout.NORTH);
        
        // 动画控制面板
        JPanel animationPanel = new JPanel(new FlowLayout());
        playButton = new JButton("▶ 播放");
        pauseButton = new JButton("⏸ 暂停");
        nextStepButton = new JButton("⏭ 下一步");
        
        animationPanel.add(playButton);
        animationPanel.add(pauseButton);
        animationPanel.add(nextStepButton);
        
        animationPanel.add(new JLabel("速度:"));
        speedSlider = new JSlider(100, 2000, 1000);
        speedSlider.setPreferredSize(new Dimension(100, 30));
        animationPanel.add(speedSlider);
        
        topPanel.add(animationPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);
        
        // 可视化面板
        visualPanel = new PermutationPanel();
        visualPanel.setPreferredSize(new Dimension(1000, 500));
        add(visualPanel, BorderLayout.CENTER);
        
        // 日志面板
        logArea = new JTextArea(8, 50);
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.SOUTH);
        
        // 事件监听
        generateButton.addActionListener(e -> generatePermutations());
        demoButton.addActionListener(e -> {
            inputField.setText("1,2,3");
            generatePermutations();
        });
        clearButton.addActionListener(e -> clearAll());
        
        // 动画控制事件
        playButton.addActionListener(e -> startAnimation());
        pauseButton.addActionListener(e -> pauseAnimation());
        nextStepButton.addActionListener(e -> nextStep());
        
        speedSlider.addChangeListener(e -> {
            animationSpeed = speedSlider.getValue();
            if (animationTimer != null && animationTimer.isRunning()) {
                animationTimer.setDelay(animationSpeed);
            }
        });
        
        // 初始状态设置
        pauseButton.setEnabled(false);
        nextStepButton.setEnabled(false);
        playButton.setEnabled(false);
        

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
            
            if (nums.length > 4) {
                JOptionPane.showMessageDialog(this, "为了演示效果，数组长度不超过4个元素");
                return;
            }
            
            result.clear();
            animationStates.clear();
            currentStateIndex = 0;

            String selectedAlgorithmName = (String) algorithmComboBox.getSelectedItem();
            PermutationAlgorithm algorithm = getAlgorithm(selectedAlgorithmName);

            if (algorithm == null) {
                JOptionPane.showMessageDialog(this, "选择的算法暂未实现");
                return;
            }

            logArea.setText("");
            appendLog("开始生成全排列...");
            appendLog("输入数组: " + Arrays.toString(nums));
            appendLog("使用 " + selectedAlgorithmName + " 生成所有排列\n");

            // 预先生成所有动画状态
            animationStates = algorithm.generateStates(nums);
            
            // 从返回的状态中提取最终结果
            result.clear();
            for (AnimationState state : animationStates) {
                if ("found".equals(state.action)) {
                    result.add(state.foundPermutation);
                }
            }
            
            // 启用动画控制按钮
            playButton.setEnabled(true);
            nextStepButton.setEnabled(true);
            
            appendLog("动画状态生成完成，共 " + animationStates.size() + " 个步骤");
            appendLog("点击播放按钮开始动画演示\n");
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字");
        }
    }
    
    private PermutationAlgorithm getAlgorithm(String name) {
        switch (name) {
            case "NO046 - 回溯算法":
                return new BacktrackingPermutation();
            case "NO046 - 字典序算法":
                return new LexicographicalPermutation();
            case "NO046 - Heap算法":
                return new HeapPermutation();

            default:
                return new BacktrackingPermutation();
        }
    }
    
    private void startAnimation() {
        if (animationStates.isEmpty()) {
            return;
        }
        
        isPlaying = true;
        playButton.setEnabled(false);
        pauseButton.setEnabled(true);
        
        if (animationTimer != null) {
            animationTimer.stop();
        }
        
        animationTimer = new javax.swing.Timer(animationSpeed, e -> {
            if (currentStateIndex < animationStates.size()) {
                showCurrentState();
                currentStateIndex++;
            } else {
                pauseAnimation();
                appendLog("\n全排列生成完成!");
                appendLog("总共找到 " + result.size() + " 个排列:");
                for (int i = 0; i < result.size(); i++) {
                    appendLog((i + 1) + ": " + result.get(i));
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
        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
    }
    
    private void nextStep() {
        if (currentStateIndex < animationStates.size()) {
            showCurrentState();
            currentStateIndex++;
        }
        
        if (currentStateIndex >= animationStates.size()) {
            nextStepButton.setEnabled(false);
            appendLog("\n全排列生成完成!");
            appendLog("总共找到 " + result.size() + " 个排列:");
            for (int i = 0; i < result.size(); i++) {
                appendLog((i + 1) + ": " + result.get(i));
            }
        }
    }
    
    private void showCurrentState() {
        if (currentStateIndex >= animationStates.size()) {
            return;
        }
        
        AnimationState state = animationStates.get(currentStateIndex);
        visualPanel.updateVisualization(state);
        
        // 更新日志
        switch (state.action) {
            case "start":
                appendLog("开始回溯算法");
                break;
            case "choose":
                appendLog("选择元素 " + state.nums[state.chosenIndex] + 
                         ", 当前路径: " + state.currentPath + 
                         ", 深度: " + state.depth);
                break;
            case "backtrack":
                appendLog("回溯，撤销选择 " + state.nums[state.chosenIndex] + 
                         ", 当前路径: " + state.currentPath + 
                         ", 深度: " + state.depth);
                break;
            case "found":
                appendLog("✓ 找到排列: " + state.foundPermutation);
                break;
            case "end":
                appendLog("算法结束");
                break;
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
        private AnimationState currentState;
        
        public void updateVisualization(AnimationState state) {
            this.currentState = state;
            SwingUtilities.invokeLater(() -> visualPanel.repaint());
        }
        
        public void updateVisualization(int[] nums, List<Integer> path, boolean[] used, int depth, List<Integer> permutation) {
            // 保持向后兼容性
            SwingUtilities.invokeLater(() -> visualPanel.repaint());
        }
        
        public void clear() {
            currentState = null;
            SwingUtilities.invokeLater(() -> visualPanel.repaint());
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int width = getWidth();
            int height = getHeight();
            
            // 绘制标题
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 18));
            g2d.setColor(Color.BLACK);
            String algorithmName = (String) algorithmComboBox.getSelectedItem();
            g2d.drawString(algorithmName + " 可视化", 20, 30);
            
            if (currentState == null) {
                return;
            }
            
            // 绘制原始数组
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            g2d.drawString("原始数组: " + Arrays.toString(currentState.nums), 20, 60);
            
            if (currentState.used != null) { // Specific to backtracking
                // 绘制当前路径
                g2d.setColor(Color.BLUE);
                g2d.drawString("当前路径: " + currentState.currentPath, 20, 85);

                // 绘制递归深度
                g2d.setColor(Color.MAGENTA);
                g2d.drawString("递归深度: " + currentState.depth, 20, 110);
            }
            
            // 绘制当前动作
            g2d.setColor(Color.RED);
            String actionText = getActionText(currentState);
            g2d.drawString("当前动作: " + actionText, 20, 135);
            
            // 绘制数组元素状态
            g2d.setColor(Color.BLACK);
            g2d.drawString("数组元素状态:", 20, 170);
            int startX = 20;
            int startY = 190;
            int cellSize = 40;
            
            for (int i = 0; i < currentState.nums.length; i++) {
                int x = startX + i * (cellSize + 10);
                int y = startY;
                
                // Set color based on state
                g2d.setColor(getElementColor(currentState, i));
                
                g2d.fillRect(x, y, cellSize, cellSize);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y, cellSize, cellSize);
                
                // 绘制数字
                g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
                FontMetrics fm = g2d.getFontMetrics();
                String text = String.valueOf(currentState.nums[i]);
                int textX = x + (cellSize - fm.stringWidth(text)) / 2;
                int textY = y + (cellSize + fm.getAscent()) / 2;
                g2d.drawString(text, textX, textY);
                
                // Draw status label for backtracking
                if (currentState.used != null) {
                    g2d.setFont(new Font("微软雅黑", Font.PLAIN, 10));
                    if (currentState.used[i]) {
                        g2d.setColor(Color.RED);
                        g2d.drawString("已用", x, y + cellSize + 15);
                    } else {
                        g2d.setColor(Color.GREEN);
                        g2d.drawString("可用", x, y + cellSize + 15);
                    }
                }
            }
            
            // 绘制已找到的排列
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            g2d.drawString("已找到的排列 (共" + result.size() + "个):", 20, 280);
            int y = 300;
            for (int i = 0; i < result.size() && i < 8; i++) {
                if (currentState.foundPermutation != null && 
                    result.get(i).toString().equals(currentState.foundPermutation.toString())) {
                    g2d.setColor(Color.GREEN);
                    g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
                } else {
                    g2d.setColor(Color.BLACK);
                    g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                }
                g2d.drawString((i + 1) + ": " + result.get(i), 30, y);
                y += 20;
            }
            
            if (result.size() > 8) {
                g2d.setColor(Color.GRAY);
                g2d.drawString("... 还有 " + (result.size() - 8) + " 个排列", 30, y);
            }
        }
    }
    
    private String getActionText(AnimationState state) {
        switch (state.action) {
            case "start": return "开始算法";
            case "choose": return "选择元素: " + state.nums[state.chosenIndex];
            case "backtrack": return "回溯，撤销选择: " + state.nums[state.chosenIndex];
            case "swap":
                if (state.details != null) {
                    return "交换元素: " + state.details.get("index1") + " 和 " + state.details.get("index2");
                }
                return "交换元素";
            case "reverse":
                if (state.details != null) {
                    return "反转子数组 (从索引 " + state.details.get("start_index") + " 开始)";
                }
                return "反转子数组";
            case "found": return "找到排列: " + state.foundPermutation;
            case "end": return "算法结束";
            default: return "";
        }
    }

    private Color getElementColor(AnimationState state, int index) {
        if (state.action.equals("swap") && state.details != null) {
            int index1 = (int) state.details.get("index1");
            int index2 = (int) state.details.get("index2");
            if (index == index1 || index == index2) {
                return Color.CYAN;
            }
        }

        if (state.action.equals("reverse") && state.details != null) {
            int startIndex = (int) state.details.get("start_index");
            if (index >= startIndex) {
                return Color.PINK;
            }
        }

        if (state.used != null) { // Backtracking specific
            if (state.used[index]) {
                return Color.LIGHT_GRAY;
            } else if (state.action.equals("choose") && index == state.chosenIndex) {
                return Color.YELLOW;
            } else if (state.action.equals("backtrack") && index == state.chosenIndex) {
                return Color.ORANGE;
            }
        }

        return Color.WHITE;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PermutationsAnimation().setVisible(true);
        });
    }
}