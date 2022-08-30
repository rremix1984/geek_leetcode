/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.SwapUtil.swap;
import static java.lang.Math.min;

/**
    541. 反转字符串 II
        给定一个字符串 s 和一个整数 k，从字符串开头算起，每计数至 2k 个字符，就反转这 2k 字符中的前 k 个字符。
        如果剩余字符少于 k 个，则将剩余字符全部反转。
        如果剩余字符小于 2k 但大于或等于 k 个，则反转前 k 个字符，其余字符保持原样。
    示例 1：
        输入：s = "abcdefg", k = 2
        输出："bacdfeg"
    示例 2：
        输入：s = "abcd", k = 2
        输出："bacd"
*/
public class NO541_ReverseStringII {

    @Test
    public void test() {
        info(reverseStr("abcdefg", 2));// "bacdfeg"
        info(reverseStr("abcd", 2));// "bacd"
    }

    public String reverseStr(String s, int k) {
        char[] arr = s.toCharArray();
        for (int i = 0; i < s.length(); i += 2 * k) {
            reverse(arr, i, min(i + k, s.length()) - 1);
        }
        return new String(arr);
    }

    public void reverse(char[] arr, int left, int right) {
        while (left < right) {
            swap(arr, left, right);
            left++;
            right--;
        }
    }
}




















/**
public String reverseStr(String s, int k) {
    int n = s.length();
    char[] arr = s.toCharArray();
    for (int i = 0; i < n; i += 2 * k)
        reverse(arr, i, min(i + k, n) - 1);

    return new String(arr);
}

public void reverse(char[] arr, int left, int right) {
    while (left < right) {
        swap(arr, left, right);
        left++;
        right--;
    }
}
*/