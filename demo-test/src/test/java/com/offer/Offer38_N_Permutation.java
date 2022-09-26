/**
 * copyright 2022/1/19
 */
package com.offer;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.getArrays;
import static com.leetcode.util.SwapUtil.swap;
import static java.util.Arrays.asList;

/**
    (中等)
    剑指 Offer 38. 字符串的排列
        输入一个字符串，打印出该字符串中字符的所有排列。
        你可以以任意顺序返回这个字符串数组，但里面不能有重复元素。
    示例:
        输入：s = "abc"
        输出：["abc","acb","bac","bca","cab","cba"]
*/
public class Offer38_N_Permutation {

    @Test
    public void test() {
        List<String> target = asList(getArrays("abc", "acb", "bac", "bca", "cab", "cba"));
        List<String> source = asList(permutation("abc"));
        assert target.containsAll(source);
        assert source.containsAll(target);
    }

    public String[] permutation(String s) {
        // 先排序
        char[] arr = s.toCharArray();
        Arrays.sort(arr);

        // 递归，剪枝
        List<String> res = new ArrayList<>();
        backtrack(res, new boolean[s.length()], arr, 0, s.length(), new StringBuffer());

        return res.toArray(new String[0]);
    }

    public void backtrack(List<String> res, boolean[] vis, char[] arr, int i, int n, StringBuffer perm) {
        // 满足条件
        if (i == n) {
            res.add(perm.toString());
            return;
        }

        // 剪枝
        for (int j = 0; j < n; j++) {
            // 前、后相等 或者 干脆访问过
            if (vis[j] || (j > 0
                    && !vis[j - 1]
                    && arr[j - 1] == arr[j]))
                continue;

            // 访问过
            vis[j] = true;

            perm.append(arr[j]);

            backtrack(res, vis, arr, i + 1, n, perm);

            perm.deleteCharAt(perm.length() - 1);

            vis[j] = false;
        }
    }

}

















/**
// 方法1：剪枝法
public String[] permutation(String s) {
    // 先排序
    char[] arr = s.toCharArray();
    Arrays.sort(arr);

    // 递归，剪枝
    List<String> res = new ArrayList<>();
    backtrack(res, new boolean[s.length()], arr, 0, s.length(), new StringBuffer());

    return res.toArray(new String[0]);
}

public void backtrack(List<String> res, boolean[] vis, char[] arr, int i, int n, StringBuffer perm) {
    // 满足条件
    if (i == n) {
        res.add(perm.toString());
        return;
    }

    // 剪枝
    for (int j = 0; j < n; j++) {
        // 前、后相等 或者 干脆访问过
        if (vis[j] || (j > 0
                && !vis[j - 1]
                && arr[j - 1] == arr[j]))
            continue;

        // 访问过
        vis[j] = true;

        perm.append(arr[j]);

        backtrack(res, vis, arr, i + 1, n, perm);

        perm.deleteCharAt(perm.length() - 1);

        vis[j] = false;
    }
}
*/