/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
 * 给定一个长度为 n 的整数数组height。有n条垂线，第 i 条线的两个端点是(i, 0)和(i, height[i])。
 *
 * 找出其中的两条线，使得它们与x轴共同构成的容器可以容纳最多的水。
 *
 * 返回容器可以储存的最大水量。
 *
 * 说明：你不能倾斜容器。
 *      输入：[1,8,6,2,5,4,8,3,7]
 *      输出：49
 *      解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。
 *      在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
 *
 *     8              8     |
 *     |--------------|-----7
 *     |---6----------|-----|
 *     |---|-----5----|-----|
 *     |---|-----|-4--|-----|
 *     |---|-----|-|--|--3--|
 *     |---|--2--|-|--|--|--|
 *     |---|--|--|-|--|--|--|
 *
 * 示例 2：
 *      输入：height = [1,1]
 *      输出：1
 *
 *      [4,3,2,1,4] = 16
 */
public class NO11_ContainerWithMostWater {

    @Test
    public void test() {
//        info(maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));// 49
//        info(maxArea(new int[]{1, 1}));// 1
        info(maxArea(new int[]{4, 3, 2, 1, 4}));// 16
    }

    public int maxArea(int[] a) {
        int max = 0;
        for (int i = 0, j = a.length - 1; i < j;) {
            int min = a[i] < a[j]?a[i++]:a[j--];
            max = Math.max(max, (j - i + 1) * min);
        }
        return max;
    }

}






/**
public int maxArea(int[] a) {
     int max = 0;
     for (int i = 0, j = a.length - 1; i < j; ) {
         int min = a[i] < a[j] ? a[i++] : a[j--];
         int area = (j - i + 1) * min;
         max = Math.max(max, area);
     }
     return max;
}
*/