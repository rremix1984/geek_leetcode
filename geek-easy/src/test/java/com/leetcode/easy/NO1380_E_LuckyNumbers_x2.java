/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.*;
import static com.leetcode.util.MathUtils.getArray;

/**
    (简单)
    1380. 矩阵中的幸运数
        给你一个 m * n 的矩阵，矩阵中的数字 各不相同 。请你按 任意 顺序返回矩阵中的所有幸运数。
        幸运数 是指矩阵中满足同时下列两个条件的元素：
        在同一行的所有元素中最小
        在同一列的所有元素中最大
    示例 1：
        输入：matrix = {{3, 7, 8}, {9, 11, 13}, {15, 16, 17}}
        输出：{15}
        解释：15 是唯一的幸运数，因为它是其所在行中的最小值，也是所在列中的最大值。
    示例 2：
        输入：matrix = {{1, 10, 4, 2}, {9, 3, 8, 7}, {15, 16, 17, 12}}
        输出：{12}
        解释：12 是唯一的幸运数，因为它是其所在行中的最小值，也是所在列中的最大值。
    示例 3：
        输入：matrix = {{7, 8}, {1, 2}}
        输出：{7}
        解释：7是唯一的幸运数字，因为它是行中的最小值，列中的最大值。
*/
public class NO1380_E_LuckyNumbers_x2 {

    @Test
    public void test() {
        assert getArray(15).equals(luckyNumbers(new int[][]{{3, 7, 8}, {9, 11, 13}, {15, 16, 17}}));
        assert getArray(12).equals(luckyNumbers(new int[][]{{1, 10, 4, 2}, {9, 3, 8, 7}, {15, 16, 17, 12}}));
        assert getArray(7).equals(luckyNumbers(new int[][]{{7, 8}, {1, 2}}));
    }

    public List<Integer> luckyNumbers(int[][] matrix) {
        List<Integer> ret = new ArrayList<>();
        return ret;
    }

}














/**
// 方法1：
public List<Integer> luckyNumbers(int[][] matrix) {
    List<Integer> ret = new ArrayList<>();
    int m = matrix.length;
    int n = matrix[0].length;

    int[] minRow = new int[m];
    int[] maxCol = new int[n];
    Arrays.fill(minRow, Integer.MAX_VALUE);

    // 找出每行最小值（minRow），每列最大值（maxCol）
    for (int i = 0; i < m; i++)
        for (int j = 0; j < n; j++) {
            minRow[i] = min(minRow[i], matrix[i][j]);
            maxCol[j] = max(maxCol[j], matrix[i][j]);
        }

    for (int i = 0; i < m; i++)
        for (int j = 0; j < n; j++)
            if (matrix[i][j] == minRow[i] && matrix[i][j] == maxCol[j])
                ret.add(matrix[i][j]);

    return ret;
}
*/