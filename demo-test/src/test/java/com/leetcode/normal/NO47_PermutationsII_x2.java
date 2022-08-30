/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;

import java.util.*;

import static com.leetcode.util.LogUtil.info;

/**
    （中等）
    47. 全排列 II
    给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。
    示例 1：
        输入：nums = [1,1,2]
        输出：
        [[1,1,2],
        [1,2,1],
        [2,1,1]]
*/
public class NO47_PermutationsII_x2 {

    @Test
    public void test() {
        info(permuteUnique(new int[]{1, 1, 1, 1, 1, 1, 2}));
    }

    //存放结果
    List<List<Integer>> res = new ArrayList<>();

    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        call(new LinkedList<>(), nums, new boolean[nums.length]);
        return res;
    }

    void call(Deque<Integer> path, int[] nums, boolean[] used) {
        if (nums.length == path.size()) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && !used[i - 1] && nums[i] == nums[i - 1])
                continue;

            if (!used[i]) {
                used[i] = true;
                path.add(nums[i]);

                call(path, nums, used);

                used[i] = false;
                path.removeLast();
            }
        }
    }
}
















/**
//存放结果
List<List<Integer>> res = new ArrayList<>();

public List<List<Integer>> permuteUnique(int[] nums) {
    Arrays.sort(nums);
    call(new LinkedList<>(), nums, new boolean[nums.length]);
    return res;
}

void call(Deque<Integer> path, int[] nums, boolean[] used) {
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
        if (!used[i]) {
            // 标记为使用过，加入队列
            used[i] = true;
            path.add(nums[i]);

            call(path, nums, used);

            // 标记为未使用，剔除队列
            path.removeLast();
            used[i] = false;//回溯
        }
    }
}
*/