/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    2315. 统计星号
        给你一个字符串 s ，每 两个 连续竖线 '|' 为 一对 。换言之，第一个和第二个 '|' 为一对，第三个和第四个 '|' 为一对，以此类推。
        请你返回 不在 竖线对之间，s 中 '*' 的数目。
        注意，每个竖线 '|' 都会 恰好 属于一个对。
    示例 1：
        输入：s = "l|*e*et|c**o|*de|"
        输出：2
        解释：不在竖线对之间的字符加粗加斜体后，得到字符串："l|*e*et|c**o|*de|" 。
        第一和第二条竖线 '|' 之间的字符不计入答案。
        同时，第三条和第四条竖线 '|' 之间的字符也不计入答案。
        不在竖线对之间总共有 2 个星号，所以我们返回 2 。
    示例 2：
        输入：s = "iamprogrammer"
        输出：0
        解释：在这个例子中，s 中没有星号。所以返回 0 。
    示例 3：
        输入：s = "yo|uar|e**|b|e***au|tifu|l"
        输出：5
        解释：需要考虑的字符加粗加斜体后："yo|uar|e**|b|e***au|tifu|l" 。不在竖线对之间总共有 5 个星号。所以我们返回 5 。
*/
public class NO2315_E_CountAsterisks_x2 {

    @Test
    public void test() {
        assert 2 == countAsterisks("l|*e*et|c**o|*de|");
        assert 0 == countAsterisks("iamprogrammer");
        assert 5 == countAsterisks("yo|uar|e**|b|e***au|tifu|l");
    }

    public int countAsterisks(String s) {
        int res = 0;
        return res;
    }

}
















/**
// 方法1：
public int countAsterisks(String s) {
    int ans = 0;
    int cnt = 0;
    for (char c : s.toCharArray()) {
        if ('|' == c)
            cnt++;

        if (cnt % 2 == 0 && '*' == c)
            ans++;
    }
    return ans;
}
*/