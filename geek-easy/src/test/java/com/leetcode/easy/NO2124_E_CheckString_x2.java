/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [STRING]
    (简单)
    2124. 检查是否所有 A 都在 B 之前
        给你一个 仅 由字符 'a' 和 'b' 组成的字符串  s 。如果字符串中 每个 'a' 都出现在 每个 'b' 之前，返回 true ；否则，返回 false 。
    示例 1：
        输入：s = "aaabbb"
        输出：true
        解释：'a' 位于下标 0、1 和 2 ；而 'b' 位于下标 3、4 和 5 。
             因此，每个 'a' 都出现在每个 'b' 之前，所以返回 true 。
    示例 2：
        输入：s = "abab"
        输出：false
        解释：存在一个 'a' 位于下标 2 ，而一个 'b' 位于下标 1 。
             因此，不能满足每个 'a' 都出现在每个 'b' 之前，所以返回 false 。
    示例 3：
        输入：s = "bbb"
        输出：true
        解释：不存在 'a' ，因此可以视作每个 'a' 都出现在每个 'b' 之前，所以返回 true 。
*/
public class NO2124_E_CheckString_x2 {

    @Test
    public void test() {
        assert checkString("aaabbb");
        assert !checkString("abab");
        assert checkString("bbb");
    }

    public boolean checkString(String s) {
        // 字符串  s 仅 由 字符 'a' 和 'b' 组成。
        int i;// 记录 b 第一次出现的地方

        if (s.contains("b"))
            i = s.indexOf("b");
        else
            return true;

        char[] arr = s.toCharArray();

        if (i == arr.length - 1)
            return true;

        for (; i < arr.length; i++)
            if (arr[i] == 'a')
                return false;

        return true;
    }

}















/**
// 方法1：
public boolean checkString(String s) {
    int q = s.indexOf('b');
    int p = s.lastIndexOf('a');
    if (q == -1 || p == -1)
        return true;

    return q > p;
}

// 方法2：暴力法
public boolean checkString(String s) {
    return !s.contains("ba");
}

// 方法3：
public boolean checkString(String s) {
    // 字符串  s 仅 由 字符 'a' 和 'b' 组成。
    int i;// 记录 b 第一次出现的地方

    if (s.contains("b"))
        i = s.indexOf("b");
    else
        return true;

    char[] arr = s.toCharArray();

    if (i == arr.length - 1)
        return true;

    for (; i < arr.length; i++)
        if (arr[i] == 'a')
            return false;

    return true;
}
*/