/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.SwapUtil.swap;
import static java.lang.Character.isLetter;

/**
    （简单）
    917. 仅仅反转字母
        给你一个字符串 s ，根据下述规则反转字符串：
        所有非英文字母保留在原有位置。
        所有英文字母（小写或大写）位置反转。
        返回反转后的 s 。
    示例 1：
        输入：s = "ab-cd"
        输出："dc-ba"
    示例 2：
        输入：s = "a-bC-dEf-ghIj"
        输出："j-Ih-gfE-dCba"
    示例 3：
        输入：s = "Test1ng-Leet=code-Q!"
        输出："Qedo1ct-eeLg=ntse-T!"
*/
public class NO917_ReverseOnlyLetters {

    @Test
    public void test() {
        info(reverseOnlyLetters("ab-cd"));// dc-ba
        info(reverseOnlyLetters("a-bC-dEf-ghIj"));// j-Ih-gfE-dCba
        info(reverseOnlyLetters("Test1ng-Leet=code-Q!"));// Qedo1ct-eeLg=ntse-T!
    }

    public String reverseOnlyLetters(String s) {
        int n = s.length();
        char[] arr = s.toCharArray();
        int left = 0;
        int right = n - 1;
        while (true) {
            while (left < right && !isLetter(s.charAt(left))) // 判断左边是否扫描到字母
                left++;

            while (right > left && !isLetter(s.charAt(right))) // 判断右边是否扫描到字母
                right--;

            if (left >= right)
                break;

            swap(arr, left, right);
            left++;
            right--;
        }
        return new String(arr);
    }
}















/**
// 方法1：
public String reverseOnlyLetters(String s) {
    int n = s.length();
    char[] arr = s.toCharArray();
    int left = 0;
    int right = n - 1;
    while (true) {
        while (left < right && !Character.isLetter(s.charAt(left))) // 判断左边是否扫描到字母
            left++;

        while (right > left && !Character.isLetter(s.charAt(right))) // 判断右边是否扫描到字母
            right--;

        if (left >= right)
            break;

        swap(arr, left, right);
        left++;
        right--;
    }
    return new String(arr);
}
*/