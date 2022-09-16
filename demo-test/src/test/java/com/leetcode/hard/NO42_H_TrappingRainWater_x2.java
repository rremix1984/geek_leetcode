/**
 * copyright 2022/1/19
 */
package com.leetcode.hard;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    （困难）
    42. 接雨水
        给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
    示例 1：
        输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
        输出：6
        解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。
*/
public class NO42_H_TrappingRainWater_x2 {

    @Test
    public void test() {
        assert 6 == trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1});// 6
    }

    public int trap(int[] height) {
        return -1;
    }

}








/**
// 方法1（动态规划）
public int trap(int[] height) {
    // 初始化
    int ans = 0;
    int len = height.length;
    if (len < 3) {
        return 0;
    }

    // 动态规划数组
    int[] left_arr = new int[len];
    int[] right_arr = new int[len];
    left_arr[0] = height[0];
    right_arr[len-1] = height[len-1];

    // 从左往右找到左边界
    for (int i = 1; i < len; i++) {
        left_arr[i] = Math.max(left_arr[i - 1], height[i]);
    }

    // 从右往左找到右边界
    for (int i = len - 2; i >= 0; i--) {
        right_arr[i] = Math.max(right_arr[i + 1], height[i]);
    }

    // 每个位置对应的积水量（左、右边界的最小值 减去 柱子自身高度 height[i]）
    for (int i = 0; i < len; i++) {
        // 每个柱子都有自己的储水量，所以是累加的关系
        ans += Math.min(left_arr[i], right_arr[i]) - height[i];
    }
    return ans;
}

// 方法2（单调栈）
public int trap(int[] height) {
    Stack<Integer> st = new Stack<>();
    int i = 0;
    int ans = 0;
    while (i < height.length) {
        while (!st.isEmpty() && height[i] > height[st.peek()]) {
            int top = st.pop();
            if (st.empty()) {
                break;
            }
            int distance = i - st.peek() - 1;
            int bounded_height = Math.min(height[i], height[st.peek()]) - height[top];
            ans += distance * bounded_height;
        }
        st.push(i++);
    }
    return ans;
}

// 方法3（双指针）
public int trap(int[] height) {
    int ans = 0, int left_max = 0, right_max = 0;
    int left = 0;
    int right = height.length - 1;
    while (left < right) {
        if (height[left] < height[right]) {
            if (height[left] > left_max) {
                left_max = height[left];
            } else {
                ans += left_max - height[left];
            }
            left++;
        } else {
            if (height[right] > right_max) {
                right_max = height[right];
            } else {
                ans += right_max - height[right];
            }
            right--;
        }
    }
    return ans;
}
*/