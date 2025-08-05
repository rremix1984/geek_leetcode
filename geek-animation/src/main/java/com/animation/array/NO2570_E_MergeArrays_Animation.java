package com.animation.array;

import javax.swing.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.event.ActionEvent;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.event.ActionListener;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.util.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.util.List;
import com.animation.launcher.AlgorithmTreeLauncher;

/**
 * NO.2570 合并两个二维数组 - 动画演示
 * 演示双指针合并排序数组的过程
 */
public class NO2570_E_MergeArrays_Animation extends JFrame {
    private java.util.List<int[]> nums1;
    private java.util.List<int[]> nums2;
    private java.util.List<int[]> result;
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JTextField idField, valueField;
    private JButton addToNums1Button, addToNums2Button, mergeButton, resetButton, stepButton;
    private JLabel resultLabel;
    private int currentStep = 0;
    private int pointer1 = 0, pointer2 = 0;
    private boolean isMerging = false;
    
    public NO2570_E_MergeArrays_Animation() {
        setTitle("NO.2570 合并两个二维数组 - 动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        
        nums1 = new ArrayList<>();
        nums2 = new ArrayList<>();
        result = new ArrayList<>();
        initComponents();
        setupLayout();
        setupEventHandlers();
        initializeDefaultData();
    }
    
    private void initComponents() {
        mainPanel = new JPanel();
        controlPanel = new JPanel();
        
        idField = new JTextField(5);
        valueField = new JTextField(5);
        addToNums1Button = new JButton("添加到数组1");
        addToNums2Button = new JButton("添加到数组2");
        mergeButton = new JButton("开始合并");
        resetButton = new JButton("重置");
        stepButton = new JButton("单步执行");
        resultLabel = new JLabel("结果: ");
        
        // 设置字体
        Font font = new Font("微软雅黑", Font.PLAIN, 14);
        resultLabel.setFont(font);
        
        stepButton.setEnabled(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 主面板
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new ArrayVisualizationPanel(), BorderLayout.CENTER);
        
        // 控制面板
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JLabel("ID:"));
        controlPanel.add(idField);
        controlPanel.add(new JLabel("值:"));
        controlPanel.add(valueField);
        controlPanel.add(addToNums1Button);
        controlPanel.add(addToNums2Button);
        controlPanel.add(mergeButton);
        controlPanel.add(stepButton);
        controlPanel.add(resetButton);
        controlPanel.add(resultLabel);
        
        add(mainPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        addToNums1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isMerging) {
                    addToArray(nums1, "数组1");
                }
            }
        });
        
        addToNums2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isMerging) {
                    addToArray(nums2, "数组2");
                }
            }
        });
        
        mergeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isMerging) {
                    startMerge();
                } else {
                    completeMerge();
                }
            }
        });
        
        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeNextStep();
            }
        });
        
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });

        // Add window listener to show main window on close
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                AlgorithmTreeLauncher.showMainWindow();
            }
        });
    }
    
    private void addToArray(java.util.List<int[]> array, String arrayName) {
        try {
            int id = Integer.parseInt(idField.getText());
            int value = Integer.parseInt(valueField.getText());
            
            // 检查ID是否已存在
            for (int[] pair : array) {
                if (pair[0] == id) {
                    resultLabel.setText("错误: ID " + id + " 在" + arrayName + "中已存在");
                    return;
                }
            }
            
            array.add(new int[]{id, value});
            // 保持数组按ID排序
            array.sort(Comparator.comparingInt(a -> a[0]));
            
            updateDisplay();
            resultLabel.setText("结果: 添加 [" + id + "," + value + "] 到" + arrayName);
            idField.setText("");
            valueField.setText("");
        } catch (NumberFormatException ex) {
            resultLabel.setText("错误: 请输入有效的数字");
        }
    }
    
    private void initializeDefaultData() {
        // 初始化默认数据
        nums1.add(new int[]{1, 2});
        nums1.add(new int[]{2, 3});
        nums1.add(new int[]{4, 5});
        
        nums2.add(new int[]{1, 4});
        nums2.add(new int[]{3, 2});
        nums2.add(new int[]{4, 1});
        
        updateDisplay();
    }
    
    private void startMerge() {
        isMerging = true;
        pointer1 = 0;
        pointer2 = 0;
        result.clear();
        currentStep = 0;
        
        mergeButton.setText("完成合并");
        stepButton.setEnabled(true);
        addToNums1Button.setEnabled(false);
        addToNums2Button.setEnabled(false);
        
        resultLabel.setText("开始合并: 使用双指针算法");
        updateDisplay();
    }
    
    private void executeNextStep() {
        if (pointer1 < nums1.size() && pointer2 < nums2.size()) {
            int[] pair1 = nums1.get(pointer1);
            int[] pair2 = nums2.get(pointer2);
            
            if (pair1[0] < pair2[0]) {
                result.add(new int[]{pair1[0], pair1[1]});
                resultLabel.setText("步骤" + (++currentStep) + ": 添加数组1中的 [" + pair1[0] + "," + pair1[1] + "]");
                pointer1++;
            } else if (pair1[0] > pair2[0]) {
                result.add(new int[]{pair2[0], pair2[1]});
                resultLabel.setText("步骤" + (++currentStep) + ": 添加数组2中的 [" + pair2[0] + "," + pair2[1] + "]");
                pointer2++;
            } else {
                // ID相同，合并值
                result.add(new int[]{pair1[0], pair1[1] + pair2[1]});
                resultLabel.setText("步骤" + (++currentStep) + ": 合并ID=" + pair1[0] + ", 值=" + pair1[1] + "+" + pair2[1] + "=" + (pair1[1] + pair2[1]));
                pointer1++;
                pointer2++;
            }
        } else if (pointer1 < nums1.size()) {
            int[] pair1 = nums1.get(pointer1);
            result.add(new int[]{pair1[0], pair1[1]});
            resultLabel.setText("步骤" + (++currentStep) + ": 添加数组1剩余的 [" + pair1[0] + "," + pair1[1] + "]");
            pointer1++;
        } else if (pointer2 < nums2.size()) {
            int[] pair2 = nums2.get(pointer2);
            result.add(new int[]{pair2[0], pair2[1]});
            resultLabel.setText("步骤" + (++currentStep) + ": 添加数组2剩余的 [" + pair2[0] + "," + pair2[1] + "]");
            pointer2++;
        } else {
            // 合并完成
            stepButton.setEnabled(false);
            mergeButton.setText("开始合并");
            resultLabel.setText("合并完成! 最终结果包含 " + result.size() + " 个元素");
            isMerging = false;
            addToNums1Button.setEnabled(true);
            addToNums2Button.setEnabled(true);
        }
        
        updateDisplay();
    }
    
    private void completeMerge() {
        while (pointer1 < nums1.size() || pointer2 < nums2.size()) {
            executeNextStep();
        }
    }
    
    private void reset() {
        nums1.clear();
        nums2.clear();
        result.clear();
        pointer1 = 0;
        pointer2 = 0;
        currentStep = 0;
        isMerging = false;
        
        mergeButton.setText("开始合并");
        stepButton.setEnabled(false);
        addToNums1Button.setEnabled(true);
        addToNums2Button.setEnabled(true);
        
        initializeDefaultData();
        resultLabel.setText("结果: 已重置");
    }
    
    private void updateDisplay() {
        repaint();
    }
    
    // 数组可视化面板
    private class ArrayVisualizationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            drawArray(g2d, nums1, "数组1 (nums1)", 50, 50, Color.BLUE, pointer1);
            drawArray(g2d, nums2, "数组2 (nums2)", 50, 150, Color.GREEN, pointer2);
            drawArray(g2d, result, "结果数组", 50, 300, Color.RED, -1);
            drawAlgorithmInfo(g2d);
        }
        
        private void drawArray(Graphics2D g2d, java.util.List<int[]> array, String title, int x, int y, Color color, int pointer) {
            g2d.setColor(Color.BLACK);
            g2d.drawString(title + ":", x, y - 10);
            
            int elementWidth = 80;
            int elementHeight = 40;
            
            for (int i = 0; i < array.size(); i++) {
                int[] pair = array.get(i);
                int elementX = x + i * (elementWidth + 10);
                
                Rectangle rect = new Rectangle(elementX, y, elementWidth, elementHeight);
                
                // 高亮当前指针位置
                if (isMerging && i == pointer) {
                    g2d.setColor(color.darker());
                    g2d.fill(rect);
                    g2d.setColor(Color.WHITE);
                    g2d.draw(rect);
                    
                    // 绘制指针箭头
                    g2d.setColor(Color.RED);
                    int arrowX = elementX + elementWidth / 2;
                    int arrowY = y - 15;
                    g2d.drawString("↓", arrowX - 5, arrowY);
                } else {
                    g2d.setColor(color.brighter());
                    g2d.fill(rect);
                    g2d.setColor(Color.BLACK);
                    g2d.draw(rect);
                }
                
                // 绘制ID和值
                g2d.setColor(Color.BLACK);
                String idStr = "ID:" + pair[0];
                String valueStr = "V:" + pair[1];
                FontMetrics fm = g2d.getFontMetrics();
                
                int idX = elementX + (elementWidth - fm.stringWidth(idStr)) / 2;
                int valueX = elementX + (elementWidth - fm.stringWidth(valueStr)) / 2;
                
                g2d.drawString(idStr, idX, y + 15);
                g2d.drawString(valueStr, valueX, y + 30);
            }
            
            if (array.isEmpty()) {
                g2d.setColor(Color.GRAY);
                g2d.drawString("空数组", x, y + 25);
            }
        }
        
        private void drawAlgorithmInfo(Graphics2D g2d) {
            int infoX = 50;
            int infoY = getHeight() - 250;
            
            g2d.setColor(Color.BLACK);
            g2d.drawString("双指针合并算法:", infoX, infoY);
            g2d.drawString("1. 使用两个指针分别指向两个数组的当前元素", infoX, infoY + 20);
            g2d.drawString("2. 比较两个指针指向元素的ID", infoX, infoY + 40);
            g2d.drawString("3. ID较小的元素加入结果，对应指针后移", infoX, infoY + 60);
            g2d.drawString("4. ID相同时，合并值后加入结果，两指针都后移", infoX, infoY + 80);
            g2d.drawString("5. 处理完一个数组后，将另一个数组剩余元素加入结果", infoX, infoY + 100);
            
            // 显示当前状态
            if (isMerging) {
                g2d.setColor(Color.BLUE);
                g2d.drawString("当前状态:", infoX + 500, infoY);
                g2d.drawString("指针1位置: " + pointer1 + "/" + nums1.size(), infoX + 500, infoY + 20);
                g2d.drawString("指针2位置: " + pointer2 + "/" + nums2.size(), infoX + 500, infoY + 40);
                g2d.drawString("已合并元素: " + result.size(), infoX + 500, infoY + 60);
                
                // 显示当前比较的元素
                if (pointer1 < nums1.size() && pointer2 < nums2.size()) {
                    int[] pair1 = nums1.get(pointer1);
                    int[] pair2 = nums2.get(pointer2);
                    g2d.setColor(Color.RED);
                    g2d.drawString("正在比较: [" + pair1[0] + "," + pair1[1] + "] vs [" + pair2[0] + "," + pair2[1] + "]", 
                                  infoX + 500, infoY + 100);
                }
            }
            
            // 绘制结果说明
            if (!result.isEmpty()) {
                g2d.setColor(Color.DARK_GRAY);
                g2d.drawString("结果数组包含的元素:", infoX, infoY + 140);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < result.size(); i++) {
                    int[] pair = result.get(i);
                    sb.append("[").append(pair[0]).append(",").append(pair[1]).append("]");
                    if (i < result.size() - 1) sb.append(", ");
                }
                g2d.drawString(sb.toString(), infoX, infoY + 160);
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
                new NO2570_E_MergeArrays_Animation().setVisible(true);
            }
        });
    }
}
