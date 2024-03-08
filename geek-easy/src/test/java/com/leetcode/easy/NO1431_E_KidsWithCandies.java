/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.MathUtils.getArray;
import static java.lang.Math.max;

/**
    [ARRAY] |
    (简单)
    1431. 拥有最多糖果的孩子
        给你一个数组candies和一个整数extraCandies，其中candies[i]代表第i个孩子拥有的糖果数目。
        对每一个孩子，检查是否存在一种方案，将额外的extraCandies个糖果分配给孩子们之后，
        此孩子有最多的糖果。
        注意，允许有多个孩子同时拥有最多的糖果数目。
    示例 1：
        输入：candies = [2, 3, 5, 1, 3], extraCandies = 3
        输出：[true, true, true, false, true]
        解释：
        孩子 1 有 2 个糖果，如果他得到所有额外的糖果（3个），那么他总共有 5 个糖果，他将成为拥有最多糖果的孩子。
        孩子 2 有 3 个糖果，如果他得到至少 2 个额外糖果，那么他将成为拥有最多糖果的孩子。
        孩子 3 有 5 个糖果，他已经是拥有最多糖果的孩子。
        孩子 4 有 1 个糖果，即使他得到所有额外的糖果，他也只有 4 个糖果，无法成为拥有糖果最多的孩子。
        孩子 5 有 3 个糖果，如果他得到至少 2 个额外糖果，那么他将成为拥有最多糖果的孩子。
    示例 2：
        输入：candies = [4, 2, 1, 1, 2], extraCandies = 1
        输出：[true,false,false,false,false]
        解释：只有 1 个额外糖果，所以不管额外糖果给谁，只有孩子 1 可以成为拥有糖果最多的孩子。
    示例 3：
        输入：candies = [12, 1, 12], extraCandies = 10
        输出：[true,false,true]
*/
public class NO1431_E_KidsWithCandies {

    @Test
    public void test() {
        assert getArray(true, true, true, false, true)
                .equals(kidsWithCandies(new int[]{2, 3, 5, 1, 3}, 3));
        assert getArray(true, false, false, false, false)
                .equals(kidsWithCandies(new int[]{4, 2, 1, 1, 2}, 1));
        assert getArray(true, false, true)
                .equals(kidsWithCandies(new int[]{12, 1, 12}, 10));
    }

    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        // 2024/3/8 NO.1
        List<Boolean> ret = new ArrayList<>();
        return ret;
    }

}














/*
// 方法1：
public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
    List<Boolean> ret = new ArrayList<>();
    int max = 0;
    for (int j : candies)
        max = max(max, j);

    for (int candy : candies)
        ret.add(candy + extraCandies >= max);

    return ret;
}
*/