/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    1662. 检查两个字符串数组是否相等
        给你两个字符串数组 word1 和 word2 。如果两个数组表示的字符串相同，返回 true ；否则，返回 false 。
        数组表示的字符串 是由数组中的所有元素 按顺序 连接形成的字符串。
    示例 1：
        输入：word1 = {"ab", "c"}, word2 = {"a", "bc"}
        输出：true
        解释：
        word1 表示的字符串为 "ab" + "c" -> "abc"
        word2 表示的字符串为 "a" + "bc" -> "abc"
        两个字符串相同，返回 true
    示例 2：
        输入：word1 = {"a", "cb"}, word2 = {"ab", "c"}
        输出：false
    示例 3：
        输入：word1  = {"abc", "d", "defg"}, word2 = {"abcddefg"}
        输出：true
*/
public class NO1662_E_ArrayStringsAreEqual_x2 {

    @Test
    public void test() {
        assert arrayStringsAreEqual(new String[]{"ab", "c"}, new String[]{"a", "bc"});
        assert !arrayStringsAreEqual(new String[]{"a", "cb"}, new String[]{"ab", "c"});
        assert arrayStringsAreEqual(new String[]{"abc", "d", "defg"}, new String[]{"abcddefg"});
    }

    public boolean arrayStringsAreEqual(String[] word1, String[] word2) {
        return true;
    }

}












/**
// 方法1：
public boolean arrayStringsAreEqual(String[] word1, String[] word2) {
    StringBuilder sb1 = new StringBuilder();
    StringBuilder sb2 = new StringBuilder();
    for(String w : word1)
        sb1.append(w);

    for(String w : word2)
        sb2.append(w);

    return sb1.toString().equals(sb2.toString());
}
*/