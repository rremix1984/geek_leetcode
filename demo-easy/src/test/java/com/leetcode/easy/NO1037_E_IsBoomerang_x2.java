/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    1037. 有效的回旋镖
        给定一个数组 points ，其中 points[i] = [xi, yi] 表示 X-Y 平面上的一个点，如果这些点构成一个 回旋镖 则返回 true 。
        回旋镖 定义为一组三个点，这些点 各不相同 且 不在一条直线上 。
    示例 1：
        输入：points = {{1, 1}, {2, 3}, {3, 2}}
        输出：true
    示例 2：
        输入：points = {{1, 1}, {2, 2}, {3, 3}}
        输出：false

    方法一：向量叉乘
        计算从 points[0] 开始，分别指向 points[1] 和 points[2] 的向量 v1 和 v2。
        「三点各不相同且不在一条直线上」等价于「这两个向量的叉乘结果不为零」：
*/
public class NO1037_E_IsBoomerang_x2 {

    @Test
    public void test() {
        assert isBoomerang(new int[][]{{1, 1}, {2, 3}, {3, 2}});
        assert !isBoomerang(new int[][]{{1, 1}, {2, 2}, {3, 3}});
    }

    public boolean isBoomerang(int[][] points) {
        return false;
    }

}













/**
public boolean isBoomerang(int[][] points) {
    int[] v1 = {points[1][0] - points[0][0],
            points[1][1] - points[0][1]};

    int[] v2 = {points[2][0] - points[0][0],
            points[2][1] - points[0][1]};

    // 不在一条直线上
    return v1[0] * v2[1] - v1[1] * v2[0] != 0;
}
*/