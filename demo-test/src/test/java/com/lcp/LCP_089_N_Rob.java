package com.lcp;

import org.junit.Test;
import static java.lang.Math.max;

/**
    [ARRAY] ||
    (中等)
    LCP.089 打家劫舍
    一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响小偷偷
    窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋
    在同一晚上被小偷闯入，系统会自动报警。
    给定一个代表每个房屋存放金额的非负整数数组 nums ，请计算 不触动警报装置
    的情况下 ，一夜之内能够偷窃到的最高金额。
    示例 1：
        输入：nums = [1, 2, 3, 1]
        输出：4
        解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
        偷窃到的最高金额 = 1 + 3 = 4 。
    示例 2：
        输入：nums = [2, 7, 9, 3, 1]
        输出：12
        解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
        偷窃到的最高金额 = 2 + 9 + 1 = 12 。
    提示：
        1 <= nums.length <= 100
        0 <= nums[i] <= 400
    Related Topics:数组,动态规划
*/
public class LCP_089_N_Rob {

    @Test
    public void test() {
        assert 4 == rob(new int[]{1, 2, 3, 1});
        assert 12 == rob(new int[]{2, 7, 9, 3, 1});
    }

    public int rob(int[] nums) {
        // 2024/3/19 NO.1 动态规划，看懂了，但是写不出来
        // 2024/3/21 NO.2 没做出来
        int n = nums.length;
        int[] dp = new int[n + 1];

        return dp[n];

//        int cur = 0;
//        return cur;
    }

}


















/*
// 方法1：
public int rob(int[] nums) {
    int n = nums.length;
    if (n == 0)
        return 0;

    int[] dp = new int[n + 1];
    dp[1] = nums[0];
    for (int i = 2; i <= n; i++)
        dp[i] = max(dp[i - 1], dp[i - 2] + nums[i - 1]);

    return dp[n];
}

// 方法2：两个指针 cur、pre
private int myRob(int[] nums) {
    // 同NO.089 打家劫舍 I 的思路
    int pre = 0;
    int cur = 0;
    for (int num : nums) {
        int tmp = cur;
        cur = max(pre + num, cur);
        pre = tmp;
    }
    return cur;
}
*/