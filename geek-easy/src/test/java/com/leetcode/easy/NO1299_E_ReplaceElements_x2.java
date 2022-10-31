/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import static java.lang.Math.max;
import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    1299. 将每个元素替换为右侧最大元素
        给你一个数组 arr ，请你将每个元素用它右边最大的元素替换，
        如果是最后一个元素，用 -1 替换。完成所有替换操作后，请你返回这个数组。
    示例 1：
        输入：arr = {17, 18, 5, 4, 6, 1}
        输出：{18, 6, 6, 6, 1, -1}
        解释：- 下标 0 的元素 --> 右侧最大元素是下标 1 的元素 (18)
             - 下标 1 的元素 --> 右侧最大元素是下标 4 的元素 (6)
             - 下标 2 的元素 --> 右侧最大元素是下标 4 的元素 (6)
             - 下标 3 的元素 --> 右侧最大元素是下标 4 的元素 (6)
             - 下标 4 的元素 --> 右侧最大元素是下标 5 的元素 (1)
             - 下标 5 的元素 --> 右侧没有其他元素，替换为 -1
    示例 2：
        输入：arr = {400}
        输出：{-1}
        解释：下标 0 的元素右侧没有其他元素。

    方法一：逆序遍历
        本题等价于对于数组 arr 中的每个元素 arr[i]，将其替换成 arr[i + 1], arr[i + 2], ..., arr[n - 1] 中的最大值。
        因此我们可以逆序地遍历整个数组，同时维护从数组右端到当前位置所有元素的最大值。
        设 ans[i] = max(arr[i + 1], arr[i + 2], ..., arr[n - 1])，那么在进行逆序遍历时，
        我们可以直接通过 ans[i] = max(ans[i + 1], arr[i + 1])  来递推地得到答案。
*/
public class NO1299_E_ReplaceElements_x2 {

    @Test
    public void test() {
        assertArrayEquals(new int[]{18, 6, 6, 6, 1, -1},
                replaceElements(new int[]{17, 18, 5, 4, 6, 1}));
        assertArrayEquals(new int[]{-1},
                replaceElements(new int[]{400}));
    }

    public int[] replaceElements(int[] arr) {
        int[] ans = new int[arr.length];
        ans[arr.length - 1] = -1;
        return ans;
    }
    
}



















/**
// 方法1：
public int[] replaceElements(int[] arr) {
    int[] ans = new int[arr.length];
    // 审题可知，最后一个元素一定是 -1
    ans[arr.length - 1] = -1;

    // 从倒数第二个元素开始，向前遍历
    for (int i = arr.length - 2; i >= 0; i--)
        ans[i] = max(ans[i + 1], arr[i + 1]);

    return ans;
}
*/
