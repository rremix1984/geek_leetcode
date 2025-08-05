package com.animation.hard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

/**
 * NO.85 最大矩形 - 动画演示
 * <p>
 * 设计文档：
 * 1. 功能需求：可视化展示求解最大矩形面积的算法过程。
 * 2. 核心算法：将问题转化为“柱状图中最大的矩形”（NO.84），逐行处理，使用单调栈计算最大面积。
 * 3. 动画展示要点：
 *    - 矩阵的绘制。
 *    - 逐行生成直方图高度数组的过程。
 *    - 单调栈的入栈、出栈操作。
 *    - 每次计算并更新最大面积时的矩形高亮显示。
 * 4. 交互功能：
 *    - 用户可输入自定义矩阵。
 *    - 提供“开始/暂停”、“单步执行”、“重置”功能。
 *    - 显示当前行、当前直方图、单调栈状态和当前最大面积。
 */
public class NO85_H_MaximalRectangle_Animation extends JFrame {

    private static final int CELL_SIZE = 40;
    private static final int PADDING = 20;
    private static final Color MATRIX_COLOR = new Color(200, 200, 200);
    private static final Color HISTOGRAM_COLOR = new Color(70, 130, 180);
    private static final Color MAX_RECT_COLOR = new Color(255, 99, 71, 150);
    private static final Color CURRENT_RECT_COLOR = new Color(255, 165, 0, 150);

    private char[][] matrix;
    private int[] heights;
    private int currentRow = -1;
    private int maxArea = 0;
    private Rectangle maxAreaRect = null;

    private Timer animationTimer;
    private boolean isPlaying = false;
    private Stack<Integer> stack;
    private int histogramIndex = 0;

    private AnimationPanel animationPanel;
    private JButton startButton, stepButton, resetButton;
    private JTextArea infoArea;

    public NO85_H_MaximalRectangle_Animation() {
        // 默认矩阵
        this.matrix = new char[][]{
                {'1', '0', '1', '0', '0'},
                {'1', '0', '1', '1', '1'},
                {'1', '1', '1', '1', '1'},
                {'1', '0', '0', '1', '0'}
        };
        initUI();
    }

    private void initUI() {
        setTitle("NO.85 最大矩形 - 动画演示");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        animationPanel = new AnimationPanel();
        add(animationPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        startButton = new JButton("开始/暂停");
        stepButton = new JButton("单步");
        resetButton = new JButton("重置");
        controlPanel.add(startButton);
        controlPanel.add(stepButton);
        controlPanel.add(resetButton);

        infoArea = new JTextArea(5, 30);
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(controlPanel, BorderLayout.NORTH);
        bottomPanel.add(new JScrollPane(infoArea), BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        startButton.addActionListener(e -> toggleAnimation());
        stepButton.addActionListener(e -> step());
        resetButton.addActionListener(e -> reset());

        animationTimer = new Timer(1000, e -> step());
        reset();
    }

    private void toggleAnimation() {
        isPlaying = !isPlaying;
        if (isPlaying) {
            animationTimer.start();
            startButton.setText("暂停");
        } else {
            animationTimer.stop();
            startButton.setText("开始");
        }
    }

    private void reset() {
        animationTimer.stop();
        isPlaying = false;
        startButton.setText("开始");
        currentRow = -1;
        maxArea = 0;
        maxAreaRect = null;
        heights = new int[matrix[0].length];
        stack = new Stack<>();
        histogramIndex = 0;
        updateInfo();
        animationPanel.repaint();
    }

    private void step() {
        if (currentRow >= matrix.length) {
            animationTimer.stop();
            isPlaying = false;
            startButton.setText("完成");
            return;
        }

        if (currentRow == -1 || histogramIndex >= heights.length) {
            currentRow++;
            if (currentRow >= matrix.length) return;
            histogramIndex = 0;
            stack.clear();
            updateHeights();
        }

        // 使用单调栈计算当前行直方图的最大面积
        calculateMaxInHistogram();

        updateInfo();
        animationPanel.repaint();
    }

    private void updateHeights() {
        for (int j = 0; j < matrix[0].length; j++) {
            if (matrix[currentRow][j] == '1') {
                heights[j]++;
            } else {
                heights[j] = 0;
            }
        }
    }

    private void calculateMaxInHistogram() {
        // 这里简化为一次性计算，实际动画可以分步展示栈操作
        Stack<Integer> s = new Stack<>();
        for (int i = 0; i <= heights.length; i++) {
            int h = (i == heights.length) ? 0 : heights[i];
            while (!s.isEmpty() && h < heights[s.peek()]) {
                int height = heights[s.pop()];
                int width = s.isEmpty() ? i : i - 1 - s.peek();
                if (height * width > maxArea) {
                    maxArea = height * width;
                    // 计算实际的矩形位置
                    int startX = (s.isEmpty() ? 0 : s.peek() + 1);
                    int startY = currentRow - height + 1;
                    maxAreaRect = new Rectangle(startX, startY, width, height);
                }
            }
            s.push(i);
        }
        histogramIndex = heights.length; // 标记当前行处理完毕
    }

    private void updateInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("当前行: %d\n", currentRow));
        sb.append("直方图高度: ").append(java.util.Arrays.toString(heights)).append("\n");
        sb.append(String.format("最大面积: %d", maxArea));
        infoArea.setText(sb.toString());
    }

    class AnimationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 绘制矩阵
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    g2d.setColor(matrix[i][j] == '1' ? Color.BLACK : MATRIX_COLOR);
                    g2d.fillRect(PADDING + j * CELL_SIZE, PADDING + i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    g2d.setColor(Color.WHITE);
                    g2d.drawRect(PADDING + j * CELL_SIZE, PADDING + i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }

            // 绘制当前处理的直方图
            if (currentRow != -1) {
                g2d.setColor(HISTOGRAM_COLOR);
                for (int j = 0; j < heights.length; j++) {
                    if (heights[j] > 0) {
                        g2d.fillRect(PADDING + j * CELL_SIZE, PADDING + (currentRow - heights[j] + 1) * CELL_SIZE, CELL_SIZE, heights[j] * CELL_SIZE);
                    }
                }
            }

            // 高亮显示最大面积矩形
            if (maxAreaRect != null) {
                g2d.setColor(MAX_RECT_COLOR);
                g2d.fillRect(PADDING + maxAreaRect.x * CELL_SIZE, PADDING + maxAreaRect.y * CELL_SIZE, maxAreaRect.width * CELL_SIZE, maxAreaRect.height * CELL_SIZE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NO85_H_MaximalRectangle_Animation frame = new NO85_H_MaximalRectangle_Animation();
            frame.setVisible(true);
        });
    }
}