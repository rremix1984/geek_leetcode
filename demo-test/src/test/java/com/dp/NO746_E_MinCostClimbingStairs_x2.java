/**
 * copyright 2022/1/19
 */
package com.dp;

import org.junit.Test;

/**
   （简单）
    746. 使用最小花费爬楼梯
        给你一个整数数组 cost ，其中 cost[i] 是从楼梯第 i 个台阶向上爬需要支付的费用。一旦你支付此费用，即可选择向上爬一个或者两个台阶。
        你可以选择从下标为 0 或下标为 1 的台阶开始爬楼梯。
        请你计算并返回达到楼梯顶部的最低花费。
    示例 1：
        输入：cost = [10,15,20]
        输出：15
        解释：你将从下标为 1 的台阶开始。
        - 支付 15 ，向上爬两个台阶，到达楼梯顶部。
        总花费为 15 。
    示例 2：
        输入：cost = [1,100,1,1,1,100,1,1,100,1]
        输出：6
        解释：你将从下标为 0 的台阶开始。
        - 支付 1 ，向上爬两个台阶，到达下标为 2 的台阶。
        - 支付 1 ，向上爬两个台阶，到达下标为 4 的台阶。
        - 支付 1 ，向上爬两个台阶，到达下标为 6 的台阶。
        - 支付 1 ，向上爬一个台阶，到达下标为 7 的台阶。
        - 支付 1 ，向上爬两个台阶，到达下标为 9 的台阶。
        - 支付 1 ，向上爬一个台阶，到达楼梯顶部。
        总花费为 6 。
*/
public class NO746_E_MinCostClimbingStairs_x2 {

    @Test
    public void test() {
        assert 15 == minCostClimbingStairs(new int[]{10,15,20});// 15
        assert  6 == minCostClimbingStairs(new int[]{1,100,1,1,1,100,1,1,100,1});// 6
    }

    public int minCostClimbingStairs(int[] cost) {
        return -1;
    }
}














/**
// 方法1：迭代法
public int minCostClimbingStairs(int[] cost) {
    int pre = 0;
    int cur = 0;
    for (int i = 2; i <= cost.length; i++) {
        int next = min(cur + cost[i - 1],
                pre + cost[i - 2]);
        pre = cur;
        cur = next;
    }
    return cur;
}
*/