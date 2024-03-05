/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [STRING]
    (简单)
    859. 亲密字符串
        给你两个字符串s和goal，只要我们可以通过交换s中的两个字母得到
        与goal相等的结果，就返回true；否则返回false。
        交换字母的定义是：取两个下标i和j（下标从0开始）且满足i != j，
        接着交换s[i]和s[j]处的字符。
        例如，在 "abcd" 中交换下标 0 和下标 2 的元素可以生成 "cbad" 。
    示例 1：
        输入：s = "ab", goal = "ba"
        输出：true
        解释：你可以交换 s[0] = 'a' 和 s[1] = 'b' 生成 "ba"，此时 s 和 goal 相等。
    示例 2：
        输入：s = "ab", goal = "ab"
        输出：false
        解释：你只能交换 s[0] = 'a' 和 s[1] = 'b' 生成 "ba"，此时 s 和 goal 不相等。
    示例 3：
        输入：s = "aa", goal = "aa"
        输出：true
        解释：你可以交换 s[0] = 'a' 和 s[1] = 'a' 生成 "aa"，此时 s 和 goal 相等。
    提示：
        1 <= s.length, goal.length <= 2 * 104
        s 和 goal 由小写英文字母组成
*/
public class NO859_E_BuddyStrings_x2 {

    @Test
    public void test() {
        assert buddyStrings("ab","ba");
        assert !buddyStrings("ab","ab");
        assert buddyStrings("aa","aa");
    }

    public boolean buddyStrings(String A, String B) {
        return false;
    }

}
















/**
// 方法1：
public boolean buddyStrings(String A, String B) {
    if (A.length() != B.length())
        return false;

    int i = 0;
    int j = A.length() - 1;

    char[] arr = A.toCharArray();
    //如果AB相等，判断A中是否有重复字母
    if (A.equals(B)) {
        Set<Character> set = new HashSet<>();
        while (i < A.length())
            if (!set.add(arr[i++]))
                return true;
        return false;
    }

    //如果AB不同，将A中ab不同的位置交换，最后看A是否等于B
    while (i < A.length()) {
        if (A.charAt(i) != B.charAt(i))
            break;
        i++;
    }

    while (j >= 0) {
        if (A.charAt(j) != B.charAt(j))
            break;
        j--;
    }

    if (i != j) {
        swap(arr, i, j);
        return B.equals(new String(arr));
    }
    return false;
}
*/