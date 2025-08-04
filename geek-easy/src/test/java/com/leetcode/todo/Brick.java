package com.leetcode.todo;

import java.awt.*;

/**
 * 砖块类
 * 可以被玛丽撞击破坏的砖块
 */
public class Brick extends GameObject {
    public static final int BRICK_WIDTH = 40;
    public static final int BRICK_HEIGHT = 40;
    
    private boolean breakable; // 是否可破坏
    private boolean breaking = false; // 是否正在破碎
    private int breakingAnimation = 0; // 破碎动画帧
    
    public Brick(double x, double y, boolean breakable) {
        super(x, y, BRICK_WIDTH, BRICK_HEIGHT);
        this.breakable = breakable;
        this.solid = true;
    }
    
    /**
     * 普通砖块构造器（可破坏）
     */
    public Brick(double x, double y) {
        this(x, y, true);
    }
    
    @Override
    public void update() {
        if (breaking) {
            breakingAnimation++;
            if (breakingAnimation > 10) { // 破碎动画持续10帧
                active = false;
            }
        }
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        if (!active) return;
        
        if (breaking) {
            drawBreakingAnimation(g2d);
        } else {
            drawNormalBrick(g2d);
        }
        
        // 绘制调试边框
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect((int)x, (int)y, width, height);
    }
    
    /**
     * 绘制正常砖块
     */
    private void drawNormalBrick(Graphics2D g2d) {
        // 砖块主体颜色
        Color brickColor = breakable ? new Color(139, 69, 19) : new Color(105, 105, 105);
        g2d.setColor(brickColor);
        g2d.fillRect((int)x, (int)y, width, height);
        
        // 砖块纹理
        g2d.setColor(brickColor.darker());
        // 水平线
        g2d.drawLine((int)x, (int)y + height/2, (int)x + width, (int)y + height/2);
        // 垂直线（交错排列）
        g2d.drawLine((int)x + width/2, (int)y, (int)x + width/2, (int)y + height/2);
        g2d.drawLine((int)x + width/4, (int)y + height/2, (int)x + width/4, (int)y + height);
        g2d.drawLine((int)x + 3*width/4, (int)y + height/2, (int)x + 3*width/4, (int)y + height);
        
        // 高光效果
        g2d.setColor(brickColor.brighter());
        g2d.drawLine((int)x, (int)y, (int)x + width, (int)y); // 顶边
        g2d.drawLine((int)x, (int)y, (int)x, (int)y + height); // 左边
    }
    
    /**
     * 绘制破碎动画
     */
    private void drawBreakingAnimation(Graphics2D g2d) {
        Color brickColor = new Color(139, 69, 19);
        g2d.setColor(brickColor);
        
        // 破碎效果：绘制飞散的小块
        int offset = breakingAnimation * 2;
        int size = width / 4;
        
        // 四个小碎片
        g2d.fillRect((int)x - offset, (int)y - offset, size, size);
        g2d.fillRect((int)x + width + offset, (int)y - offset, size, size);
        g2d.fillRect((int)x - offset, (int)y + height + offset, size, size);
        g2d.fillRect((int)x + width + offset, (int)y + height + offset, size, size);
    }
    
    /**
     * 被撞击时调用
     */
    public void hit() {
        if (breakable && !breaking) {
            breaking = true;
            solid = false; // 破碎时不再是实体
        }
    }
    
    /**
     * 检查砖块是否可以被破坏
     */
    public boolean isBreakable() {
        return breakable;
    }
    
    /**
     * 检查砖块是否正在破碎
     */
    public boolean isBreaking() {
        return breaking;
    }
    
    @Override
    public void onCollision(GameObject other) {
        if (other instanceof Mario) {
            Mario mario = (Mario) other;
            // 如果玛丽从下方撞击砖块
            if (mario.getY() + mario.MARIO_HEIGHT <= this.y + 5 && mario.velocityY < 0) {
                hit();
            }
        }
    }
}
