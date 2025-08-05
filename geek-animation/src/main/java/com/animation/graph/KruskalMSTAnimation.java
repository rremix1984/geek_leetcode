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
 * Kruskal最小生成树算法 - 动画演示
 * 
 * 算法描述：
 * Kruskal算法是一种用于寻找加权无向图的最小生成树的算法。
 * 它使用贪心策略，按权重从小到大排序所有边，然后依次选择不会形成环的边。
 * 
 * 动画特色：
 * • 可视化Kruskal算法执行过程
 * • 显示边的排序过程
 * • 展示并查集的合并操作
 * • 动态显示最小生成树的构建
 */
public class KruskalMSTAnimation extends JFrame implements Animation {
    
    // 动画控制
    private Timer animationTimer;
    private int currentStep = 0;
    private boolean isAnimating = false;
    
    // 算法数据
    private List<Edge> allEdges;
    private List<Edge> mstEdges;
    private UnionFind unionFind;
    private int currentEdgeIndex = -1;
    private Edge currentEdge = null;
    private boolean edgeAccepted = false;
    private int totalWeight = 0;
    
    // UI组件
    private JPanel controlPanel;
    private JButton startButton, stepButton, resetButton, homeButton;
    private JLabel statusLabel;
    private GraphVisualizationPanel visualPanel;
    private JTextArea logArea;
    
    // 图的节点位置
    private Map<Integer, Point> nodePositions;
    private int nodeCount = 6;
    
    // 边类
    private static class Edge implements Comparable<Edge> {
        int from, to, weight;
        
        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
        
        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.weight, other.weight);
        }
        
        @Override
        public String toString() {
            return "(" + from + "-" + to + ", " + weight + ")";
        }
    }
    
    // 并查集类
    private static class UnionFind {
        private int[] parent;
        private int[] rank;
        
        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }
        
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // 路径压缩
            }
            return parent[x];
        }
        
        public boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            
            if (rootX == rootY) {
                return false; // 已经在同一个集合中
            }
            
            // 按秩合并
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
            
            return true;
        }
        
        public boolean connected(int x, int y) {
            return find(x) == find(y);
        }
    }
    
    public KruskalMSTAnimation() {
        setTitle("Kruskal最小生成树算法 - 动画演示");
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
        allEdges = new ArrayList<>();
        allEdges.add(new Edge(0, 1, 4));
        allEdges.add(new Edge(0, 2, 2));
        allEdges.add(new Edge(1, 2, 1));
        allEdges.add(new Edge(1, 3, 5));
        allEdges.add(new Edge(2, 3, 8));
        allEdges.add(new Edge(2, 4, 10));
        allEdges.add(new Edge(3, 4, 2));
        allEdges.add(new Edge(3, 5, 6));
        allEdges.add(new Edge(4, 5, 3));
        
        // 按权重排序
        Collections.sort(allEdges);
        
        // 设置节点位置
        nodePositions = new HashMap<>();
        nodePositions.put(0, new Point(200, 150));
        nodePositions.put(1, new Point(400, 100));
        nodePositions.put(2, new Point(400, 200));
        nodePositions.put(3, new Point(600, 150));
        nodePositions.put(4, new Point(600, 250));
        nodePositions.put(5, new Point(800, 200));
    }
    
    private void initComponents() {
        // 控制面板
        controlPanel = new JPanel(new FlowLayout());
        startButton = new JButton("开始动画");
        stepButton = new JButton("单步执行");
        resetButton = new JButton("重置");
        homeButton = new JButton("返回主页");
        statusLabel = new JLabel("准备开始Kruskal算法");
        
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
            animationTimer = new Timer(2000, e -> stepExecution());
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
            // 开始Kruskal算法
            statusLabel.setText("开始Kruskal算法，边已按权重排序");
            logArea.append("开始Kruskal最小生成树算法\n");
            logArea.append("节点数: " + nodeCount + "\n");
            logArea.append("边数: " + allEdges.size() + "\n\n");
            logArea.append("按权重排序的边:\n");
            for (Edge edge : allEdges) {
                logArea.append(edge.toString() + "\n");
            }
            logArea.append("\n开始处理边:\n");
            currentStep++;
        } else if (currentEdgeIndex + 1 < allEdges.size() && mstEdges.size() < nodeCount - 1) {
            // 处理下一条边
            currentEdgeIndex++;
            currentEdge = allEdges.get(currentEdgeIndex);
            
            statusLabel.setText("检查边 " + currentEdge.toString());
            logArea.append("\n检查边 " + currentEdge.toString() + "\n");
            
            // 检查是否会形成环
            if (!unionFind.connected(currentEdge.from, currentEdge.to)) {
                // 不会形成环，接受这条边
                unionFind.union(currentEdge.from, currentEdge.to);
                mstEdges.add(currentEdge);
                totalWeight += currentEdge.weight;
                edgeAccepted = true;
                
                logArea.append("接受边 " + currentEdge.toString() + " (不会形成环)\n");
                logArea.append("当前MST权重: " + totalWeight + "\n");
                logArea.append("MST边数: " + mstEdges.size() + "/" + (nodeCount - 1) + "\n");
            } else {
                // 会形成环，拒绝这条边
                edgeAccepted = false;
                logArea.append("拒绝边 " + currentEdge.toString() + " (会形成环)\n");
            }
            
            currentStep++;
        } else {
            // 算法完成
            statusLabel.setText("Kruskal算法完成！MST总权重: " + totalWeight);
            logArea.append("\nKruskal算法完成！\n");
            logArea.append("最小生成树包含的边:\n");
            for (Edge edge : mstEdges) {
                logArea.append(edge.toString() + "\n");
            }
            logArea.append("最小生成树总权重: " + totalWeight + "\n");
            stopAnimation();
        }
        
        visualPanel.repaint();
    }
    
    private void resetAnimation() {
        stopAnimation();
        currentStep = 0;
        currentEdgeIndex = -1;
        currentEdge = null;
        edgeAccepted = false;
        totalWeight = 0;
        mstEdges = new ArrayList<>();
        unionFind = new UnionFind(nodeCount);
        statusLabel.setText("准备开始Kruskal算法");
        logArea.setText("");
        visualPanel.repaint();
    }
    
    private class GraphVisualizationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            drawGraph(g2d);
            drawEdgeList(g2d);
            drawMSTInfo(g2d);
            drawUnionFindInfo(g2d);
        }
        
        private void drawGraph(Graphics2D g2d) {
            // 绘制所有边（灰色）
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(Color.LIGHT_GRAY);
            for (Edge edge : allEdges) {
                Point fromPos = nodePositions.get(edge.from);
                Point toPos = nodePositions.get(edge.to);
                g2d.drawLine(fromPos.x, fromPos.y, toPos.x, toPos.y);
                
                // 绘制权重
                int midX = (fromPos.x + toPos.x) / 2;
                int midY = (fromPos.y + toPos.y) / 2;
                g2d.setColor(Color.GRAY);
                g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
                g2d.drawString(String.valueOf(edge.weight), midX - 5, midY - 5);
                g2d.setColor(Color.LIGHT_GRAY);
            }
            
            // 绘制MST边（绿色）
            g2d.setStroke(new BasicStroke(4));
            g2d.setColor(Color.GREEN);
            for (Edge edge : mstEdges) {
                Point fromPos = nodePositions.get(edge.from);
                Point toPos = nodePositions.get(edge.to);
                g2d.drawLine(fromPos.x, fromPos.y, toPos.x, toPos.y);
            }
            
            // 绘制当前处理的边
            if (currentEdge != null) {
                Point fromPos = nodePositions.get(currentEdge.from);
                Point toPos = nodePositions.get(currentEdge.to);
                
                if (edgeAccepted) {
                    g2d.setColor(Color.BLUE);
                } else {
                    g2d.setColor(Color.RED);
                }
                g2d.setStroke(new BasicStroke(5));
                g2d.drawLine(fromPos.x, fromPos.y, toPos.x, toPos.y);
                
                // 高亮权重
                int midX = (fromPos.x + toPos.x) / 2;
                int midY = (fromPos.y + toPos.y) / 2;
                g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
                g2d.drawString(String.valueOf(currentEdge.weight), midX - 5, midY - 10);
            }
            
            // 绘制节点
            for (Map.Entry<Integer, Point> entry : nodePositions.entrySet()) {
                int node = entry.getKey();
                Point pos = entry.getValue();
                
                // 根据并查集着色
                int root = unionFind.find(node);
                Color nodeColor = getColorForRoot(root);
                
                g2d.setColor(nodeColor);
                g2d.fillOval(pos.x - 25, pos.y - 25, 50, 50);
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawOval(pos.x - 25, pos.y - 25, 50, 50);
                
                // 绘制节点标签
                g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
                FontMetrics fm = g2d.getFontMetrics();
                String label = String.valueOf(node);
                int labelWidth = fm.stringWidth(label);
                int labelHeight = fm.getHeight();
                g2d.drawString(label, pos.x - labelWidth/2, pos.y + labelHeight/4);
            }
        }
        
        private Color getColorForRoot(int root) {
            Color[] colors = {Color.CYAN, Color.YELLOW, Color.PINK, Color.ORANGE, Color.MAGENTA, Color.LIGHT_GRAY};
            return colors[root % colors.length];
        }
        
        private void drawEdgeList(Graphics2D g2d) {
            // 绘制边列表
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            g2d.drawString("边列表 (按权重排序):", 50, 50);
            
            int y = 80;
            for (int i = 0; i < allEdges.size(); i++) {
                Edge edge = allEdges.get(i);
                
                if (i == currentEdgeIndex) {
                    if (edgeAccepted) {
                        g2d.setColor(Color.BLUE);
                    } else {
                        g2d.setColor(Color.RED);
                    }
                } else if (i < currentEdgeIndex) {
                    if (mstEdges.contains(edge)) {
                        g2d.setColor(Color.GREEN);
                    } else {
                        g2d.setColor(Color.GRAY);
                    }
                } else {
                    g2d.setColor(Color.BLACK);
                }
                
                String status = "";
                if (i == currentEdgeIndex) {
                    status = edgeAccepted ? " ✓" : " ✗";
                } else if (i < currentEdgeIndex) {
                    status = mstEdges.contains(edge) ? " ✓" : " ✗";
                }
                
                g2d.drawString(edge.toString() + status, 50, y);
                y += 20;
                
                if (y > 300) break; // 避免超出显示区域
            }
        }
        
        private void drawMSTInfo(Graphics2D g2d) {
            // 绘制MST信息
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            g2d.drawString("最小生成树信息:", 50, 350);
            
            g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
            g2d.drawString("边数: " + mstEdges.size() + "/" + (nodeCount - 1), 50, 370);
            g2d.drawString("总权重: " + totalWeight, 50, 390);
            
            if (currentEdge != null) {
                g2d.drawString("当前边: " + currentEdge.toString(), 50, 410);
                g2d.drawString("状态: " + (edgeAccepted ? "接受" : "拒绝"), 50, 430);
            }
        }
        
        private void drawUnionFindInfo(Graphics2D g2d) {
            // 绘制并查集信息
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            g2d.drawString("连通分量:", 50, 470);
            
            Map<Integer, java.util.List<Integer>> components = new HashMap<>();
            for (int i = 0; i < nodeCount; i++) {
                int root = unionFind.find(i);
                components.computeIfAbsent(root, k -> new ArrayList<>()).add(i);
            }
            
            int y = 490;
            g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
            for (Map.Entry<Integer, java.util.List<Integer>> entry : components.entrySet()) {
                g2d.drawString("组 " + entry.getKey() + ": " + entry.getValue().toString(), 50, y);
                y += 20;
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new KruskalMSTAnimation().setVisible(true);
        });
    }
}