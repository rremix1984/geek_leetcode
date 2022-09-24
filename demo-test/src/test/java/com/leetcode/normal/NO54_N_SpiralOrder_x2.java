/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static com.leetcode.util.MathUtils.getArray;

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
public class NO54_N_SpiralOrder_x2 {

    @Test
    public void test() {
        assert getArray(new int[]{1, 2, 3, 6, 9, 8, 7, 4, 5}).toString().equals(
            spiralOrder(new int[][]{{1, 2, 3},
                                    {4, 5, 6},
                                    {7, 8, 9}}).toString());
        assert getArray(new int[]{1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7}).toString().equals(
            spiralOrder(new int[][]{{1, 2, 3, 4},
                                    {5, 6, 7, 8},
                                    {9, 10, 11, 12}}).toString()
        );
        assert getArray(new int[]{3, 2}).toString().equals(
            spiralOrder(new int[][]{{3, 2}}).toString()
        );
    }

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> order = new ArrayList<>();
        return order;
    }

}



















/**
// 方法1：
public List<Integer> spiralOrder(int[][] matrix) {
    List<Integer> order = new ArrayList<>();
    if (matrix == null || matrix.length == 0)
        return order;

    int left = 0;
    int right = matrix[0].length - 1;
    int top = 0;
    int bottom = matrix.length - 1;

    while (left <= right && top <= bottom) {
        for (int i = left; i <= right; i++)
            order.add(matrix[top][i]);

        for (int i = top + 1; i <= bottom; i++)
            order.add(matrix[i][right]);

        if (left < right && top < bottom) {
             for (int i = right - 1; i > left ; i--)
                order.add(matrix[bottom][i]);

             for (int i = bottom; i > top ; i--)
                order.add(matrix[i][left]);
        }
        left++;
        right--;
        top++;
        bottom--;
    }
    return order;
}
*/