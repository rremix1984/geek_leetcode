/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.SwapUtil.swap;

/**
    （简单）
    344. 反转字符串
        编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 s 的形式给出。
        不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
    示例 1：
        输入：s = {'h','e','l','l','o'}
        输出：{'o','l','l','e','h'}
    示例 2：
        输入：s = {'H','a','n','n','a','h'}
        输出：{'h','a','n','n','a','H'}
*/
public class NO344_E_ReverseString_x2 {

    @Test
    public void test() {
        char[] char1 = new char[]{'h','e','l','l','o'};
        reverseString(char1);
        info(char1);// {'o','l','l','e','h'}

        char[] char2 = new char[]{'H','a','n','n','a','h'};
        reverseString(char2);
        info(char2);// {'h','a','n','n','a','H'}
    }

    // 双指针法
    public void reverseString(char[] s) {
        int left = 0;
        int right = s.length - 1;
        for (;left < right;) {
            swap(s,left++,right--);
        }
    }
}














/**
// 双指针法
public void reverseString(char[] s) {
    int left = 0;
    int right = s.length - 1;
    while (left < right)
        swap(s, left++, right--);
}
*/