package com.animation.sort;

import javax.swing.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.event.ActionEvent;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.event.ActionListener;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.util.ArrayList;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.util.List;
import com.animation.launcher.AlgorithmTreeLauncher;

/**
 * 插入排序算法 - 动画演示
 * 演示插入排序算法的过程
 */
public class InsertionSortAnimation extends JFrame {
    private List<Integer> array;
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JTextField inputField;
    private JButton addButton, startButton, resetButton;
    private JLabel resultLabel;
    private Timer timer;
    private int currentStep = 1;
    
    public InsertionSortAnimation() {
        setTitle("插入排序算法 - 动画演示");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        array = new ArrayList<>();
        initComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initComponents() {
        mainPanel = new JPanel();
        controlPanel = new JPanel();
        
        inputField = new JTextField(5);
        addButton = new JButton("添加元素");
        startButton = new JButton("开始排序");
        resetButton = new JButton("重置");
        resultLabel = new JLabel("状态: 等待输入...");
        
        // 设置字体
        Font font = new Font("微软雅黑", Font.PLAIN, 14);
        resultLabel.setFont(font);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 主面板
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new SortVisualizationPanel(), BorderLayout.CENTER);
        
        // 控制面板
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JLabel("元素值:"));
        controlPanel.add(inputField);
        // 返回首页按钮
        JButton homeButton = new JButton("返回首页");
        homeButton.addActionListener(e -> {
            dispose(); // 关闭当前窗口
            // 启动主界面
            SwingUtilities.invokeLater(() -> {
                 try {
                     dispose(); // 关闭当前动画窗口
                     com.animation.launcher.AlgorithmTreeLauncher.showMainWindow();
                 } catch (Exception ex) {
                     ex.printStackTrace();
                 }
             });
        });

        controlPanel.add(addButton);
        controlPanel.add(startButton);
        controlPanel.add(resetButton);
        controlPanel.add(homeButton);
        controlPanel.add(resultLabel);
        
        add(mainPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int value = Integer.parseInt(inputField.getText());
                    array.add(value);
                    updateDisplay();
                    resultLabel.setText("添加元素: " + value);
                    inputField.setText("");
                } catch (NumberFormatException ex) {
                    resultLabel.setText("错误: 请输入有效数字");
                }
            }
        });
        
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSorting();
            }
        });
        
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
    }
    
    private void startSorting() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentStep < array.size()) {
                    int key = array.get(currentStep);
                    int j = currentStep - 1;
                    
                    while (j >= 0 && array.get(j) > key) {
                        array.set(j + 1, array.get(j));
                        j--;
                    }
                    array.set(j + 1, key);
                    currentStep++;
                    updateDisplay();
                } else {
                    timer.stop();
                    resultLabel.setText("排序完成!");
                }
            }
        });
        timer.start();
    }
    
    private void reset() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        array.clear();
        currentStep = 1;
        resultLabel.setText("状态: 重置完毕");
        updateDisplay();
    }
    
    private void updateDisplay() {
        SwingUtilities.invokeLater(() -> this.repaint());
    }
    
    // 排序可视化面板
    private class SortVisualizationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            drawArray(g2d);
        }
        
        private void drawArray(Graphics2D g2d) {
            int startX = 50;
            int startY = 50;
            int width = 60;
            int heightUnit = 10;
            
            for (int i = 0; i < array.size(); i++) {
                int value = array.get(i);
                int height = value * heightUnit;
                Rectangle rect = new Rectangle(startX + i * (width + 10), startY + 200 - height, width, height);
                g2d.setColor(i < currentStep ? Color.GREEN : Color.GRAY);
                g2d.fill(rect);
                g2d.setColor(Color.BLACK);
                g2d.draw(rect);
                g2d.drawString(String.valueOf(value), startX + i * (width + 10) + 20, startY + 220);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new InsertionSortAnimation().setVisible(true);
            }
        });
    }
}
