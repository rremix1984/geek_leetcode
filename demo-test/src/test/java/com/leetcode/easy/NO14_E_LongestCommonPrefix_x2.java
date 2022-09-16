/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static org.junit.Assert.assertEquals;

/**
    （简单）
    14. 最长公共前缀
        编写一个函数来查找字符串数组中的最长公共前缀。
        如果不存在公共前缀，返回空字符串 ""。
    示例 1：
        输入：strs = {"flower", "flow", "flight"}
        输出："fl"
    示例 2：
        输入：strs = {"dog", "racecar", "car"}
        输出：""
        解释：输入不存在公共前缀。
*/
public class NO14_E_LongestCommonPrefix_x2 {

    @Test
    public void test() {
        assertEquals("fl", longestCommonPrefix(new String[]{"flower", "flow", "flight"}));// fl
        assertEquals("", longestCommonPrefix(new String[]{"dog", "racecar", "car"}));// ""
    }

    public String longestCommonPrefix(String[] strs) {
        return strs[0];
    }
}

























/*
public String longestCommonPrefix(String[] strs) {
    if (strs == null || strs.length == 0)
        return "";

    for (int i = 0; i < strs[0].length(); i++)  // 遍历每一个单词，每一个字符
        for (int j = 1; j < strs.length; j++)   // 遍历从第二个字符开始的，每一个单词
            if (i == strs[j].length()           // 当字符找完了
                    || strs[j].charAt(i) != strs[0].charAt(i)) // 或者第一次出现第 i, j 个字符不相等的情况时
                return strs[0].substring(0, i); // 前i个字符就是公共前缀

    return strs[0];
}
*/