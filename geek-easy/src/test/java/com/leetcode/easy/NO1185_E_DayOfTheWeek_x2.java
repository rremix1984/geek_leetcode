/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.time.Year;
import static com.leetcode.util.MathUtils.*;

/**
    [STRING]
    (简单)
    1185. 一周中的第几天
        给你一个日期，请你设计一个算法来判断它是对应一周中的哪一天。
        输入为三个整数：day、month 和 year，分别表示日、月、年。
        您返回的结果必须是这几个值中的一个 {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"}。
    示例 1：
        输入：day = 31, month = 8, year = 2019
        输出："Saturday"
    示例 2：
        输入：day = 18, month = 7, year = 1999
        输出："Sunday"
    示例 3：
        输入：day = 15, month = 8, year = 1993
        输出："Sunday"

    题目保证日期是在 1971 到 2100 之间，我们可以计算给定日期距离 1970 的最后一天（星期四）间隔了多少天，从而得知给定日期是周几。
    具体的，可以先通过循环处理计算年份在 [1971,year−1] 时间段，经过了多少天（注意平年为 365，闰年为 366）；
    然后再处理当前年 yearyear 的月份在 [1,month−1] 时间段 ，经过了多少天（注意当天年是否为闰年，特殊处理 22 月份），
    最后计算当前月 monthmonth 经过了多少天，即再增加 dayday 天。
    得到距离 1970 的最后一天（星期四）的天数后进行取模，即可映射回答案。
*/
public class NO1185_E_DayOfTheWeek_x2 {

    @Test
    public void test() {
        assert "Saturday".equals(dayOfTheWeek(31, 8, 2019));
        assert "Sunday".equals(dayOfTheWeek(18, 7, 1999));
        assert "Sunday".equals(dayOfTheWeek(15, 8, 1993));
        assert "Monday".equals(dayOfTheWeek(29, 2, 2016));
    }

    public String dayOfTheWeek(int day, int month, int year) {
        // 1971年第一天是周四（4）
        int ans = 4 + day;
        for (int y = 1971; y < year; y++) {
            ans += 365;
            // 是闰年
            if (isLeap(y))
                ans++;
        }

        for (int m = 1; m < month; m++) {
            ans += MONTHS[m - 1];
            if (isLeap(year) && month > 2)
                ans++;
        }

        return WEEKS[ans % 7];
    }

}



















/**
// 方法1：
public String dayOfTheWeek(int day, int month, int year) {
    // 1971年第一天是周四（4）
    int ans = 4;
    for (int y = 1971; y < year; y++) {
        // 是闰年
        ans += Year.isLeap(y) ? 366 : 365;
    }

    for (int m = 1; m < month; m++) {
        ans += MONTHS[m - 1];
        if (m == 2 && Year.isLeap(year))
            ans++;
    }
    ans += day;
    return WEEKS[ans % 7];
}
*/