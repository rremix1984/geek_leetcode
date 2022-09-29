/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import org.junit.Test;
import static java.lang.Math.min;

/**
    (简单)
    剑指 Offer II 088. 爬楼梯的最少成本
        数组的每个下标作为一个阶梯，第 i 个阶梯对应着一个非负数的体力花费值 cost[i]（下标从 0 开始）。
        每当爬上一个阶梯都要花费对应的体力值，一旦支付了相应的体力值，就可以选择向上爬一个阶梯或者爬两个阶梯。
        请找出达到楼层顶部的最低花费。在开始时，你可以选择从下标为 0 或 1 的元素作为初始阶梯。
    示例 1：
        输入：cost = [10, 15, 20]
        输出：15
        解释：最低花费是从 cost[1] 开始，然后走两步即可到阶梯顶，一共花费 15 。
    示例 2：
        输入：cost = [1, 100, 1, 1, 1, 100, 1, 1, 100, 1]
        输出：6
        解释：最低花费方式是从 cost[0] 开始，逐个经过那些 1 ，跳过 cost[3] ，一共花费 6 。
*/
public class OfferII_088_E_MinCostClimbingStairs_x2 {

    @Test
    public void test() {
        assert 15 == minCostClimbingStairs(new int[]{10, 15, 20});
        assert 6 == minCostClimbingStairs(new int[]{1, 100, 1, 1, 1, 100, 1, 1, 100, 1});
    }

    public int minCostClimbingStairs(int[] cost) {
        return -1;
    }

}


















/**
// 方法1
public int minCostClimbingStairs(int[] cost) {
    int n = cost.length;
    int prev = 0, curr = 0;
    for (int i = 2; i <= n; i++) {
        int next = Math.min(curr + cost[i - 1], prev + cost[i - 2]);
        prev = curr;
        curr = next;
    }
    return curr;
}

// 方法2：动态规划 dp
public int minCostClimbingStairs(int[] cost) {
    int len = cost.length;
    int[] dp = new int[len + 1];

    for (int i = 2; i <= len; i++)
        dp[i] = min(dp[i - 1] + cost[i - 1],
                dp[i - 2] + cost[i - 2]);

    return dp[len];
}
*/