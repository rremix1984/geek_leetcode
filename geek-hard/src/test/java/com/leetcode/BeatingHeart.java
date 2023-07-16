package com.leetcode;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class BeatingHeart extends JPanel implements Runnable {

    private static final int PANEL_WIDTH = 500;
    private static final int PANEL_HEIGHT = 500;
    private static final int HEART_SIZE = 100;
    private static final int ANIMATION_DELAY = 10;

    private int x = PANEL_WIDTH / 2 - HEART_SIZE / 2;
    private int y = PANEL_HEIGHT / 2 - HEART_SIZE / 2;
    private int dx = 1;
    private int dy = 1;
    private double scale = 1.0;
    private double dScale = 0.01;

    public BeatingHeart() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Shape heart = createHeartShape(HEART_SIZE, HEART_SIZE);
        g2d.setColor(Color.PINK);
        g2d.translate(x, y);
        g2d.scale(scale, scale);
        g2d.fill(heart);
    }

    public void run() {
        while (true) {
            scale += dScale;
            if (scale >= 1.2 || scale <= 0.8) {
                dScale = -dScale;
            }

            repaint();

            try {
                Thread.sleep(ANIMATION_DELAY);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static Shape createHeartShape(double width, double height) {
        Path2D.Double heart = new Path2D.Double();
        double x0 = width / 2;
        double y0 = height / 2;
        double x1 = width / 5;
        double y1 = height / 5;
        double x2 = width / 2;
        double y2 = height / 10;
        double x3 = width * 4 / 5;
        double y3 = height / 5;
        double x4 = width * 4 / 5;
        double y4 = height * 3 / 5;
        double x5 = width / 2;
        double y5 = height * 4 / 5;
        double x6 = width / 5;
        double y6 = height * 3 / 5;

        heart.moveTo(x0, y0);
        heart.curveTo(x1, y1, x2, y2, x3, y3);
        heart.curveTo(x4, y4, x5, y5, x6, y6);
        heart.closePath();
        return heart;
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Beating Heart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        frame.setResizable(false);
        BeatingHeart beatingHeart = new BeatingHeart();
        frame.add(beatingHeart);
        frame.setVisible(true);
    }
}
