package com.leetcode.animation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

// Array algorithms
import com.leetcode.animation.array.*;

// Sort algorithms
import com.leetcode.animation.sort.*;

// Data structure algorithms
import com.leetcode.animation.datastructure.*;

// Search algorithms
import com.leetcode.animation.search.*;

// Dynamic programming algorithms
import com.leetcode.animation.dp.*;

// Greedy algorithms
import com.leetcode.animation.greedy.*;

// String algorithms
import com.leetcode.animation.string.*;

// Bit manipulation algorithms
import com.leetcode.animation.bit.*;

// Graph algorithms
import com.leetcode.animation.graph.*;

// Math algorithms
import com.leetcode.animation.math.*;

// Tree algorithms
import com.leetcode.animation.tree.*;

/**
 * 算法动画演示启动器
 * 提供统一的入口来启动各种算法的动画演示
 */
public class AlgorithmAnimationLauncher extends JFrame {
    private Map<String, Runnable> animations;
    private JPanel mainPanel;
    private JList<String> algorithmList;
    private JTextArea descriptionArea;
    private JButton startButton;
    
    public AlgorithmAnimationLauncher() {
        setTitle("LeetCode算法动画演示启动器");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        initAnimations();
        initComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initAnimations() {
        animations = new HashMap<>();
        
        // 注册所有动画
        animations.put("NO.001 两数之和", () -> {
            new NO001_E_TwoSum_Animation().setVisible(true);
        });
        
        animations.put("NO.088 合并两个有序数组", () -> {
            new NO088_E_MergeSortedArray_Animation().setVisible(true);
        });
        
        animations.put("NO.118 杨辉三角", () -> {
            new NO118_E_Generate_Animation().setVisible(true);
        });
        
        animations.put("NO.225 用队列实现栈", () -> {
            new NO225_E_MyStack_Animation().setVisible(true);
        });
        
        animations.put("NO.704 二分查找", () -> {
            new NO704_E_BinarySearch_Animation().setVisible(true);
        });
        
        animations.put("NO.977 有序数组的平方", () -> {
            new NO977_E_SortedSquares_Animation().setVisible(true);
        });
        
        animations.put("NO.2558 从数量最多的堆取走礼物", () -> {
            new NO2558_E_PickGifts_Animation().setVisible(true);
        });
        
        animations.put("NO.2570 合并两个二维数组", () -> {
            new NO2570_E_MergeArrays_Animation().setVisible(true);
        });
        
        animations.put("插入排序算法演示", () -> {
            new InsertionSortAnimation().setVisible(true);
        });
        
        animations.put("NO.2553 分割数组中数字的数位", () -> {
            new NO2553_E_SeparateDigits_Animation().setVisible(true);
        });
        
        animations.put("NO.2706 购买两块巧克力", () -> {
            new com.leetcode.animation.greedy.NO2706_E_BuyChoco_Animation().setVisible(true);
        });
        
        animations.put("NO.2824 统计和小于目标的下标对数目", () -> {
            new NO2824_E_CountPairs_Animation().setVisible(true);
        });
        
        // donnot目录中的新增动画
        animations.put("NO.1002 查找常用字符", () -> {
            new NO1002_E_CommonChars_Animation().setVisible(true);
        });
        
        animations.put("NO.463 岛屿的周长", () -> {
            new NO463_E_IslandPerimeter_Animation().setVisible(true);
        });
        
        animations.put("NO.190 颠倒二进制位", () -> {
            new NO190_E_ReverseBits_Animation().setVisible(true);
        });
        
        animations.put("NO.1051 高度检查器", () -> {
            new NO1051_E_HeightChecker_Animation().setVisible(true);
        });
        
        // 第二批donnot目录动画
        animations.put("NO.703 数据流中的第K大元素", () -> {
            new NO703_E_KthLargest_Animation().setVisible(true);
        });
        
        animations.put("NO.705 设计哈希集合", () -> {
            new NO705_E_MyHashSet_Animation().setVisible(true);
        });
        
        animations.put("NO.706 设计哈希映射", () -> {
            new NO706_E_MyHashMap_Animation().setVisible(true);
        });
        
        animations.put("NO.1005 K次取反后最大化数组和", () -> {
            new NO1005_E_LargestSumAfterKNegations_Animation().setVisible(true);
        });
        
        // 可以继续添加更多动画...
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
        leftPanel.setBorder(BorderFactory.createTitledBorder("算法列表"));
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
        JLabel titleLabel = new JLabel("选择一个算法查看动画演示", JLabel.CENTER);
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
        descriptionArea.setText("请从左侧列表中选择一个算法来查看详细描述和启动动画演示。\\n\\n" +
                              "当前可用的动画演示包括：\\n" +
                              "• 栈和队列相关算法\\n" +
                              "• 优先队列（堆）算法\\n" +
                              "• 数组合并算法\\n" +
                              "• 更多算法正在开发中...");
    }
    
    private void updateDescription(String algorithmName) {
        String description = getAlgorithmDescription(algorithmName);
        descriptionArea.setText(description);
        descriptionArea.setCaretPosition(0); // 滚动到顶部
    }
    
    private String getAlgorithmDescription(String algorithmName) {
        switch (algorithmName) {
            case "NO.001 两数之和":
                return "【NO.001 两数之和】\\n\\n" +
                       "问题描述：\\n" +
                       "给定一个整数数组nums和一个整数目标值target，请你在该数组中找出和为目标值target的那两个整数，并返回它们的数组下标。\\n\\n" +
                       "核心算法：\\n" +
                       "• 使用HashMap存储已遍历的数值和索引\\n" +
                       "• 对于每个元素，计算target - nums[i]\\n" +
                       "• 检查差值是否在HashMap中存在\\n" +
                       "• 如果存在则返回两个索引，否则将当前元素加入HashMap\\n\\n" +
                       "动画特色：\\n" +
                       "• 可视化数组遍历过程\\n" +
                       "• 显示HashMap的查找和插入操作\\n" +
                       "• 高亮显示匹配的两个数字\\n" +
                       "• 支持自定义数组和目标值";

            case "NO.088 合并两个有序数组":
                return "【NO.088 合并两个有序数组】\\n\\n" +
                       "问题描述：\\n" +
                       "给你两个按非递减顺序排列的整数数组nums1和nums2，另有两个整数m和n，分别表示nums1和nums2中元素的数量。请你合并nums2到nums1中，使合并后的数组同样按非递减顺序排列。\\n\\n" +
                       "核心算法：\\n" +
                       "• 使用双指针从数组末尾开始比较\\n" +
                       "• 将较大的元素放到nums1的末尾\\n" +
                       "• 避免额外空间，直接在nums1中操作\\n" +
                       "• 处理剩余元素\\n\\n" +
                       "动画特色：\\n" +
                       "• 可视化双指针的移动过程\\n" +
                       "• 显示元素比较和移动\\n" +
                       "• 高亮显示当前操作的元素\\n" +
                       "• 支持单步执行和自动演示";

            case "NO.118 杨辉三角":
                return "【NO.118 杨辉三角】\\n\\n" +
                       "问题描述：\\n" +
                       "给定一个非负整数numRows，生成杨辉三角的前numRows行。在杨辉三角中，每个数是它左上方和右上方的数的和。\\n\\n" +
                       "核心算法：\\n" +
                       "• 第一行和每行的首尾元素都是1\\n" +
                       "• 其他元素等于上一行对应位置两个数的和\\n" +
                       "• 使用动态规划逐行构建\\n" +
                       "• triangle[i][j] = triangle[i-1][j-1] + triangle[i-1][j]\\n\\n" +
                       "动画特色：\\n" +
                       "• 逐行生成杨辉三角\\n" +
                       "• 可视化数字计算过程\\n" +
                       "• 高亮显示计算路径\\n" +
                       "• 支持自定义行数";

            case "NO.225 用队列实现栈":
                return "【NO.225 用队列实现栈】\\n\\n" +
                       "问题描述：\\n" +
                       "请你仅使用两个队列实现一个后入先出（LIFO）的栈，并支持普通栈的全部四种操作（push、pop、top、empty）。\\n\\n" +
                       "核心算法：\\n" +
                       "• 使用两个队列 queue1 和 queue2\\n" +
                       "• Push操作：将新元素加入queue2，然后将queue1的所有元素移到queue2，最后交换两个队列\\n" +
                       "• Pop操作：直接从queue1中取出元素\\n" +
                       "• Top操作：查看queue1的队首元素\\n\\n" +
                       "动画特色：\\n" +
                       "• 可视化两个队列的状态变化\\n" +
                       "• 显示栈的概念视图\\n" +
                       "• 实时显示队列内容\\n" +
                       "• 支持交互式操作";

            case "NO.704 二分查找":
                return "【NO.704 二分查找】\\n\\n" +
                       "问题描述：\\n" +
                       "给定一个n个元素有序的（升序）整型数组nums和一个目标值target，写一个函数搜索nums中的target，如果目标值存在返回下标，否则返回-1。\\n\\n" +
                       "核心算法：\\n" +
                       "• 维护左右边界left和right\\n" +
                       "• 计算中点mid = (left + right) / 2\\n" +
                       "• 比较nums[mid]与target的大小关系\\n" +
                       "• 根据比较结果调整搜索范围\\n" +
                       "• 时间复杂度O(log n)\\n\\n" +
                       "动画特色：\\n" +
                       "• 可视化搜索范围的缩小过程\\n" +
                       "• 高亮显示left、right、mid指针\\n" +
                       "• 显示每步比较的详细信息\\n" +
                       "• 支持自定义数组和目标值";

            case "NO.977 有序数组的平方":
                return "【NO.977 有序数组的平方】\\n\\n" +
                       "问题描述：\\n" +
                       "给你一个按非递减顺序排序的整数数组nums，返回每个数字的平方组成的新数组，要求也按非递减顺序排序。\\n\\n" +
                       "核心算法：\\n" +
                       "• 使用双指针技术，分别指向数组首尾\\n" +
                       "• 比较两端元素平方的大小\\n" +
                       "• 将较大的平方值放入结果数组的末尾\\n" +
                       "• 移动对应指针，重复直到完成\\n\\n" +
                       "动画特色：\\n" +
                       "• 可视化双指针的移动过程\\n" +
                       "• 显示平方计算和比较\\n" +
                       "• 高亮显示当前处理的元素\\n" +
                       "• 实时显示结果数组的构建";

            case "NO.2553 分割数组中数字的数位":
                return "【NO.2553 分割数组中数字的数位】\\n\\n" +
                       "问题描述：\\n" +
                       "给你一个正整数数组nums，返回一个数组answer，其中answer[i]等于nums[i]的各个数位按原顺序组成的数组。\\n\\n" +
                       "核心算法：\\n" +
                       "• 遍历数组中的每个数字\\n" +
                       "• 将数字转换为字符串\\n" +
                       "• 逐个字符转换回数字\\n" +
                       "• 将所有数位添加到结果数组\\n\\n" +
                       "动画特色：\\n" +
                       "• 可视化数字分解过程\\n" +
                       "• 显示每个数位的提取\\n" +
                       "• 高亮显示当前处理的数字\\n" +
                       "• 实时显示结果数组的构建";

            case "NO.2558 从数量最多的堆取走礼物":
                return "【NO.2558 从数量最多的堆取走礼物】\\n\\n" +
                       "问题描述：\\n" +
                       "给你一个整数数组gifts，表示各堆礼物的数量。每一秒选择礼物数量最多的那一堆，留下平方根数量的礼物（向下取整），取走其他的礼物。返回在k秒后剩下的礼物数量。\\n\\n" +
                       "核心算法：\\n" +
                       "• 使用优先队列（最大堆）维护礼物数量\\n" +
                       "• 每次取出最大值，计算其平方根\\n" +
                       "• 将新值重新加入堆中\\n" +
                       "• 重复k次操作\\n\\n" +
                       "动画特色：\\n" +
                       "• 可视化最大堆的结构\\n" +
                       "• 高亮显示当前最大值\\n" +
                       "• 显示每步操作的计算过程\\n" +
                       "• 支持自动演示和单步调试\\n" +
                       "• 实时显示剩余礼物总数";

            case "NO.2570 合并两个二维数组":
                return "【NO.2570 合并两个二维数组】\\n\\n" +
                       "问题描述：\\n" +
                       "给你两个二维整数数组nums1和nums2，每个数组包含[id, value]对。将两个数组合并为一个按id递增顺序排列的数组，相同id的值要相加。\\n\\n" +
                       "核心算法：\\n" +
                       "• 使用双指针技术\\n" +
                       "• 比较两个指针指向元素的id\\n" +
                       "• id较小的直接加入结果\\n" +
                       "• id相同时合并值后加入结果\\n" +
                       "• 处理剩余元素\\n\\n" +
                       "动画特色：\\n" +
                       "• 可视化双指针的移动过程\\n" +
                       "• 高亮显示当前比较的元素\\n" +
                       "• 显示合并步骤的详细信息\\n" +
                       "• 支持单步执行和完整合并\\n" +
                       "• 实时显示指针位置和合并进度";

            case "NO.2706 购买两块巧克力":
                return "【NO.2706 购买两块巧克力】\\n\\n" +
                       "问题描述：\\n" +
                       "给你一个整数数组prices，表示巧克力的价格，和一个整数money，表示你的钱数。你必须购买两块巧克力，返回购买后的剩余钱数。如果无法购买两块巧克力，返回money。\\n\\n" +
                       "核心算法：\\n" +
                       "• 找到数组中最小的两个元素\\n" +
                       "• 使用贪心算法，一次遍历找到最小值和次小值\\n" +
                       "• 检查是否有足够的钱购买\\n" +
                       "• 返回剩余金额\\n\\n" +
                       "动画特色：\\n" +
                       "• 可视化寻找最小值的过程\\n" +
                       "• 高亮显示当前最小值和次小值\\n" +
                       "• 显示价格比较过程\\n" +
                       "• 实时显示剩余金额计算";

            case "NO.2824 统计和小于目标的下标对数目":
                return "【NO.2824 统计和小于目标的下标对数目】\\n\\n" +
                       "问题描述：\\n" +
                       "给你一个下标从0开始长度为n的整数数组nums和一个整数target，返回满足0 <= i < j < n且nums[i] + nums[j] < target的下标对(i, j)的数目。\\n\\n" +
                       "核心算法：\\n" +
                       "• 使用双重循环遍历所有可能的下标对\\n" +
                       "• 对于每个i，检查所有j > i的情况\\n" +
                       "• 计算nums[i] + nums[j]是否小于target\\n" +
                       "• 统计满足条件的对数\\n\\n" +
                       "动画特色：\\n" +
                       "• 可视化双重循环的执行过程\\n" +
                       "• 高亮显示当前检查的下标对\\n" +
                       "• 显示和的计算过程\\n" +
                       "• 实时统计满足条件的对数";

            case "插入排序算法演示":
                return "【插入排序算法演示】\\n\\n" +
                       "问题描述：\\n" +
                       "插入排序是一种简单直观的排序算法。它的工作原理是通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入。\\n\\n" +
                       "核心算法：\\n" +
                       "• 从第二个元素开始，将当前元素作为key\\n" +
                       "• 将key与前面已排序的元素逐一比较\\n" +
                       "• 将大于key的元素向后移动一位\\n" +
                       "• 将key插入到正确位置\\n" +
                       "• 重复以上步骤直到所有元素排序完成\\n\\n" +
                       "动画特色：\\n" +
                       "• 柱状图可视化数组元素\\n" +
                       "• 颜色区分已排序和未排序部分\\n" +
                       "• 逐步展示插入过程\\n" +
                       "• 支持自定义数据输入\\n" +
                       "• 自动演示排序全过程";

            case "NO.1002 查找常用字符":
                return "【NO.1002 查找常用字符】\\n\\n" +
                       "问题描述：\\n" +
                       "给你一个字符串数组words，请你找出所有在words的每个字符串中都出现的共用字符（包括重复字符），并以数组形式返回。你可以按任意顺序返回答案。\\n\\n" +
                       "核心算法：\\n" +
                       "• 统计第一个字符串中每个字符的频次\\n" +
                       "• 遍历其他字符串，更新字符的最小频次\\n" +
                       "• 根据最终频次构建结果数组\\n" +
                       "• 使用数组或HashMap进行字符计数\\n\\n" +
                       "动画特色：\\n" +
                       "• 可视化字符频次统计过程\\n" +
                       "• 显示每个字符串的处理步骤\\n" +
                       "• 高亮显示当前处理的字符\\n" +
                       "• 实时显示频次表的更新\\n" +
                       "• 支持自定义字符串数组";

            case "NO.463 岛屿的周长":
                return "【NO.463 岛屿的周长】\\n\\n" +
                       "问题描述：\\n" +
                       "给定一个row x col的二维网格地图grid，其中grid[i][j] = 1表示陆地，grid[i][j] = 0表示水域。网格中的格子水平和竖直方向相连（对角线方向不相连）。整个网格被水完全包围，但其中恰好有一个岛屿。岛屿没有湖（水域在岛屿内部且不和岛屿周围的水相连）。格子是边长为1的正方形。网格为长方形，且宽度和高度均不超过100。计算这个岛屿的周长。\\n\\n" +
                       "核心算法：\\n" +
                       "• 遍历网格中的每个陆地格子\\n" +
                       "• 对于每个陆地格子，检查其四个方向\\n" +
                       "• 如果某个方向是边界或水域，则周长+1\\n" +
                       "• 累计所有陆地格子的边界数\\n\\n" +
                       "动画特色：\\n" +
                       "• 可视化网格遍历过程\\n" +
                       "• 高亮显示当前检查的格子\\n" +
                       "• 显示四个方向的边界检查\\n" +
                       "• 实时统计周长计算\\n" +
                       "• 支持自定义网格输入";

            case "NO.190 颠倒二进制位":
                return "【NO.190 颠倒二进制位】\\n\\n" +
                       "问题描述：\\n" +
                       "颠倒给定的32位无符号整数的二进制位。\\n\\n" +
                       "核心算法：\\n" +
                       "• 逐位处理输入的32位数字\\n" +
                       "• 使用位运算提取最低位：n & 1\\n" +
                       "• 将结果左移一位，为新位腾出空间\\n" +
                       "• 将提取的位加入结果：result = (result << 1) | (n & 1)\\n" +
                       "• 将输入右移一位：n >>= 1\\n" +
                       "• 重复32次完成所有位的处理\\n\\n" +
                       "动画特色：\\n" +
                       "• 可视化32位二进制表示\\n" +
                       "• 逐位显示处理过程\\n" +
                       "• 高亮显示当前处理的位\\n" +
                       "• 实时显示结果的构建过程\\n" +
                       "• 支持自定义输入数字";

            case "NO.1051 高度检查器":
                return "【NO.1051 高度检查器】\\n\\n" +
                       "问题描述：\\n" +
                       "学校打算为全体学生拍一张年度纪念照。根据要求，学生需要按照非递减的高度顺序排列。排列后的高度情况用整数数组expected表示，其中expected[i]是预期排在这个位置的学生的高度。给你一个整数数组heights，表示当前学生站位的高度情况。返回为了满足预期排列，需要移动的学生人数。\\n\\n" +
                       "核心算法：\\n" +
                       "• 复制原数组并进行排序得到期望数组\\n" +
                       "• 逐一比较原数组和排序后数组\\n" +
                       "• 统计不匹配的位置数量\\n" +
                       "• 也可使用计数排序优化\\n\\n" +
                       "动画特色：\\n" +
                       "• 可视化排序过程\\n" +
                       "• 对比原数组和期望数组\\n" +
                       "• 高亮显示不匹配的位置\\n" +
                       "• 实时统计需要移动的学生数\\n" +
                       "• 支持计数排序算法演示";

            case "NO.703 数据流中的第K大元素":
                return "【NO.703 数据流中的第K大元素】\\n\\n" +
                       "问题描述：\\n" +
                       "设计一个找到数据流中第K大元素的类。注意是排序后的第K大元素，不是第K个不同的元素。实现KthLargest类，包含构造函数和add方法。\\n\\n" +
                       "核心算法：\\n" +
                       "• 使用最小堆（优先队列）维护K个最大元素\\n" +
                       "• 堆的大小始终保持为K\\n" +
                       "• 当堆大小超过K时，移除最小元素\\n" +
                       "• 堆顶元素就是第K大元素\\n\\n" +
                       "动画特色：\\n" +
                       "• 可视化最小堆的结构和操作\\n" +
                       "• 显示每次add操作的堆调整过程\\n" +
                       "• 高亮显示堆顶元素（第K大元素）\\n" +
                       "• 实时显示堆的大小变化\\n" +
                       "• 支持自定义K值和数据流";

            case "NO.705 设计哈希集合":
                return "【NO.705 设计哈希集合】\\n\\n" +
                       "问题描述：\\n" +
                       "不使用任何内建的哈希表库设计一个哈希集合。实现MyHashSet类，包含add、remove、contains方法。\\n\\n" +
                       "核心算法：\\n" +
                       "• 使用数组+链表实现哈希集合\\n" +
                       "• 哈希函数：hash(key) = key % bucketSize\\n" +
                       "• 使用链地址法解决哈希冲突\\n" +
                       "• 每个桶使用链表存储冲突的元素\\n\\n" +
                       "动画特色：\\n" +
                       "• 可视化哈希表的桶结构\\n" +
                       "• 显示哈希函数的计算过程\\n" +
                       "• 演示链地址法解决冲突\\n" +
                       "• 高亮显示当前操作的桶和元素\\n" +
                       "• 支持自定义操作序列";

            case "NO.706 设计哈希映射":
                return "【NO.706 设计哈希映射】\\n\\n" +
                       "问题描述：\\n" +
                       "不使用任何内建的哈希表库设计一个哈希映射。实现MyHashMap类，包含put、get、remove方法。\\n\\n" +
                       "核心算法：\\n" +
                       "• 使用数组存储键值对\\n" +
                       "• 哈希函数：hash(key) = key % arraySize\\n" +
                       "• 使用线性探测法解决哈希冲突\\n" +
                       "• 删除时需要重新哈希后续元素\\n\\n" +
                       "动画特色：\\n" +
                       "• 可视化哈希表的数组结构\\n" +
                       "• 显示线性探测的过程\\n" +
                       "• 演示键值对的存储和查找\\n" +
                       "• 高亮显示当前操作的位置\\n" +
                       "• 支持自定义操作序列";

            case "NO.1005 K次取反后最大化数组和":
                return "【NO.1005 K次取反后最大化数组和】\\n\\n" +
                       "问题描述：\\n" +
                       "给你一个整数数组nums和一个整数k，按以下方式修改数组：选择某个下标i并将nums[i]替换为-nums[i]。重复这个过程恰好k次。可以多次选择同一个下标i。以这种方式修改数组后，返回数组可能的最大和。\\n\\n" +
                       "核心算法：\\n" +
                       "• 使用贪心策略：优先对负数进行取反\\n" +
                       "• 对数组进行排序，从最小的负数开始处理\\n" +
                       "• 如果还有剩余次数，对绝对值最小的数取反\\n" +
                       "• 每次操作都选择能使数组和增加最多的元素\\n\\n" +
                       "动画特色：\\n" +
                       "• 可视化贪心选择过程\\n" +
                       "• 显示每次取反操作的效果\\n" +
                       "• 高亮显示当前选择的元素\\n" +
                       "• 实时显示数组和的变化\\n" +
                       "• 支持自定义数组和K值";
                       
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
                
                // 直接启动树形分类启动器，不再显示原始的列表启动器
                AlgorithmTreeLauncher.showMainWindow();
            }
        });
    }
}
