/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    1582. 二进制矩阵中的特殊位置
        给你一个大小为 rows x cols 的矩阵 mat，其中 mat[i][j] 是 0 或 1，请返回 矩阵 mat 中特殊位置的数目 。
        特殊位置 定义：如果 mat[i][j] == 1 并且第 i 行和第 j 列中的所有其他元素均为 0（行和列的下标均 从 0 开始 ），
        则位置 (i, j) 被称为特殊位置。
    示例 1：
        输入：mat = {{1, 0, 0}, 
                    {0, 0, 1},
                    {1, 0, 0}}
        输出：1
        解释：(1, 2) 是一个特殊位置，因为 mat{1}{2} == 1 且所处的行和列上所有其他元素都是 0
    示例 2：
        输入：mat = {{1, 0, 0}, 
                    {0, 1, 0},
                    {0, 0, 1}}
        输出：3
        解释：(0, 0),  (1, 1) 和 (2, 2) 都是特殊位置
    示例 3：
        输入：mat = {{0, 0, 0, 1}, 
                    {1, 0, 0, 0},
                    {0, 1, 1, 0},
                    {0, 0, 0, 0}}
        输出：2
    示例 4：
        输入：mat = {{0, 0, 0, 0, 0}, 
                    {1, 0, 0, 0, 0},
                    {0, 1, 0, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 0, 1, 1}}
        输出：3
*/
public class NO1582_E_NumSpecial_x2 {

    @Test
    public void test() {
        assert 1 == numSpecial(
                new int[][]{{1, 0, 0},
                            {0, 0, 1},
                            {1, 0, 0}});
        assert 3 == numSpecial(
                new int[][]{{1, 0, 0},
                            {0, 1, 0},
                            {0, 0, 1}});
        assert 2 == numSpecial(
                new int[][]{{0, 0, 0, 1},
                            {1, 0, 0, 0},
                            {0, 1, 1, 0},
                            {0, 0, 0, 0}});
        assert 3 == numSpecial(
                new int[][]{{0, 0, 0, 0, 0},
                            {1, 0, 0, 0, 0},
                            {0, 1, 0, 0, 0},
                            {0, 0, 1, 0, 0},
                            {0, 0, 0, 1, 1}});
    }

    public int numSpecial(int[][] mat) {
        int ret = 0;
        return ret;
    }

}













/**
public int numSpecial(int[][] mat) {
    int ret = 0;
    int m = mat.length;
    int n = mat[0].length;

    // 行的和
    int[] rows = new int[m];

    // 列的和
    int[] cols = new int[n];

    for (int i = 0; i < m; i++)
        for (int j = 0; j < n; j++) {
            // 分别把行列求和
            rows[i] += mat[i][j];
            cols[j] += mat[i][j];
        }

    for (int i = 0; i < m; i++)
        for (int j = 0; j < n; j++)
            // 单元格是1，整行和是1，整列的和也是1
            if (mat[i][j] == 1 && rows[i] == 1 && cols[j] == 1)
                ret++;

    return ret;
}
*/