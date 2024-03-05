/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [STRING]
    (简单)
    1784. 检查二进制字符串字段
        给你一个二进制字符串 s ，该字符串 不含前导零 。
        如果 s 包含 零个或一个由连续的 '1' 组成的字段 ，返回 true 。否则，返回 false 。
        如果 s 中 由连续若干个 '1' 组成的字段 数量不超过 1，返回 true 。否则，返回 false 。
    示例 1：
        输入：s = "1001"
        输出：false
        解释：由连续若干个 '1' 组成的字段数量为 2，返回 false
    示例 2：
        输入：s = "110"
        输出：true
*/
public class NO1784_E_CheckOnesSegment_x2 {

    @Test
    public void test() {
        assert !checkOnesSegment("1001");
        assert checkOnesSegment("110");
    }

    public boolean checkOnesSegment(String s) {
        return false;
    }

}
















/**
public boolean checkOnesSegment(String s) {
    return !s.contains("01");
}
*/