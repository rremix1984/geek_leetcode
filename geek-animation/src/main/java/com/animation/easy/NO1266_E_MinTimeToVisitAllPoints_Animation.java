package com.animation.easy;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 1266. 访问所有点的最小时间 - 动画演示
 * 使用切比雪夫距离计算访问所有点的最小时间
 */
public class NO1266_E_MinTimeToVisitAllPoints_Animation extends JFrame {
    
    private int[][] points;
    private List<AnimationState> animationStates;
    private int currentStateIndex;
    private AnimationPanel animationPanel;
    
    public NO1266_E_MinTimeToVisitAllPoints_Animation() {
        initializeData();
        initializeAnimationStates();
        setupUI();
    }
    
    private void initializeData() {
        // 使用示例数据
        points = new int[][]{{1, 1}, {3, 4}, {-1, 0}};
        currentStateIndex = 0;
    }
    
    private void initializeAnimationStates() {
        animationStates = new ArrayList<>();
        
        // 初始状态
        animationStates.add(new AnimationState(
            "开始访问点的路径",
            -1, -1, -1, -1,
            0, 0,
            "初始化：准备按顺序访问所有点"
        ));
        
        int totalTime = 0;
        int x0 = points[0][0];
        int y0 = points[0][1];
        
        // 添加起始点状态
        animationStates.add(new AnimationState(
            "起始点",
            x0, y0, -1, -1,
            0, totalTime,
            String.format("起始点: (%d, %d)", x0, y0)
        ));
        
        // 遍历每个点，计算切比雪夫距离
        for (int i = 1; i < points.length; i++) {
            int x1 = points[i][0];
            int y1 = points[i][1];
            
            int dx = Math.abs(x1 - x0);
            int dy = Math.abs(y1 - y0);
            int distance = Math.max(dx, dy);
            
            // 添加计算距离的状态
            animationStates.add(new AnimationState(
                "计算距离",
                x0, y0, x1, y1,
                distance, totalTime,
                String.format("从 (%d,%d) 到 (%d,%d): dx=%d, dy=%d, 距离=%d", 
                    x0, y0, x1, y1, dx, dy, distance)
            ));
            
            totalTime += distance;
            
            // 添加累计时间的状态
            animationStates.add(new AnimationState(
                "累计时间",
                x0, y0, x1, y1,
                distance, totalTime,
                String.format("累计时间: %d + %d = %d", totalTime - distance, distance, totalTime)
            ));
            
            x0 = x1;
            y0 = y1;
        }
        
        // 最终结果状态
        animationStates.add(new AnimationState(
            "最终结果",
            -1, -1, -1, -1,
            0, totalTime,
            String.format("访问所有点的最小时间: %d 秒", totalTime)
        ));
    }
    
    private void setupUI() {
        setLayout(new BorderLayout());
        
        // 创建动画面板
        animationPanel = new AnimationPanel();
        add(animationPanel, BorderLayout.CENTER);
        
        // 创建控制面板
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.SOUTH);
        
        // 创建信息面板
        JPanel infoPanel = createInfoPanel();
        add(infoPanel, BorderLayout.EAST);
        
        updateDisplay();
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        JButton playButton = new JButton("播放");
        JButton pauseButton = new JButton("暂停");
        JButton nextButton = new JButton("下一步");
        JButton resetButton = new JButton("重置");
        
        JSlider speedSlider = new JSlider(1, 10, 5);
        speedSlider.setMajorTickSpacing(3);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        
        playButton.addActionListener(e -> startAnimation());
        pauseButton.addActionListener(e -> pauseAnimation());
        nextButton.addActionListener(e -> nextStep());
        resetButton.addActionListener(e -> resetAnimation());
        speedSlider.addChangeListener(e -> setAnimationSpeed(speedSlider.getValue()));
        
        panel.add(new JLabel("控制:"));
        panel.add(playButton);
        panel.add(pauseButton);
        panel.add(nextButton);
        panel.add(resetButton);
        panel.add(new JLabel("速度:"));
        panel.add(speedSlider);
        
        return panel;
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(300, 0));
        
        JTextArea logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        panel.add(new JLabel("算法执行日志:"), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // 设置日志区域
        // LogUtils.setLogArea(logArea);
        
        return panel;
    }
    
    private void updateDisplay() {
        if (currentStateIndex < animationStates.size()) {
            AnimationState state = animationStates.get(currentStateIndex);
            System.out.println(state.description);
        }
        animationPanel.repaint();
    }
    
    private void nextStep() {
        if (currentStateIndex < animationStates.size() - 1) {
            currentStateIndex++;
            updateDisplay();
        }
    }
    
    private void resetAnimation() {
        currentStateIndex = 0;
        updateDisplay();
        // LogUtils.clearLog();
    }
    
    private Timer animationTimer;
    private boolean isPlaying = false;
    private int animationSpeed = 5;
    
    private void startAnimation() {
        if (!isPlaying && currentStateIndex < animationStates.size() - 1) {
            isPlaying = true;
            int delay = 1100 - animationSpeed * 100; // 速度越大，延迟越小
            animationTimer = new Timer(delay, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    nextStep();
                    if (currentStateIndex >= animationStates.size() - 1) {
                        pauseAnimation();
                    }
                }
            });
            animationTimer.start();
        }
    }
    
    private void pauseAnimation() {
        isPlaying = false;
        if (animationTimer != null) {
            animationTimer.stop();
            animationTimer = null;
        }
    }
    
    private void setAnimationSpeed(int speed) {
        this.animationSpeed = speed;
        if (isPlaying && animationTimer != null) {
            pauseAnimation();
            startAnimation();
        }
    }
    
    // 动画状态类
    private static class AnimationState {
        String phase;
        int x0, y0, x1, y1;
        int distance;
        int totalTime;
        String description;
        
        public AnimationState(String phase, int x0, int y0, int x1, int y1, 
                            int distance, int totalTime, String description) {
            this.phase = phase;
            this.x0 = x0;
            this.y0 = y0;
            this.x1 = x1;
            this.y1 = y1;
            this.distance = distance;
            this.totalTime = totalTime;
            this.description = description;
        }
    }
    
    // 动画面板类
    private class AnimationPanel extends JPanel {
        private AnimationState currentState;
        
        public void updateState(AnimationState state) {
            this.currentState = state;
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            if (currentState == null) return;
            
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // 设置坐标系
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            int scale = 30;
            
            // 绘制坐标轴
            drawCoordinateSystem(g2d, centerX, centerY, scale);
            
            // 绘制所有点
            drawAllPoints(g2d, centerX, centerY, scale);
            
            // 绘制当前路径
            if (currentState.x0 != -1 && currentState.x1 != -1) {
                drawPath(g2d, centerX, centerY, scale);
            }
            
            // 绘制信息
            drawInfo(g2d);
            
            g2d.dispose();
        }
        
        private void drawCoordinateSystem(Graphics2D g2d, int centerX, int centerY, int scale) {
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.setStroke(new BasicStroke(1));
            
            // 绘制网格
            for (int i = -10; i <= 10; i++) {
                int x = centerX + i * scale;
                int y = centerY + i * scale;
                g2d.drawLine(x, centerY - 10 * scale, x, centerY + 10 * scale);
                g2d.drawLine(centerX - 10 * scale, y, centerX + 10 * scale, y);
            }
            
            // 绘制坐标轴
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(centerX - 10 * scale, centerY, centerX + 10 * scale, centerY);
            g2d.drawLine(centerX, centerY - 10 * scale, centerX, centerY + 10 * scale);
        }
        
        private void drawAllPoints(Graphics2D g2d, int centerX, int centerY, int scale) {
            for (int i = 0; i < points.length; i++) {
                int x = centerX + points[i][0] * scale;
                int y = centerY - points[i][1] * scale;
                
                // 绘制点
                g2d.setColor(Color.BLUE);
                g2d.fillOval(x - 8, y - 8, 16, 16);
                
                // 绘制点的标签
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
                String label = String.format("P%d(%d,%d)", i, points[i][0], points[i][1]);
                g2d.drawString(label, x + 12, y - 12);
            }
        }
        
        private void drawPath(Graphics2D g2d, int centerX, int centerY, int scale) {
            int x0 = centerX + currentState.x0 * scale;
            int y0 = centerY - currentState.y0 * scale;
            int x1 = centerX + currentState.x1 * scale;
            int y1 = centerY - currentState.y1 * scale;
            
            // 绘制路径线
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(x0, y0, x1, y1);
            
            // 绘制箭头
            drawArrow(g2d, x0, y0, x1, y1);
            
            // 高亮当前点
            g2d.setColor(Color.GREEN);
            g2d.fillOval(x0 - 10, y0 - 10, 20, 20);
            g2d.setColor(Color.ORANGE);
            g2d.fillOval(x1 - 10, y1 - 10, 20, 20);
        }
        
        private void drawArrow(Graphics2D g2d, int x0, int y0, int x1, int y1) {
            double angle = Math.atan2(y1 - y0, x1 - x0);
            int arrowLength = 15;
            double arrowAngle = Math.PI / 6;
            
            int arrowX1 = (int) (x1 - arrowLength * Math.cos(angle - arrowAngle));
            int arrowY1 = (int) (y1 - arrowLength * Math.sin(angle - arrowAngle));
            int arrowX2 = (int) (x1 - arrowLength * Math.cos(angle + arrowAngle));
            int arrowY2 = (int) (y1 - arrowLength * Math.sin(angle + arrowAngle));
            
            g2d.drawLine(x1, y1, arrowX1, arrowY1);
            g2d.drawLine(x1, y1, arrowX2, arrowY2);
        }
        
        private void drawInfo(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            
            int y = 30;
            g2d.drawString("阶段: " + currentState.phase, 10, y);
            y += 25;
            
            if (currentState.distance > 0) {
                g2d.drawString("当前距离: " + currentState.distance, 10, y);
                y += 25;
            }
            
            g2d.drawString("累计时间: " + currentState.totalTime, 10, y);
            y += 25;
            
            // 绘制算法说明
            g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
            g2d.drawString("切比雪夫距离 = max(|x1-x0|, |y1-y0|)", 10, y);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NO1266_E_MinTimeToVisitAllPoints_Animation frame = new NO1266_E_MinTimeToVisitAllPoints_Animation();
            frame.setTitle("1266. 访问所有点的最小时间 - 动画演示");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}