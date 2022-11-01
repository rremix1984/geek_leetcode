/**
 * copyright 2022/1/19
 */
package com.dp;

import org.junit.Test;

/**
    (简单)
    392. 判断子序列
        给定字符串 s 和 t ，判断 s 是否为 t 的子序列。
        字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。（例如，"ace"是"abcde"的一个子序列，而"aec"不是）。
        进阶：如果有大量输入的 S，称作 S1, S2, ... , Sk 其中 k >= 10亿，你需要依次检查它们是否为 T 的子序列。在这种情况下，你会怎样改变代码？
    示例 1：
        输入：s = "abc", t = "ahbgdc"
        输出：true
    示例 2：
        输入：s = "axc", t = "ahbgdc"
        输出：false
*/
public class NO392_E_IsSubsequence_x2 {

    @Test
    public void test() {
        assert isSubsequence("abc", "ahbgdc");
        assert !isSubsequence("axc", "ahbgdc");
    }

    // 方法1：双指针法
    public boolean isSubsequence(String s, String t) {
        int i = 0;
        for (char c : t.toCharArray())
            if (s.charAt(i) == c && ++i == s.length())
                return true;
        return false;
    }

}
