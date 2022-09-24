/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

/**
    (中等)
    54. 螺旋矩阵
        给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。
    示例 1：
        输入：matrix = [[1, 2, 3],[4, 5, 6],[7, 8, 9]]
        输出：[1, 2, 3, 6, 9, 8, 7, 4, 5]
    示例 2：
        输入：matrix = [[1, 2, 3, 4], [5, 6, 7, 8], [9, 10, 11, 12]]
        输出：[1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7]
*/
public class NO54_N_SpiralOrder {

    @Test
    public void test() {
        assert new ArrayList<Integer>(){{
            add(1);add(2);add(3);add(6);add(9);add(8);add(7);add(4);add(5);
        }}.toString().equals(
            spiralOrder(new int[][]{{1, 2, 3},
                                    {4, 5, 6},
                                    {7, 8, 9}}).toString())
        && new ArrayList<Integer>(){{
            add(1);add(2);add(3);add(4);add(8);add(12);add(11);add(10);add(9);add(5);add(6);add(7);
        }}.toString().equals(
            spiralOrder(new int[][]{{1, 2, 3, 4},
                                    {5, 6, 7, 8},
                                    {9, 10, 11, 12}}).toString()
        );
    }

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return res;

        int rows = matrix.length, columns = matrix[0].length;
        int left = 0, right = columns - 1, top = 0, bottom = rows - 1;
        while (left <= right && top <= bottom) {
            for (int column = left; column <= right; column++)
                res.add(matrix[top][column]);

            for (int row = top + 1; row <= bottom; row++)
                res.add(matrix[row][right]);

            if (left < right && top < bottom)
                for (int column = right - 1; column > left; column--)
                    res.add(matrix[bottom][column]);

                for (int row = bottom; row > top; row--)
                    res.add(matrix[row][left]);

            left++;
            right--;
            top++;
            bottom--;
        }
        return res;
    }

}



















/**
// 方法1：
public List<Integer> spiralOrder(int[][] matrix) {
    List<Integer> order = new ArrayList<>();
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
        return order;
    }
    int rows = matrix.length, columns = matrix[0].length;
    int left = 0, right = columns - 1, top = 0, bottom = rows - 1;
    while (left <= right && top <= bottom) {
        for (int column = left; column <= right; column++) {
            order.add(matrix[top][column]);
        }
        for (int row = top + 1; row <= bottom; row++) {
            order.add(matrix[row][right]);
        }
        if (left < right && top < bottom) {
            for (int column = right - 1; column > left; column--) {
                order.add(matrix[bottom][column]);
            }
            for (int row = bottom; row > top; row--) {
                order.add(matrix[row][left]);
            }
        }
        left++;
        right--;
        top++;
        bottom--;
    }
    return order;
}
*/