package com.leetcode.tools;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.management.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * JVM进程监控工具
 * 可以监控本地或远程的Java进程内存使用情况
 * 
 * @author AI Assistant
 * @version 2.0
 */
public class JVMProcessMonitor extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    // 连接相关
    private MBeanServerConnection mbsc;
    private boolean isRemoteConnection = false;
    private String currentTarget = "本地JVM";
    
    // MBean对象名
    private ObjectName memoryObjectName;
    private ObjectName runtimeObjectName;
    private ObjectName osObjectName;
    private ObjectName threadObjectName;
    private Set<ObjectName> gcObjectNames;
    private Set<ObjectName> memoryPoolObjectNames;
    
    // UI组件
    private JComboBox<String> processComboBox;
    private JLabel targetLabel;
    private JLabel connectionStatusLabel;
    private JLabel portLabel;
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
    
    // 新增：详细堆内存信息
    private JLabel edenUsedLabel;
    private JLabel edenMaxLabel;
    private JLabel survivor0UsedLabel;
    private JLabel survivor0MaxLabel;

    private JLabel oldGenUsedLabel;
    private JLabel oldGenMaxLabel;
    private JLabel gcAlgorithmLabel;
    private JLabel youngGcCountLabel;
    private JLabel youngGcTimeLabel;
    private JLabel oldGcCountLabel;
    private JLabel oldGcTimeLabel;
    
    // 新增：详细堆内存进度条
    private JProgressBar edenProgressBar;
    private JProgressBar survivor0ProgressBar;

    private JProgressBar oldGenProgressBar;
    
    // 进度条
    private JProgressBar heapProgressBar;
    private JProgressBar nonHeapProgressBar;
    private JProgressBar codeCacheProgressBar;
    private JProgressBar metaspaceProgressBar;
    private JProgressBar compressedClassProgressBar;
    
    // 初始化组件方法
    private void initializeComponents() {
        processComboBox = new JComboBox<>();
        targetLabel = new JLabel("未连接");
        connectionStatusLabel = new JLabel("未连接");
        portLabel = new JLabel("端口: 未连接");
        
        heapUsedLabel = new JLabel("0 MB");
        heapMaxLabel = new JLabel("0 MB");
        nonHeapUsedLabel = new JLabel("0 MB");
        nonHeapMaxLabel = new JLabel("0 MB");
        
        codeCacheUsedLabel = new JLabel("0 MB");
        codeCacheMaxLabel = new JLabel("0 MB");
        codeCachePercentLabel = new JLabel("0%");
        
        metaspaceUsedLabel = new JLabel("0 MB");
        metaspaceMaxLabel = new JLabel("0 MB");
        
        compressedClassUsedLabel = new JLabel("0 MB");
        compressedClassMaxLabel = new JLabel("0 MB");
        
        uptimeLabel = new JLabel("0秒");
        threadsLabel = new JLabel("0");
        gcCountLabel = new JLabel("0");
        gcTimeLabel = new JLabel("0 ms");
        cpuUsageLabel = new JLabel("0%");
        systemMemoryLabel = new JLabel("0 MB");
        
        // 新增：详细堆内存信息标签
        edenUsedLabel = new JLabel("0 MB");
        edenMaxLabel = new JLabel("0 MB");
        survivor0UsedLabel = new JLabel("0 MB");
        survivor0MaxLabel = new JLabel("0 MB");

        oldGenUsedLabel = new JLabel("0 MB");
        oldGenMaxLabel = new JLabel("0 MB");
        gcAlgorithmLabel = new JLabel("未知");
        youngGcCountLabel = new JLabel("0");
        youngGcTimeLabel = new JLabel("0 ms");
        oldGcCountLabel = new JLabel("0");
        oldGcTimeLabel = new JLabel("0 ms");
        
        heapProgressBar = new JProgressBar(0, 100);
        heapProgressBar.setStringPainted(true);
        heapProgressBar.setForeground(new Color(255, 140, 0));
        
        nonHeapProgressBar = new JProgressBar(0, 100);
        nonHeapProgressBar.setStringPainted(true);
        nonHeapProgressBar.setForeground(new Color(100, 149, 237));
        
        codeCacheProgressBar = new JProgressBar(0, 100);
        codeCacheProgressBar.setStringPainted(true);
        codeCacheProgressBar.setForeground(new Color(255, 140, 0));
        
        metaspaceProgressBar = new JProgressBar(0, 100);
        metaspaceProgressBar.setStringPainted(true);
        metaspaceProgressBar.setForeground(new Color(60, 179, 113));
        
        compressedClassProgressBar = new JProgressBar(0, 100);
        compressedClassProgressBar.setStringPainted(true);
        compressedClassProgressBar.setForeground(new Color(255, 99, 71));
        
        // 新增：详细堆内存进度条
        edenProgressBar = new JProgressBar(0, 100);
        edenProgressBar.setStringPainted(true);
        edenProgressBar.setForeground(new Color(144, 238, 144));
        
        survivor0ProgressBar = new JProgressBar(0, 100);
        survivor0ProgressBar.setStringPainted(true);
        survivor0ProgressBar.setForeground(new Color(255, 218, 185));
        

        
        oldGenProgressBar = new JProgressBar(0, 100);
        oldGenProgressBar.setStringPainted(true);
        oldGenProgressBar.setForeground(new Color(255, 160, 122));
    }
    
    // 定时器
    private Timer refreshTimer;
    private final DecimalFormat df = new DecimalFormat("#,##0.00");
    private final DecimalFormat percentFormat = new DecimalFormat("0.0%");
    
    public JVMProcessMonitor() {
        initializeComponents();
        initializeConnection();
        initializeUI();
        startRefreshTimer();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 800);
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
    
    private void initializeConnection() {
        try {
            // 默认连接到本地JVM
            mbsc = ManagementFactory.getPlatformMBeanServer();
            setupMBeanObjectNames();
            currentTarget = "本地JVM (监控工具自身)";
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "初始化连接失败: " + e.getMessage(), 
                "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void setupMBeanObjectNames() throws Exception {
        memoryObjectName = new ObjectName(ManagementFactory.MEMORY_MXBEAN_NAME);
        runtimeObjectName = new ObjectName(ManagementFactory.RUNTIME_MXBEAN_NAME);
        osObjectName = new ObjectName(ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME);
        threadObjectName = new ObjectName(ManagementFactory.THREAD_MXBEAN_NAME);
        
        // 获取GC和内存池对象名
        gcObjectNames = mbsc.queryNames(new ObjectName(ManagementFactory.GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE + ",*"), null);
        memoryPoolObjectNames = mbsc.queryNames(new ObjectName(ManagementFactory.MEMORY_POOL_MXBEAN_DOMAIN_TYPE + ",*"), null);
    }
    
    private void initializeUI() {
        setTitle("JVM进程内存监控工具 v2.0 - 支持多进程监控");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // 顶部端口号显示面板
        JPanel topPanel = createPortDisplayPanel();
        add(topPanel, BorderLayout.NORTH);
        
        // 创建主面板
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // 添加进程选择面板
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        mainPanel.add(createProcessSelectionPanel(), gbc);
        
        // 添加连接状态面板
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        mainPanel.add(createConnectionStatusPanel(), gbc);
        
        // 添加各个监控面板
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        mainPanel.add(createSystemInfoPanel(), gbc);
        
        // 详细堆内存信息（新增）
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        mainPanel.add(createDetailedHeapMemoryPanel(), gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1;
        mainPanel.add(createHeapMemoryPanel(), gbc);
        
        gbc.gridx = 1; gbc.gridy = 4;
        mainPanel.add(createNonHeapMemoryPanel(), gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        mainPanel.add(createCodeCachePanel(), gbc);
        
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 1;
        mainPanel.add(createMetaspacePanel(), gbc);
        
        gbc.gridx = 1; gbc.gridy = 6;
        mainPanel.add(createCompressedClassPanel(), gbc);
        
        // 详细GC信息（增强）
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        mainPanel.add(createDetailedGCPanel(), gbc);
        
        // 添加控制按钮
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        mainPanel.add(createControlPanel(), gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // 设置窗口属性
        pack();
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(900, 800));
    }
    
    private JPanel createProcessSelectionPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("目标进程选择"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("监控目标:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        processComboBox.addItem("本地JVM (当前进程)");
        processComboBox.addItem("手动输入JMX端口...");
        processComboBox.addActionListener(e -> handleProcessSelection());
        panel.add(processComboBox, gbc);
        
        gbc.gridx = 2; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JButton refreshProcessButton = new JButton("刷新进程列表");
        refreshProcessButton.addActionListener(e -> refreshProcessList());
        panel.add(refreshProcessButton, gbc);
        
        return panel;
    }
    
    private JPanel createConnectionStatusPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("连接状态"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("当前目标:"), gbc);
        gbc.gridx = 1;
        targetLabel.setText(currentTarget);
        targetLabel.setFont(targetLabel.getFont().deriveFont(Font.BOLD));
        panel.add(targetLabel, gbc);
        
        gbc.gridx = 2; gbc.gridy = 0;
        panel.add(new JLabel("连接状态:"), gbc);
        gbc.gridx = 3;
        connectionStatusLabel.setText("✅ 已连接");
        connectionStatusLabel.setForeground(Color.GREEN);
        panel.add(connectionStatusLabel, gbc);
        
        return panel;
    }
    
    private void handleProcessSelection() {
        String selected = (String) processComboBox.getSelectedItem();
        if ("手动输入JMX端口...".equals(selected)) {
            String port = JOptionPane.showInputDialog(this, 
                "请输入JMX端口号 (例如: 9999):\n\n" +
                "提示: 需要在目标程序启动时添加JMX参数:\n" +
                "-Dcom.sun.management.jmxremote.port=9999\n" +
                "-Dcom.sun.management.jmxremote.authenticate=false\n" +
                "-Dcom.sun.management.jmxremote.ssl=false", 
                "连接到远程JVM", JOptionPane.QUESTION_MESSAGE);
            
            if (port != null && !port.trim().isEmpty()) {
                connectToRemoteJVM(port.trim());
            }
        } else {
            // 连接到本地JVM
            connectToLocalJVM();
        }
    }
    
    private void connectToRemoteJVM(String port) {
        try {
            String url = "service:jmx:rmi:///jndi/rmi://localhost:" + port + "/jmxrmi";
            JMXServiceURL serviceURL = new JMXServiceURL(url);
            JMXConnector connector = JMXConnectorFactory.connect(serviceURL, null);
            mbsc = connector.getMBeanServerConnection();
            setupMBeanObjectNames();
            
            currentTarget = "远程JVM (端口: " + port + ")";
            targetLabel.setText(currentTarget);
            connectionStatusLabel.setText("✅ 已连接");
            connectionStatusLabel.setForeground(Color.GREEN);
            
            // 更新端口号显示
            portLabel.setText("端口: " + port);
            portLabel.setForeground(new Color(0, 128, 0));
            
            isRemoteConnection = true;
            
            JOptionPane.showMessageDialog(this, "成功连接到远程JVM!", "连接成功", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "连接失败: " + e.getMessage() + "\n\n" +
                "请确保:\n" +
                "1. 目标程序已启动\n" +
                "2. 已添加JMX参数\n" +
                "3. 端口号正确", 
                "连接失败", JOptionPane.ERROR_MESSAGE);
            
            connectionStatusLabel.setText("❌ 连接失败");
            connectionStatusLabel.setForeground(Color.RED);
            portLabel.setText("端口: 连接失败");
            portLabel.setForeground(Color.RED);
        }
    }
    
    private void connectToLocalJVM() {
        try {
            mbsc = ManagementFactory.getPlatformMBeanServer();
            setupMBeanObjectNames();
            
            currentTarget = "本地JVM (监控工具自身)";
            targetLabel.setText(currentTarget);
            connectionStatusLabel.setText("✅ 已连接");
            connectionStatusLabel.setForeground(Color.GREEN);
            
            // 更新端口号显示
            portLabel.setText("端口: 本地JVM");
            portLabel.setForeground(new Color(25, 25, 112));
            
            isRemoteConnection = false;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "连接本地JVM失败: " + e.getMessage(), 
                "错误", JOptionPane.ERROR_MESSAGE);
            portLabel.setText("端口: 连接失败");
            portLabel.setForeground(Color.RED);
        }
    }
    
    private void refreshProcessList() {
        // 这里可以扩展为自动发现本地Java进程
        JOptionPane.showMessageDialog(this, 
            "当前版本支持:\n" +
            "1. 本地JVM监控 (当前进程)\n" +
            "2. 远程JMX连接\n\n" +
            "要监控算法动画程序，请:\n" +
            "1. 停止算法动画程序\n" +
            "2. 使用带JMX参数的启动脚本重新启动\n" +
            "3. 在此工具中连接到对应端口", 
            "进程监控说明", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private JPanel createSystemInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("系统信息"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("运行时间:"), gbc);
        gbc.gridx = 1;
        panel.add(uptimeLabel, gbc);
        
        gbc.gridx = 2; gbc.gridy = 0;
        panel.add(new JLabel("活跃线程:"), gbc);
        gbc.gridx = 3;
        panel.add(threadsLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("CPU使用率:"), gbc);
        gbc.gridx = 1;
        panel.add(cpuUsageLabel, gbc);
        
        gbc.gridx = 2; gbc.gridy = 1;
        panel.add(new JLabel("系统内存:"), gbc);
        gbc.gridx = 3;
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
        panel.add(heapUsedLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("最大值:"), gbc);
        gbc.gridx = 1;
        panel.add(heapMaxLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
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
        panel.add(nonHeapUsedLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("最大值:"), gbc);
        gbc.gridx = 1;
        panel.add(nonHeapMaxLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        nonHeapProgressBar.setStringPainted(true);
        panel.add(nonHeapProgressBar, gbc);
        
        return panel;
    }
    
    private JPanel createCodeCachePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("🎯 代码缓存 (Code Cache) - 重点监控"));
        panel.setBackground(new Color(255, 248, 220));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("已使用:"), gbc);
        gbc.gridx = 1;
        panel.add(codeCacheUsedLabel, gbc);
        
        gbc.gridx = 2; gbc.gridy = 0;
        panel.add(new JLabel("最大值:"), gbc);
        gbc.gridx = 3;
        panel.add(codeCacheMaxLabel, gbc);
        
        gbc.gridx = 4; gbc.gridy = 0;
        panel.add(new JLabel("使用率:"), gbc);
        gbc.gridx = 5;
        codeCachePercentLabel.setFont(codeCachePercentLabel.getFont().deriveFont(Font.BOLD));
        panel.add(codeCachePercentLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 6; gbc.fill = GridBagConstraints.HORIZONTAL;
        codeCacheProgressBar.setStringPainted(true);
        codeCacheProgressBar.setForeground(new Color(255, 140, 0));
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
        panel.add(metaspaceUsedLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("最大值:"), gbc);
        gbc.gridx = 1;
        panel.add(metaspaceMaxLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
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
        panel.add(compressedClassUsedLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("最大值:"), gbc);
        gbc.gridx = 1;
        panel.add(compressedClassMaxLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        compressedClassProgressBar.setStringPainted(true);
        panel.add(compressedClassProgressBar, gbc);
        
        return panel;
    }
    
    private JPanel createPortDisplayPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        portLabel = new JLabel("端口: 未连接");
        portLabel.setFont(portLabel.getFont().deriveFont(Font.BOLD, 14f));
        portLabel.setForeground(new Color(25, 25, 112));
        panel.add(portLabel);
        
        return panel;
    }
    
    private JPanel createDetailedHeapMemoryPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("🔍 详细堆内存分析"));
        panel.setBackground(new Color(248, 255, 248));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 5, 3, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Eden区
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Eden区:"), gbc);
        gbc.gridx = 1;
        panel.add(edenUsedLabel, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("/"), gbc);
        gbc.gridx = 3;
        panel.add(edenMaxLabel, gbc);
        gbc.gridx = 4; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(edenProgressBar, gbc);
        
        // Survivor区 (G1GC只有一个Survivor Space)
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Survivor区:"), gbc);
        gbc.gridx = 1;
        panel.add(survivor0UsedLabel, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("/"), gbc);
        gbc.gridx = 3;
        panel.add(survivor0MaxLabel, gbc);
        gbc.gridx = 4; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(survivor0ProgressBar, gbc);
        
        // 老年代
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("老年代:"), gbc);
        gbc.gridx = 1;
        panel.add(oldGenUsedLabel, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("/"), gbc);
        gbc.gridx = 3;
        panel.add(oldGenMaxLabel, gbc);
        gbc.gridx = 4; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(oldGenProgressBar, gbc);
        
        return panel;
    }
    
    private JPanel createDetailedGCPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("🗑️ 详细垃圾回收信息"));
        panel.setBackground(new Color(255, 250, 240));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 5, 3, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // GC算法
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("GC算法:"), gbc);
        gbc.gridx = 1;
        panel.add(gcAlgorithmLabel, gbc);
        
        // Young GC
        gbc.gridx = 2; gbc.gridy = 0;
        panel.add(new JLabel("Young GC:"), gbc);
        gbc.gridx = 3;
        panel.add(youngGcCountLabel, gbc);
        gbc.gridx = 4;
        panel.add(new JLabel("次"), gbc);
        gbc.gridx = 5;
        panel.add(youngGcTimeLabel, gbc);
        
        // Old GC
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Old GC:"), gbc);
        gbc.gridx = 1;
        panel.add(oldGcCountLabel, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("次"), gbc);
        gbc.gridx = 3;
        panel.add(oldGcTimeLabel, gbc);
        
        // 总计
        gbc.gridx = 4; gbc.gridy = 1;
        panel.add(new JLabel("总GC:"), gbc);
        gbc.gridx = 5;
        panel.add(gcCountLabel, gbc);
        gbc.gridx = 6;
        panel.add(new JLabel("次"), gbc);
        gbc.gridx = 7;
        panel.add(gcTimeLabel, gbc);
        
        return panel;
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        JButton refreshButton = new JButton("立即刷新");
        refreshButton.addActionListener(e -> updateMemoryInfo());
        
        JButton gcButton = new JButton("执行GC");
        gcButton.addActionListener(e -> {
            try {
                if (isRemoteConnection) {
                    // 远程GC
                    mbsc.invoke(memoryObjectName, "gc", null, null);
                    JOptionPane.showMessageDialog(this, "已请求远程JVM执行垃圾回收", "GC", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // 本地GC
                    System.gc();
                    JOptionPane.showMessageDialog(this, "已请求本地JVM执行垃圾回收", "GC", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "执行GC失败: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton helpButton = new JButton("使用说明");
        helpButton.addActionListener(e -> showHelpDialog());
        
        JButton exitButton = new JButton("退出");
        exitButton.addActionListener(e -> {
            if (refreshTimer != null) {
                refreshTimer.stop();
            }
            System.exit(0);
        });
        
        panel.add(refreshButton);
        panel.add(gcButton);
        panel.add(helpButton);
        panel.add(exitButton);
        
        return panel;
    }
    
    private void showHelpDialog() {
        String helpText = "JVM进程监控工具 v2.0 使用说明\n\n" +
            "🎯 监控算法动画程序的步骤:\n\n" +
            "1. 停止当前的算法动画程序\n\n" +
            "2. 创建带JMX参数的启动脚本:\n" +
            "   添加以下JVM参数:\n" +
            "   -Dcom.sun.management.jmxremote.port=9999\n" +
            "   -Dcom.sun.management.jmxremote.authenticate=false\n" +
            "   -Dcom.sun.management.jmxremote.ssl=false\n\n" +
            "3. 使用新脚本启动算法动画程序\n\n" +
            "4. 在此监控工具中选择'手动输入JMX端口'\n\n" +
            "5. 输入端口号 9999 进行连接\n\n" +
            "✅ 连接成功后即可实时监控算法动画程序!";
        
        JOptionPane.showMessageDialog(this, helpText, "使用说明", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void startRefreshTimer() {
        refreshTimer = new Timer(1000, e -> updateMemoryInfo());
        refreshTimer.start();
        updateMemoryInfo();
    }
    
    private void updateMemoryInfo() {
        SwingUtilities.invokeLater(() -> {
            try {
                if (mbsc != null) {
                    updateSystemInfo();
                    updateHeapMemory();
                    updateNonHeapMemory();
                    updateMemoryPools();
                    updateDetailedHeapMemory();
                    updateDetailedGCInfo();
                    updateGCInfo();
                }
            } catch (Exception e) {
                connectionStatusLabel.setText("❌ 连接断开");
                connectionStatusLabel.setForeground(Color.RED);
                // 可以在这里尝试重连
            }
        });
    }
    
    private void updateSystemInfo() {
        try {
            // 运行时间
            Long uptime = (Long) mbsc.getAttribute(runtimeObjectName, "Uptime");
            long hours = uptime / (1000 * 60 * 60);
            long minutes = (uptime % (1000 * 60 * 60)) / (1000 * 60);
            long seconds = (uptime % (1000 * 60)) / 1000;
            uptimeLabel.setText(String.format("%d小时 %d分钟 %d秒", hours, minutes, seconds));
            
            // 线程数
            Integer threadCount = (Integer) mbsc.getAttribute(threadObjectName, "ThreadCount");
            threadsLabel.setText(String.valueOf(threadCount));
            
            // CPU和系统内存信息
            cpuUsageLabel.setText("远程监控");
            systemMemoryLabel.setText("远程监控");
            
        } catch (Exception e) {
            uptimeLabel.setText("获取失败");
            threadsLabel.setText("获取失败");
        }
    }
    
    private void updateHeapMemory() {
        try {
            Object heapUsage = mbsc.getAttribute(memoryObjectName, "HeapMemoryUsage");
            if (heapUsage instanceof javax.management.openmbean.CompositeData) {
                javax.management.openmbean.CompositeData cd = 
                    (javax.management.openmbean.CompositeData) heapUsage;
                
                Long used = (Long) cd.get("used");
                Long max = (Long) cd.get("max");
                
                heapUsedLabel.setText(formatBytes(used));
                heapMaxLabel.setText(formatBytes(max));
                
                int percentage = (int) ((used * 100) / max);
                heapProgressBar.setValue(percentage);
                heapProgressBar.setString(percentage + "%");
                
                if (percentage > 80) {
                    heapProgressBar.setForeground(Color.RED);
                } else if (percentage > 60) {
                    heapProgressBar.setForeground(Color.ORANGE);
                } else {
                    heapProgressBar.setForeground(Color.GREEN);
                }
            }
        } catch (Exception e) {
            heapUsedLabel.setText("获取失败");
            heapMaxLabel.setText("获取失败");
        }
    }
    
    private void updateNonHeapMemory() {
        try {
            Object nonHeapUsage = mbsc.getAttribute(memoryObjectName, "NonHeapMemoryUsage");
            if (nonHeapUsage instanceof javax.management.openmbean.CompositeData) {
                javax.management.openmbean.CompositeData cd = 
                    (javax.management.openmbean.CompositeData) nonHeapUsage;
                
                Long used = (Long) cd.get("used");
                Long max = (Long) cd.get("max");
                
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
        } catch (Exception e) {
            nonHeapUsedLabel.setText("获取失败");
            nonHeapMaxLabel.setText("获取失败");
        }
    }
    
    private void updateMemoryPools() {
        try {
            for (ObjectName poolName : memoryPoolObjectNames) {
                String name = (String) mbsc.getAttribute(poolName, "Name");
                Object usage = mbsc.getAttribute(poolName, "Usage");
                
                if (usage instanceof javax.management.openmbean.CompositeData) {
                    javax.management.openmbean.CompositeData cd = 
                        (javax.management.openmbean.CompositeData) usage;
                    
                    Long used = (Long) cd.get("used");
                    Long max = (Long) cd.get("max");
                    
                    if (name.contains("Code Cache")) {
                        codeCacheUsedLabel.setText(formatBytes(used));
                        if (max > 0) {
                            codeCacheMaxLabel.setText(formatBytes(max));
                            double percentage = (double) used / max;
                            int percentInt = (int) (percentage * 100);
                            
                            codeCacheProgressBar.setValue(percentInt);
                            codeCacheProgressBar.setString(percentInt + "%");
                            codeCachePercentLabel.setText(percentFormat.format(percentage));
                            
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
                        }
                    } else if (name.contains("Metaspace")) {
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
                    } else if (name.contains("Compressed Class Space")) {
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
        } catch (Exception e) {
            codeCacheUsedLabel.setText("获取失败");
            codeCacheMaxLabel.setText("获取失败");
        }
    }
    
    private void updateGCInfo() {
        try {
            long totalCollections = 0;
            long totalTime = 0;
            
            for (ObjectName gcName : gcObjectNames) {
                Long collections = (Long) mbsc.getAttribute(gcName, "CollectionCount");
                Long time = (Long) mbsc.getAttribute(gcName, "CollectionTime");
                
                if (collections != null) totalCollections += collections;
                if (time != null) totalTime += time;
            }
            
            gcCountLabel.setText(String.valueOf(totalCollections));
            gcTimeLabel.setText(totalTime + " ms");
            
        } catch (Exception e) {
            gcCountLabel.setText("获取失败");
            gcTimeLabel.setText("获取失败");
        }
    }
    
    private void updateDetailedHeapMemory() {
        try {
            for (ObjectName poolName : memoryPoolObjectNames) {
                String name = (String) mbsc.getAttribute(poolName, "Name");
                Object usage = mbsc.getAttribute(poolName, "Usage");
                
                if (usage instanceof javax.management.openmbean.CompositeData) {
                    javax.management.openmbean.CompositeData cd = 
                        (javax.management.openmbean.CompositeData) usage;
                    
                    Long used = (Long) cd.get("used");
                    Long max = (Long) cd.get("max");
                    
                    if (name.contains("Eden") || name.contains("eden")) {
                        edenUsedLabel.setText(formatBytes(used));
                        if (max > 0) {
                            edenMaxLabel.setText(formatBytes(max));
                            int percentage = (int) ((used * 100) / max);
                            edenProgressBar.setValue(percentage);
                            edenProgressBar.setString(percentage + "%");
                            
                            if (percentage > 80) {
                                edenProgressBar.setForeground(Color.RED);
                            } else if (percentage > 60) {
                                edenProgressBar.setForeground(Color.ORANGE);
                            } else {
                                edenProgressBar.setForeground(new Color(144, 238, 144));
                            }
                        }
                    } else if (name.contains("Survivor") || name.contains("survivor")) {
                        // G1GC只有一个Survivor Space
                        survivor0UsedLabel.setText(formatBytes(used));
                        if (max > 0) {
                            survivor0MaxLabel.setText(formatBytes(max));
                            int percentage = (int) ((used * 100) / max);
                            survivor0ProgressBar.setValue(percentage);
                            survivor0ProgressBar.setString(percentage + "%");
                            
                            // 设置颜色
                            if (percentage > 80) {
                                survivor0ProgressBar.setForeground(Color.RED);
                            } else if (percentage > 60) {
                                survivor0ProgressBar.setForeground(Color.ORANGE);
                            } else {
                                survivor0ProgressBar.setForeground(new Color(255, 218, 185)); // 浅橙色
                            }
                        } else {
                            survivor0MaxLabel.setText("动态分配");
                            survivor0ProgressBar.setValue(0);
                            survivor0ProgressBar.setString("动态");
                        }
                    } else if (name.contains("Old") || name.contains("Tenured") || 
                               name.contains("old") || name.contains("tenured")) {
                        oldGenUsedLabel.setText(formatBytes(used));
                        if (max > 0) {
                            oldGenMaxLabel.setText(formatBytes(max));
                            int percentage = (int) ((used * 100) / max);
                            oldGenProgressBar.setValue(percentage);
                            oldGenProgressBar.setString(percentage + "%");
                            
                            if (percentage > 80) {
                                oldGenProgressBar.setForeground(Color.RED);
                            } else if (percentage > 60) {
                                oldGenProgressBar.setForeground(Color.ORANGE);
                            } else {
                                oldGenProgressBar.setForeground(new Color(255, 160, 122));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            edenUsedLabel.setText("获取失败");
            survivor0UsedLabel.setText("获取失败");
            oldGenUsedLabel.setText("获取失败");
        }
    }
    
    private void updateDetailedGCInfo() {
        try {
            long youngGcCount = 0;
            long youngGcTime = 0;
            long oldGcCount = 0;
            long oldGcTime = 0;
            String gcAlgorithm = "未知";
            
            for (ObjectName gcName : gcObjectNames) {
                String name = (String) mbsc.getAttribute(gcName, "Name");
                Long collections = (Long) mbsc.getAttribute(gcName, "CollectionCount");
                Long time = (Long) mbsc.getAttribute(gcName, "CollectionTime");
                
                if (collections != null && time != null) {
                    if (name.contains("Young") || name.contains("Minor") || 
                        name.contains("Copy") || name.contains("PS Scavenge") ||
                        name.contains("G1 Young") || name.contains("ParNew")) {
                        youngGcCount += collections;
                        youngGcTime += time;
                    } else if (name.contains("Old") || name.contains("Major") || 
                               name.contains("MarkSweep") || name.contains("PS MarkSweep") ||
                               name.contains("G1 Old") || name.contains("ConcurrentMarkSweep")) {
                        oldGcCount += collections;
                        oldGcTime += time;
                    }
                    
                    // 识别GC算法
                    if (name.contains("G1")) {
                        gcAlgorithm = "G1GC";
                    } else if (name.contains("Parallel") || name.contains("PS")) {
                        gcAlgorithm = "Parallel GC";
                    } else if (name.contains("ConcurrentMarkSweep") || name.contains("CMS")) {
                        gcAlgorithm = "CMS";
                    } else if (name.contains("Serial")) {
                        gcAlgorithm = "Serial GC";
                    } else if (name.contains("ZGC")) {
                        gcAlgorithm = "ZGC";
                    } else if (name.contains("Shenandoah")) {
                        gcAlgorithm = "Shenandoah";
                    }
                }
            }
            
            gcAlgorithmLabel.setText(gcAlgorithm);
            youngGcCountLabel.setText(String.valueOf(youngGcCount));
            youngGcTimeLabel.setText(youngGcTime + " ms");
            oldGcCountLabel.setText(String.valueOf(oldGcCount));
            oldGcTimeLabel.setText(oldGcTime + " ms");
            
        } catch (Exception e) {
            gcAlgorithmLabel.setText("获取失败");
            youngGcCountLabel.setText("获取失败");
            oldGcCountLabel.setText("获取失败");
        }
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
            
            new JVMProcessMonitor().setVisible(true);
        });
    }
}