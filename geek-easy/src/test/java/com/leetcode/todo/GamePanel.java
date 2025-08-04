package com.leetcode.todo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 游戏面板类
 * 负责游戏的绘制和更新逻辑
 */
public class GamePanel extends JPanel implements ActionListener, KeyListener {
    // 游戏配置常量
    public static final int PANEL_WIDTH = 800;
    public static final int PANEL_HEIGHT = 600;
    public static final int GROUND_HEIGHT = 100; // 地面高度
    
    // 游戏循环相关
    private Timer gameTimer;
    private static final int DELAY = 16; // 约60FPS (1000ms/60 ≈ 16ms)
    
    // 游戏状态
    private boolean gameRunning = false;
    private long gameTime = 0;
    
    // 玛丽角色（暂时用简单图形代替）
    private Mario mario;
    
    // 键盘输入状态
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean spacePressed = false;
    private List<GameObject> gameObjects;
    
    public GamePanel() {
        initializePanel();
        initializeGameObjects();
    }
    
    /**
     * 初始化面板设置
     */
    private void initializePanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.CYAN); // 天空蓝色背景
        setFocusable(true); // 使面板能够接收键盘输入
        
        // 添加键盘监听器
        addKeyListener(this);
        
        // 初始化游戏定时器
        gameTimer = new Timer(DELAY, this);
    }
    
    /**
     * 初始化游戏对象
     */
    private void initializeGameObjects() {
        gameObjects = new ArrayList<>();
        
        // 创建玛丽角色，初始位置在地面上
        mario = new Mario(100, PANEL_HEIGHT - GROUND_HEIGHT - 50);
        gameObjects.add(mario);
        
        // 添加一些砖块
        addBricks();
        
        // 添加一些金币
        addCoins();
        
        // 添加敌人
        addEnemies();
    }
    
    /**
     * 添加砖块供测试
     */
    private void addBricks() {
        int yLevel = PANEL_HEIGHT - GROUND_HEIGHT - Brick.BRICK_HEIGHT;
        for (int i = 0; i < 200; i += Brick.BRICK_WIDTH) {
            gameObjects.add(new Brick(i + 300, yLevel));
        }
    }
    
    /**
     * 添加金币供测试
     */
    private void addCoins() {
        gameObjects.add(new Coin(400, PANEL_HEIGHT - GROUND_HEIGHT - 70));
        gameObjects.add(new Coin(450, PANEL_HEIGHT - GROUND_HEIGHT - 100));
        gameObjects.add(new Coin(500, PANEL_HEIGHT - GROUND_HEIGHT - 130));
    }
    
    /**
     * 添加敌人
     */
    private void addEnemies() {
        gameObjects.add(new Goomba(500, PANEL_HEIGHT - GROUND_HEIGHT - Goomba.GOOMBA_HEIGHT));
    }
    
    /**
     * 启动游戏循环
     */
    public void startGameLoop() {
        if (!gameRunning) {
            gameRunning = true;
            gameTimer.start();
            System.out.println("游戏开始！");
        }
    }
    
    /**
     * 停止游戏循环
     */
    public void stopGameLoop() {
        if (gameRunning) {
            gameRunning = false;
            gameTimer.stop();
            System.out.println("游戏暂停！");
        }
    }
    
    /**
     * 游戏更新逻辑（每帧调用）
     */
    private void updateGame() {
        if (!gameRunning) return;
        
        gameTime += DELAY;
        
        // 处理键盘输入
        handleInput();
        
        // 更新玛丽状态
        mario.update();
        
        // 更新其他游戏对象
        for (GameObject obj : gameObjects) {
            if (obj.isActive()) {
                obj.update();
                
                // 检查玛丽与其他对象的碰撞
                if (obj != mario && obj.collidesWith(mario)) {
                    obj.onCollision(mario);
                    if (obj.isSolid()) {
                        mario.onCollision(obj);
                    }
                }
            }
        }
    }
    
    /**
     * 处理键盘输入
     */
    private void handleInput() {
        // 左右移动控制
        if (leftPressed && rightPressed) {
            // 同时按下左右键，停止移动
            mario.stopMoving();
        } else if (leftPressed) {
            mario.moveLeft();
        } else if (rightPressed) {
            mario.moveRight();
        } else {
            mario.stopMoving();
        }
        
        // 跳跃控制
        if (spacePressed) {
            mario.jump();
        }
    }
    
    /**
     * 绘制游戏画面
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;
        // 开启抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        drawBackground(g2d);
        drawGround(g2d);
        // 绘制所有游戏对象
        for (GameObject obj : gameObjects) {
            obj.draw(g2d);
        }
        drawUI(g2d);
    }
    
    /**
     * 绘制背景
     */
    private void drawBackground(Graphics2D g2d) {
        // 绘制渐变天空背景
        GradientPaint skyGradient = new GradientPaint(
            0, 0, new Color(135, 206, 235), // 天空蓝
            0, PANEL_HEIGHT - GROUND_HEIGHT, new Color(173, 216, 230) // 浅蓝
        );
        g2d.setPaint(skyGradient);
        g2d.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT - GROUND_HEIGHT);
        
        // 绘制简单的云朵
        g2d.setColor(Color.WHITE);
        drawCloud(g2d, 150, 80);
        drawCloud(g2d, 450, 120);
        drawCloud(g2d, 650, 60);
    }
    
    /**
     * 绘制云朵
     */
    private void drawCloud(Graphics2D g2d, int x, int y) {
        g2d.fillOval(x, y, 60, 40);
        g2d.fillOval(x + 20, y - 10, 50, 35);
        g2d.fillOval(x + 40, y + 5, 45, 30);
    }
    
    /**
     * 绘制地面
     */
    private void drawGround(Graphics2D g2d) {
        // 绘制草地
        g2d.setColor(new Color(34, 139, 34)); // 森林绿
        g2d.fillRect(0, PANEL_HEIGHT - GROUND_HEIGHT, PANEL_WIDTH, GROUND_HEIGHT);
        
        // 绘制地面砖块纹理
        g2d.setColor(new Color(139, 69, 19)); // 棕色
        for (int i = 0; i < PANEL_WIDTH; i += 40) {
            g2d.fillRect(i, PANEL_HEIGHT - 30, 38, 30);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(i, PANEL_HEIGHT - 30, 38, 30);
            g2d.setColor(new Color(139, 69, 19));
        }
    }
    
    /**
     * 绘制玛丽
     */
    private void drawMario(Graphics2D g2d) {
        mario.draw(g2d);
    }
    
    /**
     * 绘制UI信息
     */
    private void drawUI(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        
        // 显示游戏时间
        g2d.drawString("时间: " + (gameTime / 1000) + "s", 10, 25);
        
        // 显示玛丽位置（调试信息）
        g2d.drawString("玛丽位置: (" + mario.getX() + ", " + mario.getY() + ")", 10, 50);
        
        // 显示FPS（调试信息）
        g2d.drawString("FPS: ~60", 10, 75);
    }
    
    /**
     * 定时器事件处理（游戏主循环）
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        updateGame();
        repaint(); // 重绘画面
    }
    
    // =============  KeyListener 接口实现  =============
    
    /**
     * 键盘按下事件
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                leftPressed = true;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                rightPressed = true;
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                spacePressed = true;
                break;
            case KeyEvent.VK_ESCAPE:
                // ESC键暂停/继续游戏
                if (gameRunning) {
                    stopGameLoop();
                } else {
                    startGameLoop();
                }
                break;
        }
    }
    
    /**
     * 键盘释放事件
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                leftPressed = false;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                rightPressed = false;
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                spacePressed = false;
                break;
        }
    }
    
    /**
     * 键盘字符输入事件（未使用）
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // 不需要处理
    }
}
