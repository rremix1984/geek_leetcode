package com.leetcode.todo;

import java.awt.*;

/**
 * 玛丽角色类
 * 负责玛丽的移动、跳跃、绘制等逻辑
 */
public class Mario extends GameObject {
    // 玛丽的基本属性
    public double velocityX = 0; // 水平速度
    public double velocityY = 0; // 垂直速度
    
    // 玛丽的尺寸
    public static final int MARIO_WIDTH = 32;
    public static final int MARIO_HEIGHT = 48;
    
    // 移动相关常量
    private static final double MOVE_SPEED = 5.0; // 移动速度
    private static final double JUMP_STRENGTH = -12.0; // 跳跃力度（负值表示向上）
    private static final double GRAVITY = 0.5; // 重力加速度
    private static final double MAX_FALL_SPEED = 10.0; // 最大下落速度
    private static final double FRICTION = 0.8; // 摩擦力
    
    // 状态标志
    private boolean onGround = false; // 是否在地面上
    private boolean facingRight = true; // 是否面向右侧
    private boolean moving = false; // 是否在移动
    private boolean jumping = false; // 是否在跳跃
    
    // 动画相关
    private int animationFrame = 0;
    private int animationCounter = 0;
    private static final int ANIMATION_SPEED = 8; // 动画帧切换速度
    
    // 地面高度（从GamePanel获取）
    private static final int GROUND_Y = GamePanel.PANEL_HEIGHT - GamePanel.GROUND_HEIGHT;
    
    public Mario(double x, double y) {
        super(x, y, MARIO_WIDTH, MARIO_HEIGHT);
    }
    
    /**
     * 更新玛丽的状态（每帧调用）
     */
    public void update() {
        // 应用重力
        if (!onGround) {
            velocityY += GRAVITY;
            if (velocityY > MAX_FALL_SPEED) {
                velocityY = MAX_FALL_SPEED;
            }
        }
        
        // 更新位置
        x += velocityX;
        y += velocityY;
        
        // 应用摩擦力
        velocityX *= FRICTION;
        
        // 检查地面碰撞
        checkGroundCollision();
        
        // 检查边界
        checkBoundaries();
        
        // 更新动画
        updateAnimation();
    }
    
    /**
     * 检查地面碰撞
     */
    private void checkGroundCollision() {
        if (y + MARIO_HEIGHT >= GROUND_Y) {
            y = GROUND_Y - MARIO_HEIGHT;
            velocityY = 0;
            onGround = true;
            jumping = false;
        } else {
            onGround = false;
        }
    }
    
    /**
     * 检查边界碰撞
     */
    private void checkBoundaries() {
        // 左边界
        if (x < 0) {
            x = 0;
            velocityX = 0;
        }
        // 右边界
        else if (x + MARIO_WIDTH > GamePanel.PANEL_WIDTH) {
            x = GamePanel.PANEL_WIDTH - MARIO_WIDTH;
            velocityX = 0;
        }
    }
    
    /**
     * 更新动画帧
     */
    private void updateAnimation() {
        animationCounter++;
        if (animationCounter >= ANIMATION_SPEED) {
            animationCounter = 0;
            if (moving) {
                animationFrame = (animationFrame + 1) % 3; // 3帧走路动画
            } else {
                animationFrame = 0; // 静止帧
            }
        }
    }
    
    /**
     * 向左移动
     */
    public void moveLeft() {
        velocityX = -MOVE_SPEED;
        facingRight = false;
        moving = true;
    }
    
    /**
     * 向右移动
     */
    public void moveRight() {
        velocityX = MOVE_SPEED;
        facingRight = true;
        moving = true;
    }
    
    /**
     * 停止移动
     */
    public void stopMoving() {
        moving = false;
    }
    
    /**
     * 跳跃
     */
    public void jump() {
        if (onGround) {
            velocityY = JUMP_STRENGTH;
            onGround = false;
            jumping = true;
        }
    }
    
    /**
     * 绘制玛丽
     */
    public void draw(Graphics2D g2d) {
        // 保存原始变换
        Graphics2D g = (Graphics2D) g2d.create();
        
        // 如果面向左侧，翻转图像
        if (!facingRight) {
            g.translate(x + MARIO_WIDTH, y);
            g.scale(-1, 1);
        } else {
            g.translate(x, y);
        }
        
        drawMarioSprite(g);
        
        // 绘制调试信息（可选）
        drawDebugInfo(g2d);
        
        g.dispose();
    }
    
    /**
     * 绘制玛丽精灵图像（简化版）
     */
    private void drawMarioSprite(Graphics2D g) {
        // 帽子（红色）
        g.setColor(Color.RED);
        g.fillRect(8, 0, 16, 8);
        
        // 脸部（粉色）
        g.setColor(new Color(255, 220, 177));
        g.fillRect(6, 8, 20, 12);
        
        // 眼睛
        g.setColor(Color.BLACK);
        g.fillRect(10, 12, 2, 2);
        g.fillRect(18, 12, 2, 2);
        
        // 鼻子
        g.setColor(Color.BLACK);
        g.fillRect(14, 16, 2, 2);
        
        // 胡子
        g.setColor(new Color(139, 69, 19)); // 棕色
        g.fillRect(8, 18, 4, 2);
        g.fillRect(20, 18, 4, 2);
        
        // 身体（蓝色背带裤）
        g.setColor(Color.BLUE);
        g.fillRect(8, 20, 16, 20);
        
        // 背带
        g.setColor(Color.YELLOW);
        g.fillRect(10, 20, 2, 20);
        g.fillRect(20, 20, 2, 20);
        
        // 手臂（根据动画帧调整位置）
        g.setColor(new Color(255, 220, 177));
        if (jumping) {
            // 跳跃时手臂向上
            g.fillRect(2, 18, 6, 8);
            g.fillRect(24, 18, 6, 8);
        } else if (moving) {
            // 移动时手臂摆动
            int armOffset = (animationFrame % 2 == 0) ? 2 : -2;
            g.fillRect(2, 22 + armOffset, 6, 8);
            g.fillRect(24, 22 - armOffset, 6, 8);
        } else {
            // 静止时手臂自然下垂
            g.fillRect(2, 22, 6, 8);
            g.fillRect(24, 22, 6, 8);
        }
        
        // 腿部（根据动画帧调整）
        g.setColor(Color.BLUE);
        if (moving && onGround) {
            // 走路动画
            int legOffset = (animationFrame % 2 == 0) ? 2 : -2;
            g.fillRect(10, 40, 4, 8);
            g.fillRect(18, 40, 4, 8);
            
            // 脚（棕色鞋子）
            g.setColor(new Color(139, 69, 19));
            g.fillRect(8 + legOffset, 46, 6, 2);
            g.fillRect(18 - legOffset, 46, 6, 2);
        } else {
            // 静止或跳跃时的腿部
            g.fillRect(10, 40, 4, 8);
            g.fillRect(18, 40, 4, 8);
            
            // 脚
            g.setColor(new Color(139, 69, 19));
            g.fillRect(8, 46, 6, 2);
            g.fillRect(18, 46, 6, 2);
        }
    }
    
    /**
     * 绘制调试信息
     */
    private void drawDebugInfo(Graphics2D g2d) {
        // 绘制碰撞盒
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect((int)x, (int)y, MARIO_WIDTH, MARIO_HEIGHT);
        
        // 绘制状态信息
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));
        g2d.drawString("Ground: " + onGround, (int)x, (int)y - 10);
        g2d.drawString("VelY: " + String.format("%.1f", velocityY), (int)x, (int)y - 20);
    }
    
    /**
     * 处理碰撞事件
     */
    @Override
    public void onCollision(GameObject other) {
        if (other instanceof Brick) {
            Brick brick = (Brick) other;
            // 简单的碰撞处理 - 阻止玛丽穿过砖块
            if (velocityY > 0) { // 从上方落下
                y = brick.getY() - MARIO_HEIGHT;
                velocityY = 0;
                onGround = true;
            }
        }
    }
    
    // Getter方法
    public boolean isOnGround() { return onGround; }
    public boolean isFacingRight() { return facingRight; }
    public boolean isMoving() { return moving; }
    public boolean isJumping() { return jumping; }
}
