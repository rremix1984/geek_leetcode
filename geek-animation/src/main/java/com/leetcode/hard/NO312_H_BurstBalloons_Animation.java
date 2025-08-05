package com.leetcode.hard;

import com.animation.Animation;


import javax.swing.*;
import java.awt.*;

public class NO312_H_BurstBalloons_Animation extends JPanel implements Animation {

    private int[] nums;
    private int maxCoins = 0;

    public NO312_H_BurstBalloons_Animation(int[] nums) {
        this.nums = nums;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 动画绘制逻辑将在这里实现
        drawBalloons(g);
    }

    private void drawBalloons(Graphics g) {
        if (nums == null) return;
        for (int i = 0; i < nums.length; i++) {
            g.setColor(Color.ORANGE);
            g.fillOval(50 + i * 60, 100, 50, 50);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(nums[i]), 70 + i * 60, 130);
        }
    }

    public void start() {
        // 动画执行逻辑将在这里实现
        // 这里只是一个示例，实际需要实现算法逻辑
        int n = nums.length;
        int[] points = new int[n + 2];
        points[0] = 1;
        points[n + 1] = 1;
        System.arraycopy(nums, 0, points, 1, n);

        int[][] dp = new int[n + 2][n + 2];

        for (int i = n; i >= 0; i--) {
            for (int j = i + 1; j < n + 2; j++) {
                for (int k = i + 1; k < j; k++) {
                    dp[i][j] = Math.max(dp[i][j], dp[i][k] + dp[k][j] + points[i] * points[k] * points[j]);
                }
            }
        }
        maxCoins = dp[0][n + 1];
        repaint();
    }

    public static void main(String[] args) {
        int[] nums = {3, 1, 5, 8};
        JFrame frame = new JFrame("Burst Balloons Animation");
        NO312_H_BurstBalloons_Animation animation = new NO312_H_BurstBalloons_Animation(nums);
        frame.add(animation);
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        animation.start();
    }
}