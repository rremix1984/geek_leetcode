/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static java.lang.Math.min;

/**
    [ARRAY] |
    (简单)
    2073. 买票需要的时间
        有n个人前来排队买票，其中第0人站在队伍最前方，第(n-1)人站在队伍最后方。
        给你一个下标从0开始的整数数组tickets，数组长度为n，其中第i人想要购买的票数为tickets[i]。
        每个人买票都需要用掉恰好1秒。一个人一次只能买一张票，如果需要购买更多票，他必须走到队尾重新排队（瞬间发生，不计时间）。
        如果一个人没有剩下需要买的票，那他将会离开队伍。
        返回位于位置k（下标从 0 开始）的人完成买票需要的时间（以秒为单位）。
    示例 1：
        输入：tickets = {2, 3, 2},  k = 2
        输出：6
        解释：- 第一轮，队伍中的每个人都买到一张票，队伍变为 {1,  2,  1} 。
             - 第二轮，队伍中的每个都又都买到一张票，队伍变为 {0,  1,  0} 。
             位置 2 的人成功买到 2 张票，用掉 3 + 3 = 6 秒。
    示例 2：
        输入：tickets = {5, 1, 1, 1},  k = 0
        输出：8
        解释：- 第一轮，队伍中的每个人都买到一张票，队伍变为 {4,  0,  0,  0} 。
             - 接下来的 4 轮，只有位置 0 的人在买票。
             位置 0 的人成功买到 5 张票，用掉 4 + 1 + 1 + 1 + 1 = 8 秒。

    思路与算法：
    为了计算第k个人买完票所需的时间，我们可以首先计算在这个过程中每个人买票所需要的时间，再对这些时间求和得到答案。
    我们可以对每个人的下标i分类讨论：
      1）如果这个人初始在第k个人的前方，或者这个人恰好为第k个人，即i ≤ k，
       此时在第k个人买完票之前他最多可以购买tickets[k]张。
       考虑到他想要购买的票数，那么他买票所需时间即为
            >> min(tickets[k], tickets[i])；
      2）如果这个人初始在第 k 个人的后方，即 i > k，此时在第 k 个人买完票之前他最多可以购买 [k] − 1 张。
       考虑到他想要购买的票数，那么他买票所需时间即为
            >> min(tickets[k] − 1, tickets[i])。
    我们遍历每个人的下标，按照上述方式计算并维护每个人买票所需时间之和，即可得到第 kk 个人买完票所需的时间，我们返回该值作为答案。
*/
public class NO2073_E_TimeRequiredToBuy {

    @Test
    public void test() {
        assert 6 == timeRequiredToBuy(new int[]{2, 3, 2}, 2);
        assert 8 == timeRequiredToBuy(new int[]{5, 1, 1, 1}, 0);
    }

    public int timeRequiredToBuy(int[] tickets, int k) {
        // 2024/3/9 NO.1
        int min = 0;
        return min;
    }

}













/*
// 方法1：
public int timeRequiredToBuy(int[] tickets, int k) {
    int res = 0;
    for (int i = 0; i < tickets.length; i++)
        // 遍历计算每个人所需时间, res里面是每个人的时间的总和
        if (i <= k)
            res += min(tickets[i], tickets[k]);
        else
            res += min(tickets[i], tickets[k] - 1);
    return res;
}
*/