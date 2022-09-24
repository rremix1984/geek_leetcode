/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.getArray;
import static java.lang.Math.min;
import static org.junit.Assert.assertEquals;

/**
    （中等）
    120. 三角形最小路径和
        给定一个三角形 triangle，找出自顶向下的最小路径和。
        每一步只能移动到下一行中相邻的结点上。相邻的结点在
        这里指的是 下标 与 上一层结点下标 相同或者等于 上一层
        结点下标 + 1 的两个结点。也就是说，如果正位于当前行的
        下标 i ，那么下一步可以移动到下一行的下标 i 或 i + 1 。
    示例 1：
        输入：triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
        输出：11
        解释：如下面简图所示：
             2
            3 4
           6 5 7
          4 1 8 3
        自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）。
    示例 2：
        输入：triangle = [[-10]]
        输出：-10
*/
public class NO120_N_Triangle_x2 {

    @Test
    public void test() {
        ArrayList<ArrayList<Integer>> list = getArray(new int[][]{{2}, {3, 4}, {6, 5, 7}, {4, 1, 8, 3}});
        assertEquals(11, minimumTotal(list)); // 11
        assertEquals(-10, minimumTotal(getArray(new int[][]{{-10}}))); // -10
    }

    public int minimumTotal(ArrayList<ArrayList<Integer>> triangle) {
        return -1;
    }

}













/*
// 方法1：动态规划，一般公式（dp[i][j] = min(dp[i + 1][j], dp[i + 1][j + 1] + a[i][j]）
public int minimumTotal(List<List<Integer>> triangle) {
    int n = triangle.size();
    // dp[i][j] 表示从点 (i, j) 到底边的最小路径和。
    int[][] dp = new int[n + 1][n + 1];
    // 从三角形的最后一行开始递推。
    for (int i = n - 1; i >= 0; i--)
        for (int j = 0; j <= i; j++)
            dp[i][j] = Math.min(dp[i + 1][j], dp[i + 1][j + 1])
                + triangle.get(i).get(j);
    return dp[0][0];
}
*/