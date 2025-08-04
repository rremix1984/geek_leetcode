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
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.io.IOException;

/**
 * 简化版JVM进程监控工具
 * 监控本地Java进程的基本内存使用情况
 * 
 * @author AI Assistant
 * @version 2.1 (简化版)
 */
public class JVMProcessMonitor extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    // 连接相关
    private MBeanServerConnection mbsc;
    private String currentTarget = "本地JVM";
    
    // MBean对象名
    private ObjectName memoryObjectName;
    private ObjectName runtimeObjectName;
    private ObjectName threadObjectName;
    
    // UI组件
    private JLabel connectionStatusLabel;
    private JLabel heapUsedLabel;
    private JLabel heapMaxLabel;
    private JLabel nonHeapUsedLabel;
    private JLabel nonHeapMaxLabel;
    private JLabel uptimeLabel;
    private JLabel threadsLabel;
    private JLabel gcCountLabel;
    private JLabel gcTimeLabel;
    
    // 进度条
    private JProgressBar heapProgressBar;
    private JProgressBar nonHeapProgressBar;
    
    private Timer refreshTimer;
    private final DecimalFormat df = new DecimalFormat("#,##0.00");
    private final DecimalFormat percentFormat = new DecimalFormat("0.0%");
    
    public JVMProcessMonitor() {
        initializeComponents();
        initializeConnection();
        initializeUI();
        startRefreshTimer();
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setTitle("JVM进程监控 - 简化版");
    }
    
    private void initializeComponents() {
        connectionStatusLabel = new JLabel("未连接");
        heapUsedLabel = new JLabel("0 MB");
        heapMaxLabel = new JLabel("0 MB");
        nonHeapUsedLabel = new JLabel("0 MB");
        nonHeapMaxLabel = new JLabel("0 MB");
        uptimeLabel = new JLabel("0");
        threadsLabel = new JLabel("0");
        gcCountLabel = new JLabel("0");
        gcTimeLabel = new JLabel("0 ms");
        
        heapProgressBar = new JProgressBar(0, 100);
        heapProgressBar.setStringPainted(true);
        nonHeapProgressBar = new JProgressBar(0, 100);
        nonHeapProgressBar.setStringPainted(true);
    }
    
    private void initializeConnection() {
        try {
            // 连接到本地JVM
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            mbsc = server;
            setupMBeanObjectNames();
            connectionStatusLabel.setText("已连接到本地JVM");
            connectionStatusLabel.setForeground(Color.GREEN);
        } catch (Exception e) {
            connectionStatusLabel.setText("连接失败: " + e.getMessage());
            connectionStatusLabel.setForeground(Color.RED);
        }
    }
    
    private void setupMBeanObjectNames() throws Exception {
        memoryObjectName = new ObjectName(ManagementFactory.MEMORY_MXBEAN_NAME);
        runtimeObjectName = new ObjectName(ManagementFactory.RUNTIME_MXBEAN_NAME);
        threadObjectName = new ObjectName(ManagementFactory.THREAD_MXBEAN_NAME);
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout());
        
        // 顶部状态面板
        JPanel statusPanel = createStatusPanel();
        add(statusPanel, BorderLayout.NORTH);
        
        // 中央监控面板
        JPanel monitorPanel = createMonitorPanel();
        add(monitorPanel, BorderLayout.CENTER);
        
        // 底部控制面板
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(new TitledBorder("连接状态"));
        
        panel.add(new JLabel("状态: "));
        panel.add(connectionStatusLabel);
        
        return panel;
    }
    
    private JPanel createMonitorPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // 堆内存面板
        panel.add(createHeapMemoryPanel());
        
        // 非堆内存面板
        panel.add(createNonHeapMemoryPanel());
        
        // 系统信息面板
        panel.add(createSystemInfoPanel());
        
        return panel;
    }
    
    private JPanel createHeapMemoryPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("堆内存"));
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("已使用: "), gbc);
        gbc.gridx = 1;
        panel.add(heapUsedLabel, gbc);
        
        gbc.gridx = 2; gbc.insets = new Insets(0, 20, 0, 0);
        panel.add(new JLabel("最大值: "), gbc);
        gbc.gridx = 3;
        panel.add(heapMaxLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 4; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 0, 0);
        panel.add(heapProgressBar, gbc);
        
        return panel;
    }
    
    private JPanel createNonHeapMemoryPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("非堆内存"));
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("已使用: "), gbc);
        gbc.gridx = 1;
        panel.add(nonHeapUsedLabel, gbc);
        
        gbc.gridx = 2; gbc.insets = new Insets(0, 20, 0, 0);
        panel.add(new JLabel("最大值: "), gbc);
        gbc.gridx = 3;
        panel.add(nonHeapMaxLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 4; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 0, 0);
        panel.add(nonHeapProgressBar, gbc);
        
        return panel;
    }
    
    private JPanel createSystemInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 10, 5));
        panel.setBorder(new TitledBorder("系统信息"));
        
        panel.add(new JLabel("运行时间: "));
        panel.add(uptimeLabel);
        panel.add(new JLabel("线程数: "));
        panel.add(threadsLabel);
        
        panel.add(new JLabel("GC次数: "));
        panel.add(gcCountLabel);
        panel.add(new JLabel("GC时间: "));
        panel.add(gcTimeLabel);
        
        return panel;
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        JButton refreshButton = new JButton("立即刷新");
        refreshButton.addActionListener(e -> updateMemoryInfo());
        panel.add(refreshButton);
        
        JButton closeButton = new JButton("关闭");
        closeButton.addActionListener(e -> dispose());
        panel.add(closeButton);
        
        return panel;
    }
    
    private void startRefreshTimer() {
        refreshTimer = new Timer(2000, e -> updateMemoryInfo());
        refreshTimer.start();
    }
    
    private void updateMemoryInfo() {
        if (mbsc == null) return;
        
        try {
            updateHeapMemory();
            updateNonHeapMemory();
            updateSystemInfo();
        } catch (Exception e) {
            connectionStatusLabel.setText("更新失败: " + e.getMessage());
            connectionStatusLabel.setForeground(Color.RED);
        }
    }
    
    private void updateHeapMemory() {
        try {
            Object heapMemoryUsage = mbsc.getAttribute(memoryObjectName, "HeapMemoryUsage");
            if (heapMemoryUsage instanceof CompositeData) {
                CompositeData cd = (CompositeData) heapMemoryUsage;
                long used = (Long) cd.get("used");
                long max = (Long) cd.get("max");
                
                heapUsedLabel.setText(formatBytes(used));
                heapMaxLabel.setText(formatBytes(max));
                
                if (max > 0) {
                    int percentage = (int) ((used * 100) / max);
                    heapProgressBar.setValue(percentage);
                    heapProgressBar.setString(percentage + "%");
                }
            }
        } catch (Exception e) {
            heapUsedLabel.setText("获取失败");
        }
    }
    
    private void updateNonHeapMemory() {
        try {
            Object nonHeapMemoryUsage = mbsc.getAttribute(memoryObjectName, "NonHeapMemoryUsage");
            if (nonHeapMemoryUsage instanceof CompositeData) {
                CompositeData cd = (CompositeData) nonHeapMemoryUsage;
                long used = (Long) cd.get("used");
                long max = (Long) cd.get("max");
                
                nonHeapUsedLabel.setText(formatBytes(used));
                nonHeapMaxLabel.setText(max > 0 ? formatBytes(max) : "无限制");
                
                if (max > 0) {
                    int percentage = (int) ((used * 100) / max);
                    nonHeapProgressBar.setValue(percentage);
                    nonHeapProgressBar.setString(percentage + "%");
                } else {
                    nonHeapProgressBar.setValue(0);
                    nonHeapProgressBar.setString("无限制");
                }
            }
        } catch (Exception e) {
            nonHeapUsedLabel.setText("获取失败");
        }
    }
    
    private void updateSystemInfo() {
        try {
            // 运行时间
            long uptime = (Long) mbsc.getAttribute(runtimeObjectName, "Uptime");
            uptimeLabel.setText(formatUptime(uptime));
            
            // 线程数
            int threadCount = (Integer) mbsc.getAttribute(threadObjectName, "ThreadCount");
            threadsLabel.setText(String.valueOf(threadCount));
            
            // 简化的GC信息
            List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
            long totalGcCount = 0;
            long totalGcTime = 0;
            for (GarbageCollectorMXBean gcBean : gcBeans) {
                totalGcCount += gcBean.getCollectionCount();
                totalGcTime += gcBean.getCollectionTime();
            }
            gcCountLabel.setText(String.valueOf(totalGcCount));
            gcTimeLabel.setText(totalGcTime + " ms");
            
        } catch (Exception e) {
            uptimeLabel.setText("获取失败");
        }
    }
    
    private String formatBytes(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return df.format(bytes / 1024.0) + " KB";
        if (bytes < 1024 * 1024 * 1024) return df.format(bytes / (1024.0 * 1024.0)) + " MB";
        return df.format(bytes / (1024.0 * 1024.0 * 1024.0)) + " GB";
    }
    
    private String formatUptime(long uptime) {
        long seconds = uptime / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        
        if (days > 0) {
            return days + "天 " + (hours % 24) + "小时";
        } else if (hours > 0) {
            return hours + "小时 " + (minutes % 60) + "分钟";
        } else {
            return minutes + "分钟 " + (seconds % 60) + "秒";
        }
    }
    
    @Override
    public void dispose() {
        if (refreshTimer != null) {
            refreshTimer.stop();
        }
        super.dispose();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new JVMProcessMonitor().setVisible(true);
        });
    }
}