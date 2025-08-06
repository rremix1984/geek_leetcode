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
 * 图的深度优先搜索(DFS) - 动画演示
 * 
 * 算法描述：
 * 深度优先搜索是一种用于遍历或搜索树或图的算法。
 * 沿着树的深度遍历树的节点，尽可能深的搜索树的分支。
 * 
 * 动画特色：
 * • 可视化DFS遍历过程
 * • 显示访问栈的变化
 * • 展示回溯过程
 * • 动态显示访问顺序
 */
public class GraphDFSAnimation extends JFrame implements Animation {
    
    // 动画控制
    private Timer animationTimer;
    private int currentStep = 0;
    private boolean isAnimating = false;
    
    // 算法数据
    private Map<Integer, List<Integer>> graph;
    private Set<Integer> visited;
    private Stack<Integer> dfsStack;
    private List<Integer> visitOrder;
    private int currentNode = -1;
    private int startNode = 0;
    
    // UI组件
    private JPanel controlPanel;
    private JButton startButton, stepButton, resetButton, homeButton;
    private JLabel statusLabel;
    private GraphVisualizationPanel visualPanel;
    private JTextArea logArea;
    
    // 图的节点位置
    private Map<Integer, Point> nodePositions;
    
    public GraphDFSAnimation() {
        setTitle("图的深度优先搜索(DFS) - 动画演示");
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
        statusLabel = new JLabel("准备开始DFS遍历");
        
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
            // 开始DFS
            dfsStack.push(startNode);
            currentNode = startNode;
            statusLabel.setText("开始DFS遍历，从节点 " + startNode + " 开始");
            logArea.append("开始DFS遍历\n");
            logArea.append("将起始节点 " + startNode + " 压入栈\n");
            currentStep++;
        } else if (!dfsStack.isEmpty()) {
            // 继续DFS过程
            currentNode = dfsStack.pop();
            
            if (!visited.contains(currentNode)) {
                visited.add(currentNode);
                visitOrder.add(currentNode);
                statusLabel.setText("访问节点 " + currentNode);
                logArea.append("访问节点 " + currentNode + "\n");
                SwingUtilities.invokeLater(() -> visualPanel.repaint());
                
                // 将邻居节点压入栈（逆序，保证按字典序访问）
                List<Integer> neighbors = new ArrayList<>(graph.get(currentNode));
                Collections.reverse(neighbors);
                for (int neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        dfsStack.push(neighbor);
                        logArea.append("将邻居节点 " + neighbor + " 压入栈\n");
                    }
                }
            }
            currentStep++;
        } else {
            // DFS完成
            statusLabel.setText("DFS遍历完成！访问顺序：" + visitOrder.toString());
            logArea.append("DFS遍历完成！\n");
            logArea.append("访问顺序：" + visitOrder.toString() + "\n");
            stopAnimation();
        }
        
        SwingUtilities.invokeLater(() -> visualPanel.repaint());
    }
    
    private void resetAnimation() {
        stopAnimation();
        currentStep = 0;
        currentNode = -1;
        visited = new HashSet<>();
        dfsStack = new Stack<>();
        visitOrder = new ArrayList<>();
        statusLabel.setText("准备开始DFS遍历");
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
            drawStack(g2d);
            drawVisitOrder(g2d);
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
                if (visited.contains(node)) {
                    g2d.setColor(Color.GREEN);
                } else if (node == currentNode) {
                    g2d.setColor(Color.ORANGE);
                } else if (dfsStack.contains(node)) {
                    g2d.setColor(Color.YELLOW);
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
            }
        }
        
        private void drawStack(Graphics2D g2d) {
            // 绘制栈状态
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            g2d.drawString("DFS栈状态:", 50, 50);
            
            int y = 80;
            Stack<Integer> tempStack = new Stack<>();
            tempStack.addAll(dfsStack);
            
            if (tempStack.isEmpty()) {
                g2d.drawString("栈为空", 50, y);
            } else {
                while (!tempStack.isEmpty()) {
                    int node = tempStack.pop();
                    g2d.setColor(Color.CYAN);
                    g2d.fillRect(50, y - 15, 30, 20);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(50, y - 15, 30, 20);
                    g2d.drawString(String.valueOf(node), 60, y);
                    y += 25;
                }
            }
        }
        
        private void drawVisitOrder(Graphics2D g2d) {
            // 绘制访问顺序
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            g2d.drawString("访问顺序:", 50, 300);
            
            StringBuilder order = new StringBuilder();
            for (int i = 0; i < visitOrder.size(); i++) {
                if (i > 0) order.append(" -> ");
                order.append(visitOrder.get(i));
            }
            g2d.drawString(order.toString(), 50, 320);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GraphDFSAnimation().setVisible(true);
        });
    }
}