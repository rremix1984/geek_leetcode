/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

import static com.leetcode.util.LogUtil.info;

/**
    188. 买卖股票的最佳时机 IV
        给定一个整数数组 prices ，它的第 i 个元素 prices[i] 是一支给定的股票在第 i 天的价格。
        设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。
        注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
    示例 1：
        输入：k = 2, prices = [2, 4, 1]
        输出：2
        解释：在第 1 天 (股票价格 = 2) 的时候买入，
            在第 2 天 (股票价格 = 4) 的时候卖出，
            这笔交易所能获得利润 = 4-2 = 2 。
    示例 2：
        输入：k = 2, prices = [3, 2, 6, 5, 0, 3]
        输出：7
        解释：在第 2 天 (股票价格 = 2) 的时候买入，
            在第 3 天 (股票价格 = 6) 的时候卖出,
            这笔交易所能获得利润 = 6-2 = 4 。
        随后，在第 5 天 (股票价格 = 0) 的时候买入，
            在第 6 天 (股票价格 = 3) 的时候卖出,
            这笔交易所能获得利润 = 3-0 = 3 。
*/
public class NO188_BestTimeToBuyAndSellStockIV {

    @Test
    public void test() {
        info(maxProfit(2, new int[]{2, 4, 1}));// 2
        info(maxProfit(2, new int[]{3, 2, 6, 5, 0, 3}));// 7
    }

    public int maxProfit(int k, int[] prices) {
        return -1;
    }
}
















/**
//   第四题，k = +infinity with fee
//   每次交易要支付手续费，只要把手续费从利润中减去即可。
//   改写方程：
//        dp[i][0] = max(dp[i-1][0], dp[i-1][1] + prices[i])
//        dp[i][1] = max(dp[i-1][1], dp[i-1][0] - prices[i] - fee)
//   解释：相当于买入股票的价格升高了。
//   在第一个式子里减也是一样的，相当于卖出股票的价格减小了。
// 直接翻译成代码：
int maxProfit_with_fee(int[] prices, int fee) {
    int n = prices.length;
    int dp_i_0 = 0;
    int dp_i_1 = Integer.MIN_VALUE;
    for (int i = 0; i < n; i++) {
        int temp = dp_i_0;
        dp_i_0 = Math.max(dp_i_0, dp_i_1 + prices[i]);
        dp_i_1 = Math.max(dp_i_1, temp   - prices[i] - fee);
    }
    return dp_i_0;
}
*/