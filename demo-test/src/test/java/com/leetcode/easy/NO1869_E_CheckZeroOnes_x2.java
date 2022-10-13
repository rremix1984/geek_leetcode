/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    1869. 哪种连续子字符串更长
        给你一个二进制字符串 s 。如果字符串中由 1 组成的 最长 连续子字符串 严格长于 由 0 组成的 最长 连续子字符串，返回 true ；否则，返回 false 。
        例如，s = "110100010" 中，由 1 组成的最长连续子字符串的长度是 2 ，由 0 组成的最长连续子字符串的长度是 3 。
        注意，如果字符串中不存在 0 ，此时认为由 0 组成的最长连续子字符串的长度是 0 。字符串中不存在 1 的情况也适用此规则。
    示例 1：
        输入：s = "1101"
        输出：true
        解释：由 1 组成的最长连续子字符串的长度是 2："1101"
             由 0 组成的最长连续子字符串的长度是 1："1101"
             由 1 组成的子字符串更长，故返回 true 。
    示例 2：
        输入：s = "111000"
        输出：false
        解释：由 1 组成的最长连续子字符串的长度是 3："111000"
             由 0 组成的最长连续子字符串的长度是 3："111000"
             由 1 组成的子字符串不比由 0 组成的子字符串长，故返回 false 。
    示例 3：
        输入：s = "110100010"
        输出：false
        解释：由 1 组成的最长连续子字符串的长度是 2："110100010"
             由 0 组成的最长连续子字符串的长度是 3："110100010"
             由 1 组成的子字符串不比由 0 组成的子字符串长，故返回 false 。

*/
public class NO1869_E_CheckZeroOnes_x2 {

    @Test
    public void test() {
        assert checkZeroOnes("1101");
        assert !checkZeroOnes("111000");
        assert !checkZeroOnes("110100010");
    }

    public boolean checkZeroOnes(String s) {
        return false;
    }

}
















/**
public boolean checkZeroOnes(String s){
    // 字符串【1】长度
    int len1 = 0;
    // 字符串【0】长度
    int len0 = 0;
    // 字符串【1】长度的最大值
    int max1 = 0;
    // 字符串【0】长度的最大值
    int max0 = 0;
    for (char c : s.toCharArray()) {
        if (c == '0') {
            len0++;//【0】计数器+1
            len1 = 0;//【1】计数器归0
        } else {
            len1++;//【1】计数器+1
            len0 = 0;//【0】计数器归0
        }
        max1 = max(len1, max1);
        max0 = max(len0, max0);
    }
    return max1 > max0;
}
*/