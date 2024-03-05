/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import java.util.Arrays;

/**
    [ARRAY]
    (简单)
    2144. 打折购买糖果的最小开销
        一家商店正在打折销售糖果。每购买两个糖果，商店会免费送一个糖果。
        免费送的糖果唯一的限制是：它的价格需要小于等于购买的两个糖果价格的 较小值 。
        比方说，总共有 4 个糖果，价格分别为 1 ，2 ，3 和 4 ，一位顾客买了价格为 2 和 3 的糖果，
        那么他可以免费获得价格为 1 的糖果，但不能获得价格为 4 的糖果。
        给你一个下标从 0 开始的整数数组 cost ，其中 cost[i] 表示第 i 个糖果的价格，
        请你返回获得 所有 糖果的 最小 总开销。
    示例 1：
        输入：cost = {1, 2, 3}
        输出：5
        解释：我们购买价格为 2 和 3 的糖果，然后免费获得价格为 1 的糖果。
        总开销为 2 + 3 = 5 。这是开销最小的 唯一 方案。
        注意，我们不能购买价格为 1 和 3 的糖果，并免费获得价格为 2 的糖果。
        这是因为免费糖果的价格必须小于等于购买的 2 个糖果价格的较小值。
    示例 2：
        输入：cost = {6, 5, 7, 9, 2, 2}
        输出：23
        解释：最小总开销购买糖果方案为：
        - 购买价格为 9 和 7 的糖果
        - 免费获得价格为 6 的糖果
        - 购买价格为 5 和 2 的糖果
        - 免费获得价格为 2 的最后一个糖果
        因此，最小总开销为 9 + 7 + 5 + 2 = 23 。
    示例 3：
        输入：cost = {5, 5}
        输出：10
        解释：由于只有 2 个糖果，我们需要将它们都购买，而且没有免费糖果。
        所以总最小开销为 5 + 5 = 10 。
*/
public class NO2144_E_MinimumCost {

    @Test
    public void test() {
        assert 5 == minimumCost(new int[]{1, 2, 3});
        assert 23 == minimumCost(new int[]{6, 5, 7, 9, 2, 2});
        assert 10 == minimumCost(new int[]{5, 5});
    }

    public int minimumCost(int[] cost) {
        int ret = 0;
        Arrays.sort(cost);
        int n = cost.length - 1;
        while (n >= 2) {
            ret += cost[n] + cost[n - 1];
            n -= 3;
        }

        while (n >= 0)
            ret += cost[n--];

        return ret;
    }

}















/**
public int minimumCost(int[] cost) {
    int ret = 0;
    Arrays.sort(cost);
    int index = cost.length - 1;
    while (index >= 2) {
        ret += cost[index] + cost[index - 1];
        index -= 3;
    }

    while (index >= 0)
        ret += cost[index--];

    return ret;
}
*/