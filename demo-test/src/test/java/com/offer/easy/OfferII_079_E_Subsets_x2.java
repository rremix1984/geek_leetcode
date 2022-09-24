/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static com.leetcode.util.LogUtil.info;

/**
    (中等)
    剑指 Offer II 079. 所有子集
        给定一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
        解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
    示例 1：
        输入：nums = {1, 2, 3}
        输出：{{}, {1}, {2}, {1, 2}, {3}, {1, 3}, {2, 3}, {1, 2, 3}}
    示例 2：
        输入：nums = {0}
        输出：{{}, {0}}
*/
public class OfferII_079_E_Subsets_x2 {

    @Test
    public void test() {
        List<List<Integer>> list = new ArrayList<List<Integer>>(){{
            add(new ArrayList<>());
            add(new ArrayList<Integer>(){{add(1);}});
            add(new ArrayList<Integer>(){{add(2);}});
            add(new ArrayList<Integer>(){{add(3);}});
            add(new ArrayList<Integer>(){{add(1);add(2);}});
            add(new ArrayList<Integer>(){{add(1);add(3);}});
            add(new ArrayList<Integer>(){{add(2);add(3);}});
            add(new ArrayList<Integer>(){{add(1);add(2);add(3);}});
        }};
        List<List<Integer>> list2 = subsets(new int[]{1, 2, 3});
        assert list.containsAll(list2);
        assert list2.containsAll(list);

        ArrayList<ArrayList<Integer>> list3 = new ArrayList<ArrayList<Integer>>(){{
            add(new ArrayList<>());
            add(new ArrayList<Integer>(){{add(0);}});
        }};
        List<List<Integer>> list4 = subsets(new int[]{0});
        assert list3.containsAll(list4);
        assert list4.containsAll(list3);
    }

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        return ans;
    }
}
















/**
// 方法1：剪枝法
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> ans = new ArrayList<>();
    dfs(0, nums, new ArrayList<>(), ans);
    return ans;
}

public void dfs(int cur, int[] nums, List<Integer> t, List<List<Integer>> ans) {
    if (cur == nums.length) {
        ans.add(new ArrayList<>(t));
        return;
    }
    t.add(nums[cur]);
    dfs(cur + 1, nums, t, ans);
    t.remove(t.size() - 1);
    dfs(cur + 1, nums, t, ans);
}
*/