/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static com.leetcode.util.MathUtils.isVowel;
import static com.leetcode.util.SwapUtil.swap;
import static org.junit.Assert.assertEquals;

/**
    (简单)
    345. 反转字符串中的元音字母
        给你一个字符串 s ，仅反转字符串中的所有元音字母，并返回结果字符串。
        元音字母包括 'a'、'e'、'i'、'o'、'u'，且可能以大小写两种形式出现。
    示例 1：
        输入：s = "hello"
        输出："holle"
    示例 2：
        输入：s = "leetcode"
        输出："leotcede"
    提示：
        1 <= s.length <= 3 * 105
        s 由 可打印的 ASCII 字符组成
*/
public class NO345_E_ReverseVowels_x2 {

    @Test
    public void test() {
        assertEquals("holle", reverseVowels("hello"));
        assertEquals("leotcede", reverseVowels("leetcode"));
    }

    public String reverseVowels(String s) {
        char[] chars = s.toCharArray();
        return new String(chars);
    }

}




















/**
public String reverseVowels(String s) {
    char[] arr = s.toCharArray();
    int i = 0;
    int j = s.length() - 1;
    while (i < j) {
        while (i < s.length() && !isVowel(arr[i]))
            i++;

        while (j > 0 && !isVowel(arr[j]))
            j--;

        if (i < j)
            swap(arr, i++, j--);
    }
    return new String(arr);
}
*/