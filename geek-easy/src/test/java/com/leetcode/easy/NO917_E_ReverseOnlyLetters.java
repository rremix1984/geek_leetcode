/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.SwapUtil.swap;
import static java.lang.Character.isLetter;
import static org.junit.Assert.assertEquals;

/**
    [STRING] |
    （简单）
    917. 仅仅反转字母
        给你一个字符串s，根据下述规则反转字符串：
         1）所有非英文字母保留在原有位置。
         2）所有英文字母（小写或大写）位置反转。
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
public class NO917_E_ReverseOnlyLetters {

    @Test
    public void test() {
        assertEquals("dc-ba",
            reverseOnlyLetters("ab-cd"));// dc-ba
        assertEquals("j-Ih-gfE-dCba",
            reverseOnlyLetters("a-bC-dEf-ghIj"));// j-Ih-gfE-dCba
        assertEquals("Qedo1ct-eeLg=ntse-T!",
            reverseOnlyLetters("Test1ng-Leet=code-Q!"));// Qedo1ct-eeLg=ntse-T!
    }

    public String reverseOnlyLetters(String s) {
        // 2024/3/4 NO.1
        char[] arr = s.toCharArray();
        return new String(arr);
    }

}















/**
// 方法1：
public String reverseOnlyLetters(String s) {
    char[] arr = s.toCharArray();
    int left = 0;
    int right = s.length() - 1;
    while (true) {
        // 判断左边是否扫描到字母
        while (left < right && !Character.isLetter(s.charAt(left)))
            left++;

        // 判断右边是否扫描到字母
        while (right > left && !Character.isLetter(s.charAt(right)))
            right--;

        if (left >= right)
            break;

        swap(arr, left++, right--);
    }
    return new String(arr);
}
*/