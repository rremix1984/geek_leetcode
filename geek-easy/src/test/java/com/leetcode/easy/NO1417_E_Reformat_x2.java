/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import static com.leetcode.util.SwapUtil.swap;
import static java.lang.Character.isAlphabetic;
import static java.lang.Math.abs;

/**
    [STRING]
    (简单)
    1417. 重新格式化字符串
        给你一个混合了数字和字母的字符串 s，其中的字母均为小写英文字母。
        请你将该字符串重新格式化，使得任意两个相邻字符的类型都不同。也就是说，字母后面应该跟着数字，而数字后面应该跟着字母。
        请你返回 重新格式化后 的字符串；如果无法按要求重新格式化，则返回一个 空字符串 。
    示例 1：
        输入：s = "a0b1c2"
        输出："0a1b2c"
        解释："0a1b2c" 中任意两个相邻字符的类型都不同。 "a0b1c2", "0a1b2c", "0c2a1b" 也是满足题目要求的答案。
    示例 2：
        输入：s = "leetcode"
        输出：""
        解释："leetcode" 中只有字母，所以无法满足重新格式化的条件。
    示例 3：
        输入：s = "1229857369"
        输出：""
        解释："1229857369" 中只有数字，所以无法满足重新格式化的条件。
    示例 4：
        输入：s = "covid2019"
        输出："c2o0v1i9d"
    示例 5：
        输入：s = "ab123"
        输出："1a2b3"
*/
public class NO1417_E_Reformat_x2 {

    @Test
    public void test() {
        assert "0a1b2c".equals(reformat("a0b1c2"));
        assert "".equals(reformat("leetcode"));
        assert "".equals(reformat("1229857369"));
        assert "c2o0v1i9d".equals(reformat("covid2019"));
        assert "1a2b3".equals(reformat("ab123"));
    }

    public String reformat(String s) {
        StringBuilder ret = new StringBuilder();
        return ret.toString();
    }

}






















/**
// 方法1：
public String reformat(String s) {
    // 先挑出所有的字母
    StringBuilder ret = new StringBuilder();
    for (char ch : s.toCharArray())
        if (isAlphabetic(ch))
            ret.append(ch);

    //【字母(2倍)】和【数字】的长度【差】不能超过 1
    if (abs(s.length() - ret.length() * 2) > 1)
        return "";

    // 字母都提取出来了，数字使用插入的方式，
    // 如果字母多，数字就插入到下标为奇数的位置（下标是从0开始的）,
    // 否则数字就插入到下标为偶数的位置
    int index = (ret.length() * 2 > s.length()) ? 1 : 0;
    for (int i = 0; i < s.length(); i++) {
        // 跳过字母
        if (isAlphabetic(s.charAt(i)))
            continue;

        // 插入数字 StringBuilder.insert 方法
        ret.insert(index, s.charAt(i));

        // 包含字母、数字一共2个字符
        index += 2;
    }
    return ret.toString();
}

// 方法2：
public String reformat(String s) {
    int sumDigit = 0;
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (Character.isDigit(c)) {
            sumDigit++;
        }
    }
    int sumAlpha = s.length() - sumDigit;
    if (Math.abs(sumDigit - sumAlpha) > 1) {
        return "";
    }

    boolean flag = sumDigit > sumAlpha;
    char[] arr = s.toCharArray();
    for (int i = 0, j = 1; i < s.length(); i += 2) {
        if (Character.isDigit(arr[i]) != flag) {
            while (Character.isDigit(arr[j]) != flag)
                j += 2;
            swap(arr, i, j);
        }
    }
    return new String(arr);
}
*/