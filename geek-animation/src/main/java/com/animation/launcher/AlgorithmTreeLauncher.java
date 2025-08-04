package com.animation.launcher;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;
import java.awt.Window;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

// 数组算法导入
import com.animation.array.*;

// 排序算法导入
import com.animation.sort.*;

// 数据结构设计导入
import com.animation.datastructure.*;

// 搜索算法导入
import com.animation.search.*;

// 动态规划导入
import com.animation.dp.*;

// 贪心算法导入
import com.animation.greedy.*;

// DFS算法导入
import com.animation.dfs.*;

// BFS算法导入
import com.animation.bfs.*;

// 字符串算法导入
import com.animation.string.*;

// 位运算导入
import com.animation.bit.*;

// 图论算法导入
import com.animation.graph.*;

// 数学算法导入
import com.animation.math.*;

// 树算法导入
import com.animation.tree.*;

// 困难算法导入
import com.animation.hard.NO403_H_FrogJump_Animation;
import com.animation.normal.*;

// 导入JVM监控工具
import com.leetcode.tools.JVMProcessMonitor;
import com.leetcode.tools.JVMMemoryMonitor;

/**
 * 算法动画演示树形启动器
 * 按算法类型分类展示，提供更好的层次结构和用户体验
 * 使用单例模式确保只有一个主窗口实例
 * 
 * @author 开发工程师
 * @version 1.0
 */
public class AlgorithmTreeLauncher extends JFrame {
    private static AlgorithmTreeLauncher instance;
    
    private Map<String, Runnable> animations;
    private JTree algorithmTree;
    private JTextArea descriptionArea;
    private JButton startButton;
    private JButton processMonitorButton;
    private JButton memoryMonitorButton;
    private JLabel statusLabel;
    private DefaultMutableTreeNode rootNode;
    private DefaultMutableTreeNode filteredRootNode;
    private JTextField searchField;
    private JButton clearSearchButton;
    private List<DefaultMutableTreeNode> allAlgorithmNodes;
    
    // 算法分类节点
    private DefaultMutableTreeNode arrayNode;
    private DefaultMutableTreeNode sortNode;
    private DefaultMutableTreeNode dataStructureNode;
    private DefaultMutableTreeNode searchNode;
    private DefaultMutableTreeNode dpNode;
    private DefaultMutableTreeNode greedyNode;
    private DefaultMutableTreeNode dfsNode;
    private DefaultMutableTreeNode bfsNode;
    private DefaultMutableTreeNode stringNode;
    private DefaultMutableTreeNode bitNode;
    private DefaultMutableTreeNode graphNode;
    private DefaultMutableTreeNode mathNode;
    private DefaultMutableTreeNode treeNode;
    private DefaultMutableTreeNode hardNode;
    private DefaultMutableTreeNode normalNode;
    
    private AlgorithmTreeLauncher() {
        // 获取JMX端口号并设置标题
        String jmxPort = System.getProperty("com.sun.management.jmxremote.port", "未启用");
        String title = "LeetCode算法动画演示系统 - 树形分类版";
        if (!"未启用".equals(jmxPort)) {
            title += " [JMX端口: " + jmxPort + "]";
        }
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        allAlgorithmNodes = new ArrayList<>();
        initAnimations();
        initTreeStructure();
        initComponents();
        setupLayout();
        setupEventHandlers();
        
        // 展开所有节点
        expandAllNodes();
    }
    
    /**
     * 获取单例实例
     */
    public static AlgorithmTreeLauncher getInstance() {
        if (instance == null) {
            instance = new AlgorithmTreeLauncher();
        }
        return instance;
    }
    
    /**
     * 显示主窗口
     */
    public static void showMainWindow() {
        SwingUtilities.invokeLater(() -> {
            AlgorithmTreeLauncher launcher = getInstance();
            launcher.setVisible(true);
            launcher.toFront();
            launcher.requestFocus();
        });
    }
    
    /**
     * 初始化所有动画映射
     */
    private void initAnimations() {
        animations = new HashMap<>();
        
        // 数组算法
        animations.put("NO.001 两数之和", () -> new NO001_E_TwoSum_Animation().setVisible(true));
        animations.put("NO.026 删除有序数组中的重复项", () -> new NO026_E_RemoveDuplicatesFromSortedArray_Animation().setVisible(true));
        animations.put("NO.027 移除元素", () -> new NO027_E_RemoveElement_Animation().setVisible(true));
        animations.put("NO.088 合并两个有序数组", () -> new NO088_E_MergeSortedArray_Animation().setVisible(true));
        animations.put("NO.283 移动零", () -> new NO283_E_MoveZeroes_Animation().setVisible(true));
        animations.put("NO.977 有序数组的平方", () -> new NO977_E_SortedSquares_Animation().setVisible(true));
        animations.put("NO.2553 分割数组中数字的数位", () -> new NO2553_E_SeparateDigits_Animation().setVisible(true));
        animations.put("NO.2706 购买两块巧克力", () -> new NO2706_E_BuyChoco_Animation().setVisible(true));
        animations.put("NO.2824 统计和小于目标的下标对数目", () -> new NO2824_E_CountPairs_Animation().setVisible(true));
        animations.put("NO.1051 高度检查器", () -> new NO1051_E_HeightChecker_Animation().setVisible(true));
        animations.put("NO.2570 合并两个二维数组", () -> new NO2570_E_MergeArrays_Animation().setVisible(true));
        
        // 排序算法
        animations.put("插入排序算法演示", () -> new InsertionSortAnimation().setVisible(true));
        
        // 数据结构设计
        animations.put("NO.225 用队列实现栈", () -> new NO225_E_MyStack_Animation().setVisible(true));
        animations.put("NO.234 回文链表", () -> new NO234_E_PalindromeLinkedList_Animation().setVisible(true));
        animations.put("NO.705 设计哈希集合", () -> new NO705_E_MyHashSet_Animation().setVisible(true));
        animations.put("NO.706 设计哈希映射", () -> new NO706_E_MyHashMap_Animation().setVisible(true));
        animations.put("NO.703 数据流中的第K大元素", () -> new NO703_E_KthLargest_Animation().setVisible(true));
        
        // 搜索算法
        animations.put("NO.035 搜索插入位置", () -> new NO035_E_SearchInsert_Animation().setVisible(true));
        animations.put("NO.704 二分查找", () -> new NO704_E_BinarySearch_Animation().setVisible(true));
        
        // 动态规划
        animations.put("NO.118 杨辉三角", () -> new NO118_E_Generate_Animation().setVisible(true));
        
        // 贪心算法
        animations.put("NO.055 跳跃游戏", () -> new NO055_E_JumpGame_Animation().setVisible(true));
        animations.put("NO.055 跳跃游戏 (Normal)", () -> {
            try {
                Class<?> clazz = Class.forName("com.leetcode.animation.greedy.NO055_N_JumpGame_Animation");
                JFrame frame = (JFrame) clazz.getDeclaredConstructor().newInstance();
                frame.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "无法启动动画: " + e.getMessage());
            }
        });
        animations.put("NO.045 跳跃游戏 II", () -> new NO045_N_JumpGameII_Animation().setVisible(true));
        animations.put("NO.2498 青蛙过河 II", () -> new NO2498_N_MaxJump_Animation().setVisible(true));
        animations.put("NO.121 买卖股票的最佳时机", () -> new NO121_E_BestTimeToBuyAndSellStock_Animation().setVisible(true));
        animations.put("NO.2558 从数量最多的堆取走礼物", () -> new NO2558_E_PickGifts_Animation().setVisible(true));
        animations.put("NO.1005 K次取反后最大化数组和", () -> new NO1005_E_LargestSumAfterKNegations_Animation().setVisible(true));
        
        // DFS算法
        animations.put("NO.1306 跳跃游戏 III", () -> new NO1306_N_JumpGameIII_Animation().setVisible(true));
        
        // BFS算法
        animations.put("NO.1654 到家的最少跳跃次数", () -> new NO1654_N_MinimumJumps_Animation().setVisible(true));
        
        // 字符串算法
        animations.put("NO.1002 查找常用字符", () -> new NO1002_E_CommonChars_Animation().setVisible(true));
        
        // 位运算
        animations.put("NO.136 只出现一次的数字", () -> new NO136_E_SingleNumber_Animation().setVisible(true));
        animations.put("NO.190 颠倒二进制位", () -> new NO190_E_ReverseBits_Animation().setVisible(true));
        
        // 图论算法
        animations.put("NO.463 岛屿的周长", () -> new NO463_E_IslandPerimeter_Animation().setVisible(true));
        
        // 数学算法
        animations.put("NO.009 回文数", () -> new NO009_E_IsPalindrome_Animation().setVisible(true));
        animations.put("NO.066 加一", () -> new NO066_E_PlusOne_Animation().setVisible(true));
        animations.put("NO.069 x的平方根", () -> new NO069_E_SqrtX_Animation().setVisible(true));
        
        // 树算法
        animations.put("NO.094 二叉树的中序遍历", () -> new NO094_E_InorderTraversal_Animation().setVisible(true));
        animations.put("NO.100 相同的树", () -> new NO100_E_IsSameTree_Animation().setVisible(true));
        animations.put("NO.101 对称二叉树", () -> new NO101_E_IsSymmetric_Animation().setVisible(true));
        animations.put("NO.104 二叉树的最大深度", () -> new NO104_E_MaximumDepthOfBinaryTree_Animation().setVisible(true));
        animations.put("NO.108 将有序数组转换为二叉搜索树", () -> new NO108_E_SortedArrayToBST_Animation().setVisible(true));
        
        // 新增算法 - 2024年12月批次
        animations.put("NO.2529 正整数和负整数的最大计数", () -> new NO2529_E_MaximumCount_Animation().setVisible(true));
        animations.put("NO.2535 数组元素和与数字和的绝对差", () -> new NO2535_E_DifferenceOfSum_Animation().setVisible(true));
        animations.put("NO.2540 最小公共值", () -> new NO2540_E_GetCommon_Animation().setVisible(true));
        animations.put("NO.2549 统计桌面上的不同数字", () -> new NO2549_E_DistinctIntegers_Animation().setVisible(true));
        animations.put("NO.2562 找出数组的串联值", () -> new NO2562_E_FindTheArrayConcVal_Animation().setVisible(true));
        animations.put("NO.2574 左右元素和的差值", () -> new NO2574_E_LeftRightDifference_Animation().setVisible(true));
        animations.put("NO.2586 统计范围内的元音字符串数", () -> new NO2586_E_VowelStrings_Animation().setVisible(true));
        
        // 困难算法
        animations.put("NO.403 青蛙过河", () -> new NO403_H_FrogJump_Animation().setVisible(true));
        
        // Normal算法 - donnot目录
        animations.put("NO.208 实现Trie(前缀树)", () -> new NO208_N_Trie_Animation().setVisible(true));
        animations.put("NO.211 添加与搜索单词", () -> new NO211_N_WordDictionary_Animation().setVisible(true));
        animations.put("NO.284 顶端迭代器", () -> new NO284_N_PeekingIterator_Animation().setVisible(true));
        animations.put("NO.764 最大加号标志", () -> new NO764_N_OrderOfLargestPlusSign_Animation().setVisible(true));
        animations.put("NO.915 分割数组", () -> new NO915_N_PartitionDisjoint_Animation().setVisible(true));
        
        // Normal算法 - normal目录新增
        animations.put("NO.005 最长回文子串", () -> new NO005_N_LongestPalindromicSubstring_Animation().setVisible(true));
        animations.put("NO.015 三数之和", () -> new NO015_N_ThreeSum_Animation().setVisible(true));
        animations.put("NO.053 最大子数组和", () -> new NO053_N_MaximumSubarray_Animation().setVisible(true));
        animations.put("NO.046 全排列", () -> new NO046_N_Permutations_Animation().setVisible(true));
        animations.put("NO.200 岛屿数量", () -> new NO200_N_NumberOfIslands_Animation().setVisible(true));
        animations.put("NO.322 零钱兑换", () -> new NO322_N_CoinChange_Animation().setVisible(true));
        animations.put("NO.139 单词拆分", () -> new NO139_N_WordBreak_Animation().setVisible(true));
    }
    
    /**
     * 初始化树形结构
     */
    private void initTreeStructure() {
        rootNode = new DefaultMutableTreeNode("🎯 LeetCode算法动画演示系统");
        
        // 创建主要难度分组
        DefaultMutableTreeNode easyGroupNode = new DefaultMutableTreeNode("🟢 简单算法 (Easy) - 共42个");
        DefaultMutableTreeNode normalGroupNode = new DefaultMutableTreeNode("🟡 中等算法 (Normal) - 共14个");
        DefaultMutableTreeNode hardGroupNode = new DefaultMutableTreeNode("🔴 困难算法 (Hard) - 共1个");
        
        // 创建Easy算法分类节点
        arrayNode = new DefaultMutableTreeNode("📊 数组算法 (18个)");
        sortNode = new DefaultMutableTreeNode("🔄 排序算法 (1个)");
        dataStructureNode = new DefaultMutableTreeNode("🏗️ 数据结构设计 (5个)");
        searchNode = new DefaultMutableTreeNode("🔍 搜索算法 (2个)");
        dpNode = new DefaultMutableTreeNode("💡 动态规划 (1个)");
        greedyNode = new DefaultMutableTreeNode("🎯 贪心算法 (7个)");
        stringNode = new DefaultMutableTreeNode("📝 字符串算法 (1个)");
        bitNode = new DefaultMutableTreeNode("⚡ 位运算 (2个)");
        graphNode = new DefaultMutableTreeNode("🌐 图论算法 (1个)");
        mathNode = new DefaultMutableTreeNode("🔢 数学算法 (4个)");
        treeNode = new DefaultMutableTreeNode("🌳 树算法 (5个)");
        
        // 创建Normal算法分类节点
        dfsNode = new DefaultMutableTreeNode("🔍 深度优先搜索 (1个)");
        bfsNode = new DefaultMutableTreeNode("🌊 广度优先搜索 (1个)");
        normalNode = new DefaultMutableTreeNode("🎯 Normal算法 (12个)");
        
        // 创建Hard算法分类节点
        hardNode = new DefaultMutableTreeNode("🔥 动态规划算法 (1个)");
        
        // 将分类节点添加到主分组
        easyGroupNode.add(arrayNode);
        easyGroupNode.add(sortNode);
        easyGroupNode.add(dataStructureNode);
        easyGroupNode.add(searchNode);
        easyGroupNode.add(dpNode);
        easyGroupNode.add(greedyNode);
        easyGroupNode.add(stringNode);
        easyGroupNode.add(bitNode);
        easyGroupNode.add(graphNode);
        easyGroupNode.add(mathNode);
        easyGroupNode.add(treeNode);
        
        normalGroupNode.add(dfsNode);
        normalGroupNode.add(bfsNode);
        normalGroupNode.add(normalNode);
        
        hardGroupNode.add(hardNode);
        
        // 将主分组添加到根节点
        rootNode.add(easyGroupNode);
        rootNode.add(normalGroupNode);
        rootNode.add(hardGroupNode);
        
        // 添加具体算法到分类节点
        addAlgorithmToCategory(arrayNode, "NO.001 两数之和", "Easy", "哈希表 + 双指针");
        addAlgorithmToCategory(arrayNode, "NO.026 删除有序数组中的重复项", "Easy", "双指针 + 原地删除");
        addAlgorithmToCategory(arrayNode, "NO.027 移除元素", "Easy", "双指针 + 原地删除");
        addAlgorithmToCategory(arrayNode, "NO.088 合并两个有序数组", "Easy", "双指针 + 原地合并");
        addAlgorithmToCategory(arrayNode, "NO.283 移动零", "Easy", "双指针 + 原地移动");
        addAlgorithmToCategory(arrayNode, "NO.977 有序数组的平方", "Easy", "双指针 + 平方比较");
        addAlgorithmToCategory(arrayNode, "NO.2553 分割数组中数字的数位", "Easy", "数组遍历 + 数位分解");
        addAlgorithmToCategory(arrayNode, "NO.2706 购买两块巧克力", "Easy", "贪心 + 最小值查找");
        addAlgorithmToCategory(arrayNode, "NO.2824 统计和小于目标的下标对数目", "Easy", "双重循环 + 条件统计");
        addAlgorithmToCategory(arrayNode, "NO.1051 高度检查器", "Easy", "排序 + 比较");
        addAlgorithmToCategory(arrayNode, "NO.2570 合并两个二维数组", "Easy", "双指针 + 有序合并");
        // 新增数组算法
        addAlgorithmToCategory(arrayNode, "NO.2529 正整数和负整数的最大计数", "Easy", "数组遍历 + 计数统计");
        addAlgorithmToCategory(arrayNode, "NO.2535 数组元素和与数字和的绝对差", "Easy", "数组遍历 + 数位分解");
        addAlgorithmToCategory(arrayNode, "NO.2540 最小公共值", "Easy", "双指针 + 有序数组");
        addAlgorithmToCategory(arrayNode, "NO.2562 找出数组的串联值", "Easy", "双指针 + 字符串拼接");
        addAlgorithmToCategory(arrayNode, "NO.2574 左右元素和的差值", "Easy", "前缀和 + 数组遍历");
        addAlgorithmToCategory(arrayNode, "NO.2586 统计范围内的元音字符串数", "Easy", "字符串遍历 + 条件判断");
        
        addAlgorithmToCategory(sortNode, "插入排序算法演示", "Basic", "插入排序 + 可视化");
        
        addAlgorithmToCategory(dataStructureNode, "NO.225 用队列实现栈", "Easy", "队列 + LIFO模拟");
        addAlgorithmToCategory(dataStructureNode, "NO.234 回文链表", "Easy", "快慢指针 + 栈");
        addAlgorithmToCategory(dataStructureNode, "NO.705 设计哈希集合", "Easy", "哈希表 + 链地址法");
        addAlgorithmToCategory(dataStructureNode, "NO.706 设计哈希映射", "Easy", "哈希表 + 线性探测");
        addAlgorithmToCategory(dataStructureNode, "NO.703 数据流中的第K大元素", "Easy", "最小堆 + 优先队列");
        
        addAlgorithmToCategory(searchNode, "NO.035 搜索插入位置", "Easy", "二分搜索 + 插入位置");
        addAlgorithmToCategory(searchNode, "NO.704 二分查找", "Easy", "二分搜索 + 有序数组");
        
        addAlgorithmToCategory(dpNode, "NO.118 杨辉三角", "Easy", "动态规划 + 组合数学");
        
        addAlgorithmToCategory(greedyNode, "NO.055 跳跃游戏", "Easy", "贪心 + 最远可达位置");
        addAlgorithmToCategory(greedyNode, "NO.055 跳跃游戏 (Normal)", "Normal", "贪心 + 最远可达位置 (详细版)");
        addAlgorithmToCategory(greedyNode, "NO.045 跳跃游戏 II", "Normal", "贪心 + 最少跳跃次数");
        addAlgorithmToCategory(greedyNode, "NO.2498 青蛙过河 II", "Normal", "贪心 + 最小跳跃距离");
        addAlgorithmToCategory(greedyNode, "NO.121 买卖股票的最佳时机", "Easy", "贪心 + 一次遍历");
        addAlgorithmToCategory(greedyNode, "NO.2558 从数量最多的堆取走礼物", "Easy", "贪心 + 最大堆");
        addAlgorithmToCategory(greedyNode, "NO.1005 K次取反后最大化数组和", "Easy", "贪心 + 排序");
        
        addAlgorithmToCategory(stringNode, "NO.1002 查找常用字符", "Easy", "字符统计 + 频次计算");
        
        addAlgorithmToCategory(bitNode, "NO.136 只出现一次的数字", "Easy", "位运算 + 异或运算");
        addAlgorithmToCategory(bitNode, "NO.190 颠倒二进制位", "Easy", "位运算 + 逐位处理");
        
        addAlgorithmToCategory(graphNode, "NO.463 岛屿的周长", "Easy", "网格遍历 + 边界计算");
        
        addAlgorithmToCategory(mathNode, "NO.009 回文数", "Easy", "数学运算 + 数位反转");
        addAlgorithmToCategory(mathNode, "NO.066 加一", "Easy", "数学运算 + 进位处理");
        addAlgorithmToCategory(mathNode, "NO.069 x的平方根", "Easy", "二分查找 + 数学运算");
        addAlgorithmToCategory(mathNode, "NO.2549 统计桌面上的不同数字", "Easy", "数学推理 + 模拟");
        
        addAlgorithmToCategory(treeNode, "NO.094 二叉树的中序遍历", "Easy", "树遍历 + 栈模拟");
        addAlgorithmToCategory(treeNode, "NO.100 相同的树", "Easy", "树比较 + 递归");
        addAlgorithmToCategory(treeNode, "NO.101 对称二叉树", "Easy", "树遍历 + 对称性检查");
        addAlgorithmToCategory(treeNode, "NO.104 二叉树的最大深度", "Easy", "树遍历 + 深度计算");
        addAlgorithmToCategory(treeNode, "NO.108 将有序数组转换为二叉搜索树", "Easy", "分治算法 + 平衡树构建");
        
        addAlgorithmToCategory(dfsNode, "NO.1306 跳跃游戏 III", "Normal", "深度优先搜索 + 递归回溯");
        
        addAlgorithmToCategory(bfsNode, "NO.1654 到家的最少跳跃次数", "Normal", "广度优先搜索 + 最短路径");
        
        // Normal算法
        addAlgorithmToCategory(normalNode, "NO.208 实现Trie(前缀树)", "Normal", "Trie + 字符串处理");
        addAlgorithmToCategory(normalNode, "NO.211 添加与搜索单词", "Normal", "Trie + 通配符搜索");
        addAlgorithmToCategory(normalNode, "NO.284 顶端迭代器", "Normal", "迭代器设计 + 缓存");
        addAlgorithmToCategory(normalNode, "NO.764 最大加号标志", "Normal", "动态规划 + 网格处理");
        addAlgorithmToCategory(normalNode, "NO.915 分割数组", "Normal", "数组分割 + 双指针");
        addAlgorithmToCategory(normalNode, "NO.005 最长回文子串", "Normal", "动态规划 + 中心扩展");
        addAlgorithmToCategory(normalNode, "NO.015 三数之和", "Normal", "双指针 + 排序");
        addAlgorithmToCategory(normalNode, "NO.053 最大子数组和", "Normal", "动态规划 + Kadane算法");
        addAlgorithmToCategory(normalNode, "NO.046 全排列", "Normal", "回溯算法 + 递归");
        addAlgorithmToCategory(normalNode, "NO.200 岛屿数量", "Normal", "深度优先搜索 + 网格遍历");
        addAlgorithmToCategory(normalNode, "NO.322 零钱兑换", "Normal", "动态规划 + 完全背包");
        addAlgorithmToCategory(normalNode, "NO.139 单词拆分", "Normal", "动态规划 + 字符串匹配");
        
        addAlgorithmToCategory(hardNode, "NO.403 青蛙过河", "Hard", "动态规划 + 状态转移");
    }
    
    /**
     * 添加算法到指定分类
     */
    private void addAlgorithmToCategory(DefaultMutableTreeNode categoryNode, String algorithmName, 
                                      String difficulty, String technique) {
        AlgorithmInfo info = new AlgorithmInfo(algorithmName, difficulty, technique);
        DefaultMutableTreeNode algorithmNode = new DefaultMutableTreeNode(info);
        categoryNode.add(algorithmNode);
        allAlgorithmNodes.add(algorithmNode);
    }
    
    /**
     * 初始化UI组件
     */
    private void initComponents() {
        // 创建搜索框
        searchField = new JTextField();
        searchField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(250, 30));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // 创建清除搜索按钮
        clearSearchButton = new JButton("✕");
        clearSearchButton.setFont(new Font("微软雅黑", Font.BOLD, 12));
        clearSearchButton.setPreferredSize(new Dimension(30, 30));
        clearSearchButton.setToolTipText("清除搜索");
        clearSearchButton.setEnabled(false);
        
        // 创建树组件
        algorithmTree = new JTree(rootNode);
        algorithmTree.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        algorithmTree.setRowHeight(25);
        algorithmTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        
        // 设置树的渲染器
        algorithmTree.setCellRenderer(new AlgorithmTreeCellRenderer());
        
        // 创建描述区域
        descriptionArea = new JTextArea();
        descriptionArea.setEditable(false);
        descriptionArea.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        descriptionArea.setBackground(new Color(248, 249, 250));
        descriptionArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        
        // 创建启动按钮
        startButton = new JButton("🚀 启动动画演示");
        startButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        startButton.setEnabled(false);
        startButton.setPreferredSize(new Dimension(150, 35));
        
        // 创建进程监控按钮
        processMonitorButton = new JButton("📊 进程监控");
        processMonitorButton.setFont(new Font("微软雅黑", Font.BOLD, 12));
        processMonitorButton.setPreferredSize(new Dimension(120, 35));
        processMonitorButton.setToolTipText("启动JVM进程监控工具");
        
        // 创建内存监控按钮
        memoryMonitorButton = new JButton("💾 内存监控");
        memoryMonitorButton.setFont(new Font("微软雅黑", Font.BOLD, 12));
        memoryMonitorButton.setPreferredSize(new Dimension(120, 35));
        memoryMonitorButton.setToolTipText("启动JVM内存监控工具");
        
        // 创建状态标签
        statusLabel = new JLabel("请选择一个算法查看详细信息");
        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        statusLabel.setForeground(Color.GRAY);
        
        // 设置默认描述
        setDefaultDescription();
    }
    
    /**
     * 设置布局
     */
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 创建主分割面板
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainSplitPane.setDividerLocation(350);
        mainSplitPane.setResizeWeight(0.35);
        
        // 左侧：算法树
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "算法分类目录", 
            TitledBorder.LEFT, TitledBorder.TOP, 
            new Font("微软雅黑", Font.BOLD, 14)));
        
        // 搜索面板
        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));
        
        JLabel searchLabel = new JLabel("🔍 搜索:");
        searchLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(clearSearchButton, BorderLayout.EAST);
        
        JScrollPane treeScrollPane = new JScrollPane(algorithmTree);
        treeScrollPane.setPreferredSize(new Dimension(350, 600));
        
        leftPanel.add(searchPanel, BorderLayout.NORTH);
        leftPanel.add(treeScrollPane, BorderLayout.CENTER);
        
        // 右侧：详细信息和控制
        JPanel rightPanel = new JPanel(new BorderLayout());
        
        // 描述区域
        JPanel descPanel = new JPanel(new BorderLayout());
        descPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "算法详细信息", 
            TitledBorder.LEFT, TitledBorder.TOP, 
            new Font("微软雅黑", Font.BOLD, 14)));
        
        JScrollPane descScrollPane = new JScrollPane(descriptionArea);
        descScrollPane.setPreferredSize(new Dimension(600, 500));
        descPanel.add(descScrollPane, BorderLayout.CENTER);
        
        // 控制面板
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        controlPanel.setBorder(BorderFactory.createEtchedBorder());
        controlPanel.add(startButton);
        controlPanel.add(processMonitorButton);
        controlPanel.add(memoryMonitorButton);
        
        rightPanel.add(descPanel, BorderLayout.CENTER);
        rightPanel.add(controlPanel, BorderLayout.SOUTH);
        
        mainSplitPane.setLeftComponent(leftPanel);
        mainSplitPane.setRightComponent(rightPanel);
        
        add(mainSplitPane, BorderLayout.CENTER);
        
        // 顶部标题
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        titlePanel.setBackground(new Color(240, 248, 255));
        
        JLabel titleLabel = new JLabel("🎯 LeetCode算法动画演示系统", JLabel.LEFT);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        titleLabel.setForeground(new Color(25, 118, 210));
        
        JLabel subtitleLabel = new JLabel("按算法类型分类，提供可视化动画演示", JLabel.LEFT);
        subtitleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        subtitleLabel.setForeground(Color.GRAY);
        
        JPanel titleTextPanel = new JPanel(new BorderLayout());
        titleTextPanel.setOpaque(false);
        titleTextPanel.add(titleLabel, BorderLayout.NORTH);
        titleTextPanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        titlePanel.add(titleTextPanel, BorderLayout.WEST);
        titlePanel.add(statusLabel, BorderLayout.EAST);
        
        add(titlePanel, BorderLayout.NORTH);
    }
    
    /**
     * 设置事件处理器
     */
    private void setupEventHandlers() {
        // 搜索框事件
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                performSearch();
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                performSearch();
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {
                performSearch();
            }
        });
        
        // 搜索框回车事件
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performSearch();
                }
            }
        });
        
        // 清除搜索按钮事件
        clearSearchButton.addActionListener(e -> {
            searchField.setText("");
            clearSearch();
        });
        
        // 树选择事件
        algorithmTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) algorithmTree.getLastSelectedPathComponent();
            
            if (selectedNode != null && selectedNode.getUserObject() instanceof AlgorithmInfo) {
                AlgorithmInfo info = (AlgorithmInfo) selectedNode.getUserObject();
                updateDescription(info.getName());
                startButton.setEnabled(true);
                statusLabel.setText("已选择: " + info.getName() + " [" + info.getDifficulty() + "]");
            } else {
                setDefaultDescription();
                startButton.setEnabled(false);
                statusLabel.setText("请选择一个具体的算法");
            }
        });
        
        // 树双击事件
        algorithmTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) algorithmTree.getLastSelectedPathComponent();
                    if (selectedNode != null && selectedNode.getUserObject() instanceof AlgorithmInfo) {
                        startAnimation();
                    }
                }
            }
        });
        
        // 启动按钮事件
        startButton.addActionListener(e -> startAnimation());
        
        // 进程监控按钮事件
        processMonitorButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                try {
                    JVMProcessMonitor monitor = new JVMProcessMonitor();
                    monitor.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, 
                        "启动进程监控工具失败: " + ex.getMessage(), 
                        "错误", JOptionPane.ERROR_MESSAGE);
                }
            });
        });
        
        // 内存监控按钮事件
        memoryMonitorButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                try {
                    JVMMemoryMonitor monitor = new JVMMemoryMonitor();
                    monitor.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, 
                        "启动内存监控工具失败: " + ex.getMessage(), 
                        "错误", JOptionPane.ERROR_MESSAGE);
                }
            });
        });
    }
    
    /**
     * 展开所有树节点
     */
    private void expandAllNodes() {
        for (int i = 0; i < algorithmTree.getRowCount(); i++) {
            algorithmTree.expandRow(i);
        }
    }
    
    /**
     * 设置默认描述
     */
    private void setDefaultDescription() {
        descriptionArea.setText(
            "🎯 欢迎使用LeetCode算法动画演示系统！\n\n" +
            "📚 系统特色：\n" +
            "• 按算法类型科学分类，层次清晰\n" +
            "• 提供可视化动画演示，直观易懂\n" +
            "• 支持单步调试和完整演示\n" +
            "• 涵盖多种经典算法和数据结构\n\n" +
            "🔍 使用方法：\n" +
            "1. 从左侧树形目录中选择感兴趣的算法分类\n" +
            "2. 点击具体的算法名称查看详细信息\n" +
            "3. 点击\"启动动画演示\"按钮开始可视化学习\n\n" +
            "📊 当前包含的算法分类：\n" +
            "• 数组算法 - 基础数组操作和双指针技巧\n" +
            "• 排序算法 - 经典排序算法可视化\n" +
            "• 数据结构设计 - 栈、队列、哈希表等\n" +
            "• 搜索算法 - 二分查找等高效搜索\n" +
            "• 动态规划 - DP思想和状态转移\n" +
            "• 贪心算法 - 局部最优到全局最优\n" +
            "• 字符串算法 - 字符处理和模式匹配\n" +
            "• 位运算 - 高效的位操作技巧\n" +
            "• 图论算法 - 图的遍历和基础算法\n\n" +
            "💡 提示：每个算法都包含详细的问题描述、核心算法思路和动画特色说明。"
        );
        descriptionArea.setCaretPosition(0);
    }
    
    /**
     * 更新算法描述
     */
    private void updateDescription(String algorithmName) {
        String description = getAlgorithmDescription(algorithmName);
        descriptionArea.setText(description);
        descriptionArea.setCaretPosition(0);
    }
    
    /**
     * 获取算法详细描述
     */
    private String getAlgorithmDescription(String algorithmName) {
        // 这里复用原来的描述方法，但格式化更好
        switch (algorithmName) {
            case "NO.001 两数之和":
                return "🎯 【NO.001 两数之和】\n\n" +
                       "📝 问题描述：\n" +
                       "给定一个整数数组nums和一个整数目标值target，请你在该数组中找出和为目标值target的那两个整数，并返回它们的数组下标。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 使用HashMap存储已遍历的数值和索引\n" +
                       "• 对于每个元素，计算target - nums[i]\n" +
                       "• 检查差值是否在HashMap中存在\n" +
                       "• 如果存在则返回两个索引，否则将当前元素加入HashMap\n\n" +
                       "⏰ 时间复杂度：O(n)\n" +
                       "💾 空间复杂度：O(n)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 可视化数组遍历过程\n" +
                       "• 显示HashMap的查找和插入操作\n" +
                       "• 高亮显示匹配的两个数字\n" +
                       "• 支持自定义数组和目标值\n\n" +
                       "💡 算法技巧：哈希表 + 一次遍历";

            case "NO.088 合并两个有序数组":
                return "🎯 【NO.088 合并两个有序数组】\n\n" +
                       "📝 问题描述：\n" +
                       "给你两个按非递减顺序排列的整数数组nums1和nums2，另有两个整数m和n，分别表示nums1和nums2中元素的数量。请你合并nums2到nums1中，使合并后的数组同样按非递减顺序排列。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 使用双指针从数组末尾开始比较\n" +
                       "• 将较大的元素放到nums1的末尾\n" +
                       "• 避免额外空间，直接在nums1中操作\n" +
                       "• 处理剩余元素\n\n" +
                       "⏰ 时间复杂度：O(m+n)\n" +
                       "💾 空间复杂度：O(1)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 可视化双指针的移动过程\n" +
                       "• 显示元素比较和移动\n" +
                       "• 高亮显示当前操作的元素\n" +
                       "• 支持单步执行和自动演示\n\n" +
                       "💡 算法技巧：双指针 + 逆向合并";

            case "NO.977 有序数组的平方":
                return "🎯 【NO.977 有序数组的平方】\n\n" +
                       "📝 问题描述：\n" +
                       "给你一个按非递减顺序排序的整数数组nums，返回每个数字的平方组成的新数组，要求也按非递减顺序排序。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 使用双指针技术，分别指向数组首尾\n" +
                       "• 比较两端元素平方的大小\n" +
                       "• 将较大的平方值放入结果数组的末尾\n" +
                       "• 移动对应指针，重复直到完成\n\n" +
                       "⏰ 时间复杂度：O(n)\n" +
                       "💾 空间复杂度：O(n)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 可视化双指针的移动过程\n" +
                       "• 显示平方计算和比较\n" +
                       "• 高亮显示当前处理的元素\n" +
                       "• 实时显示结果数组的构建\n\n" +
                       "💡 算法技巧：双指针 + 平方比较";

            case "NO.704 二分查找":
                return "🎯 【NO.704 二分查找】\n\n" +
                       "📝 问题描述：\n" +
                       "给定一个n个元素有序的（升序）整型数组nums和一个目标值target，写一个函数搜索nums中的target，如果目标值存在返回下标，否则返回-1。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 维护左右边界left和right\n" +
                       "• 计算中点mid = (left + right) / 2\n" +
                       "• 比较nums[mid]与target的大小关系\n" +
                       "• 根据比较结果调整搜索范围\n\n" +
                       "⏰ 时间复杂度：O(log n)\n" +
                       "💾 空间复杂度：O(1)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 可视化搜索范围的缩小过程\n" +
                       "• 高亮显示left、right、mid指针\n" +
                       "• 显示每步比较的详细信息\n" +
                       "• 支持自定义数组和目标值\n\n" +
                       "💡 算法技巧：二分搜索 + 边界控制";

            case "NO.118 杨辉三角":
                return "🎯 【NO.118 杨辉三角】\n\n" +
                       "📝 问题描述：\n" +
                       "给定一个非负整数numRows，生成杨辉三角的前numRows行。在杨辉三角中，每个数是它左上方和右上方的数的和。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 第一行和每行的首尾元素都是1\n" +
                       "• 其他元素等于上一行对应位置两个数的和\n" +
                       "• 使用动态规划逐行构建\n" +
                       "• triangle[i][j] = triangle[i-1][j-1] + triangle[i-1][j]\n\n" +
                       "⏰ 时间复杂度：O(n²)\n" +
                       "💾 空间复杂度：O(n²)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 逐行生成杨辉三角\n" +
                       "• 可视化数字计算过程\n" +
                       "• 高亮显示计算路径\n" +
                       "• 支持自定义行数\n\n" +
                       "💡 算法技巧：动态规划 + 组合数学";

            case "NO.225 用队列实现栈":
                return "🎯 【NO.225 用队列实现栈】\n\n" +
                       "📝 问题描述：\n" +
                       "请你仅使用两个队列实现一个后入先出（LIFO）的栈，并支持普通栈的全部四种操作（push、pop、top、empty）。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 使用两个队列 queue1 和 queue2\n" +
                       "• Push操作：将新元素加入queue2，然后将queue1的所有元素移到queue2，最后交换两个队列\n" +
                       "• Pop操作：直接从queue1中取出元素\n" +
                       "• Top操作：查看queue1的队首元素\n\n" +
                       "⏰ 时间复杂度：Push O(n), Pop/Top O(1)\n" +
                       "💾 空间复杂度：O(n)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 可视化两个队列的状态变化\n" +
                       "• 显示栈的概念视图\n" +
                       "• 实时显示队列内容\n" +
                       "• 支持交互式操作\n\n" +
                       "💡 算法技巧：队列模拟 + LIFO实现";

            case "NO.234 回文链表":
                return "🎯 【NO.234 回文链表】\n\n" +
                       "📝 问题描述：\n" +
                       "给你一个单链表的头节点head，请你判断该链表是否为回文链表。如果是，返回true；否则，返回false。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 使用快慢指针找到链表中点\n" +
                       "• 快指针每次走两步，慢指针每次走一步\n" +
                       "• 将前半部分节点值压入栈中\n" +
                       "• 比较栈中元素与后半部分链表节点值\n" +
                       "• 如果全部相等则为回文链表\n\n" +
                       "⏰ 时间复杂度：O(n)\n" +
                       "💾 空间复杂度：O(n)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 可视化快慢指针的移动过程\n" +
                       "• 显示栈的压入和弹出操作\n" +
                       "• 高亮显示比较过程\n" +
                       "• 支持自定义链表输入\n" +
                       "• 实时显示判断结果\n\n" +
                       "💡 算法技巧：快慢指针 + 栈辅助";

            case "NO.026 删除有序数组中的重复项":
                return "🎯 【NO.026 删除有序数组中的重复项】\n\n" +
                       "📝 问题描述：\n" +
                       "给你一个升序排列的数组nums，请你原地删除重复出现的元素，使每个元素只出现一次，返回删除后数组的新长度。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 使用双指针技术，slow和fast指针\n" +
                       "• fast指针遍历整个数组\n" +
                       "• 当nums[fast] != nums[slow]时，将nums[fast]复制到nums[slow+1]\n" +
                       "• slow指针向前移动，继续处理下一个不重复元素\n\n" +
                       "⏰ 时间复杂度：O(n)\n" +
                       "💾 空间复杂度：O(1)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 可视化双指针的移动过程\n" +
                       "• 高亮显示重复和非重复元素\n" +
                       "• 实时显示数组的变化\n" +
                       "• 支持自定义数组输入\n\n" +
                       "💡 算法技巧：双指针 + 原地删除";

            case "NO.121 买卖股票的最佳时机":
                return "🎯 【NO.121 买卖股票的最佳时机】\n\n" +
                       "📝 问题描述：\n" +
                       "给定一个数组prices，它的第i个元素prices[i]表示一支给定股票第i天的价格。你只能选择某一天买入这只股票，并选择在未来的某一天卖出该股票。设计一个算法来计算你所能获取的最大利润。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 维护一个最小价格minPrice\n" +
                       "• 遍历价格数组，更新最小价格\n" +
                       "• 计算当前价格与最小价格的差值（利润）\n" +
                       "• 维护最大利润maxProfit\n\n" +
                       "⏰ 时间复杂度：O(n)\n" +
                       "💾 空间复杂度：O(1)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 可视化股票价格走势图\n" +
                       "• 高亮显示最佳买入和卖出时机\n" +
                       "• 实时显示当前最大利润\n" +
                       "• 支持自定义价格数据\n\n" +
                       "💡 算法技巧：贪心算法 + 一次遍历";

            case "NO.136 只出现一次的数字":
                return "🎯 【NO.136 只出现一次的数字】\n\n" +
                       "📝 问题描述：\n" +
                       "给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 利用异或运算的性质：a ⊕ a = 0，a ⊕ 0 = a\n" +
                       "• 相同的数字异或结果为0\n" +
                       "• 任何数字与0异或都等于它本身\n" +
                       "• 遍历数组，将所有数字进行异或运算\n\n" +
                       "⏰ 时间复杂度：O(n)\n" +
                       "💾 空间复杂度：O(1)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 可视化异或运算过程\n" +
                       "• 显示二进制位的变化\n" +
                       "• 高亮显示相同数字的抵消过程\n" +
                       "• 支持自定义数组输入\n\n" +
                       "💡 算法技巧：位运算 + 异或性质";

            case "NO.283 移动零":
                return "🎯 【NO.283 移动零】\n\n" +
                       "📝 问题描述：\n" +
                       "给定一个数组nums，编写一个函数将所有0移动到数组的末尾，同时保持非零元素的相对顺序。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 使用双指针技术，left指向下一个非零元素的位置\n" +
                       "• right指针遍历整个数组\n" +
                       "• 当nums[right] != 0时，交换nums[left]和nums[right]\n" +
                       "• left指针向前移动\n\n" +
                       "⏰ 时间复杂度：O(n)\n" +
                       "💾 空间复杂度：O(1)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 可视化双指针的移动过程\n" +
                       "• 高亮显示零和非零元素\n" +
                       "• 实时显示元素交换过程\n" +
                       "• 支持自定义数组输入\n\n" +
                       "💡 算法技巧：双指针 + 原地移动";

            case "插入排序算法演示":
                return "🎯 【插入排序算法演示】\n\n" +
                       "📝 问题描述：\n" +
                       "插入排序是一种简单直观的排序算法。它的工作原理是通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 从第二个元素开始，将当前元素作为key\n" +
                       "• 将key与前面已排序的元素逐一比较\n" +
                       "• 将大于key的元素向后移动一位\n" +
                       "• 将key插入到正确位置\n" +
                       "• 重复以上步骤直到所有元素排序完成\n\n" +
                       "⏰ 时间复杂度：O(n²) 平均和最坏，O(n) 最好\n" +
                       "💾 空间复杂度：O(1)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 柱状图可视化数组元素\n" +
                       "• 颜色区分已排序和未排序部分\n" +
                       "• 逐步展示插入过程\n" +
                       "• 支持自定义数据输入\n" +
                       "• 自动演示排序全过程\n\n" +
                       "💡 算法技巧：插入排序 + 原地排序";

            // Normal算法 - donnot目录
            case "NO.208 实现Trie(前缀树)":
                return "🎯 【NO.208 实现Trie(前缀树)】\n\n" +
                       "📝 问题描述：\n" +
                       "Trie（发音类似 \"try\"）或者说前缀树是一种树形数据结构，用于高效地存储和检索字符串数据集中的键。这一数据结构有相当多的应用情景，例如自动补完和拼写检查。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 每个节点包含一个字符和子节点映射\n" +
                       "• 插入：逐字符遍历，创建不存在的节点\n" +
                       "• 搜索：逐字符遍历，检查路径是否存在\n" +
                       "• 前缀匹配：检查是否存在以给定前缀开头的单词\n\n" +
                       "⏰ 时间复杂度：O(m) - m为字符串长度\n" +
                       "💾 空间复杂度：O(ALPHABET_SIZE * N * M)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 可视化Trie树结构\n" +
                       "• 显示插入和搜索过程\n" +
                       "• 高亮显示遍历路径\n" +
                       "• 支持自定义单词输入\n\n" +
                       "💡 算法技巧：Trie + 字符串处理";

            case "NO.211 添加与搜索单词":
                return "🎯 【NO.211 添加与搜索单词】\n\n" +
                       "📝 问题描述：\n" +
                       "请你设计一个数据结构，支持添加新单词和查找字符串是否与任何先前添加的字符串匹配。查找可以包含文字字符a-z或者通配符'.'，其中'.'可以表示任何一个字母。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 基于Trie树实现\n" +
                       "• 添加单词：标准Trie插入操作\n" +
                       "• 搜索单词：支持'.'通配符的DFS搜索\n" +
                       "• 遇到'.'时，尝试所有可能的子节点\n\n" +
                       "⏰ 时间复杂度：添加O(m)，搜索O(n*26^m)\n" +
                       "💾 空间复杂度：O(ALPHABET_SIZE * N * M)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 可视化Trie树和通配符搜索\n" +
                       "• 显示DFS搜索过程\n" +
                       "• 高亮显示匹配路径\n" +
                       "• 支持通配符模式输入\n\n" +
                       "💡 算法技巧：Trie + DFS + 通配符匹配";

            case "NO.284 顶端迭代器":
                return "🎯 【NO.284 顶端迭代器】\n\n" +
                       "📝 问题描述：\n" +
                       "给定一个迭代器类的接口，接口包含两个方法：next()和hasNext()。设计并实现一个支持peek()操作的顶端迭代器——其本质就是把原本应由next()方法返回的元素peek()出来。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 使用缓存机制存储下一个元素\n" +
                       "• peek()：返回缓存的元素，不移动迭代器\n" +
                       "• next()：返回并移除缓存的元素\n" +
                       "• hasNext()：检查是否还有下一个元素\n\n" +
                       "⏰ 时间复杂度：O(1)\n" +
                       "💾 空间复杂度：O(1)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 可视化迭代器状态\n" +
                       "• 显示缓存机制\n" +
                       "• 高亮显示当前位置\n" +
                       "• 支持交互式操作\n\n" +
                       "💡 算法技巧：迭代器设计 + 缓存机制";

            case "NO.764 最大加号标志":
                return "🎯 【NO.764 最大加号标志】\n\n" +
                       "📝 问题描述：\n" +
                       "在一个大小在(0, 10000)的由1和0组成的二维网格grid中，每个1标记了一个陆地。现在给定一个整数数组mines，其中mines[i] = [xi, yi]表示第i个地雷的位置。返回网格中包含1的最大的轴对齐加号标志的阶数。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 对每个位置计算四个方向的连续1的长度\n" +
                       "• 使用动态规划预处理每个方向\n" +
                       "• 加号的阶数为四个方向最小值\n" +
                       "• 处理地雷位置，将对应位置设为0\n\n" +
                       "⏰ 时间复杂度：O(N²)\n" +
                       "💾 空间复杂度：O(N²)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 可视化网格和地雷位置\n" +
                       "• 显示四个方向的计算过程\n" +
                       "• 高亮显示最大加号\n" +
                       "• 支持自定义网格大小\n\n" +
                       "💡 算法技巧：动态规划 + 四方向扫描";

            case "NO.915 分割数组":
                return "🎯 【NO.915 分割数组】\n\n" +
                       "📝 问题描述：\n" +
                       "给定一个数组nums，将其划分为两个连续的子数组left和right，使得：left中的每个元素都小于或等于right中的每个元素。left和right都是非空的。left要尽可能小。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 计算左侧最大值数组leftMax\n" +
                       "• 计算右侧最小值数组rightMin\n" +
                       "• 找到满足leftMax[i] <= rightMin[i+1]的最小i\n" +
                       "• 返回分割点i+1\n\n" +
                       "⏰ 时间复杂度：O(n)\n" +
                       "💾 空间复杂度：O(n)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 可视化数组分割过程\n" +
                       "• 显示leftMax和rightMin数组\n" +
                       "• 高亮显示分割点\n" +
                       "• 支持自定义数组输入\n\n" +
                       "💡 算法技巧：数组分割 + 双向扫描";

            case "NO.005 最长回文子串":
                return "🎯 【NO.005 最长回文子串】\n\n" +
                       "📝 问题描述：\n" +
                       "给你一个字符串s，找到s中最长的回文子串。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 中心扩展法：以每个字符为中心向两边扩展\n" +
                       "• 考虑奇数长度和偶数长度的回文串\n" +
                       "• 对于每个中心，尽可能向外扩展\n" +
                       "• 记录最长回文串的起始位置和长度\n\n" +
                       "⏰ 时间复杂度：O(n²)\n" +
                       "💾 空间复杂度：O(1)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 可视化中心扩展过程\n" +
                       "• 高亮显示当前检查的回文串\n" +
                       "• 显示奇数和偶数长度的处理\n" +
                       "• 支持自定义字符串输入\n\n" +
                       "💡 算法技巧：中心扩展 + 双指针";

            case "NO.015 三数之和":
                return "🎯 【NO.015 三数之和】\n\n" +
                       "📝 问题描述：\n" +
                       "给你一个包含n个整数的数组nums，判断nums中是否存在三个元素a,b,c，使得a+b+c=0？请你找出所有和为0且不重复的三元组。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 首先对数组进行排序\n" +
                       "• 固定第一个数，用双指针寻找另外两个数\n" +
                       "• 跳过重复元素避免重复解\n" +
                       "• 根据三数之和调整左右指针\n\n" +
                       "⏰ 时间复杂度：O(n²)\n" +
                       "💾 空间复杂度：O(1)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 可视化排序和双指针移动\n" +
                       "• 高亮显示当前三元组\n" +
                       "• 显示重复元素跳过过程\n" +
                       "• 支持自定义数组输入\n\n" +
                       "💡 算法技巧：排序 + 双指针";

            case "NO.053 最大子数组和":
                return "🎯 【NO.053 最大子数组和】\n\n" +
                       "📝 问题描述：\n" +
                       "给你一个整数数组nums，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。\n\n" +
                       "🔧 核心算法：\n" +
                       "• Kadane算法：动态规划的经典应用\n" +
                       "• dp[i] = max(dp[i-1] + nums[i], nums[i])\n" +
                       "• 如果前面的和为负数，则重新开始\n" +
                       "• 记录过程中的最大值\n\n" +
                       "⏰ 时间复杂度：O(n)\n" +
                       "💾 空间复杂度：O(1)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 可视化动态规划过程\n" +
                       "• 高亮显示最大子数组\n" +
                       "• 显示状态转移方程\n" +
                       "• 支持自定义数组输入\n\n" +
                       "💡 算法技巧：动态规划 + Kadane算法";

            case "NO.046 全排列":
                return "🎯 【NO.046 全排列】\n\n" +
                       "📝 问题描述：\n" +
                       "给定一个不含重复数字的数组nums，返回其所有可能的全排列。你可以按任意顺序返回答案。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 回溯算法：深度优先搜索 + 状态回退\n" +
                       "• 使用visited数组标记已使用的元素\n" +
                       "• 递归构建排列，到达叶子节点时记录结果\n" +
                       "• 回溯时撤销选择，尝试其他可能\n\n" +
                       "⏰ 时间复杂度：O(n! × n)\n" +
                       "💾 空间复杂度：O(n)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 可视化递归树的构建过程\n" +
                       "• 高亮显示当前路径和回溯过程\n" +
                       "• 显示visited数组的状态变化\n" +
                       "• 支持自定义数组输入\n\n" +
                       "💡 算法技巧：回溯算法 + 递归";

            case "NO.200 岛屿数量":
                return "🎯 【NO.200 岛屿数量】\n\n" +
                       "📝 问题描述：\n" +
                       "给你一个由'1'（陆地）和'0'（水）组成的的二维网格，请你计算网格中岛屿的数量。岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 深度优先搜索（DFS）遍历网格\n" +
                       "• 遇到'1'时开始DFS，将连通的'1'标记为已访问\n" +
                       "• 每次DFS完成代表找到一个岛屿\n" +
                       "• 四个方向递归搜索相邻的陆地\n\n" +
                       "⏰ 时间复杂度：O(m × n)\n" +
                       "💾 空间复杂度：O(m × n)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 可视化网格和DFS搜索过程\n" +
                       "• 高亮显示当前搜索的岛屿\n" +
                       "• 显示已访问区域的标记\n" +
                       "• 支持自定义网格输入\n\n" +
                       "💡 算法技巧：深度优先搜索 + 网格遍历";

            case "NO.322 零钱兑换":
                return "🎯 【NO.322 零钱兑换】\n\n" +
                       "📝 问题描述：\n" +
                       "给你一个整数数组coins，表示不同面额的硬币；以及一个整数amount，表示总金额。计算并返回可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回-1。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 动态规划：完全背包问题的变种\n" +
                       "• dp[i] = min(dp[i], dp[i-coin] + 1)\n" +
                       "• 对每个金额计算最少硬币数\n" +
                       "• 遍历所有硬币面额进行状态转移\n\n" +
                       "⏰ 时间复杂度：O(amount × coins.length)\n" +
                       "💾 空间复杂度：O(amount)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 可视化DP表的填充过程\n" +
                       "• 高亮显示状态转移方程\n" +
                       "• 显示硬币选择的决策过程\n" +
                       "• 支持自定义硬币和金额\n\n" +
                       "💡 算法技巧：动态规划 + 完全背包";

            case "NO.139 单词拆分":
                return "🎯 【NO.139 单词拆分】\n\n" +
                       "📝 问题描述：\n" +
                       "给你一个字符串s和一个字符串列表wordDict作为字典。请你判断是否可以利用字典中出现的单词拼接出s。注意：不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 动态规划：dp[i]表示s[0...i-1]是否可以拆分\n" +
                       "• 状态转移：dp[i] = dp[j] && s[j...i-1] in wordDict\n" +
                       "• 遍历所有可能的分割点\n" +
                       "• 检查子串是否在字典中\n\n" +
                       "⏰ 时间复杂度：O(n² × m)\n" +
                       "💾 空间复杂度：O(n)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 可视化字符串拆分过程\n" +
                       "• 高亮显示当前检查的子串\n" +
                       "• 显示DP状态的更新\n" +
                       "• 支持自定义字符串和字典\n\n" +
                       "💡 算法技巧：动态规划 + 字符串匹配";

            // 继续添加其他算法的描述...
            default:
                return "🔍 算法描述加载中...\n\n请稍候，正在为您准备详细的算法说明。";
        }
    }
    
    /**
     * 算法信息类
     */
    private static class AlgorithmInfo {
        private String name;
        private String difficulty;
        private String technique;
        
        public AlgorithmInfo(String name, String difficulty, String technique) {
            this.name = name;
            this.difficulty = difficulty;
            this.technique = technique;
        }
        
        public String getName() { return name; }
        public String getDifficulty() { return difficulty; }
        public String getTechnique() { return technique; }
        
        @Override
        public String toString() {
            return name;
        }
    }
    
    /**
     * 自定义树节点渲染器
     */
    private static class AlgorithmTreeCellRenderer extends DefaultTreeCellRenderer {
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            Object userObject = node.getUserObject();
            
            if (userObject instanceof AlgorithmInfo) {
                AlgorithmInfo info = (AlgorithmInfo) userObject;
                setText(info.getName());
                
                // 根据难度设置不同的图标和颜色
                switch (info.getDifficulty()) {
                    case "Easy":
                        setIcon(createColorIcon(new Color(76, 175, 80))); // 绿色
                        break;
                    case "Medium":
                        setIcon(createColorIcon(new Color(255, 193, 7))); // 黄色
                        break;
                    case "Hard":
                        setIcon(createColorIcon(new Color(244, 67, 54))); // 红色
                        break;
                    default:
                        setIcon(createColorIcon(new Color(96, 125, 139))); // 灰色
                        break;
                }
            }
            
            return this;
        }
        
        private Icon createColorIcon(Color color) {
            return new Icon() {
                @Override
                public void paintIcon(Component c, Graphics g, int x, int y) {
                    g.setColor(color);
                    g.fillOval(x, y, getIconWidth(), getIconHeight());
                    g.setColor(color.darker());
                    g.drawOval(x, y, getIconWidth(), getIconHeight());
                }
                
                @Override
                public int getIconWidth() { return 8; }
                
                @Override
                public int getIconHeight() { return 8; }
            };
        }
    }
    
    /**
     * 执行搜索功能
     */
    private void performSearch() {
        String searchText = searchField.getText().trim().toLowerCase();
        
        if (searchText.isEmpty()) {
            // 如果搜索框为空，显示完整的树
            algorithmTree.setModel(new DefaultTreeModel(rootNode));
            expandAllNodes();
            return;
        }
        
        // 创建过滤后的根节点
        filteredRootNode = new DefaultMutableTreeNode("算法分类");
        
        // 遍历所有算法节点，查找匹配的算法
        for (DefaultMutableTreeNode algorithmNode : allAlgorithmNodes) {
            AlgorithmInfo info = (AlgorithmInfo) algorithmNode.getUserObject();
            String algorithmName = info.getName().toLowerCase();
            String technique = info.getTechnique().toLowerCase();
            
            // 检查算法名称或技术标签是否包含搜索关键字
            if (algorithmName.contains(searchText) || technique.contains(searchText)) {
                // 找到匹配的算法，需要重建其父级结构
                DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) algorithmNode.getParent();
                String categoryName = parentNode.getUserObject().toString();
                
                // 在过滤结果中查找或创建对应的分类节点
                DefaultMutableTreeNode filteredCategoryNode = findOrCreateCategoryNode(filteredRootNode, categoryName);
                
                // 创建算法节点的副本并添加到过滤结果中
                DefaultMutableTreeNode algorithmCopy = new DefaultMutableTreeNode(info);
                filteredCategoryNode.add(algorithmCopy);
            }
        }
        
        // 更新树模型
        algorithmTree.setModel(new DefaultTreeModel(filteredRootNode));
        expandAllNodes();
        
        // 更新状态标签
        int matchCount = countAlgorithmNodes(filteredRootNode);
        statusLabel.setText("搜索到 " + matchCount + " 个匹配的算法");
    }
    
    /**
     * 在指定根节点中查找或创建分类节点
     */
    private DefaultMutableTreeNode findOrCreateCategoryNode(DefaultMutableTreeNode root, String categoryName) {
        // 遍历根节点的子节点，查找是否已存在该分类
        for (int i = 0; i < root.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) root.getChildAt(i);
            if (child.getUserObject().toString().equals(categoryName)) {
                return child;
            }
        }
        
        // 如果不存在，创建新的分类节点
        DefaultMutableTreeNode categoryNode = new DefaultMutableTreeNode(categoryName);
        root.add(categoryNode);
        return categoryNode;
    }
    
    /**
     * 统计树中算法节点的数量
     */
    private int countAlgorithmNodes(DefaultMutableTreeNode node) {
        int count = 0;
        
        if (node.getUserObject() instanceof AlgorithmInfo) {
            count = 1;
        }
        
        for (int i = 0; i < node.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
            count += countAlgorithmNodes(child);
        }
        
        return count;
    }
    
    /**
     * 清除搜索
     */
    private void clearSearch() {
        searchField.setText("");
        algorithmTree.setModel(new DefaultTreeModel(rootNode));
        expandAllNodes();
        statusLabel.setText("就绪");
    }
    
    /**
     * 启动算法动画
     */
    private void startAnimation() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) algorithmTree.getLastSelectedPathComponent();
        
        if (selectedNode == null || !(selectedNode.getUserObject() instanceof AlgorithmInfo)) {
            JOptionPane.showMessageDialog(this, 
                "请先选择一个具体的算法！", 
                "提示", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        AlgorithmInfo selectedAlgorithm = (AlgorithmInfo) selectedNode.getUserObject();
        String algorithmName = selectedAlgorithm.getName();
        
        // 检查算法是否存在
        if (!animations.containsKey(algorithmName)) {
            JOptionPane.showMessageDialog(this, 
                "算法 \"" + algorithmName + "\" 的动画实现未找到！", 
                "错误", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            statusLabel.setText("正在启动算法动画：" + algorithmName);
            
            // 在新线程中启动动画，避免阻塞UI
            new Thread(() -> {
                try {
                    Runnable animation = animations.get(algorithmName);
                    
                    // 隐藏主窗口
                    SwingUtilities.invokeLater(() -> {
                        AlgorithmTreeLauncher.this.setVisible(false);
                    });
                    
                    // 运行动画
                    animation.run();
                    
                    // 动画结束后显示主窗口
                    SwingUtilities.invokeLater(() -> {
                        AlgorithmTreeLauncher.this.setVisible(true);
                        AlgorithmTreeLauncher.this.toFront();
                        AlgorithmTreeLauncher.this.requestFocus();
                        statusLabel.setText("算法动画已结束：" + algorithmName);
                    });
                    
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> {
                        statusLabel.setText("启动失败：" + ex.getMessage());
                        JOptionPane.showMessageDialog(AlgorithmTreeLauncher.this, 
                            "启动算法动画时发生错误：\n" + ex.getMessage(), 
                            "错误", 
                            JOptionPane.ERROR_MESSAGE);
                        // 确保主窗口可见
                        AlgorithmTreeLauncher.this.setVisible(true);
                        AlgorithmTreeLauncher.this.toFront();
                    });
                    ex.printStackTrace();
                }
            }).start();
            
        } catch (Exception e) {
            statusLabel.setText("启动失败：" + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "启动算法动画时发生错误：\n" + e.getMessage(), 
                "错误", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * 主方法
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // 设置系统外观
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                
                // 设置一些UI属性
                UIManager.put("Tree.paintLines", true);
                UIManager.put("Tree.lineTypeDashed", true);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // 显示树形启动器（使用单例）
            showMainWindow();
        });
    }
}