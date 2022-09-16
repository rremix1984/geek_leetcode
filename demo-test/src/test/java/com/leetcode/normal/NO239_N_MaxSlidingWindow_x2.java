/**
copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import java.util.LinkedList;
import static com.leetcode.util.LogUtil.info;
import static org.junit.Assert.assertArrayEquals;

/**
    （困难）
    239. 滑动窗口最大值
    给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
    返回 滑动窗口中的最大值 。

    示例 1：
        输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
        输出：[3,3,5,5,6,7]
        解释：
        滑动窗口的位置                最大值
        ---------------               -----
        [1  3  -1] -3  5  3  6  7       3
        1 [3  -1  -3] 5  3  6  7       3
        1  3 [-1  -3  5] 3  6  7       5
        1  3  -1 [-3  5  3] 6  7       5
        1  3  -1  -3 [5  3  6] 7       6
        1  3  -1  -3  5 [3  6  7]      7

*/
public class NO239_N_MaxSlidingWindow_x2 {

    @Test
    public void test(){
        assertArrayEquals(new int[]{3, 3, 5, 5, 6, 7},
            maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3));// [3, 3, 5, 5, 6, 7]
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] res = new int[nums.length - k + 1];
        return res;
    }
}








/**
public int[] maxSlidingWindow(int[] nums, int k) {
    if (k==0 || nums.length == 0) {
        return new int[0];
    }
    int[] res = new int[nums.length - k + 1];
    LinkedList<Integer> queue = new LinkedList<>();
    int resIndex = 0;
    for (int i = 0; i < nums.length; i++) {
        if (!queue.isEmpty() && i - k == queue.peek()) {
            queue.remove();
        }

        // 如果新入队元素大于老元素，就要把老元素删除
        while (!queue.isEmpty() && nums[i] >= nums[queue.peekLast()]) {
            queue.removeLast();
        }
        queue.add(i);
        if (i >= k - 1)
            res[resIndex++] = nums[queue.peek()];
    }
    return res;
}
*/