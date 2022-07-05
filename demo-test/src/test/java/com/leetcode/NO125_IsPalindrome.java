/**
 * copyright 2022/1/19
 */
package com.leetcode;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static com.leetcode.util.LogUtils.info;
import static java.lang.Character.isLetterOrDigit;
import static java.lang.Character.toLowerCase;

@Slf4j
public class NO125_IsPalindrome {

    @Test
    public void test() {
        info(log, isPalindrome("A man, a plan, a canal：Panama"));
    }

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

}