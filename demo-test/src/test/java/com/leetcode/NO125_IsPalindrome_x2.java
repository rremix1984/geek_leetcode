/**
 * copyright 2022/1/19
 */
package com.leetcode;

import com.leetcode.util.Logable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
 * 回文字符串
 * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
 * 说明：本题中，我们将空字符串定义为有效的回文串。
 *
 * 示例 1:
 *
 * 输入: "A man, a plan, a canal: Panama"
 * 输出: true
 * 解释："amanaplanacanalpanama" 是回文串
 * 示例 2:
 *
 * 输入: "race a car"
 * 输出: false
 * 解释："raceacar" 不是回文串
 *
 * 提示：解题思路：双指针法，收尾指针对比
 */
@Slf4j
public class NO125_IsPalindrome_x2 {

    @Test
    public void test() {
        info(isPalindrome("A man, a plan, a canal：Panama"));
    }

    @Logable
    public boolean isPalindrome(String s) {
        int start = 0;
        int end = s.length() - 1;
        char[] chars = s.toCharArray();
        while (start < end) {
            if (chars[start] == ' ' || !Character.isLetterOrDigit(chars[start])) {
                start++;
                continue;
            }

            if (chars[end] == ' ' || !Character.isLetterOrDigit(chars[end])) {
                end--;
                continue;
            }

            if (Character.toLowerCase(chars[start++]) != Character.toLowerCase(chars[end--])) {
                return false;
            }
        }
        return true;
    }

}





/*
public boolean isPalindrome(String s) {
    //解题思路：双指针法，收尾指针对比
    int start = 0;
    int end = s.length() - 1;
    while (start < end) {
        //如果头尾不是字母或数字则往丢弃，看下一个字符
        if(!isLetterOrDigit(s.charAt(start))){
            start++;
            continue;
        }
        if(!isLetterOrDigit(s.charAt(end))){
            end--;
            continue;
        }
        if (toLowerCase(s.charAt(start++)) != toLowerCase(s.charAt(end--)))
            return false;
    }
    return true;
}
*/
