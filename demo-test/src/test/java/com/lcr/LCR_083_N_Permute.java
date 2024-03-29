/**
 * copyright@2019/08/12 lcr
 */
package com.lcr;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.MathUtils.getArray;
import static com.leetcode.util.SystemUtil.arrayAllMatch;

/**
    [ARRAY] |
    (中等)
    LCR.083 全排列
    给定一个不含重复数字的整数数组nums，返回其所有可能的全排列。
    可以 按任意顺序 返回答案。
    示例 1：
        输入：nums = [1, 2, 3]
        输出：[[1, 2, 3]，
              [1, 3, 2],
              [2, 1, 3],
              [2, 3, 1],
              [3, 1, 2],
              [3, 2, 1]]
    示例 2：
        输入：nums = [0, 1]
        输出：[[0, 1],
              [1, 0]]
    示例 3：
        输入：nums = [1]
        输出：[[1]]
    提示：
        1 <= nums.length <= 6
        -10 <= nums[i] <= 10
        nums 中的所有整数 互不相同
    Related Topics:数组,回溯
*/
public class LCR_083_N_Permute {

    @Test
    public void test() {
        assert arrayAllMatch(permute(1, 2, 3),
                getArray(new int[][]{{1, 2, 3},
                                     {1, 3, 2},
                                     {2, 1, 3},
                                     {2, 3, 1},
                                     {3, 1, 2},
                                     {3, 2, 1}}));
        assert arrayAllMatch(permute(0, 1),
                getArray(new int[][]{{0, 1},
                                     {1, 0}}));
        assert arrayAllMatch(permute(1),
                getArray(new int[][]{{1}}));
    }

    public List<List<Integer>> permute(int... nums) {
        // 2024/3/18 NO.1
        // 2024/3/22 NO.2
        List<List<Integer>> res = new ArrayList<>();

        return res;
    }

}















/*
// 方法1：
public List<List<Integer>> permute(int... nums) {
    List<List<Integer>> res = new ArrayList<>();
    dfs(res, new ArrayList<>(), nums, new int[nums.length]);
    return res;
}

public void dfs(List<List<Integer>> res, List<Integer> list, int[] nums, int[] visit) {
    if (list.size() == nums.length) {
        res.add(new ArrayList<>(list));
        return;
    }

    for (int i = 0; i < nums.length; i++) {
        if (visit[i] == 1)
            continue;

        list.add(nums[i]);
        //记录新状态
        visit[i] = 1;

        dfs(res, list, nums, visit);

        list.remove(list.size() - 1);
        //退出时，返回状态
        visit[i] = 0;
    }
}
*/