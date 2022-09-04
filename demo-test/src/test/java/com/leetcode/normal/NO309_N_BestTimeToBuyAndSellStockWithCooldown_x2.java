/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    （中等）
    309. 最佳买卖股票时机含冷冻期
        给定一个整数数组prices，其中第  prices[i] 表示第 i 天的股票价格 。
        设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成
        更多的交易（多次买卖一支股票）:卖出股票后，你无法在第二天买入股票
        (即冷冻期为 1 天)。
        注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
    示例 1:
        输入: prices = [1, 2, 3, 0, 2]
        输出: 3
        解释: 对应的交易状态为: [买入, 卖出, 冷冻期, 买入, 卖出]
    示例 2:
        输入: prices = [1]
        输出: 0

    一般转移方程
    dp[i][0] = max(dp[i-1][0], dp[i-1][1] + prices[i]);
    dp[i][1] = max(dp[i-1][1], dp[i-2][0] - prices[i]);
*/
public class NO309_N_BestTimeToBuyAndSellStockWithCooldown_x2 {

    @Test
    public void test() {
        info(maxProfit(new int[]{1, 2, 3, 0, 2}));// 3
        info(maxProfit(new int[]{1}));// 0
        info(maxProfit(new int[]{1, 2}));// 1
    }

    public int maxProfit(int[] prices) {
        int sell = 0;
        return sell;
    }
}










/*
// 方法1：
public int maxProfit(int[] prices) {
    int n = prices.length;
    int buy = -prices[0];// 手中持有股票
    int sell = 0;// 手中没有股票
    int profit_freeze = 0;
    for (int i = 0; i < n; i++) {
        int tmp = sell;
        sell = max(sell, buy + prices[i]);
        buy = max(buy, profit_freeze - prices[i]);
        profit_freeze = tmp;
    }
    return sell;
}
*/