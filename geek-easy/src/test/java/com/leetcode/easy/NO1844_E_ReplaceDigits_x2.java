/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [STRING]
    (简单)
    1844. 将所有数字用字符替换
        给你一个下标从 0 开始的字符串 s ，它的 偶数 下标处为小写英文字母，奇数 下标处为数字。
        定义一个函数 shift(c, x) ，其中 c 是一个字符且 x 是一个数字，函数返回字母表中 c 后面第 x 个字符。
        比方说，shift('a', 5) = 'f' 和 shift('x', 0) = 'x' 。
        对于每个 奇数 下标 i ，你需要将数字 s[i] 用 shift(s[i-1], s[i]) 替换。
        请你替换所有数字以后，将字符串 s 返回。题目 保证 shift(s[i-1], s[i]) 不会超过 'z' 。
    示例 1：
        输入：s = "a1c1e1"
        输出："abcdef"
        解释：数字被替换结果如下：
            - s[1] -> shift('a',1) = 'b'
            - s[3] -> shift('c',1) = 'd'
            - s[5] -> shift('e',1) = 'f'
    示例 2：
        输入：s = "a1b2c3d4e"
        输出："abbdcfdhe"
        解释：数字被替换结果如下：
            - s[1] -> shift('a',1) = 'b'
            - s[3] -> shift('b',2) = 'd'
            - s[5] -> shift('c',3) = 'f'
            - s[7] -> shift('d',4) = 'h'
*/
public class NO1844_E_ReplaceDigits_x2 {

    @Test
    public void test() {
        assert "abcdef".equals(replaceDigits("a1c1e1"));
        assert "abbdcfdhe".equals(replaceDigits("a1b2c3d4e"));
    }

    public String replaceDigits(String s) {
        char[] arr = s.toCharArray();
        for (int i = 1; i < arr.length; i += 2)
            arr[i] += arr[i - 1] - '0';

        return new String(arr);
    }

}

















/**
public String replaceDigits(String s) {
    char[] arr = s.toCharArray();
    for (int i = 1; i < arr.length; i += 2)
        arr[i] += arr[i - 1] - '0';

    return new String(arr);
}
*/