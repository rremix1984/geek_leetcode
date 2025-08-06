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
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.border.EmptyBorder;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

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

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

// 字符串算法导入
import com.animation.string.*;

// 位运算导入
import com.animation.bit.*;

// 图论算法导入
import com.animation.graph.*;

// 数学算法导入
import com.animation.math.*;
import com.animation.easy.*;

// 树算法导入
import com.animation.tree.*;
import com.animation.normal.*;
import com.animation.atomics.*;
import com.animation.interval.*;
import com.animation.lonch.*;
import com.animation.tree.BPlusTreeAnimation;

// 困难算法导入
import com.animation.hard.NO403_H_FrogJump_Animation;
import com.animation.hard.NO51_H_NQueens_Animation;
import com.animation.hard.NO52_H_NQueensII_Animation;
import com.animation.hard.NO85_H_MaximalRectangle_Animation;
import com.animation.hard.NO023_H_MergeKSortedLists_Animation;
import com.animation.hard.NO126_H_WordLadderII_Animation;
import com.animation.hard.NO127_H_WordLadder_Animation;
import com.animation.hard.NO297_H_SerializeAndDeserializeBinaryTree_Animation;
import com.animation.hard.NO312_H_BurstBalloons_Animation;
import com.animation.hard.NO773_H_SlidingPuzzle_Animation;
import com.animation.hard.NO834_H_SumOfDistancesInTree_Animation;
import com.animation.tree.*;

// 导入JVM监控工具
import com.leetcode.tools.JVMProcessMonitor;
import com.leetcode.tools.JVMMemoryMonitor;
import com.animation.utils.CommandUtils;
import com.animation.utils.ProcessUtils;
import com.animation.utils.ThreadUtils;

/**
 * 算法动画演示树形启动器
 * 按算法类型分类展示，提供更好的层次结构和用户体验
 * 使用单例模式确保只有一个主窗口实例
 * 
 * @author 开发工程师
 * @version 1.0
 */
import java.util.logging.Logger;

public class AlgorithmTreeLauncher extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(AlgorithmTreeLauncher.class.getName());
    private static AlgorithmTreeLauncher instance;
    
    private Map<String, Runnable> animations;
    private JTree algorithmTree;
    private JTextArea descriptionArea;
    private JButton startButton;
    private JButton processMonitorButton;
    private JButton memoryMonitorButton;
    // 移除统计按钮，改为在标题栏显示饼图
    private StatsPieChartPanel statsPieChartPanel;
    private JLabel statusLabel;
    private DefaultMutableTreeNode rootNode;
    private DefaultMutableTreeNode filteredRootNode;
    private JTextField searchField;
    private JButton clearSearchButton;
    private List<DefaultMutableTreeNode> allAlgorithmNodes;
    
    // 最近访问功能
    private RecentAlgorithmManager recentAlgorithmManager;
    private JList<String> recentList;
    private DefaultListModel<String> recentListModel;
    private JPanel recentPanel;
    
    // 树操作按钮
    private JButton expandAllButton;
    private JButton collapseAllButton;
    
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
    private DefaultMutableTreeNode lonchNode;
    private DefaultMutableTreeNode atomicIntegerNode, atomicReferenceNode, longAdderNode, atomicArrayNode, atomicStampedNode, atomicMarkableNode;
    private DefaultMutableTreeNode intervalTreeNode, intervalBacktrackNode, intervalOtherNode;
    
    // 算法信息列表，用于统计
    private List<AlgorithmInfo> algorithmInfos;
    
    // 防止重复点击的标志
    private volatile boolean isAnimationRunning = false;
    
    // 当前动画窗口引用（单例模式）
    private Window currentAnimationWindow = null;
    private String currentAnimationName = null;
    
    private AlgorithmTreeLauncher() {
        LOGGER.info("Constructor started.");
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
        
        // 添加窗口关闭监听器，确保父窗口关闭时正确处理子窗口
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // 关闭所有子窗口
                closeCurrentAnimationWindow();
                // 执行清理工作
                if (recentAlgorithmManager != null) {
                    try {
                        // 保存最近访问的算法列表
                        // recentAlgorithmManager.saveRecentAlgorithms();
                    } catch (Exception ex) {
                        System.err.println("保存最近访问算法列表失败: " + ex.getMessage());
                    }
                }
                LOGGER.info("主窗口正在关闭，已清理所有子窗口");
            }
        });
        
        allAlgorithmNodes = new ArrayList<>();
        algorithmInfos = new ArrayList<>();
        
        // 初始化最近访问管理器，添加异常处理
        try {
            recentAlgorithmManager = new RecentAlgorithmManager();
        } catch (Exception e) {
            System.err.println("初始化最近访问管理器失败: " + e.getMessage());
            e.printStackTrace();
            recentAlgorithmManager = null;
        }
        
        LOGGER.info("Initializing animations...");
        initAnimations();
        LOGGER.info("Animations initialized.");

        LOGGER.info("Initializing tree structure...");
        initTreeStructure();
        LOGGER.info("Tree structure initialized.");

        LOGGER.info("Initializing components...");
        initComponents();
        LOGGER.info("Components initialized.");

        LOGGER.info("Setting up layout...");
        setupLayout();
        LOGGER.info("Layout set up.");

        LOGGER.info("Setting up event handlers...");
        setupEventHandlers();
        LOGGER.info("Event handlers set up.");
        
        // 默认折叠所有节点（只保持主分组展开）
        collapseAllNodes();
    }

    public static void main(String[] args) {
        // 在EDT中启动应用程序
        SwingUtilities.invokeLater(() -> {
            // 设置JMX属性以进行监控
            System.setProperty("com.sun.management.jmxremote", "true");
            System.setProperty("com.sun.management.jmxremote.port", "9010");
            System.setProperty("com.sun.management.jmxremote.authenticate", "false");
            System.setProperty("com.sun.management.jmxremote.ssl", "false");

            // 启动主窗口
            showMainWindow();
        });
    }
    
    /**
     * 获取单例实例 - 确保整个应用程序只有一个主窗口实例
     */
    public static AlgorithmTreeLauncher getInstance() {
        if (instance == null) {
            synchronized (AlgorithmTreeLauncher.class) {
                if (instance == null) {
                    instance = new AlgorithmTreeLauncher();
                    LOGGER.info("创建主窗口单例实例");
                }
            }
        }
        return instance;
    }
    
    /**
     * 获取父窗口实例 - 供子窗口使用以建立正确的父子关系
     * @return 主窗口实例，作为所有子窗口的父窗口
     */
    public static JFrame getParentWindow() {
        return getInstance();
    }
    
    /**
     * 检查是否有子窗口正在运行
     * @return true如果有子窗口正在运行，false否则
     */
    public boolean hasChildWindowRunning() {
        return currentAnimationWindow != null && currentAnimationWindow.isDisplayable();
    }
    
    /**
     * 获取当前运行的子窗口名称
     * @return 当前子窗口名称，如果没有则返回null
     */
    public String getCurrentChildWindowName() {
        return currentAnimationName;
    }
    
    /**
     * 显示主窗口
     */
    public static void showMainWindow() {
        SwingUtilities.invokeLater(() -> {
            LOGGER.info("Creating and showing GUI.");
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
        LOGGER.info("initAnimations started.");
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
        animations.put("NO.055 跳跃游戏 (Normal)", new Runnable() {
            @Override
            public void run() {
                try {
                    Class<?> clazz = Class.forName("com.leetcode.animation.greedy.NO055_N_JumpGame_Animation");
                    JFrame frame = (JFrame) clazz.getDeclaredConstructor().newInstance();
                    frame.setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "无法启动动画: " + e.getMessage());
                }
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
        animations.put("图的深度优先搜索(DFS)", () -> new GraphDFSAnimation().setVisible(true));
        animations.put("图的广度优先搜索(BFS)", () -> new GraphBFSAnimation().setVisible(true));
        animations.put("Dijkstra最短路径算法", () -> new DijkstraAnimation().setVisible(true));
        animations.put("Kruskal最小生成树算法", () -> new KruskalMSTAnimation().setVisible(true));
        animations.put("拓扑排序算法", () -> new TopologicalSortAnimation().setVisible(true));
        
        // 数学算法
        animations.put("NO.009 回文数", () -> new NO009_E_IsPalindrome_Animation().setVisible(true));
        animations.put("NO.066 加一", () -> new NO066_E_PlusOne_Animation().setVisible(true));
        animations.put("NO.069 x的平方根", () -> new NO069_E_SqrtX_Animation().setVisible(true));
        animations.put("NO.1266 访问所有点的最小时间", () -> new NO1266_E_MinTimeToVisitAllPoints_Animation().setVisible(true));
        
        // 树算法导入
        animations.put("NO.144 二叉树的前序遍历", () -> new NO144_E_PreorderTraversal_Animation().setVisible(true));
        animations.put("NO.094 二叉树的中序遍历", () -> new NO094_E_InorderTraversal_Animation().setVisible(true));
        animations.put("NO.145 二叉树的后序遍历", () -> new NO145_E_PostorderTraversal_Animation().setVisible(true));
        animations.put("NO.100 相同的树", () -> new NO100_E_IsSameTree_Animation().setVisible(true));
        animations.put("NO.101 对称二叉树", () -> new NO101_E_IsSymmetric_Animation().setVisible(true));
        animations.put("NO.104 二叉树的最大深度", () -> new NO104_E_MaximumDepthOfBinaryTree_Animation().setVisible(true));
        animations.put("NO.108 将有序数组转换为二叉搜索树", () -> new NO108_E_SortedArrayToBST_Animation().setVisible(true));
        animations.put("NO.199 二叉树的右视图", () -> new NO199_N_BinaryTreeRightSideView_Animation().setVisible(true));
        
        // 新增算法 - 2024年12月批次
        animations.put("NO.2529 正整数和负整数的最大计数", () -> new NO2529_E_MaximumCount_Animation().setVisible(true));
        animations.put("NO.2535 数组元素和与数字和的绝对差", () -> new NO2535_E_DifferenceOfSum_Animation().setVisible(true));
        animations.put("NO.2540 最小公共值", () -> new NO2540_E_GetCommon_Animation().setVisible(true));
        animations.put("NO.2549 统计桌面上的不同数字", () -> new NO2549_E_DistinctIntegers_Animation().setVisible(true));
        animations.put("NO.2562 找出数组的串联值", () -> new NO2562_E_FindTheArrayConcVal_Animation().setVisible(true));
        animations.put("NO.2574 左右元素和的差值", () -> new NO2574_E_LeftRightDifference_Animation().setVisible(true));
        animations.put("NO.2586 统计范围内的元音字符串数", () -> new NO2586_E_VowelStrings_Animation().setVisible(true));
        
        // 困难算法
        animations.put("NO.23 合并K个升序链表", () -> new NO023_H_MergeKSortedLists_Animation().setVisible(true));
        animations.put("NO.51 N皇后", () -> new NO51_H_NQueens_Animation().setVisible(true));
        animations.put("NO.52 N皇后 II", () -> new NO52_H_NQueensII_Animation(8).setVisible(true));
        animations.put("NO.85 最大矩形", () -> new NO85_H_MaximalRectangle_Animation().setVisible(true));
        animations.put("NO.126 单词接龙 II", () -> new NO126_H_WordLadderII_Animation().setVisible(true));
        animations.put("NO.127 单词接龙", () -> new NO127_H_WordLadder_Animation().setVisible(true));
        animations.put("NO.297 二叉树的序列化与反序列化", () -> new NO297_H_SerializeAndDeserializeBinaryTree_Animation().setVisible(true));
        animations.put("NO.312 戳气球", () -> new NO312_H_BurstBalloons_Animation().setVisible(true));
        animations.put("NO.403 青蛙过河", () -> new NO403_H_FrogJump_Animation().setVisible(true));
        animations.put("NO.773 滑动谜题", () -> new NO773_H_SlidingPuzzle_Animation().setVisible(true));
        animations.put("NO.834 树中距离之和", () -> new NO834_H_SumOfDistancesInTree_Animation().setVisible(true));
        
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
        animations.put("NO.047 全排列 II", () -> new NO047_N_PermutationsII_Animation().setVisible(true));
        animations.put("NO.200 岛屿数量", () -> new com.animation.graph.NO200_N_NumberOfIslands_Animation().setVisible(true));
        animations.put("NO.322 零钱兑换", () -> new NO322_N_CoinChange_Animation().setVisible(true));
        animations.put("NO.139 单词拆分", () -> new NO139_N_WordBreak_Animation().setVisible(true));
        animations.put("NO.784 字母大小写全排列", () -> new NO784_N_LetterCasePermutation_Animation().setVisible(true));
        
        // 原子类动画
        animations.put("AtomicInteger 多线程安全演示", () -> new AtomicIntegerAnimation().setVisible(true));
        animations.put("AtomicReference CAS操作演示", () -> new AtomicReferenceAnimation().setVisible(true));
        animations.put("LongAdder 高并发性能演示", () -> new LongAdderAnimation().setVisible(true));
        animations.put("AtomicIntegerArray 数组操作演示", () -> new AtomicIntegerArrayAnimation().setVisible(true));
        animations.put("AtomicStampedReference ABA问题演示", () -> new AtomicStampedReferenceAnimation().setVisible(true));
        animations.put("AtomicMarkableReference 标记演示", () -> new AtomicMarkableReferenceAnimation().setVisible(true));
        
        // Interval算法动画
        animations.put("面试题 04.05. 合法二叉搜索树", () -> new com.animation.interval.Interval_04_05_N_IsValidBST_Animation().setVisible(true));
        animations.put("面试题 04.10. 检查子树", () -> new com.animation.interval.Interval_04_10_N_CheckSubTree_Animation().setVisible(true));
        animations.put("面试题 08.09. 括号", () -> new com.animation.interval.Interval_08_09_N_GenerateParenthesis_Animation().setVisible(true));
        animations.put("面试题 04.06. 后继者", () -> new com.animation.interval.Interval_04_06_N_InorderSuccessor_Animation().setVisible(true));
        animations.put("面试题 04.12. 求和路径", () -> new com.animation.interval.Interval_04_12_N_PathSum_Animation().setVisible(true));
        animations.put("Interval_02_05_N_AddTwoNumbers", () -> new com.animation.interval.Interval_02_05_N_AddTwoNumbers_Animation().setVisible(true));

        // Lonch 算法
        animations.put("NO.10 二叉树动画", () -> new NO10_BinaryTree_Animation().setVisible(true));
        animations.put("NO.11 二叉树遍历动画", () -> new NO11_BinaryTreeTraversal_Animation().setVisible(true));
        animations.put("NO.9 贪吃蛇游戏", () -> new NO9_SnakeGame_Animation().setVisible(true));
        animations.put("NO.5 矩阵链表动画", () -> new com.animation.lonch.NO5_MatrixLinkedListAnimation().setVisible(true));
        animations.put("NO.6 矩阵链表最短路径动画", () -> new com.animation.lonch.NO6_MatrixLinkedListShortestPathAnimation().setVisible(true));
        animations.put("B+-树", () -> new BPlusTreeAnimation().setVisible(true));
    }
    
    /**
     * 初始化树形结构
     */
    private void initTreeStructure() {
        rootNode = new DefaultMutableTreeNode("🎯 LeetCode算法动画演示系统");
        
        // 创建主要难度分组
        DefaultMutableTreeNode easyGroupNode = new DefaultMutableTreeNode("🟢 简单算法 (Easy) - 共42个");
        DefaultMutableTreeNode normalGroupNode = new DefaultMutableTreeNode("🟡 中等算法 (Normal) - 共14个");
        DefaultMutableTreeNode hardGroupNode = new DefaultMutableTreeNode("🔴 困难算法 (Hard) - 共6个");
        DefaultMutableTreeNode atomicsGroupNode = new DefaultMutableTreeNode("⚛️ 原子类动画 (Atomics) - 共6个");
        DefaultMutableTreeNode intervalGroupNode = new DefaultMutableTreeNode("📋 面试题算法 (Interval) - 共5个");
        
        // 创建Easy算法分类节点
        arrayNode = new DefaultMutableTreeNode("📊 数组算法 (18个)");
        sortNode = new DefaultMutableTreeNode("🔄 排序算法 (1个)");
        dataStructureNode = new DefaultMutableTreeNode("🏗️ 数据结构设计 (5个)");
        searchNode = new DefaultMutableTreeNode("🔍 搜索算法 (2个)");
        dpNode = new DefaultMutableTreeNode("💡 动态规划 (1个)");
        greedyNode = new DefaultMutableTreeNode("🎯 贪心算法 (7个)");
        stringNode = new DefaultMutableTreeNode("📝 字符串算法 (1个)");
        bitNode = new DefaultMutableTreeNode("⚡ 位运算 (2个)");
        graphNode = new DefaultMutableTreeNode("🌐 图论算法 (2个)");
        mathNode = new DefaultMutableTreeNode("🔢 数学算法 (4个)");
        treeNode = new DefaultMutableTreeNode("🌳 树算法 (5个)");
        
        // 创建Normal算法分类节点
        dfsNode = new DefaultMutableTreeNode("🔍 深度优先搜索 (1个)");
        bfsNode = new DefaultMutableTreeNode("🌊 广度优先搜索 (1个)");
        normalNode = new DefaultMutableTreeNode("🎯 Normal算法 (11个)");
        
        // 创建Hard算法分类节点
        hardNode = new DefaultMutableTreeNode("🔥 困难算法 (8个)");
        
        // 创建Atomics原子类分类节点
        atomicIntegerNode = new DefaultMutableTreeNode("🔢 AtomicInteger (1个)");
        atomicReferenceNode = new DefaultMutableTreeNode("📦 AtomicReference (1个)");
        longAdderNode = new DefaultMutableTreeNode("➕ LongAdder (1个)");
        atomicArrayNode = new DefaultMutableTreeNode("📊 AtomicArray (1个)");
        atomicStampedNode = new DefaultMutableTreeNode("🏷️ AtomicStamped (1个)");
        atomicMarkableNode = new DefaultMutableTreeNode("✅ AtomicMarkable (1个)");
        
        // 创建Interval面试题分类节点
        intervalTreeNode = new DefaultMutableTreeNode("🌳 树算法 (3个)");
        intervalBacktrackNode = new DefaultMutableTreeNode("🔄 回溯算法 (1个)");
        intervalOtherNode = new DefaultMutableTreeNode("🎯 其他算法 (1个)");
        lonchNode = new DefaultMutableTreeNode("Lonch");
        
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
        
        atomicsGroupNode.add(atomicIntegerNode);
        atomicsGroupNode.add(atomicReferenceNode);
        atomicsGroupNode.add(longAdderNode);
        atomicsGroupNode.add(atomicArrayNode);
        atomicsGroupNode.add(atomicStampedNode);
        atomicsGroupNode.add(atomicMarkableNode);
        
        intervalGroupNode.add(intervalTreeNode);
        intervalGroupNode.add(intervalBacktrackNode);
        intervalGroupNode.add(intervalOtherNode);

        // 将主分组添加到根节点
        rootNode.add(easyGroupNode);
        rootNode.add(normalGroupNode);
        rootNode.add(hardGroupNode);
        rootNode.add(atomicsGroupNode);
        rootNode.add(intervalGroupNode);
        rootNode.add(lonchNode);


        
        // 添加具体算法到分类节点
        addAllAlgorithms();
    }

    private void addAllAlgorithms() {
        // Easy
        addAlgorithmToCategory(arrayNode, "NO.001 两数之和", "Easy", "哈希表");
        addAlgorithmToCategory(arrayNode, "NO.088 合并两个有序数组", "Easy", "双指针");
        addAlgorithmToCategory(arrayNode, "NO.977 有序数组的平方", "Easy", "双指针");
        addAlgorithmToCategory(arrayNode, "NO.026 删除有序数组中的重复项", "Easy", "双指针");
        addAlgorithmToCategory(arrayNode, "NO.283 移动零", "Easy", "双指针");
        addAlgorithmToCategory(dataStructureNode, "NO.225 用队列实现栈", "Easy", "队列, 栈");
        addAlgorithmToCategory(dataStructureNode, "NO.234 回文链表", "Easy", "链表, 双指针, 栈");
        addAlgorithmToCategory(searchNode, "NO.704 二分查找", "Easy", "二分查找");
        addAlgorithmToCategory(dpNode, "NO.118 杨辉三角", "Easy", "动态规划");
        addAlgorithmToCategory(dpNode, "NO.121 买卖股票的最佳时机", "Easy", "动态规划, 贪心");
        addAlgorithmToCategory(bitNode, "NO.136 只出现一次的数字", "Easy", "位运算, 异或");
        addAlgorithmToCategory(sortNode, "插入排序算法演示", "Easy", "排序, 插入排序");

        // Medium
        addAlgorithmToCategory(normalNode, "NO.208 实现Trie(前缀树)", "Medium", "Trie, 数据结构");
        addAlgorithmToCategory(normalNode, "NO.211 添加与搜索单词", "Medium", "Trie, DFS");
        addAlgorithmToCategory(normalNode, "NO.284 顶端迭代器", "Medium", "设计, 迭代器");
        addAlgorithmToCategory(normalNode, "NO.764 最大加号标志", "Medium", "动态规划");
        addAlgorithmToCategory(normalNode, "NO.915 分割数组", "Medium", "数组");
        addAlgorithmToCategory(normalNode, "NO.046 全排列", "Medium", "回溯");
        addAlgorithmToCategory(normalNode, "NO.047 全排列 II", "Medium", "回溯");
        addAlgorithmToCategory(normalNode, "NO.784 字母大小写全排列", "Medium", "回溯");
        addAlgorithmToCategory(dataStructureNode, "Interval_02_05_N_AddTwoNumbers", "Medium", "链表, 数学");

        // Hard
        addAlgorithmToCategory(normalNode, "NO.005 最长回文子串", "Medium", "动态规划, 字符串");
        addAlgorithmToCategory(normalNode, "NO.015 三数之和", "Medium", "数组, 双指针, 排序");
        addAlgorithmToCategory(normalNode, "NO.053 最大子数组和", "Medium", "动态规划, 数组");
        addAlgorithmToCategory(hardNode, "NO.200 岛屿数量", "Hard", "DFS, BFS, 图");
        addAlgorithmToCategory(hardNode, "NO.322 零钱兑换", "Hard", "动态规划, 背包");
        addAlgorithmToCategory(hardNode, "NO.139 单词拆分", "Hard", "动态规划, 字符串");
        addAlgorithmToCategory(hardNode, "NO.85 最大矩形", "Hard", "单调栈, 动态规划");
        addAlgorithmToCategory(hardNode, "NO.312 戳气球", "Hard", "动态规划, 区间DP");
        addAlgorithmToCategory(hardNode, "NO.773 滑动谜题", "Hard", "BFS, 状态压缩");
        addAlgorithmToCategory(hardNode, "NO.127 单词接龙", "Hard", "BFS, 图");
        addAlgorithmToCategory(hardNode, "NO.126 单词接龙 II", "Hard", "BFS, DFS, 图");

        // Atomics
        addAlgorithmToCategory(atomicIntegerNode, "AtomicInteger 多线程安全演示", "Atomics", "原子整数 + 线程安全");
        addAlgorithmToCategory(atomicReferenceNode, "AtomicReference CAS操作演示", "Atomics", "原子引用 + CAS操作");
        addAlgorithmToCategory(longAdderNode, "LongAdder 高并发性能演示", "Atomics", "高并发计数器 + 性能对比");
        addAlgorithmToCategory(atomicArrayNode, "AtomicIntegerArray 数组操作演示", "Atomics", "原子数组 + 并发操作");
        addAlgorithmToCategory(atomicStampedNode, "AtomicStampedReference ABA问题演示", "Atomics", "版本戳引用 + ABA问题解决");
        addAlgorithmToCategory(atomicMarkableNode, "AtomicMarkableReference 标记演示", "Atomics", "可标记引用 + 布尔标记");

        // 数据库
        DefaultMutableTreeNode dbNode = new DefaultMutableTreeNode("数据库");
        addAlgorithmToCategory(dbNode, "B+-树", "Hard", "数据库");
        rootNode.add(dbNode);

        // Interview
        addAlgorithmToCategory(intervalTreeNode, "B+-树", "Hard", "B+树, 数据结构");
        addAlgorithmToCategory(intervalTreeNode, "NO.297 二叉树的序列化与反序列化", "Hard", "树, DFS, 设计");
         addAlgorithmToCategory(intervalTreeNode, "NO.834 树中距离之和", "Hard", "树, DFS, 动态规划");
        addAlgorithmToCategory(intervalBacktrackNode, "面试题 08.09. 括号", "Interval", "回溯算法 + 括号生成");
        addAlgorithmToCategory(intervalOtherNode, "面试题 04.12. 求和路径", "Interval", "树遍历 + 路径统计");

        // Lonch
        addAlgorithmToCategory(lonchNode, "NO.10 二叉树动画", "Lonch", "二叉树基础动画");
        addAlgorithmToCategory(lonchNode, "NO.11 二叉树遍历动画", "Lonch", "二叉树遍历过程动画");
        addAlgorithmToCategory(lonchNode, "NO.9 贪吃蛇游戏", "Lonch", "经典游戏动画");
        addAlgorithmToCategory(lonchNode, "NO.5 矩阵链表动画", "Lonch", "矩阵链表创建");
        addAlgorithmToCategory(lonchNode, "NO.6 矩阵链表最短路径动画", "Lonch", "矩阵链表最短路径");
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
        
        addAlgorithmToCategory(sortNode, "插入排序算法演示", "Easy", "插入排序 + 可视化");
        
        addAlgorithmToCategory(dataStructureNode, "NO.225 用队列实现栈", "Easy", "队列 + LIFO模拟");
        addAlgorithmToCategory(dataStructureNode, "NO.234 回文链表", "Easy", "快慢指针 + 栈");
        addAlgorithmToCategory(dataStructureNode, "NO.705 设计哈希集合", "Easy", "哈希表 + 链地址法");
        addAlgorithmToCategory(dataStructureNode, "NO.706 设计哈希映射", "Easy", "哈希表 + 线性探测");
        addAlgorithmToCategory(dataStructureNode, "NO.703 数据流中的第K大元素", "Easy", "最小堆 + 优先队列");
        
        addAlgorithmToCategory(searchNode, "NO.035 搜索插入位置", "Easy", "二分搜索 + 插入位置");
        
        addAlgorithmToCategory(mathNode, "NO.009 回文数", "Easy", "数学 + 回文判断");
        addAlgorithmToCategory(mathNode, "NO.066 加一", "Easy", "数学 + 进位处理");
        addAlgorithmToCategory(mathNode, "NO.069 x的平方根", "Easy", "数学 + 二分查找");
        addAlgorithmToCategory(mathNode, "NO.1266 访问所有点的最小时间", "Easy", "数学 + 切比雪夫距离");
        addAlgorithmToCategory(searchNode, "NO.704 二分查找", "Easy", "二分搜索 + 有序数组");
        
        addAlgorithmToCategory(dpNode, "NO.118 杨辉三角", "Easy", "动态规划 + 组合数学");
        
        addAlgorithmToCategory(greedyNode, "NO.055 跳跃游戏", "Easy", "贪心 + 最远可达位置");
        addAlgorithmToCategory(greedyNode, "NO.055 跳跃游戏 (Normal)", "Easy", "贪心 + 最远可达位置 (详细版)");
        addAlgorithmToCategory(greedyNode, "NO.045 跳跃游戏 II", "Easy", "贪心 + 最少跳跃次数");
        addAlgorithmToCategory(greedyNode, "NO.2498 青蛙过河 II", "Easy", "贪心 + 最小跳跃距离");
        addAlgorithmToCategory(greedyNode, "NO.121 买卖股票的最佳时机", "Easy", "贪心 + 一次遍历");
        addAlgorithmToCategory(greedyNode, "NO.2558 从数量最多的堆取走礼物", "Easy", "贪心 + 最大堆");
        addAlgorithmToCategory(greedyNode, "NO.1005 K次取反后最大化数组和", "Easy", "贪心 + 排序");
        
        addAlgorithmToCategory(stringNode, "NO.1002 查找常用字符", "Easy", "字符统计 + 频次计算");
        
        addAlgorithmToCategory(bitNode, "NO.136 只出现一次的数字", "Easy", "位运算 + 异或运算");
        addAlgorithmToCategory(bitNode, "NO.190 颠倒二进制位", "Easy", "位运算 + 逐位处理");

        // Lonch 算法
        addAlgorithmToCategory(lonchNode, "NO.10 二叉树动画", "Lonch", "二叉树基础动画");
        addAlgorithmToCategory(lonchNode, "NO.11 二叉树遍历动画", "Lonch", "二叉树遍历过程动画");
        addAlgorithmToCategory(lonchNode, "NO.9 贪吃蛇游戏", "Lonch", "经典游戏动画");
        addAlgorithmToCategory(lonchNode, "NO.5 矩阵链表动画", "Lonch", "矩阵链表创建");
        addAlgorithmToCategory(lonchNode, "NO.6 矩阵链表最短路径动画", "Lonch", "矩阵链表最短路径");
        
        addAlgorithmToCategory(graphNode, "NO.463 岛屿的周长", "Easy", "网格遍历 + 边界计算");
        addAlgorithmToCategory(graphNode, "NO.200 岛屿数量", "Easy", "深度优先搜索 + 网格遍历");
        addAlgorithmToCategory(graphNode, "图的深度优先搜索(DFS)", "Easy", "深度优先搜索 + 栈 + 回溯");
        addAlgorithmToCategory(graphNode, "图的广度优先搜索(BFS)", "Easy", "广度优先搜索 + 队列 + 层次遍历");
        addAlgorithmToCategory(graphNode, "Dijkstra最短路径算法", "Easy", "最短路径 + 优先队列 + 贪心");
        addAlgorithmToCategory(graphNode, "Kruskal最小生成树算法", "Easy", "最小生成树 + 并查集 + 贪心");
        addAlgorithmToCategory(graphNode, "拓扑排序算法", "Easy", "拓扑排序 + Kahn算法 + 入度");
        
        addAlgorithmToCategory(mathNode, "NO.009 回文数", "Easy", "数学运算 + 数位反转");
        addAlgorithmToCategory(mathNode, "NO.066 加一", "Easy", "数学运算 + 进位处理");
        addAlgorithmToCategory(mathNode, "NO.069 x的平方根", "Easy", "二分查找 + 数学运算");
        addAlgorithmToCategory(mathNode, "NO.2549 统计桌面上的不同数字", "Easy", "数学推理 + 模拟");
        
        addAlgorithmToCategory(treeNode, "NO.144 二叉树的前序遍历", "Easy", "树遍历 + 递归/栈模拟");
        addAlgorithmToCategory(treeNode, "NO.094 二叉树的中序遍历", "Easy", "树遍历 + 栈模拟");
        addAlgorithmToCategory(treeNode, "NO.145 二叉树的后序遍历", "Easy", "树遍历 + 递归/栈模拟");
        addAlgorithmToCategory(treeNode, "NO.100 相同的树", "Easy", "树比较 + 递归");
        addAlgorithmToCategory(treeNode, "NO.101 对称二叉树", "Easy", "树遍历 + 对称性检查");
        addAlgorithmToCategory(treeNode, "NO.104 二叉树的最大深度", "Easy", "树遍历 + 深度计算");
        addAlgorithmToCategory(treeNode, "NO.108 将有序数组转换为二叉搜索树", "Easy", "分治算法 + 平衡树构建");
        addAlgorithmToCategory(treeNode, "NO.199 二叉树的右视图", "Easy", "树 + 广度优先搜索");
        
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
        addAlgorithmToCategory(normalNode, "NO.322 零钱兑换", "Normal", "动态规划 + 完全背包");
        addAlgorithmToCategory(normalNode, "NO.139 单词拆分", "Normal", "动态规划 + 字符串匹配");
        
        addAlgorithmToCategory(hardNode, "NO.51 N皇后", "Hard", "回溯算法");
        addAlgorithmToCategory(hardNode, "NO.403 青蛙过河", "Hard", "动态规划 + 集合");
        addAlgorithmToCategory(hardNode, "NO.23 合并K个升序链表", "Hard", "优先队列 + 链表");
        addAlgorithmToCategory(hardNode, "NO.52 N皇后II", "Hard", "回溯算法");
        // NO.297 和 NO.834 在 geek-hard 模块中，暂时移除
        // addAlgorithmToCategory(hardNode, "NO.297 二叉树的序列化与反序列化", "Hard", "前序遍历 + 递归重建");
        // addAlgorithmToCategory(hardNode, "NO.834 树中距离之和", "Hard", "树形DP + DFS");
        
        // 添加Interval面试题算法
        addAlgorithmToCategory(intervalTreeNode, "面试题 04.05. 合法二叉搜索树", "Interval", "二叉搜索树 + DFS验证");
        addAlgorithmToCategory(intervalTreeNode, "面试题 04.10. 检查子树", "Interval", "树遍历 + 字符串匹配");
        addAlgorithmToCategory(intervalTreeNode, "面试题 04.06. 后继者", "Interval", "二叉搜索树 + 中序遍历");
        addAlgorithmToCategory(intervalBacktrackNode, "面试题 08.09. 括号", "Interval", "回溯算法 + 括号生成");
        addAlgorithmToCategory(intervalOtherNode, "面试题 04.12. 求和路径", "Interval", "树遍历 + 路径统计");
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
        algorithmInfos.add(info);  // 添加到统计列表
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
        
        // 创建进程和内存监控按钮
        processMonitorButton = new JButton("PID " + ProcessUtils.getCurrentProcessId());
        processMonitorButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        processMonitorButton.setToolTipText("打开jvisualvm监控当前进程");

        memoryMonitorButton = new JButton("JConsole");
        memoryMonitorButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        memoryMonitorButton.setToolTipText("打开jconsole");

        // 初始化统计饼图面板
        statsPieChartPanel = new StatsPieChartPanel();
        statsPieChartPanel.updateData();
        
        // 创建状态标签
        statusLabel = new JLabel("请选择一个算法查看详细信息");
        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        statusLabel.setForeground(Color.GRAY);
        
        // 创建最近访问列表
        recentListModel = new DefaultListModel<>();
        recentList = new JList<>(recentListModel);
        recentList.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        recentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        recentList.setCellRenderer(new RecentAlgorithmCellRenderer());
        recentList.setFixedCellHeight(35);
        
        // 创建树操作按钮
        expandAllButton = new JButton("📂 展开");
        expandAllButton.setFont(new Font("微软雅黑", Font.PLAIN, 11));
        expandAllButton.setPreferredSize(new Dimension(60, 25));
        expandAllButton.setToolTipText("展开所有分类");
        
        collapseAllButton = new JButton("📁 折叠");
        collapseAllButton.setFont(new Font("微软雅黑", Font.PLAIN, 11));
        collapseAllButton.setPreferredSize(new Dimension(60, 25));
        collapseAllButton.setToolTipText("折叠所有分类");
        
        // 初始化最近访问面板
        initRecentPanel();
        
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
        
        // 左侧：算法树和最近访问
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "算法分类目录", 
            TitledBorder.LEFT, TitledBorder.TOP, 
            new Font("微软雅黑", Font.BOLD, 14)));
        
        // 顶部面板：搜索 + 最近访问
        JPanel topPanel = new JPanel(new BorderLayout(0, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // 搜索面板
        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        JLabel searchLabel = new JLabel("🔍 搜索:");
        searchLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(clearSearchButton, BorderLayout.EAST);
        
        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(recentPanel, BorderLayout.CENTER);
        
        // 树面板：工具栏 + 树
        JPanel treePanel = new JPanel(new BorderLayout());
        
        // 树工具栏
        JPanel treeToolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        treeToolbar.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        treeToolbar.add(new JLabel("📋 分类:"));
        treeToolbar.add(expandAllButton);
        treeToolbar.add(collapseAllButton);
        
        JScrollPane treeScrollPane = new JScrollPane(algorithmTree);
        treeScrollPane.setPreferredSize(new Dimension(350, 400));
        
        treePanel.add(treeToolbar, BorderLayout.NORTH);
        treePanel.add(treeScrollPane, BorderLayout.CENTER);
        
        leftPanel.add(topPanel, BorderLayout.NORTH);
        leftPanel.add(treePanel, BorderLayout.CENTER);
        
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
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.add(processMonitorButton);
        controlPanel.add(memoryMonitorButton);
        controlPanel.add(startButton);
        
        rightPanel.add(descPanel, BorderLayout.CENTER);
        rightPanel.add(controlPanel, BorderLayout.SOUTH);
        
        mainSplitPane.setLeftComponent(leftPanel);
        mainSplitPane.setRightComponent(rightPanel);
        
        add(mainSplitPane, BorderLayout.CENTER);
        
        // 顶部标题和统计面板
        JPanel topContainer = new JPanel(new BorderLayout());
        
        // 统计饼图面板
        JPanel statsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
        statsContainer.setBackground(new Color(248, 250, 252));
        statsContainer.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        statsPieChartPanel.setPreferredSize(new Dimension(getWidth(), 80));
        statsContainer.add(statsPieChartPanel);
        
        // 标题面板
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
        
        // 组合顶部容器
        topContainer.add(statsContainer, BorderLayout.NORTH);
        topContainer.add(titlePanel, BorderLayout.CENTER);
        
        add(topContainer, BorderLayout.NORTH);
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
            ThreadUtils.execute(() -> {
                CommandUtils.exec("jvisualvm --openpid " + ProcessUtils.getCurrentProcessId());
            });
        });

        // 内存监控按钮事件
        memoryMonitorButton.addActionListener(e -> {
            ThreadUtils.execute(() -> {
                CommandUtils.exec("jconsole");
            });
        });
        
        // 最近访问列表事件
        recentList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedAlgorithm = recentList.getSelectedValue();
                if (selectedAlgorithm != null) {
                    selectAlgorithmInTree(selectedAlgorithm);
                }
            }
        });
        
        // 展开所有按钮事件
        expandAllButton.addActionListener(e -> {
            expandAllNodes();
            statusLabel.setText("已展开所有分类");
        });
        
        // 折叠所有按钮事件
        collapseAllButton.addActionListener(e -> {
            collapseAllNodes();
            statusLabel.setText("已折叠所有分类");
        });

        // 统计按钮已移除，饼图直接显示在标题栏
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
     * 折叠所有树节点（保留根节点展开）
     */
    private void collapseAllNodes() {
        // 从最后一行开始折叠，避免索引变化问题
        for (int i = algorithmTree.getRowCount() - 1; i >= 1; i--) {
            algorithmTree.collapseRow(i);
        }
    }
    
    /**
     * 初始化最近访问面板
     */
    private void initRecentPanel() {
        recentPanel = new JPanel(new BorderLayout());
        recentPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "🕒 最近访问 TOP5", 
            TitledBorder.LEFT, TitledBorder.TOP, 
            new Font("微软雅黑", Font.BOLD, 11)));
        
        JScrollPane recentScrollPane = new JScrollPane(recentList);
        recentScrollPane.setPreferredSize(new Dimension(340, 120));
        recentScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        recentScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        recentPanel.add(recentScrollPane, BorderLayout.CENTER);
        
        // 加载最近访问记录
        loadRecentAlgorithms();
    }
    
    /**
     * 加载最近访问记录
     */
    private void loadRecentAlgorithms() {
        if (recentAlgorithmManager == null) {
            return;
        }
        
        try {
            List<RecentAlgorithmManager.RecentAlgorithmInfo> recentAlgorithms = recentAlgorithmManager.getRecentAlgorithms();
            recentListModel.clear();
            for (RecentAlgorithmManager.RecentAlgorithmInfo info : recentAlgorithms) {
                recentListModel.addElement(info.getAlgorithmName());
            }
        } catch (Exception e) {
            System.err.println("加载最近访问记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 在树中选择指定算法
     */
    private void selectAlgorithmInTree(String algorithmName) {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) algorithmTree.getModel().getRoot();
        DefaultMutableTreeNode targetNode = findNodeByName(root, algorithmName);
        
        if (targetNode != null) {
            TreePath path = new TreePath(targetNode.getPath());
            algorithmTree.setSelectionPath(path);
            algorithmTree.scrollPathToVisible(path);
            
            // 展开到该节点
            algorithmTree.expandPath(path.getParentPath());
        }
    }
    
    /**
     * 递归查找指定名称的节点
     */
    private DefaultMutableTreeNode findNodeByName(DefaultMutableTreeNode node, String name) {
        if (node.getUserObject() instanceof AlgorithmInfo) {
            AlgorithmInfo info = (AlgorithmInfo) node.getUserObject();
            if (info.getName().equals(name)) {
                return node;
            }
        }
        
        for (int i = 0; i < node.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
            DefaultMutableTreeNode result = findNodeByName(child, name);
            if (result != null) {
                return result;
            }
        }
        
        return null;
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

            case "NO.85 最大矩形":
                return "🎯 【NO.85 最大矩形】\n\n" +
                       "📝 问题描述：\n" +
                       "给定一个仅包含 0 和 1 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 将问题转化为多个'柱状图中最大的矩形'问题\n" +
                       "• 逐行遍历，将每一行及其上方连续的1视为一个柱状图的高度\n" +
                       "• 使用单调栈计算每个柱状图的最大矩形面积\n\n" +
                       "⏰ 时间复杂度：O(m*n)\n" +
                       "💾 空间复杂度：O(n)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 可视化每行柱状图的生成过程\n" +
                       "• 动态演示单调栈的入栈和出栈操作\n" +
                       "• 高亮显示计算出的最大矩形面积\n\n" +
                       "💡 算法技巧：单调栈 + 动态规划";

            case "NO.312 戳气球":
                return "🎯 【NO.312 戳气球】\n\n" +
                       "📝 问题描述：\n" +
                       "有 n 个气球，编号为0到n-1，每个气球上都标有一个数字，这些数字存在数组nums中。现在要求你戳破所有的气球。每当你戳破一个气球i时，你可以获得nums[left] * nums[i] * nums[right]个硬币。这里的left和right代表和i相邻的两个气球的序号。求所能获得硬币的最大数量。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 使用区间动态规划\n" +
                       "• dp[i][j]表示戳破(i, j)范围内所有气球能获得的最大硬币数\n" +
                       "• 状态转移：最后戳破k，dp[i][j] = max(dp[i][k] + dp[k][j] + nums[i]*nums[k]*nums[j])\n\n" +
                       "⏰ 时间复杂度：O(n^3)\n" +
                       "💾 空间复杂度：O(n^2)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 可视化DP表的填充过程\n" +
                       "• 动态展示区间的划分和合并\n" +
                       "• 高亮显示当前计算的最优解\n\n" +
                       "💡 算法技巧：区间DP + 逆向思维";

            case "NO.773 滑动谜题":
                return "🎯 【NO.773 滑动谜题】\n\n" +
                       "📝 问题描述：\n" +
                       "在一个 2x3 的板上（board）有 5 块砖瓦，用数字 1~5 来表示, 以及一块空缺用 0 来表示。一次移动定义为选择 0 与一个上下左右相邻的数字交换位置。最终使得板 board 的状态变为 [[1,2,3],[4,5,0]]。返回达到目标板所需的最小移动次数，如果不能到达目标板，返回 -1。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 使用广度优先搜索(BFS)寻找最短路径\n" +
                       "• 将二维数组状态压缩成一维字符串或整数，用于判重\n" +
                       "• 每次扩展当前状态，生成所有可能的下一步状态\n\n" +
                       "⏰ 时间复杂度：O((m*n)! * (m*n))\n" +
                       "💾 空间复杂度：O((m*n)!)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 可视化棋盘状态的变化\n" +
                       "• 动态展示BFS的层次遍历过程\n" +
                       "• 高亮显示当前搜索的节点和路径\n\n" +
                       "💡 算法技巧：BFS + 状态压缩";

            case "NO.127 单词接龙":
                return "🎯 【NO.127 单词接龙】\n\n" +
                       "📝 问题描述：\n" +
                       "字典 wordList 中从单词 beginWord 和 endWord 的 转换序列 是一个按下述规格形成的序列：序列中第一个单词是 beginWord ，最后一个单词是 endWord ，并且序列中相邻的单词只差一个字母。对于每个转换，单词必须存在于字典中。返回最短转换序列的长度。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 将问题抽象为图的最短路径问题\n" +
                       "• 使用广度优先搜索(BFS)寻找最短转换序列\n" +
                       "• 优化：可以使用双向BFS来加速搜索\n\n" +
                       "⏰ 时间复杂度：O(N * C^2)，N是单词数，C是单词长度\n" +
                       "💾 空间复杂度：O(N * C^2)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 可视化单词图的构建和遍历\n" +
                       "• 动态展示BFS的层次扩展\n" +
                       "• 高亮显示找到的最短路径\n\n" +
                       "💡 算法技巧：BFS + 图论建模";

            case "NO.126 单词接龙 II":
                return "🎯 【NO.126 单词接龙 II】\n\n" +
                       "📝 问题描述：\n" +
                       "给定两个单词（beginWord 和 endWord）和一个字典 wordList，找出所有从 beginWord 到 endWord 的最短转换序列。\n\n" +
                       "🔧 核心算法：\n" +
                       "• 先使用BFS构建出从起点到各节点的最短距离，并记录前驱节点\n" +
                       "• 再使用DFS根据前驱节点信息，从终点回溯到起点，重建所有最短路径\n\n" +
                       "⏰ 时间复杂度：O(N * C^2 + V + E)，V是顶点数，E是边数\n" +
                       "💾 空间复杂度：O(N * C^2)\n\n" +
                       "🎬 动画特色：\n" +
                       "• 分步展示BFS构建最短路径图和DFS回溯路径\n" +
                       "• 可视化每个节点的前驱和后继关系\n" +
                       "• 动态生成并展示所有最短转换路径\n\n" +
                       "💡 算法技巧：BFS + DFS + 图论";

            case "B+-树":
                return "🎯 【B+-树】\n\n" +
                        "📝 问题描述：\n" +
                        "B+-树是一种自平衡的树，能够保持数据有序，并允许在对数时间内进行搜索、顺序访问、插入和删除。它特别适用于数据库和文件系统。\n\n" +
                        "🔧 核心算法：\n" +
                        "• 节点分裂与合并：当节点满或太空时，进行分裂或合并以保持平衡。\n" +
                        "• 键的重新分配：在兄弟节点之间移动键以避免分裂或合并。\n" +
                        "• 所有数据都存储在叶子节点中，内部节点仅用作索引。\n\n" +
                        "⏰ 时间复杂度：O(log n) for search, insertion, deletion\n" +
                        "💾 空间复杂度：O(n)\n\n" +
                        "🎬 动画特色：\n" +
                        "• 可视化B+-树的插入、删除和搜索操作。\n" +
                        "• 动态展示节点的分裂、合并和键的重新分配过程。\n" +
                        "• 高亮显示搜索路径和受影响的节点。\n\n" +
                        "💡 算法技巧：自平衡树 + 索引结构";

            // NO.297 和 NO.834 的描述已移除，因为它们在 geek-hard 模块中

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
     * 最近访问列表的自定义渲染器
     */
    private static class RecentAlgorithmCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value instanceof String) {
                String algorithmName = (String) value;
                
                // 设置显示文本（截断过长的名称）
                if (algorithmName.length() > 30) {
                    setText(algorithmName.substring(0, 27) + "...");
                } else {
                    setText(algorithmName);
                }
                
                // 设置工具提示
                setToolTipText(algorithmName);
                
                // 设置图标
                setIcon(createRecentIcon());
                
                // 设置字体
                setFont(new Font("微软雅黑", Font.PLAIN, 11));
                
                // 设置边距
                setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
            }
            
            return this;
        }
        
        private Icon createRecentIcon() {
            return new Icon() {
                @Override
                public void paintIcon(Component c, Graphics g, int x, int y) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    // 绘制时钟图标
                    g2.setColor(new Color(100, 149, 237));
                    g2.fillOval(x, y, 12, 12);
                    g2.setColor(Color.WHITE);
                    g2.fillOval(x + 2, y + 2, 8, 8);
                    g2.setColor(new Color(100, 149, 237));
                    g2.drawLine(x + 6, y + 6, x + 6, y + 3);
                    g2.drawLine(x + 6, y + 6, x + 9, y + 6);
                    
                    g2.dispose();
                }
                
                @Override
                public int getIconWidth() { return 12; }
                
                @Override
                public int getIconHeight() { return 12; }
            };
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
                    case "Atomics":
                        setIcon(createColorIcon(new Color(156, 39, 176))); // 紫色
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
            // 如果搜索框为空，显示完整的树并保持折叠状态
            algorithmTree.setModel(new DefaultTreeModel(rootNode));
            collapseAllNodes();
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
        collapseAllNodes();
        statusLabel.setText("就绪");
    }
    
    /**
     * 启动算法动画
     */
    private void startAnimation() {
        // 防止重复点击
        if (isAnimationRunning) {
            JOptionPane.showMessageDialog(this, 
                "动画正在运行中，请等待当前动画结束后再启动新的动画！", 
                "提示", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
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
            // 设置动画运行标志
            isAnimationRunning = true;
            startButton.setEnabled(false); // 禁用启动按钮
            statusLabel.setText("正在启动算法动画：" + algorithmName);
            
            // 记录到最近访问
            if (recentAlgorithmManager != null) {
                try {
                    recentAlgorithmManager.addRecentAlgorithm(
                        algorithmName, 
                        selectedAlgorithm.getDifficulty(), 
                        selectedAlgorithm.getTechnique()
                    );
                    
                    // 更新最近访问列表显示
                    loadRecentAlgorithms();
                } catch (Exception e) {
                    System.err.println("记录最近访问失败: " + e.getMessage());
                }
            }
            
            // 直接在EDT线程中创建模态窗口
            try {
                Runnable animation = animations.get(algorithmName);
                createModalAnimationWindow(animation, algorithmName);
            } catch (Exception ex) {
                statusLabel.setText("启动失败：" + ex.getMessage());
                JOptionPane.showMessageDialog(this, 
                    "启动算法动画时发生错误：\n" + ex.getMessage(), 
                    "错误", 
                    JOptionPane.ERROR_MESSAGE);
                // 重置状态
                isAnimationRunning = false;
                startButton.setEnabled(true);
                ex.printStackTrace();
            }
            
        } catch (Exception e) {
            statusLabel.setText("启动失败：" + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "启动算法动画时发生错误：\n" + e.getMessage(), 
                "错误", 
                JOptionPane.ERROR_MESSAGE);
            // 重置状态
            isAnimationRunning = false;
            startButton.setEnabled(true);
            e.printStackTrace();
        }
    }
    
    /**
     * 创建模态动画窗口（单例模式）
     */
    /**
     * 创建模态动画窗口 - 确保子页面是父页面的真正子窗口
     * 实现单例模式：同一时间只能有一个动画窗口打开
     */
    private void createModalAnimationWindow(Runnable animation, String algorithmName) {
        try {
            // 如果当前已有动画窗口且是同一个动画，则将窗口置于前台
            if (currentAnimationWindow != null && algorithmName.equals(currentAnimationName)) {
                currentAnimationWindow.toFront();
                currentAnimationWindow.requestFocus();
                statusLabel.setText("动画窗口已置于前台：" + algorithmName);
                return;
            }
            
            // 关闭现有的动画窗口（确保单例）
            closeCurrentAnimationWindow();
            
            // 如果动画是JPanel类型，需要包装在JFrame中
            if (animation instanceof JPanel) {
                JPanel animationPanel = (JPanel) animation;
                
                // 创建模态对话框，明确设置父窗口关系
                JDialog dialog = new JDialog(this, "算法动画演示 - " + algorithmName, true);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                
                // 设置窗口属性 - 确保子窗口特性
                dialog.setSize(800, 600);
                dialog.setLocationRelativeTo(this); // 相对于父窗口居中
                dialog.setAlwaysOnTop(false); // 移除置顶，让其表现为正常的子窗口
                
                // 设置窗口图标与父窗口一致
                if (this.getIconImage() != null) {
                    dialog.setIconImage(this.getIconImage());
                }
                
                // 添加动画面板
                dialog.add(animationPanel);
                
                // 保存当前窗口引用
                currentAnimationWindow = dialog;
                currentAnimationName = algorithmName;
                
                // 添加窗口关闭监听器
                dialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        // 动画窗口关闭后重置状态
                        resetAnimationState(algorithmName);
                    }
                    
                    @Override
                    public void windowClosing(WindowEvent e) {
                        // 窗口正在关闭时也重置状态
                        resetAnimationState(algorithmName);
                    }
                });
                
                // 更新状态标签
                statusLabel.setText("正在启动算法动画：" + algorithmName);
                
                // 显示模态对话框（阻塞调用）
                dialog.setVisible(true);
                
                // 对话框关闭后重置状态
                resetAnimationState(algorithmName);
                
            } else {
                // 对于JFrame类型的动画，也包装在模态对话框中
                if (animation instanceof JFrame) {
                    JFrame animationFrame = (JFrame) animation;
                    
                    // 创建模态对话框来包装JFrame的内容，明确设置父窗口关系
                    JDialog dialog = new JDialog(this, "算法动画演示 - " + algorithmName, true);
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    
                    // 获取JFrame的内容面板
                    Container contentPane = animationFrame.getContentPane();
                    
                    // 将JFrame的内容转移到对话框中
                    dialog.setContentPane(contentPane);
                    
                    // 设置窗口属性 - 确保子窗口特性
                    dialog.setSize(animationFrame.getSize().width > 0 ? animationFrame.getSize() : new Dimension(800, 600));
                    dialog.setLocationRelativeTo(this); // 相对于父窗口居中
                    
                    // 设置窗口图标与父窗口一致
                    if (this.getIconImage() != null) {
                        dialog.setIconImage(this.getIconImage());
                    }
                    
                    // 保存当前窗口引用
                    currentAnimationWindow = dialog;
                    currentAnimationName = algorithmName;
                    
                    // 添加窗口关闭监听器
                    dialog.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            // 动画窗口关闭后重置状态
                            resetAnimationState(algorithmName);
                        }
                        
                        @Override
                        public void windowClosing(WindowEvent e) {
                            // 窗口正在关闭时也重置状态
                            resetAnimationState(algorithmName);
                        }
                    });
                    
                    // 更新状态标签
                    statusLabel.setText("正在启动算法动画：" + algorithmName);
                    
                    // 显示模态对话框（阻塞调用）
                    dialog.setVisible(true);
                    
                    // 对话框关闭后重置状态
                    resetAnimationState(algorithmName);
                } else {
                    // 对于其他类型的动画，先运行获取JFrame实例，然后包装在模态对话框中
                    currentAnimationName = algorithmName;
                    statusLabel.setText("正在启动算法动画：" + algorithmName);
                    
                    try {
                        // 运行动画创建逻辑
                        animation.run();
                            
                            // 查找新创建的JFrame窗口
                             Window[] windows = Window.getWindows();
                             JFrame foundFrame = null;
                             for (Window window : windows) {
                                 if (window instanceof JFrame && window.isVisible() && 
                                     window != AlgorithmTreeLauncher.this) {
                                     foundFrame = (JFrame) window;
                                     break;
                                 }
                             }
                             
                             final JFrame animationFrame = foundFrame;
                             if (animationFrame != null) {
                                // 隐藏原始JFrame
                                animationFrame.setVisible(false);
                                
                                // 创建模态对话框来包装JFrame的内容
                                JDialog dialog = new JDialog(AlgorithmTreeLauncher.this, "算法动画演示 - " + algorithmName, true);
                                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                
                                // 获取JFrame的内容面板
                                Container contentPane = animationFrame.getContentPane();
                                
                                // 将JFrame的内容转移到对话框中
                                dialog.setContentPane(contentPane);
                                
                                // 设置窗口属性
                                dialog.setSize(animationFrame.getSize().width > 0 ? animationFrame.getSize() : new Dimension(800, 600));
                                dialog.setLocationRelativeTo(AlgorithmTreeLauncher.this);
                                
                                // 设置窗口图标
                                if (AlgorithmTreeLauncher.this.getIconImage() != null) {
                                    dialog.setIconImage(AlgorithmTreeLauncher.this.getIconImage());
                                }
                                
                                // 保存当前窗口引用
                                currentAnimationWindow = dialog;
                                
                                // 添加窗口关闭监听器
                                dialog.addWindowListener(new WindowAdapter() {
                                    @Override
                                    public void windowClosed(WindowEvent e) {
                                        // 确保原始JFrame也被销毁
                                        animationFrame.dispose();
                                        resetAnimationState(algorithmName);
                                    }
                                    
                                    @Override
                                    public void windowClosing(WindowEvent e) {
                                        // 确保原始JFrame也被销毁
                                        animationFrame.dispose();
                                        resetAnimationState(algorithmName);
                                    }
                                });
                                
                                // 显示模态对话框
                                dialog.setVisible(true);
                                
                                // 对话框关闭后重置状态
                                resetAnimationState(algorithmName);
                            } else {
                                // 如果没有找到JFrame，重置状态
                                resetAnimationState(algorithmName);
                            }
                    } catch (Exception e) {
                        resetAnimationState(algorithmName);
                        statusLabel.setText("启动失败：" + e.getMessage());
                        JOptionPane.showMessageDialog(this, 
                            "创建动画窗口时发生错误：\n" + e.getMessage(), 
                            "错误", 
                            JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                    }
                }
            }
            
        } catch (Exception e) {
            // 发生异常时重置状态
            resetAnimationState(algorithmName);
            statusLabel.setText("启动失败：" + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "创建动画窗口时发生错误：\n" + e.getMessage(), 
                "错误", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * 关闭当前动画窗口 - 确保正确清理子窗口资源
     */
    private void closeCurrentAnimationWindow() {
        if (currentAnimationWindow != null) {
            try {
                LOGGER.info("正在关闭动画窗口: " + currentAnimationName);
                
                if (currentAnimationWindow instanceof JDialog) {
                    JDialog dialog = (JDialog) currentAnimationWindow;
                    // 确保对话框不可见
                    dialog.setVisible(false);
                    // 释放资源
                    dialog.dispose();
                } else if (currentAnimationWindow instanceof JFrame) {
                    JFrame frame = (JFrame) currentAnimationWindow;
                    // 确保窗口不可见
                    frame.setVisible(false);
                    // 释放资源
                    frame.dispose();
                }
                
                LOGGER.info("动画窗口已关闭: " + currentAnimationName);
            } catch (Exception e) {
                LOGGER.warning("关闭动画窗口时发生异常: " + e.getMessage());
                e.printStackTrace();
            } finally {
                // 无论如何都要重置引用
                currentAnimationWindow = null;
                currentAnimationName = null;
            }
        }
    }
    
    /**
     * 重置动画状态
     */
    private void resetAnimationState(String algorithmName) {
        isAnimationRunning = false;
        startButton.setEnabled(true);
        currentAnimationWindow = null;
        currentAnimationName = null;
        statusLabel.setText("算法动画已结束：" + algorithmName);
    }
    
    /**
     * 主方法
     */
    // 统计功能已移至标题栏饼图显示，不再需要单独的统计窗口

    /**
     * 主方法
     */
    // 统计饼图面板类
    private class StatsPieChartPanel extends JPanel {
        private Map<String, Integer> difficultyData;
        private Map<String, Integer> categoryData;
        private Color[] pieColors = {
            new Color(54, 162, 235),   // 蓝色 - Easy
            new Color(255, 205, 86),   // 黄色 - Medium  
            new Color(255, 99, 132),   // 红色 - Hard
            new Color(153, 102, 255)   // 紫色 - Atomics
        };
        private Color[] categoryColors = {
            new Color(255, 99, 132),   // 红色 - 数组
            new Color(54, 162, 235),   // 蓝色 - 树
            new Color(255, 205, 86),   // 黄色 - 图
            new Color(75, 192, 192),   // 青色 - 动态规划
            new Color(153, 102, 255),  // 紫色 - 贪心
            new Color(255, 159, 64),   // 橙色 - 搜索
            new Color(199, 199, 199),  // 灰色 - 字符串
            new Color(83, 102, 255),   // 靛蓝 - 位运算
            new Color(255, 99, 255),   // 品红 - 数据结构
            new Color(99, 255, 132),   // 绿色 - 数学
            new Color(255, 206, 84),   // 金色 - 排序
            new Color(54, 162, 132)    // 深青 - 其他
        };
        
        public StatsPieChartPanel() {
            setPreferredSize(new Dimension(800, 80));
            setBackground(new Color(248, 250, 252));
        }
        
        public void updateData() {
            // 统计难度分布
            difficultyData = new LinkedHashMap<>();
            difficultyData.put("简单", 0);
            difficultyData.put("中等", 0);
            difficultyData.put("困难", 0);
            difficultyData.put("原子类", 0);
            
            // 统计分类分布
            categoryData = new LinkedHashMap<>();
            categoryData.put("数组", 0);
            categoryData.put("树", 0);
            categoryData.put("图", 0);
            categoryData.put("动态规划", 0);
            categoryData.put("贪心", 0);
            categoryData.put("搜索", 0);
            categoryData.put("字符串", 0);
            categoryData.put("位运算", 0);
            categoryData.put("数据结构", 0);
            categoryData.put("数学", 0);
            categoryData.put("排序", 0);
            categoryData.put("其他", 0);
            
            // 统计各难度和分类的算法数量
            for (AlgorithmInfo info : algorithmInfos) {
                String difficulty = info.getDifficulty();
                String technique = info.getTechnique();
                
                // 将英文难度映射到中文
                String mappedDifficulty;
                switch (difficulty) {
                    case "Easy":
                        mappedDifficulty = "简单";
                        break;
                    case "Normal":
                        mappedDifficulty = "中等";
                        break;
                    case "Hard":
                        mappedDifficulty = "困难";
                        break;
                    case "Atomics":
                        mappedDifficulty = "原子类";
                        break;
                    default:
                        mappedDifficulty = "简单"; // 默认为简单
                        break;
                }
                Integer count = difficultyData.get(mappedDifficulty);
                if (count != null) {
                    difficultyData.put(mappedDifficulty, count + 1);
                }
                
                // 根据技术标签确定分类
                String category = getCategoryFromTechnique(technique);
                Integer categoryCount = categoryData.get(category);
                if (categoryCount != null) {
                    categoryData.put(category, categoryCount + 1);
                }
            }
            SwingUtilities.invokeLater(() -> statsPieChartPanel.repaint());
        }
        
        private String getCategoryFromTechnique(String technique) {
            if (technique == null) return "其他";
            
            String lowerTechnique = technique.toLowerCase();
            if (lowerTechnique.contains("数组") || lowerTechnique.contains("array")) {
                return "数组";
            } else if (lowerTechnique.contains("树") || lowerTechnique.contains("tree") || lowerTechnique.contains("二叉")) {
                return "树";
            } else if (lowerTechnique.contains("图") || lowerTechnique.contains("graph") || lowerTechnique.contains("岛屿") || lowerTechnique.contains("dfs") || lowerTechnique.contains("bfs")) {
                return "图";
            } else if (lowerTechnique.contains("动态规划") || lowerTechnique.contains("dp") || lowerTechnique.contains("背包")) {
                return "动态规划";
            } else if (lowerTechnique.contains("贪心") || lowerTechnique.contains("greedy")) {
                return "贪心";
            } else if (lowerTechnique.contains("搜索") || lowerTechnique.contains("search") || lowerTechnique.contains("二分")) {
                return "搜索";
            } else if (lowerTechnique.contains("字符串") || lowerTechnique.contains("string") || lowerTechnique.contains("字符")) {
                return "字符串";
            } else if (lowerTechnique.contains("位运算") || lowerTechnique.contains("bit") || lowerTechnique.contains("异或")) {
                return "位运算";
            } else if (lowerTechnique.contains("数据结构") || lowerTechnique.contains("栈") || lowerTechnique.contains("队列") || lowerTechnique.contains("哈希") || lowerTechnique.contains("堆")) {
                return "数据结构";
            } else if (lowerTechnique.contains("数学") || lowerTechnique.contains("math") || lowerTechnique.contains("数位")) {
                return "数学";
            } else if (lowerTechnique.contains("排序") || lowerTechnique.contains("sort")) {
                return "排序";
            } else {
                return "其他";
            }
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int width = getWidth();
            int height = getHeight();
            
            if (difficultyData == null || difficultyData.isEmpty()) {
                g2d.dispose();
                return;
            }
            
            // 计算总数
            int total = difficultyData.values().stream().mapToInt(Integer::intValue).sum();
            if (total == 0) {
                g2d.dispose();
                return;
            }
            
            // 绘制难度分布统计（左侧）
            drawDifficultyChart(g2d, width, height, total);
            
            // 绘制分类分布统计（右侧）
            drawCategoryChart(g2d, width, height, total);
            
            g2d.dispose();
        }
        
        private void drawDifficultyChart(Graphics2D g2d, int width, int height, int total) {
            // 绘制标题
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 12));
            g2d.setColor(new Color(60, 60, 60));
            String title = "算法难度分布统计 (总计: " + total + "个)";
            g2d.drawString(title, 20, 20);
            
            // 饼图参数
            int pieSize = Math.min(height - 40, 50);
            int pieX = 50;
            int pieY = (height - pieSize) / 2 + 10;
            
            // 绘制饼图
            double startAngle = 0;
            int colorIndex = 0;
            
            for (Map.Entry<String, Integer> entry : difficultyData.entrySet()) {
                String difficulty = entry.getKey();
                int count = entry.getValue();
                
                if (count > 0) {
                    double percentage = (double) count / total;
                    double arcAngle = percentage * 360;
                    
                    g2d.setColor(pieColors[colorIndex % pieColors.length]);
                    g2d.fillArc(pieX, pieY, pieSize, pieSize, (int) startAngle, (int) arcAngle);
                    
                    startAngle += arcAngle;
                }
                colorIndex++;
            }
            
            // 绘制饼图边框
            g2d.setColor(Color.GRAY);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawOval(pieX, pieY, pieSize, pieSize);
            
            // 绘制图例
            int legendX = pieX + pieSize + 20;
            int legendY = pieY + 5;
            int legendItemHeight = 15;
            
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 11));
            colorIndex = 0;
            
            for (Map.Entry<String, Integer> entry : difficultyData.entrySet()) {
                String difficulty = entry.getKey();
                int count = entry.getValue();
                double percentage = total > 0 ? (double) count / total * 100 : 0;
                
                // 绘制颜色块
                g2d.setColor(pieColors[colorIndex % pieColors.length]);
                g2d.fillRect(legendX, legendY, 12, 12);
                g2d.setColor(Color.GRAY);
                g2d.drawRect(legendX, legendY, 12, 12);
                
                // 绘制文字
                g2d.setColor(Color.BLACK);
                String legendText = String.format("%s: %d (%.1f%%)", difficulty, count, percentage);
                g2d.drawString(legendText, legendX + 18, legendY + 10);
                
                legendY += legendItemHeight;
                colorIndex++;
            }
        }
        
        private void drawCategoryChart(Graphics2D g2d, int width, int height, int total) {
            if (categoryData == null || categoryData.isEmpty()) {
                return;
            }
            
            // 绘制标题
            g2d.setFont(new Font("微软雅黑", Font.BOLD, 12));
            g2d.setColor(new Color(60, 60, 60));
            String title = "算法分类分布统计";
            g2d.drawString(title, width / 2 + 20, 20);
            
            // 饼图参数
            int pieSize = Math.min(height - 40, 50);
            int pieX = width / 2 + 50;
            int pieY = (height - pieSize) / 2 + 10;
            
            // 绘制饼图
            double startAngle = 0;
            int colorIndex = 0;
            
            for (Map.Entry<String, Integer> entry : categoryData.entrySet()) {
                String category = entry.getKey();
                int count = entry.getValue();
                
                if (count > 0) {
                    double percentage = (double) count / total;
                    double arcAngle = percentage * 360;
                    
                    g2d.setColor(categoryColors[colorIndex % categoryColors.length]);
                    g2d.fillArc(pieX, pieY, pieSize, pieSize, (int) startAngle, (int) arcAngle);
                    
                    startAngle += arcAngle;
                }
                colorIndex++;
            }
            
            // 绘制饼图边框
            g2d.setColor(Color.GRAY);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawOval(pieX, pieY, pieSize, pieSize);
            
            // 绘制图例（只显示有数据的分类）
            int legendX = pieX + pieSize + 20;
            int legendY = pieY + 5;
            int legendItemHeight = 12;
            
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 10));
            colorIndex = 0;
            int displayIndex = 0;
            
            for (Map.Entry<String, Integer> entry : categoryData.entrySet()) {
                String category = entry.getKey();
                int count = entry.getValue();
                
                if (count > 0) {
                    double percentage = total > 0 ? (double) count / total * 100 : 0;
                    
                    // 绘制颜色块
                    g2d.setColor(categoryColors[colorIndex % categoryColors.length]);
                    g2d.fillRect(legendX, legendY + displayIndex * legendItemHeight, 10, 10);
                    g2d.setColor(Color.GRAY);
                    g2d.drawRect(legendX, legendY + displayIndex * legendItemHeight, 10, 10);
                    
                    // 绘制文字
                    g2d.setColor(Color.BLACK);
                    String legendText = String.format("%s: %d (%.1f%%)", category, count, percentage);
                    g2d.drawString(legendText, legendX + 15, legendY + displayIndex * legendItemHeight + 8);
                    
                    displayIndex++;
                }
                colorIndex++;
            }
        }
    }

    private ImageIcon createImageIconFromSVG(String path, int width, int height) {
        try {
            PNGTranscoder transcoder = new PNGTranscoder();
            transcoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, (float) width);
            transcoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, (float) height);

            TranscoderInput input = new TranscoderInput(getClass().getResourceAsStream(path));
            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(ostream);
            transcoder.transcode(input, output);

            return new ImageIcon(ostream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}