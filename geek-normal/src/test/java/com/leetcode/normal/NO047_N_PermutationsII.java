/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import java.util.*;
import static com.leetcode.util.SystemUtil.*;

/**
    [ARRAY] |||
    （中等）
    NO.47 全排列 II
    给定一个可包含重复数字的序列nums，按任意顺序返回所有不重复的全排列。
    示例 1：
        输入：nums = [1, 1, 2]
        输出：      [[1, 1, 2],
                    [1, 2, 1],
                    [2, 1, 1]]
    示例 2：
        输入：nums = [1, 2, 3]
        输出：      [[1, 2, 3],
                    [1, 3, 2],
                    [2, 1, 3],
                    [2, 3, 1],
                    [3, 1, 2],
                    [3, 2, 1]]
    提示：
        1 <= nums.length <= 8
        -10 <= nums[i] <= 10
    Related Topics:数组,回溯
*/
public class NO047_N_PermutationsII {

    @Test
    public void test() {
        assert arrayAllMatch(
            getArrayList(new int[][]{{1, 2, 3},
                                     {1, 3, 2},
                                     {2, 1, 3},
                                     {2, 3, 1},
                                     {3, 1, 2},
                                     {3, 2, 1}}),
            permuteUnique(new int[]{1, 2, 3}));
        assert arrayAllMatch(
            getArrayList(new int[][]{{1, 1, 2},
                                     {1, 2, 1},
                                     {2, 1, 1}}),
            permuteUnique(new int[]{1, 1, 2}));
        assert arrayAllMatch(
            getArrayList(new int[][]{{1, 1, 2},
                                     {1, 2, 1},
                                     {2, 1, 1}}),
            permuteUnique(new int[]{1, 1, 1, 1, 1, 1, 2}));
    }

    public ArrayList<ArrayList<Integer>> permuteUnique(int[] nums) {
        // 2024/3/13 NO.1 回溯法
        // 2024/3/18 NO.2 做出来了，但是犹豫了
        // 2024/3/21 NO.3 在提示下，做出来了，死记硬背的题型，并没有理解
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        return res;
    }

}
















/*
// 方法1：
// 存放结果
public List<List<Integer>> permuteUnique(int[] nums) {
    List<List<Integer>> res = new ArrayList<>();
    Arrays.sort(nums);
    call(res, new ArrayList<>(), nums, new boolean[nums.length]);
    return res;
}

void call(List<List<Integer>> res, List<Integer> path, int[] nums, boolean[] used) {
    // 把每一层所有节点全部遍历完成, 把path全部加入进去
    if (path.size() == nums.length) {
        res.add(new ArrayList<>(path));
        return;
    }

    // 遍历每一个元素
    for (int i = 0; i < nums.length; i++) {
        // （之前已经排好序了）如果和前一个元素相同就直接跳过
        if (i > 0
                && nums[i] == nums[i - 1]
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