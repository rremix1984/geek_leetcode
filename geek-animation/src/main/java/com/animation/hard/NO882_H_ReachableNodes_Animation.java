package com.animation.hard;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.*;
import java.util.List;

/**
 * NO882 细分图中的可到达节点 - 动画演示
 * 
 * 动画要点：
 * 1. 图的可视化：显示原始图和细分后的图
 * 2. Dijkstra算法过程：优先队列、距离更新、节点访问
 * 3. 边上节点的计算：显示每条边上可到达的节点数
 * 4. 实时统计：当前可到达节点总数
 * 5. 算法复杂度：时间O((V+E)logV)，空间O(V+E)
 */
public class NO882_H_ReachableNodes_Animation extends JFrame {
    
    // GUI组件
    private JPanel drawPanel;
    private JComboBox<String> testCaseCombo;
    private JButton startButton, pauseButton, resetButton, nextStepButton;
    private JLabel statusLabel, complexityLabel;
    private Timer animationTimer;
    
    // 动画状态
    private boolean isAnimating = false;
    private boolean isPaused = false;
    private int currentStep = 0;
    private int animationSpeed = 1000; // 毫秒
    
    // 算法数据
    private int[][] edges;
    private int maxMoves;
    private int n;
    private List<int[]>[] adjList;
    private Map<Integer, Integer> used;
    private Set<Integer> visited;
    private PriorityQueue<int[]> pq;
    private int reachableNodes;
    private List<AnimationStep> steps;
    
    // 可视化数据
    private Point[] nodePositions;
    private Map<String, Integer> edgeSubNodes; // 边上的细分节点数
    private Map<String, Integer> edgeUsed; // 边上已使用的节点数
    private int currentNode = -1;
    private String currentEdge = "";
    
    // 测试用例
    private TestCase[] testCases = {
        new TestCase("示例1", new int[][]{{0,1,10},{0,2,1},{1,2,2}}, 6, 3),
        new TestCase("示例2", new int[][]{{0,1,4},{1,2,6},{0,2,8},{1,3,1}}, 10, 4),
        new TestCase("示例3", new int[][]{{1,2,4},{1,4,5},{1,3,1},{2,3,4},{3,4,5}}, 17, 5),
        new TestCase("简单图", new int[][]{{0,1,2},{1,2,3}}, 5, 3)
    };
    
    // 动画步骤类
    private static class AnimationStep {
        String type;
        String description;
        int node;
        String edge;
        int distance;
        int usedNodes;
        
        AnimationStep(String type, String description, int node, String edge, int distance, int usedNodes) {
            this.type = type;
            this.description = description;
            this.node = node;
            this.edge = edge;
            this.distance = distance;
            this.usedNodes = usedNodes;
        }
    }
    
    // 测试用例类
    private static class TestCase {
        String name;
        int[][] edges;
        int maxMoves;
        int n;
        
        TestCase(String name, int[][] edges, int maxMoves, int n) {
            this.name = name;
            this.edges = edges;
            this.maxMoves = maxMoves;
            this.n = n;
        }
        
        @Override
        public String toString() {
            return name;
        }
    }
    
    public NO882_H_ReachableNodes_Animation() {
        initializeGUI();
        loadTestCase(0);
    }
    
    @SuppressWarnings("unchecked")
    private void initializeGUI() {
        setTitle("NO882 细分图中的可到达节点 - 动画演示");
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
        drawPanel.setPreferredSize(new Dimension(1000, 600));
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
        
        // 测试用例选择
        panel.add(new JLabel("测试用例:"));
        testCaseCombo = new JComboBox<>();
        for (TestCase testCase : testCases) {
            testCaseCombo.addItem(testCase.toString());
        }
        testCaseCombo.addActionListener(e -> {
            if (!isAnimating) {
                loadTestCase(testCaseCombo.getSelectedIndex());
                SwingUtilities.invokeLater(() -> drawPanel.repaint());
            }
        });
        panel.add(testCaseCombo);
        
        // 控制按钮
        startButton = new JButton("开始动画");
        startButton.addActionListener(e -> startAnimation());
        panel.add(startButton);
        
        pauseButton = new JButton("暂停");
        pauseButton.addActionListener(e -> pauseAnimation());
        pauseButton.setEnabled(false);
        panel.add(pauseButton);
        
        resetButton = new JButton("重置");
        resetButton.addActionListener(e -> resetAnimation());
        panel.add(resetButton);
        
        nextStepButton = new JButton("下一步");
        nextStepButton.addActionListener(e -> nextStep());
        panel.add(nextStepButton);
        
        // 速度控制
        panel.add(new JLabel("速度:"));
        JSlider speedSlider = new JSlider(100, 2000, animationSpeed);
        speedSlider.addChangeListener(e -> animationSpeed = speedSlider.getValue());
        panel.add(speedSlider);
        
        return panel;
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        
        statusLabel = new JLabel("准备开始动画演示");
        statusLabel.setBorder(BorderFactory.createTitledBorder("算法状态"));
        panel.add(statusLabel);
        
        complexityLabel = new JLabel("时间复杂度: O((V+E)logV), 空间复杂度: O(V+E)");
        complexityLabel.setBorder(BorderFactory.createTitledBorder("复杂度分析"));
        panel.add(complexityLabel);
        
        return panel;
    }
    
    private void loadTestCase(int index) {
        TestCase testCase = testCases[index];
        this.edges = testCase.edges;
        this.maxMoves = testCase.maxMoves;
        this.n = testCase.n;
        
        // 初始化数据结构
        adjList = (List<int[]>[]) new List[n];
        for (int i = 0; i < n; i++) {
            adjList[i] = new ArrayList<>();
        }
        
        edgeSubNodes = new HashMap<>();
        edgeUsed = new HashMap<>();
        
        // 构建邻接表
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], nodes = edge[2];
            adjList[u].add(new int[]{v, nodes});
            adjList[v].add(new int[]{u, nodes});
            
            String edgeKey1 = u + "-" + v;
            String edgeKey2 = v + "-" + u;
            edgeSubNodes.put(edgeKey1, nodes);
            edgeSubNodes.put(edgeKey2, nodes);
            edgeUsed.put(edgeKey1, 0);
            edgeUsed.put(edgeKey2, 0);
        }
        
        // 计算节点位置
        calculateNodePositions();
        
        resetAnimation();
    }
    
    private void calculateNodePositions() {
        nodePositions = new Point[n];
        int centerX = 500, centerY = 300;
        int radius = 200;
        
        if (n == 1) {
            nodePositions[0] = new Point(centerX, centerY);
        } else {
            for (int i = 0; i < n; i++) {
                double angle = 2 * Math.PI * i / n;
                int x = centerX + (int)(radius * Math.cos(angle));
                int y = centerY + (int)(radius * Math.sin(angle));
                nodePositions[i] = new Point(x, y);
            }
        }
    }
    
    private void startAnimation() {
        if (isPaused) {
            isPaused = false;
            animationTimer.start();
        } else {
            setupAnimation();
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
        }
        
        startButton.setEnabled(false);
        pauseButton.setEnabled(true);
        testCaseCombo.setEnabled(false);
    }
    
    private void pauseAnimation() {
        isPaused = true;
        animationTimer.stop();
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
    }
    
    private void stopAnimation() {
        isAnimating = false;
        isPaused = false;
        if (animationTimer != null) {
            animationTimer.stop();
        }
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        testCaseCombo.setEnabled(true);
    }
    
    private void resetAnimation() {
        stopAnimation();
        currentStep = 0;
        currentNode = -1;
        currentEdge = "";
        
        // 重置算法状态
        used = new HashMap<>();
        visited = new HashSet<>();
        pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        reachableNodes = 0;
        steps = new ArrayList<>();
        
        // 重置边使用情况
        for (String key : edgeUsed.keySet()) {
            edgeUsed.put(key, 0);
        }
        
        statusLabel.setText("准备开始动画演示 - MaxMoves: " + maxMoves);
        SwingUtilities.invokeLater(() -> drawPanel.repaint());
    }
    
    private void nextStep() {
        if (!isAnimating && currentStep < steps.size()) {
            performStep(steps.get(currentStep));
            currentStep++;
            SwingUtilities.invokeLater(() -> drawPanel.repaint());
        } else if (steps.isEmpty()) {
            setupAnimation();
            if (!steps.isEmpty()) {
                performStep(steps.get(currentStep));
                currentStep++;
                SwingUtilities.invokeLater(() -> drawPanel.repaint());
            }
        }
    }
    
    private void setupAnimation() {
        steps.clear();
        
        // 模拟算法执行过程
        used = new HashMap<>();
        visited = new HashSet<>();
        pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        reachableNodes = 0;
        
        pq.offer(new int[]{0, 0});
        steps.add(new AnimationStep("init", "初始化：将起点0加入优先队列", 0, "", 0, 0));
        
        while (!pq.isEmpty() && pq.peek()[0] <= maxMoves) {
            int[] pair = pq.poll();
            int step = pair[0], u = pair[1];
            
            steps.add(new AnimationStep("poll", "从优先队列取出节点 " + u + "，距离 " + step, u, "", step, 0));
            
            if (!visited.add(u)) {
                steps.add(new AnimationStep("skip", "节点 " + u + " 已访问，跳过", u, "", step, 0));
                continue;
            }
            
            reachableNodes++;
            steps.add(new AnimationStep("visit", "访问节点 " + u + "，可达节点数 +1", u, "", step, reachableNodes));
            
            for (int[] next : adjList[u]) {
                int v = next[0], nodes = next[1];
                String edgeKey = u + "-" + v;
                
                if (nodes + step + 1 <= maxMoves && !visited.contains(v)) {
                    pq.offer(new int[]{nodes + step + 1, v});
                    steps.add(new AnimationStep("add", "将节点 " + v + " 加入队列，距离 " + (nodes + step + 1), v, edgeKey, nodes + step + 1, 0));
                }
                
                int usedNodes = Math.min(nodes, maxMoves - step);
                used.put(encode(u, v, n), usedNodes);
                steps.add(new AnimationStep("edge", "边 " + edgeKey + " 使用 " + usedNodes + " 个节点", u, edgeKey, step, usedNodes));
            }
        }
        
        // 计算最终结果
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], nodes = edge[2];
            int totalUsed = used.getOrDefault(encode(u, v, n), 0) + used.getOrDefault(encode(v, u, n), 0);
            int edgeNodes = Math.min(nodes, totalUsed);
            reachableNodes += edgeNodes;
            
            String edgeKey = u + "-" + v;
            steps.add(new AnimationStep("final", "边 " + edgeKey + " 最终贡献 " + edgeNodes + " 个节点", -1, edgeKey, 0, edgeNodes));
        }
        
        steps.add(new AnimationStep("result", "算法完成，总可达节点数: " + reachableNodes, -1, "", 0, reachableNodes));
    }
    
    private void performStep(AnimationStep step) {
        currentNode = step.node;
        currentEdge = step.edge;
        
        if (step.type.equals("edge") || step.type.equals("final")) {
            edgeUsed.put(step.edge, step.usedNodes);
        }
        
        statusLabel.setText(step.description);
    }
    
    private void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制标题
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g2d.setColor(Color.BLACK);
        g2d.drawString("NO882 细分图中的可到达节点", 20, 30);
        
        // 绘制图的边
        drawEdges(g2d);
        
        // 绘制节点
        drawNodes(g2d);
        
        // 绘制算法信息
        drawAlgorithmInfo(g2d);
        
        g2d.dispose();
    }
    
    private void drawEdges(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(2));
        
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], nodes = edge[2];
            Point p1 = nodePositions[u];
            Point p2 = nodePositions[v];
            
            String edgeKey1 = u + "-" + v;
            String edgeKey2 = v + "-" + u;
            
            // 根据当前状态设置颜色
            if (edgeKey1.equals(currentEdge) || edgeKey2.equals(currentEdge)) {
                g2d.setColor(Color.RED);
            } else {
                g2d.setColor(Color.GRAY);
            }
            
            // 绘制边
            g2d.draw(new Line2D.Double(p1.x, p1.y, p2.x, p2.y));
            
            // 绘制边上的信息
            int midX = (p1.x + p2.x) / 2;
            int midY = (p1.y + p2.y) / 2;
            
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
            
            int used1 = edgeUsed.getOrDefault(edgeKey1, 0);
            int used2 = edgeUsed.getOrDefault(edgeKey2, 0);
            int totalUsed = Math.min(nodes, used1 + used2);
            
            String edgeInfo = nodes + "(" + totalUsed + ")";
            g2d.drawString(edgeInfo, midX - 15, midY - 5);
        }
    }
    
    private void drawNodes(Graphics2D g2d) {
        for (int i = 0; i < n; i++) {
            Point p = nodePositions[i];
            
            // 设置节点颜色
            if (i == currentNode) {
                g2d.setColor(Color.RED);
            } else if (visited != null && visited.contains(i)) {
                g2d.setColor(Color.GREEN);
            } else {
                g2d.setColor(Color.LIGHT_GRAY);
            }
            
            // 绘制节点
            g2d.fill(new Ellipse2D.Double(p.x - 20, p.y - 20, 40, 40));
            
            // 绘制节点边框
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.draw(new Ellipse2D.Double(p.x - 20, p.y - 20, 40, 40));
            
            // 绘制节点标签
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 14));
            FontMetrics fm = g2d.getFontMetrics();
            String label = String.valueOf(i);
            int labelWidth = fm.stringWidth(label);
            g2d.drawString(label, p.x - labelWidth/2, p.y + 5);
        }
    }
    
    private void drawAlgorithmInfo(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        
        int x = 20, y = 80;
        int lineHeight = 20;
        
        g2d.drawString("算法信息:", x, y);
        y += lineHeight;
        g2d.drawString("• 最大移动步数: " + maxMoves, x, y);
        y += lineHeight;
        g2d.drawString("• 当前可达节点数: " + (visited != null ? visited.size() : 0), x, y);
        y += lineHeight;
        g2d.drawString("• 优先队列大小: " + (pq != null ? pq.size() : 0), x, y);
        y += lineHeight;
        
        // 图例
        y += lineHeight;
        g2d.drawString("图例:", x, y);
        y += lineHeight;
        
        // 未访问节点
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillOval(x, y - 10, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(x, y - 10, 15, 15);
        g2d.drawString("未访问节点", x + 20, y);
        y += lineHeight;
        
        // 已访问节点
        g2d.setColor(Color.GREEN);
        g2d.fillOval(x, y - 10, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(x, y - 10, 15, 15);
        g2d.drawString("已访问节点", x + 20, y);
        y += lineHeight;
        
        // 当前节点
        g2d.setColor(Color.RED);
        g2d.fillOval(x, y - 10, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(x, y - 10, 15, 15);
        g2d.drawString("当前处理节点", x + 20, y);
    }
    
    private int encode(int u, int v, int n) {
        return u * n + v;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO882_H_ReachableNodes_Animation().setVisible(true);
        });
    }
}