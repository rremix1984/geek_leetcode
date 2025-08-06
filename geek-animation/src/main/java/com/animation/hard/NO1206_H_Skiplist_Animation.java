package com.animation.hard;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;

/**
 * NO1206 设计跳表 - 动画演示
 * 
 * 动画要点：
 * 1. 跳表结构可视化：多层链表结构展示
 * 2. 搜索过程：从高层到低层的查找路径
 * 3. 插入过程：随机层数生成和节点插入
 * 4. 删除过程：多层节点删除操作
 * 5. 算法复杂度：平均时间O(logn)，空间O(n)
 */
public class NO1206_H_Skiplist_Animation extends JFrame {
    
    // GUI组件
    private JPanel drawPanel;
    private JComboBox<String> operationCombo;
    private JTextField valueField;
    private JButton executeButton, resetButton, autoTestButton;
    private JLabel statusLabel, complexityLabel;
    private Timer animationTimer;
    
    // 动画状态
    private boolean isAnimating = false;
    private int currentStep = 0;
    private int animationSpeed = 1000; // 毫秒
    
    // 跳表数据结构
    private SkiplistNode[] forward;
    private final int MAX_LEVEL = 8;
    private Random random = new Random();
    
    // 可视化数据
    private List<AnimationStep> steps;
    private int currentLevel = -1;
    private SkiplistNode currentNode = null;
    private SkiplistNode targetNode = null;
    private String currentOperation = "";
    private int searchTarget = -1;
    private boolean operationResult = false;
    
    // 颜色定义
    private static final Color PRIMARY_COLOR = new Color(52, 152, 219);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color WARNING_COLOR = new Color(241, 196, 15);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color LIGHT_GRAY = new Color(236, 240, 241);
    private static final Color DARK_GRAY = new Color(149, 165, 166);
    
    // 跳表节点类
    private static class SkiplistNode {
        int data;
        SkiplistNode next;
        SkiplistNode down;
        int level;
        
        SkiplistNode(int data, int level) {
            this.data = data;
            this.level = level;
        }
    }
    
    // 动画步骤类
    private static class AnimationStep {
        String type;
        String description;
        int level;
        SkiplistNode node;
        SkiplistNode target;
        boolean result;
        
        AnimationStep(String type, String description, int level, SkiplistNode node) {
            this.type = type;
            this.description = description;
            this.level = level;
            this.node = node;
        }
        
        AnimationStep(String type, String description, int level, SkiplistNode node, 
                     SkiplistNode target, boolean result) {
            this.type = type;
            this.description = description;
            this.level = level;
            this.node = node;
            this.target = target;
            this.result = result;
        }
    }
    
    public NO1206_H_Skiplist_Animation() {
        initializeSkiplist();
        initializeGUI();
    }
    
    private void initializeSkiplist() {
        forward = new SkiplistNode[MAX_LEVEL];
        SkiplistNode head = new SkiplistNode(-1, MAX_LEVEL - 1);
        
        for (int i = MAX_LEVEL - 1; i >= 0; i--) {
            SkiplistNode tmp = new SkiplistNode(-1, i);
            tmp.down = head;
            forward[i] = head;
            head = tmp;
        }
    }
    
    private void initializeGUI() {
        setTitle("NO1206 设计跳表 - 动画演示");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // 创建绘图面板
        drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawAnimation(g);
            }
        };
        drawPanel.setPreferredSize(new Dimension(1200, 600));
        drawPanel.setBackground(Color.WHITE);
        
        // 创建控制面板
        JPanel controlPanel = createControlPanel();
        
        // 创建信息面板
        JPanel infoPanel = createInfoPanel();
        
        add(drawPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        // 操作选择
        panel.add(new JLabel("操作:"));
        operationCombo = new JComboBox<>(new String[]{"search", "add", "erase"});
        panel.add(operationCombo);
        
        // 值输入
        panel.add(new JLabel("值:"));
        valueField = new JTextField(10);
        panel.add(valueField);
        
        // 执行按钮
        executeButton = new JButton("执行操作");
        executeButton.addActionListener(e -> executeOperation());
        panel.add(executeButton);
        
        // 重置按钮
        resetButton = new JButton("重置跳表");
        resetButton.addActionListener(e -> resetSkiplist());
        panel.add(resetButton);
        
        // 自动测试按钮
        autoTestButton = new JButton("自动测试");
        autoTestButton.addActionListener(e -> autoTest());
        panel.add(autoTestButton);
        
        // 速度控制
        panel.add(new JLabel("速度:"));
        JSlider speedSlider = new JSlider(500, 2000, animationSpeed);
        speedSlider.addChangeListener(e -> animationSpeed = speedSlider.getValue());
        panel.add(speedSlider);
        
        return panel;
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        
        statusLabel = new JLabel("准备执行跳表操作");
        statusLabel.setBorder(BorderFactory.createTitledBorder("操作状态"));
        panel.add(statusLabel);
        
        complexityLabel = new JLabel("平均时间复杂度: O(logn), 空间复杂度: O(n)");
        complexityLabel.setBorder(BorderFactory.createTitledBorder("复杂度分析"));
        panel.add(complexityLabel);
        
        return panel;
    }
    
    private void executeOperation() {
        if (isAnimating) return;
        
        String operation = (String) operationCombo.getSelectedItem();
        String valueText = valueField.getText().trim();
        
        if (valueText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入一个值");
            return;
        }
        
        try {
            int value = Integer.parseInt(valueText);
            currentOperation = operation;
            searchTarget = value;
            
            switch (operation) {
                case "search":
                    animateSearch(value);
                    break;
                case "add":
                    animateAdd(value);
                    break;
                case "erase":
                    animateErase(value);
                    break;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的整数");
        }
    }
    
    private void animateSearch(int target) {
        steps = new ArrayList<>();
        steps.add(new AnimationStep("start", "开始搜索值: " + target, 0, forward[0]));
        
        SkiplistNode localNode = forward[0];
        int level = 0;
        
        while (level < MAX_LEVEL) {
            steps.add(new AnimationStep("check_level", 
                "在第" + (MAX_LEVEL - level - 1) + "层搜索", level, localNode));
            
            if (localNode.data == target) {
                steps.add(new AnimationStep("found", "找到目标值: " + target, level, localNode, localNode, true));
                operationResult = true;
                break;
            } else if (localNode.next != null && localNode.next.data <= target) {
                localNode = localNode.next;
                steps.add(new AnimationStep("move_right", 
                    "向右移动到节点: " + localNode.data, level, localNode));
            } else {
                if (localNode.down != null) {
                    localNode = localNode.down;
                    level++;
                    steps.add(new AnimationStep("move_down", 
                        "向下移动到第" + (MAX_LEVEL - level - 1) + "层", level, localNode));
                } else {
                    break;
                }
            }
        }
        
        if (localNode.data != target) {
            steps.add(new AnimationStep("not_found", "未找到目标值: " + target, level, localNode, null, false));
            operationResult = false;
        }
        
        startAnimation();
    }
    
    private void animateAdd(int num) {
        steps = new ArrayList<>();
        steps.add(new AnimationStep("start", "开始插入值: " + num, -1, null));
        
        // 查找插入位置
        SkiplistNode[] preNodes = new SkiplistNode[MAX_LEVEL];
        for (int i = MAX_LEVEL - 1; i >= 0; i--) {
            SkiplistNode perHead = forward[i];
            while (perHead.next != null && perHead.next.data < num) {
                perHead = perHead.next;
            }
            preNodes[i] = perHead;
            steps.add(new AnimationStep("find_position", 
                "在第" + i + "层找到插入位置", i, perHead));
        }
        
        // 随机生成层数
        int levelNum = randomLevel();
        steps.add(new AnimationStep("random_level", 
            "随机生成层数: " + levelNum, -1, null));
        
        // 插入节点
        SkiplistNode target = new SkiplistNode(num, MAX_LEVEL - 1);
        for (int i = 0; i < levelNum; i++) {
            SkiplistNode tmp = new SkiplistNode(num, MAX_LEVEL - 1 - i);
            tmp.down = target;
            tmp.next = preNodes[MAX_LEVEL - 1 - i].next;
            preNodes[MAX_LEVEL - 1 - i].next = target;
            target = tmp;
            
            steps.add(new AnimationStep("insert_level", 
                "在第" + (MAX_LEVEL - 1 - i) + "层插入节点", MAX_LEVEL - 1 - i, target));
        }
        
        steps.add(new AnimationStep("complete", "插入完成", -1, null));
        operationResult = true;
        startAnimation();
    }
    
    private void animateErase(int num) {
        steps = new ArrayList<>();
        steps.add(new AnimationStep("start", "开始删除值: " + num, -1, null));
        
        // 先搜索是否存在
        if (!searchValue(num)) {
            steps.add(new AnimationStep("not_found", "值不存在，删除失败", -1, null, null, false));
            operationResult = false;
            startAnimation();
            return;
        }
        
        // 删除所有层的节点
        for (int i = MAX_LEVEL - 1; i >= 0; i--) {
            SkiplistNode perHead = forward[i];
            while (perHead.next != null) {
                if (perHead.next.data == num) {
                    SkiplistNode toDelete = perHead.next;
                    perHead.next = perHead.next.next;
                    steps.add(new AnimationStep("delete_level", 
                        "在第" + i + "层删除节点", i, toDelete));
                    break;
                }
                perHead = perHead.next;
            }
        }
        
        steps.add(new AnimationStep("complete", "删除完成", -1, null, null, true));
        operationResult = true;
        startAnimation();
    }
    
    private boolean searchValue(int target) {
        SkiplistNode localNode = forward[0];
        int level = 0;
        
        while (level < MAX_LEVEL) {
            if (localNode.data == target) {
                return true;
            } else if (localNode.next != null && localNode.next.data <= target) {
                localNode = localNode.next;
            } else {
                if (localNode.down != null) {
                    localNode = localNode.down;
                    level++;
                } else {
                    break;
                }
            }
        }
        return localNode.data == target;
    }
    
    private int randomLevel() {
        int level = 1;
        while (level < MAX_LEVEL && random.nextDouble() < 0.5) {
            level++;
        }
        return level;
    }
    
    private void startAnimation() {
        if (steps == null || steps.isEmpty()) return;
        
        isAnimating = true;
        currentStep = 0;
        
        animationTimer = new Timer(animationSpeed, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentStep < steps.size()) {
                    performStep(steps.get(currentStep));
                    currentStep++;
                    SwingUtilities.invokeLater(() -> drawPanel.repaint());
                } else {
                    stopAnimation();
                }
            }
        });
        animationTimer.start();
        
        executeButton.setEnabled(false);
    }
    
    private void stopAnimation() {
        isAnimating = false;
        if (animationTimer != null) {
            animationTimer.stop();
        }
        executeButton.setEnabled(true);
        
        // 显示操作结果
        String result = operationResult ? "成功" : "失败";
        statusLabel.setText(currentOperation + " " + searchTarget + " - " + result);
    }
    
    private void performStep(AnimationStep step) {
        currentLevel = step.level;
        currentNode = step.node;
        targetNode = step.target;
        statusLabel.setText(step.description);
    }
    
    private void resetSkiplist() {
        if (isAnimating) return;
        
        initializeSkiplist();
        currentLevel = -1;
        currentNode = null;
        targetNode = null;
        statusLabel.setText("跳表已重置");
        SwingUtilities.invokeLater(() -> drawPanel.repaint());
    }
    
    private void autoTest() {
        if (isAnimating) return;
        
        // 自动添加一些测试数据
        int[] testData = {1, 3, 7, 9, 12, 19, 25, 28};
        for (int data : testData) {
            addValue(data);
        }
        
        statusLabel.setText("自动测试数据已添加");
        SwingUtilities.invokeLater(() -> drawPanel.repaint());
    }
    
    private void addValue(int num) {
        SkiplistNode[] preNodes = new SkiplistNode[MAX_LEVEL];
        for (int i = MAX_LEVEL - 1; i >= 0; i--) {
            SkiplistNode perHead = forward[i];
            while (perHead.next != null && perHead.next.data < num) {
                perHead = perHead.next;
            }
            preNodes[i] = perHead;
        }
        
        int levelNum = randomLevel();
        SkiplistNode target = new SkiplistNode(num, MAX_LEVEL - 1);
        for (int i = 0; i < levelNum; i++) {
            SkiplistNode tmp = new SkiplistNode(num, MAX_LEVEL - 1 - i);
            tmp.down = target;
            tmp.next = preNodes[MAX_LEVEL - 1 - i].next;
            preNodes[MAX_LEVEL - 1 - i].next = target;
            target = tmp;
        }
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制标题
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 20));
        g2d.setColor(PRIMARY_COLOR);
        g2d.drawString("NO1206 设计跳表", 20, 30);
        
        // 绘制跳表结构
        drawSkiplist(g2d);
        
        // 绘制算法信息
        drawAlgorithmInfo(g2d);
        
        g2d.dispose();
    }
    
    private void drawSkiplist(Graphics2D g2d) {
        int startX = 50;
        int startY = 80;
        int nodeWidth = 60;
        int nodeHeight = 40;
        int levelSpacing = 60;
        int nodeSpacing = 80;
        
        // 绘制层级标签
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 12));
        g2d.setColor(Color.BLACK);
        for (int level = 0; level < MAX_LEVEL; level++) {
            int y = startY + level * levelSpacing;
            g2d.drawString("L" + (MAX_LEVEL - level - 1), 10, y + nodeHeight / 2);
        }
        
        // 绘制每一层的节点
        for (int level = 0; level < MAX_LEVEL; level++) {
            int y = startY + level * levelSpacing;
            SkiplistNode current = forward[level];
            int x = startX;
            
            while (current != null) {
                // 设置节点颜色
                Color nodeColor = LIGHT_GRAY;
                if (current == currentNode && level == currentLevel) {
                    nodeColor = WARNING_COLOR;
                } else if (current == targetNode) {
                    nodeColor = SUCCESS_COLOR;
                } else if (current.data == -1) {
                    nodeColor = DARK_GRAY; // 头节点
                }
                
                // 绘制节点
                g2d.setColor(nodeColor);
                g2d.fill(new Rectangle2D.Double(x, y, nodeWidth, nodeHeight));
                g2d.setColor(Color.BLACK);
                g2d.draw(new Rectangle2D.Double(x, y, nodeWidth, nodeHeight));
                
                // 绘制节点值
                String nodeText = current.data == -1 ? "HEAD" : String.valueOf(current.data);
                FontMetrics fm = g2d.getFontMetrics();
                int textX = x + (nodeWidth - fm.stringWidth(nodeText)) / 2;
                int textY = y + (nodeHeight + fm.getAscent()) / 2;
                g2d.drawString(nodeText, textX, textY);
                
                // 绘制水平连接线
                if (current.next != null) {
                    g2d.setStroke(new BasicStroke(2));
                    g2d.drawLine(x + nodeWidth, y + nodeHeight / 2, 
                                x + nodeSpacing, y + nodeHeight / 2);
                    
                    // 绘制箭头
                    int arrowX = x + nodeSpacing - 10;
                    int arrowY = y + nodeHeight / 2;
                    g2d.drawLine(arrowX, arrowY, arrowX - 5, arrowY - 3);
                    g2d.drawLine(arrowX, arrowY, arrowX - 5, arrowY + 3);
                }
                
                // 绘制垂直连接线（down指针）
                if (current.down != null && level < MAX_LEVEL - 1) {
                    g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, 
                                                BasicStroke.JOIN_BEVEL, 0, new float[]{3}, 0));
                    g2d.setColor(Color.BLUE);
                    g2d.drawLine(x + nodeWidth / 2, y + nodeHeight, 
                                x + nodeWidth / 2, y + levelSpacing);
                    g2d.setColor(Color.BLACK);
                    g2d.setStroke(new BasicStroke(1));
                }
                
                current = current.next;
                x += nodeSpacing;
            }
        }
    }
    
    private void drawAlgorithmInfo(Graphics2D g2d) {
        int startX = 50;
        int startY = 500;
        
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        g2d.setColor(Color.BLACK);
        
        g2d.drawString("跳表信息:", startX, startY);
        g2d.drawString("• 最大层数: " + MAX_LEVEL, startX, startY + 20);
        g2d.drawString("• 当前操作: " + currentOperation, startX, startY + 40);
        g2d.drawString("• 目标值: " + (searchTarget >= 0 ? searchTarget : "无"), startX, startY + 60);
        
        // 图例
        startX = 400;
        g2d.drawString("图例:", startX, startY);
        
        // 普通节点
        g2d.setColor(LIGHT_GRAY);
        g2d.fillRect(startX, startY + 15, 20, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(startX, startY + 15, 20, 15);
        g2d.drawString("普通节点", startX + 25, startY + 27);
        
        // 当前节点
        g2d.setColor(WARNING_COLOR);
        g2d.fillRect(startX, startY + 35, 20, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(startX, startY + 35, 20, 15);
        g2d.drawString("当前节点", startX + 25, startY + 47);
        
        // 目标节点
        g2d.setColor(SUCCESS_COLOR);
        g2d.fillRect(startX, startY + 55, 20, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(startX, startY + 55, 20, 15);
        g2d.drawString("目标节点", startX + 25, startY + 67);
        
        // 头节点
        g2d.setColor(DARK_GRAY);
        g2d.fillRect(startX, startY + 75, 20, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(startX, startY + 75, 20, 15);
        g2d.drawString("头节点", startX + 25, startY + 87);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO1206_H_Skiplist_Animation().setVisible(true);
        });
    }
}