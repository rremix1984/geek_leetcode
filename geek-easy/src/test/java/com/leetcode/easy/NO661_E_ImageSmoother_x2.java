/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import static com.leetcode.util.LogUtil.info;
import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    661. 图片平滑器
        图像平滑器 是大小为 3 x 3 的过滤器，用于对图像的每个单元格平滑处理，
        平滑处理后单元格的值为该单元格的平均灰度。
        每个单元格的平均灰度定义为：该单元格自身及其周围的8个单元格的平均值，
        结果需向下取整。（即，需要计算蓝色平滑器中9个单元格的平均值）。
        如果一个单元格周围存在单元格缺失的情况，则计算平均灰度时不考虑缺失的单元格
        （即，需要计算红色平滑器中 4 个单元格的平均值）。
        给你一个表示图像灰度的 m x n 整数矩阵 img ，返回对图像的每个单元格平滑处理后的图像 。
    示例 1:
        输入:img = {{1, 1, 1},
                   {1, 0, 1},
                   {1, 1, 1}}
        输出:{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
        解释:
        对于点 (0, 0),  (0, 2),  (2, 0),  (2, 2): 平均(3/4) = 平均(0.75) = 0
        对于点 (0, 1),  (1, 0),  (1, 2),  (2, 1): 平均(5/6) = 平均(0.83333333) = 0
        对于点 (1, 1): 平均(8/9) = 平均(0.88888889) = 0
    示例 2:
        输入: img = {{100, 200, 100},
                    {200, 50, 200},
                    {100, 200, 100}}
        输出: {{137, 141, 137},
              {141, 138, 141},
              {137, 141, 137}}
        解释:
        对于点 (0, 0), (0, 2), (2, 0), (2, 2): floor((100+200+200+50)/4) = floor(137.5) = 137
        对于点 (0, 1), (1, 0), (1, 2), (2, 1): floor((200 +200+50+200+100+100) / 6) = floor(141.666667) = 141
        对于点 (1, 1): floor((50 + 200 + 200 + 200 + 200 + 100 + 100 + 100 + 100) / 9)
                    = floor(138.888889) = 138
    提示:
        m == img.length
        n == img[i].length
        1 <= m, n <= 200
        0 <= img[i][j] <= 255
*/
public class NO661_E_ImageSmoother_x2 {

    @Test
    public void test() {
        assertArrayEquals(
            new int[][]{{0, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0}},
            imageSmoother(
                new int[][]{{1, 1, 1},
                            {1, 0, 1},
                            {1, 1, 1}}));
        assertArrayEquals(
            new int[][]{{137, 141, 137},
                        {141, 138, 141},
                        {137, 141, 137}},
            imageSmoother(
                new int[][]{{100, 200, 100},
                            {200, 50,  200},
                            {100, 200, 100}}));
    }

    public int[][] imageSmoother(int[][] img) {
        int m = img.length, n = img[0].length;
        int[][] ret = new int[m][n];
        return ret;
    }

}



















/**
// 方法1：
public int[][] imageSmoother(int[][] M) {
    int[][] ret = new int[M.length][M[0].length];
    for (int i = 0; i < M.length; i++) {
        for (int j = 0; j < M[i].length; j++) {
            int num = 1;
            int sum = M[i][j];
            if (i >= 1) {
                sum += M[i-1][j];
                num++;
                if (j>=1) {
                    sum += M[i-1][j-1];
                    num++;
                }
                if (j<M[i].length-1) {
                    sum += M[i - 1][j + 1];
                    num++;
                }
            }
            if (i < M.length - 1) {
                sum += M[i + 1][j];
                num++;
                if (j >= 1) {
                    sum += M[i + 1][j-1];
                    num++;
                }
                if (j < M[i].length-1) {
                    sum += M[i + 1][j + 1];
                    num++;
                }
            }
            if (j >= 1) {
                sum += M[i][j-1];
                num++;
            }
            if (j < M[i].length - 1) {
                sum += M[i][j+1];
                num++;
            }
            ret[i][j] = sum / num;
        }
    }
    return ret;
}

// 方法2：
public int[][] imageSmoother(int[][] img) {
    int m = img.length, n = img[0].length;
    int[][] ret = new int[m][n];
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            // 因为有4（角）、6（边）、9（中）三种可能
            int num = 0;
            int sum = 0;
            for (int x = i - 1; x <= i + 1; x++) {
                for (int y = j - 1; y <= j + 1; y++) {
                    if (x >= 0 && x < m && y >= 0 && y < n) {
                        num++;
                        sum += img[x][y];
                    }
                }
            }
            ret[i][j] = sum / num;
        }
    }
    return ret;
}
*/