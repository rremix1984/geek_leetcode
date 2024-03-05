/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [STRING]
    (简单)
    1507. 转变日期格式
        给你一个字符串 date ，它的格式为 Day Month Year ，其中：
        Day 是集合 {"1st", "2nd", "3rd", "4th", ..., "30th", "31st"} 中的一个元素。
        Month 是集合 {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"} 中的一个元素。
        Year 的范围在 ​[1900, 2100] 之间。
        请你将字符串转变为 YYYY-MM-DD 的格式，其中：
        YYYY 表示 4 位的年份。
        MM 表示 2 位的月份。
        DD 表示 2 位的天数。
    示例 1：
        输入：date = "20th Oct 2052"
        输出："2052-10-20"
    示例 2：
        输入：date = "6th Jun 1933"
        输出："1933-06-06"
    示例 3：
        输入：date = "26th May 1960"
        输出："1960-05-26"
*/
public class NO1507_E_ReformatDate_x2 {

    @Test
    public void test() {
        assert "2052-10-20".equals(reformatDate("20th Oct 2052"));
        assert "1933-06-06".equals(reformatDate("6th Jun 1933"));
        assert "1960-05-26".equals(reformatDate("26th May 1960"));
        assert "1960-05-02".equals(reformatDate("2nd May 1960"));
        assert "1960-05-01".equals(reformatDate("1st May 1960"));
    }

    public String reformatDate(String date) {
        return null;
    }

}
















/**
public String reformatDate(String date) {
    Map<String, Integer> map = new HashMap<>();
    for (int i = 0; i < 12; i++)
        map.put(MONTH_ENUM[i], i + 1);

    String[] array = date.split(" ");
    int month = map.get(array[1]);
    int day = Integer.parseInt(array[0].substring(0, array[0].length() - 2));
    return String.format("%s-%02d-%02d", array[2], month, day);
}
*/