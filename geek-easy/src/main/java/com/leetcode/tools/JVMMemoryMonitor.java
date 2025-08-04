package com.leetcode.tools;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.management.*;
import java.text.DecimalFormat;
import java.util.List;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * JVM内存监控工具
 * 实时显示JVM内存分配情况，特别关注CodeCache使用情况
 * 
 * @author AI Assistant
 * @version 1.0
 */
public class JVMMemoryMonitor extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    // 内存管理相关
    private final MemoryMXBean memoryBean;
    private final List<MemoryPoolMXBean> memoryPools;
    private final RuntimeMXBean runtimeBean;
    private final OperatingSystemMXBean osBean;
    private final ThreadMXBean threadBean;
    private final GarbageCollectorMXBean[] gcBeans;
    
    // UI组件
    private JLabel heapUsedLabel;
    private JLabel heapMaxLabel;
    private JLabel nonHeapUsedLabel;
    private JLabel nonHeapMaxLabel;
    private JLabel codeCacheUsedLabel;
    private JLabel codeCacheMaxLabel;
    private JLabel codeCachePercentLabel;
    private JLabel metaspaceUsedLabel;
    private JLabel metaspaceMaxLabel;
    private JLabel compressedClassUsedLabel;
    private JLabel compressedClassMaxLabel;
    private JLabel uptimeLabel;
    private JLabel threadsLabel;
    private JLabel gcCountLabel;
    private JLabel gcTimeLabel;
    private JLabel cpuUsageLabel;
    private JLabel systemMemoryLabel;
    
    // 进度条
    private JProgressBar heapProgressBar;
    private JProgressBar nonHeapProgressBar;
    private JProgressBar codeCacheProgressBar;
    private JProgressBar metaspaceProgressBar;
    private JProgressBar compressedClassProgressBar;
    
    // 定时器
    private Timer refreshTimer;
    private final DecimalFormat df = new DecimalFormat("#,##0.00");
    private final DecimalFormat percentFormat = new DecimalFormat("0.0%");
    
    public JVMMemoryMonitor() {
        // 初始化MBean
        memoryBean = ManagementFactory.getMemoryMXBean();
        memoryPools = ManagementFactory.getMemoryPoolMXBeans();
        runtimeBean = ManagementFactory.getRuntimeMXBean();
        osBean = ManagementFactory.getOperatingSystemMXBean();
        threadBean = ManagementFactory.getThreadMXBean();
        gcBeans = ManagementFactory.getGarbageCollectorMXBeans().toArray(new GarbageCollectorMXBean[0]);
        
        initializeUI();
        startRefreshTimer();
        
        // 窗口关闭时停止定时器
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (refreshTimer != null) {
                    refreshTimer.stop();
                }
                System.exit(0);
            }
        });
    }
    
    private void initializeUI() {
        setTitle("JVM内存监控工具 - 实时监控");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // 创建主面板
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // 添加各个监控面板
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        mainPanel.add(createSystemInfoPanel(), gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        mainPanel.add(createHeapMemoryPanel(), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        mainPanel.add(createNonHeapMemoryPanel(), gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        mainPanel.add(createCodeCachePanel(), gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        mainPanel.add(createMetaspacePanel(), gbc);
        
        gbc.gridx = 1; gbc.gridy = 3;
        mainPanel.add(createCompressedClassPanel(), gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        mainPanel.add(createGCPanel(), gbc);
        
        // 添加控制按钮
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        mainPanel.add(createControlPanel(), gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // 设置窗口属性
        pack();
        setLocationRelativeTo(null);
        setResizable(true);
        
        // 设置最小尺寸
        setMinimumSize(new Dimension(800, 600));
    }
    
    private JPanel createSystemInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("系统信息"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // 运行时间
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("运行时间:"), gbc);
        gbc.gridx = 1;
        uptimeLabel = new JLabel();
        panel.add(uptimeLabel, gbc);
        
        // 线程数
        gbc.gridx = 2; gbc.gridy = 0;
        panel.add(new JLabel("活跃线程:"), gbc);
        gbc.gridx = 3;
        threadsLabel = new JLabel();
        panel.add(threadsLabel, gbc);
        
        // CPU使用率
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("CPU使用率:"), gbc);
        gbc.gridx = 1;
        cpuUsageLabel = new JLabel();
        panel.add(cpuUsageLabel, gbc);
        
        // 系统内存
        gbc.gridx = 2; gbc.gridy = 1;
        panel.add(new JLabel("系统内存:"), gbc);
        gbc.gridx = 3;
        systemMemoryLabel = new JLabel();
        panel.add(systemMemoryLabel, gbc);
        
        return panel;
    }
    
    private JPanel createHeapMemoryPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("堆内存 (Heap Memory)"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("已使用:"), gbc);
        gbc.gridx = 1;
        heapUsedLabel = new JLabel();
        panel.add(heapUsedLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("最大值:"), gbc);
        gbc.gridx = 1;
        heapMaxLabel = new JLabel();
        panel.add(heapMaxLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        heapProgressBar = new JProgressBar(0, 100);
        heapProgressBar.setStringPainted(true);
        panel.add(heapProgressBar, gbc);
        
        return panel;
    }
    
    private JPanel createNonHeapMemoryPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("非堆内存 (Non-Heap Memory)"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("已使用:"), gbc);
        gbc.gridx = 1;
        nonHeapUsedLabel = new JLabel();
        panel.add(nonHeapUsedLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("最大值:"), gbc);
        gbc.gridx = 1;
        nonHeapMaxLabel = new JLabel();
        panel.add(nonHeapMaxLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        nonHeapProgressBar = new JProgressBar(0, 100);
        nonHeapProgressBar.setStringPainted(true);
        panel.add(nonHeapProgressBar, gbc);
        
        return panel;
    }
    
    private JPanel createCodeCachePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("代码缓存 (Code Cache) - 重点监控"));
        panel.setBackground(new Color(255, 248, 220)); // 浅黄色背景突出显示
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("已使用:"), gbc);
        gbc.gridx = 1;
        codeCacheUsedLabel = new JLabel();
        panel.add(codeCacheUsedLabel, gbc);
        
        gbc.gridx = 2; gbc.gridy = 0;
        panel.add(new JLabel("最大值:"), gbc);
        gbc.gridx = 3;
        codeCacheMaxLabel = new JLabel();
        panel.add(codeCacheMaxLabel, gbc);
        
        gbc.gridx = 4; gbc.gridy = 0;
        panel.add(new JLabel("使用率:"), gbc);
        gbc.gridx = 5;
        codeCachePercentLabel = new JLabel();
        codeCachePercentLabel.setFont(codeCachePercentLabel.getFont().deriveFont(Font.BOLD));
        panel.add(codeCachePercentLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 6; gbc.fill = GridBagConstraints.HORIZONTAL;
        codeCacheProgressBar = new JProgressBar(0, 100);
        codeCacheProgressBar.setStringPainted(true);
        codeCacheProgressBar.setForeground(new Color(255, 140, 0)); // 橙色进度条
        panel.add(codeCacheProgressBar, gbc);
        
        return panel;
    }
    
    private JPanel createMetaspacePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("元空间 (Metaspace)"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("已使用:"), gbc);
        gbc.gridx = 1;
        metaspaceUsedLabel = new JLabel();
        panel.add(metaspaceUsedLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("最大值:"), gbc);
        gbc.gridx = 1;
        metaspaceMaxLabel = new JLabel();
        panel.add(metaspaceMaxLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        metaspaceProgressBar = new JProgressBar(0, 100);
        metaspaceProgressBar.setStringPainted(true);
        panel.add(metaspaceProgressBar, gbc);
        
        return panel;
    }
    
    private JPanel createCompressedClassPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("压缩类空间 (Compressed Class Space)"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("已使用:"), gbc);
        gbc.gridx = 1;
        compressedClassUsedLabel = new JLabel();
        panel.add(compressedClassUsedLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("最大值:"), gbc);
        gbc.gridx = 1;
        compressedClassMaxLabel = new JLabel();
        panel.add(compressedClassMaxLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        compressedClassProgressBar = new JProgressBar(0, 100);
        compressedClassProgressBar.setStringPainted(true);
        panel.add(compressedClassProgressBar, gbc);
        
        return panel;
    }
    
    private JPanel createGCPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("垃圾回收信息"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("GC次数:"), gbc);
        gbc.gridx = 1;
        gcCountLabel = new JLabel();
        panel.add(gcCountLabel, gbc);
        
        gbc.gridx = 2; gbc.gridy = 0;
        panel.add(new JLabel("GC时间:"), gbc);
        gbc.gridx = 3;
        gcTimeLabel = new JLabel();
        panel.add(gcTimeLabel, gbc);
        
        return panel;
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        JButton refreshButton = new JButton("立即刷新");
        refreshButton.addActionListener(e -> updateMemoryInfo());
        
        JButton gcButton = new JButton("执行GC");
        gcButton.addActionListener(e -> {
            System.gc();
            JOptionPane.showMessageDialog(this, "已请求垃圾回收", "GC", JOptionPane.INFORMATION_MESSAGE);
        });
        
        JButton exitButton = new JButton("退出");
        exitButton.addActionListener(e -> {
            if (refreshTimer != null) {
                refreshTimer.stop();
            }
            System.exit(0);
        });
        
        panel.add(refreshButton);
        panel.add(gcButton);
        panel.add(exitButton);
        
        return panel;
    }
    
    private void startRefreshTimer() {
        refreshTimer = new Timer(1000, e -> updateMemoryInfo()); // 每秒刷新
        refreshTimer.start();
        updateMemoryInfo(); // 立即更新一次
    }
    
    private void updateMemoryInfo() {
        SwingUtilities.invokeLater(() -> {
            try {
                updateSystemInfo();
                updateHeapMemory();
                updateNonHeapMemory();
                updateMemoryPools();
                updateGCInfo();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    private void updateSystemInfo() {
        // 运行时间
        long uptime = runtimeBean.getUptime();
        long hours = uptime / (1000 * 60 * 60);
        long minutes = (uptime % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (uptime % (1000 * 60)) / 1000;
        uptimeLabel.setText(String.format("%d小时 %d分钟 %d秒", hours, minutes, seconds));
        
        // 线程数
        threadsLabel.setText(String.valueOf(threadBean.getThreadCount()));
        
        // CPU使用率（如果支持）
        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
            com.sun.management.OperatingSystemMXBean sunOsBean = 
                (com.sun.management.OperatingSystemMXBean) osBean;
            double cpuUsage = sunOsBean.getProcessCpuLoad();
            if (cpuUsage >= 0) {
                cpuUsageLabel.setText(percentFormat.format(cpuUsage));
            } else {
                cpuUsageLabel.setText("不可用");
            }
            
            // 系统内存
            long totalMemory = sunOsBean.getTotalPhysicalMemorySize();
            long freeMemory = sunOsBean.getFreePhysicalMemorySize();
            systemMemoryLabel.setText(String.format("%s / %s", 
                formatBytes(totalMemory - freeMemory), formatBytes(totalMemory)));
        } else {
            cpuUsageLabel.setText("不支持");
            systemMemoryLabel.setText("不支持");
        }
    }
    
    private void updateHeapMemory() {
        MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
        long used = heapUsage.getUsed();
        long max = heapUsage.getMax();
        
        heapUsedLabel.setText(formatBytes(used));
        heapMaxLabel.setText(formatBytes(max));
        
        int percentage = (int) ((used * 100) / max);
        heapProgressBar.setValue(percentage);
        heapProgressBar.setString(percentage + "%");
        
        // 根据使用率设置颜色
        if (percentage > 80) {
            heapProgressBar.setForeground(Color.RED);
        } else if (percentage > 60) {
            heapProgressBar.setForeground(Color.ORANGE);
        } else {
            heapProgressBar.setForeground(Color.GREEN);
        }
    }
    
    private void updateNonHeapMemory() {
        MemoryUsage nonHeapUsage = memoryBean.getNonHeapMemoryUsage();
        long used = nonHeapUsage.getUsed();
        long max = nonHeapUsage.getMax();
        
        nonHeapUsedLabel.setText(formatBytes(used));
        if (max > 0) {
            nonHeapMaxLabel.setText(formatBytes(max));
            int percentage = (int) ((used * 100) / max);
            nonHeapProgressBar.setValue(percentage);
            nonHeapProgressBar.setString(percentage + "%");
        } else {
            nonHeapMaxLabel.setText("无限制");
            nonHeapProgressBar.setValue(0);
            nonHeapProgressBar.setString("N/A");
        }
    }
    
    private void updateMemoryPools() {
        for (MemoryPoolMXBean pool : memoryPools) {
            String poolName = pool.getName();
            MemoryUsage usage = pool.getUsage();
            
            if (usage == null) continue;
            
            long used = usage.getUsed();
            long max = usage.getMax();
            
            if (poolName.contains("Code Cache")) {
                codeCacheUsedLabel.setText(formatBytes(used));
                if (max > 0) {
                    codeCacheMaxLabel.setText(formatBytes(max));
                    double percentage = (double) used / max;
                    int percentInt = (int) (percentage * 100);
                    
                    codeCacheProgressBar.setValue(percentInt);
                    codeCacheProgressBar.setString(percentInt + "%");
                    codeCachePercentLabel.setText(percentFormat.format(percentage));
                    
                    // 根据使用率设置颜色和警告
                    if (percentage > 0.9) {
                        codeCacheProgressBar.setForeground(Color.RED);
                        codeCachePercentLabel.setForeground(Color.RED);
                        codeCachePercentLabel.setText(percentFormat.format(percentage) + " ⚠️");
                    } else if (percentage > 0.7) {
                        codeCacheProgressBar.setForeground(Color.ORANGE);
                        codeCachePercentLabel.setForeground(Color.ORANGE);
                    } else {
                        codeCacheProgressBar.setForeground(new Color(255, 140, 0));
                        codeCachePercentLabel.setForeground(Color.BLACK);
                    }
                } else {
                    codeCacheMaxLabel.setText("无限制");
                    codeCacheProgressBar.setValue(0);
                    codeCachePercentLabel.setText("N/A");
                }
            } else if (poolName.contains("Metaspace")) {
                metaspaceUsedLabel.setText(formatBytes(used));
                if (max > 0) {
                    metaspaceMaxLabel.setText(formatBytes(max));
                    int percentage = (int) ((used * 100) / max);
                    metaspaceProgressBar.setValue(percentage);
                    metaspaceProgressBar.setString(percentage + "%");
                } else {
                    metaspaceMaxLabel.setText("无限制");
                    metaspaceProgressBar.setValue(0);
                    metaspaceProgressBar.setString("N/A");
                }
            } else if (poolName.contains("Compressed Class Space")) {
                compressedClassUsedLabel.setText(formatBytes(used));
                if (max > 0) {
                    compressedClassMaxLabel.setText(formatBytes(max));
                    int percentage = (int) ((used * 100) / max);
                    compressedClassProgressBar.setValue(percentage);
                    compressedClassProgressBar.setString(percentage + "%");
                } else {
                    compressedClassMaxLabel.setText("无限制");
                    compressedClassProgressBar.setValue(0);
                    compressedClassProgressBar.setString("N/A");
                }
            }
        }
    }
    
    private void updateGCInfo() {
        long totalCollections = 0;
        long totalTime = 0;
        
        for (GarbageCollectorMXBean gcBean : gcBeans) {
            totalCollections += gcBean.getCollectionCount();
            totalTime += gcBean.getCollectionTime();
        }
        
        gcCountLabel.setText(String.valueOf(totalCollections));
        gcTimeLabel.setText(totalTime + " ms");
    }
    
    private String formatBytes(long bytes) {
        if (bytes < 0) return "N/A";
        
        String[] units = {"B", "KB", "MB", "GB", "TB"};
        int unitIndex = 0;
        double size = bytes;
        
        while (size >= 1024 && unitIndex < units.length - 1) {
            size /= 1024;
            unitIndex++;
        }
        
        return df.format(size) + " " + units[unitIndex];
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            new JVMMemoryMonitor().setVisible(true);
        });
    }
}