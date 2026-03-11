package com.animation.launcher;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JFrame;

// 数组算法
import com.animation.array.NO001_E_TwoSum_Animation;
import com.animation.array.NO026_E_RemoveDuplicatesFromSortedArray_Animation;
import com.animation.array.NO027_E_RemoveElement_Animation;
import com.animation.array.NO088_E_MergeSortedArray_Animation;
import com.animation.array.NO283_E_MoveZeroes_Animation;
import com.animation.array.NO977_E_SortedSquares_Animation;
import com.animation.array.NO1051_E_HeightChecker_Animation;
import com.animation.array.NO2529_E_MaximumCount_Animation;
import com.animation.array.NO2535_E_DifferenceOfSum_Animation;
import com.animation.array.NO2540_E_GetCommon_Animation;
import com.animation.array.NO2553_E_SeparateDigits_Animation;
import com.animation.array.NO2562_E_FindTheArrayConcVal_Animation;
import com.animation.array.NO2570_E_MergeArrays_Animation;
import com.animation.array.NO2574_E_LeftRightDifference_Animation;
import com.animation.array.NO2586_E_VowelStrings_Animation;
import com.animation.array.NO2733_E_FindNonMinOrMax_Animation;
import com.animation.array.NO2824_E_CountPairs_Animation;
import com.animation.array.NO2859_E_SumIndicesWithKSetBits_Animation;

// 排序算法
import com.animation.sort.InsertionSortAnimation;

// 数据结构
import com.animation.datastructure.NO225_E_MyStack_Animation;
import com.animation.datastructure.NO234_E_PalindromeLinkedList_Animation;
import com.animation.datastructure.NO703_E_KthLargest_Animation;
import com.animation.datastructure.NO705_E_MyHashSet_Animation;
import com.animation.datastructure.NO706_E_MyHashMap_Animation;

// 搜索算法
import com.animation.search.NO035_E_SearchInsert_Animation;
import com.animation.search.NO704_E_BinarySearch_Animation;

// 动态规划
import com.animation.dp.NO118_E_Generate_Animation;

// 贪心算法
import com.animation.greedy.NO045_N_JumpGameII_Animation;
import com.animation.greedy.NO055_E_JumpGame_Animation;
import com.animation.greedy.NO121_E_BestTimeToBuyAndSellStock_Animation;
import com.animation.greedy.NO1005_E_LargestSumAfterKNegations_Animation;
import com.animation.greedy.NO2498_N_MaxJump_Animation;
import com.animation.greedy.NO2558_E_PickGifts_Animation;
import com.animation.greedy.NO2706_E_BuyChoco_Animation;

// DFS/BFS
import com.animation.dfs.NO1306_N_JumpGameIII_Animation;
import com.animation.bfs.NO1654_N_MinimumJumps_Animation;

// 字符串
import com.animation.string.NO1002_E_CommonChars_Animation;
import com.animation.string.NO2828_E_IsAcronym_Animation;
import com.animation.string.NO2942_E_FindWordsContaining_Animation;

// 位运算
import com.animation.bit.NO136_E_SingleNumber_Animation;
import com.animation.bit.NO190_E_ReverseBits_Animation;

// 图论
import com.animation.graph.NO463_E_IslandPerimeter_Animation;
import com.animation.graph.NO200_N_NumberOfIslands_Animation;
import com.animation.graph.NO695_N_MaxAreaOfIsland_Animation;
import com.animation.graph.NO994_N_RottingOranges_Animation;
import com.animation.graph.GraphDFSAnimation;
import com.animation.graph.GraphBFSAnimation;
import com.animation.graph.DijkstraAnimation;
import com.animation.graph.KruskalMSTAnimation;
import com.animation.graph.TopologicalSortAnimation;

// 数学
import com.animation.math.NO009_E_IsPalindrome_Animation;
import com.animation.math.NO066_E_PlusOne_Animation;
import com.animation.math.NO069_E_SqrtX_Animation;
import com.animation.math.NO2549_E_DistinctIntegers_Animation;
import com.animation.easy.NO1266_E_MinTimeToVisitAllPoints_Animation;

// 树
import com.animation.tree.NO094_E_InorderTraversal_Animation;
import com.animation.tree.NO100_E_IsSameTree_Animation;
import com.animation.tree.NO101_E_IsSymmetric_Animation;
import com.animation.tree.NO104_E_MaximumDepthOfBinaryTree_Animation;
import com.animation.tree.NO108_E_SortedArrayToBST_Animation;
import com.animation.tree.NO144_E_PreorderTraversal_Animation;
import com.animation.tree.NO145_E_PostorderTraversal_Animation;
import com.animation.tree.NO199_N_BinaryTreeRightSideView_Animation;
import com.animation.tree.BinaryTreeTopView_Animation;
import com.animation.tree.BPlusTreeAnimation;

// 中等难度
import com.animation.normal.NO005_N_LongestPalindromicSubstring_Animation;
import com.animation.normal.NO015_N_ThreeSum_Animation;
import com.animation.normal.NO046_N_Permutations_Animation;
import com.animation.normal.NO047_N_PermutationsII_Animation;
import com.animation.normal.NO053_N_MaximumSubarray_Animation;
import com.animation.normal.NO139_N_WordBreak_Animation;
import com.animation.normal.NO208_N_Trie_Animation;
import com.animation.normal.NO211_N_WordDictionary_Animation;
import com.animation.normal.NO284_N_PeekingIterator_Animation;
import com.animation.normal.NO322_N_CoinChange_Animation;
import com.animation.normal.NO764_N_OrderOfLargestPlusSign_Animation;
import com.animation.normal.NO784_N_LetterCasePermutation_Animation;
import com.animation.normal.NO915_N_PartitionDisjoint_Animation;

// 原子类
import com.animation.atomics.AtomicIntegerAnimation;
import com.animation.atomics.AtomicReferenceAnimation;
import com.animation.atomics.LongAdderAnimation;
import com.animation.atomics.AtomicIntegerArrayAnimation;
import com.animation.atomics.AtomicStampedReferenceAnimation;
import com.animation.atomics.AtomicMarkableReferenceAnimation;

// 区间
import com.animation.interval.Interval_02_05_N_AddTwoNumbers_Animation;
import com.animation.interval.Interval_04_05_N_IsValidBST_Animation;
import com.animation.interval.Interval_04_06_N_InorderSuccessor_Animation;
import com.animation.interval.Interval_04_10_N_CheckSubTree_Animation;
import com.animation.interval.Interval_04_12_N_PathSum_Animation;
import com.animation.interval.Interval_08_09_N_GenerateParenthesis_Animation;

// 其他
import com.animation.lonch.NO5_MatrixLinkedListAnimation;
import com.animation.lonch.NO6_MatrixLinkedListShortestPathAnimation;
import com.animation.lonch.NO9_SnakeGame_Animation;
import com.animation.lonch.NO10_BinaryTree_Animation;
import com.animation.lonch.NO11_BinaryTreeTraversal_Animation;

// 困难难度
import com.animation.hard.NO023_H_MergeKSortedLists_Animation;
import com.animation.hard.NO403_H_FrogJump_Animation;
import com.animation.hard.NO51_H_NQueens_Animation;
import com.animation.hard.NO52_H_NQueensII_Animation;
import com.animation.hard.NO85_H_MaximalRectangle_Animation;
import com.animation.hard.NO773_H_SlidingPuzzle_Animation;
import com.animation.hard.NO1206_H_Skiplist_Animation;
import com.animation.hard.NO1235_H_JobScheduling_Animation;

/**
 * 算法动画注册中心
 * 统一管理所有算法动画的注册和获取
 */
public class AnimationRegistry {
    
    private final Map<String, Runnable> animations = new HashMap<>();
    
    public AnimationRegistry() {
        registerAll();
    }
    
    private void registerAll() {
        // ===== 数组算法 =====
        register("NO.001 两数之和", () -> new NO001_E_TwoSum_Animation().setVisible(true));
        register("NO.026 删除有序数组中的重复项", () -> new NO026_E_RemoveDuplicatesFromSortedArray_Animation().setVisible(true));
        register("NO.027 移除元素", () -> new NO027_E_RemoveElement_Animation().setVisible(true));
        register("NO.088 合并两个有序数组", () -> new NO088_E_MergeSortedArray_Animation().setVisible(true));
        register("NO.283 移动零", () -> new NO283_E_MoveZeroes_Animation().setVisible(true));
        register("NO.977 有序数组的平方", () -> new NO977_E_SortedSquares_Animation().setVisible(true));
        register("NO.1051 高度检查器", () -> new NO1051_E_HeightChecker_Animation().setVisible(true));
        register("NO.2529 正整数和负整数的最大计数", () -> new NO2529_E_MaximumCount_Animation().setVisible(true));
        register("NO.2535 数组元素和与数字和的绝对差", () -> new NO2535_E_DifferenceOfSum_Animation().setVisible(true));
        register("NO.2540 最小公共值", () -> new NO2540_E_GetCommon_Animation().setVisible(true));
        register("NO.2553 分割数组中数字的数位", () -> new NO2553_E_SeparateDigits_Animation().setVisible(true));
        register("NO.2562 数组连接值", () -> new NO2562_E_FindTheArrayConcVal_Animation().setVisible(true));
        register("NO.2570 合并两个二维数组", () -> new NO2570_E_MergeArrays_Animation().setVisible(true));
        register("NO.2574 左右差值", () -> new NO2574_E_LeftRightDifference_Animation().setVisible(true));
        register("NO.2586 元音字符串", () -> new NO2586_E_VowelStrings_Animation().setVisible(true));
        register("NO.2733 找非最小最大值", () -> new NO2733_E_FindNonMinOrMax_Animation().setVisible(true));
        register("NO.2824 统计和小于目标的下标对数目", () -> new NO2824_E_CountPairs_Animation().setVisible(true));
        register("NO.2859 K位索引和", () -> new NO2859_E_SumIndicesWithKSetBits_Animation().setVisible(true));
        
        // ===== 排序算法 =====
        register("插入排序算法演示", () -> new InsertionSortAnimation().setVisible(true));
        
        // ===== 数据结构 =====
        register("NO.225 用队列实现栈", () -> new NO225_E_MyStack_Animation().setVisible(true));
        register("NO.234 回文链表", () -> new NO234_E_PalindromeLinkedList_Animation().setVisible(true));
        register("NO.703 数据流中的第K大元素", () -> new NO703_E_KthLargest_Animation().setVisible(true));
        register("NO.705 设计哈希集合", () -> new NO705_E_MyHashSet_Animation().setVisible(true));
        register("NO.706 设计哈希映射", () -> new NO706_E_MyHashMap_Animation().setVisible(true));
        
        // ===== 搜索算法 =====
        register("NO.035 搜索插入位置", () -> new NO035_E_SearchInsert_Animation().setVisible(true));
        register("NO.704 二分查找", () -> new NO704_E_BinarySearch_Animation().setVisible(true));
        
        // ===== 动态规划 =====
        register("NO.118 杨辉三角", () -> new NO118_E_Generate_Animation().setVisible(true));
        
        // ===== 贪心算法 =====
        register("NO.045 跳跃游戏 II", () -> new NO045_N_JumpGameII_Animation().setVisible(true));
        register("NO.055 跳跃游戏", () -> new NO055_E_JumpGame_Animation().setVisible(true));
        register("NO.121 买卖股票的最佳时机", () -> new NO121_E_BestTimeToBuyAndSellStock_Animation().setVisible(true));
        register("NO.1005 K次取反后最大化数组和", () -> new NO1005_E_LargestSumAfterKNegations_Animation().setVisible(true));
        register("NO.2498 青蛙过河 II", () -> new NO2498_N_MaxJump_Animation().setVisible(true));
        register("NO.2558 从数量最多的堆取走礼物", () -> new NO2558_E_PickGifts_Animation().setVisible(true));
        register("NO.2706 购买两块巧克力", () -> new NO2706_E_BuyChoco_Animation().setVisible(true));
        
        // ===== DFS =====
        register("NO.1306 跳跃游戏 III", () -> new NO1306_N_JumpGameIII_Animation().setVisible(true));
        
        // ===== BFS =====
        register("NO.1654 到家的最少跳跃次数", () -> new NO1654_N_MinimumJumps_Animation().setVisible(true));
        
        // ===== 字符串 =====
        register("NO.1002 查找常用字符", () -> new NO1002_E_CommonChars_Animation().setVisible(true));
        register("NO.2828 是否首字母缩写", () -> new NO2828_E_IsAcronym_Animation().setVisible(true));
        register("NO.2942 查找包含字符的单词", () -> new NO2942_E_FindWordsContaining_Animation().setVisible(true));
        
        // ===== 位运算 =====
        register("NO.136 只出现一次的数字", () -> new NO136_E_SingleNumber_Animation().setVisible(true));
        register("NO.190 颠倒二进制位", () -> new NO190_E_ReverseBits_Animation().setVisible(true));
        
        // ===== 图论算法 =====
        register("NO.463 岛屿的周长", () -> new NO463_E_IslandPerimeter_Animation().setVisible(true));
        register("NO.200 岛屿数量", () -> new NO200_N_NumberOfIslands_Animation().setVisible(true));
        register("NO.695 岛屿的最大面积", () -> new NO695_N_MaxAreaOfIsland_Animation().setVisible(true));
        register("NO.994 腐烂的橘子", () -> new NO994_N_RottingOranges_Animation().setVisible(true));
        register("图的深度优先搜索(DFS)", () -> new GraphDFSAnimation().setVisible(true));
        register("图的广度优先搜索(BFS)", () -> new GraphBFSAnimation().setVisible(true));
        register("Dijkstra最短路径算法", () -> new DijkstraAnimation().setVisible(true));
        register("Kruskal最小生成树算法", () -> new KruskalMSTAnimation().setVisible(true));
        register("拓扑排序算法", () -> new TopologicalSortAnimation().setVisible(true));
        
        // ===== 数学算法 =====
        register("NO.009 回文数", () -> new NO009_E_IsPalindrome_Animation().setVisible(true));
        register("NO.066 加一", () -> new NO066_E_PlusOne_Animation().setVisible(true));
        register("NO.069 x的平方根", () -> new NO069_E_SqrtX_Animation().setVisible(true));
        register("NO.1266 访问所有点的最小时间", () -> new NO1266_E_MinTimeToVisitAllPoints_Animation().setVisible(true));
        register("NO.2549 不同整数的个数", () -> new NO2549_E_DistinctIntegers_Animation().setVisible(true));
        
        // ===== 树算法 =====
        register("NO.094 二叉树的中序遍历", () -> new NO094_E_InorderTraversal_Animation().setVisible(true));
        register("NO.100 相同的树", () -> new NO100_E_IsSameTree_Animation().setVisible(true));
        register("NO.101 对称二叉树", () -> new NO101_E_IsSymmetric_Animation().setVisible(true));
        register("NO.104 二叉树的最大深度", () -> new NO104_E_MaximumDepthOfBinaryTree_Animation().setVisible(true));
        register("NO.108 将有序数组转换为二叉搜索树", () -> new NO108_E_SortedArrayToBST_Animation().setVisible(true));
        register("NO.144 二叉树的前序遍历", () -> new NO144_E_PreorderTraversal_Animation().setVisible(true));
        register("NO.145 二叉树的后序遍历", () -> new NO145_E_PostorderTraversal_Animation().setVisible(true));
        register("NO.199 二叉树的右视图", () -> new NO199_N_BinaryTreeRightSideView_Animation().setVisible(true));
        register("二叉树的顶层视图", () -> new BinaryTreeTopView_Animation().setVisible(true));
        register("B+树动画", () -> new BPlusTreeAnimation().setVisible(true));
        
        // ===== 中等难度 =====
        register("NO.005 最长回文子串", () -> new NO005_N_LongestPalindromicSubstring_Animation().setVisible(true));
        register("NO.015 三数之和", () -> new NO015_N_ThreeSum_Animation().setVisible(true));
        register("NO.046 全排列", () -> new NO046_N_Permutations_Animation().setVisible(true));
        register("NO.047 全排列 II", () -> new NO047_N_PermutationsII_Animation().setVisible(true));
        register("NO.053 最大子数组和", () -> new NO053_N_MaximumSubarray_Animation().setVisible(true));
        register("NO.139 单词拆分", () -> new NO139_N_WordBreak_Animation().setVisible(true));
        register("NO.208 实现 Trie", () -> new NO208_N_Trie_Animation().setVisible(true));
        register("NO.211 添加与搜索单词", () -> new NO211_N_WordDictionary_Animation().setVisible(true));
        register("NO.284 窥探迭代器", () -> new NO284_N_PeekingIterator_Animation().setVisible(true));
        register("NO.322 零钱兑换", () -> new NO322_N_CoinChange_Animation().setVisible(true));
        register("NO.764 最大加号标志", () -> new NO764_N_OrderOfLargestPlusSign_Animation().setVisible(true));
        register("NO.784 字母大小写全排列", () -> new NO784_N_LetterCasePermutation_Animation().setVisible(true));
        register("NO.915 分割数组", () -> new NO915_N_PartitionDisjoint_Animation().setVisible(true));
        
        // ===== 原子类 =====
        register("AtomicInteger 动画", () -> new AtomicIntegerAnimation().setVisible(true));
        register("AtomicReference 动画", () -> new AtomicReferenceAnimation().setVisible(true));
        register("LongAdder 动画", () -> new LongAdderAnimation().setVisible(true));
        register("AtomicIntegerArray 动画", () -> new AtomicIntegerArrayAnimation().setVisible(true));
        register("AtomicStampedReference 动画", () -> new AtomicStampedReferenceAnimation().setVisible(true));
        register("AtomicMarkableReference 动画", () -> new AtomicMarkableReferenceAnimation().setVisible(true));
        
        // ===== 区间问题 =====
        register("NO.02.05 两数相加", () -> new Interval_02_05_N_AddTwoNumbers_Animation().setVisible(true));
        register("NO.04.05 验证BST", () -> new Interval_04_05_N_IsValidBST_Animation().setVisible(true));
        register("NO.04.06 中序后继", () -> new Interval_04_06_N_InorderSuccessor_Animation().setVisible(true));
        register("NO.04.10 检查子树", () -> new Interval_04_10_N_CheckSubTree_Animation().setVisible(true));
        register("NO.04.12 路径总和", () -> new Interval_04_12_N_PathSum_Animation().setVisible(true));
        register("NO.08.09 括号生成", () -> new Interval_08_09_N_GenerateParenthesis_Animation().setVisible(true));
        
        // ===== 其他 =====
        register("NO.5 矩阵链表", () -> new NO5_MatrixLinkedListAnimation().setVisible(true));
        register("NO.6 矩阵链表最短路径", () -> new NO6_MatrixLinkedListShortestPathAnimation().setVisible(true));
        register("NO.9 贪吃蛇", () -> new NO9_SnakeGame_Animation().setVisible(true));
        register("NO.10 二叉树", () -> new NO10_BinaryTree_Animation().setVisible(true));
        register("NO.11 二叉树遍历", () -> new NO11_BinaryTreeTraversal_Animation().setVisible(true));
        
        // ===== 困难难度 =====
        register("NO.023 合并K个升序链表", () -> new NO023_H_MergeKSortedLists_Animation().setVisible(true));
        register("NO.403 青蛙过河", () -> new NO403_H_FrogJump_Animation().setVisible(true));
        register("NO.51 N皇后", () -> new NO51_H_NQueens_Animation().setVisible(true));
        register("NO.52 N皇后 II", () -> new NO52_H_NQueensII_Animation(8).setVisible(true));
        register("NO.85 最大矩形", () -> new NO85_H_MaximalRectangle_Animation().setVisible(true));
        register("NO.773 滑动谜题", () -> new NO773_H_SlidingPuzzle_Animation().setVisible(true));
        register("NO.1206 跳表", () -> new NO1206_H_Skiplist_Animation().setVisible(true));
        register("NO.1235 工作调度", () -> new NO1235_H_JobScheduling_Animation().setVisible(true));
    }
    
    private void register(String name, Runnable launcher) {
        animations.put(name, launcher);
    }
    
    public Map<String, Runnable> getAnimations() {
        return animations;
    }
    
    public void launch(String name) {
        Runnable launcher = animations.get(name);
        if (launcher != null) {
            launcher.run();
        }
    }
    
    public boolean hasAnimation(String name) {
        return animations.containsKey(name);
    }
    
    public int size() {
        return animations.size();
    }
}
