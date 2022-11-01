/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;
import java.util.Deque;
import java.util.LinkedList;

/**
    (中等)
    907. 子数组的最小值之和
        给定一个整数数组 arr，找到 min(b) 的总和，其中 b 的范围为 arr 的每个（连续）子数组。
        由于答案可能很大，因此 返回答案模 10^9 + 7 。
    示例 1：
        输入：arr = [3, 1, 2, 4]
        输出：17
        解释：子数组为 [3]，[1]，[2]，[4]，[3, 1]，[1, 2]，
             [2, 4]，[3, 1, 2]，[1, 2, 4]，[3, 1, 2, 4]。
        最小值为 3，1，2，4，1，1，2，1，1，1，和为 17。
    示例 2：
        输入：arr = [11, 81, 94, 43, 3]
        输出：444
    提示：
        1 <= arr.length <= 3 * 104
        1 <= arr[i] <= 3 * 104

 方法二：动态规划:
    具体解法过程如下：
        从左向右遍历数组并维护一个单调递增的栈，如果栈顶的元素大于等于当前元素arr[i]则弹出栈，
    此时栈顶的元素即为左边第一个小于小于当前值的元素；我们求出以当前值为最右且最小的子序列的
    长度 k，根据上述递推公式求出dp[i]，最终的返回值即为：
         n−1
          ∑  dp[i]。
         i=0
*/
public class NO907_N_SumSubarrayMins {

    @Test
    public void test() {
        assert 17 == sumSubarrayMins(new int[]{3, 1, 2, 4});
        assert 444 == sumSubarrayMins(new int[]{11, 81, 94, 43, 3});
    }

    public int sumSubarrayMins(int[] arr) {
        int n = arr.length;
        long ans = 0;
        final int MOD = 1000000007;
        Deque<Integer> monoStack = new LinkedList<>();
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            while (!monoStack.isEmpty() && arr[monoStack.peek()] > arr[i]) {
                monoStack.pop();
            }
            int k = monoStack.isEmpty() ? (i + 1) : (i - monoStack.peek());
            dp[i] = k * arr[i] + (monoStack.isEmpty() ? 0 : dp[i - k]);
            ans = (ans + dp[i]) % MOD;
            monoStack.push(i);
        }
        return (int) ans;
    }

}
















/**
// 方法1：
public int sumSubarrayMins(int[] arr) {
    int n = arr.length;
    Deque<Integer> monoStack = new LinkedList<>();
    int[] left = new int[n];
    int[] right = new int[n];
    for (int i = 0; i < n; i++) {
        while (!monoStack.isEmpty() && arr[i] <= arr[monoStack.peek()])
            monoStack.pop();

        left[i] = i - (monoStack.isEmpty() ? -1 : monoStack.peek());
        monoStack.push(i);
    }
    monoStack.clear();
    for (int i = n - 1; i >= 0; i--) {
        while (!monoStack.isEmpty() && arr[i] < arr[monoStack.peek()])
            monoStack.pop();

        right[i] = (monoStack.isEmpty() ? n : monoStack.peek()) - i;
        monoStack.push(i);
    }
    long ans = 0;
    final int MOD = 1000000007;
    for (int i = 0; i < n; i++)
        ans = (ans + (long) left[i] * right[i] * arr[i]) % MOD;

    return (int) ans;
}
*/