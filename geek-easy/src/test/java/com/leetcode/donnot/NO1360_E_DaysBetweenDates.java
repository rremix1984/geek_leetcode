/**
 * copyright 2022/1/19
 */
package com.leetcode.donnot;

import org.junit.Test;

import static java.lang.Math.abs;

/**
    (简单)
    1360. 日期之间隔几天
        请你编写一个程序来计算两个日期之间隔了多少天。
        日期以字符串形式给出，格式为 YYYY-MM-DD，如示例所示。
    示例 1：
        输入：date1 = "2019-06-29", date2 = "2019-06-30"
        输出：1
    示例 2：
        输入：date1 = "2020-01-15", date2 = "2019-12-31"
        输出：15
*/
public class NO1360_E_DaysBetweenDates {

    @Test
    public void test() {
        assert 1 == daysBetweenDates("2019-06-29", "2019-06-30");
        assert 15 == daysBetweenDates("2020-01-15", "2019-12-31");
    }

    public int daysBetweenDates(String date1, String date2) {
        return abs(toDay(date1) - toDay(date2));
    }

    public int toDay(String dateStr) {
        String[] temp = dateStr.split("-");
        int year = Integer.parseInt(temp[0]);
        int month = Integer.parseInt(temp[1]);
        int day = Integer.parseInt(temp[2]);
        // 对于二月份的处理非常麻烦，因为二月份需要考虑当年是闰年还是平年来决定天数。
        // 既然这样，我们不妨把原先的日历往前2个月。这样一来，三月份便充当了今年的1月，
        // 四月份为今年的2月，依此类推，十二月份为今年的10月，并把下一年的一月份和二月份作为今年的11月和12月。
        // 假如为1月或2月, 分别改为去年的11月和12月
        if (month <= 2) {
            year--;
            month += 10;
        // 否则仅需往前2个月
        } else {
            month -= 2;
        }
        // Zeller公式
        return 365 * year
               + (year / 4 - year / 100 + year / 400)
               + (30 * month)
               + (3 * month - 1) / 5
               + day;
    }

}