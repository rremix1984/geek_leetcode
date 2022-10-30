/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    2437. 有效时间的数目
        给你一个长度为 5 的字符串 time ，表示一个电子时钟当前的时间，格式为 "hh:mm" 。
        最早 可能的时间是 "00:00"，最晚可能的时间是"23:59"。
        在字符串time中，被字符?替换掉的数位是未知的，被替换的数字可能是0到9中的任何一个。
        请你返回一个整数answer，将每一个?都用0到9中一个数字替换后，
        可以得到的有效时间的数目。
    示例 1：
        输入：time = "?5:00"
        输出：2
        解释：我们可以将 ? 替换成 0 或 1 ，得到 "05:00" 或者 "15:00" 。注意我们不能替换成 2 ，因为时间 "25:00" 是无效时间。所以我们有两个选择。
    示例 2：
        输入：time = "0?:0?"
        输出：100
        解释：两个 ? 都可以被 0 到 9 之间的任意数字替换，所以我们总共有 100 种选择。
    示例 3：
        输入：time = "??:??"
        输出：1440
        解释：小时总共有 24 种选择，分钟总共有 60 种选择。所以总共有 24 * 60 = 1440 种选择。
    提示：
        time 是一个长度为 5 的有效字符串，格式为 "hh:mm" 。
        "00" <= hh <= "23"
        "00" <= mm <= "59"
        字符串中有的数位是 '?' ，需要用 0 到 9 之间的数字替换。
*/
public class NO2437_E_CountTime_x2 {

    @Test
    public void test() {
        assert 2 == countTime("?5:00");
        assert 100 == countTime("0?:0?");
        assert 1440 == countTime("??:??");
    }

    public int countTime(String time) {
        int H = 1;
        int M = 1;
        return H * M;
    }

}

















/**
// 方法1：
public int countTime(String time) {
    int hCount = 1;
    int mCount = 1;

    char[] digit = time.toCharArray();
    // 1 两个都是问号
    if (digit[0] == '?' && digit[1] == '?')
        hCount = 24;
        // 1.1 第一个是问号，小时数高位取决于低位
    else if(digit[0] == '?')
        hCount = (digit[1] <= '3') ? 3 : 2;
        // 1.2 第二个是问号
    else if(digit[1] == '?')
        hCount = (digit[0] <= '1') ? 10 : 4;

    // 2、两个都是问号
    if (digit[3] == '?' && digit[4] == '?')
        mCount = 60;
        // 2.1 第一个是问号
    else if (digit[3] == '?')
        mCount = 6;
        // 2.2 第二个是问号
    else if (digit[4] == '?')
        mCount = 10;

    return hCount * mCount;
}
*/