/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.getArray;
import static org.junit.Assert.assertEquals;

/**
    （中等）
    77. 组合
    给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。
    你可以按 任何顺序 返回答案。
    示例 1：
        输入：n = 4, k = 2
        输出：{{1, 2}, {1, 3}, {1, 4}, {2, 3}, {2, 4}, {3, 4}}
    示例 2：
        输入：n = 1, k = 1
        输出：{{ 1 }}
*/
@SuppressWarnings("all")
public class NO77_N_Combinations_x2 {

    @Test
    public void test() {
        assertEquals(getArray(new int[][]{{1, 2}, {1, 3}, {1, 4}, {2, 3}, {2, 4}, {3, 4}}),
            combine(4, 2));//{{1, 2}, {1, 3}, {1, 4}, {2, 3}, {2, 4}, {3, 4}}
        assertEquals(getArray(new int[][]{{1}}),
            combine(1, 1));
    }

    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        return ans;
    }

}















/**
List<Integer> temp = new ArrayList<>();
List<List<Integer>> ans = new ArrayList<>();

public List<List<Integer>> combine(int n, int k) {
    dfs(1, n, k);
    return ans;
}

// 从cur 开始，到n为止，k个元素的数组
public void dfs(int cur, int n, int k) {
    // 没加够，不往后面走，继续给temp累加
    // 剪枝：temp 长度加上区间 [cur, n] 的长度小于 k，不可能构造出长度为 k 的 temp
    if (temp.size() + (n - cur + 1) < k)
        return;

    // 记录合法的答案
    if (temp.size() == k) {
        ans.add(new ArrayList<>(temp));
        return;
    }

    // 到这步之前，相当于所有 cur 开头的结果都遍历完了
    // 也就是从 cur 开始向后遍历了
    // 考虑选择当前位置
    temp.add(cur);

    // [1, 2]、 [1, 3]、 [1, 4]
    dfs(cur + 1, n, k);

    temp.remove(temp.size() - 1);

    // 考虑不选择当前位置
    // [2, 3]、 [2, 4]
    dfs(cur + 1, n, k);
}


// 方法2：
public List<List<Integer>> combine(int n, int k) {
    List<List<Integer>> res = new ArrayList<>();
    call(res, new ArrayList<Integer>(), 1, n, k);
    return res;
}

public void call(List<List<Integer>> res, List<Integer> list, int begin, int end, int k) {
    if (list.size() == k) {
        res.add(new ArrayList<>(list));
        return;
    }

    for (int i = begin; i <= end; i++) {
        if (list.contains(i))
            continue;

        list.add(i);

        call(res, list, i, end, k);

        list.remove(list.size() - 1);
    }
}
*/