/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
    (简单)
    2264. 字符串中最大的 3 位相同数字
        给你一个字符串 num ，表示一个大整数。如果一个整数满足下述所有条件，则认为该整数是一个 优质整数 ：
        该整数是 num 的一个长度为 3 的 子字符串 。
        该整数由唯一一个数字重复 3 次组成。
        以字符串形式返回 最大的优质整数 。如果不存在满足要求的整数，则返回一个空字符串 "" 。
        注意：
        子字符串 是字符串中的一个连续字符序列。
        num 或优质整数中可能存在 前导零 。
    示例 1：
        输入：num = "6777133339"
        输出："777"
        解释：num 中存在两个优质整数："777" 和 "333" 。
        "777" 是最大的那个，所以返回 "777" 。
    示例 2：
        输入：num = "2300019"
        输出："000"
        解释："000" 是唯一一个优质整数。
    示例 3：
        输入：num = "42352338"
        输出：""
        解释：不存在长度为 3 且仅由一个唯一数字组成的整数。因此，不存在优质整数。
*/
public class NO2264_E_LargestGoodInteger_x2 {

    @Test
    public void test() {
        assertEquals("", largestGoodInteger("42352338"));
    }

    public String largestGoodInteger(String num) {
        String [] strs = {"000", "111", "222", "333", "444",
                          "555", "666", "777", "888", "999"};
        int index = -1;
        for (int i = 0; i < strs.length; i++)
            if (num.contains(strs[i]))
                index = i;

        if (index == -1)
            return "";

        return strs[index];
    }

}

















/**
// 方法1：
public String largestGoodInteger(String num) {
    int f = 1;
    int x = -1;
    for (int i = 1; i < num.length(); i++)
        if (num.charAt(i) != num.charAt(i - 1))
            f = 1;
        else if (++f == 3)
            x = Math.max(x, num.charAt(i) - '0');

    return x == -1 ? "" : x == 0 ? "000" : Integer.toString(x * 111);
}

// 方法2：
public String largestGoodInteger(String num) {
    String [] strs = {"000", "111", "222", "333", "444",
            "555", "666", "777", "888", "999"};
    int index = -1;
    for (int i = 0; i < strs.length; i++)
        if (num.contains(strs[i]))
            index = i;

    if (index == -1)
        return "";
    else
        return strs[index];
}
*/