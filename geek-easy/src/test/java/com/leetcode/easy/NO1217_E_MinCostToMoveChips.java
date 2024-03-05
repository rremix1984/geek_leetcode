/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static java.lang.Math.min;

/**
    [ARRAY]
    (简单)
    1217. 玩筹码
        有n个筹码。第i个筹码的位置是position[i]。
        我们需要把所有筹码移到同一个位置。在一步中，我们可以将第i个筹码的位置从position[i]改变为:
        position[i]+2 或 position[i]-2，此时 cost=0
        position[i]+1 或 position[i]-1，此时 cost=1
        返回将所有筹码移动到同一位置上所需要的最小代价。
    示例 1：
        输入：position = [1, 2, 3]
        输出：1
        解释：第一步:将位置3的筹码移动到位置1，成本为0。
             第二步:将位置2的筹码移动到位置1，成本=1。
             总成本是1。
    示例 2：
        输入：position = [2, 2, 2, 3, 3]
        输出：2
        解释：我们可以把位置3的两个筹码移到位置2。
             每一步的成本为1。
             总成本=2。
    示例 3:
        输入：position = [1, 1000000000]
        输出：1
    提示：
        1 <= chips.length <= 100
        1 <= chips[i] <= 10^9

    方法一：贪心
        思路与算法
        首先很容易得出：从某一个偶（奇）数位置 pi 改变到另一个偶（奇）数位置 pj，
        不妨设 pi < pj，那么一定 ∃ k ∈ N∗
        使得 pi + 2k = pj 成立，即此时的最小开销为 0。
        从某一个偶（奇）数位置 pi 改变到另一个奇（偶）数位置 pj ，不妨设 pi < pj，
        那么一定 ∃k ∈ N 使得 pi + 2k + 1 = pj 成立，即此时的最小开销为 1。
        那么我们可以把初始每一个偶数位置的「筹码」看作一个整体，每一个奇数位置的「筹码」看作一个整体。
        因为我们的目标是最后将全部的「筹码」移动到同一个位置，那么最后的位置只有两种情况：
          1）移动到某一个偶数位置，此时的开销最小值就是初始奇数位置「筹码」的数量。
          2）移动到某一个奇数位置，此时的开销最小值就是初始偶数位置「筹码」的数量。
        那么这两种情况中的最小值就是最后将所有筹码移动到同一位置上所需要的最小代价。
*/
public class NO1217_E_MinCostToMoveChips {

    @Test
    public void test() {
        assert 1 == minCostToMoveChips(new int[]{1, 2, 3});
        assert 2 == minCostToMoveChips(new int[]{2, 2, 2, 3, 3});
        assert 1 == minCostToMoveChips(new int[]{1, 1000000000});
    }

    public int minCostToMoveChips(int[] position) {
        return -1;
    }

}

















/**
// 方法1：
public int minCostToMoveChips(int[] position) {
    int odd = 0;
    int even = 0;
    for (int pos : position)
        if ((pos & 1) != 0)
            // 如果奇数，odd++
            odd++;
        else
            // 如果偶数，even++
            even++;
    // 奇、偶里面最小的一个
    return min(odd, even);
}
*/
