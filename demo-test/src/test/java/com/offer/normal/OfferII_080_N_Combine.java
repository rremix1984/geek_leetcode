/**
 * copyright 2022/1/19
 */
package com.offer.normal;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.MathUtils.getArray;
import static com.leetcode.util.SystemUtil.arrayAllMatch;

/**
    [ARRAY] |
    (中等)
    剑指 Offer II 080. 含有 k 个元素的组合
        给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
    示例 1:
        输入: n = 4, k = 2
        输出:[[2, 4], [3, 4], [2, 3],
             [1, 2], [1, 3], [1, 4]]
    示例 2:
        输入: n = 1, k = 1
        输出: [[1]]
*/
public class OfferII_080_N_Combine {

    @Test
    public void test() {
        arrayAllMatch(combine(4, 2),
                getArray(new int[][]{{2, 4}, {3, 4}, {2, 3}, {1, 2}, {1, 3}, {1, 4}}));
        arrayAllMatch(combine(1, 1),
                getArray(new int[][]{{1}}));
    }

    public List<List<Integer>> combine(int n, int k) {
        // 2024/3/20 NO.1
        List<List<Integer>> ans = new ArrayList<>();
        return ans;
    }

}


















/*
// 方法1：深度遍历 dfs
public List<List<Integer>> combine(int n, int k) {
    List<List<Integer>> ans = new ArrayList<>();
    dfs(ans, new ArrayList<>(), 1, n, k);
    return ans;
}

// 从 1 到 n 最多 k 个元素
public void dfs(List<List<Integer>> ans, List<Integer> temp, int cur, int n, int k) {
    // 剪枝：temp 长度加上区间 [cur, n] 的长度小于 k，不可能构造出长度为 k 的 temp
    if (temp.size() + (n - cur + 1) < k)
        return;

    // 记录合法的答案
    if (temp.size() == k) {
        ans.add(new ArrayList<>(temp));
        return;
    }

    // 考虑选择当前位置
    temp.add(cur);

    dfs(ans, temp, cur + 1, n, k);

    temp.remove(temp.size() - 1);

    // 考虑不选择当前位置
    dfs(ans, temp, cur + 1, n, k);
}
*/