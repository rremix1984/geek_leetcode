/**
 * copyright(c) 2021.  All rights reserved.
 */
package com.leetcode.normal;

import static com.leetcode.util.MathUtils.max;
import org.junit.Test;

/**
    [ARRAY] |||||
    (中等)
    NO.2498 青蛙过河 II
        给你一个下标从 0 开始的整数数组 stones，数组中的元素严格递增，
    表示一条河中石头的位置。一只青蛙一开始在第一块石头上，它想到达最后一
    块石头，然后回到第一块石头。同时每块石头至多到达一次。一次跳跃的长度
    是青蛙跳跃前和跳跃后所在两块石头之间的距离。如果青蛙从 stones[i]
    跳到 stones[j]，跳跃的长度为|stones[i] - stones[j]|。一条路径
    的代价是这条路径里的最大跳跃长度。请你返回这只青蛙的最小代价。
    示例 1：
        输入：stones = [0, 2, 5, 6, 7]
        输出：5
        解释：下图展示了一条最优路径。
                  (5)    (1)(1)
            start ------>-->-->
                 |      |  |  |
              [ 0, 2,   5, 6, 7 ]
            end |<-|<---------|
                (2)    (5)
        这条路径的代价是 5 ，是这条路径中的最大跳跃长度。
        无法得到一条代价小于 5 的路径，我们返回 5 。
    示例 2：
        输入：stones = [0, 3, 9]
        输出：9
        解释：         (9)
            start------------>
            |                |
          [ 0,  3,          9 ]
         end|<--|<----------|
             (3)     (6)
        青蛙可以直接跳到最后一块石头，然后跳回第一块石头。
        在这条路径中，每次跳跃长度都是 9。所以路径代价是 max(9,9)=9。
        这是可行路径中的最小代价。
    提示：
        2 <= stones.length <= 10 ^ 5
        0 <= stones[i] <= 10 ^ 9
        stones[0] == 0
        stones 中的元素严格递增。
    Related Topics:贪心,数组,二分查找
    解释：
        核心思想是通过追踪两个潜在的起跳点（first 和 second），来确保能
    够覆盖所有可能的最大跳跃距离情况。最后，它比较了从最后两个起跳点跳到终
    点的距离，以及之前记录的最大跳跃距离，从而找到并返回整个过程中的最大跳
    跃距离。
*/
public class NO2498_N_MaxJump {

    @Test
    public void test() {
        assert 5 == maxJump(new int[]{0, 2, 5, 6, 7});
        assert 9 == maxJump(new int[]{0, 3, 9});
    }

    public int maxJump(int[] stones) {
        // 2024/3/21 NO.1 没想出来，动态规划，不容易想
        // 2024/3/22 NO.2
        // 2024/3/27 NO.3 没思路
        // 2024/3/28 NO.4 有思路了，但是没做出来
        // 2024/4/1  NO.5 思路有了，但是没做出来
        int max = 0;
        return max;
    }

}

















/*
// 方法1：
public int maxJump(int[] stones) {
    int max = 0;
    int first = 0;
    int second = 0;
    for (int stone : stones) {
        if (first < second) {
            max = max(max, stone - first);
            first = stone;
        } else {
            max = max(max, stone - second);
            second = stone;
        }
    }
    return max;
}
*/