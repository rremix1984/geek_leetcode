/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    1154. 一年中的第几天
        给你一个字符串 date ，按 YYYY-MM-DD 格式表示一个 现行公元纪年法 日期。返回该日期是当年的第几天。
    示例 1：
        输入：date = "2019-01-09"
        输出：9
        解释：给定日期是2019年的第九天。
    示例 2：
        输入：date = "2019-02-10"
        输出：41
*/
public class NO1154_E_DayOfYear_x2 {

    @Test
    public void test() {
        assert 9 == dayOfYear("2019-01-09");
        assert 41 == dayOfYear("2019-02-10");
    }

    public int dayOfYear(String date) {
        return -1;
    }

}




















/**
// 方法1：
public int dayOfYear(String date) {
    int year = Integer.parseInt(date.substring(0, 4));
    int month = Integer.parseInt(date.substring(5, 7));
    int day = Integer.parseInt(date.substring(8));

    // 一三五七八十腊，三十一天毫不差，四六九十一三十天，只有二月二十八
    int[] amount = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    // 闰年 2 月加 1 天
    if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))
        amount[1]++;

    int ans = day;
    for (int i = 0; i < month - 1; i++)
        ans += amount[i];

    return ans;
}
*/