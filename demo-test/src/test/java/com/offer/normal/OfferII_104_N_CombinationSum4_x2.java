/**
 * copyright 2022/1/19
 */
package com.offer.normal;

import org.junit.Test;
import static com.leetcode.util.MathUtils.getArrays;

/**
    (中等)
    剑指 Offer II 104. 排列的数目
        给定一个由 不同 正整数组成的数组 nums ，和一个目标整数 target 。请从 nums 中找出并返回总和为 target 的元素组合的个数。数组中的数字可以在一次排列中出现任意次，但是顺序不同的序列被视作不同的组合。
        题目数据保证答案符合 32 位整数范围。
    示例 1：
        输入：nums = {1, 2, 3}, target = 4
        输出：7
        解释：所有可能的组合为：
                (1, 1, 1, 1)
                (1, 1, 2)
                (1, 2, 1)
                (1, 3)
                (2, 1, 1)
                (2, 2)
                (3, 1)
                请注意，顺序不同的序列被视作不同的组合。
    示例 2：
        输入：nums = [9], target = 3
        输出：0

*/
public class OfferII_104_N_CombinationSum4_x2 {

    @Test
    public void test() {
        assert 7 == combinationSum4(getArrays(1, 2, 3), 4);
        assert 0 == combinationSum4(getArrays(9), 3);
    }

    public int combinationSum4(int[] nums, int target) {
        return -1;
    }

}


















/**
// 方法1：动态规划（推荐）
public int combinationSum4(int[] nums, int target) {
    int[] dp = new int[target + 1];
    dp[0] = 1;
    for (int i = 1; i <= target; i++)
        for (int num : nums)
            if (num <= i)
                dp[i] += dp[i - num];

    return dp[target];
}


// 方法2：剪枝法
public int combinationSum4(int[] nums, int target) {
    ArrayList<ArrayList<Integer>> res = new ArrayList<>();
    call(res, new ArrayList<>(), nums, target);
    return res.size();
}

private void call(ArrayList<ArrayList<Integer>> res, ArrayList<Integer> list, int[] nums, int target) {
    int sum = list.stream().mapToInt(s->s.intValue()).sum();
    if (sum == target) {
        res.add(new ArrayList<>(list));
        return;
    }

    for (int num : nums) {
        if (list.size() > target)
            continue;

        list.add(num);

        call(res, list, nums, target);

        list.remove(list.size() - 1);
    }
}
*/