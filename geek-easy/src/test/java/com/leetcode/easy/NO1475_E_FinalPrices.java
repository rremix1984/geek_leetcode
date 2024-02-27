/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY] |
    (简单)
    1475. 商品折扣后的最终价格
        给你一个数组prices，其中prices[i]是商店里第i件商品的价格。
        商店里正在进行促销活动，如果你要买第i件商品，那么你可以得到与prices[j]相等的折扣，
        其中j是满足j>i且prices[j]<=prices[i]的最小下标，如果没有满足条件的j，你将没有任何折扣。
        请你返回一个数组，数组中第i个元素是折扣后你购买商品i最终需要支付的价格。
    示例 1：
        输入：prices={8, 4, 6, 2, 3}
        输出：{4, 2, 4, 2, 3}
        解释：
          商品0的价格为price{0}=8，你将得到prices{1}=4的折扣，所以最终价格为8-4=4。
          商品1的价格为price{1}=4，你将得到prices{3}=2的折扣，所以最终价格为4-2=2。
          商品2的价格为price{2}=6，你将得到prices{3}=2的折扣，所以最终价格为6-2=4。
          商品3和4都没有折扣。
    示例 2：
        输入：prices={1, 2, 3, 4, 5}
        输出：{1, 2, 3, 4, 5}
        解释：
          在这个例子中，所有商品都没有折扣。
    示例 3：
        输入：prices={10, 1, 1, 6}
        输出：{9, 0, 1, 6}
*/
@SuppressWarnings("all")
public class NO1475_E_FinalPrices {

    @Test
    public void test() {
        assertArrayEquals(new int[]{4, 2, 4, 2, 3},
              finalPrices(new int[]{8, 4, 6, 2, 3}));
        assertArrayEquals(new int[]{1, 2, 3, 4, 5},
              finalPrices(new int[]{1, 2, 3, 4, 5}));
        assertArrayEquals(new int[]{9, 0, 1, 6},
              finalPrices(new int[]{10, 1, 1, 6}));
    }

    public int[] finalPrices(int[] prices) {
        // 2024/2/26 NO.3
        int[] ans = new int[prices.length];
        return ans;
    }

}













/*
// 方法1：
public int[] finalPrices(int[] prices) {
    int[] ans = new int[prices.length];
    for (int i = 0; i < prices.length; i++) {
        int discount = 0;
        for (int j = i + 1; j < prices.length; j++)
            if (prices[j] <= prices[i]) {
                discount = prices[j];
                break;
            }
        ans[i] = prices[i] - discount;
    }
    return ans;
}
*/