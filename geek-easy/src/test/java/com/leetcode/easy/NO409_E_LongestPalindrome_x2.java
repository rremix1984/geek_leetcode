/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [STRING]
    (简单)
    409. 最长回文串
        给定一个包含大写字母和小写字母的字符串s，返回通过这些字母构造成的最长的回文串 。
        在构造过程中，请注意区分大小写。比如"Aa"不能当做一个回文字符串。
    示例 1:
        输入: s = "abccccdd"
        输出: 7
        解释: 我们可以构造的最长的回文串是"dccaccd", 它的长度是 7。
    示例 2:
        输入: s = "a"
        输入: 1
    提示:
        1 <= s.length <= 2000
        s 只由小写 和/或 大写英文字母组成

    方法一：贪心
    思路
       回文串是一个正着读和反着读都一样的字符串。以回文中心为分界线，对于回文串中左侧的字符 ch，
    在右侧对称的位置也会出现同样的字符。例如在字符串 "abba" 中，回文中心是 "ab|ba" 中竖线的位置，
    而在字符串 "abcba" 中，回文中心是 "ab(c)ba" 中的字符 "c" 本身。我们可以发现，在一个回文串中，
    只有最多一个字符出现了奇数次，其余的字符都出现偶数次。
    那么我们如何通过给定的字符构造一个回文串呢？我们可以将每个字符使用偶数次，使得它们根据回文中心对称。
    在这之后，如果有剩余的字符，我们可以再取出一个，作为回文中心。
    算法
       对于每个字符 ch，假设它出现了 v 次，我们可以使用该字符 v / 2 * 2 次，在回文串的左侧和
    右侧分别放置 v / 2 个字符 ch，其中 / 为整数除法。例如若 "a" 出现了 5 次，那么我们可以使用
    "a" 的次数为 4，回文串的左右两侧分别放置 2 个 "a"。
       如果有任何一个字符 ch 的出现次数 v 为奇数（即 v % 2 == 1），那么可以将这个字符作为回文中心，
    注意只能最多有一个字符作为回文中心。在代码中，我们用 ans 存储回文串的长度，由于在遍历字符时，
    ans 每次会增加 v / 2 * 2，因此 ans 一直为偶数。但在发现了第一个出现次数为奇数的字符后，
    我们将 ans 增加 1，这样 ans 变为奇数，在后面发现其它出现奇数次的字符时，
    我们就不改变 ans 的值了。
*/
public class NO409_E_LongestPalindrome_x2 {

    @Test
    public void test() {
        assert 7 == longestPalindrome("abccccdd");
        assert 1 == longestPalindrome("a");
    }

    public int longestPalindrome(String s) {
        int ans = 0;
        int[] count = new int[128];
        for (char c : s.toCharArray())
            count[c]++;

        for (int c : count) {
            ans += (c / 2) * 2;
            // 最多一个字符出现奇数次，其他的字符都必须是偶数次
            if (c % 2 == 1 && ans % 2 == 0)
                ans++;
        }
        return ans;
    }

}


















/**
// 方法1：
public int longestPalindrome(String s) {
    int ans = 0;
    int[] count = new int[128];
    for (char c : s.toCharArray())
        count[c]++;

    for (int c : count) {
        ans += (c / 2) * 2;
        // 最多一个字符出现奇数次，其他的字符都必须是偶数次
        if (c % 2 == 1 && ans % 2 == 0)
            ans++;
    }
    return ans;
}
*/