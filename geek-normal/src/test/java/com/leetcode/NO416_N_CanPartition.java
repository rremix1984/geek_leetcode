package com.leetcode;

import org.junit.Test;
import java.util.Arrays;

import static com.leetcode.util.SystemUtil.printArr;
import static com.leetcode.util.SystemUtil.printArrs;
import static java.util.Arrays.stream;

/**
    [ARRAY] |||
    (中等)
    NO.416 分割等和子集
    给你一个只包含正整数的非空数组nums。请你判断是否可以将这个数组分割成两个子集，
    使得两个子集的元素和相等。
    示例 1：
        输入：nums = [1, 5, 11, 5]
        输出：true
        解释：数组可以分割成 [1, 5, 5] 和 [11] 。
    示例 2：
        输入：nums = [1, 2, 3, 5]
        输出：false
        解释：数组不能分割成两个元素和相等的子集。
    提示：
        1 <= nums.length <= 200
        1 <= nums[i] <= 100
    Related Topics:数组,动态规划
    解题思路：
        这个过程就像是你和你的旅伴交替从一堆共享的装备中挑选
    装备放入各自的背包，每次选择都基于之前的选择来确保最后两
    个背包的重量相等。动态规划在这里就像是一张大表，记录了所有
    可能的选择结果，以确保在最终决定中能找到一个公平的分配方案。

    1）检查总重量：
        首先，你需要检查所有物品的总重量。如果总重量是奇数，那就意味
    着无论怎样分配，都不可能让两个背包的重量完全相同（因为两个相同的
    奇数相加不会是一个偶数），所以直接返回false。

    2）目标重量：
        既然总重量是偶数，我们的目标就是让每个背包装下总重量的一半。
    这样，两个背包的重量加起来就是所有物品的总重量，且重量相等。

    3）使用动态规划解决问题：
        这里，我们使用一个二维数组dp来记录达到目标的可能性。dp[i][j]
    表示考虑前i件物品时，能否组合出总重量为j的情况。初始化时，认为每个
    背包在不装任何东西时（即重量为0）是一个可行的状态，所以dp[i][0] = true。

    3）填充dp数组：
        对于数组中的每一个物品（和每一个可能的重量目标），我们都检查
    是否可以通过不添加这个新物品（dp[i-1][j]）或者添加这个新物品
    （j >= nums[i] && dp[i-1][j-nums[i]]）来达到当前的重量目标。
    这样，我们就可以逐步构建出所有可能的组合方式。

        我们用dp[i][j]来表示“考虑前i个物品时，能否找到一种方法，
    使得这些物品的总重量正好为j”。
*/
public class NO416_N_CanPartition {

    @Test
    public void test() {
        assert canPartition(new int[]{1, 5, 11, 5});
        assert !canPartition(new int[]{1, 2, 3, 5});
    }

    public boolean canPartition(int[] nums) {
        // 2024/3/12 NO.1
        // 2024/3/30 NO.2
        // 2024/3/31 NO.3 没思路，能看懂
        int target = stream(nums).sum();
        if (target % 2 != 0)
            return false;

        target = target >> 1;
        boolean[][] dp = new boolean[nums.length][target + 1];
        for (int i = 0; i < nums.length; i++)
            dp[i][0] = true;

        // TODO 为了降低难度 只写这部分
        return dp[nums.length - 1][target];
    }

}















/*
// 方法1：
public boolean canPartition(int[] nums) {
    final int n = nums.length;
    int target = stream(nums).sum();
    if (target % 2 != 0)
        return false;

    target = target >> 1;
    boolean[][] dp = new boolean[n][target + 1];
    for (int i = 0; i < n; i++)
        dp[i][0] = true;

    for (int i = 1; i < n; i++)
        for (int j = 1; j <= target; j++)
            dp[i][j] = dp[i - 1][j] || (j >= nums[i] && dp[i - 1][j - nums[i]]);

    return dp[n - 1][target];
}
*/