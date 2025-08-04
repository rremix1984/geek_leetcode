package com.leetcode.todo;

import java.awt.*;

/**
 * 金币类
 * 可以被玛丽收集的金币
 */
public class Coin extends GameObject {
    public static final int COIN_SIZE = 20;
    
    private int animationFrame = 0;
    private int animationCounter = 0;
    private static final int ANIMATION_SPEED = 10; // 动画速度
    private boolean collected = false;
    private int collectAnimation = 0;
    
    public Coin(double x, double y) {
        super(x, y, COIN_SIZE, COIN_SIZE);
        this.solid = false; // 金币不是实体，可以穿过
    }
    
    @Override
    public void update() {
        if (collected) {
            // 收集动画
            collectAnimation++;
            if (collectAnimation > 20) { // 动画持续20帧
                active = false;
            }
        } else {
            // 旋转动画
            animationCounter++;
            if (animationCounter >= ANIMATION_SPEED) {
                animationCounter = 0;
                animationFrame = (animationFrame + 1) % 8; // 8帧旋转动画
            }
        }
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        if (!active) return;
        
        if (collected) {
            drawCollectAnimation(g2d);
        } else {
            drawCoin(g2d);
        }
        
        // 绘制调试边框
        g2d.setColor(Color.YELLOW);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect((int)x, (int)y, width, height);
    }
    
    /**
     * 绘制金币
     */
    private void drawCoin(Graphics2D g2d) {
        Graphics2D g = (Graphics2D) g2d.create();
        g.translate(x + width/2, y + height/2);
        
        // 根据动画帧调整宽度，制造旋转效果
        double scaleX = Math.cos(animationFrame * Math.PI / 4);
        if (scaleX < 0.1) scaleX = 0.1; // 避免完全消失
        
        g.scale(scaleX, 1.0);
        
        // 金币外圈（金色）
        g.setColor(new Color(255, 215, 0)); // 金色
        g.fillOval(-COIN_SIZE/2, -COIN_SIZE/2, COIN_SIZE, COIN_SIZE);
        
        // 内圈（亮金色）
        g.setColor(new Color(255, 255, 0)); // 亮金色
        g.fillOval(-COIN_SIZE/2 + 3, -COIN_SIZE/2 + 3, COIN_SIZE - 6, COIN_SIZE - 6);
        
        // 中心标记
        g.setColor(new Color(255, 215, 0));
        g.fillOval(-COIN_SIZE/4, -COIN_SIZE/4, COIN_SIZE/2, COIN_SIZE/2);
        
        // 边框
        g.setColor(new Color(184, 134, 11)); // 深金色
        g.setStroke(new BasicStroke(2));
        g.drawOval(-COIN_SIZE/2, -COIN_SIZE/2, COIN_SIZE, COIN_SIZE);
        
        g.dispose();
    }
    
    /**
     * 绘制收集动画
     */
    private void drawCollectAnimation(Graphics2D g2d) {
        Graphics2D g = (Graphics2D) g2d.create();
        g.translate(x + width/2, y + height/2 - collectAnimation * 2); // 向上飞
        
        // 逐渐变透明和变小
        float alpha = 1.0f - (collectAnimation / 20.0f);
        float scale = 1.0f + (collectAnimation / 10.0f);
        
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.scale(scale, scale);
        
        // 绘制发光的金币
        g.setColor(new Color(255, 255, 0)); // 亮黄色
        g.fillOval(-COIN_SIZE/2, -COIN_SIZE/2, COIN_SIZE, COIN_SIZE);
        
        // 绘制分数文字
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.drawString("+100", -15, -COIN_SIZE);
        
        g.dispose();
    }
    
    /**
     * 被收集
     */
    public void collect() {
        if (!collected) {
            collected = true;
            collectAnimation = 0;
        }
    }
    
    /**
     * 检查是否已被收集
     */
    public boolean isCollected() {
        return collected;
    }
    
    @Override
    public void onCollision(GameObject other) {
        if (other instanceof Mario && !collected) {
            collect();
            GameState.getInstance().collectCoin(100);
            // 这里可以添加音效
            System.out.println("金币被收集！+100分");
        }
    }
}
