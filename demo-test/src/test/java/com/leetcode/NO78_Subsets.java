/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.leetcode.util.LogUtil.info;

/**
    78. 子集
        给你一个整数数组 nums ，数组中的元素 互不相同 。
        返回该数组所有可能的子集（幂集）。解集 不能 包含
        重复的子集。你可以按 任意顺序 返回解集。
    示例 1：
        输入：nums = [1,2,3]
        输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
    示例 2：
        输入：nums = [0]
        输出：[[],[0]]
*/
@SuppressWarnings("all")
public class NO78_Subsets {

    @Test
    public void test() {
        info(subsets(new int[]{1, 2, 3}));
    }

    List<Integer> t = new ArrayList<>();
    List<List<Integer>> ans = new ArrayList<List<Integer>>();

    public List<List<Integer>> subsets(int[] nums) {
        dfs(0, nums);
        return ans;
    }

    public void dfs(int cur, int[] nums) {
        if (cur == nums.length) {
            ans.add(new ArrayList(t));
            return;
        }
        t.add(nums[cur]);
        dfs(cur + 1, nums);
        t.remove(t.size() - 1);

        dfs(cur + 1, nums);
    }

}













/**
List<Integer> t = new ArrayList<Integer>();
List<List<Integer>> ans = new ArrayList<List<Integer>>();

public List<List<Integer>> subsets(int[] nums) {
    dfs(0, nums);
    return ans;
}

public void dfs(int cur, int[] nums) {
    if (cur == nums.length) {
        ans.add(new ArrayList<Integer>(t));
        return;
    }
    t.add(nums[cur]);
    dfs(cur + 1, nums);
    t.remove(t.size() - 1);
    dfs(cur + 1, nums);
}
*/
/**
List<List<Integer>> ans = new ArrayList<List<Integer>>();

public List<List<Integer>> subsets(int[] nums) {
    List<Integer> t = new ArrayList<Integer>();
    int n = nums.length;
    for (int mask = 0; mask < (1 << n); ++mask) {
        t.clear();
        for (int i = 0; i < n; ++i) {
            if ((mask & (1 << i)) != 0) {
                t.add(nums[i]);
            }
        }
        ans.add(new ArrayList(t));
    }
    return ans;
}
*/