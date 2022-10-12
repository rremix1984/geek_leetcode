/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static java.lang.Math.max;

/**
    (简单)
    1446. 连续字符
        给你一个字符串 s ，字符串的「能量」定义为：只包含一种字符的最长非空子字符串的长度。
        请你返回字符串 s 的 能量。
    示例 1：
        输入：s = "leetcode"
        输出：2
        解释：子字符串 "ee" 长度为 2 ，只包含字符 'e' 。
    示例 2：
        输入：s = "abbcccddddeeeeedcba"
        输出：5
        解释：子字符串 "eeeee" 长度为 5 ，只包含字符 'e' 。
*/
public class NO1446_E_MaxPower_x2 {

    @Test
    public void test() {
        assert 2 == maxPower("leetcode");
        assert 5 == maxPower("abbcccddddeeeeedcba");
    }

    public int maxPower(String s) {
        int res = 1;
        int cnt = 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                res = max(res, cnt++);
            } else
                cnt = 1;
        }
        return res;
    }

}
