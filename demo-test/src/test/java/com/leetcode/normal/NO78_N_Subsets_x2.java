/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import java.util.*;
import static com.leetcode.util.LogUtil.info;

/**
    （中等）
    78. 子集
        给你一个整数数组 nums ，数组中的元素 互不相同 。
        返回该数组所有可能的子集（幂集）。解集 不能 包含
        重复的子集。你可以按 任意顺序 返回解集。
    示例 1：
        输入：nums = [1,2,3]
        输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
    示例 2：
        输入：nums = [0]
        输出：[[], [0]]
*/
@SuppressWarnings("all")
public class NO78_N_Subsets_x2 {

    @Test
    public void test() {
        assert new ArrayList<ArrayList<Integer>>(){{
            add(new ArrayList<Integer>(){{}});
            add(new ArrayList<Integer>(){{add(1);}});
            add(new ArrayList<Integer>(){{add(2);}});
            add(new ArrayList<Integer>(){{add(1);add(2);}});
            add(new ArrayList<Integer>(){{add(3);}});
            add(new ArrayList<Integer>(){{add(1);add(3);}});
            add(new ArrayList<Integer>(){{add(2);add(3);}});
            add(new ArrayList<Integer>(){{add(1);add(2);add(3);}});
        }}.stream().allMatch(
            s -> subsets(new int[]{1, 2, 3}).contains(s)
        );
    }

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        return ans;
    }

}













/**
// 方法1 递归法
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> ans = new ArrayList<>();
    ans.add(new ArrayList<>());
    for (int num : nums) {
        List<List<Integer>> newSubsets = new ArrayList<>();
        for (List<Integer> subset : ans) {
            List<Integer> newSubset = new ArrayList<>(subset);
            newSubset.add(num);
            newSubsets.add(newSubset);
        }
        ans.addAll(newSubsets);
    }
    return ans;
}


// 方法2 回溯法
List<List<Integer>> ans = new ArrayList<>();

public List<List<Integer>> subsets(int[] nums) {
    // start 子集第一个数在nums中最早可以出现的位置
    // i 是子集的长度
    // cur 是正在构造的子集
    for (int i = 0; i <= nums.length; i++) {
        call(0, i, new ArrayList<>(), nums);
    }
    return ans;
}

//运行一次能够构造所有长度为 k 的子集
void call(int start, int k, ArrayList<Integer> cur, int[] nums) {
    if (k == 0) {
        ans.add(new ArrayList<Integer>(cur));
        return;
    }

    for (int i = start; i < nums.length; i++) {
        cur.add(nums[i]);
        call(i + 1, k - 1, cur, nums);
        cur.remove(cur.size() - 1);
    }
}


// 方案3 字典排序法
List<List<Integer>> ans = new ArrayList<>();

public List<List<Integer>> subsets(int[] nums) {
    int n = nums.length;
    for (int i = (int) Math.pow(2, n);
         i < (int) Math.pow(2, n + 1) ;
         i++) {
        String mask = Integer.toBinaryString(i).substring(1);
        List<Integer> cur = new ArrayList<>();
        for (int j = 0; j < n; j++) {
            if (mask.charAt(j) == '1')
                cur.add(nums[j]);
        }
        ans.add(cur);
    }
    return ans;
}
*/