/**
 * copyright 2024
 */
package com.leetcode.hard.animation;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * NO.834 树中距离之和 - 动画演示
 * 
 * 核心算法：两次DFS
 * 第一次DFS：计算每个节点的子树大小和从根节点到其他节点的距离之和
 * 第二次DFS：利用换根技巧，根据父节点信息计算子节点的距离之和
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 */
public class NO834_H_SumOfDistancesInTree_Animation extends JPanel {

    private int n;
    private int[][] edges;
    private List<List<Integer>> graph;
    private int[] count;
    private int[] answer;
    private boolean isFirstDFS;
    private boolean isSecondDFS;
    private int currentNode;
    private int parentNode;
    private int step;
    private javax.swing.Timer timer;
    private boolean isRunning;
    private boolean isPaused;
    private Set<Integer> visitedNodes;
    private Map<Integer, Point> nodePositions;

    // UI组件
    private JButton startBtn, pauseBtn, resetBtn, stepBtn;
    private JSlider speedSlider;
    private JTextField inputField;
    private JTextArea logArea;
    private JLabel statusLabel;

    // 绘制参数
    private final int NODE_RADIUS = 25;
    private final Color NODE_COLOR = Color.CYAN;
    private final Color CURRENT_NODE_COLOR = Color.ORANGE;
    private final Color VISITED_NODE_COLOR = new Color(144, 238, 144);
    private final Color EDGE_COLOR = Color.BLACK;
    private final Color CURRENT_EDGE_COLOR = Color.RED;

    public NO834_H_SumOfDistancesInTree_Animation() {
        initializeComponents();
        setupLayout();
        setupEventListeners();
        initializeData();
    }

    private void initializeComponents() {
        startBtn = new JButton("开始");
        pauseBtn = new JButton("暂停");
        resetBtn = new JButton("重置");
        stepBtn = new JButton("单步");

        speedSlider = new JSlider(1, 10, 5);
        speedSlider.setMajorTickSpacing(3);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);

        inputField = new JTextField("6,[[0,1],[0,2],[2,3],[2,4],[2,5]]", 30);
        logArea = new JTextArea(8, 40);
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        statusLabel = new JLabel("状态: 准备开始");

        timer = new Timer(1000, e -> nextStep());
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // 控制面板
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(new JLabel("输入(n,edges):"));
        controlPanel.add(inputField);
        controlPanel.add(startBtn);
        controlPanel.add(pauseBtn);
        controlPanel.add(resetBtn);
        controlPanel.add(stepBtn);
        controlPanel.add(new JLabel("速度:"));
        controlPanel.add(speedSlider);

        // 状态面板
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(statusLabel, BorderLayout.NORTH);
        statusPanel.add(new JScrollPane(logArea), BorderLayout.CENTER);

        add(controlPanel, BorderLayout.NORTH);
        add(statusPanel, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(1200, 800));
    }

    private void setupEventListeners() {
        startBtn.addActionListener(e -> start());
        pauseBtn.addActionListener(e -> pause());
        resetBtn.addActionListener(e -> reset());
        stepBtn.addActionListener(e -> nextStep());

        speedSlider.addChangeListener(e -> {
            int delay = 1100 - speedSlider.getValue() * 100;
            timer.setDelay(delay);
        });
    }

    private void initializeData() {
        // 默认示例：n=6, edges=[[0,1],[0,2],[2,3],[2,4],[2,5]]
        n = 6;
        edges = new int[][] { { 0, 1 }, { 0, 2 }, { 2, 3 }, { 2, 4 }, { 2, 5 } };
        reset();
    }

    public void start() {
        if (!isRunning) {
            parseInput();
            isRunning = true;
            isPaused = false;
            timer.start();
            updateStatus("开始第一次DFS...");
        } else if (isPaused) {
            isPaused = false;
            timer.start();
            updateStatus("继续执行...");
        }
    }

    public void pause() {
        if (isRunning && !isPaused) {
            isPaused = true;
            timer.stop();
            updateStatus("已暂停");
        }
    }

    public void reset() {
        timer.stop();
        isRunning = false;
        isPaused = false;
        isFirstDFS = true;
        isSecondDFS = false;
        step = 0;
        currentNode = 0;
        parentNode = -1;

        buildGraph();
        count = new int[n];
        answer = new int[n];
        visitedNodes = new HashSet<>();
        calculateNodePositions();

        logArea.setText("");
        updateStatus("已重置，准备开始");
        repaint();
    }

    private void parseInput() {
        try {
            String input = inputField.getText().trim();
            if (input.isEmpty())
                return;

            // 解析输入格式："6,[[0,1],[0,2],[2,3],[2,4],[2,5]]"
            String[] parts = input.split(",\\[", 2);
            n = Integer.parseInt(parts[0]);

            String edgesStr = parts[1];
            if (edgesStr.endsWith("]]")) {
                edgesStr = edgesStr.substring(0, edgesStr.length() - 2);
            }

            String[] edgePairs = edgesStr.split("\\],\\[");
            edges = new int[edgePairs.length][2];

            for (int i = 0; i < edgePairs.length; i++) {
                String pair = edgePairs[i].replaceAll("[\\[\\]]", "");
                String[] nums = pair.split(",");
                edges[i][0] = Integer.parseInt(nums[0]);
                edges[i][1] = Integer.parseInt(nums[1]);
            }

            reset();

        } catch (Exception e) {
            updateStatus("输入格式错误: " + e.getMessage());
        }
    }

    private void buildGraph() {
        graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }
    }

    private void calculateNodePositions() {
        nodePositions = new HashMap<>();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2 - 100;

        if (n == 0)
            return;

        // 使用BFS布局节点位置
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> positioned = new HashSet<>();

        nodePositions.put(0, new Point(centerX, centerY));
        queue.offer(0);
        positioned.add(0);

        int level = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            double angleStep = 2 * Math.PI / Math.max(size, 1);
            int radius = 80 * level;

            for (int i = 0; i < size; i++) {
                int node = queue.poll();
                Point nodePos = nodePositions.get(node);

                int childIndex = 0;
                for (int neighbor : graph.get(node)) {
                    if (!positioned.contains(neighbor)) {
                        double angle = angleStep * childIndex;
                        int x = (int) (nodePos.x + radius * Math.cos(angle));
                        int y = (int) (nodePos.y + radius * Math.sin(angle));

                        nodePositions.put(neighbor, new Point(x, y));
                        queue.offer(neighbor);
                        positioned.add(neighbor);
                        childIndex++;
                    }
                }
            }
            level++;
        }
    }

    private void nextStep() {
        if (isFirstDFS) {
            firstDFSStep();
        } else if (isSecondDFS) {
            secondDFSStep();
        }
        repaint();
    }

    private void firstDFSStep() {
        if (step == 0) {
            // 开始第一次DFS
            logArea.append("第一次DFS开始，计算子树大小和距离之和\n");
            logArea.append("当前节点: " + currentNode + "\n");
            visitedNodes.add(currentNode);
            step++;
            return;
        }

        // 模拟DFS过程
        boolean hasUnvisitedChild = false;
        for (int child : graph.get(currentNode)) {
            if (child != parentNode && !visitedNodes.contains(child)) {
                // 访问子节点
                parentNode = currentNode;
                currentNode = child;
                visitedNodes.add(child);
                logArea.append("访问子节点: " + child + " (父节点: " + parentNode + ")\n");
                hasUnvisitedChild = true;
                break;
            }
        }

        if (!hasUnvisitedChild) {
            // 回溯或完成第一次DFS
            if (currentNode == 0) {
                // 第一次DFS完成
                calculateFirstDFSResult();
                isFirstDFS = false;
                isSecondDFS = true;
                currentNode = 0;
                parentNode = -1;
                visitedNodes.clear();
                logArea.append("第一次DFS完成，开始第二次DFS\n");
                updateStatus("开始第二次DFS...");
            } else {
                // 回溯到父节点
                int oldNode = currentNode;
                currentNode = parentNode;
                parentNode = findParent(currentNode, oldNode);
                logArea.append("回溯到节点: " + currentNode + "\n");
            }
        }

        step++;
    }

    private void secondDFSStep() {
        if (visitedNodes.isEmpty()) {
            visitedNodes.add(0);
            logArea.append("第二次DFS开始，计算所有节点的距离之和\n");
            logArea.append("当前节点: " + currentNode + "\n");
            return;
        }

        boolean hasUnvisitedChild = false;
        for (int child : graph.get(currentNode)) {
            if (child != parentNode && !visitedNodes.contains(child)) {
                // 计算子节点的答案
                answer[child] = answer[currentNode] - count[child] + n - count[child];
                logArea.append(String.format("计算节点%d: answer[%d] = %d - %d + %d - %d = %d\n",
                        child, child, answer[currentNode], count[child], n, count[child], answer[child]));

                parentNode = currentNode;
                currentNode = child;
                visitedNodes.add(child);
                hasUnvisitedChild = true;
                break;
            }
        }

        if (!hasUnvisitedChild) {
            if (currentNode == 0 && visitedNodes.size() == n) {
                // 第二次DFS完成
                timer.stop();
                isRunning = false;
                isSecondDFS = false;
                logArea.append("算法完成!\n");
                logArea.append("最终结果: " + Arrays.toString(answer) + "\n");
                updateStatus("算法完成!");
            } else {
                // 回溯
                int oldNode = currentNode;
                currentNode = parentNode;
                parentNode = findParent(currentNode, oldNode);
                logArea.append("回溯到节点: " + currentNode + "\n");
            }
        }
    }

    private int findParent(int current, int child) {
        // 简化的父节点查找
        for (int neighbor : graph.get(current)) {
            if (neighbor != child && visitedNodes.contains(neighbor)) {
                return neighbor;
            }
        }
        return -1;
    }

    private void calculateFirstDFSResult() {
        // 实际计算第一次DFS的结果
        count[0] = dfs(0, -1);
        logArea.append("第一次DFS结果:\n");
        logArea.append("count数组: " + Arrays.toString(count) + "\n");
        logArea.append("answer数组: " + Arrays.toString(answer) + "\n");
    }

    private int dfs(int node, int parent) {
        int subCount = 1;
        for (int child : graph.get(node)) {
            if (child != parent) {
                subCount += dfs(child, node);
                answer[node] += answer[child] + count[child];
            }
        }
        count[node] = subCount;
        return subCount;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (nodePositions == null || nodePositions.isEmpty()) {
            calculateNodePositions();
        }

        // 绘制边
        drawEdges(g2d);

        // 绘制节点
        drawNodes(g2d);

        // 绘制信息
        drawInfo(g2d);
    }

    private void drawEdges(Graphics2D g2d) {
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            Point posU = nodePositions.get(u);
            Point posV = nodePositions.get(v);

            if (posU != null && posV != null) {
                // 判断是否是当前处理的边
                boolean isCurrentEdge = (u == currentNode && v == parentNode) ||
                        (v == currentNode && u == parentNode);

                g2d.setColor(isCurrentEdge ? CURRENT_EDGE_COLOR : EDGE_COLOR);
                g2d.setStroke(new BasicStroke(isCurrentEdge ? 3 : 1));
                g2d.drawLine(posU.x, posU.y, posV.x, posV.y);
            }
        }
    }

    private void drawNodes(Graphics2D g2d) {
        for (int i = 0; i < n; i++) {
            Point pos = nodePositions.get(i);
            if (pos == null)
                continue;

            // 选择颜色
            Color nodeColor;
            if (i == currentNode) {
                nodeColor = CURRENT_NODE_COLOR;
            } else if (visitedNodes.contains(i)) {
                nodeColor = VISITED_NODE_COLOR;
            } else {
                nodeColor = NODE_COLOR;
            }

            // 绘制节点
            g2d.setColor(nodeColor);
            g2d.fillOval(pos.x - NODE_RADIUS, pos.y - NODE_RADIUS,
                    2 * NODE_RADIUS, 2 * NODE_RADIUS);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(pos.x - NODE_RADIUS, pos.y - NODE_RADIUS,
                    2 * NODE_RADIUS, 2 * NODE_RADIUS);

            // 绘制节点编号
            FontMetrics fm = g2d.getFontMetrics();
            String nodeText = String.valueOf(i);
            int textX = pos.x - fm.stringWidth(nodeText) / 2;
            int textY = pos.y + fm.getAscent() / 2;
            g2d.drawString(nodeText, textX, textY);

            // 绘制count和answer信息
            if (count != null && answer != null) {
                String info = String.format("c:%d a:%d", count[i], answer[i]);
                int infoX = pos.x - fm.stringWidth(info) / 2;
                int infoY = pos.y + NODE_RADIUS + 15;
                g2d.setColor(Color.BLUE);
                g2d.drawString(info, infoX, infoY);
            }
        }
    }

    private void drawInfo(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));

        int y = 30;
        g2d.drawString("树中距离之和算法演示", 20, y);
        y += 25;

        if (isFirstDFS) {
            g2d.drawString("第一次DFS: 计算子树大小和距离之和", 20, y);
        } else if (isSecondDFS) {
            g2d.drawString("第二次DFS: 利用换根技巧计算所有节点答案", 20, y);
        }
        y += 25;

        g2d.drawString("当前节点: " + currentNode +
                (parentNode >= 0 ? " (父节点: " + parentNode + ")" : ""), 20, y);

        // 绘制图例
        y += 40;
        g2d.drawString("图例:", 20, y);
        y += 20;

        // 当前节点
        g2d.setColor(CURRENT_NODE_COLOR);
        g2d.fillOval(20, y - 10, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("当前节点", 45, y);
        y += 20;

        // 已访问节点
        g2d.setColor(VISITED_NODE_COLOR);
        g2d.fillOval(20, y - 10, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("已访问节点", 45, y);
        y += 20;

        // 未访问节点
        g2d.setColor(NODE_COLOR);
        g2d.fillOval(20, y - 10, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("未访问节点", 45, y);
    }

    private void updateStatus(String message) {
        statusLabel.setText("状态: " + message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("NO.834 树中距离之和 - 动画演示");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new NO834_H_SumOfDistancesInTree_Animation());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}