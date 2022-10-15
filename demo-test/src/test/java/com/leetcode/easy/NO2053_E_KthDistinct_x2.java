/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    2053. 数组中第 K 个独一无二的字符串
        独一无二的字符串 指的是在一个数组中只出现过 一次 的字符串。
        给你一个字符串数组 arr 和一个整数 k ，请你返回 arr 中第 k 个 独一无二的字符串 。
        如果 少于 k 个独一无二的字符串，那么返回 空字符串 "" 。
        注意，按照字符串在原数组中的 顺序 找到第 k 个独一无二字符串。
    示例 1:
        输入：arr = ["d","b","c","b","c","a"], k = 2
        输出："a"
        解释：arr 中独一无二字符串包括 "d" 和 "a" 。
             "d" 首先出现，所以它是第 1 个独一无二字符串。
             "a" 第二个出现，所以它是 2 个独一无二字符串。
             由于 k == 2 ，返回 "a" 。
    示例 2:
        输入：arr = ["aaa","aa","a"], k = 1
        输出："aaa"
        解释：arr 中所有字符串都是独一无二的，所以返回第 1 个字符串 "aaa" 。
    示例 3：
        输入：arr = ["a","b","a"], k = 3
        输出：""
        解释：唯一一个独一无二字符串是 "b" 。由于少于 3 个独一无二字符串，我们返回空字符串 "" 。
*/
public class NO2053_E_KthDistinct_x2 {

    @Test
    public void test() {
        assert "a".equals(kthDistinct(new String[]{"d", "b", "c", "b", "c", "a"}, 2));
        assert "aaa".equals(kthDistinct(new String[]{"aaa", "aa", "a"}, 1));
        assert "".equals(kthDistinct(new String[]{"a", "b", "a"}, 3));
    }

    public String kthDistinct(String[] arr, int k) {
        return "";
    }

}
















/**
public String kthDistinct(String[] arr, int k) {
    // 用HashMap统计字符串出现的次数
    Map<String,Integer> map = new HashMap<>();
    for (String v : arr)
        map.put(v, map.getOrDefault(v, 0) + 1);

    int cnt = 0;
    // 遍历数组，找到第k个独一无二的字符串
    for (String v : arr)
        if (map.get(v) == 1 && ++cnt == k)
            return v;

    return "";
}
*/