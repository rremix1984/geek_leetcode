/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import java.util.concurrent.atomic.AtomicInteger;

/**
    (中等)
    494. 目标和
        给你一个整数数组 nums 和一个整数 target 。
        向数组中的每个整数前添加 '+' 或 '-' ，然后串联起所有整数，可以构造一个 表达式 ：
        例如，nums = [2, 1] ，可以在 2 之前添加 '+' ，在 1 之前添加 '-' ，然后串联起来得到表达式 "+2-1" 。
        返回可以通过上述方法构造的、运算结果等于 target 的不同 表达式 的数目。
    示例 1：
        输入：nums = {1, 1, 1, 1, 1}, target = 3
        输出：5
        解释：一共有 5 种方法让最终目标和为 3 。
        -1 + 1 + 1 + 1 + 1 = 3
        +1 - 1 + 1 + 1 + 1 = 3
        +1 + 1 - 1 + 1 + 1 = 3
        +1 + 1 + 1 - 1 + 1 = 3
        +1 + 1 + 1 + 1 - 1 = 3
    示例 2：
        输入：nums = {1}, target = 1
        输出：1
*/
public class NO494_N_FindTargetSumWays_x2 {

    @Test
    public void test() {
        assert 1 == findTargetSumWays(new int[]{1}, 1);
        assert 5 == findTargetSumWays(new int[]{1, 1, 1, 1, 1}, 3);
    }

    public int findTargetSumWays(int[] nums, int target) {
        int sum = 0;
        for (int num : nums)
            sum += num;

        int diff = sum - target;
        if (diff < 0 || diff % 2 != 0)
            return 0;

        int neg = diff / 2;
        int[] dp = new int[neg + 1];
        dp[0] = 1;
        for (int num : nums)
            for (int j = num; j <= neg; j++)
                dp[j] += dp[j - num];

        return dp[neg];
    }

}

















/**
// 方法1：剪枝法
public int findTargetSumWays(int[] nums, int target) {
    AtomicInteger ans = new AtomicInteger(0);
    call(ans, nums, 0, target);
    return ans.intValue();
}

public void call(AtomicInteger ans, int[] cands, int index, int target) {
    if (0 == target && index == cands.length)
        ans.addAndGet(1);

    if (index != cands.length) {
        call(ans, cands, index + 1, target + cands[index]);
        call(ans, cands, index + 1, target - cands[index]);
        return;
    }

}


// 方法2：动态规划法
// 记数组的元素和为 sum，添加 - 号的元素之和为 neg，则其余添加 + 的元素之和为 sum − neg，得到的表达式的结果为
// 问题转化成在数组 nums 中选取若干元素，使得这些元素之和等于 neg，计算选取元素的方案数。我们可以使用动态规划的方法求解。
// 定义二维数组 dp，其中 dp[i][j] 表示在数组 nums 的前 i 个数中选取元素，使得这些元素之和等于 j 的方案数。
// 假设数组 nums 的长度为 n，则最终答案为 dp[n][neg]。
public int findTargetSumWays(int[] nums, int target) {
    int sum = 0;
    for (int num : nums)
        sum += num;

    int diff = sum - target;
    if (diff < 0 || diff % 2 != 0)
        return 0;

    int neg = diff / 2;
    int[] dp = new int[neg + 1];
    dp[0] = 1;
    for (int num : nums)
        for (int j = neg; j >= num; j--)
            dp[j] += dp[j - num];

    return dp[neg];
}
*/