/**
 * copyright 2022/1/19
 */
package com.leetcode.offer;

import org.junit.Test;

/**
    (中等)
    剑指 Offer 04. 二维数组中的查找
        在一个 n * m 的二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个高效的函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
    示例:
        现有矩阵 matrix 如下：
        {{1,   4,  7, 11, 15},
        {2,   5,  8, 12, 19},
        {3,   6,  9, 16, 22},
        {10, 13, 14, 17, 24},
        {18, 21, 23, 26, 30}}
    给定 target = 5，返回 true。
    给定 target = 20，返回 false。
 */
public class Offer_04_N_FindNumberIn2DArray_x2 {

    @Test
    public void test() {
        assert findNumberIn2DArray(new int[][]
               {{1,   4,  7, 11, 15},
                {2,   5,  8, 12, 19},
                {3,   6,  9, 16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}}, 5);
        assert !findNumberIn2DArray(new int[][]
               {{1,   4,  7, 11, 15},
                {2,   5,  8, 12, 19},
                {3,   6,  9, 16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}}, 20);
        assert !findNumberIn2DArray(new int[][]
                {{1, 1}}, 2);
        assert !findNumberIn2DArray(new int[][]
                {{}}, 0);
    }

    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        return false;
    }

}

















/**
// 方法1：
public boolean findNumberIn2DArray(int[][] matrix, int target) {
    int r = matrix.length - 1;
    int l = 0;
    while (r >= 0 && l < matrix[0].length)
        if (matrix[r][l] > target)
            r--;
        else if (matrix[r][l] < target)
            l++;
        else
            return true;
    return false;
}
*/