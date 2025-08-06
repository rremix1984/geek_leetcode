package com.animation.aqs;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class AQSDemoAnimation extends JFrame {

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

    private final ReentrantLock lock = new ReentrantLock();
    private final List<String> waitingQueue = new ArrayList<>();
    private String currentThread = "";

    private DrawingPanel drawingPanel;
    private JTextArea descriptionArea;

    public AQSDemoAnimation() {
        setTitle("AQS Demo Animation");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);

        descriptionArea = new JTextArea();
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setPreferredSize(new Dimension(WINDOW_WIDTH, 100));
        add(scrollPane, BorderLayout.SOUTH);

        startAnimation();
    }

    private void startAnimation() {
        new Thread(() -> {
            // A
            updateStatus("A", "尝试获取锁");
            lock.lock();
            try {
                updateStatus("A", "获取锁成功，正在处理业务");
                currentThread = "A";
                sleep(5000);
            } finally {
                updateStatus("A", "释放锁");
                currentThread = "";
                lock.unlock();
            }
        }, "A").start();

        sleep(1000);

        new Thread(() -> {
            // B
            updateStatus("B", "尝试获取锁");
            waitingQueue.add("B");
            lock.lock();
            try {
                waitingQueue.remove("B");
                updateStatus("B", "获取锁成功，正在处理业务");
                currentThread = "B";
                sleep(5000);
            } finally {
                updateStatus("B", "释放锁");
                currentThread = "";
                lock.unlock();
            }
        }, "B").start();

        sleep(1000);

        new Thread(() -> {
            // C
            updateStatus("C", "尝试获取锁");
            waitingQueue.add("C");
            lock.lock();
            try {
                waitingQueue.remove("C");
                updateStatus("C", "获取锁成功，正在处理业务");
                currentThread = "C";
                sleep(5000);
            } finally {
                updateStatus("C", "释放锁");
                currentThread = "";
                lock.unlock();
            }
        }, "C").start();
    }

    private void updateStatus(String threadName, String status) {
        SwingUtilities.invokeLater(() -> {
            descriptionArea.append(String.format("[%s]: %s\n", threadName, status));
            drawingPanel.repaint();
        });
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // 银行窗口
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(300, 100, 200, 100);
            g.setColor(Color.BLACK);
            g.drawRect(300, 100, 200, 100);
            g.drawString("银行窗口", 370, 155);

            // 等候区
            g.setColor(Color.ORANGE);
            g.fillRect(100, 300, 600, 100);
            g.setColor(Color.BLACK);
            g.drawRect(100, 300, 600, 100);
            g.drawString("等候区 (AQS 队列)", 350, 355);

            // 正在办理业务的线程
            if (!currentThread.isEmpty()) {
                g.setColor(Color.GREEN);
                g.fillOval(375, 125, 50, 50);
                g.setColor(Color.BLACK);
                g.drawString(currentThread, 395, 155);
            }

            // 等待队列中的线程
            for (int i = 0; i < waitingQueue.size(); i++) {
                String threadName = waitingQueue.get(i);
                g.setColor(Color.YELLOW);
                g.fillOval(150 + i * 100, 325, 50, 50);
                g.setColor(Color.BLACK);
                g.drawString(threadName, 170 + i * 100, 355);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AQSDemoAnimation animation = new AQSDemoAnimation();
            animation.setVisible(true);
        });
    }
}