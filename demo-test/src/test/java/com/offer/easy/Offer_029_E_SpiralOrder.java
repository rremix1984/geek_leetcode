/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    剑指 Offer 29. 顺时针打印矩阵
        输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字。
    示例 1：
        输入：matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}
        输出：{1, 2, 3, 6, 9, 8, 7, 4, 5}
    示例 2：
        输入：matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}}
        输出：{1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7}
*/
public class Offer_029_E_SpiralOrder {

    @Test
    public void test() {
        assertArrayEquals(new int[]{1, 2, 3, 6, 9, 8, 7, 4, 5},
            spiralOrder(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}));
        assertArrayEquals(new int[]{1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7},
            spiralOrder(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}}));
        assertArrayEquals(new int[]{3, 2},
            spiralOrder(new int[][]{{3}, {2}}));
    }

    public int[] spiralOrder(int[][] matrix) {
        return spiralOrderInner(matrix).stream().mapToInt(Integer::valueOf).toArray();
    }

    public List<Integer> spiralOrderInner(int[][] matrix) {
        List<Integer> order = new ArrayList<>();
        return order;
    }

}

















/**
public int[] spiralOrder(int[][] matrix) {
    List<Integer> order = new ArrayList<>();
    if (matrix == null || matrix.length == 0)
        return order.stream().mapToInt(Integer::valueOf).toArray();

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
    return order.stream().mapToInt(Integer::valueOf).toArray();
}
*/