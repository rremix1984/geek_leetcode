/**
 * copyright 2024
 */
package com.leetcode.hard.animation;

import com.leetcode.util.TreeNode;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * NO.297 二叉树的序列化与反序列化 - 动画演示
 * 
 * 核心算法：层序遍历 + 队列
 * 序列化：使用BFS遍历二叉树，将节点值和null按层序存储
 * 反序列化：根据序列化字符串重建二叉树
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 */
public class NO297_H_SerializeAndDeserializeBinaryTree_Animation extends JPanel {

    private TreeNode root;
    private String serializedData;
    private TreeNode deserializedRoot;
    private Queue<TreeNode> serializeQueue;
    private Queue<TreeNode> deserializeQueue;
    private String[] deserializeArray;
    private int deserializeIndex;
    private StringBuilder serializeResult;
    private boolean isSerializing;
    private boolean isDeserializing;
    private TreeNode currentNode;
    private TreeNode currentDeserializeNode;
    private int step;
    private javax.swing.Timer timer;
    private boolean isRunning;
    private boolean isPaused;

    // UI组件
    private JButton startBtn, pauseBtn, resetBtn, stepBtn;
    private JSlider speedSlider;
    private JTextField inputField;
    private JTextArea logArea;
    private JLabel statusLabel;

    // 绘制参数
    private final int NODE_RADIUS = 25;
    private final int LEVEL_HEIGHT = 80;
    private final int NODE_SPACING = 60;
    private final Color NODE_COLOR = Color.CYAN;
    private final Color CURRENT_NODE_COLOR = Color.ORANGE;
    private final Color VISITED_NODE_COLOR = new Color(144, 238, 144);
    private final Color QUEUE_COLOR = Color.YELLOW;

    public NO297_H_SerializeAndDeserializeBinaryTree_Animation() {
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

        inputField = new JTextField("1,2,3,null,null,4,5", 20);
        logArea = new JTextArea(8, 30);
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        statusLabel = new JLabel("状态: 准备开始");

        timer = new Timer(1000, e -> nextStep());
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // 控制面板
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(new JLabel("输入数组:"));
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

        setPreferredSize(new Dimension(1000, 700));
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
        // 创建示例二叉树 [1,2,3,null,null,4,5]
        root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(5);

        reset();
    }

    public void start() {
        if (!isRunning) {
            parseInput();
            isRunning = true;
            isPaused = false;
            timer.start();
            updateStatus("开始序列化...");
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
        isSerializing = true;
        isDeserializing = false;
        step = 0;
        deserializeIndex = 1;

        serializeQueue = new LinkedList<>();
        deserializeQueue = new LinkedList<>();
        serializeResult = new StringBuilder();
        serializedData = "";
        deserializedRoot = null;
        currentNode = null;
        currentDeserializeNode = null;

        if (root != null) {
            serializeQueue.offer(root);
            serializeResult.append("[");
        }

        logArea.setText("");
        updateStatus("已重置，准备开始");
        repaint();
    }

    private void parseInput() {
        try {
            String input = inputField.getText().trim();
            if (input.isEmpty())
                return;

            // 解析输入创建二叉树
            String[] values = input.split(",");
            if (values.length == 0)
                return;

            root = buildTreeFromArray(values);
            reset();

        } catch (Exception e) {
            updateStatus("输入格式错误: " + e.getMessage());
        }
    }

    private TreeNode buildTreeFromArray(String[] values) {
        if (values.length == 0 || "null".equals(values[0].trim())) {
            return null;
        }

        TreeNode root = new TreeNode(Integer.parseInt(values[0].trim()));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;
        while (!queue.isEmpty() && i < values.length) {
            TreeNode node = queue.poll();

            // 左子节点
            if (i < values.length && !"null".equals(values[i].trim())) {
                node.left = new TreeNode(Integer.parseInt(values[i].trim()));
                queue.offer(node.left);
            }
            i++;

            // 右子节点
            if (i < values.length && !"null".equals(values[i].trim())) {
                node.right = new TreeNode(Integer.parseInt(values[i].trim()));
                queue.offer(node.right);
            }
            i++;
        }

        return root;
    }

    private void nextStep() {
        if (isSerializing) {
            serializeStep();
        } else if (isDeserializing) {
            deserializeStep();
        }
        repaint();
    }

    private void serializeStep() {
        if (serializeQueue.isEmpty()) {
            // 序列化完成，开始反序列化
            serializedData = serializeResult.toString();
            if (serializedData.endsWith(",")) {
                serializedData = serializedData.substring(0, serializedData.length() - 1);
            }
            serializedData += "]";

            logArea.append("序列化完成: " + serializedData + "\n");

            // 准备反序列化
            isSerializing = false;
            isDeserializing = true;

            if (!serializedData.equals("[]")) {
                deserializeArray = serializedData.substring(1, serializedData.length() - 1).split(",");
                deserializedRoot = new TreeNode(Integer.parseInt(deserializeArray[0].trim()));
                deserializeQueue.offer(deserializedRoot);
                currentDeserializeNode = deserializedRoot;
            }

            updateStatus("开始反序列化...");
            return;
        }

        currentNode = serializeQueue.poll();

        if (currentNode != null) {
            serializeResult.append(currentNode.val);
            serializeQueue.offer(currentNode.left);
            serializeQueue.offer(currentNode.right);
            logArea.append("序列化节点: " + currentNode.val + "\n");
        } else {
            serializeResult.append("null");
            logArea.append("序列化节点: null\n");
        }

        serializeResult.append(",");
        step++;
    }

    private void deserializeStep() {
        if (deserializeQueue.isEmpty() || deserializeIndex >= deserializeArray.length) {
            // 反序列化完成
            timer.stop();
            isRunning = false;
            isDeserializing = false;
            updateStatus("反序列化完成!");
            logArea.append("反序列化完成!\n");
            return;
        }

        currentDeserializeNode = deserializeQueue.poll();

        // 处理左子节点
        if (deserializeIndex < deserializeArray.length) {
            String leftVal = deserializeArray[deserializeIndex].trim();
            if (!"null".equals(leftVal)) {
                currentDeserializeNode.left = new TreeNode(Integer.parseInt(leftVal));
                deserializeQueue.offer(currentDeserializeNode.left);
                logArea.append("创建左子节点: " + leftVal + "\n");
            } else {
                logArea.append("左子节点为null\n");
            }
            deserializeIndex++;
        }

        // 处理右子节点
        if (deserializeIndex < deserializeArray.length) {
            String rightVal = deserializeArray[deserializeIndex].trim();
            if (!"null".equals(rightVal)) {
                currentDeserializeNode.right = new TreeNode(Integer.parseInt(rightVal));
                deserializeQueue.offer(currentDeserializeNode.right);
                logArea.append("创建右子节点: " + rightVal + "\n");
            } else {
                logArea.append("右子节点为null\n");
            }
            deserializeIndex++;
        }

        step++;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight() - 200; // 为控制面板和日志区域留空间

        // 绘制原始二叉树
        if (root != null) {
            g2d.setColor(Color.BLACK);
            g2d.drawString("原始二叉树:", 20, 30);
            drawTree(g2d, root, width / 4, 50, width / 8, 0, currentNode);
        }

        // 绘制反序列化后的二叉树
        if (deserializedRoot != null) {
            g2d.setColor(Color.BLACK);
            g2d.drawString("反序列化后的二叉树:", width / 2 + 20, 30);
            drawTree(g2d, deserializedRoot, 3 * width / 4, 50, width / 8, 0, currentDeserializeNode);
        }

        // 绘制序列化结果
        if (serializeResult.length() > 0) {
            g2d.setColor(Color.BLACK);
            g2d.drawString("序列化结果: " + serializeResult.toString(), 20, height - 80);
        }

        // 绘制队列状态
        drawQueue(g2d, 20, height - 50);
    }

    private void drawTree(Graphics2D g2d, TreeNode node, int x, int y, int xOffset, int level, TreeNode highlight) {
        if (node == null)
            return;

        // 绘制连线
        if (node.left != null) {
            g2d.setColor(Color.BLACK);
            g2d.drawLine(x, y, x - xOffset, y + LEVEL_HEIGHT);
            drawTree(g2d, node.left, x - xOffset, y + LEVEL_HEIGHT, xOffset / 2, level + 1, highlight);
        }

        if (node.right != null) {
            g2d.setColor(Color.BLACK);
            g2d.drawLine(x, y, x + xOffset, y + LEVEL_HEIGHT);
            drawTree(g2d, node.right, x + xOffset, y + LEVEL_HEIGHT, xOffset / 2, level + 1, highlight);
        }

        // 绘制节点
        if (node == highlight) {
            g2d.setColor(CURRENT_NODE_COLOR);
        } else {
            g2d.setColor(NODE_COLOR);
        }

        g2d.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, 2 * NODE_RADIUS, 2 * NODE_RADIUS);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(x - NODE_RADIUS, y - NODE_RADIUS, 2 * NODE_RADIUS, 2 * NODE_RADIUS);

        // 绘制节点值
        FontMetrics fm = g2d.getFontMetrics();
        String value = String.valueOf(node.val);
        int textX = x - fm.stringWidth(value) / 2;
        int textY = y + fm.getAscent() / 2;
        g2d.drawString(value, textX, textY);
    }

    private void drawQueue(Graphics2D g2d, int startX, int startY) {
        g2d.setColor(Color.BLACK);

        if (isSerializing && !serializeQueue.isEmpty()) {
            g2d.drawString("序列化队列: ", startX, startY);
            int x = startX + 100;
            for (TreeNode node : serializeQueue) {
                g2d.setColor(QUEUE_COLOR);
                g2d.fillRect(x, startY - 15, 40, 20);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, startY - 15, 40, 20);

                String val = (node == null) ? "null" : String.valueOf(node.val);
                g2d.drawString(val, x + 5, startY);
                x += 45;
            }
        } else if (isDeserializing && !deserializeQueue.isEmpty()) {
            g2d.drawString("反序列化队列: ", startX, startY);
            int x = startX + 120;
            for (TreeNode node : deserializeQueue) {
                g2d.setColor(QUEUE_COLOR);
                g2d.fillRect(x, startY - 15, 40, 20);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, startY - 15, 40, 20);

                String val = String.valueOf(node.val);
                g2d.drawString(val, x + 5, startY);
                x += 45;
            }
        }
    }

    private void updateStatus(String message) {
        statusLabel.setText("状态: " + message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("NO.297 二叉树的序列化与反序列化 - 动画演示");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new NO297_H_SerializeAndDeserializeBinaryTree_Animation());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
