/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
    (机房)
    168. Excel表列名称
        给你一个整数 columnNumber ，返回它在 Excel 表中相对应的列名称。
    例如：A -> 1      Z -> 26
         B -> 2      AA -> 27
         C -> 3      AB -> 28
         ...         ...
    示例 1：
        输入：columnNumber = 1
        输出："A"
    示例 2：
        输入：columnNumber = 28
        输出："AB"
    示例 3：
        输入：columnNumber = 701
        输出："ZY"
    示例 4：
        输入：columnNumber = 2147483647
        输出："FXSHRXW"
*/
public class NO168_E_ConvertToTitle_x2 {

    @Test
    public void test() {
        assertEquals("A",
            convertToTitle(1));
        assertEquals("AB",
            convertToTitle(28));
        assertEquals("ZY",
            convertToTitle(701));
        assertEquals("FXSHRXW",
            convertToTitle(2147483647));
    }

    public String convertToTitle(int columnNumber) {
        StringBuilder sb = new StringBuilder();
        return sb.reverse().toString();
    }

}



















/**
// 方法1：26进制法
public String convertToTitle(int columnNumber) {
    StringBuilder sb = new StringBuilder();
    while (columnNumber > 0) {
        int tmp = (columnNumber - 1) % 26 + 1;
        sb.append((char) (tmp - 1 + 'A'));
        columnNumber = (columnNumber - tmp) / 26;
    }
    return sb.reverse().toString();
}
*/