/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    (简单)
    剑指 Offer 05. 替换空格
        请实现一个函数，把字符串 s 中的每个空格替换成"%20"。
    示例 1：
        输入：s = "We are happy."
        输出："We%20are%20happy."
*/
public class Offer_05_E_ReplaceSpace_x2 {

    @Test
    public void test() {
        info(replaceSpace("We are happy."));
        assert replaceSpace("We are happy.").equals("We%20are%20happy.");
    }

    public String replaceSpace(String s) {
        return null;
    }
}



















/**
// 方法1：
public String replaceSpace(String s) {
    int count = 0;
    for (char c : s.toCharArray())
        if (c == ' ')
            count+=2;

    char[] res = new char[s.length() + count];
    int idx = 0;
    for (char c : s.toCharArray()) {
        if (c == ' ') {
            res[idx++] = '%';
            res[idx++] = '2';
            res[idx++] = '0';
        } else {
            res[idx++] = c;
        }
    }
    return new String(res);
}

// 方法2：优化后的方法1
public String replaceSpace(String s) {
    char[] res = new char[s.length() * 3];
    int idx = 0;
    for (char c : s.toCharArray()) {
        if (c == ' ') {
            res[idx++] = '%';
            res[idx++] = '2';
            res[idx++] = '0';
        } else {
            res[idx++] = c;
        }
    }
    return new String(res, 0, idx);
}
*/