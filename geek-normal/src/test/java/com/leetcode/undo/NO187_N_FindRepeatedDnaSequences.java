/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.leetcode.util.MathUtils.getArray;

/**
    (中等)
    187. 重复的DNA序列
        DNA序列 由一系列核苷酸组成，缩写为 'A', 'C', 'G' 和 'T'.。
        例如，"ACGAATTCCG" 是一个 DNA序列 。
        在研究 DNA 时，识别 DNA 中的重复序列非常有用。
        给定一个表示 DNA序列 的字符串 s ，返回所有在 DNA 分子中出现不止一次的
        长度为 10 的序列(子字符串)。你可以按 任意顺序 返回答案。
    示例 1：
        输入：s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"
        输出：["AAAAACCCCC", "CCCCCAAAAA"]
    示例 2：
        输入：s = "AAAAAAAAAAAAA"
        输出：["AAAAAAAAAA"]
    提示：
        0 <= s.length <= 105
        s[i]=='A'、'C'、'G' or 'T'
*/
public class NO187_N_FindRepeatedDnaSequences {

    @Test
    public void test() {
        assert getArray("AAAAACCCCC","CCCCCAAAAA").equals(
                findRepeatedDnaSequences("AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"));
        assert getArray("AAAAAAAAAA").equals(
                findRepeatedDnaSequences("AAAAAAAAAAAAA"));
    }

    public List<String> findRepeatedDnaSequences(String s) {
        List<String> ans = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i + 10 <= s.length(); i++) {
            String cur = s.substring(i, i + 10);
            int cnt = map.getOrDefault(cur, 0);
            if (cnt == 1)
                ans.add(cur);
            map.put(cur, cnt + 1);
        }
        return ans;
    }

}
















/**
// 方法1：
public List<String> findRepeatedDnaSequences(String s) {
    List<String> ans = new ArrayList<>();
    Map<String, Integer> cnt = new HashMap<>();
    for (int i = 0; i <= s.length() - 10; i++) {
        String sub = s.substring(i, i + 10);
        cnt.put(sub, cnt.getOrDefault(sub, 0) + 1);
        if (cnt.get(sub) == 2)
            ans.add(sub);
    }
    return ans;
}

// 方法2：
public List<String> findRepeatedDnaSequences(String s) {
    List<String> ans = new ArrayList<>();
    int n = s.length();
    Map<String, Integer> map = new HashMap<>();
    for (int i = 0; i + 10 <= n; i++) {
        String cur = s.substring(i, i + 10);
        int cnt = map.getOrDefault(cur, 0);
        if (cnt == 1) ans.add(cur);
        map.put(cur, cnt + 1);
    }
    return ans;
}
*/