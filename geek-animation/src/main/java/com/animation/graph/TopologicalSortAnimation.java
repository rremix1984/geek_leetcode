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
 * 拓扑排序算法 - 动画演示
 * 
 * 算法描述：
 * 拓扑排序是对有向无环图(DAG)的顶点的一种线性排序，
 * 使得对于每一条有向边(u,v)，顶点u都在顶点v之前。
 * 
 * 动画特色：
 * • 可视化Kahn算法执行过程
 * • 显示入度计算和更新
 * • 展示队列的变化
 * • 动态显示拓扑序列的构建
 */
public class TopologicalSortAnimation extends JFrame implements Animation {
    
    // 动画控制
    private Timer animationTimer;
    private int currentStep = 0;
    private boolean isAnimating = false;
    
    // 算法数据
    private Map<Integer, List<Integer>> graph;
    private Map<Integer, Integer> inDegree;
    private Queue<Integer> zeroInDegreeQueue;
    private List<Integer> topologicalOrder;
    private int currentNode = -1;
    private Set<Integer> processedNodes;
    private Map<Integer, Integer> originalInDegree;
    
    // UI组件
    private JPanel controlPanel;
    private JButton startButton, stepButton, resetButton, homeButton;
    private JLabel statusLabel;
    private GraphVisualizationPanel visualPanel;
    private JTextArea logArea;
    
    // 图的节点位置
    private Map<Integer, Point> nodePositions;
    private int nodeCount = 6;
    
    public TopologicalSortAnimation() {
        setTitle("拓扑排序算法 - 动画演示");
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
        // 创建一个有向无环图(DAG)
        graph = new HashMap<>();
        graph.put(0, Arrays.asList(1, 2));
        graph.put(1, Arrays.asList(3));
        graph.put(2, Arrays.asList(3, 4));
        graph.put(3, Arrays.asList(5));
        graph.put(4, Arrays.asList(5));
        graph.put(5, Arrays.asList());
        
        // 设置节点位置（按层次排列）
        nodePositions = new HashMap<>();
        nodePositions.put(0, new Point(200, 100));
        nodePositions.put(1, new Point(350, 200));
        nodePositions.put(2, new Point(350, 300));
        nodePositions.put(3, new Point(500, 250));
        nodePositions.put(4, new Point(650, 350));
        nodePositions.put(5, new Point(800, 300));
    }
    
    private void initComponents() {
        // 控制面板
        controlPanel = new JPanel(new FlowLayout());
        startButton = new JButton("开始动画");
        stepButton = new JButton("单步执行");
        resetButton = new JButton("重置");
        homeButton = new JButton("返回主页");
        statusLabel = new JLabel("准备开始拓扑排序");
        
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
            // 初始化：计算所有节点的入度
            calculateInDegrees();
            statusLabel.setText("计算所有节点的入度");
            logArea.append("开始拓扑排序算法 (Kahn算法)\n");
            logArea.append("步骤1: 计算所有节点的入度\n");
            for (Map.Entry<Integer, Integer> entry : inDegree.entrySet()) {
                logArea.append("节点 " + entry.getKey() + " 入度: " + entry.getValue() + "\n");
            }
            currentStep++;
        } else if (currentStep == 1) {
            // 将所有入度为0的节点加入队列
            for (Map.Entry<Integer, Integer> entry : inDegree.entrySet()) {
                if (entry.getValue() == 0) {
                    zeroInDegreeQueue.offer(entry.getKey());
                }
            }
            statusLabel.setText("将入度为0的节点加入队列");
            logArea.append("\n步骤2: 将入度为0的节点加入队列\n");
            logArea.append("初始队列: " + zeroInDegreeQueue.toString() + "\n\n");
            currentStep++;
        } else if (!zeroInDegreeQueue.isEmpty()) {
            // 处理队列中的节点
            currentNode = zeroInDegreeQueue.poll();
            topologicalOrder.add(currentNode);
            processedNodes.add(currentNode);
            
            statusLabel.setText("处理节点 " + currentNode + "，加入拓扑序列");
            logArea.append("从队列中取出节点 " + currentNode + "\n");
            logArea.append("将节点 " + currentNode + " 加入拓扑序列\n");
            
            // 更新邻居节点的入度
            for (int neighbor : graph.get(currentNode)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                logArea.append("更新节点 " + neighbor + " 的入度: " + inDegree.get(neighbor) + "\n");
                
                if (inDegree.get(neighbor) == 0) {
                    zeroInDegreeQueue.offer(neighbor);
                    logArea.append("节点 " + neighbor + " 入度为0，加入队列\n");
                }
            }
            
            logArea.append("当前拓扑序列: " + topologicalOrder.toString() + "\n");
            logArea.append("当前队列: " + zeroInDegreeQueue.toString() + "\n\n");
            currentStep++;
        } else {
            // 算法完成
            if (topologicalOrder.size() == nodeCount) {
                statusLabel.setText("拓扑排序完成！");
                logArea.append("拓扑排序完成！\n");
                logArea.append("最终拓扑序列: " + topologicalOrder.toString() + "\n");
                logArea.append("该图是有向无环图(DAG)\n");
            } else {
                statusLabel.setText("检测到环！无法完成拓扑排序");
                logArea.append("拓扑排序失败！\n");
                logArea.append("检测到环，该图不是有向无环图\n");
                logArea.append("已处理节点数: " + topologicalOrder.size() + "/" + nodeCount + "\n");
            }
            stopAnimation();
        }
        
        visualPanel.repaint();
    }
    
    private void calculateInDegrees() {
        // 初始化所有节点的入度为0
        inDegree = new HashMap<>();
        for (int i = 0; i < nodeCount; i++) {
            inDegree.put(i, 0);
        }
        
        // 计算每个节点的入度
        for (Map.Entry<Integer, List<Integer>> entry : graph.entrySet()) {
            for (int neighbor : entry.getValue()) {
                inDegree.put(neighbor, inDegree.get(neighbor) + 1);
            }
        }
        
        // 保存原始入度用于显示
        originalInDegree = new HashMap<>(inDegree);
    }
    
    private void resetAnimation() {
        stopAnimation();
        currentStep = 0;
        currentNode = -1;
        inDegree = new HashMap<>();
        originalInDegree = new HashMap<>();
        zeroInDegreeQueue = new LinkedList<>();
        topologicalOrder = new ArrayList<>();
        processedNodes = new HashSet<>();
        statusLabel.setText("准备开始拓扑排序");
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
            drawInDegreeTable(g2d);
            drawQueue(g2d);
            drawTopologicalOrder(g2d);
        }
        
        private void drawGraph(Graphics2D g2d) {
            // 绘制边
            g2d.setStroke(new BasicStroke(2));
            for (Map.Entry<Integer, List<Integer>> entry : graph.entrySet()) {
                int from = entry.getKey();
                Point fromPos = nodePositions.get(from);
                
                for (int to : entry.getValue()) {
                    Point toPos = nodePositions.get(to);
                    
                    // 根据节点状态设置边的颜色
                    if (processedNodes.contains(from)) {
                        g2d.setColor(Color.GRAY);
                    } else if (from == currentNode) {
                        g2d.setColor(Color.ORANGE);
                        g2d.setStroke(new BasicStroke(3));
                    } else {
                        g2d.setColor(Color.BLACK);
                        g2d.setStroke(new BasicStroke(2));
                    }
                    
                    // 绘制箭头
                    drawArrow(g2d, fromPos, toPos);
                }
            }
            
            // 绘制节点
            for (Map.Entry<Integer, Point> entry : nodePositions.entrySet()) {
                int node = entry.getKey();
                Point pos = entry.getValue();
                
                // 根据状态设置颜色
                if (processedNodes.contains(node)) {
                    g2d.setColor(Color.GREEN);
                } else if (node == currentNode) {
                    g2d.setColor(Color.ORANGE);
                } else if (zeroInDegreeQueue.contains(node)) {
                    g2d.setColor(Color.YELLOW);
                } else {
                    g2d.setColor(Color.LIGHT_GRAY);
                }
                
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
                
                // 绘制入度信息
                if (inDegree.containsKey(node)) {
                    g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
                    g2d.setColor(Color.BLUE);
                    String inDegreeStr = "入度:" + inDegree.get(node);
                    g2d.drawString(inDegreeStr, pos.x - 20, pos.y - 35);
                }
            }
        }
        
        private void drawArrow(Graphics2D g2d, Point from, Point to) {
            // 计算箭头的起点和终点（考虑节点半径）
            double angle = Math.atan2(to.y - from.y, to.x - from.x);
            int startX = (int) (from.x + 25 * Math.cos(angle));
            int startY = (int) (from.y + 25 * Math.sin(angle));
            int endX = (int) (to.x - 25 * Math.cos(angle));
            int endY = (int) (to.y - 25 * Math.sin(angle));
            
            // 绘制箭头线
            g2d.drawLine(startX, startY, endX, endY);
            
            // 绘制箭头头部
            int arrowLength = 10;
            double arrowAngle = Math.PI / 6;
            
            int arrowX1 = (int) (endX - arrowLength * Math.cos(angle - arrowAngle));
            int arrowY1 = (int) (endY - arrowLength * Math.sin(angle - arrowAngle));
            int arrowX2 = (int) (endX - arrowLength * Math.cos(angle + arrowAngle));
            int arrowY2 = (int) (endY - arrowLength * Math.sin(angle + arrowAngle));
            
            g2d.drawLine(endX, endY, arrowX1, arrowY1);
            g2d.drawLine(endX, endY, arrowX2, arrowY2);
        }
        
        private void drawInDegreeTable(Graphics2D g2d) {
            // 绘制入度表
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            g2d.drawString("节点入度:", 50, 50);
            
            int y = 80;
            g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
            for (int i = 0; i < nodeCount; i++) {
                int currentInDegree = inDegree.getOrDefault(i, 0);
                int originalDegree = originalInDegree.getOrDefault(i, 0);
                
                if (processedNodes.contains(i)) {
                    g2d.setColor(Color.GREEN);
                } else if (i == currentNode) {
                    g2d.setColor(Color.ORANGE);
                } else if (currentInDegree == 0 && originalDegree > 0) {
                    g2d.setColor(Color.BLUE);
                } else {
                    g2d.setColor(Color.BLACK);
                }
                
                String degreeInfo = "节点 " + i + ": " + currentInDegree;
                if (originalDegree != currentInDegree) {
                    degreeInfo += " (原: " + originalDegree + ")";
                }
                
                g2d.drawString(degreeInfo, 50, y);
                y += 20;
            }
        }
        
        private void drawQueue(Graphics2D g2d) {
            // 绘制队列状态
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            g2d.drawString("入度为0的队列:", 50, 250);
            
            int x = 50;
            int y = 280;
            
            if (zeroInDegreeQueue.isEmpty()) {
                g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
                g2d.drawString("队列为空", x, y);
            } else {
                g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
                g2d.drawString("队头 -> ", x, y);
                x += 60;
                
                for (int node : zeroInDegreeQueue) {
                    g2d.setColor(Color.YELLOW);
                    g2d.fillRect(x, y - 15, 30, 20);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(x, y - 15, 30, 20);
                    g2d.drawString(String.valueOf(node), x + 10, y);
                    x += 35;
                }
                
                g2d.drawString(" <- 队尾", x, y);
            }
        }
        
        private void drawTopologicalOrder(Graphics2D g2d) {
            // 绘制拓扑序列
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            g2d.drawString("拓扑序列:", 50, 330);
            
            if (topologicalOrder.isEmpty()) {
                g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
                g2d.drawString("序列为空", 50, 350);
            } else {
                StringBuilder order = new StringBuilder();
                for (int i = 0; i < topologicalOrder.size(); i++) {
                    if (i > 0) order.append(" -> ");
                    order.append(topologicalOrder.get(i));
                }
                g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
                g2d.drawString(order.toString(), 50, 350);
            }
            
            // 显示进度
            g2d.setColor(Color.BLUE);
            g2d.drawString("进度: " + topologicalOrder.size() + "/" + nodeCount, 50, 380);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TopologicalSortAnimation().setVisible(true);
        });
    }
}