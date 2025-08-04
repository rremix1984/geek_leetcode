package com.leetcode.todo;

import java.awt.*;

/**
 * 蘑菇怪敌人类
 * 会左右移动的基础敌人
 */
public class Goomba extends GameObject {
    public static final int GOOMBA_WIDTH = 32;
    public static final int GOOMBA_HEIGHT = 32;
    
    // 移动相关
    private double velocityX = -1.0; // 初始向左移动
    private double velocityY = 0;
    private static final double GRAVITY = 0.5;
    private static final double MAX_FALL_SPEED = 10.0;
    
    // 状态
    private boolean onGround = false;
    private boolean facingLeft = true;
    private boolean defeated = false;
    private int defeatAnimation = 0;
    
    // 动画
    private int animationFrame = 0;
    private int animationCounter = 0;
    private static final int ANIMATION_SPEED = 15;
    
    // 地面高度
    private static final int GROUND_Y = GamePanel.PANEL_HEIGHT - GamePanel.GROUND_HEIGHT;
    
    public Goomba(double x, double y) {
        super(x, y, GOOMBA_WIDTH, GOOMBA_HEIGHT);
        this.solid = true; // 蘑菇怪是实体
    }
    
    @Override
    public void update() {
        if (defeated) {
            // 被击败的动画
            defeatAnimation++;
            if (defeatAnimation > 30) { // 30帧后消失
                active = false;
            }
            return;
        }
        
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
        
        // 检查地面碰撞
        checkGroundCollision();
        
        // 检查边界碰撞，遇到边界就转向
        checkBoundaries();
        
        // 更新动画
        updateAnimation();
    }
    
    /**
     * 检查地面碰撞
     */
    private void checkGroundCollision() {
        if (y + GOOMBA_HEIGHT >= GROUND_Y) {
            y = GROUND_Y - GOOMBA_HEIGHT;
            velocityY = 0;
            onGround = true;
        } else {
            onGround = false;
        }
    }
    
    /**
     * 检查边界碰撞
     */
    private void checkBoundaries() {
        if (x <= 0) {
            x = 0;
            turnAround();
        } else if (x + GOOMBA_WIDTH >= GamePanel.PANEL_WIDTH) {
            x = GamePanel.PANEL_WIDTH - GOOMBA_WIDTH;
            turnAround();
        }
    }
    
    /**
     * 转向
     */
    private void turnAround() {
        velocityX = -velocityX;
        facingLeft = !facingLeft;
    }
    
    /**
     * 更新动画
     */
    private void updateAnimation() {
        animationCounter++;
        if (animationCounter >= ANIMATION_SPEED) {
            animationCounter = 0;
            animationFrame = (animationFrame + 1) % 2; // 2帧走路动画
        }
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        if (!active) return;
        
        if (defeated) {
            drawDefeatedGoomba(g2d);
        } else {
            drawGoomba(g2d);
        }
        
        // 绘制调试边框
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect((int)x, (int)y, width, height);
    }
    
    /**
     * 绘制正常的蘑菇怪
     */
    private void drawGoomba(Graphics2D g2d) {
        Graphics2D g = (Graphics2D) g2d.create();
        g.translate(x, y);
        
        // 如果面向右侧，翻转图像
        if (!facingLeft) {
            g.translate(GOOMBA_WIDTH, 0);
            g.scale(-1, 1);
        }
        
        // 蘑菇怪主体（棕色）
        g.setColor(new Color(139, 69, 19));
        g.fillOval(4, 8, 24, 20);
        
        // 蘑菇帽（深棕色）
        g.setColor(new Color(101, 67, 33));
        g.fillOval(2, 2, 28, 18);
        
        // 斑点
        g.setColor(new Color(160, 82, 45));
        g.fillOval(6, 6, 4, 4);
        g.fillOval(18, 8, 4, 4);
        g.fillOval(12, 10, 3, 3);
        
        // 眼睛
        g.setColor(Color.BLACK);
        g.fillOval(8, 12, 3, 3);
        g.fillOval(17, 12, 3, 3);
        
        // 嘴巴（愤怒表情）
        g.setColor(Color.BLACK);
        g.fillRect(12, 18, 6, 2);
        
        // 脚部动画
        g.setColor(new Color(139, 69, 19));
        if (animationFrame == 0) {
            // 第一帧
            g.fillOval(6, 26, 6, 4);
            g.fillOval(16, 26, 6, 4);
        } else {
            // 第二帧 - 脚稍微偏移
            g.fillOval(8, 26, 6, 4);
            g.fillOval(14, 26, 6, 4);
        }
        
        g.dispose();
    }
    
    /**
     * 绘制被击败的蘑菇怪
     */
    private void drawDefeatedGoomba(Graphics2D g2d) {
        Graphics2D g = (Graphics2D) g2d.create();
        g.translate(x, y + GOOMBA_HEIGHT - 8); // 压扁效果
        
        // 逐渐变透明
        float alpha = 1.0f - (defeatAnimation / 30.0f);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        
        // 压扁的蘑菇怪
        g.setColor(new Color(139, 69, 19));
        g.fillOval(0, 0, GOOMBA_WIDTH, 8);
        
        // 眼睛变成X
        g.setColor(Color.BLACK);
        g.drawLine(8, 2, 12, 6);
        g.drawLine(12, 2, 8, 6);
        g.drawLine(20, 2, 24, 6);
        g.drawLine(24, 2, 20, 6);
        
        g.dispose();
    }
    
    /**
     * 被击败
     */
    public void defeat() {
        if (!defeated) {
            defeated = true;
            defeatAnimation = 0;
            solid = false; // 被击败后不再是实体
            GameState.getInstance().defeatEnemy(200);
            System.out.println("蘑菇怪被击败！+200分");
        }
    }
    
    /**
     * 检查是否被击败
     */
    public boolean isDefeated() {
        return defeated;
    }
    
    @Override
    public void onCollision(GameObject other) {
        if (other instanceof Mario && !defeated) {
            Mario mario = (Mario) other;
            
            // 如果玛丽从上方跳到蘑菇怪身上
            if (mario.getY() + Mario.MARIO_HEIGHT <= this.y + 5 && mario.velocityY > 0) {
                defeat();
                // 让玛丽反弹一下
                mario.velocityY = -8.0;
            } else {
                // 侧面碰撞 - 玛丽受伤（这里简单地让玛丽弹开）
                if (mario.getX() < this.x) {
                    mario.velocityX = -5.0;
                } else {
                    mario.velocityX = 5.0;
                }
                System.out.println("玛丽被蘑菇怪撞到了！");
            }
        } else if (other instanceof Brick && !defeated) {
            // 撞到砖块就转向
            turnAround();
        }
    }
}
