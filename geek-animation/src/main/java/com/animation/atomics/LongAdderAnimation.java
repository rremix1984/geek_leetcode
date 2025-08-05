package com.animation.atomics;

import com.animation.Animation;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.ArrayList;
import java.util.List;

/**
 * LongAdder vs AtomicLong 性能对比动画演示
 * 演示高并发场景下LongAdder的性能优势
 */
public class LongAdderAnimation extends JFrame implements Animation {
    private static final int THREAD_COUNT = 8;
    private static final int OPERATIONS_PER_THREAD = 10000;
    
    private LongAdder longAdder;
    private AtomicLong atomicLong;
    private LongAccumulator longAccumulator;
    private volatile long synchronizedCounter;
    private final Object syncLock = new Object();
    
    private JLabel longAdderLabel;
    private JLabel atomicLongLabel;
    private JLabel accumulatorLabel;
    private JLabel synchronizedLabel;
    private JProgressBar[] threadProgressBars;
    private JButton startButton;
    private JButton resetButton;
    private JTextArea logArea;
    private JLabel timeLabel;
    private boolean isRunning = false;
    
    // 性能统计
    private long longAdderTime;
    private long atomicLongTime;
    private long accumulatorTime;
    private long synchronizedTime;
    
    public LongAdderAnimation() {
        initComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initComponents() {
        setTitle("LongAdder vs AtomicLong 性能对比动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        
        longAdder = new LongAdder();
        atomicLong = new AtomicLong(0);
        longAccumulator = new LongAccumulator(Long::sum, 0);
        synchronizedCounter = 0;
        
        longAdderLabel = new JLabel("LongAdder: 0", SwingConstants.CENTER);
        longAdderLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        longAdderLabel.setForeground(Color.BLUE);
        longAdderLabel.setOpaque(true);
        longAdderLabel.setBackground(new Color(230, 240, 255));
        
        atomicLongLabel = new JLabel("AtomicLong: 0", SwingConstants.CENTER);
        atomicLongLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        atomicLongLabel.setForeground(Color.RED);
        atomicLongLabel.setOpaque(true);
        atomicLongLabel.setBackground(new Color(255, 240, 240));
        
        accumulatorLabel = new JLabel("LongAccumulator: 0", SwingConstants.CENTER);
        accumulatorLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        accumulatorLabel.setForeground(Color.GREEN);
        accumulatorLabel.setOpaque(true);
        accumulatorLabel.setBackground(new Color(240, 255, 240));
        
        synchronizedLabel = new JLabel("Synchronized: 0", SwingConstants.CENTER);
        synchronizedLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        synchronizedLabel.setForeground(Color.ORANGE);
        synchronizedLabel.setOpaque(true);
        synchronizedLabel.setBackground(new Color(255, 250, 230));
        
        timeLabel = new JLabel("执行时间统计将在测试完成后显示", SwingConstants.CENTER);
        timeLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        
        threadProgressBars = new JProgressBar[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            threadProgressBars[i] = new JProgressBar(0, OPERATIONS_PER_THREAD);
            threadProgressBars[i].setStringPainted(true);
            threadProgressBars[i].setString("线程 " + (i + 1));
        }
        
        startButton = new JButton("开始性能测试");
        resetButton = new JButton("重置");
        
        logArea = new JTextArea(15, 50);
        logArea.setEditable(false);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 11));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 顶部面板 - 计数器显示
        JPanel topPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        topPanel.setBorder(BorderFactory.createTitledBorder("计数器状态对比"));
        topPanel.add(longAdderLabel);
        topPanel.add(atomicLongLabel);
        topPanel.add(accumulatorLabel);
        topPanel.add(synchronizedLabel);
        topPanel.add(timeLabel);
        
        // 中间面板 - 线程进度条
        JPanel middlePanel = new JPanel(new GridLayout(THREAD_COUNT, 1, 2, 2));
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
        
        // 主要内容面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel, BorderLayout.CENTER);
        add(logPanel, BorderLayout.EAST);
    }
    
    private void setupEventHandlers() {
        startButton.addActionListener(e -> {
            if (!isRunning) {
                startPerformanceTest();
            }
        });
        
        resetButton.addActionListener(e -> {
            if (!isRunning) {
                resetDemo();
            }
        });
    }
    
    private void startPerformanceTest() {
        isRunning = true;
        startButton.setEnabled(false);
        resetButton.setEnabled(false);
        
        logArea.append("开始性能对比测试...\n");
        logArea.append("测试参数: " + THREAD_COUNT + " 个线程，每个线程执行 " + OPERATIONS_PER_THREAD + " 次操作\n\n");
        
        // 依次测试四种方式
        testLongAdder();
    }
    
    private void testLongAdder() {
        logArea.append("正在测试 LongAdder...\n");
        longAdder.reset();
        
        long startTime = System.nanoTime();
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    for (int j = 0; j < OPERATIONS_PER_THREAD; j++) {
                        longAdder.increment();
                        
                        if (j % 1000 == 0) {
                            final int currentProgress = j;
                            SwingUtilities.invokeLater(() -> {
                                threadProgressBars[threadId].setValue(currentProgress);
                                longAdderLabel.setText("LongAdder: " + longAdder.sum());
                            });
                        }
                    }
                    
                    SwingUtilities.invokeLater(() -> {
                        threadProgressBars[threadId].setValue(OPERATIONS_PER_THREAD);
                        longAdderLabel.setText("LongAdder: " + longAdder.sum());
                    });
                } finally {
                    latch.countDown();
                }
            }, "LongAdder-Worker-" + i).start();
        }
        
        new Thread(() -> {
            try {
                latch.await();
                longAdderTime = System.nanoTime() - startTime;
                
                SwingUtilities.invokeLater(() -> {
                    logArea.append("LongAdder 测试完成，耗时: " + (longAdderTime / 1_000_000) + " ms\n\n");
                    resetProgressBars();
                    testAtomicLong();
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
    
    private void testAtomicLong() {
        logArea.append("正在测试 AtomicLong...\n");
        atomicLong.set(0);
        
        long startTime = System.nanoTime();
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    for (int j = 0; j < OPERATIONS_PER_THREAD; j++) {
                        atomicLong.incrementAndGet();
                        
                        if (j % 1000 == 0) {
                            final int currentProgress = j;
                            SwingUtilities.invokeLater(() -> {
                                threadProgressBars[threadId].setValue(currentProgress);
                                atomicLongLabel.setText("AtomicLong: " + atomicLong.get());
                            });
                        }
                    }
                    
                    SwingUtilities.invokeLater(() -> {
                        threadProgressBars[threadId].setValue(OPERATIONS_PER_THREAD);
                        atomicLongLabel.setText("AtomicLong: " + atomicLong.get());
                    });
                } finally {
                    latch.countDown();
                }
            }, "AtomicLong-Worker-" + i).start();
        }
        
        new Thread(() -> {
            try {
                latch.await();
                atomicLongTime = System.nanoTime() - startTime;
                
                SwingUtilities.invokeLater(() -> {
                    logArea.append("AtomicLong 测试完成，耗时: " + (atomicLongTime / 1_000_000) + " ms\n\n");
                    resetProgressBars();
                    testLongAccumulator();
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
    
    private void testLongAccumulator() {
        logArea.append("正在测试 LongAccumulator...\n");
        longAccumulator = new LongAccumulator(Long::sum, 0);
        
        long startTime = System.nanoTime();
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    for (int j = 0; j < OPERATIONS_PER_THREAD; j++) {
                        longAccumulator.accumulate(1);
                        
                        if (j % 1000 == 0) {
                            final int currentProgress = j;
                            SwingUtilities.invokeLater(() -> {
                                threadProgressBars[threadId].setValue(currentProgress);
                                accumulatorLabel.setText("LongAccumulator: " + longAccumulator.get());
                            });
                        }
                    }
                    
                    SwingUtilities.invokeLater(() -> {
                        threadProgressBars[threadId].setValue(OPERATIONS_PER_THREAD);
                        accumulatorLabel.setText("LongAccumulator: " + longAccumulator.get());
                    });
                } finally {
                    latch.countDown();
                }
            }, "LongAccumulator-Worker-" + i).start();
        }
        
        new Thread(() -> {
            try {
                latch.await();
                accumulatorTime = System.nanoTime() - startTime;
                
                SwingUtilities.invokeLater(() -> {
                    logArea.append("LongAccumulator 测试完成，耗时: " + (accumulatorTime / 1_000_000) + " ms\n\n");
                    resetProgressBars();
                    testSynchronized();
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
    
    private void testSynchronized() {
        logArea.append("正在测试 Synchronized...\n");
        synchronizedCounter = 0;
        
        long startTime = System.nanoTime();
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    for (int j = 0; j < OPERATIONS_PER_THREAD; j++) {
                        synchronized (syncLock) {
                            synchronizedCounter++;
                        }
                        
                        if (j % 1000 == 0) {
                            final int currentProgress = j;
                            SwingUtilities.invokeLater(() -> {
                                threadProgressBars[threadId].setValue(currentProgress);
                                synchronized (syncLock) {
                                    synchronizedLabel.setText("Synchronized: " + synchronizedCounter);
                                }
                            });
                        }
                    }
                    
                    SwingUtilities.invokeLater(() -> {
                        threadProgressBars[threadId].setValue(OPERATIONS_PER_THREAD);
                        synchronized (syncLock) {
                            synchronizedLabel.setText("Synchronized: " + synchronizedCounter);
                        }
                    });
                } finally {
                    latch.countDown();
                }
            }, "Synchronized-Worker-" + i).start();
        }
        
        new Thread(() -> {
            try {
                latch.await();
                synchronizedTime = System.nanoTime() - startTime;
                
                SwingUtilities.invokeLater(() -> {
                    logArea.append("Synchronized 测试完成，耗时: " + (synchronizedTime / 1_000_000) + " ms\n\n");
                    showResults();
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
    
    private void showResults() {
        logArea.append("=== 性能测试结果 ===\n");
        logArea.append(String.format("LongAdder:      %6d ms\n", longAdderTime / 1_000_000));
        logArea.append(String.format("AtomicLong:     %6d ms\n", atomicLongTime / 1_000_000));
        logArea.append(String.format("LongAccumulator:%6d ms\n", accumulatorTime / 1_000_000));
        logArea.append(String.format("Synchronized:   %6d ms\n\n", synchronizedTime / 1_000_000));
        
        // 计算性能提升比例
        double adderVsAtomic = (double) atomicLongTime / longAdderTime;
        double adderVsSync = (double) synchronizedTime / longAdderTime;
        
        logArea.append("性能分析:\n");
        logArea.append(String.format("LongAdder 比 AtomicLong 快 %.2fx\n", adderVsAtomic));
        logArea.append(String.format("LongAdder 比 Synchronized 快 %.2fx\n", adderVsSync));
        logArea.append("\n原理说明:\n");
        logArea.append("- LongAdder 使用分段累加，减少CAS竞争\n");
        logArea.append("- AtomicLong 所有线程竞争同一个变量\n");
        logArea.append("- Synchronized 使用重量级锁，开销最大\n");
        
        // 更新时间标签
        timeLabel.setText(String.format("LongAdder: %dms | AtomicLong: %dms | LongAccumulator: %dms | Synchronized: %dms", 
            longAdderTime / 1_000_000, atomicLongTime / 1_000_000, 
            accumulatorTime / 1_000_000, synchronizedTime / 1_000_000));
        
        isRunning = false;
        startButton.setEnabled(true);
        resetButton.setEnabled(true);
        
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void resetProgressBars() {
        for (JProgressBar bar : threadProgressBars) {
            bar.setValue(0);
        }
    }
    
    private void resetDemo() {
        longAdder.reset();
        atomicLong.set(0);
        longAccumulator = new LongAccumulator(Long::sum, 0);
        synchronizedCounter = 0;
        
        longAdderLabel.setText("LongAdder: 0");
        atomicLongLabel.setText("AtomicLong: 0");
        accumulatorLabel.setText("LongAccumulator: 0");
        synchronizedLabel.setText("Synchronized: 0");
        timeLabel.setText("执行时间统计将在测试完成后显示");
        
        resetProgressBars();
        
        logArea.setText("");
        logArea.append("演示已重置\n\n");
        logArea.append("说明:\n");
        logArea.append("- LongAdder: 高并发累加器，性能优异\n");
        logArea.append("- AtomicLong: 传统原子长整型\n");
        logArea.append("- LongAccumulator: 可自定义累加函数\n");
        logArea.append("- Synchronized: 传统同步方式\n\n");
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
            new LongAdderAnimation().startAnimation();
        });
    }
}