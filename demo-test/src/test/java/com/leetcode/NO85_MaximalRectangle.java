/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static java.lang.Math.*;

/**
    （困难）
    85. 最大矩形
        给定一个仅包含 0 和 1 、大小为 rows x cols 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。
    示例 1：
        输入：matrix = { {'1', '0', '1', '0', '0'},
                        {'1', '0', '1', '1', '1'},
                        {'1', '1', '1', '1', '1'},
                        {'1', '0', '0', '1', '0'} }
        输出：6
        解释：最大矩形如上图所示。
    示例 2：
        输入：matrix = {}
        输出：0
    示例 3：
        输入：matrix = {{'0'}}
        输出：0
    示例 4：
        输入：matrix = {{'1'}}
        输出：1
    示例 5：
        输入：matrix = {{'0', '0'}}
        输出：0
*/
public class NO85_MaximalRectangle {

    @Test
    public void test() {
        info(maximalRectangle(new char[][]{{'1', '0', '1', '0', '0'},
                                           {'1', '0', '1', '1', '1'},
                                           {'1', '1', '1', '1', '1'},
                                           {'1', '0', '0', '1', '0'}}));// 6
        info(maximalRectangle(new char[][]{}));// 0
        info(maximalRectangle(new char[][]{{'0'}}));// 0
        info(maximalRectangle(new char[][]{{'1'}}));// 1
        info(maximalRectangle(new char[][]{{'0'}, {'0'}}));// 0
    }

    public int maximalRectangle(char[][] matrix) {
        int m = matrix.length;
        if (m == 0)
            return 0;

        int n = matrix[0].length;
        int[][] left = new int[m][n];

        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (matrix[i][j] == '1')
                    left[i][j] = (j == 0 ? 0 : left[i][j - 1]) + 1;

        int max = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '0')
                    continue;

                int width = left[i][j];
                int area = width;
                for (int k = i - 1; k >= 0; k--) {
                    width = min(width, left[k][j]);
                    area = max(area, (i - k + 1) * width);
                }
                max = max(max, area);
            }
        }
        return max;
    }
}
















/**
// 方法1：
public int maximalRectangle(char[][] matrix) {
    int m = matrix.length;
    if (m == 0)
        return 0;

    int n = matrix[0].length;
    int[][] left = new int[m][n];

    for (int i = 0; i < m; i++)
        for (int j = 0; j < n; j++)
            if (matrix[i][j] == '1')
                left[i][j] = (j == 0 ? 0 : left[i][j - 1]) + 1;

    int max = 0;
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (matrix[i][j] == '0')
                continue;

            int width = left[i][j];
            int area = width;
            for (int k = i - 1; k >= 0; k--) {
                width = min(width, left[k][j]);
                area = max(area, (i - k + 1) * width);
            }
            max = max(max, area);
        }
    }
    return max;
}
*/