/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [ARRAY]
    [STRING]
    (简单)
    2347. 最好的扑克手牌
        给你一个整数数组 ranks 和一个字符数组 suit 。你有 5 张扑克牌，第 i 张牌大小为 ranks[i] ，花色为 suits[i] 。
        下述是从好到坏你可能持有的 手牌类型 ：
            "Flush"：同花，五张相同花色的扑克牌。
            "Three of a Kind"：三条，有 3 张大小相同的扑克牌。
            "Pair"：对子，两张大小一样的扑克牌。
            "High Card"：高牌，五张大小互不相同的扑克牌。
        请你返回一个字符串，表示给定的 5 张牌中，你能组成的 最好手牌类型 。
        注意：返回的字符串 大小写 需与题目描述相同。
    示例 1：
        输入：ranks = {13, 2, 3, 1, 9},  suits = {'a', 'a', 'a', 'a', 'a'}
        输出："Flush"
        解释：5 张扑克牌的花色相同，所以返回 "Flush" 。
    示例 2：
        输入：ranks = {4, 4, 2, 4, 4},  suits = {'d', 'a', 'a', 'b', 'c'}
        输出："Three of a Kind"
        解释：第一、二和四张牌组成三张相同大小的扑克牌，所以得到 "Three of a Kind" 。
            注意我们也可以得到 "Pair" ，但是 "Three of a Kind" 是更好的手牌类型。
            有其他的 3 张牌也可以组成 "Three of a Kind" 手牌类型。
    示例 3：
        输入：ranks = {10, 10, 2, 12, 9},  suits = {'a', 'b', 'c', 'a', 'd'}
        输出："Pair"
        解释：第一和第二张牌大小相同，所以得到 "Pair" 。
            我们无法得到 "Flush" 或者 "Three of a Kind" 。
*/
public class NO2347_E_BestHand_x2 {

    @Test
    public void test() {
        assert "Flush".equals(bestHand(
                new int[]{13, 2, 3, 1, 9}, new char[]{'a', 'a', 'a', 'a', 'a'}));
        assert "Three of a Kind".equals(bestHand(
                new int[]{4, 4, 2, 4, 4}, new char[]{'d', 'a', 'a', 'b', 'c'}));
        assert "Pair".equals(bestHand(
                new int[]{10, 10, 2, 12, 9}, new char[]{'a', 'b', 'c', 'a', 'd'}));
        assert "High Card".equals(bestHand(
                new int[]{7, 6, 4, 12, 13}, new char[]{'b', 'd', 'd', 'd', 'a'}));
        assert "Three of a Kind".equals(bestHand(
                new int[]{1, 1, 1, 2, 2}, new char[]{'a', 'b', 'c', 'a', 'd'}));
    }

    public String bestHand(int[] ranks, char[] suits) {
        String ret = "High Card";
        return ret;
    }

}
















/**
// 方法1：时间复杂度O(n)
public String bestHand(int[] ranks, char[] suits) {
    String ret = "High Card";
    int[] rank = new int[14];
    for (int i = 0; i < ranks.length; i++) {
        rank[ranks[i]]++;
        if (i > 0 && suits[i] == suits[i - 1] && ++rank[0] == suits.length - 1)
            return "Flush";

        if (rank[ranks[i]] == 2)
            ret = "Pair";

        if (rank[ranks[i]] == 3)
            return "Three of a Kind";
    }
    return ret;
}
*/