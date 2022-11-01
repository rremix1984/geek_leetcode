/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;
import static java.lang.Math.max;

/**
    (中等)
    486. 预测赢家
        给你一个整数数组 nums 。玩家 1 和玩家 2 基于这个数组设计了一个游戏。
        玩家 1 和玩家 2 轮流进行自己的回合，玩家 1 先手。开始时，两个玩家的初始分值都是 0 。每一回合，
        玩家从数组的任意一端取一个数字（即，nums[0] 或 nums[nums.length - 1]），取到的数字将会从
        数组中移除（数组长度减 1 ）。玩家选中的数字将会加到他的得分上。当数组中没有剩余数字可取时，游戏结束。
        如果玩家 1 能成为赢家，返回 true 。如果两个玩家得分相等，同样认为玩家 1 是游戏的赢家，也返回 true 。
        你可以假设每个玩家的玩法都会使他的分数最大化。
    示例 1：
        输入：nums = [1, 5, 2]
        输出：false
        解释：一开始，玩家 1 可以从 1 和 2 中进行选择。
             如果他选择 2（或者 1 ），那么玩家 2 可以从 1（或者 2 ）和 5 中进行选择。如果玩家 2 选择了 5 ，那么玩家 1 则只剩下 1（或者 2 ）可选。
             所以，玩家 1 的最终分数为 1 + 2 = 3，而玩家 2 为 5 。
             因此，玩家 1 永远不会成为赢家，返回 false 。
    示例 2：
        输入：nums = [1, 5, 233, 7]
        输出：true
        解释：玩家 1 一开始选择 1 。然后玩家 2 必须从 5 和 7 中进行选择。无论玩家 2 选择了哪个，玩家 1 都可以选择 233 。
             最终，玩家 1（234 分）比玩家 2（12 分）获得更多的分数，所以返回 true，表示玩家 1 可以成为赢家。
*/
public class NO486_N_PredictTheWinner {

    @Test
    public void test() {
        assert !PredictTheWinner(new int[]{1, 5, 2});
        assert PredictTheWinner(new int[]{1, 5, 233, 7});
    }

    public boolean PredictTheWinner(int[] nums) {
        int length = nums.length;

        // 先手 i 后手 j 差值最大化，就可以让收益最大化
        int[][] dp = new int[length][length];

        for (int i = 0; i < length; i++)
            dp[i][i] = nums[i];

        for (int i = length - 2; i >= 0; i--)
            for (int j = i + 1; j < length; j++)
                // 先手、后手差值的【最大值】
                dp[i][j] = max(nums[i] - dp[i + 1][j],
                               nums[j] - dp[i][j - 1]);

        // 先手减去后手 >=0 先手就赢了
        return dp[0][length - 1] >= 0;
    }

}
















/**
// 方法1：递归法
public boolean PredictTheWinner(int[] nums) {
    return total(nums, 0, nums.length - 1, 1) >= 0;
}

// 从 start 开始到 end 结束，第 turn 轮
public int total(int[] nums, int start, int end, int turn) {
    // 当 start 和 end 相遇了，结束
    if (start == end)
        return nums[start] * turn;

    int scoreStart =
            nums[start] * turn + total(nums, start + 1, end, -turn);

    int scoreEnd =
            nums[end] * turn + total(nums, start, end - 1, -turn);

    return max(scoreStart * turn, scoreEnd * turn) * turn;
}



// 方法3：dfs深度优先
public boolean PredictTheWinner(int[] nums) {
    int len = nums.length;
    int[][] memo = new int[len][len];

    for (int i = 0; i < len; i++) {
        Arrays.fill(memo[i], Integer.MIN_VALUE);
    }
    return dfs(nums, 0, len - 1, memo) >= 0;
}

private int dfs(int[] nums, int i, int j, int[][] memo) {
    if (i > j) {
        return 0;
    }

    if (memo[i][j] != Integer.MIN_VALUE) {
        return memo[i][j];
    }
    int chooseLeft = nums[i] - dfs(nums, i + 1, j, memo);
    int chooseRight = nums[j] - dfs(nums, i, j - 1, memo);
    memo[i][j] = Math.max(chooseLeft, chooseRight);
    return memo[i][j];
}


// 方法2：动态规划
public boolean PredictTheWinner(int[] nums) {
    int length = nums.length;
    // 先手 i 后手 j 差值最大化，就可以让收益最大化
    int[][] dp = new int[length][length];

    for (int i = 0; i < length; i++)
        dp[i][i] = nums[i];

    for (int i = length - 2; i >= 0; i--)
        for (int j = i + 1; j < length; j++)
            // 先手、后手差值的【最大值】
            dp[i][j] = max(nums[i] - dp[i + 1][j],
                           nums[j] - dp[i][j - 1]);

    // 先手减去后手 >=0 先手就赢了
    return dp[0][length - 1] >= 0;
}
*/