/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    1544. 整理字符串
        给你一个由大小写英文字母组成的字符串 s 。
        一个整理好的字符串中，两个相邻字符 s[i] 和 s[i + 1]，其中 0 <= i <= s.length - 2 ，要满足如下条件:
            若 s[i] 是小写字符，则 s[i + 1] 不可以是相同的大写字符。
            若 s[i] 是大写字符，则 s[i + 1] 不可以是相同的小写字符。
        请你将字符串整理好，每次你都可以从字符串中选出满足上述条件的 两个相邻 字符并删除，直到字符串整理好为止。
        请返回整理好的 字符串 。题目保证在给出的约束条件下，测试样例对应的答案是唯一的。
        注意：空字符串也属于整理好的字符串，尽管其中没有任何字符。
    示例 1：
        输入：s = "leEeetcode"
        输出："leetcode"
        解释：无论你第一次选的是 i = 1 还是 i = 2，都会使 "leEeetcode" 缩减为 "leetcode" 。
    示例 2：
        输入：s = "abBAcC"
        输出：""
        解释：存在多种不同情况，但所有的情况都会导致相同的结果。例如：
             "abBAcC" --> "aAcC" --> "cC" --> ""
             "abBAcC" --> "abBA" --> "aA" --> ""
    示例 3：
        输入：s = "s"
        输出："s"
*/
public class NO1544_E_MakeGood_x2 {

    @Test
    public void test() {
        assert "com/leetcode".equals(makeGood("leEeetcode"));
        assert "".equals(makeGood("abBAcC"));
        assert "s".equals(makeGood("s"));
    }

    public String makeGood(String s) {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

}
















/**
// 方法1：
public String makeGood(String s) {
    StringBuffer ret = new StringBuffer();
    int retIndex = -1;
    int length = s.length();
    for (int i = 0; i < length; i++) {
        char ch = s.charAt(i);
        if (ret.length() > 0
            && toLowerCase(ret.charAt(retIndex)) == toLowerCase(ch)
            && ret.charAt(retIndex) != ch) {
            ret.deleteCharAt(retIndex);
            retIndex--;
        } else {
            ret.append(ch);
            retIndex++;
        }
    }
    return ret.toString();
}

// 方法2：
public String makeGood(String s) {
    StringBuilder sb = new StringBuilder();
    for (char ch : s.toCharArray())
        // ch 和 sb最后一个元素正好是同一个字符的【大写】和【小写】 | 'E' — 'e' | == 32
        if (sb.length() > 0 && abs(ch - sb.charAt(sb.length() - 1)) == 32)
            sb.deleteCharAt(sb.length() - 1);
        else
            sb.append(ch);

    return sb.toString();
}
*/