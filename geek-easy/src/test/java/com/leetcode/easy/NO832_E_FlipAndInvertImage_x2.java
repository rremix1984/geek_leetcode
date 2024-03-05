/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
    [MATRIX]
    (简单)
    832. 翻转图像
        给定一个 n x n 的二进制矩阵 image ，先水平翻转图像，然后反转图像并返回结果 。
        水平翻转图片就是将图片的每一行都进行翻转，即逆序。
        例如，水平翻转 [1,1,0] 的结果是 [0,1,1]。
        反转图片的意思是图片中的 0 全部被 1 替换， 1 全部被 0 替换。
        例如，反转 [0,1,1] 的结果是 [1,0,0]。
    示例 1：
        输入：image = {{1, 1, 0},
                      {1, 0, 1},
                      {0, 0, 0}}
        输出：{{1, 0, 0},
              {0, 1, 0},
              {1, 1, 1}}
        解释：首先翻转每一行: {{0, 1, 1},
                            {1, 0, 1},
                            {0, 0, 0}}；
             然后反转图片: {{1, 0, 0},
                          {0, 1, 0},
                          {1, 1, 1}}
    示例 2：
        输入：image = {{1, 1, 0, 0},
                      {1, 0, 0, 1},
                      {0, 1, 1, 1},
                      {1, 0, 1, 0}}
        输出：{{1, 1, 0, 0},
              {0, 1, 1, 0},
              {0, 0, 0, 1},
              {1, 0, 1, 0}}
        解释：首先翻转每一行: {{0, 0, 1, 1},
                            {1, 0, 0, 1},
                            {1, 1, 1, 0},
                            {0, 1, 0, 1}}；
             然后反转图片: {{1, 1, 0, 0},
                          {0, 1, 1, 0},
                          {0, 0, 0, 1},
                          {1, 0, 1, 0}}
    提示：
        n == image.length
        n == image[i].length
        1 <= n <= 20
        images[i][j] == 0 或 1.
*/
public class NO832_E_FlipAndInvertImage_x2 {

    @Test
    public void test() {
        assertArrayEquals(
                new int[][]{{1, 0, 0},
                            {0, 1, 0},
                            {1, 1, 1}},
                flipAndInvertImage(
                        new int[][] {{1, 1, 0},
                                     {1, 0, 1},
                                     {0, 0, 0}}));
        assertArrayEquals(
                new int[][]{{1, 1, 0, 0},
                            {0, 1, 1, 0},
                            {0, 0, 0, 1},
                            {1, 0, 1, 0}},
                flipAndInvertImage(
                        new int[][]{{1, 1, 0, 0},
                                    {1, 0, 0, 1},
                                    {0, 1, 1, 1},
                                    {1, 0, 1, 0}}));
    }

    public int[][] flipAndInvertImage(int[][] image) {
        return image;
    }

}















/**
// 方法1：
public int[][] flipAndInvertImage(int[][] image) {
    for (int[] row : image) {
        int left = 0;
        int right = image.length - 1;
        while (left < right) {
            if (row[left] == row[right]) {
                row[left] ^= 1;
                row[right] ^= 1;
            }
            left++;
            right--;
        }

        if (left == right)
            row[left] ^= 1;
    }
    return image;
}
*/
