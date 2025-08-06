package com.animation.hard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.List;

/**
 * NO.23 合并K个升序链表 - 增强动画演示
 * 
 * 算法描述：
 * 给你一个链表数组，每个链表都已经按升序排列。
 * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
 * 
 * 算法思路：
 * 使用优先队列（最小堆）来维护各个链表的当前最小节点
 * 1. 将每个链表的头节点加入优先队列
 * 2. 每次从队列中取出最小的节点
 * 3. 将该节点的next节点（如果存在）加入队列
 * 4. 重复直到队列为空
 * 
 * 时间复杂度：O(N*log(k)) 其中N是所有节点总数，k是链表个数
 * 空间复杂度：O(k)
 */
public class NO023_H_MergeKSortedLists_Animation extends JFrame {
    private JPanel visualPanel;
    private JPanel controlPanel;
    private JButton startButton;
    private JButton resetButton;
    private JButton stepButton;
    private JSlider speedSlider;
    private JTextArea logArea;
    private Timer animationTimer;
    private PriorityQueue<ListNodeWithIndex> pq;
    private List<ListNode> originalLists;
    private List<ListNode> currentPointers;
    private List<AnimationNode> resultList;
    private List<AnimationNode> animationNodes; // 用于动画的节点列表
    private boolean isRunning = false;
    private int animationSpeed = 1000;

    // 辅助类：带索引的链表节点，用于优先队列
    private static class ListNodeWithIndex {
        ListNode node;
        int listIndex;
        
        public ListNodeWithIndex(ListNode node, int listIndex) {
            this.node = node;
            this.listIndex = listIndex;
        }
    }
    
    public NO023_H_MergeKSortedLists_Animation() {
        setTitle("NO.23 合并K个升序链表 - 增强动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        initializeData();
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private void initializeComponents() {
        // 控制面板
        controlPanel = new JPanel(new FlowLayout());
        startButton = new JButton("▶ 开始合并");
        resetButton = new JButton("🔄 重置");
        stepButton = new JButton("⏭ 单步执行");
        
        controlPanel.add(startButton);
        controlPanel.add(stepButton);
        controlPanel.add(resetButton);
        
        controlPanel.add(new JLabel("速度:"));
        speedSlider = new JSlider(100, 2000, 1000);
        speedSlider.setPreferredSize(new Dimension(100, 30));
        controlPanel.add(speedSlider);
        
        // 可视化面板
        visualPanel = new VisualizationPanel();
        visualPanel.setPreferredSize(new Dimension(900, 400));
        visualPanel.setBackground(Color.WHITE);
        visualPanel.setBorder(BorderFactory.createTitledBorder("合并过程可视化"));
        
        // 日志面板
        logArea = new JTextArea(8, 50);
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        logArea.setBorder(BorderFactory.createTitledBorder("执行日志"));
    }

    private void setupLayout() {
        add(controlPanel, BorderLayout.NORTH);
        add(visualPanel, BorderLayout.CENTER);
        add(new JScrollPane(logArea), BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        startButton.addActionListener(e -> {
            if (!isRunning) {
                startAnimation();
            } else {
                pauseAnimation();
            }
        });
        
        stepButton.addActionListener(e -> executeStep());
        resetButton.addActionListener(e -> resetAnimation());
        
        speedSlider.addChangeListener(e -> {
            animationSpeed = speedSlider.getValue();
            if (animationTimer != null && animationTimer.isRunning()) {
                animationTimer.setDelay(animationSpeed);
            }
        });
    }
    
    private void initializeData() {
        // 初始化示例数据
        originalLists = new ArrayList<>();
        animationNodes = new ArrayList<>();
        resultList = new ArrayList<>();
        currentPointers = new ArrayList<>();

        // 创建示例链表
        originalLists.add(createList(new int[]{1, 4, 5}));
        originalLists.add(createList(new int[]{1, 3, 4}));
        originalLists.add(createList(new int[]{2, 6}));

        resetAnimation();
    }
    
    private ListNode createList(int[] values) {
        if (values.length == 0) return null;
        
        ListNode head = new ListNode(values[0]);
        ListNode current = head;
        
        for (int i = 1; i < values.length; i++) {
            current.next = new ListNode(values[i]);
            current = current.next;
        }
        
        return head;
    }
    
    private void startAnimation() {
        isRunning = true;
        startButton.setText("⏸ 暂停");
        stepButton.setEnabled(false);
        
        animationTimer = new Timer(animationSpeed, e -> executeStep());
        animationTimer.start();
        
        appendLog("开始合并K个升序链表...");
    }
    
    private void pauseAnimation() {
        isRunning = false;
        startButton.setText("▶ 继续");
        stepButton.setEnabled(true);
        
        if (animationTimer != null) {
            animationTimer.stop();
        }
        
        appendLog("动画已暂停");
    }
    
    private void executeStep() {
        if (!pq.isEmpty()) {
            // 1. 从优先队列中取出最小节点
            ListNodeWithIndex minNodeWithIndex = pq.poll();
            ListNode minNode = minNodeWithIndex.node;
            int listIndex = minNodeWithIndex.listIndex;

            // 2. 找到对应的动画节点并移动到结果区
            for (AnimationNode node : animationNodes) {
                if (node.listNode == minNode) {
                    node.isHighlighted = true;
                    int resultIndex = resultList.size();
                    node.moveTo(620 + resultIndex * 45, 60);
                    resultList.add(node);
                    break;
                }
            }

            // 3. 更新日志
            appendLog(String.format("从链表%d取出节点%d，加入结果。", listIndex + 1, minNode.val));

            // 4. 将该链表的下一个节点加入优先队列
            currentPointers.set(listIndex, minNode.next);
            if (minNode.next != null) {
                pq.offer(new ListNodeWithIndex(minNode.next, listIndex));
                appendLog(String.format("将链表%d的下一个节点%d加入优先队列。", listIndex + 1, minNode.next.val));
            }

            SwingUtilities.invokeLater(() -> visualPanel.repaint());
        } else {
            // 合并完成
            if (animationTimer != null) {
                animationTimer.stop();
            }
            isRunning = false;
            startButton.setText("✅ 完成");
            startButton.setEnabled(false);
            stepButton.setEnabled(false);

            appendLog("合并完成！");
        }
    }
    
    private void resetAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }

        isRunning = false;
        startButton.setText("▶ 开始合并");
        startButton.setEnabled(true);
        stepButton.setEnabled(true);

        // 重置数据
        resultList.clear();
        animationNodes.clear();
        currentPointers.clear();
        pq = new PriorityQueue<>((a, b) -> a.node.val - b.node.val);

        // 创建动画节点并初始化指针和优先队列
        int startY = 60;
        int listHeight = 50;
        int nodeSize = 35;
        for (int i = 0; i < originalLists.size(); i++) {
            ListNode head = originalLists.get(i);
            currentPointers.add(head);
            if (head != null) {
                pq.offer(new ListNodeWithIndex(head, i));
            }

            ListNode current = head;
            int x = 100;
            int y = startY + i * listHeight;
            while (current != null) {
                Color color = ((VisualizationPanel) visualPanel).LIST_COLORS[i % ((VisualizationPanel) visualPanel).LIST_COLORS.length];
                animationNodes.add(new AnimationNode(current, i, x, y, color));
                current = current.next;
                x += nodeSize + 20;
            }
        }

        logArea.setText("");
        appendLog("动画已重置，准备开始合并");
        appendLog("输入链表:");
        for (int i = 0; i < originalLists.size(); i++) {
            appendLog(String.format("链表%d: %s", i + 1, listToString(originalLists.get(i))));
        }

        SwingUtilities.invokeLater(() -> visualPanel.repaint());
    }
    
    private String listToString(ListNode head) {
        if (head == null) return "[]";;
        
        StringBuilder sb = new StringBuilder("[");
        ListNode current = head;
        while (current != null) {
            sb.append(current.val);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }
    
    private void appendLog(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }

    // 动画节点类
    private static class AnimationNode {
        ListNode listNode;
        int listIndex;
        double currentX, currentY; // 当前位置
        double targetX, targetY; // 目标位置
        Color color;
        boolean isHighlighted;

        public AnimationNode(ListNode listNode, int listIndex, int x, int y, Color color) {
            this.listNode = listNode;
            this.listIndex = listIndex;
            this.currentX = x;
            this.currentY = y;
            this.targetX = x;
            this.targetY = y;
            this.color = color;
            this.isHighlighted = false;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AnimationNode that = (AnimationNode) o;
            return listNode.equals(that.listNode);
        }

        @Override
        public int hashCode() {
            return listNode.hashCode();
        }

        public void moveTo(int targetX, int targetY) {
            this.targetX = targetX;
            this.targetY = targetY;
        }

        public void updatePosition() {
            double dx = targetX - currentX;
            double dy = targetY - currentY;
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < 1) {
                currentX = targetX;
                currentY = targetY;
            } else {
                // Move towards the target with a fixed speed
                double speed = 0.1;
                currentX += dx * speed;
                currentY += dy * speed;
            }
        }

        public void draw(Graphics2D g2d) {
            g2d.setColor(isHighlighted ? Color.YELLOW : color);
            g2d.fillOval((int)currentX, (int)currentY, 35, 35);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            String text = String.valueOf(listNode.val);
            int textWidth = g2d.getFontMetrics().stringWidth(text);
            g2d.drawString(text, (int)currentX + (35 - textWidth) / 2, (int)currentY + 23);
        }
        
        @Override
        public String toString() {
            return String.valueOf(listNode.val);
        }
    }

    // 可视化面板类
    private class VisualizationPanel extends JPanel implements ActionListener {
        private final Color[] LIST_COLORS = {
            new Color(255, 182, 193), // 浅粉色
            new Color(173, 216, 230), // 浅蓝色
            new Color(144, 238, 144), // 浅绿色
            new Color(255, 218, 185), // 桃色
            new Color(221, 160, 221), // 梅花色
        };

        public VisualizationPanel() {
            Timer timer = new Timer(16, this); // ~60 FPS
            timer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 更新所有节点位置
            for (AnimationNode node : animationNodes) {
                node.updatePosition();
            }
            for (AnimationNode node : resultList) {
                node.updatePosition();
            }

            // 绘制标题
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.drawString("输入链表", 20, 30);
            g2d.drawString("优先队列 (Min-Heap)", 300, 30);
            g2d.drawString("合并后链表", 600, 30);

            // 绘制所有动画节点
            for (AnimationNode node : animationNodes) {
                node.draw(g2d);
            }

            // 绘制结果链表
            for (AnimationNode node : resultList) {
                node.draw(g2d);
            }

            // 绘制优先队列
            if (pq != null && !pq.isEmpty()) {
                int pqX = 320;
                int pqY = 60;
                int nodeSize = 35;
                g2d.setColor(Color.DARK_GRAY);
                g2d.drawRect(pqX - 10, pqY - 20, 150, 200);

                // 创建一个副本进行绘制，避免并发修改问题
                List<ListNodeWithIndex> pqCopy = new ArrayList<>(pq);
                pqCopy.sort((a, b) -> a.node.val - b.node.val);

                for (ListNodeWithIndex nodeWithIndex : pqCopy) {


                    g2d.setColor(LIST_COLORS[nodeWithIndex.listIndex % LIST_COLORS.length]);
                    g2d.fillOval(pqX, pqY, nodeSize, nodeSize);
                    g2d.setColor(Color.WHITE);
                    String text = String.valueOf(nodeWithIndex.node.val);
                    int textWidth = g2d.getFontMetrics().stringWidth(text);
                    g2d.drawString(text, pqX + (nodeSize - textWidth) / 2, pqY + 23);
                    pqY += 45;
                }
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(() -> repaint());
        }
    }
    
    private static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ListNode listNode = (ListNode) o;
            return val == listNode.val &&
                    java.util.Objects.equals(next, listNode.next);
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(val, next);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NO023_H_MergeKSortedLists_Animation().setVisible(true));
    }
}