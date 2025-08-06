package com.animation.normal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * NO915 分割数组 动画演示
 * 
 * 题目描述：
 * 给定一个数组 nums，将其划分为两个连续的子数组 left 和 right，使得：
 * - left 中的每个元素都小于或等于 right 中的每个元素。
 * - left 和 right 都是非空的。
 * - left 的长度要尽可能小。
 * 
 * @author AI Assistant
 */
public class NO915_N_PartitionDisjoint_Animation extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    // UI组件
    private JTextField arrayField;
    private JButton parseButton;
    private JButton solveButton;
    private JButton stepButton;
    private JButton resetButton;
    private JTextArea logArea;
    private JPanel visualPanel;
    private JLabel resultLabel;
    
    // 数据
    private int[] nums;
    private int partitionIndex;
    private boolean solved;
    private int currentStep;
    private int[] leftMax;
    private int[] rightMin;
    
    // 动画相关
    private static final int BAR_WIDTH = 40;
    private static final int BAR_SPACING = 5;
    private static final Color LEFT_COLOR = new Color(100, 150, 255);
    private static final Color RIGHT_COLOR = new Color(255, 150, 100);
    private static final Color CURRENT_COLOR = Color.YELLOW;
    private static final Color PARTITION_COLOR = Color.RED;
    
    public NO915_N_PartitionDisjoint_Animation() {
        initializeUI();
        loadDefaultData();
    }
    
    private void initializeUI() {
        setTitle("NO915 - 分割数组 动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // 顶部控制面板
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.NORTH);
        
        // 中央面板
        JPanel centerPanel = new JPanel(new BorderLayout());
        
        // 可视化面板
        visualPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawVisualization(g);
            }
        };
        visualPanel.setBackground(Color.WHITE);
        visualPanel.setPreferredSize(new Dimension(800, 400));
        centerPanel.add(visualPanel, BorderLayout.CENTER);
        
        // 右侧信息面板
        JPanel infoPanel = createInfoPanel();
        centerPanel.add(infoPanel, BorderLayout.EAST);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // 底部日志面板
        logArea = new JTextArea(8, 50);
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("操作日志"));
        add(scrollPane, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        
        addLog("分割数组算法初始化完成");
        addLog("请输入数组，然后点击求解或单步执行");
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("操作控制"));
        
        panel.add(new JLabel("输入数组 (用逗号分隔):"));
        
        arrayField = new JTextField("5,0,3,8,6", 20);
        panel.add(arrayField);
        
        parseButton = new JButton("解析数组");
        parseButton.addActionListener(e -> parseArray());
        panel.add(parseButton);
        
        solveButton = new JButton("直接求解");
        solveButton.addActionListener(e -> solve());
        panel.add(solveButton);
        
        stepButton = new JButton("单步执行");
        stepButton.addActionListener(e -> stepSolve());
        panel.add(stepButton);
        
        resetButton = new JButton("重置");
        resetButton.addActionListener(e -> reset());
        panel.add(resetButton);
        
        // 预设按钮
        JButton demoButton = new JButton("演示数据");
        demoButton.addActionListener(e -> loadDemoData());
        panel.add(demoButton);
        
        return panel;
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("算法信息"));
        panel.setPreferredSize(new Dimension(250, 400));
        
        resultLabel = new JLabel("<html>分割位置: 未计算</html>");
        resultLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        panel.add(resultLabel);
        
        panel.add(Box.createVerticalStrut(20));
        
        JLabel instructionLabel = new JLabel("<html><b>算法说明:</b><br/>" +
            "目标: 找到最小的分割点，使得左边所有元素 ≤ 右边所有元素<br/><br/>" +
            "<b>算法步骤:</b><br/>" +
            "1. 预处理: 计算每个位置左边的最大值和右边的最小值<br/>" +
            "2. 遍历: 找到第一个满足 leftMax[i] ≤ rightMin[i+1] 的位置<br/>" +
            "3. 返回: 该位置就是最小分割点<br/><br/>" +
            "<b>可视化说明:</b><br/>" +
            "• 蓝色: 左子数组<br/>" +
            "• 橙色: 右子数组<br/>" +
            "• 红线: 分割位置<br/>" +
            "• 黄色: 当前检查位置<br/><br/>" +
            "<b>时间复杂度:</b> O(n)<br/>" +
            "<b>空间复杂度:</b> O(n)</html>");
        instructionLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        panel.add(instructionLabel);
        
        return panel;
    }
    
    private void loadDefaultData() {
        arrayField.setText("5,0,3,8,6");
        parseArray();
    }
    
    private void parseArray() {
        try {
            String input = arrayField.getText().trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入数组", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String[] parts = input.split(",");
            nums = new int[parts.length];
            
            for (int i = 0; i < parts.length; i++) {
                nums[i] = Integer.parseInt(parts[i].trim());
            }
            
            if (nums.length < 2) {
                JOptionPane.showMessageDialog(this, "数组长度至少为2", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            reset();
            addLog("解析数组: " + Arrays.toString(nums));
            SwingUtilities.invokeLater(() -> visualPanel.repaint());
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void solve() {
        if (nums == null) {
            JOptionPane.showMessageDialog(this, "请先解析数组", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        addLog("开始求解分割数组...");
        
        // 预处理：计算左边最大值和右边最小值
        preprocessArrays();
        
        // 找分割点
        partitionIndex = findPartitionIndex();
        
        solved = true;
        currentStep = nums.length;
        
        String result = "分割位置: " + partitionIndex + " (左子数组长度: " + (partitionIndex + 1) + ")";
        resultLabel.setText("<html>" + result + "</html>");
        addLog(result);
        addLog("左子数组: " + Arrays.toString(Arrays.copyOfRange(nums, 0, partitionIndex + 1)));
        addLog("右子数组: " + Arrays.toString(Arrays.copyOfRange(nums, partitionIndex + 1, nums.length)));
        
        SwingUtilities.invokeLater(() -> visualPanel.repaint());
    }
    
    private void stepSolve() {
        if (nums == null) {
            JOptionPane.showMessageDialog(this, "请先解析数组", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (currentStep == 0) {
            addLog("开始单步求解...");
            preprocessArrays();
            addLog("预处理完成，计算了每个位置的左最大值和右最小值");
        }
        
        if (currentStep < nums.length - 1) {
            int i = currentStep;
            addLog("检查位置 " + i + ": leftMax[" + i + "] = " + leftMax[i] + 
                   ", rightMin[" + (i + 1) + "] = " + rightMin[i + 1]);
            
            if (leftMax[i] <= rightMin[i + 1]) {
                partitionIndex = i;
                solved = true;
                addLog("找到分割点: " + i + " (满足条件: " + leftMax[i] + " ≤ " + rightMin[i + 1] + ")");
                
                String result = "分割位置: " + partitionIndex + " (左子数组长度: " + (partitionIndex + 1) + ")";
                resultLabel.setText("<html>" + result + "</html>");
            } else {
                addLog("位置 " + i + " 不满足条件: " + leftMax[i] + " > " + rightMin[i + 1]);
            }
            
            currentStep++;
        } else {
            addLog("单步执行完成");
        }
        
        SwingUtilities.invokeLater(() -> visualPanel.repaint());
    }
    
    private void preprocessArrays() {
        int n = nums.length;
        leftMax = new int[n];
        rightMin = new int[n];
        
        // 计算左边最大值
        leftMax[0] = nums[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], nums[i]);
        }
        
        // 计算右边最小值
        rightMin[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMin[i] = Math.min(rightMin[i + 1], nums[i]);
        }
    }
    
    private int findPartitionIndex() {
        for (int i = 0; i < nums.length - 1; i++) {
            if (leftMax[i] <= rightMin[i + 1]) {
                return i;
            }
        }
        return nums.length - 2; // 默认返回倒数第二个位置
    }
    
    private void reset() {
        solved = false;
        currentStep = 0;
        partitionIndex = -1;
        leftMax = null;
        rightMin = null;
        resultLabel.setText("<html>分割位置: 未计算</html>");
        addLog("重置算法状态");
        SwingUtilities.invokeLater(() -> visualPanel.repaint());
    }
    
    private void loadDemoData() {
        String[] demos = {"5,0,3,8,6", "1,1,1,0,6,12", "1,2,3,4,5"};
        int choice = JOptionPane.showOptionDialog(this, 
            "选择演示数据:", "演示数据", 
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
            demos, demos[0]);
        
        if (choice >= 0) {
            arrayField.setText(demos[choice]);
            parseArray();
            addLog("加载演示数据: " + demos[choice]);
        }
    }
    
    private void drawVisualization(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (nums == null) {
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
            g2d.setColor(Color.GRAY);
            String message = "请先解析数组";
            FontMetrics fm = g2d.getFontMetrics();
            int x = (visualPanel.getWidth() - fm.stringWidth(message)) / 2;
            int y = visualPanel.getHeight() / 2;
            g2d.drawString(message, x, y);
            return;
        }
        
        drawArray(g2d);
        drawAuxiliaryArrays(g2d);
        drawPartition(g2d);
    }
    
    private void drawArray(Graphics2D g2d) {
        int n = nums.length;
        int totalWidth = n * BAR_WIDTH + (n - 1) * BAR_SPACING;
        int startX = (visualPanel.getWidth() - totalWidth) / 2;
        int baseY = 150;
        
        // 找到最大值用于缩放
        int maxVal = Arrays.stream(nums).max().orElse(1);
        int minVal = Arrays.stream(nums).min().orElse(0);
        int range = Math.max(maxVal - minVal, 1);
        
        // 绘制数组元素
        for (int i = 0; i < n; i++) {
            int x = startX + i * (BAR_WIDTH + BAR_SPACING);
            int height = Math.max(20, (nums[i] - minVal) * 80 / range + 20);
            int y = baseY - height;
            
            // 确定颜色
            Color barColor;
            if (!solved) {
                if (i < currentStep) {
                    barColor = Color.LIGHT_GRAY;
                } else if (i == currentStep) {
                    barColor = CURRENT_COLOR;
                } else {
                    barColor = Color.WHITE;
                }
            } else {
                if (i <= partitionIndex) {
                    barColor = LEFT_COLOR;
                } else {
                    barColor = RIGHT_COLOR;
                }
            }
            
            // 绘制柱状图
            g2d.setColor(barColor);
            g2d.fillRect(x, y, BAR_WIDTH, height);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, BAR_WIDTH, height);
            
            // 绘制数值
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
            String value = String.valueOf(nums[i]);
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (BAR_WIDTH - fm.stringWidth(value)) / 2;
            int textY = y + height / 2 + fm.getAscent() / 2;
            g2d.drawString(value, textX, textY);
            
            // 绘制索引
            g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
            String index = String.valueOf(i);
            int indexX = x + (BAR_WIDTH - g2d.getFontMetrics().stringWidth(index)) / 2;
            g2d.drawString(index, indexX, baseY + 15);
        }
        
        // 绘制标题
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("原数组: " + Arrays.toString(nums), 20, 30);
    }
    
    private void drawAuxiliaryArrays(Graphics2D g2d) {
        if (leftMax == null || rightMin == null) return;
        
        int n = nums.length;
        int totalWidth = n * BAR_WIDTH + (n - 1) * BAR_SPACING;
        int startX = (visualPanel.getWidth() - totalWidth) / 2;
        
        // 绘制leftMax数组
        g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        g2d.setColor(Color.BLUE);
        g2d.drawString("leftMax:", 20, 200);
        
        for (int i = 0; i < n; i++) {
            int x = startX + i * (BAR_WIDTH + BAR_SPACING);
            g2d.drawString(String.valueOf(leftMax[i]), x + 5, 200);
        }
        
        // 绘制rightMin数组
        g2d.setColor(Color.RED);
        g2d.drawString("rightMin:", 20, 220);
        
        for (int i = 0; i < n; i++) {
            int x = startX + i * (BAR_WIDTH + BAR_SPACING);
            g2d.drawString(String.valueOf(rightMin[i]), x + 5, 220);
        }
    }
    
    private void drawPartition(Graphics2D g2d) {
        if (!solved || partitionIndex < 0) return;
        
        int n = nums.length;
        int totalWidth = n * BAR_WIDTH + (n - 1) * BAR_SPACING;
        int startX = (visualPanel.getWidth() - totalWidth) / 2;
        
        // 绘制分割线
        int partitionX = startX + (partitionIndex + 1) * (BAR_WIDTH + BAR_SPACING) - BAR_SPACING / 2;
        g2d.setColor(PARTITION_COLOR);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(partitionX, 50, partitionX, 180);
        
        // 绘制分割标签
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        g2d.drawString("分割线", partitionX - 15, 45);
        
        // 绘制左右子数组标签
        g2d.setColor(LEFT_COLOR);
        g2d.drawString("左子数组", startX, 250);
        
        g2d.setColor(RIGHT_COLOR);
        int rightStartX = startX + (partitionIndex + 1) * (BAR_WIDTH + BAR_SPACING);
        g2d.drawString("右子数组", rightStartX, 250);
    }
    
    private void addLog(String message) {
        logArea.append("[" + java.time.LocalTime.now().toString().substring(0, 8) + "] " + message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO915_N_PartitionDisjoint_Animation().setVisible(true);
        });
    }
}