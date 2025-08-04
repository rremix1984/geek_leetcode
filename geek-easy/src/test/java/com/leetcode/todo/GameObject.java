package com.leetcode.todo;

import java.awt.*;

/**
 * 游戏对象基础类
 * 所有游戏元素的父类
 */
public abstract class GameObject {
    protected double x, y; // 位置坐标
    protected int width, height; // 尺寸
    protected boolean active = true; // 是否激活
    protected boolean solid = false; // 是否为实体（可碰撞）
    
    public GameObject(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    /**
     * 更新游戏对象状态
     */
    public abstract void update();
    
    /**
     * 绘制游戏对象
     */
    public abstract void draw(Graphics2D g2d);
    
    /**
     * 获取碰撞盒
     */
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
    }
    
    /**
     * 检查与另一个对象是否碰撞
     */
    public boolean collidesWith(GameObject other) {
        return this.getBounds().intersects(other.getBounds());
    }
    
    /**
     * 检查与矩形区域是否碰撞
     */
    public boolean collidesWith(Rectangle rect) {
        return this.getBounds().intersects(rect);
    }
    
    /**
     * 处理碰撞事件
     */
    public void onCollision(GameObject other) {
        // 子类可以重写此方法来处理特定的碰撞逻辑
    }
    
    // Getter和Setter方法
    public double getX() { return x; }
    public double getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public boolean isActive() { return active; }
    public boolean isSolid() { return solid; }
    
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setActive(boolean active) { this.active = active; }
    public void setSolid(boolean solid) { this.solid = solid; }
}
