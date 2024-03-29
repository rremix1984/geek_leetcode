/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.MathUtils.getArray;
import static com.leetcode.util.SystemUtil.*;

/**
    [ARRAY] ||||||
   （中等）
    NO.46 全排列
        给定一个不含重复数字的数组nums，
        返回其所有可能的全排列。你可以按任意顺序返回答案。
    示例1：
        输入：nums = {1, 2, 3}
        输出：{{1, 2, 3}, {1, 3, 2}, {2, 1, 3},
              {2, 3, 1}, {3, 1, 2}, {3, 2, 1}}
    示例2：
        输入：nums = {0, 1}
        输出：{{0, 1}, {1, 0}}
    示例3：
        输入：nums = {1}
        输出：{{1}}
*/
@SuppressWarnings("all")
public class NO046_N_Permutations {

    @Test
    public void test() {
        assert arrayAllMatch(getArray(
                new int[][]{{1, 2, 3}, {1, 3, 2},
                            {2, 3, 1}, {2, 1, 3},
                            {3, 2, 1}, {3, 1, 2}}),
                permute(new int[]{1, 2, 3}));
        assert arrayAllMatch(getArray(
                new int[][]{{1}}),
                permute(new int[]{1}));
    }

    private List<List<Integer>> permute(int[] nums) {
        // 2024/2/24 NO.6
        // 2024/3/13 NO.7 回溯法
        // 2024/3/16 NO.8 虽然没做出来，但是进步很大，就差一点。
        // 2024/3/18 NO.9 一遍过
        // 2024/3/21 NO.10 在提示下，做出来了，死记硬背的题型，并没有理解
        // 2024/3/23 NO.11 一遍过
        List<List<Integer>> res = new ArrayList<>();

        return res;
    }

}












/*
// 方法1：
public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> res = new LinkedList<>();
    Deque<Integer> alreadyList = new LinkedList<>();
    backtrack(res, nums, alreadyList);
    return res;
}

void backtrack(List<List<Integer>> res, int[] nums, Deque<Integer> alreadyList) {
    if (alreadyList.size() == nums.length) {
        res.add(new LinkedList(alreadyList));
        return;
    }

    for (int i = 0; i < nums.length; i++) {
        // 为了不重复，每次都判断里面是否已经有了这个元素
        if (alreadyList.contains(nums[i]))
            continue;

        // 加入元素 [1] 、[1, 2]、 [1, 2, 3]、 [1, 2, 3, 4]
        alreadyList.add(nums[i]);

        // 递归调用方法 形成数组
        backtrack(res, nums, alreadyList);

        // 上一层遍历过的元素，下一层就换个继续便利
        //  [1]
        // add [2] -> [2, 3, 4] remove [2] -> [3, 4]
        alreadyList.removeLast();
    }
}

// 方法2：回溯法
public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> res = new LinkedList<>();
    Deque<Integer> list = new LinkedList();
    call(res, nums, list);
    return res;
}

public void call(List<List<Integer>> res, int[] nums, Deque<Integer> list) {
    // 当所有元素都在list里面 代表遍历完成
    if (list.size() == nums.length) {
        res.add(new ArrayList<>(list));
        return;
    }

    for (int num : nums) {
        if (list.contains(num))
            continue;
        list.add(num);
        call(res, nums, list);
        list.removeLast();
    }
}
*/