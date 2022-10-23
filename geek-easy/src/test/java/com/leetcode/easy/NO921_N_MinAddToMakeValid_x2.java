/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.LogUtil;
import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    (中等)
    921. 使括号有效的最少添加
        只有满足下面几点之一，括号字符串才是有效的：
            它是一个空字符串，或者
            它可以被写成 AB （A 与 B 连接）, 其中 A 和 B 都是有效字符串，或者
            它可以被写作 (A)，其中 A 是有效字符串。
        给定一个括号字符串 s ，移动N次，你就可以在字符串的任何位置插入一个括号。
        例如，如果 s = "()))" ，你可以插入一个开始括号为 "(()))" 或结束括号为 "())))" 。
        返回 为使结果字符串 s 有效而必须添加的最少括号数。
    示例 1：
        输入：s = "())"
        输出：1
    示例 2：
        输入：s = "((("
        输出：3
*/
public class NO921_N_MinAddToMakeValid_x2 {

    @Test
    public void test() {
        assert 1 == minAddToMakeValid("())");
        assert 3 == minAddToMakeValid("(((");
        info(minAddToMakeValid(")("));
    }

    public int minAddToMakeValid(String s) {
        int ans = 0;
        return ans;
    }

}
















/**
// 方法1:
public int minAddToMakeValid(String s) {
    int ans = 0;
    int leftCount = 0;
    for (char c : s.toCharArray())
        if (c == '(')
            leftCount++;
        else
            if (leftCount > 0)
                leftCount--;
            else
                ans++;
    ans += leftCount;
    return ans;
}
*/