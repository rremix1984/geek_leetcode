/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.MathUtils.getArray;
import static com.leetcode.util.MathUtils.getArrays;
import static com.leetcode.util.MathUtils.getArrays;
import static com.leetcode.util.SystemUtil.arrayAllMatch;
import static org.junit.Assert.assertEquals;

/**
    [ARRAY] |||
    (中等,面试题)
    NO.39 组合总和
        给你一个无重复元素的整数数组 candidates 和一个目标整数 target，
    找出 candidates 中可以使数字和为目标数target的所有不同组合，并以
    列表形式返回。你可以按任意顺序返回这些组合。candidates 中的同一个
    数字可以【无限制重复】被选取。如果至少一个数字的被选数量不同，则两种
    组合是不同的。对于给定的输入，保证和为 target 的不同组合数少于150个。
    示例 1：
        输入：candidates = {2, 3, 6, 7},  target = 7
        输出：{{2, 2, 3},
              {7}}
        解释：2 和 3 可以形成一组候选，2 + 2 + 3 = 7 。注意 2 可以使用多次。
             7 也是一个候选，7 = 7。
             仅有这两种组合。
    示例 2：
        输入: candidates = {2, 3, 5},  target = 8
        输出: {{2, 2, 2, 2},
              {2, 3, 3},
              {3, 5}}
    示例 3：
        输入: candidates = {2},  target = 1
        输出: {}
*/
public class NO039_N_CombinationSum {

    @Test
    public void test() {
        assert arrayAllMatch(
            getArray(new int[][]{{2, 2, 3}, {7}}),
            combinationSum(getArrays(2, 3, 6, 7), 7));
        assert arrayAllMatch(
            getArray(new int[][]{{2, 2, 2, 2}, {2, 3, 3}, {3, 5}}),
            combinationSum(getArrays(2, 3, 5), 8));
        assert arrayAllMatch(
            getArray(new int[][]{}),
            combinationSum(getArrays(2), 1));
    }

    public List<List<Integer>> combinationSum(int[] cand, int target) {
        // 2024/3/12 NO.1 挺难想的，但是不是做不出来
        // 2024/3/19 NO.2 有点思路了，但是还是做不出来，很经典
        // 2024/3/25 NO.3 没做出来，但是思路接近了
        List<List<Integer>> res = new ArrayList<>();

        return res;
    }

}
















/*
// 方法1：
public List<List<Integer>> combinationSum(int[] candidates, int target) {
    List<List<Integer>> res = new ArrayList<>();
    call(res, new ArrayList<>(), candidates, 0, target);
    return res;
}

// candidates 候选数组
// start      搜索起点
// target     每减去一个元素，目标值变小
// res        结果集列表
private void call(List<List<Integer>> res, ArrayList<Integer> list,
                int[] cand, int start, int target) {
    // target 为负数和 0 的时候不再产生新的孩子结点
    if (target < 0)
        return;

    // target减到0，说明已经找齐了所有元素
    if (0 == target) {
        res.add(new ArrayList<>(list));
        return;
    }

    // 题目里面分析与顺序有关，即：2，3，3 和 3，2，3 是一样的。
    // 因此需要一个一个循环的来取数
    for (int i = start; i < cand.length; i++) {
        // 重点理解这里从 begin 开始搜索的语意
        list.add(cand[i]);

        // 注意：由于每一个元素可以重复使用，下一轮搜索的起点依然是 i，这里非常容易弄错
        call(res, list, cand, i, target - cand[i]);

        // 状态重置, 这里的意思是：
        // 首先，在位置 i 上尝试所有可能（通过for循环）；
        // 然后，再进行 i + 1 位置上的尝试；
        list.remove(list.size() - 1);
    }
}
*/