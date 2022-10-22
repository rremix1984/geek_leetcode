/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static java.lang.Math.max;
import static org.junit.Assert.assertEquals;

/**
    (简单)
    121. 买卖股票的最佳时机
        给定一个数组prices ，它的第i个元素prices[i]表示一支
        给定股票第i天的价格。你只能选择某一天买入这只股票，并选择
        在未来的某一个不同的日子卖出该股票。设计一个算法来计算你所
        能获取的最大利润。返回你可以从这笔交易中获取的最大利润。如果
        你不能获取任何利润，返回 0 。
    示例 1：
        输入：[7, 1, 5, 3, 6, 4]
        输出：5
        解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，
            最大利润 = 6 - 1 = 5 。
        注意利润不能是 7 - 1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
    示例 2：
        输入：prices = [7, 6, 4, 3, 1]
        输出：0
        解释：在这种情况下, 没有交易完成, 所以最大利润为 0。

     我们来假设自己来购买股票。随着时间的推移，每天我们都可以选择出售股票与否。
     那么，假设在第 i 天，如果我们要在今天卖股票，那么我们能赚多少钱呢？
     显然，如果我们真的在买卖股票，我们肯定会想：如果我是在历史最低点买的股票就好了
     ！太好了，在题目中，我们只要用一个变量记录一个历史最低价格 minprice，我们就
     可以假设自己的股票是在那天买的。那么我们在第 i 天卖出股票能得到的利润就是
     prices[i] - minprice。
     因此，我们只需要遍历价格数组一遍，记录历史最低点，然后在每一天考虑这么一个
     问题：如果我是在历史最低点买进的，那么我今天卖出能赚多少钱？当考虑完所有天
     数之时，我们就得到了最好的答案。
*/
@SuppressWarnings("all")
public class NO121_E_BestTimeToBuyAndSellStock_x3 {

    @Test
    public void test() {
        assertEquals(5, maxProfit(new int[]{7, 1, 5, 3, 6, 4}));// 5
        assertEquals(0, maxProfit(new int[]{7, 6, 4, 3, 1}));// 0
    }

    private int maxProfit(int[] ints) {
        return 0;
    }


}













/*
// 方法1：暴力法
public int maxProfit(int[] prices) {
    int maxprofit = 0;
    for (int i = 0; i < prices.length - 1; i++) {
        for (int j = i + 1; j < prices.length; j++) {
            int profit = prices[j] - prices[i];
            if (profit > maxprofit)
                maxprofit = profit;
        }
    }
    return maxprofit;
}

// 方法2：一次遍历 O(N)
public int maxProfit(int prices[]) {
    int minprice = Integer.MAX_VALUE;
    int maxprofit = 0;
    for (int i = 0; i < prices.length; i++) {
        if (prices[i] < minprice)
            minprice = prices[i];
        else if (prices[i] - minprice > maxprofit)
            maxprofit = prices[i] - minprice;
    }
    return maxprofit;
}

// 方法3：
public int maxProfit(int[] prices) {
    int min = MAX_VALUE;
    int res = 0;
    for (int c : prices) {
        if (c < min)
            min = c;
        else
            res = max(res, c - min);
    }
    return res;
}

// 方法4：
public int maxProfit(int[] prices) {
    int n = prices.length;
    if (n < 2)
        return 0;

    // 动态规划：第n天，手里持有[1]或者不持有[0]
    int[][] dp = new int[n][2];
    dp[0][0] = 0;
    dp[0][1] = -prices[0];

    // 从第2天开始遍历
    for (int i = 1; i < n; i++) {
        dp[i][0] = max(dp[i-1][0], dp[i-1][1] + prices[i]);
        dp[i][1] = max(dp[i-1][1], - prices[i]);// 一共只能买一次，如果买了那说明 一定会花掉 -price[i]，所以不用参考昨天干了什么
    }

    // 结果一定是在第n-1天，状态为不持有[0]
    return dp[n-1][0];
}

// 方法5：
public int maxProfit(int[] prices) {
    int n = prices.length;
    if (n < 2)
        return 0;

    int max = 0;
    int min = prices[0];
    for (int i = 0; i < n; i++) {
        if (prices[i] > min)
            // 当前值大于最小值，就需要重新审视最大收益 max
            max = max(max, prices[i] - min);
        else
            // 当前元素小于最小值，更新最小值
            min = prices[i];
    }
    return max;
}
*/