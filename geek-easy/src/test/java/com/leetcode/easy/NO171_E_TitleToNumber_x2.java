/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    171. Excel 表列序号
        给你一个字符串 columnTitle ，表示 Excel 表格中的列名称。
        返回 该列名称对应的列序号 。
    例如：
        A -> 1
        B -> 2
        C -> 3
        ...
        Z -> 26
        AA -> 27
        AB -> 28
        ...
    示例 1:
        输入: columnTitle = "A"
        输出: 1
    示例 2:
        输入: columnTitle = "AB"
        输出: 28
    示例 3:
        输入: columnTitle = "ZY"
        输出: 701
*/
public class NO171_E_TitleToNumber_x2 {

    @Test
    public void test() {
        assert 1 == titleToNumber("A");
        assert 28 == titleToNumber("AB");
        assert 701 == titleToNumber("ZY");
    }

    public int titleToNumber(String columnTitle) {
        int ans = 0;
        for (char c : columnTitle.toCharArray())
            ans = ans * 26 + (c - 'A' + 1);

        return ans;
    }

}
