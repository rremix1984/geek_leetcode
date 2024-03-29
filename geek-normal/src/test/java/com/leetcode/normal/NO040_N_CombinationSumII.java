/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static com.leetcode.util.MathUtils.getArray;
import static com.leetcode.util.SystemUtil.*;
import static java.util.Arrays.sort;

/**
    [ARRAY] ||||
    (中等)
    40. 组合总和 II
        给定一个候选人编号的集合candidates和一个目标数 target，
        找出 candidates 中所有可以使数字和为 target 的组合。
        candidates 中的每个数字在每个组合中只能使用一次。
        注意：解集不能包含重复的组合。
    示例 1:
        输入: candidates = {10, 1, 2, 7, 6, 1, 5}, target = 8,
        输出:[[1, 1, 6],
             [1, 2, 5],
             [1, 7],
             [2, 6]]
    示例 2:
        输入: candidates = {2, 5, 2, 1, 2}, target = 5,
        输出: [[1, 2, 2],
              [5]]
*/
public class NO040_N_CombinationSumII {

    @Test
    public void test() {
        assert getArray(new int[]{1, 1, 6},
                        new int[]{1, 2, 5},
                        new int[]{1, 7},
                        new int[]{2, 6}).equals(
                combinationSum2(
                        new int[]{10, 1, 2, 7, 6, 1, 5}, 8));
        assert getArray(new int[]{1, 2, 2},
                        new int[]{5}).equals(
                combinationSum2(
                        new int[]{2, 5, 2, 1, 2}, 5));
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        // 2024/3/12 NO.1
        // 2024/3/21 NO.2 没思路，这类题不能总不会，要研究
        // 2024/3/22 NO.3 能看懂，但是写不出来
        // 2024/3/25 NO.4 思路对，但是没做对
        List<List<Integer>> res = new ArrayList<>();
        return res;
    }

}




















/*
// 方法1：减枝法
public List<List<Integer>> combinationSum2(int[] candidates, int target) {
    List<List<Integer>> res = new ArrayList<>();
    Arrays.sort(candidates);
    backtrack(res, new ArrayList<>(), candidates, target, 0, 0);
    return res;
}

private void backtrack(List<List<Integer>> res, List<Integer> path, int[] cands, int target, int sum, int begin) {
    if (sum == target) {
        res.add(new ArrayList<>(path));
        return;
    }

    for (int i = begin; i < cands.length; i++) {
        if (i > begin && cands[i] == cands[i-1])
            continue;

        int rt = cands[i] + sum;

        if (rt > target)
            break;

        path.add(cands[i]);

        backtrack(res, path, cands, target, rt, i + 1);

        path.remove(path.size() - 1);
    }
}

// 方法2：
public List<List<Integer>> combinationSum2(int[] candidates, int target) {
    List<List<Integer>> res = new ArrayList<>();
    Arrays.sort(candidates);
    backTrack(res, new ArrayList<>(),
            candidates, target, 0, new boolean[candidates.length]);
    return res;
}

public void backTrack(List<List<Integer>> res, List<Integer> path,
            int[] nums, int target, int start, boolean[] visit) {
    if (target == 0) {
        res.add(new ArrayList<>(path));
        return;
    }

    if (target < 0)
        return;

    for (int i = start; i < nums.length; i++) {
        // 前后两个数字相同，且前一个数字选择过并撤销了，当前数字再选择就会重复
        if (i > 0 && nums[i] == nums[i - 1] && !visit[i - 1])
            continue;

        visit[i] = true;
        path.add(nums[i]);

        backTrack(res, path, nums, target - nums[i], i + 1, visit);

        path.remove(path.size() - 1);
        visit[i] = false;
    }
}
*/