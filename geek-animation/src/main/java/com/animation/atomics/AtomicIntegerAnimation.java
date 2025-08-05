package com.animation.atomics;

import com.animation.Animation;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * AtomicInteger 原子整数动画演示
 * 演示多线程环境下原子操作的安全性
 */
public class AtomicIntegerAnimation extends JFrame implements Animation {
    private static final int THREAD_COUNT = 5;
    private static final int OPERATIONS_PER_THREAD = 100;
    
    private AtomicInteger atomicCounter;
    private int normalCounter;
    private JLabel atomicLabel;
    private JLabel normalLabel;
    private JProgressBar[] threadProgressBars;
    private JButton startButton;
    private JButton resetButton;
    private JTextArea logArea;
    private boolean isRunning = false;
    
    public AtomicIntegerAnimation() {
        initComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initComponents() {
        setTitle("AtomicInteger 原子操作动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        atomicCounter = new AtomicInteger(0);
        normalCounter = 0;
        
        atomicLabel = new JLabel("原子计数器: 0", SwingConstants.CENTER);
        atomicLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        atomicLabel.setForeground(Color.BLUE);
        
        normalLabel = new JLabel("普通计数器: 0", SwingConstants.CENTER);
        normalLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        normalLabel.setForeground(Color.RED);
        
        threadProgressBars = new JProgressBar[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            threadProgressBars[i] = new JProgressBar(0, OPERATIONS_PER_THREAD);
            threadProgressBars[i].setStringPainted(true);
            threadProgressBars[i].setString("线程 " + (i + 1));
        }
        
        startButton = new JButton("开始演示");
        resetButton = new JButton("重置");
        
        logArea = new JTextArea(10, 50);
        logArea.setEditable(false);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 12));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 顶部面板 - 计数器显示
        JPanel topPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        topPanel.setBorder(BorderFactory.createTitledBorder("计数器状态"));
        topPanel.add(atomicLabel);
        topPanel.add(normalLabel);
        
        // 中间面板 - 线程进度条
        JPanel middlePanel = new JPanel(new GridLayout(THREAD_COUNT, 1, 5, 5));
        middlePanel.setBorder(BorderFactory.createTitledBorder("线程执行进度"));
        for (JProgressBar bar : threadProgressBars) {
            middlePanel.add(bar);
        }
        
        // 底部面板 - 控制按钮
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(startButton);
        bottomPanel.add(resetButton);
        
        // 日志面板
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(BorderFactory.createTitledBorder("执行日志"));
        logPanel.add(new JScrollPane(logArea), BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        add(logPanel, BorderLayout.EAST);
    }
    
    private void setupEventHandlers() {
        startButton.addActionListener(e -> {
            if (!isRunning) {
                startDemo();
            }
        });
        
        resetButton.addActionListener(e -> {
            if (!isRunning) {
                resetDemo();
            }
        });
    }
    
    private void startDemo() {
        isRunning = true;
        startButton.setEnabled(false);
        resetButton.setEnabled(false);
        
        logArea.append("开始演示...\n");
        logArea.append("启动 " + THREAD_COUNT + " 个线程，每个线程执行 " + OPERATIONS_PER_THREAD + " 次操作\n\n");
        
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        List<Thread> threads = new ArrayList<>();
        
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadId = i;
            Thread thread = new Thread(() -> {
                try {
                    for (int j = 0; j < OPERATIONS_PER_THREAD; j++) {
                        // 原子操作
                        atomicCounter.incrementAndGet();
                        
                        // 非原子操作（存在线程安全问题）
                        normalCounter++;
                        
                        // 更新进度条
                        final int currentProgress = j + 1;
                        SwingUtilities.invokeLater(() -> {
                            threadProgressBars[threadId].setValue(currentProgress);
                            atomicLabel.setText("原子计数器: " + atomicCounter.get());
                            normalLabel.setText("普通计数器: " + normalCounter);
                        });
                        
                        // 模拟一些处理时间
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                } finally {
                    latch.countDown();
                }
            }, "Worker-" + (i + 1));
            
            threads.add(thread);
            thread.start();
        }
        
        // 等待所有线程完成
        new Thread(() -> {
            try {
                latch.await();
                SwingUtilities.invokeLater(() -> {
                    int expectedValue = THREAD_COUNT * OPERATIONS_PER_THREAD;
                    int atomicValue = atomicCounter.get();
                    int normalValue = normalCounter;
                    
                    logArea.append("演示完成！\n");
                    logArea.append("期望值: " + expectedValue + "\n");
                    logArea.append("原子计数器结果: " + atomicValue + " (" + (atomicValue == expectedValue ? "正确" : "错误") + ")\n");
                    logArea.append("普通计数器结果: " + normalValue + " (" + (normalValue == expectedValue ? "正确" : "错误") + ")\n");
                    logArea.append("\n原子操作保证了线程安全，而普通操作可能出现数据竞争问题。\n\n");
                    
                    isRunning = false;
                    startButton.setEnabled(true);
                    resetButton.setEnabled(true);
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
    
    private void resetDemo() {
        atomicCounter.set(0);
        normalCounter = 0;
        
        atomicLabel.setText("原子计数器: 0");
        normalLabel.setText("普通计数器: 0");
        
        for (JProgressBar bar : threadProgressBars) {
            bar.setValue(0);
        }
        
        logArea.setText("");
        logArea.append("演示已重置\n\n");
    }
    
    @Override
    public void start() {
        setVisible(true);
    }
    
    public void startAnimation() {
        setVisible(true);
    }
    
    public void stopAnimation() {
        dispose();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AtomicIntegerAnimation().startAnimation();
        });
    }
}