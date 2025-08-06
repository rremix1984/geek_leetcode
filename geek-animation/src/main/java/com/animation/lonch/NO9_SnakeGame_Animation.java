package com.animation.lonch;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

/**
 * NO.9 贪吃蛇游戏动画
 * 
 * 动画要点：
 * 1. 可视化贪吃蛇游戏的完整过程
 * 2. 展示蛇的移动、食物生成、碰撞检测
 * 3. 实时显示游戏状态和得分
 * 4. 支持键盘控制和自动演示
 * 5. 展示游戏算法的核心逻辑
 */
public class NO9_SnakeGame_Animation extends JFrame implements KeyListener {
    
    // 游戏常量
    private static final int BOARD_WIDTH = 600;
    private static final int BOARD_HEIGHT = 600;
    private static final int ROWS = 20;
    private static final int COLS = 20;
    private static final int CELL_SIZE = BOARD_WIDTH / COLS;
    
    // 颜色定义
    private static final Color SNAKE_COLOR = Color.GREEN;
    private static final Color FOOD_COLOR = Color.RED;
    private static final Color BOARD_COLOR = Color.BLACK;
    private static final Color GRID_COLOR = Color.GRAY;
    
    // 方向枚举
    enum Direction {
        UP(-1, 0, "上"),
        DOWN(1, 0, "下"),
        LEFT(0, -1, "左"),
        RIGHT(0, 1, "右");
        
        final int dx, dy;
        final String name;
        
        Direction(int dx, int dy, String name) {
            this.dx = dx;
            this.dy = dy;
            this.name = name;
        }
    }
    
    // GUI组件
    private JPanel drawPanel;
    private JButton startButton;
    private JButton pauseButton;
    private JButton resetButton;
    private JButton autoPlayButton;
    private JLabel scoreLabel;
    private JLabel statusLabel;
    private JTextArea logArea;
    private JComboBox<String> speedCombo;
    
    // 游戏状态
    private LinkedList<Point> snake;
    private Point food;
    private Direction currentDirection;
    private int score;
    private boolean gameRunning;
    private boolean gameOver;
    private boolean autoPlay;
    private Timer gameTimer;
    private Random random;
    
    // 自动游戏AI
    private Queue<Direction> moveQueue;
    
    public NO9_SnakeGame_Animation() {
        initializeGUI();
        initializeGame();
    }
    
    private void initializeGUI() {
        setTitle("NO.9 贪吃蛇游戏动画");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // 创建绘图面板
        drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGame(g);
            }
        };
        drawPanel.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        drawPanel.setBackground(BOARD_COLOR);
        drawPanel.setFocusable(true);
        drawPanel.addKeyListener(this);
        
        // 创建控制面板
        JPanel controlPanel = createControlPanel();
        
        // 创建信息面板
        JPanel infoPanel = createInfoPanel();
        
        add(drawPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        
        // 设置焦点
        drawPanel.requestFocusInWindow();
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        // 游戏控制按钮
        startButton = new JButton("开始游戏");
        startButton.addActionListener(e -> startGame());
        panel.add(startButton);
        
        pauseButton = new JButton("暂停");
        pauseButton.addActionListener(e -> pauseGame());
        pauseButton.setEnabled(false);
        panel.add(pauseButton);
        
        resetButton = new JButton("重置");
        resetButton.addActionListener(e -> resetGame());
        panel.add(resetButton);
        
        autoPlayButton = new JButton("自动演示");
        autoPlayButton.addActionListener(e -> toggleAutoPlay());
        panel.add(autoPlayButton);
        
        // 速度控制
        panel.add(new JLabel("速度:"));
        String[] speeds = {"慢速", "中速", "快速", "极速"};
        speedCombo = new JComboBox<>(speeds);
        speedCombo.setSelectedIndex(1);
        speedCombo.addActionListener(e -> updateGameSpeed());
        panel.add(speedCombo);
        
        // 游戏状态显示
        scoreLabel = new JLabel("得分: 0");
        scoreLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        panel.add(scoreLabel);
        
        statusLabel = new JLabel("准备开始");
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        panel.add(statusLabel);
        
        return panel;
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // 游戏日志
        logArea = new JTextArea(8, 50);
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder("游戏日志"));
        panel.add(logScroll, BorderLayout.CENTER);
        
        // 控制说明
        JPanel instructionPanel = new JPanel(new GridLayout(5, 1));
        instructionPanel.setBorder(BorderFactory.createTitledBorder("控制说明"));
        instructionPanel.add(new JLabel("W/↑ - 向上移动"));
        instructionPanel.add(new JLabel("S/↓ - 向下移动"));
        instructionPanel.add(new JLabel("A/← - 向左移动"));
        instructionPanel.add(new JLabel("D/→ - 向右移动"));
        instructionPanel.add(new JLabel("空格 - 暂停/继续"));
        panel.add(instructionPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    private void initializeGame() {
        snake = new LinkedList<>();
        random = new Random();
        moveQueue = new LinkedList<>();
        resetGame();
    }
    
    private void resetGame() {
        // 停止游戏定时器
        if (gameTimer != null) {
            gameTimer.stop();
        }
        
        // 重置游戏状态
        snake.clear();
        snake.add(new Point(ROWS/2, COLS/2)); // 蛇的初始位置
        currentDirection = Direction.RIGHT;
        score = 0;
        gameRunning = false;
        gameOver = false;
        autoPlay = false;
        
        // 生成初始食物
        generateFood();
        
        // 更新UI
        updateUI();
        SwingUtilities.invokeLater(() -> drawPanel.repaint());
        
        // 清空日志
        logArea.setText("");
        addLog("游戏重置完成");
        addLog("蛇的初始位置: (" + (ROWS/2) + ", " + (COLS/2) + ")");
        addLog("食物位置: (" + food.x + ", " + food.y + ")");
        
        // 重新获取焦点
        drawPanel.requestFocusInWindow();
    }
    
    private void startGame() {
        if (!gameRunning && !gameOver) {
            gameRunning = true;
            startButton.setEnabled(false);
            pauseButton.setEnabled(true);
            
            // 创建游戏定时器
            int delay = getGameDelay();
            gameTimer = new Timer(delay, e -> gameStep());
            gameTimer.start();
            
            statusLabel.setText("游戏进行中");
            addLog("游戏开始！");
        }
    }
    
    private void pauseGame() {
        if (gameRunning) {
            gameTimer.stop();
            gameRunning = false;
            startButton.setEnabled(true);
            pauseButton.setEnabled(false);
            statusLabel.setText("游戏暂停");
            addLog("游戏暂停");
        }
    }
    
    private void toggleAutoPlay() {
        autoPlay = !autoPlay;
        autoPlayButton.setText(autoPlay ? "手动控制" : "自动演示");
        
        if (autoPlay) {
            addLog("切换到自动演示模式");
            if (!gameRunning && !gameOver) {
                startGame();
            }
        } else {
            addLog("切换到手动控制模式");
        }
        
        drawPanel.requestFocusInWindow();
    }
    
    private void gameStep() {
        if (!gameRunning || gameOver) return;
        
        // 自动游戏AI
        if (autoPlay) {
            currentDirection = getNextDirection();
        }
        
        // 移动蛇
        moveSnake();
        
        // 检查碰撞
        if (checkCollision()) {
            gameOver();
            return;
        }
        
        // 检查是否吃到食物
        if (checkFood()) {
            eatFood();
        }
        
        // 更新显示
        updateUI();
        SwingUtilities.invokeLater(() -> drawPanel.repaint());
    }
    
    private void moveSnake() {
        Point head = snake.getFirst();
        Point newHead = new Point(
            head.x + currentDirection.dx,
            head.y + currentDirection.dy
        );
        
        snake.addFirst(newHead);
        
        // 如果没有吃到食物，移除尾部
        if (!newHead.equals(food)) {
            snake.removeLast();
        }
        
        addLog("蛇移动到: (" + newHead.x + ", " + newHead.y + ") 方向: " + currentDirection.name);
    }
    
    private boolean checkCollision() {
        Point head = snake.getFirst();
        
        // 检查边界碰撞
        if (head.x < 0 || head.x >= ROWS || head.y < 0 || head.y >= COLS) {
            addLog("撞墙！位置: (" + head.x + ", " + head.y + ")");
            return true;
        }
        
        // 检查自身碰撞
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                addLog("撞到自己！位置: (" + head.x + ", " + head.y + ")");
                return true;
            }
        }
        
        return false;
    }
    
    private boolean checkFood() {
        return snake.getFirst().equals(food);
    }
    
    private void eatFood() {
        score++;
        addLog("吃到食物！得分: " + score);
        generateFood();
        addLog("新食物生成: (" + food.x + ", " + food.y + ")");
    }
    
    private void generateFood() {
        do {
            food = new Point(random.nextInt(ROWS), random.nextInt(COLS));
        } while (snake.contains(food));
    }
    
    private void gameOver() {
        gameRunning = false;
        gameOver = true;
        gameTimer.stop();
        
        startButton.setEnabled(false);
        pauseButton.setEnabled(false);
        statusLabel.setText("游戏结束");
        
        addLog("游戏结束！最终得分: " + score);
        
        // 显示游戏结束对话框
        int option = JOptionPane.showConfirmDialog(
            this,
            "游戏结束！得分: " + score + "\\n是否重新开始？",
            "游戏结束",
            JOptionPane.YES_NO_OPTION
        );
        
        if (option == JOptionPane.YES_OPTION) {
            resetGame();
        }
    }
    
    private Direction getNextDirection() {
        // 简单的AI：朝食物方向移动
        Point head = snake.getFirst();
        
        // 计算到食物的距离
        int dx = food.x - head.x;
        int dy = food.y - head.y;
        
        // 优先选择距离更远的方向
        Direction bestDirection = currentDirection;
        
        if (Math.abs(dx) > Math.abs(dy)) {
            bestDirection = dx > 0 ? Direction.DOWN : Direction.UP;
        } else {
            bestDirection = dy > 0 ? Direction.RIGHT : Direction.LEFT;
        }
        
        // 检查是否会撞墙或撞自己
        if (isValidDirection(bestDirection)) {
            return bestDirection;
        }
        
        // 如果最佳方向不可行，尝试其他方向
        for (Direction dir : Direction.values()) {
            if (isValidDirection(dir) && !isOppositeDirection(dir)) {
                return dir;
            }
        }
        
        return currentDirection; // 如果没有安全方向，保持当前方向
    }
    
    private boolean isValidDirection(Direction direction) {
        Point head = snake.getFirst();
        Point newHead = new Point(
            head.x + direction.dx,
            head.y + direction.dy
        );
        
        // 检查边界
        if (newHead.x < 0 || newHead.x >= ROWS || newHead.y < 0 || newHead.y >= COLS) {
            return false;
        }
        
        // 检查是否撞到自己（除了尾部，因为尾部会移动）
        for (int i = 0; i < snake.size() - 1; i++) {
            if (newHead.equals(snake.get(i))) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean isOppositeDirection(Direction direction) {
        return (currentDirection == Direction.UP && direction == Direction.DOWN) ||
               (currentDirection == Direction.DOWN && direction == Direction.UP) ||
               (currentDirection == Direction.LEFT && direction == Direction.RIGHT) ||
               (currentDirection == Direction.RIGHT && direction == Direction.LEFT);
    }
    
    private int getGameDelay() {
        switch (speedCombo.getSelectedIndex()) {
            case 0: return 300; // 慢速
            case 1: return 200; // 中速
            case 2: return 100; // 快速
            case 3: return 50;  // 极速
            default: return 200;
        }
    }
    
    private void updateGameSpeed() {
        if (gameTimer != null && gameTimer.isRunning()) {
            gameTimer.setDelay(getGameDelay());
        }
    }
    
    private void updateUI() {
        scoreLabel.setText("得分: " + score);
        
        if (gameOver) {
            statusLabel.setText("游戏结束");
        } else if (gameRunning) {
            statusLabel.setText("游戏进行中");
        } else {
            statusLabel.setText("游戏暂停");
        }
    }
    
    private void addLog(String message) {
        logArea.append(message + "\\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void drawGame(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制网格
        drawGrid(g2d);
        
        // 绘制食物
        drawFood(g2d);
        
        // 绘制蛇
        drawSnake(g2d);
        
        // 绘制游戏信息
        drawGameInfo(g2d);
    }
    
    private void drawGrid(Graphics2D g2d) {
        g2d.setColor(GRID_COLOR);
        
        // 绘制垂直线
        for (int i = 0; i <= COLS; i++) {
            int x = i * CELL_SIZE;
            g2d.drawLine(x, 0, x, BOARD_HEIGHT);
        }
        
        // 绘制水平线
        for (int i = 0; i <= ROWS; i++) {
            int y = i * CELL_SIZE;
            g2d.drawLine(0, y, BOARD_WIDTH, y);
        }
    }
    
    private void drawFood(Graphics2D g2d) {
        if (food != null) {
            g2d.setColor(FOOD_COLOR);
            int x = food.y * CELL_SIZE;
            int y = food.x * CELL_SIZE;
            g2d.fillOval(x + 2, y + 2, CELL_SIZE - 4, CELL_SIZE - 4);
            
            // 绘制食物标记
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
            FontMetrics fm = g2d.getFontMetrics();
            String text = "F";
            int textX = x + (CELL_SIZE - fm.stringWidth(text)) / 2;
            int textY = y + (CELL_SIZE + fm.getAscent()) / 2;
            g2d.drawString(text, textX, textY);
        }
    }
    
    private void drawSnake(Graphics2D g2d) {
        for (int i = 0; i < snake.size(); i++) {
            Point segment = snake.get(i);
            int x = segment.y * CELL_SIZE;
            int y = segment.x * CELL_SIZE;
            
            if (i == 0) {
                // 绘制蛇头
                g2d.setColor(new Color(0, 100, 0));
                g2d.fillRect(x + 1, y + 1, CELL_SIZE - 2, CELL_SIZE - 2);
                
                // 绘制眼睛
                g2d.setColor(Color.WHITE);
                g2d.fillOval(x + 5, y + 5, 4, 4);
                g2d.fillOval(x + CELL_SIZE - 9, y + 5, 4, 4);
                g2d.setColor(Color.BLACK);
                g2d.fillOval(x + 6, y + 6, 2, 2);
                g2d.fillOval(x + CELL_SIZE - 8, y + 6, 2, 2);
            } else {
                // 绘制蛇身
                g2d.setColor(SNAKE_COLOR);
                g2d.fillRect(x + 1, y + 1, CELL_SIZE - 2, CELL_SIZE - 2);
            }
        }
    }
    
    private void drawGameInfo(Graphics2D g2d) {
        // 绘制算法信息
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        
        int infoX = 10;
        int infoY = 20;
        g2d.drawString("蛇长度: " + snake.size(), infoX, infoY);
        g2d.drawString("当前方向: " + currentDirection.name, infoX, infoY + 20);
        
        if (autoPlay) {
            g2d.drawString("AI模式: 开启", infoX, infoY + 40);
        }
        
        // 绘制算法复杂度
        g2d.setColor(Color.YELLOW);
        g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        int complexityY = BOARD_HEIGHT - 40;
        g2d.drawString("时间复杂度: O(1) - 每步移动", infoX, complexityY);
        g2d.drawString("空间复杂度: O(n) - 蛇的长度", infoX, complexityY + 15);
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameRunning || autoPlay) return;
        
        Direction newDirection = null;
        
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                newDirection = Direction.UP;
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                newDirection = Direction.DOWN;
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                newDirection = Direction.LEFT;
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                newDirection = Direction.RIGHT;
                break;
            case KeyEvent.VK_SPACE:
                if (gameRunning) {
                    pauseGame();
                } else if (!gameOver) {
                    startGame();
                }
                return;
        }
        
        // 防止反向移动
        if (newDirection != null && !isOppositeDirection(newDirection)) {
            currentDirection = newDirection;
            addLog("方向改变: " + currentDirection.name);
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) {}
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NO9_SnakeGame_Animation().setVisible(true);
        });
    }
}