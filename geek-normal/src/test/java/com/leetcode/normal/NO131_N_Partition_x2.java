/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.leetcode.util.MathUtils.getArray;
import static com.leetcode.util.MathUtils.isHuiwen;


/**
    (中等)
    131. 分割回文串
        给你一个字符串 s，请你将 s 分割成一些子串，使每个子串都是 回文串 。返回 s 所有可能的分割方案。
        回文串 是正着读和反着读都一样的字符串。
    示例 1：
        输入：s = "aab"
        输出：[["a","a","b"],["aa","b"]]
    示例 2：
        输入：s = "a"
        输出：[["a"]]
*/
public class NO131_N_Partition_x2 {

    @Test
    public void test() {
        assert partition("aab").equals(getArray(new String[][]{{"a", "a", "b"}, {"aa", "b"}}));
        assert partition("a").equals(getArray(new String[][]{{"a"}}));
    }

    // 方法1：回溯法
    public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();
        call(res, new LinkedList<>(), s, 0);
        return res;
    }

    private void call(List<List<String>> res, LinkedList<String> list, String s, int i) {
        if (i == s.length()) {
            res.add(new ArrayList<>(list));
            return;
        }

        for (int j = i; j < s.length(); j++) {
            if (!isHuiwen(s.toCharArray(), i, j))
                continue;
            list.addLast(s.substring(i, j + 1));
            call(res, list, s, j + 1);
            list.removeLast();
        }
    }

}

















/**
// 方法1：回溯法
public List<List<String>> partition(String target) {
    List<List<String>> res = new ArrayList<>();
    call(res, new LinkedList<>(), target, 0);
    return res;
}

public void call(List<List<String>> res, LinkedList<String> list, String target, int start){
    if (start >= target.length()){
        res.add(new ArrayList<>(list));
        return;
    }

    for (int i = start; i < target.length(); i++) {
        if (!isHuiwen(target.toCharArray(), start, i))
            continue;

        list.addLast(target.substring(start, i + 1));
        call(res, list, target, i + 1);
        list.removeLast();
    }
}
*/