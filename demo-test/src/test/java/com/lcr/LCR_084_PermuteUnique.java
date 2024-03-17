package com.lcr;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.leetcode.util.SystemUtil.arrayAllMatch;
import static com.leetcode.util.SystemUtil.getArrayList;
import static java.util.Arrays.sort;

/**
    [ARRAY] |
    (中等)
    LCR.084 全排列 II
    给定一个可包含重复数字的整数集合nums，按任意顺序返回它所有不重复的全排列。
    示例 1：
        输入：nums = [1,1,2]
        输出：
            [[1,1,2],
             [1,2,1],
             [2,1,1]]
    示例 2：
        输入：nums = [1,2,3]
        输出：[[1,2,3],
              [1,3,2],
              [2,1,3],
              [2,3,1],
              [3,1,2],
              [3,2,1]]
        提示：
            1 <= nums.length <= 8
            -10 <= nums[i] <= 10

    Related Topics:数组,回溯
*/
public class LCR_084_PermuteUnique {

    @Test
    public void test() {
        arrayAllMatch(
                getArrayList(new int[][]{{1,1,2},
                                         {1,2,1},
                                         {2,1,1}}),
                permuteUnique(new int[]{1, 1, 1, 1, 1, 1, 2}));
        arrayAllMatch(
                getArrayList(new int[][]{{1,1,2},
                                         {1,2,1},
                                         {2,1,1}}),
                permuteUnique(new int[]{1, 1, 2}));
    }

    public List<List<Integer>> permuteUnique(int[] nums) {
        // 2024/3/17 NO.1 不会做，挺经典的一道题
        List<List<Integer>> res = new ArrayList<>();
        return res;
    }

}















/*
// 方法1：
public List<List<Integer>> permuteUnique(int[] nums) {
    List<List<Integer>> res = new ArrayList<>();
    sort(nums);
    call(res, new ArrayList<>(), nums, new boolean[nums.length]);
    return res;
}

private void call(List<List<Integer>> res, List<Integer> path, int[] nums, boolean[] used) {
    // 把每一层所有节点全部遍历完成, 把path全部加入进去
    if (path.size() == nums.length) {
        res.add(new ArrayList<>(path));
        return;
    }

    // 遍历每一个元素
    for (int i = 0; i < nums.length; i++) {
        // （之前已经排好序了）如果和前一个元素相同就直接跳过
        if (i > 0 && nums[i] == nums[i - 1]
                && !used[i - 1]) {
            continue;
        }

        // 对于没有被使用过的元素
        if (used[i])
            continue;

        // 标记为使用过，加入队列
        used[i] = true;
        path.add(nums[i]);

        call(res, path, nums, used);

        // 标记为未使用，剔除队列
        path.remove(path.size() - 1);
        used[i] = false;//回溯
    }
}
*/