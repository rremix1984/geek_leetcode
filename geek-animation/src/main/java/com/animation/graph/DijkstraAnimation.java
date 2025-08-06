package com.animation.graph;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import com.animation.Animation;

/**
 * Dijkstra最短路径算法 - 动画演示
 * 
 * 算法描述：
 * Dijkstra算法是一种用于在加权图中找到单源最短路径的算法。
 * 它使用贪心策略，每次选择距离源点最近的未访问节点。
 * 
 * 动画特色：
 * • 可视化Dijkstra算法执行过程
 * • 显示优先队列的变化
 * • 展示距离更新过程
 * • 动态显示最短路径树的构建
 */
public class DijkstraAnimation extends JFrame implements Animation {
    
    // 动画控制
    private Timer animationTimer;
    private int currentStep = 0;
    private boolean isAnimating = false;
    
    // 算法数据
    private Map<Integer, List<Edge>> graph;
    private Map<Integer, Integer> distances;
    private Map<Integer, Integer> previous;
    private Set<Integer> visited;
    private PriorityQueue<Node> priorityQueue;
    private int sourceNode = 0;
    private int currentNode = -1;
    private Edge currentEdge = null;
    
    // UI组件
    private JPanel controlPanel;
    private JButton startButton, stepButton, resetButton, homeButton;
    private JLabel statusLabel;
    private GraphVisualizationPanel visualPanel;
    private JTextArea logArea;
    
    // 图的节点位置
    private Map<Integer, Point> nodePositions;
    
    // 边类
    private static class Edge {
        int to;
        int weight;
        
        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }
    
    // 节点类（用于优先队列）
    private static class Node implements Comparable<Node> {
        int id;
        int distance;
        
        Node(int id, int distance) {
            this.id = id;
            this.distance = distance;
        }
        
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.distance, other.distance);
        }
    }
    
    public DijkstraAnimation() {
        setTitle("Dijkstra最短路径算法 - 动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        
        initGraph();
        initComponents();
        setupLayout();
        setupEventHandlers();
        resetAnimation();
    }
    
    private void initGraph() {
        // 创建一个带权重的示例图
        graph = new HashMap<>();
        graph.put(0, Arrays.asList(new Edge(1, 4), new Edge(2, 2)));
        graph.put(1, Arrays.asList(new Edge(0, 4), new Edge(2, 1), new Edge(3, 5)));
        graph.put(2, Arrays.asList(new Edge(0, 2), new Edge(1, 1), new Edge(3, 8), new Edge(4, 10)));
        graph.put(3, Arrays.asList(new Edge(1, 5), new Edge(2, 8), new Edge(4, 2), new Edge(5, 6)));
        graph.put(4, Arrays.asList(new Edge(2, 10), new Edge(3, 2), new Edge(5, 3)));
        graph.put(5, Arrays.asList(new Edge(3, 6), new Edge(4, 3)));
        
        // 设置节点位置
        nodePositions = new HashMap<>();
        nodePositions.put(0, new Point(150, 200));
        nodePositions.put(1, new Point(350, 100));
        nodePositions.put(2, new Point(350, 300));
        nodePositions.put(3, new Point(550, 150));
        nodePositions.put(4, new Point(550, 350));
        nodePositions.put(5, new Point(750, 250));
    }
    
    private void initComponents() {
        // 控制面板
        controlPanel = new JPanel(new FlowLayout());
        startButton = new JButton("开始动画");
        stepButton = new JButton("单步执行");
        resetButton = new JButton("重置");
        homeButton = new JButton("返回主页");
        statusLabel = new JLabel("准备开始Dijkstra算法");
        
        controlPanel.add(startButton);
        controlPanel.add(stepButton);
        controlPanel.add(resetButton);
        controlPanel.add(homeButton);
        controlPanel.add(statusLabel);
        
        // 可视化面板
        visualPanel = new GraphVisualizationPanel();
        
        // 日志区域
        logArea = new JTextArea(15, 35);
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.NORTH);
        add(visualPanel, BorderLayout.CENTER);
        add(new JScrollPane(logArea), BorderLayout.EAST);
    }
    
    private void setupEventHandlers() {
        startButton.addActionListener(e -> {
            if (isAnimating) {
                stopAnimation();
            } else {
                startAnimation();
            }
        });
        
        stepButton.addActionListener(e -> stepExecution());
        resetButton.addActionListener(e -> resetAnimation());
        homeButton.addActionListener(e -> {
            dispose();
            // 返回主页的逻辑
        });
    }
    
    @Override
    public void start() {
        startAnimation();
    }
    
    public void startAnimation() {
        if (!isAnimating) {
            isAnimating = true;
            startButton.setText("停止动画");
            animationTimer = new Timer(1500, e -> stepExecution());
            animationTimer.start();
        }
    }
    
    public void stopAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
            isAnimating = false;
            startButton.setText("开始动画");
        }
    }
    
    private void stepExecution() {
        if (currentStep == 0) {
            // 初始化Dijkstra算法
            distances.put(sourceNode, 0);
            priorityQueue.offer(new Node(sourceNode, 0));
            statusLabel.setText("初始化：源节点 " + sourceNode + " 距离为 0");
            logArea.append("开始Dijkstra算法\n");
            logArea.append("源节点: " + sourceNode + "\n");
            logArea.append("初始化距离: " + sourceNode + " -> 0\n");
            currentStep++;
        } else if (!priorityQueue.isEmpty()) {
            // 从优先队列中取出距离最小的节点
            Node current = priorityQueue.poll();
            currentNode = current.id;
            
            if (visited.contains(currentNode)) {
                // 如果已访问，跳过
                return;
            }
            
            visited.add(currentNode);
            statusLabel.setText("访问节点 " + currentNode + "，当前距离: " + distances.get(currentNode));
            logArea.append("\n访问节点 " + currentNode + "，距离: " + distances.get(currentNode) + "\n");
            
            // 更新邻居节点的距离
            for (Edge edge : graph.get(currentNode)) {
                int neighbor = edge.to;
                int newDistance = distances.get(currentNode) + edge.weight;
                
                if (newDistance < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    distances.put(neighbor, newDistance);
                    previous.put(neighbor, currentNode);
                    priorityQueue.offer(new Node(neighbor, newDistance));
                    currentEdge = edge;
                    
                    logArea.append("更新节点 " + neighbor + " 距离: " + newDistance + " (通过节点 " + currentNode + ")\n");
                } else {
                    logArea.append("节点 " + neighbor + " 距离无需更新\n");
                }
            }
            currentStep++;
        } else {
            // 算法完成
            statusLabel.setText("Dijkstra算法完成！");
            logArea.append("\nDijkstra算法完成！\n");
            logArea.append("最终距离:\n");
            for (Map.Entry<Integer, Integer> entry : distances.entrySet()) {
                logArea.append("节点 " + entry.getKey() + ": " + entry.getValue() + "\n");
            }
            stopAnimation();
        }
        
        SwingUtilities.invokeLater(() -> visualPanel.repaint());
    }
    
    private void resetAnimation() {
        stopAnimation();
        currentStep = 0;
        currentNode = -1;
        currentEdge = null;
        distances = new HashMap<>();
        previous = new HashMap<>();
        visited = new HashSet<>();
        priorityQueue = new PriorityQueue<>();
        
        // 初始化所有节点距离为无穷大
        for (int i = 0; i < 6; i++) {
            distances.put(i, Integer.MAX_VALUE);
        }
        
        statusLabel.setText("准备开始Dijkstra算法");
        logArea.setText("");
        SwingUtilities.invokeLater(() -> visualPanel.repaint());
    }
    
    private class GraphVisualizationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            drawGraph(g2d);
            drawDistanceTable(g2d);
            drawPriorityQueue(g2d);
            drawShortestPaths(g2d);
        }
        
        private void drawGraph(Graphics2D g2d) {
            // 绘制边
            g2d.setStroke(new BasicStroke(2));
            for (Map.Entry<Integer, List<Edge>> entry : graph.entrySet()) {
                int from = entry.getKey();
                Point fromPos = nodePositions.get(from);
                
                for (Edge edge : entry.getValue()) {
                    int to = edge.to;
                    if (from < to) { // 避免重复绘制
                        Point toPos = nodePositions.get(to);
                        
                        // 根据是否为当前处理的边设置颜色
                        if (currentEdge != null && 
                            ((currentNode == from && currentEdge.to == to) ||
                             (currentNode == to && currentEdge.to == from))) {
                            g2d.setColor(Color.RED);
                            g2d.setStroke(new BasicStroke(3));
                        } else {
                            g2d.setColor(Color.GRAY);
                            g2d.setStroke(new BasicStroke(2));
                        }
                        
                        g2d.drawLine(fromPos.x, fromPos.y, toPos.x, toPos.y);
                        
                        // 绘制权重
                        int midX = (fromPos.x + toPos.x) / 2;
                        int midY = (fromPos.y + toPos.y) / 2;
                        g2d.setColor(Color.BLUE);
                        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
                        g2d.drawString(String.valueOf(edge.weight), midX - 5, midY - 5);
                    }
                }
            }
            
            // 绘制节点
            for (Map.Entry<Integer, Point> entry : nodePositions.entrySet()) {
                int node = entry.getKey();
                Point pos = entry.getValue();
                
                // 根据状态设置颜色
                if (node == sourceNode) {
                    g2d.setColor(Color.GREEN);
                } else if (node == currentNode) {
                    g2d.setColor(Color.ORANGE);
                } else if (visited.contains(node)) {
                    g2d.setColor(Color.CYAN);
                } else {
                    g2d.setColor(Color.LIGHT_GRAY);
                }
                
                g2d.fillOval(pos.x - 25, pos.y - 25, 50, 50);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(pos.x - 25, pos.y - 25, 50, 50);
                
                // 绘制节点标签
                g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
                FontMetrics fm = g2d.getFontMetrics();
                String label = String.valueOf(node);
                int labelWidth = fm.stringWidth(label);
                int labelHeight = fm.getHeight();
                g2d.drawString(label, pos.x - labelWidth/2, pos.y + labelHeight/4);
                
                // 绘制距离信息
                int distance = distances.getOrDefault(node, Integer.MAX_VALUE);
                String distStr = distance == Integer.MAX_VALUE ? "∞" : String.valueOf(distance);
                g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
                g2d.setColor(Color.RED);
                g2d.drawString(distStr, pos.x + 20, pos.y - 20);
            }
        }
        
        private void drawDistanceTable(Graphics2D g2d) {
            // 绘制距离表
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            g2d.drawString("距离表:", 50, 50);
            
            int y = 80;
            for (int i = 0; i < 6; i++) {
                int distance = distances.getOrDefault(i, Integer.MAX_VALUE);
                String distStr = distance == Integer.MAX_VALUE ? "∞" : String.valueOf(distance);
                
                if (i == currentNode) {
                    g2d.setColor(Color.ORANGE);
                } else if (visited.contains(i)) {
                    g2d.setColor(Color.GREEN);
                } else {
                    g2d.setColor(Color.BLACK);
                }
                
                g2d.drawString("节点 " + i + ": " + distStr, 50, y);
                y += 20;
            }
        }
        
        private void drawPriorityQueue(Graphics2D g2d) {
            // 绘制优先队列状态
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            g2d.drawString("优先队列:", 50, 250);
            
            int y = 280;
            if (priorityQueue.isEmpty()) {
                g2d.drawString("队列为空", 50, y);
            } else {
                PriorityQueue<Node> tempQueue = new PriorityQueue<>(priorityQueue);
                while (!tempQueue.isEmpty()) {
                    Node node = tempQueue.poll();
                    g2d.drawString("节点 " + node.id + " (距离: " + node.distance + ")", 50, y);
                    y += 20;
                }
            }
        }
        
        private void drawShortestPaths(Graphics2D g2d) {
            // 绘制最短路径树
            g2d.setStroke(new BasicStroke(3));
            g2d.setColor(Color.GREEN);
            
            for (Map.Entry<Integer, Integer> entry : previous.entrySet()) {
                int to = entry.getKey();
                int from = entry.getValue();
                
                if (visited.contains(to)) {
                    Point fromPos = nodePositions.get(from);
                    Point toPos = nodePositions.get(to);
                    g2d.drawLine(fromPos.x, fromPos.y, toPos.x, toPos.y);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DijkstraAnimation().setVisible(true);
        });
    }
}