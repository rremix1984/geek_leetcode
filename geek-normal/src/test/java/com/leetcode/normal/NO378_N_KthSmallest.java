package com.leetcode.normal;

import org.junit.Test;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

import static java.util.Arrays.sort;

/**
    [ARRAY] |||
    (中等)
    NO.378 有序矩阵中第K小的元素
    给你一个 n x n 矩阵 matrix ，其中每行和每列元素均按升序排序，
    找到矩阵中第 k 小的元素。请注意，它是排序后的第k小元素，
    而不是第 k 个不同的元素。你必须找到一个内存复杂度优于 O(n2) 的解决方案。
    示例 1：
        输入：matrix = [[ 1,  5,  9],
                       [10, 11, 13],
                       [12, 13, 15]], k = 8
        输出：13
        解释：矩阵中的元素为 [1, 5, 9, 10, 11, 12, 13, 13, 15]，
             第 8 小元素是 13
    示例 2：
        输入：matrix = [[-5]], k = 1
        输出：-5
    提示：
        n == matrix.length
        n == matrix[i].length
        1 <= n <= 300
        -109 <= matrix[i][j] <= 109
        题目数据 保证 matrix 中的所有行和列都按 非递减顺序 排列
        1 <= k <= n2
        进阶：
        你能否用一个恒定的内存(即 O(1) 内存复杂度)来解决这个问题?
        你能在 O(n) 的时间复杂度下解决这个问题吗?这个方法对于面试来说可能太超前了，但是你会发现阅读这篇文章（ this paper ）很有趣。
    Related Topics:
        数组:二分查找,矩阵,排序,堆（优先队列）
*/
public class NO378_N_KthSmallest {

    @Test
    public void test() {
        assert 13 == kthSmallest(
            new int[][]{{ 1,  5,  9},
                        {10, 11, 13},
                        {12, 13, 15}}, 8);
        assert -5 == kthSmallest(
            new int[][]{{-5}},1);
    }

    public int kthSmallest(int[][] matrix, int k) {
        // 2024/3/15 NO.1
        // 2024/3/18-21 NO.2-3 做出来了
        return -1;
    }

}
















/*
// 方法1：
public int kthSmallest(int[][] matrix, int k) {
    int row = matrix.length;
    int col = matrix[0].length;
    int[] sorted = new int[row * col];
    int index = 0;
    for (int[] r : matrix)
        for (int num : r)
            sorted[index++] = num;

    return sorted[k - 1];
}
*/