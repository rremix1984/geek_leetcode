/**
 * copyright 2022/1/19
 */
package com.leetcode.hard;

import org.junit.Test;

import static com.leetcode.util.LogUtil.info;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.junit.Assert.assertEquals;

/**
    （困难）
    84. 柱状图中最大的矩形
        给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
        求在该柱状图中，能够勾勒出来的矩形的最大面积。
    示例 1:
        输入：heights = [2,1,5,6,2,3]
        输出：10
        解释：最大的矩形为图中红色区域，面积为 10
    示例 2：
        输入： heights = [2,4]
        输出： 4
 */
public class NO084_H_LargestRectangleArea_x3 {

    @Test
    public void test() {
        assertEquals(10,largestRectangleArea(
            new int[]{2, 1, 5, 6, 2, 3}
        ));// 10
    }

    public int largestRectangleArea(int[] h) {
        int maxarea = 0;
        return maxarea;
    }
}











/*
public int largestRectangleArea(int[] h) {
    int maxarea = 0;
    for (int i = 0; i < h.length; i++) {
        int minheight = Integer.MAX_VALUE;
        for (int j = i; j < h.length; j++) {
            minheight = Math.min(minheight, h[j]);
            maxarea = Math.max(maxarea, minheight * (j - i + 1));
        }
    }
    return maxarea;
}

public int largestRectangleArea(int[] h) {
    int maxarea = 0;
    Stack<Integer> stack = new Stack<>();
    stack.push(-1);
    for (int i = 0; i < h.length; i++) {
        while (stack.peek() != -1 && h[i] <= h[stack.peek()]) {
            maxarea = max(maxarea, h[stack.pop()] * (i - stack.peek() - 1));
        }
        stack.push(i);
    }
    while (stack.peek() != -1)
        maxarea = max(maxarea, h[stack.pop()] * (h.length - stack.peek() - 1));
    return maxarea;
}
*/
