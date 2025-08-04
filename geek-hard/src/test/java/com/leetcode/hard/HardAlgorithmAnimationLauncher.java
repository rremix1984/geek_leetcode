package com.leetcode.hard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Hard难度算法动画演示启动器
 * 提供统一的入口来启动各种Hard难度算法的动画演示
 */
public class HardAlgorithmAnimationLauncher extends JFrame {
    private Map<String, Runnable> animations;
    private JPanel mainPanel;
    private JList<String> algorithmList;
    private JTextArea descriptionArea;
    private JButton startButton;
    
    public HardAlgorithmAnimationLauncher() {
        setTitle("LeetCode Hard算法动画演示启动器");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        
        initAnimations();
        initComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initAnimations() {
        animations = new HashMap<>();
        
        // 注册所有Hard难度动画
        animations.put("NO.23 合并K个升序链表", () -> {
            new NO023_H_MergeKSortedLists_Animation().setVisible(true);
        });
        
        animations.put("NO.42 接雨水", () -> {
            new NO042_H_TrappingRainWater_Animation().setVisible(true);
        });
        
        animations.put("NO.84 柱状图中最大的矩形", () -> {
            new NO084_H_LargestRectangleArea_Animation().setVisible(true);
        });
        
        animations.put("NO.123 买卖股票的最佳时机 III", () -> {
            new NO123_H_BestTimeToBuyAndSellStockIII_Animation().setVisible(true);
        });
        
        animations.put("NO.188 买卖股票的最佳时机 IV", () -> {
            new NO188_H_BestTimeToBuyAndSellStockIV_Animation().setVisible(true);
        });
        
        animations.put("NO.403 青蛙过河", () -> {
            new NO403_H_FrogJump_Animation().setVisible(true);
        });
        
        // 可以继续添加更多Hard难度动画...
    }
    
    private void initComponents() {
        mainPanel = new JPanel();
        
        // 创建算法列表
        String[] algorithmNames = animations.keySet().toArray(new String[0]);
        algorithmList = new JList<>(algorithmNames);
        algorithmList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        algorithmList.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        
        // 创建描述区域
        descriptionArea = new JTextArea();
        descriptionArea.setEditable(false);
        descriptionArea.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        descriptionArea.setBackground(getBackground());
        descriptionArea.setBorder(BorderFactory.createTitledBorder("算法描述"));
        
        // 创建启动按钮
        startButton = new JButton("启动动画演示");
        startButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        startButton.setEnabled(false);
        
        // 设置默认描述
        setDefaultDescription();
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 主面板使用分割面板
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        // 左侧：算法列表
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Hard难度算法列表"));
        leftPanel.add(new JScrollPane(algorithmList), BorderLayout.CENTER);
        
        // 右侧：描述和控制
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(startButton);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        splitPane.setDividerLocation(300);
        
        add(splitPane, BorderLayout.CENTER);
        
        // 顶部说明
        JLabel titleLabel = new JLabel("选择一个Hard难度算法查看动画演示", JLabel.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);
    }
    
    private void setupEventHandlers() {
        // 列表选择事件
        algorithmList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = algorithmList.getSelectedValue();
                if (selected != null) {
                    updateDescription(selected);
                    startButton.setEnabled(true);
                } else {
                    setDefaultDescription();
                    startButton.setEnabled(false);
                }
            }
        });
        
        // 启动按钮事件
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = algorithmList.getSelectedValue();
                if (selected != null && animations.containsKey(selected)) {
                    animations.get(selected).run();
                }
            }
        });
    }
    
    private void setDefaultDescription() {
        descriptionArea.setText("请从左侧列表中选择一个Hard难度算法来查看详细描述和启动动画演示。\\n\\n" +
                              "当前可用的Hard难度动画演示包括：\\n" +
                              "• 链表相关算法\\n" +
                              "• 动态规划算法\\n" +
                              "• 单调栈算法\\n" +
                              "• 更多算法正在开发中...");
    }
    
    private void updateDescription(String algorithmName) {
        String description = getAlgorithmDescription(algorithmName);
        descriptionArea.setText(description);
        descriptionArea.setCaretPosition(0); // 滚动到顶部
    }
    
    private String getAlgorithmDescription(String algorithmName) {
        switch (algorithmName) {
            case "NO.23 合并K个升序链表":
                return "【NO.23 合并K个升序链表】\\n\\n" +
                       "问题描述：\\n" +
                       "给你一个链表数组，每个链表都已经按升序排列。请你将所有链表合并到一个升序链表中，返回合并后的链表。\\n\\n" +
                       "核心算法：\\n" +
                       "• 使用优先队列（最小堆）\\n" +
                       "• 将所有链表的头节点加入优先队列\\n" +
                       "• 每次取出最小值节点，将其下一个节点加入队列\\n" +
                       "• 重复直到队列为空\\n\\n" +
                       "时间复杂度：O(N*logk)，其中N是所有节点总数，k是链表个数\\n" +
                       "空间复杂度：O(k)\\n\\n" +
                       "动画特色：\\n" +
                       "• 可视化优先队列的工作过程\\n" +
                       "• 实时显示合并结果\\n" +
                       "• 展示节点选择和连接过程";
                       
            case "NO.42 接雨水":
                return "【NO.42 接雨水】\\n\\n" +
                       "问题描述：\\n" +
                       "给定n个非负整数表示每个宽度为1的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。\\n\\n" +
                       "核心算法（动态规划）：\\n" +
                       "• 计算每个位置左边的最大高度\\n" +
                       "• 计算每个位置右边的最大高度\\n" +
                       "• 每个位置的积水量 = min(leftMax, rightMax) - height\\n" +
                       "• 累加所有位置的积水量\\n\\n" +
                       "时间复杂度：O(n)\\n" +
                       "空间复杂度：O(n)\\n\\n" +
                       "动画特色：\\n" +
                       "• 分步骤可视化计算过程\\n" +
                       "• 实时显示leftMax和rightMax数组\\n" +
                       "• 动态展示积水效果\\n" +
                       "• 支持自定义柱子高度";
                       
            case "NO.84 柱状图中最大的矩形":
                return "【NO.84 柱状图中最大的矩形】\\n\\n" +
                       "问题描述：\\n" +
                       "给定n个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为1。" +
                       "求在该柱状图中，能够勾勒出来的矩形的最大面积。\\n\\n" +
                       "核心算法（单调栈）：\\n" +
                       "• 维护一个单调递增的栈\\n" +
                       "• 遍历每个柱子，当当前高度小于栈顶高度时\\n" +
                       "• 弹出栈顶元素，计算以该高度为矩形高度的最大面积\\n" +
                       "• 宽度 = 当前位置 - 栈顶位置 - 1\\n\\n" +
                       "时间复杂度：O(n)\\n" +
                       "空间复杂度：O(n)\\n\\n" +
                       "动画特色：\\n" +
                       "• 可视化单调栈的工作过程\\n" +
                       "• 实时显示栈的状态\\n" +
                       "• 动态计算和更新最大面积\\n" +
                       "• 支持自定义柱子高度";
                       
            case "NO.123 买卖股票的最佳时机 III":
                return "【NO.123 买卖股票的最佳时机 III】\\n\\n" +
                       "问题描述：\\n" +
                       "给定一个数组，它的第i个元素是一支给定的股票在第i天的价格。" +
                       "设计一个算法来计算你所能获取的最大利润。你最多可以完成两笔交易。\\n\\n" +
                       "核心算法（动态规划）：\\n" +
                       "• 状态定义：dp[i][j][k] 表示第i天，完成j笔交易，持股状态为k的最大利润\\n" +
                       "• k=0表示不持股，k=1表示持股\\n" +
                       "• 状态转移方程：\\n" +
                       "  - dp[i][j][0] = max(dp[i-1][j][0], dp[i-1][j][1] + prices[i])\\n" +
                       "  - dp[i][j][1] = max(dp[i-1][j][1], dp[i-1][j-1][0] - prices[i])\\n\\n" +
                       "时间复杂度：O(n)\\n" +
                       "空间复杂度：O(1)\\n\\n" +
                       "动画特色：\\n" +
                       "• 可视化DP状态转移过程\\n" +
                       "• 实时显示股票价格和利润计算\\n" +
                       "• 展示最优买卖时机选择\\n" +
                       "• 支持自定义股票价格";
                       
            case "NO.188 买卖股票的最佳时机 IV":
                return "【NO.188 买卖股票的最佳时机 IV】\\n\\n" +
                       "问题描述：\\n" +
                       "给定一个整数数组prices，它的第i个元素prices[i]是一支给定的股票在第i天的价格。" +
                       "设计一个算法来计算你所能获取的最大利润。你最多可以完成k笔交易。\\n\\n" +
                       "核心算法（动态规划）：\\n" +
                       "• 状态定义：dp[i][j] 表示进行至多j次交易，在第i天能获得的最大利润\\n" +
                       "• 状态转移方程：\\n" +
                       "  - buy[j] = max(buy[j], sell[j-1] - price)\\n" +
                       "  - sell[j] = max(sell[j], buy[j] + price)\\n" +
                       "• 优化：当k >= n/2时，退化为无限次交易问题\\n\\n" +
                       "时间复杂度：O(nk)\\n" +
                       "空间复杂度：O(k)\\n\\n" +
                       "动画特色：\\n" +
                       "• 可视化多次交易的DP计算过程\\n" +
                       "• 实时显示不同交易次数下的最大利润\\n" +
                       "• 展示k值对结果的影响\\n" +
                       "• 支持自定义交易次数和价格";
                       
            case "NO.403 青蛙过河":
                return "【NO.403 青蛙过河】\\n\\n" +
                       "问题描述：\\n" +
                       "一只青蛙想要过河。假定河流被等分为若干个单元格，并且在每一个单元格内都有可能放有一块石子。" +
                       "青蛙可以跳上石子，但是不可以跳入水中。给你石子的位置列表stones，请判定青蛙能否成功过河。\\n\\n" +
                       "核心算法（动态规划）：\\n" +
                       "• 状态定义：dp[i][k] 表示能否以步长k跳到第i个石子\\n" +
                       "• 初始状态：dp[0][0] = true（青蛙在第一个石子上）\\n" +
                       "• 状态转移：从石子j跳到石子i，步长为k = stones[i] - stones[j]\\n" +
                       "• 转移条件：dp[i][k] = dp[j][k-1] || dp[j][k] || dp[j][k+1]\\n" +
                       "• 跳跃规则：如果上一步跳了k个单位，下一步只能跳k-1、k或k+1个单位\\n\\n" +
                       "时间复杂度：O(n²)\\n" +
                       "空间复杂度：O(n²)\\n\\n" +
                       "动画特色：\\n" +
                       "• 可视化青蛙跳跃过程\\n" +
                       "• 实时显示DP状态表\\n" +
                       "• 动态展示跳跃路径\\n" +
                       "• 支持自定义石子位置\\n" +
                       "• 分步骤演示算法执行过程";
                       
            default:
                return "算法描述加载中...";
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
                
                // 显示启动器
                new HardAlgorithmAnimationLauncher().setVisible(true);
                
                // 显示欢迎信息
                JOptionPane.showMessageDialog(null, 
                    "欢迎使用LeetCode Hard难度算法动画演示系统！\\n\\n" +
                    "功能特点：\\n" +
                    "• 可视化复杂算法执行过程\\n" +
                    "• 支持交互式操作\\n" +
                    "• 提供详细的算法说明\\n" +
                    "• 帮助理解高难度算法原理\\n\\n" +
                    "请从列表中选择一个算法开始体验！", 
                    "欢迎", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
