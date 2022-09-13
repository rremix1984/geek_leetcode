/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import java.util.*;
import static com.leetcode.util.LogUtil.info;

/**
   （中等）
    46.全排列
        给定一个不含重复数字的数组 nums，
        返回其所有可能的全排列 。你可以按任意顺序返回答案。
    示例1：
        输入：nums = [1,2,3]
        输出：[[1, 2, 3], [1, 3, 2], [2, 1, 3],
              [2, 3, 1], [3, 1, 2], [3, 2, 1]]
    示例2：
        输入：nums = [0, 1]
        输出：[[0, 1], [1, 0]]
    示例3：
        输入：nums = [1]
        输出：[[1]]
*/
public class NO46_N_Permutations_x3 {

    @Test
    public void test() {
        info(permute(new int[]{1, 2, 3}));
    }

    List<List<Integer>> res = new LinkedList<>();

    public List<List<Integer>> permute(int[] nums) {
        return res;
    }

}













/**
List<List<Integer>> res = new LinkedList<>();

public List<List<Integer>> permute(int[] nums) {
    Deque<Integer> alreadyList = new LinkedList<>();
    backtrack(nums, alreadyList);
    return res;
}

void backtrack(int[] nums, Deque<Integer> alreadyList) {
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
        backtrack(nums, alreadyList);

        // 上一层遍历过的元素，下一层就换个继续便利
        //  [1]
        // add [2] -> [2, 3, 4] remove [2] -> [3, 4]
        alreadyList.removeLast();
    }
}


// 方法2：回溯法
List<List<Integer>> res = new LinkedList<>();

public List<List<Integer>> permute(int[] nums) {
    Deque<Integer> list = new LinkedList();
    call(nums, list);
    return res;
}

public void call(int[] nums, Deque<Integer> list) {
    // 当所有元素都在list里面 代表遍历完成
    if (list.size() == nums.length) {
        res.add(new ArrayList<>(list));
        return;
    }

    for (int num : nums) {
        if (list.contains(num))
            continue;

        list.add(num);

        call(nums, list);

        list.removeLast();
    }
}
*/