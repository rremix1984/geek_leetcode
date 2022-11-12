/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    1704. 判断字符串的两半是否相似
        给你一个偶数长度的字符串 s 。将其拆分成长度相同的两半，前一半为 a ，后一半为 b 。
        两个字符串 相似 的前提是它们都含有相同数目的元音
        （'a'，'e'，'i'，'o'，'u'，'A'，'E'，'I'，'O'，'U'）。注意，s 可能同时含有大写和小写字母。
        如果 a 和 b 相似，返回 true ；否则，返回 false 。
    示例 1：
        输入：s = "book"
        输出：true
        解释：a = "bo" 且 b = "ok" 。a 中有 1 个元音，b 也有 1 个元音。所以，a 和 b 相似。
    示例 2：
        输入：s = "textbook"
        输出：false
        解释：a = "text" 且 b = "book" 。a 中有 1 个元音，b 中有 2 个元音。因此，a 和 b 不相似。
        注意，元音 o 在 b 中出现两次，记为 2 个。
*/
public class NO1704_E_HalvesAreAlike_x2 {

    @Test
    public void test() {
        assert halvesAreAlike("book");
        assert !halvesAreAlike("textbook");
    }

    public boolean halvesAreAlike(String s) {
        int cnt = 0;
        s = s.toUpperCase();
        int mid = s.length() / 2;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ("AEIOU".indexOf(c) != -1)
                cnt = i < mid ? ++cnt : --cnt;
        }
        return cnt == 0;
    }

}

















/**
// 方法二： 维护一个cnt值，a中有元音+1，b中有元音-1，最后cnt为0则结果为true
public boolean halvesAreAlike(String s) {
    int cnt = 0;
    s = s.toUpperCase();
    int mid = s.length() >> 1;
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if ("AEIOU".indexOf(c) != -1)
            cnt = i < mid ? ++cnt : --cnt;
    }
    return cnt == 0;
}
*/
