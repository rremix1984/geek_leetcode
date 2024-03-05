/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [MATRIX]
    (简单)
    1572. 矩阵对角线元素的和
        给你一个正方形矩阵 mat，请你返回矩阵对角线元素的和。
        请你返回在矩阵主对角线上的元素和副对角线上且不在主对角线上元素的和。
    示例  1：
        输入：mat = [[1,2,3],
                    [4,5,6],
                    [7,8,9]]
        输出：25
        解释：对角线的和为：1 + 5 + 9 + 3 + 7 = 25
        请注意，元素 mat[1][1] = 5 只会被计算一次。
    示例  2：
        输入：mat = [[1,1,1,1],
                    [1,1,1,1],
                    [1,1,1,1],
                    [1,1,1,1]]
        输出：8
    示例 3：
        输入：mat = [[5]]
        输出：5
*/
public class NO1572_E_DiagonalSum_x2 {

    @Test
    public void test() {
        assert 25 == diagonalSum(new int[][]{{1, 2, 3},
                                             {4, 5, 6},
                                             {7, 8, 9}});
        assert 8 == diagonalSum(new int[][]{{1, 1, 1, 1},
                                            {1, 1, 1, 1},
                                            {1, 1, 1, 1},
                                            {1, 1, 1, 1}});
        assert 5 == diagonalSum(new int[][]{{5}});
    }

    public int diagonalSum(int[][] mat) {
        int sum = 0;
        return sum;
    }

}














/**
// 方法1：
public int diagonalSum(int[][] mat) {
    int sum = 0;
    for (int i = 0; i < mat.length; ++i)
        for (int j = 0; j < mat.length; ++j)
            if (i == j || i + j == mat.length - 1)
                sum += mat[i][j];
    return sum;
}
*/