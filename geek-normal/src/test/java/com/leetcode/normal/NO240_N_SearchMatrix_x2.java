/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;

/**
    (中等)
    240. 搜索二维矩阵 II
        编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target 。
        该矩阵具有以下特性：
            1）每行的元素从左到右升序排列。
            2）每列的元素从上到下升序排列。
    示例 1：
        输入：matrix = {{ 1, 4, 7,11,15},
                       { 2, 5, 8,12,19},
                       { 3, 6, 9,16,22},
                       {10,13,14,17,24},
                       {18,21,23,26,30}}, target = 5
        输出：true
    示例 2：
        输入：matrix = {{ 1, 4, 7,11,15},
                       { 2, 5, 8,12,19},
                       { 3, 6, 9,16,22},
                       {10,13,14,17,24},
                       {18,21,23,26,30}}, target = 20
        输出：false
    提示：
        m == matrix.length
        n == matrix[i].length
        1 <= n, m <= 300
        -109 <= matrix[i][j] <= 109
        每行的所有元素从左到右升序排列
        每列的所有元素从上到下升序排列
        -109 <= target <= 109
*/
public class NO240_N_SearchMatrix_x2 {

    @Test
    public void test() {
        assert searchMatrix(
            new int[][]{{ 1, 4, 7,11,15},
                        { 2, 5, 8,12,19},
                        { 3, 6, 9,16,22},
                        {10,13,14,17,24},
                        {18,21,23,26,30}}, 5);
        assert !searchMatrix(
            new int[][]{{ 1, 4, 7,11,15},
                        { 2, 5, 8,12,19},
                        { 3, 6, 9,16,22},
                        {10,13,14,17,24},
                        {18,21,23,26,30}}, 20);
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        int l = 0;
        int r = matrix[0].length - 1;
        while (l < matrix.length && r >= 0) {
            if (matrix[l][r] == target)
                return true;

            if (matrix[l][r] > target)
                r--;
            else
                l++;
        }
        return false;
    }

}


















/**
// 方法1：
public boolean searchMatrix(int[][] matrix, int target) {
    int l = 0;
    int r = matrix[0].length - 1;
    while (l < matrix.length && r >= 0) {
        if (matrix[l][r] == target)
            return true;

        if (matrix[l][r] > target)
            r--;
        else
            l++;
    }
    return false;
}
*/