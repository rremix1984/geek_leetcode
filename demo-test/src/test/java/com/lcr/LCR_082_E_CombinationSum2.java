package com.lcr;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.MathUtils.getArray;

/**
    [ARRAY] |
    (简单)
    LCR.082 组合总和
    给定一个可能有重复数字的整数数组 candidates 和一个目标数 target ，
    找出 candidates 中所有可以使数字和为 target 的组合。
    candidates 中的每个数字在每个组合中只能使用一次，
    解集不能包含重复的组合。
    示例 1:
        输入: candidates = [10, 1, 2, 7, 6, 1, 5], target = 8,
        输出: [[1,1,6],
              [1,2,5],
              [1,7],
              [2,6]]
    示例 2:
        输入: candidates = [2, 5, 2, 1, 2], target = 5,
        输出: [[1,2,2],
              [5]]
    提示:
        1 <= candidates.length <= 100
        1 <= candidates[i] <= 50
        1 <= target <= 30
    Related Topics:数组,回溯
    思路：
        整体思路和求没有重复元素组合问题一样
        剪枝操作
        candidates 进行排序
        以[1, 1, 2]为例子，其递归树如下图

        图中红色剪枝判断依据 是若前后两个数字相同，并且前一个数字选择了并从路径中撤销了
        （即visit对应为false），
    ps：
        visit[i-1] = false说明这个数字不是第一次使用了，选择就会导致重复。
        若前一个位置 visit[i-1] 为 true,当前位置visit[i] = false说明当前位置这个数字是
        第一次选择进路径，不会导致重复。
*/
public class LCR_082_E_CombinationSum2 {

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
        List<List<Integer>> res = new ArrayList<>();
        return res;
    }

}


















/*
// 方法1：
public List<List<Integer>> combinationSum2(int[] candidates, int target) {
    List<List<Integer>> res = new ArrayList<>();
    Arrays.sort(candidates);
    backTrack(res, new ArrayList<>(), candidates,
            0, target, new boolean[candidates.length]);
    return res;
}

public void backTrack(List<List<Integer>> res, List<Integer> list, int[] cands,
                      int start, int target, boolean[] visit) {
    if (target == 0) {
        res.add(new ArrayList<>(list));
        return;
    }

    if (target < 0)
        return;

    for (int i = start; i < cands.length; i++) {
        // 前后两个数字相同，且前一个数字选择过并撤销了，当前数字再选择就会重复
        if (i > 0
                && cands[i] == cands[i - 1] // 每个数字在数组中只能使用一次
                && !visit[i - 1]) //
            continue;

        visit[i] = true;
        list.add(cands[i]);

        backTrack(res, list, cands, i + 1, target - cands[i], visit);

        list.remove(list.size() - 1);
        visit[i] = false;
    }
}
*/