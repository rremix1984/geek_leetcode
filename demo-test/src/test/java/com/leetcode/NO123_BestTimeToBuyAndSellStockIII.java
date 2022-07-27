/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    （困难）
    123. 买卖股票的最佳时机 III
        给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
        设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。
        注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
    示例 1:
        输入：prices = [3, 3, 5, 0, 0, 3, 1, 4]
        输出：6
        解释：在第 4 天（股票价格 = 0）的时候买入，在第 6 天（股票价格 = 3）的时候卖出，这笔交易所能获得利润 = 3-0 = 3 。
        随后，在第 7 天（股票价格 = 1）的时候买入，在第 8 天 （股票价格 = 4）的时候卖出，这笔交易所能获得利润 = 4-1 = 3 。
    示例 2：
        输入：prices = [1, 2, 3, 4, 5]
        输出：4
        解释：在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
        注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。
        因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
    示例 3：
        输入：prices = [7, 6, 4, 3, 1]
        输出：0
        解释：在这个情况下, 没有交易完成, 所以最大利润为 0。
    示例 4：
        输入：prices = [1]
        输出：0
*/
@SuppressWarnings("all")
public class NO123_BestTimeToBuyAndSellStockIII {

    @Test
    public void test() {
        info(maxProfit(new int[]{3, 3, 5, 0, 0, 3, 1, 4}));// 6
    }

    public int maxProfit(int[] prices) {
        return -1;
    }

}
















/**
// 第三题，k = +infinity with cooldown
//    每次 sell 之后要等一天才能继续交易。只要把这个特点融入上一题的
//    状态转移方程即可：
//    dp[i][0] = max(dp[i-1][0], dp[i-1][1] + prices[i])
//    dp[i][1] = max(dp[i-1][1], dp[i-2][0] - prices[i])
//  解释：第 i 天选择 buy 的时候，要从 i-2 的状态转移，而不是 i-1 。
//
// 一般情况：
// （1）dp[-1][k][0] = 0
//     解释：因为 i 是从 0 开始的，所以 i = -1 意味着还没有开始，
//          这时候的利润当然是 0 。
//
// （2）dp[-1][k][1] = -infinity
//     解释：还没开始的时候，是不可能持有股票的，用负无穷表示这种不可能。
//
// （3）dp[i][0][0] = 0
//     解释：因为 k 是从 1 开始的，所以 k = 0 意味着根本不允许交易，
//          这时候利润当然是 0 。
//
// （4）dp[i][0][1] = -infinity
//     解释：不允许交易的情况下，是不可能持有股票的，用负无穷表示这种不可能。
//
// 翻译成代码：
int maxProfit_with_cool(int[] prices) {
    int n = prices.length;
    int dp_i_0 = 0;
    int dp_i_1 = Integer.MIN_VALUE;
    // 代表 dp[i-2][0]
    int dp_pre_0 = 0;
    for (int i = 0; i < n; i++) {
        int temp = dp_i_0;
        dp_i_0 = Math.max(dp_i_0, dp_i_1   + prices[i]);
        dp_i_1 = Math.max(dp_i_1, dp_pre_0 - prices[i]);
        dp_pre_0 = temp;
    }
    return dp_i_0;
}
*/

