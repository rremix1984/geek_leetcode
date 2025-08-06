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
 * 图的广度优先搜索(BFS) - 动画演示
 * 
 * 算法描述：
 * 广度优先搜索是一种用于遍历或搜索树或图的算法。
 * 从根节点开始，沿着树的宽度遍历树的节点。
 * 
 * 动画特色：
 * • 可视化BFS遍历过程
 * • 显示队列的变化
 * • 展示层次遍历
 * • 动态显示访问顺序和层级
 */
public class GraphBFSAnimation extends JFrame implements Animation {
    
    // 动画控制
    private Timer animationTimer;
    private int currentStep = 0;
    private boolean isAnimating = false;
    
    // 算法数据
    private Map<Integer, List<Integer>> graph;
    private Set<Integer> visited;
    private Queue<Integer> bfsQueue;
    private List<Integer> visitOrder;
    private Map<Integer, Integer> levels;
    private int currentNode = -1;
    private int startNode = 0;
    private int currentLevel = 0;
    
    // UI组件
    private JPanel controlPanel;
    private JButton startButton, stepButton, resetButton, homeButton;
    private JLabel statusLabel;
    private GraphVisualizationPanel visualPanel;
    private JTextArea logArea;
    
    // 图的节点位置
    private Map<Integer, Point> nodePositions;
    
    public GraphBFSAnimation() {
        setTitle("图的广度优先搜索(BFS) - 动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        
        initGraph();
        initComponents();
        setupLayout();
        setupEventHandlers();
        resetAnimation();
    }
    
    private void initGraph() {
        // 创建一个示例图
        graph = new HashMap<>();
        graph.put(0, Arrays.asList(1, 2));
        graph.put(1, Arrays.asList(0, 3, 4));
        graph.put(2, Arrays.asList(0, 5, 6));
        graph.put(3, Arrays.asList(1));
        graph.put(4, Arrays.asList(1, 7));
        graph.put(5, Arrays.asList(2));
        graph.put(6, Arrays.asList(2, 7));
        graph.put(7, Arrays.asList(4, 6));
        
        // 设置节点位置
        nodePositions = new HashMap<>();
        nodePositions.put(0, new Point(400, 100));
        nodePositions.put(1, new Point(200, 200));
        nodePositions.put(2, new Point(600, 200));
        nodePositions.put(3, new Point(100, 350));
        nodePositions.put(4, new Point(300, 350));
        nodePositions.put(5, new Point(500, 350));
        nodePositions.put(6, new Point(700, 350));
        nodePositions.put(7, new Point(500, 500));
    }
    
    private void initComponents() {
        // 控制面板
        controlPanel = new JPanel(new FlowLayout());
        startButton = new JButton("开始动画");
        stepButton = new JButton("单步执行");
        resetButton = new JButton("重置");
        homeButton = new JButton("返回主页");
        statusLabel = new JLabel("准备开始BFS遍历");
        
        controlPanel.add(startButton);
        controlPanel.add(stepButton);
        controlPanel.add(resetButton);
        controlPanel.add(homeButton);
        controlPanel.add(statusLabel);
        
        // 可视化面板
        visualPanel = new GraphVisualizationPanel();
        
        // 日志区域
        logArea = new JTextArea(10, 30);
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
            animationTimer = new Timer(1000, e -> stepExecution());
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
            // 开始BFS
            bfsQueue.offer(startNode);
            visited.add(startNode);
            levels.put(startNode, 0);
            currentNode = startNode;
            statusLabel.setText("开始BFS遍历，从节点 " + startNode + " 开始");
            logArea.append("开始BFS遍历\n");
            logArea.append("将起始节点 " + startNode + " 加入队列，层级为 0\n");
            currentStep++;
        } else if (!bfsQueue.isEmpty()) {
            // 继续BFS过程
            currentNode = bfsQueue.poll();
            visitOrder.add(currentNode);
            currentLevel = levels.get(currentNode);
            
            statusLabel.setText("访问节点 " + currentNode + "，层级 " + currentLevel);
            logArea.append("访问节点 " + currentNode + "，层级 " + currentLevel + "\n");
            SwingUtilities.invokeLater(() -> visualPanel.repaint());
            
            // 将未访问的邻居节点加入队列
            for (int neighbor : graph.get(currentNode)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    bfsQueue.offer(neighbor);
                    levels.put(neighbor, currentLevel + 1);
                    logArea.append("将邻居节点 " + neighbor + " 加入队列，层级为 " + (currentLevel + 1) + "\n");
                }
            }
            currentStep++;
        } else {
            // BFS完成
            statusLabel.setText("BFS遍历完成！访问顺序：" + visitOrder.toString());
            logArea.append("BFS遍历完成！\n");
            logArea.append("访问顺序：" + visitOrder.toString() + "\n");
            stopAnimation();
        }
        
        SwingUtilities.invokeLater(() -> visualPanel.repaint());
    }
    
    private void resetAnimation() {
        stopAnimation();
        currentStep = 0;
        currentNode = -1;
        currentLevel = 0;
        visited = new HashSet<>();
        bfsQueue = new LinkedList<>();
        visitOrder = new ArrayList<>();
        levels = new HashMap<>();
        statusLabel.setText("准备开始BFS遍历");
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
            drawQueue(g2d);
            drawVisitOrder(g2d);
            drawLevels(g2d);
        }
        
        private void drawGraph(Graphics2D g2d) {
            // 绘制边
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(Color.GRAY);
            for (Map.Entry<Integer, List<Integer>> entry : graph.entrySet()) {
                int from = entry.getKey();
                Point fromPos = nodePositions.get(from);
                for (int to : entry.getValue()) {
                    if (from < to) { // 避免重复绘制
                        Point toPos = nodePositions.get(to);
                        g2d.drawLine(fromPos.x, fromPos.y, toPos.x, toPos.y);
                    }
                }
            }
            
            // 绘制节点
            for (Map.Entry<Integer, Point> entry : nodePositions.entrySet()) {
                int node = entry.getKey();
                Point pos = entry.getValue();
                
                // 根据状态设置颜色
                if (visitOrder.contains(node)) {
                    g2d.setColor(Color.GREEN);
                } else if (node == currentNode) {
                    g2d.setColor(Color.ORANGE);
                } else if (bfsQueue.contains(node)) {
                    g2d.setColor(Color.YELLOW);
                } else if (visited.contains(node)) {
                    g2d.setColor(Color.CYAN);
                } else {
                    g2d.setColor(Color.LIGHT_GRAY);
                }
                
                g2d.fillOval(pos.x - 20, pos.y - 20, 40, 40);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(pos.x - 20, pos.y - 20, 40, 40);
                
                // 绘制节点标签
                g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
                FontMetrics fm = g2d.getFontMetrics();
                String label = String.valueOf(node);
                int labelWidth = fm.stringWidth(label);
                int labelHeight = fm.getHeight();
                g2d.drawString(label, pos.x - labelWidth/2, pos.y + labelHeight/4);
                
                // 绘制层级信息
                if (levels.containsKey(node)) {
                    g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
                    g2d.setColor(Color.BLUE);
                    g2d.drawString("L" + levels.get(node), pos.x + 15, pos.y - 15);
                }
            }
        }
        
        private void drawQueue(Graphics2D g2d) {
            // 绘制队列状态
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            g2d.drawString("BFS队列状态:", 50, 50);
            
            int x = 50;
            int y = 80;
            
            if (bfsQueue.isEmpty()) {
                g2d.drawString("队列为空", x, y);
            } else {
                g2d.drawString("队头 -> ", x, y);
                x += 60;
                
                for (int node : bfsQueue) {
                    g2d.setColor(Color.CYAN);
                    g2d.fillRect(x, y - 15, 30, 20);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(x, y - 15, 30, 20);
                    g2d.drawString(String.valueOf(node), x + 10, y);
                    x += 35;
                }
                
                g2d.drawString(" <- 队尾", x, y);
            }
        }
        
        private void drawVisitOrder(Graphics2D g2d) {
            // 绘制访问顺序
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            g2d.drawString("访问顺序:", 50, 130);
            
            StringBuilder order = new StringBuilder();
            for (int i = 0; i < visitOrder.size(); i++) {
                if (i > 0) order.append(" -> ");
                order.append(visitOrder.get(i));
            }
            g2d.drawString(order.toString(), 50, 150);
        }
        
        private void drawLevels(Graphics2D g2d) {
            // 绘制层级信息
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            g2d.drawString("当前层级: " + currentLevel, 50, 180);
            
            // 统计各层级的节点
            Map<Integer, java.util.List<Integer>> levelNodes = new HashMap<>();
            for (Map.Entry<Integer, Integer> entry : levels.entrySet()) {
                int node = entry.getKey();
                int level = entry.getValue();
                levelNodes.computeIfAbsent(level, k -> new ArrayList<>()).add(node);
            }
            
            int y = 200;
            for (Map.Entry<Integer, java.util.List<Integer>> entry : levelNodes.entrySet()) {
                int level = entry.getKey();
                java.util.List<Integer> nodes = entry.getValue();
                g2d.drawString("层级 " + level + ": " + nodes.toString(), 50, y);
                y += 20;
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GraphBFSAnimation().setVisible(true);
        });
    }
}